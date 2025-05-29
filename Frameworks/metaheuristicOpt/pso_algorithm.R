pso_algorithm <-
  function(runs,
           pop_size,
           iterations,
           omega,
           c1,
           c2,
           problem,
           lb,
           ub,
           dim,
           file_name,
           problem_name) {
    
    rangeVar <- matrix(c(lb, ub), nrow = 2)
    
    control <-
      list(
        numPopulation = pop_size,
        maxIter = iterations,
        ci = c1,
        cg = c2,
        w = omega
      )
    
    results <- c()
    
    for (i in 1:runs) {
      best.variable <-
        metaOpt(problem,
                optimType = "MIN",
                algorithm = "PSO",
                dim,
                rangeVar,
                control)
      results <- c(results, best.variable[["optimumValue"]])
      filename_runs <- paste("results/runs/PSO-metaheuristicOpt_", problem_name, "_vars=", dim, "_run=", i, ".csv")
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

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = shifted_sphere,
  lb = -100,
  ub = 100,
  dim = 60,
  file_name = "PSO-metaheuristicOpt_ShiftedSphereD60.txt",
  problem_name = "ShiftedSphere"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = shifted_sum_of_squares,
  lb = -100,
  ub = 100,
  dim = 60,
  file_name = "PSO-metaheuristicOpt_ShiftedSumOfSquaresD60.txt",
  problem_name = "ShiftedSumOfSquares"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = shifted_schwefel2,
  lb = -100,
  ub = 100,
  dim = 60,
  file_name = "PSO-metaheuristicOpt_ShiftedSchwefelD60.txt",
  problem_name = "ShiftedSchwefel"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = shifted_rastrigin,
  lb = -5.12,
  ub = 5.12,
  dim = 60,
  file_name = "PSO-metaheuristicOpt_ShiftedRastriginD60.txt",
  problem_name = "ShiftedRastrigin"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = shifted_ackley,
  lb = -32,
  ub = 32,
  dim = 60,
  file_name = "PSO-metaheuristicOpt_ShiftedAckleyD60.txt",
  problem_name = "ShiftedAckley"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = shifted_griewank,
  lb = -600,
  ub = 600,
  dim = 60,
  file_name = "PSO-metaheuristicOpt_ShiftedGriewankD60.txt",
  problem_name = "ShiftedGriewank"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = rosenbrock,
  lb = -30,
  ub = 30,
  dim = 60,
  file_name = "PSO-metaheuristicOpt_RosenbrockD60.txt",
  problem_name = "Rosenbrock"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = shekels_foxholes,
  lb = -65.536,
  ub = 65.536,
  dim = 2,
  file_name = "PSO-metaheuristicOpt_ShekelsFoxholesD2.txt",
  problem_name = "ShekelsFoxholes"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = six_hump_camel_back,
  lb = -5,
  ub = 5,
  dim = 2,
  file_name = "PSO-metaheuristicOpt_SixHumpCamelBackD2.txt",
  problem_name = "SixHumpCamelBack"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = branin,
  lb = c(-5, 0),
  ub = c(10, 15),
  dim = 2,
  file_name = "PSO-metaheuristicOpt_BraninD2.txt",
  problem_name = "Branin"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = goldstein_price,
  lb = -2,
  ub = 2,
  dim = 2,
  file_name = "PSO-metaheuristicOpt_GoldsteinPriceD2.txt",
  problem_name = "GoldsteinPrice"
)

pso_algorithm(
  runs = runs,
  pop_size = 30,
  iterations = 500,
  omega = 0.7,
  c1 = 2,
  c2 = 2,
  problem = hartman_3d,
  lb = 0,
  ub = 1,
  dim = 3,
  file_name = "PSO-metaheuristicOpt_HartmanD3.txt",
  problem_name = "Hartman"
)