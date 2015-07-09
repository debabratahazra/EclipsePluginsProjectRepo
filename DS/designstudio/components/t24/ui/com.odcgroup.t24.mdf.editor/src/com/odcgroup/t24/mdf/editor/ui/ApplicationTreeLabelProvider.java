package com.odcgroup.t24.mdf.editor.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.schema.InputBehaviour;

/**
 *
 * @author phanikumark
 *
 */
public class ApplicationTreeLabelProvider extends MdfTreeLabelProvider {
	
	private MdfClass application = null;
	private MdfClass extendApplication = null;


	public ApplicationTreeLabelProvider(MdfClass application) {
		super();
		this.application = application;
	}
	
	public ApplicationTreeLabelProvider() {
		super();
	} 
	
	public Image getImage(Object item) {
        if (item instanceof MdfAttribute) {
        	MdfAttribute attr = (MdfAttribute) item;
        	if (attr.isPrimaryKey()) {
                return MdfPlugin.getImage("icon_key");        		
        	}
        	if (attr.isRequired()) {
                return MdfPlugin.getImage("FieldMandatory");
        	} 
        	InputBehaviour beh = T24Aspect.getInputBehaviour(attr);
        	if (beh != null && (beh.equals(InputBehaviour.F) || beh.equals(InputBehaviour.N))) {
        		return MdfPlugin.getImage("FieldNoInputable");
        	}
        } else if (item instanceof MdfAssociation) {
        	MdfAssociation assc = (MdfAssociation) item;
        	if (assc.getContainment() == MdfContainment.BY_REFERENCE && assc.isPrimaryKey()) {
                return MdfPlugin.getImage("icon_key");          		
        	} else if (assc.getContainment() == MdfContainment.BY_REFERENCE && assc.isRequired()) {
        		return MdfPlugin.getImage("RefFieldMandatory");
        	} else if (assc.getContainment() == MdfContainment.BY_VALUE && assc.isRequired()) {        		
        		return MdfPlugin.getImage("MultiValueMadatory");        		
        	} else if (isIsolatedGroup(assc)) {
    			List<?> children = getProperties((MdfClass) assc.getType());
        		return getImage(children.get(0));      		
        	} else if (assc.getContainment() == MdfContainment.BY_REFERENCE) {
        		InputBehaviour beh = T24Aspect.getInputBehaviour(assc);
            	if (beh != null && (beh.equals(InputBehaviour.F) || beh.equals(InputBehaviour.N))) {
            		return MdfPlugin.getImage("FKeyInputable");
            	}
        	}
        }
        return super.getImage(item);
    }
	

	@Override
    public String getText(Object item) {
    	if(item == null){
    		return new String();
    	}
    	String name = null;
        if (item instanceof MdfModelElement) {
        	if (item instanceof MdfAssociation) {
        		MdfAssociation association = (MdfAssociation) item;
				return getGroupName(association);
        	}  
        }  
        if (name == null) {
        	name = getT24Name((MdfModelElement) item);
        }
        return name;
    }
	
	@SuppressWarnings("unchecked")
	protected List<?> getProperties(MdfClass klass) {
		List<?> props = new ArrayList<Object>();
		props.addAll(klass.getProperties(true));
		return props;
	}
	
	private boolean isGroup(MdfAssociation association) {
		if (association.getContainment() == MdfContainment.BY_VALUE) {
			return true;
		}
		return false;
	}
	
	private String getGroupName(MdfAssociation association) {
		if (isGroup(association)) {
			List<?> children = getProperties((MdfClass) association.getType());
			if (children.size() > 1) {
				EObject eObj = ((EObject) association).eContainer();
				if (checkIsParentApplication(eObj)) {
					return "MV-GROUP";
				} else  {
					return "SV-GROUP";
				}
			} else if (children.size() == 1) {
				return getT24Name((MdfModelElement)children.get(0));
			} 
		} 
		return getT24Name(association);
	}
	
	private boolean checkIsParentApplication(EObject eObj) {
		if (eObj.equals(application) || (extendApplication != null && eObj.equals(extendApplication))) {
			return true;
		}
		return false;
	}
	
	private String getT24Name(MdfModelElement element) {
		String name = T24Aspect.getT24Name(element);
		if (name == null) {
			name = super.getText(element);
			name = name.replace("_", ".");
		}
		return name;
	}
	
	private boolean isIsolatedGroup(MdfAssociation association) {
		if (isGroup(association)) {
			List<?> children = getProperties((MdfClass) association.getType());
			if (children.size() == 1) {
				MdfProperty property = (MdfProperty) children.get(0);
				if (property instanceof MdfAttribute) {
					return true;
				} else if(property instanceof MdfAssociation) {
					MdfAssociation assc = (MdfAssociation) property;
					List<?> aChildren = getProperties((MdfClass) assc.getType());
					if (aChildren.size() == 1) {
						return true;
					}
				}
			}
		}
		return false;		
	}
	
	/**
	 * @param application the application to set
	 */
	public void setExtendedApplication(MdfClass application) {
		this.extendApplication = application;
	}

}
