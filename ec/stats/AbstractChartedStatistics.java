package ec.stats;

import java.io.File;
import java.util.Date;

import ec.*;
import ec.display.chart.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

import org.jfree.chart.JFreeChart;

public abstract class AbstractChartedStatistics extends XYSeriesChartStatistics {
    private boolean saveToImage;
    private String filename;
    private int width;
    private int height;

    public void setup(EvolutionState state, final Parameter base) {
        super.setup(state, base);

        xlabel = state.parameters.getStringWithDefault(base.push("xlabel"), null, "Number of generations");
        ylabel = state.parameters.getStringWithDefault(base.push("ylabel"), null, "Fitness");
        title = state.parameters.getStringWithDefault(base.push("title"), null, "Evolution of fitness over time");
        saveToImage = state.parameters.getBoolean(base.push("save-to-image"), null, true);
        filename = state.parameters.getStringWithDefault(base.push("filename"), null, "out.%fmt%.png");
        width = state.parameters.getIntWithDefault(base.push("width"), null, 1024);
        height = state.parameters.getIntWithDefault(base.push("height"), null, 768);
    }

    public void finalStatistics(EvolutionState state, int result) {
        if(saveToImage) {
            try {
                File outFile = new File(filename.replaceAll("%fmt%", (new Date()).toString()));
                javax.imageio.ImageIO.write(makeChart().createBufferedImage(width, height),
                        filename.substring(filename.lastIndexOf('.') + 1),
                        outFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
