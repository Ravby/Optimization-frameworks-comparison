//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_ABC_H
#define ABC_ABC_H

#include "Problem.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <conio.h>
#include <time.h>

class ABC {
    /*
    double Foods[FoodNumber][D]; //Foods is the population of food sources. Each row of Foods matrix is a vector holding D parameters to be optimized. The number of rows of Foods matrix equals to the FoodNumber
    double f[FoodNumber];  //f is a vector holding objective function values associated with food sources
    double fitness[FoodNumber]; //fitness is a vector holding fitness (quality) values associated with food source
    double trial[FoodNumber]; //trial is a vector holding trial numbers through which solutions can not be improved
    double prob[FoodNumber]; //prob is a vector holding probabilities of food sources (solutions) to be chosen
    double solution[D]; //New solution (neighbour) produced by v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) j is a randomly chosen parameter and k is a randomlu chosen solution different from i
    double GlobalParams[D]; //Parameters of the optimum solution
*/

    /* Control Parameters of ABC algorithm*/
    int NP; /* The number of colony size (employed bees+onlooker bees)*/
    int FoodNumber; /*The number of food sources equals the half of the colony size*/
    int limit;  /*A food source which could not be improved through "limit" trials is abandoned by its employed bee*/
    int currentEval;
    int maxEval = 15000;

    std::unique_ptr<double[]> solution;
    std::unique_ptr<double[]> GlobalParams;
    //std::unique_ptr<double*[]> Foods;
    std::vector<double*> Foods;
    std::unique_ptr<double[]> f;
    std::unique_ptr<double[]> fitness;
    std::unique_ptr<double[]> trial;
    std::unique_ptr<double[]> prob;

    double ObjValSol; /*Objective function value of new solution*/
    double FitnessSol; /*Fitness value of new solution*/
    int neighbour, param2change; /*param2change corrresponds to j, neighbour corresponds to k in equation v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij})*/
    double GlobalMin; /*Optimum solution obtained by ABC algorithm*/

    double r; /*a random number in the range [0,1)*/

    Problem* problem;

    double calculateFunction(double *sol);

public:
    ABC(Problem* problem, int NP, int limit, int maxEval);

    virtual ~ABC();

    void init(int index);
    void initial();
    double CalculateFitness(double fun);
    void SendEmployedBees();
    void CalculateProbabilities();
    void SendOnlookerBees();
    void MemorizeBestSource();
    void SendScoutBees();

    double run();
};


#endif //ABC_ABC_H
