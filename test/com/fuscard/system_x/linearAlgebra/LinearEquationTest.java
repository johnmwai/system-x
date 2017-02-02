package com.fuscard.system_x.linearAlgebra;

import static com.fuscard.system_x.linearAlgebra.Variables.*;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class LinearEquationTest {

    @Test(expected = MalformedExpressionException.class)
    public void throwsException_EquationStartsWithEquals() throws MalformedExpressionException {
        ExpressionBuilder br = b();
        assertThat(br.equals().t(3, x).plus().t(2, y).toString(), is("= 3x + 2y"));
        le(br);
    }

    @Test(expected = MalformedExpressionException.class)
    public void throwsException_LastTermHasVariable() throws MalformedExpressionException {
        //3x + 2y = 2b
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).equals().t(2, b).toString(), is("3x + 2y = 2b"));
        le(br);
    }

    @Test(expected = MalformedExpressionException.class)
    public void throwsException_MoreThatnOneTermAfterEquals() throws MalformedExpressionException {
        //3x + 2y = 2 + 6x
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).equals().t(2).plus().t(6, x).toString(), is("3x + 2y = 2 + 6x"));
        le(br);
    }

    @Test(expected = MalformedExpressionException.class)
    public void throwsException_TwoTermsFollowEachOther() throws MalformedExpressionException {
        //3x + 2y 4c = 2b
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).t(4, c).equals().t(2, b).toString(), is("3x + 2y 4c = 2b"));
        le(br);
    }

    @Test
    public void confirmsSolutionGivenAsArray() throws MalformedExpressionException {
        //3x + 2y = 19
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).equals().t(19).toString(), is("3x + 2y = 19"));
        LinearEquation le = le(br);
        assertTrue(le.isSolution(new int[]{5, 2}));
        assertFalse(le.isSolution(new int[]{2, 5}));

        br = b();
        assertThat(br.t(5, x).minus().t(2, y).equals().t(10).toString(), is("5x - 2y = 10"));
        le = le(br);
        assertTrue(le.isSolution(new int[]{10, 20}));
    }
    
    @Test
    public void addsTwoEquations() throws MalformedExpressionException {
        //3x + 2y = 19
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).equals().t(19).toString(), is("3x + 2y = 19"));
        LinearEquation l1 = le(br);

        br = b();
        assertThat(br.t(5, x).plus().t(2, y).equals().t(10).toString(), is("5x + 2y = 10"));
        LinearEquation l2 = le(br);
        
        l2.add(l1);
        
        assertThat(l2.toString(), is("8x + 4y = 29"));
    }
    
     @Test
    public void eliminatesVariablesInAddition() throws MalformedExpressionException {
        //3x + 2y = 19
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).equals().t(19).toString(), is("3x + 2y = 19"));
        LinearEquation l1 = le(br);

        br = b();
        assertThat(br.t(5, x).minus().t(2, y).equals().t(10).toString(), is("5x - 2y = 10"));
        LinearEquation l2 = le(br);
        
        l2.add(l1);
        
        assertThat(l2.toString(), is("8x = 29"));
    }
    
    @Test
    public void multipliesLinearEquationByAFactor() throws MalformedExpressionException {
        //3x + 2y = 19
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).equals().t(19).toString(), is("3x + 2y = 19"));
        LinearEquation le = le(br);
        le.multiply(2);
        
        assertThat(le.toString(), is("6x + 4y = 38"));
    }

    @Test
    public void confirmsSolutionGivenAsMap() throws MalformedExpressionException {
        //3x + 2y = 19
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).equals().t(19).toString(), is("3x + 2y = 19"));
        LinearEquation le = le(br);

        assertTrue(le.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, 5);
                put(y, 2);
            }
        }));
        assertFalse(le.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, 2);
                put(y, 5);
            }
        }));

    }

    @Test
    public void confirmsSolution_NegativeTerm() throws MalformedExpressionException {
        ExpressionBuilder br = b().t(5, x).minus().t(2, y).equals().t(10);
        LinearEquation le = new LinearEquation(br);
        assertThat(le.toString(), is("5x - 2y = 10"));
        assertTrue(le.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, 10);
                put(y, 20);
            }
        }));
    }
    
    
    @Test
    public void confirmsSolution_ZeroRHS() throws MalformedExpressionException {
        ExpressionBuilder br = b().t(1, x).plus().t(1, y).equals().t(0);
        LinearEquation le = new LinearEquation(br);
        assertThat(le.toString(), is("x + y = 0"));
        assertTrue(le.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, 1);
                put(y, -1);
            }
        }));
    }

    @Test(expected = IllegalStateException.class)
    public void isSolution_throwsException_EquationNotSimplified() throws MalformedExpressionException {
        //3x + 2y + 2x = 19
        ExpressionBuilder br = b();
        assertThat(br.t(3, x).plus().t(2, y).plus().t(2, x).equals().t(19).toString(), is("3x + 2y + 2x = 19"));
        LinearEquation le = le(br);

        le.isSolution(new HashMap<Variable, Integer>() {
            {
                put(x, 5);
                put(y, 2);
            }
        });
    }

    private ExpressionBuilder b() {
        return new ExpressionBuilder();
    }

    private LinearEquation le(ExpressionBuilder b) throws MalformedExpressionException {
        return new LinearEquation(b);
    }
}
