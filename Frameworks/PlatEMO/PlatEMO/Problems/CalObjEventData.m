% Custom event data class to pass fitness and decision variables
classdef CalObjEventData < event.EventData
    properties
        Problem
        Evaluation
        Fitness % Store the calculated fitness values (PopObj)
        PopDec  % Store the input decision variables
    end
    methods
        function obj = CalObjEventData(problem, fitness, popDec, evaluationCount)
            obj.Problem = problem;
            obj.Evaluation = evaluationCount;
            obj.Fitness = fitness;
            obj.PopDec = popDec;
        end
    end
end