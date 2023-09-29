function ObjVal = Hartman(Chrom, switch1)

a = [    3, 10, 30;    0.1, 10, 35;    3, 10, 30;    0.1, 10, 35];
p = [    0.3689, 0.1170, 0.2673;    0.4699, 0.4387, 0.7470;    0.1091, 0.8732, 0.5547;    0.03815, 0.5743, 0.8828];
c = [1, 1.2, 3, 3.2];

% Compute population parameters
[Nind, Nvar] = size(Chrom);

fitness = zeros(Nind, 1);
for i = 1:size(a, 1)
    sum_ = 0;
    for j = 1:Nvar
        sum_ = sum_ + a(i,j) * (Chrom(:,j) - p(i,j)).^2;
    end
    fitness = fitness + c(i) * exp(-sum_);
end
ObjVal = -fitness;
end