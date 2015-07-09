package com.odcgroup.mdf.generation.legacy.castor;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.common.mdf.generation.legacy.SkipImportDomainComputation;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;


/**
 * Class to produce an XML map file for use with CASTOR. Creation date:
 * (5/8/2002 12:00 PM)
 *
 * @author: Peter Brooks
 * @author David Nemeshazy
 * @author David Nemeshazy
 * @version 0.3
 */
public class CastorXMLMapWriter extends CastorGenerationCore implements ModelWriter, SkipImportDomainComputation {
    private static Logger LOGGER = LoggerFactory.getLogger(CastorXMLMapWriter.class);
    private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);

    /**
     * Creates a new CastorXMLMapWriter object.
     */
    public CastorXMLMapWriter() {
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
     * @see com.odcgroup.mdf.generation.legacy.ModelWriter#getName()
     */
    public String getName() {
        return "Castor XML Mapping generator";
    }

    /**
     * @see com.odcgroup.mdf.ModelWriter#write(com.odcgroup.mdf.metamodel.MdfDomain,
     *      java.io.File)
     */
    public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
        writeDomain(domain, factory, messageHandler, true);
    }

}
