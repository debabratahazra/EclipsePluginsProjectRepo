package com.odcgroup.workbench.ui.navigator.process;

import com.odcgroup.process.model.NamedElement;
import com.odcgroup.workbench.ui.navigator.AbstractModelLabelProvider;


public class ProcessLabelProvider extends AbstractModelLabelProvider {
	 
	/**
	 * OCS-24647 (Change extension process to workflow)
	 */
	public ProcessLabelProvider(){
	    super(ProcessAdapterFactoryProvider.getAdapterFactory(), "workflow");
	  }
	 
	  public String getText(Object object) {
    	if(object instanceof com.odcgroup.process.model.Process) {
    		com.odcgroup.process.model.Process process = (com.odcgroup.process.model.Process) object;
	    	String name = process.getDisplayName();
	    	if(name==null || name.length()==0) {
	    		name = process.getName();
	    	}
	    	return name;
    	}
    	if(object instanceof NamedElement) {
	    	NamedElement namedElement = (NamedElement) object;
	    	/*
	    	if (namedElement instanceof Task){
	    		Localizable localizable = (Localizable)(ProcessLocalizableAdapterFactory.INSTANCE.adapt((Task)namedElement, Localizable.class));
	    		LocalizableElement lElement = localizable.getLocalizableElements().get(0);		
	    		String text = lElement.getMessage(TranslationServiceFactory.getDefaultLocale());
	    		if (text == null || text.length() == 0) {
	    			return namedElement.getName();
	    		}
	    		return text;
	    	}
	    	*/
	    	String name = namedElement.getName();
	    	if(name==null || name.length()==0) {
	    		name = namedElement.getID();
	    	}
	    	return name;
    	}
	    return super.getText(object);
	  }
}
