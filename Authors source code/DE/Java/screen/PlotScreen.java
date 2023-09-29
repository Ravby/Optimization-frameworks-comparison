package DeApp1.screen;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
import DeApp1.panel.*;
import DeApp1.de.*;
import DeApp1.plot.*;



public class PlotScreen extends Screen
/***********************************************************
**                                                        **
** A new screen where the optional plotting takes place.  **
**                                                        **
** Authors:            Rainer Storn                       **
**                                                        **
** Date:               3/16/98                            **
**                                                        **
***********************************************************/

{
  Dimension   minSize; //set the minimum size of the screen

  public static final java.awt.Color BACKGROUNDCOLOR = Color.lightGray;
  GridBagLayout	gridbag=new GridBagLayout(); 

  //public PlotGraph0   plotGraph0;      // First graphics panel
  public DeLuxePlotGraph0   plotGraph0;      // First graphics panel
  //public PlotGraph1   plotGraph1;	   // Second graphics panel
  public DeLuxePlotGraph1   plotGraph1;      // Second graphics panel
  //public PlotGraph2   plotGraph2;	   // Third graphics panel
  public DeLuxePlotGraph2   plotGraph2;      // Third graphics panel
  public PlotGraph3   plotGraph3;	   // Fourth graphics panel

  public DEScreen     app;
  int                 w, h;			  // width and height
  int                 graph_type;	  // selects what to plot



  public PlotScreen(DEScreen deScreen, int i)
  /***********************************************************
  ** Define a screen which contains the graphics to be      **
  ** plotted. The selection process is done in a pretty     **
  ** simple way, no inheritance or strategy pattern or the  **
  ** like. This leads to a slight inefficiency in the       **
  ** refreshImage() method but it allows you to use         **
  ** totally different plotting routines for the various    **
  ** subplots. You needn't take the plotting functions      **
  ** provided in plot graph.                                **
  ***********************************************************/
  {
	super("Plot"); //print Title
    setBackground(Color.lightGray);
    minSize = new Dimension(300,300);  //set minimum size

	app        = deScreen;
	graph_type = i;

	if (graph_type == 0)
	{
      this.setTitle("Tolerance Scheme Plot"); //print Title
	}
	else if (graph_type == 1)
	{
      this.setTitle("Coefficient Plot"); //print Title
	}
	else if (graph_type == 2)
	{
	   this.setTitle("Magnitude Plot"); //print Title
	}
	else
	{
	   this.setTitle("Pole/Zero Plot"); //print Title
	}

    this.resize(400,400);

	w = size().width;
	h = size().height;


	//----Place the graph on the plot screen-----
	this.setLayout(gridbag);  // apply gridbag layout to the
                              // entire screen

	//----Create the graphics panel--------------
	if (graph_type == 0)
	{
	   //plotGraph0  = new PlotGraph0(app,w,h);
	   plotGraph0  = new DeLuxePlotGraph0(app,w,h);
       constrain(this,plotGraph0,0,0,1,1,GridBagConstraints.BOTH,
			     GridBagConstraints.CENTER,1.0,1.0,10,10,10,10);
	}
	else if (graph_type == 1)
	{
	   //plotGraph1  = new PlotGraph1(app,w,h);
	   plotGraph1  = new DeLuxePlotGraph1(app,w,h);	   
	   constrain(this,plotGraph1,0,0,1,1,GridBagConstraints.BOTH,
			     GridBagConstraints.CENTER,1.0,1.0,10,10,10,10);	   

	}
	else if (graph_type == 2)
	{
	   //plotGraph2  = new PlotGraph2(app,w,h);
	   plotGraph2  = new DeLuxePlotGraph2(app,w,h);	   	   
	   constrain(this,plotGraph2,0,0,1,1,GridBagConstraints.BOTH,
			     GridBagConstraints.CENTER,1.0,1.0,10,10,10,10);	   

	}
	else
	{
	   plotGraph3  = new PlotGraph3(app,w,h); 
	   constrain(this,plotGraph3,0,0,1,1,GridBagConstraints.BOTH,
			     GridBagConstraints.CENTER,1.0,1.0,10,10,10,10);	   

	}


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

  public void refreshImage()
  /***********************************************************
  ** Updates the actual graph that is plotted.              **
  ***********************************************************/
  {
	 if (graph_type == 0)
	 {
        plotGraph0.refreshImage();
	 }
	 else if (graph_type == 1)
	 {
        plotGraph1.refreshImage();
	 }
	 else if (graph_type == 2)
	 {
        plotGraph2.refreshImage();
	 } 
	 else
	 {
        plotGraph3.refreshImage();
	 } 
  }

}// Class PlotScreen




