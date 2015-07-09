package com.odcgroup.workbench.ui.navigator.pageflow;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeListener;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.workbench.ui.navigator.AbstractModelContentProvider;

public class PageflowContentProvider extends AbstractModelContentProvider
		implements IResourceChangeListener {

	  public PageflowContentProvider(){
	    super(PageflowAdapterFactoryProvider.getAdapterFactory(), "pageflow", Pageflow.class);
	  }
	 
	@SuppressWarnings("unchecked")
	public Object[] getChildren(Object object) {
		Object[] children = super.getChildren(object);
		  
		return getStateElements(children);
	  }	
	 
	  private Object[] getStateElements(Object[] objects) {
		  List<Object> stateElements = new ArrayList<Object>();
		  for(Object obj : objects) {
			  if(obj instanceof State) {
				  stateElements.add(obj);
			  }
		  }
		  return stateElements.toArray();
	  }
}
