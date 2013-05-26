package uk.org.djce.countdown.resultsformatter;

import java.util.ArrayList;
import java.util.List;

public class OpTreeBuilder {
    private List<OpTree> opTrees;

    public OpTree build(List<NumberOrOperator> ops) throws BuildException {

        opTrees = new ArrayList<OpTree>();

        for (NumberOrOperator numberOrOperator : ops) {
            if (numberOrOperator.isOperator()) {
                processOperator(numberOrOperator.getOperator());
            } else {
                opTrees.add(new NumericNode(numberOrOperator.getNumber()));
            }
        }

        if (opTrees.isEmpty()) {
            throw new NoResultsException();
        }

        if (opTrees.size() > 1) {
            throw new TooManyResultsException();
        }

        return opTrees.get(0);
    }

    private void processOperator(Operator operator) throws StackUnderrunException, IllegalValueException {
        if (opTrees.size() < 2) {
            throw new StackUnderrunException();
        }

        OpTree left = opTrees.remove(opTrees.size() - 1);
        OpTree right = opTrees.remove(opTrees.size() - 1);

        if (!operator.areArgsAllowable(left.value(), right.value())) {
            throw new IllegalValueException();
        }

        OpTree op = new BinopNode(left, operator, right);
        opTrees.add(op);
    }

    public class BuildException extends Exception { }
    public class NoResultsException extends BuildException { }
    public class TooManyResultsException extends BuildException { }
    public class StackUnderrunException extends BuildException { }
    public class IllegalValueException extends BuildException { }
}
