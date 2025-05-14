from mealpy import Problem
import numpy as np


class Shekelfoxholes(Problem):
    def __init__(self, bounds=None, minmax="min", name="Shekelfoxholes", **kwargs):
        super().__init__(bounds, minmax, **kwargs)
        self.name = name

    a = [[-32, -32],
         [-16, -32],
         [0, -32],
         [16, -32],
         [32, -32],
         [-32, -16],
         [-16, -16],
         [0, -16],
         [16, -16],
         [32, -16],
         [-32, 0],
         [-16, 0],
         [0, 0],
         [16, 0],
         [32, 0],
         [-32, 16],
         [-16, 16],
         [0, 16],
         [16, 16],
         [32, 16],
         [-32, 32],
         [-16, 32],
         [0, 32],
         [16, 32],
         [32, 32]]

    def obj_func(self, solution):
        fitness = 0.0
        for j in range(25):
            sum = 0.0
            for i in range(len(solution)):
                sum += (solution[i] - Shekelfoxholes.a[j][i]) ** 6
            sum += j + 1
            fitness += 1.0 / sum
        fitness += 1.0 / 500.0
        return fitness ** -1
