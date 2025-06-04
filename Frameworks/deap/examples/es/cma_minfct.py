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

import numpy as np

from deap import algorithms
from deap import base
from deap import benchmarks
from deap import cma
from deap import creator
from deap import tools

import glob
import os
import typing

import random
import array

from deap.benchmarks import LogExecution

frameworkName = "DEAP"
directoryPath = "../../lpm/data/"
directoryPathRuns = "../../lpm/data/runs/"
#directoryPath = "../../../../EARS comparison/Algorithm results/"

fitnesEvals = 15000
numOfReruns = 50
algorithmName = 'CMA-ES'
numOfDims = [60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 2, 2, 2, 2, 3]
popSize = 30
problems = [benchmarks.sphereShifted, benchmarks.sumOfSquaresShifted, benchmarks.schwefel12Shifted,
            benchmarks.rastriginShifted, benchmarks.ackleyShifted, benchmarks.griewankShifted,
            benchmarks.sphere, benchmarks.sumOfSquares, benchmarks.schwefel12, benchmarks.rastrigin, benchmarks.ackley,
            benchmarks.griewank, benchmarks.rosenbrock, benchmarks.shekelFoxholes, benchmarks.sixHumpCamelBack,
            benchmarks.branin, benchmarks.goldsteinPrice, benchmarks.hartman]
problemNames = ['ShiftedSphere', 'ShiftedSumOfSquares', 'ShiftedSchwefel', 'ShiftedRastrigin', 'ShiftedAckley',
                'ShiftedGriewank', 'Sphere', 'SumOfSquares', 'Schwefel', 'Rastrigin', 'Ackley', 'Griewank',
                'Rosenbrock', 'ShekelsFoxholes', 'SixHumpCamelBack', 'Branin', 'GoldsteinPrice', 'Hartman']
lbs = [-100, -100, -100, -5.12, -32, -600, -100, -100, -100, -5.12, -32, -600, -30, -65.536, -5, [-5, 0], -2, 0]
ubs = [100, 100, 100, 5.12, 32, 600, 100, 100, 100, 5.12, 32, 600, 30, 65.536, 5, [10, 15], 2, 1]

# GENS
NGEN = int(fitnesEvals / popSize)

creator.create("FitnessMin", base.Fitness, weights=(-1.0,))
creator.create("Individual", list, fitness=creator.FitnessMin)

def attr_float(lower_bound, upper_bound):
    return random.uniform(lower_bound, upper_bound)

def main(numOfDim, lb, ub, problem):
    toolbox = base.Toolbox()

    if isinstance(lb, typing.List):
        toolbox.register("attr_float_dim1", attr_float, lower_bound=lb[0], upper_bound=ub[0])
        toolbox.register("attr_float_dim2", attr_float, lower_bound=lb[1], upper_bound=ub[1])
        toolbox.register("individual", tools.initCycle, creator.Individual,
                         (toolbox.attr_float_dim1, toolbox.attr_float_dim2))
    else:
        toolbox.register("attr_float", random.uniform, lb, ub)
        toolbox.register("individual", tools.initRepeat, creator.Individual, toolbox.attr_float, numOfDim)

    toolbox.register("evaluate", problem)

    # The CMA-ES algorithm takes a population of one individual as argument
    # The centroid is set to a vector of 5.0 see http://www.lri.fr/~hansen/cmaes_inmatlab.html
    # for more details about the rastrigin and other tests for CMA-ES    
    strategy = cma.Strategy(centroid=toolbox.individual(), sigma=0.5, lambda_=popSize)
    toolbox.register("generate", strategy.generate, creator.Individual)
    toolbox.register("update", strategy.update)

    hof = tools.HallOfFame(1)
    stats = tools.Statistics(lambda ind: ind.fitness.values)
    stats.register("avg", np.mean)
    stats.register("std", np.std)
    stats.register("min", np.min)
    stats.register("max", np.max)

    # The CMA-ES algorithm converge with good probability with those settings
    algorithms.eaGenerateUpdate(toolbox, ngen=NGEN, stats=stats, halloffame=hof, verbose=None)

    # print "Best individual is %s, %s" % (hof[0], hof[0].fitness.values)
    return hof[0].fitness.values[0]

if __name__ == "__main__":
    print("CMA-ES - Algorithm")

    for j, problem in enumerate(problems):
        best_fitnesses = np.zeros(numOfReruns)
        print("Problem: ", problemNames[j])
        for r in range(numOfReruns):
            best_fitness = main(numOfDim=numOfDims[j], lb=lbs[j], ub=ubs[j], problem=problems[j])
            best_fitnesses[r] = best_fitness

            filename = directoryPathRuns + "CMA-ES" + '-' + frameworkName + '_' + problemNames[
                j] + "_vars=" + str(numOfDims[j]) + "_run=" + str(r + 1) + ".csv"
            LogExecution.write_improvements_to_file(filename)

            print("Best fitness: " + str(best_fitness))
        # Write best_fitness to file
        filepath = directoryPath + algorithmName + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(numOfDims[j]) + '.txt'
        np.savetxt(filepath, best_fitnesses, fmt='%.10f')


    #main(2, [-5, 0], [10, 15], benchmarks.branin)

