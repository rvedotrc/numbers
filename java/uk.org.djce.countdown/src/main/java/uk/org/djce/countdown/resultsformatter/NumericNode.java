package uk.org.djce.countdown.resultsformatter;

public class NumericNode implements OpTree {

    private final long value;

    public NumericNode(long value) {
        this.value = value;
    }

    @Override
    public long value() {
        return value;
    }

}
