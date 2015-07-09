package com.odcgroup.t24.application.importer.internal;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.helper.ImportHelper;


/**
 * This class is responsible to read the fix provided in server.properties file
 * and make modify the xml using given input to avoid import errors.
 * 
 * @author mumesh
 * 
 */
public class ModifyXml<T> implements IImportingStep<ApplicationInfo> {

	private IImportModelReport report;

	/**
	 * @param report
	 * @param server2
	 */
	public ModifyXml(IImportModelReport report) {
		this.report = report;
	}

	@Override
	public boolean execute(ApplicationInfo model, IProgressMonitor monitor) {
		try {
			File file = ImportHelper.getIntrospectionFile();
			if(file != null){
				Properties importFixProperties = ImportHelper.readPropertiesFile(file);
				String applicationFix = ImportHelper.getApplicationFix(importFixProperties);
				List<String> list = Arrays.asList(applicationFix.split("\\|\\|"));
				if (null != list && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						String entry = list.get(i);
						List<String> entryList = Arrays.asList(entry.split("\\|"));
						if (entryList.get(0).equals(model.getXMLFilename()) && (entryList.size() == 3)) {
							String actualXml = model.getXmlString();
							if (actualXml.contains(entryList.get(1))) {
								String modifiedXml = StringUtils.replace(actualXml,
										entryList.get(1), entryList.get(2));
								model.setXmlString(modifiedXml);
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			report.error(model, e);
			return false;
		}
	}


}