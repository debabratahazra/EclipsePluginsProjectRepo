package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.generator.Application
import com.odcgroup.iris.generator.FieldTypeChecker
import com.odcgroup.iris.generator.IRISEnquiryMapper
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator
import com.odcgroup.t24.enquiry.enquiry.ApplicationType
import com.odcgroup.t24.enquiry.enquiry.BlankType
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenType
import com.odcgroup.t24.enquiry.enquiry.DownloadType
import com.odcgroup.t24.enquiry.enquiry.DrillDown
import com.odcgroup.t24.enquiry.enquiry.Enquiry
import com.odcgroup.t24.enquiry.enquiry.EnquiryType
import com.odcgroup.t24.enquiry.enquiry.EnquiryMode
import com.odcgroup.t24.enquiry.enquiry.FromFieldType
import com.odcgroup.t24.enquiry.enquiry.FunctionKind
import com.odcgroup.t24.enquiry.enquiry.JavaScriptType
import com.odcgroup.t24.enquiry.enquiry.PWProcessType
import com.odcgroup.t24.enquiry.enquiry.QuitSEEType
import com.odcgroup.t24.enquiry.enquiry.RunType
import com.odcgroup.t24.enquiry.enquiry.ScreenType
import com.odcgroup.t24.enquiry.enquiry.TabbedScreenType
import com.odcgroup.t24.enquiry.enquiry.UtilType
import com.odcgroup.t24.enquiry.enquiry.ViewType
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl
import com.odcgroup.t24.enquiry.util.EMUtils
import com.odcgroup.translation.translationDsl.LocalTranslation
import com.odcgroup.translation.translationDsl.Translations
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.DomainModel
import com.temenos.interaction.rimdsl.rim.Event
import com.temenos.interaction.rimdsl.rim.RimFactory
import com.temenos.interaction.rimdsl.rim.RimPackage
import com.temenos.interaction.rimdsl.rim.State
import com.temenos.interaction.rimdsl.rim.TransitionRef
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.InternalEObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon.*
import com.odcgroup.t24.enquiry.util.EMEntity
import com.odcgroup.t24.enquiry.util.EMProperty

class Enquiry2ResourceMapper {

	Logger _logger = LoggerFactory::getLogger(typeof(Enquiry2ResourceMapper));
    /*
     * Define if we want the drilldown links to be strict or quoted.
     */
    static val strictMode = "true".equals(System.getProperty("rim.strict.mode"));
    /*
     * Since looking for the fact that the resource already exist slow down a lot the generation, allow to skip it
     */
    static val fastMode = "true".equals(System.getProperty("rim.fast.mode"));
    
	
	def DomainModel mapEnquiry(ModelLoader loader, Enquiry enquiry, Application application) throws Exception {
		val eventFactory = new EventFactory(loader, enquiry)
		val entityName = T24ResourceModelsGenerator::TYPE_ENQUIRY + EMUtils::camelCaseName(enquiry.getName());	
		val thisEnquiryCollectionResource = entityName + "s";
		if (!fastMode){
			val targetEnquiryState = loader.getNamedEObjectOrProxy(enquiry, thisEnquiryCollectionResource, RimPackage.Literals::STATE, true, false) as State;
			if (targetEnquiryState != null) {
				val internal = targetEnquiryState as InternalEObject;
				val path = internal.eProxyURI();
				/*
				 * only skip it if this is in a adopted directory !
				 */
				for(String oneSegment : path.segments){
					if (oneSegment.equals("adopted")){
						_logger.info("Skipping generation of [" + enquiry.name + "] [" + thisEnquiryCollectionResource + "], already in our models project");
						return null;  // already in our models project
					}
				}
			}
		}
		_logger.info("Generating RIM for [" + enquiry.name + "]");
		
		val domainModel = RimFactory::eINSTANCE.createDomainModel;
		val enquiryMapper = new IRISEnquiryMapper();
		val fieldTypeChecker = new FieldTypeChecker(enquiry);
		val emEntity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE::enquiry);

		// domain
		val domain = RimFactory::eINSTANCE.createDomainDeclaration;
		domain.setName(T24ResourceModelsGenerator::getDomain(null));
		domainModel.rims.add(domain);

		// models references within this domain
		val modelReferences = domain.rims;
		addUsedDomains(modelReferences);
		
		// build the rim
		val rim = RimFactory::eINSTANCE.createResourceInteractionModel;
		rim.setName(entityName);
		modelReferences.add(rim);

		// create the command factory, this adds only the command used
		val commandFactory = new CommandFactory(rim.commands);
		// add the company basepath
		val basepath = RimFactory::eINSTANCE.createBasePath;
		basepath.setName("/{companyid}");
		rim.setBasepath(basepath);
		
		// We generate different commands if data is from a RO database.
		var collectionCommand = "";
		var itemCommand = ""
		
		if(isROEnquiry(enquiry)) {
			collectionCommand = "GETRODBEntities";
			itemCommand = "GETRODBEntity"; 
		} else {
			collectionCommand = "GETEntities";
			itemCommand = "GETEntity";			
		}  
		
		// one resource state for collection
		val collection = createCollectionResource(commandFactory, entityName, thisEnquiryCollectionResource, collectionCommand);
		rim.states.add(collection)				
		
		// one resource state for item
		val item = createItemResource(commandFactory, itemCommand, entityName);
		
		// transitions from collection
		val transitions = collection.getTransitions();
		// create the transition to the entity (in odata each collection, must have a link to an item)
		transitions.add(createTransitionForEach(eventFactory.createGET(), item, null, buildTransitionParametersItem(emEntity)));
		
		if("IM.DOCUMENT.IMAGE".equals(application.t24Name)) {
			// As we are dealing with an enquiry related to images add a transition to the image streaming resource
			val imageStreamingItem = createImageStreamingItemResource(commandFactory, "ImageDownload", "T24.enqImageList.StreamImage", "enqImageList");
			transitions.add(createTransitionForEach(eventFactory.createGET(), imageStreamingItem, null, buildImageStreamingTransitionParametersItem(emEntity)));
		}
		
		for (DrillDown dd : enquiry.drillDowns) {
			val drillDownResolver = new EnquiryDrillDownResolver(enquiry)
			var targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
			var target = ""; 
			val type = dd.type;
			if (type != null){ 
				if (type instanceof EnquiryType){
					targetType = T24ResourceModelsGenerator::TYPE_ENQUIRY;
					target = type.value;
				}else if (type instanceof ScreenType){
					targetType = T24ResourceModelsGenerator::TYPE_VERSION;
					target = type.value;
				}else if (type instanceof CompositeScreenType){
					targetType = T24ResourceModelsGenerator::TYPE_COMPOSITE;
					target = type.value;
				}else if (type instanceof TabbedScreenType){
					targetType = T24ResourceModelsGenerator::TYPE_COMPOSITE;
					target = type.value;
				}else if (type instanceof FromFieldType){
					targetType = T24ResourceModelsGenerator::TYPE_DYNAMIC;
					target = type.value;
				}else if (type instanceof QuitSEEType){
					targetType = T24ResourceModelsGenerator::TYPE_VERSION;
					target = application.t24Name; // We can now go straight to the application
					/*
					 * Do as if it were a See
					 */
					val parameters = new EnquiryFactoryImpl().createParameters();
					parameters.function = FunctionKind.SEE;
					parameters.fieldName.add(0, type.value); // This is the id of what we want to see.
					dd.parameters = parameters;
				}else if (type instanceof ApplicationType){
					targetType = T24ResourceModelsGenerator::TYPE_VERSION;
					target = type.value; // We can now go straight to the application
				}else if (type instanceof BlankType){ // eg SEAT.SCRIPTS.UPLOAD.enquiry
					// Nothing to do. Just cosmetic.
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
				}else if (type instanceof ViewType){
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
					target = type.value;
					_logger.error("Unsupported type of DrillDown in Enquiry [ " + enquiry.name + " ] : " + type.getClass().getName() + "\"");
				}else if (type instanceof DownloadType){
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
					target = type.value;
					_logger.error("Unsupported type of DrillDown in Enquiry [ " + enquiry.name + " ] : " + type.getClass().getName() + "\"");
				}else if (type instanceof PWProcessType){
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
					target = type.value;
					_logger.error("Unsupported type of DrillDown in Enquiry [ " + enquiry.name + " ] : " + type.getClass().getName() + "\"");
				}else if (type instanceof UtilType){ // eg EB.UPDATE.CLIENT.SYSTEMS.enquiry
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
					target = type.value;
					_logger.error("Unsupported type of DrillDown in Enquiry [ " + enquiry.name + " ] : " + type.getClass().getName() + "\"");
				}else if (type instanceof RunType){ // eg BATCH.STATUS.enquiry
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
					target = type.value;
					_logger.error("Unsupported type of DrillDown in Enquiry [ " + enquiry.name + " ] : " + type.getClass().getName() + "\"");
				}else if (type instanceof JavaScriptType){ // eg BATCH.STATUS.enquiry
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
					target = type.value;
					_logger.error("Unsupported type of DrillDown in Enquiry [ " + enquiry.name + " ] : " + type.getClass().getName() + "\"");
				}else {
					targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
					_logger.warn("Unsupported type of DrillDown in Enquiry [ " + enquiry.name + " ] : " + type.getClass().getName() + "\"");
				}
			}else{
				targetType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
				_logger.error("Impossible to determine the type of drillDown in Enquiry [ " + enquiry.name + " ]");
			}
			
			if (targetType == T24ResourceModelsGenerator::TYPE_DYNAMIC){
				mapDynamicDrillDown(dd, drillDownResolver, loader, enquiry, target, targetType, rim.name, transitions, eventFactory)
			} else if (targetType != T24ResourceModelsGenerator::TYPE_UNKNOWN){
				 //Now build the parameters.
				 val linkParameters = new HashMap<String,String>();
				 val parameters = drillDownResolver.resolveParameters(targetType,target, dd);
				 if (parameters != null){
				 	for (EnquiryDrillDownResolver.Parameter oneParam : parameters){
				 		linkParameters.put(oneParam.type, oneParam.value);
				 	}
				 }
				 
				 val targetDomain = T24ResourceModelsGenerator::getDomain(null);
                 val targetRim = targetType.toString() + EMUtils::camelCaseName(target);
  				 target = targetDomain + "." + targetRim + "." + drillDownResolver.getTargetResourceName();
  				 if (!strictMode){
  				 	target = "\"" + target + "\"";
  				 }
				 transitions.add(createTransitionForEach(eventFactory.createGET(), target, getLanguageString(dd.description, 0), linkParameters, dd.drill_name));
			}
		}

		// add all resource states
		val resources = rim.states;
		resources.add(collection);
		resources.add(item);

		return domainModel;
	}
	
	// Create the image streaming resource for each item in a collection
	def static State createImageStreamingItemResource(CommandFactory commandFactory, String commandName, String resourceName, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(resourceName);
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		
		val itemCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.setView(itemCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", itemCommand));
		
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+ entityName + "s('{id}')");
		item.setPath(itemPath);
		return item;
	}
	
	
	def static Map<String,String> buildImageStreamingTransitionParametersItem(EMEntity emEntity) {
		// create the transition to the entity (in odata each collection, must have a link to an item)
		val parameters = new HashMap<String,String>();
		
		// Not used by the image streaming resource but currently mandatory :-(
		parameters.put("customer_id", "XXX");
		
		for (EMProperty property : emEntity.properties) {
			if ("IMAGE".equals(property.t24Name)) {
				parameters.put("document_id", "{" + EMUtils::camelCaseName(property.name) + "}");
			}
		}
		return parameters;
	}
	
	
	/**
	 * Check if enquiry is from a RO database.
	 */
	def static boolean isROEnquiry(Enquiry enquiry) {
		if (enquiry.getEnquiryMode() == EnquiryMode.DB) {
			return(true);
		}
		return(false);	
	}
	
	def static mapDynamicDrillDown(DrillDown dd, EnquiryDrillDownResolver drillDownResolver, ModelLoader loader, Enquiry enquiry, String target, RESOURCE_TYPE targetType, String rimName, EList<TransitionRef> transitions, EventFactory eventFactory) {
		/*
		 * Now build the parameters.
		 */
		 val linkParameters = new HashMap<String,String>();
		 val parameters = drillDownResolver.resolveParameters(targetType,target, dd);
		 
		 if (parameters != null){
		 	for (EnquiryDrillDownResolver.Parameter oneParam : parameters){
		 		linkParameters.put(oneParam.type, oneParam.value);
		 	}
		 }
		 
		val locatorName = "t24ResourceLocator";			
		val targetStateName = "locator "  + locatorName + "(\"{" + EMUtils::camelCaseName( target ) + "}\")";					
		var transition = createTransitionForEach(eventFactory.createGET(), targetStateName, getLanguageString(dd.description, 0), linkParameters);
			
		transitions.add(transition);
	}	
	
	def static TransitionRef buildTransition(TransitionRef transition, Event event, String targetStr, String titleStr, Map<String,String> parameters) {
		transition.setEvent(event);
		transition.setName(targetStr);
		return buildTransitionSpec(transition, titleStr, parameters);
	}

	def static String getLanguageString(Translations translations, int language) {
		if ( translations != null ) {
			val strings = translations.eContents();
        	val translation = strings.get(language) as LocalTranslation;
			if ( translation != null ) {
                return translation.text;
            }
        }
        return null;
    }
}
