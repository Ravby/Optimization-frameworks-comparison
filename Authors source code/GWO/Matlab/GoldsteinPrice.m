function ObjVal = GoldsteinPrice(Chrom,switch1)

% Dimension of objective function
Dim=size(Chrom,2);

% Compute population parameters
[Nind,Nvar] = size(Chrom);

ObjVal = (1 + (Chrom(:,1) + Chrom(:,2) + 1).^2 .* (19 - 14 .* Chrom(:,1) + 3 .* Chrom(:,1).^2 - 14 .* Chrom(:,2) + 6 .* Chrom(:,1) .* Chrom(:,2) + 3 .* Chrom(:,2).^2)) .* ...
         (30 + (2 .* Chrom(:,1) - 3 .* Chrom(:,2)).^2 .* (18 - 32 .* Chrom(:,1) + 12 .* Chrom(:,1).^2 + 48 .* Chrom(:,2) - 36 .* Chrom(:,1) .* Chrom(:,2) + 27 .* Chrom(:,2).^2));
end