from mealpy import Problem
import numpy as np


class Griewank(Problem):
    def __init__(self, lb, ub, minmax, name="Griewank", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        d = solution.size
        sum_sq_term = np.sum(solution ** 2) / 4000
        prod_cos_term = np.prod(np.cos(solution / np.sqrt(np.arange(1, d + 1))))
        return 1 + sum_sq_term - prod_cos_term