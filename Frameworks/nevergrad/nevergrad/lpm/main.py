import numpy as np

import nevergrad as ng
import nevergrad.lpm.problems as problems
from nevergrad.lpm import LogExecution

params1 = ng.p.Instrumentation(
    ng.p.Array(shape=(60,)).set_bounds(lower=-100, upper=100))  # Sphere, Sum of Squares, Schwefel Function12
params_rastrigin = ng.p.Instrumentation(ng.p.Array(shape=(60,)).set_bounds(lower=-5.12, upper=5.12))  # Rastrigin
params_ackley = ng.p.Instrumentation(ng.p.Array(shape=(60,)).set_bounds(lower=-32, upper=32))  # Ackley
params_griewank = ng.p.Instrumentation(ng.p.Array(shape=(60,)).set_bounds(lower=-600, upper=600))  # Griewank
params_rosenbrock = ng.p.Instrumentation(ng.p.Array(shape=(60,)).set_bounds(lower=-30, upper=30))  # Rosenbrock
params_shekels_foxholes = ng.p.Instrumentation(
    ng.p.Array(shape=(2,)).set_bounds(lower=-65.536, upper=65.536))  # Shekel's Foxholes
params_sixhump_camelback = ng.p.Instrumentation(
    ng.p.Array(shape=(2,)).set_bounds(lower=-5, upper=5))  # Six-Hump Camel Back
params_branin = ng.p.Instrumentation(
    ng.p.Array(shape=(2,)).set_bounds(lower=np.array([-5, 0]), upper=np.array([10, 15])))
params_goldstein_price = ng.p.Instrumentation(ng.p.Array(shape=(2,)).set_bounds(lower=-2, upper=2))  # Goldstein-Price
params_hartman = ng.p.Instrumentation(ng.p.Array(shape=(3,)).set_bounds(lower=0, upper=1))  # Hartman


def run_pso(problem, params, problem_name, dimension):
    print("[PSO] " + problem_name + " start")
    results = []
    for i in range(50):
        print("[PSO] Run {}...".format(i + 1))
        optimizer = ng.optimizers.PSO(parametrization=params, budget=15000)
        recommendation = optimizer.minimize(problem)
        results.append(recommendation.loss)
        filename_runs = 'runs/PSO-nevergrad_' + problem_name + '_vars=' + str(dimension) + '_run=' + str(
            i + 1) + '.txt'
        LogExecution.write_improvements_to_file(filename_runs)
    with open('results/PSO-nevergrad_' + problem_name + 'D' + str(dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[PSO] " + problem_name + " finish")


def run_de(problem, params, problem_name, dimension):
    print("[DE] " + problem_name + " start")
    results = []
    for i in range(50):
        print("[DE] Run {}...".format(i + 1))
        optimizer = ng.optimizers.DE(parametrization=params, budget=15000)
        recommendation = optimizer.minimize(problem)
        results.append(recommendation.loss)
        filename_runs = 'runs/DE-nevergrad_' + problem_name + '_vars=' + str(dimension) + '_run=' + str(i + 1) + '.txt'
        LogExecution.write_improvements_to_file(filename_runs)
    with open('results/DE-nevergrad_' + problem_name + 'D' + str(dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[DE] " + problem_name + " finish")


def run_cmaes(problem, params, problem_name, dimension):
    print("[CMA-ES] " + problem_name + " start")
    results = []
    for i in range(50):
        print("[CMA-ES] Run {}...".format(i + 1))
        optimizer = ng.optimizers.PymooCMAES(parametrization=params, budget=15000)
        recommendation = optimizer.minimize(problem)
        results.append(recommendation.loss)
    with open('results/CMA-ES-nevergrad_' + problem_name + 'D' + str(dimension) + '.txt', 'w') as outfile:
        outfile.write("\n".join(str(result) for result in results))
    print("[CMA-ES] " + problem_name + " finish")


#run_pso(problems.shifted_sphere, params1, "ShiftedSphere", 60)
#run_pso(problems.shifted_sum_squares, params1, "ShiftedSumOfSquares", 60)
#run_pso(problems.shifted_schwefel2, params1, "ShiftedSchwefel", 60)
#run_pso(problems.shifted_rastrigin, params_rastrigin, "ShiftedRastrigin", 60)
#run_pso(problems.shifted_ackley, params_ackley, "ShiftedAckley", 60)
#run_pso(problems.shifted_griewank, params_griewank, "ShiftedGriewank", 60)
#run_pso(problems.rosenbrock, params_rosenbrock, "Rosenbrock", 60)
#run_pso(problems.shekels_foxholes, params_shekels_foxholes, "ShekelsFoxholes", 2)
#run_pso(problems.six_hump_camel_back, params_sixhump_camelback, "SixHumpCamelBack", 2)
#run_pso(problems.branin, params_branin, "Branin", 2)
#run_pso(problems.goldstein_price, params_goldstein_price, "GoldsteinPrice", 2)
#run_pso(problems.hartman, params_hartman, "Hartman", 3)

#run_cmaes(problems.shifted_sphere, params1, "ShiftedSphere", 60)

run_de(problems.shifted_sphere, params1, "ShiftedSphere", 60)
run_de(problems.shifted_sum_squares, params1, "ShiftedSumOfSquares", 60)
run_de(problems.shifted_schwefel2, params1, "ShiftedSchwefel", 60)
run_de(problems.shifted_rastrigin, params_rastrigin, "ShiftedRastrigin", 60)
run_de(problems.shifted_ackley, params_ackley, "ShiftedAckley", 60)
run_de(problems.shifted_griewank, params_griewank, "ShiftedGriewank", 60)
run_de(problems.rosenbrock, params_rosenbrock, "Rosenbrock", 60)
run_de(problems.shekels_foxholes, params_shekels_foxholes, "ShekelsFoxholes", 2)
run_de(problems.six_hump_camel_back, params_sixhump_camelback, "SixHumpCamelBack", 2)
run_de(problems.branin, params_branin, "Branin", 2)
run_de(problems.goldstein_price, params_goldstein_price, "GoldsteinPrice", 2)
run_de(problems.hartman, params_hartman, "Hartman", 3)