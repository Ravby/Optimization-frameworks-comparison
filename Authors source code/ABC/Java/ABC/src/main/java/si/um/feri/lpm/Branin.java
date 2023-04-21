package si.um.feri.lpm;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Branin extends Problem {

    public Branin() {
        super("Branin", 2);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        lowerLimit.add(-5.0);
        upperLimit.add(10.0);

        lowerLimit.add(0.0);
        upperLimit.add(15.0);
    }

    @Override
    public double evaluate(double[] x) {

        double fitness = pow(x[1] - (5.1 / (4 * PI * PI)) * x[0] * x[0] + (5.0 / PI) * x[0] - 6, 2) + 10 * (1 - 1.0 / (8.0 * PI)) * cos(x[0]) + 10;

        return fitness;
    }
}
