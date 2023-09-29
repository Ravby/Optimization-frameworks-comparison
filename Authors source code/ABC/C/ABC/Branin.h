//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_BRANIN_H
#define ABC_BRANIN_H


#include <cmath>
#include <string>
#include <vector>
#include "problem.h"

class Branin : public Problem {
public:
    Branin() : Problem("Branin", 2) {
        lowerLimit = {-5.0, 0.0};
        upperLimit = {10.0, 15.0};
    }

    double evaluate(double x[]) override {

        double fitness = 0;
        fitness = 4 * pow(x[0], 2)
                  - 2.1 * pow(x[0], 4)
                  + (1.0 / 3.0) * pow(x[0], 6)
                  + x[0] * x[1]
                  - 4 * pow(x[1], 2)
                  + 4 * pow(x[1], 4);
        return fitness;
    }
};


#endif //ABC_BRANIN_H
