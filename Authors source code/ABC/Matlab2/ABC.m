function [GlobalMin,currentEval] = ABC(objfun, D, ub, lb, NP, limit, maxEval)

FoodNumber=floor(NP/2);
currentEval = 0;

% /*All food sources are initialized */
%/*Variables are initialized in the range [lb,ub]. If each parameter has different range, use arrays lb[j], ub[j] instead of lb and ub */

Range = repmat((ub-lb),[FoodNumber 1]);
Lower = repmat(lb, [FoodNumber 1]);
Foods = rand(FoodNumber,D) .* Range + Lower;

currentEval = currentEval + FoodNumber;

ObjVal=feval(objfun,Foods);
Fitness=calculateFitness(ObjVal);

%reset trial counters
trial=zeros(1,FoodNumber);

%/*The best food source is memorized*/
BestInd=find(ObjVal==min(ObjVal));
BestInd=BestInd(end);
GlobalMin=ObjVal(BestInd);
GlobalParams=Foods(BestInd,:);

iter=1;
while ((currentEval < maxEval))
    
    %%%%%%%%% EMPLOYED BEE PHASE %%%%%%%%%%%%%%%%%%%%%%%%
    for i=1:(FoodNumber)
        if(currentEval >= maxEval)
            break;
        end
        %/*The parameter to be changed is determined randomly*/
        Param2Change=fix(rand*D)+1;
        
        %/*A randomly chosen solution is used in producing a mutant solution of the solution i*/
        neighbour=fix(rand*(FoodNumber))+1;
        
        %/*Randomly selected solution must be different from the solution i*/
        while(neighbour==i)
            neighbour=fix(rand*(FoodNumber))+1;
        end
        
        sol=Foods(i,:);
        %  /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
        sol(Param2Change)=Foods(i,Param2Change)+(Foods(i,Param2Change)-Foods(neighbour,Param2Change))*(rand-0.5)*2;
        
        %  /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
        ind=find(sol<lb);
        sol(ind)=lb(ind);
        ind=find(sol>ub);
        sol(ind)=ub(ind);
        
        %evaluate new solution
        currentEval = currentEval + 1;
        ObjValSol=feval(objfun,sol);
        FitnessSol=calculateFitness(ObjValSol);
        
        % /*a greedy selection is applied between the current solution i and its mutant*/
        if (FitnessSol>Fitness(i)) %/*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
            Foods(i,:)=sol;
            Fitness(i)=FitnessSol;
            ObjVal(i)=ObjValSol;
            trial(i)=0;
        else
            trial(i)=trial(i)+1; %/*if the solution i can not be improved, increase its trial counter*/
        end
        
        
    end
    
    %%%%%%%%%%%%%%%%%%%%%%%% CalculateProbabilities %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    %/* A food source is chosen with the probability which is proportioal to its quality*/
    %/*Different schemes can be used to calculate the probability values*/
    %/*For example prob(i)=fitness(i)/sum(fitness)*/
    %/*or in a way used in the method below prob(i)=a*fitness(i)/max(fitness)+b*/
    %/*probability values are calculated by using fitness values and normalized by dividing maximum fitness value*/
    
    prob=(0.9.*Fitness./max(Fitness))+0.1;
    
    %%%%%%%%%%%%%%%%%%%%%%%% ONLOOKER BEE PHASE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    i=1;
    t=0;
    while(t<FoodNumber)
        if(currentEval >= maxEval)
            break;
        end
        if(rand<prob(i))
            t=t+1;
            %/*The parameter to be changed is determined randomly*/
            Param2Change=fix(rand*D)+1;
            
            %/*A randomly chosen solution is used in producing a mutant solution of the solution i*/
            neighbour=fix(rand*(FoodNumber))+1;
            
            %/*Randomly selected solution must be different from the solution i*/
            while(neighbour==i)
                neighbour=fix(rand*(FoodNumber))+1;
            end
            
            sol=Foods(i,:);
            %  /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
            sol(Param2Change)=Foods(i,Param2Change)+(Foods(i,Param2Change)-Foods(neighbour,Param2Change))*(rand-0.5)*2;
            
            %  /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
            ind=find(sol<lb);
            sol(ind)=lb(ind);
            ind=find(sol>ub);
            sol(ind)=ub(ind);
            
            %evaluate new solution
            currentEval = currentEval + 1;
            ObjValSol=feval(objfun,sol);
            FitnessSol=calculateFitness(ObjValSol);
            
            % /*a greedy selection is applied between the current solution i and its mutant*/
            if (FitnessSol>Fitness(i)) %/*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
                Foods(i,:)=sol;
                Fitness(i)=FitnessSol;
                ObjVal(i)=ObjValSol;
                trial(i)=0;
            else
                trial(i)=trial(i)+1; %/*if the solution i can not be improved, increase its trial counter*/
            end
        end
        
        i=i+1;
        if (i==(FoodNumber)+1)
            i=1;
        end
    end
    
    
    %/*The best food source is memorized*/
    ind=find(ObjVal==min(ObjVal));
    ind=ind(end);
    if (ObjVal(ind)<GlobalMin)
        GlobalMin=ObjVal(ind);
        GlobalParams=Foods(ind,:);
    end
    
    
    %%%%%%%%%%%% SCOUT BEE PHASE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    %/*determine the food sources whose trial counter exceeds the "limit" value.
    %In Basic ABC, only one scout is allowed to occur in each cycle*/
    
    ind=find(trial==max(trial));
    ind=ind(end);
    if (trial(ind)>limit)
        if(currentEval >= maxEval)
            break;
        end
        trial(ind)=0;
        sol=(ub-lb).*rand(1,D)+lb;
        currentEval = currentEval + 1;
        ObjValSol=feval(objfun,sol);
        FitnessSol=calculateFitness(ObjValSol);
        Foods(ind,:)=sol;
        Fitness(ind)=FitnessSol;
        ObjVal(ind)=ObjValSol;
    end
    
    
    
    %fprintf('Iter=%d ObjVal=%g\n',iter,GlobalMin);
    iter=iter+1;
    
end % End of ABC

end

