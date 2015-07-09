package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import java.util.Collections;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.internal.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowTabbedPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;

public class DecisionActionPropertiesSection extends PageflowTabbedPropertySection implements SelectionListener {
	
	private static final String actionUriToolTip = Messages.PropertiesActionURIToolTip;
	private Text uri;
	private Text actionName;

	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.diagram.custom.properties.sections.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected void configureProperties() {
		//addAttribute(new SectionAttributePropertyHelper(Messages.DecisionActionPropertiesSectionNameLabel, getTabItems()[0],PageflowPackage.eINSTANCE.getAction_Name(), PageflowPackage.eINSTANCE.getDecisionState_Action()));
		//addAttribute(new SectionAttributePropertyHelper(Messages.DecisionActionPropertiesSectionURILabel, getTabItems()[0],PageflowPackage.eINSTANCE.getAction_Uri(), PageflowPackage.eINSTANCE.getDecisionState_Action(), actionUriToolTip));
		addAttribute(new SectionAttributePropertyHelper(Messages.DecisionActionPropertiesSectionDescLabel, getTabItems()[0], PageflowPackage.eINSTANCE.getAction_Desc(), PageflowPackage.eINSTANCE.getDecisionState_Action(),true));
		addListReference(new SectionListReferencePropertyHelper(null, getTabItems()[1], PageflowPackage.eINSTANCE.getAction_Property(),PageflowPackage.eINSTANCE.getDecisionState_Action(), PageflowFactory.eINSTANCE,PageflowPackage.eINSTANCE.getProperty()));
	}

	@Override
	public String[] getTabItems() {
		return new String[]{Messages.DecisionActionPropertiesSectionDefTab, Messages.DecisionActionPropertiesSectionPropTab};
	}
	
	/**
	 * @param input
	 * @param original
	 * @return
	 */
	private boolean valideInput(String input, String original) {
		if (input != null && !input.trim().equals("") && !input.equals(original)) {
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.AbstractTabbedPropertiesSection#createEnumerationSpecialControls(org.eclipse.swt.widgets.Composite, java.lang.String)
	 */
	public void createEnumerationSpecialControls(Composite body, String tabName) {
		if (tabName.equals(getTabItems()[0])) {
			OFSUIFactory.INSTANCE.createLabel(body, "Name", actionUriToolTip, true);
			Composite comp = OFSUIFactory.INSTANCE.createComposite(body);
			GridLayout gridLayout = new GridLayout(3, false);
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 1;
			comp.setLayout(gridLayout);						
			actionName = OFSUIFactory.INSTANCE.createTextField(comp);	
			actionName.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                	final String actName = ((Text)e.widget).getText();
                	if (valideInput(actName, getActionName())) {
                    	TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();
                    	CommandUtil.execute(domain, "SetAction", getEObject(), new Runnable(){						
    						public void run() {	
    							Action action = null;
    							if (getEObject().eGet(PageflowPackage.eINSTANCE.getDecisionState_Action()) == null) {
        							action = PageflowFactory.eINSTANCE.createDecisionAction();
        							getEObject().eSet(PageflowPackage.eINSTANCE.getDecisionState_Action(), action);						
        						} 
    							action = (Action) getEObject().eGet(PageflowPackage.eINSTANCE.getDecisionState_Action());
    							action.eSet(PageflowPackage.eINSTANCE.getAction_Name(), actName);        						
    						}                		
                    	});		
                	}
                	refreshControls();
                }
            });
			Button selectButton = new Button(comp, 0x800000);
			selectButton.setToolTipText(Messages.SubPageflowGeneralPropertySectionBrowseTooltip);
			GridData gridData_1 = new GridData();
			gridData_1.heightHint = 20;
			selectButton.setLayoutData(gridData_1);
			selectButton.setText(Messages.SubPageflowGeneralPropertySectionBrowseBtnText);
			selectButton.addSelectionListener(this);
			
			OFSUIFactory.INSTANCE.createLabel(body, "URI", actionUriToolTip, true);					
			uri = OFSUIFactory.INSTANCE.createTextField(body);
			uri.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                	final String className = ((Text)e.widget).getText();
                	if (valideInput(className, getDecisionUri())) {
                		TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();
                    	CommandUtil.execute(domain, "SetActionURI", getEObject(), new Runnable(){						
    						public void run() {	
    							Action action = null;
    							if (getEObject().eGet(PageflowPackage.eINSTANCE.getDecisionState_Action()) == null) {	
        							action = PageflowFactory.eINSTANCE.createDecisionAction();
        							getEObject().eSet(PageflowPackage.eINSTANCE.getDecisionState_Action(), action);						
        						}
    							action = (Action) getEObject().eGet(PageflowPackage.eINSTANCE.getDecisionState_Action());
    							action.eSet(PageflowPackage.eINSTANCE.getAction_Property(), Collections.EMPTY_LIST);
    							action.eSet(PageflowPackage.eINSTANCE.getAction_Uri(), className);
    						}
                    	});
                	} 
            		refreshControls();              	
                }
            });
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
		
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		FilteredTypesSelectionDialog dialog = new FilteredTypesSelectionDialog(shell, false, PlatformUI.getWorkbench().getProgressService(), null, IJavaSearchConstants.TYPE);
		dialog.setTitle("Select Action Class"); 
		dialog.setMessage("Select a Decision Action"); 
		dialog.setInitialPattern(Signature.getSimpleName(uri.getText()));
		final int resultCode = dialog.open();
		if (resultCode == IDialogConstants.OK_ID) {
			IType result = (IType)dialog.getFirstResult();
			final String actName = result.getElementName();
			final String className = "class:"+result.getFullyQualifiedName();	
			TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();						
			CommandUtil.execute(domain, "SetActionURI", getEObject(), new Runnable(){						
				public void run() {		
					if (uri.getText() != null && getEObject().eGet(PageflowPackage.eINSTANCE.getDecisionState_Action()) != null){
						final String erlVal = uri.getText();
						Action action = (Action) getEObject().eGet(PageflowPackage.eINSTANCE.getDecisionState_Action());
						if (!erlVal.equals(className)){
							action.eSet(PageflowPackage.eINSTANCE.getAction_Property(), Collections.EMPTY_LIST);
						}
						action.eSet(PageflowPackage.eINSTANCE.getAction_Name(), actName);
						action.eSet(PageflowPackage.eINSTANCE.getAction_Uri(), className);
					} else if (getEObject().eGet(PageflowPackage.eINSTANCE.getDecisionState_Action()) == null) {	
						Action action = PageflowFactory.eINSTANCE.createDecisionAction();
						action.setName(actName);
						action.setUri(className);
						getEObject().eSet(PageflowPackage.eINSTANCE.getDecisionState_Action(), action);						
					} 
				}
			});					
			refreshControls();	
		}
		actionName.setText(getActionName());
		uri.setText(getDecisionUri());					
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.workbench.editors.propeties.AbstractBasicPropertiesSection#refreshControls()
	 */
	protected void refreshControls() {
		super.refreshControls();
		actionName.setText(getActionName());
		uri.setText(getDecisionUri());
		validateModel();
	}
	
	/**
	 * @return
	 */
	protected String getActionName() {
		Object obj = getEObject().eGet(
				PageflowPackage.eINSTANCE.getDecisionState_Action());
		if (obj != null && obj instanceof Action) {
			String str = ((Action)obj).getName();
			if (str != null){
				return str;
			}
		}
		return "";		
	}

	/**
	 * @return
	 */
	protected String getDecisionUri() {
		Object obj = getEObject().eGet(
				PageflowPackage.eINSTANCE.getDecisionState_Action());
		if (obj != null && obj instanceof Action) {
			if( ((Action)obj).getUri() != null) {
				return ((Action)obj).getUri();
			} else {
				return "";
			}
		}
		return "";
	}
}
