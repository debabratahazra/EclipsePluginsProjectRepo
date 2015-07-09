package com.odcgroup.workbench.ui.navigator.pageflow;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.workbench.ui.navigator.AbstractModelLabelProvider;


public class PageflowLabelProvider extends AbstractModelLabelProvider {
	 
	  public PageflowLabelProvider() {
	    super(PageflowAdapterFactoryProvider.getAdapterFactory(), "pageflow");
	  }
	 	 
	  public String getText(Object object) {
	    if(object instanceof Pageflow) {
	    	Pageflow pageflow = (Pageflow) object;
	    	String name = pageflow.getName();
	    	return name;
	    }
	    if(object instanceof State) {
	    	State namedElement = (State) object;
	    	String name = namedElement.getDisplayName();
	    	if(name==null || name.length()==0) {
	    		name = namedElement.getName();
	    	}
	    	return name;
	    }
	    return super.getText(object);
	  }
}