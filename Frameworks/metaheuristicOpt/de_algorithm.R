de_algorithm <-
  function(runs,
           pop_size,
           iterations,
           F,
           CR,
           variant,
           problem,
           lb,
           ub,
           dim,
           file_name) {
    rangeVar <- matrix(c(lb, ub), nrow = 2)
    
    control <-
      list(
        numPopulation = pop_size,
        maxIter = iterations,
        scalingVector = F,
        crossOverRate = CR,
        strategy = variant
      )
    
    results <- c()
    
    for (i in 1:runs) {
      best.variable <-
        metaOpt(problem,
                optimType = "MIN",
                algorithm = "DE",
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

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = sphere,
  lb = -100,
  ub = 100,
  dim = 30,
  file_name = "DE-metaheuristicOpt_SphereD30.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = sum_of_squares,
  lb = -100,
  ub = 100,
  dim = 30,
  file_name = "DE-metaheuristicOpt_SumOfSquaresD30.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = schwefel2,
  lb = -100,
  ub = 100,
  dim = 30,
  file_name = "DE-metaheuristicOpt_SchwefelD30.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = rastrigin,
  lb = -5.12,
  ub = 5.12,
  dim = 30,
  file_name = "DE-metaheuristicOpt_RastriginD30.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = ackley,
  lb = -32,
  ub = 32,
  dim = 30,
  file_name = "DE-metaheuristicOpt_AckleyD30.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = griewank,
  lb = -600,
  ub = 600,
  dim = 30,
  file_name = "DE-metaheuristicOpt_GriewankD30.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = rosenbrock,
  lb = -30,
  ub = 30,
  dim = 30,
  file_name = "DE-metaheuristicOpt_RosenbrockD30.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = shekels_foxholes,
  lb = -65.536,
  ub = 65.536,
  dim = 2,
  file_name = "DE-metaheuristicOpt_ShekelsFoxholesD2.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = six_hump_camel_back,
  lb = -5,
  ub = 5,
  dim = 2,
  file_name = "DE-metaheuristicOpt_SixHumpCamelBackD2.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = branin,
  lb = c(-5, 0),
  ub = c(10, 15),
  dim = 2,
  file_name = "DE-metaheuristicOpt_BraninD2.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = goldstein_price,
  lb = -2,
  ub = 2,
  dim = 2,
  file_name = "DE-metaheuristicOpt_GoldsteinPriceD2.txt"
)

de_algorithm(
  runs = runs,
  pop_size = 50,
  iterations = 300,
  F = 0.5,
  CR = 0.9,
  variant = "classical",
  # RAND_1_BIN
  problem = hartman_3d,
  lb = 0,
  ub = 1,
  dim = 3,
  file_name = "DE-metaheuristicOpt_HartmanD3.txt"
)