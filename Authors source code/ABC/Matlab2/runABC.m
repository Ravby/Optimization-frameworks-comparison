%/* ABC algorithm coded using MATLAB language */

%/* Artificial Bee Colony (ABC) is one of the most recently defined algorithms by Dervis Karaboga in 2005, motivated by the intelligent behavior of honey bees. */

%/* Reference Papers*/

%/*D. Karaboga, AN IDEA BASED ON HONEY BEE SWARM FOR NUMERICAL OPTIMIZATION,TECHNICAL REPORT-TR06, Erciyes University, Engineering Faculty, Computer Engineering Department 2005.*/

%/*D. Karaboga, B. Basturk, A powerful and Efficient Algorithm for Numerical Function Optimization: Artificial Bee Colony (ABC) Algorithm, Journal of Global Optimization, Volume:39, Issue:3,pp:459-171, November 2007,ISSN:0925-5001 , doi: 10.1007/s10898-007-9149-x */

%/*D. Karaboga, B. Basturk, On The Performance Of Artificial Bee Colony (ABC) Algorithm, Applied Soft Computing,Volume 8, Issue 1, January 2008, Pages 687-697. */

%/*D. Karaboga, B. Akay, A Comparative Study of Artificial Bee Colony Algorithm,  Applied Mathematics and Computation, 214, 108-132, 2009. */

%/*Copyright � 2009 Erciyes University, Intelligent Systems Research Group, The Dept. of Computer Engineering*/

%/*Contact:
%Dervis Karaboga (karaboga@erciyes.edu.tr )
%Bahriye Basturk Akay (bahriye@erciyes.edu.tr)
%*/

rng('shuffle');

clear all
close all
clc

%/* Control Parameters of ABC algorithm*/
NP=125; %/* The number of colony size (employed bees+onlooker bees)*/
limit=100; %/*A food source which could not be improved through "limit" trials is abandoned by its employed bee*/
maxEval=15000; %/*The number of cycles for foraging {a stopping criteria}*/


%/* Problem specific variables*/

functions = {
    {'ShiftedSphere', 60, ones(1,60)*100, ones(1,60)*(-100)},
    {'ShiftedSumOfSquares', 60, ones(1,60)*100, ones(1,60)*(-100)},
    {'ShiftedSchwefel', 60, ones(1,60)*100, ones(1,60)*(-100)},
    {'ShiftedRastrigin', 60, ones(1,60)*5.12, ones(1,60)*(-5.12)},
    {'ShiftedAckley', 60, ones(1,60)*32.0, ones(1,60)*(-32.0)},
    {'ShiftedGriewank', 60, ones(1,60)*600.0, ones(1,60)*(-600.0)}
    {'Sphere', 60, ones(1,60)*100, ones(1,60)*(-100)},
    {'SumOfSquares', 60, ones(1,60)*100, ones(1,60)*(-100)},
    {'Schwefel', 60, ones(1,60)*100, ones(1,60)*(-100)},
    {'Rastrigin', 60, ones(1,60)*5.12, ones(1,60)*(-5.12)},
    {'Ackley', 60, ones(1,60)*32.0, ones(1,60)*(-32.0)},
    {'Griewank', 60, ones(1,60)*600.0, ones(1,60)*(-600.0)},
    {'Rosenbrock', 60, ones(1,60)*30.0, ones(1,60)*(-30.0)},
    {'ShekelsFoxholes', 2, ones(1,2)*65.536, ones(1,2)*(-65.536)},
    {'SixHumpCamelBack', 2, ones(1,2)*5.0, ones(1,2)*(-5.0)},
    {'Branin', 2, [10.0, 15.0], [-5.0, 0.0]},
    {'GoldsteinPrice', 2, ones(1,2)*2.0, ones(1,2)*(-2.0)},
    {'Hartman', 3, ones(1,3)*1.0, ones(1,3)*(0.0)}
    };

runtime=50;


%test functions
% for f = 1:length(functions)
%     objfun = functions{f}{1};
%     D = functions{f}{2};
%     ub = functions{f}{3};
%     lb = functions{f}{4};
%   
%     fitnessFunc = str2func(objfun);
%     
%     position = ones(1,D);
%     fitness=feval(fitnessFunc,position);
% 
%     if abs(fitness) >= 1e6
%         fprintf('Problem: %s\nFitness at all ones: %.10E\n\n', objfun, fitness);
%     else
%         fprintf('Problem: %s\nFitness at all ones: %.10f\n\n', objfun, fitness);
%     end
% end

for f = 1:length(functions)
    objfun = functions{f}{1};
    D = functions{f}{2};
    ub = functions{f}{3};
    lb = functions{f}{4};
    
    fprintf('Problem %s\n', objfun);
    
    fitnessFunc = str2func(objfun);
    
    GlobalMins=zeros(1,runtime);
    
    for r=1:runtime
        
        clear CreateFitnessLogger
        
        [loggedFitness, getImprovements, getEvaluationCount] = CreateFitnessLogger(fitnessFunc);
        
        [GlobalMin, currentEval] = ABC(loggedFitness, D, ub, lb, NP, limit, maxEval);
        if currentEval ~= maxEval
            fprintf('Error: algorithm consumed more evaluations than allowed!\n');
        end

        fprintf('%d\n',GlobalMin);
        GlobalMins(r)=GlobalMin;
        
        %fprintf('Evaluation count check: %d\n',getEvaluationCount());
        
        filename = sprintf('ABC-Author-Matlab_%s_vars=%d_run=%d', objfun, D, r);
        WriteRunToFile(filename, getImprovements());
    end
    
    writeResultsToFile( "ABC-Author-Matlab_" + objfun +"D"+D, GlobalMins);
    
end

save all

