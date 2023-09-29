package DeApp1.de;



public class DECurrent2Rand extends DEStrategy
/***********************************************************
**                                                        **
** Ken's new current-to-rand strategy.                    **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
***********************************************************/
{
  public void apply (double F, double Cr, int dim, double[]x, double[]gen_best,
  double[][]g0)
  {
    for (i=0; i<dim; i++)
	{
	   x[i] = x[i] + Cr*(g0[0][i]-x[i]) + F*(g0[1][i] - g0[2][i]);
	}
  }
}




