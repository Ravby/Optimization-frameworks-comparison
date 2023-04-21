//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_SHEKELSFOXHOLES_H
#define ABC_SHEKELSFOXHOLES_H


#include <vector>
#include <cmath>
#include "Problem.h"

class ShekelsFoxholes : public Problem {
private:
    static constexpr  double a[25][2]= {{-32, -32},
                                        {-16, -32},
                                        {0, -32},
                                        {16, -32},
                                        {32, -32},
                                        {-32, -16},
                                        {-16, -16},
                                        {0, -16},
                                        {16, -16},
                                        {32, -16},
                                        {-32, 0},
                                        {-16, 0},
                                        {0, 0},
                                        {16, 0},
                                        {32, 0},
                                        {-32, 16},
                                        {-16, 16},
                                        {0, 16},
                                        {16, 16},
                                        {32, 16},
                                        {-32, 32},
                                        {-16, 32},
                                        {0, 32},
                                        {16, 32},
                                        {32, 32}};
public:
    ShekelsFoxholes() : Problem("ShekelsFoxholes", 2) {
        lowerLimit = std::vector<double>(dimensions, -65.536);
        upperLimit = std::vector<double>(dimensions, 65.536);
    }

    double evaluate(double x[]) override {
        double fitness = 0.0;
        double sum;
        for (int j = 0; j < 25; j++) {
            sum = 0.0;
            for (int i = 0; i < dimensions; i++) {
                sum += pow(x[i] - a[j][i], 6);
            }
            sum += j + 1;
            fitness += 1.0 / sum;
        }
        fitness += 1.0 / 500.0;
        fitness = pow(fitness, -1.0);

        return fitness;
    }
};

#endif //ABC_SHEKELSFOXHOLES_H
