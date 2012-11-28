package ec.stats;

import ec.*;
import ec.display.chart.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class ChartedStatistics extends XYSeriesChartStatistics {
    Fitness[] bestFitnessesSoFar;

    public void setup(EvolutionState state, final Parameter base) {
        super.setup(state, base);

        // TODO(alexis): make this parameters.
        xlabel = "Number of generations";
        ylabel = "Fitness";
        title = "Evolution of fitness over time";
    }

    public void postInitializationStatistics(EvolutionState state) {
        super.postInitializationStatistics(state);

        bestFitnessesSoFar = new Fitness[state.population.subpops.length];

        for(int i = 0; i < bestFitnessesSoFar.length; ++i) {
            // java 1.4 no String.format
            addSeries("Best fitness (subpop #" + Integer.toString(i) + ")");
            addSeries("Average fitness (subpop #)" + Integer.toString(i) + ")");
        }
    }

    public void postEvaluationStatistics(EvolutionState state) {
        super.postEvaluationStatistics(state);

        for(int i = 0; i < bestFitnessesSoFar.length; ++i) {
            float fitnessesSum = 0.f;

            for(int j = 0; j < state.population.subpops[i].individuals.length; ++j) {
                Individual ind = state.population.subpops[i].individuals[j];
                if(bestFitnessesSoFar[i] == null || ind.fitness.betterThan(bestFitnessesSoFar[i])) {
                    bestFitnessesSoFar[i] = ind.fitness;
                }

                fitnessesSum += ind.fitness.fitness();
            }

            addDataPoint(0, state.generation, bestFitnessesSoFar[i].fitness());
            addDataPoint(1, state.generation, fitnessesSum / state.population.subpops[i].individuals.length);
        }
    }
}
