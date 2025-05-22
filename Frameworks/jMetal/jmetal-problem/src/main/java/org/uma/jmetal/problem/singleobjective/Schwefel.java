package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class Schwefel extends LoggingDoubleProblem {

    public Schwefel() {
        this(10);
    }

    public Schwefel(Integer numberOfVariables) {
        numberOfObjectives(1);
        name("Schwefel");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>(numberOfVariables);

        IntStream.range(0, numberOfVariables).forEach(i -> {
            lowerLimit.add(-100.0);
            upperLimit.add(100.0);
        });

        variableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution computeFitness(DoubleSolution solution) {

        double fitness = 0;
        double sum;
        for (int i = 0; i < numberOfVariables(); i++) {
            sum = 0;
            for (int j = 0; j < i; j++) {
                sum += solution.variables().get(i);
            }
            fitness += pow(sum, 2);
        }

        solution.objectives()[0] = fitness;

        return solution;
    }
}
