package com.odcgroup.workbench.ui.navigator.process;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.process.model.NamedElement;
import com.odcgroup.workbench.ui.navigator.AbstractModelContentProvider;

public class ProcessContentProvider extends AbstractModelContentProvider {

	/**
	 * OCS-24647 (Change extension process to workflow)
	 */
	public ProcessContentProvider() {
	    super(ProcessAdapterFactoryProvider.getAdapterFactory(), "workflow",
	    		com.odcgroup.process.model.Process.class);
	  }
	 	 
	  @SuppressWarnings("unchecked")
	  public Object[] getChildren(Object object) {
		Object[] children = super.getChildren(object);
		  
		if(object instanceof NamedElement) {
			children = getNamedElements(super.getChildren(object));
		}
		return getNamedElements(children);
	  }
	 
	  private Object[] getNamedElements(Object[] objects) {
		  List<Object> namedElements = new ArrayList<Object>();
		  for(Object obj : objects) {
			  if(obj instanceof NamedElement) {
				  namedElements.add(obj);
			  }
		  }
		  return namedElements.toArray();
	  }  
}
