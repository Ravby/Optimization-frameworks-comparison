package DeApp1;
/***********************************************************
** This is the main class of the DE development environ-  **
** ment. It's intention is to allow for the optimization  **
** of various objective functions with graphical output   **
** support.                                               **
**                                                        **
**                                                        **
** Version-Nr.:        1.0.3                              **
** Date:               2/24/99                            **
**                                                        **
**  Known Problems:                                       **
**  1) Coding and naming conventions not always adhered   **
**     to ->renaming makes program more difficult to read.**
**  6) Choice of strategy should not be just 1 automati-  **
**     cally.                                             **
** 10) For all problems objects are created, whether      **
**     they are needed or not.                            **
** 13) Inconsistent initialization. SetProblem() but not  **
**     SetStrategy(). This has to be cleaned up.          **
** 14) Remove access to status panel from control panel.  **
** 18) Number of plotting samples should be flexibly      **
**     adjustable depending on the problem.               **
**                                                        **
** Contributors:       Mikal Keenan                       **
**                     Rituraj Deb Nath                   **
**                     Kenneth Price                      **
**                     Rainer Storn						  **
**                                                        **
**                                                        **
** Bug fixes and changes:                                 **
**                                                        **
** Change                       |     Who      |   When   **
** -----------------------------|--------------|----------**
** Change applet into applica-  | R.Storn      | 3/5/98   **
** tion.                        |              |          **
** Change the monolithic stru-  | R.Storn      | 3/7/98   **
** cture into a hierachical one.|              |          **
** Change of variable, class,   | R.Storn      | 3/10/98  **
** object and method names      |              |          **
** according to naming conven-  |              |          **
** tions.                       |              |          **
** Removal of unnecessary       | R.Storn      | 3/15/98  **
** methods.                     |              |          **
** Addition of more comments.   | R.Storn      | 3/15/98  **
** Separation of graphics and   | R.Storn      | 3/18/98  **
** introduction of a separate   |      		   |          **
** optimization thread.         |              |          **
** Sliders removed for the sake | R.Storn      | 3/24/98  **
** of textual input.            |              |          **
** Change of DeRandom.next() to | R. D. Nath   | 6/25/98  **
** DeRandom.nextValue() to get  |              |          **
** rid of JDK1.1.6 problems.    |              |          **
** DeScreen.destroyPlotScreenX()| R. D. Nath   | 6/25/98  **
** checks that the plotScreenX  |              |          **
** is not null before attempting|              |          **
** to destroy them.             |              |          **
** PlotChoicePanel.handleEvent()| R. D. Nath   | 6/25/98  **
** checks that the screen does  |              |          **
** not exist before attempting  |              |          **
** to create a new one.			|              |          **
** Check that the event is an   | R. D. Nath   | 6/25/98  **
** ACTION_EVENT before handling |              |          **
** it to avoid flickering of    |              |          **
** plot screens when mouse is   |              |          **
** moved over checkboxes.       |              |          **
** Plotting capabilities of     | R. Storn     | 2/14/99  **
** ptplot included. Zooming now |              |          **
** possible.                    |              |          **
** Added a simple digital filter| R. Storn     | 2/20/99  **
** design example               |              |          **
** More DE strategies imple-    | R. Storn     | 2/22/99  **
** mented.                      |              |          **
**                              |              |          **
***********************************************************/


import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import DeApp1.screen.*;	   // in order to have access to DEScreen


public class DeApp1
{
  public static void main (String args[])
  // The program starts here as in C and C++
  {

	DEScreen deScreen = new DEScreen();    // Create the application
	deScreen.pack();                       // Arrange components
	deScreen.show();                       // Make them visible

  }
}





