package si.um.feri.lpm;

import java.util.ArrayList;

public class SumOfSquares extends Problem {

        public SumOfSquares(int dimensions) {
            super("SumOfSquares", dimensions);

            lowerLimit = new ArrayList<>();
            upperLimit = new ArrayList<>();

            for (int i = 0; i < dimensions; i++) {
                lowerLimit.add(-100.0);
                upperLimit.add(100.0);
            }
        }

        @Override
        public double evaluate(double[] x) {
            double sum = 0.0;
            for (int i = 0; i < dimensions; i++) {
                sum += (i + 1) * x[i] * x[i];
            }
            return sum;
        }
}
