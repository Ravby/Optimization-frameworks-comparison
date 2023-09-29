from mealpy import Problem
import numpy as np


class Ackley(Problem):
    def __init__(self, lb, ub, minmax, name="Ackley", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        a = 20.0
        b = 0.2
        c = 2 * np.pi
        sum1 = 0
        sum2 = 0
        for i in range(len(solution)):
            sum1 += solution[i] ** 2
            sum2 += np.cos(c * solution[i])
        return -a * np.exp(-b * np.sqrt(1.0 / len(solution) * sum1)) - np.exp(1.0 / len(solution) * sum2) + a + np.e
