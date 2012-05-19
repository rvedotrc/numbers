package uk.org.djce.countdown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SolvableAsStringTest {

    @Test
    public void compoundTest1 () {
        // ( 7 + (3 * 4) ) - (6 / 2)

        Solvable seven = new NumericNode(7L);
        assertEquals("7", seven.expressAsString());

        Solvable threeTimesFour = new BinopNode(
                new NumericNode(3L),
                Solvable.Operator.MULTIPLY,
                new NumericNode(4L)
        );

        assertEquals("(3) * (4)", threeTimesFour.expressAsString());

        Solvable sevenPlusThreeTimesFour = new BinopNode(
                seven,
                Solvable.Operator.ADD,
                threeTimesFour
        );

        assertEquals("(7) + ((3) * (4))", sevenPlusThreeTimesFour.expressAsString());

        Solvable sixOverTwo = new BinopNode(
                new NumericNode(6L),
                Solvable.Operator.DIVIDE,
                new NumericNode(2L)
        );

        assertEquals("(6) / (2)", sixOverTwo.expressAsString());

        Solvable all = new BinopNode(
                sevenPlusThreeTimesFour,
                Solvable.Operator.SUBTRACT,
                sixOverTwo
        );

        assertEquals("((7) + ((3) * (4))) - ((6) / (2))", all.expressAsString());

    }

}
