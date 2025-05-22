package si.um.feri.lpm;

import org.um.feri.ears.util.random.RNG;

public class GenerateShiftData {

    public static void main(String[] args) {

        RNG.setSeed(0xDEADBEEF);

        //F1 (Sphere) shift [-80, 80]
        //F2 (Sum of Squares) shift [-80, 80]
        //F3 (Schwefel02) shift [-80, 80]
        //F4 (Rastrigin) shift [−4.096,4.096]
        //F5 (Ackley) shift [−25.6,25.6]
        //F6 (Griewank) [−480,480]

        int maxDim = 100;

        System.out.println("Shifted Optimum for sphere function in range [-80, 80] with 100 dimensions:");
        generatedAndPrintShiftData(maxDim, -80, 80);

        System.out.println("\nShifted Optimum for Sum of Squares function in range [-80, 80] with 100 dimensions:");
        generatedAndPrintShiftData(maxDim, -80, 80);

        System.out.println("\nShifted Optimum for Schwefel02 function in range [-80, 80] with 100 dimensions:");
        generatedAndPrintShiftData(maxDim, -80, 80);

        System.out.println("\nShifted Optimum for Rastrigin function in range [-4.096, 4.096] with 100 dimensions:");
        generatedAndPrintShiftData(maxDim, -4.096, 4.096);

        System.out.println("\nShifted Optimum for Ackley function in range [-25.6, 25.6] with 100 dimensions:");
        generatedAndPrintShiftData(maxDim, -25.6, 25.6);

        System.out.println("\nShifted Optimum for Griewank function in range [-480, 480] with 100 dimensions:");
        generatedAndPrintShiftData(maxDim, -480, 480);
        System.out.print("\n");
    }

    static void generatedAndPrintShiftData(int maxDim, double min, double max) {
        for (int i = 1; i <= maxDim; i++) {
            System.out.print(RNG.nextDouble(min, max));
            if(i < maxDim) {
                System.out.print(", ");
            }
            if(i % 5 == 0) {
                System.out.print("\n");
            }
        }
    }
}
