//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_SCHWEFEL_H
#define ABC_SCHWEFEL_H


#include <cmath>
#include <vector>
#include <string>
#include "Problem.h"

class Schwefel : public Problem {
public:
    Schwefel(int dimensions) : Problem("Schwefel", dimensions) {
        for (int i = 0; i < dimensions; i++) {
            lowerLimit.push_back(-100.0);
            upperLimit.push_back(100.0);
        }
    }

    double evaluate(double x[]) override {
        double fitness = 0;
        double sum;
        for (int i = 0; i < dimensions; i++) {
            sum = 0;
            for (int j = 0; j < i; j++) {
                sum += x[i];
            }
            fitness += pow(sum, 2);
        }
        return fitness;
    }
};


#endif //ABC_SCHWEFEL_H
