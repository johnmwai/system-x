package com.fuscard.system_x.linearAlgebra;

import static com.fuscard.system_x.linearAlgebra.matchers.EqualTuples.equalToTuple;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Class that contains Algebra matchers.
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class AlgebraSugar {

    public static void assertEqualTuples(int[] a, int[] b) {
        assertThat(a, is(equalToTuple(b)));
    }

    public static void assertUnEqualTuples(int[] a, int[] b) {
        assertThat(a, is(not(equalToTuple(b))));
    }
}
