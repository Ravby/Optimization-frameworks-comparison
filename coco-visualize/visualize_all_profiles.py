from tqdm import tqdm
from cocoviz import indicator as ind
import itertools
import pandas as pd
import matplotlib.pyplot as plt
import polars as pl
import numpy as np
from pathlib import Path
from cocoviz import ProblemDescription, Result, ResultSet, Indicator, rtpplot

project_dir = Path.cwd()
parent_dir = project_dir.parent
files_dir = parent_dir / "EARS comparison" / "Algorithm results" / "Runs"
DATA_DIR = Path(files_dir)
OUTPUT_DIR = Path(parent_dir / "EARS comparison" / "Problem profiles")

def load_results(algorithms, functions, vars, runs, data_dir, max_evaluations=15000):
    """
    Load and validate experimental results from CSV files.

    Parameters:
    -----------
    algorithms : list
        List of algorithm names
    functions : list
        List of tuples (function_name, optimum_value)
    vars : list
        List of variable dimensions
    runs : list
        List of run numbers
    data_dir : Path
        Directory containing the data files
    max_evaluations : int, optional
        Maximum number of evaluations to include (default: 15000)

    Returns:
    --------
    ResultSet
        Collection of validated results

    Raises:
    -------
    ValueError
        If no valid results were loaded
    """
    all_results = list(itertools.product(algorithms, [f[0] for f in functions], vars, runs))
    results = ResultSet()

    for alg, fn, var, run in tqdm(all_results):
        file_path = data_dir / f"{alg}_{fn}_vars={var}_run={run}.csv"
        if not file_path.exists():
            print(f"Warning: CSV file not found: {file_path}")
            continue
        try:
            data = pd.read_csv(file_path)
            if "Fitness" not in data.columns:
                print(f"Error: 'Fitness' column missing in {file_path}")
                continue
            if "Evaluations" not in data.columns:
                print(f"Error: 'Evaluations' column missing in {file_path}")
                continue
            data = data[data["Evaluations"] <= max_evaluations]
            if data.empty:
                print(f"Warning: Empty data after filtering for {file_path}")
                continue
            if data["Fitness"].isna().any():
                print(f"Warning: NaN values in 'Fitness' column for {file_path}")
                continue
            problem = ProblemDescription(fn, 1, var, 3)
            result = Result(alg, problem, data, "Evaluations")
            results.append(result)
        except Exception as e:
            print(f"Error processing {file_path}: {e}")
            continue
    if not results:
        raise ValueError("No valid results were loaded. Check CSV files and data.")
    return results


def generate_log_targets(results: ResultSet, indicator: ind.Indicator, min: float | str, number_of_targets: int = 101):
    indicator = ind.resolve(indicator)
    targets = {}
    for desc, problem_results in results.by_problem():
        indicator_values = pl.concat([r._data for r in problem_results])[indicator.name]
        low = min
        high = indicator_values.max()
        delta = high - low
        mul = np.logspace(-16, 0, number_of_targets)
        if low == high:
            targets[desc] = np.linspace(low, high, 1)
        elif indicator.larger_is_better:
            targets[desc] = low + delta * mul
        else:
            targets[desc] = np.flip(low + delta * mul)
    return targets


def generate_plots_for_algorithm_group(group_name, algorithms, functions_d60, functions_d3, functions_d2, vars_d60,
                                       vars_d3, vars_d2, runs, data_dir, indicator, output_dir,
                                       max_evaluations=15000):
    """
    Generate and save RTP plots for a single algorithm group.

    Parameters:
    -----------
    group_name : str
        Name of the algorithm group (e.g., 'PSO')
    algorithms : list
        List of algorithm names
    functions_d60, functions_d3, functions_d2 : list
        Lists of (function_name, optimum_value) tuples for different dimensions
    vars_d60, vars_d3, vars_d2 : list
        Lists of variable dimensions
    runs : list
        List of run numbers
    data_dir : Path
        Directory containing data files
    indicator : Indicator
        Performance indicator for plotting
    output_dir : str or Path
        Directory to save output plots (default: current directory)
    max_evaluations : int
        Maximum number of evaluations (default: 15000)
    """
    print(f"Processing algorithm group: {group_name}")
    output_dir.mkdir(exist_ok=True)

    # Load results for each dimension
    results_d60 = load_results(algorithms, functions_d60, vars_d60, runs, data_dir, max_evaluations)
    results_d3 = load_results(algorithms, functions_d3, vars_d3, runs, data_dir, max_evaluations)
    results_d2 = load_results(algorithms, functions_d2, vars_d2, runs, data_dir, max_evaluations)

    # Combine all functions
    functions_all = functions_d60 + functions_d3 + functions_d2

    # Merge results
    problem_results = []
    for result_set in [results_d60, results_d3, results_d2]:
        problem_results.extend(list(result_set.by_problem()))

    # Generate custom targets
    targets_all = {}
    for func_name, optimum in functions_all:
        for desc, problem_result_set in problem_results:
            if desc.name == func_name:
                try:
                    targets_all[desc] = generate_log_targets(problem_result_set, indicator, optimum,
                                                             number_of_targets=1001).get(desc)
                except Exception as e:
                    print(f"Error generating targets for {func_name}: {e}")
                    targets_all[desc] = None
                break

    # Split functions into three columns
    n_functions = len(functions_all)
    n_cols = 3
    n_rows = 4
    col_size = n_functions // n_cols
    col1_functions = functions_all[:col_size]
    col2_functions = functions_all[col_size:2 * col_size]
    col3_functions = functions_all[2 * col_size:]

    # Set color cycle
    colors = plt.cm.tab20(np.linspace(0, 1, len(algorithms)))
    plt.rcParams['axes.prop_cycle'] = plt.cycler(color=colors)

    #n_algorithms = len(algorithms)
    #colors = plt.cm.tab10(np.linspace(0, 1, 10))  # Get 10 distinct colors from tab10
    #colors = np.tile(colors, (n_algorithms + 9) // 10)[:n_algorithms]  # Repeat colors to match n_algorithms
    #linestyles = ['-' for _ in range(min(10, n_algorithms))] + ['--' for _ in range(
    #    max(0, n_algorithms - 10))]  # Solid for first 10, dashed for rest
    #plt.rcParams['axes.prop_cycle'] = plt.cycler(color=colors, linestyle=linestyles)

    limit_for_60D = 100
    plot_width = 12
    plot_height = n_rows * 3.5

    # Create subplots
    fig, axes = plt.subplots(n_rows, n_cols, figsize=(plot_width, plot_height), constrained_layout=True)

    # Plot first column
    for i, (func_name, _) in enumerate(col1_functions):
        ax = axes[i, 0]
        for r, result_subset in problem_results:
            if r.name == func_name:
                targets_dict = {r: targets_all.get(r)} if targets_all.get(r) is not None else None
                if targets_dict:
                    rtpplot(result_subset, indicator, targets=targets_dict, ax=ax)
                else:
                    print(f"Warning: No valid targets for {func_name}, using default number_of_targets")
                    rtpplot(result_subset, indicator, number_of_targets=1001, ax=ax)
                ax.set_title(f"{r.name}, {r.number_of_variables}-D")
                ax.legend(fontsize=8, title='')
                ax.set_xlabel("# fevals")
                if r.number_of_variables == 60:
                    ax.set_ylim(0, limit_for_60D)
                break

    # Plot second column
    for i, (func_name, _) in enumerate(col2_functions):
        ax = axes[i, 1]
        for r, result_subset in problem_results:
            if r.name == func_name:
                targets_dict = {r: targets_all.get(r)} if targets_all.get(r) is not None else None
                if targets_dict:
                    rtpplot(result_subset, indicator, targets=targets_dict, ax=ax)
                else:
                    print(f"Warning: No valid targets for {func_name}, using default number_of_targets")
                    rtpplot(result_subset, indicator, number_of_targets=1001, ax=ax)
                ax.set_title(f"{r.name}, {r.number_of_variables}-D")
                ax.legend(fontsize=8, title='')
                ax.set_xlabel("# fevals")
                if r.number_of_variables == 60:
                    ax.set_ylim(0, limit_for_60D)
                break

    # Plot third column
    for i, (func_name, _) in enumerate(col3_functions):
        ax = axes[i, 2]
        for r, result_subset in problem_results:
            if r.name == func_name:
                targets_dict = {r: targets_all.get(r)} if targets_all.get(r) is not None else None
                if targets_dict:
                    rtpplot(result_subset, indicator, targets=targets_dict, ax=ax)
                else:
                    print(f"Warning: No valid targets for {func_name}, using default number_of_targets")
                    rtpplot(result_subset, indicator, number_of_targets=1001, ax=ax)
                ax.set_title(f"{r.name}, {r.number_of_variables}-D")
                ax.legend(fontsize=8, title='')
                ax.set_xlabel("# fevals")
                if r.number_of_variables == 60:
                    ax.set_ylim(0, limit_for_60D)
                break

    # Hide empty subplots
    for i in range(len(col3_functions), n_rows):
        for j in range(n_cols):
            axes[i, j].set_visible(False)

    # Save plot
    output_file = output_dir / f"profiles-per-problem-{group_name}.pdf"
    plt.savefig(output_file, bbox_inches="tight")
    plt.close(fig)
    print(f"Saved plot to {output_file}")


# Algorithm lists
PSO = ["PSO-EARS", "PSO-pymoo", "PSO-jMetal", "PSO-PlatEMO", "PSO-pagmo2", "PSO-YPEA", "PSO-EvoloPy", "PSO-MEALPY", "PSO-NiaPy", "PSO-DEAP", "PSO-metaheuristicOpt", "PSO-Nevergrad"]
ABC = ["ABC-EARS", "ABC-MEALPY", "ABC-PlatEMO", "ABC-NiaPy", "ABC-Author-Matlab", "ABC-YPEA", "ABC-metaheuristicOpt", "ABC-pagmo2"]
GA = ["GA-PlatEMO", "GA-jMetal", "GA-MOEA", "GA-pagmo2", "GA-pymoo", "GA-DEAP"] # "GA-MEALPY", "GA-NiaPy", "GA-metaheuristicOpt", "GA-YPEA", "GA-EvoloPy"
DE = ["DE-EARS", "DE-jMetal", "DE-MEALPY", "DE-MOEA", "DE-NiaPy", "DE-PlatEMO", "DE-pagmo2", "DE-Author-Java", "DE-pymoo", "DE-metaheuristicOpt", "DE-YPEA", "DE-EvoloPy", "DE-Nevergrad", "DE-DEAP"]
GWO = ["GWO-EARS", "GWO-NiaPy", "GWO-MEALPY", "GWO-pagmo2", "GWO-Author-Matlab", "GWO-PlatEMO", "GWO-metaheuristicOpt", "GWO-EvoloPy"]
CMAES = ["CMA-ES-jMetal", "CMA-ES-pagmo2", "CMA-ES-Author-Python", "CMA-ES-pymoo", "CMA-ES-DEAP"]

# Function lists with optimum values
FUNCTIONS_D60 = [
    ("ShiftedSphere", 0.0),
    ("ShiftedSumOfSquares", 0.0),
    ("ShiftedSchwefel", 0.0),
    ("ShiftedRastrigin", 0.0),
    ("ShiftedAckley", 0.0),
    ("ShiftedGriewank", 0.0),
    ("Rosenbrock", 0.0)
]
FUNCTIONS_D3 = [("Hartman", -3.86278214782076)]
FUNCTIONS_D2 = [
    ("ShekelsFoxholes", 0.998003838),
    ("SixHumpCamelBack", -1.031628453489877),
    ("Branin", 0.39788735772973816),
    ("GoldsteinPrice", 3.0)
]
VARS_D60 = [60]
VARS_D3 = [3]
VARS_D2 = [2]
RUN = [i for i in range(1, 51)]
INDICATOR = Indicator("Fitness", display_name="Fitness", larger_is_better=False)

# Define algorithm groups to process (modify this list to select specific groups)
ALGORITHM_GROUPS = [
    ("PSO", PSO),
    ("ABC", ABC),
    ("GA", GA),
    ("DE", DE),
    ("GWO", GWO),
    ("CMA-ES", CMAES)
]

# Loop through selected algorithm groups
for group_name, algorithms in ALGORITHM_GROUPS:
    generate_plots_for_algorithm_group(
        group_name=group_name,
        algorithms=algorithms,
        functions_d60=FUNCTIONS_D60,
        functions_d3=FUNCTIONS_D3,
        functions_d2=FUNCTIONS_D2,
        vars_d60=VARS_D60,
        vars_d3=VARS_D3,
        vars_d2=VARS_D2,
        runs=RUN,
        data_dir=DATA_DIR,
        indicator=INDICATOR,
        output_dir=OUTPUT_DIR
    )