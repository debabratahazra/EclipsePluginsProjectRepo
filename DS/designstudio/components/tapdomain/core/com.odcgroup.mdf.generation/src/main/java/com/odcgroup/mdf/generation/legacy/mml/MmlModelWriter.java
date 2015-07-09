package com.odcgroup.mdf.generation.legacy.mml;

import java.io.BufferedOutputStream;
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
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;
import com.odcgroup.otf.xml.XMLNameFormat;


/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MmlModelWriter implements ModelWriter {
    private static Logger LOGGER = LoggerFactory.getLogger(MmlModelWriter.class);
    private static final XMLNameFormat XNF = XMLNameFormat.getDefault();
    private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);
    private boolean indenting = false;

    /**
     * Constructor for MmlModelWriter
     *
     * @throws IOException
     */
    public MmlModelWriter() throws IOException {
        super();
    }

    /**
     * Returns the MML file name for a given MDF domain name.
     *
     * @param domain the name of the domain
     *
     * @return a String containing the MML file name
     */
    public static String getModelFileName(String domain) {
        return XNF.format(domain) + ".mml";
    }

    /**
     * Sets whether output should be indented or not, defaults to
     * <code>false</code>.
     *
     * @param indenting <code>true</code> if the output should be indented,
     *        <code>false</code> otherwise
     */
    public void setIndenting(boolean indenting) {
        this.indenting = indenting;
    }

    /**
     * Tells whether output will be indented or not.
     *
     * @return <code>true</code> if the output will be indented,
     *         <code>false</code> otherwise
     */
    public boolean isIndenting() {
        return indenting;
    }
    
    /**
     * @see com.odcgroup.mdf.ModelReader#setMessageHandler(com.odcgroup.mdf.MessageHandler)
     */
    public void setMessageHandler(MessageHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");

        this.messageHandler = handler;
    }

    /**
     * @see com.odcgroup.mdf.ModelWriter#getName()
     */
    public String getName() {
        return "MML model writer";
    }

    /**
     * @see com.odcgroup.mdf.ModelWriter#write(com.odcgroup.mdf.metamodel.MdfDomain,
     *      java.io.File)
     */
    public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
        String path = getModelPath(domain);
        messageHandler.info("Writing domain [" + domain.getName() +
            "] to path [" + path + "]");

        write(domain, importedDomains, factory.openStream(path));
    }

    public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStream model)
        throws IOException {
        try {
            OutputStream out = new BufferedOutputStream(model);
            OutputFormat format = new OutputFormat(Method.XML, "UTF-8",
                    indenting);

            XMLSerializer xml = new XMLSerializer(out, format);
            MmlSerializer serializer = new MmlSerializer(this, xml, messageHandler);

            try {
                xml.startDocument();
                char[] header = getHeader(domain);
                if (header.length >0) {
                	serializer.comment(header, 0, header.length);
                }
                serializer.handle(domain, importedDomains);
                xml.endDocument();
            } catch (SAXException e) {
                messageHandler.error(e.getLocalizedMessage(), e);
                throw new IOException(e.getLocalizedMessage(), e);
            }
        } finally {
            model.close();
        }
    }
    
    public char[] getHeader(MdfDomain domain) {
    	return "".toCharArray();
    }

    public String getModelPath(MdfDomain domain) {
        return MmlModelWriter.getModelFileName(domain.getName());
    }

}
