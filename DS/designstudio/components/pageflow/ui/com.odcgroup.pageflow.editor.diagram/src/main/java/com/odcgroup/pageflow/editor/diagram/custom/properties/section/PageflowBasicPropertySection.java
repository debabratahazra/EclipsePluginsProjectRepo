package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.workbench.editors.properties.AbstractBasicPropertiesSection;

/**
 * @author pkk
 *
 */
public abstract class PageflowBasicPropertySection extends
		AbstractBasicPropertiesSection {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.ofs.workbench.editors.propeties.AbstractBasicPropertiesSection#configureAttributes()
	 */
	protected abstract void configureProperties();

	
	/* (non-Javadoc)
	 * @see com.odcgroup.ofs.workbench.editors.propeties.AbstractBasicPropertiesSection#getReferenceObject(org.eclipse.emf.ecore.EClass)
	 */
	public EFactory getEFactory() {
		return PageflowFactory.eINSTANCE;
	}
		
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.propeties.AbstractBasicPropertiesSection#checkIsTaken(java.lang.String)
	 */
	public boolean checkIsTaken(Object key, EStructuralFeature feature){
		EObject object = getEObject().eContainer();
		if (object instanceof Pageflow) {
			Pageflow pageflow = (Pageflow)object;
			if (pageflow.getName().equals(key)){
				return true;
			}
			EList list = pageflow.eContents();
			for(Iterator iter = list.iterator();iter.hasNext();){
				Object obj = iter.next();
				if (obj instanceof State && getEObject() instanceof State){
					State state = (State)obj;
					if (state.eGet(feature) != null && state.eGet(feature).equals(key)){
						return true;
					}
				} 
				/*
				 else if (obj  instanceof Transition && getEObject() instanceof Transition){
					Transition ref = (Transition)getEObject();
					Transition trans = (Transition)obj;
					if (!ref.equals(trans)&& ref.getFromState().equals(trans.getFromState()) && trans.eGet(feature) != null && trans.eGet(feature).equals(key)){
						return true;
					}					
				}
				*/
			}
		}
		return false;
	}

}
