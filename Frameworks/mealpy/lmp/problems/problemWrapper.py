from numpy.f2py.auxfuncs import throw_error

from mealpy import Problem
import numpy as np

class ProblemWrapper(Problem):
    def __init__(self, problem=None, **kwargs):
        self.m_problem = problem
        self.evaluationsCount = 0
        self.improvements = [] # Contains fitness improvements (evaluationNum, fitness)
        super().__init__(problem.bounds, problem.minmax, **kwargs)
        self.name = problem.name

    def obj_func(self, solution):
        self.evaluationsCount += 1

        fitness = self.m_problem.obj_func(solution)

        # Check if fitness of the last improvement in improvements is bigger than fitness (if yes, add fitness to the improvements)
        if self.minmax == "min":
            # For minimization, lower fitness is better
            if not self.improvements or fitness < self.improvements[-1][1]:
                self.improvements.append((self.evaluationsCount, fitness))
        else:
            # For maximization, higher fitness is better
            if not self.improvements or fitness > self.improvements[-1][1]:
                self.improvements.append((self.evaluationsCount, fitness))

        return fitness

    def reset(self):
        self.evaluationsCount = 0
        self.improvements = []

    def write_improvements_to_file(self, filename):
        with open(filename, 'w') as f:
            f.write(f"Evaluations,Fitness\n")
            for eval_num, fitness in self.improvements:
                f.write(f"{eval_num},{fitness}\n")
        self.reset()
