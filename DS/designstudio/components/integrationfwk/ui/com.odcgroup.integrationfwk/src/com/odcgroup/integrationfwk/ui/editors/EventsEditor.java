package com.odcgroup.integrationfwk.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.model.ComponentService;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.Overrides;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.CacheManager;
import com.odcgroup.integrationfwk.ui.utils.FileUtil;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Class which controls the Event Editor and its pages.
 * 
 */
public class EventsEditor extends FormEditor {

	private TextEditor textEditor;
	private boolean isPageModified;
	private ExitPointPage exitPointPage;
	private OverridesPage overridesPage;
	private static final String EDITOR_ID = "com.temenos.tws.consumer.events.editor";
	private FlowPage flowPage;
	/** project corresponding to this event */
	private TWSConsumerProject currentProject;
	/** current event name */
	private String eventName;
	/** name of the project corresponding to this event */
	private String projectName;

	public EventsEditor() {
	}

	@Override
	protected void addPages() {
		createExitPointPage();
		createFlowPage();
		createOverridesPage();
		createSourcePage();
		updateTitle();
		initFlowPageContent();
	}

	/**
	 * method which helps to create the exit point page.
	 */
	private void createExitPointPage() {
		int index;
		try {
			exitPointPage = new ExitPointPage(this);
			index = addPage(exitPointPage);
			setPageText(index, "Exit point");
		} catch (PartInitException e) {
			TWSConsumerLog.logError(e);
		}
	}

	/**
	 * method which helps to create the flow page.
	 */
	private void createFlowPage() {
		int index;
		try {
			flowPage = new FlowPage(this);
			// TODO: what shall I do if there is no flowYet?? decide else
			index = addPage(flowPage);
			setPageText(index, "Flow");
		} catch (PartInitException e) {
			TWSConsumerLog.logError(e);
		}
	}

	/**
	 * method which helps to create the overrides page.
	 */
	private void createOverridesPage() {
		int index;
		try {
			overridesPage = new OverridesPage(this);
			index = addPage(overridesPage);
			setPageText(index, "Overrides");
		} catch (PartInitException e) {
			TWSConsumerLog.logError(e);
		}
	}

	/**
	 * method which helps to create the source page.
	 */
	private void createSourcePage() {
		try {
			textEditor = new TextEditor();
			int index = addPage(textEditor, getEditorInput());
			setPageText(index, "Source");
		} catch (PartInitException e) {
			TWSConsumerLog.logError(e);
		}
	}

	@Override
	protected FormToolkit createToolkit(Display display) {
		// Create a toolkit that shares colors between editors.
		return new FormToolkit(display);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (exitPointPage != null && !exitPointPage.hasValidChanges()) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(),
					"Save Event Details",
					"Invalid or incomplete Details in Exit Point Page.");
			return;
		}
		if (isPageModified) {
			// update the flow name.
			Event event = ConsumerEditorManager.getInstance().getEvent(
					projectName, eventName);
			event.setFlowName(flowPage.getFlowName());
			// TODO: string should be moved to constant
			if (!event.getExitPointType().getExitPoint()
					.equalsIgnoreCase("INPUT.ROUTINE")
					|| event.getExitPointType() instanceof ComponentService) {
				event.setOverrides(new Overrides());
			}
			ConsumerEditorManager.getInstance()
					.addToFactory(projectName, event);
			updateSourcePage();
		}
		isPageModified = false;
		textEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		textEditor.doSave(null);
		setInput(textEditor.getEditorInput());
		updateTitle();
	}

	@Override
	public int getActivePage() {
		return super.getActivePage();
	}

	/**
	 * get the id of the events editor.
	 * 
	 * @return
	 */
	public String getContributorId() {
		return EDITOR_ID;
	}

	/**
	 * get the project corresponding to the current event.
	 * 
	 * @return instance of {@link TWSConsumerProject}
	 */
	public TWSConsumerProject getCurrentProject() {
		return currentProject;
	}

	/**
	 * get the name of the current event.
	 * 
	 * @return name of the event.
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * get the override page instance.
	 * 
	 * @return
	 */
	public OverridesPage getOverridesPage() {
		return overridesPage;
	}

	/**
	 * get the name of the project corresponding to this current event.
	 * 
	 * @return name of the project.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * get the source editor.
	 * 
	 * @return instance of {@link ITextEditor}
	 */
	public ITextEditor getSourceEditor() {
		return textEditor;
	}

	@Override
	protected void handlePropertyChange(int propertyId) {
		if (propertyId == IEditorPart.PROP_DIRTY) {
			isPageModified = isDirty();
		}
		super.handlePropertyChange(propertyId);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof IFileEditorInput)) {
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");
		}
		IFile iFile = ((FileEditorInput) input).getFile();
		setCurrentProject(iFile);
		projectName = currentProject.getProject().getName();
		eventName = Utils.getFileNameWithoutExtention(iFile, "event");
		loadEvent(iFile);
		super.init(site, input);
	}

	/**
	 * prepare the flow page content.
	 */
	private void initFlowPageContent() {
		String flowName = ConsumerEditorManager.getInstance()
				.getEvent(getProjectName(), getEventName()).getFlowName();
		flowPage.setFlowNameText(flowName);
	}

	@Override
	public boolean isDirty() {
		return isPageModified || super.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * method which helps to convert the given iFile to Event object and place
	 * into the factory collection of{@link ConsumerEditorManager} for future
	 * access.
	 * 
	 * @param iFile
	 *            - instance of {@link IFile} which is basically read from the
	 *            physical location of the current event.
	 */
	private void loadEvent(IFile iFile) {
		Event event = new CacheManager().getEventFromString(FileUtil
				.getFileContents(iFile));
		// This should never happen.
		if (event == null) {
			return;
		}
		event.setEventName(eventName);
		ConsumerEditorManager.getInstance().addToFactory(projectName, event);
	}

	@Override
	protected void pageChange(int newPageIndex) {
		// switch (newPageIndex) {
		// // overrides page
		// case 2:
		// overridesPage.refresh();
		// break;
		// // flows page
		// case 1:
		// flowPage.refresh();
		// break;
		// // exit point page
		// case 0:
		// exitPointPage.refresh();
		// break;
		// }
		super.pageChange(newPageIndex);
	}

	/**
	 * method which helps to get the current project from given iFile and set it
	 * in the instance variable.
	 * 
	 * @param iFile
	 *            - instance of {@link IFile}.
	 */
	private void setCurrentProject(IFile iFile) {
		currentProject = new TWSConsumerProject(iFile.getProject());
	}

	/**
	 * method which helps to handle the dirty property of Editor.
	 * 
	 * @param isPageModified
	 *            - true if need to be dirty, false otherwise.
	 */
	public void setPageModified(boolean isPageModified) {
		this.isPageModified = isPageModified;
		updateTitle();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	/**
	 * method which helps to update the source page content.
	 */
	public void updateSourcePage() {
		if (textEditor == null) {
			return;
		}
		Event event = ConsumerEditorManager.getInstance().getEvent(
				currentProject.getProject().getName(), eventName);
		if (event == null) {
			return;
		}
		String stringEvent = new CacheManager().eventToXML(event);
		textEditor.getDocumentProvider()
				.getDocument(textEditor.getEditorInput()).set(stringEvent);
	}

	/**
	 * method which helps to update the title of the event editor.
	 */
	private void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}
}