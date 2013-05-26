package uk.org.djce.countdown;

import java.util.ArrayList;
import java.util.List;

public final class Solver {

    private Solver() {

    }

    public static void solveAndPrint(Long target, List<Long> numbers) {
        Long bestDistance = null;

        List<SolverState> queue = new ArrayList<SolverState>();
        queue.add(new SolverState(numbers));

        while (!queue.isEmpty()) {
            SolverState s = queue.remove(0);

            if (s.isSolvable()) {
                Solvable solvable = s.solvable();
                Long value = solvable.solve();
                Long distance = Math.abs(target - value);

                if (bestDistance == null || distance <= bestDistance) {
                    System.out.println(String.format("%s = %d (%d off)", solvable.expressAsString(), value, distance));
                    bestDistance = distance;
                }
            }

            for (SolverState next : s.nextStates()) {
                queue.add(next);
            }
        }
    }

}
