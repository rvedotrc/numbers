package uk.org.djce.countdown.resultsformatter;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class NumbersResultsProcessorTest {

    @Mock private BufferedReader bufferedReader;
    @Mock private LeafLineParser leafLineParser;
    NumbersResultsProcessor underTest;

    @Before
    public void before() {
        underTest = new NumbersResultsProcessor(bufferedReader, leafLineParser);
    }

    @Test
    public void itShouldReadLinesAndPassThemToTheLeafLineParser() throws Exception {
        when(bufferedReader.readLine())
                .thenReturn("line 1")
                .thenReturn("line 2")
                .thenReturn(null);

        underTest.run();

        InOrder inOrder = inOrder(leafLineParser);
        inOrder.verify(leafLineParser).processLeafLine("line 1");
        inOrder.verify(leafLineParser).processLeafLine("line 2");
        verifyNoMoreInteractions(leafLineParser);
    }

}
