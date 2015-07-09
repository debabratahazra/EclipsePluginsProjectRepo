/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.pageflow.editor.diagram.custom.util.ActionLabel;
import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.custom.util.StateSize;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * @generated NOT
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = getPreferenceStore();
		DiagramPrintingPreferencePage.initDefaults(store);
		DiagramGeneralPreferencePage.initDefaults(store);
		DiagramAppearancePreferencePage.initDefaults(store);
		DiagramConnectionsPreferencePage.initDefaults(store);
		DiagramRulersAndGridPreferencePage.initDefaults(store);
		setColorPreferences();
		getPreferenceStore().setDefault(IPreferenceConstants.PREF_SHOW_CONNECTION_HANDLES, false);
		getPreferenceStore().setDefault(IPreferenceConstants.PREF_SHOW_GRID, true);
		getPreferenceStore().setDefault(IPreferenceConstants.PREF_GRID_SPACING, "0.25");
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_TRN_LINE_STYLE,
				Routing.MANUAL_LITERAL.getName());
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_TRN_ROUTE_SHORTEST, false);
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_TRN_ROUTE_SMOOTH_FACTOR,
				Smoothness.NONE_LITERAL.getName());
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_TRN_LABEL_ACTION_CONTENT,
				ActionLabel.ACTION_NONE_LITERAL.getName());
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_PAGEFLOW_ERROR_URL,
				PageflowPreferenceConstants.DEF_PAGEFLOW_ERROR_URL);
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_PAGEFLOW_ABORT_CLASS,
				PageflowPreferenceConstants.DEF_PAGEFLOW_ABORT_CLASS);
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_DESC_LABEL_DISPLAY, true);
		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_SHOWID_LABEL, false);

		getPreferenceStore().setDefault(PageflowPreferenceConstants.PREF_STATE_SIZE,
				StateSize.STATE_SIZE_BIG_LITERAL.getName());
	}

	/**
	 * 
	 */
	protected void setColorPreferences() {
		PreferenceConverter.setDefault(getPreferenceStore(), PageflowPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR,
				new RGB(242, 132, 17));
		PreferenceConverter.setDefault(getPreferenceStore(), PageflowPreferenceConstants.PREF_TRN_LINE_FILL_COLOR,
				ColorConstants.black.getRGB());
		PreferenceConverter.setDefault(getPreferenceStore(), PageflowPreferenceConstants.PREF_VIEW_STATE_FILL_COLOR,
				new RGB(248, 228, 152));
		PreferenceConverter.setDefault(getPreferenceStore(),
				PageflowPreferenceConstants.PREF_SUBPAGEFLOW_STATE_FILL_COLOR, new RGB(166, 188, 198));
		PreferenceConverter.setDefault(getPreferenceStore(),
				PageflowPreferenceConstants.PREF_PAGEFLOW_SHAPE_LINE_COLOR, ColorConstants.black.getRGB());
	}

	/**
	 * @generated
	 */
	protected IPreferenceStore getPreferenceStore() {
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
}
