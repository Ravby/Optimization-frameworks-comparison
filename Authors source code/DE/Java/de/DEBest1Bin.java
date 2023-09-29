package DeApp1.de;



public class DEBest1Bin extends DEStrategy
/***********************************************************
**                                                        **
** The classic strategy with binary crossover.            **
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
    while (counter++ < dim)
    { 
	  if ((deRandom.nextDouble() < Cr) || (counter == dim))
      x[i] = gen_best[i] + F * (g0[0][i] - g0[1][i]);
      i = ++i % dim;
    }
  }
}




