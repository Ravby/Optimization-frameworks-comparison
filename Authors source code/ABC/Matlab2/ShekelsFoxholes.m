function ObjVal = ShekelsFoxholes(Chrom,switch1)

a = [-32 -32;
     -16 -32;
      0  -32;
     16  -32;
     32  -32;
     -32 -16;
     -16 -16;
      0  -16;
     16  -16;
     32  -16;
     -32   0;
     -16   0;
      0    0;
     16    0;
     32    0;
     -32  16;
     -16  16;
      0   16;
     16   16;
     32   16;
     -32  32;
     -16  32;
      0   32;
     16   32;
     32   32];
 
 
 % Compute population parameters
 [Nind,Nvar] = size(Chrom);
 
 fitness = zeros(Nind,1);
 for n = 1:Nind
     for j = 1:25
         sum_ = 0;
         for i = 1:Nvar
             sum_ = sum_ + (Chrom(n, i) - a(j, i))^6;
         end
         sum_ = sum_ + j;
         fitness(n) = fitness(n) + 1.0/sum_;
     end
     fitness(n) = fitness(n) + 1.0/500.0;
     fitness(n) = 1.0/fitness(n);
 end
 ObjVal = fitness;
end