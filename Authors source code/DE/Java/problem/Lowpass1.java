package DeApp1.problem;
import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
import DeApp1.de.*;



public class Lowpass1 extends DEProblem
/***********************************************************
** Objective function which uses a tolerance scheme that  **
** can be fitted by a Chebychev polynomial T4.            **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
***********************************************************/
{
  double MINI;      // just a constant
  double pi, pi2;   // mathematical constants
  double c0, c1, c2, c3;  // Constants for filter
  double o0, o1, o2, o3;  // normalized frequencies 
  int    cpole, czero, D; // number of poles and zeroes

  public Lowpass1 ()
  /***********************************************************
  ** Constructor initializes some parameters.               **
  ***********************************************************/
  {
	cpole = 0;     
    czero = 8;
	D     = 2*(czero+cpole)+1; // number of parameters = dim

    best = new double [dim = D];
    c0 =  1.005;
    c1 =  0.995;
    c2 =  0.01;
    c3 =  0.0001;

    o0 = 0.0;
    o1 = 0.125;
    o2 = 0.25;
    o3 = 0.5;

	MINI = 1.0e-10;
	pi   =   3.14159265358979323846;
	pi2  =   6.28318530717958647692;
  }

  public boolean completed ()
  /***********************************************************
  ** Is TRUE if the value-to-reach (VTR) has been reached   **
  ** or passed.                                             **
  ***********************************************************/
  {
    return mincost <= 1.0e-6; // TRUE if mincost is <= 1.e-6
  }

  public double evaluate(T_DEOptimizer t_DEOptimizer, double trialVector[], int dim)
//  public double evaluate(T_DEOptimizer t_DEOptimizer, double temp[], int dim)

  /*****************************************************************
  ** The actual objective function consists of the sum of squared **
  ** errors, where an error is the magnitude of deviation of the  **
  ** polynomial at a specific argument value.					  **
  *****************************************************************/
{
   int   i;
   int  M5  = 5;
   int  M10 = 10;
   int  M20 = 20;
   int  M40 = 40;
   double res, x, y, err, sbub;
   double a0 = 0.005;  // this variable should be passed as a parameter

   int cz1, cz2, czp;
   double temp[] = new double [D];

/*-----Initialization--------------------------------------------------*/

   cz1  = czero+1;
   cz2  = 2*czero+1;
   czp  = 2*czero+cpole+1;

   for (i=0; i<D; i++)
   {
	   temp[i]=trialVector[i];
   }
   

//----Work on the penalties first by setting the parameters--------
//-----into the right range----------------------------------------

   for (i=0; i<D; i++) // all parameters must be > 0 
   {
      if (temp[i] < 0) temp[i]=-temp[i];
   } 

   for (i=1; i < cz1; i++) // zeroes have a maximum radius of 1(minimum phase) 
   {
      if (temp[i] > 1.0) //reflect point outside to the inside
	  {
		  //x = Math.floor(temp[i]);
		  //temp[i] = 1.0-x; //temp=bound-mod(temp,bound);
		  //temp[i]=t_DEOptimizer.deRandom.nextValue(1);
	  }
   }
   for (i=cz1; i < cz2; i++)// phase angle of zeroes < 0.5 
   {
      if (temp[i] > 0.5) 
	  {
		  //x = Math.floor(2*temp[i]);
		  //temp[i] = 0.5*(1.0-x); //temp=bound-mod(temp,bound);
		  temp[i]=0.5*t_DEOptimizer.deRandom.nextValue(1);
	  }
   }  

   t_DEOptimizer.updateTrialVector(temp, D);

//-----compute objective function--------------------------------------
//----This one uses the maximum error----------

   err = 0.;

   for (i=0; i<=M40; i++)   /* passband */
   {
      x    = o1*(double)i/(double)M40;
      y    = amag(temp,x,czero,cpole,a0);
      //----maximum deviation----------------
      //res  = y - c0;   //exceed upper boundary ?
      //if (res > err) err = res;
      //res  = c1 - y;   //exceed lower boundary ?
      //if (res > err) err = res;
      //----square error---------------------
	  res  = y - c0;   //exceed upper boundary ?
      if (res > 0) err += res*res;
      res  = c1 - y;   //exceed lower boundary ?
      if (res > 0) err += res*res;
   }

   for (i=0; i<=M10; i++)  /* transition band */
   {
      x    = o1 + (o2 - o1)*(double)i/(double)M10;
      y    = amag(temp,x,czero,cpole,a0);
      //----maximum deviation----------------
      //res  = y - c0; //exceed upper boundary ?
      //if (res > err) err = res;
      //----square error---------------------      
	  res  = y - c0; //exceed upper boundary ?
      if (res > 0) err += res*res;
   }

   for (i=0; i<=M20; i++)  /* stop band */
   {
      x    = o2 + (o3 - o2)*(double)i/(double)M20;
      y    = amag(temp,x,czero,cpole,a0);
      sbub = c2 + (c3 - c2)*(double)i/(double)M20; /* stop band upper bound */
      //----maximum deviation----------------
      //res  = y - sbub;
      //if (res > err) err = res;
      //----square error---------------------      
	  res  = y - sbub;
      if (res > 0) err += res*res;
   }


/*-------------Passband (group delay)-----------------------------------*/


/*-------------penalties--------------------------------*/
/*   res = 0.;
   for (i=0; i<D; i++) // all parameters must be > 0 
   {
      if (temp[i] < 0) res += 2.e4 - temp[i]*100.;
   }
   for (i=1; i < cz1; i++) // zeroes have a maximum radius of 1(minimum phase) 
   {
      if (temp[i] > 1.000000001) res += 100. + 100.*temp[i]; //20 + 10
   }
   for (i=cz1; i < cz2; i++)// phase angle of zeroes < 0.5 
   {
      if (temp[i] > 0.5) res += 20. + temp[i]*10.;	//20 + 10
   }

   if (cpole > 0)
   {
      for (i=cz2; i < czp; i++) // poles have a maximum radius of 1 
      {
	 if (temp[i] > 1.) res += 20. + temp[i]*100.;
      }
      for (i=czp; i<D; i++) // poles in stopband doesn't make sense 
      {
	 if ((temp[i] > 0.375) || (temp[i] < 0.25)) res += 20. + temp[i]*100.;
      }
   }  

   if (res > err) err = res; */

   return (double)err;
}


public double amag(double p[], double x, int czero, int cpole, double a0)
/*****************************************************************
**                                                              **
**   Computes magnitude over normalized frequency x.            **
**                                                              **
**   czero:    denotes the number of conjugate complex poles,   **
**             i.e. p[1]       ... p[czero] contains the radii  **
**                  p[czero+1] ... p[2*czero] the angles.       **
**                                                              **
**   cpole:    denotes the number of conjugate complex poles,   **
**             i.e. p[2*czero+1] ... p[2*czero+cpole] -> radii  **
**                  p[2*czero+cpole+1] ... p[2*(czero+cpole)]   **
**                  -> angles.                                  **
**   a0:       amplification factor. If <= 0 a0 = p[0].         **
**                                                              **
*****************************************************************/
{
   double sum, prod, r2;
   int k, k1, k2, k3, k4, cz1, cz2, czp;

/*--------Initialization----------------------------------*/

   cz1  = czero+1;
   cz2  = 2*czero+1;
   czp  = 2*czero+cpole+1;

/*--------Calculation of amag-----------------------------*/

   r2  = pi*x;

   //sum = -0.066542*Math.cos(r2*3.5)    /* contribution from FIR-filter */
  /*	 -0.039632*Math.cos(r2*2.5)
	 +0.33973*Math.cos(r2*1.5)
	 +0.830908*Math.cos(r2*0.5);     */

   //sum = sum*sum;
   sum = 1.;

   if (a0 > 0)  /* a0 in reasonable range ? */
   {
     p[0]= a0;
   }            /* else amplification is variable */

   prod = p[0]*p[0];/* amplification */
   for (k=0; k < czero; k++)
   {
      k1    = k+1;       /* counter for zero radii  */
      k2    = k+cz1; /* counter for zero angles */

      r2    = p[k1]*p[k1];
      prod *= (1. - 2.*p[k+1]*Math.cos(pi2*(x-p[k2])) + r2)*
	      (1. - 2.*p[k+1]*Math.cos(pi2*(x+p[k2])) + r2);
   }
   sum = sum*prod;

   prod = 1;

   if (cpole > 0)
   {
      for (k=0; k < cpole; k++)
      {
	 k3    = k+cz2; /* counter for pole radii */
	 k4    = k+czp;
	 r2    = p[k3]*p[k3];
	 prod *= (1. - 2.*p[k3]*Math.cos(pi2*(x-p[k4])) + r2)*
		 (1. - 2.*p[k3]*Math.cos(pi2*(x+p[k4])) + r2);
      }

      if (prod < MINI) prod = MINI;
   }
   sum = Math.sqrt(sum/prod);
   return(sum);
}

} //end of class


