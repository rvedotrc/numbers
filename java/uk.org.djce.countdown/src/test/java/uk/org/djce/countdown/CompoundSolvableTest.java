package uk.org.djce.countdown;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CompoundSolvableTest {

    @Test
    public void compoundTest1 () {
        // ( 7 + (3 * 4) ) - (6 / 2)

        Solvable seven = new NumericNode(7L);

        Solvable threeTimesFour = new BinopNode(
                new NumericNode(3L),
                Solvable.Operator.MULTIPLY,
                new NumericNode(4L)
        );

        assertTrue("3*4==12", 12L == threeTimesFour.solve());

        Solvable sevenPlusThreeTimesFour = new BinopNode(
                seven,
                Solvable.Operator.ADD,
                threeTimesFour
        );

        assertTrue("7+3*4==19", 19L == sevenPlusThreeTimesFour.solve());

        Solvable sixOverTwo = new BinopNode(
                new NumericNode(6L),
                Solvable.Operator.DIVIDE,
                new NumericNode(2L)
        );

        assertTrue("6/2==3", 3L == sixOverTwo.solve());

        Solvable all = new BinopNode(
                sevenPlusThreeTimesFour,
                Solvable.Operator.SUBTRACT,
                sixOverTwo
        );

        assertTrue("...=16", 16L == all.solve());

    }

}
