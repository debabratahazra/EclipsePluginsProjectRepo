package com.odcgroup.ds.t24.packager.t24tafj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.odcgroup.ds.t24.packager.generator.T24Packager;
import com.odcgroup.ds.t24.packager.helper.ZipHelper;

public class TafjProjectPackager {
	
	private T24Packager packager;

	private List<TafjProjectArchive> t24TafjProjectArchives = new ArrayList<TafjProjectArchive>();
	
	public void addTafjProject(TafjProjectArchive archive) {
		t24TafjProjectArchives.add(archive);
	}

	public void processTafjProjects() throws IOException {
		for (final TafjProjectArchive tafjProjectArchive: t24TafjProjectArchives) {
			packager.getLogger().debug(" - processing : " + tafjProjectArchive.getArchive().getAbsolutePath());
			
			// Include the jar to the tar folder
			packager.getLogger().debug("   - Copying jar");
			tafjProjectArchive.copyJarTo(packager.getTafjFolder());
			
			// Extract all the public basic files to the tar folder
			if (tafjProjectArchive.getDataPublicCodeArchive().exists()) {
				packager.getLogger().debug("   - processing attached artifact : " + tafjProjectArchive.getDataPublicCodeArchive().getAbsolutePath());
				packager.getLogger().debug("   - Extracting public basic files");
				tafjProjectArchive.extractPublicBasicTo(packager.getPackageRootFolder(),
						new ZipHelper.Relocate() {
					public String relocate(String entry) {
						int index = entry.indexOf("/");
						if (index == -1)
							return entry;
						String component = entry.substring(0, index);
						if (!StringUtils.equals(component, packager.getComponentName())) {
							throw new IllegalArgumentException("Archive format error: component mismatch between packager and projects (Packager component: " + packager.getComponentName() +", component in archive: " + component + ", archive: " + tafjProjectArchive.getArchive().getAbsolutePath());
						}
						String rest = entry.substring(index+1);
						return "/BP/" + rest;
					}
				});
				
				// Extract all the data to the tar folder
				packager.getLogger().debug("   - Extracting all data files");
				tafjProjectArchive.extractDataTo(packager.getPackageRootFolder(),
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
			} else {
				packager.getLogger().debug("   - Skipping data and basic processing as not attached artifact were found: " + tafjProjectArchive.getDataPublicCodeArchive().getAbsolutePath());
			}

		}
		
		t24TafjProjectArchives.clear();
	}

	public T24Packager getPackager() {
		return packager;
	}

	public void setPackager(T24Packager packager) {
		this.packager = packager;
	}

}
