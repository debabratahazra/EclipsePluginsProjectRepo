package com.odcgroup.integrationfwk.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
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
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.CacheManager;
import com.odcgroup.integrationfwk.ui.utils.FileUtil;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Class which controls the Flows Editor and its pages.
 * 
 */
public class FlowsEditor extends FormEditor {

	private TextEditor textEditor;
	private boolean isPageModified;
	private VisualFlowPage visualFlowPage;
	private static final String EDITOR_ID = "com.temenos.tws.consumer.flows.editor";
	/** current consumer project instance */
	private TWSConsumerProject currentProject;
	/** name of the current consumer project */
	private String projectName;
	/** name of the current flow */
	private String flowName;

	public FlowsEditor() {
	}

	@Override
	protected void addPages() {
		createVisualFlowPage();
		createSourcePage();
		updateTitle();
	}

	/**
	 * Method which helps to create and add the source page.
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

	/**
	 * Method which helps to create and add the visual flow page.
	 */
	private void createVisualFlowPage() {
		int index;
		try {
			new Composite(getContainer(), SWT.RESIZE);
			visualFlowPage = new VisualFlowPage(this);
			index = addPage(visualFlowPage);
			setPageText(index, "Enrichments");
		} catch (PartInitException e) {
			TWSConsumerLog.logError(e);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (getActivePage() == 0 && isPageModified) {
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
	 * Get the editor id.
	 * 
	 * @return id of the flows editor.
	 */
	public String getContributorId() {
		return EDITOR_ID;
	}

	/**
	 * Get the current TWS consumer project instance.
	 * 
	 * @return instance of {@link TWSConsumerProject}
	 */
	public TWSConsumerProject getCurrentProject() {
		return currentProject;
	}

	/**
	 * Get the current flow name.
	 * 
	 * @return name of the current flow.
	 */
	public String getFlowName() {
		return flowName;
	}

	/**
	 * Get the name of the current project.
	 * 
	 * @return name of the current consumer project.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * get the source(text) editor of this instance.
	 * 
	 * @return
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
		flowName = Utils.getFileNameWithoutExtention(iFile, "flow");
		loadFlow(iFile);
		super.init(site, input);
	}

	@Override
	public boolean isDirty() {
		return isPageModified || super.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Method which helps to read the physical file corresponding to given
	 * iFile, convert it into the Flow object as instance of {@link Flow} and
	 * then store it into the factory of {@link ConsumerEditorManager} which can
	 * be accessible for later on process.
	 * 
	 * @param iFile
	 *            - instance of {@link IFile}
	 */
	private void loadFlow(IFile iFile) {
		Flow flowInstance = CacheManager.getFlowFromString(FileUtil
				.getFileContents(iFile));
		// This should never happen.
		if (flowInstance == null) {
			return;
		}
		flowInstance.setFlowName(flowName);
		ConsumerEditorManager.getInstance().addToFactory(projectName,
				flowInstance);
	}

	@Override
	protected void pageChange(int newPageIndex) {
		// switch (newPageIndex) {
		// // source page
		// case 1:
		// if (isPageModified) {
		// updateSourcePage();
		// }
		// break;
		// // visual
		// case 0:
		// if (isDirty()) {
		// initVisualFlowPageContent();
		// }
		// }
		super.pageChange(newPageIndex);
	}

	/**
	 * Method which helps to build the TWSConsumer project using given iFile
	 * instance and store it in instance variable for further requirements.
	 * 
	 * @param iFile
	 *            - instance of {@link IFile}
	 */
	private void setCurrentProject(IFile iFile) {
		currentProject = new TWSConsumerProject(iFile.getProject());
	}

	/**
	 * Method which helps to modify the dirty property of flows editor.
	 * 
	 * @param isPageModified
	 *            - true if editor need to be dirty, false otherwise.
	 */
	public void setPageModified(boolean isPageModified) {
		this.isPageModified = isPageModified;
		updateTitle();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	/**
	 * Method which helps to update the source page content.
	 */
	public void updateSourcePage() {
		if (textEditor == null) {
			return;
		}
		Flow flow = ConsumerEditorManager.getInstance().getFlow(projectName,
				flowName);
		if (flow == null) {
			return;
		}
		String flowContents = CacheManager.flowToXML(flow);
		textEditor.getDocumentProvider()
				.getDocument(textEditor.getEditorInput()).set(flowContents);
	}

	/**
	 * Method which helps to update the title of the flows editor.
	 */
	private void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

}