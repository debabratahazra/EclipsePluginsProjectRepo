package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.generator.Application
import com.odcgroup.mdf.metamodel.MdfClass
import com.odcgroup.t24.version.versionDSL.Version
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel
import com.temenos.interaction.rimdsl.rim.State
import java.util.HashMap

import static com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon.*
import com.odcgroup.workbench.core.helper.FeatureSwitches
import org.eclipse.emf.common.util.EList
import com.odcgroup.t24.version.versionDSL.DealSlip

/**
 * TODO: Document me!
 *
 * @author sjunejo
 *
 */
class Version2ResourceMapperForLiveNauHist extends T24VersionMapper {
	
	// Constructor
	new(ModelLoader _loader, MdfClass _mdfClass, Application _application, Version _version, 
		ResourceInteractionModel _rim) {
		super(_loader, _mdfClass, _application, _version, _rim)
	}
	
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
		
		if (isUnauthFile(applicationType)){
			val seeIAuthResource = createSeeResource("inau", commandFactory, entityName, isAAVersion);
			rim.states.add(seeIAuthResource);	
			
			if (isHistFile(applicationType)) {
			// resource state for "see History"
			   val seeHistResource = createSeeResource("hist", commandFactory, entityName, isAAVersion);
			   rim.states.add(seeHistResource);
			}
		}		
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
		
		if (isUnauthFile(applicationType)) {
			// collection resource for unauth
			val unauthCollectionResource = createUnauthCollectionResource(commandFactory, entityName, "_IAuth", "GetIauthEntities");
			rim.states.add(unauthCollectionResource);
			// resource state for 'new'
			val newResource = createNewResource(commandFactory, entityName, isAAVersion);
			rim.states.add(newResource);
			// resource state for 'input'
			val inputResource = createInputResource(commandFactory, entityName, isAAVersion);
			rim.states.add(inputResource);
			// resource state for 'hold'
			val holdResource = createHoldResource(commandFactory, entityName, isAAVersion);
			rim.states.add(holdResource);			
			// resource state for 'authorise'
			val authoriseResource = createAuthoriseResource(commandFactory, entityName);
			rim.states.add(authoriseResource);

			// resource state for 'changedValues'
			var changedValuesCommandName = "GetChangedValues"

			if(isAAVersion) {
				changedValuesCommandName = "GetAAChangedValues"
			}

			val changedValuesResource =  createChangedValuesResource(commandFactory, entityName, changedValuesCommandName);
			rim.states.add(changedValuesResource);

			// resource state for 'audit'
			val auditResource = createAuditResource(commandFactory, entityName);
			rim.states.add(auditResource);			
			// resource state for 'reverse'
			var State reverseResource;
	    	reverseResource = createReverseResource(commandFactory, entityName, isAAVersion)
     	   	rim.states.add(reverseResource)
			// resource state for 'validate'
			val validateResource = createValidateResource(commandFactory, entityName, isAAVersion);
			rim.states.add(validateResource);
			// one resource state for unauthorised item
			var unauthCommandName = "GetIauthEntity"
			if (isAAVersion) {
				unauthCommandName = "GetIauthAAEntity"
			} 
			val unAuthItem = createUnAuthItemResource(commandFactory, entityName, "_IAuth", unauthCommandName);
			rim.states.add(unAuthItem);
			val restoreResource = createRestoreResource(commandFactory, entityName);						
			// resource state for 'delete'
			val deleteResource = createDeleteResource(commandFactory, entityName);
			rim.states.add(deleteResource);
			// resource state for 'entry'
			val entryResource = createEntryResource(commandFactory, entityName)
			rim.states.add(entryResource);
			// resource state for 'populate'
			val populateResource = createPopulateResource(commandFactory, entityName, isAAVersion)
			rim.states.add(populateResource)
			// resource for 'copy'
			val copyResource = createCopyResource(commandFactory, entityName, isAAVersion)
			rim.states.add(copyResource)
			// resource for 'paste'
			val pasteResource = createPasteResource(commandFactory, entityName, isAAVersion)
			rim.states.add(pasteResource)
			
			// resource for create delivery preview
			val createDePreviewResource = createDeliveryPreviewResource(commandFactory, entityName, eventFactory)
			
			if(additionalInfo != null && additionalInfo.contains("PREVIEW")) {
				// Only include delivery preview resource if applicable			
				rim.states.add(createDePreviewResource)	
			}
			
			// resource for get deal slips
			val dealSlipsResource = getDealSlipsResource(commandFactory, entityName, eventFactory)
			
			val EList<DealSlip> dealSlipFormats = version.dealSlipFormats
			
			var includeDealSlip = false;
						
			if(dealSlipFormats != null && !dealSlipFormats.empty) {
				// Only include deal slips resource if the version has deal slip config
				rim.states.add(dealSlipsResource);
				includeDealSlip = true;
			}			
		
			// resource state for item 'enrichment'
			val enrichmentItemResource = createEnrichmentIemResource(commandFactory, entityName, thisVersionCollectionResource, "_enrichment")
			rim.states.add(enrichmentItemResource)
			
			// resource state for checking auto ID support
			val autoIdResource = createAutoResource(commandFactory, entityName)
			rim.states.add(autoIdResource)		
						
			var State histItem = null 
			if (isHistFile(applicationType)) {
				// resource state for 'restore'
				rim.states.add(restoreResource);							
				// collection resource for hist
				val histCollectionResource = createUnauthCollectionResource(commandFactory, entityName, "_HAuth", "GetHauthEntities");
				rim.states.add(histCollectionResource);						
				// one resource state for hist item
				var commandName = "GetHauthEntity";
				if (isAAVersion) {
					commandName = "GetHauthAAEntity";
				}
				histItem = createUnAuthItemResource(commandFactory, entityName, "_HAuth", commandName);
				rim.states.add(histItem);
				// transitions to hist collection
				// create the transition to the entity (in odata each collection, must have a link to an item)
				histCollectionResource.transitions.add(createTransitionForEach(eventFactory.createGET(), histItem, null, 
				buildTransitionParametersItem(emEntity)
				));	
				// create link to 'new'
				val histCollectionToNewTransition = createTransition(eventFactory.createPOST(), newResource, "create new deal", null);
				histCollectionResource.transitions.add(addOkCondition(histCollectionToNewTransition, autoIdResource));
				// create link to 'populate'
				histCollectionResource.transitions.add(createTransition(eventFactory.createPOST(), populateResource, "populate existing deal", null));		
				// crate for each link to 'restore'
				histCollectionResource.transitions.add(createTransitionForEach(eventFactory.createPOST(), restoreResource, "restore deal", 
				buildTransitionParametersItem(emEntity)
				));				
				// transitions to hist item
				// embedded metadata
				histItem.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));
				// hist to validate
				histItem.transitions.add(createTransition(eventFactory.createPOST(), validateResource, "validate deal", buildTransitionParametersItem(emEntity)));
				// hist to input
				histItem.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", buildTransitionParametersItem(emEntity)));
				// create the 'hold' transition
				histItem.transitions.add(createTransition(eventFactory.createPOST(), holdResource, "hold deal", buildTransitionParametersItem(emEntity)));						
				// hist to authorise
				histItem.transitions.add(createTransition(eventFactory.createPOST(), authoriseResource, "authorise deal", buildTransitionParametersItem(emEntity)));
				// hist to delete
				histItem.transitions.add(createTransition(eventFactory.createDELETE(), deleteResource, "delete", buildTransitionParametersItem(emEntity)));
				// context enquiry list
				histItem.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));
			}			
			// create link to 'new'
			val collectionToNewTransition = createTransition(eventFactory.createPOST(), newResource, "create new deal", null);
			collection.transitions.add(addOkCondition(collectionToNewTransition,autoIdResource));
			// create link to 'populate'
			collection.transitions.add(createTransition(eventFactory.createPOST(), populateResource, "populate existing deal", null));		
			// create link to 'input'
			collection.transitions.add(createTransitionForEach(eventFactory.createPUT(), inputResource, "input deal", 
				buildTransitionParametersItem(emEntity)
			))
			// create link to 'audit'
			collection.transitions.add(createTransitionForEach(eventFactory.createPOST(), auditResource, "audit deal", 
				buildTransitionParametersItem(emEntity)
			))			
			// create link to 'reverse' (if application type is H)
			if (isHistFile(applicationType)) {
		    	collection.transitions.add(createTransitionForEach(eventFactory.createPOST(), reverseResource, "reverse deal", 
			    buildTransitionParametersItem(emEntity)
		   	))
			}
			// transitions from unauth collection
			// create the transition to the entity (in odata each collection, must have a link to an item)
			unauthCollectionResource.transitions.add(createTransitionForEach(eventFactory.createGET(), unAuthItem, null, 
				buildTransitionParametersItem(emEntity)
			));
			// create link to 'new'
			val unauthCollectionToNewTransition = createTransition(eventFactory.createPOST(), newResource, "create new deal", null);		
			unauthCollectionResource.transitions.add(addOkCondition(unauthCollectionToNewTransition,autoIdResource));
			// create link to 'populate'
			unauthCollectionResource.transitions.add(createTransition(eventFactory.createPOST(), populateResource, "populate existing deal", null));		
			// create link to 'input'
			unauthCollectionResource.transitions.add(createTransitionForEach(eventFactory.createPUT(), inputResource, "input deal", 
				buildTransitionParametersItem(emEntity)
			))
			// create link to 'delete'
			unauthCollectionResource.transitions.add(createTransitionForEach(eventFactory.createDELETE(), deleteResource, "delete", 
				buildTransitionParametersItem(emEntity)
			))
			// create link to 'authorise'
			unauthCollectionResource.transitions.add(createTransitionForEach(eventFactory.createPUT(), authoriseResource, "authorise deal", 
				buildTransitionParametersItem(emEntity)
			))		
		
			//
			// transitions from item
			//
			
			// if there is an unauthorised record auto transition to it
			val unAuthItemTransition = createTransitionAuto(eventFactory.createGET(), unAuthItem, null, 
				buildTransitionParametersItem(emEntity)
			);	
			item.transitions.add(addOkCondition(unAuthItemTransition,unAuthItem));
			
			// create the 'validate' transition
			item.transitions.add(createTransition(eventFactory.createPOST(), validateResource, "validate deal", 
				buildTransitionParametersItem(emEntity)
			));			
			// create the 'input' transition
			item.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
				buildTransitionParametersItem(emEntity)
			));
			// create the 'review' transition
			item.transitions.add(createTransition(eventFactory.createPOST(), auditResource, "audit deal", 
				buildTransitionParametersItem(emEntity)
			));
			// create the 'reverse' transition
			item.transitions.add(createTransition(eventFactory.createPOST(), reverseResource, "reverse deal", 
				buildTransitionParametersItem(emEntity)
			));					
			
			// Add transition to delivery preview if additionalInfo in the domain contains "PREVIEW"			
			if(additionalInfo != null && additionalInfo.contains("PREVIEW")) {								
				item.transitions.add(createTransition(eventFactory.createPOST(), createDePreviewResource, "delivery preview",
					buildTransitionParametersItem(emEntity)))
			}
			
			if(includeDealSlip) {
			// Only include deal slips resource if the associated application is TELLER
				item.transitions.add(createTransitionEmbedded(eventFactory.createGET(), dealSlipsResource, "deal slips",
					buildTransitionParametersItem(emEntity)))								
			}			
							
			// add context enquiry transition
			item.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory)); 	
			
			//
			// transitions from unauthorised item
			//
			// metadata link		
			unAuthItem.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));
			// create the 'changedValues' transition
			unAuthItem.transitions.add(createTransitionEmbedded(eventFactory.createGET(), changedValuesResource, "changed values",
				buildTransitionParametersItem(emEntity)
			));
			// create the 'validate' transition
			unAuthItem.transitions.add(createTransition(eventFactory.createPOST(), validateResource, "validate deal", 
				buildTransitionParametersItem(emEntity)
			));			
			// create the 'input' transition
			unAuthItem.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
				buildTransitionParametersItem(emEntity)
			));		
			// create the 'hold' transition
			unAuthItem.transitions.add(createTransition(eventFactory.createPOST(), holdResource, "hold deal", 
				buildTransitionParametersItem(emEntity)
			));					
			// create the 'authorise' transition
			unAuthItem.transitions.add(createTransition(eventFactory.createPOST(), authoriseResource, "authorise deal", 
				buildTransitionParametersItem(emEntity)
			));
			// create the 'delete' transition
			unAuthItem.transitions.add(createTransition(eventFactory.createDELETE(), deleteResource, "delete", 
				buildTransitionParametersItem(emEntity)
			));
			// add context enquiry transition
			unAuthItem.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));
			//
			// transitions from 'new' item
			//
			var State aapopulateResource = null
			// For AA Resource
			if (isAAVersion) {
				// create the 'metadata' transition
				newResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), regularMetadataResourceForAADeals, "metadata", null));
				// resource state for AA Populate
				aapopulateResource = createAAPopulateResource(commandFactory, entityName);
				rim.states.add(aapopulateResource);
				newResource.transitions.add(createTransition(eventFactory.createPOST(), aapopulateResource, "populate deal", 
					buildTransitionParametersItem(emEntity)
				));
			} else {
				// For non-AA Resources
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
				// create the 'hold' transition
				newResource.transitions.add(createTransition(eventFactory.createPOST(), holdResource, "hold deal", 
					buildTransitionParametersItem(emEntity)
				));
			} 
			
			// Add transition to delivery preview if additionalInfo in the domain contains "PREVIEW"			
			if(additionalInfo != null && additionalInfo.contains("PREVIEW")) {								
				newResource.transitions.add(createTransition(eventFactory.createPOST(), createDePreviewResource, "delivery preview",
					buildTransitionParametersItem(emEntity)))
			}
			
			// add context enquiry transition
			newResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));
		
			//
			// create link for AA Populate only for AA Version
			//
			if(isAAVersion && aapopulateResource != null) {
				// embed the metadata
				aapopulateResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", 
					buildTransitionParametersItem(emEntity)
				));
				// create the 'validate' transition
				aapopulateResource.transitions.add(createTransition(eventFactory.createPOST(), validateResource, "validate deal", 
					buildTransitionParametersItem(emEntity)
				));
				// create the 'input' transition
				aapopulateResource.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
					buildTransitionParametersItem(emEntity)
				));
				// create the 'hold' transition
				aapopulateResource.transitions.add(createTransition(eventFactory.createPOST(), holdResource, "hold deal", 
					buildTransitionParametersItem(emEntity)
				));
				// add context enquiry transition
				aapopulateResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));
			}
			
			//
			// transitions from 'input' item
			//
			// As we will have the custom error handler for this resource we should be able to
			// 'input' to next state transition
			val nextStateItem = createNextStateItemResource(commandFactory);
			val inputToNextStateAutoTransition = createTransitionAuto(eventFactory.createGET(), nextStateItem, null, null);
			inputResource.transitions.add(addOkCondition(inputToNextStateAutoTransition, nextStateItem));
			//did 'input' create a unauthorised contract?
			val inputToUnAuthAutoTransition = createTransitionAuto(eventFactory.createGET(), unAuthItem, null, 
				buildTransitionParametersItem(emEntity)
			);
			inputResource.transitions.add(addOkCondition(inputToUnAuthAutoTransition, unAuthItem));			
			// did 'input' create a LIVE contract?			
			val inputToLiveTransition = createTransitionAuto(eventFactory.createGET(), item, null, 
				buildTransitionParametersItem(emEntity)
			);
			inputResource.transitions.add(addOkCondition(inputToLiveTransition, item));	
			// add context enquiry transition
			inputResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));
			// add the 'onerror' transition to custom error handler
			inputResource.errorState = customErrorHandler
			// transition from hold to input
			holdResource.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
				buildTransitionParametersItem(emEntity)));
			// transition from hold to delete			
			holdResource.transitions.add(createTransition(eventFactory.createDELETE(), deleteResource, "delete", 
				buildTransitionParametersItem(emEntity)));
			//conditional embedded transition from validate to Errors
			embedConditionalErrors(validateResource, eventFactory.createGET, "errors", null)
			//transition from validate to metadata
			validateResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));			
			// transition from validate to input
			validateResource.transitions.add(createTransition(eventFactory.createPUT(), inputResource, "input deal", 
				buildTransitionParametersItem(emEntity)
			));
			//transition from validate to hold
			validateResource.transitions.add(createTransition(eventFactory.createPOST(), holdResource, "hold deal", 
				buildTransitionParametersItem(emEntity)
			));			
			// transitions from 'authorise' item		
			val authToUnAuthAutoTransition = createTransitionAuto(eventFactory.createGET(), unAuthItem, null, 
				buildTransitionParametersItem(emEntity)
			);			
			authoriseResource.transitions.add(addOkCondition(authToUnAuthAutoTransition,unAuthItem))			
			val authToLiveAuthAutoTransition = createTransitionAuto(eventFactory.createGET(), item, null, 
				buildTransitionParametersItem(emEntity))
			authoriseResource.transitions.add(addOkCondition(authToLiveAuthAutoTransition, item));
			// transitions from 'audit' resource
			// create the 'input' transition
			auditResource.transitions.add(createTransition(eventFactory.createGET(), item, null, 
				buildTransitionParametersItem(emEntity)
			));
			// transitions from 'reverse' item
			val reverseToUnauthTransition = createTransitionAuto(eventFactory.createGET(), unAuthItem, null, buildTransitionParametersItem(emEntity));
			reverseResource.transitions.add(addOkCondition(reverseToUnauthTransition,unAuthItem));
			if (isHistFile(applicationType)) {
				// transition from 'reverse' to hist
				val reverseToHistTransition = createTransitionAuto(eventFactory.createGET(), histItem, null, buildTransitionParametersItem(emEntity));
				reverseResource.transitions.add(addOkCondition(reverseToHistTransition, histItem));
				// restore state transitions
				//transition to unauth item
				val restoreToUnauthTransition = createTransitionAuto(eventFactory.createGET(), unAuthItem, null, buildTransitionParametersItem(emEntity));
				restoreResource.transitions.add(addOkCondition(restoreToUnauthTransition,unAuthItem));
				//transition to unauth item
				val restoreToLiveTransition = createTransitionAuto(eventFactory.createGET(), item, null, buildTransitionParametersItem(emEntity));
				restoreResource.transitions.add(addOkCondition(restoreToLiveTransition,item));				
				// metadata
				restoreResource.transitions.add(createTransition(eventFactory.createPOST(), metadataResource, "metadata", null));		
				// add context enquiry transition
				restoreResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));			
			}
			// add the 'onerror' transition to custom error handler
			reverseResource.errorState = customErrorHandler
			// add context enquiry transition
			authoriseResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));			
			// add the 'onerror' transition to custom error handler
			authoriseResource.errorState = customErrorHandler
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
			if (isUnauthFile(applicationType)) { // Only if this is an application having Unauth records.
				val transitionToIAuth = createTransitionEmbedded(eventFactory.createGET(),  entityName + "_IAuth_see", "GetIauthEntity", params)
			    entryResource.transitions.add(transitionToIAuth);  
			    
	            if (isHistFile(applicationType)) { // No need of this link if no History
				    val transitionToHAuth = createTransitionEmbedded(eventFactory.createGET(),  entityName + "_HAuth_see", "GetHauthEntity", params)
				    entryResource.transitions.add(transitionToHAuth);  
			    }
			}

			/*
			 * Add the same relations, but to the non-see resource.
			 */
			if (isUnauthFile(applicationType)) { // Only if this is an application having Unauth records.
				val transitionToIAuth = createTransitionEmbedded(eventFactory.createGET(),  entityName + "_IAuth", "GetIauthEntity", params)
			    entryResource.transitions.add(transitionToIAuth);  
			    
	            if (isHistFile(applicationType)) { // No need of this link if no History
				    val transitionToHAuth = createTransitionEmbedded(eventFactory.createGET(),  entityName + "_HAuth", "GetHauthEntity", params)
				    entryResource.transitions.add(transitionToHAuth);  
			    }
			}

			
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
		
			val populateToDeleteTransition = createTransition(eventFactory.createDELETE(), deleteResource, "delete", buildTransitionParametersItem(emEntity))		
			populateResource.transitions.add(addOkCondition(populateToDeleteTransition, unAuthItem));		
		
			val populateToMetadataTransition = createTransition(eventFactory.createPOST(), metadataResource, "metadata", null) 
			populateResource.transitions.add(populateToMetadataTransition)
		
			// add context enquiry transition
			populateResource.transitions.add(createContextEnquiryTransition(loader, version, entityName, eventFactory));

			if (errorsResource != null) {
				holdResource.errorState = errorsResource;
				auditResource.errorState = errorsResource;
				restoreResource.errorState = errorsResource;
			}
			
			//
			// transition from 'paste' item i.e. same as 'new'
			//
			// For AA Resource
			if (isAAVersion) {
				// create the 'metadata' transition
				pasteResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), regularMetadataResourceForAADeals, "metadata", null));
				// create the 'aapopulate' transition
				pasteResource.transitions.add(createTransition(eventFactory.createPOST(), aapopulateResource, "populate deal", 
					buildTransitionParametersItem(emEntity)
				));
			} else {
				// For non-AA resources
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
				// create the 'hold' transition
				pasteResource.transitions.add(createTransition(eventFactory.createPOST(), holdResource, "hold deal", 
					buildTransitionParametersItem(emEntity)
				));
			}
		}
	}
}