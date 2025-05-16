function [loggedFitness, getImprovements] = CreateFitnessLogger(fitnessFunc)
    persistent evaluationCount bestFitness isFirstEvaluation improvements
    if isempty(evaluationCount)
        evaluationCount = 0;
        bestFitness = Inf;
        isFirstEvaluation = true;
        improvements = [];
    end

    loggedFitness = @loggedFitnessImpl;
    getImprovements = @getImprovementsImpl;

    function ObjVal = loggedFitnessImpl(x)
        evaluationCount = evaluationCount + 1;
        ObjVal = fitnessFunc(x);

        if isFirstEvaluation || ObjVal < bestFitness
            improvements = [improvements; evaluationCount, ObjVal];
            bestFitness = ObjVal;
            isFirstEvaluation = false;
        end
    end

    function result = getImprovementsImpl()
        result = improvements;
    end
end
