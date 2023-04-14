package experiments;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.examples.AlgorithmRunner;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolution;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.algorithm.singleobjective.particleswarmoptimization.StandardPSO2007;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.problem.ProblemFactory;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.*;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.MultiThreadedSolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainRunner {

    public static void main(String[] args) {

        final int NUMBER_OF_RUNS = 100;
        final int MAX_EVALUATIONS = 15000;

        Algorithm<DoubleSolution> algorithm;
        SolutionListEvaluator<DoubleSolution> evaluator;

        ArrayList<DoubleProblem> problems = new ArrayList<>();
        problems.add(new Sphere(30));
        problems.add(new SumOfSquares(30));
        problems.add(new Schwefel(30));
        problems.add(new Rastrigin(30));
        problems.add(new Ackley(30));
        problems.add(new Griewank(30));
        problems.add(new Rosenbrock(30));
        problems.add(new ShekelsFoxholes());
        problems.add(new SixHumpCamelBack());
        problems.add(new Branin());
        problems.add(new GoldsteinPrice());
        problems.add(new Hartman());

        evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();

        double[] results = new double[NUMBER_OF_RUNS];
        for (DoubleProblem problem : problems) {

            System.out.println("Problem: " + problem.name());

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                algorithm = new StandardPSO2007(problem,30,0.7, MAX_EVALUATIONS / 30,3,evaluator);

                AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

                DoubleSolution solution = algorithm.result();
                results[i] = solution.objectives()[0];

            }
            writeResultsToFile( "PSO-jMetal_" + problem.name()+"D"+problem.numberOfVariables(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                DifferentialEvolutionCrossover crossover =
                        new DifferentialEvolutionCrossover(
                                0.9, 0.5, DifferentialEvolutionCrossover.DE_VARIANT.RAND_1_BIN);

                algorithm = new DifferentialEvolutionBuilder(problem)
                        .setSolutionListEvaluator(evaluator)
                        .setMaxEvaluations(MAX_EVALUATIONS)
                        .setCrossover(crossover)
                        .setPopulationSize(50)
                        .build();

                AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

                DoubleSolution solution = algorithm.result();
                results[i] = solution.objectives()[0];

            }
            writeResultsToFile( "DE-jMetal_" + problem.name()+"D"+problem.numberOfVariables(), results);
        }

        evaluator.shutdown();
    }

    private static void writeResultsToFile(String name, double[] results) {

        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile().getParentFile();
        String filesDir = parentDirFile + File.separator + "EARS comparison" + File.separator + "Algorithm results";

        String fileLocation = filesDir  + File.separator + name + ".txt";

        File file = new File(fileLocation);
        String directory = file.getParent();
        if (directory == null) {
            directory = System.getProperty("user.dir");
            fileLocation = directory + File.separator + fileLocation;
            file = new File(fileLocation);
        }
        File fileDirectory = new File(directory);
        try {
            fileDirectory.mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))) {
            for (double result : results) {
                bw.write(result + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
