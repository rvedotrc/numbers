package uk.org.djce.countdown;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SimpleSolvableTest {

    @Test
    public void solveNumber() {
        Solvable n = new NumericNode(123L);
        assertTrue("solve", 123L == n.solve());
    }

    @Test
    public void solveSimpleAdd() {
        Solvable n = new BinopNode(new NumericNode(12L), Solvable.Operator.ADD, new NumericNode(3L));
        assertTrue("solve", 15L == n.solve());
    }

    @Test
    public void solveSimpleSubtract() {
        Solvable n = new BinopNode(new NumericNode(12L), Solvable.Operator.SUBTRACT, new NumericNode(3L));
        assertTrue("solve", 9L == n.solve());
    }

    @Test
    public void solveSimpleMultiply() {
        Solvable n = new BinopNode(new NumericNode(12L), Solvable.Operator.MULTIPLY, new NumericNode(3L));
        assertTrue("solve", 36L == n.solve());
    }

    @Test
    public void solveSimpleDivide() {
        Solvable n = new BinopNode(new NumericNode(12L), Solvable.Operator.DIVIDE, new NumericNode(3L));
        assertTrue("solve", 4L == n.solve());
    }

}
