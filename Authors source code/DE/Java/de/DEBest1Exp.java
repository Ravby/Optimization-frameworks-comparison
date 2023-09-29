package DeApp1.de;


public class DEBest1Exp extends DEStrategy
/***********************************************************
**                                                        **
** Ken's classic strategy. However, we have found several **
** optimization problems where misconvergence occurs.     **
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
	  x[i] = gen_best[i] + F * (g0[0][i] - g0[1][i]);
      i = ++i % dim;
    } while ((deRandom.nextDouble() < Cr) && (++counter < dim));
  }
}



