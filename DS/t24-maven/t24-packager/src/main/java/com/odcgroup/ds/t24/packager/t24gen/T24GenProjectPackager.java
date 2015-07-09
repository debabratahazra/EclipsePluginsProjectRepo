package com.odcgroup.ds.t24.packager.t24gen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.odcgroup.ds.t24.packager.generator.T24Packager;

public class T24GenProjectPackager {

	private T24Packager packager;
	
	private List<T24GenProjectArchive> t24genProjectArchives = new ArrayList<T24GenProjectArchive>();

	public void addT24GenProject(T24GenProjectArchive archive) {
		if(!archive.getArchive().exists()){
			packager.getLogger().warn("Ignoring the added archive as it does not exists "+archive.getArchive() );
			return;
		}
		t24genProjectArchives.add(archive);
	}
	
	public void processT24GenProjects() throws IOException {
		for (T24GenProjectArchive archive: t24genProjectArchives) {
			packager.getLogger().debug(" - processing " + archive.getArchive().getAbsolutePath());
			archive.extractTo(packager.getDsGeneratedFolder());
		}
	}

	public T24Packager getPackager() {
		return packager;
	}

	public void setPackager(T24Packager packager) {
		this.packager = packager;
	}
	
}
