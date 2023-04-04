import glob
import os

import numpy as np

from mealpy.swarm_based import ABC
from mealpy.evolutionary_based import DE, GA
from mealpy.swarm_based import GWO
from mealpy.swarm_based import PSO

from problems import Sphere
from problems import SumOfSquares
from problems import Schwefel12
from problems import Rastrigin
from problems import Ackley
from problems import Griewank
from problems import Rosenbrock
from problems import Shekelfoxholes
from problems import SixHumpCamelBack
from problems import Branin
from problems import GoldsteinPrice
from problems import Hartman

framework_name = "MealPy"
test_run_num = 1
directory_path = "data/"

term_dict1 = {
    #"max_epoch": 1000,
    "max_fe": 100,  # 100000 number of function evaluation
    #"max_time": 100000,  # 10 seconds to run the program
    #"max_early_stop": 15  # 15 epochs if the best fitness is not getting better we stop the program
}

pop_size = 50
num_of_reruns = 10

print(f"ABC, DE, GA, GWO, PSO algorithms test")

models = [
        ABC.OriginalABC(pop_size=pop_size, couple_bees=(16, 4), patch_variables=(5.0, 0.985), sites=(3, 1)),
        DE.BaseDE(pop_size=pop_size, wf=1.0, cr=0.9, strategy=0),
        GA.BaseGA(pop_size=pop_size, pc=0.95, pm=0.025),
        GWO.OriginalGWO(pop_size=pop_size),
        PSO.OriginalPSO(pop_size=pop_size, c1=2.05, c2=2.05, w_min=0.4, w_max=0.9)
]

num_of_dimension_1 = 10
num_of_dimension_2 = 2
num_of_dimension_3 = 3

problems = [
    Sphere(lb=[-10, ] * num_of_dimension_1, ub=[10, ] * num_of_dimension_1, minmax="min"),
    SumOfSquares(lb=[-10, ] * num_of_dimension_1, ub=[10, ] * num_of_dimension_1, minmax="min"),
    Schwefel12(lb=[-100, ] * num_of_dimension_1, ub=[100, ] * num_of_dimension_1, minmax="min"),
    Rastrigin(lb=[-5.12, ] * num_of_dimension_1, ub=[5.12, ] * num_of_dimension_1, minmax="min"),
    Ackley(lb=[-32.768, ] * num_of_dimension_1, ub=[32.768, ] * num_of_dimension_1, minmax="min"),
    Griewank(lb=[-600, ] * num_of_dimension_1, ub=[600, ] * num_of_dimension_1, minmax="min"),
    Rosenbrock(lb=[-30, ] * num_of_dimension_1, ub=[30, ] * num_of_dimension_1, minmax="min"),
    Shekelfoxholes(lb=[-65.536, ] * num_of_dimension_2, ub=[65.536, ] * num_of_dimension_2, minmax="min"), # TODO check why no work
    SixHumpCamelBack(lb=[-5, ] * num_of_dimension_2, ub=[5, ] * num_of_dimension_2, minmax="min"),
    Branin(lb=[-5, 0], ub=[10, 15], minmax="min"),
    GoldsteinPrice(lb=[-2, ] * num_of_dimension_2, ub=[2, ] * num_of_dimension_2, minmax="min"),
    Hartman(lb=[-0, ] * num_of_dimension_3, ub=[1, ] * num_of_dimension_3, minmax="min")
]

# Remove files if already exist for current test_run_num
for file_path in glob.glob(directory_path + "*"):
    if file_path.__contains__('_' + str(test_run_num) + '_'):
        os.remove(file_path)
if(1):
    for x in range(num_of_reruns):
        for index, model in enumerate(models):
            best_fitnesses = []
            for problem in problems:
                # Find the best solution and add it to best_fitness_results
                best_position, best_fitness = model.solve(problem, termination=term_dict1)
                # Write best_fitness to file
                with open(directory_path + framework_name + '_' + model.name + '_' + str(
                        test_run_num) + '_' + problem.name + '.txt', 'a') as f:
                    f.write(str(best_fitness) + '\n')
                #print(f"Algorithm: {model.name}, Problem: {problem.name} --> Best solution: {best_position},\nBest fitness: {best_fitness}")


if(0):
    # Test run of algorithms
    print(f"Sphere:  { problems[0].fit_func(np.array([0, ] * 10)) }")
    print(f"SumOfSquares:  { problems[1].fit_func(np.array([0, ] * 10)) }")
    print(f"Schwefel12:  { problems[2].fit_func(np.array([0, ] * 10)) }")
    print(f"Rastrigin:  { problems[3].fit_func(np.array([0, ] * 10)) }")
    print(f"Ackley:  { problems[4].fit_func(np.array([0, ] * 10)) }")
    print(f"Griewank:  { problems[5].fit_func(np.array([0, ] * 10)) }")
    print(f"Rosenbrock:  { problems[6].fit_func(np.array([1, ] * 10)) }")
    print(f"Shekel’s Foxholes :  { problems[7].fit_func(np.array([-31.97833, -31.97833])) }")
    print(f"Six-Hump Camel Back :  { problems[8].fit_func(np.array([0.0898, -0.7126])) }")
    print(f"Branin :  { problems[9].fit_func(np.array([-np.pi,12.275])) }")
    print(f"Goldstein-Price :  { problems[10].fit_func(np.array([0, -1])) }")
    print(f"Hartman :  { problems[11].fit_func(np.array([0.114614,0.555649,0.852547])) }")
