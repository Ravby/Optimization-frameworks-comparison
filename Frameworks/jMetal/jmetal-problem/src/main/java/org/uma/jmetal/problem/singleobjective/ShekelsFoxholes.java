package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class ShekelsFoxholes extends AbstractDoubleProblem {

    private static final double[][] a = {{-32, -32},
            {-16, -32},
            {0, -32},
            {16, -32},
            {32, -32},
            {-32, -16},
            {-16, -16},
            {0, -16},
            {16, -16},
            {32, -16},
            {-32, 0},
            {-16, 0},
            {0, 0},
            {16, 0},
            {32, 0},
            {-32, 16},
            {-16, 16},
            {0, 16},
            {16, 16},
            {32, 16},
            {-32, 32},
            {-16, 32},
            {0, 32},
            {16, 32},
            {32, 32}
    };

    public ShekelsFoxholes() {
        numberOfObjectives(1);
        name("ShekelsFoxholes");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>(2);

        IntStream.range(0, 2).forEach(i -> {
            lowerLimit.add(-65.536);
            upperLimit.add(65.536);
        });

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {

        double fitness = 0;
        double sum;
        for (int j = 0; j < 25; j++) {
            sum = 0;
            for (int i = 0; i < numberOfVariables(); i++) {
                sum += pow(solution.variables().get(i) - a[j][i], 6);
            }
            sum += j + 1;
            fitness += 1.0 / sum;
        }
        fitness += 1.0 / 500.0;

        solution.objectives()[0] = pow(fitness, -1);

        return solution;
    }
}
