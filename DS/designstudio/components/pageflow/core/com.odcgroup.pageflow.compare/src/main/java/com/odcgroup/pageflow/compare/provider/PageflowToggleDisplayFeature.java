package com.odcgroup.pageflow.compare.provider;

import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class PageflowToggleDisplayFeature {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.util.IToggleDisplayFeature#getDisplayFeature()
	 */
	public static String getDisplayFeature() {
		boolean showName = getPreferenceStore().getBoolean(PageflowPreferenceConstants.PREF_SHOWID_LABEL);
		if (showName) {
			return "name";
		} else {
			return "displayName";
		}
	}
	
	/**
	 * @return
	 */
	private static IPreferenceStore getPreferenceStore() {
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

}
