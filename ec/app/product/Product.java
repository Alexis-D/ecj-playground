package ec.app.product;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class Product extends Problem implements SimpleProblemForm {
    public static final String P_PRODUCT = "product";

    public Parameter defaultBase() {
        // if we had parameters to be configured in the params for this
        // problems we would be able to do product.xxx = rather than for
        // instance eval.problem.xxx
        return super.defaultBase().push(P_PRODUCT);
    }

    public void evaluate(final EvolutionState state,
        final Individual ind,
        final int subpopulation,
        final int threadnum) {
        if(ind.evaluated) {
            return;
        }

        if(!(ind instanceof FloatVectorIndividual)) {
            state.output.fatal("ind should be an FloatVectorIndividual", null);
        }

        float fitness = 1.f;
        FloatVectorIndividual fInd = (FloatVectorIndividual)ind;

        for(float v : fInd.genome) {
            fitness *= v;  // compute fitness
        }

        ((SimpleFitness)fInd.fitness).setFitness(state, fitness, fitness == 1.);
        fInd.evaluated = true;
    }
}
