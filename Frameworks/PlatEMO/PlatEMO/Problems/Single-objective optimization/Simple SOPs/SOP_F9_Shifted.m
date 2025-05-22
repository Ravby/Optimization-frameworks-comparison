classdef SOP_F9_Shifted < PROBLEM
% <single> <real> <expensive/none>
% Generalized Rastrigin's function

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
            obj.lower    = zeros(1,obj.D) - 5.12;
            obj.upper    = zeros(1,obj.D) + 5.12;
            obj.encoding = ones(1,obj.D);
            % Define the shift vector
            obj.shift = [4.070088073870897, -3.3584914395112166, 2.5276924556298725, 1.373781594176445, -1.8387129521132595, ...
                        2.057549949794417, -0.0023364913111585395, 2.013487904123477, -3.7248964059839147, -0.33919629789907413, ... 
                        2.8301821463273367, -2.2929959706368352, -2.2706325455604484, -3.513692538288365, -1.523336663056385, ... 
                        -4.0764777207287075, -1.6237586256460155, 0.9178097684770039, -0.2723487256238868, -2.9841270159498463, ... 
                        2.9288432571682828, 0.22934413055341984, 3.720378352924264, -1.429905611686805, 1.6068438224463835, ...
                        2.1513735804078467, -0.6971225700819095, -2.8180867567781878, -1.2171333029086893, -4.064563849878518, ...
                        1.3543350787859474, 2.4050645503835417, -2.5584324886855985, 1.5660383241939355, 2.1289004812453385, ...
                        3.8337296275949706, -0.5606593824135673, 1.141577243686811, 0.8120054159429309, 0.5394159295625087, ...
                        -2.5598649074502093, 4.033201745873486, 3.24227861085845, 2.916933432390337, 3.639752419533484, ...
                        1.5188619717725969, 3.839178330380485, 3.975865667365813, -1.191858979126291, -3.489747339689812, ...
                        3.6319133591436685, -0.8694397826397151, -0.039238131176527524, 0.857258173067124, 3.5742721242496724, ...
                        -1.2964486629139182, 0.622681353542129, -3.2417846861744932, -3.5847655715322952, -0.20674408185538518];
        end
        %% Calculate objective values
        function PopObj = CalObj(obj,PopDec)
            PopObj = sum((PopDec - obj.shift).^2-10*cos(2*pi*(PopDec - obj.shift))+10,2);

            EventManager.getInstance().evaluationPerformed(PopObj, PopDec);
        end
    end
end