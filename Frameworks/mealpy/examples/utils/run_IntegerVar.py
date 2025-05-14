#!/usr/bin/env python
# Created by "Thieu" at 13:34, 27/09/2023 ----------%                                                                               
#       Email: nguyenthieu2102@gmail.com            %                                                    
#       Github: https://github.com/thieu1995        %                         
# --------------------------------------------------%

import numpy as np
from mealpy import IntegerVar, Problem


# ## 1. One variable
# bounds = [
#     IntegerVar(lb=-10, ub=10, name="delta"),
# ]

## 2. Multiple variables
bounds = [
    IntegerVar(lb=(-1, )*20, ub=(1, )*20, name="delta")
]

problem = Problem(bounds, obj_func=lambda sol: np.sum(sol**2))
print(f"Problem: {problem}")
print(f"Bounds: {problem.bounds}")
print(problem.lb, problem.ub)

## Generate encoded solution (the real-value solution)
x = problem.generate_solution()
print(x)

x = problem.generate_solution(encoded=False)    # Real world (actual solution - decoded solution) for the problem
x1 = problem.encode_solution(x)                 # Optimizer solution (encoded solution) for the problem
x2 = problem.correct_solution(x1)               # Correct the solution (encoded and bounded solution) for the problem
x3 = problem.decode_solution(x1)                # Real world (actual solution - decoded solution) for the problem
print(f"Real value solution: {x}")
print(f"Encoded solution: {x1}")
print(f"Bounded solution: {x2}")
print(f"Real value solution after decoded: {x3}")
