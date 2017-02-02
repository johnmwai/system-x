package com.fuscard.system_x.linearAlgebra;

import static com.fuscard.system_x.linearAlgebra.Variables.*;
import static com.fuscard.system_x.linearAlgebra.matchers.HashMapWithSameElements.aHashMapWithSameElementsAs;
import static com.fuscard.system_x.linearAlgebra.matchers.LinearSystemSolutionStepMatcher.aSolutionStep;
import java.util.HashMap;
import java.util.Observer;
import static org.hamcrest.CoreMatchers.is;
import org.jmock.Expectations;
import static org.jmock.Expectations.same;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
@RunWith(JMock.class)
public class GaussMethodTest {

    private final Mockery context = new JUnit4Mockery();
    private final Sequence elimination = context.sequence("gaussian-elimination");
    private LinearEquation l1, l2, l3;
    private LinearSystem ls;

    HashMap<Variable, Integer> solution = new HashMap<Variable, Integer>() {
        {
            put(x, 1);
            put(y, -1);
            put(z, 0);
        }
    };

    @Before
    public void prepareEquations() throws MalformedExpressionException {
        ExpressionBuilder b = new ExpressionBuilder();
        l1 = new LinearEquation(
                b.t(1, x).plus().t(1, y).equals().t(0)
        );
        b = new ExpressionBuilder();
        l2 = new LinearEquation(
                b.t(2, x).minus().t(1, y).plus().t(3, z).equals().t(3)
        );
        b = new ExpressionBuilder();
        l3 = new LinearEquation(
                b.t(1, x).minus().t(2, y).minus().t(1, z).equals().t(3)
        );
        assertThat(l1.toString(), is("x + y = 0"));
        assertThat(l2.toString(), is("2x - y + 3z = 3"));
        assertThat(l3.toString(), is("x - 2y - z = 3"));
        ls = new LinearSystem(new LinearEquation[]{
            l1,
            l2,
            l3
        });
        assertTrue(ls.isSolution(solution));
    }

    @Test
    public void findsSolutionOfLinearSystem() throws MalformedExpressionException {
        GaussianMethod gm = new GaussianMethod();
        HashMap<Variable, Integer> sol = gm.solve(ls);
        assertThat(sol, is(aHashMapWithSameElementsAs(solution)));
    }
    
    @Test
    public void firesUpdatesWhileEliminating() throws MalformedExpressionException {
        final Observer observer = context.mock(Observer.class);
        final GaussianMethod gm = new GaussianMethod();
        gm.addObserver(observer);
        context.checking(new Expectations() {
            {

                oneOf(observer).update(with(same(gm)),
                        with(aSolutionStep("-2r1+r2",
                                        new String[]{
                                            "x + y = 0",
                                            "- 3y + 3z = 3",
                                            "x - 2y - z = 3"
                                        })));
                inSequence(elimination);
                oneOf(observer).update(with(same(gm)),
                        with(aSolutionStep("-r1+r3",
                                        new String[]{
                                            "x + y = 0",
                                            "- 3y + 3z = 3",
                                            "- 3y - z = 3"
                                        })));
                inSequence(elimination);
                oneOf(observer).update(with(same(gm)),
                        with(aSolutionStep("-r2+r3",
                                        new String[]{
                                            "x + y = 0",
                                            "- 3y + 3z = 3",
                                            "- 4z = 0"
                                        })));
                inSequence(elimination);
            }
        });

        gm.solve(ls);
    }
}
