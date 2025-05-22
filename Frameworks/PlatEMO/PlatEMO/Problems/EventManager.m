classdef EventManager < handle
    properties
        EvaluationCount
        Improvements
    end
    % Define events
    %events
    %    GlobalEvent % Name of the global event
    %end

    methods (Access = private)
        function obj = EventManager()
            % Private constructor
            obj.EvaluationCount = 0;
            obj.Improvements = [];
        end
    end
    
    % Method to trigger the event
    methods (Static)
        function obj = getInstance()
            persistent uniqueInstance
            if isempty(uniqueInstance) || ~isvalid(uniqueInstance)
                uniqueInstance = EventManager();
            end
            obj = uniqueInstance;
        end
    end

    methods
        function evaluationPerformed(obj, PopObj, PopDec)
            for i=1:length(PopObj)
                obj.EvaluationCount = obj.EvaluationCount + 1;
                if isempty(obj.Improvements) || PopObj(i) < obj.Improvements(end, 2)
                    obj.Improvements = [obj.Improvements; [obj.EvaluationCount, PopObj(i)]];
                end      
            end
        end
        function improvements = getImprovements(obj)
            improvements = obj.Improvements;
        end
    end
end