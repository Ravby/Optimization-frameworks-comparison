//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_RASTRIGIN_H
#define ABC_RASTRIGIN_H


#include <vector>
#include <cmath>
#include "Problem.h"

class Rastrigin : public Problem {
public:
    Rastrigin(int dimensions) : Problem("Rastrigin", dimensions) {
        lowerLimit.resize(dimensions, -5.12);
        upperLimit.resize(dimensions, 5.12);
    }

    double evaluate(double x[]) override {
        double fitness = 0.0;
        double a = 10.0;
        double w = 2 * M_PI;

        for (int i = 0; i < dimensions; i++) {
            fitness += x[i] * x[i] - a * cos(w * x[i]);
        }

        fitness += a * dimensions;

        return fitness;
    }
};


#endif //ABC_RASTRIGIN_H
