package DeApp1.screen;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
import DeApp1.panel.*;
import DeApp1.de.*;
import DeApp1.problem.*;



public class DEScreen extends Screen
/***********************************************************
** This is the mediator class for the entire application. **
** All major classes are known to DEScreen.               **
** DEScreen also provides for the main graphical user     **
** interface (GUI). Note that we have almost always       **
** employed the gridbag layout to allow resizing of the   **
** GUI.                                                   **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
***********************************************************/
{
  Dimension   minSize; //set the minimum size of the screen

  public static final java.awt.Color BACKGROUNDCOLOR = Color.lightGray;
  GridBagLayout	gridbag=new GridBagLayout(); //define the layout


  /*-----Define identifiers which are used to select classes-------*/
  public String problem_identifier[]   = 
  {
    "T4",
    "T8",
	"Lowpass1"
  };  // chooses the cost function

 /* public String strategy_identifier[] =
  { 
	"Best1Exp", "Rand1Exp", "RandToBest1Exp", "Best2Exp", "Rand2Exp",
    "Best1Bin", "Rand1Bin", "RandToBest1Bin", "Best2Bin", "Rand2Bin",
  }; */

  public String strategy_identifier[] =
  { 
    "Best2Bin", "Rand1Bin", "RandToBest1Bin", // only show the most promising
    "Best3Bin", "Best1Bin"
  };   						                  // strategies


  public T_DEOptimizer  t_DEOptimizer;	 // Optimization thread

  public ControlPanel controlPanel;      // Push controlPanel for start, pause etc.
  public MonitorPanel monitorPanel;      // monitorPanel generations, evaluations, minimum
  public InputPanel   inputPanel;        // inputPanel for NP, F and CR
  public StatusPanel  statusPanel;       // Shows the applet's current status
  public PlotChoicePanel plotChoicePanel;// Chooses various plots for monitoring
  public PrintOut     printOut;


  /*=====plotting stuff============================*/
  public PlotScreen   plotScreen0;       // a separate screen for plots
  public PlotScreen   plotScreen1;       // a separate screen for plots
  public PlotScreen   plotScreen2;       // a separate screen for plots
  public PlotScreen   plotScreen3;       // a separate screen for plots


  public boolean plot_screen0_exists = false;  // flag for existence of plot screen
  public boolean plot_screen1_exists = false;  // flag for existence of plot screen
  public boolean plot_screen2_exists = false;  // flag for existence of plot screen
  public boolean plot_screen3_exists = false;  // flag for existence of plot screen

  /*===== end plotting stuff=======================*/


  public DEScreen()
  /*************************************
  ** Constructor of the class.        **
  *************************************/
  {
    super("DeApp 1.0.3"); //print Title
    setBackground(Color.lightGray);

	// this really sets the initial size if you provide the 
	// methods preferredSize() and minimumSize()
    minSize = new Dimension(205,450);  //set minimum size


	Panel mainPanel = new Panel();               // For controlPanel, monitorPanel, inputPanel

	t_DEOptimizer   = new T_DEOptimizer (this);  // Create the optimizer object, must be
	                                             // after control and input because the optimizer
												 // needs data from these panels

	statusPanel     = new StatusPanel (this);    // Create the status panel
	controlPanel    = new ControlPanel (this);   // Create button controls panel
	monitorPanel    = new MonitorPanel (this);   // Create monitorPanel panel
    inputPanel      = new InputPanel (this);     // Create scrollbar panel
	plotChoicePanel = new PlotChoicePanel(this,t_DEOptimizer); // Create panel for choosing plots
	printOut        = new PrintOut(this);        // Object for file output

	System.out.println("Panels Created");		 // Debug message

	/*---Arrange the subpanels on the panel P----------------------*/
	/*---which contains four subpanels.----------------------------*/
	mainPanel.setLayout(gridbag);  // gridbag layout for the panel P         
	constrain(mainPanel,controlPanel,0,0,8,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,0,0,0,0);
	constrain(mainPanel,monitorPanel,0,1,8,10,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(mainPanel,inputPanel,0,12,5,2,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(mainPanel,plotChoicePanel,0,15,5,2,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);


	/*---Arrange the main panel and the status panel on the DE screen--------------*/
	this.setLayout(gridbag);  // apply gridbag layout to DEScreen
	constrain(this,mainPanel,0,0,5,10,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,10,10,10,10);
	constrain(this,statusPanel,0,15,5,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,10,10,10,10);

	t_DEOptimizer.start(); // start the optimization thread
  }

  public boolean handleEvent (Event e)
  /***********************************************************
  ** Handles events occuring from manipulating the screen   **
  ** (not the panels inside).                               **
  ***********************************************************/
  {	// The "x" field closes the application
	  if(e.id == Event.WINDOW_DESTROY)
	  {
		 System.exit(0);
		 return true;
	  }
	  else 
	  {	// if something else happens, use the parent's class event handler
		  return super.handleEvent(e);
	  } // we need this in order to keep writing access to the DEScreen
  }		// especially in the input panel

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


  public void idle ()
  /*****************************************
  ** Optimization is sleeping.            **
  *****************************************/
  {
	t_DEOptimizer.optIdle();
	monitorPanel.repaint();
	inputPanel.enable(); // allow for a change of control variables
	statusPanel.idle();   // show "Idle"
  }


  public void start ()
  /*****************************************
  ** Start the optimization.              **
  *****************************************/
  {
	t_DEOptimizer.optStart();
    inputPanel.disable(); // freeze control variables
    statusPanel.running(); // show "Running"
	t_DEOptimizer.optStart();
  }


  public void pause ()
  /*****************************************
  ** Pause the optimization.              **
  *****************************************/
  {
	t_DEOptimizer.makeNotReady();
	inputPanel.pause(); // allow to change some (not all) variables
	statusPanel.pause(); // show "Paused"
  }


  public void resume ()
  /*****************************************
  ** Resume the optimization activity.    **
  *****************************************/
  {
	inputPanel.resume(); // freeze variables
	statusPanel.resume(); // show "Running"
	t_DEOptimizer.optResume();
  }


  public void stop ()
  /*****************************************
  ** Stop the optimization.               **
  *****************************************/
  {
	t_DEOptimizer.makeNotReady();
	inputPanel.enable();
	statusPanel.stop(); // show nothing
	printOut.printResult(); // as the name says
  }


  public void done ()
  /*****************************************
  ** Is called when the optimization has  **
  ** come to an end.                      **
  *****************************************/
  {
    controlPanel.done();// reset control panel 
	inputPanel.done();	// Enable input panel
    statusPanel.done(); // show "Completed"
	printOut.printResult(); // as the name says
  }


  public String[] getProblemIdentifiers ()
  /*****************************************
  ** Access the problem identifiers.      **
  *****************************************/
  {
    return problem_identifier;
  }


  public String[] getStrategyIdentifiers ()
  /*****************************************
  ** Access the strategy identifiers.     **
  *****************************************/
  {
    return strategy_identifier;
  }


  public void getParameters ()
  /*****************************************
  ** Fetch DE's control variables from    **
  ** the various panels.                  **
  *****************************************/
  {
    inputPanel.getParameters (t_DEOptimizer);
    controlPanel.getParameters (t_DEOptimizer);
  }


  public void setProblem (int Index)
  /*****************************************
  ** Define the cost function for DE.     **
  *****************************************/
  {
	t_DEOptimizer.setProblem(Index);
    idle();
  }


  public void setStatus (String Text)
  /*****************************************
  ** Update the status panel.             **
  *****************************************/
  {
    if (statusPanel != null)
	{
      statusPanel.setText (Text);
	}
  }


  public void repaint ()
  /*****************************************
  ** Update the monitor panel and the     **
  ** plot screens (if existing).          **
  *****************************************/
  {
    monitorPanel.repaint();

	if (plot_screen0_exists)
	{
	  plotScreen0.refreshImage();
	}
	if (plot_screen1_exists)
	{
	  plotScreen1.refreshImage();
	}
	if (plot_screen2_exists)
	{
	  plotScreen2.refreshImage();
	}
	if (plot_screen3_exists)
	{
	  plotScreen3.refreshImage();
	}


  }


  public int getGeneration ()
  /*****************************************
  ** Get generation counter.              **
  *****************************************/
  {
	return t_DEOptimizer.getGeneration();
  }


  public int getEvaluation ()
  /*****************************************
  ** Get evaluation counter.              **
  *****************************************/
  {
	return t_DEOptimizer.getEvaluation();
  }

  public double[] getBest ()
  /**********************************
  ** Best vector.                  **
  **********************************/
  {
    return t_DEOptimizer.best;
  }

  public int getDimension ()
  /*****************************************
  ** Get number of parameters.            **
  *****************************************/
  {
	return t_DEOptimizer.dim;
  }


  public double getMinimum ()
  /*****************************************
  ** Get best so far cost.                **
  *****************************************/
  {
	return t_DEOptimizer.getMinimum();
  }

  public void consoleEnable()
  /*****************************************
  ** Enable console output trace of       **
  ** results.                             **
  *****************************************/
  {
	t_DEOptimizer.consoleEnable();
  }

  public void consoleDisable()
  /*****************************************
  ** Enable console output trace of       **
  ** results.                             **
  *****************************************/
  {
	t_DEOptimizer.consoleDisable();
  }

  public void newPlotScreen0()
  /*****************************************
  ** Instantiate plot screen.             **
  *****************************************/
  {
	plotScreen0     = new PlotScreen(this,0);
	plotScreen0.pack();  // arrange components
	plotScreen0.show();  // and show them
  }

  public void destroyPlotScreen0()
  /*****************************************
  ** Destroy plot screen.                 **
  *****************************************/
  {
	if ( plotScreen0 != null ) plotScreen0.dispose(); // BUG:: No Null Pointer check
  }

  public void newPlotScreen1()
  /*****************************************
  ** Instantiate plot screen.             **
  *****************************************/
  {
	plotScreen1     = new PlotScreen(this,1);
	plotScreen1.pack();  // arrange components
	plotScreen1.show();  // and show them
  }

  public void destroyPlotScreen1()
  /*****************************************
  ** Destroy plot screen.                 **
  *****************************************/
  {
	if ( plotScreen1 != null ) plotScreen1.dispose(); // BUG:: No Null Pointer check
  }

  public void newPlotScreen2()
  /*****************************************
  ** Instantiate plot screen.             **
  *****************************************/
  {
	plotScreen2     = new PlotScreen(this,2);
	plotScreen2.pack();  // arrange components
	plotScreen2.show();  // and show them
  }

  public void destroyPlotScreen2()
  /*****************************************
  ** Destroy plot screen.                 **
  *****************************************/
  {
	if ( plotScreen2 != null ) plotScreen2.dispose(); // BUG:: No Null Pointer check
  }

  public void newPlotScreen3()
  /*****************************************
  ** Instantiate plot screen.             **
  *****************************************/
  {
	plotScreen3     = new PlotScreen(this,3);
	plotScreen3.pack();  // arrange components
	plotScreen3.show();  // and show them
  }

  public void destroyPlotScreen3()
  /*****************************************
  ** Destroy plot screen.                 **
  *****************************************/
  {
	if ( plotScreen3 != null ) plotScreen3.dispose(); // BUG:: No Null Pointer check
  }





}

// Class DEScreen




