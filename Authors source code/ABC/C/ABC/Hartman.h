//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_HARTMAN_H
#define ABC_HARTMAN_H


#include <cmath>
#include <vector>
#include <string>
#include "Problem.h"


class Hartman : public Problem {
private:
    static constexpr double a[4][3] = {
            {3, 10, 30},
            {0.1, 10, 35},
            {3, 10, 30},
            {0.1, 10, 35}
    };
    static constexpr double p[4][3] = {
            {0.3689, 0.1170, 0.2673},
            {0.4699, 0.4387, 0.7470},
            {0.1091, 0.8732, 0.5547},
            {0.03815, 0.5743, 0.8828}
    };
    static constexpr double c[4] = {1, 1.2, 3, 3.2};

public:
    Hartman() : Problem("Hartman", 3) {
        lowerLimit = std::vector<double>(dimensions, 0.0);
        upperLimit = std::vector<double>(dimensions, 1.0);
    }

    double evaluate(double x[]) override {
        double fitness = 0;
        double sum;
        for (int i = 0; i < 4; i++) {
            sum = 0;
            for (int j = 0; j < dimensions; j++) {
                sum += a[i][j] * pow(x[j] - p[i][j], 2);
            }
            fitness += c[i] * exp(sum * (-1));
        }
        return fitness * -1;
    }
};

#endif //ABC_HARTMAN_H
