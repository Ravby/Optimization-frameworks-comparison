import numpy as np

from niapy.problems import Problem


class Branin(Problem):
    def __init__(self, dimension, lower=np.array([-5, 0]), upper=np.array([10, 15]), *args, **kwargs):
        super().__init__(dimension, lower, upper, *args, **kwargs)

    def _evaluate(self, x):
        a = 1
        b = 5.1 / (4 * np.pi ** 2)
        c = 5 / np.pi
        r = 6
        s = 10
        t = 1 / (8 * np.pi)

        x1 = x[0]
        x2 = x[1]

        term1 = a * (x2 - b * x1 ** 2 + c * x1 - r) ** 2
        term2 = s * (1 - t) * np.cos(x1)
        term3 = s

        return term1 + term2 + term3
