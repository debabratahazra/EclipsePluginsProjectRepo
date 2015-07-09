package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.generator.IRISDefaultListEnquiryMapper
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator
import com.odcgroup.mdf.metamodel.MdfClass
import com.odcgroup.t24.applicationimport.T24Aspect
import com.odcgroup.t24.applicationimport.schema.ApplicationType
import com.odcgroup.t24.enquiry.util.EMUtils
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.DomainModel
import com.temenos.interaction.rimdsl.rim.RimFactory
import com.temenos.interaction.rimdsl.rim.RimPackage
import com.temenos.interaction.rimdsl.rim.State
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.xtext.generator.IFileSystemAccess
import org.slf4j.LoggerFactory

import static com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon.*

/**
 * This class is responsible for building the DomainModel from the MdfClass to build rim for the default list enquiry 
 * of the application (represented by the MdfClass)
 */
class Domain2ResourceMapper {
	
	
	 /*
     * Since looking for the fact that the resource already exist slow down a lot the generation, 
     * allow to skip it. In the case of 500 domain in a 1/2 fully loaded environment, having this flag turned on 
     * the rim generation takes 30 seconds. If the flag is turned off, this takes 166 seconds !
     */
    static val fastMode = "true".equals(System.getProperty("rim.fast.mode"));
	
	def DomainModel mapDomain(MdfClass mdfClass, ModelLoader modelLoader, IFileSystemAccess fsa) throws Exception {
		
		var logger = LoggerFactory::getLogger(typeof(Domain2ResourceMapper));
		
		// build the enquiry name
		val enqName = T24ResourceModelsGenerator::TYPE_ENQLIST + EMUtils::camelCaseName(mdfClass.name)
		val enqCollectionName = enqName + "s";
		val rimName =  enqName;

		if (!fastMode){
			// check if the enquiry already exists to ignore the rim generation (if exists)
		    val domainEobject = mdfClass as EObject

			val existingResource = modelLoader.getNamedEObjectOrProxy(domainEobject, enqCollectionName, RimPackage.Literals::STATE, true, false) as State;
			if (existingResource != null) {
				val internal = existingResource as InternalEObject;
				val path = internal.eProxyURI();
				/*
				 * only skip it if this is in a adopted directory !
				 */
				for(String oneSegment : path.segments){
					if (oneSegment.equals("adopted")){
						logger.info("Skipping generation of [" + rimName + "], already in our models project");
						return null;  // already in our models project
					}
				}
			}
		}

		logger.info("Generating RIM for [" + rimName + "]");		
		
		val domainModel = RimFactory::eINSTANCE.createDomainModel
		
		val domain = RimFactory::eINSTANCE.createDomainDeclaration;
		domain.setName(T24ResourceModelsGenerator::getDomain(null));
		domainModel.rims.add(domain)
		
		val modelReferences = domain.rims
		addUsedDomains(modelReferences);
		
		val rim = RimFactory::eINSTANCE.createResourceInteractionModel
		rim.setName(rimName)
		modelReferences.add(rim);
		
		// initialise the underlying application type
		var applicationType = "";
		
		val ApplicationType tmpAppType = T24Aspect::getType(mdfClass);
		
		if(tmpAppType != null) { 
			applicationType = tmpAppType.name;
		}
		
		var defaultListEnquiryMapper = new IRISDefaultListEnquiryMapper();
		var defaultEnquiryEntity = defaultListEnquiryMapper.getExistingEnquiry(mdfClass, modelLoader, "");
		/*
		 * We don't really know what to do if neither the %enquiry nor the enquiry-LIST exists.
		 * So we do as if the %ENQUIRY was existing.
		 */
		var targetReference = "";
		if (defaultEnquiryEntity != null){
			targetReference = T24ResourceModelsGenerator::TYPE_ENQUIRY + EMUtils::camelCaseName(defaultEnquiryEntity.name) + "s";
		}else{
			targetReference = T24ResourceModelsGenerator::TYPE_ENQUIRY + EMUtils::camelCaseName("%" + mdfClass.name) + "s";
		}
		
		val commandFactory = new CommandFactory(rim.commands);
		
		val basepath = RimFactory::eINSTANCE.createBasePath;
		basepath.setName("/{companyid}");
		rim.setBasepath(basepath);
		
		// event creation
		val getEvent = RimFactory::eINSTANCE.createEvent();
		getEvent.httpMethod = "GET";
		getEvent.name = "GET"
		
		// one resource state for collection
		val collection = createCollectionResource(commandFactory, enqName, enqCollectionName);
		var relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://schemas.microsoft.com/ado/2007/08/dataservices/related/live/" + targetReference);
		collection.relations.add(relation);
		rim.states.add(collection)
		
		// one resource state for live item
		val liveItem = createDomainItemResource(commandFactory, enqName);		
		// transitions from collection
		var transitions = collection.getTransitions();
		transitions.add(createTransitionForEach(getEvent, liveItem, null, buildTransitionParametersItem()));
		rim.states.add(liveItem);
		
		if (isUnauthFile(applicationType)) {
			// collection resource for unauth
			val unauthCollectionResource = createDefaultListCollectionResource(commandFactory, enqName, "Unauth", "GETEntities");
			relation = RimFactory::eINSTANCE.createRelationConstant();
			/*
			 * We maybe need to re-query the defaultListEnquiryMapper with $NAU as suffix to get a new targetReference
			 */
		    relation.setName("http://schemas.microsoft.com/ado/2007/08/dataservices/related/unauth/" + targetReference);
		    unauthCollectionResource.relations.add(relation);
			rim.states.add(unauthCollectionResource);
		
			// one resource state for unauth item
			val unauthItem = createDefaultListItemResource(commandFactory, enqName, "Unauth", "GETEntity");		
			// transitions from collection
			transitions = unauthCollectionResource.getTransitions();
			transitions.add(createTransitionForEach(getEvent, unauthItem, null, buildTransitionParametersItem()));
			rim.states.add(unauthItem);
			
			if (isHistFile(applicationType)) {		
				// collection resource for hist
				val histCollectionResource = createDefaultListCollectionResource(commandFactory, enqName, "Hist", "GETEntities");
				relation = RimFactory::eINSTANCE.createRelationConstant();
				/*
			 	* We maybe need to re-query the defaultListEnquiryMapper with $HIS as suffix to get a new targetReference
			 	*/
		        relation.setName("http://schemas.microsoft.com/ado/2007/08/dataservices/related/history/" + targetReference);
		        histCollectionResource.relations.add(relation);
				rim.states.add(histCollectionResource);		
		
				// one resource state for hist item
				val histItem = createDefaultListItemResource(commandFactory, enqName, "Hist", "GETEntity");		
				// transitions from collection
				transitions = histCollectionResource.getTransitions();
				transitions.add(createTransitionForEach(getEvent, histItem, null, buildTransitionParametersItem()));
				rim.states.add(histItem);		
			}
		}
		return domainModel
	}	
	
	def static Map<String,String> buildTransitionParametersItem() {
		// create the transition to the entity (in odata each collection, must have a link to an item)
		val parameters = new HashMap<String,String>();
		parameters.put("id", "{Id}");
		return parameters;
	}
}
