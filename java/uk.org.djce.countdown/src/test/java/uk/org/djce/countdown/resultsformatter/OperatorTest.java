package uk.org.djce.countdown.resultsformatter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OperatorTest {

    @Test
    public void itShouldFindBySymbol() throws Exception {
        testBothWays(Operator.ADD, "+");
        testBothWays(Operator.SUBTRACT, "-");
        testBothWays(Operator.MULTIPLY, "*");
        testBothWays(Operator.DIVIDE, "/");

        try {
            Operator.findByOp("]");
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException e) {

        }
    }

    private void testBothWays(Operator operator, String string) {
        assertEquals(operator, Operator.findByOp(string));
        assertEquals(string, operator.getOp());
    }
}
