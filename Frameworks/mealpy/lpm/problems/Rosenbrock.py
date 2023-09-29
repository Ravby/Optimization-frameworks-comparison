from mealpy import Problem
import numpy as np


class Rosenbrock(Problem):
    def __init__(self, lb, ub, minmax, name="Rosenbrock", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        d = solution.size
        res = 0
        for i in range(d - 1):
            res += 100 * (solution[i + 1] - solution[i] ** 2) ** 2 + (solution[i] - 1) ** 2
        return res
