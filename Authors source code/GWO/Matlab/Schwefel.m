function ObjVal = Schwefel(Chrom, switch1)

% Get dimensions of chromosome array
[Nind, Nvar] = size(Chrom);

fitness = zeros(Nind, 1);
for n = 1:Nind
    for i = 1:Nvar
        sum_ = 0;
        for j = 1:i-1
            sum_ = sum_ + Chrom(n, i);
        end
        fitness(n) = fitness(n) + sum_^2;
    end
end

ObjVal = fitness;

end