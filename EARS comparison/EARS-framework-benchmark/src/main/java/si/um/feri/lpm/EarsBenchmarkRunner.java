package si.um.feri.lpm;

import org.um.feri.ears.algorithms.DummyAlgorithm;
import org.um.feri.ears.algorithms.NumberAlgorithm;
import org.um.feri.ears.benchmark.Benchmark;
import org.um.feri.ears.statistic.rating_system.RatingType;
import org.um.feri.ears.statistic.rating_system.glicko2.TournamentResults;
import org.um.feri.ears.util.random.RNG;
import org.um.feri.ears.visualization.rating.RatingIntervalPlot;

import java.io.File;
import java.util.ArrayList;

public class EarsBenchmarkRunner {
    public static void main(String[] args) {
        RNG.setSeed(System.currentTimeMillis());
        Benchmark.printInfo = false;
        boolean displayRatingCharts = true;

        int numberOfRuns = 50;

        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile();
        String algorithmResultsDir = parentDirFile + File.separator + "Algorithm results";
        String ratingIntervalsDir = parentDirFile + File.separator + "Rating intervals";

        FrameworksBenchmark frameworksBenchmark;
        TournamentResults tournamentResults;
        ArrayList<NumberAlgorithm> players = new ArrayList<>();

        players.add(new DummyAlgorithm("PSO-EARS", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-jMetal", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-PlatEMO", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-MEALPY", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-NiaPy", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-DEAP", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-pagmo2", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-YPEA", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-EvoloPy", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-pymoo", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-metaheuristicOpt", algorithmResultsDir));
        players.add(new DummyAlgorithm("PSO-Nevergrad", algorithmResultsDir));

        frameworksBenchmark = new FrameworksBenchmark();
        frameworksBenchmark.setDisplayRatingCharts(false);
        frameworksBenchmark.addAlgorithms(players);
        frameworksBenchmark.run(numberOfRuns);
        tournamentResults = frameworksBenchmark.getTournamentResults();
        tournamentResults.saveToFile(ratingIntervalsDir + File.separator + "PSO_15000_evaluations");
        if (displayRatingCharts) {
            RatingIntervalPlot.displayChart(tournamentResults.getPlayers(), RatingType.GLICKO2, "Rating Interval");
        }

        players.clear();
        players.add(new DummyAlgorithm("ABC-EARS", algorithmResultsDir));
        players.add(new DummyAlgorithm("ABC-PlatEMO", algorithmResultsDir));
        players.add(new DummyAlgorithm("ABC-MEALPY", algorithmResultsDir));
        players.add(new DummyAlgorithm("ABC-NiaPy", algorithmResultsDir));
        players.add(new DummyAlgorithm("ABC-pagmo2", algorithmResultsDir));
        players.add(new DummyAlgorithm("ABC-Author-Matlab", algorithmResultsDir));
        players.add(new DummyAlgorithm("ABC-YPEA", algorithmResultsDir));
        players.add(new DummyAlgorithm("ABC-metaheuristicOpt", algorithmResultsDir));

        frameworksBenchmark = new FrameworksBenchmark();
        frameworksBenchmark.setDisplayRatingCharts(false);
        frameworksBenchmark.addAlgorithms(players);
        frameworksBenchmark.run(numberOfRuns);
        tournamentResults = frameworksBenchmark.getTournamentResults();
        tournamentResults.saveToFile(ratingIntervalsDir + File.separator + "ABC_15000_evaluations");
        if (displayRatingCharts) {
            RatingIntervalPlot.displayChart(tournamentResults.getPlayers(), RatingType.GLICKO2, "Rating Interval");
        }

        players.clear();
        //players.add(new DummyAlgorithm("GA-NiaPy", algorithmResultsDir));
        //players.add(new DummyAlgorithm("GA-MEALPY", algorithmResultsDir));
        //players.add(new DummyAlgorithm("GA-EvoloPy", algorithmResultsDir));
        //players.add(new DummyAlgorithm("GA-metaheuristicOpt", algorithmResultsDir));
        //players.add(new DummyAlgorithm("GA-YPEA", algorithmResultsDir));
        players.add(new DummyAlgorithm("GA-PlatEMO", algorithmResultsDir));
        players.add(new DummyAlgorithm("GA-jMetal", algorithmResultsDir));
        players.add(new DummyAlgorithm("GA-MOEA", algorithmResultsDir));
        players.add(new DummyAlgorithm("GA-DEAP", algorithmResultsDir));
        players.add(new DummyAlgorithm("GA-pagmo2", algorithmResultsDir));
        players.add(new DummyAlgorithm("GA-pymoo", algorithmResultsDir));

        frameworksBenchmark = new FrameworksBenchmark();
        frameworksBenchmark.setDisplayRatingCharts(false);
        frameworksBenchmark.addAlgorithms(players);
        frameworksBenchmark.run(numberOfRuns);
        tournamentResults = frameworksBenchmark.getTournamentResults();
        tournamentResults.saveToFile(ratingIntervalsDir + File.separator + "GA_15000_evaluations");
        if (displayRatingCharts) {
            RatingIntervalPlot.displayChart(tournamentResults.getPlayers(), RatingType.GLICKO2, "Rating Interval");
        }

        players.clear();
        players.add(new DummyAlgorithm("DE-EARS", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-jMetal", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-MEALPY", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-MOEA", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-NiaPy", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-DEAP", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-PlatEMO", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-pagmo2", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-Author-Java", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-pymoo", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-metaheuristicOpt", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-YPEA", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-EvoloPy", algorithmResultsDir));
        players.add(new DummyAlgorithm("DE-Nevergrad", algorithmResultsDir));

        frameworksBenchmark = new FrameworksBenchmark();
        frameworksBenchmark.setDisplayRatingCharts(false);
        frameworksBenchmark.addAlgorithms(players);
        frameworksBenchmark.run(numberOfRuns);
        tournamentResults = frameworksBenchmark.getTournamentResults();
        tournamentResults.saveToFile(ratingIntervalsDir + File.separator + "DE_15000_evaluations");
        if (displayRatingCharts) {
            RatingIntervalPlot.displayChart(tournamentResults.getPlayers(), RatingType.GLICKO2, "Rating Interval");
        }

        players.clear();
        players.add(new DummyAlgorithm("GWO-EARS", algorithmResultsDir));
        players.add(new DummyAlgorithm("GWO-NiaPy", algorithmResultsDir));
        players.add(new DummyAlgorithm("GWO-MEALPY", algorithmResultsDir));
        players.add(new DummyAlgorithm("GWO-pagmo2", algorithmResultsDir));
        players.add(new DummyAlgorithm("GWO-Author-Matlab", algorithmResultsDir));
        players.add(new DummyAlgorithm("GWO-PlatEMO", algorithmResultsDir));
        players.add(new DummyAlgorithm("GWO-metaheuristicOpt", algorithmResultsDir));
        players.add(new DummyAlgorithm("GWO-EvoloPy", algorithmResultsDir));

        frameworksBenchmark = new FrameworksBenchmark();
        frameworksBenchmark.setDisplayRatingCharts(false);
        frameworksBenchmark.addAlgorithms(players);
        frameworksBenchmark.run(numberOfRuns);
        tournamentResults = frameworksBenchmark.getTournamentResults();
        tournamentResults.saveToFile(ratingIntervalsDir + File.separator + "GWO_15000_evaluations");
        if (displayRatingCharts) {
            RatingIntervalPlot.displayChart(tournamentResults.getPlayers(), RatingType.GLICKO2, "Rating Interval");
        }

        players.clear();
        //players.add(new DummyAlgorithm("CMA-ES-EARS", algorithmResultsDir)); //not working
        //players.add(new DummyAlgorithm("CMA-ES-MOEA", algorithmResultsDir)); //not working
        //players.add(new DummyAlgorithm("CMA-ES-PlatEMO", algorithmResultsDir)); //not working
        //players.add(new DummyAlgorithm("CMA-ES-YPEA", algorithmResultsDir)); //not working
        //players.add(new DummyAlgorithm("CMA-ES-MEALPY", algorithmResultsDir)); //not working
        //players.add(new DummyAlgorithm("CMA-ES-Nevergrad", algorithmResultsDir)); // not working

        players.add(new DummyAlgorithm("CMA-ES-jMetal", algorithmResultsDir));
        players.add(new DummyAlgorithm("CMA-ES-pagmo2", algorithmResultsDir));
        players.add(new DummyAlgorithm("CMA-ES-DEAP", algorithmResultsDir));
        players.add(new DummyAlgorithm("CMA-ES-Author-Python", algorithmResultsDir));
        players.add(new DummyAlgorithm("CMA-ES-pymoo", algorithmResultsDir));

        frameworksBenchmark = new FrameworksBenchmark();
        frameworksBenchmark.setDisplayRatingCharts(false);
        frameworksBenchmark.addAlgorithms(players);
        frameworksBenchmark.run(numberOfRuns);
        tournamentResults = frameworksBenchmark.getTournamentResults();
        tournamentResults.saveToFile(ratingIntervalsDir + File.separator + "CMA-ES_15000_evaluations");
        if (displayRatingCharts) {
            RatingIntervalPlot.displayChart(tournamentResults.getPlayers(), RatingType.GLICKO2, "Rating Interval");
        }
    }
}