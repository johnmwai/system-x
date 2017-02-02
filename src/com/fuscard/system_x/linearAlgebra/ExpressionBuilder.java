package com.fuscard.system_x.linearAlgebra;

import com.fuscard.system_x.linearAlgebra.ExpressionUnit.*;
import java.util.LinkedList;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class ExpressionBuilder {

    private LinkedList<ExpressionUnit> units = new LinkedList<>();
    private ExpressionWriter expressionWriter;

    public ExpressionBuilder() {
        this.expressionWriter = new ExpressionWriter();
    }

    public ExpressionBuilder t(int c, Variable v) {
        units.add(new Term(c, v));
        return this;
    }

    public ExpressionBuilder t(int c) {
        units.add(new Term(c, null));
        return this;
    }

    public ExpressionBuilder plus() {
        units.add(new Plus());
        return this;
    }
    
    public ExpressionBuilder minus() {
        units.add(new Minus());
        return this;
    }

    public ExpressionBuilder equals() {
        units.add(new Equals());
        return this;
    }

    public LinkedList<ExpressionUnit> getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return getExpressionWriter().getString(units.toArray(new ExpressionUnit[0]));
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
