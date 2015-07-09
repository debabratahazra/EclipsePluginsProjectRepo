package com.odcgroup.mdf.generation.legacy.java;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.LoggerFactory;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.common.mdf.generation.legacy.SkipImportDomainComputation;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;


/**
 * A MDF writer that generates Java source files.
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class AbstractSourceCodeWriter implements ModelWriter, SkipImportDomainComputation {
    private MessageHandler messageHandler = null;
    private OutputStreamFactory factory = null;
    private final SourceCodeGeneratorFactory generators;
    private final String name;

    /**
     * Constructor for ByteCodeWriter
     */
    public AbstractSourceCodeWriter(String name,
        SourceCodeGeneratorFactory generators) {
        super();
        this.name = name;
        this.generators = generators;
        messageHandler = new Slf4jMessageHandler(LoggerFactory.getLogger(getClass()));
    }

    /**
     * @see com.odcgroup.mdf.ModelWriter#setMessageHandler(com.odcgroup.mdf.MessageHandler)
     */
    public void setMessageHandler(MessageHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");

        this.messageHandler = handler;
    }

    /**
     * @see com.odcgroup.mdf.generation.legacy.ModelWriter#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see com.odcgroup.mdf.ModelWriter#write(com.odcgroup.mdf.metamodel.MdfDomain,
     *      java.io.File)
     */
    public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
        this.factory = factory;
        
        try {
        	handle(domain);
        } catch (RuntimeException e) {
        	throw new RuntimeException("Error while generating code of the " + domain + " domain", e);
        }

        Iterator<MdfEntity> it = domain.getEntities().iterator();
        while (it.hasNext()) {
            MdfEntity currentEntity = it.next();
            try {
            	handle(currentEntity);
            } catch (RuntimeException e) {
            	throw new RuntimeException("Error while generating code of the "
            			+ currentEntity.toString() + " entity (of the "
            			+ domain + " domain)", e);
            }
        }
        
        Iterator<MdfPrimitive> it2 = domain.getPrimitives().iterator();
        while (it2.hasNext()) {
        	MdfPrimitive currentPrimitive = it2.next();
            try {
            	handle(currentPrimitive);
            } catch (RuntimeException e) {
            	throw new RuntimeException("Error while generating code of the "
            			+ currentPrimitive.toString() + " primitive (of the "
            			+ domain + " domain)", e);
            }
        }
    }

    private void handle(MdfModelElement element) throws IOException {
        handle(element, generators.getGenerators(element));
    }

    private void handle(MdfModelElement element,
        SourceCodeGenerator[] generators) throws IOException {
        if (generators != null) {
            for (int i = 0; i < generators.length; i++) {
                handle(element, generators[i]);
            }
        }
    }

    private void handle(MdfModelElement element, SourceCodeGenerator gen)
        throws IOException {
        if (gen != null) {
            messageHandler.info("Generating class " + gen.getClassName() +
                " for model element " + element.getName());

            String sourceCode = gen.generate();

            String path = gen.getClassName().replace('.', File.separatorChar) +
                ".java";

            OutputStreamWriter writer = new OutputStreamWriter(factory.openStream(
                        path), "UTF-8");

            try {
                writer.write(sourceCode);
            } finally {
                writer.close();
            }
        }
    }

}
