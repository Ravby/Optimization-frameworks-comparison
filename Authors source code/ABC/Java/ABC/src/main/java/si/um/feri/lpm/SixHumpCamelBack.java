package si.um.feri.lpm;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class SixHumpCamelBack extends Problem {

    public SixHumpCamelBack() {
        super("SixHumpCamelBack", 2);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-5.0);
            upperLimit.add(5.0);
        }
    }

    @Override
    public double evaluate(double[] x) {
        double fitness = 0;
        fitness = 4 * pow(x[0], 2)
                - 2.1 * pow(x[0], 4)
                + (1.0 / 3.0) * pow(x[0], 6)
                + x[0] * x[1]
                - 4 * pow(x[1], 2)
                + 4 * pow(x[1], 4);
        return fitness;
    }
}
