package org.uma.jmetal.component.catalogue.ea.variation;

import java.util.List;
import org.uma.jmetal.solution.Solution;

public interface Variation<S extends Solution<?>> {
  List<S> variate(List<S> solutionList, List<S> matingPool) ;
  int getMatingPoolSize() ;
  int getOffspringPopulationSize() ;
}
