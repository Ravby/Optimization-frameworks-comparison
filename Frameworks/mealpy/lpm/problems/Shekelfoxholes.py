from mealpy import Problem
import numpy as np


class Shekelfoxholes(Problem):
    def __init__(self, lb, ub, minmax, name="Shekelfoxholes", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        m = 10
        a = np.array([
            [4, 4, 4, 4, 4, 4, 4, 4, 4, 4],
            [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
            [8, 8, 8, 8, 8, 8, 8, 8, 8, 8],
            [6, 6, 6, 6, 6, 6, 6, 6, 6, 6],
            [3, 7, 3, 7, 3, 7, 3, 7, 3, 7]
        ])
        c = np.array([
            0.1, 0.2, 0.2, 0.4, 0.4, 0.6, 0.3, 0.7, 0.5, 0.5
        ])

        result = 0
        for i in range(m):
            term = 0
            for j in range(solution.shape[0]):
                term += (solution[j] - a[j, i]) ** 2
            result -= 1 / (term + c[i])

        return result
