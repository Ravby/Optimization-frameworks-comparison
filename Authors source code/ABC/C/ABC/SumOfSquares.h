//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_SUMOFSQUARES_H
#define ABC_SUMOFSQUARES_H


#include <string>
#include <vector>
#include "Problem.h"

class SumOfSquares : public Problem {
public:
    SumOfSquares(int dimensions) : Problem("SumOfSquares", dimensions) {
        lowerLimit.resize(dimensions, -100.0);
        upperLimit.resize(dimensions, 100.0);
    }

    double evaluate(double* x) override {
        double sum = 0.0;
        for (int i = 0; i < dimensions; i++) {
            sum += (i + 1) * x[i] * x[i];
        }
        return sum;
    }
};


#endif //ABC_SUMOFSQUARES_H
