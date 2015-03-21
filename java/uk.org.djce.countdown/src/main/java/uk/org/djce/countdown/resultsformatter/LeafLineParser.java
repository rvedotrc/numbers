package uk.org.djce.countdown.resultsformatter;

import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeafLineParser {

    private static final String LEAF_PATTERN = "^leaf = nums=\\d+ \\((.*?)\\) ops=\\d+ \\((.*?)\\) stack=1 \\((\\d+)\\)$";
    private static final int NUMS_GROUP = 1;
    private static final int OPS_GROUP = 2;
    private static final int VALUE_GROUP = 3;
    
    private final LeafProcessor leafProcessor;

    public LeafLineParser(LeafProcessor leafProcessor) {
        this.leafProcessor = leafProcessor;
    }

    public boolean processLeafLine(final String line) {
        // e.g. leaf = nums=0 () ops=11 (8 1 5 8 5 7 + * - - *) stack=1 (720)
        Pattern p = Pattern.compile(LEAF_PATTERN);
        Matcher m = p.matcher(line);

        if (!m.matches()) {
            return false;
        }

        List<Long> nums = nums(Splitter.on(" ").omitEmptyStrings().split(m.group(NUMS_GROUP)));
        System.out.println("nums = " + nums);

        List<NumberOrOperator> ops = ops(Splitter.on(" ").omitEmptyStrings().split(m.group(OPS_GROUP)));
        System.out.println("ops = " + ops);

        Long value = Long.parseLong(m.group(VALUE_GROUP));
        System.out.println("value = " + value);

        leafProcessor.process(nums, ops, value);

        return true;
    }

    private static List<Long> nums(Iterable<String> in) {
        List<Long> out = new ArrayList<Long>();
        for (String n : in) {
            out.add(Long.parseLong(n));
        }
        return out;
    }

    private static List<NumberOrOperator> ops(Iterable<String> in) {
        List<NumberOrOperator> out = new ArrayList<NumberOrOperator>();
        for (String n : in) {
            try {
                Long num = Long.parseLong(n);
                out.add(new NumberOrOperator(num));
            } catch (NumberFormatException e) {
                Operator operator = Operator.findByOp(n);
                out.add(new NumberOrOperator(operator));
            }
        }
        return out;
    }


}