//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_ACKLEY_H
#define ABC_ACKLEY_H


#include <cmath>
#include <vector>
#include <string>
#include <algorithm>
#include "Problem.h"


class Ackley : public Problem {
public:
    Ackley(int dimensions) : Problem("Ackley", dimensions) {
        lowerLimit = std::vector<double>(dimensions, -32.0);
        upperLimit = std::vector<double>(dimensions, 32.0);
    }

    double evaluate(double x[]) override {
        double fitness = 0;
        double a = 20.0, b = 0.2, c = 2 * M_PI;
        double sum1 = 0, sum2 = 0;

        for (int i = 0; i < dimensions; i++) {
            sum1 += x[i] * x[i];
            sum2 += cos(c * x[i]);
        }

        fitness = -a * exp(-b * sqrt(1.0 / dimensions * sum1)) - exp(1.0 / dimensions * sum2) + a + M_E;

        return fitness;
    }
};


#endif //ABC_ACKLEY_H
