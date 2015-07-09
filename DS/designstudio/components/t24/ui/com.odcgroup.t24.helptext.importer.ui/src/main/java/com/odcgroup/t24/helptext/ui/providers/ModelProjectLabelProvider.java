package com.odcgroup.t24.helptext.ui.providers;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.core.OfsCore;

public class ModelProjectLabelProvider extends LabelProvider {

	
	public Image getImage(Object element) {
		
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_PROJECT);
	
	 }

	public String getText(Object element) {
		 if(element instanceof IProject){
		  if(OfsCore.isOfsProject((IProject)element)) {
			  return ((IProject)element).getName();
		   }
		 }
		return null;
	} 
	

 }
