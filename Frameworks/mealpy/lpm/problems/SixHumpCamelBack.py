from mealpy import Problem
import numpy as np


class SixHumpCamelBack(Problem):
    def __init__(self, lb, ub, minmax, name="SixHumpCamelBack", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        return (4 - 2.1 * solution[0] ** 2 + (solution[0] ** 4) / 3) * solution[0] ** 2 + solution[0] * solution[1] + (-4 + 4 * solution[1] ** 2) * solution[1] ** 2
