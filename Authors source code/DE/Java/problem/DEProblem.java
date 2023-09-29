package DeApp1.problem;

import java.awt.*;         // Import all classes from the java.awt package
// AWT is the Abstract Window Toolkit. The AWT
import java.io.*;

import DeApp1.de.*;


public abstract class DEProblem
/***********************************************************
 **                                                        **
 ** Describes the problem to solve.                        **
 ** The abstract class declares the methods that all       **
 ** subtypes must have. Not all of them, however, are      **
 ** implemented in the abstract class.                     **
 **                                                        **
 ** Authors:            Mikal Keenan                       **
 **                     Rainer Storn                       **
 **                                                        **
 ***********************************************************/
{
    /*----Public variables---------------------*/
    public static final int NAPTIME = 10;
    public double mincost;

    /*----Protected variables------------------*/
    double best[];
    public int dim;
    public String name ="";

    public double[] upperLimit;
    public double[] lowerLimit;

    /*----Function stubs-----------------------*/
    public abstract boolean completed(); // TRUE if evaluation is completed

    public abstract double evaluate(T_DEOptimizer t_DEOptimizer, double[] X, int dim); //the actal cost function


    public final double[] getBest()
    /**********************************
     ** Best vector.                  **
     **********************************/
    {
        return best;
    }

    public final int getLength()
    /***********************************
     ** Dimensionality of the problem. **
     ***********************************/
    {
        return dim;
    }
}




