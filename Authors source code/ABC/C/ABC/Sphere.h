//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_SPHERE_H
#define ABC_SPHERE_H


#include <string>
#include <vector>
#include <algorithm>
#include <numeric>
#include "Problem.h"

class Sphere : public Problem {
public:
    Sphere(int dimensions) : Problem("Sphere", dimensions) {
        lowerLimit.resize(dimensions, -100.0);
        upperLimit.resize(dimensions, 100.0);
    }

    double evaluate(double x[]) override {
        double sum = 0.0;
        for (int i = 0; i < dimensions; i++) {
            sum += x[i] * x[i];
        }
        return sum;
    }
};


#endif //ABC_SPHERE_H
