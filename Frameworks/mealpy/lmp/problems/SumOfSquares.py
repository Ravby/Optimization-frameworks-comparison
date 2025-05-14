from mealpy import Problem
import numpy as np


class SumOfSquares(Problem):
    def __init__(self, bounds=None, minmax="min", name="SumOfSquares", **kwargs):
        super().__init__(bounds, minmax, **kwargs)
        self.name = name

    def obj_func(self, solution):
        sum = 0
        for i, value in enumerate(solution):
            sum += i * (value ** 2)
        return sum
