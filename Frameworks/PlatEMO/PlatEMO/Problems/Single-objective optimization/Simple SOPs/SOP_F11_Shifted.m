classdef SOP_F11_Shifted < PROBLEM
% <single> <real> <expensive/none>
% Generalized Griewank's function

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
            obj.lower    = zeros(1,obj.D) - 600;
            obj.upper    = zeros(1,obj.D) + 600;
            obj.encoding = ones(1,obj.D);
            obj.shift = [104.94691081342978, -135.6761348470227, 92.81100891955543, -10.87108518188711, 407.63013054172563, ...
                        -223.31315231746964, -224.56627659185563, -222.7632371161696, -338.7309010063917, -236.65660889064418, ...
                        -102.44810336184622, 126.42998956660733, 145.51932139597716, 362.4195007946686, -136.07822974158148, ...
                        385.3787391034516, 224.54916653990915, 43.478124239142176, 174.10299590196337, -214.8310656032395, ...
                        -398.01422639093084, -309.2614310193993, -178.06570153291875, -162.6651823263993, 450.850657541738, ...
                        -454.92932203096916, -419.61768171271166, -297.80826342133184, 66.9431726795051, 81.54612611060406, ...
                        33.75350391540678, 452.7503721088576, -394.37679317004665, 211.96497077719948, 209.2762825707889, ...
                        -456.2796841185877, 43.786866583660526, 39.94139518856173, -392.0757065399322, 368.3834199650955, ...
                        -230.1695108177757, 260.9386598025741, 424.9982235544909, 219.5539088594145, -56.74195565523951, ...
                        353.31823225966764, -314.409074063865, -344.22129416136335, 350.389300480917, 210.6305580593255, ...
                        -351.3688705488921, -419.56080494622336, -352.4149162964608, -306.0148711883166, 234.1092941008617, ...
                        179.7062426106279, -180.45299833941465, -353.7895714765393, 304.63535113945875, 11.040575864524044];
        end
        %% Calculate objective values
        function PopObj = CalObj(obj,PopDec)
            PopObj = 1/4000*sum((PopDec - obj.shift).^2,2) - prod(cos((PopDec - obj.shift)./sqrt(repmat(1:size((PopDec - obj.shift),2),size((PopDec - obj.shift),1),1))),2) + 1;
        end
    end
end