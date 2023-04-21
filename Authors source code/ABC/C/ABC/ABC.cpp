//
// Created by Ravby on 20/04/2023.
//

#include <memory>
#include "ABC.h"

ABC::ABC(Problem* problem, int NP, int limit, int maxEval) : NP(NP), limit(limit), maxEval(maxEval) {
    this->problem = problem;

    FoodNumber = NP/2; /*The number of food sources equals the half of the colony size*/
    currentEval = 0;
    solution = std::unique_ptr<double[]>(new double[problem->dimensions]);
    GlobalParams = std::unique_ptr<double[]>(new double[problem->dimensions]);

    Foods.resize(FoodNumber);
    for (int i = 0; i < FoodNumber; ++i)
        Foods[i] = new double[problem->dimensions];

    f = std::unique_ptr<double[]>(new double[FoodNumber]);
    fitness = std::unique_ptr<double[]>(new double[FoodNumber]);
    trial = std::unique_ptr<double[]>(new double[FoodNumber]);
    prob = std::unique_ptr<double[]>(new double[FoodNumber]);
}

ABC::~ABC() {
    for (int i = 0; i < FoodNumber; ++i)
        delete[] Foods[i];
}

double ABC::calculateFunction(double sol[]) {
    currentEval++;
    return problem->evaluate(sol);
}

double ABC::CalculateFitness(double fun) {
    double result = 0;
    if (fun >= 0) {
        result = 1 / (fun + 1);
    } else {
        result = 1 + fabs(fun);
    }
    return result;
}

void ABC::MemorizeBestSource() {
    int i, j;

    for (i = 0; i < FoodNumber; i++) {
        if (f[i] < GlobalMin) {
            GlobalMin = f[i];
            for (j = 0; j < problem->dimensions; j++)
                GlobalParams[j] = Foods[i][j];
        }
    }
}

void ABC::init(int index) {
    int j;
    for (j = 0; j < problem->dimensions; j++) {
        r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
        Foods[index][j] = r * (problem->upperLimit[j] - problem->lowerLimit[j]) +  problem->lowerLimit[j];
        solution[j] = Foods[index][j];
    }
    f[index] = calculateFunction(solution.get());
    fitness[index] = CalculateFitness(f[index]);
    trial[index] = 0;
}

void ABC::initial() {
    int i;
    for (i = 0; i < FoodNumber; i++) {
        init(i);
    }
    GlobalMin = f[0];
    for (i = 0; i < problem->dimensions; i++)
        GlobalParams[i] = Foods[0][i];
}

void ABC::SendEmployedBees() {
    int i, j;
    /*Employed Bee Phase*/
    for (i = 0; i < FoodNumber; i++) {
        /*The parameter to be changed is determined randomly*/
        r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
        param2change = (int) (r * problem->dimensions);

        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
        r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
        neighbour = (int) (r * FoodNumber);

        /*Randomly selected solution must be different from the solution i*/
        while (neighbour == i) {
            r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
            neighbour = (int) (r * FoodNumber);
        }
        for (j = 0; j < problem->dimensions; j++)
            solution[j] = Foods[i][j];

        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
        r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
        solution[param2change] =
                Foods[i][param2change] + (Foods[i][param2change] - Foods[neighbour][param2change]) * (r - 0.5) * 2;

        /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
        if (solution[param2change] < problem->lowerLimit[param2change])
            solution[param2change] = problem->lowerLimit[param2change];
        if (solution[param2change] > problem->upperLimit[param2change])
            solution[param2change] = problem->upperLimit[param2change];
        ObjValSol = calculateFunction(solution.get());
        FitnessSol = CalculateFitness(ObjValSol);

        /*a greedy selection is applied between the current solution i and its mutant*/
        if (FitnessSol > fitness[i]) {
            /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
            trial[i] = 0;
            for (j = 0; j < problem->dimensions; j++)
                Foods[i][j] = solution[j];
            f[i] = ObjValSol;
            fitness[i] = FitnessSol;
        } else {   /*if the solution i can not be improved, increase its trial counter*/
            trial[i] = trial[i] + 1;
        }


    }
    /*end of employed bee phase*/
}

void ABC::CalculateProbabilities() {
    int i;
    double maxfit;
    maxfit = fitness[0];
    for (i = 1; i < FoodNumber; i++) {
        if (fitness[i] > maxfit)
            maxfit = fitness[i];
    }

    for (i = 0; i < FoodNumber; i++) {
        prob[i] = (0.9 * (fitness[i] / maxfit)) + 0.1;
    }
}

void ABC::SendOnlookerBees() {

    int i, j, t;
    i = 0;
    t = 0;
    /*onlooker Bee Phase*/
    while (t < FoodNumber) {

        r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
        if (r < prob[i]) /*choose a food source depending on its probability to be chosen*/
        {
            t++;

            /*The parameter to be changed is determined randomly*/
            r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
            param2change = (int) (r * problem->dimensions);

            /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
            r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
            neighbour = (int) (r * FoodNumber);

            /*Randomly selected solution must be different from the solution i*/
            while (neighbour == i) {
                r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
                neighbour = (int) (r * FoodNumber);
            }
            for (j = 0; j < problem->dimensions; j++)
                solution[j] = Foods[i][j];

            /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
            r = ((double) rand() / ((double) (RAND_MAX) + (double) (1)));
            solution[param2change] =
                    Foods[i][param2change] + (Foods[i][param2change] - Foods[neighbour][param2change]) * (r - 0.5) * 2;

            /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
            if (solution[param2change] < problem->lowerLimit[param2change])
                solution[param2change] = problem->lowerLimit[param2change];
            if (solution[param2change] > problem->upperLimit[param2change])
                solution[param2change] = problem->upperLimit[param2change];
            ObjValSol = calculateFunction(solution.get());
            FitnessSol = CalculateFitness(ObjValSol);

            /*a greedy selection is applied between the current solution i and its mutant*/
            if (FitnessSol > fitness[i]) {
                /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
                trial[i] = 0;
                for (j = 0; j < problem->dimensions; j++)
                    Foods[i][j] = solution[j];
                f[i] = ObjValSol;
                fitness[i] = FitnessSol;
            } else {   /*if the solution i can not be improved, increase its trial counter*/
                trial[i] = trial[i] + 1;
            }
        } /*if */
        i++;
        if (i == FoodNumber)
            i = 0;
    }/*while*/

    /*end of onlooker bee phase     */
}

void ABC::SendScoutBees() {
    int maxtrialindex, i;
    maxtrialindex = 0;
    for (i = 1; i < FoodNumber; i++) {
        if (trial[i] > trial[maxtrialindex])
            maxtrialindex = i;
    }
    if (trial[maxtrialindex] >= limit) {
        init(maxtrialindex);
    }
}

double ABC::run() {
    initial();
    MemorizeBestSource();
    int iter = 0;

    while (currentEval < maxEval) {
        iter++;
        SendEmployedBees();
        CalculateProbabilities();
        if(currentEval >= maxEval)
            break;
        SendOnlookerBees();
        MemorizeBestSource();
        SendScoutBees();
    }

    return GlobalMin;
}

