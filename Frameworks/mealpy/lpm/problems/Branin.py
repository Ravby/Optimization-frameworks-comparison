from mealpy import Problem
import numpy as np


class Branin(Problem):
    def __init__(self, lb, ub, minmax, name="Branin", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        a = 1
        b = 5.1 / (4 * np.pi ** 2)
        c = 5 / np.pi
        r = 6
        s = 10
        t = 1 / (8 * np.pi)

        x1 = solution[0]
        x2 = solution[1]

        term1 = a * (x2 - b * x1 ** 2 + c * x1 - r) ** 2
        term2 = s * (1 - t) * np.cos(x1)
        term3 = s

        return term1 + term2 + term3
