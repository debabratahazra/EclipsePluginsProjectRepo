package com.odcgroup.page.model.corporate.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.osgi.service.prefs.BackingStoreException;

import com.odcgroup.page.model.PageModelCore;
import com.odcgroup.page.model.corporate.CorporateImagesConstants;
import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.model.corporate.ImageFileDescriptor;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * An implementation of the CorporateImages which uses the Eclipse Preferences
 * mechanism.
 * 
 * @author atr
 */
public class CorporateImagesImpl extends AbstractCorporateImages {

	/**
	 * 
	 */
	private IEclipsePreferences defScope;

	/**
	 * 
	 */
	private IPreferenceChangeListener defListener = new IPreferenceChangeListener() {
		public void preferenceChange(PreferenceChangeEvent event) {
			//System.out.println("default preferences changed");
			//System.out.println(event.getKey() + "@" + event.getNewValue() + "@" + event.getOldValue());
		}
	};

	/**
	 * @param descriptor
	 *            the image descriptor
	 * @return error message
	 */
	public static String getErrorMessage(ImageDescriptor descriptor) {
		StringBuilder builder = new StringBuilder();
		builder.append("This image is not found : name=[");
		builder.append(descriptor.getName());
		builder.append("] type=[");
		builder.append(descriptor.getType());
		builder.append("] dir=[");
		builder.append(descriptor.getFileDescriptor().getDirectory());
		builder.append("] filename=[");
		builder.append(descriptor.getFileDescriptor().getFilename());
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @see com.odcgroup.page.model.corporate.internal.AbstractCorporateImages#dispose()
	 */
	/* package */void dispose() {
		defScope.removePreferenceChangeListener(defListener);
	}

	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#getEnabledImageDescriptors()
	 */
	@SuppressWarnings("unchecked")
	public List<ImageDescriptor> getEnabledImageDescriptors() {
		return (List<ImageDescriptor>) CollectionUtils.select(getAllImageDescriptors(), new Predicate() {
			public boolean evaluate(Object obj) {
				return ((ImageDescriptor) obj).isEnabled();
			}
		});
	}

	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#getImageDescriptor(java.lang.String)
	 */
	public ImageDescriptor getImageDescriptor(final String name) {
		ImageDescriptor descriptor = (ImageDescriptor) CollectionUtils.find(getEnabledImageDescriptors(),
				new Predicate() {
					public boolean evaluate(Object obj) {
						return name.equalsIgnoreCase(((ImageDescriptor) obj).getName());
					}
				});
		return descriptor;
	}

	/**
	 * @param descriptors
	 * @return the encoded descriptors
	 */
	public static String encodeImageDescriptors(List<ImageDescriptor> descriptors) {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		for (ImageDescriptor img : descriptors) {
			ImageFileDescriptor file = img.getFileDescriptor();
			String keyFile = file.toString();
			Set<String> imgSet = map.get(keyFile);
			if (imgSet == null) {
				imgSet = new HashSet<String>();
				map.put(keyFile, imgSet);
			}
			imgSet.add(img.toString());
		}
		return map.toString();
	}

	/**
	 * @param value
	 * @param descriptors
	 */
	public static void decodeImageDescriptors(String value, List<ImageDescriptor> descriptors) {
		Map<String, ImageFileDescriptor> fileMap = new HashMap<String, ImageFileDescriptor>();
		String[] images = value.split("]");
		for (String image : images) {
			if (image.charAt(0) == '{') {
				image = image.substring(1);
			}
			if (image.charAt(0) == ',') {
				image = image.substring(1);
			}
			if (image.charAt(0) == ' ') {
				image = image.substring(1);
			}
			if (image.charAt(image.length() - 1) == '}') {
				image = image.substring(0, image.length() - 1);
			}
			String[] words = image.split("=");
			if (words.length > 1) {
				String fileKey = words[0];
				ImageFileDescriptor fd = fileMap.get(fileKey);
				if (fd == null) {
					fd = ImageFileDescriptor.valueOf(fileKey);
					fileMap.put(fileKey, fd);
				}
				String val = words[1];
				if (val.charAt(0) == '[') {
					val = val.substring(1);
				}
				if (fd.isRegular()) {
					ImageDescriptor descriptor = ImageDescriptor.valueOf(fd, val);
					descriptors.add(descriptor);
				} else {
					String[] imgs = val.split(" ");
					for (String img : imgs) {
						if (img.charAt(img.length() - 1) == ',') {
							img = img.substring(0, img.length() - 1);
						}
						ImageDescriptor descriptor = ImageDescriptor.valueOf(fd, img);
						descriptors.add(descriptor);
					}
				}
			}
		}
	}

	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#getAllImageDescriptors()
	 */
	public List<ImageDescriptor> getAllImageDescriptors() {
		List<ImageDescriptor> list = new ArrayList<ImageDescriptor>();
		IEclipsePreferences usrPrefs = getProjectScope().getNode(getQualifier());
		IEclipsePreferences defPrefs = new DefaultScope().getNode(getQualifier());
		String defValue = defPrefs.get(CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS, "");
		String usrValue = usrPrefs.get(CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS, defValue);
		if (StringUtils.isNotEmpty(usrValue)) {
			decodeImageDescriptors(usrValue, list);
		}
		return list;
	}

	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#imageExists(java.lang.String)
	 */
	public boolean imageExists(String name) {
		boolean exists = false;
		ImageDescriptor descriptor = getImageDescriptor(name);
		if (descriptor != null) {
			exists = imageExists(descriptor);
		}
		return exists;
	}

	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#imageExists(com.odcgroup.page.model.corporate.ImageDescriptor)
	 */
	public boolean imageExists(ImageDescriptor id) {
		boolean exists = false;
		InputStream is = null;
		try {
			URL url = id.toURL();
			is = url.openStream();
			exists = true;
		} catch (FileNotFoundException ex) {
			PageModelCore.getDefault().logWarning(getErrorMessage(id), ex);
		} catch (MalformedURLException ex) {
			PageModelCore.getDefault().logWarning(getErrorMessage(id), ex);
		} catch (IOException ex) {
			PageModelCore.getDefault().logWarning(getErrorMessage(id), ex);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// silently ignore
				}
			}
		}
		return exists;
	}

		
	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#saveImagesDescriptors(java.util.List)
	 */
	public void saveImagesDescriptors(List<ImageDescriptor> descriptors) {
		String value = encodeImageDescriptors(descriptors);
		IEclipsePreferences prefs = getProjectScope().getNode(getQualifier());
		prefs.put(CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS, value);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.odcgroup.page.model.corporate.CorporateImages#addImageDescriptor(java.util.List)
	 */
	public void addImageDescriptor(List<ImageDescriptor> descriptors) {
		Map <String, ImageDescriptor> map = new HashMap<String, ImageDescriptor>();
		for (ImageDescriptor id : getAllImageDescriptors()) {
			map.put(id.getName(), id);
		}
		for (ImageDescriptor id : descriptors) {
			map.put(id.getName(), id);
		}
		saveImagesDescriptors(new ArrayList<ImageDescriptor>(map.values()));
	}

	/**
	 * @param ofsProject
	 * @param qualifier
	 */
	public CorporateImagesImpl(IOfsProject ofsProject, String qualifier) {
		super(ofsProject, qualifier);
		defScope = new DefaultScope().getNode(getQualifier());
		defScope.addPreferenceChangeListener(defListener);
	}

}