package DeApp1.de;


public class DERand2Bin extends DEStrategy
/***********************************************************
**                                                        **
** Strangely enough this strategy is often not very       **
** successful.                                            **
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
      x[i] = g0[0][i] + F * (g0[1][i]+g0[2][i]-g0[3][i]-g0[4][i]);
      i = ++i % dim;
    }
  }
}





