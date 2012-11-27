package ec.app.squares;

import java.lang.Math;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class Squares extends Problem implements SimpleProblemForm {
    public static final String P_SQUARES = "squares";
    public static final String P_EPSILON = "fitness-epsilon";
    private double epsilon;

    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);

        Parameter def = defaultBase();
        epsilon = state.parameters.getDoubleWithMax(base.push(P_EPSILON), def.push(P_EPSILON),
                0.d, 1.d);

        if(epsilon < 0.d) {
            state.output.fatal("Epsilon should be in the interval [0, 1].", base.push(P_EPSILON), def.push(P_EPSILON));
        }
    }

    public Parameter defaultBase() {
        return super.defaultBase().push(P_SQUARES);
    }

    public void evaluate(final EvolutionState state,
        final Individual ind,
        final int subpopulation,
        final int threadnum) {
        if(ind.evaluated) {
            return;
        }

        if(!(ind instanceof IntegerVectorIndividual)) {
            state.output.fatal("ind should be an IntegerVectorIndividual.");
        }

        IntegerVectorIndividual iInd = (IntegerVectorIndividual)ind;
        float fitness = 0.f;

        for(int i = 0; i < iInd.genome.length; ++i) {
            float squared = (i + 1) * (i + 1);
            fitness += Math.abs(squared - iInd.genome[i]) / squared;
        }

        // + .001 to avoid division by 0
        ((SimpleFitness)iInd.fitness).setFitness(state, 1.f / (fitness + .001f), fitness == 0.f);
        iInd.evaluated = true;
    }
}
