#include <iostream>
#include <fstream>
#include <vector>
#include <string>

#include <pagmo/algorithm.hpp>

// ABC PSO DE GWO GA
#include <pagmo/algorithms/bee_colony.hpp>
#include <pagmo/algorithms/pso.hpp>
#include <pagmo/algorithms/de.hpp>
#include <pagmo/algorithms/gwo.hpp>
#include <pagmo/algorithms/sga.hpp>

#include <pagmo/archipelago.hpp>

// Problems
#include <pagmo/problem.hpp>
#include <pagmo/problems/sphere.hpp>
#include <pagmo/problems/sphere_shifted.hpp>
#include <pagmo/problems/sum_of_squares.hpp>
#include <pagmo/problems/sum_of_squares_shifted.hpp>
#include <pagmo/problems/schwefel.hpp>
#include <pagmo/problems/schwefel_shifted.hpp>
#include <pagmo/problems/rastrigin.hpp>
#include <pagmo/problems/rastrigin_shifted.hpp>
#include <pagmo/problems/ackley.hpp>
#include <pagmo/problems/ackley_shifted.hpp>
#include <pagmo/problems/griewank.hpp>
#include <pagmo/problems/griewank_shifted.hpp>
#include <pagmo/problems/rosenbrock.hpp>
#include <pagmo/problems/shekel_fox_holes.hpp>
#include <pagmo/problems/camel_back.hpp>
#include <pagmo/problems/branin.hpp>
#include <pagmo/problems/goldstein_price.hpp>
#include <pagmo/problems/hartman.hpp> 
#include <pagmo/algorithms/cmaes.hpp>
#include <pagmo/problems/fitness_tracker.hpp>

using namespace pagmo;

int main()
{
    std::string frameworkName = "pagmo2";

    // Algorithm initialization
    algorithm algABC{bee_colony(59, 100)};
    algorithm algPSO{pso(499, 0.7, 2.,2.)};
    algorithm algDE{de(299, 0.5, 0.9, 7u)};
    algorithm algGWO{gwo(499)};
    algorithm algSGA{sga(149, 0.95, 20., 0.025, 20, 2, "sbx","polynomial")};
    algorithm algCMAES{cmaes(499)};

    //std::vector<algorithm> algorithms{algABC, algPSO, algDE, algGWO, algSGA, algCMAES};
    //std::vector<std::string> algorithmNames{"ABC", "PSO", "DE", "GWO", "GA", "CMA-ES"};
    //std::vector<int> numOfIndividuals{125, 30, 50, 30, 100, 30};

    std::vector<algorithm> algorithms{ algCMAES};
    std::vector<std::string> algorithmNames{"CMA-ES"};
    std::vector<int> numOfIndividuals{30};


    // Problems
    problem probSphere{sphere(60)};
    problem probSphereShifted{sphereShifted(60)};

    problem probSumOfSquares{sumOfSquares(60)};
    problem probSumOfSquaresShifted{sumOfSquaresShifted(60)};

    problem proSschwefel{schwefel(60)};
    problem proSschwefelShifted{schwefelShifted(60)};

    problem probRastrigin{rastrigin(60)};
    problem probRastriginShifted{rastriginShifted(60)};

    problem probAckley{ackley(60)};
    problem probAckleyShifted{ackleyShifted(60)};

    problem probGriewank{griewank(60)};
    problem probGriewankShifted{griewankShifted(60)};


    problem probRosenbrock{rosenbrock(60)};
    problem probShekelFoxHoles{shekelsFoxHoles(2)};
    problem probCamelBack{camelBack(2)};
    problem probBranin{branin(2)};
    problem probFoldsteinPrice{goldsteinPrice(2)};
    problem probHartman{hartman(3)};
    
    std::vector<problem> problems{
        probSphere,          probSumOfSquares,     proSschwefel,      probRastrigin,
        probAckley,          probGriewank,         probSphereShifted, probSumOfSquaresShifted,
        proSschwefelShifted, probRastriginShifted, probAckleyShifted, probGriewankShifted, 
        probRosenbrock,      probShekelFoxHoles,   probCamelBack,     probBranin,
        probFoldsteinPrice, probHartman};

    std::vector<std::string> problemNames{
        "Sphere",          "SumOfSquares",     "Schwefel",         "Rastrigin",
        "Ackley",          "Griewank",         "ShiftedSphere",    "ShiftedSumOfSquares",
        "ShiftedSchwefel", "ShiftedRastrigin", "ShiftedAckley",    "ShiftedGriewank",
        "Rosenbrock",      "ShekelsFoxholes",  "SixHumpCamelBack", "Branin",
        "GoldsteinPrice",  "Hartman",
    }; 

    std::vector<int> dims{60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 2, 2, 2, 2, 3}; 

    std::cout << std::to_string(FitnessTracker::evaluationCount)<< std::endl;

    unsigned numberOfRepeates = 50u;

    int counter = 0;
    for (const auto &alg : algorithms) {
        std::cout << alg.get_name() << '\n';
        
        int problemCounter = 0;
        for (const auto &probl : problems) {
            std::cout << probl.get_name() << '\n';

            std::vector<double> bestSolutions;

            for (unsigned r = 1; r <= numberOfRepeates; r++) {
                FitnessTracker::reset();

                archipelago archi{1, alg, probl, numOfIndividuals[counter]};
                archi.evolve(1);
                archi.wait_check();

                for (const auto &isl : archi) {
                    bestSolutions.push_back(isl.get_population().champion_f()[0]);
                }

                std::string filename = "algorithm_results/Runs/" + algorithmNames[counter] + "-" + frameworkName + "_"
                                       + problemNames[problemCounter] + "_vars=" + std::to_string(dims[problemCounter])
                                       + "_run=" + std::to_string(r);
                FitnessTracker::saveProgressToFile(filename);
            }
            std::string filename = "algorithm_results/" + algorithmNames[counter] + "-" + frameworkName + "_"
                                   + problemNames[problemCounter] + "D" + std::to_string(dims[problemCounter]) + ".txt";

            std::ofstream outFile(filename);

            if (!outFile) {
                std::cerr << "Error: Unable to open the file " << filename << " for writing." << std::endl;
                return -1;
            }

            for (const auto &sol : bestSolutions) {
                outFile << sol << std::endl;
            }

            outFile.close();

            problemCounter++;

            /*archipelago archi{numberOfRepeates, alg, probl, numOfIndividuals[counter]};
            archi.evolve(1);
            archi.wait_check();

            std::string filename = "algorithm_results/" + algorithmNames[counter] + "-" + frameworkName + "_"
                                          + problemNames[problemCounter] + "D" + std::to_string(dims[problemCounter]) + ".txt";

            std::ofstream outFile(filename);

            if (!outFile) {
                std::cerr << "Error: Unable to open the file " << filename << " for writing." << std::endl;
                return -1;
            }

            for (const auto &isl : archi) {
                outFile << isl.get_population().champion_f()[0] << std::endl; 
            }

            outFile.close();

            problemCounter++;*/
        }
        counter++;
    }

    return 0;
}

/* void saveVectorToFile(const std::vector<double> &values, const std::string &filename)
{
    std::ofstream outFile(filename); // Open the file for writing

    if (!outFile) {
        std::cerr << "Error: Unable to open the file " << filename << " for writing." << std::endl;
        return;
    }

    for (const double &value : values) {
        outFile << value << std::endl; // Write each value to the file, one per line
    }

    outFile.close(); // Close the file
}*/