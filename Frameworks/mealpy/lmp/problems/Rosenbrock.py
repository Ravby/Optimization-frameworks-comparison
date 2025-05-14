from mealpy import Problem
import numpy as np


class Rosenbrock(Problem):
    def __init__(self, bounds=None, minmax="min", name="Rosenbrock", **kwargs):
        super().__init__(bounds, minmax, **kwargs)
        self.name = name

    def obj_func(self, solution):
        d = solution.size
        res = 0
        for i in range(d - 1):
            res += 100 * (solution[i + 1] - solution[i] ** 2) ** 2 + (solution[i] - 1) ** 2
        return res
