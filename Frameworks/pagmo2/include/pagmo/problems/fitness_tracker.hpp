#ifndef FITNESS_TRACKER_H
#define FITNESS_TRACKER_H

#include <map>
#include <fstream>
#include <iostream>

class FitnessTracker
{
public:
    static int evaluationCount;
    static std::map<int, double> fitnessMap;

    // Add a fitness value for the current evaluation
    static void addFitness(double fitness)
    {
        evaluationCount++;
        // Check if fitness is smaller than the last one in fitnessMap
        if (fitnessMap.empty() || fitness < fitnessMap.rbegin()->second)
        {
            fitnessMap[evaluationCount] = fitness;
        }
    }

    // Get the entire fitness map
    static const std::map<int, double> &getFitnessMap()
    {
        return fitnessMap;
    }

    // Reset the tracker
    static void reset()
    {
        evaluationCount = 0;
        fitnessMap.clear();
    }

    // Save current progress to file
    static void saveProgressToFile(const std::string &filename)
    {
        // write the fitnessMap to a file in format "evaluationCount:fitness"
        std::ofstream outFile(filename);
        if (!outFile.is_open()) {
            std::cerr << "Error: Could not open file '" << filename << "' for writing." << std::endl;
            return;
        }

        outFile << std::fixed << std::setprecision(6);

        if (fitnessMap.empty()) {
            outFile << "No fitness evaluations recorded." << std::endl;
        } else {
            outFile << "Evaluations,Fitness" << std::endl;
            for (const auto &pair : fitnessMap) {
                outFile << pair.first << "," << pair.second << std::endl;
            }
        }

        outFile.close();

        reset();
    }
};

#endif // FITNESS_TRACKER_H