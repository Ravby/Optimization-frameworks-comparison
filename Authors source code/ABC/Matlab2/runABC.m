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


clear all
close all
clc

%/* Control Parameters of ABC algorithm*/
NP=125; %/* The number of colony size (employed bees+onlooker bees)*/
limit=100; %/*A food source which could not be improved through "limit" trials is abandoned by its employed bee*/
maxEval=15000; %/*The number of cycles for foraging {a stopping criteria}*/


%/* Problem specific variables*/

functions = {
    {'Sphere', 30, ones(1,30)*100, ones(1,30)*(-100)}, 
    {'SumOfSquares', 30, ones(1,30)*100, ones(1,30)*(-100)}, 
    {'Schwefel', 30, ones(1,30)*100, ones(1,30)*(-100)},
    {'Rastrigin', 30, ones(1,30)*5.12, ones(1,30)*(-5.12)},
    {'Ackley', 30, ones(1,30)*32.0, ones(1,30)*(-32.0)},
    {'Griewank', 30, ones(1,30)*600.0, ones(1,30)*(-600.0)},
    {'Rosenbrock', 30, ones(1,30)*30.0, ones(1,30)*(-30.0)},
    {'ShekelsFoxholes', 2, ones(1,2)*65.536, ones(1,2)*(-65.536)},
    {'SixHumpCamelBack', 2, ones(1,2)*5.0, ones(1,2)*(-5.0)},
    {'Branin', 2, [10.0, 15.0], [-5.0, 0.0]},
    {'GoldsteinPrice', 2, ones(1,2)*2.0, ones(1,2)*(-2.0)},
    {'Hartman', 3, ones(1,3)*1.0, ones(1,3)*(0.0)}
};

runtime=100;


for f = 1:length(functions)
    objfun = functions{f}{1};
    D = functions{f}{2};
    ub = functions{f}{3};
    lb = functions{f}{4};

    fprintf('Problem %s\n', objfun);
    
    GlobalMins=zeros(1,runtime);
    
    for r=1:runtime

        GlobalMin = ABC(objfun, D, ub, lb, NP, limit, maxEval);    
        fprintf('%d\n',GlobalMin);
        GlobalMins(r)=GlobalMin;
    end
    
    writeResultsToFile( "ABC-Author-Matlab_" + objfun +"D"+D, GlobalMins);
    
end

save all

