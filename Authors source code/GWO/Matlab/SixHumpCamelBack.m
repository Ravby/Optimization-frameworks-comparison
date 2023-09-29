function ObjVal = SixHumpCamelBack(Chrom, switch1)

% Compute population parameters
[Nind, Nvar] = size(Chrom);

ObjVal = zeros(Nind, 1); % initialize ObjVal as a column vector

for i = 1:Nind
    ObjVal(i) = 4 * Chrom(i, 1)^2 ...
        - 2.1 * Chrom(i, 1)^4 ...
        + (1/3) * Chrom(i, 1)^6 ...
        + Chrom(i, 1) * Chrom(i, 2) ...
        - 4 * Chrom(i, 2)^2 ...
        + 4 * Chrom(i, 2)^4;
end

end



