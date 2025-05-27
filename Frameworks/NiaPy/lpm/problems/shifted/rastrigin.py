# encoding=utf8

"""Implementation of Rastrigin function."""

import numpy as np

from lpm.problems import LogExecution
from niapy.problems.problem import Problem

__all__ = ['ShiftedRastrigin']


class ShiftedRastrigin(Problem):
    r"""Implementation of Rastrigin problem.

    Date: 2018

    Authors: Lucija Brezočnik and Iztok Fister Jr.

    License: MIT

    Function: **Rastrigin function**

        :math:`f(\mathbf{x}) = 10D + \sum_{i=1}^D \left(x_i^2 -10\cos(2\pi x_i)\right)`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [-5.12, 5.12]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:** :math:`f(x^*) = 0`, at :math:`x^* = (0,...,0)`

    LaTeX formats:
        Inline:
            $f(\mathbf{x}) = 10D + \sum_{i=1}^D \left(x_i^2 -10\cos(2\pi x_i)\right)$

        Equation:
            \begin{equation} f(\mathbf{x}) =
            10D + \sum_{i=1}^D \left(x_i^2 -10\cos(2\pi x_i)\right)
            \end{equation}

        Domain:
            $-5.12 \leq x_i \leq 5.12$

    Reference:
        https://www.sfu.ca/~ssurjano/rastr.html

    """

    def __init__(self, dimension=4, lower=-5.12, upper=5.12, *args, **kwargs):
        r"""Initialize Rastrigin problem..

        Args:
            dimension (Optional[int]): Dimension of the problem.
            lower (Optional[Union[float, Iterable[float]]]): Lower bounds of the problem.
            upper (Optional[Union[float, Iterable[float]]]): Upper bounds of the problem.

        See Also:
            :func:`niapy.problems.Problem.__init__`

        """
        super().__init__(dimension, lower, upper, *args, **kwargs)
        self.shift = np.array([
            4.070088073870897, -3.3584914395112166, 2.5276924556298725, 1.373781594176445, -1.8387129521132595,
            2.057549949794417, -0.0023364913111585395, 2.013487904123477, -3.7248964059839147, -0.33919629789907413,
            2.8301821463273367, -2.2929959706368352, -2.2706325455604484, -3.513692538288365, -1.523336663056385,
            -4.0764777207287075, -1.6237586256460155, 0.9178097684770039, -0.2723487256238868, -2.9841270159498463,
            2.9288432571682828, 0.22934413055341984, 3.720378352924264, -1.429905611686805, 1.6068438224463835,
            2.1513735804078467, -0.6971225700819095, -2.8180867567781878, -1.2171333029086893, -4.064563849878518,
            1.3543350787859474, 2.4050645503835417, -2.5584324886855985, 1.5660383241939355, 2.1289004812453385,
            3.8337296275949706, -0.5606593824135673, 1.141577243686811, 0.8120054159429309, 0.5394159295625087,
            -2.5598649074502093, 4.033201745873486, 3.24227861085845, 2.916933432390337, 3.639752419533484,
            1.5188619717725969, 3.839178330380485, 3.975865667365813, -1.191858979126291, -3.489747339689812,
            3.6319133591436685, -0.8694397826397151, -0.039238131176527524, 0.857258173067124, 3.5742721242496724,
            -1.2964486629139182, 0.622681353542129, -3.2417846861744932, -3.5847655715322952, -0.20674408185538518,
            -1.4176918157719318, -1.8093890874563168, -2.4458826348346667, -2.4951135809966636, -0.3701415900112224,
            -0.9412743172996478, -2.5093000736061777, -0.957089975451122, -0.34490389779906305, -0.9080868663263582,
            2.5324398853872463, 0.7681006679604776, 0.0686178202466019, 3.3249963877119377, -1.7808876498791788,
            -2.765311233368114, -4.021295100468257, -3.0287066679231973, 1.1237366969449676, 2.593350377277937,
            -2.882030611770738, -1.0382816516673712, -2.7461834729291623, -3.886857036748543, 2.3862007049941916,
            1.7060737084162199, 3.0561050423140896, 2.7412547729688193, 3.6348483498236694, 2.0446951025524065,
            -2.1226445447582245, 2.2055941964197308, -1.881997196954174, -2.0742308641271237, -4.0625624578635255,
            -3.0987340790486426, 2.5444665506465656, -1.6879107868549954, -2.299820194854612, -0.6872363415421097
        ])

    @staticmethod
    def latex_code():
        r"""Return the latex code of the problem.

        Returns:
            str: Latex code.

        """
        return r'''$f(\mathbf{x}) = 10D + \sum_{i=1}^D \left(x_i^2 -10\cos(2\pi x_i)\right)$'''

    @LogExecution
    def _evaluate(self, x):
        shifted_x = x - self.shift[:len(x)]
        return 10.0 * self.dimension + np.sum(shifted_x * shifted_x - 10.0 * np.cos(2 * np.pi * shifted_x))
