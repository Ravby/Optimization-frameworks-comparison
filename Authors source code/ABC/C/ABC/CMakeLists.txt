cmake_minimum_required(VERSION 3.21)
project(ABC)

set(CMAKE_CXX_STANDARD 20)

add_executable(ABC main.cpp ABC.cpp ABC.h Problem.h Sphere.h SumOfSquares.h Schwefel.h Rastrigin.h Ackley.h Griewank.h Rosenbrock.h ShekelsFoxholes.h SixHumpCamelBack.h Branin.h GoldsteinPrice.h Hartman.h)
