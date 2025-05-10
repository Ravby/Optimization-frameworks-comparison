%___________________________________________________________________%
%  Grey Wolf Optimizer (GWO) source codes version 1.0               %
%                                                                   %
%  Developed in MATLAB R2011b(7.13)                                 %
%                                                                   %
%  Author and programmer: Seyedali Mirjalili                        %
%                                                                   %
%         e-Mail: ali.mirjalili@gmail.com                           %
%                 seyedali.mirjalili@griffithuni.edu.au             %
%                                                                   %
%       Homepage: http://www.alimirjalili.com                       %
%                                                                   %
%   Main paper: S. Mirjalili, S. M. Mirjalili, A. Lewis             %
%               Grey Wolf Optimizer, Advances in Engineering        %
%               Software , in press,                                %
%               DOI: 10.1016/j.advengsoft.2013.12.007               %
%                                                                   %
%___________________________________________________________________%

% You can simply define your cost in a seperate file and load its handle to fobj 
% The initial parameters that you need are:
%__________________________________________
% fobj = @YourCostFunction
% dim = number of your variables
% Max_iteration = maximum number of generations
% SearchAgents_no = number of search agents
% lb=[lb1,lb2,...,lbn] where lbn is the lower bound of variable n
% ub=[ub1,ub2,...,ubn] where ubn is the upper bound of variable n
% If all the variables have equal lower bound you can just
% define lb and ub as two single number numbers

% To run GWO: [Best_score,Best_pos,GWO_cg_curve]=GWO(SearchAgents_no,Max_iteration,lb,ub,dim,fobj)
%__________________________________________

clear all 
clc

maxEvaluations = 15000;
maxIterations=500; % Maximum numbef of iterations
populationSize=30; % Number of search agents
runtime=100;

% Function_name='rosenbrock';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end

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


for f = 1:length(functions)
    objfun = functions{f}{1};
    D = functions{f}{2};
    ub = functions{f}{3};
    lb = functions{f}{4};


    fprintf('Problem %s\n', objfun);
    
    GlobalMins=zeros(1,runtime);
    
    for r=1:runtime

        [Best_score,Best_pos,GWO_cg_curve, evaluations]=GWO(populationSize,maxIterations,lb,ub,D,objfun);
        
        if evaluations ~= maxEvaluations
            fprintf('Error: algorithm consumed more evaluations than allowed!\n');
        end
        
        display(num2str(Best_score, 20));
        fprintf('%d\n',Best_score);
        GlobalMins(r)=Best_score;
    end
    
    writeResultsToFile( "GWO-Author-Matlab_" + objfun +"D"+D, GlobalMins);
    
end

save all

% Function_name='Rosenbrock';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end
% 
% Function_name='griewank';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end
% 
% Function_name='ackley';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end
% 
% Function_name='rastrigin';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end
% 
% Function_name='schwefel';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end


% Function_name='zakharov';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end


% Function_name='dixonprice';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end

% Function_name='trid';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end
% 
% Function_name='salomon';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end
% 
% Function_name='powell';
% disp(Function_name);
% [lb,ub,dim,fobj]=Get_Functions_details(Function_name);
% for r=1:runtime
%     [Best_score,Best_pos,GWO_cg_curve]=GWO(populationSize,maxIterations,lb,ub,dim,fobj);
%     display(num2str(Best_score, 20));
% end


% figure('Position',[500 500 660 290])
% %Draw search space
% subplot(1,2,1);
% func_plot(Function_name);
% title('Parameter space')
% xlabel('x_1');
% ylabel('x_2');
% zlabel([Function_name,'( x_1 , x_2 )'])
% 
% %Draw objective space
% subplot(1,2,2);
% semilogy(GWO_cg_curve,'Color','r')
% title('Objective space')
% xlabel('Iteration');
% ylabel('Best score obtained so far');
% 
% axis tight
% grid on
% box on
% legend('GWO')

%display(['The best solution obtained by GWO is : ', num2str(Best_pos)]);


        



