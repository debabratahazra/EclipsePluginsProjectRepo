package com.odcgroup.t24.mdf.editor.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfClassPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.t24.applicationimport.T24Aspect;

public class T24MdfClassPage extends MdfClassPage {

	MdfClass klass = null;
	
	public T24MdfClassPage(MdfClass klass) {
		super(klass);
		this.klass = klass;
	}
	
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createControl(parent);
		if(getSuperClassSelector().getSelection() != null) {
			getText().setEnabled(false);
		}
	}
	
	public void modifyText(ModifyEvent e) {	
		super.modifyText(e);
		if(e.getSource() instanceof EntitySelector) {
			if (this.originalCopy!=null) {
				MdfDomain domain = (MdfDomain) ((EObject) this.originalCopy).eContainer();
				if (domain!=null) {
					boolean isLocal = T24Aspect.getLocalRefApplications((MdfDomain) domain);
					if (isLocal) {
						if (getSuperClassSelector().getSelection() == null) {
							getText().setEnabled(true);
						} else {
							String superClass = getSuperClassSelector().getSelection().toString();
							String className = superClass.substring(superClass.indexOf(":") + 1,superClass.length());
							getText().setText("X_" + className);
							getText().setEnabled(false);
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected MdfModelElement[] getCandidateSuperClasses() {
        List candidates = new ArrayList();
        List<MdfEntity> entities = MdfEcoreUtil.getAllEntities(((EObject)getInitialClass()).eResource());
        for (MdfEntity entity : entities) {
            if (entity instanceof MdfClass) {
            	MdfClass tempKlass = (MdfClass) entity;
                if (!isInHierarchy(tempKlass)) {
                	if(tempKlass.hasPrimaryKey())
                		candidates.add(entity);
                	
                }
            }
        }
        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
    }
}
