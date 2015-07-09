package com.odcgroup.ocs.server.embedded.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WuiBlockHelper {
	
	private static final String BLOCK_XML = "BLOCK-INF/block.xml";
	
	private static Logger logger = LoggerFactory.getLogger(WuiBlockHelper.class);

	public List<File> sort(List<File> classpath) throws DuplicateWuiBlockIdException {
		Map<String, File> ids = new HashMap<String, File>();
		Map<Integer, List<File>> blockWeight = new TreeMap<Integer, List<File>>(new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				return i2-i1;
			}
		});
		List<File> tail = new ArrayList<File>();
		
		// Separate blocks from non blocks
		for (File f: classpath) {
			BlockInfo blockInfo = getBlockInfo(f);
			if (blockInfo != null) {
				String id = blockInfo.getId();
				if (ids.containsKey(id)) {
					throw new DuplicateWuiBlockIdException(id, ids.get(id).getAbsolutePath(), f.getAbsolutePath());
				}
				ids.put(id, f);
				if (!blockWeight.containsKey(blockInfo.getWeight())) {
					blockWeight.put(blockInfo.getWeight(), new LinkedList<File>());
				}
				blockWeight.get(blockInfo.getWeight()).add(f);
			} else {
				tail.add(f);
			}
		}
		
		// Assemble the result
		List<File> result = new ArrayList<File>();
		for (Map.Entry<Integer, List<File>> entry: blockWeight.entrySet()) {
			result.addAll(entry.getValue());
		}
		result.addAll(tail);
		
		return result;
	}

	/**
	 * Create a simplified block descriptor from a file or from a directory
	 * @param f file
	 * @return block descriptor
	 */
	protected BlockInfo getBlockInfo(File f) {
		if (f == null || !f.exists()) {
			return null;
		}
		File blockXml = new File(f, BLOCK_XML);
		InputStream inputStream = null;
		ZipFile zipFile = null;
		try {
			if (f.isDirectory()) {
				inputStream = new BufferedInputStream(new FileInputStream(
						blockXml));
				return getBlockInfo(inputStream);
			} else {
				zipFile = new ZipFile(f);
				ZipEntry entry = zipFile.getEntry(BLOCK_XML);
				if (entry != null) {
					InputStream zipInputStream = zipFile.getInputStream(entry);
					return getBlockInfo(zipInputStream);
				}
			}
		} catch (FileNotFoundException e) {
			// Ignore
		} catch (Exception e) {
			logger.warn("Unable to parse " + f.getAbsolutePath() + ", cause: "
					+ e.getMessage() + ". This block will be ignored.");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// Ignore
				}
			}
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					// Ignore
				}
			}
		}
		return null;
	}

	private BlockInfo getBlockInfo(InputStream inputStream) throws JDOMException, IOException, NumberFormatException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(inputStream);
		Element root = doc.getRootElement();
		String id = root.getAttributeValue("id");
		String weight = root.getAttributeValue("weight");
		if ((id != null) && (weight != null)) {
			int weightInt = Integer.parseInt(weight);
			return new BlockInfo(id, weightInt);
		}
		return null;
	}

}
