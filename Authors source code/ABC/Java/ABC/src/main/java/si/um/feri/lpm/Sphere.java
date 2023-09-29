package si.um.feri.lpm;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Sphere extends Problem {

    public Sphere(int dimensions) {
        super("Sphere", dimensions);

        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        IntStream.range(0, dimensions).forEach(i -> {
            lowerLimit.add(-100.0);
            upperLimit.add(100.0);
        });
    }

    @Override
    public double evaluate(double[] x) {
        double sum = 0.0;
        for (double v : x) {
            sum += v * v;
        }

        return sum;
    }
}
