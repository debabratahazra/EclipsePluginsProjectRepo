package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.generator.Application
import com.odcgroup.mdf.metamodel.MdfClass
import com.odcgroup.t24.version.versionDSL.Version
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel
import java.util.HashMap

import static com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon.*
import com.odcgroup.workbench.core.helper.FeatureSwitches

/**
 * This class is responsible for generating a RIM model for versions supports VERIFY operation in T24.
 * These type of APPLICATION do not have any NAU and HIST files hence can only be INPUT/VERIFY/DELETE
 *
 * @author sjunejo
 *
 */
class Version2ResourceMapperForVerifyOnly extends T24VersionMapper {
	
	// Constrcuctor
	new(ModelLoader _loader, MdfClass _mdfClass, Application _application, Version _version, 
		ResourceInteractionModel _rim) {
		super(_loader, _mdfClass, _application, _version, _rim)
	}
	
	
	// Implementation for Generate
	override void generate() {
		
		// one resource state for LIVE collection
		val collection = createCollectionResource(commandFactory, entityName, thisVersionCollectionResource);
		rim.states.add(collection)				
		// one resource state for item
		val item = createVersionItemResource(commandFactory, entityName, isAAVersion);
		rim.states.add(item);
		// resource state for "see"
		val seeResource = createSeeResource(null, commandFactory, entityName, isAAVersion);
		rim.states.add(seeResource);		
			
		//
		// transitions from LIVE collection
		//
		// create the transition to the entity (in odata each collection, must have a link to an item)
		collection.transitions.add(createTransitionForEach(eventFactory.createGET(), item, null, 
			buildTransitionParametersItem(emEntity)
		));	
		// create link to 'see'
		collection.transitions.add(createTransitionForEach(eventFactory.createGET(), seeResource, "see record", 
			buildTransitionParametersItem(emEntity)
		))
		
		// Create the link to all the contextEnquiries (including the list and search ones)
		val contextEnquiriesResource = createContextEnquiriesResource(application, commandFactory, eventFactory, entityName, applicationType, isAAArr);
		val skipDomainGenerationForIRIS = FeatureSwitches.skipDomainGenerationForIRIS.get();
		if(!skipDomainGenerationForIRIS) {
			rim.states.add(contextEnquiriesResource);
		}
		
		// resource state for 'metadata' and default transitions
		rim.states.add(metadataResource);
		// default embedded transition from see
		seeResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));
		// default embedded transition from item
		item.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));

		// resource state for 'new'
		val newResource = createNewResource(commandFactory, entityName, isAAVersion);
		rim.states.add(newResource);
		// resource state for 'validate'
		val validateResource = createValidateResource(commandFactory, entityName, isAAVersion);
		rim.states.add(validateResource);
		// resource state for 'input'
		val inputResource = createInputResource(commandFactory, entityName, isAAVersion);
		rim.states.add(inputResource);
		// resource state for 'verify'
		val verifyResource = createVerifyResource(commandFactory, entityName);
		rim.states.add(verifyResource);
		// resource state for 'audit'
		val auditResource = createAuditResource(commandFactory, entityName);
		rim.states.add(auditResource);
		// resource state for 'reverse'
		val reverseResource = createReverseResource(commandFactory, entityName, isAAVersion)
     	rim.states.add(reverseResource)
		// resource state for 'entry'
		val entryResource = createEntryResource(commandFactory, entityName)
		rim.states.add(entryResource);
		// resource state for 'populate'
		val populateResource = createPopulateResource(commandFactory, entityName, isAAVersion)
		rim.states.add(populateResource)
		// resource state for item 'enrichment'
		val enrichmentItemResource = createEnrichmentIemResource(commandFactory, entityName, thisVersionCollectionResource, "_enrichment")
		rim.states.add(enrichmentItemResource)
		// resource state for checking auto ID support
		val autoIdResource = createAutoResource(commandFactory, entityName)
		rim.states.add(autoIdResource)
		// resource for 'copy'
		val copyResource = createCopyResource(commandFactory, entityName, isAAVersion)
		rim.states.add(copyResource)
		// resource for 'paste'
		val pasteResource = createPasteResource(commandFactory, entityName, isAAVersion)
		rim.states.add(pasteResource)
		
		// Transitions from Collection
		// create link to 'new'
		val collectionToNewTransition = createTransition(eventFactory.createPOST(), newResource, "create new deal", null);
		collection.transitions.add(addOkCondition(collectionToNewTransition,autoIdResource));
		// create link to 'populate'
		collection.transitions.add(createTransition(eventFactory.createPOST(), populateResource, "populate existing deal", null));		
		// create link to 'input'
		collection.transitions.add(createTransitionForEach(eventFactory.createPUT(), inputResource, "input deal", 
			buildTransitionParametersItem(emEntity)
		))
		// create link to 'verify'
		collection.transitions.add(createTransitionForEach(eventFactory.createPOST(), verifyResource, "verify deal", 
			buildTransitionParametersItem(emEntity)
		))
		// create link to 'audit'
		collection.transitions.add(createTransitionForEach(eventFactory.createPOST(), auditResource, "audit deal", 
			buildTransitionParametersItem(emEntity)
		))
		// create link to 'reverse'
		collection.transitions.add(createTransitionForEach(eventFactory.createPUT(), reverseResource, "reverse deal", 
			buildTransitionParametersItem(emEntity)
		))
		//
		// transition from Item
		// 		
		// create the 'validate' transition
		item.transitions.add(createTransition(eventFactory.createPOST(), validateResource, "validate deal", 
			buildTransitionParametersItem(emEntity)
		));			
		// create the 'input' transition
		item.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
			buildTransitionParametersItem(emEntity)
		));
		// create the 'verify' transition
		item.transitions.add(createTransition(eventFactory.createPUT(), verifyResource, "verify deal", 
			buildTransitionParametersItem(emEntity)
		));
		// create the 'review' transition
		item.transitions.add(createTransition(eventFactory.createPOST(), auditResource, "audit deal", 
			buildTransitionParametersItem(emEntity)
		));
		item.transitions.add(createTransition(eventFactory.createPUT(), reverseResource, "reverse deal", 
			buildTransitionParametersItem(emEntity)
		))
		// add context enquiry transition
		item.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));	
		//
		// transitions from 'new' item
		//
		// create the 'metadata' transition
		newResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));
		// create the 'validate' transition
		newResource.transitions.add(createTransition(eventFactory.createPOST(), validateResource, "validate deal", 
			buildTransitionParametersItem(emEntity)
		));
		// create the 'input' transition
		newResource.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
			buildTransitionParametersItem(emEntity)
		));
		// create the 'verify' transition
		newResource.transitions.add(createTransition(eventFactory.createPOST(), verifyResource, "verify deal", 
			buildTransitionParametersItem(emEntity)
		));
		//
		// transitions from 'input' item
		//
		inputResource.transitions.add(createTransition(eventFactory.createPOST(), verifyResource, "verify deal", 
			buildTransitionParametersItem(emEntity)
		));	
		inputResource.transitions.add(createTransition(eventFactory.createPUT(), reverseResource, "reverse deal", 
			buildTransitionParametersItem(emEntity)
		));
		// add context enquiry transition
		inputResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));
		// set customer error handler as error state
		inputResource.errorState = customErrorHandler
		//
		// transition from 'validate' item
		//
		//conditional embedded transition from validate to Errors
		embedConditionalErrors(validateResource, eventFactory.createGET, "errors", null)
		// transition from validate to input
		validateResource.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
			buildTransitionParametersItem(emEntity)
		));
		//transition from validate to verify
		validateResource.transitions.add(createTransition(eventFactory.createPOST(), verifyResource, "verify deal", 
			buildTransitionParametersItem(emEntity)
		));
		//
		// transition from 'reverse' item
		//
		reverseResource.errorState = customErrorHandler
		//
		// transition from 'Entry' resource
		// add transitions to xxxEntry resource (e.g., verCustomer_InputEntry)		
		val entryToNewItemTransition = createTransition(eventFactory.createPOST(), newResource, "create new deal", null)		
		entryResource.transitions.add(addOkCondition(entryToNewItemTransition, autoIdResource));
	
		val entryToPopulateItemTransition = createTransition(eventFactory.createPOST(), populateResource, "populate existing deal", null)
		entryResource.transitions.add(entryToPopulateItemTransition)
		
		val params = new HashMap<String, String>();
		params.put("id", "@");
		
		/*
		 * Add relations to the /see resource
		 */
		val transitionToSee = createTransitionEmbedded(eventFactory.createGET(),  entityName + "_see", "See transaction", params)
	    entryResource.transitions.add(transitionToSee); 
		
		val transitionToContextEnquiries = createTransitionEmbedded(eventFactory.createGET(),  entityName + "_ContextEnquiries", "All context Enquiries", null)
		if(!skipDomainGenerationForIRIS) {
			entryResource.transitions.add(transitionToContextEnquiries);
		}
	    
	    // transition to copy
	    entryResource.transitions.add(createTransition(eventFactory.createPOST, copyResource, 'Copy deal', null))
	    // transition to paste
	    entryResource.transitions.add(createTransition(eventFactory.createPOST, pasteResource, 'Paste deal', null))
	    
		// add transitions to xxx_populate resource (e.g., verCustomer_Input_populate)		
		val populateToValidateTransition = createTransition(eventFactory.createPOST(), validateResource, "validate deal", 
			buildTransitionParametersItem(emEntity))
		populateResource.transitions.add(populateToValidateTransition)
	
		val populateToInputTransition = createTransition(eventFactory.createPOST(), inputResource, "input deal", buildTransitionParametersItem(emEntity))
		populateResource.transitions.add(populateToInputTransition)
		
		val populateToVerifyTransition = createTransition(eventFactory.createPOST(), verifyResource, "verify deal", buildTransitionParametersItem(emEntity))
		populateResource.transitions.add(populateToVerifyTransition)
	
		val populateToMetadataTransition = createTransition(eventFactory.createPOST(), metadataResource, "metadata", null) 
		populateResource.transitions.add(populateToMetadataTransition)
	
		populateResource.transitions.add(createTransition(eventFactory.createPUT, reverseResource, "reverse deal", buildTransitionParametersItem(emEntity)))
	
		// add context enquiry transition
		populateResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));
		
		//
		// transition from 'paste' item i.e. same as 'new'
		//
		// create the 'metadata' transition
		pasteResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));
		// create the 'validate' transition
		pasteResource.transitions.add(createTransition(eventFactory.createPOST(), validateResource, "validate deal", 
			buildTransitionParametersItem(emEntity)
		));
		// create the 'input' transition
		pasteResource.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
			buildTransitionParametersItem(emEntity)
		));
		// create the 'verify' transition
		pasteResource.transitions.add(createTransition(eventFactory.createPOST(), verifyResource, "verify deal", 
			buildTransitionParametersItem(emEntity)
		));
	}
}