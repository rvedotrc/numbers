package uk.org.djce.countdown;

public class BinopNode implements Solvable {
    private final Operator operator;
    private final Solvable left;
    private final Solvable right;

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
    }

    @Override
    public Long solve() {
        if (operator == Operator.ADD) {
            return left.solve() + right.solve();
        } else if (operator == Operator.SUBTRACT) {
            return left.solve() - right.solve();
        } else if (operator == Operator.MULTIPLY) {
            return left.solve() * right.solve();
        } else if (operator == Operator.DIVIDE) {
            return left.solve() / right.solve();
        }

        throw new IllegalStateException("Unknown operator "+operator);
    }
}
