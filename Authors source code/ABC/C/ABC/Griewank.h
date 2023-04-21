//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_GRIEWANK_H
#define ABC_GRIEWANK_H


#include <cmath>
#include <vector>
#include "Problem.h"

class Griewank : public Problem {
public:
    Griewank(int dimensions) : Problem("Griewank", dimensions) {
        lowerLimit = std::vector<double>(dimensions, -600.0);
        upperLimit = std::vector<double>(dimensions, 600.0);
    }

    double evaluate(double* x) override {
        double fitness = 0.0;
        double sum = 0.0;
        double mult = 1.0;
        double d = 4000.0;

        for (int i = 0; i < dimensions; i++) {
            sum += x[i] * x[i];
            mult *= std::cos(x[i] / std::sqrt(i + 1));
        }

        fitness = 1.0 / d * sum - mult + 1.0;
        return fitness;
    }
};


#endif //ABC_GRIEWANK_H
