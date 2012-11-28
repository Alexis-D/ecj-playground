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

        if(!(ind instanceof DoubleVectorIndividual)) {
            state.output.fatal("ind should be an DoubleVectorIndividual", null);
        }

        double fitness = 1.f;
        DoubleVectorIndividual fInd = (DoubleVectorIndividual)ind;

        for(double v : fInd.genome) {
            fitness *= v;  // compute fitness
        }

        ((SimpleFitness)fInd.fitness).setFitness(state, (float)fitness, fitness == 1.d);
        fInd.evaluated = true;
    }
}
