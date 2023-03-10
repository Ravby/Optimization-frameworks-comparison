
* Meta-heuristic Categories: (Based on `this article`_)
    + Evolutionary-based: Idea from Darwin's law of natural selection, evolutionary computing
    + Swarm-based: Idea from movement, interaction of birds, organization of social ...
    + Physics-based: Idea from physics law such as Newton's law of universal gravitation, black hole, multiverse
    + Human-based: Idea from human interaction such as queuing search, teaching learning, ...
    + Biology-based: Idea from biology creature (or microorganism),...
    + System-based: Idea from eco-system, immune-system, network-system, ...
    + Math-based: Idea from mathematical form or mathematical law such as sin-cosin
    + Music-based: Idea from music instrument
    + Probabilistic-base: Probabilistic based algorithm
    + Dummy: Non-sense algorithms and Non-sense papers (code proofs)

.. _this article: https://doi.org/10.1016/j.procs.2020.09.075


* Difficulty - Difficulty Level (Personal Opinion): Objective observation from author. Depend on the number of parameters, number of equations, the original ideas, time spend for coding, source lines of code (SLOC).
    + Easy: A few paras, few equations, SLOC very short
    + Medium: more equations than Easy level, SLOC longer than Easy level
    + Hard: Lots of equations, SLOC longer than Medium level, the paper hard to read.
    + Hard* - Very hard: Lots of equations, SLOC too long, the paper is very hard to read.

**For newbie, I recommend to read the paper of algorithms which difficulty is "easy" or "medium" difficulty level.**


+---------------+--------------------------------------------------+-----------+-------------------+-------+--------+-------------+
| Group         | Name                                             | Module    | Class             | Year  | Paras  | Difficulty  |
+===============+==================================================+===========+===================+=======+========+=============+
| Evolutionary  | Evolutionary Programming                         | EP        | OriginalEP        | 1964  | 3      | easy        |
| Evolutionary  | -                                                | -         | LevyEP            | -     | 3      | easy        |
| Evolutionary  | Evolution Strategies                             | ES        | OriginalES        | 1971  | 3      | easy        |
| Evolutionary  | -                                                | -         | LevyES            | -     | 3      | easy        |
| Evolutionary  | Memetic Algorithm                                | MA        | OriginalMA        | 1989  | 7      | easy        |
| Evolutionary  | Genetic Algorithm                                | GA        | BaseGA            | 1992  | 4      | easy        |
| Evolutionary  | -                                                | -         | SingleGA          | -     | 7      | easy        |
| Evolutionary  | -                                                | -         | MultiGA           | -     | 7      | easy        |
| Evolutionary  | -                                                | -         | EliteSingleGA     | -     | 10     | easy        |
| Evolutionary  | -                                                | -         | EliteMultiGA      | -     | 10     | easy        |
| Evolutionary  | Differential Evolution                           | DE        | BaseDE            | 1997  | 5      | easy        |
| Evolutionary  | -                                                | -         | JADE              | 2009  | 6      | medium      |
| Evolutionary  | -                                                | -         | SADE              | 2005  | 2      | medium      |
| Evolutionary  | -                                                | -         | SHADE             | 2013  | 4      | medium      |
| Evolutionary  | -                                                | -         | L_SHADE           | 2014  | 4      | medium      |
| Evolutionary  | -                                                | -         | SAP_DE            | 2006  | 3      | medium      |
| Evolutionary  | Flower Pollination Algorithm                     | FPA       | OriginalFPA       | 2014  | 4      | medium      |
| Evolutionary  | Coral Reefs Optimization                         | CRO       | OriginalCRO       | 2014  | 11     | medium      |
| Evolutionary  | -                                                | -         | OCRO              | 2019  | 12     | medium      |
| -             | -                                                | -         | -                 | -     | -      | -           |
| Swarm         | Particle Swarm Optimization                      | PSO       | OriginalPSO       | 1995  | 6      | easy        |
| Swarm         | -                                                | -         | PPSO              | 2019  | 2      | medium      |
| Swarm         | -                                                | -         | HPSO_TVAC         | 2017  | 4      | medium      |
| Swarm         | -                                                | -         | C_PSO             | 2015  | 6      | medium      |
| Swarm         | -                                                | -         | CL_PSO            | 2006  | 6      | medium      |
| Swarm         | Bacterial Foraging Optimization                  | BFO       | OriginalBFO       | 2002  | 10     | hard        |
| Swarm         | -                                                | -         | ABFO              | 2019  | 8      | medium      |
| Swarm         | Bees Algorithm                                   | BeesA     | OriginalBeesA     | 2005  | 8      | medium      |
| Swarm         | -                                                | -         | ProbBeesA         | 2015  | 5      | medium      |
| Swarm         | Cat Swarm Optimization                           | CSO       | OriginalCSO       | 2006  | 11     | hard        |
| Swarm         | Artificial Bee Colony                            | ABC       | OriginalABC       | 2007  | 8      | medium      |
| Swarm         | Ant Colony Optimization                          | ACO-R     | OriginalACOR      | 2008  | 5      | easy        |
| Swarm         | Cuckoo Search Algorithm                          | CSA       | OriginalCSA       | 2009  | 3      | medium      |
| Swarm         | Firefly Algorithm                                | FFA       | OriginalFFA       | 2009  | 8      | easy        |
| Swarm         | Fireworks Algorithm                              | FA        | OriginalFA        | 2010  | 7      | medium      |
| Swarm         | Bat Algorithm                                    | BA        | OriginalBA        | 2010  | 6      | medium      |
| Swarm         | -                                                | -         | AdaptiveBA        | -     | 8      | medium      |
| Swarm         | -                                                | -         | ModifiedBA        | -     | 5      | medium      |
| Swarm         | Fruit-fly Optimization Algorithm                 | FOA       | OriginalFOA       | 2012  | 2      | easy        |
| Swarm         | -                                                | -         | BaseFOA           | -     | 2      | easy        |
| Swarm         | -                                                | -         | WhaleFOA          | 2020  | 2      | medium      |
| Swarm         | Social Spider Optimization                       | SSpiderO  | OriginalSSpiderO  | 2018  | 4      | hard*       |
| Swarm         | Grey Wolf Optimizer                              | GWO       | OriginalGWO       | 2014  | 2      | easy        |
| Swarm         | -                                                | -         | RW_GWO            | 2019  | 2      | easy        |
| Swarm         | Social Spider Algorithm                          | SSpiderA  | OriginalSSpiderA  | 2015  | 5      | medium      |
| Swarm         | Ant Lion Optimizer                               | ALO       | OriginalALO       | 2015  | 2      | easy        |
| Swarm         | -                                                | -         | BaseALO           | -     | 2      | easy        |
| Swarm         | Moth Flame Optimization                          | MFO       | OriginalMFO       | 2015  | 2      | easy        |
| Swarm         | -                                                | -         | BaseMFO           | -     | 2      | easy        |
| Swarm         | Elephant Herding Optimization                    | EHO       | OriginalEHO       | 2015  | 5      | easy        |
| Swarm         | Jaya Algorithm                                   | JA        | OriginalJA        | 2016  | 2      | easy        |
| Swarm         | -                                                | -         | BaseJA            | -     | 2      | easy        |
| Swarm         | -                                                | -         | LevyJA            | 2021  | 2      | easy        |
| Swarm         | Whale Optimization Algorithm                     | WOA       | OriginalWOA       | 2016  | 2      | medium      |
| Swarm         | -                                                | -         | HI_WOA            | 2019  | 3      | medium      |
| Swarm         | Dragonfly Optimization                           | DO        | OriginalDO        | 2016  | 2      | medium      |
| Swarm         | Bird Swarm Algorithm                             | BSA       | OriginalBSA       | 2016  | 9      | medium      |
| Swarm         | Spotted Hyena Optimizer                          | SHO       | OriginalSHO       | 2017  | 4      | medium      |
| Swarm         | Salp Swarm Optimization                          | SSO       | OriginalSSO       | 2017  | 2      | easy        |
| Swarm         | Swarm Robotics Search And Rescue                 | SRSR      | OriginalSRSR      | 2017  | 2      | hard*       |
| Swarm         | Grasshopper Optimisation Algorithm               | GOA       | OriginalGOA       | 2017  | 4      | easy        |
| Swarm         | Coyote Optimization Algorithm                    | COA       | OriginalCOA       | 2018  | 3      | medium      |
| Swarm         | Moth Search Algorithm                            | MSA       | OriginalMSA       | 2018  | 5      | easy        |
| Swarm         | Sea Lion Optimization                            | SLO       | OriginalSLO       | 2019  | 2      | medium      |
| Swarm         | -                                                | -         | ModifiedSLO       | -     | 2      | medium      |
| Swarm         | -                                                | -         | ImprovedSLO       | -     | 4      | medium      |
| Swarm         | Nake Mole-Rat Algorithm                          | NMRA      | OriginalNMRA      | 2019  | 3      | easy        |
| Swarm         | -                                                | -         | ImprovedNMRA      | -     | 4      | medium      |
| Swarm         | Pathfinder Algorithm                             | PFA       | OriginalPFA       | 2019  | 2      | medium      |
| Swarm         | Sailfish Optimizer                               | SFO       | OriginalSFO       | 2019  | 5      | easy        |
| Swarm         | -                                                | -         | ImprovedSFO       | -     | 3      | medium      |
| Swarm         | Harris Hawks Optimization                        | HHO       | OriginalHHO       | 2019  | 2      | medium      |
| Swarm         | Manta Ray Foraging Optimization                  | MRFO      | OriginalMRFO      | 2020  | 3      | medium      |
| Swarm         | Bald Eagle Search                                | BES       | OriginalBES       | 2020  | 7      | easy        |
| Swarm         | Sparrow Search Algorithm                         | SSA       | OriginalSSA       | 2020  | 5      | medium      |
| Swarm         | -                                                | -         | BaseSSA           | -     | 5      | medium      |
| Swarm         | Hunger Games Search                              | HGS       | OriginalHGS       | 2021  | 4      | medium      |
| Swarm         | Aquila Optimizer                                 | AO        | OriginalAO        | 2021  | 2      | easy        |
| Swarm         | Hybrid Grey Wolf - Whale Optimization Algorithm  | GWO       | GWO_WOA           | 2022  | 2      | easy        |
| Swarm         | Marine Predators Algorithm                       | MPA       | OriginalMPA       | 2020  | 2      | medium      |
| Swarm         | Honey Badger Algorithm                           | HBA       | OriginalHBA       | 2022  | 2      | easy        |
| Swarm         | Sand Cat Swarm Optimization                      | SCSO      | OriginalSCSO      | 2022  | 2      | easy        |
| Swarm         | Tuna Swarm Optimization                          | TSO       | OriginalTSO       | 2021  | 2      | medium      |
| Swarm         | African Vultures Optimization Algorithm          | AVOA      | OriginalAVOA      | 2022  | 7      | medium      |
| Swarm         | Artificial Gorilla Troops Optimization           | AGTO      | OriginalAGTO      | 2021  | 5      | medium      |
| Swarm         | Artificial Rabbits Optimization                  | ARO       | OriginalARO       | 2022  | 2      | easy        |
| -             | -                                                | -         | -                 | -     | -      | -           |
| Physics       | Simulated Annealling                             | SA        | OriginalSA        | 1987  | 9      | medium      |
| Physics       | Wind Driven Optimization                         | WDO       | OriginalWDO       | 2013  | 7      | easy        |
| Physics       | Multi-Verse Optimizer                            | MVO       | OriginalMVO       | 2016  | 4      | easy        |
| Physics       | -                                                | -         | BaseMVO           | -     | 4      | easy        |
| Physics       | Tug of War Optimization                          | TWO       | OriginalTWO       | 2016  | 2      | easy        |
| Physics       | -                                                | -         | OppoTWO           | -     | 2      | medium      |
| Physics       | -                                                | -         | LevyTWO           | -     | 2      | medium      |
| Physics       | -                                                | -         | EnhancedTWO       | 2020  | 2      | medium      |
| Physics       | Electromagnetic Field Optimization               | EFO       | OriginalEFO       | 2016  | 6      | easy        |
| Physics       | -                                                | -         | BaseEFO           | -     | 6      | medium      |
| Physics       | Nuclear Reaction Optimization                    | NRO       | OriginalNRO       | 2019  | 2      | hard*       |
| Physics       | Henry Gas Solubility Optimization                | HGSO      | OriginalHGSO      | 2019  | 3      | medium      |
| Physics       | Atom Search Optimization                         | ASO       | OriginalASO       | 2019  | 4      | medium      |
| Physics       | Equilibrium Optimizer                            | EO        | OriginalEO        | 2019  | 2      | easy        |
| Physics       | -                                                | -         | ModifiedEO        | 2020  | 2      | medium      |
| Physics       | -                                                | -         | AdaptiveEO        | 2020  | 2      | medium      |
| Physics       | Archimedes Optimization Algorithm                | ArchOA    | OriginalArchOA    | 2021  | 8      | medium      |
| -             | -                                                | -         | -                 | -     | -      | -           |
| Human         | Culture Algorithm                                | CA        | OriginalCA        | 1994  | 3      | easy        |
| Human         | Imperialist Competitive Algorithm                | ICA       | OriginalICA       | 2007  | 8      | hard*       |
| Human         | Teaching Learning-based Optimization             | TLO       | OriginalTLO       | 2011  | 2      | easy        |
| Human         | -                                                | -         | BaseTLO           | 2012  | 2      | easy        |
| Human         | -                                                | -         | ITLO              | 2013  | 3      | medium      |
| Human         | Brain Storm Optimization                         | BSO       | OriginalBSO       | 2011  | 8      | medium      |
| Human         | -                                                | -         | ImprovedBSO       | 2017  | 7      | medium      |
| Human         | Queuing Search Algorithm                         | QSA       | OriginalQSA       | 2019  | 2      | hard        |
| Human         | -                                                | -         | BaseQSA           | -     | 2      | hard        |
| Human         | -                                                | -         | OppoQSA           | -     | 2      | hard        |
| Human         | -                                                | -         | LevyQSA           | -     | 2      | hard        |
| Human         | -                                                | -         | ImprovedQSA       | 2021  | 2      | hard        |
| Human         | Search And Rescue Optimization                   | SARO      | OriginalSARO      | 2019  | 4      | medium      |
| Human         | -                                                | -         | BaseSARO          | -     | 4      | medium      |
| Human         | Life Choice-Based Optimization                   | LCO       | OriginalLCO       | 2019  | 3      | easy        |
| Human         | -                                                | -         | BaseLCO           | -     | 3      | easy        |
| Human         | -                                                | -         | ImprovedLCO       | -     | 2      | easy        |
| Human         | Social Ski-Driver Optimization                   | SSDO      | OriginalSSDO      | 2019  | 2      | easy        |
| Human         | Gaining Sharing Knowledge-based Algorithm        | GSKA      | OriginalGSKA      | 2019  | 6      | medium      |
| Human         | -                                                | -         | BaseGSKA          | -     | 4      | medium      |
| Human         | Coronavirus Herd Immunity Optimization           | CHIO      | OriginalCHIO      | 2020  | 4      | medium      |
| Human         | -                                                | -         | BaseCHIO          | -     | 4      | medium      |
| Human         | Forensic-Based Investigation Optimization        | FBIO      | OriginalFBIO      | 2020  | 2      | medium      |
| Human         | -                                                | -         | BaseFBIO          | -     | 2      | medium      |
| Human         | Battle Royale Optimization                       | BRO       | OriginalBRO       | 2020  | 3      | medium      |
| Human         | -                                                | -         | BaseBRO           | -     | 3      | medium      |
| Human         | Student Psychology Based Optimization            | SPBO      | OriginalSPBO      | 2020  | 2      | medium      |
| Human         | -                                                | -         | DevSPBO           |       | 2      | medium      |
| Human         | Dwarf Mongoose Optimization Algorithm            | DMOA      | OriginalDMOA      | 2022  | 4      | medium      |
| Human         | -                                                | -         | DevDMOA           | -     | 3      | medium      |
| -             | -                                                | -         | -                 | -     | -      | -           |
| Bio           | Invasive Weed Optimization                       | IWO       | OriginalIWO       | 2006  | 7      | easy        |
| Bio           | Biogeography-Based Optimization                  | BBO       | OriginalBBO       | 2008  | 4      | easy        |
| Bio           | -                                                | -         | BaseBBO           | -     | 4      | easy        |
| Bio           | Virus Colony Search                              | VCS       | OriginalVCS       | 2016  | 4      | hard*       |
| Bio           | -                                                | -         | BaseVCS           | -     | 4      | hard*       |
| Bio           | Satin Bowerbird Optimizer                        | SBO       | OriginalSBO       | 2017  | 5      | easy        |
| Bio           | -                                                | -         | BaseSBO           | -     | 5      | easy        |
| Bio           | Earthworm Optimisation Algorithm                 | EOA       | OriginalEOA       | 2018  | 8      | medium      |
| Bio           | Wildebeest Herd Optimization                     | WHO       | OriginalWHO       | 2019  | 12     | hard        |
| Bio           | Slime Mould Algorithm                            | SMA       | OriginalSMA       | 2020  | 3      | easy        |
| Bio           | -                                                | -         | BaseSMA           | -     | 3      | easy        |
| Bio           | Barnacles Mating Optimizer                       | BMO       | OriginalBMO       | 2018  | 3      | easy        |
| Bio           | Tunicate Swarm Algorithm                         | TSA       | OriginalTSA       | 2020  | 2      | easy        |
| Bio           | Symbiotic Organisms Search                       | SOS       | OriginalSOS       | 2014  | 2      | medium      |
| Bio           | Seagull Optimization Algorithm                   | SOA       | OriginalSOA       | 2019  | 3      | easy        |
| Bio           | -                                                | -         | DevSOA            | -     | 3      | easy        |
| -             | -                                                | -         | -                 | -     | -      | -           |
| System        | Germinal Center Optimization                     | GCO       | OriginalGCO       | 2018  | 4      | medium      |
| System        | -                                                | -         | BaseGCO           | -     | 4      | medium      |
| System        | Water Cycle Algorithm                            | WCA       | OriginalWCA       | 2012  | 5      | medium      |
| System        | Artificial Ecosystem-based Optimization          | AEO       | OriginalAEO       | 2019  | 2      | easy        |
| System        | -                                                | -         | EnhancedAEO       | 2020  | 2      | medium      |
| System        | -                                                | -         | ModifiedAEO       | 2020  | 2      | medium      |
| System        | -                                                | -         | ImprovedAEO       | 2021  | 2      | medium      |
| System        | -                                                | -         | AdaptiveAEO       | -     | 2      | medium      |
| -             | -                                                | -         | -                 | -     | -      | -           |
| Math          | Hill Climbing                                    | HC        | OriginalHC        | 1993  | 3      | easy        |
| Math          | -                                                | -         | SwarmHC           | -     | 3      | easy        |
| Math          | Cross-Entropy Method                             | CEM       | OriginalCEM       | 1997  | 4      | easy        |
| Math          | Sine Cosine Algorithm                            | SCA       | OriginalSCA       | 2016  | 2      | easy        |
| Math          | -                                                | -         | BaseSCA           | -     | 2      | easy        |
| Math          | Gradient-Based Optimizer                         | GBO       | OriginalGBO       | 2020  | 5      | medium      |
| Math          | Arithmetic Optimization Algorithm                | AOA       | OrginalAOA        | 2021  | 6      | easy        |
| Math          | Chaos Game Optimization                          | CGO       | OriginalCGO       | 2021  | 2      | easy        |
| Math          | Pareto-like Sequential Sampling                  | PSS       | OriginalPSS       | 2021  | 4      | medium      |
| Math          | weIghted meaN oF vectOrs                         | INFO      | OriginalINFO      | 2022  | 2      | medium      |
| Math          | RUNge Kutta optimizer                            | RUN       | OriginalRUN       | 2021  | 2      | hard        |
| Math          | Circle Search Algorithm                          | CircleSA  | OriginalCircleSA  | 2022  | 3      | easy        |
| -             | -                                                | -         | -                 | -     | -      | -           |
| Music         | Harmony Search                                   | HS        | OriginalHS        | 2001  | 4      | easy        |
| Music         | -                                                | -         | BaseHS            | -     | 4      | easy        |
+---------------+--------------------------------------------------+-----------+-------------------+-------+--------+-------------+



.. toctree::
   :maxdepth: 4

.. toctree::
   :maxdepth: 4

.. toctree::
   :maxdepth: 4

