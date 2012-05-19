package uk.org.djce.countdown;

public interface Solvable {

    public static enum Operator { ADD, SUBTRACT, MULTIPLY, DIVIDE };

    public Long solve();

    public String expressAsString();

}
