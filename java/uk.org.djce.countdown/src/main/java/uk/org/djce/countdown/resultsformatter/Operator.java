package uk.org.djce.countdown.resultsformatter;

public enum Operator {
    ADD("+") {
        @Override
        public boolean areArgsAllowable(long left, long right) {
            return true;
        }

        @Override
        public long evaluate(long left, long right) {
            return left + right;
        }
    },
    SUBTRACT("-") {
        @Override
        public boolean areArgsAllowable(long left, long right) {
            return left > right;
        }

        @Override
        public long evaluate(long left, long right) {
            return left - right;
        }
    },
    MULTIPLY("*") {
        @Override
        public boolean areArgsAllowable(long left, long right) {
            return true;
        }

        @Override
        public long evaluate(long left, long right) {
            return left * right;
        }
    },
    DIVIDE("/") {
        @Override
        public boolean areArgsAllowable(long left, long right) {
            return((left % right)==0);
        }

        @Override
        public long evaluate(long left, long right) {
            return left / right;
        }
    };

    private final String op;

    Operator(final String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    public static Operator findByOp(final String op) {
        for (Operator operator : Operator.values()) {
            if (operator.op.equals(op)) {
                return operator;
            }
        }

        throw new IllegalArgumentException();
    }

    public String toString() {
        return op;
    }

    public abstract boolean areArgsAllowable(long left, long right);
    public abstract long evaluate(long left, long right);
}

