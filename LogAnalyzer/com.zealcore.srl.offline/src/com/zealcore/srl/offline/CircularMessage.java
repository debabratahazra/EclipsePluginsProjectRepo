package com.zealcore.srl.offline;

public class CircularMessage extends AbstractMessage {
    private long ts;

    public long getTs() {
        return ts;
    }

    public void setTs(final long ts) {
        this.ts = ts;
    }

    static final String ZEROS = "00000000";

    @Override
    public String toString() {
        final StringBuilder output = new StringBuilder();
        final int pos = getData().position();
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
