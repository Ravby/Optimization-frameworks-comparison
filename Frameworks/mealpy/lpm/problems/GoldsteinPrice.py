from mealpy import Problem
import numpy as np


class GoldsteinPrice(Problem):
    def __init__(self, lb, ub, minmax, name="GoldsteinPrice", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        term1 = (1 + ((solution[0] + solution[1] + 1) ** 2) * (
                    19 - 14 * solution[0] + 3 * solution[0] ** 2 - 14 * solution[1] + 6 * solution[0] * solution[1] + 3 * solution[1] ** 2))
        term2 = (30 + ((2 * solution[0] - 3 * solution[1]) ** 2) * (
                    18 - 32 * solution[0] + 12 * solution[0] ** 2 + 48 * solution[1] - 36 * solution[0] * solution[1] + 27 * solution[1] ** 2))
        return term1 * term2
