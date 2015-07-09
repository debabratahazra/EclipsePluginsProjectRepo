package com.odcgroup.page.model.corporate;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.osgi.framework.Bundle;

import com.odcgroup.page.model.PageModelCore;
import com.odcgroup.page.model.corporate.internal.CorporateImagesImpl;
import com.odcgroup.page.model.corporate.internal.CorporateImagesRegistry;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Helper class for Images
 * 
 * @author atr
 * @since DS 1.40.0
 */
public final class CorporateImagesUtils {

	/** */
	private static CorporateImagesRegistry registry;

	/**
	 * Provides only static services
	 */
	private CorporateImagesUtils() {
	}

	/** the name of the file that contains the silk icons */
	private static final String KEY_FILE_NAME = "file_name";

	/** The number of icons in the silk file */
	private static final String KEY_FILE_ICON_COUNT = "file_images";

	/** The number of columns in the silk file */
	private static final String KEY_FILE_COLUMN_COUNT = "file_columns";

	/** The width in pixels of each icon */
	private static final String KEY_FILE_ICON_WIDTH = "file_width";

	/** The height in pixels of each icon */
	private static final String KEY_FILE_ICON_HEIGHT = "file_height";

	/** The separator used to separe field int the file descriptor */
	private static final String FIELD_SEPARATOR = ",";

	/** The default folder that contains default images files */
	private static final String BUILTIN_SPRITE_FOLDER = "/icons";

	/** The default folder that contains default images files */
	private static final String BUILTIN_REGULAR_FOLDER = "/icons";

	/** The extension of sprite file descriptors */
	private static final String BUILTIN_SPRITE_DESCRIPTOR_EXTENSION = "properties";
	
	/** The place holder used to reference built-in path */
	private static final String BUILTIN_PLACE_HOLDER = "$BUILTIN$";
	
	/** GIF extension */
	private static final String GIF = "gif";


	/**
	 * @param properties
	 * @param property
	 * @return String
	 */
	private static String getStringValue(Properties properties, String property) {
		return properties.getProperty(KEY_FILE_NAME);
	}

	/**
	 * @param properties
	 * @param property
	 * @return int
	 */
	private static int getIntValue(Properties properties, String property) {
		String value = properties.getProperty(property);
		if (value != null) {
			value = value.trim();
		}
		int intValue = Integer.parseInt(value);
		return intValue;
	}

	/**
	 * @param fileDescriptor
	 * @param properties
	 * @param descriptors
	 */
	private static void makeImageDescriptors(ImageFileDescriptor fileDescriptor, Properties properties, List<ImageDescriptor> descriptors) {

		Set<Object> keys = properties.keySet();
		for (Object keyword : keys) {
			
			String fields = (String)properties.get(keyword);
			StringTokenizer st = new StringTokenizer(fields, FIELD_SEPARATOR);
			try {
				int row = Integer.parseInt(st.nextToken().trim());
				int column = Integer.parseInt(st.nextToken().trim());
				boolean available = Boolean.valueOf(st.nextToken().trim());
				descriptors.add(new ImageDescriptor((String) keyword, fileDescriptor, row, column, available));
			} catch (Throwable ex) {
				String msg = "Unable to decode the image descriptor for image [" + (String) keyword + "] fields=[" + fields + "]";
				PageModelCore.getDefault().logError(msg, ex);
			}
		}
	}
	/**
	 * @param folder
	 * @param inFile
	 * @param descriptors
	 */
	private static void makeSpriteImageDescriptors(File folder, File inFile, List<ImageDescriptor> descriptors) {
		
		String directory = folder.toString();

		Properties properties = new Properties();
		InputStream is = null;
		try {
			
			// load the properties file
			is = inFile.toURI().toURL().openStream();
			properties.load(is);

			// load characteristics of the sprite file
			String name = getStringValue(properties, KEY_FILE_NAME);
			properties.remove(KEY_FILE_NAME);
			int nbIcons = getIntValue(properties, KEY_FILE_ICON_COUNT);
			properties.remove(KEY_FILE_ICON_COUNT);
			int nbColumns = getIntValue(properties, KEY_FILE_COLUMN_COUNT);
			properties.remove(KEY_FILE_COLUMN_COUNT);
			int width = getIntValue(properties, KEY_FILE_ICON_WIDTH);
			properties.remove(KEY_FILE_ICON_WIDTH);
			int height = getIntValue(properties, KEY_FILE_ICON_HEIGHT);
			properties.remove(KEY_FILE_ICON_HEIGHT);

			// make a descriptor for that file
			ImageFileDescriptor fileDescriptor = new ImageFileDescriptor(BUILTIN_PLACE_HOLDER + directory, name, nbIcons, nbColumns, width, height);
			
			// load the image descriptors
			makeImageDescriptors(fileDescriptor, properties, descriptors);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {

				}
			}
		}

	}
	
	/**
	 * @param folder
	 * @param inFile
	 * @return ImageDescriptor
	 */
	private static ImageDescriptor makeImageDescriptor(File folder, File inFile) {
		String fullname = inFile.toString();
		int separatorPos = fullname.lastIndexOf(File.separator);
		String directory = fullname.substring(0,separatorPos + 1);
		String filename = fullname.substring(separatorPos+1);
		String imgName = fullname.substring(separatorPos + 1, fullname.lastIndexOf('.'));
		return new ImageDescriptor(imgName, new ImageFileDescriptor(directory, filename), true);
	}

	/**
	 * @param folder
	 * @param descriptors
	 */
	private static void fetchBuiltinSpriteImageDescriptors(IPath folder, List<ImageDescriptor> descriptors) {
		
		Bundle bundle = PageModelCore.getDefault().getBundle();
		final URL url = FileLocator.find(bundle, folder, null);
		File dir;
		try {
			dir = new File(FileLocator.toFileURL(url).getPath());
		} catch (IOException ex) {
			String msg = "Invalid location for builtin sprite images.";
			PageModelCore.getDefault().logError(msg, ex);
			return;
		}
		
		// load first sprite files
		for (File inFile : dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				String filename = pathname.toString();
				String extension = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
				return BUILTIN_SPRITE_DESCRIPTOR_EXTENSION.equals(extension);
			}
		})) {
			makeSpriteImageDescriptors(folder.toFile(), inFile, descriptors);
		}
	}
	
	public static boolean isBuiltIn(String fullpath) {
		return fullpath.startsWith(BUILTIN_PLACE_HOLDER);
	}
	
	public static URL getAbsolutBuiltInUrl(String fullpath) {
		if (!isBuiltIn(fullpath)) {
			return null;
		}
		String relativePath = StringUtils.remove(fullpath, BUILTIN_PLACE_HOLDER);
		Bundle bundle = PageModelCore.getDefault().getBundle();
		return FileLocator.find(bundle, new Path(relativePath), null);
	}
	
	/**
	 * Accept only gif files
	 * @param folder
	 * @param descriptors
	 */
	private static void fetchBuiltinRegularImageDescriptors(IPath folder, List<ImageDescriptor> descriptors) {
		Bundle bundle = PageModelCore.getDefault().getBundle();
		final URL url = FileLocator.find(bundle, folder, null);
		File dir;
		try {
			dir = new File(FileLocator.toFileURL(url).getPath());
		} catch (IOException ex) {
			String msg = "Invalid location for builtin regular images.";
			PageModelCore.getDefault().logError(msg, ex);
			return;
		}

		for (File inFile : dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				String filename = pathname.toString();
				String extension = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
				return GIF.equalsIgnoreCase(extension);
			}
		})) {
			descriptors.add(makeImageDescriptor(dir, inFile));
		}

	}

	/**
	 * @param folder
	 * @param descriptors
	 */
	private static void fetchRegularImageDescriptors(IPath folder, List<ImageDescriptor> descriptors) {

		File dir = new File(folder.toPortableString());

		for (File inFile : dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				String filename = pathname.toString();
				String extension = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
				return GIF.equalsIgnoreCase(extension);
			}
		})) {
			descriptors.add(makeImageDescriptor(dir, inFile));
		}
	}

	/**
	 * @return List<ImageDescriptor>
	 */ 
	private static List<ImageDescriptor> getBuiltinImageDescriptors() {
		
		List<ImageDescriptor> descriptors = new ArrayList<ImageDescriptor>();
		
		IPath folder = null;
		
		// load builtin sprites images
		folder = new Path(BUILTIN_SPRITE_FOLDER);
		fetchBuiltinSpriteImageDescriptors(folder, descriptors);
			
		
		// load builtin regular images
		folder = new Path(BUILTIN_REGULAR_FOLDER);
		fetchBuiltinRegularImageDescriptors(folder, descriptors);
		
		// load regular images from workspace
		folder = PageModelCore.getDefault().getStateLocation();
		fetchRegularImageDescriptors(folder, descriptors);
		
		return descriptors;
	}
	

	/**
	 * Adds the given collection of image descriptor into the default scope. Existing image
	 * descriptor will be replaced by he new ones.
	 * 
	 * @param newDescriptors
	 */
	private static void addImageDescriptorsToDefaultScope(List<ImageDescriptor> newDescriptors) {
		List<ImageDescriptor> allDescriptors = new ArrayList<ImageDescriptor>();
		IEclipsePreferences defPrefs = new DefaultScope().getNode(CorporateImagesConstants.PROPERTY_STORE_ID);
		String value = defPrefs.get(CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS, "");
		if (StringUtils.isNotEmpty(value)) {
			CorporateImagesImpl.decodeImageDescriptors(value, allDescriptors);
		}
		Map<String, ImageDescriptor> map = new HashMap<String, ImageDescriptor>();
		for (ImageDescriptor descriptor : allDescriptors) {
			map.put(descriptor.getName(), descriptor);
		}
		for (ImageDescriptor descriptor : newDescriptors) {
			map.put(descriptor.getName(), descriptor);
		}
		allDescriptors.clear();
		allDescriptors.addAll(map.values());
		value = CorporateImagesImpl.encodeImageDescriptors(allDescriptors);
		defPrefs.put(CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS, value);
		for (CorporateImages ci : registry.getCorporateImages()) {
			ci.addImageDescriptor(newDescriptors);
		}
	}
	
	/**
	 * @param ofsProject
	 * @return CorporateImages
	 */
	public static CorporateImages getCorporateImages(IOfsProject ofsProject) {
		return CorporateImagesUtils.getRegistry().getCorporateImages(ofsProject);
	}

	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void addPreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getCorporateImages(ofsProject).addPreferenceChangeListener(listener);
	}

	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void removePreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getCorporateImages(ofsProject).removePreferenceChangeListener(listener);
	}

	/**
	 * Initializes the registry of corporate images
	 */
	public static void installCorporateImages() {
		CorporateImagesUtils.registry = new CorporateImagesRegistry();
	}

	/**
	 * Disposes the the registry of corporate images
	 */
	public static void uninstallCorporateImages() {
		CorporateImagesUtils.registry.dispose();
	}
	
	/**
	 * @return List<ImageDescriptor>
	 */
	public static List<ImageDescriptor> getMissingImageFiles() {
		Map<String, ImageDescriptor> map = new HashMap<String,ImageDescriptor>();
		for (CorporateImages ci : registry.getCorporateImages()) {
			for (ImageDescriptor id : ci.getEnabledImageDescriptors()) {
				if (!ci.imageExists(id)) {
					map.put(id.getName(), id);
				}
			}
		}
		return new ArrayList<ImageDescriptor>(map.values());
	}
	
	/**
	 * @return List<ImageDescriptor>
	 */
	public static List<ImageDescriptor> getDefaultImageDescriptors() {
		List<ImageDescriptor> descriptors = new ArrayList<ImageDescriptor>();
		IEclipsePreferences node = new DefaultScope().getNode(CorporateImagesConstants.PROPERTY_STORE_ID);
		String value = node.get(CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS, "");
		if (StringUtils.isNotEmpty(value)) {
			CorporateImagesImpl.decodeImageDescriptors(value, descriptors);
		}
		return descriptors;
	}	

	/**
	 * Install Defaults
	 */
	public static void initializeDefaultScope() {
		String encodedDescriptors = CorporateImagesImpl.encodeImageDescriptors(getBuiltinImageDescriptors());
		IEclipsePreferences node = new DefaultScope().getNode(CorporateImagesConstants.PROPERTY_STORE_ID);
		node.put(CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS, encodedDescriptors);
	}

	/**
	 * Import into the workspace the designated collection of images
	 * @param folder the source folder
	 * @param fileNames the image filenames
	 */
	public static void importCustomRegularImages(String folder, String[] fileNames) {

		/*
		 * Gets the folder for this plugin. All selected files will be 
		 * copied in that folder.
		 */
		IPath targetLocation = PageModelCore.getDefault().getStateLocation();

		String sourceLocation = folder;
		
		List<ImageDescriptor> newImageDescriptors = new ArrayList<ImageDescriptor>();
		
		// copy source to target location
		for (String fn : fileNames) {
			
			File inputFile  = new File(sourceLocation + "/" + fn);
			File outputFile = new File(targetLocation + "/" + fn);
			
			PageModelCore.getDefault().logInfo("Copying "+inputFile.getAbsolutePath()+" into the workspace", null);

			try {
				
				FileOutputStream os = FileUtils.openOutputStream(outputFile);
				InputStream is = new FileInputStream(inputFile);
				IOUtils.copy(is, os);
				
				// build the image descriptor
				//String imageName = fn.substring(fn.lastIndexOf('.'), fn.length());
				String imageName = fn.substring(0, fn.lastIndexOf('.'));
				ImageFileDescriptor ifd = new ImageFileDescriptor(targetLocation.toPortableString(), fn);
				newImageDescriptors.add(new ImageDescriptor(imageName, ifd, true));
				
			} catch (IOException ex) {
				String msg = "Could not copy the file ["+inputFile.getAbsolutePath()+"] into the workspace";
				PageModelCore.getDefault().logError(msg, ex);
			}
		}
		
		CorporateImagesUtils.addImageDescriptorsToDefaultScope(newImageDescriptors);
	}

	/**
	 * @return CorporateImagesRegistry
	 */
	static CorporateImagesRegistry getRegistry() {
		return CorporateImagesUtils.registry;
	}

}
