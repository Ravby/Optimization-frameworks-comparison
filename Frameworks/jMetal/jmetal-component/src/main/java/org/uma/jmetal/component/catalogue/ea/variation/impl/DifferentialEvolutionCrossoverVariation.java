package org.uma.jmetal.component.catalogue.ea.variation.impl;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.component.catalogue.ea.variation.Variation;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.NullMutation;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.sequencegenerator.SequenceGenerator;

/** */
public class DifferentialEvolutionCrossoverVariation implements Variation<DoubleSolution> {
  private int matingPoolSize;
  private int offspringPopulationSize;
  private SequenceGenerator<Integer> solutionIndexGenerator ;
  private DifferentialEvolutionCrossover crossover;
  private MutationOperator<DoubleSolution> mutation;

  public DifferentialEvolutionCrossoverVariation(
      int offspringPopulationSize,
      DifferentialEvolutionCrossover crossover,
      MutationOperator<DoubleSolution> mutation, SequenceGenerator<Integer> solutionIndexGenerator) {
    this.offspringPopulationSize = offspringPopulationSize;
    this.crossover = crossover;
    this.mutation = mutation;
    this.solutionIndexGenerator = solutionIndexGenerator ;

    this.matingPoolSize = offspringPopulationSize * crossover.numberOfRequiredParents();
  }

  public DifferentialEvolutionCrossoverVariation(
      int offspringPopulationSize, DifferentialEvolutionCrossover crossover, SequenceGenerator<Integer> solutionIndexGenerator) {
    this(offspringPopulationSize, crossover, new NullMutation<>(), solutionIndexGenerator);
  }

  @Override
  public List<DoubleSolution> variate(
      List<DoubleSolution> solutionList, List<DoubleSolution> matingPool) {

    List<DoubleSolution> offspringPopulation = new ArrayList<>();
    while (offspringPopulation.size() < offspringPopulationSize) {
      crossover.setCurrentSolution(solutionList.get(solutionIndexGenerator.getValue()));

      int numberOfRequiredParentsToCross = crossover.numberOfRequiredParents() ;

      List<DoubleSolution> parents = new ArrayList<>(numberOfRequiredParentsToCross);
      for (int j = 0; j < numberOfRequiredParentsToCross; j++) {
        parents.add(matingPool.get(j));
        //matingPool.remove(0);
      }

      List<DoubleSolution> offspring = crossover.execute(parents);

      offspringPopulation.add(mutation.execute(offspring.get(0)));
    }

    return offspringPopulation;
  }

  public DifferentialEvolutionCrossover getCrossover() {
    return crossover;
  }

  public MutationOperator<DoubleSolution> getMutation() {
    return mutation;
  }

  @Override
  public int getMatingPoolSize() {
    return matingPoolSize;
  }

  @Override
  public int getOffspringPopulationSize() {
    return offspringPopulationSize;
  }
}
