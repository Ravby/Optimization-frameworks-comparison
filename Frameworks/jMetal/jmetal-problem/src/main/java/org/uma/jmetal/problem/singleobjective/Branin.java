package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class Branin extends LoggingDoubleProblem {

    public Branin() {
        numberOfObjectives(1);
        name("Branin");

        List<Double> lowerLimit = new ArrayList<>(Collections.nCopies(2, 0.0));
        List<Double> upperLimit = new ArrayList<>(Collections.nCopies(2, 0.0));

        lowerLimit.set(0, -5.0);
        upperLimit.set(0, 10.0);

        lowerLimit.set(1, 0.0);
        upperLimit.set(1, 15.0);

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution computeFitness(DoubleSolution solution) {

        double x0 = solution.variables().get(0);
        double x1 = solution.variables().get(1);
        solution.objectives()[0] = pow(x1 - (5.1 / (4 * PI * PI)) * x0 * x0 + (5.0 / PI) * x0 - 6, 2) + 10 * (1 - 1.0 / (8.0 * PI)) * cos(x0) + 10;

        return solution;
    }
}
