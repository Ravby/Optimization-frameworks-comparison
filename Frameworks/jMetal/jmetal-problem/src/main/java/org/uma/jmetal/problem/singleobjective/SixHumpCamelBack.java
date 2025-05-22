package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class SixHumpCamelBack  extends LoggingDoubleProblem {

    public SixHumpCamelBack() {
        numberOfObjectives(1);
        name("SixHumpCamelBack");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>(2);

        IntStream.range(0, 2).forEach(i -> {
            lowerLimit.add(-5.0);
            upperLimit.add(5.0);
        });

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution computeFitness(DoubleSolution solution) {

        double x0 = solution.variables().get(0);
        double x1 = solution.variables().get(1);

        solution.objectives()[0] = 4 * pow(x0, 2)
                - 2.1 * pow(x0, 4)
                + (1.0 / 3.0) * pow(x0, 6)
                + x0 * x1
                - 4 * pow(x1, 2)
                + 4 * pow(x1, 4);

        return solution;
    }
}
