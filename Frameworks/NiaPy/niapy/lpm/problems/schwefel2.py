from niapy.problems import Problem


class Schwefel2(Problem):
    def __init__(self, dimension, lower=-100, upper=100, *args, **kwargs):
        super().__init__(dimension, lower, upper, *args, **kwargs)

    def _evaluate(self, x):
        fitness = 0
        n = len(x)
        for i in range(n):
            sum = 0
            for j in range(i):
                sum += x[j]
            fitness += sum ** 2
        return fitness
