package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.generator.Application
import com.odcgroup.mdf.metamodel.MdfClass
import com.odcgroup.t24.version.versionDSL.Version
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel

import static com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon.*

/**
 * This class can be used to generate RIM for LIVE version only
 *
 * @author sjunejo
 *
 */
 
class Version2ResourceMapperForLiveOnly extends T24VersionMapper {
	
	// Constrcuctor
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
		rim.states.add(contextEnquiriesResource);
		
		// resource state for 'metadata' and default transitions
		rim.states.add(metadataResource);
		// default embedded transition from see
		seeResource.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));
		// default embedded transition from item
		item.transitions.add(createTransitionEmbedded(eventFactory.createPOST(), metadataResource, "metadata", null));
	}
	
}