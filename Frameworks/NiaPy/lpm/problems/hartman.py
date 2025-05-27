import numpy as np

from lpm.problems import LogExecution
from niapy.problems import Problem


class Hartman(Problem):
    def __init__(self, dimension, lower=0, upper=1, *args, **kwargs):
        super().__init__(dimension, lower, upper, *args, **kwargs)

    @LogExecution
    def _evaluate(self, x):
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
            inner = np.sum(A[i] * (x - P[i]) ** 2)
            res -= alpha[i] * np.exp(-inner)
        return res
