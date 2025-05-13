classdef SOP_F10_Shifted < PROBLEM
% <single> <real> <expensive/none>
% Ackley's function

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
            obj.lower    = zeros(1,obj.D) - 32;
            obj.upper    = zeros(1,obj.D) + 32;
            obj.encoding = ones(1,obj.D);
            obj.shift = [21.125524221556333, -11.11861062406522, -8.384759722218377, -4.998410532366073, 15.080716791129063, ...
                        17.219200462474568, 17.30816180946608, 10.917710600242835, 20.963400683396372, 19.33124762324178, ...
                        -10.07785283476705, -0.6470251048379687, -9.17403575481461, 0.2263125149343992, 3.5360559029458436, ...
                        -24.976221736741277, 5.4509130845083185, -22.008998257857435, 4.319939429353628, -0.015227633728962076, ...
                        -10.266583117363774, -2.984262944040246, -14.575704508621596, 9.332038871399178, 12.260212736282845, ...
                        2.6286817951067007, 3.7062873124753715, -7.422751349625646, 22.839645124520274, 18.139957746273467, ...
                        -23.12538796733297, 3.106978031277709, -21.8650793129579, 23.285704349592457, -8.877654886311145, ...
                        -15.936478675865914, -14.529257223964459, -0.5256354355698463, 3.421693809849465, -22.925462999946205, ...
                        -13.789171558857868, -12.260934479396866, 0.8518120279262078, -6.2876202233673375, 17.750101847964885, ...
                        24.91013310009094, -3.1246259173628452, -7.631501678844927, -7.124798758640981, 0.9420940535559126, ...
                        -3.5298708787570163, -13.655076475547936, -3.220475098055129, -0.7478333903264627, 13.62921655558997, ...
                        -3.336289145576181, -1.7729883120516092, -2.487660518823951, 13.831869546225832, 3.8314346375240227];
        end
        %% Calculate objective values
        function PopObj = CalObj(obj,PopDec)
            PopObj = -20*exp(-0.2*sqrt(mean((PopDec - obj.shift).^2,2))) - exp(mean(cos(2*pi*(PopDec - obj.shift)),2)) + 20 + exp(1);
        end
    end
end