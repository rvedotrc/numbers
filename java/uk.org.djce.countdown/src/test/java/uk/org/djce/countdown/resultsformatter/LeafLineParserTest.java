package uk.org.djce.countdown.resultsformatter;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LeafLineParserTest {

    @Mock private LeafProcessor leafProcessor;
    private LeafLineParser underTest;

    @Before
    public void before() {
        underTest = new LeafLineParser(leafProcessor);
    }

    @Test
    public void itShouldIgnoreNonLeafLines() throws Exception {
        underTest.processLeafLine("not a leaf line");
        verifyNoMoreInteractions(leafProcessor);
    }

    @Test
    public void itShouldParseLeafLines() throws Exception {
        final String input = "leaf = nums=0 () ops=11 (8 1 5 8 5 7 + * - - *) stack=1 (720)";

        underTest.processLeafLine(input);

        ArgumentCaptor<List<NumberOrOperator>> captor = GenericCaptors.listCapture();
        verify(leafProcessor, times(1)).process(eq(Collections.EMPTY_LIST), captor.capture(), eq(720L));
    }

    @Test
    public void itShouldParseLeafLinesWithNumbers() throws Exception {
        final String input = "leaf = nums=2 (4 7) ops=11 (8 1 5 8 5 7 + * - - *) stack=1 (720)";

        underTest.processLeafLine(input);

        ArgumentCaptor<List<NumberOrOperator>> captor = GenericCaptors.listCapture();
        verify(leafProcessor, times(1)).process(
                eq(ImmutableList.of(4L, 7L)),
                captor.capture(),
                eq(720L)
        );
    }

    @Test
    public void itShouldParseLeafLinesWithOperators() throws Exception {
        final String input = "leaf = nums=0 () ops=11 (8 1 5 8 5 7 + * - - *) stack=1 (720)";

        underTest.processLeafLine(input);

        ArgumentCaptor<List<NumberOrOperator>> captor = GenericCaptors.listCapture();
        verify(leafProcessor, times(1)).process(
                eq(Collections.EMPTY_LIST),
                captor.capture(),
                eq(720L)
        );

        assertEquals(11, captor.getValue().size());
        assertEquals(8L, captor.getValue().get(0).getNumber().longValue());
        assertEquals(Operator.MULTIPLY, captor.getValue().get(10).getOperator());
    }

}
