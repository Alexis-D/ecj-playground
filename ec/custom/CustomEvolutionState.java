package ec.custom;

import ec.Individual;
import ec.simple.SimpleEvolutionState;

@SuppressWarnings("serial")
public class CustomEvolutionState extends SimpleEvolutionState {
	private Individual best = null;

	public int evolve() {
		int result = super.evolve();
		updateBest();
		return result;
	}

	private void updateBest() {
		for (int i = 0; i < population.subpops.length; ++i) {
			for (int j = 0; j < population.subpops[i].individuals.length; ++j) {
				Individual ind = population.subpops[i].individuals[j];

				if (best == null || ind.fitness.betterThan(best.fitness)) {
					best = ind;
				}
			}
		}
	}

	public Individual getBestSoFar() {
		return best;
	}
}
