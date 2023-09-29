package DeApp1.screen;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;



public class PrintOut
/***********************************************************
**                                                        **
***********************************************************/
{
  DEScreen deScreen;

  public PrintOut(DEScreen app)
  /*************************************
  ** Constructor of the class.        **
  *************************************/
  {
	  deScreen = app;
  }


  public void printResult()
  /***********************************************************
  **                                                   **
  ***********************************************************/
  {
	    int i, imax;
        double best[] = new double [100]; 

		try
		{
		  FileOutputStream out = new FileOutputStream("DEOut.dat");
		  PrintStream printout = new PrintStream(out,false);
		  imax = deScreen.getDimension();
		  best = deScreen.getBest();
		  for (i=0; i<imax; i++)
		  {
		    printout.println("best["+i+"]="+best[i]+"\r");
		  }

		  printout.close();
		}
		catch(IOException e)
		{
		   e.printStackTrace();
		}
  }	// End printResult()

}// Class PrintOut




