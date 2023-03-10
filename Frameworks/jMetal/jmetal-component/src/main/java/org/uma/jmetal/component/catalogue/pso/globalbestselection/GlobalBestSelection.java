package org.uma.jmetal.component.catalogue.pso.globalbestselection;

import java.util.List;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

public interface GlobalBestSelection {
  DoubleSolution select(List<DoubleSolution> globalBestList) ;
}
