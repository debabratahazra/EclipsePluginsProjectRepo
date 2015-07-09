package com.odcgroup.t24.version.editor.ui.controls;

/**Field Attributes.
 * @author snn
 *
 */
public enum T24FieldAttributes{
    POPUP_DROPDOWN("POPUP.DROPDOWN"), 
    NO_BROWSER_TEXT("NO.BROWSER.TEXT"), 
    AMNUM("CLASS-AMNUM"), 
    BOLD("CLASS-BOLD"), 
    BOLD_ITALIC("CLASS-BOLD.ITALIC"), 
    BOLD_RED("CLASS-BOLD.RED"), 
    COS_HEADING("CLASS-COS.HEADING"), 
    COS_HEADING_DATA_LABEL("CLASS-COS.HEADING.DATA.LABEL"), 
    COS_HEADING_DATA_VALUE("CLASS-COS.HEADING.DATA.VALUE") ,
    COS_SUBHEADING ("CLASS-COS.SUBHEADING"), 
    DEMO("CLASS-DEMO") ,
    DESCR("CLASS-DESCR") ,
    ENQ_AA_BAL_TYPE("CLASS-ENQ.AA.BAL.TYPE"), 
    ENQ_COLUMN_LABEL("CLASS-ENQ.COLUMN.LABEL"), 
    ENQ_DATA_ID("CLASS-ENQ.DATA.ID"), 
    ENQ_DATA_LABEL("CLASS-ENQ.DATA.LABEL"),
    ENQ_DATA_VALUE("CLASS-ENQ.DATA.VALUE") ,
    ENQ_H_DATA("CLASS-ENQ.H.DATA"), 
    ENQ_H_DATA_LABEL("CLASS-ENQ.H.DATA.LABEL"), 
    ENQ_H_ID("CLASS-ENQ.H.ID"), 
    ENQ_H_TITLE("CLASS-ENQ.H.TITLE"), 
    ENQ_ID_LABEL("CLASS-ENQ.ID.LABEL"), 
    ENQ_NORECS("CLASS-ENQ.NORECS"), 
    FDCT("CLASS-FDCT"), 
    FDLB("CLASS-FDLB"), 
    HEADER("CLASS-HEADER"), 
    HIDDEN_DATA("CLASS-HIDDEN-DATA"), 
    ITALIC("CLASS-ITALIC"), 
    MODELSTATUS("CLASS-MODELSTATUS"), 
    POSNEG("CLASS-POSNEG"), 
    RATING("CLASS-RATING"), 
    TAMNUM("CLASS-TAMNUM"), 
    TITLE("CLASS-TITLE"); 
     
    private String t24FieldAttributeName;

    private T24FieldAttributes(String attributeName) {
	this.t24FieldAttributeName = attributeName;
    }

    @Override
    public String toString() {
	return t24FieldAttributeName;
    }
    /**Get the Values as String Array.
     * @return String array
     */
    public static String[] getValues() {
	String[] values = new String[values().length];
	for (int i = 0; i <=values().length-1; i++) {
	    values[i] = values()[i].toString();
	}
	return values;
    }
}

