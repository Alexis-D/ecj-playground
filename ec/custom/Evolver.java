package ec.custom;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class Evolver extends Evolve {
    public Individual run(String[] args) {
        EvolutionState eState;
        ParameterDatabase parameters;

        parameters = loadParameterDatabase(args);
        int numJobs = parameters.getIntWithDefault(new Parameter("jobs"), null, 1);
        Individual best = null;

        for(int i = 0; i < numJobs; ++i) {
            eState = initialize(parameters, i);

            if(!(eState instanceof CustomEvolutionState)) {
                eState.output.fatal("state should be CustomEvolutionState if ec.custom.Evolver is used.");
            }

            CustomEvolutionState state = (CustomEvolutionState)eState;

            state.job = new Object[1];
            state.job[0] = new Integer(i);
            state.runtimeArguments = args;
            state.run(EvolutionState.C_STARTED_FRESH);

            if(best == null || state.getBestSoFar().fitness.betterThan(best.fitness)) {
                best = state.getBestSoFar();
            }

            cleanup(state);
        }

        return best;
    }

    public static void main(String[] args) {
        String[] argv = {"-file", "ec/app/product/product.params"};
        (new Evolver()).run(argv);
    }
}
