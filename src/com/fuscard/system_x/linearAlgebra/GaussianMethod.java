package com.fuscard.system_x.linearAlgebra;

import com.fuscard.system_x.linearAlgebra.ExpressionUnit.Term;
import java.util.HashMap;
import java.util.Observable;

/**
 * Class that uses Gauss's Method to obtain the solution to a system of linear
 * equations.
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class GaussianMethod extends Observable {

    /**
     * Solve the system of linear equations notifying observers of the steps
     * taken.
     *
     * @param ls The system of linear equations
     * @return The solution of the system of linear equations
     */
    public HashMap<Variable, Integer> solve(LinearSystem ls) {
        /*
         Use the topmost row to eliminate the leftmost term on all the other terms
         Use the second topmost row to eliminate the second leftmost term from all the rows below it
         */
        LinearEquation[] eqs = ls.getLinearEquations();
        for (int i = 0; i < eqs.length; i++) {
            for (int j = i + 1; j < eqs.length; j++) {
                eliminateLeftmostTerm(eqs, i, j);
            }
        }
        HashMap<Variable, Integer> res = new HashMap<>();
        for (int i = eqs.length - 1; i >= 0; i--) {
            LinearEquation eq = eqs[i];

            if (eq.getLHS().size() == 1) {
                //This row has been reduced so that there is only one term
                //This is the key to solving systems of linear equations
                Term t = eq.getFirstTerm();
                res.put(t.getVariable(), eq.getRHS() / t.getNormalizedCoefficient());
            } else {
                int total = 0;

                int c = 0;
//                Variable v = null;
                Term t2 = null;

                for (Term t : eq.getLHS()) {
                    Variable v1 = t.getVariable();
                    if (v1 == null) {
                        //This term doesn't have a variable
                        total += t.getNormalizedCoefficient();
                    } else if (res.containsKey(v1)) {
                        //If this term is in a solved variable
                        total += t.getNormalizedCoefficient() * res.get(v1);
                    } else {
                        //The variable that we are trying to solve
                        //If it has already been set then we have a problem
                        if (t2 == null) {
                            t2 = t;
                        } else {
                            throw new IllegalStateException(""
                                    + "Row cannot be solved because it has two unsolved unkowns");
                        }
                    }
                }

                if (t2 == null) {
                    //If we do not have a variable to store and the total of the
                    //LHS is not the same as the RHS, the system is overspecified
                    if (total != eq.getRHS()) {
                        throw new IllegalStateException(""
                                + "The system is overspecified");
                    }
                } else {
                    res.put(t2.getVariable(), (eq.getRHS() - total) / t2.getNormalizedCoefficient());
                }
            }

        }
        return res;
    }

    private void eliminateLeftmostTerm(LinearEquation[] eqs, int i, int j) {

        LinearEquation l1 = eqs[i], l2 = eqs[j];
        if (!(l1.isSimplified() && l2.isSimplified())) {
            throw new IllegalStateException("Equations not simplified");
        }
        Term t1 = l1.getFirstTerm();
        Variable v = t1.getVariable();

        Term t2 = l2.getFirstTerm(v);

        //If the second equation doesn't have a term with that variable, there is nothing to do
        if (t2 == null) {
            return;
        }
        //Get the factor to multiply the frst equation with
        int factor = -t2.getCoefficient() / t1.getCoefficient();
        //Create a shorthand notation for this operation
        String op = (factor == 1 ? "" : factor == -1 ? "-" : factor) + "r" + (i + 1) + "+r" + (j + 1);
        LinearEquation l1c = cloneEq(l1);
        l1c.multiply(factor);
        l2.add(l1c);

        SolutionStep ssp = new SolutionStep(eqs, op);

        setChanged();
        notifyObservers(ssp);
    }

    private LinearEquation cloneEq(LinearEquation le) {
        try {
            return (LinearEquation) le.clone();
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException("Could not clone linear equation");
        }
    }
}
