//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_GOLDSTEINPRICE_H
#define ABC_GOLDSTEINPRICE_H


#include <vector>
#include <cmath>
#include "Problem.h"

class GoldsteinPrice : public Problem {
public:
    GoldsteinPrice() : Problem("GoldsteinPrice", 2) {
        lowerLimit = {-2.0, -2.0};
        upperLimit = {2.0, 2.0};
    }

    double evaluate(double x[]) override {
        double fitness = 0;
        fitness = (1 + pow(x[0] + x[1] + 1, 2) * (19 - 14 * x[0] + 3 * pow(x[0], 2) - 14 * x[1] + 6 * x[0] * x[1] + 3 * pow(x[1], 2))) *
                  (30 + pow(2 * x[0] - 3 * x[1], 2) * (18 - 32 * x[0] + 12 * pow(x[0], 2) + 48 * x[1] - 36 * x[0] * x[1] + 27 * pow(x[1], 2)));
        return fitness;
    }
};


#endif //ABC_GOLDSTEINPRICE_H
