from niapy.problems import Problem


class GoldsteinPrice(Problem):
    def __init__(self, dimension, lower=-2, upper=2, *args, **kwargs):
        super().__init__(dimension, lower, upper, *args, **kwargs)

    def _evaluate(self, x):
        term1 = (1 + ((x[0] + x[1] + 1) ** 2) * (
                    19 - 14 * x[0] + 3 * x[0] ** 2 - 14 * x[1] + 6 * x[0] * x[1] + 3 * x[1] ** 2))
        term2 = (30 + ((2 * x[0] - 3 * x[1]) ** 2) * (
                    18 - 32 * x[0] + 12 * x[0] ** 2 + 48 * x[1] - 36 * x[0] * x[1] + 27 * x[1] ** 2))
        return term1 * term2
