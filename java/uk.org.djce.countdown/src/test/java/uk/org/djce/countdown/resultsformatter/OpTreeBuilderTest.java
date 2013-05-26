package uk.org.djce.countdown.resultsformatter;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OpTreeBuilderTest {

    private List<NumberOrOperator> ops;
    private OpTreeBuilder underTest;
    private OpTree result;

    @Before
    public void before() {
        underTest = new OpTreeBuilder();
    }

    @Test
    public void testNumber() throws Exception {
        givenOps(4L);
        run();
        assertTrue(result instanceof NumericNode);
        assertEquals(4L, result.value());
    }

    @Test
    public void testNoOps() throws Exception {
        givenOps();
        try {
            run();
            fail("Expected exception not thrown");
        } catch (OpTreeBuilder.NoResultsException e) {
        }
    }

    @Test
    public void testTwoNumbers() throws Exception {
        givenOps(4L, 5L);
        try {
            run();
            fail("Expected exception not thrown");
        } catch (OpTreeBuilder.TooManyResultsException e) {
        }
    }

    @Test
    public void testSimpleAdd() throws Exception {
        givenOps(4L, 5L, Operator.ADD);
        run();
        assertEquals(9L, result.value());
    }

    @Test
    public void testSimpleSubtract() throws Exception {
        givenOps(4L, 7L, Operator.SUBTRACT);
        run();
        assertEquals(3L, result.value());
    }

    @Test
    public void testSimpleMultiply() throws Exception {
        givenOps(4L, 7L, Operator.MULTIPLY);
        run();
        assertEquals(28L, result.value());
    }

    @Test
    public void testSimpleDivide() throws Exception {
        givenOps(4L, 72L, Operator.DIVIDE);
        run();
        assertEquals(18L, result.value());
    }

    @Test
    public void testBadSubtract() throws Exception {
        givenOps(4L, 2L, Operator.DIVIDE);
        try {
            run();
            fail("Expected exception not thrown");
        } catch (OpTreeBuilder.IllegalValueException e) {
        }
    }

    @Test
    public void testBadDivide() throws Exception {
        givenOps(4L, 7L, Operator.DIVIDE);
        try {
            run();
            fail("Expected exception not thrown");
        } catch (OpTreeBuilder.IllegalValueException e) {
        }
    }

    @Test
    public void testUnderrun() throws Exception {
        givenOps(4L, Operator.DIVIDE);
        try {
            run();
            fail("Expected exception not thrown");
        } catch (OpTreeBuilder.StackUnderrunException e) {
        }
    }

    private void givenOps(Object... args) {
        ImmutableList.Builder<NumberOrOperator> builder = ImmutableList.builder();

        for (Object arg : args) {
            if (arg instanceof Long) {
                builder.add(new NumberOrOperator((Long)arg));
            } else if (arg instanceof Operator) {
                builder.add(new NumberOrOperator((Operator)arg));
            } else {
                throw new IllegalArgumentException();
            }
        }

        ops = builder.build();
    }

    private void run() throws Exception {
        result = underTest.build(ops);
    }

}
