package com.temenos.t24.tools.eclipse.basic.views.callsHierarchy;

/**
 * A POJO represents a line in a T24 Subroutine which makes a call to another
 * T24 Subroutine.
 * 
 * @author ssethupathi
 * 
 */
public class CallLineDetail {

    private int lineNo;
    private String fullLine;

    public CallLineDetail(int lineNo, String fullLine) {
        this.lineNo = lineNo;
        this.fullLine = fullLine;
    }

    /**
     * Returns the line number
     * 
     * @return line number
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Returns the line of call
     * 
     * @return line
     */
    public String getFullLine() {
        return fullLine;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        if (lineNo < 0) {
            return null;
        }
        return lineNo + " " + fullLine.trim();
    }
}
