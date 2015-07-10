package com.zealcore.srl.offline;

public class LinearMessage extends AbstractMessage {

    static final String ZEROS = "00000000";

    @Override
    public String toString() {
        final StringBuilder output = new StringBuilder();
        final int pos = getData().position();
        getData().rewind();
        while (getData().hasRemaining()) {
            output.append("  "
                    + padLeft(Integer.toHexString(getData().getInt())
                            .toUpperCase()));
        }
        getData().position(pos);
        return output.toString();
    }

    private String padLeft(final String str) {
        return ZEROS.substring(str.length()) + str;
    }
}
