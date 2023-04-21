function ObjVal = Branin(Chrom, switch1)

% Dimension of objective function
Dim = 2;

% Compute population parameters
[Nind, Nvar] = size(Chrom);

ObjVal = (Chrom(:,2) - (5.1 / (4 * pi^2)) .* Chrom(:,1).^2 + (5 / pi) .* Chrom(:,1) - 6).^2 ...
+ 10 .* (1 - 1 / (8 * pi)) .* cos(Chrom(:,1)) + 10;

end