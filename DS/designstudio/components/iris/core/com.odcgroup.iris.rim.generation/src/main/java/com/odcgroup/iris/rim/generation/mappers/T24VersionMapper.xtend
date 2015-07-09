package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.generator.Application
import com.odcgroup.iris.generator.FieldTypeChecker
import com.odcgroup.iris.generator.IRISDomainMapper
import com.odcgroup.iris.generator.IRISEnquiryMapper
import com.odcgroup.iris.generator.IRISVersionMapper
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator
import com.odcgroup.mdf.metamodel.MdfClass
import com.odcgroup.t24.applicationimport.T24Aspect
import com.odcgroup.t24.enquiry.enquiry.Enquiry
import com.odcgroup.t24.enquiry.util.EMEntity
import com.odcgroup.t24.enquiry.util.EMUtils
import com.odcgroup.t24.version.versionDSL.Version
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.Event
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel
import com.temenos.interaction.rimdsl.rim.RimFactory
import com.temenos.interaction.rimdsl.rim.RimPackage
import com.temenos.interaction.rimdsl.rim.State
import com.temenos.interaction.rimdsl.rim.TransitionRef
import java.util.HashMap
import java.util.Map

import static com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon.*

/**
 * Super class which will provide common functionality required for different types of Version2Resource Mappers
 *
 * @author sjunejo
 *
 */
abstract class T24VersionMapper {
	
	// Global Variables
	protected ModelLoader loader
	protected MdfClass mdfClass
	protected Application application
	protected Version version
	protected EMEntity emEntity
	protected String entityName
	protected String thisVersionCollectionResource
	protected ResourceInteractionModel rim
	protected EventFactory eventFactory
	protected CommandFactory commandFactory
	
	protected String applicationType;
	protected String additionalInfo;
	protected boolean isAAVersion
	protected boolean isAAArr
	protected FieldTypeChecker fieldTypeChecker
//	var fieldTypeChecker = new FieldTypeChecker(MappersConstants.FIELD_TYPE_CHECKER_FILE_LOCATION)
	
	/*
	 * A Flag to know if we already tried to get the errorResource or not
	 */
	protected static boolean errorsResourceSearched = false;
	protected static State checkIfErrorResource = null;
	protected static State processErrorResource = null;
	protected static State errorsResource = null;
	protected static State customErrorHandler = null;
	protected static State metadataResource = null;
	protected static State regularMetadataResourceForAADeals = null;
	
	new(ModelLoader _loader, MdfClass _mdfClass, Application _application, Version _version, 
		ResourceInteractionModel _rim) {
		loader = _loader
		mdfClass = _mdfClass
		application = _application
		version = _version
		rim = _rim
		fieldTypeChecker = new FieldTypeChecker(_version)
		// Initailise
		initialiseRIM()
	}
	
	def private void initialiseRIM() {
		// Get the Entity
		val versionMapper = new IRISVersionMapper();
		
		emEntity = versionMapper.getEntity(version, application, fieldTypeChecker);
		
		// Find out EntityName
		entityName = T24ResourceModelsGenerator::TYPE_VERSION + EMUtils::camelCaseName(version.t24Name);
		thisVersionCollectionResource = entityName + "s"
		
		// Initialise EventFactory
		eventFactory = new EventFactory(loader, version);
		// Initialise CommandFactory for this RIM 
		commandFactory = new CommandFactory(rim.commands);
		// add the company basepath
		val basepath = RimFactory::eINSTANCE.createBasePath;
		basepath.setName(MappersConstants.RIM_BASE_PATH);
		rim.setBasepath(basepath);
		
		// initialise the underlying application type
		applicationType = T24Aspect::getType(mdfClass).name;
		
		// initialise the underlying application additional information
		additionalInfo = T24Aspect::getAdditionalInfo(mdfClass);
		
		// initialise if the version being processed is for application AA.ARRANGEMENT.ACTIVITY
		isAAVersion = version.t24Name.startsWith(MappersConstants.AAA_VERSION_PREFIX);
		isAAArr = IRISDomainMapper.isAAResource(mdfClass);
		
		// resource state for 'metadata' and default transitions
		metadataResource = createMetadataResource(commandFactory, entityName, isAAVersion);
		if (isAAVersion) {
			// resource state for 'metadata' only for AA to use from its New, rest should be using default
			regularMetadataResourceForAADeals = createRegularMetadataResourceForAADeals(commandFactory, entityName);
			rim.states.add(regularMetadataResourceForAADeals)
		}
		
		// Initialise Common and Custom Error handlers
		initialiseErrorHandler()
	}
	
	private def initialiseErrorHandler() {
		// find the 'Errors' resource
		if (!errorsResourceSearched){
			// Generic error resource
			errorsResource = loader.getNamedEObjectOrProxy(version, "Errors.Errors", RimPackage.Literals::STATE, true, false) as State;
			// Error resource which can process T24 Errors
			checkIfErrorResource = loader.getNamedEObjectOrProxy(version, "Errors.CheckIfError", RimPackage.Literals::STATE, true, false) as State;
			processErrorResource = loader.getNamedEObjectOrProxy(version, "Errors.ProcessErrors", RimPackage.Literals::STATE, true, false) as State;
			
			errorsResourceSearched = true; // Don't look for it anmore. Either we found it or we never will find it.
		}
		
		customErrorHandler = createCustomeErrorHandlerResource(commandFactory, entityName)
		embedErrorResource(customErrorHandler, eventFactory.createGET, "errors", null)
		customErrorHandler.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null))
		rim.states.add(customErrorHandler)
	}
	
	// Method which needs to be implemented by all extensions
	def abstract void generate()
	
	// creates the transition to context enquiry state
	def static TransitionRef createContextEnquiryTransition(ModelLoader loader, Version version, String entityName,
		EventFactory eventFactory) {
		val parameters = new HashMap<String, String>();
		parameters.put("entity", entityName);
		return createTransition(eventFactory.createGET(), "\"T24.ContextEnquiry.ContextEnquiryList\"", "Context Enquiries", parameters);
	}

	// Create the resource for 'http://temenostech.temenos.com/rels/new'
	def static State createNewResource(CommandFactory commandFactory, String entityName, boolean isAAVersion) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_new");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName));
		// odataRelation is temporary 'hack' to allow Edge to load the correct screen
		val odataRelation = RimFactory::eINSTANCE.createRelationConstant();
		odataRelation.setName(MappersConstants.MS_ODATA_RELATED + "/" + entityName);
		item.relations.add(odataRelation);
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/new");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "CreateEntity";
		if (isAAVersion) {
			commandName = "CreateAAEntity"
		}
		val newCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(newCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST",newCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s()/new");
		item.setPath(itemPath);
		return item;
	}

	// Create the resource for 'http://temenostech.temenos.com/rels/copy'
	def static State createCopyResource(CommandFactory commandFactory, String entityName, boolean isAAVersion) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_copy");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName));
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/copy");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "CopyEntity";
		if (isAAVersion) {
			commandName = "CopyAAEntity"
		}
		val command = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(command);
		itemImpl.methods.add(commandFactory.createMethod("POST", command));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s()/copy");
		item.setPath(itemPath);
		return item;
	}
	
	// Create the resource for 'http://temenostech.temenos.com/rels/deliveryPreview'
	def static State createDeliveryPreviewResource(CommandFactory commandFactory, String entityName, EventFactory eventFactory) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_deliveryPreview");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName));
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/deliveryPreview");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "CreateDEPreview";
		val command = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(command);
		itemImpl.methods.add(commandFactory.createMethod("POST", command));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();    		
    	itemPath.setName("/"+entityName + "s('{id}')/deliveryPreview");
    	item.setPath(itemPath);
    			
		val params = new HashMap<String,String>();
		params.put("filter", "ContractId eq '{id}'")
    	
		item.transitions.add(createTransitionEmbedded(eventFactory.createGET(), "T24.enqDePreview.enqDePreviews", "delivery preview enquiry", params))    	

		return item;
	}
	
	// Get the resource for 'http://temenostech.temenos.com/rels/dealSlip'
	def static State getDealSlipsResource(CommandFactory commandFactory, String entityName, EventFactory eventFactory) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_dealSlips");
		item.setType(createCollectionResourceType());
		item.setEntity(createEntity("DealSlip"));
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/dealSlip");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "GetDealSlipEntities";
		val command = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(command);
		itemImpl.methods.add(commandFactory.createMethod("GET", command));
		item.setImpl(itemImpl);
    			
		return item;
	}	
		
	
	// Create the resource for 'http://temenostech.temenos.com/rels/paste'
	def static State createPasteResource(CommandFactory commandFactory, String entityName, boolean isAAVersion) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_paste");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName));
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/paste");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "PasteEntity";
		if (isAAVersion) {
			commandName = "PasteAAEntity"
		}
		val command = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(command);
		itemImpl.methods.add(commandFactory.createMethod("POST", command));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s()/paste");
		item.setPath(itemPath);
		return item;
	}

	// Create the resource for 'http://temenostech.temenos.com/rels/input'
	def static State createInputResource(CommandFactory commandFactory, String entityName, boolean isAAVersion) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + "_input");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/input");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "InputEntity";
		if (isAAVersion) {
			commandName = "InputAAEntity";
		}
		val inputCommandForPut = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(inputCommandForPut);
		val inputCommandForPost = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());		
		itemImpl.actions.add(inputCommandForPost);
		itemImpl.methods.add(commandFactory.createMethod("PUT", inputCommandForPut));
		itemImpl.methods.add(commandFactory.createMethod("POST", inputCommandForPost));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')");
		item.setPath(itemPath);
		return item;
	}
	
	// Create the resource for 'http://temenostech.temenos.com/rels/error'
	def static State createCustomeErrorHandlerResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + "_errorHandler");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/error");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val dummyCommand = commandFactory.createResourceCommand("NoopGET", new HashMap<String,String>());
		itemImpl.actions.add(dummyCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", dummyCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/error");
		item.setPath(itemPath);
		return item;
	}
	
	// Create the resource for 'http://temenostech.temenos.com/rels/verify'
	def static State createVerifyResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + "_verify");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/verify");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "VerifyEntity"
		val verifyCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(verifyCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST", verifyCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/verify");
		item.setPath(itemPath);
		return item;
	}	
	
	// Create the resource for 'http://temenostech.temenos.com/rels/hold'
	def static State createHoldResource(CommandFactory commandFactory, String entityName, boolean isAAResource) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_hold");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/hold");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "HoldEntity"
		if (isAAResource) {
			commandName = "HoldAAEntity"
		}
		val inputCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(inputCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST", inputCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/hold");
		item.setPath(itemPath);
		return item;
	}	

	// Create the resource for 'http://temenostech.temenos.com/rels/authorise'
	def static State createAuthoriseResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_authorise");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/authorise");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val authoriseCommand = commandFactory.createResourceCommand("AuthoriseEntity", new HashMap<String,String>());
		itemImpl.actions.add(authoriseCommand);
		itemImpl.methods.add(commandFactory.createMethod("PUT",authoriseCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/authorise");
		item.setPath(itemPath);
		return item;
	}

	// Create the resource for 'http://temenostech.temenos.com/rels/changedValues'
	def static State createChangedValuesResource(CommandFactory commandFactory, String entityName, String command) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_changedValues");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/changedValues");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val changeValuesCommand = commandFactory.createResourceCommand(command, new HashMap<String,String>());
		itemImpl.actions.add(changeValuesCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", changeValuesCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/changedValues");
		item.setPath(itemPath);
		return item;
	}
	
	// Create the resource for 'http://temenostech.temenos.com/rels/restore
	def static State createRestoreResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_restore");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/restore");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val authoriseCommand = commandFactory.createResourceCommand("RestoreEntity", new HashMap<String,String>());
		itemImpl.actions.add(authoriseCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST",authoriseCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/restore");
		item.setPath(itemPath);
		return item;
	}	
	
	// Create the resource for 'http://temenostech.temenos.com/rels/review'
	def static State createAuditResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_audit");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/review");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val authoriseCommand = commandFactory.createResourceCommand("ReviewEntity", new HashMap<String,String>());
		itemImpl.actions.add(authoriseCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST",authoriseCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/review");
		item.setPath(itemPath);
		return item;
	}
	
	
	// Create the resource for 'http://temenostech.temenos.com/rels/reverse'
	def static State createReverseResource(CommandFactory commandFactory, String entityName, boolean isAAResource) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_reverse");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/reverse");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "ReverseEntity"
		if (isAAResource) {
			commandName = "ReverseAAEntity"
		}
		val reverseCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(reverseCommand);
		itemImpl.methods.add(commandFactory.createMethod("PUT", reverseCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/reverse");
		item.setPath(itemPath);
		return item;
	}	

	// Create the resource for 'http://temenostech.temenos.com/rels/delete'
	def static State createDeleteResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_delete");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/delete");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val deleteCommand = commandFactory.createResourceCommand("DeleteEntity", new HashMap<String,String>());
		itemImpl.actions.add(deleteCommand);
		itemImpl.methods.add(commandFactory.createMethod("DELETE", deleteCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/delete");
		item.setPath(itemPath);
		return item;
	}

	// Create the resource for 'http://temenostech.temenos.com/rels/validate'
	def static State createValidateResource(CommandFactory commandFactory, String entityName, boolean isAAVersion) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_validate");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/validate");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "ValidateEntity";
		if (isAAVersion) {
			commandName = "ValidateAAEntity";
		}
		val validateCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(validateCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST", validateCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/validate");
		item.setPath(itemPath);
		return item;
	}

/**
 * Create the resource for 'http://temenostech.temenos.com/rels/see'
 * t24Type can be : 
 *     null  -> live
 *    "inau" -> IAuth
 *    "hist" -> "HAuth"
 */
	def static State createSeeResource(String t24Type, CommandFactory commandFactory, String entityName, boolean isAAResource) {
	
		var type = ""
		if (t24Type != null){
			type = t24Type.toLowerCase();
		}
		var commandSuffix = "Live";
		var pathAndNameSuffix = ""; 
		if ("inau".equals(type)){
			commandSuffix = "Iauth";
			pathAndNameSuffix = "_IAuth";
		}else if ("hist".equals(type)){
			commandSuffix = "Hauth";
			pathAndNameSuffix = "_HAuth";
		}
		
		
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + pathAndNameSuffix + "_see");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		
		val relation1 = RimFactory::eINSTANCE.createRelationConstant();
		relation1.setName("http://schemas.microsoft.com/ado/2007/08/dataservices/related/"+ entityName + pathAndNameSuffix);
		item.relations.add(relation1);

		val relation2 = RimFactory::eINSTANCE.createRelationConstant();
		relation2.setName("http://temenostech.temenos.com/rels/see");
		item.relations.add(relation2);
		
		
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "Get" + commandSuffix + "Entity"
		if (isAAResource) {
			commandName = "Get" + commandSuffix + "AAEntity"
		}
		val seeCommands = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(seeCommands);
		itemImpl.methods.add(commandFactory.createMethod("GET", seeCommands));		
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s" + pathAndNameSuffix + "('{id}')/see");
		item.setPath(itemPath);
		return item;
	}

	// Create the resource for 'http://temenostech.temenos.com/rels/metadata' pointing to normal
	// command for AA version types only 
	def static State createRegularMetadataResourceForAADeals(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_metadata_regular");
		item.setType(createCollectionResourceType());
		item.setEntity(createEntity("T24FieldMetadata"))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/metadata");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "T24FieldMetadata"
		val metadataCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(metadataCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST", metadataCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s()/regularmetadata");
		item.setPath(itemPath);
		return item;
	}

	// Create the resource for 'http://temenostech.temenos.com/rels/metadata'
	def static State createMetadataResource(CommandFactory commandFactory, String entityName, boolean isAAResource) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_metadata");
		item.setType(createCollectionResourceType());
		item.setEntity(createEntity("T24FieldMetadata"))
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/metadata");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "T24FieldMetadata"
		if (isAAResource) {
			commandName = "AAFieldMetadata"
		}
		val metadataCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(metadataCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST", metadataCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s()/metadata");
		item.setPath(itemPath);
		return item;
	}
	
	// Create resource for xxxEntry (eg., verCustomer_InputEntry)
	def static State createEntryResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + "Entry");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val odataRelation = RimFactory::eINSTANCE.createRelationConstant();
		odataRelation.setName(MappersConstants.MS_ODATA_RELATED + "/" + entityName);
		item.relations.add(odataRelation);
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/contract");
		item.relations.add(relation);		
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val entryCommand = commandFactory.createResourceCommand("NoopGET", new HashMap<String, String>());
		itemImpl.setView(entryCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", entryCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/" + entityName + "Entry");
		item.setPath(itemPath);
		return item;
	}	
	
	// Create the resource for 'http://temenostech.temenos.com/rels/populate'
	def static State createPopulateResource(CommandFactory commandFactory, String entityName, boolean isAAVersion) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_populate");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val odataRelation = RimFactory::eINSTANCE.createRelationConstant();
		odataRelation.setName(MappersConstants.MS_ODATA_RELATED + "/" + entityName);
		item.relations.add(odataRelation);		
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/populate");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "CreateEntity";
		if (isAAVersion) {
			commandName = "CreateAAEntity";
		}
		val populateCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(populateCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST", populateCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s()/populate");
		item.setPath(itemPath);
		return item;
	}
	
	def static State createAAPopulateResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_aapopulate");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName));
		// odataRelation is temporary 'hack' to allow Edge to load the correct screen
		val odataRelation = RimFactory::eINSTANCE.createRelationConstant();
		odataRelation.setName(MappersConstants.MS_ODATA_RELATED + "/" + entityName);
		item.relations.add(odataRelation);
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/aapopulate");
		item.relations.add(relation);
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		var commandName = "PopulateAAEntity";
		val newCommand = commandFactory.createResourceCommand(commandName, new HashMap<String,String>());
		itemImpl.actions.add(newCommand);
		itemImpl.methods.add(commandFactory.createMethod("POST",newCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "s('{id}')/aapopulate");
		item.setPath(itemPath);
		return item;
	}
	
	// Create resource for xxx_autoId (eg., verCustomer_Input_autoId)
	def static State createAutoResource(CommandFactory commandFactory, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + "_autoId");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val checkAutoIdSupportCommand = commandFactory.createResourceCommand("CheckAutoIdSupport", new HashMap<String, String>());
		itemImpl.setView(checkAutoIdSupportCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", checkAutoIdSupportCommand));
		item.setImpl(itemImpl);
		return item;
	}	
	
	// builds the default list enquiry state name for the application (eg., T24.enqlistCustomer.enqlistCustomers for CUSTOMER)
	def static String buildDefaultListEnquiryStateName(Application application) {
		val applicationName = EMUtils::camelCaseName(application.t24Name)
		val resourceName = T24ResourceModelsGenerator::TYPE_ENQLIST + applicationName
		var enqListName = "T24." + resourceName + "." + resourceName + "s";
		return enqListName
	}
	
	// adds a target to a transition with 'OK' condition generically
	def static TransitionRef addOkCondition(TransitionRef transition, State targetResource) {
			val inputOkUnAuthExpression = RimFactory::eINSTANCE.createExpression();
			val inputOkUnAuthFunction = RimFactory::eINSTANCE.createOKFunction();
			inputOkUnAuthFunction.setState(targetResource);
			inputOkUnAuthExpression.expressions.add(inputOkUnAuthFunction);
			transition.spec.setEval(inputOkUnAuthExpression);		
			return transition;
	}
	
		/*
	 *  Create the resource returning all the contextEnquiries'
	 * 
	 * all the List & Search (Live, Unauth, Hist) + a link to the ContextEnquiries resource 
	 * 
	 */
	def static State createContextEnquiriesResource(Application application, CommandFactory commandFactory, EventFactory eventFactory, String entityName, String applicationType, boolean isAAArr) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName+"_ContextEnquiries");
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val noopGet = commandFactory.createResourceCommand("NoopGET", new HashMap<String,String>());
		itemImpl.actions.add(noopGet);
		itemImpl.methods.add(commandFactory.createMethod("GET", noopGet));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+entityName + "/ContextEnquiries");
		item.setPath(itemPath);
		
		val applicationName = EMUtils::camelCaseName(application.t24Name)
		val resourceName = T24ResourceModelsGenerator::TYPE_ENQLIST + applicationName
		var enqListName = "T24." + resourceName + "." + resourceName;
		 
		val params = new HashMap<String, String>();
		var entry = null as TransitionRef;
		
		/*
		 * Since we are not generating enqlists for AA.ARR... (see T24ResourceModeslGenerator)
		 * we should not assume these resources exists !
		 */
		if (!isAAArr){
			params.put("param", "list");
			
			entry = createTransition(eventFactory.createGET(),  enqListName + "s", "List live deals", params)
			item.transitions.add(entry);
			
			if (isUnauthFile(applicationType)){
				entry = createTransition(eventFactory.createGET(),  enqListName + "sUnauth", "List unauthorised deals", params)
				item.transitions.add(entry);
				
				if (isHistFile(applicationType)){
					entry = createTransition(eventFactory.createGET(),  enqListName + "sHist", "List history deals", params)
					item.transitions.add(entry);
				}
				
			}
			entry = createTransition(eventFactory.createGET(),  enqListName + "s", "Search live deals", null)
			item.transitions.add(entry);
	
			if (isUnauthFile(applicationType)){
				entry = createTransition(eventFactory.createGET(),  enqListName + "sUnauth", "Search unauthorised deals", null)
				item.transitions.add(entry);
				
				if (isHistFile(applicationType)){
					entry = createTransition(eventFactory.createGET(),  enqListName + "sHist", "Search history deals", null)
					item.transitions.add(entry);
				}
				
			}		
		}
		params.clear();
		params.put("entity", entityName);
		entry = createTransitionEmbedded(eventFactory.createGET(),  "ContextEnquiryList", "Other context Enquiries", params)
		item.transitions.add(entry);
		
		return item;
	}
	
	// Method to add processErrorResource as embedded
	def static void embedErrorResource(State resource, Event event, String title, Map<String, String> params) {
		if (processErrorResource != null) {
			// Embedded errors
			val embeddedErrorTransition = createTransitionEmbedded(event, processErrorResource, title, params);
			resource.transitions.add(embeddedErrorTransition)
		}
	}
	
	// Method to append processErrorResource as Embedded with conditional link to checkIfError
	def static void embedConditionalErrors(State resource, Event event, String title, Map<String, String> params) {
		if (checkIfErrorResource != null && processErrorResource != null) {
			// Embedded errors
			val embeddedErrorTransition = createTransitionEmbedded(event, processErrorResource, title, params);
			resource.transitions.add(addOkCondition(embeddedErrorTransition, checkIfErrorResource))
		}
	}
}
