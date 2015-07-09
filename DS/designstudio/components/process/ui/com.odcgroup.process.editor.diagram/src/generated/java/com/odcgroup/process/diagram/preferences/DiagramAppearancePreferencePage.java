/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.preferences;

import org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.process.diagram.custom.preferences.ProcessPreferenceConstants;
import com.odcgroup.process.diagram.custom.util.TaskSize;
import com.odcgroup.process.diagram.part.Messages;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramAppearancePreferencePage extends AppearancePreferencePage {

	private static String PREF_POOLREF_SHAPE_FILL_COLOR = ProcessPreferenceConstants.PREF_POOL_SHAPE_FILL_COLOR;
	private static String PREF_MANUALACTIVITY_SHAPE_FILL_COLOR = ProcessPreferenceConstants.PREF_MANUALACTIVITY_SHAPE_FILL_COLOR;
	private static String PREF_SERVICEACTIVITY_SHAPE_FILL_COLOR = ProcessPreferenceConstants.PREF_SERVICEACTIVITY_SHAPE_FILL_COLOR;

	private static String PREF_PROCESS_TASK_SIZE = ProcessPreferenceConstants.PREF_TASK_SIZE;

	/**
	 * @generated
	 */
	public DiagramAppearancePreferencePage() {
		setPreferenceStore(ProcessDiagramEditorPlugin.getInstance().getPreferenceStore());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage#addFontAndColorFields(org.eclipse.swt.widgets.Composite)
	 */
	protected void addFontAndColorFields(Composite composite) {
		Composite colorFields = new Composite(composite, 2);
		addField(PREF_POOLREF_SHAPE_FILL_COLOR, "Pool background color", colorFields);
		addField(PREF_MANUALACTIVITY_SHAPE_FILL_COLOR, "Manual Acitivity background color", colorFields);
		addField(PREF_SERVICEACTIVITY_SHAPE_FILL_COLOR, "Service Activity background color", colorFields);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage#addFields(org.eclipse.swt.widgets.Composite)
	 */
	protected void addFields(Composite parent) {
		Composite main = createPageLayout(parent);
		createFontAndColorGroup(main);
		createTasksSizeGroup(main);
	}

	/**
	 * @param name
	 * @param labelText
	 * @param parent
	 */
	private void addField(String name, String labelText, Composite parent) {
		addField(((org.eclipse.jface.preference.FieldEditor) (new ColorFieldEditor(name, labelText, parent))));
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Composite createTasksSizeGroup(Composite parent) {

		Composite group = new Composite(parent, 0);
		RadioGroupFieldEditor labelContentEditor = new RadioGroupFieldEditor(PREF_PROCESS_TASK_SIZE,
				Messages.StateSizeGroupName, 3, new String[][] {
						new String[] { TaskSize.TASK_SIZE_SMALL_LITERAL.getLiteral(),
								TaskSize.TASK_SIZE_SMALL_LITERAL.getName() },
						new String[] { TaskSize.TASK_SIZE_MEDIUM_LITERAL.getLiteral(),
								TaskSize.TASK_SIZE_MEDIUM_LITERAL.getName() },
						new String[] { TaskSize.TASK_SIZE_BIG_LITERAL.getLiteral(),
								TaskSize.TASK_SIZE_BIG_LITERAL.getName() } }, group, true);
		labelContentEditor.setPreferenceStore(super.getPreferenceStore());
		super.addField(labelContentEditor);
		return group;
	}
}
