package com.fuscard.system_x.linearAlgebra;

import com.fuscard.system_x.linearAlgebra.ExpressionUnit.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class LinearEquation implements Cloneable {

    private int RHS;

    private LinkedList<Term> LHS = new LinkedList<>();

    private ExpressionWriter expressionWriter;

    private final EquationParser equationParser;

    public LinearEquation(ExpressionBuilder b) throws MalformedExpressionException {
        this.equationParser = new EquationParser(b.getUnits(), this);
        this.equationParser.parse();
        this.expressionWriter = new ExpressionWriter();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LinearEquation clone = (LinearEquation) super.clone();
        LinkedList<Term> l = (LinkedList<Term>) LHS.clone();
        clone.LHS = l;
        for(int i = 0; i < l.size(); i++){
            l.set(i, (Term) l.get(i).clone());
        }
        return clone;
    }

    public boolean isSolution(int[] tuple) {
        //We cannot confirm the solution of an equation that is not simplified
        checkSimplified();
        return sumUp(tuple) == getRHS();
    }

    public boolean isSolution(HashMap<Variable, Integer> map) {
        //We cannot confirm the solution of an equation that is not simplified
        checkSimplified();
        int sum = 0;
        for (Term t : getLHS()) {
            Variable v = t.getVariable();
            if (v != null && map.get(v) != null) {
                int r = map.get(v) * t.getCoefficient();
                if (t.isPositive()) {
                    sum += r;
                } else {
                    sum -= r;
                }
            } else {
                if (t.isPositive()) {
                    sum += t.getCoefficient();
                } else {
                    sum -= t.getCoefficient();
                }

            }
        }
        return sum == getRHS();
    }

    private void checkSimplified() {
        if (!isSimplified()) {
            throw new IllegalStateException(
                    "Cannot check the solution of an equation that is not simplified.");
        }
    }

    /**
     * Add up the terms of the equation while replacing the coefficients with
     * the elements of the tuple given. The equation must have as many elements
     * as the tuple
     */
    private int sumUp(int[] tuple) {
        if (tuple.length != getLHS().size()) {
            throw new IllegalArgumentException(
                    "The tuple must have the same length as the equation LHS");
        }
        int res = 0;
        for (int i = 0; i < getLHS().size(); i++) {
            int r = tuple[i] * getLHS().get(i).getCoefficient();
            if (getLHS().get(i).isPositive()) {
                res += r;
            } else {
                res -= r;
            }
        }
        return res;
    }

    private Variable[] getVariables() {
        LinkedList<Variable> v = new LinkedList<>();

        for (Term t : getLHS()) {
            Variable variable = t.getVariable();
            if (variable != null && !v.contains(variable)) {
                v.add(variable);
            }
        }

        return v.toArray(new Variable[0]);
    }

    public int countTerms(Variable v) {
        int c = 0;
        for (Term t : getLHS()) {
            Variable variable = t.getVariable();
            if (variable == v) {
                c++;
            }
        }
        return c;
    }

    public Term getFirstTerm() {
        return getLHS().getFirst();
    }

    public Term getFirstTerm(Variable v) {
        for (Term t : getLHS()) {
            if (t.getVariable() == v) {
                return t;
            }
        }
        return null;
    }

    public boolean isSimplified() {
        for (Variable v : getVariables()) {
            if (countTerms(v) > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return getExpressionWriter().getString(this);
    }

    /**
     * @return the LHS
     */
    public LinkedList<Term> getLHS() {
        return LHS;
    }

    /**
     * @return the RHS
     */
    public int getRHS() {
        return RHS;
    }

    /**
     * @param RHS the RHS to set
     */
    public void setRHS(int RHS) {
        this.RHS = RHS;
    }

    public void multiply(int factor) {
        for (Term t : getLHS()) {
            t.setCoefficient(t.getCoefficient() * factor);
        }
        setRHS(getRHS() * factor);
    }

    public void add(LinearEquation le) {
        List<Term> kol = new LinkedList<>();
        for (Term t : getLHS()) {
            Term ft = le.getFirstTerm(t.getVariable());
            if (ft != null) {
                t.add(ft);
                if (t.getCoefficient() == 0) {
                    kol.add(t);
                }
            }
        }
        removeTerms(kol);
        setRHS(getRHS() + le.getRHS());
    }

    private void removeTerms(List<Term> kol) {
        LHS.removeAll(kol);
    }

    /**
     * @return the expressionWriter
     */
    public ExpressionWriter getExpressionWriter() {
        return expressionWriter;
    }

    /**
     * @param expressionWriter the expressionWriter to set
     */
    public void setExpressionWriter(ExpressionWriter expressionWriter) {
        this.expressionWriter = expressionWriter;
    }
}
