package DeApp1.plot;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
import DeApp1.screen.*;    // Import screens
import DeApp1.de.*;
import DeApp1.ptplot.*;	   // import plotting routines from PtPlot1.3



public class DeLuxePlotGraph2 extends Plot
/***********************************************************
**                                                        **
**                                                        **
** Authors:            Rainer Storn                       **
**                                                        **
** Date:               2/5/99                             **
**                                                        **
***********************************************************/
{

	protected DEScreen deScreen;

    double best[];	// best parameter vector so far
	int    dim;     // dimension of the problem
	protected int initFlag;	// 1: indicates that initialization must be done
	int    no_of_persistent_points;


	int plotting_samples;   // number of samples used for graph plotting

	protected double min_x; // Relative coordinates
	protected double max_x;
	protected double min_y;
	protected double max_y;

	protected double c0,c1,c2,c3,o0,o1,o2,o3; // tolerance scheme



  public DeLuxePlotGraph2(DEScreen father, int width, int height)
  /***********************************************************
  ** Constructor.                                           **
  ***********************************************************/
  {
	deScreen = father;

	initFlag = 1;		    // indicates that init is required
	plotting_samples = 100;	// number of samples for the plot
	no_of_persistent_points = plotting_samples; //no. of points which remain
								                //visible at a time.


	// Constants for the plot
		min_x         =  0.0;            // Mimimum abscissa value
		max_x         =  0.5;
		min_y         =  0.0;            // Minimum ordinate value
		max_y         =  +1.5;

		c0 =  1.005;
        c1 =  0.995;
        c2 =  0.01;
        c3 =  0.0001;

        o0 = 0.0;
        o1 = 0.125;
        o2 = 0.25;
        o3 = 0.5;

	this.setXRange(min_x,max_x);				 // x-range
	this.setYRange(min_y,max_y);				 // y-range
	//this.setXLabel("x-axis");
	//this.setYLabel("y-axis");
	this.setGrid(true);							 // plot grid
	this.setNumSets(15);					     // fifteen graphs my be written into one picture
	this.setPointsPersistence(no_of_persistent_points); // lifetime of points. This holds for every
	                                             // graph number. When the number of plotting samples
	                                             // is less than those in the argument none of them
	                                             // will disappear (we uses that for the tolerance scheme).
	this.show();
  }

  public void plotTolerance ()
  /**********************************************************
  ** Plot the tolerance scheme.                            **
  ** We have much less points than no_of_persistent_points **
  ** so the tolerance scheme remains visible all the time. **
  **********************************************************/
  {
 /*
    c0-----------------
					  |
    c1----------	  |
			   |	  |
    c2		   |	  |
			   |	   ---
    c3		   |		  ---
			   |
	----------------------------		   
	o0         o1     o2     o3
  */

	  //---upper part of tolerance scheme--------
	  addPoint(1,o0,c0,!true);
      addPoint(1,o2,c0,!false);
	  addPoint(1,o2,c2,!false);
	  addPoint(1,o3,c3,!false);

	  //---lower part of tolerance scheme--------
      addPoint(1,o0,c1,!true);
      addPoint(1,o1,c1,!false);
      addPoint(1,o1,0.,!false);
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
   double MINI = 1.0e-10;
   double pi   =   3.14159265358979323846;
   double pi2  =   6.28318530717958647692;

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

  public void refreshImage()
  /***********************************************************
  ** Update function which recomputes the variable screen   **
  ** image.                                                 **
  ***********************************************************/
  {
	dim  = deScreen.getDimension();
	best = deScreen.getBest();
	boolean first = true; //first point is not connected to a predecessor
    double coefficient;
	double x2;
	int    i;
	int    czero = 8;
	int    cpole = 0;
	double a0    = 0.005;

	//----Take care of proper initialization------------
    if (initFlag == 1)
	{
	  init();
	  plotTolerance();
      drawPlot(_graphics, true); // make graphics immediately visible
	  initFlag = 0;	 // prevent that graphics is constantly initialized
	}

	//***********************************************************
	//****Here is the actual plotting routine********************
	//***********************************************************

    //----Colors (which are tightly linked to graph numbers)-----
	    //0: white
        //1: red	  // graph number 1 is plotted in red
        //2: blue
        //3: green-ish
        //4: black
        //5: orange
        //6: cadetblue4
        //7: coral
        //8: dark green-ish
        //9: sienna-ish
        //10: grey-ish
        //11: cyan-ish

    
	coefficient = (max_x - min_x) / ((double) plotting_samples);

	//-----now plot new graph-----------------------------

    for (i = 0;  i <= plotting_samples;  i++)
    {
      x2 = min_x + ((double)i) * coefficient;
	  this.addPoint(2,x2,amag(best,x2,czero,cpole,a0),!first);
	  first = false; // from now on points will be connected with lines
    }	
	paint(_graphics);

  }

  public void paint (Graphics g)
  /*******************************************************
  ** Whenever the component is exposed anew, this method *
  ** is called.                                         **
  *******************************************************/
  {
	//----Take care of proper initialization------------
	if (initFlag == 1)
	{
	  init();
	  plotTolerance();
      drawPlot(_graphics, true); // make graphics immediately visible
	  initFlag = 0;	 // prevent that graphics is constantly initialized
	}

	//----Take care of screen resize--------------------
	if (_reshapeFlag == 1)
	{
      drawPlot(_graphics, true); // make graphics immediately visible
	  _reshapeFlag = 0;	 
	}
  }

}




