/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.preferences;

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

import com.odcgroup.process.diagram.custom.preferences.ProcessPreferenceConstants;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramConnectionsPreferencePage extends ConnectionsPreferencePage {

	private static String PREF_TRN_LINE_STYLE = ProcessPreferenceConstants.PREF_TRN_LINE_STYLE;
	private static String PREF_TRN_ROUTE_SHORTEST = ProcessPreferenceConstants.PREF_TRN_ROUTE_SHORTEST;
	private static String PREF_TRN_ROUTE_SMOOTH_FACTOR = ProcessPreferenceConstants.PREF_TRN_ROUTE_SMOOTH_FACTOR;
	private static String PREF_TRN_LINE_FILL_COLOR = ProcessPreferenceConstants.PREF_TRN_LINE_FILL_COLOR;
	private static String PREF_TRN_LINE_HIGHLIGHT_COLOR = ProcessPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR;

	/**
	 * @generated
	 */
	public DiagramConnectionsPreferencePage() {
		setPreferenceStore(ProcessDiagramEditorPlugin.getInstance().getPreferenceStore());
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
				2, new String[][] {
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

	}
}
