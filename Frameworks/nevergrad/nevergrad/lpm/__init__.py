from functools import wraps


# Define the wrapper (decorator) for logging
class LogExecution:
    evaluationsCount = 0
    improvements = []

    def __init__(self, func):
        self.func = func
        wraps(func)(self)

    def __get__(self, obj, objtype):
        """Support instance methods by returning a wrapper that binds the instance"""

        @wraps(self.func)
        def wrapper(*args, **kwargs):
            LogExecution.evaluationsCount += 1
            fitness = self.func(obj, *args, **kwargs)
            if not LogExecution.improvements or fitness < LogExecution.improvements[-1][1]:
                LogExecution.improvements.append((LogExecution.evaluationsCount, fitness))
            return fitness

        return wrapper

    def __call__(self, *args, **kwargs):
        LogExecution.evaluationsCount += 1

        fitness = self.func(*args, **kwargs)
        if not LogExecution.improvements or fitness < LogExecution.improvements[-1][1]:
            LogExecution.improvements.append((LogExecution.evaluationsCount, fitness))

        return fitness

    @staticmethod
    def reset():
        LogExecution.evaluationsCount = 0
        LogExecution.improvements = []

    @staticmethod
    def write_improvements_to_file(filename):
        with open(filename, 'w') as f:
            f.write(f"Evaluations,Fitness\n")
            for eval_num, fitness in LogExecution.improvements:
                f.write(f"{eval_num},{fitness}\n")
        LogExecution.reset()
