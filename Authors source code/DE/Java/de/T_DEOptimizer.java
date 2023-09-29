package DeApp1.de;

import java.awt.*;         // Import all classes from the java.awt package
// AWT is the Abstract Window Toolkit. The AWT
import java.io.*;

import DeApp1.screen.*;     // Import screens
import DeApp1.panel.*;       // Import panels
import DeApp1.problem.*;       // Import cost


public class T_DEOptimizer implements Runnable
/***************************************************************
 **                                                            **
 **        D I F F E R E N T I A L     E V O L U T I O N       **
 **                                                            **
 ** This is the kernel routine for the DE optimization.        **
 **                                                            **
 ** Authors:            Mikal Keenan                           **
 **                     Kenneth Price                          **
 **                     Rainer Storn                           **
 **                                                            **
 ** This program implements some variants of Differential      **
 ** Evolution (DE) as described in part in the techreport      **
 ** tr-95-012.ps of ICSI. You can get this report either via   **
 ** ftp.icsi.berkeley.edu/pub/techreports/1995/tr-95-012.ps.Z  **
 ** or via WWW: http://http.icsi.berkeley.edu/~storn/litera.html*
 ** A more extended version of tr-95-012.ps has appeared in    **
 ** the Journal of global optimization.                        **
 **                                                            **
 ** You may use this program for any purpose, give it to any   **
 ** person or change it according to your needs as long as you **
 ** are referring to Rainer Storn and Ken Price as the origi-  **
 ** nators of the the DE idea.                                 **
 **                                                            **
 ***************************************************************/
{
    /*======Public variables======================================*/
    public DEScreen deScreen;    // container class
    public DERandom deRandom = new DERandom();
    public long Seed = 0;

    public DEStrategy Strategem[];
    public int current_strategy;

    public DEProblem deProblemArray[];  // prepare for all cost functions
    public int current_problem;            // pointer to the current problem


    /*----Initialize some public variables which will be accessed----*/
    /*----by the container class. The monitor panel wants them.------*/
    public int generation = 0;
    public int evaluation = 0;
    public double mincost = Double.MAX_VALUE;

    /*----These variables will be read from the panels----------------*/
    public int dim;  // this one is not taken from the panels but from
    // the problem.
    public int NP;
    public double F;
    public double Cr;
    public double Range;
    public int Refresh;   // Animation refresh rate

    /*======Protected variables======================================*/

    int MaxD = 100;   //maximum number of parameters
    int MaxN = 500;  //maximum for NP
    int MaxR = 10;   //maximum number of random choices

    double trial[] = new double[MaxD]; // the trial vector
    public double best[] = new double[MaxD]; // the best vector so far
    double genbest[] = new double[MaxD]; // the best vector of the current generation
    double cost[] = new double[MaxN];

    double p1[][] = new double[MaxN][MaxD]; // array of vectors
    double p2[][] = new double[MaxN][MaxD];

    double rvec[][] = new double[MaxR][MaxD]; // array of randomly chosen vectors
    int rnd[] = new int[MaxR];           // array of random indices

    double g0[][];    // just some pointers (placeholders)
    double g1[][];

    int i, j, k;  //simple counter variables
    int min_index = 0;

    Thread action;   //the optimizing thread

    boolean running = false;
    boolean console_out = false;     // dummy initializer. The true
    // initialization takes place
    // in PlotScreenPanel


    public T_DEOptimizer(DEScreen app)
    /***********************************************************
     ** Constructor of the class. Fetches strategy and problem.**
     ***********************************************************/
    {
        deScreen = app;     //fetch container
    }


    private void assign(double[] to, double[] from)
    /***************************************************************
     ** (Slow) assignment operator. Java is Java, C/C++ is C/C++...**
     ***************************************************************/
    {
        int i;
        for (i = 0; i < dim; i++) {
            to[i] = from[i];
        }
    }


    public double optimize(DEProblem deProblem)
    /***********************************************************
     ** The central component which actually does the DE       **
     ** optimization.                                          **
     ***********************************************************/
    {
        /*---Fetch control variables------------------------*/
        dim = deProblem.getLength(); // get size of the problem

        /*--------Optimize----------------------------------*/

        for (i = 0; i < NP; i++) {
            double[] x = p1[i];
            int j;
            for (j = 0; j < dim; j++) {
                x[j] = deRandom.nextValue(deProblem.lowerLimit[j], deProblem.upperLimit[j]);
            }
            cost[i] = deProblem.evaluate(this, x, dim);
            evaluation++;
        }

        mincost = cost[0];
        min_index = 0;
        for (j = 0; j < NP; j++) {
            double x = cost[j];
            if (x < mincost) {
                mincost = x;
                min_index = j;
            }
        }

        assign(best, p1[min_index]);
        assign(genbest, best);

        g0 = p1;   // generation t
        g1 = p2;   // generation t+1

        int iterations = Refresh; // how many iterations for this call
        // (defined in scroll panel)
        while (iterations-- > 0) {
            for (int i = 0; i < NP; i++) {
                assign(trial, g0[i]);    // trial vector

                do rnd[0] = deRandom.nextValue(NP); // BUG:: Changed next() to nextValue
                while (rnd[0] == i);

                do rnd[1] = deRandom.nextValue(NP);// BUG:: Changed next() to nextValue
                while ((rnd[1] == i) || (rnd[1] == rnd[0]));

                do rnd[2] = deRandom.nextValue(NP);// BUG:: Changed next() to nextValue
                while ((rnd[2] == i) || (rnd[2] == rnd[1]) || (rnd[2] == rnd[0]));

                do rnd[3] = deRandom.nextValue(NP);// BUG:: Changed next() to nextValue
                while ((rnd[3] == i) || (rnd[3] == rnd[2]) || (rnd[3] == rnd[1]) || (rnd[3] == rnd[0]));

                do rnd[4] = deRandom.nextValue(NP);// BUG:: Changed next() to nextValue
                while ((rnd[4] == i) || (rnd[4] == rnd[3]) || (rnd[4] == rnd[2]) || (rnd[4] == rnd[1]) || (rnd[4] == rnd[0]));

                do rnd[5] = deRandom.nextValue(NP);// BUG:: Changed next() to nextValue
                while ((rnd[5] == i) || (rnd[5] == rnd[4]) || (rnd[5] == rnd[3]) || (rnd[5] == rnd[2])
                        || (rnd[5] == rnd[1]) || (rnd[5] == rnd[0]));

                for (k = 0; k < 6; k++)  // select the random vectors
                {
                    rvec[k] = g0[rnd[k]];
                }
                /*---Apply the DE strategy of choice-------------------*/
                Strategem[current_strategy].apply(F, Cr, dim, trial, genbest, rvec);

                /*---cost of trial vector------------------------*/
                double testcost = deProblem.evaluate(this, trial, dim);
                evaluation++;

                if (testcost <= cost[i])  // Better solution than target vectors cost
                {
                    assign(g1[i], trial);    // if yes put trial vector in new population
                    cost[i] = testcost;        // and save the new cost value
                    if (testcost < mincost) // if testcost is best ever
                    {
                        mincost = testcost;   // new mincost
                        assign(best, trial);    // best vector is trial vector
                        min_index = i;        // save index of best vector
                    }
                } else    // if trial vector is worse than target vector
                {
                    assign(g1[i], g0[i]); // Propagate the old
                }
            }// end for (int i = 0;  i < NP;  i++)

            assign(genbest, best);   // Save current generation's best

            double gx[][] = g0;       // Swap population pointers
            g0 = g1;
            g1 = gx;

            generation++;
        }// loop


        if (console_out == true) {
            for (i = 0; i < dim; i++) {
                System.out.println("best[" + i + "] = " + best[i]);
            }
            System.out.println(" ");
        }

        //assign(deProblem.getBest(), best);

        return mincost;
    }

    public void start()
    /***********************************************************
     ** The "Init()" method for the thread.                    **
     ***********************************************************/
    {
        deScreen.getParameters(); // get all the parameters

        System.out.println("Optimizer started");


        /*-----Deal with strategy first------------------*/
        String S[] = deScreen.getStrategyIdentifiers();
        Strategem = new DEStrategy[S.length];
        for (i = 0; i < S.length; i++) {
            try { //give the full pathname here so the class can be located
                Class C = Class.forName("DeApp1.de.DE" + S[i]);
                //System.out.println("Class"+ C);//Debug
                Strategem[i] = (DEStrategy) C.newInstance();
                Strategem[i].init(deRandom);
            } catch (Exception e) {
                System.err.println(e);  // Fix
            }
            ;
        }
        System.out.println("Strategies chosen\n");

        /*-----Now care about the problem-----------------*/
        String Identifier[] = deScreen.getProblemIdentifiers(); //list of problems
        deProblemArray = new DEProblem[Identifier.length]; // Array of problem objects
        //System.out.println("o.k.\n");

        for (i = 0; i < Identifier.length; i++) {//create an object for all problems possible -> improve that!
            try {//Give full path name here so the class can be found
                Class C = Class.forName("DeApp1.problem." + Identifier[i]);
                //System.out.println(C);
                deProblemArray[i] = (DEProblem) C.newInstance(); //select the problem
                //deProblemArray[i].init (); //set initial size and some constants
            } catch (Exception e) {
                System.err.println(e);
                System.out.println("i=" + i);
            }
            ;
        }

        if (action == null) //if thread is not running
        {
            action = new Thread(this); // Instantiate the new thread
            action.start();             // Start it
        }
    }


    public void stop()
    /***********************************************************
     ** Stop the thread when you leave the application.        **
     ***********************************************************/
    {
        action = null;
    }


    public void run()
    /***********************************************************
     ** The "main()" method for the thread.                    **
     ***********************************************************/
    // Let it run! The optimization is taking place here!
    {
        while (action != null) //as long as there is an active thread
        {
            /*--If we want the thread to pause we suspend the optimization.---*/
            /*--We need this to be able to steer the optimization with--------*/
            /*--the GUI buttons. Otherwise the optimization runs and runs...--*/
            /*--Resuming of the thread activity via action.resume() is done---*/
            /*--by means of the appropriate GUI event.------------------------*/
            if (running == false) {
                action.suspend();
            }

            /*--Let the optimization sleep for the time NAPTIME so that the--*/
            /*--observers get some time to show their graphics.--------------*/
            /*--If we don't do this the human eye often can't follow the-----*/
            /*--update of the console or the graphics.-----------------------*/
            try {
                action.sleep(DEProblem.NAPTIME);  // Suspend optimization for NAPTIME
            } catch (Exception E) {
                System.err.println(E);  // In case the sleep command fails
            }
            ;

            /*--Run the optimizer Refresh times---------------*/
            optimize(deProblemArray[current_problem]);
            /*--update all observers (data panel,plot screens)---*/
            deScreen.repaint();

            /*--Give the new mincost to the problem. It's-----*/
            /*--function completed() then decides whether-----*/
            /*--to stop.--------------------------------------*/
            deProblemArray[current_problem].mincost = mincost;

            /*--Check if the VTR has been reached and the optimization--*/
            /*--is completed.-------------------------------------------*/
            if (deProblemArray[current_problem].completed()) {
                makeNotReady(); // running = false, pause the thread
                deScreen.done();
            }
        }
    }


    public synchronized void makeReady()
    /***********************************************************
     ** As the name says: Make the thread ready for running.   **
     ***********************************************************/
    {
        running = true;
    }


    public synchronized void makeNotReady()
    /***********************************************************
     ** Prepare the thread for sleep.                          **
     ***********************************************************/
    {
        running = false;
    }


    // Some helper methods


    public void setProblem(int index)
    /***********************************************************
     ** Select the cost function.                              **
     ***********************************************************/
    {
        current_problem = index;
    }


    public void optIdle()
    /***********************************************************
     ** Suspend the animation.                                 **
     ***********************************************************/
    {
        makeNotReady();       // Suspend the optimization
    }


    public void optStart()
    /***********************************************************
     ** Start the animation.                                   **
     ***********************************************************/
    {
        generation = 0;                    // Initialize
        evaluation = 0;                    // dto.
        mincost = Double.MAX_VALUE;    // dto.
        deScreen.getParameters();    // get all the parameters from the panels

        makeReady();                       // Wake up the animation
        action.resume();                   // and get it going
    }


    public void optResume()
    /***********************************************************
     ** Wake the sleeping animation.                           **
     ***********************************************************/
    {
        deScreen.getParameters();    // get all the parameters from the panels
        makeReady();                       // Wake the animation up
        action.resume();                   // and get it going
    }



    /*====Some methods for the observers to get their input.=====*/

    public int getGeneration()
    /***********************************************************
     ** Which iteration of the optimization ?                  **
     ***********************************************************/
    {
        return generation;
    }


    public int getEvaluation()
    /***********************************************************
     ** How many evaluations have been done so far ?           **
     ***********************************************************/
    {
        return evaluation;
    }


    public double getMinimum()
    /***********************************************************
     ** What's the best cost value so far ?                    **
     ***********************************************************/
    {
        return mincost;
    }

    public final double[] getBest()
    /**********************************
     ** Best vector.                  **
     **********************************/
    {
        return best;
    }

    /*====End of the observer methods.========================*/

    public void consoleEnable()
    /***********************************************************
     ** Enable console output trace of optimization parameters.**
     ***********************************************************/
    {
        console_out = true;
    }

    public void consoleDisable()
    /***********************************************************
     ** Enable console output trace of optimization parameters.**
     ***********************************************************/
    {
        console_out = false;
    }

    public void updateTrialVector(double x[], int dim)
    /***********************************************************
     ** Allows to updat the trial vector from outside.         **
     ***********************************************************/
    {
        int i;
        for (i = 0; i < dim; i++) {
            trial[i] = x[i];
        }
    }


}





