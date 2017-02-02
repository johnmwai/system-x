package com.fuscard.system_x.linearAlgebra;

import static java.lang.String.format;
import java.util.LinkedList;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class EquationParser {

    private final LinkedList<ExpressionUnit> units;

    private final LinearEquation equation;
    private ExpressionUnit unit;
    private int index = 0;

    public EquationParser(LinkedList<ExpressionUnit> units, LinearEquation equation) {
        this.units = units;
        this.equation = equation;
    }

    public void parse() throws MalformedExpressionException {
        next();
        firstTerm();
        midTerms();
        equals();
        lastTerm();
        end();
    }

    /*
     term
     if the 
     */
    private void firstTerm() throws MalformedExpressionException {
        //Get a term or a plus followed by a term
        boolean plus = true;
        if (isPlusOrMinus()) {
            if (isMinus()) {
                plus = false;
            }
            next();
        }

        if (!(unit instanceof ExpressionUnit.Term)) {
            fail("Term");
        }

        ExpressionUnit.Term t = (ExpressionUnit.Term) unit;
        t.setPositive(plus);
        equation.getLHS().add((ExpressionUnit.Term) unit);

    }

    private boolean isMinus() {
        return unit instanceof ExpressionUnit.Minus;
    }

    private boolean isPlusOrMinus() {
        return isPlusOrMinus(unit);
    }

    private boolean isPlusOrMinus(ExpressionUnit unit) {
        return unit instanceof ExpressionUnit.Plus || unit instanceof ExpressionUnit.Minus;
    }

    private void midTerms() throws MalformedExpressionException {
        //if there is a plus, consume a plus and a term
        //else return without failing
        boolean iterate;
        next();
        do {
            if (isPlusOrMinus()) {
                boolean plus = !isMinus();
                next(ExpressionUnit.Term.class);
                ExpressionUnit.Term t = (ExpressionUnit.Term) unit;
                t.setPositive(plus);
                equation.getLHS().add(t);
                next();
                iterate = true;
            } else {
                iterate = false;
            }
        } while (iterate);
    }

    private void equals() throws MalformedExpressionException {
        if (unit instanceof ExpressionUnit.Equals) {
            //The RHS must start with a term
            next(ExpressionUnit.Term.class);
        } else {
            fail("Equals");
        }
    }

    private void end() throws MalformedExpressionException {
        if (index != units.size()) {
            fail("End " + format("but units length was: %d and index was: %d ", units.size(), index));
        }
    }

    private void lastTerm() throws MalformedExpressionException {
        //Will only be called after the equals method if and only if the current 
        //term is a Term
        ExpressionUnit.Term t = (ExpressionUnit.Term) unit;
        if (t.getVariable() != null) {
            throw new MalformedExpressionException();
        }
        equation.setRHS(t.getCoefficient());
    }

    private ExpressionUnit next() {
        if (index >= units.size()) {
            throw new IndexOutOfBoundsException();
        }
        unit = units.get(index++);
        return unit;
    }

    private ExpressionUnit next(Class clazz) throws MalformedExpressionException {
        next();
        if (!clazz.isInstance(unit)) {
            fail(clazz.getSimpleName());
        }
        return unit;
    }

    private void fail(String expected) throws MalformedExpressionException {
        throw new MalformedExpressionException("Expected " + expected);
    }
}
