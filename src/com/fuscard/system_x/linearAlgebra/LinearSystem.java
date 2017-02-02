package com.fuscard.system_x.linearAlgebra;

import java.util.HashMap;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class LinearSystem {
    private LinearEquation[] linearEquations;

    public LinearSystem(LinearEquation[] linearEquations) {
        this.linearEquations = linearEquations;
    }
    
    
    public int[] getSolution(){
        return null;
    }

    public LinearEquation[] getLinearEquations() {
        return linearEquations;
    }
    
    public boolean isSolution(int[] tuple){
        for(LinearEquation le : linearEquations){
            if(!le.isSolution(tuple)){
                return false;
            }
        }
        return true;
    }
    
    public boolean isSolution(HashMap<Variable, Integer> map){
        for(LinearEquation le : linearEquations){
            if(!le.isSolution(map)){
                return false;
            }
        }
        return true;
    }
}
