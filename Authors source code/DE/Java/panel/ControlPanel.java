package DeApp1.panel;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
                           
import DeApp1.screen.*;     // Import screens
import DeApp1.de.*;




public class ControlPanel extends MyPanel
/***********************************************************
**                                                        **
** Defines the user operated control panel.               **
**                                                        **
** Authors:            Rainer Storn                       **
**                                                        **
***********************************************************/
 
{
  GridBagLayout	gridbag=new GridBagLayout(); 

  public final static String startString  = "Start";
  public final static String stopString   = "Stop";
  public final static String pauseString  = "Pause";
  public final static String resumeString = "Resume";

  public final static Font buttonFont = new Font ("Dialog", Font.BOLD, 12);
  public final static Font choiceFont = new Font ("Dialog", Font.PLAIN, 12);
  public final static Font errorFont  = new Font ("TimesRoman", Font.BOLD, 20);

  public DEScreen      deScreen;

  Button   startButton;                                 // The start button
  Button   pauseButton;                                 // The pause button
  Button   exitButton;                                  // The exit button

  Choice   problemList;                                 // Problem control
  Choice   strategyList;                                // Strategy control

  // Labels to the choices
  Label     problemLab;                        
  Label     strategyLab;


  public int      current_problem  = 0;      // Initial problem number
  public int      current_strategy = 0;      // Initial strategy number

  int      i, n;  // some general variables


  public ControlPanel (DEScreen app)
  /********************************************
  ** Constructor.                            **
  ********************************************/
  {
	deScreen = app;
	//setLayout (new FlowLayout (FlowLayout.LEFT, 10, 10)); // "best effort" layout
    //setLayout(new GridLayout(2,3));
	this.setLayout(gridbag);                  // P layout manager, 3 rows

	startButton = new Button();                  // Create the start button
	startButton.setFont (buttonFont);            // Define its font
	startButton.setLabel (startString);          // and its text
	//add (startButton);                           // and make it visible

	pauseButton = new Button();                  // Create the pause button
	pauseButton.setFont (buttonFont);            // Define its font
	pauseButton.setLabel (pauseString);          // and its text
	//add (pauseButton);                           // and make it visible

	exitButton = new Button();                  // Create the exit button
	exitButton.setFont (buttonFont);            // Define its font
	exitButton.setLabel ("Exit");               // and its text
	//add (exitButton);                           // and make it visible

	problemList = new Choice();
	problemList.setFont (choiceFont);
	String[] identifier = deScreen.getProblemIdentifiers();
	n = identifier.length;	// how many different cost functions ?
	for (i = 0;  i < n;  i++)
	  problemList.addItem (identifier[i]);       // Put problems into list
	//add (problemList);                         // Make the list visible
	problemLab  = new Label("Problem:");


	strategyList = new Choice();
	strategyList.setFont (choiceFont);
	identifier = deScreen.getStrategyIdentifiers();
	n = identifier.length; // how many different strategies ?
	for (i = 0;  i < n;  i++)
	  strategyList.addItem (identifier[i]);     // Put strategies into list
	//add (strategyList);                         // Make the list visible
	strategyLab  = new Label("Strategy:");

	constrain(this,startButton,0,0,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,pauseButton,1,0,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,exitButton,2,0,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
    constrain(this,problemLab,0,1,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,problemList,1,1,2,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
    constrain(this,strategyLab,0,2,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,strategyList,1,2,2,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);

	reset();                                    // ?
	deScreen.statusPanel.idle();  // not nicely programmed
    //deScreen.setProblem (current_problem);	 // initialize the current problem
  }


  public boolean action (Event E, Object O)
  /********************************************
  ** Handles mouse events for the panel.     **
  ********************************************/
  {
    if (E.target instanceof Choice)
    {
      if (E.target.equals (problemList))              // Selected a problem
      {
        current_problem = ((Choice) (E.target)).getSelectedIndex();
        deScreen.setProblem (current_problem);
      }
      else if (E.target.equals (strategyList))       // Selected a strategy
      {
	    current_strategy = ((Choice) (E.target)).getSelectedIndex();
        userReset();
        deScreen.idle();
      }
    }
    else if (E.target instanceof Button)
    {
      if (startString.equals ((String) O))
      {
	    startButton.setLabel (stopString);   // Now animation can only stop
	    pauseButton.enable();                // or pause
	    problemList.disable();               // No choices during animation
	    strategyList.disable();              // No choices during animation
	    deScreen.start();
      }
      else if (pauseString.equals ((String) O))
      {
	    pauseButton.setLabel (resumeString);  // Show: animation can resume
        deScreen.pause();
      }
      else if (resumeString.equals ((String) O))
      {
	    pauseButton.setLabel (pauseString);   // Show: animation can pause
	    deScreen.resume();
	  }
      else if (stopString.equals ((String) O))
      {
	    reset();
        deScreen.stop();
	  }
	  else if(E.target==exitButton)
	  {
		 System.exit(0);
		 return true;
	  }
	  else return	super.handleEvent(E);
    }
	repaint();                                         // Redraw everything
	return true;
  }


  private void userReset ()
  /********************************************
  ** A special kind of reset.                **
  ********************************************/
  { 
	startButton.setLabel (startString);       // Start button shows "start"
	pauseButton.setLabel (pauseString);       // Pause button shows "pause"
	startButton.enable();
	pauseButton.disable();
  }


  public void getParameters (T_DEOptimizer opt)
  /********************************************
  ** Load the optimizer with DE's control    **
  ** variables.                              **
  ********************************************/
  {
    opt.current_problem  = current_problem;
    opt.current_strategy = current_strategy;
  }


  public void done ()
  /********************************************
  ** Do this when the optimization is        **
  ** finished.                               **
  ********************************************/
  {
    reset();
  }

  public void reset ()
  /********************************************
  ** Reset button labels.                    **
  ********************************************/
  {
	userReset();
	problemList.enable();                      // Enable problem selection
	strategyList.enable();                     // Enable strategy selection
	repaint();                                 // Redraw everything
  }

}// End class ControlPanel





