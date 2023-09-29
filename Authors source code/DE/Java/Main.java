package DeApp1;

import DeApp1.de.DERand1Bin;
import DeApp1.de.DERandom;
import DeApp1.de.DEStrategy;
import DeApp1.de.T_DEOptimizer;
import DeApp1.problem.*;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        final int NUMBER_OF_RUNS = 100;
        final int MAX_EVALUATIONS = 15000;

        ArrayList<DEProblem> problems = new ArrayList<>();
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

        DERand1Bin strategy   = new DERand1Bin();
        DERandom random = new DERandom();
        strategy.init (random);


        T_DEOptimizer de   = new T_DEOptimizer(null);

        de.Strategem = new DEStrategy[1];
        de.Strategem[0] = strategy;
        de.current_strategy = 0;

        de.NP      = 50;
        de.F       = 0.5;
        de.Cr      = 0.9;
        de.Range   = 100;
        de.Refresh = 299; //iterations (15000 - NP) / NP = 299

        double[] results = new double[NUMBER_OF_RUNS];

        for (DEProblem problem : problems) {
            System.out.println("Problem: " + problem.name);
            for (int i = 0; i < NUMBER_OF_RUNS; i++) {

                de.evaluation = 0;
                de.generation = 0;

                results[i] = de.optimize(problem);
                if(de.getEvaluation() != MAX_EVALUATIONS)
                    System.err.println("Algorithm consumed more evaluations than allowed!");

                System.out.println("Run: " + i + " Result: " + results[i]);
            }
            writeResultsToFile( "DE-Author-Java_" + problem.name+"D"+problem.dim, results);
        }
    }

    private static void writeResultsToFile(String name, double[] results) {

        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile().getParentFile().getParentFile();
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
