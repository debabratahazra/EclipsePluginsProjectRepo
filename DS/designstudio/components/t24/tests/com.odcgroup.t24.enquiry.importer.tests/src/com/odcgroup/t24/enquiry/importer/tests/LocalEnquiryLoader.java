package com.odcgroup.t24.enquiry.importer.tests;

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

import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class LocalEnquiryLoader implements IExternalLoader {

	private File folder;

	private Map<String, String> files;

	public LocalEnquiryLoader(Properties properties, File folder) {
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

	private String getKey(EnquiryDetail detail) {
		StringBuilder key = new StringBuilder(64);
		key.append(detail.getName());
		key.append(detail.getComponent());
		key.append(detail.getModule());
		return key.toString();
	}

	private EnquiryDetail getEnquiryDetail(File file) {
		String filename = file.getName();
		int pos = filename.lastIndexOf(".xml");
		if (pos != -1) {
			filename = filename.substring(0,pos);
		}
		String part[] = StringUtils.splitPreserveAllTokens(filename, '-');
		String component = part[0];
		String module = part[1];
		String name = part[2];
		EnquiryDetail detail = new EnquiryDetail(name, component, module);
		return detail;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		List<EnquiryDetail> details = new ArrayList<EnquiryDetail>();
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(".xml")) {
				EnquiryDetail detail = getEnquiryDetail(file);
				if (detail != null) {
					details.add(detail);
				}
			}
		}
		return (List<T>) details;
	}

	private File getFile(EnquiryDetail detail) {
		if (files == null) {
			files = new HashMap<String, String>();
			for (File file : folder.listFiles()) {
				if (file.getName().endsWith(".xml")) {
					files.put(getKey(getEnquiryDetail(file)), file.getPath());
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
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		StringBuilder buffer = new StringBuilder();

		File file = getFile((EnquiryDetail) detail);
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
