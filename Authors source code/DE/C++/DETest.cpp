// Differential Evolution Test Program
// Based on algorithms developed by Dr. Rainer Storn & Kenneth Price
// Written By: Lester E. Godwin
//             PushCorp, Inc.
//             Dallas, Texas
//             972-840-0208 x102
//             godwin@pushcorp.com
// Created: 6/8/98
// Last Modified: 6/8/98
// Revision: 1.0

#include <stdio.h>
#include "DESolver.h"

// Polynomial fitting problem
class PolynomialSolver : public DESolver
{
public:
	PolynomialSolver(int dim,int pop) : DESolver(dim,pop), count(0) {;}
	double EnergyFunction(double trial[],bool &bAtSolution);

private:
	int count;
};

double PolynomialSolver::EnergyFunction(double *trial,bool &bAtSolution)
{
	int i, j;
	int const M=60;
	double px, x=-1, dx=M, result=0;

	dx = 2.0 / dx;
	for (i=0; i<=M; i++)
	{
		px = trial[0];
		for (j=1;j<nDim;j++)
			px = x*px + trial[j];

		if (px<-1 || px>1)
			result += (1 - px) * (1 - px);

		x += dx;
	}

	px = trial[0];
	for (j=1;j<nDim;j++)
		px = 1.2*px + trial[j];

	px = px - 72.661;
	if (px<0)
		result += px * px;

	px = trial[0];
	for (j=1; j<nDim; j++)
		px = -1.2*px + trial[j];

	px = px - 72.661;
	
	if (px<0)
		result+=px*px;

	if (count++ % nPop == 0)
		printf("%d %lf\n",count / nPop + 1,Energy());
	
	return(result);
}

#define N_DIM 9
#define N_POP 100
#define MAX_GENERATIONS	800
void main(void)
{
	double min[N_DIM];
	double max[N_DIM];
	int i;

	PolynomialSolver solver(N_DIM,N_POP);

	for (i=0;i<N_DIM;i++)
	{
		max[i] =  100.0;
		min[i] = -100.0;
	}

	solver.Setup(min,max,stBest1Exp,0.9,1.0);
	
	printf("Calculating...\n\n");
	solver.Solve(MAX_GENERATIONS);

	double *solution = solver.Solution();

	printf("\n\nBest Coefficients:\n");
	for (i=0;i<N_DIM;i++)
		printf("[%d]: %lf\n",i,solution[i]);

	return;
}
