package com.zealcore.se.ui.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.dialogs.FileSystemElement;
import org.eclipse.ui.dialogs.WizardResourceImportPage;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.model.WorkbenchViewerComparator;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

public class LTTngFileSystemResourceImportPage extends WizardResourceImportPage
		implements Listener {
	// widgets
	protected Combo sourceNameField;

	protected Button overwriteExistingResourcesCheckbox;

	protected Button createContainerStructureButton;

	protected Button createOnlySelectedButton;

	protected Button createVirtualFoldersButton;

	protected Button createLinksInWorkspaceButton;

	protected Button advancedButton;

	protected String pathVariable;

	protected Button sourceBrowseButton;

	protected Button selectAllButton;

	protected Button deselectAllButton;

	// A boolean to indicate if the user has typed anything
	private boolean entryChanged = false;

	private FileSystemStructureProvider fileSystemStructureProvider = FileSystemStructureProvider.INSTANCE;

	// dialog store id constants

	private static final String SELECT_ALL_TITLE = "Select All";

	private static final String DESELECT_ALL_TITLE = "Deselect All";

	private static final String SELECT_SOURCE_TITLE = "Select source";

	private static final String SELECT_SOURCE_MESSAGE = "Select Source";

	protected static final String SOURCE_EMPTY_MESSAGE = "";

	public static final String LTTNG_FILE_EXT = ".lttng";

	private Text containerNameField;

	private String initialContainerFieldValue;

	private ResourceTreeAndListGroup selectionGroup;

	private String path;

	protected String tracesFolder;

	/**
	 * Creates an instance of this class
	 */
	protected LTTngFileSystemResourceImportPage(String name,
			IWorkbench aWorkbench, IStructuredSelection selection) {
		super(name, selection);
	}

	/**
	 * Creates an instance of this class
	 * 
	 * @param aWorkbench
	 *            IWorkbench
	 * @param selection
	 *            IStructuredSelection
	 */
	public LTTngFileSystemResourceImportPage(IWorkbench workbench,
			IStructuredSelection selection) {
		this("fileSystemImportPage1", workbench, selection);
		setTitle("Import LTTng Traces from file system");
		setDescription("Import traces from the local file system.");
		Object element = ((IStructuredSelection) workbench
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection()).getFirstElement();
		if (element instanceof IFolder) {
			IFolder f = (IFolder) element;
			path = f.getWorkspace().getRoot().getLocation().toOSString()
					+ f.getFullPath().toOSString();
		} else if (element instanceof IProject) {
			IProject p = (IProject)element;
			path = p.getWorkspace().getRoot().getLocation().toOSString()
					+ p.getFullPath().toOSString();
		}
	}

	/**
	 * Creates a new button with the given id.
	 * <p>
	 * The <code>Dialog</code> implementation of this framework method creates a
	 * standard push button, registers for selection events including button
	 * presses and registers default buttons with its shell. The button id is
	 * stored as the buttons client data. Note that the parent's layout is
	 * assumed to be a GridLayout and the number of columns in this layout is
	 * incremented. Subclasses may override.
	 * </p>
	 * 
	 * @param parent
	 *            the parent composite
	 * @param id
	 *            the id of the button (see <code>IDialogConstants.*_ID</code>
	 *            constants for standard dialog button ids)
	 * @param label
	 *            the label from the button
	 * @param defaultButton
	 *            <code>true</code> if the button is to be the default button,
	 *            and <code>false</code> otherwise
	 */
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns++;

		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());

		GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(buttonData);

		button.setData(new Integer(id));
		button.setText(label);

		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
			button.setFocus();
		}
		return button;
	}

	/**
	 * Creates the buttons for selecting specific types or selecting all or none
	 * of the elements.
	 * 
	 * @param parent
	 *            the parent control
	 */
	protected final void createButtonsGroup(Composite parent) {
		// top level group
		Composite buttonComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = true;
		buttonComposite.setLayout(layout);
		buttonComposite.setFont(parent.getFont());
		GridData buttonData = new GridData(SWT.FILL, SWT.FILL, true, false);
		buttonComposite.setLayoutData(buttonData);

		selectAllButton = createButton(buttonComposite,
				IDialogConstants.SELECT_ALL_ID, SELECT_ALL_TITLE, false);

		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setAllSelections(true);
				updateWidgetEnablements();
			}
		};
		selectAllButton.addSelectionListener(listener);
		setButtonLayoutData(selectAllButton);

		deselectAllButton = createButton(buttonComposite,
				IDialogConstants.DESELECT_ALL_ID, DESELECT_ALL_TITLE, false);

		listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setAllSelections(false);
				updateWidgetEnablements();
			}
		};
		deselectAllButton.addSelectionListener(listener);
		setButtonLayoutData(deselectAllButton);

	}

	/**
	 * Create the group for creating the root directory
	 */
	protected void createRootDirectoryGroup(Composite parent) {
		Composite sourceContainerGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		sourceContainerGroup.setLayout(layout);
		sourceContainerGroup.setFont(parent.getFont());
		sourceContainerGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

		Label groupLabel = new Label(sourceContainerGroup, SWT.NONE);
		// LTTng
		groupLabel.setText("Select traces directory:");
		groupLabel.setFont(parent.getFont());

		// source name entry field
		sourceNameField = new Combo(sourceContainerGroup, SWT.BORDER);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		sourceNameField.setLayoutData(data);
		sourceNameField.setFont(parent.getFont());

		sourceNameField.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateFromSourceField();
			}
		});

		sourceNameField.addKeyListener(new KeyListener() {
			/*
			 * @see KeyListener.keyPressed
			 */
			public void keyPressed(KeyEvent e) {
				// If there has been a key pressed then mark as dirty
				entryChanged = true;
				if (e.character == SWT.CR) {
					entryChanged = false;
					updateFromSourceField();
				}
			}

			/*
			 * @see KeyListener.keyReleased
			 */
			public void keyReleased(KeyEvent e) {
			}
		});

		sourceNameField.addFocusListener(new FocusListener() {
			/*
			 * @see FocusListener.focusGained(FocusEvent)
			 */
			public void focusGained(FocusEvent e) {
				// Do nothing when getting focus
			}

			/*
			 * @see FocusListener.focusLost(FocusEvent)
			 */
			public void focusLost(FocusEvent e) {
				// Clear the flag to prevent constant update
				if (entryChanged) {
					entryChanged = false;
					updateFromSourceField();
				}

			}
		});

		// source browse button
		sourceBrowseButton = new Button(sourceContainerGroup, SWT.PUSH);
		sourceBrowseButton.setText("Browse");
		sourceBrowseButton.addListener(SWT.Selection, this);
		sourceBrowseButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));
		sourceBrowseButton.setFont(parent.getFont());
		setButtonLayoutData(sourceBrowseButton);
	}

	/**
	 * Update the receiver from the source name field.
	 */

	private void updateFromSourceField() {

		setSourceName(sourceNameField.getText());
		// Update enablements when this is selected
		updateWidgetEnablements();
	}

	/**
	 * Creates and returns a <code>FileSystemElement</code> if the specified
	 * file system object merits one. The criteria for this are: Also create the
	 * children.
	 */
	protected LttngMinimizedFileSystemElement createRootElement(
			Object fileSystemObject, IImportStructureProvider provider) {
		boolean isContainer = provider.isFolder(fileSystemObject);
		String elementLabel = provider.getLabel(fileSystemObject);

		// Use an empty label so that display of the element's full name
		// doesn't include a confusing label
		LttngMinimizedFileSystemElement dummyParent = new LttngMinimizedFileSystemElement(
				"", null, true);
		dummyParent.setPopulated();
		LttngMinimizedFileSystemElement result = new LttngMinimizedFileSystemElement(
				elementLabel, dummyParent, isContainer);
		result.setFileSystemObject(fileSystemObject);

		// Get the files for the element so as to build the first level
		result.getFiles(provider);

		return dummyParent;
	}

	/**
	 * Create the import source specification widgets
	 */
	protected void createSourceGroup(Composite parent) {

		createRootDirectoryGroup(parent);
		createFileSelectionGroup(parent);
		createButtonsGroup(parent);
	}

	/**
	 * Create the import source selection widget
	 */
	protected void createFileSelectionGroup(Composite parent) {

		// Just create with a dummy root.
		this.setSelectionGroup(new ResourceTreeAndListGroup(
				parent,
				new FileSystemElement("Dummy", null, true),//$NON-NLS-1$
				getFolderProvider(), new WorkbenchLabelProvider(),
				getFileProvider(), new WorkbenchLabelProvider(), SWT.NONE, true));

		ICheckStateListener listener = new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				updateWidgetEnablements();
			}
		};

		WorkbenchViewerComparator comparator = new WorkbenchViewerComparator();
		this.getSelectionGroup().setTreeComparator(comparator);
		this.getSelectionGroup().setListComparator(comparator);
		this.getSelectionGroup().addCheckStateListener(listener);

	}

	/**
	 * Enable or disable the button group.
	 */
	protected void enableButtonGroup(boolean enable) {
		selectAllButton.setEnabled(enable);
		deselectAllButton.setEnabled(enable);
	}

	/**
	 * Answer a boolean indicating whether the specified source currently exists
	 * and is valid
	 */
	protected boolean ensureSourceIsValid() {
		if (new File(getSourceDirectoryName()).isDirectory()) {
			return true;
		}

		setErrorMessage("Invalid file");
		return false;
	}

	/**
	 * Execute the passed import operation. Answer a boolean indicating success.
	 */
	protected boolean executeImportOperation(ImportOperation op) {
		initializeOperation(op);

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			displayErrorDialog(e.getTargetException());
			return false;
		}

		IStatus status = op.getStatus();
		if (!status.isOK()) {
			ErrorDialog.openError(getContainer().getShell(),
					"file import failed", null, // no special message
					status);
			return false;
		}

		return true;
	}

	/**
	 * The Finish button was pressed. Try to do the required work now and answer
	 * a boolean indicating success. If false is returned then the wizard will
	 * not close.
	 * 
	 * @return boolean
	 */
	public boolean finish() {
		if (!ensureSourceIsValid()) {
			return false;
		}

		saveWidgetValues();

		Iterator<?> resourcesEnum = selectionGroup.getAllCheckedListItems()
				.iterator();
		List<Object> fileSystemObjects = new ArrayList<Object>();
		while (resourcesEnum.hasNext()) {
			fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
					.getFileSystemObject());
		}

		if (fileSystemObjects.size() > 0) {
			final String[] traceFiles = new String[fileSystemObjects.size()];
			int index = 0;
			for (Iterator iterator = fileSystemObjects.iterator(); iterator
					.hasNext();) {
				File fse = (File) iterator.next();
				traceFiles[index] = fse.getAbsolutePath();
				index++;
			}
			tracesFolder = getNewTraceFolder(path, getContainerFieldValue());
			try {
				ResourcesPlugin.getWorkspace().getRoot()
						.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
			}
			CopyFilesAndFoldersOperation copyOp = new CopyFilesAndFoldersOperation(
					getShell());
			copyOp.copyFiles(traceFiles, ResourcesPlugin.getWorkspace()
					.getRoot().getContainerForLocation(new Path(tracesFolder)));
			return true;
		}

		MessageDialog.openInformation(getContainer().getShell(),
				"file transfer", "None selected");

		return false;
	}

	private String getNewTraceFolder(String path, String name) {
		String folderName = path + System.getProperty("file.separator") + name;
		File file = new File(folderName);
		int index = 0;
		while (file.exists()) {
			index++;
			folderName = folderName + index;
			file = new File(folderName);
		}
		file.mkdir();
		return folderName;
	}

	/**
	 * Returns a content provider for <code>FileSystemElement</code>s that
	 * returns only files as children.
	 */
	protected ITreeContentProvider getFileProvider() {
		return new WorkbenchContentProvider() {
			public Object[] getChildren(Object o) {
				if (o instanceof LttngMinimizedFileSystemElement) {
					LttngMinimizedFileSystemElement element = (LttngMinimizedFileSystemElement) o;
					return element.getFiles(fileSystemStructureProvider)
							.getChildren(element);
				}
				return new Object[0];
			}
		};
	}

	/**
	 * Answer the root FileSystemElement that represents the contents of the
	 * currently-specified source. If this FileSystemElement is not currently
	 * defined then create and return it.
	 */
	protected LttngMinimizedFileSystemElement getFileSystemTree() {

		File sourceDirectory = getSourceDirectory();
		if (sourceDirectory == null) {
			return null;
		}

		return selectFiles(sourceDirectory, fileSystemStructureProvider);
	}

	/**
	 * Returns a content provider for <code>FileSystemElement</code>s that
	 * returns only folders as children.
	 */
	protected ITreeContentProvider getFolderProvider() {
		return new WorkbenchContentProvider() {
			public Object[] getChildren(Object o) {
				if (o instanceof LttngMinimizedFileSystemElement) {
					LttngMinimizedFileSystemElement element = (LttngMinimizedFileSystemElement) o;
					return element.getFolders(fileSystemStructureProvider)
							.getChildren(element);
				}
				return new Object[0];
			}

			public boolean hasChildren(Object o) {
				if (o instanceof LttngMinimizedFileSystemElement) {
					LttngMinimizedFileSystemElement element = (LttngMinimizedFileSystemElement) o;
					// if (element.isPopulated()) {
					if (getChildren(element) != null) {
						return getChildren(element).length > 0;
					}

					// If we have not populated then wait until asked
					return true;
				}
				return false;
			}
		};
	}

	/**
	 * Returns a File object representing the currently-named source directory
	 * iff it exists as a valid directory, or <code>null</code> otherwise.
	 */
	protected File getSourceDirectory() {
		return getSourceDirectory(this.sourceNameField.getText());
	}

	/**
	 * Returns a File object representing the currently-named source directory
	 * iff it exists as a valid directory, or <code>null</code> otherwise.
	 * 
	 * @param path
	 *            a String not yet formatted for java.io.File compatability
	 */
	private File getSourceDirectory(String path) {
		File sourceDirectory = new File(getSourceDirectoryName(path));
		if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
			return null;
		}

		return sourceDirectory;
	}

	/**
	 * Answer the directory name specified as being the import source. Note that
	 * if it ends with a separator then the separator is first removed so that
	 * java treats it as a proper directory
	 */
	private String getSourceDirectoryName() {
		return getSourceDirectoryName(this.sourceNameField.getText());
	}

	/**
	 * Answer the directory name specified as being the import source. Note that
	 * if it ends with a separator then the separator is first removed so that
	 * java treats it as a proper directory
	 */
	private String getSourceDirectoryName(String sourceName) {
		IPath result = new Path(sourceName.trim());

		if (result.getDevice() != null && result.segmentCount() == 0) {
			result = result.addTrailingSeparator();
		} else {
			result = result.removeTrailingSeparator();
		}

		return result.toOSString();
	}

	/**
	 * Answer the string to display as the label for the source specification
	 * field
	 */
	protected String getSourceLabel() {
		return "File import from directory";
	}

	/**
	 * Handle all events and enablements for widgets in this dialog
	 * 
	 * @param event
	 *            Event
	 */
	public void handleEvent(Event event) {
		if (event.widget == sourceBrowseButton) {
			handleSourceBrowseButtonPressed();
		}
		super.handleEvent(event);
	}

	/**
	 * Open an appropriate source browser so that the user can specify a source
	 * to import from
	 */
	protected void handleSourceBrowseButtonPressed() {

		String currentSource = this.sourceNameField.getText();
		DirectoryDialog dialog = new DirectoryDialog(
				sourceNameField.getShell(), SWT.SAVE);
		dialog.setText(SELECT_SOURCE_TITLE);
		dialog.setMessage(SELECT_SOURCE_MESSAGE);
		dialog.setFilterPath(getSourceDirectoryName(currentSource));

		String selectedDirectory = dialog.open();
		if (selectedDirectory != null) {
			// Just quit if the directory is not valid
			if ((getSourceDirectory(selectedDirectory) == null)
					|| selectedDirectory.equals(currentSource)) {
				return;
			}
			// If it is valid then proceed to populate
			setErrorMessage(null);
			setSourceName(selectedDirectory);
			setContainerFieldValue(getSourceDirectory().getName().toString());
			selectionGroup.setFocus();
		}
	}

	/**
	 * Open a registered type selection dialog and note the selections in the
	 * receivers types-to-export field., Added here so that inner classes can
	 * have access
	 */
	protected void handleTypesEditButtonPressed() {

		super.handleTypesEditButtonPressed();
	}

	/**
	 * Import the resources with extensions as specified by the user
	 */
	protected boolean importResources(List fileSystemObjects) {
		ImportOperation operation;

		boolean shouldImportTopLevelFoldersRecursively = // selectionGroup.isEveryItemChecked()
															// &&
		createOnlySelectedButton.getSelection()
				&& (createLinksInWorkspaceButton != null && createLinksInWorkspaceButton
						.getSelection())
				&& (createVirtualFoldersButton != null && createVirtualFoldersButton
						.getSelection() == false);

		if (shouldImportTopLevelFoldersRecursively)
			operation = new ImportOperation(getContainerFullPath(),
					getSourceDirectory(), fileSystemStructureProvider, this,
					Arrays.asList(new File[] { getSourceDirectory() }));
		else
			operation = new ImportOperation(getContainerFullPath(),
					getSourceDirectory(), fileSystemStructureProvider, this,
					fileSystemObjects);

		operation.setContext(getShell());
		return executeImportOperation(operation);
	}

	protected void handleContainerBrowseButtonPressed() {
		super.handleContainerBrowseButtonPressed();
		updateWidgetEnablements();
	}

	/**
	 * Initializes the specified operation appropriately.
	 */
	protected void initializeOperation(ImportOperation op) {
		op.setCreateContainerStructure(createContainerStructureButton
				.getSelection());
		op.setOverwriteResources(overwriteExistingResourcesCheckbox
				.getSelection());

	}

	/**
	 * Returns whether the extension provided is an extension that has been
	 * specified for export by the user.
	 * 
	 * @param extension
	 *            the resource name
	 * @return <code>true</code> if the resource name is suitable for export
	 *         based upon its extension
	 */
	protected boolean isExportableExtension(String extension) {
		if (selectedTypes == null) {
			return true;
		}

		Iterator itr = selectedTypes.iterator();
		while (itr.hasNext()) {
			if (extension.equalsIgnoreCase((String) itr.next())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Repopulate the view based on the currently entered directory.
	 */
	protected void resetSelection() {

		LttngMinimizedFileSystemElement currentRoot = getFileSystemTree();
		this.getSelectionGroup().setRoot(currentRoot);
	}

	/**
	 * Invokes a file selection operation using the specified file system and
	 * structure provider. If the user specifies files to be imported then this
	 * selection is cached for later retrieval and is returned.
	 */
	protected LttngMinimizedFileSystemElement selectFiles(
			final Object rootFileSystemObject,
			final IImportStructureProvider structureProvider) {

		final LttngMinimizedFileSystemElement[] results = new LttngMinimizedFileSystemElement[1];

		BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
			public void run() {
				// Create the root element from the supplied file system object
				results[0] = createRootElement(rootFileSystemObject,
						structureProvider);
			}
		});

		return results[0];
	}

	/**
	 * Set all of the selections in the selection group to value. Implemented
	 * here to provide access for inner classes.
	 * 
	 * @param value
	 *            boolean
	 */
	protected void setAllSelections(boolean value) {
		selectionGroup.setAllSelections(value);
	}

	/**
	 * Sets the source name of the import to be the supplied path. Adds the name
	 * of the path to the list of items in the source combo and selects it.
	 * 
	 * @param path
	 *            the path to be added
	 */
	protected void setSourceName(String path) {

		if (path.length() > 0) {

			String[] currentItems = this.sourceNameField.getItems();
			int selectionIndex = -1;
			for (int i = 0; i < currentItems.length; i++) {
				if (currentItems[i].equals(path)) {
					selectionIndex = i;
				}
			}
			if (selectionIndex < 0) {
				int oldLength = currentItems.length;
				String[] newItems = new String[oldLength + 1];
				System.arraycopy(currentItems, 0, newItems, 0, oldLength);
				newItems[oldLength] = path;
				this.sourceNameField.setItems(newItems);
				selectionIndex = oldLength;
			}
			this.sourceNameField.select(selectionIndex);

			resetSelection();
		}
	}

	/*
	 * (non-Javadoc) Method declared on IDialogPage. Set the selection up when
	 * it becomes visible.
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		resetSelection();
		if (visible) {
			this.sourceNameField.setFocus();
		}
	}

	/**
	 * Update the selections with those in map . Implemented here to give inner
	 * class visibility
	 * 
	 * @param map
	 *            Map - key tree elements, values Lists of list elements
	 */
	protected void updateSelections(Map map) {
		super.updateSelections(map);
	}

	/**
	 * Check if widgets are enabled or disabled by a change in the dialog.
	 * Provided here to give access to inner classes.
	 */
	protected void updateWidgetEnablements() {
		super.updateWidgetEnablements();
		enableButtonGroup(ensureSourceIsValid());

	}

	/**
	 * Answer a boolean indicating whether self's source specification widgets
	 * currently all contain valid values.
	 */
	protected boolean validateSourceGroup() {
		File sourceDirectory = getSourceDirectory();
		if (sourceDirectory == null) {
			setMessage(SOURCE_EMPTY_MESSAGE);
			enableButtonGroup(false);
			return false;
		}

		List resourcesToExport = getSelectionGroup().getAllWhiteCheckedItems();
		if (resourcesToExport.size() == 0) {
			setMessage(null);
			setErrorMessage("There are no traces currently selected for import.");

			return false;
		}
		if (!validateTraceFileName()) {
			return false;
		}

		enableButtonGroup(true);
		setErrorMessage(null);
		return true;
	}

	protected boolean validateTraceFileName() {
		if (getContainerFieldValue().trim().length() <= 0) {
			setErrorMessage("Trace file name cannot be empty.");
			return false;
		}
		String traceLogFilePathName = path
				+ System.getProperty("file.separator")
				+ getContainerFieldValue() + LTTNG_FILE_EXT;

		File traceLogFile = new File(traceLogFilePathName);
		if (traceLogFile.exists()) {
			setErrorMessage("'" + getContainerFieldValue() + LTTNG_FILE_EXT
					+ "' already exists.");
			return false;
		} else {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus result = workspace.validateName(getContainerFieldValue(),
					IResource.FILE);
			if (!result.isOK()) {
				setErrorMessage(result.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the source location conflicts with the destination
	 * resource. This will occur if the source is already under the destination.
	 * 
	 * @param sourcePath
	 *            the path to check
	 * @return <code>true</code> if there is a conflict, <code>false</code> if
	 *         not
	 */
	protected boolean sourceConflictsWithDestination(IPath sourcePath) {

		IContainer container = getSpecifiedContainer();
		if (container == null) {
			return false;
		}

		IPath destinationLocation = getSpecifiedContainer().getLocation();
		if (destinationLocation != null) {
			return destinationLocation.isPrefixOf(sourcePath);
		}
		// null destination location is handled in
		// WizardResourceImportPage
		return false;
	}

	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite.setFont(parent.getFont());

		createSourceGroup(composite);

		createDestinationFileGroup(composite);
		// createDestinationGroup(composite);

		createOptionsGroup(composite);

		restoreWidgetValues();
		updateWidgetEnablements();
		setPageComplete(determinePageCompletion());
		setErrorMessage(null); // should not initially have error message

		setControl(composite);

		validateSourceGroup();
	}

	protected void createOptionsGroup(Composite parent) {
	}

	protected final void createDestinationFileGroup(Composite parent) {
		// container specification group
		Composite containerGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		containerGroup.setLayout(layout);
		containerGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		containerGroup.setFont(parent.getFont());

		// container label
		Label resourcesLabel = new Label(containerGroup, SWT.NONE);
		resourcesLabel.setText("Into trace file name:");
		resourcesLabel.setFont(parent.getFont());

		// container name entry field
		containerNameField = new Text(containerGroup, SWT.SINGLE | SWT.BORDER);
		containerNameField.addListener(SWT.Modify, this);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		containerNameField.setLayoutData(data);
		containerNameField.setFont(parent.getFont());

		initialPopulateDestinationField();
	}

	public void setContainerFieldValue(String value) {
		if (containerNameField == null) {
			initialContainerFieldValue = value;
		} else {
			if (containerNameField.getText().trim().length() <= 0) {
				containerNameField.setText(value);
			}
		}
	}

	public String getContainerFieldValue() {
		return containerNameField.getText();

	}

	protected void initialPopulateDestinationField() {
		if (initialContainerFieldValue != null) {
			containerNameField.setText(initialContainerFieldValue);
		}
	}

	protected boolean determinePageCompletion() {
		boolean complete = validateSourceGroup();

		// Avoid draw flicker by not clearing the error
		// message unless all is valid.
		if (complete) {
			setErrorMessage(null);
		}

		return complete;
	}

	public void setSelectionGroup(ResourceTreeAndListGroup selectionGroup) {
		this.selectionGroup = selectionGroup;
	}

	public ResourceTreeAndListGroup getSelectionGroup() {
		return selectionGroup;
	}

	private class LttngMinimizedFileSystemElement extends FileSystemElement {

		private boolean populated = false;

		/**
		 * Create a <code>MinimizedFileSystemElement</code> with the supplied
		 * name and parent.
		 * 
		 * @param name
		 *            the name of the file element this represents
		 * @param parent
		 *            the containing parent
		 * @param isDirectory
		 *            indicated if this could have children or not
		 */
		public LttngMinimizedFileSystemElement(String name,
				FileSystemElement parent, boolean isDirectory) {
			super(name, parent, isDirectory);
		}

		/**
		 * Returns a list of the files that are immediate children. Use the
		 * supplied provider if it needs to be populated. of this folder.
		 */
		public AdaptableList getFiles(IImportStructureProvider provider) {
			if (!populated) {
				populate(provider);
			}
			return super.getFiles();
		}

		/**
		 * Returns a list of the folders that are immediate children. Use the
		 * supplied provider if it needs to be populated. of this folder.
		 */
		public AdaptableList getFolders(IImportStructureProvider provider) {
			if (!populated) {
				populate(provider);
			}
			return super.getFolders();
		}

		/**
		 * Return whether or not population has happened for the receiver.
		 */
		boolean isPopulated() {
			return this.populated;
		}

		/**
		 * Populate the files and folders of the receiver using the suppliec
		 * structure provider.
		 * 
		 * @param provider
		 *            org.eclipse.ui.wizards.datatransfer.
		 *            IImportStructureProvider
		 */
		private void populate(IImportStructureProvider provider) {

			Object fileSystemObject = getFileSystemObject();

			List children = provider.getChildren(fileSystemObject);
			if (children == null) {
				children = new ArrayList(1);
			}
			Iterator childrenEnum = children.iterator();
			while (childrenEnum.hasNext()) {
				Object child = childrenEnum.next();

				String elementLabel = provider.getLabel(child);
				// Create one level below
				LttngMinimizedFileSystemElement result = new LttngMinimizedFileSystemElement(
						elementLabel, this, provider.isFolder(child));
				result.setFileSystemObject(child);
			}
			setPopulated();
		}

		/**
		 * Set whether or not population has happened for the receiver to true.
		 */
		public void setPopulated() {
			this.populated = true;
		}
	}

}
