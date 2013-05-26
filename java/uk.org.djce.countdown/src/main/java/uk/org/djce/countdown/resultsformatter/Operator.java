package uk.org.djce.countdown.resultsformatter;

public enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String op;

    Operator(final String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    public static Operator findByOp(final String op) {
        for (Operator operator : Operator.values()) {
            if (operator.op.equals(op)) return operator;
        }
        throw new IllegalArgumentException();
    }
}

