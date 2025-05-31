function ObjVal = SumOfSquares(Chrom,switch1)

% Compute population parameters
[Nind,Nvar] = size(Chrom);

fitness = zeros(Nind,1);
for n = 1:Nind
    for i = 1:Nvar
        fitness(n) = fitness(n) + i * Chrom(n,i)^2;
    end
end

ObjVal = fitness;

