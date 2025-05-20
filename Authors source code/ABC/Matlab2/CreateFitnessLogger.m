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
        % Get number of individuals
        Nind = size(x, 1);
        
        % Compute fitness for all individuals
        ObjVal = fitnessFunc(x);
        
        % Validate output
        if ~isvector(ObjVal) || length(ObjVal) ~= Nind
            error('Fitness function must return a vector of length %d, got size: %s', ...
                Nind, mat2str(size(ObjVal)));
        end
        
        % Ensure ObjVal is a column vector
        ObjVal = ObjVal(:);
        
        % Check for improvements
        for i = 1:Nind
            evaluationCount = evaluationCount + 1; % Increment per individual
            if isFirstEvaluation || ObjVal(i) < bestFitness
                improvements = [improvements; evaluationCount, ObjVal(i)];
                bestFitness = ObjVal(i);
                isFirstEvaluation = false;
            end
        end
    end

    function result = getImprovementsImpl()
        result = improvements;
    end

    function count = getEvaluationCountImpl()
        count = evaluationCount;
    end
end


