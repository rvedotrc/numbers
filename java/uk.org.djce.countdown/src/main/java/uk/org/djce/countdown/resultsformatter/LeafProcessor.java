package uk.org.djce.countdown.resultsformatter;

import java.util.List;

public class LeafProcessor {

    private final OpTreeBuilder opTreeBuilder;

    public LeafProcessor(OpTreeBuilder opTreeBuilder) {
        this.opTreeBuilder = opTreeBuilder;
    }

    public void process(List<Long>nums, List<NumberOrOperator> ops, Long value) {
        try {
            OpTree opTree = opTreeBuilder.build(ops);
        } catch (OpTreeBuilder.BuildException e) {
            throw new RuntimeException(e);
        }
    }

}
