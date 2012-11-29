package ec.stats;

import java.io.File;
import java.util.Date;

import ec.*;
import ec.display.chart.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

import org.jfree.chart.JFreeChart;

public class GlobalChartedStatistics extends AbstractChartedStatistics {
    // a singleton would be better, but it's not really possible due to how ECJ constructs objects
    static Fitness[] bestFitnesses = null;
    static float[] fitnessesSum = null;
    static int numberOfIndividualsPerGenerations = 0;
    static int numberOfJobs;

    public void setup(EvolutionState state, final Parameter base) {
        super.setup(state, base);
        numberOfJobs = state.parameters.getIntWithDefault(new Parameter("jobs"), null, 1);
    }

    public void postInitializationStatistics(EvolutionState state) {
        super.postInitializationStatistics(state);

        if(fitnessesSum == null) {
            fitnessesSum = new float[state.numGenerations];
            bestFitnesses = new Fitness[state.numGenerations];
        }

        for(int i = 0; i < state.population.subpops.length; ++i) {
            numberOfIndividualsPerGenerations += state.population.subpops[i].individuals.length;
        }
    }

    public void postEvaluationStatistics(EvolutionState state) {
        super.postEvaluationStatistics(state);

        for(int i = 0; i < state.population.subpops.length; ++i) {
            for(int j = 0; j < state.population.subpops[i].individuals.length; ++j) {
                Individual ind = state.population.subpops[i].individuals[j];
                fitnessesSum[state.generation] += ind.fitness.fitness();

                if(bestFitnesses[state.generation] == null || ind.fitness.betterThan(bestFitnesses[state.generation])) {
                    bestFitnesses[state.generation] = ind.fitness;
                }
            }
        }
    }

    private int getCurrentJob(EvolutionState state) {
        return ((Integer)state.job[0]).intValue();
    }

    public void finalStatistics(EvolutionState state, int result) {
        if(getCurrentJob(state) == numberOfJobs - 1) {
            addSeries("Best fitness (across all jobs/subpops)");
            addSeries("Average fitness (across all jobs/subpops)");

            Fitness bestSoFar = bestFitnesses[0];  // keep track of the very best fitness

            for(int i = 0; i < bestFitnesses.length; ++i) {
                if(bestFitnesses[i].betterThan(bestSoFar)) {
                    bestSoFar = bestFitnesses[i];
                }

                addDataPoint(0, i, bestSoFar.fitness());
                addDataPoint(1, i, fitnessesSum[i] / numberOfIndividualsPerGenerations);
            }

            super.finalStatistics(state, result);
        }
    }
}
