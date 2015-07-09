package com.odcgroup.mdf.generation.legacy.java;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.common.mdf.generation.legacy.SkipImportDomainComputation;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.generation.legacy.mml.MmlModelWriter;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;


public class ModelSerializerWriter implements ModelWriter, SkipImportDomainComputation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelSerializerWriter.class);
    private static final String DOT_MODELS_PATH = "META-INF/models/.serializers";
    private static final String MODELS_FOLDER = "META-INF/models/";
    private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);

    public ModelSerializerWriter(MmlModelWriter writer) {
        super();
    }

    public ModelSerializerWriter() throws IOException {
        super();
    }

    public final String getDotModelsPath() {
        return DOT_MODELS_PATH;
    }

    /**
	 * @see com.odcgroup.mdf.mml.MmlModelWriter#getModelPath(java.lang.String)
	 */
    public final String getModelPath(String domain) {
        return MODELS_FOLDER + MmlModelWriter.getModelFileName(domain);
    }

    public void setMessageHandler(MessageHandler handler) {
        this.messageHandler = handler;
    }

    public String getName() {
        return "Serializer repository";
    }

    public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
        writeDotModels(domain, factory);
    }

    protected void writeDotModels(MdfDomain domain, OutputStreamFactory factory)
        throws IOException {
    	messageHandler.info("Updating .serializer file");
        String dotmodels = DOT_MODELS_PATH;
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    factory.openStream(dotmodels, true), "UTF-8"));

        List entities = domain.getClasses();
        for (Iterator iterator = entities.iterator(); iterator.hasNext();) {
        	MdfClass klass = (MdfClass) iterator.next();
            out.write(JavaCore.getQualifiedBeanClassName(klass));
            out.write(",");
            out.write(JavaCore.getQualifiedBeanClassSerializerName(klass));
            out.write(",");
            out.write(Long.toString(JavaCore.generateBeanSUID(klass)));
            out.write('\n');
            if (klass.hasPrimaryKey(true)){
                out.write(JavaCore.getQualifiedRefClassName(klass));
                out.write(",");
                out.write(JavaCore.getQualifiedRefClassSerializerName(klass));
                out.write(",");
                out.write(Long.toString(JavaCore.generateRefSUID(klass)));
                out.write('\n');
                
            }
        }
        
        out.close();
    }

}
