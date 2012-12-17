package ec.app.product;

import ec.EvolutionState;
import ec.vector.DoubleVectorIndividual;
import ec.vector.FloatVectorSpecies;

@SuppressWarnings("serial")
public class DumbDoubleVectorIndividual extends DoubleVectorIndividual {
	public void reset(EvolutionState state, int thread) {
		FloatVectorSpecies s = (FloatVectorSpecies) species;
		for (int x = 0; x < genome.length; x++) {
			if (state.random[thread].nextDouble() < .5) {
				genome[x] = (s.minGene(x) + Math.pow(
						state.random[thread].nextDouble(), .5)
						* (s.maxGene(x) - s.minGene(x)));
			} else {
				genome[x] = (s.minGene(x) + Math.pow(
						state.random[thread].nextDouble(), 2)
						* (s.maxGene(x) - s.minGene(x)));
			}
		}
	}
}
