package com.odcgroup.iris.rim.generation.mappers;

/**
 * A <code>IrisResourceUrlSetWriter</code> instance knows how to persist a {@link IrisResourceUrlSet} object to the appropriate folder under the hothouse-models-gen project.
 *
 * @author Simon Hayes
 */
public class IrisResourceUrlSetWriter extends ModelsGenEdgeFileWriter<IrisResourceUrlSet> {
    public IrisResourceUrlSetWriter(PatternCOSPropertiesWriter p_cosPropsWriter) {
        super(p_cosPropsWriter.getHothouseModelsGenProject(), "data/COS/preload");
    }
}
