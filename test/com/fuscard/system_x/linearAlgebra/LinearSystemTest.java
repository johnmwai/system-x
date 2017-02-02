package com.fuscard.system_x.linearAlgebra;

import static com.fuscard.system_x.linearAlgebra.Variables.*;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class LinearSystemTest {

    @Test
    public void createsInstanceWithArrayOfEquations() {
        LinearEquation[] les = {};
        LinearSystem ls = new LinearSystem(les);
        Assert.assertThat(ls.getLinearEquations(), is(les));
    }

    @Test
    public void confirmsSolutionGivenAsArray() throws MalformedExpressionException {
        LinearSystem ls = new LinearSystem(new LinearEquation[]{
            le(b().t(3, x).plus().t(2, y).equals().t(7)),
            le(b().t(-1, x).plus().t(1, y).equals().t(6))
        });

        assertTrue(ls.isSolution(new int[]{-1, 5}));
        assertFalse(ls.isSolution(new int[]{5, -1}));
    }

    @Test
    public void confirmsSolutionGivenAsMap() throws MalformedExpressionException {
        LinearEquation l1 = le(b().t(3, x).plus().t(2, y).equals().t(7));
        LinearEquation l2 = le(b().t(-1, x).plus().t(1, y).equals().t(6));
        assertThat(l1.toString(), is("3x + 2y = 7"));
        assertThat(l2.toString(), is("-x + y = 6"));
        LinearSystem ls = new LinearSystem(new LinearEquation[]{
            l1,
            l2
        });

        assertTrue(ls.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, -1);
                put(y, 5);
            }
        }));
        assertFalse(ls.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, 5);
                put(y, -1);
            }
        }));
    }
    
    @Test
    public void confirmsSolutionWhenEquationsHaveDifferentLengths() throws MalformedExpressionException {
        LinearEquation l1 = le(b().t(1, x).plus().t(6, y).equals().t(9));
        LinearEquation l2 = le(b().t(1, x).plus().t(5, y).minus().t(2, z).equals().t(2));
        LinearEquation l3 = le(b().t(3, z).equals().t(9));
        assertThat(l1.toString(), is("x + 6y = 9"));
        assertThat(l2.toString(), is("x + 5y - 2z = 2"));
        assertThat(l3.toString(), is("3z = 9"));
        LinearSystem ls = new LinearSystem(new LinearEquation[]{
            l1,
            l2,
            l3
        });

        assertTrue(ls.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, 3);
                put(y, 1);
                put(z, 3);
            }
        }));
        
        
    }

    private ExpressionBuilder b() {
        return new ExpressionBuilder();
    }

    private LinearEquation le(ExpressionBuilder b) throws MalformedExpressionException {
        return new LinearEquation(b);
    }
}
