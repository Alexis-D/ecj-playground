package ec.stats;

import ec.EvolutionState;
import ec.Fitness;
import ec.Individual;
import ec.util.Parameter;

@SuppressWarnings("serial")
public class GlobalChartedStatistics extends AbstractChartedStatistics {
	// a singleton would be better, but it's not really possible due to how ECJ
	// constructs objects
	private static Fitness[] bestFitnesses = null;
	private static float[] fitnessesSum = null;
	private static int numberOfIndividualsPerGenerations = 0;
	private static int numberOfJobs;
	private static String bestDescription;
	private static String averageDescription;

	public void setup(EvolutionState state, final Parameter base) {
		super.setup(state, base);
		numberOfJobs = state.parameters.getIntWithDefault(
				new Parameter("jobs"), null, 1);

		title = title.replaceAll("%jobs%", String.valueOf(numberOfJobs));
		bestDescription = state.parameters.getStringWithDefault(
				base.push("best-description"), null,
				"Best fitness (across all jobs/subpops)");
		averageDescription = state.parameters.getStringWithDefault(
				base.push("average-description"), null,
				"Average fitness (across all jobs/subpops)");
	}

	public void postInitializationStatistics(EvolutionState state) {
		super.postInitializationStatistics(state);

		if (fitnessesSum == null) {
			fitnessesSum = new float[state.numGenerations];
			bestFitnesses = new Fitness[state.numGenerations];
		}

		for (int i = 0; i < state.population.subpops.length; ++i) {
			numberOfIndividualsPerGenerations += state.population.subpops[i].individuals.length;
		}
	}

	public void postEvaluationStatistics(EvolutionState state) {
		super.postEvaluationStatistics(state);

		for (int i = 0; i < state.population.subpops.length; ++i) {
			for (int j = 0; j < state.population.subpops[i].individuals.length; ++j) {
				Individual ind = state.population.subpops[i].individuals[j];
				fitnessesSum[state.generation] += ind.fitness.fitness();

				if (bestFitnesses[state.generation] == null
						|| ind.fitness
								.betterThan(bestFitnesses[state.generation])) {
					bestFitnesses[state.generation] = ind.fitness;
				}
			}
		}
	}

	private int getCurrentJob(EvolutionState state) {
		return ((Integer) state.job[0]).intValue();
	}

	public void finalStatistics(EvolutionState state, int result) {
		title = title.replaceAll("%individuals%",
				String.valueOf(numberOfIndividualsPerGenerations));

		if (getCurrentJob(state) == numberOfJobs - 1) {
			addSeries(bestDescription);
			addSeries(averageDescription);

			// keep track of the very best fitness
			Fitness bestSoFar = bestFitnesses[0];

			for (int i = 0; i < bestFitnesses.length; ++i) {
				if (bestFitnesses[i].betterThan(bestSoFar)) {
					bestSoFar = bestFitnesses[i];
				}

				addDataPoint(0, i, bestSoFar.fitness());
				addDataPoint(1, i, fitnessesSum[i]
						/ numberOfIndividualsPerGenerations);
			}

			super.finalStatistics(state, result);
		}
	}
}
