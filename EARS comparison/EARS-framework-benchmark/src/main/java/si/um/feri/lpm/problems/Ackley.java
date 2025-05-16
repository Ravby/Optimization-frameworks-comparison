package si.um.feri.lpm.problems;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.*;

public class Ackley extends LoggingDoubleProblem {

    public Ackley(int d) {
        super("Ackley", d, 1, 1, 0);
        lowerLimit = new ArrayList<Double>(Collections.nCopies(numberOfDimensions, -32.0));
        upperLimit = new ArrayList<Double>(Collections.nCopies(numberOfDimensions, 32.0));
    }

    @Override
    public double computeFitness(double[] x) {
        double a = 20.0, b = 0.2, c = 2 * PI;
        double sum1 = 0, sum2 = 0;

        for (int i = 0; i < numberOfDimensions; i++) {
            sum1 += pow(x[i], 2);
            sum2 += cos(c * x[i]);
        }
        return -a * exp(-b * sqrt(1.0 / numberOfDimensions * sum1)) - exp(1.0 / numberOfDimensions * sum2) + a + E;
    }
}
