from mealpy import Problem
import numpy as np


class Sphere(Problem):
    def __init__(self, lb, ub, minmax, name="Sphere", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        return np.sum(solution ** 2)
