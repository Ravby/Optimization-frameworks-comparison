classdef SOP_F24 < PROBLEM
    % <single> <real> <expensive/none>
    % Sum of Squared function

    methods
        %% Default settings of the problem
        function Setting(obj)
            obj.M = 1;
            if isempty(obj.D); obj.D = 30; end
            obj.lower    = zeros(1,obj.D) - 100;
            obj.upper    = zeros(1,obj.D) + 100;
            obj.encoding = ones(1,obj.D);
        end
        %% Calculate objective values
        function PopObj = CalObj(obj,PopDec)
            n = size(PopDec, 2);
            weights = 1:n;
            PopObj = sum(weights .* (PopDec .^ 2), 2);

            EventManager.getInstance().evaluationPerformed(PopObj, PopDec);
        end
    end
end