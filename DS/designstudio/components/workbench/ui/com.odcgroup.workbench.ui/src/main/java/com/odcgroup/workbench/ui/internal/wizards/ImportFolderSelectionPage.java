/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.odcgroup.workbench.ui.internal.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;


public class ImportFolderSelectionPage extends WizardPage {
	
	public ImportFolderSelectionPage(String pageName) {
		super(pageName);
		setTitle(pageName); //NON-NLS-1
		setDescription("A subfolder for the imported models will be automatically created in this folder"); //NON-NLS-1
	}
	
    /** (non-Javadoc)
     * Method declared on IDialogPage.
     */
	public void createControl(Composite parent) {
        initializeDialogUnits(parent);

        // Show description on opening
        setErrorMessage(null);
        setMessage(null);
        setControl(parent);
        setPageComplete(false);
	}
	
    /**
     * Returns whether this page's controls currently all contain valid 
     * values.
     *
     * @return <code>true</code> if all controls are valid, and
     *   <code>false</code> if at least one is invalid
     */
	protected boolean validatePage() {
        boolean valid = true;
        
       	valid = false;

        return valid;
    }
}
