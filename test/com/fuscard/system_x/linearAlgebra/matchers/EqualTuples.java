package com.fuscard.system_x.linearAlgebra.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class EqualTuples extends TypeSafeMatcher<int[]> {

    private final int[] expectedTuple;
    
    public EqualTuples(int[] tuple){
        this.expectedTuple = tuple;
    }
    
    @Override
    protected boolean matchesSafely(int[] item) {
        if(expectedTuple == null || item == null){
            return false;
        }
        if(item.length != expectedTuple.length){
            return false;
        }
        for (int i = 0 ; i < item.length; i++){
            if(item[i] != expectedTuple[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equal to tuple " + expectedTuple);
    }

    @Factory
    public static <T> Matcher<int[]> equalToTuple(int[] tuple) {
        return new EqualTuples(tuple);
    }
}
