from mealpy import Problem
import math


class Schwefel12(Problem):
    def __init__(self, lb, ub, minmax, name="Schwefel", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        d = solution.size
        fitnes = 0
        for i in range(d):
            sum = 0
            for value in solution:
                sum += value * math.sin(math.sqrt(abs(value)))
            fitnes += sum ** 2
        return fitnes
