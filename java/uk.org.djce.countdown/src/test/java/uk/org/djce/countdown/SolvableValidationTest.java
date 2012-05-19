package uk.org.djce.countdown;

import org.junit.Test;

public class SolvableValidationTest {

    @Test(expected = IllegalStateException.class)
    public void subtractShouldNotAllowNegative() {
        Solvable n = new BinopNode(
                new NumericNode(5L),
                Solvable.Operator.SUBTRACT,
                new NumericNode(6L)
        );
    }

    @Test(expected = IllegalStateException.class)
    public void subtractShouldNotAllowZero() {
        Solvable n = new BinopNode(
                new NumericNode(5L),
                Solvable.Operator.SUBTRACT,
                new NumericNode(5L)
        );
    }

    @Test(expected = IllegalStateException.class)
    public void divideShouldNotAllowFractions() {
        Solvable n = new BinopNode(
                new NumericNode(15L),
                Solvable.Operator.DIVIDE,
                new NumericNode(4L)
        );
    }

}
