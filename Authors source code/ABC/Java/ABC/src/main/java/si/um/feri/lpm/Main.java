package si.um.feri.lpm;

import java.io.*;
import java.util.ArrayList;

public class Main {
    static BeeColony bee = new BeeColony();

    public static void main(String[] args) {

        final int NUMBER_OF_RUNS = 100;
        final int MAX_EVALUATIONS = 15000;
        bee.maxEval = MAX_EVALUATIONS;
        bee.NP = 125;
        bee.limit = 100;

        ArrayList<Problem> problems = new ArrayList<>();
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

        double[] results = new double[NUMBER_OF_RUNS];

        for (Problem problem : problems) {
            System.out.println("Problem: " + problem.getName());
            bee.setProblem(problem);
            for (int i = 0; i < NUMBER_OF_RUNS; i++) {
                bee.currentEval = 0;
                bee.initial();
                bee.MemorizeBestSource();
                int iter = 0;
                while (bee.currentEval < bee.maxEval) {
                    iter++;
                    bee.SendEmployedBees();
                    bee.CalculateProbabilities();
                    if(bee.currentEval >= bee.maxEval)
                        break;
                    bee.SendOnlookerBees();
                    bee.MemorizeBestSource();
                    bee.SendScoutBees();
                }
                if(bee.currentEval != bee.maxEval)
                    System.err.println("Algorithm consumed more evaluations than allowed!");

                results[i] = bee.GlobalMin;
                //System.out.println("Run: " + i + " Result: " + results[i]);
            }
            writeResultsToFile( "ABC-Author-Java_" + problem.getName()+"D"+problem.getNumberOfDimensions(), results);
        }
    }

    private static void writeResultsToFile(String name, double[] results) {

        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile().getParentFile().getParentFile().getParentFile();
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