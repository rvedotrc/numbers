package uk.org.djce.countdown.resultsformatter;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LeafProcessorTest {

    @Mock private OpTreeBuilder opTreeBuilder;

    private List<Long> nums;
    private List<NumberOrOperator> ops;
    private Long value;

    private LeafProcessor underTest;

    @Before
    public void before() {
        nums = ImmutableList.of(6L);

        ops = ImmutableList.of(
                new NumberOrOperator(7L),
                new NumberOrOperator(3L),
                new NumberOrOperator(Operator.MULTIPLY)
        );

        value = 21L;

        underTest = new LeafProcessor(opTreeBuilder);
    }

    @Test
    public void itShouldBuildAnOpTree() throws Exception {
        underTest.process(nums, ops, value);
        Mockito.verify(opTreeBuilder).build(ops);
    }

}
