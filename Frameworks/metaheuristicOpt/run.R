if (!require("metaheuristicOpt")) {
  install.packages("metaheuristicOpt")
  library("metaheuristicOpt")
}

source("problems.R")
source("abc_algorithm.R")
source("de_algorithm.R")
source("ga_algorithm.R")
source("gwo_algorithm.R")
source("pso_algorithm.R")