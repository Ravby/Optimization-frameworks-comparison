from abc import abstractmethod

import numpy as np
import time
from pymoo.algorithms.soo.nonconvex.cmaes import CMAES
from pymoo.algorithms.soo.nonconvex.de import DE
from pymoo.algorithms.soo.nonconvex.ga import GA
from pymoo.algorithms.soo.nonconvex.pso import PSO
from pymoo.core.problem import Problem
from pymoo.operators.crossover.sbx import SBX
from pymoo.operators.mutation.pm import PM
from pymoo.optimize import minimize
import pymoo.gradient.toolbox as anp
from pathlib import Path

# Parameters
number_of_runs = 50
max_evaluations = 15_000
n_dimensions = 60


class LoggingProblem(Problem):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.evaluation_count = 0
        self.best_fitness = float('inf')
        self.is_first_evaluation = True
        self.improvements = []

    def _evaluate(self, x, out, *args, **kwargs):
        # Get number of individuals
        N = x.shape[0]
        F = self._compute_fitness(x, out, *args, **kwargs)
        out["F"] = F
        # Log improvements
        for i in range(N):
            self.evaluation_count += 1
            fitness = F[i]
            if self.is_first_evaluation or fitness < self.best_fitness:
                self.improvements.append([self.evaluation_count, fitness])
                self.best_fitness = fitness
                self.is_first_evaluation = False

    @abstractmethod
    def _compute_fitness(self, x, out, *args, **kwargs):
        pass

    def get_improvements(self):
        return self.improvements

    def get_evaluation_count(self):
        return self.evaluation_count

    def reset(self):
        self.evaluation_count = 0
        self.best_fitness = float('inf')
        self.is_first_evaluation = True
        self.improvements = []


class ShiftedProblem(LoggingProblem):
    def __init__(self, base_problem, shift):
        super().__init__(
            n_var=base_problem.n_var,
            n_obj=base_problem.n_obj,
            n_ieq_constr=base_problem.n_ieq_constr,
            xl=base_problem.xl,
            xu=base_problem.xu,
            vtype=base_problem.vtype
        )
        self.base_problem = base_problem
        self.shift = np.asarray(shift)[:self.n_var]  # Trim to n_var

    def name(self):
        # Get the class name of the base problem
        return f"Shifted{self.base_problem.__class__.__name__}"

    def _compute_fitness(self, x, out, *args, **kwargs):
        shifted_x = x - self.shift
        return self.base_problem._compute_fitness(shifted_x, out, *args, **kwargs)

    def _calc_pareto_front(self):
        return self.base_problem._calc_pareto_front()

    def _calc_pareto_set(self):
        return self.base_problem._calc_pareto_set() + self.shift


shift_sphere = np.array([
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

shift_sum_of_squares = np.array([
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

shift_schwefel = np.array([
    65.90306349157711, 7.223609380925922, 48.06403595428989, -61.23008904674027, -56.996531732952235,
    -65.67006959079087, 56.81852866354663, -49.51353093024842, -42.403457350576694, 64.22460834371887,
    -15.782490031817943, 78.2275393766464, 58.981759831549084, -41.74229801452056, 70.70390026719807,
    -27.120714671264494, -43.362534341811525, -11.357893018802812, 25.329639322623194, -24.02213869456814,
    -29.51839742945309, -50.65352492132828, -17.33907934005014, -75.7276769694174, 25.60834507308691,
    -40.61133175959546, 53.35222265494423, 30.414516938139826, -37.83651132063628, 30.243572616044418,
    -2.2080543227080227, -51.34193306616531, 37.36039082956739, -44.93167877354562, 63.19570228206453,
    -0.3230550173947364, 54.997116479336086, -39.773290218583256, 54.1565458883382, 12.124637138785076,
    43.85247188324382, -75.91826727683562, 49.74255951193999, 63.92380934836413, 54.34684698334098,
    -19.563396930885908, 31.656857648987852, -32.907795698796306, -29.963471074339374, -60.672896416251675,
    -77.62660870400686, -75.95478227792732, 23.6157226667015, 75.57747129002121, -15.915369172179027,
    -45.240636434786694, 34.3519391836095, 65.07767014581174, 13.32242757194949, 71.34237817983137,
    -74.51968139294803, 3.512587033090128, 56.30141471139396, 31.538703365256055, 24.585815604555407,
    -67.48845399940869, -40.64252673369548, 59.91338156677921, -74.95426285566866, -38.150045876281204,
    -43.153328841782056, 21.868730315180528, -41.62610523688311, 1.6077581521206525, -72.85317012180792,
    63.05034100312395, 48.208504063943536, -19.726627265139726, -14.832261021455807, -5.053624253018285,
    -60.42559524043723, -53.2642183230471, -48.14019430704212, 35.20030522956961, 79.05287296526038,
    -79.9325666407405, 59.03982443403498, 53.3011236362469, 20.874500877568096, 44.9656312058855,
    74.28787167461365, 44.31739930419482, -78.40723117562789, -54.82586056439315, -36.33222788844623,
    69.06988428571137, 79.06863059997934, 22.41510141643954, 61.96066562216663, -5.6050556467548205
])

shift_rastrigin = np.array([
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

shift_ackley = np.array([
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

shift_griewank = np.array([
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


class Sphere(LoggingProblem):

    def __init__(self, n_var=10):
        super().__init__(n_var=n_var, n_obj=1, n_ieq_constr=0, xl=-100, xu=100, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        return anp.sum(anp.square(x), axis=1)

    def _calc_pareto_front(self):
        return 0.0

    def _calc_pareto_set(self):
        return np.full(self.n_var, 0.0)


class Rastrigin(LoggingProblem):
    def __init__(self, n_var=10):
        super().__init__(n_var=n_var, n_obj=1, n_ieq_constr=0, xl=-5.12, xu=5.12, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        A = 10
        return A * self.n_var + np.sum(x ** 2 - A * np.cos(2 * np.pi * x), axis=1)

    def _calc_pareto_front(self):
        return 0.0

    def _calc_pareto_set(self):
        return np.zeros(self.n_var)


class Ackley(LoggingProblem):
    def __init__(self, n_var=10):
        super().__init__(n_var=n_var, n_obj=1, n_ieq_constr=0, xl=-32.0, xu=32.0, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        a = 20.0
        b = 0.2
        c = 2 * np.pi
        n = self.n_var
        sum1 = np.sum(x ** 2, axis=1) / n
        sum2 = np.sum(np.cos(c * x), axis=1) / n
        return -a * np.exp(-b * np.sqrt(sum1)) - np.exp(sum2) + a + np.e

    def _calc_pareto_front(self):
        return 0.0

    def _calc_pareto_set(self):
        return np.zeros(self.n_var)


class Rosenbrock(LoggingProblem):
    def __init__(self, n_var=10):
        super().__init__(n_var=n_var, n_obj=1, n_ieq_constr=0, xl=-30.0, xu=30.0, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        fitness = np.sum(
            100 * (x[:, 1:] - x[:, :-1] ** 2) ** 2 + (1 - x[:, :-1]) ** 2,
            axis=1)
        return fitness

    def _calc_pareto_front(self):
        return 0.0

    def _calc_pareto_set(self):
        return np.ones(self.n_var)


class SumOfSquares(LoggingProblem):
    def __init__(self, n_var=10):
        super().__init__(n_var=n_var, n_obj=1, n_ieq_constr=0, xl=-100.0, xu=100.0, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        indices = np.arange(1, self.n_var + 1)
        fitness = np.sum(indices * x ** 2, axis=1)
        return fitness

    def _calc_pareto_front(self):
        return 0.0

    def _calc_pareto_set(self):
        return np.zeros(self.n_var)


def schwefel(x):
    fitness = 0
    for i in range(len(x)):
        sum_j = sum(x[i] for j in range(i))  # Sum up to i
        fitness += sum_j ** 2
    return fitness


class Schwefel(LoggingProblem):
    def __init__(self, n_var=10):
        super().__init__(n_var=n_var, n_obj=1, n_ieq_constr=0, xl=-100.0, xu=100.0, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        N, D = x.shape
        fitness = np.zeros((N, 1))
        for i in range(N):
            fitness[i] = schwefel(x[i])
        return fitness

    def _calc_pareto_front(self):
        return 0.0

    def _calc_pareto_set(self):
        return np.zeros(self.n_var)


class Griewank(LoggingProblem):
    def __init__(self, n_var=10):
        super().__init__(n_var=n_var, n_obj=1, n_ieq_constr=0, xl=-600.0, xu=600.0, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        sum_ = np.sum(x ** 2, axis=1)
        prod = np.prod(np.cos(x / np.sqrt(np.arange(1, self.n_var + 1))), axis=1)
        return (1.0 / 4000.0) * sum_ - prod + 1.0

    def _calc_pareto_front(self):
        return 0.0

    def _calc_pareto_set(self):
        return np.zeros(self.n_var)


class ShekelsFoxholes(LoggingProblem):
    def __init__(self):
        super().__init__(n_var=2, n_obj=1, n_ieq_constr=0, xl=-65.536, xu=65.536, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        a = np.array([
            [-32, -32], [-16, -32], [0, -32], [16, -32], [32, -32],
            [-32, -16], [-16, -16], [0, -16], [16, -16], [32, -16],
            [-32, 0], [-16, 0], [0, 0], [16, 0], [32, 0],
            [-32, 16], [-16, 16], [0, 16], [16, 16], [32, 16],
            [-32, 32], [-16, 32], [0, 32], [16, 32], [32, 32]
        ])
        fitness = np.zeros((x.shape[0], 1))
        for j in range(25):
            sum_j = np.sum((x - a[j]) ** 6, axis=1) + (j + 1)
            fitness += 1.0 / sum_j[:, np.newaxis]
        fitness += 1.0 / 500.0
        return 1.0 / fitness

    def _calc_pareto_front(self):
        return 1.0 / (1.0 / 500.0 + np.sum(1.0 / (np.arange(1, 26))))

    def _calc_pareto_set(self):
        return np.array([-32.0, -32.0])


class SixHumpCamelBack(LoggingProblem):
    def __init__(self):
        super().__init__(n_var=2, n_obj=1, n_ieq_constr=0, xl=[-5.0, -5.0], xu=[5.0, 5.0], vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        x0, x1 = x[:, 0], x[:, 1]
        fitness = (4 * x0 ** 2
                   - 2.1 * x0 ** 4
                   + (1.0 / 3.0) * x0 ** 6
                   + x0 * x1
                   - 4 * x1 ** 2
                   + 4 * x1 ** 4)
        return fitness[:, np.newaxis]

    def _calc_pareto_front(self):
        return -1.031628453489877

    def _calc_pareto_set(self):
        return np.array([[0.089842, -0.712656], [-0.089842, 0.712656]])


class Branin(LoggingProblem):
    def __init__(self):
        super().__init__(n_var=2, n_obj=1, n_ieq_constr=0, xl=[-5.0, 0.0], xu=[10.0, 15.0], vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        x0, x1 = x[:, 0], x[:, 1]
        fitness = ((x1 - (5.1 / (4 * np.pi ** 2)) * x0 ** 2 + (5.0 / np.pi) * x0 - 6) ** 2
                   + 10 * (1 - 1.0 / (8.0 * np.pi)) * np.cos(x0) + 10)
        return fitness[:, np.newaxis]

    def _calc_pareto_front(self):
        return 0.397887

    def _calc_pareto_set(self):
        return np.array([[-np.pi, 12.275], [np.pi, 2.275], [9.42478, 2.475]])


class GoldsteinPrice(LoggingProblem):
    def __init__(self):
        super().__init__(n_var=2, n_obj=1, n_ieq_constr=0, xl=-2.0, xu=2.0, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        x0, x1 = x[:, 0], x[:, 1]
        term1 = 1 + (x0 + x1 + 1) ** 2 * (19 - 14 * x0 + 3 * x0 ** 2 - 14 * x1 + 6 * x0 * x1 + 3 * x1 ** 2)
        term2 = 30 + (2 * x0 - 3 * x1) ** 2 * (18 - 32 * x0 + 12 * x0 ** 2 + 48 * x1 - 36 * x0 * x1 + 27 * x1 ** 2)
        fitness = term1 * term2
        return fitness[:, np.newaxis]

    def _calc_pareto_front(self):
        return 3.0

    def _calc_pareto_set(self):
        return np.array([0.0, -1.0])


class Hartman(LoggingProblem):
    def __init__(self):
        super().__init__(n_var=3, n_obj=1, n_ieq_constr=0, xl=0.0, xu=1.0, vtype=float)

    def _compute_fitness(self, x, out, *args, **kwargs):
        c = np.array([1.0, 1.2, 3.0, 3.2])
        a = np.array([[3.0, 10, 30], [0.1, 10, 35], [3.0, 10, 30], [0.1, 10, 35]])
        p = np.array(
            [[0.3689, 0.1170, 0.2673], [0.4699, 0.4387, 0.7470], [0.1091, 0.8732, 0.5547], [0.03815, 0.5743, 0.8828]])
        fitness = np.zeros((x.shape[0], 1))
        for i in range(4):
            sum_j = np.sum(a[i] * (x - p[i]) ** 2, axis=1)
            fitness += c[i] * np.exp(-sum_j)[:, np.newaxis]
        return -fitness

    def _calc_pareto_front(self):
        return -0.083  # Approximate optimum for n_var=3

    def _calc_pareto_set(self):
        return np.array([0.114614, 0.555649, 0.852547])


#problem = ShiftedProblem(Schwefel(60), shift_schwefel)
# problem = Schwefel(60)
# x = np.ones((1, 60))
#x = np.asarray(shift_schwefel)[:60].reshape(1, -1)
#out = {}
#problem._evaluate(x, out)
#print(out["F"])

def write_results_to_file(filename, results):
    project_dir = Path.cwd()
    parent_dir = project_dir.parent.parent
    files_dir = parent_dir / "EARS comparison" / "Algorithm results"
    file_location = files_dir / filename
    files_dir.mkdir(parents=True, exist_ok=True)
    with open(file_location, 'w') as f:
        for result in results:
            f.write(f"{result}\n")

def write_runs_to_file(filename, improvements):
    project_dir = Path.cwd()
    parent_dir = project_dir.parent.parent
    files_dir = parent_dir / "EARS comparison" / "Algorithm results" / "Runs"
    file_location = files_dir / filename
    files_dir.mkdir(parents=True, exist_ok=True)
    with open(file_location, 'w') as f:
        f.write('Evaluations,Fitness\n')
        for improvement in improvements:
            f.write(f"{int(improvement[0])},{improvement[1]}\n")

problems = [
    ShiftedProblem(Sphere(n_dimensions), shift_sphere),
    ShiftedProblem(SumOfSquares(n_dimensions), shift_sum_of_squares),
    ShiftedProblem(Schwefel(n_dimensions), shift_schwefel),
    ShiftedProblem(Rastrigin(n_dimensions), shift_rastrigin),
    ShiftedProblem(Ackley(n_dimensions), shift_ackley),
    ShiftedProblem(Griewank(n_dimensions), shift_griewank),
    Sphere(n_dimensions),
    SumOfSquares(n_dimensions),
    Schwefel(n_dimensions),
    Rastrigin(n_dimensions),
    Ackley(n_dimensions),
    Griewank(n_dimensions),
    Rosenbrock(n_dimensions),
    ShekelsFoxholes(),
    SixHumpCamelBack(),
    Branin(),
    GoldsteinPrice(),
    Hartman()
]

start_time = time.perf_counter()

for problem in problems:
    print(problem.name())
    print("PSO")
    results = []
    for i in range(number_of_runs):
        problem.reset()
        algorithm = PSO(pop_size=30, w=0.7, c1=2.0, c2=2.0, adaptive=False, pertube_best=False)  # max_velocity_rate=0.20
        sol = minimize(problem, algorithm, ('n_eval', max_evaluations), verbose=False)
        results.append(sol.F[0])
        filename = f"PSO-pymoo_{problem.name()}_vars={problem.n_var}_run={i + 1}.csv"
        write_runs_to_file(filename, problem.get_improvements())
    write_results_to_file(f"PSO-pymoo_{problem.name()}D{problem.n_var}.txt", results)

    print("DE")
    results = []
    for i in range(number_of_runs):
        problem.reset()
        algorithm = DE(pop_size=50, variant="DE/rand/1/bin", F=0.5, CR=0.9, dither=None, jitter=False)
        sol = minimize(problem, algorithm, ('n_eval', max_evaluations), verbose=False)
        results.append(sol.F[0])
        filename = f"DE-pymoo_{problem.name()}_vars={problem.n_var}_run={i + 1}.csv"
        write_runs_to_file(filename, problem.get_improvements())
    write_results_to_file(f"DE-pymoo_{problem.name()}D{problem.n_var}.txt", results)

    print("CMA-ES")
    results = []
    for i in range(number_of_runs):
        problem.reset()
        algorithm = CMAES(x0=np.random.random(problem.n_var), pop_size=30, sigma=0.5)
        sol = minimize(problem, algorithm, ('n_eval', max_evaluations), verbose=False)
        results.append(sol.F[0])
        filename = f"CMA-ES-pymoo_{problem.name()}_vars={problem.n_var}_run={i + 1}.csv"
        write_runs_to_file(filename, problem.get_improvements())
    write_results_to_file(f"CMA-ES-pymoo_{problem.name()}D{problem.n_var}.txt", results)

    print("GA")
    results = []
    for i in range(number_of_runs):
        problem.reset()
        algorithm = GA(pop_size=100, crossover=SBX(prob_var=0.95, eta=20), mutation=PM(prob=0.025, eta=20), eliminate_duplicates=False, verbose=False)
        sol = minimize(problem, algorithm, ('n_eval', max_evaluations), verbose=False)
        results.append(sol.F[0])
        filename = f"GA-pymoo_{problem.name()}_vars={problem.n_var}_run={i + 1}.csv"
        write_runs_to_file(filename, problem.get_improvements())
    write_results_to_file(f"GA-pymoo_{problem.name()}D{problem.n_var}.txt", results)

end_time = time.perf_counter()
runtime = end_time - start_time
print(f"Runtime: {runtime:.6f} seconds")
