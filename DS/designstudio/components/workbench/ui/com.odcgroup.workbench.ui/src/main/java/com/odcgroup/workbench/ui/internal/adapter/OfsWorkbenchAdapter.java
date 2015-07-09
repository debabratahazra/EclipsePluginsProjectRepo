package com.odcgroup.workbench.ui.internal.adapter;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.IWorkbenchAdapter2;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * TODO: Document me!
 *
 * @author Kai Kreuzer
 *
 */
public class OfsWorkbenchAdapter implements IWorkbenchAdapter, IWorkbenchAdapter2 {

	public final static Color DEPENDENCY_COLOR = PlatformUI.getWorkbench()
	.getThemeManager().getCurrentTheme().getColorRegistry().get(
			"com.odcgroup.workbench.ui.colorDependencies");

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object o) {
		if(o instanceof IProject) {
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject((IProject) o);
			if(ofsProject!=null) return ofsProject.getAllModelFolders();
		}
		if(o instanceof IOfsModelFolder) {
			IOfsModelFolder ofsFolder = (IOfsModelFolder) o;
			return ofsFolder.getPackages().toArray();
		}
		if(o instanceof IOfsModelPackage) {
			IOfsModelPackage ofsPackage = (IOfsModelPackage) o;
			Collection<IOfsElement> children = new HashSet<IOfsElement>(); 
			children.addAll(ofsPackage.getSubPackages());
			children.addAll(ofsPackage.getModels());
			return children.toArray();
		}
		return new Object[0];
	}
	
	protected ImageDescriptor getImageDescriptor(IOfsModelFolder ofsFolder) {
		return PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor("." + ofsFolder.getName());
	}
	
	protected ImageDescriptor getImageDescriptor(IOfsModelPackage ofsPackage) {
		ImageDescriptor descriptor = null;
		if((ofsPackage.getScope() & IOfsProject.SCOPE_PROJECT) > 0) {
			descriptor = OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "icons/package_obj.gif");
		} else {
			descriptor = OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "icons/packd_obj.gif");
		}
		return descriptor;
	}
	
	/**
	 * @param ofsResource
	 * @return
	 */
	protected ImageDescriptor getImageDescriptor(IOfsModelResource ofsResource) {
		return PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(ofsResource.getName());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
	 */
	public ImageDescriptor getImageDescriptor(Object obj) {
		
		ImageDescriptor imageDesc = null;
		
		if(obj instanceof IOfsModelFolder) {
			imageDesc = getImageDescriptor((IOfsModelFolder)obj);
		}
		
		else if(obj instanceof IOfsModelPackage) {
			imageDesc = getImageDescriptor((IOfsModelPackage) obj);
		}

		else if(obj instanceof IOfsModelResource){
			imageDesc = getImageDescriptor((IOfsModelResource) obj);
		}
		
		return imageDesc;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
	 */
	public String getLabel(Object obj) {
		String text = null;
		if(obj instanceof IOfsModelFolder) {
			IOfsModelFolder folder = (IOfsModelFolder) obj;
			text = StringHelper.toFirstUpper(StringHelper.toPlural(folder.getName()));
		}
		if(obj instanceof IOfsModelResource) {
			IOfsModelResource file = (IOfsModelResource) obj;
			text = StringHelper.withoutExtension(file.getName());
		}
		if(obj instanceof IOfsModelPackage) {
			IOfsModelPackage pkg = (IOfsModelPackage) obj;
			text = StringHelper.withoutExtension(pkg.getName());
		}
		return text;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
	 */
	public Object getParent(Object o) {
		if(o instanceof IOfsProject) {
			IOfsProject ofsProject = (IOfsProject) o;
			return ofsProject.getProject().getParent();
		}
		if(o instanceof IOfsModelFolder) {
			IOfsModelFolder ofsFolder = (IOfsModelFolder) o;
			return ofsFolder.getOfsProject();
		}
		if(o instanceof IOfsModelPackage) {
			IOfsModelPackage ofsPackage = (IOfsModelPackage) o;
			return ofsPackage.getParent();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter2#getBackground(java.lang.Object)
	 */
	public RGB getBackground(Object element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter2#getFont(java.lang.Object)
	 */
	public FontData getFont(Object element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter2#getForeground(java.lang.Object)
	 */
	public RGB getForeground(Object element) {
		if(element instanceof IOfsModelPackage) {
			IOfsModelPackage pkg = (IOfsModelPackage) element;
			if(pkg.getScope()==IOfsProject.SCOPE_DEPENDENCY) {
				return DEPENDENCY_COLOR.getRGB();
			}
		}
		if(element instanceof IOfsModelResource) {
			IOfsModelResource res = (IOfsModelResource) element;
			if(res.getScope() == IOfsProject.SCOPE_DEPENDENCY) {
				return DEPENDENCY_COLOR.getRGB();
			}
		}
		return null;
	}

}
