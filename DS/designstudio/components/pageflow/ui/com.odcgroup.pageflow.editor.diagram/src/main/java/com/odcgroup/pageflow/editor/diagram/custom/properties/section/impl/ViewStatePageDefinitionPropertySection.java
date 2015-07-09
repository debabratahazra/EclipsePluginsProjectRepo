package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.model.Model;
import com.odcgroup.pageflow.editor.diagram.custom.pageintegration.PageModelLookup;
import com.odcgroup.pageflow.editor.diagram.custom.pageintegration.PageSelectionDialog;
import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.View;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.ui.navigator.pageflow.PageflowAdapterFactoryProvider;
import com.odcgroup.workbench.ui.repository.ModelSelectionDialog;

/**
 * @author pkk
 *
 */
public class ViewStatePageDefinitionPropertySection extends PageflowBasicPropertySection  implements SelectionListener{
	
	protected EReference reference = PageflowPackage.eINSTANCE.getViewState_View();
	private static Text viewUri;
	private Button selectButton;
	private Composite body;
	private Button dsButton;
	private Button ocsButton;
	
	private static String VIEW_CHANGE_COMMAND = "ViewStateChange";
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */	
	protected void configureProperties() {
		// none
	}
	
	@Override
	public Composite createSpecialControls(Composite comp) {
		body = comp;
		Composite choiceComp = OFSUIFactory.INSTANCE.createComposite(body,2,0);
		OFSUIFactory.INSTANCE.createLabel(choiceComp, Messages.ViewStateViewDefinitionSectionViewTypeLabel);
		choiceComp.setBackground(body.getBackground());
		         
		Composite buttonComp = OFSUIFactory.INSTANCE.createComposite(choiceComp,2,0);
		buttonComp.setBackground(choiceComp.getBackground());
		dsButton = new Button(buttonComp, SWT.RADIO);
		
		dsButton.setText(Messages.ViewStateViewDefinitionDSTypeLabel);
		dsButton.setSelection(true);
		dsButton.setBackground(buttonComp.getBackground());
		dsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleButtonSelection(e);
			}
		});
		ocsButton = new Button(buttonComp, SWT.RADIO);
		ocsButton.setText(Messages.ViewStateViewDefinitionOCSTypeLabel);
		ocsButton.setBackground(buttonComp.getBackground());
		ocsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleButtonSelection(e);
			}
		});
		
		Composite composite = OFSUIFactory.INSTANCE.createComposite(body);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 1;
		composite.setLayout(gridLayout);

		OFSUIFactory.INSTANCE.createLabel(composite, Messages.ViewStateTechnicalPropertySectionURILabel+"* :");
		viewUri = OFSUIFactory.INSTANCE.createTextField(composite);
		viewUri.setEditable(false);
		viewUri.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e) {
				String input = viewUri.getText();
				if ((input !=null) && (input.trim().length()!=0) && !input.equals(getViewURI())){
					TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();	
					CommandUtil.execute(domain, VIEW_CHANGE_COMMAND, getEObject(), new Runnable(){
						public void run() {	
							View view = PageflowFactory.eINSTANCE.createView();
							view.setUrl(viewUri.getText());
							getEObject().eSet(PageflowPackage.eINSTANCE.getViewState_View(), view);
						}
					});
					refreshControls();
				}
			}
		});
		selectButton = new Button(composite, 0x800000);
		selectButton.setToolTipText(Messages.ViewStateViewDefinitionBrowseToolTip);
		selectButton.addSelectionListener(this);
		GridData gridData_1 = new GridData();
		gridData_1.heightHint = 20;
		selectButton.setLayoutData(gridData_1);
		selectButton.setText(Messages.ViewStateViewDefinitionBrowse);
				
		return body;
	}
	
	/**
	 * @param e
	 */
	public void handleButtonSelection(SelectionEvent e) {
		Button temp = (Button) e.widget;
		if((temp.getSelection() && getViewURI()!=null) && (getViewURI().trim().length()!=0)){
			boolean okpressed = MessageDialog.openConfirm(getShell(),  Messages.ViewStateViewDefinitionConfirmDialogTitle, 
					NLS.bind(Messages.ViewStateViewDefinitionConfirmDialogMsg, getViewURI()));
				if (okpressed){
					TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();
					IStatus status = CommandUtil.execute(domain, VIEW_CHANGE_COMMAND, getEObject(), new Runnable() {
						public void run() {
							View view = PageflowFactory.eINSTANCE.createView();
							view.setUrl("");
							getEObject().eSet(PageflowPackage.eINSTANCE.getViewState_View(), view);
						}						
					});
					if (CommandUtil.isStatusCancelled(status)){
						temp.setSelection(false);
						refreshControls();
					}
				}else{
					temp.setSelection(false);
					refreshControls();
				}
		}
		doCheck();	
	}
	
	/**
	 * 
	 */
	public void doCheck(){
		if(ocsButton.getSelection()){	
			viewUri.setEditable(true);	
			selectButton.setVisible(false);
		}else {
			viewUri.setEditable(false);
			selectButton.setVisible(true);
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();
		//incase of readonly file
		if (!CommandUtil.makeReadOnlyWriteable(getEObject(), domain)) {
			return;
		}
		if (e.widget == selectButton) {
			// browse button - browse for pageflow resources
			IOfsProject repository = OfsResourceHelper.getOfsProject(getEObject().eResource());
			PageModelLookup lookup = new PageModelLookup(repository);
            List<Model> pages = lookup.getAllPages();
           

            AdapterFactory adapterFactory = PageflowAdapterFactoryProvider.getAdapterFactory();

            ModelSelectionDialog dialog = PageSelectionDialog.createDialog(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    pages.toArray(), adapterFactory);

            dialog.setTitle(Messages.ViewStateViewDefinitionModelSelectionDialogTitle);
			dialog.setMultipleSelection(false);

			if (dialog.open() == 0) {
				final Model root = (Model) dialog.getFirstResult();
				CommandUtil.execute(domain, VIEW_CHANGE_COMMAND, getEObject(), new Runnable(){
					public void run() {
						View view = PageflowFactory.eINSTANCE.createView();
						view.setUrl(root.eResource().getURI().toString());
						getEObject().eSet(PageflowPackage.eINSTANCE.getViewState_View(), view);						
					}					
				});
				refreshControls();
				viewUri.setText(root.eResource().getURI().toString());
			}
		} 
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.workbench.editors.propeties.AbstractBasicPropertiesSection#refreshControls()
	 */
	protected void refreshControls() {
		super.refreshControls();
		String view = getViewURI();
		if (!view.equals("") && view.startsWith("resource:")){
			if (view.endsWith(".page") || view.endsWith(".module")){
				dsButton.setSelection(true);
				ocsButton.setSelection(false);
			}
		} else {
			dsButton.setSelection(false);
			ocsButton.setSelection(true);			
		}
		doCheck();
		viewUri.setText(getViewURI());
		validateModel();
	}

	/**
	 * @return
	 */
	protected String getViewURI() {
		Object obj = getEObject().eGet(
				PageflowPackage.eINSTANCE.getViewState_View());
		if (obj != null && obj instanceof View) {
			return ((View)obj).getUrl();
		}
		return "";
	}
	
}
