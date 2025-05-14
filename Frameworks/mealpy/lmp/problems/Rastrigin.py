from mealpy import Problem
import numpy as np


class Rastrigin(Problem):
    def __init__(self, bounds=None, minmax="min", name="Rastrigin", **kwargs):
        super().__init__(bounds, minmax, **kwargs)
        self.name = name

    def obj_func(self, solution):
        d = solution.size
        A = 10
        return A * d + np.sum(solution ** 2 - A * np.cos(2 * np.pi * solution))
