package si.um.feri.lpm.problems;

import org.um.feri.ears.problems.DoubleProblem;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;

/*
https://www.sfu.ca/~ssurjano/goldpr.html
http://benchmarkfcns.xyz/benchmarkfcns/goldsteinpricefcn.html
 */

public class GoldsteinPrice extends LoggingDoubleProblem {
    public GoldsteinPrice() {
        super("GoldsteinPrice", 2, 1, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -2.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 2.0));

        decisionSpaceOptima[0][0] = 0;
        decisionSpaceOptima[0][1] = -1;
        objectiveSpaceOptima[0] = 3.0;
    }

    @Override
    public double computeFitness(double[] x) {
        return (1 + pow(x[0] + x[1] + 1, 2) * (19 - 14 * x[0] + 3 * x[0] * x[0] - 14 * x[1] + 6 * x[0] * x[1] + 3 * x[1] * x[1])) *
                (30 + pow(2 * x[0] - 3 * x[1], 2) * (18 - 32 * x[0] + 12 * x[0] * x[0] + 48 * x[1] - 36 * x[0] * x[1] + 27 * x[1] * x[1]));
    }
}
