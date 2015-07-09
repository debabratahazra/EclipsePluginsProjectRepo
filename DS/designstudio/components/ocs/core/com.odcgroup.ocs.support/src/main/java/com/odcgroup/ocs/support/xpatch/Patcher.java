package com.odcgroup.ocs.support.xpatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Patcher extends DocumentUtils {

	private File file;
	private final Document original;
	private final List<Patch> appliedPatches = new ArrayList<Patch>();

	public Patcher(File file) throws SAXException, IOException {
		this.file = file;
		this.original = parse(file);
	}

	public File getFile() {
		return file;
	}

	public List<Patch> getAppliedPatches() {
		return Collections.unmodifiableList(appliedPatches);
	}

	public Patcher apply(Patch patch) throws DOMException,
			TransformerException, IOException {
		if (patch.apply(original)) {
			appliedPatches.add(patch);
		}
		return this;
	}

	public void save() throws IOException {
		saveAs(file);
	}

	public void saveAs(File file) throws IOException {
		this.file = file;
		FileOutputStream out = new FileOutputStream(file);
		try {
			write(original, out);
		} finally {
			out.close();
		}
	}
}
