package com.odcgroup.t24.mdf.editor.ui.wizardpage;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.AbstractDomainCreationPage;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.workbench.core.resources.OfsModelResource;

/**
 * @author ssreekanth
 * @author hdebabrata
 */
public class T24DomainCreationPage extends AbstractDomainCreationPage {
	
	private Button t24Check;
	private Button regularCheck;
	private static final String NAMESPACE = "http://www.temenos.com/t24";
	private static final String NAME = "LocalFields";

	/**
	 * @param pageName
	 * @param workbench
	 * @param containerFullPath
	 * @param initialDomain
	 * @param copyPage
	 * @param resource
	 */
	public T24DomainCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, MdfDomain initialDomain, boolean copyPage,
			OfsModelResource resource) {
		super(pageName, workbench, containerFullPath, initialDomain, copyPage, resource);
	}

	@Override
	protected void createCustomPropertiesControl(Composite parent) {
		Group modifiers = createGroup(parent, "Domain Type", 1);
		regularCheck = createButton(modifiers, "Regular domain model", SWT.RADIO);
		new Label(modifiers, SWT.NONE).setText("     Where you can design any kind of domain element such as classes or enumerations.");
		t24Check = createButton(modifiers, "T24 Application Extension Domain", SWT.RADIO);
		new Label(modifiers, SWT.NONE).setText("     Only for extending existing application(s) with reference to local field definations.");
	}
	
	/**
	 * @param parent
	 * @param text
	 * @param style
	 * @return
	 */
	private Button createButton(Composite parent, String text, int style) {
		Button button = new Button(parent, style | SWT.FLAT);
		button.setBackground(parent.getBackground());
		button.setForeground(parent.getForeground());
		if (text != null) {
			button.setText(text);
		}
		button.addSelectionListener(selectionListener);
		return button;
	}

	@Override
	protected void setCustomProperties(MdfDomain domain) {
		if (t24Check.getSelection()) {
   			T24Aspect.setLocalRefApplications((MdfDomainImpl)domain, true);
		}else if(regularCheck.getSelection()){
			T24Aspect.removeAnnotation((MdfDomainImpl)domain, NAMESPACE, NAME);
		}
	}
	
	@Override
	public boolean isPageComplete() {
		if(!(regularCheck.getSelection() || t24Check.getSelection())){
			setErrorMessage("Please select Domain Type");
		}
		return (super.isPageComplete() && (regularCheck.getSelection() || t24Check.getSelection()));
	}
}
