/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.process.diagram.custom.preferences.ProcessPreferenceConstants;
import com.odcgroup.process.diagram.custom.util.TaskSize;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;

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
		getPreferenceStore().setDefault(ProcessPreferenceConstants.PREF_TRN_LINE_STYLE,
				Routing.RECTILINEAR_LITERAL.getName());
		getPreferenceStore().setDefault(ProcessPreferenceConstants.PREF_TRN_ROUTE_SHORTEST, false);
		getPreferenceStore().setDefault(ProcessPreferenceConstants.PREF_TRN_ROUTE_SMOOTH_FACTOR,
				Smoothness.NONE_LITERAL.getName());

		getPreferenceStore().setDefault(ProcessPreferenceConstants.PREF_TASK_SIZE,
				TaskSize.TASK_SIZE_BIG_LITERAL.getName());
	}

	/**
	 * 
	 */
	protected void setColorPreferences() {
		PreferenceConverter.setDefault(getPreferenceStore(), ProcessPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR,
				new RGB(242, 132, 17));
		PreferenceConverter.setDefault(getPreferenceStore(), ProcessPreferenceConstants.PREF_TRN_LINE_FILL_COLOR,
				ColorConstants.black.getRGB());
		PreferenceConverter.setDefault(getPreferenceStore(), ProcessPreferenceConstants.PREF_POOL_SHAPE_FILL_COLOR,
				new RGB(236, 222, 187));
		PreferenceConverter.setDefault(getPreferenceStore(),
				ProcessPreferenceConstants.PREF_MANUALACTIVITY_SHAPE_FILL_COLOR, new RGB(248, 222, 110));
		PreferenceConverter.setDefault(getPreferenceStore(),
				ProcessPreferenceConstants.PREF_SERVICEACTIVITY_SHAPE_FILL_COLOR, new RGB(163, 168, 107));
		PreferenceConverter.setDefault(getPreferenceStore(), ProcessPreferenceConstants.PREF_PROCESS_SHAPE_LINE_COLOR,
				ColorConstants.black.getRGB());
	}

	/**
	 * @generated
	 */
	protected IPreferenceStore getPreferenceStore() {
		return ProcessDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
}
