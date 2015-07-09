package com.odcgroup.iris.rim.generation.mappers;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class ModelsGenEdgeFileWriter<PersistableObjectType extends FileSystemPersistable> {
    private static final String BASE_MODELS_GEN_EDGE_OUTPUT_PATH = "src/generated/edge/META-INF/resources/WEB-INF/";
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelsGenEdgeFileWriter.class);

    private final IProject m_hhouseModelsGenProject;
    private final SimplerEclipseResourceFileSystemAccess2 m_targetFSA;
    private final File m_outputDir;

    public ModelsGenEdgeFileWriter(SimplerEclipseResourceFileSystemAccess2 p_hhouseModelsFSA, String p_subDirPath) {
        final IProject hhouseModelsProject = p_hhouseModelsFSA.getProject();
        m_hhouseModelsGenProject = hhouseModelsProject.getWorkspace().getRoot().getProject(hhouseModelsProject.getName() + "-gen");
        m_targetFSA = createTargetFSA(m_hhouseModelsGenProject, p_subDirPath);
        m_outputDir = m_hhouseModelsGenProject.getLocation().append(BASE_MODELS_GEN_EDGE_OUTPUT_PATH).append(p_subDirPath).toFile();
    }
    
    public ModelsGenEdgeFileWriter(IProject p_hothouseModelsGenProject, String p_subDirPath) {
        m_hhouseModelsGenProject = p_hothouseModelsGenProject;
        m_targetFSA = createTargetFSA(m_hhouseModelsGenProject, p_subDirPath);
        m_outputDir = m_hhouseModelsGenProject.getLocation().append(BASE_MODELS_GEN_EDGE_OUTPUT_PATH).append(p_subDirPath).toFile();
    }
    
    public void writeToFile(PersistableObjectType p_persistable) throws Exception {
        CharSequence fileContents = p_persistable.getFileContent();
        
        if (fileContents == null)
            fileContents = "";
        
        final String filename = p_persistable.getFilename();
        final int numChars = fileContents.length();
        
        m_targetFSA.generateFile(p_persistable.getFilename(), fileContents);

        if (LOGGER.isInfoEnabled())
            LOGGER.info("Wrote " + numChars + " char" + ((numChars == 1) ? " to " : "s to ")  + p_persistable.getFileDescription() + " file: " + new File(m_outputDir, filename).getPath());
    }
    
    public IProject getHothouseModelsGenProject() {
        return m_hhouseModelsGenProject;
    }
    
    private static SimplerEclipseResourceFileSystemAccess2 createTargetFSA(IProject p_hothouseModelsGenProject, String p_subDirPath) {
        final SimplerEclipseResourceFileSystemAccess2 result = new SimplerEclipseResourceFileSystemAccess2();
        
        result.setProject(p_hothouseModelsGenProject);
        result.setOutputPath(BASE_MODELS_GEN_EDGE_OUTPUT_PATH + p_subDirPath);
        result.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT).setCreateOutputDirectory(true);
        result.setMonitor(new NullProgressMonitor()); // NOTE EclipseResourceFileSystemAccess2 *HAS* to have an IProgressMonitor (it doesn't check for null)
        
        return result;
    }
}
