package com.fuscard.system_x.linearAlgebra.matchers;

import com.fuscard.system_x.linearAlgebra.Variable;
import static com.fuscard.system_x.linearAlgebra.Variables.*;
import static com.fuscard.system_x.linearAlgebra.matchers.HashMapWithSameElements.aHashMapWithSameElementsAs;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class HashMapWithSameElementsTest {
    @Test public void testIsAHashMapWithSameElementsAs(){
        //Same elements
        assertThat(new HashMap<Variable, Integer>() {
            {
                put(x, 1);
                put(y, -1);
                put(z, 0);
            }
        }, is(aHashMapWithSameElementsAs(new HashMap<Variable, Integer>() {
            {
                put(x, 1);
                put(y, -1);
                put(z, 0);
            }
        })));
        //Same keys different values
        assertThat(new HashMap<Variable, Integer>() {
            {
                put(x, 1);
                put(y, -1);
                put(z, 0);
            }
        }, is(not(aHashMapWithSameElementsAs(new HashMap<Variable, Integer>() {
            {
                put(x, 1);
                put(y, 0);
                put(z, -1);
            }
        }))));
        //Different sizes
        assertThat(new HashMap<Variable, Integer>() {
            {
                put(x, 1);
                put(y, -1);
                put(z, 0);
            }
        }, is(not(aHashMapWithSameElementsAs(new HashMap<Variable, Integer>() {
            {
                put(a, 1);
                put(x, 1);
                put(y, -1);
                put(z, 0);
                
            }
        }))));
    }
}
