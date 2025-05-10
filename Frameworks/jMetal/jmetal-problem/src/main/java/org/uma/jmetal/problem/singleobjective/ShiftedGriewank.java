package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class ShiftedGriewank extends AbstractDoubleProblem {

    final double[] shift = {104.94691081342978, -135.6761348470227, 92.81100891955543, -10.87108518188711, 407.63013054172563,
            -223.31315231746964, -224.56627659185563, -222.7632371161696, -338.7309010063917, -236.65660889064418,
            -102.44810336184622, 126.42998956660733, 145.51932139597716, 362.4195007946686, -136.07822974158148,
            385.3787391034516, 224.54916653990915, 43.478124239142176, 174.10299590196337, -214.8310656032395,
            -398.01422639093084, -309.2614310193993, -178.06570153291875, -162.6651823263993, 450.850657541738,
            -454.92932203096916, -419.61768171271166, -297.80826342133184, 66.9431726795051, 81.54612611060406,
            33.75350391540678, 452.7503721088576, -394.37679317004665, 211.96497077719948, 209.2762825707889,
            -456.2796841185877, 43.786866583660526, 39.94139518856173, -392.0757065399322, 368.3834199650955,
            -230.1695108177757, 260.9386598025741, 424.9982235544909, 219.5539088594145, -56.74195565523951,
            353.31823225966764, -314.409074063865, -344.22129416136335, 350.389300480917, 210.6305580593255,
            -351.3688705488921, -419.56080494622336, -352.4149162964608, -306.0148711883166, 234.1092941008617,
            179.7062426106279, -180.45299833941465, -353.7895714765393, 304.63535113945875, 11.040575864524044,
            409.40525097198395, -417.0405885951272, 460.81384815623824, 476.25681692986484, 248.03325986310347,
            126.13612920930825, 366.9901574947629, 40.28445536257732, 196.48680138749523, -89.56987522823874,
            -31.05376733595574, -254.8501756921979, 341.9328159488531, -366.2937316074139, -98.73389669853748,
            -318.98905799691175, -152.38232167124994, 367.26470889921757, -72.94267032929105, 372.16601829879653,
            96.98492379728134, -385.9422293412532, -94.28273010579477, 346.5708159613322, -139.0701306975041,
            -62.71021784084701, -477.67009305148014, 267.4589914695547, 135.44409046482406, -107.786912911326,
            12.944227804931472, 452.8144059395564, -2.2036047536961973, -458.0828323445677, -305.00083158848463,
            352.90943721979033, -173.23435776482415, -294.46059074937193, 329.82221519045083, 295.09636446279023};

    public ShiftedGriewank() {
        this(10);
    }

    /**
     * Constructor
     * Creates a default instance of the Griewank problem
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public ShiftedGriewank(Integer numberOfVariables) {
        numberOfObjectives(1);
        numberOfConstraints(0);
        name("ShiftedGriewank");

        List<Double> lowerLimit = new ArrayList<>(numberOfVariables);
        List<Double> upperLimit = new ArrayList<>(numberOfVariables);

        for (int i = 0; i < numberOfVariables; i++) {
            lowerLimit.add(-600.0);
            upperLimit.add(600.0);
        }

        variableBounds(lowerLimit, upperLimit);
    }

    /**
     * Evaluate() method
     */
    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        int numberOfVariables = numberOfVariables();

        double[] x = new double[numberOfVariables];

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.variables().get(i);
        }

        double[] shiftedX = new double[numberOfVariables];
        for (int i = 0; i < numberOfVariables; i++) {
            shiftedX[i] = x[i] - shift[i];
        }

        double sum = 0.0;
        double mult = 1.0;
        double a = 4000.0;
        for (int var = 0; var < numberOfVariables; var++) {
            sum += shiftedX[var] * shiftedX[var];
            mult *= Math.cos(shiftedX[var] / Math.sqrt(var + 1));
        }

        solution.objectives()[0] = 1.0 / a * sum - mult + 1.0;

        return solution;
    }
}