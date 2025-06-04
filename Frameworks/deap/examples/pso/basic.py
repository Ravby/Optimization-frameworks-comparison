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

import operator
import random

import numpy as np
import math

from deap import base
from deap import benchmarks
from deap import creator
from deap import tools

import glob
import os
import typing

from deap.benchmarks import LogExecution

frameworkName = "DEAP"
directoryPath = "../../lpm/data/"
directoryPathRuns = "../../lpm/data/runs/"
#directoryPath = "../../../../EARS comparison/Algorithm results/"

fitnesEvals = 15000
numOfReruns = 50
algorithmName = 'PSO'
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

#problems = [benchmarks.sphere, benchmarks.rastrigin]
#problemNames = ['Sphere', 'Rastrigin']

creator.create("FitnessMin", base.Fitness, weights=(-1.0,))
creator.create("Particle", list, fitness=creator.FitnessMin, speed=list,
    smin=None, smax=None, best=None)

#def generate(size, pmin, pmax, smin, smax):
#    part = creator.Particle(random.uniform(pmin, pmax) for _ in range(size))
#    part.speed = [random.uniform(smin, smax) for _ in range(size)]
#    part.smin = smin
#    part.smax = smax
#    return part


def generate(size, pmin, pmax, smin, smax):
    part = creator.Particle()
    part.extend(random.uniform(pmin[i], pmax[i]) for i in range(size))
    part.speed = [random.uniform(smin, smax) for _ in range(size)]
    part.smin = smin
    part.smax = smax
    part.pmin = pmin
    part.pmax = pmax
    return part


def updateParticle(part, best, phi1, phi2):
    u1 = (random.uniform(0, phi1) for _ in range(len(part)))
    u2 = (random.uniform(0, phi2) for _ in range(len(part)))
    v_u1 = map(operator.mul, u1, map(operator.sub, part.best, part))
    v_u2 = map(operator.mul, u2, map(operator.sub, best, part))
    part.speed = list(map(operator.add, part.speed, map(operator.add, v_u1, v_u2)))
    for i, speed in enumerate(part.speed):
        if abs(speed) < part.smin:
            part.speed[i] = math.copysign(part.smin, speed)
        elif abs(speed) > part.smax:
            part.speed[i] = math.copysign(part.smax, speed)
    part[:] = list(map(operator.add, part, part.speed))

def main(numOfDim, lb, ub, problem):
    toolbox = base.Toolbox()
    toolbox.register("particle", generate, size=numOfDim, pmin=lb, pmax=ub, smin=-3, smax=3)
    toolbox.register("population", tools.initRepeat, list, toolbox.particle)
    toolbox.register("update", updateParticle, phi1=2.0, phi2=2.0)
    toolbox.register("evaluate", problem)

    pop = toolbox.population(n=popSize)
    stats = tools.Statistics(lambda ind: ind.fitness.values)
    stats.register("avg", np.mean)
    stats.register("std", np.std)
    stats.register("min", np.min)
    stats.register("max", np.max)

    logbook = tools.Logbook()
    logbook.header = ["gen", "evals"] + stats.fields

    GEN = int(fitnesEvals / popSize)
    best = None

    for g in range(GEN):
        for part in pop:
            part.fitness.values = toolbox.evaluate(part)
            if not part.best or part.best.fitness < part.fitness:
                part.best = creator.Particle(part)
                part.best.fitness.values = part.fitness.values
            if not best or best.fitness < part.fitness:
                best = creator.Particle(part)
                best.fitness.values = part.fitness.values
        for part in pop:
            toolbox.update(part, best)

        # Gather all the fitnesses in one list and print the stats
        logbook.record(gen=g, evals=len(pop), **stats.compile(pop))
        #print(logbook.stream)

    print("Best individual is ", best.fitness.values[0])

    return pop, logbook, best

if __name__ == "__main__":
    # Remove files
    #for file_path in glob.glob(directoryPath + "*PSO*"):
    #    os.remove(file_path)
    print("PSO - Algorithm")
    for j, problem in enumerate(problems):
        best_fitnesses = np.zeros(numOfReruns)
        print("Problem: ", problemNames[j])
        for r in range(numOfReruns):
            if isinstance(lbs[j], typing.List):
                pop, logbook, best = main(numOfDim=numOfDims[j], lb=lbs[j], ub=ubs[j], problem=problems[j])
            else:
                pop, logbook, best = main(numOfDim=numOfDims[j], lb=[lbs[j], ] * numOfDims[j], ub=[ubs[j], ] * numOfDims[j], problem=problems[j])
            best_fitnesses[r] = best.fitness.values[0]

            filename = directoryPathRuns + "PSO" + '-' + frameworkName + '_' + problemNames[
                j] + "_vars=" + str(numOfDims[j]) + "_run=" + str(r + 1) + ".csv"
            LogExecution.write_improvements_to_file(filename)

            print("Best fitness: " + str(best.fitness.values[0]))

        # Write best_fitness to file
        filepath = directoryPath + algorithmName + '-' + frameworkName + '_' + problemNames[j] + 'D' + str(numOfDims[j]) + '.txt'
        np.savetxt(filepath, best_fitnesses, fmt='%.10f')
