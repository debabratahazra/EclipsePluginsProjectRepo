package samplecode.source.view;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import samplecode.Activator;

public class SampleLabelProvider extends LabelProvider {
	
	@Override
	public Image getImage(Object element) {
		Image image = null;
		if(element instanceof IProject){
			image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/project.png").createImage();
		}else if(element instanceof IFolder){
			image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/folder.png").createImage();
		}else if(element instanceof IFile){
			image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/file.png").createImage();
		}else{
			return super.getImage(element);
		}
		return image;
	}
	
	@Override
	public String getText(Object element) {
		String result = "";
		if(element instanceof IResource) {
			result = ((IResource)element).getName();
		}
		return result;
	}

}
