function ObjVal = Schwefel(Chrom, switch1)

% Get dimensions of chromosome array
[Nind, Nvar] = size(Chrom);

fitness = zeros(Nind,1);
for n = 1:Nind
    sum_ = 0;
    for j = 1:Nvar
        sum_ = sum_ + Chrom(n,j);
    end
    fitness(n) = fitness(n) + sum_^2;
end

ObjVal = fitness;

end