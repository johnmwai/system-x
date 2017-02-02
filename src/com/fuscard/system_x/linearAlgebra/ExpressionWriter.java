package com.fuscard.system_x.linearAlgebra;

import com.fuscard.system_x.linearAlgebra.ExpressionUnit.Term;

/**
 * Handles converting expressions to String representation
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class ExpressionWriter {

    public String getString(ExpressionUnit[] units) {
        return getString(units, false);
    }

    private String getString(ExpressionUnit[] units, boolean termSigns) {
        String s = "";
        boolean space = false;
        for (ExpressionUnit u : units) {
            if (space) {
                s += " ";
            } else {
                space = true;
            }
            s += getString(u, termSigns);
        }
        return s;
    }

    private String getString(ExpressionUnit unit, boolean termSigns) {
        if (unit instanceof Term) {
            return ((Term) unit).toString(termSigns);
        } else {
            return unit.toString();
        }
    }

    public String getString(LinearEquation eq) {
        Term t = eq.getFirstTerm();
        String res = getString(t, !t.isPositive());
        String otherTerms = getString(eq.getLHS().subList(1, eq.getLHS().size()).toArray(new Term[0]), true);

        if (!"".equals(otherTerms)) {
            res += " " + otherTerms;
        }

        res += " = " + eq.getRHS();
        return res;
    }
}
