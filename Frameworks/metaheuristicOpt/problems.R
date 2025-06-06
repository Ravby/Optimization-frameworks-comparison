sphere <- log_execution(function(xx)
{
  ##########################################################################
  #
  # SPHERE FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUT:
  #
  # xx = c(x1, x2, ..., xd)
  #
  ##########################################################################
  
  sum <- sum(xx ^ 2)
  
  y <- sum
  return(y)
})


sum_of_squares <- log_execution(function(xx)
{
  ##########################################################################
  #
  # SUM SQUARES FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUT:
  #
  # xx = c(x1, x2, ..., xd)
  #
  ##########################################################################
  
  ii <- c(1:length(xx))
  sum <- sum(ii * xx ^ 2)
  
  y <- sum
  return(y)
})

schwefel2 <- log_execution(function(xx) {
  # number_of_variables <- length(xx)
  # fitness <- 0
  # for (i in 1:length(xx)) {
  #   sum <- 0
  #   for (j in 1:i) {
  #     sum <- sum + xx[i]
  #   }
  #   fitness <- fitness + (sum ^ 2)
  # }
  # return(fitness)
  fitness <- sum(cumsum(xx) ^ 2)
  return(fitness)
})

rastrigin <- log_execution(function(xx)
{
  ##########################################################################
  #
  # RASTRIGIN FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUT:
  #
  # xx = c(x1, x2, ..., xd)
  #
  ##########################################################################
  
  d <- length(xx)
  
  sum <- sum(xx ^ 2 - 10 * cos(2 * pi * xx))
  
  y <- 10 * d + sum
  return(y)
})

ackley <- log_execution(function(xx,
                   a = 20,
                   b = 0.2,
                   c = 2 * pi)
{
  ##########################################################################
  #
  # ACKLEY FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUTS:
  #
  # xx = c(x1, x2, ..., xd)
  # a = constant (optional), with default value 20
  # b = constant (optional), with default value 0.2
  # c = constant (optional), with default value 2*pi
  #
  ##########################################################################
  
  d <- length(xx)
  
  sum1 <- sum(xx ^ 2)
  sum2 <- sum(cos(c * xx))
  
  term1 <- -a * exp(-b * sqrt(sum1 / d))
  term2 <- -exp(sum2 / d)
  
  y <- term1 + term2 + a + exp(1)
  return(y)
})

griewank <- log_execution(function(xx)
{
  ##########################################################################
  #
  # GRIEWANK FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUT:
  #
  # xx = c(x1, x2, ..., xd)
  #
  ##########################################################################
  
  ii <- c(1:length(xx))
  sum <- sum(xx ^ 2 / 4000)
  prod <- prod(cos(xx / sqrt(ii)))
  
  y <- sum - prod + 1
  return(y)
})

rosenbrock <- log_execution(function(xx)
{
  ##########################################################################
  #
  # ROSENBROCK FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUT:
  #
  # xx = c(x1, x2, ..., xd)
  #
  ##########################################################################
  
  d <- length(xx)
  xi <- xx[1:(d - 1)]
  xnext <- xx[2:d]
  
  sum <- sum(100 * (xnext - xi ^ 2) ^ 2 + (xi - 1) ^ 2)
  
  y <- sum
  return(y)
})

shekels_foxholes <- log_execution(function(xx) {
  # My version:
  # shekels_foxholes <- function(xx) {
  #   a <- matrix(c(-32, -32, -16, -32, 0, -32, 16, -32, 32, -32, -32, -16, -16, -16, 0, -16, 16, -16, 32, -16, -32, 0, -16, 0, 0, 16, 0, 32, 0, -32, 16, -16, 16, 0, 16, 16, 16, 32, -16, 32, -32, 32, -16, 32, 0, 32, 16, 32, 32), nrow = 25, byrow = TRUE)
  #   number_of_variables <- length(xx)
  #   fitness <- 0
  #   for (j in 1:25) {
  #     sum <- 0
  #     for (i in 1:number_of_variables) {
  #       sum <- sum + ((xx[i] - a[j][i])^6)
  #     }
  #     sum <- sum + j
  #     fitness <- fitness + (1.0 / sum)
  #   }
  #   fitness <- fitness + (1.0 / 500.0)
  #   fitness <- fitness^(-1)
  #   return(fitness)
  # }
  # Shortened version from ChatGPT:
  a <-
    matrix(
      c(
        -32,-32,
        -16,-32,
        0,-32,
        16,-32,
        32,-32,
        -32,-16,
        -16,-16,
        0,-16,
        16,-16,
        32,-16,
        -32,0,
        -16,0,
        0,0,
        16,0,
        32,0,
        -32,16,
        -16,16,
        0,16,
        16,16,
        32,16,
        -32,32,
        -16,32,
        0,32,
        16,32,
        32,32
      ),
      nrow = 25,
      byrow = TRUE
    )
  sum(1 / (rowSums((xx - a) ^ 6) + 1:25)) + 1 / 500
})

six_hump_camel_back <- log_execution(function(xx) {
  x1 <- xx[1]
  x2 <- xx[2]
  fitness <-
    4 * x1 ^ 2 - 2.1 * x1 ^ 4 + (1.0 / 3.0) * x1 ^ 6 + x1 * x2 - 4 * x2 ^ 2 + 4 * x2 ^
    4
  return(fitness)
})

branin <-
  log_execution(function(xx,
           a = 1,
           b = 5.1 / (4 * pi ^ 2),
           c = 5 / pi,
           r = 6,
           s = 10,
           t = 1 / (8 * pi))
  {
    ##########################################################################
    #
    # BRANIN FUNCTION
    #
    # Authors: Sonja Surjanovic, Simon Fraser University
    #          Derek Bingham, Simon Fraser University
    # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
    #
    # Copyright 2013. Derek Bingham, Simon Fraser University.
    #
    # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
    # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
    # derivative works, such modified software should be clearly marked.
    # Additionally, this program is free software; you can redistribute it
    # and/or modify it under the terms of the GNU General Public License as
    # published by the Free Software Foundation; version 2.0 of the License.
    # Accordingly, this program is distributed in the hope that it will be
    # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
    # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    # General Public License for more details.
    #
    # For function details and reference information, see:
    # http://www.sfu.ca/~ssurjano/
    #
    ##########################################################################
    #
    # INPUTS:
    #
    # xx = c(x1, x2)
    # a = constant (optional), with default value 1
    # b = constant (optional), with default value 5.1/(4*pi^2)
    # c = constant (optional), with default value 5/pi
    # r = constant (optional), with default value 6
    # s = constant (optional), with default value 10
    # t = constant (optional), with default value 1/(8*pi)
    #
    ##########################################################################
    
    x1 <- xx[1]
    x2 <- xx[2]
    
    term1 <- a * (x2 - b * x1 ^ 2 + c * x1 - r) ^ 2
    term2 <- s * (1 - t) * cos(x1)
    
    y <- term1 + term2 + s
    return(y)
  })

goldstein_price <- log_execution(function(xx)
{
  ##########################################################################
  #
  # GOLDSTEIN-PRICE FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUT:
  #
  # xx = c(x1, x2)
  #
  ##########################################################################
  
  x1 <- xx[1]
  x2 <- xx[2]
  
  fact1a <- (x1 + x2 + 1) ^ 2
  fact1b <-
    19 - 14 * x1 + 3 * x1 ^ 2 - 14 * x2 + 6 * x1 * x2 + 3 * x2 ^
    2
  fact1 <- 1 + fact1a * fact1b
  
  fact2a <- (2 * x1 - 3 * x2) ^ 2
  fact2b <-
    18 - 32 * x1 + 12 * x1 ^ 2 + 48 * x2 - 36 * x1 * x2 + 27 * x2 ^
    2
  fact2 <- 30 + fact2a * fact2b
  
  y <- fact1 * fact2
  return(y)
})

hartman_3d <- log_execution(function(xx)
{
  ##########################################################################
  #
  # HARTMANN 3-DIMENSIONAL FUNCTION
  #
  # Authors: Sonja Surjanovic, Simon Fraser University
  #          Derek Bingham, Simon Fraser University
  # Questions/Comments: Please email Derek Bingham at dbingham@stat.sfu.ca.
  #
  # Copyright 2013. Derek Bingham, Simon Fraser University.
  #
  # THERE IS NO WARRANTY, EXPRESS OR IMPLIED. WE DO NOT ASSUME ANY LIABILITY
  # FOR THE USE OF THIS SOFTWARE.  If software is modified to produce
  # derivative works, such modified software should be clearly marked.
  # Additionally, this program is free software; you can redistribute it
  # and/or modify it under the terms of the GNU General Public License as
  # published by the Free Software Foundation; version 2.0 of the License.
  # Accordingly, this program is distributed in the hope that it will be
  # useful, but WITHOUT ANY WARRANTY; without even the implied warranty
  # of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  # General Public License for more details.
  #
  # For function details and reference information, see:
  # http://www.sfu.ca/~ssurjano/
  #
  ##########################################################################
  #
  # INPUT:
  #
  # xx = c(x1, x2, x3)
  #
  ##########################################################################
  
  alpha <- c(1.0, 1.2, 3.0, 3.2)
  A <- c(3.0, 10, 30,
         0.1, 10, 35,
         3.0, 10, 30,
         0.1, 10, 35)
  A <- matrix(A, 4, 3, byrow = TRUE)
  P <- 10 ^ (-4) * c(3689,
                     1170,
                     2673,
                     4699,
                     4387,
                     7470,
                     1091,
                     8732,
                     5547,
                     381,
                     5743,
                     8828)
  P <- matrix(P, 4, 3, byrow = TRUE)
  
  xxmat <- matrix(rep(xx, times = 4), 4, 3, byrow = TRUE)
  inner <- rowSums(A[, 1:3] * (xxmat - P[, 1:3]) ^ 2)
  outer <- sum(alpha * exp(-inner))
  
  y <- -outer
  return(y)
})