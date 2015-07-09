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
 * TODO: DOCUMENT ME!
 *
 * @version 1.0
 * @author <a href="mailto:$author_email$">$author_name$</a>
 */
public class CastorJDOMapWriter extends CastorGenerationCore implements ModelWriter, SkipImportDomainComputation {
    private static Logger LOGGER = LoggerFactory.getLogger(CastorJDOMapWriter.class);
    private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);

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
     * @see com.odcgroup.mdf.ModelWriter#getName()
     */
    public String getName() {
        return "Castor JDO Mapping generator";
    }

    /**
     * @see com.odcgroup.mdf.ModelWriter#write(com.odcgroup.mdf.metamodel.MdfDomain,
     *      com.odcgroup.mdf.OutputStreamFactory)
     */
    public void write(MdfDomain domain, Collection<MdfDomain> importedDomain, OutputStreamFactory factory)
        throws IOException {
        writeDomain(domain, factory, messageHandler, false);
        writeDatabaseFileForDomain(domain, factory, messageHandler);
        writeOtfServicesPatch(domain, factory, messageHandler);
    }
}
