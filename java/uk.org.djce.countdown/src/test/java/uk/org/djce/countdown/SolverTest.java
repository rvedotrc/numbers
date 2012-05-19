package uk.org.djce.countdown;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import javax.annotation.concurrent.Immutable;

public class SolverTest {

    @Test
    public void foo1() {
        Solver.solveAndPrint(17L, ImmutableList.of(8L));
    }

    @Test
    public void foo2() {
        Solver.solveAndPrint(17L, ImmutableList.of(8L, 6L, 3L));
    }

    @Test
    public void foo3() {
        Solver.solveAndPrint(817L, ImmutableList.of(3L, 3L, 5L, 1L, 9L, 75L));
    }
}
