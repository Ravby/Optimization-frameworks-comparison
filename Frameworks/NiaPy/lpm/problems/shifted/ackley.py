# encoding=utf8

"""Implementation of Ackley problem."""

import numpy as np

from lpm.problems import LogExecution
from niapy.problems.problem import Problem

__all__ = ['ShiftedAckley']


class ShiftedAckley(Problem):
    r"""Implementation of Ackley function.

    Date: 2018

    Author: Lucija Brezočnik

    License: MIT

    Function: **Ackley function**

        :math:`f(\mathbf{x}) = -a\;\exp\left(-b \sqrt{\frac{1}{D}\sum_{i=1}^D x_i^2}\right)
        - \exp\left(\frac{1}{D}\sum_{i=1}^D \cos(c\;x_i)\right) + a + \exp(1)`

        **Input domain:**
        The function can be defined on any input domain but it is usually
        evaluated on the hypercube :math:`x_i ∈ [-32.768, 32.768]`, for all :math:`i = 1, 2,..., D`.

        **Global minimum:** :math:`f(\textbf{x}^*) = 0`, at  :math:`x^* = (0,...,0)`

    LaTeX formats:
        Inline:
            $f(\mathbf{x}) = -a\;\exp\left(-b \sqrt{\frac{1}{D}
            \sum_{i=1}^D x_i^2}\right) - \exp\left(\frac{1}{D}
            \sum_{i=1}^D cos(c\;x_i)\right) + a + \exp(1)$

        Equation:
            \begin{equation}f(\mathbf{x}) =
            -a\;\exp\left(-b \sqrt{\frac{1}{D} \sum_{i=1}^D x_i^2}\right) -
            \exp\left(\frac{1}{D} \sum_{i=1}^D \cos(c\;x_i)\right) +
            a + \exp(1) \end{equation}

        Domain:
            $-32.768 \leq x_i \leq 32.768$

    Reference:
        https://www.sfu.ca/~ssurjano/ackley.html

    """

    def __init__(self, dimension=4, lower=-32.768, upper=32.768, a=20.0, b=0.2, c=2 * np.pi, *args, **kwargs):
        r"""Initialize Ackley problem.

        Args:
            dimension (Optional[int]): Dimension of the problem.
            lower (Optional[Union[float, Iterable[float]]]): Lower bounds of the problem.
            upper (Optional[Union[float, Iterable[float]]]): Upper bounds of the problem.
            a (Optional[float]): a parameter.
            b (Optional[float]): b parameter.
            c (Optional[float]): c parameter.

        See Also:
            :func:`niapy.problems.Problem.__init__`

        """
        super().__init__(dimension, lower, upper, *args, **kwargs)
        self.a = a
        self.b = b
        self.c = c
        self.shift = np.array([
            21.125524221556333, -11.11861062406522, -8.384759722218377, -4.998410532366073, 15.080716791129063,
            17.219200462474568, 17.30816180946608, 10.917710600242835, 20.963400683396372, 19.33124762324178,
            -10.07785283476705, -0.6470251048379687, -9.17403575481461, 0.2263125149343992, 3.5360559029458436,
            -24.976221736741277, 5.4509130845083185, -22.008998257857435, 4.319939429353628, -0.015227633728962076,
            -10.266583117363774, -2.984262944040246, -14.575704508621596, 9.332038871399178, 12.260212736282845,
            2.6286817951067007, 3.7062873124753715, -7.422751349625646, 22.839645124520274, 18.139957746273467,
            -23.12538796733297, 3.106978031277709, -21.8650793129579, 23.285704349592457, -8.877654886311145,
            -15.936478675865914, -14.529257223964459, -0.5256354355698463, 3.421693809849465, -22.925462999946205,
            -13.789171558857868, -12.260934479396866, 0.8518120279262078, -6.2876202233673375, 17.750101847964885,
            24.91013310009094, -3.1246259173628452, -7.631501678844927, -7.124798758640981, 0.9420940535559126,
            -3.5298708787570163, -13.655076475547936, -3.220475098055129, -0.7478333903264627, 13.62921655558997,
            -3.336289145576181, -1.7729883120516092, -2.487660518823951, 13.831869546225832, 3.8314346375240227,
            -17.39761276709943, 10.865095694434025, 15.228433667840704, -16.627545155754365, -20.431272665369136,
            -6.458592974832296, 7.631660014538554, 3.074842377520376, -10.126681052067994, 5.3638689257400145,
            21.993909890455164, 9.497579963476142, -25.38346714493473, 23.309384518115635, 3.51234224076034,
            22.789117409253414, 17.335098841147875, 16.853847369236334, -22.711831662040623, -21.43493872394238,
            14.12580762325981, 12.670980727215792, -0.18261513276357988, -2.756069406807832, -13.440337358321415,
            -7.500233746130899, -22.829280727381548, 22.39647155450566, -9.793500872001813, -11.480708343814184,
            -11.497396176336123, -17.84442861516022, 16.259172390942574, -4.7388363410613366, -22.313184156325793,
            -16.909573209790985, 21.735083164944925, -13.918617730409967, -21.395853820675416, -22.481403053600452
        ])

    @staticmethod
    def latex_code():
        r"""Return the latex code of the problem.

        Returns:
            str: Latex code.

        """
        return r'''$f(\mathbf{x}) = -a\;\exp\left(-b \sqrt{\frac{1}{D}
                \sum_{i=1}^D x_i^2}\right) - \exp\left(\frac{1}{D}
                \sum_{i=1}^D \cos(c\;x_i)\right) + a + \exp(1)$'''

    @LogExecution
    def _evaluate(self, x):
        shifted_x = x - self.shift[:len(x)]
        val1 = np.sum(np.square(shifted_x))
        val2 = np.sum(np.cos(self.c * shifted_x))

        temp1 = -self.b * np.sqrt(val1 / self.dimension)
        temp2 = val2 / self.dimension

        return -self.a * np.exp(temp1) - np.exp(temp2) + self.a + np.exp(1)
