package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class GoldsteinPrice extends AbstractDoubleProblem {

    public GoldsteinPrice() {
        numberOfObjectives(1);
        name("GoldsteinPrice");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>(2);

        IntStream.range(0, 2).forEach(i -> {
            lowerLimit.add(-2.0);
            upperLimit.add(2.0);
        });

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {

        double x0 = solution.variables().get(0);
        double x1 = solution.variables().get(1);
        
        solution.objectives()[0] = (1 + pow(x0 + x1 + 1, 2) * (19 - 14 * x0 + 3 * x0 * x0 - 14 * x1 + 6 * x0 * x1 + 3 * x1 * x1)) *
                (30 + pow(2 * x0 - 3 * x1, 2) * (18 - 32 * x0 + 12 * x0 * x0 + 48 * x1 - 36 * x0 * x1 + 27 * x1 * x1));

        return solution;
    }
}
