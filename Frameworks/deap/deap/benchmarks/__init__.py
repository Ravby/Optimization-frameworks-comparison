#    This file is part of DEAP.
#
#    DEAP is free software: you can redistribute it and/or modify
#    it under the terms of the GNU Lesser General Public License as
#    published by the Free Software Foundation, either version 3 of
#    the License, or (at your option) any later version.
#
#    DEAP is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
#    GNU Lesser General Public License for more details.
#
#    You should have received a copy of the GNU Lesser General Public
#    License along with DEAP. If not, see <http://www.gnu.org/licenses/>.
"""
Regroup typical EC benchmarks functions to import easily and benchmark
examples.
"""

import random
from math import sin, cos, pi, exp, e, sqrt
from operator import mul
from functools import reduce, wraps
import numpy as np
import datetime

# Define the wrapper (decorator) for logging
class LogExecution:
    evaluationsCount = 0
    improvements = []

    def __init__(self, func):
        self.func = func
        wraps(func)(self)

    def __call__(self, *args, **kwargs):
        LogExecution.evaluationsCount += 1

        fitness = self.func(*args, **kwargs)
        if not LogExecution.improvements or fitness < LogExecution.improvements[-1][1]:
            LogExecution.improvements.append((LogExecution.evaluationsCount, fitness))

        return fitness

    @staticmethod
    def reset():
        LogExecution.evaluationsCount = 0
        LogExecution.improvements = []

    @staticmethod
    def write_improvements_to_file(filename):
        with open(filename, 'w') as f:
            f.write(f"Evaluations,Fitness\n")
            for eval_num, fitness in LogExecution.improvements:
                f.write(f"{eval_num},{fitness[0]}\n")
        LogExecution.reset()

# Unimodal
def rand(individual):
    r"""Random test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization or maximization
       * - Range
         - none
       * - Global optima
         - none
       * - Function
         - :math:`f(\mathbf{x}) = \text{\texttt{random}}(0,1)`
    """
    return random.random(),


def plane(individual):
    r"""Plane test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - none
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = x_0`
    """
    return individual[0],


@LogExecution
def sphere(individual):
    r"""Sphere test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - none
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = \sum_{i=1}^Nx_i^2`
    """
    return sum(gene * gene for gene in individual),


@LogExecution
def sphereShifted(individual):
    r"""Sphere test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - none
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = \sum_{i=1}^Nx_i^2`
    """

    shift = np.array([45.00458616074768, -50.34195790924958, 16.656571994777153, -70.66157384610746, 77.87240204480926,
                      41.15636478376551, 49.128266155105166, -60.307473748449034, 33.10423742373693, 0.6236286355758835,
                      27.460586290936902, -36.02013445020688, -40.00877054048374, -64.66271141874185, 68.90566619452034,
                      62.70237277088032, -4.074486986783384, -1.317873518733137, -55.54285608300457, -47.34319489184719,
                      12.507912147062243, -40.535720288582304, 33.31036155896686, -28.838912135698926,
                      -1.4693369446360407,
                      -10.418532078512769, 73.36998380030019, -21.21493655887511, 44.00620956459555, 53.63261051536182,
                      2.7285937751837395, 13.470863898732262, 18.406813289986204, 79.81576486014623, 11.44319844208276,
                      54.93447134455431, 57.30432717171027, 40.23921236138773, 65.67885067903487, 26.366481706628406,
                      -27.30759152512293, 25.811669049736366, 33.765769465674026, 46.064188205488364, 68.68320146596031,
                      -30.14247692338735, -20.27099815222924, 72.28995446564878, -23.225596585148693,
                      -17.707777462132427,
                      -16.533526290183076, 52.71601762751186, -34.19215602995044, 17.794220792755056, 62.28063781336567,
                      48.17997449949212, -62.87666397080855, -0.3946724203053975, -63.61229513391514,
                      62.025414757794124])

    shifted_individual = np.array(individual) - shift

    return sum(gene * gene for gene in shifted_individual),


def cigar(individual):
    r"""Cigar test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - none
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = x_0^2 + 10^6\sum_{i=1}^N\,x_i^2`
    """
    return individual[0] ** 2 + 1e6 * sum(gene * gene for gene in individual[1:]),

@LogExecution
def rosenbrock(individual):
    r"""Rosenbrock test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - none
       * - Global optima
         - :math:`x_i = 1, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = \sum_{i=1}^{N-1} (1-x_i)^2 + 100 (x_{i+1} - x_i^2 )^2`

    .. plot:: code/benchmarks/rosenbrock.py
       :width: 67 %
    """
    return sum(100 * (x * x - y) ** 2 + (1. - x) ** 2 \
               for x, y in zip(individual[:-1], individual[1:])),


def h1(individual):
    r""" Simple two-dimensional function containing several local maxima.
    From: The Merits of a Parallel Genetic Algorithm in Solving Hard
    Optimization Problems, A. J. Knoek van Soest and L. J. R. Richard
    Casius, J. Biomech. Eng. 125, 141 (2003)

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - maximization
       * - Range
         - :math:`x_i \in [-100, 100]`
       * - Global optima
         - :math:`\mathbf{x} = (8.6998, 6.7665)`, :math:`f(\mathbf{x}) = 2`\n
       * - Function
         - :math:`f(\mathbf{x}) = \frac{\sin(x_1 - \frac{x_2}{8})^2 + \
            \sin(x_2 + \frac{x_1}{8})^2}{\sqrt{(x_1 - 8.6998)^2 + \
            (x_2 - 6.7665)^2} + 1}`

    .. plot:: code/benchmarks/h1.py
       :width: 67 %
    """
    num = (sin(individual[0] - individual[1] / 8)) ** 2 + (sin(individual[1] + individual[0] / 8)) ** 2
    denum = ((individual[0] - 8.6998) ** 2 + (individual[1] - 6.7665) ** 2) ** 0.5 + 1
    return num / denum,


#
# Multimodal
@LogExecution
def ackley(individual):
    r"""Ackley test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - :math:`x_i \in [-15, 30]`
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = 20 - 20\exp\left(-0.2\sqrt{\frac{1}{N} \
            \sum_{i=1}^N x_i^2} \right) + e - \exp\left(\frac{1}{N}\sum_{i=1}^N \cos(2\pi x_i) \right)`

    .. plot:: code/benchmarks/ackley.py
       :width: 67 %
    """
    N = len(individual)
    return 20 - 20 * exp(-0.2 * sqrt(1.0 / N * sum(x ** 2 for x in individual))) \
           + e - exp(1.0 / N * sum(cos(2 * pi * x) for x in individual)),

@LogExecution
def ackleyShifted(individual):
    shift = np.array(
        [21.125524221556333, -11.11861062406522, -8.384759722218377, -4.998410532366073, 15.080716791129063,
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
         -3.336289145576181, -1.7729883120516092, -2.487660518823951, 13.831869546225832, 3.8314346375240227])

    shifted_individual = np.array(individual) - shift

    N = len(shifted_individual)
    return 20 - 20 * exp(-0.2 * sqrt(1.0 / N * sum(x ** 2 for x in shifted_individual))) \
           + e - exp(1.0 / N * sum(cos(2 * pi * x) for x in shifted_individual)),


def bohachevsky(individual):
    r"""Bohachevsky test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - :math:`x_i \in [-100, 100]`
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         -  :math:`f(\mathbf{x}) = \sum_{i=1}^{N-1}(x_i^2 + 2x_{i+1}^2 - \
                   0.3\cos(3\pi x_i) - 0.4\cos(4\pi x_{i+1}) + 0.7)`

    .. plot:: code/benchmarks/bohachevsky.py
       :width: 67 %
    """
    return sum(x ** 2 + 2 * x1 ** 2 - 0.3 * cos(3 * pi * x) - 0.4 * cos(4 * pi * x1) + 0.7
               for x, x1 in zip(individual[:-1], individual[1:])),

@LogExecution
def griewank(individual):
    r"""Griewank test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - :math:`x_i \in [-600, 600]`
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = \frac{1}{4000}\sum_{i=1}^N\,x_i^2 - \
                  \prod_{i=1}^N\cos\left(\frac{x_i}{\sqrt{i}}\right) + 1`

    .. plot:: code/benchmarks/griewank.py
       :width: 67 %
    """
    return 1.0 / 4000.0 * sum(x ** 2 for x in individual) - \
           reduce(mul, (cos(x / sqrt(i + 1.0)) for i, x in enumerate(individual)), 1) + 1,

@LogExecution
def griewankShifted(individual):
    shift = np.array([104.94691081342978, -135.6761348470227, 92.81100891955543, -10.87108518188711, 407.63013054172563,
                      -223.31315231746964, -224.56627659185563, -222.7632371161696, -338.7309010063917,
                      -236.65660889064418,
                      -102.44810336184622, 126.42998956660733, 145.51932139597716, 362.4195007946686,
                      -136.07822974158148,
                      385.3787391034516, 224.54916653990915, 43.478124239142176, 174.10299590196337, -214.8310656032395,
                      -398.01422639093084, -309.2614310193993, -178.06570153291875, -162.6651823263993,
                      450.850657541738,
                      -454.92932203096916, -419.61768171271166, -297.80826342133184, 66.9431726795051,
                      81.54612611060406,
                      33.75350391540678, 452.7503721088576, -394.37679317004665, 211.96497077719948, 209.2762825707889,
                      -456.2796841185877, 43.786866583660526, 39.94139518856173, -392.0757065399322, 368.3834199650955,
                      -230.1695108177757, 260.9386598025741, 424.9982235544909, 219.5539088594145, -56.74195565523951,
                      353.31823225966764, -314.409074063865, -344.22129416136335, 350.389300480917, 210.6305580593255,
                      -351.3688705488921, -419.56080494622336, -352.4149162964608, -306.0148711883166,
                      234.1092941008617,
                      179.7062426106279, -180.45299833941465, -353.7895714765393, 304.63535113945875,
                      11.040575864524044])

    shifted_individual = np.array(individual) - shift

    return 1.0 / 4000.0 * sum(x ** 2 for x in shifted_individual) - \
           reduce(mul, (cos(x / sqrt(i + 1.0)) for i, x in enumerate(shifted_individual)), 1) + 1,

@LogExecution
def rastrigin(individual):
    r"""Rastrigin test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - :math:`x_i \in [-5.12, 5.12]`
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = 10N + \sum_{i=1}^N x_i^2 - 10 \cos(2\pi x_i)`

    .. plot:: code/benchmarks/rastrigin.py
       :width: 67 %
    """
    return 10 * len(individual) + sum(gene * gene - 10 * \
                                      cos(2 * pi * gene) for gene in individual),

@LogExecution
def rastriginShifted(individual):
    shift = np.array(
        [4.070088073870897, -3.3584914395112166, 2.5276924556298725, 1.373781594176445, -1.8387129521132595,
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
         -1.2964486629139182, 0.622681353542129, -3.2417846861744932, -3.5847655715322952, -0.20674408185538518])

    shifted_individual = np.array(individual) - shift

    return 10 * len(shifted_individual) + sum(gene * gene - 10 * \
                                              cos(2 * pi * gene) for gene in shifted_individual),


def rastrigin_scaled(individual):
    r"""Scaled Rastrigin test objective function.

    :math:`f_{\text{RastScaled}}(\mathbf{x}) = 10N + \sum_{i=1}^N \
        \left(10^{\left(\frac{i-1}{N-1}\right)} x_i \right)^2 - \
        10\cos\left(2\pi 10^{\left(\frac{i-1}{N-1}\right)} x_i \right)`
    """
    N = len(individual)
    return 10 * N + sum((10 ** (i / (N - 1)) * x) ** 2 -
                        10 * cos(2 * pi * 10 ** (i / (N - 1)) * x) for i, x in enumerate(individual)),


def rastrigin_skew(individual):
    r"""Skewed Rastrigin test objective function.

     :math:`f_{\text{RastSkew}}(\mathbf{x}) = 10N + \sum_{i=1}^N \left(y_i^2 - 10 \cos(2\pi x_i)\right)`

     :math:`\text{with } y_i = \
                            \begin{cases} \
                                10\cdot x_i & \text{ if } x_i > 0,\\ \
                                x_i & \text{ otherwise } \
                            \end{cases}`
    """
    N = len(individual)
    return 10 * N + sum((10 * x if x > 0 else x) ** 2
                        - 10 * cos(2 * pi * (10 * x if x > 0 else x)) for x in individual),


def schaffer(individual):
    r"""Schaffer test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - :math:`x_i \in [-100, 100]`
       * - Global optima
         - :math:`x_i = 0, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         -  :math:`f(\mathbf{x}) = \sum_{i=1}^{N-1} (x_i^2+x_{i+1}^2)^{0.25} \cdot \
                  \left[ \sin^2(50\cdot(x_i^2+x_{i+1}^2)^{0.10}) + 1.0 \
                  \right]`

    .. plot:: code/benchmarks/schaffer.py
        :width: 67 %
    """
    return sum((x ** 2 + x1 ** 2) ** 0.25 * ((sin(50 * (x ** 2 + x1 ** 2) ** 0.1)) ** 2 + 1.0)
               for x, x1 in zip(individual[:-1], individual[1:])),

@LogExecution
def schwefel(individual):
    r"""Schwefel test objective function.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - :math:`x_i \in [-500, 500]`
       * - Global optima
         - :math:`x_i = 420.96874636, \forall i \in \lbrace 1 \ldots N\rbrace`, :math:`f(\mathbf{x}) = 0`
       * - Function
         - :math:`f(\mathbf{x}) = 418.9828872724339\cdot N - \
            \sum_{i=1}^N\,x_i\sin\left(\sqrt{|x_i|}\right)`


    .. plot:: code/benchmarks/schwefel.py
        :width: 67 %
    """
    N = len(individual)
    return 418.9828872724339 * N - sum(x * sin(sqrt(abs(x))) for x in individual),


def himmelblau(individual):
    r"""The Himmelblau's function is multimodal with 4 defined minimums in
    :math:`[-6, 6]^2`.

    .. list-table::
       :widths: 10 50
       :stub-columns: 1

       * - Type
         - minimization
       * - Range
         - :math:`x_i \in [-6, 6]`
       * - Global optima
         - :math:`\mathbf{x}_1 = (3.0, 2.0)`, :math:`f(\mathbf{x}_1) = 0`\n
           :math:`\mathbf{x}_2 = (-2.805118, 3.131312)`, :math:`f(\mathbf{x}_2) = 0`\n
           :math:`\mathbf{x}_3 = (-3.779310, -3.283186)`, :math:`f(\mathbf{x}_3) = 0`\n
           :math:`\mathbf{x}_4 = (3.584428, -1.848126)`, :math:`f(\mathbf{x}_4) = 0`\n
       * - Function
         - :math:`f(x_1, x_2) = (x_1^2 + x_2 - 11)^2 + (x_1 + x_2^2 -7)^2`

    .. plot:: code/benchmarks/himmelblau.py
        :width: 67 %
    """
    return (individual[0] * individual[0] + individual[1] - 11) ** 2 + \
           (individual[0] + individual[1] * individual[1] - 7) ** 2,


def shekel(individual, a, c):
    r"""The Shekel multimodal function can have any number of maxima. The number
    of maxima is given by the length of any of the arguments *a* or *c*, *a*
    is a matrix of size :math:`M\times N`, where *M* is the number of maxima
    and *N* the number of dimensions and *c* is a :math:`M\times 1` vector.

    :math:`f_\text{Shekel}(\mathbf{x}) = \sum_{i = 1}^{M} \frac{1}{c_{i} +
    \sum_{j = 1}^{N} (x_{j} - a_{ij})^2 }`

    The following figure uses

    :math:`\mathcal{A} = \begin{bmatrix} 0.5 & 0.5 \\ 0.25 & 0.25 \\
    0.25 & 0.75 \\ 0.75 & 0.25 \\ 0.75 & 0.75 \end{bmatrix}` and
    :math:`\mathbf{c} = \begin{bmatrix} 0.002 \\ 0.005 \\ 0.005
    \\ 0.005 \\ 0.005 \end{bmatrix}`, thus defining 5 maximums in
    :math:`\mathbb{R}^2`.

    .. plot:: code/benchmarks/shekel.py
        :width: 67 %
    """
    return sum((1. / (c[i] + sum((individual[j] - aij) ** 2 for j, aij in enumerate(a[i])))) for i in range(len(c))),


# Multiobjectives
def kursawe(individual):
    r"""Kursawe multiobjective function.

    :math:`f_{\text{Kursawe}1}(\mathbf{x}) = \sum_{i=1}^{N-1} -10 e^{-0.2 \sqrt{x_i^2 + x_{i+1}^2} }`

    :math:`f_{\text{Kursawe}2}(\mathbf{x}) = \sum_{i=1}^{N} |x_i|^{0.8} + 5 \sin(x_i^3)`

    .. plot:: code/benchmarks/kursawe.py
       :width: 100 %
    """
    f1 = sum(-10 * exp(-0.2 * sqrt(x * x + y * y)) for x, y in zip(individual[:-1], individual[1:]))
    f2 = sum(abs(x) ** 0.8 + 5 * sin(x * x * x) for x in individual)
    return f1, f2


def schaffer_mo(individual):
    r"""Schaffer's multiobjective function on a one attribute *individual*.
    From: J. D. Schaffer, "Multiple objective optimization with vector
    evaluated genetic algorithms", in Proceedings of the First International
    Conference on Genetic Algorithms, 1987.

    :math:`f_{\text{Schaffer}1}(\mathbf{x}) = x_1^2`

    :math:`f_{\text{Schaffer}2}(\mathbf{x}) = (x_1-2)^2`
    """
    return individual[0] ** 2, (individual[0] - 2) ** 2


def zdt1(individual):
    r"""ZDT1 multiobjective function.

    :math:`g(\mathbf{x}) = 1 + \frac{9}{n-1}\sum_{i=2}^n x_i`

    :math:`f_{\text{ZDT1}1}(\mathbf{x}) = x_1`

    :math:`f_{\text{ZDT1}2}(\mathbf{x}) = g(\mathbf{x})\left[1 - \sqrt{\frac{x_1}{g(\mathbf{x})}}\right]`
    """
    g = 1.0 + 9.0 * sum(individual[1:]) / (len(individual) - 1)
    f1 = individual[0]
    f2 = g * (1 - sqrt(f1 / g))
    return f1, f2


def zdt2(individual):
    r"""ZDT2 multiobjective function.

    :math:`g(\mathbf{x}) = 1 + \frac{9}{n-1}\sum_{i=2}^n x_i`

    :math:`f_{\text{ZDT2}1}(\mathbf{x}) = x_1`

    :math:`f_{\text{ZDT2}2}(\mathbf{x}) = g(\mathbf{x})\left[1 - \left(\frac{x_1}{g(\mathbf{x})}\right)^2\right]`

    """

    g = 1.0 + 9.0 * sum(individual[1:]) / (len(individual) - 1)
    f1 = individual[0]
    f2 = g * (1 - (f1 / g) ** 2)
    return f1, f2


def zdt3(individual):
    r"""ZDT3 multiobjective function.

    :math:`g(\mathbf{x}) = 1 + \frac{9}{n-1}\sum_{i=2}^n x_i`

    :math:`f_{\text{ZDT3}1}(\mathbf{x}) = x_1`

    :math:`f_{\text{ZDT3}2}(\mathbf{x}) = g(\mathbf{x})\left[1 - \sqrt{\frac{x_1}{g(\mathbf{x})}} - \frac{x_1}{g(\mathbf{x})}\sin(10\pi x_1)\right]`

    """

    g = 1.0 + 9.0 * sum(individual[1:]) / (len(individual) - 1)
    f1 = individual[0]
    f2 = g * (1 - sqrt(f1 / g) - f1 / g * sin(10 * pi * f1))
    return f1, f2


def zdt4(individual):
    r"""ZDT4 multiobjective function.

    :math:`g(\mathbf{x}) = 1 + 10(n-1) + \sum_{i=2}^n \left[ x_i^2 - 10\cos(4\pi x_i) \right]`

    :math:`f_{\text{ZDT4}1}(\mathbf{x}) = x_1`

    :math:`f_{\text{ZDT4}2}(\mathbf{x}) = g(\mathbf{x})\left[ 1 - \sqrt{x_1/g(\mathbf{x})} \right]`

    """
    g = 1 + 10 * (len(individual) - 1) + sum(xi ** 2 - 10 * cos(4 * pi * xi) for xi in individual[1:])
    f1 = individual[0]
    f2 = g * (1 - sqrt(f1 / g))
    return f1, f2


def zdt6(individual):
    r"""ZDT6 multiobjective function.

    :math:`g(\mathbf{x}) = 1 + 9 \left[ \left(\sum_{i=2}^n x_i\right)/(n-1) \right]^{0.25}`

    :math:`f_{\text{ZDT6}1}(\mathbf{x}) = 1 - \exp(-4x_1)\sin^6(6\pi x_1)`

    :math:`f_{\text{ZDT6}2}(\mathbf{x}) = g(\mathbf{x}) \left[ 1 - (f_{\text{ZDT6}1}(\mathbf{x})/g(\mathbf{x}))^2 \right]`

    """
    g = 1 + 9 * (sum(individual[1:]) / (len(individual) - 1)) ** 0.25
    f1 = 1 - exp(-4 * individual[0]) * sin(6 * pi * individual[0]) ** 6
    f2 = g * (1 - (f1 / g) ** 2)
    return f1, f2


def dtlz1(individual, obj):
    r"""DTLZ1 multiobjective function. It returns a tuple of *obj* values.
    The individual must have at least *obj* elements.
    From: K. Deb, L. Thiele, M. Laumanns and E. Zitzler. Scalable Multi-Objective
    Optimization Test Problems. CEC 2002, p. 825 - 830, IEEE Press, 2002.

    :math:`g(\mathbf{x}_m) = 100\left(|\mathbf{x}_m| + \sum_{x_i \in \mathbf{x}_m}\left((x_i - 0.5)^2 - \cos(20\pi(x_i - 0.5))\right)\right)`

    :math:`f_{\text{DTLZ1}1}(\mathbf{x}) = \frac{1}{2} (1 + g(\mathbf{x}_m)) \prod_{i=1}^{m-1}x_i`

    :math:`f_{\text{DTLZ1}2}(\mathbf{x}) = \frac{1}{2} (1 + g(\mathbf{x}_m)) (1-x_{m-1}) \prod_{i=1}^{m-2}x_i`

    :math:`\ldots`

    :math:`f_{\text{DTLZ1}m-1}(\mathbf{x}) = \frac{1}{2} (1 + g(\mathbf{x}_m)) (1 - x_2) x_1`

    :math:`f_{\text{DTLZ1}m}(\mathbf{x}) = \frac{1}{2} (1 - x_1)(1 + g(\mathbf{x}_m))`

    Where :math:`m` is the number of objectives and :math:`\mathbf{x}_m` is a
    vector of the remaining attributes :math:`[x_m~\ldots~x_n]` of the
    individual in :math:`n > m` dimensions.

    """
    g = 100 * (len(individual[obj - 1:]) + sum(
        (xi - 0.5) ** 2 - cos(20 * pi * (xi - 0.5)) for xi in individual[obj - 1:]))
    f = [0.5 * reduce(mul, individual[:obj - 1], 1) * (1 + g)]
    f.extend(0.5 * reduce(mul, individual[:m], 1) * (1 - individual[m]) * (1 + g) for m in reversed(range(obj - 1)))
    return f


def dtlz2(individual, obj):
    r"""DTLZ2 multiobjective function. It returns a tuple of *obj* values.
    The individual must have at least *obj* elements.
    From: K. Deb, L. Thiele, M. Laumanns and E. Zitzler. Scalable Multi-Objective
    Optimization Test Problems. CEC 2002, p. 825 - 830, IEEE Press, 2002.

    :math:`g(\mathbf{x}_m) = \sum_{x_i \in \mathbf{x}_m} (x_i - 0.5)^2`

    :math:`f_{\text{DTLZ2}1}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \prod_{i=1}^{m-1} \cos(0.5x_i\pi)`

    :math:`f_{\text{DTLZ2}2}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \sin(0.5x_{m-1}\pi ) \prod_{i=1}^{m-2} \cos(0.5x_i\pi)`

    :math:`\ldots`

    :math:`f_{\text{DTLZ2}m}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \sin(0.5x_{1}\pi )`

    Where :math:`m` is the number of objectives and :math:`\mathbf{x}_m` is a
    vector of the remaining attributes :math:`[x_m~\ldots~x_n]` of the
    individual in :math:`n > m` dimensions.
    """
    xc = individual[:obj - 1]
    xm = individual[obj - 1:]
    g = sum((xi - 0.5) ** 2 for xi in xm)
    f = [(1.0 + g) * reduce(mul, (cos(0.5 * xi * pi) for xi in xc), 1.0)]
    f.extend((1.0 + g) * reduce(mul, (cos(0.5 * xi * pi) for xi in xc[:m]), 1) * sin(0.5 * xc[m] * pi) for m in
             range(obj - 2, -1, -1))

    return f


def dtlz3(individual, obj):
    r"""DTLZ3 multiobjective function. It returns a tuple of *obj* values.
    The individual must have at least *obj* elements.
    From: K. Deb, L. Thiele, M. Laumanns and E. Zitzler. Scalable Multi-Objective
    Optimization Test Problems. CEC 2002, p. 825 - 830, IEEE Press, 2002.

    :math:`g(\mathbf{x}_m) = 100\left(|\mathbf{x}_m| + \sum_{x_i \in \mathbf{x}_m}\left((x_i - 0.5)^2 - \cos(20\pi(x_i - 0.5))\right)\right)`

    :math:`f_{\text{DTLZ3}1}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \prod_{i=1}^{m-1} \cos(0.5x_i\pi)`

    :math:`f_{\text{DTLZ3}2}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \sin(0.5x_{m-1}\pi ) \prod_{i=1}^{m-2} \cos(0.5x_i\pi)`

    :math:`\ldots`

    :math:`f_{\text{DTLZ3}m}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \sin(0.5x_{1}\pi )`

    Where :math:`m` is the number of objectives and :math:`\mathbf{x}_m` is a
    vector of the remaining attributes :math:`[x_m~\ldots~x_n]` of the
    individual in :math:`n > m` dimensions.
    """
    xc = individual[:obj - 1]
    xm = individual[obj - 1:]
    g = 100 * (len(xm) + sum((xi - 0.5) ** 2 - cos(20 * pi * (xi - 0.5)) for xi in xm))
    f = [(1.0 + g) * reduce(mul, (cos(0.5 * xi * pi) for xi in xc), 1.0)]
    f.extend((1.0 + g) * reduce(mul, (cos(0.5 * xi * pi) for xi in xc[:m]), 1) * sin(0.5 * xc[m] * pi) for m in
             range(obj - 2, -1, -1))
    return f


def dtlz4(individual, obj, alpha):
    r"""DTLZ4 multiobjective function. It returns a tuple of *obj* values. The
    individual must have at least *obj* elements. The *alpha* parameter allows
    for a meta-variable mapping in :func:`dtlz2` :math:`x_i \rightarrow
    x_i^\alpha`, the authors suggest :math:`\alpha = 100`.
    From: K. Deb, L. Thiele, M. Laumanns and E. Zitzler. Scalable Multi-Objective
    Optimization Test Problems. CEC 2002, p. 825 - 830, IEEE Press, 2002.

    :math:`g(\mathbf{x}_m) = \sum_{x_i \in \mathbf{x}_m} (x_i - 0.5)^2`

    :math:`f_{\text{DTLZ4}1}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \prod_{i=1}^{m-1} \cos(0.5x_i^\alpha\pi)`

    :math:`f_{\text{DTLZ4}2}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \sin(0.5x_{m-1}^\alpha\pi ) \prod_{i=1}^{m-2} \cos(0.5x_i^\alpha\pi)`

    :math:`\ldots`

    :math:`f_{\text{DTLZ4}m}(\mathbf{x}) = (1 + g(\mathbf{x}_m)) \sin(0.5x_{1}^\alpha\pi )`

    Where :math:`m` is the number of objectives and :math:`\mathbf{x}_m` is a
    vector of the remaining attributes :math:`[x_m~\ldots~x_n]` of the
    individual in :math:`n > m` dimensions.
    """
    xc = individual[:obj - 1]
    xm = individual[obj - 1:]
    g = sum((xi - 0.5) ** 2 for xi in xm)
    f = [(1.0 + g) * reduce(mul, (cos(0.5 * xi ** alpha * pi) for xi in xc), 1.0)]
    f.extend(
        (1.0 + g) * reduce(mul, (cos(0.5 * xi ** alpha * pi) for xi in xc[:m]), 1) * sin(0.5 * xc[m] ** alpha * pi) for
        m in range(obj - 2, -1, -1))
    return f


def dtlz5(ind, n_objs):
    r"""DTLZ5 multiobjective function. It returns a tuple of *obj* values. The
    individual must have at least *obj* elements.
    From: K. Deb, L. Thiele, M. Laumanns and E. Zitzler. Scalable Multi-Objective
    Optimization Test Problems. CEC 2002, p. 825-830, IEEE Press, 2002.
    """
    g = lambda x: sum([(a - 0.5) ** 2 for a in x])
    gval = g(ind[n_objs - 1:])

    theta = lambda x: pi / (4.0 * (1 + gval)) * (1 + 2 * gval * x)
    fit = [(1 + gval) * cos(pi / 2.0 * ind[0]) * reduce(lambda x, y: x * y, [cos(theta(a)) for a in ind[1:]])]

    for m in reversed(range(1, n_objs)):
        if m == 1:
            fit.append((1 + gval) * sin(pi / 2.0 * ind[0]))
        else:
            fit.append((1 + gval) * cos(pi / 2.0 * ind[0]) *
                       reduce(lambda x, y: x * y, [cos(theta(a)) for a in ind[1:m - 1]], 1) * sin(theta(ind[m - 1])))
    return fit


def dtlz6(ind, n_objs):
    r"""DTLZ6 multiobjective function. It returns a tuple of *obj* values. The
    individual must have at least *obj* elements.
    From: K. Deb, L. Thiele, M. Laumanns and E. Zitzler. Scalable Multi-Objective
    Optimization Test Problems. CEC 2002, p. 825-830, IEEE Press, 2002.
    """
    gval = sum([a ** 0.1 for a in ind[n_objs - 1:]])
    theta = lambda x: pi / (4.0 * (1 + gval)) * (1 + 2 * gval * x)

    fit = [(1 + gval) * cos(pi / 2.0 * ind[0]) *
           reduce(lambda x, y: x * y, [cos(theta(a)) for a in ind[1:]])]

    for m in reversed(range(1, n_objs)):
        if m == 1:
            fit.append((1 + gval) * sin(pi / 2.0 * ind[0]))
        else:
            fit.append((1 + gval) * cos(pi / 2.0 * ind[0]) *
                       reduce(lambda x, y: x * y, [cos(theta(a)) for a in ind[1:m - 1]], 1) * sin(theta(ind[m - 1])))
    return fit


def dtlz7(ind, n_objs):
    r"""DTLZ7 multiobjective function. It returns a tuple of *obj* values. The
    individual must have at least *obj* elements.
    From: K. Deb, L. Thiele, M. Laumanns and E. Zitzler. Scalable Multi-Objective
    Optimization Test Problems. CEC 2002, p. 825-830, IEEE Press, 2002.
    """
    gval = 1 + 9.0 / len(ind[n_objs - 1:]) * sum([a for a in ind[n_objs - 1:]])
    fit = [x for x in ind[:n_objs - 1]]
    fit.append((1 + gval) * (n_objs - sum([a / (1.0 + gval) * (1 + sin(3 * pi * a)) for a in ind[:n_objs - 1]])))
    return fit


def fonseca(individual):
    r"""Fonseca and Fleming's multiobjective function.
    From: C. M. Fonseca and P. J. Fleming, "Multiobjective optimization and
    multiple constraint handling with evolutionary algorithms -- Part II:
    Application example", IEEE Transactions on Systems, Man and Cybernetics,
    1998.

    :math:`f_{\text{Fonseca}1}(\mathbf{x}) = 1 - e^{-\sum_{i=1}^{3}(x_i - \frac{1}{\sqrt{3}})^2}`

    :math:`f_{\text{Fonseca}2}(\mathbf{x}) = 1 - e^{-\sum_{i=1}^{3}(x_i + \frac{1}{\sqrt{3}})^2}`
    """
    f_1 = 1 - exp(-sum((xi - 1 / sqrt(3)) ** 2 for xi in individual[:3]))
    f_2 = 1 - exp(-sum((xi + 1 / sqrt(3)) ** 2 for xi in individual[:3]))
    return f_1, f_2


def poloni(individual):
    r"""Poloni's multiobjective function on a two attribute *individual*. From:
    C. Poloni, "Hybrid GA for multi objective aerodynamic shape optimization",
    in Genetic Algorithms in Engineering and Computer Science, 1997.

    :math:`A_1 = 0.5 \sin (1) - 2 \cos (1) + \sin (2) - 1.5 \cos (2)`

    :math:`A_2 = 1.5 \sin (1) - \cos (1) + 2 \sin (2) - 0.5 \cos (2)`

    :math:`B_1 = 0.5 \sin (x_1) - 2 \cos (x_1) + \sin (x_2) - 1.5 \cos (x_2)`

    :math:`B_2 = 1.5 \sin (x_1) - cos(x_1) + 2 \sin (x_2) - 0.5 \cos (x_2)`

    :math:`f_{\text{Poloni}1}(\mathbf{x}) = 1 + (A_1 - B_1)^2 + (A_2 - B_2)^2`

    :math:`f_{\text{Poloni}2}(\mathbf{x}) = (x_1 + 3)^2 + (x_2 + 1)^2`
    """
    x_1 = individual[0]
    x_2 = individual[1]
    A_1 = 0.5 * sin(1) - 2 * cos(1) + sin(2) - 1.5 * cos(2)
    A_2 = 1.5 * sin(1) - cos(1) + 2 * sin(2) - 0.5 * cos(2)
    B_1 = 0.5 * sin(x_1) - 2 * cos(x_1) + sin(x_2) - 1.5 * cos(x_2)
    B_2 = 1.5 * sin(x_1) - cos(x_1) + 2 * sin(x_2) - 0.5 * cos(x_2)
    return 1 + (A_1 - B_1) ** 2 + (A_2 - B_2) ** 2, (x_1 + 3) ** 2 + (x_2 + 1) ** 2


def dent(individual, lambda_=0.85):
    r"""Test problem Dent. Two-objective problem with a "dent". *individual* has
    two attributes that take values in [-1.5, 1.5].
    From: Schuetze, O., Laumanns, M., Tantar, E., Coello Coello, C.A., & Talbi, E.-G. (2010).
    Computing gap free Pareto front approximations with stochastic search algorithms.
    Evolutionary Computation, 18(1), 65--96. doi:10.1162/evco.2010.18.1.18103

    Note that in that paper Dent source is stated as:
    K. Witting and M. Hessel von Molo. Private communication, 2006.
    """
    d = lambda_ * exp(-(individual[0] - individual[1]) ** 2)
    f1 = 0.5 * (sqrt(1 + (individual[0] + individual[1]) ** 2) +
                sqrt(1 + (individual[0] - individual[1]) ** 2) +
                individual[0] - individual[1]) + d
    f2 = 0.5 * (sqrt(1 + (individual[0] + individual[1]) ** 2) +
                sqrt(1 + (individual[0] - individual[1]) ** 2) -
                individual[0] + individual[1]) + d
    return f1, f2,


@LogExecution
def sumOfSquares(individual):
    sum = 0
    for i, gene in enumerate(individual):
        sum += i * (gene ** 2)
    return sum,

@LogExecution
def sumOfSquaresShifted(individual):
    shift = np.array([22.479705250884777, 60.75750080751871, -59.15674324461888, 27.56336400934657, -59.29279403989607,
                      -64.68447838216555, 78.33759532500437, -76.51052473538245, -66.6507114149451, 64.93462028133038,
                      -15.23828302367869, -17.825823787619903, -31.92405019180454, 67.72646439056987,
                      -0.9830868197666973,
                      39.91084384292476, 1.4187967568760484, 24.815734535933146, -3.3313494842577285, 18.8446220852682,
                      -17.002497910454828, 50.326561978450655, 53.95001535109091, 18.403170606945253,
                      -21.184841218701195,
                      -62.069991345971154, -43.868924783714164, -24.682337470629072, -2.8605529514137373,
                      65.39162073164252,
                      2.6654994832190937, 23.60580815931415, 13.846698992449433, -41.45381813820245, -76.7118669691122,
                      40.566665904615746, 22.28454884887448, -55.31708080994516, -76.649068909635, 40.966439161432476,
                      -3.0455255815577402, 3.119008384655956, 18.342768255788243, -20.144695942649946, 8.54936385804288,
                      50.45305033701291, 23.2263485421784, 10.68869991058412, 74.77290130427247, 33.82014641772588,
                      -19.74543928622861, -43.37360584484172, 79.02166733921979, -45.92410252364756, -44.01344981695157,
                      44.109743914203506, -77.20689956137252, -72.65296438262618, 46.613878010132, -14.343495951611033])

    shifted_individual = np.array(individual) - shift

    sum = 0
    for i, gene in enumerate(shifted_individual):
        sum += i * (gene ** 2)
    return sum,

@LogExecution
def schwefel12(individual):
    return sum([sum(individual[:i]) ** 2
                for i in range(len(individual))]),

@LogExecution
def schwefel12Shifted(individual):
    shift = np.array([65.90306349157711, 7.223609380925922, 48.06403595428989, -61.23008904674027, -56.996531732952235,
                      -65.67006959079087, 56.81852866354663, -49.51353093024842, -42.403457350576694, 64.22460834371887,
                      -15.782490031817943, 78.2275393766464, 58.981759831549084, -41.74229801452056, 70.70390026719807,
                      -27.120714671264494, -43.362534341811525, -11.357893018802812, 25.329639322623194,
                      -24.02213869456814,
                      -29.51839742945309, -50.65352492132828, -17.33907934005014, -75.7276769694174, 25.60834507308691,
                      -40.61133175959546, 53.35222265494423, 30.414516938139826, -37.83651132063628, 30.243572616044418,
                      -2.2080543227080227, -51.34193306616531, 37.36039082956739, -44.93167877354562, 63.19570228206453,
                      -0.3230550173947364, 54.997116479336086, -39.773290218583256, 54.1565458883382,
                      12.124637138785076,
                      43.85247188324382, -75.91826727683562, 49.74255951193999, 63.92380934836413, 54.34684698334098,
                      -19.563396930885908, 31.656857648987852, -32.907795698796306, -29.963471074339374,
                      -60.672896416251675,
                      -77.62660870400686, -75.95478227792732, 23.6157226667015, 75.57747129002121, -15.915369172179027,
                      -45.240636434786694, 34.3519391836095, 65.07767014581174, 13.32242757194949, 71.34237817983137])

    shifted_individual = np.array(individual) - shift
    return sum([sum(shifted_individual[:i]) ** 2 for i in range(len(individual))]),

@LogExecution
def sixHumpCamelBack(individual):
    return (4 - 2.1 * individual[0] ** 2 + (individual[0] ** 4) / 3) * individual[0] ** 2 + individual[0] * individual[
        1] + (
                   -4 + 4 * individual[1] ** 2) * individual[1] ** 2,

@LogExecution
def branin(individual):
    a = 1
    b = 5.1 / (4 * np.pi ** 2)
    c = 5 / np.pi
    r = 6
    s = 10
    t = 1 / (8 * np.pi)

    x1 = individual[0]
    x2 = individual[1]

    term1 = a * (x2 - b * x1 ** 2 + c * x1 - r) ** 2
    term2 = s * (1 - t) * np.cos(x1)
    term3 = s

    return term1 + term2 + term3,

@LogExecution
def goldsteinPrice(individual):
    term1 = (1 + ((individual[0] + individual[1] + 1) ** 2) * (
            19 - 14 * individual[0] + 3 * individual[0] ** 2 - 14 * individual[1] + 6 * individual[0] * individual[
        1] + 3 *
            individual[1] ** 2))
    term2 = (30 + ((2 * individual[0] - 3 * individual[1]) ** 2) * (
            18 - 32 * individual[0] + 12 * individual[0] ** 2 + 48 * individual[1] - 36 * individual[0] * individual[
        1] + 27 *
            individual[1] ** 2))
    return term1 * term2,

@LogExecution
def hartman(individual):
    alpha = [1.0, 1.2, 3.0, 3.2]
    A = np.array([[3.0, 10, 30],
                  [0.1, 10, 35],
                  [3.0, 10, 30],
                  [0.1, 10, 35]])
    P = 0.0001 * np.array([[3689, 1170, 2673],
                           [4699, 4387, 7470],
                           [1091, 8732, 5547],
                           [381, 5743, 8828]])
    res = 0.0
    for i in range(4):
        inner = sum(A[i] * (individual - P[i]) ** 2)
        res -= alpha[i] * np.exp(-inner)
    return (res,)

@LogExecution
def shekelFoxholes(individual):
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

    fitness = 0.0
    for j in range(25):
        sum = 0.0
        for i in range(len(individual)):
            sum += (individual[i] - a[j][i]) ** 6
        sum += j + 1
        fitness += 1.0 / sum
    fitness += 1.0 / 500.0
    return (fitness ** -1,)
