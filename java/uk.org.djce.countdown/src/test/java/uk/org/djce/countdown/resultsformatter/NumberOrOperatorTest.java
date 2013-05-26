package uk.org.djce.countdown.resultsformatter;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberOrOperatorTest {

    @Test
    public void itShouldSupportNumbers() throws Exception {
        NumberOrOperator n = new NumberOrOperator(123L);
        assertFalse(n.isOperator());
        assertEquals(123L, n.getNumber().longValue());
        assertEquals("123", n.toString());
    }

    @Test
    public void itShouldSupportOperators() throws Exception {
        NumberOrOperator n = new NumberOrOperator(Operator.DIVIDE);
        assertTrue(n.isOperator());
        assertEquals(Operator.DIVIDE, n.getOperator());
        assertEquals("/", n.toString());
    }

}
