package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.iris.generation.FileSystemHelper;
import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.iris.metadata.generation.IRISMetadataConfig;
import com.odcgroup.iris.t24mappings.generation.IRIST24MappingConfig;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryMode;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMEntityModel;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.di.StandaloneHeadlessStatus;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemNotifier;

/**
 * IRIS metadata.xml and *.properties generation cartridge.
 *
 * @author Michael Vorburger
 */
public class IRISMetadataGenerator2 implements CodeGenerator2, CodeGenerator {
	private final static int PROJECT_NAME_SEGMENT_IN_URI = 1;
	private static final Logger logger = LoggerFactory.getLogger(IRISMetadataGenerator2.class);
	private static final String ENQUIRY_EXT = "enquiry";
	private final SimplerEclipseResourceFileSystemNotifier changesNotifier = new SimplerEclipseResourceFileSystemNotifier();
	private final boolean skipDomainGenerationForIRIS = FeatureSwitches.skipDomainGenerationForIRIS.get();
	private final FileSystemHelper fileSystemHelper = new FileSystemHelper();

	@Override
	public final void doGenerate(URI uri, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		try{
			if (IRISMetadataMapper.isInterested(uri)) {
				IFileSystemAccess tmpFsa = fileSystemHelper.determineFsa(fsa);

				/*
				 * This will automatically update the lastChanges file everytimes a file is updated.
				 */
				if (tmpFsa instanceof SimplerEclipseResourceFileSystemAccess2){
					((SimplerEclipseResourceFileSystemAccess2)tmpFsa).setPostProcessor(changesNotifier);
				}
				
				boolean isDomain = uri.fileExtension().equals(GeneratorConstants.DOMAIN_EXT);
				final String modelName = getModelName(uri);
				IRISMetadataMapper mapper = new IRISMetadataMapper(modelName, uri, loader);
				if(skipDomainGenerationForIRIS && isDomain && mapper.isT24Nature()) {
					logger.debug("[skipDomainGenerationForIRIS=true] Skipping metadata generation for " + uri.toString());
					return;
				}
				for (EMEntityModel entityModel : getEntityModels(mapper)) {
					EMEntity entity = entityModel.getEntities().get(0);
					String fileName = entity.getName();
					generate(new IRISMetadataConfig(), "metadata-" + fileName + ".xml", entityModel, tmpFsa);
					boolean isROEnquiry = isROEnquiry(uri, loader);
					if (!isROEnquiry) {
						generate(new IRIST24MappingConfig(), "T24-" + fileName + ".properties", entityModel, tmpFsa);
					}
					
					boolean runningInEclipse = !StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless();
					
					if (mapper.isT24Nature() && isDomain && runningInEclipse && !fileName.startsWith("enqlist")){
						/*
						 * Generate the metadata fot the applicationVersion.
						 * First copy the entity (the application comming from the domain) and replace its name 
						 * by pre-fixing by "ver". Then copy all properties, vocabularyterms, ... 
						 * and replace the entity in the entity collections
						 */
						EMEntity domainEntity = entityModel.getEntities().get(0);
						EMEntity versionEntity = new EMEntity("ver" + fileName);
						versionEntity.setT24Name(domainEntity.getT24Name());
						versionEntity.addProperties(domainEntity.getProperties());
						List<EMTerm> terms = domainEntity.getVocabularyTerms();
						for (EMTerm term : terms){
							versionEntity.addVocabularyTerm(term);
						}
						entityModel.getEntities().add(versionEntity);
						entityModel.getEntities().remove(0); // Remove the original one

						logger.info("Generating IRIS application metadata and properties for " + fileName);

						generate(new IRISMetadataConfig(), "metadata-ver" + fileName + ".xml", entityModel, tmpFsa);
						if (!isROEnquiry) {
							generate(new IRIST24MappingConfig(), "T24-ver" + fileName + ".properties", entityModel, tmpFsa);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("Failed generation for '" + uri + "'", e);
		}
	}

	private void generate(IIRISGenerator irisGenerator, String fileName, EMEntityModel entityModel, IFileSystemAccess fsa) {
		HashMap<String, Variable> globalVars = new HashMap<String, Variable>();
		globalVars.put("fileName", new Variable("fileName", fileName));
		irisGenerator.doGenerate(entityModel, fsa, globalVars);
	}

	// returns the split entity models where each model will have just one entity
	private List<EMEntityModel> getEntityModels(IRISMetadataMapper mapper) {
		List<EMEntityModel> splitEntityModels = new ArrayList<EMEntityModel>();
		EMEntityModel masterEntityModel = mapper.getProject();
		for (EMEntity entity : masterEntityModel.getEntities()) {
			ensureAtIDInEntity(entity);
			EMEntityModel entityModel = new EMEntityModel(masterEntityModel.getName());
			entityModel.addEntity(entity);
			splitEntityModels.add(entityModel);
		}
		return splitEntityModels;
	}
	
	
	/*
	 * Ensure that the property Id (@ID) exists in entity.
	 * Required for all enquiries, but certainly for Domains and Versions as well.
	 */
	private void ensureAtIDInEntity(final EMEntity entity){
		for (EMProperty prop : entity.getProperties()){
			if (prop.getName().equals("Id")){
				/*
				 * The property "Id" already exists, get out of here !
				 */
				return;
			}
		}
		/*
		 * Add the property Id -> @ID in the entity.
		 */
		EMProperty prop = new EMProperty("Id");
		prop.setT24Name("@ID");
		entity.addProperty(prop);
	}
	

	// returns the name of the model (which is project name) from the URI
	private String getModelName(URI uri) {
		String projectName = uri.segment(PROJECT_NAME_SEGMENT_IN_URI);
		return projectName;
	}

	@Override
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Check if entity is an enquiry from a RO database. Throw any exceptions up to parent.
	 */
	private boolean isROEnquiry(URI uri, ModelLoader loader) throws Exception {
		
		// Non enquires are always T24.
		if (!ENQUIRY_EXT.equals(uri.fileExtension())) {
			return(false);
		}
		
		// Get the enqiry and check RO attribute
		Enquiry enquiry = loader.getRootEObject(uri, Enquiry.class);
		if (enquiry.getEnquiryMode() == EnquiryMode.DB) {
			return (true);
		}
		return (false);
	}
}
