# encoding=utf8
"""Sum Squares problem."""

import numpy as np
from niapy.problems.problem import Problem

__all__ = ['ShiftedSumSquares']


class ShiftedSumSquares(Problem):
    r"""Implementation of Sum Squares functions.

    Date: 2018

    Authors: Lucija Brezočnik

    License: MIT

    Function: **Sum Squares function**

        :math:`f(\mathbf{x}) = \sum_{i=1}^D i x_i^2`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [-10, 10]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:** :math:`f(x^*) = 0`, at :math:`x^* = (0,...,0)`

    LaTeX formats:
        Inline:
            $f(\mathbf{x}) = \sum_{i=1}^D i x_i^2$

        Equation:
            \begin{equation}f(\mathbf{x}) = \sum_{i=1}^D i x_i^2 \end{equation}

        Domain:
            $0 \leq x_i \leq 10$

    Reference paper:
        Jamil, M., and Yang, X. S. (2013).
        A literature survey of benchmark functions for global optimisation problems.
        International Journal of Mathematical Modelling and Numerical Optimisation,
        4(2), 150-194.

    """

    def __init__(self, dimension=4, lower=-10.0, upper=10.0, *args, **kwargs):
        r"""Initialize Sum Squares problem..

        Args:
            dimension (Optional[int]): Dimension of the problem.
            lower (Optional[Union[float, Iterable[float]]]): Lower bounds of the problem.
            upper (Optional[Union[float, Iterable[float]]]): Upper bounds of the problem.

        See Also:
            :func:`niapy.problems.Problem.__init__`

        """
        super().__init__(dimension, lower, upper, *args, **kwargs)
        self.shift = np.array([
            22.479705250884777, 60.75750080751871, -59.15674324461888, 27.56336400934657, -59.29279403989607,
            -64.68447838216555, 78.33759532500437, -76.51052473538245, -66.6507114149451, 64.93462028133038,
            -15.23828302367869, -17.825823787619903, -31.92405019180454, 67.72646439056987, -0.9830868197666973,
            39.91084384292476, 1.4187967568760484, 24.815734535933146, -3.3313494842577285, 18.8446220852682,
            -17.002497910454828, 50.326561978450655, 53.95001535109091, 18.403170606945253, -21.184841218701195,
            -62.069991345971154, -43.868924783714164, -24.682337470629072, -2.8605529514137373, 65.39162073164252,
            2.6654994832190937, 23.60580815931415, 13.846698992449433, -41.45381813820245, -76.7118669691122,
            40.566665904615746, 22.28454884887448, -55.31708080994516, -76.649068909635, 40.966439161432476,
            -3.0455255815577402, 3.119008384655956, 18.342768255788243, -20.144695942649946, 8.54936385804288,
            50.45305033701291, 23.2263485421784, 10.68869991058412, 74.77290130427247, 33.82014641772588,
            -19.74543928622861, -43.37360584484172, 79.02166733921979, -45.92410252364756, -44.01344981695157,
            44.109743914203506, -77.20689956137252, -72.65296438262618, 46.613878010132, -14.343495951611033,
            25.970325670723298, -2.355868084672778, -72.92020917892613, 68.27133428368728, 60.8093904760039,
            44.33884079839375, 15.978963362849413, 6.74177622913723, 54.68618170146863, 75.90821354473843,
            32.160082912969955, 55.10526379835744, -20.089763211464202, -63.30186719081464, -29.970046051685806,
            -59.730237380251964, -74.06534479168236, -7.848712706118931, -51.60763845461748, -47.8986829918213,
            -46.24410011332074, 68.54056330770274, 41.40086049990089, -45.113643245610234, -53.385722169041614,
            -28.50002393256048, -9.680142709402858, 79.96711109340981, 78.51165296114377, 54.834496959318756,
            -64.63034566544378, -21.84107572223855, 48.37382176099271, -51.04930456902558, -43.1167085334479,
            40.877819320236654, 21.594957137775253, -12.021210965770223, 51.08608568221766, 13.845330603293263
        ])

    @staticmethod
    def latex_code():
        r"""Return the latex code of the problem.

        Returns:
            str: Latex code.

        """
        return r'''$f(\mathbf{x}) = \sum_{i=1}^D i x_i^2$'''

    def _evaluate(self, x):
        shifted_x = x - self.shift[:len(x)]
        return np.sum(np.arange(1, self.dimension + 1) * shifted_x ** 2)
