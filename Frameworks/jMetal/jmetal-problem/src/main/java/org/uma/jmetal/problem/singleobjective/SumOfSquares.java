package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class SumOfSquares  extends AbstractDoubleProblem {

    public SumOfSquares() {
        this(10);
    }

    public SumOfSquares(Integer numberOfVariables) {
        numberOfObjectives(1);
        name("SumOfSquares");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>(numberOfVariables);

        IntStream.range(0, numberOfVariables).forEach(i -> {
            lowerLimit.add(-100.0);
            upperLimit.add(100.0);
        });

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {

        double fitness = 0;
        for (int i = 0; i < numberOfVariables(); i++) {
            fitness += (i + 1) * pow(solution.variables().get(i), 2);
        }
        solution.objectives()[0] = fitness;

        return solution;
    }
}
