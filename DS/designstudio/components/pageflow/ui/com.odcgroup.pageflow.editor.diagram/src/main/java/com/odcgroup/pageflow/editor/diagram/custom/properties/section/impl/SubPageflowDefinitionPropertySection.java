package com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.PageflowSelectionDialog;
import com.odcgroup.pageflow.editor.diagram.custom.dialog.TransitionMappingDefinitionDialog;
import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowModelLookup;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.util.ListReferenceColumn;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.ResourceUtil;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class SubPageflowDefinitionPropertySection extends PageflowBasicPropertySection 
	implements SelectionListener {

	private Text pageflowName;
	private Button selectButton;
	//private static final String PAGEFLOW_EDITOR_ID = "com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorID";

	
	/* (non-Javadoc)
	 * @see com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowBasicPropertySection#configureProperties()
	 */
	protected void configureProperties() {
		SectionListReferencePropertyHelper tMappings= new SectionListReferencePropertyHelper(Messages.SubPageflowGeneralPropertySectionMappingLabel, PageflowPackage.eINSTANCE.getSubPageflowState_TransitionMappings(), null,
				PageflowFactory.eINSTANCE, PageflowPackage.eINSTANCE.getTransitionMapping());
		List<ListReferenceColumn> columns = new ArrayList<ListReferenceColumn>();
		columns.add(new ListReferenceColumn(Messages.SubPageflowDefinitionPropertySectionEndStateLabel, PageflowPackage.eINSTANCE.getTransitionMapping_EndState().getName(), PageflowPackage.eINSTANCE.getState_DisplayName()));		
		columns.add(new ListReferenceColumn(Messages.SubPageflowDefinitionPropertySectionTransitionLabel, PageflowPackage.eINSTANCE.getTransitionMapping_Transition().getName(), PageflowPackage.eINSTANCE.getTransition_DisplayName()));
		tMappings.setColumns(columns);
		addListReference(tMappings);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.AbstractBasicPropertiesSection#getPopuopDialog(org.eclipse.swt.widgets.Shell)
	 */
	public AbstractTitleAreaDialog getPopuopDialog(Shell shell) {
		return new TransitionMappingDefinitionDialog(shell);
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.AbstractBasicPropertiesSection#createSpecialControls(org.eclipse.swt.widgets.Composite)
	 */
	public Composite createSpecialControls(Composite body) {
		
		Composite comp = OFSUIFactory.INSTANCE.createComposite(body);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 1;
		comp.setLayout(gridLayout);
		OFSUIFactory.INSTANCE.createLabel(comp, Messages.SubPageflowGeneralPropertySectionGrpLabel, Messages.SubPageflowGeneralPropertySectionGrpTooltip, true);
		pageflowName = OFSUIFactory.INSTANCE.createTextField(comp);
		pageflowName.setEditable(false);
		selectButton = new Button(comp, 0x800000);
		selectButton.setToolTipText(Messages.SubPageflowGeneralPropertySectionBrowseTooltip);
		selectButton.addSelectionListener(this);
		GridData gridData_1 = new GridData();
		gridData_1.heightHint = 20;
		selectButton.setLayoutData(gridData_1);
		selectButton.setText(Messages.SubPageflowGeneralPropertySectionBrowseBtnText);
		/*
		launchButton = new Button(comp, 0x800000);
		launchButton.setImage(OFSPropertyPlugIn.getBundledImageDescriptor(
				"icons/obj16/open.gif").createImage());
		GridData gridData_3 = new GridData();
		gridData_3.heightHint = 20;
		launchButton.setLayoutData(gridData_3);
		launchButton.setToolTipText("Open selected Pageflow");
		launchButton.addSelectionListener(this);
		*/
		return comp;
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
		if (e.widget == selectButton) {

			TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();
			// incase of readonly file
			if (!CommandUtil.makeReadOnlyWriteable(getEObject(), domain)) {
				return;
			}
			// browse button - browse for pageflow resources
			IFile pageflowFile = ResourceUtil.getFile(getEObject());
			IProject project = pageflowFile.getProject();

			IOfsProject repository = OfsCore.getOfsProjectManager().getOfsProject(project);
            PageflowModelLookup lookup = new PageflowModelLookup(repository);
            List<Pageflow> pageflows = lookup.getAllPageflows();
            Pageflow pageflow = (Pageflow)getEObject().eContainer();
            // filter pageflow with the current one
            for (Pageflow pfl : pageflows) {
            	if (pageflow.getId().equals(pfl.getId()) && pageflow.getName().equals(pfl.getName())){
            		pageflows.remove(pfl);
            		break;
            	}
			}

            PageflowSelectionDialog dialog = PageflowSelectionDialog.createDialog(getShell(), pageflows);
			dialog.setMultipleSelection(false);

			if (dialog.open() == 0) {
				final Pageflow root = (Pageflow) dialog.getFirstResult();						
				CommandUtil.execute(domain, "SubPageflowChange", getEObject(), new Runnable(){
					public void run() {
						// check for any existing mappings and remove the same
						getEObject().eSet(PageflowPackage.eINSTANCE.getSubPageflowState_TransitionMappings(), Collections.EMPTY_LIST);									
						getEObject().eSet(PageflowPackage.eINSTANCE.getSubPageflowState_SubPageflow(), root);
					}
				});
				refreshControls();

				pageflowName.setText(getSubPageflowName());
			}
		} 
		/*
		else if (e.widget == launchButton) {
			// launch button - launch the selected pageflow diagram
			Object obj = getEObject()
					.eGet(
							PageflowPackage.eINSTANCE
									.getSubPageflowState_SubPageflow());
			if (obj != null && obj instanceof Pageflow) {
				IFile pageflow = ResourceUtil.getFile((EObject) obj);
				IProject project = pageflow.getProject();
				String filename = pageflow.getName();
				if (filename.lastIndexOf('.') != -1) {
					filename = filename.substring(0, filename.lastIndexOf('.'));
				}
				IFile pfl = project.getFolder("pageflow").getFile(
						filename + ".pageflow");
				if (pfl.exists()) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().openEditor(
										new FileEditorInput(pfl),
										PAGEFLOW_EDITOR_ID, true);
					} catch (PartInitException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		*/

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.workbench.editors.propeties.AbstractBasicPropertiesSection#refreshControls()
	 */
	protected void refreshControls() {
		super.refreshControls();
		pageflowName.setText(getSubPageflowName());
		validateModel();
	}

	/**
	 * @return
	 */
	protected String getSubPageflowName() {
		Object obj = getEObject().eGet(
				PageflowPackage.eINSTANCE.getSubPageflowState_SubPageflow());
		if (obj != null && obj instanceof Pageflow) {
			Pageflow subPageflow = (Pageflow) obj;
			Resource resource = subPageflow.eResource();
			if(resource!=null) {
				return resource.getURI().toString();
			} else {
				if (subPageflow.eIsProxy()) {
					InternalEObject eObj = (InternalEObject) obj;
					return eObj.eProxyURI().trimFragment().toString();
				}
			}
		}
		return "";
	}



}
