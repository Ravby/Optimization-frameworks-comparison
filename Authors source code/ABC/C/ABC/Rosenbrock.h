//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_ROSENBROCK_H
#define ABC_ROSENBROCK_H


#include <vector>
#include <cmath>
#include "Problem.h"

class Rosenbrock : public Problem {
public:
    Rosenbrock(int dimensions) : Problem("Rosenbrock", dimensions) {
        lowerLimit = std::vector<double>(dimensions, -30.0);
        upperLimit = std::vector<double>(dimensions, 30.0);
    }

    double evaluate(double x[]) override {
        double fitness = 0;
        double sum = 0.0;
        for (int i = 0; i < dimensions - 1; i++) {
            double temp1 = (x[i] * x[i]) - x[i + 1];
            double temp2 = x[i] - 1.0;
            sum += (100.0 * temp1 * temp1) + (temp2 * temp2);
        }
        fitness = sum;

        return fitness;
    }
};


#endif //ABC_ROSENBROCK_H
