package com.odcgroup.t24.version.editor.ui;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.controls.APITabControl;
import com.odcgroup.t24.version.editor.ui.controls.AttributesTabControl;
import com.odcgroup.t24.version.editor.ui.controls.ConnectTabControl;
import com.odcgroup.t24.version.editor.ui.controls.DescriptionTabControl;
import com.odcgroup.t24.version.editor.ui.controls.IVersionDataBindingControl;
import com.odcgroup.t24.version.editor.ui.controls.OtherTabControl;
import com.odcgroup.t24.version.editor.ui.controls.PresentationTabControl;
import com.odcgroup.t24.version.editor.ui.controls.RelationshipsTabControl;
import com.odcgroup.t24.version.editor.ui.controls.TransactionFlowTabControl;
import com.odcgroup.t24.version.editor.ui.controls.VersionScreenControl;
import com.odcgroup.t24.version.editor.ui.controls.WebserviceTabControl;

public class VersionFormPage extends FormPage {
	
	private VersionDesignerEditor editor;
	private DataBindingContext context;
	
	private List<IVersionDataBindingControl> tabControls = new ArrayList<IVersionDataBindingControl>();

	/**
	 * @param editor
	 */
	public VersionFormPage(VersionDesignerEditor editor) {
		super(editor, "third", "Screen Editior");
		this.editor = editor;
	}

	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		context = new EMFDataBindingContext();
		
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Screen Form");
		
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		managedForm.getForm().getBody().setData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = layout.marginHeight = 0;
		managedForm.getForm().getBody().setLayout(layout);
		
		TabFolder tabFolder = new TabFolder(managedForm.getForm().getBody(), SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().adapt(tabFolder);
		managedForm.getToolkit().paintBordersFor(tabFolder);
		tabFolder.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_VersionForm"));

		createTab(tabFolder, "Screen", VersionScreenControl.class);
		createTab(tabFolder, "Description", DescriptionTabControl.class);
		createTab(tabFolder, "Transaction Flow", TransactionFlowTabControl.class);
		createTab(tabFolder, "Presentation", PresentationTabControl.class);
		createTab(tabFolder, "Relationship", RelationshipsTabControl.class);
		createTab(tabFolder, "API", APITabControl.class);
		createTab(tabFolder, "Attributes", AttributesTabControl.class);
		createTab(tabFolder, "Web Services", WebserviceTabControl.class);
		createTab(tabFolder, "Connect", ConnectTabControl.class);
		createTab(tabFolder, "Other", OtherTabControl.class);
		
		IContextService contextService = (IContextService)getSite().getService(IContextService.class);
		if(contextService != null) {
			contextService.activateContext(VersionEditorActivator.CONTEXT_ID);
		}
		
		form.reflow(true);
	}
	
	protected void createTab(TabFolder tabFolder, String name, Class<?> clazz) {
		try {
			Constructor<?> c = clazz.getConstructor(Composite.class, VersionDesignerEditor.class, DataBindingContext.class);
			IVersionDataBindingControl control = (IVersionDataBindingControl)c.newInstance(tabFolder, this.editor, this.context);
			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(name);
			tabItem.setControl(control.getControl());
			tabControls.add(control);
		} catch (Exception ex) {
			VersionEditorActivator.getDefault().logError("Error when creating tab "+name, ex);
		}
	}
	
	public VersionDesignerEditor getEditor() {
		return editor;
	}
	
	/**
	 * 
	 */
	public void refreshBindings() {
		for (IVersionDataBindingControl tabControl : tabControls) {
			if (tabControl != null) {
				tabControl.initDataBindings(context);
			}
		}
	}
	
	/**
	 * 
	 */
	public void refreshControls() {
		for (IVersionDataBindingControl tabControl : tabControls) {
			if (tabControl != null) {
				tabControl.refreshControls();
			}
		}
	}
}
