package si.um.feri.lpm;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class ShekelsFoxholes extends Problem {
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
        super("ShekelsFoxholes", 2);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-65.536);
            upperLimit.add(65.536);
        }
    }

    @Override
    public double evaluate(double[] x) {
        double fitness = 0;
        double sum;
        for (int j = 0; j < 25; j++) {
            sum = 0;
            for (int i = 0; i < dimensions; i++) {
                sum += pow(x[i] - a[j][i], 6);
            }
            sum += j + 1;
            fitness += 1.0 / sum;
        }
        fitness += 1.0 / 500.0;
        fitness = pow(fitness, -1);

        return fitness;
    }
}
