package uk.org.djce.countdown.resultsformatter;

public class NumberOrOperator {
    private final Long number;
    private final Operator operator;

    public NumberOrOperator(final Long number) {
        this.number = number;
        this.operator = null;
    }

    public NumberOrOperator(final Operator operator) {
        this.number = null;
        this.operator = operator;
    }

    public boolean isOperator() {
        return(this.operator != null);
    }

    public long getNumber() {
        return this.number;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public String toString() {
        return this.number != null ? this.number.toString() : this.operator.toString();
    }
}
