package com.zealcore.se.core.ifw.assertions.internal;

public final class SearchMatcher {


    private static final String GREATER_THAN_EQUAL_TO_OPERAND = ">=";


    private static final String LESS_THAN_OPERAND = "<";


    private SearchMatcher() {

    }

    /**
     * Strings the match. Tries to find a needle in a haystack using case
     * insenstive regular expression.
     * 
     * Ie. needle = "foo.*|bar.*" and haystack ="FoOBaR" will return true.
     * 
     * @param haystack
     *            the haystack
     * @param needle
     *            the needle
     * 
     * @return true, if string match
     */
    public static boolean match(final String needle, final String haystack, boolean wildcard) {
        //final String operand = needle;
        final String ignoreCaseModifier = "(?i)";
        
        final String operand = makeValidOperand(needle);
        if (!operand.startsWith(ignoreCaseModifier)) {
            final String regexp = ignoreCaseModifier + operand;
            return (wildcard ^ (haystack.matches(regexp)));
        }
        return (wildcard ^ (haystack.matches(operand)));
    }

    private static String makeValidOperand(final String needle) {
        String operand = needle;
        try {
            if ((operand.startsWith(".") && (!operand.startsWith(".*") && !operand
                    .startsWith(".?")))) {
                operand = "\\." + operand.substring(1);
            }
            if (operand.endsWith(".")
                    && (!operand.endsWith(".*") && !operand.endsWith(".?"))) {
                operand = operand.substring(0, operand.length() - 1) + "\\.";
            }
            if ((operand.startsWith("*") && (!operand.startsWith(".*")))) {
                operand = ".*" + operand.substring(1);
            }
            if (operand.endsWith("*") && (!operand.endsWith(".*"))) {
                operand = operand.substring(0, operand.length() - 1) + ".*";
            }
            if (operand.contains("?")) {
                operand = operand.replace(".?", "?");
                operand = operand.replace("?", ".?");
            }
        } catch (Exception e) {}
        return operand;
    }


    public static boolean match(final Number value, final Number val1,
            final Number val2, boolean wildcard) {

        /*
         * if a == Long, use b.longValue => compare long else use a.doubleValue
         * b.doubleValue => compare doubles.
         */

        int firstCompare, secondCompare;

        if (value.getClass() == Long.class 
                || value.getClass() == Short.class 
                || value.getClass() == Integer.class) {
            final Long left = value.longValue();
            final Long right = val1.longValue();
            firstCompare = left.compareTo(right);
        } else {
            final Double left = value.doubleValue();
            final Double right = val1.doubleValue();
            firstCompare = left.compareTo(right);
        }

        if (value.getClass() == Long.class
                || value.getClass() == Short.class 
                || value.getClass() == Integer.class) {
            final Long left = value.longValue();
            final Long right = val2.longValue();
            secondCompare = left.compareTo(right);
        } else {
            final Double left = value.doubleValue();
            final Double right = val2.doubleValue();
            secondCompare = left.compareTo(right);
        }

        return ((firstCompare >= 0 & secondCompare < 0) ^ wildcard);

    }


    public static boolean match(final Number value, final Number val,
            final String operator1, boolean wildcard) {

        int i;

        if (value.getClass() == Long.class
                || value.getClass() == Short.class 
                || value.getClass() == Integer.class) {
            final Long left = value.longValue();
            final Long right = val.longValue();
            i = left.compareTo(right);
        } else {
            final Double left = value.doubleValue();
            final Double right = val.doubleValue();

            i = left.compareTo(right);

        }

        if (operator1.equals(SearchMatcher.LESS_THAN_OPERAND)) {
            return (i < 0 ^ wildcard);
        }

        if (operator1.equals(SearchMatcher.GREATER_THAN_EQUAL_TO_OPERAND)) {
            return (i >= 0 ^ wildcard);
        }


        return false;
    }
}
