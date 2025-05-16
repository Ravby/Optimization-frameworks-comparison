package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class Ackley extends LoggingDoubleProblem {

    public Ackley() {
        this(10);
    }

    public Ackley(Integer numberOfVariables) {
        numberOfObjectives(1);
        name("Ackley");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>(numberOfVariables);

        IntStream.range(0, numberOfVariables).forEach(i -> {
            lowerLimit.add(-32.0);
            upperLimit.add(32.0);
        });

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution computeFitness(DoubleSolution solution) {
        double a = 20.0, b = 0.2, c = 2 * PI;
        double sum1 = 0, sum2 = 0;

        for (double v : solution.variables()) {
            sum1 += pow(v, 2);
            sum2 += cos(c * v);
        }
        solution.objectives()[0] = -a * exp(-b * sqrt(1.0 / numberOfVariables() * sum1)) - exp(1.0 / numberOfVariables() * sum2) + a + E;

        return solution;
    }
}
