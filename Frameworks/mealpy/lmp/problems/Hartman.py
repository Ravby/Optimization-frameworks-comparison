from mealpy import Problem
import numpy as np


class Hartman(Problem):
    def __init__(self, bounds=None, minmax="min", name="Hartman", **kwargs):
        super().__init__(bounds, minmax, **kwargs)
        self.name = name

    def obj_func(self, solution):
        alpha = [1.0, 1.2, 3.0, 3.2]
        A = np.array([[3.0, 10, 30],
                      [0.1, 10, 35],
                      [3.0, 10, 30],
                      [0.1, 10, 35]])
        P = 0.0001 * np.array([[3689, 1170, 2673],
                               [4699, 4387, 7470],
                               [1091, 8732, 5547],
                               [381, 5743, 8828]])
        res = 0.0
        for i in range(4):
            inner = np.sum(A[i] * (solution - P[i]) ** 2)
            res -= alpha[i] * np.exp(-inner)
        return res
