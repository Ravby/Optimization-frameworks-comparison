classdef SOP_F1_Shifted < PROBLEM
% <single> <real> <expensive/none>
% Shifted Sphere function

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
            if isempty(obj.D); obj.D = 100; end % Set to 100 dimensions as specified
            obj.lower    = zeros(1,obj.D) - 100;
            obj.upper    = zeros(1,obj.D) + 100;
            obj.encoding = ones(1,obj.D);
            % Define the shift vector
            obj.shift = [45.00458616074768, -50.34195790924958, 16.656571994777153, -70.66157384610746, 77.87240204480926, ...
                        41.15636478376551, 49.128266155105166, -60.307473748449034, 33.10423742373693, 0.6236286355758835, ...
                        27.460586290936902, -36.02013445020688, -40.00877054048374, -64.66271141874185, 68.90566619452034, ...
                        62.70237277088032, -4.074486986783384, -1.317873518733137, -55.54285608300457, -47.34319489184719, ...
                        12.507912147062243, -40.535720288582304, 33.31036155896686, -28.838912135698926, -1.4693369446360407, ...
                        -10.418532078512769, 73.36998380030019, -21.21493655887511, 44.00620956459555, 53.63261051536182, ...
                        2.7285937751837395, 13.470863898732262, 18.406813289986204, 79.81576486014623, 11.44319844208276, ...
                        54.93447134455431, 57.30432717171027, 40.23921236138773, 65.67885067903487, 26.366481706628406, ...
                        -27.30759152512293, 25.811669049736366, 33.765769465674026, 46.064188205488364, 68.68320146596031, ...
                        -30.14247692338735, -20.27099815222924, 72.28995446564878, -23.225596585148693, -17.707777462132427, ...
                        -16.533526290183076, 52.71601762751186, -34.19215602995044, 17.794220792755056, 62.28063781336567, ...
                        48.17997449949212, -62.87666397080855, -0.3946724203053975, -63.61229513391514, 62.025414757794124];
        end
        %% Calculate objective values
        function PopObj = CalObj(obj,PopDec)
            %disp(size(PopDec));
            %disp(size(obj));
            % Subtract the shift vector from the decision variables and compute the sphere function
            PopObj = sum((PopDec - obj.shift).^2, 2);
        end
    end
end