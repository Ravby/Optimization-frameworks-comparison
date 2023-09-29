package si.um.feri.lpm;

import java.util.List;

public abstract class Problem {
    String name;
    int dimensions;
    List<Double> upperLimit;
    List<Double> lowerLimit;

    public Problem(String name, int dimensions) {
        this.name = name;
        this.dimensions = dimensions;
    }

    public abstract double evaluate(double[] x);

    public String getName() {
        return name;
    }

    public int getNumberOfDimensions() {
        return dimensions;
    }
}
