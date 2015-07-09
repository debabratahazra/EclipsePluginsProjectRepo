/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.preferences;

import org.eclipse.gmf.runtime.diagram.ui.preferences.ConnectionsPreferencePage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.odcgroup.pageflow.editor.diagram.custom.util.ActionLabel;
import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramConnectionsPreferencePage extends ConnectionsPreferencePage {

	private static String PREF_TRN_LINE_STYLE = PageflowPreferenceConstants.PREF_TRN_LINE_STYLE;
	private static String PREF_TRN_ROUTE_SHORTEST = PageflowPreferenceConstants.PREF_TRN_ROUTE_SHORTEST;
	private static String PREF_TRN_ROUTE_SMOOTH_FACTOR = PageflowPreferenceConstants.PREF_TRN_ROUTE_SMOOTH_FACTOR;
	private static String PREF_TRN_LINE_FILL_COLOR = PageflowPreferenceConstants.PREF_TRN_LINE_FILL_COLOR;
	private static String PREF_TRN_LINE_HIGHLIGHT_COLOR = PageflowPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR;

	private static String PREF_TRN_LABEL_ACTION_CONTENT = PageflowPreferenceConstants.PREF_TRN_LABEL_ACTION_CONTENT;

	/**
	 * @generated
	 */
	public DiagramConnectionsPreferencePage() {
		setPreferenceStore(PageflowDiagramEditorPlugin.getInstance().getPreferenceStore());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.preferences.ConnectionsPreferencePage#addFields(org.eclipse.swt.widgets.Composite)
	 */
	protected void addFields(Composite composite) {
		addConnectionFields(composite, "Pageflow Transitions");
	}

	/**
	 * @param parent
	 * @param groupTitle
	 */
	protected void addConnectionFields(Composite parent, String groupTitle) {
		Group bpmnGlobalGroup = new Group(parent, 0);
		GridLayout gridLayout = new GridLayout(2, false);
		bpmnGlobalGroup.setLayout(gridLayout);
		GridData gridData = new GridData(768);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		bpmnGlobalGroup.setLayoutData(gridData);
		bpmnGlobalGroup.setText(groupTitle);
		Composite newParent = new Composite(bpmnGlobalGroup, 0);
		RadioGroupFieldEditor routerStyleEditor = new RadioGroupFieldEditor(
				PREF_TRN_LINE_STYLE,
				"Style",
				2,
				new String[][] {
						new String[] { Routing.RECTILINEAR_LITERAL.getLiteral(), Routing.RECTILINEAR_LITERAL.getName() },
						new String[] { "Oblique", Routing.MANUAL_LITERAL.getName() } }, newParent, true);
		routerStyleEditor.setPreferenceStore(super.getPreferenceStore());
		super.addField(routerStyleEditor);
		BooleanFieldEditor shortestPath = new BooleanFieldEditor(PREF_TRN_ROUTE_SHORTEST, "Take shortest path",
				newParent);
		super.addField(shortestPath);
		RadioGroupFieldEditor smoothnessEditor = new RadioGroupFieldEditor(PREF_TRN_ROUTE_SMOOTH_FACTOR, "Smoothness",
				4, new String[][] {
						new String[] { Smoothness.NORMAL_LITERAL.getLiteral(), Smoothness.NORMAL_LITERAL.getName() },
						new String[] { Smoothness.NONE_LITERAL.getLiteral(), Smoothness.NONE_LITERAL.getName() },
						new String[] { Smoothness.LESS_LITERAL.getLiteral(), Smoothness.LESS_LITERAL.getName() },
						new String[] { Smoothness.MORE_LITERAL.getLiteral(), Smoothness.MORE_LITERAL.getName() } },
				newParent, true);
		smoothnessEditor.setPreferenceStore(super.getPreferenceStore());
		super.addField(smoothnessEditor);
		Composite colorFields = new Composite(newParent, 2);
		addField(((org.eclipse.jface.preference.FieldEditor) (new ColorFieldEditor(PREF_TRN_LINE_FILL_COLOR,
				"Transition Color", colorFields))));
		addField(((org.eclipse.jface.preference.FieldEditor) (new ColorFieldEditor(PREF_TRN_LINE_HIGHLIGHT_COLOR,
				"Transition Selection Color", colorFields))));

		RadioGroupFieldEditor labelContentEditor = new RadioGroupFieldEditor(PREF_TRN_LABEL_ACTION_CONTENT,
				"Label Content", 2, new String[][] {
						new String[] { ActionLabel.ACTION_URI_LITERAL.getLiteral(),
								ActionLabel.ACTION_URI_LITERAL.getName() },
						new String[] { ActionLabel.ACTION_NAME_LITERAL.getLiteral(),
								ActionLabel.ACTION_NAME_LITERAL.getName() },
						new String[] { ActionLabel.ACTION_NONE_LITERAL.getLiteral(),
								ActionLabel.ACTION_NONE_LITERAL.getName() } }, newParent, true);
		labelContentEditor.setPreferenceStore(super.getPreferenceStore());
		super.addField(labelContentEditor);

	}
}
