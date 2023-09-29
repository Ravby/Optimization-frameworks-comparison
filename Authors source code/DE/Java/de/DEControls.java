package DeApp1.de;

import java.io.*;
                      

public class DEControls
/***********************************************************
**                                                        **
** Defines some control variables for DE.                 **
**                                                        **
** Authors:            Mikal Keenan                       **
**                     Rainer Storn                       **
**                                                        **
** Date:               3/16/98                            **
**                                                        **
***********************************************************/
{
       public int MaxD     = 17;   //maximum number of parameters
       public int MaxN     = 300;  //maximum for NP

       public long Seed     = 0;

       public int Strategy;
       public int Problem;

       public int dim      = MaxD;
       public int NP;
       public double F;
       public double Cr;
       public double Range;
       public int Refresh;   // Animation refresh rate
}








