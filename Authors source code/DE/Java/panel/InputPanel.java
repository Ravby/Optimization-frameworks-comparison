package DeApp1.panel;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;
import DeApp1.screen.*;     // Import screens
import DeApp1.de.*;



public class InputPanel extends MyPanel
/***********************************************************
**                                                        **
** Defines the user operated input panel using scroll     **
** bars or text input.                                    **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
***********************************************************/
{
  public final static Font ScrollFont = new Font ("Dialog", Font.PLAIN, 12);

  public DEScreen  deScreen;


  // Text values for the scrollbars
  TextField NPText;                       
  TextField FText;
  TextField CrText;
  TextField RangeText;
  TextField RefreshText;

  // Labels to the text fields
  Label     NPLab;                        
  Label     FLab;
  Label     CrLab;
  Label     RangeLab;
  Label     RefreshLab;


  // The actual control variables
  int NP;                                 
  double F;
  double Cr;
  double Range;
  int Refresh;

  // data type wrappers for the control variables
  Integer NPObj;           
  Double  FObj;
  Double  CrObj;
  Double  RangeObj;
  Integer RefreshObj;
  
  // maximum values for the control variables
  final static int    NPMAX  = 500; 
  final static double FMAX   = 1.0;
  final static double CRMAX  = 1.0;
  final static double RMAX   = 500.0;
  final static int    RFMAX  = 200; 


  public InputPanel (DEScreen app)
/***********************************************************
**                                                        **
** Defines the input panel where DE's control variables   **
** can be set. This can be done either by using a scroll- **
** bar or by entering the data into the text fields.      **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
** Date:               3/16/98                            **
**                                                        **
***********************************************************/
  {
	deScreen = app;	  // know your container class

	/*----Initialization values for the DE control variables.-----*/
	NP      = 30;
    F       = 0.5;
    Cr      = 1.0;
    Range   = 100.0;
	Refresh = 1;
	// ToDo: Change this to GetParameters(deScreen.);
    this.setLayout (new GridBagLayout ());      // Slider below the text

    //------Parameter NP-----------------------------------
	NPText        = new TextField (10);        // Create the NP text field
    NPText.setEditable(true);
    NPLab         = new Label("NP:");

    NPText.setText (String.valueOf (NP)); // Show initial value
    this.setLayout (new GridBagLayout ());      // Slider below the text
	constrain(this,NPLab,0,0,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,NPText,1,0,2,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);

    //------Parameter F-----------------------------------
    FText        = new TextField (10);
    FText.setEditable(true);
    FLab         = new Label("F:");

    FText.setText (FObj.toString (F));
	constrain(this,FLab,0,2,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,FText,1,2,2,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);

	//----Parameter CR---------------------------------------
    CrText        = new TextField (10);
    CrText.setEditable(true);
    CrLab         = new Label("CR:");

    CrText.setText (CrObj.toString (Cr));
	constrain(this,CrLab,0,4,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,CrText,1,4,2,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);

	//---Init section-----------------------------------------
    RangeText        = new TextField (10);
    RangeText.setEditable(true);
    RangeLab         = new Label("Range:  ");

    RangeText.setText (RangeObj.toString (Range));
	constrain(this,RangeLab,0,6,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,RangeText,1,6,2,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);

    //------Range-----------------------------------
	RefreshText        = new TextField (10); // Create the Refresh text field
    RefreshText.setEditable(true);
    RefreshLab         = new Label("Refresh:");

    RefreshText.setText (String.valueOf (Refresh)); // Show initial value
	constrain(this,RefreshLab,0,8,1,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	constrain(this,RefreshText,1,8,2,1,GridBagConstraints.BOTH,
			  GridBagConstraints.CENTER,1.0,1.0,5,5,0,0);
	  

  }


  public boolean handleEvent (Event E)
  // Handles the scrollbar actions. Note: overriding the Scrollbar
  // class's own event handler.
  {

	repaint();                       // Redraw everything

	return super.handleEvent (E);    // Propagate message to the superclass
  }


  public void enable ()
  /***************************************
  ** Enable the input.                  **
  ***************************************/
  {
	/* If you enable the sliders, put them into the position which
	   corresponds to the text field
	*/
	NPText.enable();
    NPText.setText (String.valueOf (NP)); // set the text according to the actual value

	FText.enable();
    FText.setText (FObj.toString (F));

	CrText.enable();
    CrText.setText (CrObj.toString (Cr));

	RangeText.enable();
    RangeText.setText (CrObj.toString (Range));

	RefreshText.enable();
    RefreshText.setText (String.valueOf (Refresh)); // set the text according to the actual value

  }


  public void disable ()
  /********************************************
  ** Deactivate text fields and load the     **
  ** control variables with their new values.**
  ********************************************/
  {
    // Convert from text representation to numbers
	NP = (NPObj.valueOf(NPText.getText())).intValue();
	F  = (FObj.valueOf(FText.getText())).doubleValue();
	Cr = (CrObj.valueOf(CrText.getText())).doubleValue();
	Range = (RangeObj.valueOf(RangeText.getText())).doubleValue();
	Refresh = (RefreshObj.valueOf(RefreshText.getText())).intValue();

	// check for violation of ranges and then set the variables
	if ((NP > NPMAX) || (NP < 0))      NP = NPMAX;
    NPText.setText (String.valueOf (NP)); // reflect a change of variable in the text

	if ((F > FMAX) || (F < 0))         F = FMAX;
    FText.setText (FObj.toString (F));

	if ((Cr > CRMAX) || (Cr < 0))      Cr = CRMAX;
    CrText.setText (CrObj.toString (Cr));

	if ((Range > RMAX) || (Range < 0)) Range = RMAX;
    RangeText.setText (CrObj.toString (Range));

	if ((Refresh > RFMAX) || (Refresh < 0)) Refresh = RFMAX;
    RefreshText.setText (String.valueOf (Refresh)); // reflect a change of variable in the text

    // disable the textfields, so that
	//nobody can tamper with them during optimization
	NPText.disable();

	CrText.disable();

	FText.disable();

	RangeText.disable();

	RefreshText.disable();
  }


  public void pause ()
  /********************************************
  ** Activate Cr, F, and refresh rate        **
  ** manipulation during pause.              **
  ********************************************/
  {
	FText.enable();
    FText.setText (FObj.toString (F));

	CrText.enable();
    CrText.setText (CrObj.toString (Cr));

	RefreshText.enable();
    RefreshText.setText (RefreshObj.toString (Refresh));

  }


  public void resume ()
  /********************************************
  ** Deactivate Cr and F manipulation on     **
  ** resume.                                 **
  ********************************************/
  {

    // Convert from text representation to numbers
	F  = (FObj.valueOf(FText.getText())).doubleValue();
	Cr = (CrObj.valueOf(CrText.getText())).doubleValue();
	Refresh = (RefreshObj.valueOf(RefreshText.getText())).intValue();
	//System.out.println(Refresh);

	// check for violation of ranges and then set the variables
	if ((F > FMAX) || (F < 0))         F = FMAX;
    FText.setText (FObj.toString (F));// reflect a change of variable in the text

	if ((Cr > CRMAX) || (Cr < 0))      Cr = CRMAX;
    CrText.setText (CrObj.toString (Cr));

	if ((Refresh > RFMAX) || (Refresh < 0)) Refresh = RFMAX;
    RefreshText.setText (String.valueOf (Refresh)); // reflect a change of variable in the text


    // disable the textfields, so that
	//nobody can tamper with them during optimization
	CrText.disable();

	FText.disable();

	RefreshText.disable();

  }

  public void done ()
  /********************************************
  ** Optimization finished.                  **
  ********************************************/
  {
    enable();
  }

  public void getParameters (T_DEOptimizer opt)
  /********************************************
  ** DE's control variables.                 **
  ********************************************/
  {
	opt.NP      = NP;
	opt.F       = F;
	opt.Cr      = Cr;
	opt.Range   = Range;
    opt.Refresh = Refresh; // How many generations until next plot
	//System.out.println(Refresh);

  }

}// End class InputPanel




