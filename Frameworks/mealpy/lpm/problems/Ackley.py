from mealpy import Problem
import numpy as np


class Ackley(Problem):
    def __init__(self, lb, ub, minmax, name="Ackley", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        d = solution.size
        a = 20
        b = 0.2
        c = 2 * np.pi
        sum_sq_term = -a * np.exp(-b * np.sqrt(np.sum(solution ** 2) / d))
        cos_term = -np.exp(np.sum(np.cos(c * solution)) / d)
        return a + np.exp(1) + sum_sq_term + cos_term
