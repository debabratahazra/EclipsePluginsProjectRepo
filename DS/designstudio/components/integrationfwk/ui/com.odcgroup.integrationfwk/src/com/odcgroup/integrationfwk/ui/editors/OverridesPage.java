package com.odcgroup.integrationfwk.ui.editors;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.editors.listeners.AbstractEventsEditorListener;
import com.odcgroup.integrationfwk.ui.editors.listeners.OverridesPageListener;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.SourceType;

/**
 * Class which helps to draw the ui components of Overrides page which will be
 * placed in Events Editor.
 * 
 */
public class OverridesPage extends FormPage {
	private Section firstSection, secondSection;
	/** add button */
	private Button btnAdd;
	/** remove button */
	private Button btnRemove;

	private org.eclipse.swt.widgets.List list, list2;
	/** destination list viewer */
	private ListViewer destListViewer;
	/** instance of {@link AbstractEventsEditorListener} */
	private final AbstractEventsEditorListener editorListener;
	/** source list viewer */
	private ListViewer sourceListViewer;

	public OverridesPage(FormEditor editor) {
		super(editor, "Overrides", "Overrides");
		editorListener = new OverridesPageListener(this);
	}

	/**
	 * Helps to decide whether the given event has the ability to access the
	 * overrides page or not.
	 * 
	 * @param event
	 * @return true if the given event's exit point is INPUT.ROUTINE and the
	 *         type is NOT Component Service or TSA Service. Otherwise return
	 *         false.
	 */
	private boolean canEnableOverrides(Event event) {
		String sourceType = event.getExitPointType().getSourceType();
		String exitPoint = event.getExitPointType().getExitPoint();
		if (sourceType
				.equalsIgnoreCase(SourceType.COMPONENT_SERVICE.toString())
				|| sourceType.equalsIgnoreCase(SourceType.TSA_SERVICE
						.toString())) {
			return false;
		}
		return "INPUT.ROUTINE".equalsIgnoreCase(exitPoint);
	}

	/**
	 * Method which helps to create the section where the available overrides
	 * will be shown, using given toolkit on the given form.
	 * 
	 * @param form
	 *            - scrolled form which is in form page.
	 * @param toolkit
	 *            - kit which help to draw the ui components.
	 * @param title
	 *            - title of the section.
	 */
	private void createFirstSection(final ScrolledForm form,
			FormToolkit toolkit, String title) {

		firstSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		firstSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(firstSection);
		// TODO : cater for cases when there are more
		firstSection.setEnabled("INPUT.ROUTINE".equalsIgnoreCase(getEvent()
				.getExitPointType().getExitPoint()));

		Composite client = toolkit.createComposite(firstSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		client.setLayout(layout);

		sourceListViewer = new ListViewer(client);
		sourceListViewer.setContentProvider(new ArrayContentProvider());

		sourceListViewer.setInput(((OverridesPageListener) editorListener)
				.getOverrides());
		list = sourceListViewer.getList();
		list.setEnabled("INPUT.ROUTINE".equalsIgnoreCase(getEvent()
				.getExitPointType().getExitPoint()));
		sourceListViewer.addSelectionChangedListener(editorListener);
		sourceListViewer.addDoubleClickListener(editorListener);
		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				1);
		gd_list.widthHint = 250;
		gd_list.heightHint = 200;
		list.setLayoutData(gd_list);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 100;

		toolkit.paintBordersFor(client);
		btnAdd = toolkit.createButton(client, "Add", SWT.PUSH); //$NON-NLS-1$
		btnAdd.setEnabled(false);
		btnAdd.addSelectionListener(editorListener);

		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		btnAdd.setLayoutData(gd);
		firstSection.setText(title);
		firstSection.setClient(client);

		gd = new GridData(GridData.FILL_BOTH);
		firstSection.setLayoutData(gd);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText("Overrides"); //$NON-NLS-1$
		form.setBackgroundImage(IntegrationToolingActivator.getDefault().getImage(
				"icons/" + IntegrationToolingActivator.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout);
		firstSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
		secondSection = toolkit
				.createSection(form.getBody(), Section.TITLE_BAR);

		createFirstSection(form, toolkit, "Select from the list"); //$NON-NLS-1$
		createSecondSection(form, toolkit, "Selected overrides"); //$NON-NLS-1$
	}

	/**
	 * Method which helps to create the section which contains the selected
	 * overrides list, within the given form using given toolkit.
	 * 
	 * @param form
	 *            - scrolled form of the form page.
	 * @param toolkit
	 *            - kit which used to create the ui components.
	 * @param title
	 *            - title for the section.
	 */
	private void createSecondSection(final ScrolledForm form,
			FormToolkit toolkit, String title) {
		secondSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		secondSection.setToggleColor(toolkit.getColors().getColor(
				FormColors.SEPARATOR));
		toolkit.createCompositeSeparator(secondSection);
		secondSection.setEnabled("INPUT.ROUTINE".equalsIgnoreCase(getEvent()
				.getExitPointType().getExitPoint()));

		Composite client = toolkit.createComposite(secondSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		client.setLayout(layout);
		destListViewer = new ListViewer(client);
		destListViewer.setLabelProvider(new LabelProvider());
		destListViewer.setContentProvider(new ArrayContentProvider());
		destListViewer.addSelectionChangedListener(editorListener);
		destListViewer.addDoubleClickListener(editorListener);
		list2 = destListViewer.getList();

		list2.setEnabled("INPUT.ROUTINE".equalsIgnoreCase(getEvent()
				.getExitPointType().getExitPoint()));

		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				1);
		gd_list.widthHint = 250;
		gd_list.heightHint = 200;
		list2.setLayoutData(gd_list);

		destListViewer.setInput(getEvent().getOverrides().getOverrides());

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 100;
		// destListViewer.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		btnRemove = toolkit.createButton(client, "Remove", SWT.PUSH); //$NON-NLS-1$
		btnRemove.setEnabled(false);
		btnRemove.addSelectionListener(editorListener);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		btnRemove.setLayoutData(gd);
		secondSection.setText(title);
		secondSection.setClient(client);

		gd = new GridData(GridData.FILL_BOTH);
		secondSection.setLayoutData(gd);
	}

	/**
	 * 
	 * @return instance of {@link #btnAdd}
	 */
	public Button getAddButton() {
		return btnAdd;
	}

	/**
	 * 
	 * @return instance of {@link #destListViewer}
	 */
	public ListViewer getDestinationListViewer() {
		return destListViewer;
	}

	/**
	 * get the most recent event object from factory of
	 * {@link ConsumerEditorManager}.
	 * 
	 * @return instance of {@link Event}
	 */
	private Event getEvent() {
		return ConsumerEditorManager.getInstance().getEvent(
				getEventEditor().getProjectName(),
				getEventEditor().getEventName());
	}

	/**
	 * get the event editor which has the container of this override form page.
	 * 
	 * @return instance of {@link EventsEditor}
	 */
	private EventsEditor getEventEditor() {
		return ((EventsEditor) getEditor());
	}

	@Override
	public String getId() {
		return "OverridesPage";
	}

	/**
	 * 
	 * @return instance of {@link #btnRemove}
	 */
	public Button getRemoveButton() {
		return btnRemove;
	}

	/**
	 * 
	 * @return instance of {@link #sourceListViewer}
	 */
	public ListViewer getSourceListviewer() {
		return sourceListViewer;
	}

	/**
	 * Method which helps to refresh the ui components of Overrides page.
	 */
	public void refresh() {
		Event event = getEvent();
		if (destListViewer != null) {
			destListViewer.setInput(getEvent().getOverrides().getOverrides());
			boolean canBeEnabled = canEnableOverrides(event);
			list.setEnabled(canBeEnabled);
			firstSection.setEnabled(canBeEnabled);
			list2.setEnabled(canBeEnabled);
			secondSection.setEnabled(canBeEnabled);
			btnAdd.setEnabled(canBeEnabled);
			btnRemove.setEnabled(canBeEnabled);
		}
	}
}
