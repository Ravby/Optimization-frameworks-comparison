from lpm.problems import LogExecution
from niapy.problems import Problem


class SixHumpCamelBack(Problem):
    def __init__(self, dimension, lower=-5, upper=5, *args, **kwargs):
        super().__init__(dimension, lower, upper, *args, **kwargs)

    @LogExecution
    def _evaluate(self, x):
        return (4 - 2.1 * x[0] ** 2 + (x[0] ** 4) / 3) * x[0] ** 2 + x[0] * x[1] + (-4 + 4 * x[1] ** 2) * x[1] ** 2
