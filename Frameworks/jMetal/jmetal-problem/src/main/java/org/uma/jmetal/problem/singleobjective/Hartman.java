package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Hartman extends LoggingDoubleProblem {

    private static final double[][] a = {
            {3, 10, 30},
            {0.1, 10, 35},
            {3, 10, 30},
            {0.1, 10, 35}
    };
    private static final double[][] p = {
            {0.3689, 0.1170, 0.2673},
            {0.4699, 0.4387, 0.7470},
            {0.1091, 0.8732, 0.5547},
            {0.03815, 0.5743, 0.8828}
    };
    private static final double[] c = {1, 1.2, 3, 3.2};

    public Hartman() {
        numberOfObjectives(1);
        name("Hartman");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>(3);

        IntStream.range(0, 3).forEach(i -> {
            lowerLimit.add(0.0);
            upperLimit.add(1.0);
        });

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution computeFitness(DoubleSolution solution) {

        double fitness = 0;
        double sum;
        for (int i = 0; i < 4; i++) {
            sum = 0;
            for (int j = 0; j < numberOfVariables(); j++) {
                sum += a[i][j] * pow(solution.variables().get(j) - p[i][j], 2);
            }
            fitness += c[i] * exp(sum * (-1));
        }

        solution.objectives()[0] = fitness * -1;

        return solution;
    }
}
