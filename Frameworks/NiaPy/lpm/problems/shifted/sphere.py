# encoding=utf8

"""Sphere problems."""

import numpy as np

from lpm.problems import LogExecution
from niapy.problems.problem import Problem

__all__ = ['ShiftedSphere', 'Sphere2', 'Sphere3']


class ShiftedSphere(Problem):
    r"""Implementation of Sphere functions.

    Date: 2018

    Authors: Iztok Fister Jr.

    License: MIT

    Function: **Sphere function**

        :math:`f(\mathbf{x}) = \sum_{i=1}^D x_i^2`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [0, 10]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:** :math:`f(x^*) = 0`, at :math:`x^* = (0,...,0)`

    LaTeX formats:
        Inline:
            $f(\mathbf{x}) = \sum_{i=1}^D x_i^2$

        Equation:
            \begin{equation}f(\mathbf{x}) = \sum_{i=1}^D x_i^2 \end{equation}

        Domain:
            $0 \leq x_i \leq 10$

    Reference paper:
        Jamil, M., and Yang, X. S. (2013).
        A literature survey of benchmark functions for global optimisation problems.
        International Journal of Mathematical Modelling and Numerical Optimisation,
        4(2), 150-194.

    """

    def __init__(self, dimension=4, lower=-5.12, upper=5.12, *args, **kwargs):
        r"""Initialize Sphere problem..

        Args:
            dimension (Optional[int]): Dimension of the problem.
            lower (Optional[Union[float, Iterable[float]]]): Lower bounds of the problem.
            upper (Optional[Union[float, Iterable[float]]]): Upper bounds of the problem.

        See Also:
            :func:`niapy.problems.Problem.__init__`

        """
        super().__init__(dimension, lower, upper, *args, **kwargs)
        self.shift = np.array([
            45.00458616074768, -50.34195790924958, 16.656571994777153, -70.66157384610746, 77.87240204480926,
            41.15636478376551, 49.128266155105166, -60.307473748449034, 33.10423742373693, 0.6236286355758835,
            27.460586290936902, -36.02013445020688, -40.00877054048374, -64.66271141874185, 68.90566619452034,
            62.70237277088032, -4.074486986783384, -1.317873518733137, -55.54285608300457, -47.34319489184719,
            12.507912147062243, -40.535720288582304, 33.31036155896686, -28.838912135698926, -1.4693369446360407,
            -10.418532078512769, 73.36998380030019, -21.21493655887511, 44.00620956459555, 53.63261051536182,
            2.7285937751837395, 13.470863898732262, 18.406813289986204, 79.81576486014623, 11.44319844208276,
            54.93447134455431, 57.30432717171027, 40.23921236138773, 65.67885067903487, 26.366481706628406,
            -27.30759152512293, 25.811669049736366, 33.765769465674026, 46.064188205488364, 68.68320146596031,
            -30.14247692338735, -20.27099815222924, 72.28995446564878, -23.225596585148693, -17.707777462132427,
            -16.533526290183076, 52.71601762751186, -34.19215602995044, 17.794220792755056, 62.28063781336567,
            48.17997449949212, -62.87666397080855, -0.3946724203053975, -63.61229513391514, 62.025414757794124,
            -53.919013466682095, 22.71650382909786, -48.50198607380415, 39.16369296912181, -33.30665184747603,
            69.78065114460998, 44.39743053916996, -79.65007815070732, 4.313169443398394, -38.431776573060716,
            -3.2886541591535376, -4.464771381506893, -11.851966954379932, 53.314696580174, -47.80425745244058,
            -28.361356211321436, 46.48058986649623, 24.913829708880712, 32.701013472334154, -16.81491036111501,
            -74.32158198349097, 62.781854475534715, -39.33464299745868, 71.914280371025, 43.45306457719519,
            45.34911724499962, 17.635557921803695, 42.528929208317535, 57.38289380412539, -59.172599789657966,
            68.02685304924859, -22.83947203810746, 61.909059219612146, -25.281987529689864, -39.32991901928908,
            21.52408688800091, 19.141746734135268, 33.404708887073156, -40.93749669488551, -24.985279381108313
        ])

    @staticmethod
    def latex_code():
        r"""Return the latex code of the problem.

        Returns:
            str: Latex code.

        """
        return r'''$f(\mathbf{x}) = \sum_{i=1}^D x_i^2$'''

    @LogExecution
    def _evaluate(self, x):
        shifted_x = x - self.shift[:len(x)]
        return np.sum(shifted_x ** 2)


class Sphere2(Problem):
    r"""Implementation of Sphere with different powers function.

    Date: 2018

    Authors: Klemen Berkovič

    License: MIT

    Function: **Sun of different powers function**

        :math:`f(\textbf{x}) = \sum_{i = 1}^D \lvert x_i \rvert^{i + 1}`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [-1, 1]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:** :math:`f(x^*) = 0`, at :math:`x^* = (0,...,0)`

    LaTeX formats:
        Inline:
            $f(\textbf{x}) = \sum_{i = 1}^D \lvert x_i \rvert^{i + 1}$

        Equation:
            \begin{equation} f(\textbf{x}) = \sum_{i = 1}^D \lvert x_i \rvert^{i + 1} \end{equation}

        Domain:
            $-1 \leq x_i \leq 1$

    Reference URL:
        https://www.sfu.ca/~ssurjano/sumpow.html

    """

    def __init__(self, dimension=4, lower=-1.0, upper=1.0, *args, **kwargs):
        r"""Initialize Sphere2 problem..

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
        return r'''$f(\textbf{x}) = \sum_{i = 1}^D \lvert x_i \rvert^{i + 1}$'''

    def _evaluate(self, x):
        indices = np.arange(2, self.dimension + 2)
        return np.sum(np.power(np.abs(x), indices))


class Sphere3(Problem):
    r"""Implementation of rotated hyper-ellipsoid function.

    Date: 2018

    Authors: Klemen Berkovič

    License: MIT

    Function: **Sun of rotated hyper-ellipsoid function**

        :math:`f(\textbf{x}) = \sum_{i = 1}^D \sum_{j = 1}^i x_j^2`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [-65.536, 65.536]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:** :math:`f(x^*) = 0`, at :math:`x^* = (0,...,0)`

    LaTeX formats:
        Inline:
            $f(\textbf{x}) = \sum_{i = 1}^D \sum_{j = 1}^i x_j^2$

        Equation:
            \begin{equation} f(\textbf{x}) = \sum_{i = 1}^D \sum_{j = 1}^i x_j^2 \end{equation}

        Domain:
            $-65.536 \leq x_i \leq 65.536$

    Reference URL:
        https://www.sfu.ca/~ssurjano/rothyp.html

    """

    def __init__(self, dimension=4, lower=-65.536, upper=65.536, *args, **kwargs):
        r"""Initialize Sphere3 problem..

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
        return r'''$f(\textbf{x}) = \sum_{i = 1}^D \sum_{j = 1}^i x_j^2$'''

    def _evaluate(self, x):
        x_matrix = np.tile(x, (self.dimension, 1))
        val = np.sum(np.tril(x_matrix) ** 2.0, axis=0)
        return np.sum(val)
