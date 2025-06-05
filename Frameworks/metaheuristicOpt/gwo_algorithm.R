gwo_algorithm <-
  function(runs,
           pop_size,
           iterations,
           problem,
           lb,
           ub,
           dim,
           file_name,
           problem_name) {
    rangeVar <- matrix(c(lb, ub), nrow = 2)
    
    control <-
      list(numPopulation = pop_size,
           maxIter = iterations)
    
    results <- c()
    
    for (i in 1:runs) {
      attr(problem, "reset")()  # Explicitly reset before run
      best.variable <-
        metaOpt(problem,
                optimType = "MIN",
                algorithm = "GWO",
                dim,
                rangeVar,
                control)
      results <- c(results, best.variable[["optimumValue"]])
      filename_runs <- paste("results/runs/GWO-metaheuristicOpt_", problem_name, "_vars=", dim, "_run=", i, ".csv", sep = "")
      attr(problem, "write_improvements_to_file")(filename_runs)
    }
    
    write.table(
      results,
      file = paste("results/", file_name),
      row.names = FALSE,
      col.names = FALSE
    )
  }

runs = 50

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = shifted_sphere,
  lb = -100,
  ub = 100,
  dim = 60,
  file_name = "GWO-metaheuristicOpt_ShiftedSphereD60.txt",
  problem_name = "ShiftedSphere"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = shifted_sum_of_squares,
  lb = -100,
  ub = 100,
  dim = 60,
  file_name = "GWO-metaheuristicOpt_ShiftedSumOfSquaresD60.txt",
  problem_name = "ShiftedSumOfSquares"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = shifted_schwefel2,
  lb = -100,
  ub = 100,
  dim = 60,
  file_name = "GWO-metaheuristicOpt_ShiftedSchwefelD60.txt",
  problem_name = "ShiftedSchwefel"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = shifted_rastrigin,
  lb = -5.12,
  ub = 5.12,
  dim = 60,
  file_name = "GWO-metaheuristicOpt_ShiftedRastriginD60.txt",
  problem_name = "ShiftedRastrigin"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = shifted_ackley,
  lb = -32,
  ub = 32,
  dim = 60,
  file_name = "GWO-metaheuristicOpt_ShiftedAckleyD60.txt",
  problem_name = "ShiftedAckley"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = shifted_griewank,
  lb = -600,
  ub = 600,
  dim = 60,
  file_name = "GWO-metaheuristicOpt_ShiftedGriewankD60.txt",
  problem_name = "ShiftedGriewank"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = rosenbrock,
  lb = -30,
  ub = 30,
  dim = 60,
  file_name = "GWO-metaheuristicOpt_RosenbrockD60.txt",
  problem_name = "Rosenbrock"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = shekels_foxholes,
  lb = -65.536,
  ub = 65.536,
  dim = 2,
  file_name = "GWO-metaheuristicOpt_ShekelsFoxholesD2.txt",
  problem_name = "ShekelsFoxholes"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = six_hump_camel_back,
  lb = -5,
  ub = 5,
  dim = 2,
  file_name = "GWO-metaheuristicOpt_SixHumpCamelBackD2.txt",
  problem_name = "SixHumpCamelBack"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = branin,
  lb = c(-5, 0),
  ub = c(10, 15),
  dim = 2,
  file_name = "GWO-metaheuristicOpt_BraninD2.txt",
  problem_name = "Branin"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = goldstein_price,
  lb = -2,
  ub = 2,
  dim = 2,
  file_name = "GWO-metaheuristicOpt_GoldsteinPriceD2.txt",
  problem_name = "GoldsteinPrice"
)

gwo_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  problem = hartman_3d,
  lb = 0,
  ub = 1,
  dim = 3,
  file_name = "GWO-metaheuristicOpt_HartmanD3.txt",
  problem_name = "Hartman"
)