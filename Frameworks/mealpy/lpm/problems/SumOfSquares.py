from mealpy import Problem
import numpy as np


class SumOfSquares(Problem):
    def __init__(self, lb, ub, minmax, name="SumOfSquares", **kwargs):
        super().__init__(lb, ub, minmax, **kwargs)
        self.name = name

    def fit_func(self, solution):
        sum = 0
        for i, value in enumerate(solution):
            sum += i * (value ** 2)
        return sum
