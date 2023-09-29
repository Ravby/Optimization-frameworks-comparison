//
// Created by Ravby on 20/04/2023.
//

#ifndef ABC_PROBLEM_H
#define ABC_PROBLEM_H


#include <string>
#include <vector>

class Problem {
public:
    std::string name;
    int dimensions;
    std::vector<double> upperLimit;
    std::vector<double> lowerLimit;

    Problem(std::string name, int dimensions)
            : name(name), dimensions(dimensions) {}

    virtual double evaluate(double x[]) = 0;
};


#endif //ABC_PROBLEM_H
