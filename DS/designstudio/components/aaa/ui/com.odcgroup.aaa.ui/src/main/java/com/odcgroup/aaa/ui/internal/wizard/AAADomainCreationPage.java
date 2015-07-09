package com.odcgroup.aaa.ui.internal.wizard;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.AbstractDomainCreationPage;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.resources.OfsModelResource;

/**
 * @author phanikumark
 *
 */
public class AAADomainCreationPage extends AbstractDomainCreationPage {
	
	private Button udeCheck;
	private Button regularCheck;

	/**
	 * @param pageName
	 * @param workbench
	 * @param containerFullPath
	 * @param initialDomain
	 * @param copyPage
	 * @param resource
	 */
	public AAADomainCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, MdfDomain initialDomain, boolean copyPage,
			OfsModelResource resource) {
		super(pageName, workbench, containerFullPath, initialDomain, copyPage, resource);
	}

	@Override
	protected void createCustomPropertiesControl(Composite parent) {
		Group modifiers = createGroup(parent, "Domain Type", 1);
		regularCheck = createButton(modifiers, "Regular domain model", SWT.RADIO);
		new Label(modifiers, SWT.NONE).setText("     Where you can design any kind of domain element such as classes or enumerations.");
		udeCheck = createButton(modifiers, "Triple'A user-defined entities domain", SWT.RADIO);
		new Label(modifiers, SWT.NONE).setText("     Only for extending meta-dictionary with classes and enumerations.");
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
		if (udeCheck.getSelection()) {
   			AAAAspectDS.setTripleAUDEntities((MdfDomain) domain, true);
		}else if(regularCheck.getSelection()){
			AAAAspectDS.removeTAPDomainAnnotation((MdfDomain)domain);
		}
	}
	
	@Override
	public boolean isPageComplete() {
		if(!(regularCheck.getSelection() || udeCheck.getSelection())){
			setErrorMessage("Please select Domain Type");
		}
		return (super.isPageComplete() && (regularCheck.getSelection() || udeCheck.getSelection()));
	}
}
