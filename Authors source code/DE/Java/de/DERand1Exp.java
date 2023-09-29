package DeApp1.de;


public class DERand1Exp extends DEStrategy
/***********************************************************
**                                                        **
** Perhaps the most universally applicaple strategy, but  **
** not always the fastest one. Still                      **
** this is one of my favourite strategies. It works espe- **
** cially well when the "Best"-schemes experience mis-    **
** convergence. Try e.g. F=0.7 and CR=0.5 as a first      **
** guess.                                                 **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
***********************************************************/
{
  public void apply (double F, double Cr, int dim, double[]x, double[]gen_best,
  double[][]g0)
  {
    prepare (dim);
    do
    { 
	  x[i] = g0[0][i] + F * (g0[1][i] - g0[2][i]);
      i = ++i % dim;
    } while ((deRandom.nextDouble() < Cr) && (++counter < dim));
  }
}




