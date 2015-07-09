
package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.t24.enquiry.util.EMEntity
import com.odcgroup.t24.enquiry.util.EMProperty
import com.odcgroup.t24.enquiry.util.EMTermType
import com.odcgroup.t24.enquiry.util.EMUtils
import com.temenos.interaction.rimdsl.rim.Entity
import com.temenos.interaction.rimdsl.rim.Event
import com.temenos.interaction.rimdsl.rim.Ref
import com.temenos.interaction.rimdsl.rim.ResourceType
import com.temenos.interaction.rimdsl.rim.RimFactory
import com.temenos.interaction.rimdsl.rim.State
import com.temenos.interaction.rimdsl.rim.TransitionRef
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.EList

/**
 * This class contains the methods that are common to various rim generation mappers.
 */
class ResourceMapperCommon {

	def static void addUsedDomains(EList<Ref> modelReferences) {
		val useFactory = new ImportedNamespaceFactory(modelReferences);
		useFactory.createUse("common.CoreCommands.*");
		useFactory.createUse("common.HTTPEvents.*");
		useFactory.createUse("common.ODataCommands.*");
		useFactory.createUse("common.T24Commands.*");
		useFactory.createUse("common.T24Events.*");
		useFactory.createUse("common.NextState.*");
	}
	
	def static void addUsedVersionDomains(EList<Ref> modelReferences) {
		val useFactory = new ImportedNamespaceFactory(modelReferences);
		useFactory.createUse("common.CoreCommands.*");
		useFactory.createUse("common.HTTPEvents.*");
		useFactory.createUse("common.ODataCommands.*");
		useFactory.createUse("common.T24Commands.*");
		useFactory.createUse("common.T24Events.*");
		useFactory.createUse("common.NextState.*");
		useFactory.createUse("common.Errors.*");
		
	}
	
	def static ResourceType createCollectionResourceType() {
		val resourceType = RimFactory::eINSTANCE.createResourceType
		resourceType.setIsCollection(true)
		return resourceType;
	}
	
	def static Entity createEntity(String entityName) {
		val entity = RimFactory::eINSTANCE.createEntity();
		entity.setName(entityName);
		return entity;
	}	
	
	def static TransitionRef createTransitionForEach(Event event, State target, String titleStr, Map<String,String> parameters) {
		val transition = RimFactory::eINSTANCE.createTransitionForEach();
		return buildTransition(transition, event, target, titleStr, parameters);
	}	
	
	def static TransitionRef buildTransition(TransitionRef transition, Event event, State target, String titleStr, Map<String,String> parameters) {
		transition.setEvent(event);
		transition.setState(target);
		return buildTransitionSpec(transition, titleStr, parameters);
	}
	
	def static TransitionRef buildTransitionSpec(TransitionRef transition, String titleStr, Map<String,String> parameters) {
		return buildTransitionSpec(transition, titleStr, parameters, null);
	}
	
	def static TransitionRef buildTransitionSpec(TransitionRef transition, String titleStr, Map<String,String> parameters, String linkIdStr) {
		val spec = RimFactory::eINSTANCE.createTransitionSpec;
		// add title
		if (titleStr != null) {
			val title = RimFactory::eINSTANCE.createTitle;
			val titleStrCorrected = titleStr.replace("\\", "\\\\");
			title.setName(titleStrCorrected);
			spec.setTitle(title);
		}
	
		// add linkId
		if (linkIdStr != null && linkIdStr.length > 0) {
			val linkId = RimFactory::eINSTANCE.createLinkId;
			linkId.setName(linkIdStr);
			spec.setId(linkId);
		}
		
		// add any parameters and conditions
		if (parameters != null) {
			for (String paramkey : parameters.keySet) {
				val uriParameter = RimFactory::eINSTANCE.createUriLink
				uriParameter.setTemplateProperty(paramkey)
				val entityProperty = RimFactory::eINSTANCE.createUriLinkage
				entityProperty.setName(parameters.get(paramkey))
				uriParameter.setEntityProperty(entityProperty)
				spec.uriLinks.add(uriParameter)
			}
		}

		transition.setSpec(spec)
		return transition;
	}
		
	// Create the resource for the item of Enrichment
	def static State createEnrichmentIemResource(CommandFactory commandFactory, String entityName, String resourceName, String resourceSuffix) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(resourceName + resourceSuffix);
		item.setType(createItemResourceType());
		item.setEntity(createEntity("Enrichment"))
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val properties = new HashMap<String,String>();
		properties.put("entity", entityName);
		val itemCommand = commandFactory.createResourceCommand("T24Enrichment", properties);
		itemImpl.setView(itemCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", itemCommand));
		item.setImpl(itemImpl);
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://temenostech.temenos.com/rels/enrichment");
		item.relations.add(relation);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+ resourceName + "()/enrichment");
		item.setPath(itemPath);
		return item;
	}	
	
	// Create the resource for the collection
	def static State createCollectionResource(CommandFactory commandFactory, String entityName, String resourceName, String commandName) {
		val collection = RimFactory::eINSTANCE.createState();
		collection.setName(resourceName);
		collection.setType(createCollectionResourceType());
		collection.setEntity(createEntity(entityName));
		val collectionImpl = RimFactory::eINSTANCE.createImplRef();
		val properties = new HashMap<String,String>();
		properties.put("filter", "{filter}");
		
		val collectionCommand = commandFactory.createResourceCommand(commandName, properties);
		collectionImpl.setView(collectionCommand);
		collectionImpl.methods.add(commandFactory.createMethod("GET", collectionCommand));
		
		collection.setImpl(collectionImpl);
		val collectionPath = RimFactory::eINSTANCE.createPath();
		collectionPath.setName("/"+ resourceName + "()");
		collection.setPath(collectionPath);
		return collection;
	}
	
	// Create the resource for each item in a collection for Enquiry
	def static State createEnquiryItemResource(CommandFactory commandFactory, String entityName) {
		return createItemResource(commandFactory, "GETEntity", entityName);
	}
	
	// Create the resource for each item in a collection for Domains
	def static State createDomainItemResource(CommandFactory commandFactory, String entityName) {
		return createItemResource(commandFactory, "GETEntity", entityName);
	}
	
	// Create the resource for each item in a collection for Versions
	def static State createVersionItemResource(CommandFactory commandFactory, String entityName, boolean isAAVersion) {
		var commandName = "GetLiveEntity"
		if (isAAVersion) {
			commandName = "GetLiveAAEntity"
		}
		return createItemResource(commandFactory, commandName, entityName);
	}	
	
	
	// Overloaded method to support old calls when the database is always RW.
	def static State createCollectionResource(CommandFactory commandFactory, String entityName, String resourceName) {
		return(createCollectionResource(commandFactory, entityName, resourceName, "GETEntities"));
	}	
	
	def static State createUnauthCollectionResource(CommandFactory commandFactory, String entityName, String resourceIdentifier, String command) {
		// one resource state for LIVE collection
		val unauthCollection = RimFactory::eINSTANCE.createState();
		val resourceName = entityName + "s" + resourceIdentifier;
		unauthCollection.setName(resourceName);
		unauthCollection.setType(createCollectionResourceType());
		unauthCollection.setEntity(createEntity(entityName));
		val collectionImpl = RimFactory::eINSTANCE.createImplRef();
		val properties = new HashMap<String,String>();
		properties.put("filter", "{filter}");
		val unauthCollectionCommand = commandFactory.createResourceCommand(command, properties);
		collectionImpl.setView(unauthCollectionCommand);
		collectionImpl.methods.add(commandFactory.createMethod("GET", unauthCollectionCommand));
		unauthCollection.setImpl(collectionImpl);
		val collectionPath = RimFactory::eINSTANCE.createPath();
		collectionPath.setName("/" + resourceName + "()");
		unauthCollection.setPath(collectionPath);
		return unauthCollection;
	}	
	
	// Create the resource for each item in a collection
	def static State createItemResource(CommandFactory commandFactory, String commandName, String entityName) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName);
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
		
	// Overloaded method to support old calls when the database is always RW.
	def static State createItemResource(CommandFactory commandFactory, String entityName) {
		return(createItemResource(commandFactory, "GETEntity", entityName));
	}
	
	def static State createDefaultListCollectionResource(CommandFactory commandFactory, String entityName, String resourceIdentifier, String command) {
		// one resource state for LIVE collection
		val collection = RimFactory::eINSTANCE.createState();
		val resourceName = entityName + "s" + resourceIdentifier;
		collection.setName(resourceName);
		collection.setType(createCollectionResourceType());
		collection.setEntity(createEntity(entityName + resourceIdentifier));
		val collectionImpl = RimFactory::eINSTANCE.createImplRef();
		val properties = new HashMap<String,String>();
		properties.put("filter", "{filter}");
		val collectionCommand = commandFactory.createResourceCommand(command, properties);
		collectionImpl.setView(collectionCommand);
		collectionImpl.methods.add(commandFactory.createMethod("GET", collectionCommand));
		collection.setImpl(collectionImpl);
		val collectionPath = RimFactory::eINSTANCE.createPath();
		collectionPath.setName("/" + resourceName + "()");
		collection.setPath(collectionPath);
		return collection;
	}
	
	def static State createDomainEnqListItemResource(CommandFactory commandFactory, String entityName) {
 		return createItemResource(commandFactory, "GETRODBEntity", entityName);
 	}
			
	
	// Create the resource for each unauthorised item
	def static State createUnAuthItemResource(CommandFactory commandFactory, String entityName, String resourceIdentifier, String command) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + resourceIdentifier); // eg verAccount_Create + IAuth
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName))
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val unauthItemCommand = commandFactory.createResourceCommand(command, new HashMap<String,String>());
		itemImpl.setView(unauthItemCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", unauthItemCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+ entityName + "s" + resourceIdentifier + "('{id}')");
		item.setPath(itemPath);
		return item;
	}
	
	def static State createNextStateItemResource(CommandFactory commandFactory) {
			val nextStateItem = RimFactory::eINSTANCE.createState();
			nextStateItem.setName("nextState");
			nextStateItem.setType(createItemResourceType());
			nextStateItem.setEntity(createEntity("NextState"))
			val nextStateItemImpl = RimFactory::eINSTANCE.createImplRef();
			val nextStateCommand = commandFactory.createResourceCommand("T24NextState", new HashMap<String,String>());
			nextStateItemImpl.setView(nextStateCommand);
			nextStateItemImpl.methods.add(commandFactory.createMethod("GET", nextStateCommand));
			nextStateItem.setImpl(nextStateItemImpl);
			val nextStateItemPath = RimFactory::eINSTANCE.createPath();
			nextStateItemPath.setName("/verCustomer_Inputs/next");
			nextStateItem.setPath(nextStateItemPath);
			
			return nextStateItem;		
	}	
	
	// Create the resource for each unauthorised item
	def static State createDefaultListItemResource(CommandFactory commandFactory, String entityName, String resourceIdentifier, String command) {
		val item = RimFactory::eINSTANCE.createState();
		item.setName(entityName + resourceIdentifier); // eg verAccount_Create + IAuth
		item.setType(createItemResourceType());
		item.setEntity(createEntity(entityName + resourceIdentifier))
		val itemImpl = RimFactory::eINSTANCE.createImplRef();
		val itemCommand = commandFactory.createResourceCommand(command, new HashMap<String,String>());
		itemImpl.setView(itemCommand);
		itemImpl.methods.add(commandFactory.createMethod("GET", itemCommand));
		item.setImpl(itemImpl);
		val itemPath = RimFactory::eINSTANCE.createPath();
		itemPath.setName("/"+ entityName + "s" + resourceIdentifier + "('{id}')");
		item.setPath(itemPath);
		return item;
	}	
	
	def static ResourceType createItemResourceType() {
		val resourceType = RimFactory::eINSTANCE.createResourceType();
		resourceType.setIsItem(true);
		return resourceType;
	}	
	
	def static Map<String,String> buildTransitionParametersItem(EMEntity emEntity) {
		// create the transition to the entity (in odata each collection, must have a link to an item)
		val parameters = new HashMap<String,String>();
		for (EMProperty property : emEntity.properties) {
			if (property.hasVocabularyTerm(EMTermType::ID_TERM)) {
				parameters.put("id", "{"+EMUtils::camelCaseName(property.name)+"}");
			}
		}
		return parameters;
	}
	
	def static TransitionRef createTransitionForEach(Event event, String targetStr, String titleStr, Map<String,String> parameters) {
		return createTransitionForEach(event, targetStr, titleStr, parameters, null);
	}

	def static TransitionRef createTransitionForEach(Event event, String targetStr, String titleStr, Map<String,String> parameters, String linkIdStr) {
		val transition = RimFactory::eINSTANCE.createTransitionForEach();
		return buildTransition(transition, event, targetStr, titleStr, parameters, linkIdStr);
	}

	def static TransitionRef createTransitionEmbedded(Event event, State target, String titleStr, Map<String,String> parameters) {
		val transition = RimFactory::eINSTANCE.createTransitionEmbedded();
		return buildTransition(transition, event, target, titleStr, parameters);
	}

	def static TransitionRef createTransitionEmbedded(Event event, String targetStr, String titleStr, Map<String,String> parameters) {
		val transition = RimFactory::eINSTANCE.createTransitionEmbedded();
		return buildTransition(transition, event, targetStr, titleStr, parameters);
	}

	def static TransitionRef createTransitionAuto(Event event, State target, String titleStr, Map<String,String> parameters) {
		val transition = RimFactory::eINSTANCE.createTransitionAuto();
		return buildTransition(transition, event, target, titleStr, parameters);
	}

	def static TransitionRef createTransitionAuto(Event event, String targetStr, String titleStr, Map<String,String> parameters) {
		val transition = RimFactory::eINSTANCE.createTransitionAuto();
		return buildTransition(transition, event, targetStr, titleStr, parameters);
	}

	def static TransitionRef createTransition(Event event, State target, String titleStr, Map<String,String> parameters) {
		val transition = RimFactory::eINSTANCE.createTransition();
		return buildTransition(transition, event, target, titleStr, parameters);
	}

	def static TransitionRef createTransition(Event event, String targetStr, String titleStr, Map<String,String> parameters) {
		val transition = RimFactory::eINSTANCE.createTransition();
		return buildTransition(transition, event, targetStr, titleStr, parameters);
	}

	def static TransitionRef buildTransition(TransitionRef transition, Event event, String targetStr, String titleStr, Map<String,String> parameters) {
		return buildTransition(transition, event, targetStr, titleStr, parameters, null);
	}	
	
	def static TransitionRef buildTransition(TransitionRef transition, Event event, String targetStr, String titleStr, Map<String,String> parameters, String linkIdStr) {
		transition.setEvent(event);
		transition.setName(targetStr);
		return buildTransitionSpec(transition, titleStr, parameters, linkIdStr);
	}	

	// Hist file if the application type is 'H'	
	def static isHistFile(String applicationType) {
		return MappersConstants.APPLICATION_TYPE_HIST.equals(applicationType);
	}
	
	// Unauth file if the application type is either 'U', 'H' or 'D'
	def static isUnauthFile(String applicationType) {
		if (MappersConstants.APPLICATION_TYPE_UNAUTH.equals(applicationType) || 
			MappersConstants.APPLICATION_TYPE_HIST.equals(applicationType) ||
			MappersConstants.APPLICATION_TYPE_DYNAMIC.equals(applicationType)
		) {
			return true;
		}
		return false;
	}
	
	// Verifiable if applicationType is 'W'
	def static isVerifiable(String applicationType) {
		if (MappersConstants.APPLICATION_TYPE_VERIFY.equals(applicationType)) {
			return true;
		}
		return false;
	}
	
	// Only Live file if applicationType is 'L'
	def static isOnlyLiveFile(String applicationType) {
		if (MappersConstants.APPLICATION_TYPE_LIVE.equals(applicationType)) {
			return true;
		}
		return false;
	}
}
