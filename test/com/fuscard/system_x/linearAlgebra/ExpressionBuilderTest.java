package com.fuscard.system_x.linearAlgebra;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class ExpressionBuilderTest {
    
    @Test public void methodsReturnSameInstanceForChaining(){
        ExpressionBuilder eb = new ExpressionBuilder();
        //Plus
        assertThat(eb.plus(), is(eb));
        //Equals
        assertThat(eb.equals(), is(eb));
        //term
        assertThat(eb.t(0), is(eb));
        assertThat(eb.t(0, null), is(eb));
    }
    
    
}
