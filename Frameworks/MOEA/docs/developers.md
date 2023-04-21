# Developer's Guide

## Versioning

We use [semantic versioning](https://semver.org/) following the pattern `{major}.{minor}`.  Two versions with the same
`{major}` number are expected to be backwards compatible, for example allowing one to upgrade from `3.0` to `3.2`
without difficulty.  

### Preview Code

In some instances, especially when working directly from the Git default branch, new code is being actively developed.  These
packages and classes should include the `@preview` tag to indicate they are subject to change.

## Java Versions

Each release of the MOEA Framework targets a specific version of Java:

* `3.x` - Java 8+
* `2.x` - Java 6+ (some features are deprecated in Java 16+ and no longer work)

To determine if and when to update which Java version we target, we generally look at the
[support roadmap](https://www.oracle.com/java/technologies/java-se-support-roadmap.html) and favor LTS releases.
While supporting earlier versions limits our use of newer language features, the tradeoff is supporting
the widest possible audience.

## Service Providers

This library uses Java's Service Provider Interface (SPI) to support extensibility.  The idea is that we can reference any
supported algorithm, operator, or problem by name and load it dynamically.  Furthermore, the providers automatically inspect
the problem, such as looking at the decision variable types, to select the appropriate default operators.

Additionally, we now recommend each algorithm support a single-argument constructor using just the problem.  This simplifies
the creation of the algorithm, which should configure itself appropriately for the problem.

```java

DTLZ2 problem = new DTLZ2(2);
NSGAII algorithm = new NSGAII(problem);
```

Algorithms and operators are customized with "properties".  Each property should have a getter and setter method following
Java Bean naming conventions, e.g., `getFoo` and `setFoo`.  Additionally, the setter method must be annotated with the
`@Property` tag.  This lets us configure algorithms in two ways.  First, we can call the setters directly:

```java

NSGAII algorithm = new NSGAII(problem);
algorithm.setInitialPopulationSize(250);
```

or passing in a key-value collection of properties:

```java

TypedProperties properties = new TypedProperties();
properties.withInt("populationSize", 250);

NSGAII algorithm = new NSGAII(problem);
algorithm.applyConfiguration(properties);
```

Wherever possible, algorithms should also let the caller configure the variation operators.  The problem type or nature of
the optimization algorithm may restrict what operators are supported.  Again, these can be set using the setter method:

```java

NSGAII algorithm = new NSGAII(problem);
algorithm.setVariation(new CompoundVariation(new PCX(), new UM()));
```

or with key-value properties:

```java

TypedProperties properties = new TypedProperties();
properties.withString("operator", "pcx+um");
```

One major advantage of this approach is it also allows us to read the configuration of an algorithm at any time:

```java

NSGAII algorithm = new NSGAII(problem);
algorithm.getConfiguration().display();
```

### Adding a New Service Provider

To add a new service provider, we have several extension points for introducing new algorithms (see `AlgorithmProvider`),
operators (see `OperatorProvider`), and problems (see `ProblemProvider`).

1. First decide if the new algorithm, operator, or problem belongs in the MOEA Framework or a separate library.  The required
   changes are identical, the only difference is whether you compile it as part of the `MOEAFramework-X.X.jar` or a
   separate jar.
   
2. Create a new provider class, implementing one of the three interfaces (`AlgorithmProvider`, `OperatorProvider`,
   or `ProblemProvider`).  
   
3. Open the corresponding file in `META-INF/services` and add a new line with the fully-qualified class name for the new
   provider.

4. Try it out! Try creating and using your new code through one of the factories or through the `Executor`.



