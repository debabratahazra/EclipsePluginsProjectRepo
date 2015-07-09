package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public abstract class MdfModelPage extends DialogPage implements
        ModifyListener, SelectionListener {

    protected MdfModelElement workingCopy = null;
    protected final MdfModelElement originalCopy;

    /**
     * Constructor for MdfModelPage
     */
    public MdfModelPage(MdfModelElement originalCopy) {
        super();
        this.originalCopy = originalCopy;
    }

    public void setWorkingCopy(MdfModelElement copy) {
        workingCopy = copy;
    }

    private void dialogChanged() {
        updateStatus(true);
        setDirty(true);
    }
    
    protected void updateStatus() {
    	updateStatus(false);
    }

    protected void updateStatus(boolean copy) {
    	EObject model = (EObject) ((copy) ? workingCopy : originalCopy);
    	Diagnostic diagnostic = Diagnostician.INSTANCE.validate(model);
    	setStatus(BasicDiagnostic.toIStatus(diagnostic));
    }
    
    /**
     * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
     */
    public void modifyText(ModifyEvent e) {
        dialogChanged();
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
        widgetSelected(e);
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        dialogChanged();
    }

}
