#include <iostream>
#include <fstream>
#include <vector>

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
#include <pagmo/problems/sum_of_squares.hpp>
#include <pagmo/problems/schwefel.hpp>
#include <pagmo/problems/rastrigin.hpp>
#include <pagmo/problems/ackley.hpp>
#include <pagmo/problems/griewank.hpp>
#include <pagmo/problems/rosenbrock.hpp>
#include <pagmo/problems/shekel_fox_holes.hpp>
#include <pagmo/problems/camel_back.hpp>
#include <pagmo/problems/branin.hpp>
#include <pagmo/problems/goldstein_price.hpp>
#include <pagmo/problems/hartman.hpp>

using namespace pagmo;

int main()
{
    // 1 - Instantiate a pagmo problem constructing it from a UDP
    problem probSphere{sphere(30)};
    problem probSumOfSquares{sumOfSquares(30)};
    problem proSschwefel{schwefel(30)};
    problem probRastrigin{rastrigin(30)};
    problem probAckley{ackley(30)};
    problem probGriewank{griewank(30)};
    problem probRosenbrock{rosenbrock(30)};
    problem probShekelFoxHoles{shekelsFoxHoles(2)};
    problem probCamelBack{camelBack(2)};
    problem probBranin{branin(2)};
    problem probFoldsteinPrice{goldsteinPrice(2)};
    problem probHartman{hartman(3)};

    /* std::cout << "Sphere: " << probSphere.fitness(probSphere.best_known())[0] << '\n';
    std::cout << "Sum fo Squares: " << probSumOfSquares.fitness(probSumOfSquares.best_known())[0] << '\n';
    std::cout << "Schwefel: " << proSschwefel.fitness(proSschwefel.best_known())[0] << '\n';
    std::cout << "Rastrigin: " << probRastrigin.fitness(probRastrigin.best_known())[0] << '\n';
    std::cout << "Ackley: " << probAckley.fitness(probAckley.best_known())[0] << '\n';
    std::cout << "Griewank: " << probGriewank.fitness(probGriewank.best_known())[0] << '\n';
    std::cout << "Rosenbrook: " << probRosenbrock.fitness(probRosenbrock.best_known())[0] << '\n';
    std::cout << "Fox holes: " << probShekelFoxHoles.fitness(probShekelFoxHoles.best_known())[0] << '\n';
    std::cout << "Camel back: " << probCamelBack.fitness(probCamelBack.best_known())[0] << '\n';
    std::cout << "Branin: " << probBranin.fitness(probBranin.best_known())[0] << '\n';
    std::cout << "Goldstein price: " << probFoldsteinPrice.fitness(probFoldsteinPrice.best_known())[0] << '\n';
    std::cout << "Hartman: " << probHartman.fitness(probHartman.best_known())[0] << '\n';*/


    // Algorithm initialization
    algorithm algABC{bee_colony(60, 100)}; // bee_colony(unsigned gen = 1u, unsigned limit = 20u, unsigned seed = pagmo::random_device::next());
    //algABC.set_verbosity(1);
    algorithm algPSO{pso(499, 0.7, 2., 2.)}; // pso(unsigned gen = 1u, double omega = 0.7298, double eta1 = 2.05, double eta2 = 2.05, double max_vel = 0.5, unsigned variant = 5u, unsigned neighb_type = 2u, unsigned neighb_param = 4u, bool memory = false, unsigned seed = pagmo::random_device::next());
    //algPSO.set_verbosity(1);
    algorithm algDE{de(299, 0.5, 0.9, 7u)}; // de(unsigned gen = 1u, double F = 0.8, double CR = 0.9, unsigned variant = 2u, double ftol = 1e-6, double xtol = 1e-6, unsigned seed = pagmo::random_device::next());
    //algDE.set_verbosity(1);
    algorithm algGWO{gwo(499)}; // gwo(unsigned gen = 1u, unsigned seed = pagmo::random_device::next());
    //algGWO.set_verbosity(1);
    algorithm algSGA{sga(149, 0.95, 20., 0.025, 20, 2, "sbx", "polynomial")}; // sga(unsigned gen = 1u, double cr = .90, double eta_c = 1., double m = 0.02, double param_m = 1.,unsigned param_s = 2u, std::string crossover = "exponential", std::string mutation = "polynomial",std::string selection = "tournament", unsigned seed = pagmo::random_device::next());
    //algSGA.set_verbosity(1);

    std::vector<algorithm> algorithms{algABC, algPSO, algDE, algGWO, algSGA};
    //std::vector<algorithm> algorithms{algSGA};
    
    //std::vector<problem> problems {probSphere,     probSumOfSquares,   proSschwefel,  probRastrigin, probAckley,         probGriewank,     probRosenbrock,     probShekelFoxHoles,probCamelBack, probBranin,       probFoldsteinPrice, probHartman    };
    std::vector<problem> problems {probSphere,     probSumOfSquares,   proSschwefel,  probRastrigin, probAckley,         probGriewank,     probRosenbrock};

    std::string frameworkName = "pagmo2";
    std::vector<std::string> algorithmNames{"ABC", "PSO", "DE", "GWO", "GA"}; 

    std::vector<std::string> problemNames{
        "Sphere", "SumOfSquares", "Schwefel", "Rastrigin", "Ackley", "Griewank",
        "Rosenbrock", "ShekelsFoxholes", "SixHumpCamelBack", "Branin", "GoldsteinPrice", "Hartman",
    }; 
    std::vector<int> dims{60, 60, 60, 60, 60, 60, 60, 2, 2, 2, 2, 3}; 
    std::vector<int> numOfIndividuals{ 125, 30, 50, 30, 100};


    unsigned numberOfRepeates = 100u;

    int counter = 0;
    for (const auto &alg : algorithms) {
        std::cout << alg.get_name() << '\n';
        
        int problemCounter = 0;
        for (const auto &probl : problems) {
            std::cout << probl.get_name() << '\n';

            archipelago archi{numberOfRepeates, alg, probl, numOfIndividuals[counter]};
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

            problemCounter++;
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