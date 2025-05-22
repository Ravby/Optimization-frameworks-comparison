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


#    example which maximizes the sum of a list of integers
#    each of which can be 0 or 1

import random

from deap import base
from deap import benchmarks
from deap import creator
from deap import tools

import glob
import os
import typing
import numpy as np

from deap.benchmarks import LogExecution

frameworkName = "DEAP"
directoryPath = "../../lpm/data/"
directoryPathRuns = "../../lpm/data/runs/"
#directoryPath = "../../../../EARS comparison/Algorithm results/"

fitnesEvals = 15000
numOfReruns = 50
algorithmName = 'GA'
numOfDims = [60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 2, 2, 2, 2, 3]
popSize = 100
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


creator.create("FitnessMin", base.Fitness, weights=(-1.0,))
creator.create("Individual", list, fitness=creator.FitnessMin)

# the goal ('fitness') function to be maximized
def evalOneMax(individual):
    return sum(individual),


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
        toolbox.register("individual", tools.initRepeat, creator.Individual,
                     toolbox.attr_float, numOfDim)

    # define the population to be a list of individuals
    toolbox.register("population", tools.initRepeat, list, toolbox.individual)

    # ----------
    # Operator registration
    # ----------
    # register the goal / fitness function
    toolbox.register("evaluate", problem)

    # CXPB  is the probability with which two individuals
    #       are crossed
    #
    # MUTPB is the probability for mutating an individual
    CXPB, MUTPB = 0.95, 0.025

    # register the crossover operator
    #toolbox.register("mate", tools.cxTwoPoint)

    toolbox.register("mate", tools.cxSimulatedBinaryBounded, low=lb, up=ub, eta=20.0)

    # register a mutation operator with a probability to
    # flip each attribute/gene of 0.05
    #toolbox.register("mutate", tools.mutFlipBit, indpb=0.025)
    toolbox.register("mutate", tools.mutPolynomialBounded, low=lb, up=ub, eta=20.0, indpb=MUTPB)

    # operator for selecting individuals for breeding the next
    # generation: each individual of the current generation
    # is replaced by the 'fittest' (best) of three individuals
    # drawn randomly from the current generation.
    toolbox.register("select", tools.selTournament, tournsize=2)
    # ----------

    #random.seed(64)

    # create an initial population
    # each individual is a list of integers)
    pop = toolbox.population(n=popSize)


    #print("Start of evolution")

    # Evaluate the entire population
    fitnesses = list(map(toolbox.evaluate, pop))
    for ind, fit in zip(pop, fitnesses):
        ind.fitness.values = fit

    #print("  Evaluated %i individuals" % len(pop))

    # Extracting all the fitnesses of 
    fits = [ind.fitness.values[0] for ind in pop]

    # Variable keeping track of the number of generations
    g = 0
    GEN = fitnesEvals / popSize

    # Begin the evolution
    while g < GEN:
        # A new generation
        g = g + 1
        #print("-- Generation %i --" % g)

        # Select the next generation individuals
        offspring = toolbox.select(pop, len(pop))
        # Clone the selected individuals
        offspring = list(map(toolbox.clone, offspring))

        # Apply crossover and mutation on the offspring
        for child1, child2 in zip(offspring[::2], offspring[1::2]):

            # cross two individuals with probability CXPB
            if random.random() < CXPB:
                toolbox.mate(child1, child2)

                # fitness values of the children
                # must be recalculated later
                del child1.fitness.values
                del child2.fitness.values

        for mutant in offspring:

            # mutate an individual with probability MUTPB
            if random.random() < MUTPB:
                toolbox.mutate(mutant)
                del mutant.fitness.values

        # Evaluate the individuals with an invalid fitness
        invalid_ind = [ind for ind in offspring if not ind.fitness.valid]
        fitnesses = map(toolbox.evaluate, invalid_ind)
        for ind, fit in zip(invalid_ind, fitnesses):
            ind.fitness.values = fit

        #print("  Evaluated %i individuals" % len(invalid_ind))

        # The population is entirely replaced by the offspring
        pop[:] = offspring

        # Gather all the fitnesses in one list and print the stats
        fits = [ind.fitness.values[0] for ind in pop]


    #print("-- End of (successful) evolution --")

    best_ind = tools.selBest(pop, 1)[0]
    print("Best individual is  %s" % (best_ind.fitness.values))
    return best_ind

if __name__ == "__main__":
    # Remove files
    #for file_path in glob.glob(directoryPath + "*GA*"):
    #    os.remove(file_path)

    print("GA - Algorithm")

    for j, problem in enumerate(problems):
        best_fitnesses = np.zeros(numOfReruns)
        print("Problem: ", problemNames[j])
        for r in range(numOfReruns):
            best = main(numOfDim=numOfDims[j], lb=lbs[j], ub=ubs[j], problem=problems[j])
            best_fitnesses[r] = best.fitness.values[0]

            filename = directoryPathRuns + "GA" + '-' + frameworkName + '_' + problemNames[
                j] + "_vars=" + str(numOfDims[j]) + "_run=" + str(r) + ".csv"
            LogExecution.write_improvements_to_file(filename)

            print("Best fitness: " + str(best.fitness.values[0]))

        # Write best_fitness to file
        filepath = directoryPath + algorithmName + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(
            numOfDims[j]) + '.txt'
        np.savetxt(filepath, best_fitnesses, fmt='%.10f')

    #main(2, [-5, 0], [10, 15], benchmarks.branin)
