package si.um.feri.lpm;

import org.um.feri.ears.algorithms.NumberAlgorithm;
import org.um.feri.ears.algorithms.so.abc.ABC;
import org.um.feri.ears.algorithms.so.de.DEAlgorithm;
import org.um.feri.ears.algorithms.so.gwo.GWO;
import org.um.feri.ears.algorithms.so.pso.PSO;
import org.um.feri.ears.problems.*;
import org.um.feri.ears.problems.unconstrained.*;

import java.io.*;
import java.util.ArrayList;

public class EarsAlgorithmRunner {

    public static void main(String[] args) throws StopCriterionException {

        final int NUMBER_OF_RUNS = 100;
        final int MAX_EVALUATIONS = 15000;

        NumberAlgorithm algorithm;

        ArrayList<DoubleProblem> problems = new ArrayList<>();
        problems.add(new Sphere(60));
        problems.add(new SumOfSquares(60));
        problems.add(new Schwefel12(60));
        problems.add(new Rastrigin(60));
        problems.add(new Ackley1(60));
        problems.add(new Griewank(60));
        problems.add(new RosenbrockD2a(60));
        problems.add(new Foxholes());
        problems.add(new SixHumpCamelBack());
        problems.add(new Branin1());
        problems.add(new GoldsteinPrice());
        problems.add(new Hartman3());


        long start = System.nanoTime();

        double[] results = new double[NUMBER_OF_RUNS];
        for (DoubleProblem problem : problems) {

            System.out.println("Problem: " + problem.getName());

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                algorithm = new PSO(30, 0.7, 2, 2);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();

            }
            writeResultsToFile("PSO-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                algorithm = new DEAlgorithm(DEAlgorithm.Strategy.DE_RAND_1_BIN, 50, 0.5, 0.9);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();

            }
            writeResultsToFile("DE-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                algorithm = new ABC(125, 100);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();

            }
            writeResultsToFile("ABC-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                algorithm = new GWO(30);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();

            }
            writeResultsToFile("GWO-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

        }
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000000.0);
    }

    private static void writeResultsToFile(String name, double[] results) {

        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile();
        String filesDir = parentDirFile + File.separator + "Algorithm results";

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
