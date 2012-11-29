package ec.custom;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class Evolver extends Evolve {
    public Individual run(String[] args) {
        EvolutionState state;
        ParameterDatabase parameters;

        parameters = loadParameterDatabase(args);
        int numJobs = parameters.getIntWithDefault(new Parameter("jobs"), null, 1);
        Individual best = null;

        for(int i = 0; i < numJobs; ++i) {
            state = initialize(parameters, i);
            state.job = new Object[1];
            state.job[0] = new Integer(i);
            state.runtimeArguments = args;
            state.run(EvolutionState.C_STARTED_FRESH);

            best = pickBest(state, best);

            cleanup(state);
        }

        return best;
    }

    private Individual pickBest(EvolutionState state, Individual best) {
        for(int i = 0; i < state.population.subpops.length; ++i) {
            for(int j = 0; j < state.population.subpops[i].individuals.length; ++j) {
                Individual ind = state.population.subpops[i].individuals[j];

                if(best == null || ind.fitness.betterThan(best.fitness)) {
                    best = ind;
                }
            }
        }

        return best;
    }

    public static void main(String[] args) {
        String[] argv = {"-file", "ec/app/product/product.params"};
        (new Evolver()).run(argv);
    }
}
