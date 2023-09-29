from mealpy import Problem
import numpy as np


class Rastrigin(Problem):
    def __init__(self, lb, ub, minmax, name="Rastrigin", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        d = solution.size
        A = 10
        return A * d + np.sum(solution ** 2 - A * np.cos(2 * np.pi * solution))
