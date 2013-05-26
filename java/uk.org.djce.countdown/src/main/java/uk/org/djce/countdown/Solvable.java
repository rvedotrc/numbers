package uk.org.djce.countdown;

public interface Solvable {

    static enum Operator { ADD, SUBTRACT, MULTIPLY, DIVIDE }

    Long solve();

    String expressAsString();

}
