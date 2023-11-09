package si.um.feri.lpm.experiments;

import org.moeaframework.algorithm.single.DifferentialEvolution;
import org.moeaframework.algorithm.single.EvolutionStrategy;
import org.moeaframework.algorithm.single.GeneticAlgorithm;
import org.moeaframework.algorithm.single.LinearDominanceComparator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Settings;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.operator.real.DifferentialEvolutionSelection;
import org.moeaframework.core.operator.real.DifferentialEvolutionVariation;
import org.moeaframework.core.spi.OperatorFactory;
import org.moeaframework.examples.misc.SingleObjectiveExample;
import org.moeaframework.problem.AbstractProblem;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        final int NUMBER_OF_RUNS = 100;
        final int MAX_EVALUATIONS = 15000;

        ArrayList<AbstractProblem> problems = new ArrayList<>();
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


        for (AbstractProblem problem : problems) {
            System.out.println("Problem: " + problem.getName());
            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                //A single elite individual is guaranteed to survive between generations.
                GeneticAlgorithm ga = new GeneticAlgorithm(problem,
                        100,
                        new LinearDominanceComparator(),
                        new RandomInitialization(problem),
                        OperatorFactory.getInstance().getVariation(problem));

                ga.run(MAX_EVALUATIONS);
                results[i] = ga.getResult().get(0).getObjective(0);
            }
            writeResultsToFile("GA-MOEA_" + problem.getName() + "D" + problem.getNumberOfVariables(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                //DE/rand/1/bin
                DifferentialEvolution de = new DifferentialEvolution(problem,
                        50,
                        new LinearDominanceComparator(),
                        new RandomInitialization(problem),
                        new DifferentialEvolutionSelection(),
                        new DifferentialEvolutionVariation(0.9, 0.5));

                de.run(MAX_EVALUATIONS);
                results[i] = de.getResult().get(0).getObjective(0);
            }
            writeResultsToFile("DE-MOEA_" + problem.getName() + "D" + problem.getNumberOfVariables(), results);

            /*for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                //ES (1 + 1) is the standard evolution strategies algorithm
                //TODO set parameters for algorithms
                EvolutionStrategy es = new EvolutionStrategy(problem);
                es.run(MAX_EVALUATIONS);
                results[i] = es.getResult().get(0).getObjective(0);
            }
            writeResultsToFile( "ES-MOEA_" + problem.getName()+"D"+problem.getNumberOfVariables(), results);
            */
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
