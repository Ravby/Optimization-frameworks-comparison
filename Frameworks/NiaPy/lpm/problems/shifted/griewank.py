# encoding=utf8

"""Implementation of Griewank funcion."""

import numpy as np

from niapy.problems.problem import Problem

__all__ = ['ShiftedGriewank', 'ExpandedGriewankPlusRosenbrock']


class ShiftedGriewank(Problem):
    r"""Implementation of Griewank function.

    Date: 2018

    Authors: Iztok Fister Jr. and Lucija Brezočnik

    License: MIT

    Function: **Griewank function**

        :math:`f(\mathbf{x}) = \sum_{i=1}^D \frac{x_i^2}{4000} - \prod_{i=1}^D \cos(\frac{x_i}{\sqrt{i}}) + 1`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [-100, 100]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:** :math:`f(x^*) = 0`, at :math:`x^* = (0,...,0)`

    LaTeX formats:
        Inline:
            $f(\mathbf{x}) = \sum_{i=1}^D \frac{x_i^2}{4000} -
            \prod_{i=1}^D \cos(\frac{x_i}{\sqrt{i}}) + 1$

        Equation:
            \begin{equation} f(\mathbf{x}) = \sum_{i=1}^D \frac{x_i^2}{4000} -
            \prod_{i=1}^D \cos(\frac{x_i}{\sqrt{i}}) + 1 \end{equation}

        Domain:
            $-100 \leq x_i \leq 100$

    Reference paper:
    Jamil, M., and Yang, X. S. (2013).
    A literature survey of benchmark functions for global optimisation problems.
    International Journal of Mathematical Modelling and Numerical Optimisation,
    4(2), 150-194.

    """

    def __init__(self, dimension=4, lower=-100.0, upper=100.0, *args, **kwargs):
        r"""Initialize Griewank problem..

        Args:
            dimension (Optional[int]): Dimension of the problem.
            lower (Optional[Union[float, Iterable[float]]]): Lower bound of the problem.
            upper (Optional[Union[float, Iterable[float]]]): Upper bound of the problem.

        See Also:
            :func:`niapy.problems.Problem.__init__`

        """
        super().__init__(dimension, lower, upper, *args, **kwargs)
        self.shift = np.array([
            104.94691081342978, -135.6761348470227, 92.81100891955543, -10.87108518188711, 407.63013054172563,
            -223.31315231746964, -224.56627659185563, -222.7632371161696, -338.7309010063917, -236.65660889064418,
            -102.44810336184622, 126.42998956660733, 145.51932139597716, 362.4195007946686, -136.07822974158148,
            385.3787391034516, 224.54916653990915, 43.478124239142176, 174.10299590196337, -214.8310656032395,
            -398.01422639093084, -309.2614310193993, -178.06570153291875, -162.6651823263993, 450.850657541738,
            -454.92932203096916, -419.61768171271166, -297.80826342133184, 66.9431726795051, 81.54612611060406,
            33.75350391540678, 452.7503721088576, -394.37679317004665, 211.96497077719948, 209.2762825707889,
            -456.2796841185877, 43.786866583660526, 39.94139518856173, -392.0757065399322, 368.3834199650955,
            -230.1695108177757, 260.9386598025741, 424.9982235544909, 219.5539088594145, -56.74195565523951,
            353.31823225966764, -314.409074063865, -344.22129416136335, 350.389300480917, 210.6305580593255,
            -351.3688705488921, -419.56080494622336, -352.4149162964608, -306.0148711883166, 234.1092941008617,
            179.7062426106279, -180.45299833941465, -353.7895714765393, 304.63535113945875, 11.040575864524044,
            409.40525097198395, -417.0405885951272, 460.81384815623824, 476.25681692986484, 248.03325986310347,
            126.13612920930825, 366.9901574947629, 40.28445536257732, 196.48680138749523, -89.56987522823874,
            -31.05376733595574, -254.8501756921979, 341.9328159488531, -366.2937316074139, -98.73389669853748,
            -318.98905799691175, -152.38232167124994, 367.26470889921757, -72.94267032929105, 372.16601829879653,
            96.98492379728134, -385.9422293412532, -94.28273010579477, 346.5708159613322, -139.0701306975041,
            -62.71021784084701, -477.67009305148014, 267.4589914695547, 135.44409046482406, -107.786912911326,
            12.944227804931472, 452.8144059395564, -2.2036047536961973, -458.0828323445677, -305.00083158848463,
            352.90943721979033, -173.23435776482415, -294.46059074937193, 329.82221519045083, 295.09636446279023
        ])

    @staticmethod
    def latex_code():
        r"""Return the latex code of the problem.

        Returns:
            str: Latex code.

        """
        return r'''$f(\mathbf{x}) = \sum_{i=1}^D \frac{x_i^2}{4000} - \prod_{i=1}^D \cos(\frac{x_i}{\sqrt{i}}) + 1$'''

    def _evaluate(self, x):
        shifted_x = x - self.shift[:len(x)]
        val1 = np.sum(shifted_x * shifted_x / 4000.0)
        i = np.arange(1, self.dimension + 1)
        val2 = np.product(np.cos(shifted_x / np.sqrt(i)))
        return val1 - val2 + 1.0


class ExpandedGriewankPlusRosenbrock(Problem):
    r"""Implementation of Expanded Griewank's plus Rosenbrock function.

    Date: 2018

    Author: Klemen Berkovič

    License: MIT

    Function: **Expanded Griewank's plus Rosenbrock function**

        :math:`f(\textbf{x}) = h(g(x_D, x_1)) + \sum_{i=2}^D h(g(x_{i - 1}, x_i)) \\ g(x, y) = 100 (x^2 - y)^2 + (x - 1)^2 \\ h(z) = \frac{z^2}{4000} - \cos \left( \frac{z}{\sqrt{1}} \right) + 1`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [-100, 100]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:**
        :math:`f(x^*) = 0`, at :math:`x^* = (420.968746,...,420.968746)`

    LaTeX formats:
        Inline:
            $f(\textbf{x}) = h(g(x_D, x_1)) + \sum_{i=2}^D h(g(x_{i - 1}, x_i)) \\ g(x, y) = 100 (x^2 - y)^2 + (x - 1)^2 \\ h(z) = \frac{z^2}{4000} - \cos \left( \frac{z}{\sqrt{1}} \right) + 1$

        Equation:
            \begin{equation} f(\textbf{x}) = h(g(x_D, x_1)) + \sum_{i=2}^D h(g(x_{i - 1}, x_i)) \\ g(x, y) = 100 (x^2 - y)^2 + (x - 1)^2 \\ h(z) = \frac{z^2}{4000} - \cos \left( \frac{z}{\sqrt{1}} \right) + 1 \end{equation}

        Domain:
            $-100 \leq x_i \leq 100$

    Reference:
        http://www5.zzu.edu.cn/__local/A/69/BC/D3B5DFE94CD2574B38AD7CD1D12_C802DAFE_BC0C0.pdf

    """

    def __init__(self, dimension=4, lower=-100.0, upper=100.0, *args, **kwargs):
        r"""Initialize Expanded Griewank's plus Rosenbrock problem..

        Args:
            dimension (Optional[int]): Dimension of the problem.
            lower (Optional[Union[float, Iterable[float]]]): Lower bounds of the problem.
            upper (Optional[Union[float, Iterable[float]]]): Upper bounds of the problem.

        See Also:
            :func:`niapy.problems.Problem.__init__`

        """
        super().__init__(dimension, lower, upper, *args, **kwargs)

    @staticmethod
    def latex_code():
        r"""Return the latex code of the problem.

        Returns:
            str: Latex code.

        """
        return r'''$f(\textbf{x}) = h(g(x_D, x_1)) + \sum_{i=2}^D h(g(x_{i - 1}, x_i)) \\ g(x, y) = 100 (x^2 - y)^2 + (x - 1)^2 \\ h(z) = \frac{z^2}{4000} - \cos \left( \frac{z}{\sqrt{1}} \right) + 1$'''

    def _evaluate(self, x):
        x1 = 100.0 * (x[1:] - x[:-1] ** 2.0) ** 2.0 + (1 - x[:-1]) ** 2.0
        x2 = x1 * x1 / 4000.0 - np.cos(x1 / np.sqrt(np.arange(1, self.dimension)))
        return np.sum(x2)
