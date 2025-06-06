package org.uma.jmetal.problem.singleobjective;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * Class representing problem Griewank
 */
public class Griewank extends LoggingDoubleProblem {

    public Griewank() {
        this(10);
    }

    /**
     * Constructor
     * Creates a default instance of the Griewank problem
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public Griewank(Integer numberOfVariables) {
        numberOfObjectives(1);
        numberOfConstraints(0);
        name("Griewank");

        List<Double> lowerLimit = new ArrayList<>(numberOfVariables);
        List<Double> upperLimit = new ArrayList<>(numberOfVariables);

        for (int i = 0; i < numberOfVariables; i++) {
            lowerLimit.add(-600.0);
            upperLimit.add(600.0);
        }

        variableBounds(lowerLimit, upperLimit);
    }

    /**
     * Evaluate() method
     */
    @Override
    public DoubleSolution computeFitness(DoubleSolution solution) {
        int numberOfVariables = numberOfVariables();

        double[] x = new double[numberOfVariables];

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.variables().get(i);
        }

        double sum = 0.0;
        double mult = 1.0;
        double a = 4000.0;
        for (int var = 0; var < numberOfVariables; var++) {
            sum += x[var] * x[var];
            mult *= Math.cos(x[var] / Math.sqrt(var + 1));
        }

        solution.objectives()[0] = 1.0 / a * sum - mult + 1.0;

        return solution;
    }
}

