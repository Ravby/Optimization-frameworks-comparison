from mealpy import Problem
import math
import numpy as np


class Schwefel12(Problem):
    def __init__(self, bounds=None, minmax="min", name="Schwefel", **kwargs):
        super().__init__(bounds, minmax, **kwargs)
        self.name = name

    def obj_func(self, solution):
        return np.sum([np.sum(solution[:i]) ** 2
                   for i in range(len(solution))])
        #d = solution.size
        #fitnes = 0
        #for i in range(d):
        #    sum = 0
        #    for  j in range(i):
        #        sum += solution[j]
        #    fitnes += sum ** 2
        #return fitnes
