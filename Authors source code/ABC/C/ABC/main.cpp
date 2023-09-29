/* ABC algorithm coded using C programming language */

/* Artificial Bee Colony (ABC) is one of the most recently defined algorithms by Dervis Karaboga in 2005,
motivated by the intelligent behavior of honey bees. */

/* Referance Papers*/

/*D. Karaboga, AN IDEA BASED ON HONEY BEE SWARM FOR NUMERICAL OPTIMIZATION,TECHNICAL REPORT-TR06, Erciyes University, Engineering Faculty, Computer Engineering Department 2005.*/

/*D. Karaboga, B. Basturk, A powerful and Efficient Algorithm for Numerical Function Optimization: Artificial Bee Colony (ABC) Algorithm, Journal of Global Optimization, Volume:39, Issue:3,pp:459-171, November 2007,ISSN:0925-5001 , doi: 10.1007/s10898-007-9149-x */

/*D. Karaboga, B. Basturk, On The Performance Of Artificial Bee Colony (ABC) Algorithm, Applied Soft Computing,Volume 8, Issue 1, January 2008, Pages 687-697. */

/*D. Karaboga, B. Akay, A Comparative Study of Artificial Bee Colony Algorithm,  Applied Mathematics and Computation, 214, 108-132, 2009. */

/*Copyright © 2009 Erciyes University, Intelligent Systems Research Group, The Dept. of Computer Engineering*/

/*Contact:
Dervis Karaboga (karaboga@erciyes.edu.tr )
Bahriye Basturk Akay (bahriye@erciyes.edu.tr)
*/

#include <memory>
#include <iostream>
#include <fstream>
#include <string>
#include <filesystem>

#include "ABC.h"
#include "Sphere.h"
#include "SumOfSquares.h"
#include "Schwefel.h"
#include "Rastrigin.h"
#include "Ackley.h"
#include "Rosenbrock.h"
#include "Griewank.h"
#include "SixHumpCamelBack.h"
#include "ShekelsFoxholes.h"
#include "GoldsteinPrice.h"
#include "Branin.h"
#include "Hartman.h"


void writeResultsToFile(std::string name, double results[], int size);

int main() {

    srand(time(NULL));

    const int NUMBER_OF_RUNS = 100;
    const int MAX_EVALUATIONS = 15000;
    int NP = 125;
    int limit = 100;

    std::vector<std::unique_ptr<Problem>> problems;
    problems.push_back(std::make_unique<Sphere>(30));
    problems.push_back(std::make_unique<SumOfSquares>(30));
    problems.push_back(std::make_unique<Schwefel>(30));
    problems.push_back(std::make_unique<Rastrigin>(30));
    problems.push_back(std::make_unique<Ackley>(30));
    problems.push_back(std::make_unique<Griewank>(30));
    problems.push_back(std::make_unique<Rosenbrock>(30));
    problems.push_back(std::make_unique<ShekelsFoxholes>());
    problems.push_back(std::make_unique<SixHumpCamelBack>());
    problems.push_back(std::make_unique<Branin>());
    problems.push_back(std::make_unique<GoldsteinPrice>());
    problems.push_back(std::make_unique<Hartman>());

    double results[NUMBER_OF_RUNS];

    for (const auto& problem : problems) {
        std::cout<<"Problem: " << problem->name << std::endl;
        for (int run = 0; run < NUMBER_OF_RUNS; run++) {
            ABC abc(problem.get(), NP, limit, MAX_EVALUATIONS);
            results[run] = abc.run();
            std::cout << run + 1 << ". run: " << results[run] << std::endl;
        }
        writeResultsToFile( "ABC-Author-C_" + problem->name+"D"+std::to_string(problem->dimensions), results, NUMBER_OF_RUNS);
    }
}

void writeResultsToFile(std::string name, double results[], int size) {

    std::string projectDirectory = std::filesystem::current_path().string();
    std::filesystem::path projectDirFile(projectDirectory);
    std::filesystem::path parentDirFile = projectDirFile.parent_path().parent_path().parent_path().parent_path().parent_path();
    std::string filesDir = parentDirFile.string() + std::string(1, std::filesystem::path::preferred_separator) + "EARS comparison" + std::string(1,std::filesystem::path::preferred_separator) + "Algorithm results";

    std::string fileLocation = filesDir + std::string(1,std::filesystem::path::preferred_separator) + name + ".txt";

    std::filesystem::path file(fileLocation);
    std::string directory = file.parent_path().string();
    if (directory.empty()) {
        directory = std::filesystem::current_path().string();
        fileLocation = directory + std::string(1,std::filesystem::path::preferred_separator) + fileLocation;
        file = std::filesystem::path(fileLocation);
    }
    std::filesystem::path fileDirectory(directory);
    try {
        std::filesystem::create_directories(fileDirectory);
        std::ofstream outfile(file);
        for (int i = 0; i < size; i++) {
            outfile << results[i] << std::endl;
        }
        outfile.close();
    }
    catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
    }
}