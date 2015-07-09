package com.odcgroup.ds.t24.packager.t24project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.odcgroup.ds.t24.packager.basic.BasicException;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidationException;
import com.odcgroup.ds.t24.packager.generator.T24Packager;
import com.odcgroup.ds.t24.packager.helper.ZipHelper;

/**
 * This class is responsible to put the files in the T24 archived projects to
 * the correct place in the tar file. Note: the tar file is not created in this 
 * class.
 * @author yandenmatten
 */
public class T24ProjectPackager {

	private T24Packager packager;
	
	private List<T24ProjectArchive> t24projectArchives = new ArrayList<T24ProjectArchive>();

	public void addT24Project(T24ProjectArchive archive) {
		t24projectArchives.add(archive);
	}
	
	/**
	 * Put all the file to thier correct location
	 * <ul>
	 * <li>all the basic files to the compilation folder</li>
	 * <li>all the public basic files in the component/BP subfolder</li>
	 * <li>all the data at the root of the component subfolder</li>
	 * </ul>
	 */
	public void processT24Projects() throws BasicValidationException, BasicException, IOException {
		for (final T24ProjectArchive t24projectArchive: t24projectArchives) {
			packager.getLogger().debug(" - processing : " + t24projectArchive.getArchive().getAbsolutePath());
			
			// Extract all the basic files to the tar folder
			packager.getLogger().debug("   - Extracting all basic files");
			t24projectArchive.extractAllBasicTo(packager.getBasicCompilationSourceFolder());
			
			// Extract all the public basic files to the tar folder
			packager.getLogger().debug("   - Extracting public basic files");
			t24projectArchive.extractPublicBasicTo(packager.getPackageRootFolder(),
					new ZipHelper.Relocate() {
						public String relocate(String entry) {
							int index = entry.indexOf("/");
							if (index == -1)
								return entry;
							String component = entry.substring(0, index);
							if (!StringUtils.equals(component, packager.getComponentName())) {
								throw new IllegalArgumentException("Archive format error: component mismatch between packager and projects (Packager component: " + packager.getComponentName() +", component in archive: " + component + ", archive: " + t24projectArchive.getArchive().getAbsolutePath());
							}
							String rest = entry.substring(index+1);
							if(rest.startsWith("I_")){
								return "/" + rest;
							}
							return "/BP/" + rest;
						}
			});
			
			// Extract all the data to the tar folder
			packager.getLogger().debug("   - Extracting all data files");
			t24projectArchive.extractDataTo(packager.getPackageRootFolder(),
					new ZipHelper.Relocate() {
						public String relocate(String entry) {
							// This code flatten the data in the target folder
							String destination;
							if (entry.contains("/")) {
								destination = StringUtils.substringAfterLast(entry, "/");
							} else {
								destination = entry;
							}
							packager.getLogger().debug("     - Relocating " + entry + " to " + destination);
							return destination;
						}
			});
		}
		
		t24projectArchives.clear();
	}

	public T24Packager getPackager() {
		return packager;
	}

	public void setPackager(T24Packager packager) {
		this.packager = packager;
	}

	public List<T24ProjectArchive> getT24projectArchives() {
		return Collections.unmodifiableList(t24projectArchives);
	}
}
