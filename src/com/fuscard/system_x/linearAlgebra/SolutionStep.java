package com.fuscard.system_x.linearAlgebra;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class SolutionStep {

    private LinearEquation[] equations;
    private String operation;

    public SolutionStep(LinearEquation[] equations, String operation) {
        this.equations = equations;
        this.operation = operation;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the equations
     */
    public LinearEquation[] getEquations() {
        return equations;
    }

    /**
     * @param equations the equations to set
     */
    public void setEquations(LinearEquation[] equations) {
        this.equations = equations;
    }

    @Override
    public String toString() {
        String s = "{" +getOperation() + "}\n";
        for (LinearEquation le : getEquations()) {
            s += le.toString() + "\n";
        }
        return s;
    }

}
