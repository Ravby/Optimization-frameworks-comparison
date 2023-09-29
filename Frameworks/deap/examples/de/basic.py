#    This file is part of EAP.
#
#    EAP is free software: you can redistribute it and/or modify
#    it under the terms of the GNU Lesser General Public License as
#    published by the Free Software Foundation, either version 3 of
#    the License, or (at your option) any later version.
#
#    EAP is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
#    GNU Lesser General Public License for more details.
#
#    You should have received a copy of the GNU Lesser General Public
#    License along with EAP. If not, see <http://www.gnu.org/licenses/>.

import random
import array

import numpy as np

from deap import base
from deap import benchmarks
from deap import creator
from deap import tools

import glob
import os
import typing


frameworkName = "DEAP"
directoryPath = "../../lpm/data/"
fitnesEvals = 15000
numOfReruns = 100
algorithmName = 'DE'
numOfDims = [30, 30, 30, 30, 30, 30, 30, 2, 2, 2, 2, 3]
popSize = 50
problems = [benchmarks.sphere, benchmarks.sumOfSquares, benchmarks.swefel12, benchmarks.rastrigin, benchmarks.ackley, benchmarks.griewank, benchmarks.rosenbrock, benchmarks.shekelFoxholes, benchmarks.sixHumpCamelBack, benchmarks.branin, benchmarks.goldsteinPrice, benchmarks.hartman]
problemNames = ['Sphere', 'SumOfSquares', 'Schwefel', 'Rastrigin', 'Ackley', 'Griewank', 'Rosenbrock', 'ShekelsFoxholes', 'SixHumpCamelBack', 'Branin', 'GoldsteinPrice', 'Hartman']
lbs = [-100, -100, -100, -5.12, -32, -600, -30, -65.536, -5, [-5, 0], -2, 0]
ubs = [100, 100, 100, 5.12, 32, 600, 30, 65.536, 5, [10, 15], 2, 1]


creator.create("FitnessMin", base.Fitness, weights=(-1.0,))
creator.create("Individual", array.array, typecode='d', fitness=creator.FitnessMin)

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

    toolbox.register("population", tools.initRepeat, list, toolbox.individual)
    toolbox.register("select", tools.selTournament, k=3, tournsize=2)
    toolbox.register("evaluate", problem)
    # Differential evolution parameters
    CR = 0.9
    F = 0.5
    NGEN = int(fitnesEvals / popSize)

    pop = toolbox.population(n=popSize)
    hof = tools.HallOfFame(1)
    stats = tools.Statistics(lambda ind: ind.fitness.values)
    stats.register("avg", np.mean)
    stats.register("std", np.std)
    stats.register("min", np.min)
    stats.register("max", np.max)

    logbook = tools.Logbook()
    logbook.header = "gen", "evals", "std", "min", "avg", "max"

    # Evaluate the individuals
    fitnesses = toolbox.map(toolbox.evaluate, pop)
    for ind, fit in zip(pop, fitnesses):
        ind.fitness.values = fit

    record = stats.compile(pop)
    logbook.record(gen=0, evals=len(pop), **record)
    #print(logbook.stream)

    for g in range(1, NGEN):
        for k, agent in enumerate(pop):
            a,b,c = toolbox.select(pop)
            y = toolbox.clone(agent)
            index = random.randrange(numOfDim)
            for i, value in enumerate(agent):
                if i == index or random.random() < CR:
                    y[i] = a[i] + F*(b[i]-c[i])
            y.fitness.values = toolbox.evaluate(y)
            if y.fitness > agent.fitness:
                pop[k] = y
        hof.update(pop)
        record = stats.compile(pop)
        logbook.record(gen=g, evals=len(pop), **record)
        #print(logbook.stream)

    print("Best individual is ", hof[0].fitness.values[0])
    return hof[0]

def rename_files(directory, old_substring, new_substring):
    for filename in os.listdir(directory):
        if filename.endswith(".txt"):  # Specify the file extension if necessary
            new_filename = filename.replace(old_substring, new_substring)
            old_path = os.path.join(directory, filename)
            new_path = os.path.join(directory, new_filename)
            os.rename(old_path, new_path)

if __name__ == "__main__":
    # Remove files
    #for file_path in glob.glob(directoryPath + "*DE*"):
    #    os.remove(file_path)

    for j, problem in enumerate(problems):
        best_fitnesses = np.zeros(numOfReruns)
        print("Problem: ", problemNames[j])
        for x in range(numOfReruns):
            best = main(numOfDim=numOfDims[j], lb=lbs[j], ub=ubs[j], problem=problems[j])
            best_fitnesses[x] = best.fitness.values[0]
        # Write best_fitness to file
        filepath = directoryPath + algorithmName + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(numOfDims[j]) + '.txt'
        np.savetxt(filepath, best_fitnesses, fmt='%.10f')


    #main(2, [-5, 0], [10, 15], benchmarks.branin)
