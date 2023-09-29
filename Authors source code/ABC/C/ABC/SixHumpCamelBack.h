//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_SIXHUMPCAMELBACK_H
#define ABC_SIXHUMPCAMELBACK_H


#include <cmath>
#include <vector>
#include "Problem.h"


class SixHumpCamelBack : public Problem {
public:
    SixHumpCamelBack() : Problem("SixHumpCamelBack", 2) {
        lowerLimit = std::vector<double>(dimensions, -5.0);
        upperLimit = std::vector<double>(dimensions, 5.0);
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


#endif //ABC_SIXHUMPCAMELBACK_H
