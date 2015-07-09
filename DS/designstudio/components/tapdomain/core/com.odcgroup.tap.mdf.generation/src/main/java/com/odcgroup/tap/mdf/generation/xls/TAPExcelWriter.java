package com.odcgroup.tap.mdf.generation.xls;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import jxl.write.WritableWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.common.mdf.generation.legacy.SkipImportDomainComputation;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;
import com.odcgroup.otf.xml.XMLNameFormat;

public class TAPExcelWriter implements ModelWriter, SkipImportDomainComputation {
	private static final Logger LOGGER = LoggerFactory
	.getLogger(TAPExcelWriter.class);	
	
	public static final XMLNameFormat NAME_FORMAT = XMLNameFormat.getDefault();

	private static final String NAME = "Excel file generator";

	private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);

	/**
	 * @see com.odcgroup.mdf.ModelWriter#getName()
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * @see com.odcgroup.mdf.ModelWriter#setMessageHandler(com.odcgroup.mdf.MessageHandler)
	 */
	public void setMessageHandler(MessageHandler handler) {
		messageHandler = handler;
	}

	/**
	 * @see com.odcgroup.mdf.ModelWriter#write(com.odcgroup.mdf.metamodel.MdfDomain,
	 *      com.odcgroup.mdf.OutputStreamFactory)
	 */
	public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
		String path = getExcelPath(domain);
		messageHandler.info("Writing Excel file for domain ["
				+ domain.getName() + "] to path [" + path + "]");

		OutputStream excelStream = factory.openStream(path);

		try {
			TAPExcelDomainGenerator generator = new TAPExcelDomainGenerator(domain);
			
			WritableWorkbook workbook = generator.createWorkbook(excelStream);
			
			workbook.write();
			workbook.close();
			
			messageHandler.info("Done writing Excel file for domain ["
                    + domain.getName() + "]");
		} catch (Exception e) {
			messageHandler.error(e.getLocalizedMessage(), e);
			throw new IOException(e.getMessage());
		} finally {
			if (excelStream != null) {
				excelStream.close();
			}
		}

	}

	// Excel files per domain will be located in doc directory
	public static String getExcelPath(MdfDomain domain) {
		StringBuffer b = new StringBuffer();
		b.append("doc").append(File.separatorChar);
		b.append(NAME_FORMAT.format(domain.getName())).append(".xls");
		return b.toString();
	}

}
