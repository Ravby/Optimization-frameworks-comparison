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

frameworkName = "MealPy"
directory_path = "data/"

term_dict1 = {
    #"max_epoch": 1000,
    "max_fe": 15000,  # 100000 number of function evaluation
    #"max_time": 100000,  # 10 seconds to run the program
    #"max_early_stop": 15  # 15 epochs if the best fitness is not getting better we stop the program
}

num_of_reruns = 100

algorithmNames = ['PSO', 'DE']
problemNames = ['Sphere', 'SumOfSquares','Schwefel','Rastrigin','Ackley','Griewank','Rosenbrock','ShekelsFoxholes','SixHumpCamelBack','Branin','GoldsteinPrice','Hartman']
numOfDims = [30, 30, 30, 30, 30, 30, 30, 2, 2, 2, 2, 3]

models = [
        #ABC.OriginalABC(pop_size=125, couple_bees=(16, 4), patch_variables=(5.0, 0.985), sites=(3, 1), n_limits=100),
        PSO.OriginalPSO(pop_size=30, c1=2.00, c2=2.00, w_min=0.4, w_max=0.7),
        DE.BaseDE(pop_size=50, wf=0.5, cr=0.9, strategy=0),
        #GWO.OriginalGWO(pop_size=30),
        #GA.BaseGA(pop_size=50, pc=0.95, pm=0.025) # TODO spremeni pop_size
]

problems = [
    Sphere(lb=[-10, ] * numOfDims[0], ub=[10, ] * numOfDims[0], minmax="min"),
    SumOfSquares(lb=[-10, ] * numOfDims[1], ub=[10, ] * numOfDims[1], minmax="min"),
    Schwefel12(lb=[-100, ] * numOfDims[2], ub=[100, ] * numOfDims[2], minmax="min"),
    Rastrigin(lb=[-5.12, ] * numOfDims[3], ub=[5.12, ] * numOfDims[3], minmax="min"),
    Ackley(lb=[-32.768, ] * numOfDims[4], ub=[32.768, ] * numOfDims[4], minmax="min"),
    Griewank(lb=[-600, ] * numOfDims[5], ub=[600, ] * numOfDims[5], minmax="min"),
    Rosenbrock(lb=[-30, ] * numOfDims[6], ub=[30, ] * numOfDims[6], minmax="min"),
    Shekelfoxholes(lb=[-65.536, ] * numOfDims[7], ub=[65.536, ] * numOfDims[7], minmax="min"),  # TODO check why no work
    SixHumpCamelBack(lb=[-5, ] * numOfDims[8], ub=[5, ] * numOfDims[8], minmax="min"),
    Branin(lb=[-5, 0], ub=[10, 15], minmax="min"),
    GoldsteinPrice(lb=[-2, ] * numOfDims[10], ub=[2, ] * numOfDims[10], minmax="min"),
    Hartman(lb=[-0, ] * numOfDims[11], ub=[1, ] * numOfDims[11], minmax="min")
]

# Remove files
for file_path in glob.glob(directory_path + "*"):
    os.remove(file_path)

if(1):
    #for x in range(num_of_reruns):
    for index, model in enumerate(models):
        for j, problem in enumerate(problems):
            best_fitnesses = np.zeros(num_of_reruns)
            for x in range(num_of_reruns):
                # Find the best solution and add it to best_fitness_results
                best_position, best_fitness = model.solve(problem, termination=term_dict1)
                best_fitnesses[x] = best_fitness

            # Write best_fitness to file
            filepath = directory_path + algorithmNames[index] + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(numOfDims[j]) + '.txt'
            np.savetxt(filepath, best_fitnesses, fmt='%.10f')
            #with open(directory_path + frameworkName + '_' + model.name + '_' + str(test_run_num) + '_' + problem.name + '.txt', 'a') as f:
            #with open(directory_path + algorithmNames[index] + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(numOfDims[j]) + '.txt', 'a') as f:
            #    f.write(str(best_fitness) + '\n')

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
