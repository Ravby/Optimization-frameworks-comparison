package si.um.feri.lpm;

import org.um.feri.ears.algorithms.DummyAlgorithm;
import org.um.feri.ears.algorithms.NumberAlgorithm;
import org.um.feri.ears.benchmark.Benchmark;
import org.um.feri.ears.statistic.rating_system.RatingType;
import org.um.feri.ears.util.Util;
import org.um.feri.ears.visualization.rating.RatingIntervalPlot;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Util.rnd.setSeed(System.currentTimeMillis());
        Benchmark.printInfo = false;
        DummyAlgorithm.readFromJson = false;

        String projectDirectory = System.getProperty("user.dir");
        File projectDirFile = new File(projectDirectory);
        File parentDirFile = projectDirFile.getParentFile();
        String filesDir = parentDirFile + File.separator + "Algorithm results";

        ArrayList<NumberAlgorithm> players = new ArrayList<>();
        players.add(new DummyAlgorithm("DE-jMetal", filesDir));
        players.add(new DummyAlgorithm("PSO-jMetal", filesDir));

        FrameworksBenchmark frameworksBenchmark = new FrameworksBenchmark();
        frameworksBenchmark.setDisplayRatingCharts(false);
        frameworksBenchmark.addAlgorithms(players);
        frameworksBenchmark.run(50);

        RatingIntervalPlot.displayChart(frameworksBenchmark.getResultArena().getPlayers(), RatingType.GLICKO2,  "Rating intervals", 1200, 600); //2000 1400
    }
}