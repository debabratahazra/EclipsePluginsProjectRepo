package com.odcgroup.t24.application.importer.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class LocalApplicationLoader implements IExternalLoader {

	private File folder;

	private Map<String, String> files;

	public LocalApplicationLoader(Properties properties, File folder) {
		this.folder = folder;
	}

	@Override
	public void open() throws T24ServerException {
		// null;
	}

	@Override
	public void close() {
		// null;
	}

	private String getKey(ApplicationDetail detail) {
		StringBuilder key = new StringBuilder(64);
		key.append(detail.getName());
		key.append(detail.getComponent());
		return key.toString();
	}

	private ApplicationDetail getApplicationDetail(File file) {
		String filename = file.getName();
		int pos = filename.lastIndexOf(".xml");
		if (pos != -1) {
			filename = filename.substring(0,pos);
		}
		String part[] = StringUtils.splitPreserveAllTokens(filename, '-');
		String product = part[0];
		String component = part[1];
		String name = part[2];
		ApplicationDetail detail = new ApplicationDetail(name, component, product);
		return detail;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		List<ApplicationDetail> details = new ArrayList<ApplicationDetail>();
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(".xml")) {
				ApplicationDetail detail = getApplicationDetail(file);
				if (detail != null) {
					details.add(detail);
				}
			}
		}
		return (List<T>) details;
	}

	private File getFile(ApplicationDetail detail) {
		if (files == null) {
			files = new HashMap<String, String>();
			for (File file : folder.listFiles()) {
				// keep only xml files, ignore others ex: ".svn"
				if (file.getName().endsWith(".xml")) {
					files.put(getKey(getApplicationDetail(file)), file.getPath());
				}
			}
		}
		File file = null;
		String filename = files.get(getKey(detail));
		if (filename != null) {
			file = new File(filename);
		}
		return file;
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail)	throws T24ServerException {
		StringBuilder buffer = new StringBuilder();

		File file = getFile((ApplicationDetail) detail);
		if (file != null) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
			} catch (IOException ex) {
				throw new T24ServerException(ex);
			} finally {
				IOUtils.closeQuietly(br);
			}
		}
		return buffer.toString();
	}

}
