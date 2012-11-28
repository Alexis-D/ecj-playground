package ec.stats;

import ec.*;
import ec.display.chart.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class ChartedStatistics extends XYSeriesChartStatistics {
    // public static final String P_CHARTED_STATISTICS = "charted-statistics";
    // public static final String P_CHART_FILENAME = "chart-filename";
    // private String chartFilename;
    float bestFitnessSoFar = -1.f;

    public void setup(EvolutionState state, final Parameter base) {
        super.setup(state, base);

    //    Parameter def = defaultBase();
    //    chartFilename = state.parameters.getStringWithDefault(base.push(P_CHART_FILENAME), def.push(P_CHART_FILENAME), "out.png");

        // TODO(alexis): make this parameters.
        addSeries("Best fitness");
        addSeries("Average fitness");
        xlabel = "Number of generations";
        ylabel = "Fitness";
        title = "Evolution of fitness over time";
    }

    // public Parameter defaultBase() {
    //     return super.defaultBase().push(P_CHARTED_STATISTICS);
    // }

    public void postEvaluationStatistics(final EvolutionState state) {
        super.postEvaluationStatistics(state);

        float fitnessesSum = 0.f;

        // TODO(alexis): do not assume a single subpop.
        // Java 1.4 → no for each...
        for(int i = 0; i < state.population.subpops[0].individuals.length; ++i) {
            DoubleVectorIndividual ind = (DoubleVectorIndividual)state.population.subpops[0].individuals[i];
            if(ind.fitness.fitness() > bestFitnessSoFar) {
                bestFitnessSoFar = ind.fitness.fitness();
            }

            fitnessesSum += ind.fitness.fitness();
        }

        addDataPoint(0, state.generation, bestFitnessSoFar);
        addDataPoint(1, state.generation, fitnessesSum / state.population.subpops[0].individuals.length);
    }
}
