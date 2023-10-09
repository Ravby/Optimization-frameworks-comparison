import numpy as np

from lpm.problems.branin import Branin
from lpm.problems.goldstein_price import GoldsteinPrice
from lpm.problems.hartman import Hartman
from lpm.problems.schwefel2 import Schwefel2
from lpm.problems.shekels_foxholes import ShekelsFoxholes
from lpm.problems.six_hump_camel_back import SixHumpCamelBack
from niapy.algorithms.basic import ArtificialBeeColonyAlgorithm
from niapy.algorithms.basic import DifferentialEvolution
from niapy.algorithms.basic import GeneticAlgorithm
from niapy.algorithms.basic import GreyWolfOptimizer
from niapy.algorithms.basic import ParticleSwarmAlgorithm
from niapy.algorithms.basic.de import cross_rand1
from niapy.problems import Ackley
from niapy.problems import Griewank
from niapy.problems import Rastrigin
from niapy.problems import Rosenbrock
from niapy.problems import Sphere
from niapy.problems import SumSquares
from niapy.task import Task


def run_pso(problem, problem_name):
    print("[PSO] " + problem_name + " start")
    results = []
    for i in range(100):
        task = Task(problem=problem, max_evals=15000)
        algorithm = ParticleSwarmAlgorithm(population_size=30, w=0.7, c1=2.0, c2=2.0)
        best_x, best_fit = algorithm.run(task)
        results.append(best_fit)
    with open('results/PSO-NiaPy_' + problem_name + 'D' + str(problem.dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[PSO] " + problem_name + " finish")


def run_gwo(problem, problem_name):
    print("[GWO] " + problem_name + " start")
    results = []
    for i in range(100):
        task = Task(problem=problem, max_evals=15000)
        algorithm = GreyWolfOptimizer(population_size=30)
        best_x, best_fit = algorithm.run(task)
        results.append(best_fit)
    with open('results/GWO-NiaPy_' + problem_name + 'D' + str(problem.dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[GWO] " + problem_name + " finish")


def run_ga(problem, problem_name):
    print("[GA] " + problem_name + " start")
    results = []
    for i in range(100):
        task = Task(problem=problem, max_evals=15000)
        algorithm = GeneticAlgorithm(population_size=100, crossover_rate=0.95, mutation_rate=0.025, tournament_size=2)
        best_x, best_fit = algorithm.run(task)
        results.append(best_fit)
    with open('results/GA-NiaPy_' + problem_name + 'D' + str(problem.dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[GA] " + problem_name + " finish")


def run_de(problem, problem_name):
    print("[DE] " + problem_name + " start")
    results = []
    for i in range(100):
        task = Task(problem=problem, max_evals=15000)
        algorithm = DifferentialEvolution(population_size=50, differential_weight=0.5, crossover_rate=0.9,
                                          strategy=cross_rand1)
        best_x, best_fit = algorithm.run(task)
        results.append(best_fit)
    with open('results/DE-NiaPy_' + problem_name + 'D' + str(problem.dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[DE] " + problem_name + " finish")


def run_abc(problem, problem_name):
    problem.name()
    print("[ABC] " + problem_name + " start")
    results = []
    for i in range(100):
        task = Task(problem=problem, max_evals=15000)
        algorithm = ArtificialBeeColonyAlgorithm(population_size=125, limit=100)
        best_x, best_fit = algorithm.run(task)
        results.append(best_fit)
    with open('results/ABC-NiaPy_' + problem_name + 'D' + str(problem.dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[ABC] " + problem_name + " finish")



sphere = Sphere(dimension=60, lower=-100, upper=100)
sum_of_squares = SumSquares(dimension=60, lower=-100, upper=100)
schwefel2 = Schwefel2(dimension=60, lower=-100, upper=100)
rastrigin = Rastrigin(dimension=60, lower=-5.12, upper=5.12)
ackley = Ackley(dimension=60, lower=-32, upper=32)
griewank = Griewank(dimension=60, lower=-600, upper=600)
rosenbrock = Rosenbrock(dimension=60, lower=-30, upper=30)
shekels_foxholes = ShekelsFoxholes(dimension=2, lower=-65536, upper=65536)
six_hump_camel_back = SixHumpCamelBack(dimension=2, lower=-5, upper=5)
branin = Branin(dimension=2, lower=np.array([-5, 0]), upper=np.array([10, 15]))
goldstein_price = GoldsteinPrice(dimension=2, lower=-2, upper=2)
hartman = Hartman(dimension=3, lower=0, upper=1)

""""""
run_de(sphere, 'Sphere')
run_ga(sphere, 'Sphere')
run_abc(sphere, 'Sphere')
run_gwo(sphere, 'Sphere')
run_pso(sphere, 'Sphere')
""""""
""""""
run_de(sum_of_squares, 'SumOfSquares')
run_ga(sum_of_squares, 'SumOfSquares')
run_abc(sum_of_squares, 'SumOfSquares')
run_gwo(sum_of_squares, 'SumOfSquares')
run_pso(sum_of_squares, 'SumOfSquares')
""""""
""""""
run_de(schwefel2, 'Schwefel')
run_ga(schwefel2, 'Schwefel')
run_abc(schwefel2, 'Schwefel')
run_gwo(schwefel2, 'Schwefel')
run_pso(schwefel2, 'Schwefel')
""""""
""""""
run_de(rastrigin, 'Rastrigin')
run_ga(rastrigin, 'Rastrigin')
run_abc(rastrigin, 'Rastrigin')
run_gwo(rastrigin, 'Rastrigin')
run_pso(rastrigin, 'Rastrigin')
""""""
""""""
run_de(ackley, 'Ackley')
run_ga(ackley, 'Ackley')
run_abc(ackley, 'Ackley')
run_gwo(ackley, 'Ackley')
run_pso(ackley, 'Ackley')
""""""
""""""
run_de(griewank, 'Griewank')
run_ga(griewank, 'Griewank')
run_abc(griewank, 'Griewank')
run_gwo(griewank, 'Griewank')
run_pso(griewank, 'Griewank')
""""""
""""""
run_de(rosenbrock, 'Rosenbrock')
run_ga(rosenbrock, 'Rosenbrock')
run_abc(rosenbrock, 'Rosenbrock')
run_gwo(rosenbrock, 'Rosenbrock')
run_pso(rosenbrock, 'Rosenbrock')
""""""
"""
run_de(shekels_foxholes, 'ShekelsFoxholes')
run_ga(shekels_foxholes, 'ShekelsFoxholes')
run_abc(shekels_foxholes, 'ShekelsFoxholes')
run_gwo(shekels_foxholes, 'ShekelsFoxholes')
run_pso(shekels_foxholes, 'ShekelsFoxholes')
"""
"""
run_de(six_hump_camel_back, 'SixHumpCamelBack')
run_ga(six_hump_camel_back, 'SixHumpCamelBack')
run_abc(six_hump_camel_back, 'SixHumpCamelBack')
run_gwo(six_hump_camel_back, 'SixHumpCamelBack')
run_pso(six_hump_camel_back, 'SixHumpCamelBack')
"""
"""
run_de(branin, 'Branin')
run_ga(branin, 'Branin')
run_abc(branin, 'Branin')
run_gwo(branin, 'Branin')
run_pso(branin, 'Branin')
"""
"""
run_de(goldstein_price, 'GoldsteinPrice')
run_ga(goldstein_price, 'GoldsteinPrice')
run_abc(goldstein_price, 'GoldsteinPrice')
run_gwo(goldstein_price, 'GoldsteinPrice')
run_pso(goldstein_price, 'GoldsteinPrice')
"""
"""
run_de(hartman, 'Hartman')
run_ga(hartman, 'Hartman')
run_abc(hartman, 'Hartman')
run_gwo(hartman, 'Hartman')
run_pso(hartman, 'Hartman')
"""
"""
run_ga(sphere, 'Sphere')
run_ga(sum_of_squares, 'SumOfSquares')
run_ga(schwefel2, 'Schwefel')
run_ga(rastrigin, 'Rastrigin')
run_ga(ackley, 'Ackley')
run_ga(griewank, 'Griewank')
run_ga(rosenbrock, 'Rosenbrock')
run_ga(shekels_foxholes, 'ShekelsFoxholes')
run_ga(six_hump_camel_back, 'SixHumpCamelBack')
run_ga(branin, 'Branin')
run_ga(goldstein_price, 'GoldsteinPrice')
run_ga(hartman, 'Hartman')
"""