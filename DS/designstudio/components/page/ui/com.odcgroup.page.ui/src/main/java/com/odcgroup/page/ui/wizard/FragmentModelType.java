package com.odcgroup.page.ui.wizard;

/**
 * Defines the different types of fragment the use can create using fragment
 * wizards
 * 
 * @author atr
 * @since DS 1.40.0
 */
public enum FragmentModelType {

	/** The fragment describes a layer */
	LAYER,

	/** The fragment contains an horizontal box as root widget */
	HORIZONTAL,

	/** The fragment contains a vertical box as root widget */
	VERTICAL,
   
	/** The fragment contains a Xtooltip box as root widget */
	XTOOLTIP,
	/** The fragment type is Regular type */
	REGULAR
}
