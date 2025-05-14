import glob
import os

import numpy as np

from mealpy.swarm_based import ABC
from mealpy.evolutionary_based import DE, GA, ES
from mealpy.swarm_based import GWO
from mealpy.swarm_based import PSO
from mealpy import FloatVar

from problems import SphereShifted
from problems import SumOfSquaresShifted
from problems import Schwefel12Shifted
from problems import RastriginShifted
from problems import AckleyShifted
from problems import GriewankShifted

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

frameworkName = "MEALPY"
directory_path = "data/" #"../../../EARS comparison/Algorithm results/"

term_dict1 = {
    #"max_epoch": 1000,
    "max_fe": 15000,  # 100000 number of function evaluation
    #"max_time": 100000,  # 10 seconds to run the program
    #"max_early_stop": 15  # 15 epochs if the best fitness is not getting better we stop the program
}

num_of_reruns = 50

algorithmNames = ['ABC', 'PSO', 'DE', 'GWO', 'GA', 'CMAES']
#algorithmNames = ['CMAES']

problemNames = ['ShiftedSphere', 'ShiftedSumOfSquares', 'ShiftedSchwefel', 'ShiftedRastrigin', 'ShiftedAckley', 'ShiftedGriewank','Sphere', 'SumOfSquares', 'Schwefel', 'Rastrigin', 'Ackley', 'Griewank', 'Rosenbrock', 'ShekelsFoxholes', 'SixHumpCamelBack', 'Branin', 'GoldsteinPrice', 'Hartman']
numOfDims = [60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 2, 2, 2, 2, 3]

models = [
        ABC.OriginalABC(pop_size=125, couple_bees=(16, 4), patch_variables=(5.0, 0.985), sites=(3, 1), n_limits=100),
        PSO.OriginalPSO(pop_size=30, c1=2.00, c2=2.00, w_min=0.4, w_max=0.7),
        DE.OriginalDE(pop_size=50, wf=0.5, cr=0.9, strategy=0),
        GWO.OriginalGWO(pop_size=30),
        GA.BaseGA(pop_size=100, pc=0.95, pm=0.025),
        #ES.CMA_ES(pop_size=30)
]

problems = [
    SphereShifted(FloatVar(lb=(-100, )*numOfDims[0], ub=(100.,)*numOfDims[0]), minmax="min"),
    SumOfSquaresShifted(FloatVar(lb=(-100, ) * numOfDims[1], ub=(100, ) * numOfDims[1]), minmax="min"),
    Schwefel12Shifted(FloatVar(lb=(-100, ) * numOfDims[2], ub=(100, ) * numOfDims[2]), minmax="min"),
    RastriginShifted(FloatVar(lb=(-5.12, ) * numOfDims[3], ub=(5.12, ) * numOfDims[3]), minmax="min"),
    AckleyShifted(FloatVar(lb=(-32.768, ) * numOfDims[4], ub=(32.768, ) * numOfDims[4]), minmax="min"),
    GriewankShifted(FloatVar(lb=(-600, ) * numOfDims[5], ub=(600, ) * numOfDims[5]), minmax="min"),

    Sphere(FloatVar(lb=(-100, ) * numOfDims[0], ub=(100, ) * numOfDims[0]), minmax="min"),
    SumOfSquares(FloatVar(lb=(-100, ) * numOfDims[1], ub=(100, ) * numOfDims[1]), minmax="min"),
    Schwefel12(FloatVar(lb=(-100, ) * numOfDims[2], ub=(100, ) * numOfDims[2]), minmax="min"),
    Rastrigin(FloatVar(lb=(-5.12, ) * numOfDims[3], ub=(5.12, ) * numOfDims[3]), minmax="min"),
    Ackley(FloatVar(lb=(-32.768, ) * numOfDims[4], ub=(32.768, ) * numOfDims[4]), minmax="min"),
    Griewank(FloatVar(lb=(-600, ) * numOfDims[5], ub=(600, ) * numOfDims[5]), minmax="min"),
    Rosenbrock(FloatVar(lb=(-30, ) * numOfDims[6], ub=(30, ) * numOfDims[6]), minmax="min"),
    Shekelfoxholes(FloatVar(lb=(-65.536, ) * numOfDims[13], ub=(65.536, ) * numOfDims[13]), minmax="min"),
    SixHumpCamelBack(FloatVar(lb=(-5, ) * numOfDims[14], ub=(5, ) * numOfDims[14]), minmax="min"),
    Branin(FloatVar(lb=(-5, 0), ub=(10, 15)), minmax="min"),
    GoldsteinPrice(FloatVar(lb=(-2, ) * numOfDims[16], ub=(2, ) * numOfDims[16]), minmax="min"),
    Hartman(FloatVar(lb=(-0, ) * numOfDims[17], ub=(1, ) * numOfDims[17]), minmax="min")
]

# Remove files
#for file_path in glob.glob(directory_path + "*"):
#    os.remove(file_path)

if(1):
    #for x in range(num_of_reruns):
    for index, model in enumerate(models):
        for j, problem in enumerate(problems):
            best_fitnesses = np.zeros(num_of_reruns)
            for x in range(num_of_reruns):
                # Find the best solution and add it to best_fitness_results
                best = model.solve(problem, termination=term_dict1)
                best_fitnesses[x] = best.target.fitness

            # Write best_fitness to file
            filepath = directory_path + algorithmNames[index] + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(numOfDims[j]) + '.txt'
            np.savetxt(filepath, best_fitnesses, fmt='%.10f')
            #with open(directory_path + frameworkName + '_' + model.name + '_' + str(test_run_num) + '_' + problem.name + '.txt', 'a') as f:
            #with open(directory_path + algorithmNames[index] + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(numOfDims[j]) + '.txt', 'a') as f:
            #    f.write(str(best_fitness) + '\n')

if(0):
    # Test run of algorithms
    print(f"SphereShifted:  { problems[0].obj_func(SphereShifted.shift) }")
    print(f"SumOfSquaresShifted:  { problems[1].obj_func(SumOfSquaresShifted.shift) }")
    print(f"SchwefelShifted:  { problems[2].obj_func(Schwefel12Shifted.shift) }")
    print(f"AckleyShifted:  { problems[3].obj_func(RastriginShifted.shift) }")
    print(f"RastriginShifted:  { problems[4].obj_func(AckleyShifted.shift) }")
    print(f"GriewankShifted:  { problems[5].obj_func(GriewankShifted.shift) }")
    print(f"SphereShifted:  {problems[6].obj_func(np.array([0, ] * 60))}")
    print(f"SumOfSquares:  { problems[7].obj_func(np.array([0, ] * 60)) }")
    print(f"Schwefel12:  { problems[8].obj_func(np.array([0, ] * 60)) }")
    print(f"Rastrigin:  { problems[9].obj_func(np.array([0, ] * 60)) }")
    print(f"Ackley:  { problems[10].obj_func(np.array([0, ] * 60)) }")
    print(f"Griewank:  { problems[11].obj_func(np.array([0, ] * 60)) }")
    print(f"Rosenbrock:  { problems[12].obj_func(np.array([1, ] * 60)) }")
    print(f"Shekel’s Foxholes :  { problems[13].obj_func(np.array([-31.97833, -31.97833])) }")
    print(f"Six-Hump Camel Back :  { problems[14].obj_func(np.array([0.0898, -0.7126])) }")
    print(f"Branin :  { problems[15].obj_func(np.array([-np.pi,12.275])) }")
    print(f"Goldstein-Price :  { problems[16].obj_func(np.array([0, -1])) }")
    print(f"Hartman :  { problems[17].obj_func(np.array([0.114614,0.555649,0.852547])) }")
