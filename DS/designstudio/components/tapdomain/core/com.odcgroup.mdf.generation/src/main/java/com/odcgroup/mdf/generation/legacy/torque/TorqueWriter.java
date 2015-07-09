package com.odcgroup.mdf.generation.legacy.torque;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.common.mdf.generation.legacy.SkipImportDomainComputation;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.generation.legacy.xsd.XSDModelWriter;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;
import com.odcgroup.otf.xml.XMLNameFormat;

/**
 * A writer of torque XML database definition files
 * 
 * @author <a href="mailto:dnemeshazyodyssey-group.com">David Nemeshazy </a>
 * @version 1.0
 */
public class TorqueWriter implements ModelWriter, SkipImportDomainComputation {
	private static Logger LOGGER = LoggerFactory.getLogger(XSDModelWriter.class);

	private static final String COPYRIGHT = "Torque file automatically writen by Odyssey Torque generator. Do not modify.";

	/** TODO: DOCUMENT ME! */
	public static final XMLNameFormat NAME_FORMAT = XMLNameFormat.getDefault();

	private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);

	/**
	 * Creates a new TorqueWriter object.
	 */
	public TorqueWriter() {
	}

	/**
	 * @see com.odcgroup.mdf.ModelWriter#setMessageHandler(com.odcgroup.mdf.MessageHandler)
	 */
	public void setMessageHandler(MessageHandler handler) {
		if (handler == null) {
			throw new NullPointerException("handler");
		}

		this.messageHandler = handler;
	}

	/**
	 * @see com.odcgroup.mdf.transform.ModelWriter#getName()
	 */
	public String getName() {
		return "Torque generator";
	}

	/**
	 * @see com.odcgroup.mdf.ModelWriter#write(com.odcgroup.mdf.metamodel.MdfDomain,
	 *      java.io.File)
	 */
	public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
		// DAG: check in this method that the domain contains sql tags
		// Because DS studio generator access this method, and not the one on repository
		if (SQLAspect.containsClassWithSQL(domain)) {

			String path = getDomainTorquePath(domain.getName());
			messageHandler.info("Writing Torque files for domain [" + domain.getName() + "] to path [" + path + "]");

			OutputStream torque = factory.openStream(path);

			try {
				write(domain, torque);
			} finally {
				if (torque != null) {
					torque.close();
				}
			}

			// 2nd file in META-INF/torque, used by the tests.
			path = getDomainTorquePathForTests(domain.getName());
			torque = factory.openStream(path);

			try {
				write(domain, torque);
			} finally {
				if (torque != null) {
					torque.close();
				}
			}
		}

	}

	private void write(MdfDomain domain, OutputStream torque) throws IOException {
		OutputStream out = new BufferedOutputStream(torque);
		XMLSerializer xml = new XMLSerializer(out, new OutputFormat(Method.XML, "UTF-8", true));
		TorqueXMLSerializer serializer = new TorqueXMLSerializer(xml, messageHandler);

		try {
			// name, publicId, systemId
			xml.startDocument();
			xml.startDTD(TorqueConstants.DTD_NAME, null, TorqueConstants.SYSTEM_ID);
			xml.comment(COPYRIGHT);

			serializer.handle(domain);

			xml.endDTD();
			xml.endDocument();
			messageHandler.info("Done writing Torque file for domain [" + domain.getName() + "]");
		} catch (SAXException e) {
			messageHandler.error(e.getLocalizedMessage(), e);
			throw new IOException(e.getMessage());
		}
	}

	// let's use the same name as the schemas... not too bad for now.
	public static String getDomainTorquePath(String domain) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("db").append(File.separatorChar);
		buffer.append(NAME_FORMAT.format(domain)).append(".xml");

		return buffer.toString();
	}

	// let's use the same name as the schemas... not too bad for now.
	public static String getDomainTorquePathForTests(String domain) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("META-INF").append(File.separatorChar).append("torque").append(File.separatorChar);
		buffer.append(NAME_FORMAT.format(domain)).append(".xml");

		return buffer.toString();
	}

}
