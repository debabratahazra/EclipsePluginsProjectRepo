/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.preferences;

import org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.custom.util.StateSize;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramAppearancePreferencePage extends AppearancePreferencePage {

	private static String PREF_VIEW_STATE_FILL_COLOR = PageflowPreferenceConstants.PREF_VIEW_STATE_FILL_COLOR;
	private static String PREF_SUBPAGEFLOW_STATE_FILL_COLOR = PageflowPreferenceConstants.PREF_SUBPAGEFLOW_STATE_FILL_COLOR;
	private static String PREF_PAGEFLOW_SHAPE_LINE_COLOR = PageflowPreferenceConstants.PREF_PAGEFLOW_SHAPE_LINE_COLOR;

	private static String PREF_PAGEFLOW_STATE_SIZE = PageflowPreferenceConstants.PREF_STATE_SIZE;

	/**
	 * @generated
	 */
	public DiagramAppearancePreferencePage() {
		setPreferenceStore(PageflowDiagramEditorPlugin.getInstance().getPreferenceStore());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage#addFields(org.eclipse.swt.widgets.Composite)
	 */
	protected void addFields(Composite parent) {
		Composite main = createPageLayout(parent);
		createFontAndColorGroup(main);
		createStateSizeGroup(main);
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Composite createStateSizeGroup(Composite parent) {

		Composite group = new Composite(parent, 0);
		RadioGroupFieldEditor labelContentEditor = new RadioGroupFieldEditor(PREF_PAGEFLOW_STATE_SIZE,
				Messages.StateSizeGroupName, 3, new String[][] {
						new String[] { StateSize.STATE_SIZE_SMALL_LITERAL.getLiteral(),
								StateSize.STATE_SIZE_SMALL_LITERAL.getName() },
						new String[] { StateSize.STATE_SIZE_MEDIUM_LITERAL.getLiteral(),
								StateSize.STATE_SIZE_MEDIUM_LITERAL.getName() },
						new String[] { StateSize.STATE_SIZE_BIG_LITERAL.getLiteral(),
								StateSize.STATE_SIZE_BIG_LITERAL.getName() } }, group, true);
		labelContentEditor.setPreferenceStore(super.getPreferenceStore());
		super.addField(labelContentEditor);
		return group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage#addFontAndColorFields(org.eclipse.swt.widgets.Composite)
	 */
	protected void addFontAndColorFields(Composite composite) {
		Composite colorFields = new Composite(composite, 2);
		addField(PREF_VIEW_STATE_FILL_COLOR, "View State background color", colorFields);
		addField(PREF_SUBPAGEFLOW_STATE_FILL_COLOR, "Sub-Pageflow State background color", colorFields);
		addField(PREF_PAGEFLOW_SHAPE_LINE_COLOR, "Pageflow Shapes border color", colorFields);
	}

	/**
	 * @param name
	 * @param labelText
	 * @param parent
	 */
	private void addField(String name, String labelText, Composite parent) {
		addField(((org.eclipse.jface.preference.FieldEditor) (new ColorFieldEditor(name, labelText, parent))));
	}
}
