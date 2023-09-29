package DeApp1.de;


public class DERandToBest1Bin extends DEStrategy
/***********************************************************
**                                                        **
** An interesting strategy that often works well.         **
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
	  x[i] += F * ((gen_best[i] - x[i]) + (g0[0][i] - g0[1][i]));
      i = ++i % dim;
    }
  }
}



