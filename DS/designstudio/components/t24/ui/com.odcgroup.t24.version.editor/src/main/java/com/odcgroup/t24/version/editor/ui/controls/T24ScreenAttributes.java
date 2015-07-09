package com.odcgroup.t24.version.editor.ui.controls;

/**Screen Attributes 
 * @author snn
 *
 */
public enum T24ScreenAttributes {
    NO_HEADER_TAB("NO.HEADER.TAB"), 
    SHOW_NULL_FIELDS("SHOW.NULL.FIELDS"), 
    NO_COMBO_BOX("NO.COMBO.BOX"),
    SHOW_TXN_ID("SHOW.TXN.ID"), 
    SHOW_NULL_REKEYS("SHOW.NULL.REKEYS"),
    CUSTOM_VERSION_ALIGN("CUSTOM.VERSION.ALIGN"),
    FIXED_LENGTH_FILE("FIXED.LENGTH.FILE"),
    ENABLE_ALL_FIELDS("ENABLE.ALL.FIELDS");
    
    private String t24attrName;
    
    private T24ScreenAttributes(String attributeName) {
	this.t24attrName = attributeName;
    }
    public String toString() {
	return t24attrName;
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
