package com.fuscard.system_x.linearAlgebra.matchers;

import com.fuscard.system_x.linearAlgebra.LinearEquation;
import com.fuscard.system_x.linearAlgebra.SolutionStep;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class LinearSystemSolutionStepMatcher
        extends TypeSafeMatcher<SolutionStep> {

    private final String op;
    private final String[] eqs;

    public LinearSystemSolutionStepMatcher(String op, String[] eqs) {
        this.op = op;
        this.eqs = eqs;
    }

    @Override
    protected boolean matchesSafely(SolutionStep item) {
        if(op == null ? item.getOperation() != null : !op.equals(item.getOperation())){
            return false;
        }
        LinearEquation[] equations = item.getEquations();
        //The length of the equations to match can be less than the number of
        //equations in the system. The equations beyond are 'don't care'
        for(int i = 0 ; i < eqs.length; i++){
            //If you supply more equations to match than are there in the linear system
            //the test will fail
            if(i >= item.getEquations().length){
                return false;
            }
            //We can use null as a place holder when we don't care about an equation 
            //in the system
            if(eqs[i] != null){
                if(!eqs[i].equals(equations[i].toString())){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a solution step matching").appendValue(op);
    }

    public static LinearSystemSolutionStepMatcher aSolutionStep(String operation, String[] equations) {
        return new LinearSystemSolutionStepMatcher(operation, equations);
    }

}
