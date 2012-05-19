package uk.org.djce.countdown;

import sun.plugin.dom.exception.InvalidStateException;

public interface Solvable {

    public static enum Operator { ADD, SUBTRACT, MULTIPLY, DIVIDE };

    public Long solve();

}
