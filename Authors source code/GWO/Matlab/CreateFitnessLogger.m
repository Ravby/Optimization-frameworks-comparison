function [loggedFitness, getImprovements, getEvaluationCount] = CreateFitnessLogger(fitnessFunc)
    persistent evaluationCount bestFitness isFirstEvaluation improvements
    if isempty(evaluationCount)
        evaluationCount = 0;
        bestFitness = Inf;
        isFirstEvaluation = true;
        improvements = [];
    end

    loggedFitness = @loggedFitnessImpl;
    getImprovements = @getImprovementsImpl;
    getEvaluationCount = @getEvaluationCountImpl;

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

    function count = getEvaluationCountImpl()
        count = evaluationCount;
    end
end
