package org.uma.jmetal.util.artificialdecisionmaker;

import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;

public interface InteractiveAlgorithm<S,R> extends Algorithm<R> {
  void updatePointOfInterest(List<Double> newReferencePoints);
}
