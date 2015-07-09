package com.odcgroup.workbench.ui.navigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.ui.OfsUICore;

public class OfsLabelProvider extends BaseLabelProvider implements ILabelProvider, IColorProvider {
	
	private HashMap<ImageDescriptor,Image> imageCache = new HashMap<ImageDescriptor,Image>();
	
	private HashMap<ImageDescriptor,Image> composedImageCache = new HashMap<ImageDescriptor,Image>();

	public final static Color DEPENDENCY_COLOR = PlatformUI.getWorkbench()
			.getThemeManager().getCurrentTheme().getColorRegistry().get(
					"com.odcgroup.workbench.ui.colorDependencies");
	
	private final static Image OVERLAY_DEPENDENCY_IMAGE = OfsUICore.imageDescriptorFromPlugin(
			OfsUICore.PLUGIN_ID, "icons/link_bottomRight.png")
			.createImage();

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
	   for (ImageDescriptor imageDesc : imageCache.keySet()) {
	       imageCache.get(imageDesc).dispose();
	   }
	   imageCache.clear();
	   for (ImageDescriptor imageDesc : composedImageCache.keySet()) {
		   composedImageCache.get(imageDesc).dispose();
	   }
	   composedImageCache.clear();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object obj) {
		String text = null;
		if(obj instanceof IOfsModelResource) {
			IOfsModelResource file = (IOfsModelResource) obj;
			//text = StringHelper.withoutExtension(file.getName());
			text = file.getShortName();
		}
		else if(obj instanceof IOfsModelPackage) {
			IOfsModelPackage pkg = (IOfsModelPackage) obj;
			text = pkg.getName();
		}
		else if(obj instanceof IOfsModelFolder) {
			IOfsModelFolder folder = (IOfsModelFolder) obj;
			text = StringHelper.toFirstUpper(StringHelper.toPlural(folder.getName()));
		}
		return text;
	}
	
	/**
	 * @param descriptor
	 * @return
	 */
	protected Image getImageFromCache(ImageDescriptor descriptor) {
		Image image = imageCache.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;
	}
	
	/**
	 * @param ofsFolder
	 * @return
	 */
	protected Image getImage(IOfsModelFolder ofsFolder) {
		ImageDescriptor descriptor = PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor("." + ofsFolder.getName());
		
		IConfigurationElement[] configs = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(OfsUICore.MODEL_IMAGE_EXTENSION_ID);
		
		for (IConfigurationElement config : configs) {
			String modelName = config.getAttribute(OfsUICore.MODEL_NAME);
			String iconPath = config.getAttribute(OfsUICore.ICON_PATH);
			if (modelName.equals(ofsFolder.getName())) {
				descriptor = OfsUICore.imageDescriptorFromPlugin(config.getContributor().getName(), iconPath);
			}
		}
		return getImageFromCache(descriptor);
	}
	
	/**
	 * @param ofsPackage
	 * @return
	 */
	protected Image getImage(IOfsModelPackage ofsPackage) {
		ImageDescriptor descriptor = null;
		if((ofsPackage.getScope() & IOfsProject.SCOPE_PROJECT) > 0) {
			descriptor = OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "icons/package_obj.gif");
		} else {
			descriptor = OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "icons/packd_obj.gif");
		}
		return getImageFromCache(descriptor);
	}
	
	/**
	 * @param ofsResource
	 * @return
	 */
	protected Image getImage(IOfsModelResource ofsResource) {
		ImageDescriptor descriptor = PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(ofsResource.getName());

		IConfigurationElement[] configs = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(OfsUICore.MODEL_IMAGE_EXTENSION_ID);

		for (IConfigurationElement config : configs) {
			String modelName = config.getAttribute(OfsUICore.MODEL_NAME);
			String iconPath = config.getAttribute(OfsUICore.ICON_PATH);
			String pattern = ".*(." + modelName + ")";

			if (ofsResource.getName().matches(pattern)) {
				descriptor = OfsUICore.imageDescriptorFromPlugin(config.getContributor().getName(), iconPath);
			}
		}
		
		Image image = getImageFromCache(descriptor);
		if (ofsResource.getScope() == IOfsProject.SCOPE_DEPENDENCY) {
			image = getComposedImage(descriptor, image,	OVERLAY_DEPENDENCY_IMAGE);
		}
		return image;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object obj) {
		
		Image image = null;
		
		if(obj instanceof IOfsModelFolder) {
			image = getImage((IOfsModelFolder)obj);
		}
		
		else if(obj instanceof IOfsModelPackage) {
			image = getImage((IOfsModelPackage) obj);
		}

		else if(obj instanceof IOfsModelResource){
			image = getImage((IOfsModelResource) obj);
		}
		
		return image;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		if(element instanceof IOfsModelPackage) {
			IOfsModelPackage pkg = (IOfsModelPackage) element;
			if(pkg.getScope()==IOfsProject.SCOPE_DEPENDENCY) {
				return DEPENDENCY_COLOR;
			}
		}
		if(element instanceof IOfsModelResource) {
			IOfsModelResource res = (IOfsModelResource) element;
			if(res.getScope() == IOfsProject.SCOPE_DEPENDENCY) {
				return DEPENDENCY_COLOR;
			}
		}
		return null;
	}
	
	/**
	 * DS-1613 (Standardize the presentation of customized element)
	 * 
	 * @param originalImage
	 * @param overlayImage
	 * @return
	 */
	private Image getComposedImage(ImageDescriptor descriptor, Image originalImage, Image overlayImage) {
		Image image = composedImageCache.get(descriptor);
		if (image == null) {
			List<Image> images = new ArrayList<Image>(2);
			images.add(originalImage);
			images.add(overlayImage);
			ComposedImageDescriptor descr = new ComposedImageDescriptor(new ComposedImage(images));
			image = descr.createImage();
			composedImageCache.put(descriptor, image);
		}
		return image;
	}
	
	/**
	 * DS-1613 (Standardize the presentation of customized element)
	 */
	protected static class ComposedImageDescriptor extends CompositeImageDescriptor {
		
		protected ComposedImage composedImage;
		protected List<ImageData> imageDatas;

		public ComposedImageDescriptor(ComposedImage composedImage) {
			this.composedImage = composedImage;
		}

		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.resource.CompositeImageDescriptor#drawCompositeImage(int, int)
		 */
		public void drawCompositeImage(int width, int height) {
			ComposedImage.Size size = new ComposedImage.Size();
			size.width = width;
			size.height = height;
			Iterator<ImageData> images = imageDatas.iterator();
			for (Iterator<ComposedImage.Point> points = composedImage
					.getDrawPoints(size).iterator(); points.hasNext();) {
				ComposedImage.Point point = points.next();
				drawImage(images.next(), point.x, point.y);
			}
		}

		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.resource.CompositeImageDescriptor#getSize()
		 */
		public Point getSize() {
			List<Object> images = composedImage.getImages();
			imageDatas = new ArrayList<ImageData>(images.size());
			List<ComposedImage.Size> sizes = new ArrayList<ComposedImage.Size>(
					images.size());
			for (Object object : images) {
				Image image = ExtendedImageRegistry.getInstance().getImage(
						object);
				ImageData imageData = image.getImageData();
				imageDatas.add(imageData);

				ComposedImage.Size size = new ComposedImage.Size();
				size.width = imageData.width;
				size.height = imageData.height;
				sizes.add(size);
			}

			ComposedImage.Size result = composedImage.getSize(sizes);
			return new Point(result.width, result.height);
		}
	}

}
