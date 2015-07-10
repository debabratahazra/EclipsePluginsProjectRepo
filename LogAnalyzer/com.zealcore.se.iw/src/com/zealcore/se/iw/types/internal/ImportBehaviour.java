package com.zealcore.se.iw.types.internal;

public final class ImportBehaviour {

    private boolean skipEmptyLines;

    private int noOfHeaderLines;

    public ImportBehaviour(final boolean skipEmptyLines,
            final int noOfHeaderLines) {
        this.skipEmptyLines = skipEmptyLines;
        this.noOfHeaderLines = noOfHeaderLines;
    }

    public boolean getSkipEmptyLines() {
        return this.skipEmptyLines;
    }

    public void setSkipEmptyLines(final boolean skipEmptyLines) {
        this.skipEmptyLines = skipEmptyLines;
    }

    public int getNoOfHeaderLines() {
        return this.noOfHeaderLines;
    }

    public void setNoOfHeaderLines(final int noOfHeaderLines) {
        this.noOfHeaderLines = noOfHeaderLines;
    }
}
