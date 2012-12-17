package ec.stats;

import ec.EvolutionState;
import ec.Fitness;
import ec.Individual;

@SuppressWarnings("serial")
public class ChartedStatistics extends AbstractChartedStatistics {
	Fitness[] bestFitnessesSoFar; // indexed by subpop

	public void postInitializationStatistics(EvolutionState state) {
		super.postInitializationStatistics(state);

		bestFitnessesSoFar = new Fitness[state.population.subpops.length];

		for (int i = 0; i < bestFitnessesSoFar.length; ++i) {
			// java 1.4 no String.format
			// TODO(alexis): parameters?
			addSeries("Best fitness (subpop #" + Integer.toString(i) + ")");
			addSeries("Average fitness (subpop #)" + Integer.toString(i) + ")");
		}
	}

	public void postEvaluationStatistics(EvolutionState state) {
		super.postEvaluationStatistics(state);

		for (int i = 0; i < state.population.subpops.length; ++i) {
			float fitnessesSum = 0.f;

			for (int j = 0; j < state.population.subpops[i].individuals.length; ++j) {
				Individual ind = state.population.subpops[i].individuals[j];

				// this keeps tracks of the previous best, and overwrite it only
				// if we've found better
				if (bestFitnessesSoFar[i] == null
						|| ind.fitness.betterThan(bestFitnessesSoFar[i])) {
					bestFitnessesSoFar[i] = ind.fitness;
				}

				fitnessesSum += ind.fitness.fitness();
			}

			addDataPoint(0, state.generation, bestFitnessesSoFar[i].fitness());
			addDataPoint(1, state.generation, fitnessesSum
					/ state.population.subpops[i].individuals.length);
		}
	}
}
