package com.temenos.t24.tools.eclipse.basic.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.T24LabelsProvider;
import com.temenos.t24.tools.eclipse.basic.views.macrotemplate.TemplateActions;
import com.temenos.t24.tools.eclipse.basic.views.macrotemplate.TemplateUtil;

/**
 * The first page of the Extract Strings wizard displays Source File and
 * Destination File text fields, each with a browse button to the right.
 */
@SuppressWarnings("restriction")
public class SelectTemplateWizardPage extends WizardPage {

	private final int SPACING = 10; // space between elements in form layout
	private static final int COLUMN_WIDTH = 500;
	private static final int TABLE_HEIGHT = 400;

	private TemplateUtil templateUtil = null;
	private TableViewer tableViewer = null;
	private IT24ViewItem templateSelected = null;
	private boolean templateAlreadyOpened = false;
	private String projectFullPath = null;

	public SelectTemplateWizardPage() {
		super("selectTemplate");
		setTitle("Select template");
		setDescription("Select the appropriate template and click finish.");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		templateUtil = new TemplateUtil();
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = SPACING;
		formLayout.marginHeight = SPACING;
		container.setLayout(formLayout);
		setControl(container);

		// INSTANCIATE VIEWER
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.SIMPLE
				| SWT.V_SCROLL | SWT.H_SCROLL);

		// SET TABLE ELEMENTS
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		FormData formData = new FormData();
		formData.height = TABLE_HEIGHT;
		formData.left = new FormAttachment(0, SPACING);
		formData.top = new FormAttachment(0, SPACING);
		table.setLayoutData(formData);

		String columnName = "Templates";
		int columnAlignment = SWT.CENTER;
		TableColumn column = new TableColumn(table, columnAlignment, 0);
		column.setText(columnName);
		column.setWidth(COLUMN_WIDTH);

		// SET LABEL, CONTENT AND INPUT PROVIDERS
		tableViewer.setLabelProvider(new T24LabelsProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());

		// Add items to tableViewer

		tableViewer.setInput(templateUtil.getTemplatesStored());

		// Associate listeners to each element
		addListeners();

		setMessage(null);
		setErrorMessage(null);
		setPageComplete(true);
	}

	/**
	 * Links a listener to changes on other active pages, so it can react to
	 * them.
	 */
	private void addListeners() {
		// SELECT A TEMPLATE WITH MOUSE
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) event
								.getSelection();
						templateSelected = (IT24ViewItem) selection
								.getFirstElement();
					}
				});
		// DOUBLE CLICK AND RUN TEMPLATE
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				templateSelected = (IT24ViewItem) selection.getFirstElement();
				TemplateActions ta = new TemplateActions();
				ta.openTemplate(templateSelected, projectFullPath);
				setTemplateOpened(true);
			}
		});
	}

	/**
	 * Called by the wizard to initialize the receiver's cached selection.
	 * 
	 * @param selection
	 *            the selection or <code>null</code> if none
	 */
	public void init(ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return;
		getSelectedProject(selection);

	}

	public void setTemplateOpened(boolean opened) {
		this.templateAlreadyOpened = opened;
	}

	public boolean isTemplateAlreadyOpenend() {
		return this.templateAlreadyOpened;
	}

	/**
	 * @return the template selected with the mouse
	 */
	public IT24ViewItem getTemplateSelected() {
		return this.templateSelected;
	}

	/**
	 * Gets the project path where the TEMPLATE files are to be created.
	 */
    private void getSelectedProject(ISelection selection) {
        if (selection != null && selection instanceof IStructuredSelection) {
            Object objectSelected = (Object) ((IStructuredSelection) selection).getFirstElement();
            if (objectSelected == null) {
                projectFullPath = EclipseUtil.getTemporaryProject().toOSString();
            }
            if (objectSelected != null) {
                if (objectSelected instanceof IResource) {
                    IResource resource = ((IResource) objectSelected);
                    if (resource instanceof IFolder) {
                        projectFullPath = resource.getLocation().toOSString();
                    } else {
                        projectFullPath = resource.getProject().getLocation().toOSString();
                    }
                } else if (objectSelected instanceof PackageFragment) {
                    projectFullPath = ((IPackageFragment) objectSelected).getResource().getLocation().toOSString();
                } else if (objectSelected instanceof PackageFragmentRoot) {
                    projectFullPath = ((IPackageFragmentRoot) objectSelected).getResource().getLocation().toOSString();
                }
            }
        }
    }

}
