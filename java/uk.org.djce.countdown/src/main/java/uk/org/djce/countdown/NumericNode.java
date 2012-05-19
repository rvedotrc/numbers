package uk.org.djce.countdown;

public class NumericNode implements Solvable {
    private final Long value;

    NumericNode(Long value) {
        this.value = value;
    }

    @Override
    public Long solve() {
        return value;
    }
}
