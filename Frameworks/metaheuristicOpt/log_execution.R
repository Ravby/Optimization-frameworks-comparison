# Create a log_execution wrapper function
log_execution <- function(func) {
  # Static-like variables using an environment
  log_env <- new.env()
  log_env$evaluationsCount <- 0
  log_env$improvements <- list()
  
  # Wrapper function to log evaluations and improvements
  wrapped_func <- function(...) {
    log_env$evaluationsCount <- log_env$evaluationsCount + 1
    fitness <- func(...)
    
    # Log improvements if fitness is better (assuming minimization)
    if (length(log_env$improvements) == 0 || fitness < log_env$improvements[[length(log_env$improvements)]][[2]]) {
      log_env$improvements[[length(log_env$improvements) + 1]] <- c(log_env$evaluationsCount, fitness)
    }
    
    return(fitness)
  }
  
  # Add attributes to mimic Python's class methods
  attr(wrapped_func, "reset") <- function() {
    log_env$evaluationsCount <- 0
    log_env$improvements <- list()
  }
  
  attr(wrapped_func, "write_improvements_to_file") <- function(filename) {
    # Write improvements to a CSV file
    if (length(log_env$improvements) > 0) {
      df <- do.call(rbind, lapply(log_env$improvements, function(x) data.frame(Evaluations = x[1], Fitness = x[2])))
      write.csv(df, file = filename, row.names = FALSE)
    }
    # Reset after writing
    attr(wrapped_func, "reset")()
  }
  
  # Return the wrapped function with preserved metadata
  attr(wrapped_func, "original") <- func
  class(wrapped_func) <- c("log_execution", "function")
  return(wrapped_func)
}