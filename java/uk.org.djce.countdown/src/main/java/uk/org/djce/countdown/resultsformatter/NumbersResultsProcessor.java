package uk.org.djce.countdown.resultsformatter;

import java.io.BufferedReader;
import java.io.IOException;

public class NumbersResultsProcessor {

    private final BufferedReader bufferedReader;
    private final LeafLineParser leafLineParser;

    public NumbersResultsProcessor(BufferedReader bufferedReader, LeafLineParser leafLineParser) {
        this.bufferedReader = bufferedReader;
        this.leafLineParser = leafLineParser;
    }

    public void run() throws IOException {
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }

            // e.g. leaf = nums=0 () ops=11 (8 1 5 8 5 7 + * - - *) stack=1 (720)
            leafLineParser.processLeafLine(line);
        }
    }

}
