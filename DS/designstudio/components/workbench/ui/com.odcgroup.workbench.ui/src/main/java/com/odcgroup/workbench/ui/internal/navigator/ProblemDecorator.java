package com.odcgroup.workbench.ui.internal.navigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;

public class ProblemDecorator implements ILabelDecorator {

	private Image errorImageDecoration = JavaPluginImages.DESC_OVR_ERROR.createImage();
	private Image warningImageDecoration = JavaPluginImages.DESC_OVR_WARNING.createImage();
	
	private Map<Image, Image> errorImageMap = new HashMap<Image, Image>();
	private Map<Image, Image> warningImageMap = new HashMap<Image, Image>();
	
	public Image decorateImage(Image image, Object element) {
		IOfsElement ofsElement = (IOfsElement) element;
		int severity = findMaxProblemSeverity(ofsElement);
		if(severity == IMarker.SEVERITY_ERROR) {
			return getComposedErrorImage(image);
		}
		if(severity == IMarker.SEVERITY_WARNING) {
			return getComposedWarningImage(image);
		}
		return image;
	}

	private int findMaxProblemSeverity(IOfsElement ofsElement) {
		if(ofsElement.getResource()!=null && ofsElement.getResource().exists()) {
			IResource resource = ofsElement.getResource();
			try {
				return resource.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
				return IMarker.SEVERITY_INFO;
			}
		} else {
			// Handle the case of "virtual" resources here
			IFolder folder = null;
			if(ofsElement instanceof IOfsModelPackage) {
				folder = (IFolder) ((IOfsModelPackage) ofsElement).getModelFolder().getResource();
			} else if(ofsElement instanceof IOfsModelResource){
				folder = (IFolder) ofsElement.getOfsProject().getModelFolder(ofsElement.getURI().fileExtension()).getResource();
			} else {
				return IMarker.SEVERITY_INFO;			}
			try {
				IMarker[] markers = folder.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
				int maxSeverity = 0;
				for(IMarker marker : markers) {
					String uri = marker.getAttribute(EValidator.URI_ATTRIBUTE, "");
					if(uri.startsWith(ofsElement.getURI().toString())) {
						int severity = (Integer) marker.getAttribute(IMarker.SEVERITY);
						if(severity>maxSeverity) maxSeverity = severity;
					}
				}
				return maxSeverity;
			} catch (CoreException e) {
				return IMarker.SEVERITY_INFO;
			}
			
		}
	}

	public String decorateText(String text, Object element) {
		return text;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
		errorImageDecoration.dispose();
		warningImageDecoration.dispose();
		for(Image image : errorImageMap.values())   image.dispose();
		for(Image image : warningImageMap.values()) image.dispose();
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

	private Image getComposedErrorImage(Image originalImage) {
		if(!errorImageMap.containsKey(originalImage)) {
			ImageDescriptor desc = getComposedImageDescriptor(originalImage, errorImageDecoration);
			errorImageMap.put(originalImage, desc.createImage());
		}
		return errorImageMap.get(originalImage);
	}

	private Image getComposedWarningImage(Image originalImage) {
		if(!warningImageMap.containsKey(originalImage)) {
			ImageDescriptor desc = getComposedImageDescriptor(originalImage, warningImageDecoration);
			warningImageMap.put(originalImage, desc.createImage());
		}
		return warningImageMap.get(originalImage);
	}

	private ImageDescriptor getComposedImageDescriptor(Image originalImage, Image overlayImage) {
		List<Image> images = new ArrayList<Image>(2);
		images.add(originalImage);
		images.add(overlayImage);
		ComposedImageDescriptor descriptor = new ComposedImageDescriptor(new ComposedImage(images));
		return descriptor;
	}

	class ComposedImageDescriptor extends CompositeImageDescriptor {
		
		protected ComposedImage composedImage;
		protected List<ImageData> imageDatas;

		public ComposedImageDescriptor(ComposedImage composedImage) {
			this.composedImage = composedImage;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.resource.CompositeImageDescriptor#drawCompositeImage(int, int)
		 */
		public void drawCompositeImage(int width, int height) {
			ImageData bg = imageDatas.get(0);
			ImageData fg = imageDatas.get(1);
			drawImage(bg, 0, 0);
			drawImage(fg, 0, height - fg.height);
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
