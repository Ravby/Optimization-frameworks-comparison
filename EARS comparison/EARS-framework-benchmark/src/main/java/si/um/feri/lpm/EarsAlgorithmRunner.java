package si.um.feri.lpm;

import org.um.feri.ears.algorithms.NumberAlgorithm;
import org.um.feri.ears.algorithms.so.abc.ABC;
import org.um.feri.ears.algorithms.so.de.DE;
import org.um.feri.ears.algorithms.so.gwo.GWO;
import org.um.feri.ears.algorithms.so.pso.PSO;
import org.um.feri.ears.problems.*;
import si.um.feri.lpm.problems.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EarsAlgorithmRunner {

    public static void main(String[] args) throws StopCriterionException {

        final int NUMBER_OF_RUNS = 50;
        final int MAX_EVALUATIONS = 15000;

        NumberAlgorithm algorithm;

        ArrayList<LoggingDoubleProblem> problems = new ArrayList<>();
        problems.add(new ShiftedSphere(60));
        problems.add(new ShiftedSumOfSquares(60));
        problems.add(new ShiftedSchwefel(60));
        problems.add(new ShiftedRastrigin(60));
        problems.add(new ShiftedAckley(60));
        problems.add(new ShiftedGriewank(60));
        //problems.add(new Sphere(60));
        //problems.add(new SumOfSquares(60));
        //problems.add(new Schwefel(60));
        //problems.add(new Rastrigin(60));
        //problems.add(new Ackley(60));
        //problems.add(new Griewank(60));
        problems.add(new Rosenbrock(60));
        problems.add(new Foxholes());
        problems.add(new SixHumpCamelBack());
        problems.add(new Branin());
        problems.add(new GoldsteinPrice());
        problems.add(new Hartman());

        boolean storeRunResults = false;
        boolean storeFinalResults = true;


        long start = System.nanoTime();

        double[] results = new double[NUMBER_OF_RUNS];
        for (LoggingDoubleProblem problem : problems) {

            System.out.println("Problem: " + problem.getName());

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                problem.reset();
                algorithm = new PSO(30, 0.7, 2, 2);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();
                List<double[]> improvements = problem.getImprovements();
                if(storeRunResults) writeRunToFile("PSO-EARS", problem, i + 1, improvements);

            }
            if(storeFinalResults) writeResultsToFile("PSO-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                problem.reset();
                algorithm = new DE(DE.Strategy.DE_RAND_1_BIN, 50, 0.5, 0.9);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();
                List<double[]> improvements = problem.getImprovements();
                if(storeRunResults) writeRunToFile("DE-EARS", problem, i + 1, improvements);
            }
            if(storeFinalResults) writeResultsToFile("DE-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                problem.reset();
                algorithm = new ABC(125, 100);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();
                List<double[]> improvements = problem.getImprovements();
                if(storeRunResults) writeRunToFile("ABC-EARS", problem, i + 1, improvements);
            }
            if(storeFinalResults) writeResultsToFile("ABC-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                problem.reset();
                algorithm = new GWO(30);

                Task task = new Task(problem, StopCriterion.EVALUATIONS, MAX_EVALUATIONS, 0, 0);

                NumberSolution solution = algorithm.execute(task);
                results[i] = solution.getEval();
                List<double[]> improvements = problem.getImprovements();
                if(storeRunResults) writeRunToFile("GWO-EARS", problem, i + 1, improvements);
            }
            if(storeFinalResults) writeResultsToFile("GWO-EARS_" + problem.getName() + "D" + problem.getNumberOfDimensions(), results);

        }
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000000.0);
    }

    private static void writeRunToFile(String algorithmName, LoggingDoubleProblem problem, int runNumber, List<double[]> improvements) {
        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile();
        String filesDir = parentDirFile + File.separator + "Algorithm results" + File.separator + "Runs";

        String filename = String.format("%s_%s_vars=%d_run=%d.csv",
                algorithmName, problem.getName(), problem.getNumberOfDimensions(), runNumber);

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
