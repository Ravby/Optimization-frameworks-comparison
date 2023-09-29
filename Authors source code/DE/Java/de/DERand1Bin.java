package DeApp1.de;


public class DERand1Bin extends DEStrategy
/***********************************************************
 **                                                        **
 ** Perhaps the most universally applicaple strategy, but  **
 ** not always the fastest one.                            **
 **                                                        **
 ** Authors:            Mikal Keenan                       **
 **                     Rainer Storn                       **
 **                                                        **
 ***********************************************************/
{
    public void apply(double F, double Cr, int dim, double[] x, double[] gen_best,
                      double[][] g0) {
        prepare(dim);
        while (counter++ < dim) {
            if ((deRandom.nextDouble() < Cr) || (counter == dim))
                x[i] = g0[0][i] + F * (g0[1][i] - g0[2][i]);
            i = ++i % dim;
        }
    }
}




