package uk.org.djce.countdown;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverState {

    ImmutableList<Solvable> stack;
    ImmutableMap<Long, Integer> unusedNumbers;

    public SolverState (List<Long> numbers) {
        if (numbers.isEmpty()) throw new IllegalStateException();

        Map<Long, Integer> m = new HashMap<Long, Integer>();
        for (Long n : numbers) {
            if (m.containsKey(n)) {
                m.put(n, m.get(n)+1);
            } else {
                m.put(n, 1);
            }
        }

        stack = ImmutableList.of();
        unusedNumbers = ImmutableMap.copyOf(m);
    }

    private SolverState(SolverState parent, Long pushNumber) {
        Map<Long, Integer> newUnusedNumbers = new HashMap<Long, Integer>(parent.unusedNumbers);

        if (!newUnusedNumbers.containsKey(pushNumber)) throw new IllegalStateException();

        if (newUnusedNumbers.get(pushNumber) > 1) {
            newUnusedNumbers.put(pushNumber, newUnusedNumbers.get(pushNumber) - 1);
        } else if (newUnusedNumbers.get(pushNumber) == 1) {
            newUnusedNumbers.remove(pushNumber);
        } else {
            throw new IllegalStateException();
        }

        List<Solvable> newStack = new ArrayList<Solvable>(parent.stack);
        newStack.add(new NumericNode(pushNumber));

        this.stack = ImmutableList.copyOf(newStack);
        this.unusedNumbers = ImmutableMap.copyOf(newUnusedNumbers);
    }

    private SolverState(SolverState parent, Solvable.Operator pushOperator) {
        List<Solvable> newStack = new ArrayList<Solvable>(parent.stack);

        if (newStack.size() < 2) throw new IllegalStateException();

        Solvable right = newStack.remove(newStack.size() - 1);
        Solvable left = newStack.remove(newStack.size() - 1);
        newStack.add(new BinopNode(left, pushOperator, right));

        this.unusedNumbers = parent.unusedNumbers;
        this.stack = ImmutableList.copyOf(newStack);
    }

    public String toString() {
        List<String> s = new ArrayList<String>();
        for (Solvable solvable : stack) {
            s.add(solvable.expressAsString());
        }

        List<String> u = new ArrayList<String>();
        for (Map.Entry e : unusedNumbers.entrySet()) {
            u.add(String.format("%dx%d", e.getValue(), e.getKey()));
        }

        return String.format("stack=%s unused=%s",
            Joiner.on(",").join(s),
            Joiner.on(",").join(u)
        );
    }

    public boolean isSolvable() {
        return(stack.size() == 1);
    }

    public Solvable solvable() {
        if (!isSolvable()) throw new IllegalStateException();
        return stack.get(0);
    }

    public List<SolverState> nextStates() {
        List<SolverState> nexts = new ArrayList<SolverState>();

        if (stack.size() > 1) {
            // Can push an operator
            Solvable left = stack.get(stack.size() - 2);
            Solvable right = stack.get(stack.size() - 1);

            // Can always push ADD, but as optimisation, only do so if left >= right
            if (left.solve() >= right.solve()) {
                nexts.add(new SolverState(this, Solvable.Operator.ADD));
            }

            // Can only push SUBTRACT if left > right
            if (left.solve() > right.solve()) {
                nexts.add(new SolverState(this, Solvable.Operator.SUBTRACT));
            }

            // Can always push MULTIPLY, but as optimisation, only do so if left >= right
            if (left.solve() >= right.solve()) {
                nexts.add(new SolverState(this, Solvable.Operator.MULTIPLY));
            }

            // Can only push DIVIDE if left % right == 0
            Long remainder = left.solve() % right.solve();
            if (remainder.equals(0L)) {
                nexts.add(new SolverState(this, Solvable.Operator.DIVIDE));
            }
        }

        if (!unusedNumbers.isEmpty()) {
            // Can push a number
            for (Long n : unusedNumbers.keySet()) {
                nexts.add(new SolverState(this, n));
            }
        }

        return nexts;
    }
}
