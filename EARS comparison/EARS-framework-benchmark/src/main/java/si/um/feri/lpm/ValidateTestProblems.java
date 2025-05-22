package si.um.feri.lpm;

import si.um.feri.lpm.problems.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidateTestProblems {

    public static void main(String[] args) {

        final int NUMBER_OF_DIMENSIONS = 50;

        ArrayList<LoggingDoubleProblem> problems = new ArrayList<>();
        problems.add(new ShiftedSphere(NUMBER_OF_DIMENSIONS));
        problems.add(new ShiftedSumOfSquares(NUMBER_OF_DIMENSIONS));
        problems.add(new ShiftedSchwefel(NUMBER_OF_DIMENSIONS));
        problems.add(new ShiftedRastrigin(NUMBER_OF_DIMENSIONS));
        problems.add(new ShiftedAckley(NUMBER_OF_DIMENSIONS));
        problems.add(new ShiftedGriewank(NUMBER_OF_DIMENSIONS));
        problems.add(new Sphere(NUMBER_OF_DIMENSIONS));
        problems.add(new SumOfSquares(NUMBER_OF_DIMENSIONS));
        problems.add(new Schwefel(NUMBER_OF_DIMENSIONS));
        problems.add(new Rastrigin(NUMBER_OF_DIMENSIONS));
        problems.add(new Ackley(NUMBER_OF_DIMENSIONS));
        problems.add(new Griewank(NUMBER_OF_DIMENSIONS));
        problems.add(new Rosenbrock(NUMBER_OF_DIMENSIONS));
        problems.add(new Foxholes());
        problems.add(new SixHumpCamelBack());
        problems.add(new Branin());
        problems.add(new GoldsteinPrice());
        problems.add(new Hartman());

        for (LoggingDoubleProblem problem : problems) {
            System.out.println("Problem: " + problem.getName());
            double[] optimumX = problem.getDecisionSpaceOptima()[0];
            double optimum = problem.eval(optimumX);
            System.out.println("Optimum fitness: " + optimum);

            double[] ones = new double[problem.getNumberOfDimensions()];
            Arrays.fill(ones, 1.0);
            double optimumAtOnes = problem.eval(ones);
            System.out.println("Fitness at all ones: " + optimumAtOnes);
        }
    }
}
