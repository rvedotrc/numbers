package uk.org.djce.countdown.resultsformatter;

public class BinopNode implements OpTree {

    private final OpTree left;
    private final Operator operator;
    private final OpTree right;
    private final long value;

    public BinopNode(OpTree left, Operator operator, OpTree right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
        value = operator.evaluate(left.value(), right.value());
    }

    public OpTree getLeft() {
        return left;
    }

    public Operator getOperator() {
        return operator;
    }

    public OpTree getRight() {
        return right;
    }

    @Override
    public long value() {
        return value;
    }

}
