package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.generator.Application
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator
import com.odcgroup.t24.applicationimport.T24Aspect
import com.odcgroup.t24.enquiry.util.EMUtils
import com.odcgroup.t24.version.versionDSL.Version
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.DomainModel
import com.temenos.interaction.rimdsl.rim.RimFactory
import com.temenos.interaction.rimdsl.rim.RimPackage
import com.temenos.interaction.rimdsl.rim.State
import org.eclipse.emf.ecore.InternalEObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon.*

class Version2ResourceMapper {
	
	Logger _logger = LoggerFactory::getLogger(typeof(Version2ResourceMapper));
	/*
     * Since looking for the fact that the resource already exist slow down a lot the generation, allow to skip it
     */
    static val fastMode = "true".equals(System.getProperty("rim.fast.mode"));
	
	def DomainModel mapVersion(ModelLoader loader, Version version) throws Exception {
		_logger.info("Generating RIM from Version [" + version.t24Name + "]");
		val mdfClass = version.getForApplication();
		val application = new Application(mdfClass);
		if (application.getT24Name() == null)
			throw new Exception("Application for Version not found, unable to generate RIM");
		
		val domainModel = RimFactory::eINSTANCE.createDomainModel;
		val entityName = T24ResourceModelsGenerator::TYPE_VERSION + EMUtils::camelCaseName(version.t24Name);
		val thisVersionCollectionResource = entityName + "s";
		if (!fastMode){
			val targetEnquiryState = loader.getNamedEObjectOrProxy(version, thisVersionCollectionResource, RimPackage.Literals::STATE, true, true) as State;
			if (targetEnquiryState != null) {
				val internal = targetEnquiryState as InternalEObject;
				val path = internal.eProxyURI();
				/*
				 * only skip it if this is in a adopted directory !
				 */
				for(String oneSegment : path.segments){
					if (oneSegment.equals(MappersConstants.ADOPTED_RIM_SEGMENT)){
						_logger.info("Skipping generation of ["+version.t24Name+"] [" + thisVersionCollectionResource + "], already in our models project");
						return null;  // already in our models project
					}
				}
			}
		}
		
		_logger.info("Generating RIM for [" + version.t24Name + "]");

		// domain
		val domain = RimFactory::eINSTANCE.createDomainDeclaration;
		domain.setName(T24ResourceModelsGenerator::getDomain(null));
		domainModel.rims.add(domain);

		// models references within this domain
		val modelReferences = domain.rims;
		addUsedVersionDomains(modelReferences);
		
		// Specific to Version. Do not use Enquiry2ResourceMapper.
		val useFactory = new ImportedNamespaceFactory(modelReferences);
		useFactory.createUse(MappersConstants.RIM_USE_T24_CONTEXT_ENQUIRY);
		
		// build the rim
		val rim = RimFactory::eINSTANCE.createResourceInteractionModel;
		rim.setName(entityName);
		modelReferences.add(rim);

		// initialise the underlying application type
		val applicationType = T24Aspect::getType(mdfClass).name;
		
		var T24VersionMapper mapper = null;
		if (isUnauthFile(applicationType)) {
			mapper = new Version2ResourceMapperForLiveNauHist (loader, mdfClass, application, version, rim)
		} else if (isOnlyLiveFile(applicationType)) {
			mapper = new Version2ResourceMapperForLiveOnly(loader, mdfClass, application, version, rim)
		} else if (isVerifiable(applicationType)) {
			mapper = new Version2ResourceMapperForVerifyOnly (loader, mdfClass, application, version, rim)
		} 
		
		// Call generate on Mapper
		if (mapper != null) 
			mapper.generate();
		
		// Return
		return domainModel;
	}
}
