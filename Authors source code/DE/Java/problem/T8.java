package DeApp1.problem;
import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
import DeApp1.de.*;


public class T8 extends DEProblem
/***********************************************************
** Objective function which uses a tolerance scheme that  **
** can be fitted by a Chebychev polynomial T8.            **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
***********************************************************/
{
  int      evaluation_samples;
  double   lower_limit;

  public T8 ()
  /***********************************************************
  ** Constructor initializes some parameters.               **
  ***********************************************************/
  {
    best = new double [dim = 9];
    lower_limit = 72.661;
    evaluation_samples = 60;
  }


  public boolean completed ()
  /***********************************************************
  ** Is TRUE if the value-to-reach (VTR) has been reached   **
  ** or passed.                                             **
  ***********************************************************/
  {
    return mincost <= 1.0e-6; // TRUE if mincost is <= 1.e-6
  }

  public double evaluate (T_DEOptimizer t_DEOptimizer, double temp[], int dim)
  /*****************************************************************
  ** The actual objective function consists of the sum of squared **
  ** errors, where an error is the magnitude of deviation of the  **
  ** polynomial at a specific argument value.					  **
  *****************************************************************/
  {
    double y = 0.0;
    double x = -1.0;
    double z = 0.0, aux;

    double dx = 2 / ((double) evaluation_samples);
    for (int i = 0;  i <= evaluation_samples;  i++, x += dx)
    {
      if ((z = polynomial (temp, x, dim)) > 1.0)
      {
        aux = 1.0 - z;
        y += aux * aux;
      }
      else if (z < -1.0)
      {
        aux = z - 1.0;
        y += aux * aux;
      }
    }

    aux = lower_limit - z;
    aux *= aux;

    if (polynomial (temp, -1.2, dim) < lower_limit)
      y += aux;

    if (polynomial (temp, +1.2, dim) < lower_limit)
      y += aux;

    return y;
  }


  public double polynomial (double temp[], double x, int dim)
  /***********************************************************
  ** Evaluate the current polynomial.                       **
  ***********************************************************/
  {
    double y = temp[0];
    for (int j = 1;  j < dim;  j++)
	  y = x * y + temp[j];

    return y;
  }

}



