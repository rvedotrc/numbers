package uk.org.djce.countdown;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

public class SolverStateTest {

    @Test
    public void oneNumber() {
        SolverState solverState = new SolverState(ImmutableList.of(6L));
        walk(solverState);
    }

    @Test
    public void oneNumberTwice() {
        SolverState solverState = new SolverState(ImmutableList.of(7L, 7L));
        walk(solverState);
    }

    @Test
    public void twoNumbersWithDivide() {
        SolverState solverState = new SolverState(ImmutableList.of(6L, 2L));
        walk(solverState);
    }

    @Test
    public void twoNumbersNoDivide() {
        SolverState solverState = new SolverState(ImmutableList.of(6L, 5L));
        walk(solverState);
    }

    private void walk(SolverState s) {
        walk(s, 0);
    }

    private void walk(SolverState s, int depth) {
        System.out.println("walk depth="+depth+" state="+s);

        if (s.isSolvable()) {
            System.out.println("answer=" + s.solvable().solve());
        }

        for (SolverState next : s.nextStates()) {
            walk(next, depth+1);
        }
    }
}
