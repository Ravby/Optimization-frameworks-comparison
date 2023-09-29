package DeApp1.de;
import java.io.*;

public abstract class DEStrategy
/***********************************************************
**                                                        **
** Parent class for all the different DE-strategies you   **
** can choose.                                            **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
***********************************************************/
{
   protected DERandom deRandom;

   protected int i, counter;

   
   abstract public void apply (double F, double Cr, int dim, 
	                          double[]x, double[]gen_best,
                              double[][]g0);
   /***********************************************************
   ** Contains the actual strategy which alters your vectors.**
   ***********************************************************/

   public void init (DERandom deRnd)
   /***********************************************************
   ** Link to the random number generator.                   **
   ***********************************************************/
   { 
	   deRandom = deRnd;
   }

   protected final void prepare (int dim)
   /***********************************************************
   ** Fetch a random number ex [0,dim].                      **
   ***********************************************************/
   {
     i = deRandom.nextValue (dim);
     counter = 0;
   }
}




