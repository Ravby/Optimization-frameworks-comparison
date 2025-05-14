from mealpy import Problem
import numpy as np


class Sphere(Problem):
    def __init__(self, bounds=None, minmax="min", name="Sphere", **kwargs):
        super().__init__(bounds, minmax, **kwargs)
        self.name = name

    def obj_func(self, solution):
        return np.sum(solution ** 2)
