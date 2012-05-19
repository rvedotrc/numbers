package uk.org.djce.countdown;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BinopNode implements Solvable {
    private final Operator operator;
    private final Solvable left;
    private final Solvable right;
    private final Long value;

    public BinopNode(Solvable left, Operator operator, Solvable right) {
        this.left = left;
        this.operator = operator;
        this.right = right;

        if (operator == Operator.SUBTRACT) {
            if (right.solve() > left.solve()) {
                throw new IllegalStateException("Result of SUBTRACT node would be negative");
            }

            if (right.solve().equals( left.solve() )) {
                throw new IllegalStateException("Result of SUBTRACT node would be zero");
            }
        }

        if (operator == Operator.DIVIDE) {
            Long remainder = left.solve() % right.solve();
            if (!remainder.equals(0L)) {
                throw new IllegalStateException("Result of DIVIDE node would be fractional");
            }
        }

        if (operator == Operator.ADD) {
            value = left.solve() + right.solve();
        } else if (operator == Operator.SUBTRACT) {
            value = left.solve() - right.solve();
        } else if (operator == Operator.MULTIPLY) {
            value = left.solve() * right.solve();
        } else if (operator == Operator.DIVIDE) {
            value = left.solve() / right.solve();
        } else {
            throw new IllegalStateException("Unknown operator "+operator);
        }
    }

    @Override
    public Long solve() {
        return value;
    }

    @Override
    public String expressAsString() {
        if (operator == Operator.ADD) {
            return String.format("(%s) + (%s)", left.expressAsString(), right.expressAsString());
        } else if (operator == Operator.SUBTRACT) {
            return String.format("(%s) - (%s)", left.expressAsString(), right.expressAsString());
        } else if (operator == Operator.MULTIPLY) {
            return String.format("(%s) * (%s)", left.expressAsString(), right.expressAsString());
        } else if (operator == Operator.DIVIDE) {
            return String.format("(%s) / (%s)", left.expressAsString(), right.expressAsString());
        } else {
            throw new IllegalStateException("Unknown operator "+operator);
        }
    }
}
