from lpm.problems import LogExecution
from niapy.problems import Problem


class ShekelsFoxholes(Problem):
    def __init__(self, dimension, lower=-65536, upper=65536, *args, **kwargs):
        super().__init__(dimension, lower, upper, *args, **kwargs)

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

    @LogExecution
    def _evaluate(self, x):
        fitness = 0.0
        for j in range(25):
            sum = 0.0
            for i in range(len(x)):
                sum += (x[i] - ShekelsFoxholes.a[j][i]) ** 6
            sum += j + 1
            fitness += 1.0 / sum
        fitness += 1.0 / 500.0
        return fitness ** -1
