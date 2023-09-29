package DeApp1.plot;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
import DeApp1.screen.*;           // Import screens
import DeApp1.de.*;



public class PlotGraph2 extends Canvas
/***********************************************************
**                                                        **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
** Date:               3/16/98                            **
**                                                        **
** Use PlotGraph2 to adjust the graphics.                 **
***********************************************************/
{
// The axes, and tolerance scheme are computed once and plotted into a
// static image (staticI). The sample data is plotted into a background
// image and copied into the Animation's gc to avoid flicker.

	protected Image    staticImage;
	protected Graphics staticGraphics    = null;
	protected Image    offscreenImage ;
	protected Graphics offscreenGraphics = null;

	protected boolean ready = false;

	int choice = 1;

	protected DEScreen deScreen;

    double best[];
    int dim;

	int plotting_samples  = 60;  // (or 60) make this problem dependent!
	int tolerance_samples = 300;


	protected Dimension Area;
	
	protected int x;                                   
	protected int y;
	protected int w;
	protected int h;

	protected double min_x;        // Relative coordinates
	protected double max_x;
	protected double min_y;
	protected double max_y;

	protected int    abs_min_x;    // Absolute coordinates
	protected int    abs_max_x;
	protected int    abs_min_y;
	protected int    abs_max_y;

	protected int    x_tics  = 5;  // Number of tics in X-direction
	protected int    y_tics  = 6;  // Number of tics in Y-direction

	protected double c0,c1,c2,c3,o0,o1,o2,o3; // tolerance scheme

/*
  Description of the graphics screen

             abs_min_x                    abs_max_x
            ------------------------------------w
           |
abs_min_y  |     --------------------------
           |    |
           |    |
           |    |     ((abs_max_x-abs_min_x)/2, (abs_max_y-abs_min_y)/2)
           |    |      is center of drawing area
           |    |
abs_max_y  |    |
           |
           h
*/


    Dimension minSize;
    int margin = 40;



  public PlotGraph2(DEScreen father, int width, int height)
  /***********************************************************
  ** Set size of the plot and define the axes.              **
  ***********************************************************/
  {
	deScreen = father;
    minSize  = new Dimension(width-margin,height-margin);  //set minimum size
  }

  public Dimension preferredSize()
  /***********************************************************
  ** The layout manager needs this to determine the right   **
  ** size.                                                  **
  ***********************************************************/
  {
     return minimumSize();
  }

  public synchronized Dimension minimumSize()
  /***********************************************************
  ** The layout manager needs this to determine the right   **
  ** size.                                                  **
  ***********************************************************/
  {
     return minSize;
  }

  int absX (double x)
  /***********************************************************
  ** Transform relative X-values in absolute ones.          **
  ***********************************************************/
  {
	return abs_max_x + (int) (((double)(abs_min_x-abs_max_x)) * 
		   ((max_x-x)/(max_x-min_x)));
  }
	
  int absY (double y)
  /***********************************************************
  ** Transform relative Y-values in absolute ones.          **
  ***********************************************************/
  {
	return abs_min_y + (int) (((double)(abs_max_y-abs_min_y)) * 
		   ((max_y-y)/(max_y-min_y)));
  }

  void initParameters()
  /*******************************************************
  ** Set some parameters.                               **
  *******************************************************/
  {
		x            = 0;
		y            = 0;
		w            = size().width;
		h            = size().height;

		abs_min_x      = w / 8;           // Compute some variables
		abs_max_x      = w * 7 / 8;
		abs_min_y      = h / 8;
		abs_max_y      = h * 7 / 8;

		min_x         =  0.0;            // Mimimum abscissa value
		max_x         =  0.5;
		min_y         =  0.0;              // Minimum ordinate value
		max_y         =  +1.5;

		c0 =  1.005;
        c1 =  0.995;
        c2 =  0.01;
        c3 =  0.0001;

        o0 = 0.0;
        o1 = 0.125;
        o2 = 0.25;
        o3 = 0.5;

  }


  public synchronized void initGraphics()
  /***********************************************************
  ** Initializes background graphics, computes the          **
  ** tolerance scheme, etc.                                 **
  ***********************************************************/
  {	
	  /*---static part of the graphics-------------*/
		staticImage    = createImage (w,h);	 // create a static image
		staticGraphics = staticImage.getGraphics();	// graphics context for the 
		                                            // static image.
		staticGraphics.setColor (Color.white); // white background
		staticGraphics.fillRect (x, y, w, h);  // in rectangle area.
		preparePlot (staticGraphics); // plot axes and tolerance scheme

	  /*---dynamic part of the graphics------------*/
		offscreenImage    = createImage (w, h);
		offscreenGraphics = offscreenImage.getGraphics();

	  /*---draw the static image on the graphics context---*/
	  /*---offscreenGraphics (location x=0, y=0, no--------*/
	  /*---ImageObserver).---------------------------------*/
		offscreenGraphics.drawImage (staticImage, 0, 0, null);
   }

  void init()
  /*******************************************************
  ** As the name says: Initialization.                  **
  *******************************************************/
  {
	ready   = false;
	initParameters();
	initGraphics();
	ready   = true;
  }

  void preparePlot (Graphics staticGraphics)
  /*******************************************************
  ** Draws the static part of the plot.                 **
  *******************************************************/
  {
	plotAxes (staticGraphics);
    plotTolerance (staticGraphics);
  }
	
  public void plotAxes (Graphics g)
  /*******************************************************
  ** Plot coordinate system in which polynomial will be **
  ** plotted.                                           **
  *******************************************************/
  {
	int tick_height = 3;
	int i; // counter variable
    //System.out.println("Plot Axes");

	g.setColor (Color.black); 

	/*---Draw x-axis----------------------------*/
    int static0 = absY (0.0);
	g.drawLine (absX (min_x), static0, absX (max_x), static0);      // X axis

	/*---Draw y-axis----------------------------*/
 	static0 = absX (0.0);
 	g.drawLine (static0, absY (min_y), static0, absY (max_y));      // Y axis

	/*---Prepare x-axis ticks-------------------*/
    static0 = absY (0.0);
    int base_pos = absX (min_x);
    int static1  = static0;
    int static2  = static0 - tick_height;

	double increment = ((double) (abs_max_x - abs_min_x) / 
		                (double) x_tics) + 0.25;	   
	/*---Draw x-axis ticks----------------------*/
	for (i = 0;  i <= x_tics;  i++)        
	{ 
	  int x = base_pos + (int) (i * increment);
	  g.drawLine (x, static1, x, static2);
    }	

	/*---Prepare y-axis ticks-------------------*/
    base_pos = absY (min_y);
    static0  = absX (0.0);
    static1  = static0 - 2;
    static2  = static0 + 2;

	increment = ((double) (abs_max_y - abs_min_y) / 
		         (double) y_tics); 

	/*---Draw y-axis ticks----------------------*/
	for (i = 0;  i <= y_tics;  i++)                       
	{ 
	  int y = base_pos - (int) (i * increment);
	  g.drawLine (static1, y, static2, y);
    } 

	/*---Prepare x-tick labeling----------------*/
	g.setFont (new Font ("Helvetica", Font.PLAIN, 10));      

	static0 = absY (0) - 10;
	double x = min_x;

    increment = (double) (max_x - min_x) / (double) x_tics;

	/*---Draw x-tick labeling-------------------*/
	for (i = 0;  i <= x_tics;  i++, x += increment)
    {
      x = Math.rint(x*100.0)*0.01; //accuracy to 2 decimal places
	  Double DblObj = new Double (x);
	  g.drawString (DblObj.toString(), absX (x) - 10, static0);
	}

	Double DblObj = new Double (max_y);
	g.drawString (DblObj.toString(), absX (0), absY (max_y) - 8);
  }

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
  protected double upperTolerance (double x)
  /*******************************************************
  ** Computes the upper part of the tolerance scheme.   **
  *******************************************************/
  {
    if ((x >= o0) && (x < o2))
      return c0;

    if ((x >=o2) && (x <= o3))
      return ((c2-c3)*(x-o3)/(o2-o3)+c3);

    return 0;	 // default	 
  }


  protected double lowerTolerance (double x)
  /*******************************************************
  ** Computes the lower part of the tolerance scheme.   **
  ** Note that this scheme only holds for the T4        **
  ** problem.                                           **
  *******************************************************/
  {
    if ((x >= o0) && (x < o1))
      return c1;

	if (x >= o1) return 0;

    return 0;	 // default	 
  }


  public void plotTolerance (Graphics g)
  /*******************************************************
  ** Plot the tolerance scheme.                         **
  *******************************************************/
  {
	g.setColor (Color.red);          // Plot upper part of tolerance scheme
	int i;
    double coefficient = (max_x - min_x) / ((double) tolerance_samples);
    double d1, d2;

	d1 = min_x;
    for (i = 1;  i <= tolerance_samples;  i++)
    {
      d2 = min_x + ((double) i) * coefficient;
	  g.drawLine (absX (d1), absY (upperTolerance (d1)),
	               absX (d2), absY (upperTolerance (d2)));
	  d1 = d2;
    }

    d1 = min_x;                       // Plot lower part of tolerance scheme
    for (i = 1;  i <= tolerance_samples;  i++)
    {
      d2 = min_x + ((double) i) * coefficient;
	  g.drawLine (absX (d1), absY (lowerTolerance (d1)),
	               absX (d2), absY (lowerTolerance (d2)));
	  d1 = d2;
    } 
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



  public void plot (Graphics g)
  /*******************************************************
  ** Plots the current polynomial.                      **
  *******************************************************/
  {
	g.setColor (Color.blue);
	best = deScreen.getBest();
	dim  = deScreen.getDimension();
    double coefficient = (max_x - min_x) / ((double) plotting_samples);
    double x1 = min_x;
	double x2;
	int    i;
	int    czero = 8;
	int    cpole = 0;
	double a0    = 0.005;

    for (i = 1;  i <= plotting_samples;  i++)
    {
      x2 = min_x + ((double)i) * coefficient;
	  g.drawLine (absX (x1), absY(amag(best,x1,czero,cpole,a0)),
	               absX (x2), absY(amag(best,x2,czero,cpole,a0)));
	  x1 = x2;
    } 
  }


  public void paint (Graphics g)
  /*******************************************************
  ** Actually draws on the canvas.                      **
  *******************************************************/
  {
	init();	// initializing with every paint() call 
	        // allows for resizing of the plot screen
	g.drawImage (offscreenImage, 0, 0, null);
  }

  public void refreshImage()
  /***********************************************************
  ** Update function which recomputes the variable screen   **
  ** image.                                                 **
  ***********************************************************/
  {
	if (offscreenGraphics == null)
	{
		init();
	}

    offscreenGraphics.drawImage (staticImage, 0, 0, null); 
	plot (offscreenGraphics);
	repaint();	  
  }

  public void update(Graphics g)
  /*******************************************************
  ** Overriding update() reduces flicker. The normal    **
  ** update() method clears the screen before it        **
  ** repaints and hence causes flicker. We dont like	**
  ** this and leave out the screen clearing.			**
  *******************************************************/
  {
	g.drawImage (offscreenImage, 0, 0, null);
  }


}




