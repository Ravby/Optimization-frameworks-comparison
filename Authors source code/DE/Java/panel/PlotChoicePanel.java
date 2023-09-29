package DeApp1.panel;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
                           
import DeApp1.screen.*;     // Import screens
import DeApp1.de.*;




public class PlotChoicePanel extends MyPanel
/***********************************************************
**                                                        **
** Allows to choose various plots.                        **
**                                                        **
** Authors:            Rainer Storn                       **
**                                                        **
***********************************************************/
 
{
  GridBagLayout	gridbag=new GridBagLayout(); 



  public DEScreen deScreen;		//caller class
  public T_DEOptimizer t_DEOptimizer;

  Checkbox		plotCheckBox[]; //array of check boxes


  public PlotChoicePanel (DEScreen app, T_DEOptimizer opt)
  /***************************************
  ** Constructor.                       **
  ***************************************/
  {
	deScreen      = app;
	t_DEOptimizer = opt;
	this.setLayout(gridbag);                  // P layout manager, 3 rows

	plotCheckBox	 = new Checkbox [3]; // three check boxes
	plotCheckBox[0]  = new Checkbox("Tolerance Scheme Plot");
	plotCheckBox[0].setState(false); // check box not checked

	plotCheckBox[1]  = new Checkbox("Coefficient Plot");
	plotCheckBox[1].setState(false); // check box not checked

	plotCheckBox[2]  = new Checkbox("Console Output");
	plotCheckBox[2].setState(false); // check box not checked
	deScreen.consoleDisable();       // and hence suppress console trace


	constrain(this,plotCheckBox[0],0,0,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,plotCheckBox[1],0,1,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,plotCheckBox[2],0,2,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
  }


  public boolean handleEvent (Event e)
  /***************************************
  ** Handles mouse events for the panel.**
  ***************************************/
  {
	if(e.target == plotCheckBox[1] && e.id == Event.ACTION_EVENT )	// tolerance scheme plot
	{																// BUG:: No check for event type
		if (t_DEOptimizer.current_problem != 2)
		{
		   if (plotCheckBox[1].getState() == true  && !deScreen.plot_screen1_exists )	 //new plot screen
		   {																			 // BUG:: No check if the plot screen screen already exists
			  deScreen.newPlotScreen1();
			  deScreen.plot_screen1_exists = true;
		   }
		   else // disable console output
		   {
			  deScreen.destroyPlotScreen1();
			  deScreen.plot_screen1_exists = false;
		   }
		}
		else
		{
		   if (plotCheckBox[1].getState() == true  && !deScreen.plot_screen3_exists )	 //new plot screen
		   {																			 // BUG:: No check if the plot screen screen already exists
			  deScreen.newPlotScreen3();
			  deScreen.plot_screen3_exists = true;
		   }
		   else // disable console output
		   {
			  deScreen.destroyPlotScreen3();
			  deScreen.plot_screen3_exists = false;
		   }
		}	
	}

	if(e.target == plotCheckBox[0] && e.id == Event.ACTION_EVENT) // parameter plot
	{																   // BUG:: No check for event type	  
		if (t_DEOptimizer.current_problem != 2)
		{
		   if (plotCheckBox[0].getState() == true  && !deScreen.plot_screen0_exists )	 //new plot screen
		   {																			 // BUG:: No check if the plot screen screen already exists
			  deScreen.newPlotScreen0();
			  deScreen.plot_screen0_exists = true;
		   }
		   else // disable console output
		   {
			  deScreen.destroyPlotScreen0();
			  deScreen.plot_screen0_exists = false;
		   }
		}
		else
		{
		   if (plotCheckBox[0].getState() == true  && !deScreen.plot_screen2_exists )	 //new plot screen
		   {																			 // BUG:: No check if the plot screen screen already exists
			  deScreen.newPlotScreen2();
			  deScreen.plot_screen2_exists = true;
		   }
		   else // disable console output
		   {
			  deScreen.destroyPlotScreen2();
			  deScreen.plot_screen2_exists = false;
		   }
		}
	}	
	else if(e.target == plotCheckBox[2] && e.id == Event.ACTION_EVENT) 
	{												// BUG:: No check for event type
		if (plotCheckBox[2].getState() == true)	 //enable console output
		{
			deScreen.consoleEnable();
		}
		else // disable console output
		{
			deScreen.consoleDisable();
		}
	}

	return true;
  }

}// End class ControlPanel





