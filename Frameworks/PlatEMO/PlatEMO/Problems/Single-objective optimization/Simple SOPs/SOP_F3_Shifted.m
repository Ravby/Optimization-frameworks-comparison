classdef SOP_F3_Shifted < PROBLEM
% <single> <real> <expensive/none>
% Schwefel's function 1.2

%------------------------------- Reference --------------------------------
% X. Yao, Y. Liu, and G. Lin, Evolutionary programming made faster, IEEE
% Transactions on Evolutionary Computation, 1999, 3(2): 82-102.
%------------------------------- Copyright --------------------------------
% Copyright (c) 2023 BIMK Group. You are free to use the PlatEMO for
% research purposes. All publications which use this platform or any code
% in the platform should acknowledge the use of "PlatEMO" and reference "Ye
% Tian, Ran Cheng, Xingyi Zhang, and Yaochu Jin, PlatEMO: A MATLAB platform
% for evolutionary multi-objective optimization [educational forum], IEEE
% Computational Intelligence Magazine, 2017, 12(4): 73-87".
%--------------------------------------------------------------------------

    properties
        shift; % Shift vector for the problem
    end

    methods
        %% Default settings of the problem
        function Setting(obj)
            obj.M = 1;
            if isempty(obj.D); obj.D = 30; end
            obj.lower    = zeros(1,obj.D) - 100;
            obj.upper    = zeros(1,obj.D) + 100;
            obj.encoding = ones(1,obj.D);
            % Define the shift vector
            obj.shift = [65.90306349157711, 7.223609380925922, 48.06403595428989, -61.23008904674027, -56.996531732952235, ...
                        -65.67006959079087, 56.81852866354663, -49.51353093024842, -42.403457350576694, 64.22460834371887, ...
                        -15.782490031817943, 78.2275393766464, 58.981759831549084, -41.74229801452056, 70.70390026719807, ...
                        -27.120714671264494, -43.362534341811525, -11.357893018802812, 25.329639322623194, -24.02213869456814, ...
                        -29.51839742945309, -50.65352492132828, -17.33907934005014, -75.7276769694174, 25.60834507308691, ...
                        -40.61133175959546, 53.35222265494423, 30.414516938139826, -37.83651132063628, 30.243572616044418, ...
                        -2.2080543227080227, -51.34193306616531, 37.36039082956739, -44.93167877354562, 63.19570228206453, ...
                        -0.3230550173947364, 54.997116479336086, -39.773290218583256, 54.1565458883382, 12.124637138785076, ...
                        43.85247188324382, -75.91826727683562, 49.74255951193999, 63.92380934836413, 54.34684698334098, ...
                        -19.563396930885908, 31.656857648987852, -32.907795698796306, -29.963471074339374, -60.672896416251675, ...
                        -77.62660870400686, -75.95478227792732, 23.6157226667015, 75.57747129002121, -15.915369172179027, ...
                        -45.240636434786694, 34.3519391836095, 65.07767014581174, 13.32242757194949, 71.34237817983137];
        end
        %% Calculate objective values
        function PopObj = CalObj(obj,PopDec)
            PopObj = sum(cumsum((PopDec - obj.shift),2).^2,2);

            EventManager.getInstance().evaluationPerformed(PopObj, PopDec);
        end
    end
end