abc_algorithm <-
  function(runs,
           pop_size,
           iterations,
           limit,
           problem,
           lb,
           ub,
           dim,
           file_name) {
    rangeVar <- matrix(c(lb, ub), nrow = 2)
    
    control <-
      list(numPopulation = pop_size,
           maxIter = iterations,
           cycleLimit = limit)
    
    results <- c()
    
    for (i in 1:runs) {
      best.variable <-
        metaOpt(problem,
                optimType = "MIN",
                algorithm = "ABC",
                dim,
                rangeVar,
                control)
      results <- c(results, best.variable[["optimumValue"]])
    }
    
    write.table(
      results,
      file = paste("results/", file_name),
      row.names = FALSE,
      col.names = FALSE
    )
  }

runs = 100

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = sphere,
  lb = -100,
  ub = 100,
  dim = 30,
  file_name = "ABC-metaheuristicOpt_SphereD30.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = sum_of_squares,
  lb = -100,
  ub = 100,
  dim = 30,
  file_name = "ABC-metaheuristicOpt_SumOfSquaresD30.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = schwefel2,
  lb = -100,
  ub = 100,
  dim = 30,
  file_name = "ABC-metaheuristicOpt_SchwefelD30.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = rastrigin,
  lb = -5.12,
  ub = 5.12,
  dim = 30,
  file_name = "ABC-metaheuristicOpt_RastriginD30.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = ackley,
  lb = -32,
  ub = 32,
  dim = 30,
  file_name = "ABC-metaheuristicOpt_AckleyD30.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = griewank,
  lb = -600,
  ub = 600,
  dim = 30,
  file_name = "ABC-metaheuristicOpt_GriewankD30.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = rosenbrock,
  lb = -30,
  ub = 30,
  dim = 30,
  file_name = "ABC-metaheuristicOpt_RosenbrockD30.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = shekels_foxholes,
  lb = -65.536,
  ub = 65.536,
  dim = 2,
  file_name = "ABC-metaheuristicOpt_ShekelsFoxholesD2.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = six_hump_camel_back,
  lb = -5,
  ub = 5,
  dim = 2,
  file_name = "ABC-metaheuristicOpt_SixHumpCamelBackD2.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = branin,
  lb = c(-5, 0),
  ub = c(10, 15),
  dim = 2,
  file_name = "ABC-metaheuristicOpt_BraninD2.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = goldstein_price,
  lb = -2,
  ub = 2,
  dim = 2,
  file_name = "ABC-metaheuristicOpt_GoldsteinPriceD2.txt"
)

abc_algorithm(
  runs = runs,
  pop_size = 125,
  iterations = 120,
  limit = 100,
  problem = hartman_3d,
  lb = 0,
  ub = 1,
  dim = 3,
  file_name = "ABC-metaheuristicOpt_HartmanD3.txt"
)