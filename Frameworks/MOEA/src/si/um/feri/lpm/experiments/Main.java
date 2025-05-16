package si.um.feri.lpm.experiments;

import org.moeaframework.algorithm.CMAES;
import org.moeaframework.algorithm.single.DifferentialEvolution;
import org.moeaframework.algorithm.single.EvolutionStrategy;
import org.moeaframework.algorithm.single.GeneticAlgorithm;
import org.moeaframework.algorithm.single.LinearDominanceComparator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Settings;
import org.moeaframework.core.Solution;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.operator.real.DifferentialEvolutionSelection;
import org.moeaframework.core.operator.real.DifferentialEvolutionVariation;
import org.moeaframework.core.spi.OperatorFactory;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.examples.misc.SingleObjectiveExample;
import org.moeaframework.problem.AbstractProblem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        final int NUMBER_OF_RUNS = 50;
        final int MAX_EVALUATIONS = 15000;

        ArrayList<LoggingAbstractProblem> problems = new ArrayList<>();

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

        double[] results = new double[NUMBER_OF_RUNS];


        for (LoggingAbstractProblem problem : problems) {
            System.out.println("Problem: " + problem.getName());

            /*for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                problem.reset();
                System.out.println("Run: " + i);

                CMAES cmaes = new CMAES(problem);
                cmaes.setLambda(30);
                cmaes.setSigma(0.5);

                cmaes.run(MAX_EVALUATIONS);

                results[i] = cmaes.getResult().get(0).getObjective(0);
            }
            writeResultsToFile("CMA-ES-MOEA_" + problem.getName() + "D" + problem.getNumberOfVariables(), results);*/

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                problem.reset();
                //A single elite individual is guaranteed to survive between generations.
                GeneticAlgorithm ga = new GeneticAlgorithm(problem,
                        100,
                        new LinearDominanceComparator(),
                        new RandomInitialization(problem),
                        OperatorFactory.getInstance().getVariation(problem));

                ga.run(MAX_EVALUATIONS);
                results[i] = ga.getResult().get(0).getObjective(0);
                List<double[]> improvements = problem.getImprovements();
                writeRunToFile("GA-MOEA", problem, i + 1, improvements);
            }
            writeResultsToFile("GA-MOEA_" + problem.getName() + "D" + problem.getNumberOfVariables(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                problem.reset();
                //DE/rand/1/bin
                DifferentialEvolution de = new DifferentialEvolution(problem,
                        50,
                        new LinearDominanceComparator(),
                        new RandomInitialization(problem),
                        new DifferentialEvolutionSelection(),
                        new DifferentialEvolutionVariation(0.9, 0.5));

                de.run(MAX_EVALUATIONS);
                results[i] = de.getResult().get(0).getObjective(0);
                List<double[]> improvements = problem.getImprovements();
                writeRunToFile("DE-MOEA", problem, i + 1, improvements);
            }
            writeResultsToFile("DE-MOEA_" + problem.getName() + "D" + problem.getNumberOfVariables(), results);
        }
    }

    private static void writeRunToFile(String algorithmName, LoggingAbstractProblem problem, int runNumber, List<double[]> improvements) {
        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile().getParentFile();
        String filesDir = parentDirFile + File.separator + "EARS comparison" + File.separator + "Algorithm results" + File.separator + "Runs";

        String filename = String.format("%s_%s_vars=%d_run=%d.csv",
                algorithmName, problem.getName(), problem.getNumberOfVariables(), runNumber);

        String fileLocation = filesDir + File.separator + filename;

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
            bw.write("Evaluations,Fitness\n");
            for (double[] improvement : improvements) {
                bw.write(String.format(Locale.US, "%d,%f\n", (long) improvement[0], improvement[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeResultsToFile(String name, double[] results) {

        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile().getParentFile();
        String filesDir = parentDirFile + File.separator + "EARS comparison" + File.separator + "Algorithm results";

        String fileLocation = filesDir + File.separator + name + ".txt";

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
