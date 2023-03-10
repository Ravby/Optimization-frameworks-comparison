#!/usr/bin/env python
# Created by "Thieu" at 18:14, 10/04/2020 ----------%
#       Email: nguyenthieu2102@gmail.com            %
#       Github: https://github.com/thieu1995        %
# --------------------------------------------------%

import numpy as np
from mealpy.optimizer import Optimizer


class OriginalES(Optimizer):
    """
    The original version of: Evolution Strategies (ES)

    Links:
        1. https://www.cleveralgorithms.com/nature-inspired/evolution/evolution_strategies.html

    Hyper-parameters should fine-tune in approximate range to get faster convergence toward the global optimum:
        + lamda (float): [0.5, 1.0], Percentage of child agents evolving in the next generation

    Examples
    ~~~~~~~~
    >>> import numpy as np
    >>> from mealpy.evolutionary_based.ES import OriginalES
    >>>
    >>> def fitness_function(solution):
    >>>     return np.sum(solution**2)
    >>>
    >>> problem_dict1 = {
    >>>     "fit_func": fitness_function,
    >>>     "lb": [-10, -15, -4, -2, -8],
    >>>     "ub": [10, 15, 12, 8, 20],
    >>>     "minmax": "min",
    >>> }
    >>>
    >>> epoch = 1000
    >>> pop_size = 50
    >>> lamda = 0.75
    >>> model = OriginalES(epoch, pop_size, lamda)
    >>> best_position, best_fitness = model.solve(problem_dict1)
    >>> print(f"Solution: {best_position}, Fitness: {best_fitness}")

    References
    ~~~~~~~~~~
    [1] Beyer, H.G. and Schwefel, H.P., 2002. Evolution strategies–a comprehensive introduction. Natural computing, 1(1), pp.3-52.
    """

    ID_POS = 0
    ID_TAR = 1
    ID_STR = 2  # strategy

    def __init__(self, epoch=10000, pop_size=100, lamda=0.75, **kwargs):
        """
        Args:
            epoch (int): maximum number of iterations, default = 10000
            pop_size (int): number of population size (miu in the paper), default = 100
            lamda (float): Percentage of child agents evolving in the next generation, default=0.75
        """
        super().__init__(**kwargs)
        self.epoch = self.validator.check_int("epoch", epoch, [1, 100000])
        self.pop_size = self.validator.check_int("pop_size", pop_size, [10, 10000])
        self.lamda = self.validator.check_float("lamda", lamda, (0, 1.0))
        self.set_parameters(["epoch", "pop_size", "lamda"])

        self.n_child = int(self.lamda * self.pop_size)
        self.nfe_per_epoch = self.n_child
        self.sort_flag = True
    
    def initialize_variables(self):
        self.distance = 0.05 * (self.problem.ub - self.problem.lb)

    def create_solution(self, lb=None, ub=None, pos=None):
        """
        Overriding method in Optimizer class

        Returns:
            list: solution with format [position, target, strategy]
        """
        if pos is None:
            pos = self.generate_position(lb, ub)
        position = self.amend_position(pos, lb, ub)
        target = self.get_target_wrapper(position)
        strategy = np.random.uniform(0, self.distance)
        return [position, target, strategy]

    def evolve(self, epoch):
        """
        The main operations (equations) of algorithm. Inherit from Optimizer class

        Args:
            epoch (int): The current iteration
        """
        child = []
        for idx in range(0, self.n_child):
            pos_new = self.pop[idx][self.ID_POS] + self.pop[idx][self.ID_STR] * np.random.normal(0, 1.0, self.problem.n_dims)
            pos_new = self.amend_position(pos_new, self.problem.lb, self.problem.ub)
            tau = np.sqrt(2.0 * self.problem.n_dims) ** -1.0
            tau_p = np.sqrt(2.0 * np.sqrt(self.problem.n_dims)) ** -1.0
            strategy = np.exp(tau_p * np.random.normal(0, 1.0, self.problem.n_dims) + tau * np.random.normal(0, 1.0, self.problem.n_dims))
            child.append([pos_new, None, strategy])
            if self.mode not in self.AVAILABLE_MODES:
                child[-1][self.ID_TAR] = self.get_target_wrapper(pos_new)
        child = self.update_target_wrapper_population(child)
        self.pop = self.get_sorted_strim_population(child + self.pop, self.pop_size)


class LevyES(OriginalES):
    """
    The developed Levy-flight version: Evolution Strategies (ES)

    Links:
        1. https://www.cleveralgorithms.com/nature-inspired/evolution/evolution_strategies.html

    Notes
    ~~~~~
    The Levy-flight is applied, the flow and equations is changed

    Hyper-parameters should fine-tune in approximate range to get faster convergence toward the global optimum:
        + lamda (float): [0.5, 1.0], Percentage of child agents evolving in the next generation

    Examples
    ~~~~~~~~
    >>> import numpy as np
    >>> from mealpy.evolutionary_based.ES import OriginalES
    >>>
    >>> def fitness_function(solution):
    >>>     return np.sum(solution**2)
    >>>
    >>> problem_dict1 = {
    >>>     "fit_func": fitness_function,
    >>>     "lb": [-10, -15, -4, -2, -8],
    >>>     "ub": [10, 15, 12, 8, 20],
    >>>     "minmax": "min",
    >>> }
    >>>
    >>> epoch = 1000
    >>> pop_size = 50
    >>> lamda = 0.75
    >>> model = OriginalES(epoch, pop_size, lamda)
    >>> best_position, best_fitness = model.solve(problem_dict1)
    >>> print(f"Solution: {best_position}, Fitness: {best_fitness}")

    References
    ~~~~~~~~~~
    [1] Beyer, H.G. and Schwefel, H.P., 2002. Evolution strategies–a comprehensive introduction. Natural computing, 1(1), pp.3-52.
    """

    def __init__(self, epoch=10000, pop_size=100, lamda=0.75, **kwargs):
        """
        Args:
            epoch (int): maximum number of iterations, default = 10000
            pop_size (int): number of population size (miu in the paper), default = 100
            lamda (float): Percentage of child agents evolving in the next generation, default=0.75
        """
        super().__init__(epoch, pop_size, lamda, **kwargs)

    def evolve(self, epoch):
        """
        The main operations (equations) of algorithm. Inherit from Optimizer class

        Args:
            epoch (int): The current iteration
        """
        self.nfe_per_epoch = 2 * self.n_child
        child = []
        for idx in range(0, self.n_child):
            pos_new = self.pop[idx][self.ID_POS] + self.pop[idx][self.ID_STR] * np.random.normal(0, 1.0, self.problem.n_dims)
            pos_new = self.amend_position(pos_new, self.problem.lb, self.problem.ub)
            tau = np.sqrt(2.0 * self.problem.n_dims) ** -1.0
            tau_p = np.sqrt(2.0 * np.sqrt(self.problem.n_dims)) ** -1.0
            strategy = np.exp(tau_p * np.random.normal(0, 1.0, self.problem.n_dims) + tau * np.random.normal(0, 1.0, self.problem.n_dims))
            child.append([pos_new, None, strategy])
            if self.mode not in self.AVAILABLE_MODES:
                child[-1][self.ID_TAR] = self.get_target_wrapper(pos_new)
        child = self.update_target_wrapper_population(child)

        child_levy = []
        for idx in range(0, self.n_child):
            pos_new = self.pop[idx][self.ID_POS] + self.get_levy_flight_step(multiplier=0.001, size=self.problem.n_dims, case=-1)
            pos_new = self.amend_position(pos_new, self.problem.lb, self.problem.ub)
            tau = np.sqrt(2.0 * self.problem.n_dims) ** -1.0
            tau_p = np.sqrt(2.0 * np.sqrt(self.problem.n_dims)) ** -1.0
            stdevs = np.array([np.exp(tau_p * np.random.normal(0, 1.0) + tau * np.random.normal(0, 1.0)) for _ in range(self.problem.n_dims)])
            child_levy.append([pos_new, None, stdevs])
            if self.mode not in self.AVAILABLE_MODES:
                child_levy[-1][self.ID_TAR] = self.get_target_wrapper(pos_new)
        child_levy = self.update_target_wrapper_population(child_levy)
        self.pop = self.get_sorted_strim_population(child + child_levy + self.pop, self.pop_size)
