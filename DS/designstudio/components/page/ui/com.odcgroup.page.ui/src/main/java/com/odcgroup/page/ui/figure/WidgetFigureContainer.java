package com.odcgroup.page.ui.figure;

import java.util.List;

/**
 * A WidgetFigureContainer is a Figure which is made up of other Widgets. It has been
 * specifically created to address the following problem. In CDM we have a tag
 * called <cdmcomp:generic>. This tag may be transformed by OCS into two xgui Widgets
 * Label and TextField (or ComboBox or TextArea). Thus in OCS the following two
 * XML fragments are identical:
 * 
 * <xgui:hbox>
 * 	<xgui:label></xgui:label>
 *  <xgui:textfield></xgui:textfield>
 * </xgui:hbox>
 * 
 * <xgui:hbox>
 * 	<cdmcomp:generic label="true"></cdmcomp:generic> <!-- Generates a Label and a TextField -->
 * </xgui:hbox>
 * 
 * Niavely we could imagine adding a Label and TextField as a child of the GenericFigure.
 * Unfortunately, in this case these children would simply be drawn one after another. In
 * other words, the LayoutManager of the Horizontal / VerticalBox which contains the
 * GenericFigure would not be taken into account. This is contrary how OCS works.
 * 
 * The idea behind the WidgetFigureContainer is that is supplies to the LayoutManagers a means
 * of getting the contained Widgets (Label + TextField) so that it can set their Bounds correctly.
 *  
 * @author Gary Hayes
 */
public interface WidgetFigureContainer {
	
	/**
	 * Gets the WidgetFigures. These are the child Widgets which need to be laid out.
	 * 
	 * @return List of WidgetFigures
	 */
	public List getWidgetFigures();
	
}
