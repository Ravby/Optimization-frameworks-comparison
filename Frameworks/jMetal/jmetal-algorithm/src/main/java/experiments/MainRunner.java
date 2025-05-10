package experiments;


import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.examples.AlgorithmRunner;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.CovarianceMatrixAdaptationEvolutionStrategy;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.algorithm.singleobjective.particleswarmoptimization.StandardPSO2007;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.*;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.io.*;
import java.util.ArrayList;

public class MainRunner {

    public static void main(String[] args) {

        final int NUMBER_OF_RUNS = 100;
        final int MAX_EVALUATIONS = 15000;

        Algorithm<DoubleSolution> algorithm;
        SolutionListEvaluator<DoubleSolution> evaluator;

        ArrayList<DoubleProblem> problems = new ArrayList<>();

        problems.add(new ShiftedSphere(60));
        problems.add(new ShiftedSumOfSquares(60));
        problems.add(new ShiftedSchwefel(60));
        problems.add(new ShiftedRastrigin(60));
        problems.add(new ShiftedAckley(60));
        problems.add(new ShiftedGriewank(60));
        problems.add(new Sphere(60));
        problems.add(new SumOfSquares(60));
        problems.add(new Schwefel(60));
        problems.add(new Rastrigin(60));
        problems.add(new Ackley(60));
        problems.add(new Griewank(60));
        problems.add(new Rosenbrock(60));
        problems.add(new ShekelsFoxholes());
        problems.add(new SixHumpCamelBack());
        problems.add(new Branin());
        problems.add(new GoldsteinPrice());
        problems.add(new Hartman());

        evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();

        long start = System.nanoTime();

        double[] results = new double[NUMBER_OF_RUNS];
        for (DoubleProblem problem : problems) {

            System.out.println("Problem: " + problem.name());

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                CovarianceMatrixAdaptationEvolutionStrategy.Builder
                        builder = new CovarianceMatrixAdaptationEvolutionStrategy.Builder(problem)
                        .setMaxEvaluations(MAX_EVALUATIONS)
                        .setLambda(30) //population size
                        .setSigma(0.5);

                algorithm = builder.build();

                AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

                DoubleSolution solution = algorithm.result();
                results[i] = solution.objectives()[0];
            }
            writeResultsToFile( "CMA-ES-jMetal_" + problem.name()+"D"+problem.numberOfVariables(), results);

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

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(0.95, 20.0);
                MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(0.025, 20.0);
                GeneticAlgorithmBuilder<DoubleSolution> builder =
                        new GeneticAlgorithmBuilder<>(problem, crossoverOperator, mutationOperator)
                                .setPopulationSize(100)
                                .setMaxEvaluations(MAX_EVALUATIONS);

                algorithm = builder.build();

                AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

                DoubleSolution solution = algorithm.result();
                results[i] = solution.objectives()[0];
            }
            writeResultsToFile( "GA-jMetal_" + problem.name()+"D"+problem.numberOfVariables(), results);
        }


        evaluator.shutdown();
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000000.0);
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
