package com.odcgroup.iris.rim.generation.mappers;

import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.iris.rim.generation.mappers.T24VersionMapper;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.EList;

/**
 * This class is responsible for generating a RIM model for versions supports VERIFY operation in T24.
 * These type of APPLICATION do not have any NAU and HIST files hence can only be INPUT/VERIFY/DELETE
 * 
 * @author sjunejo
 */
@SuppressWarnings("all")
public class Version2ResourceMapperForVerifyOnly extends T24VersionMapper {
  public Version2ResourceMapperForVerifyOnly(final ModelLoader _loader, final MdfClass _mdfClass, final Application _application, final Version _version, final ResourceInteractionModel _rim) {
    super(_loader, _mdfClass, _application, _version, _rim);
  }
  
  public void generate() {
    final State collection = ResourceMapperCommon.createCollectionResource(this.commandFactory, this.entityName, this.thisVersionCollectionResource);
    EList<State> _states = this.rim.getStates();
    _states.add(collection);
    final State item = ResourceMapperCommon.createVersionItemResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_1 = this.rim.getStates();
    _states_1.add(item);
    final State seeResource = T24VersionMapper.createSeeResource(null, this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_2 = this.rim.getStates();
    _states_2.add(seeResource);
    EList<TransitionRef> _transitions = collection.getTransitions();
    Event _createGET = this.eventFactory.createGET();
    Map<String,String> _buildTransitionParametersItem = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransitionForEach = ResourceMapperCommon.createTransitionForEach(_createGET, item, null, _buildTransitionParametersItem);
    _transitions.add(_createTransitionForEach);
    EList<TransitionRef> _transitions_1 = collection.getTransitions();
    Event _createGET_1 = this.eventFactory.createGET();
    Map<String,String> _buildTransitionParametersItem_1 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransitionForEach_1 = ResourceMapperCommon.createTransitionForEach(_createGET_1, seeResource, "see record", _buildTransitionParametersItem_1);
    _transitions_1.add(_createTransitionForEach_1);
    final State contextEnquiriesResource = T24VersionMapper.createContextEnquiriesResource(this.application, this.commandFactory, this.eventFactory, this.entityName, this.applicationType, this.isAAArr);
    final Boolean skipDomainGenerationForIRIS = FeatureSwitches.skipDomainGenerationForIRIS.get();
    if ((!(skipDomainGenerationForIRIS).booleanValue())) {
      EList<State> _states_3 = this.rim.getStates();
      _states_3.add(contextEnquiriesResource);
    }
    EList<State> _states_4 = this.rim.getStates();
    _states_4.add(T24VersionMapper.metadataResource);
    EList<TransitionRef> _transitions_2 = seeResource.getTransitions();
    Event _createPOST = this.eventFactory.createPOST();
    TransitionRef _createTransitionEmbedded = ResourceMapperCommon.createTransitionEmbedded(_createPOST, T24VersionMapper.metadataResource, "metadata", null);
    _transitions_2.add(_createTransitionEmbedded);
    EList<TransitionRef> _transitions_3 = item.getTransitions();
    Event _createPOST_1 = this.eventFactory.createPOST();
    TransitionRef _createTransitionEmbedded_1 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_1, T24VersionMapper.metadataResource, "metadata", null);
    _transitions_3.add(_createTransitionEmbedded_1);
    final State newResource = T24VersionMapper.createNewResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_5 = this.rim.getStates();
    _states_5.add(newResource);
    final State validateResource = T24VersionMapper.createValidateResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_6 = this.rim.getStates();
    _states_6.add(validateResource);
    final State inputResource = T24VersionMapper.createInputResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_7 = this.rim.getStates();
    _states_7.add(inputResource);
    final State verifyResource = T24VersionMapper.createVerifyResource(this.commandFactory, this.entityName);
    EList<State> _states_8 = this.rim.getStates();
    _states_8.add(verifyResource);
    final State auditResource = T24VersionMapper.createAuditResource(this.commandFactory, this.entityName);
    EList<State> _states_9 = this.rim.getStates();
    _states_9.add(auditResource);
    final State reverseResource = T24VersionMapper.createReverseResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_10 = this.rim.getStates();
    _states_10.add(reverseResource);
    final State entryResource = T24VersionMapper.createEntryResource(this.commandFactory, this.entityName);
    EList<State> _states_11 = this.rim.getStates();
    _states_11.add(entryResource);
    final State populateResource = T24VersionMapper.createPopulateResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_12 = this.rim.getStates();
    _states_12.add(populateResource);
    final State enrichmentItemResource = ResourceMapperCommon.createEnrichmentIemResource(this.commandFactory, this.entityName, this.thisVersionCollectionResource, "_enrichment");
    EList<State> _states_13 = this.rim.getStates();
    _states_13.add(enrichmentItemResource);
    final State autoIdResource = T24VersionMapper.createAutoResource(this.commandFactory, this.entityName);
    EList<State> _states_14 = this.rim.getStates();
    _states_14.add(autoIdResource);
    final State copyResource = T24VersionMapper.createCopyResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_15 = this.rim.getStates();
    _states_15.add(copyResource);
    final State pasteResource = T24VersionMapper.createPasteResource(this.commandFactory, this.entityName, this.isAAVersion);
    EList<State> _states_16 = this.rim.getStates();
    _states_16.add(pasteResource);
    Event _createPOST_2 = this.eventFactory.createPOST();
    final TransitionRef collectionToNewTransition = ResourceMapperCommon.createTransition(_createPOST_2, newResource, "create new deal", null);
    EList<TransitionRef> _transitions_4 = collection.getTransitions();
    TransitionRef _addOkCondition = T24VersionMapper.addOkCondition(collectionToNewTransition, autoIdResource);
    _transitions_4.add(_addOkCondition);
    EList<TransitionRef> _transitions_5 = collection.getTransitions();
    Event _createPOST_3 = this.eventFactory.createPOST();
    TransitionRef _createTransition = ResourceMapperCommon.createTransition(_createPOST_3, populateResource, "populate existing deal", null);
    _transitions_5.add(_createTransition);
    EList<TransitionRef> _transitions_6 = collection.getTransitions();
    Event _createPUT = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_2 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransitionForEach_2 = ResourceMapperCommon.createTransitionForEach(_createPUT, inputResource, "input deal", _buildTransitionParametersItem_2);
    _transitions_6.add(_createTransitionForEach_2);
    EList<TransitionRef> _transitions_7 = collection.getTransitions();
    Event _createPOST_4 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_3 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransitionForEach_3 = ResourceMapperCommon.createTransitionForEach(_createPOST_4, verifyResource, "verify deal", _buildTransitionParametersItem_3);
    _transitions_7.add(_createTransitionForEach_3);
    EList<TransitionRef> _transitions_8 = collection.getTransitions();
    Event _createPOST_5 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_4 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransitionForEach_4 = ResourceMapperCommon.createTransitionForEach(_createPOST_5, auditResource, "audit deal", _buildTransitionParametersItem_4);
    _transitions_8.add(_createTransitionForEach_4);
    EList<TransitionRef> _transitions_9 = collection.getTransitions();
    Event _createPUT_1 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_5 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransitionForEach_5 = ResourceMapperCommon.createTransitionForEach(_createPUT_1, reverseResource, "reverse deal", _buildTransitionParametersItem_5);
    _transitions_9.add(_createTransitionForEach_5);
    EList<TransitionRef> _transitions_10 = item.getTransitions();
    Event _createPOST_6 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_6 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_1 = ResourceMapperCommon.createTransition(_createPOST_6, validateResource, "validate deal", _buildTransitionParametersItem_6);
    _transitions_10.add(_createTransition_1);
    EList<TransitionRef> _transitions_11 = item.getTransitions();
    Event _createPUT_2 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_7 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_2 = ResourceMapperCommon.createTransition(_createPUT_2, inputResource, "input deal", _buildTransitionParametersItem_7);
    _transitions_11.add(_createTransition_2);
    EList<TransitionRef> _transitions_12 = item.getTransitions();
    Event _createPUT_3 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_8 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_3 = ResourceMapperCommon.createTransition(_createPUT_3, verifyResource, "verify deal", _buildTransitionParametersItem_8);
    _transitions_12.add(_createTransition_3);
    EList<TransitionRef> _transitions_13 = item.getTransitions();
    Event _createPOST_7 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_9 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_4 = ResourceMapperCommon.createTransition(_createPOST_7, auditResource, "audit deal", _buildTransitionParametersItem_9);
    _transitions_13.add(_createTransition_4);
    EList<TransitionRef> _transitions_14 = item.getTransitions();
    Event _createPUT_4 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_10 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_5 = ResourceMapperCommon.createTransition(_createPUT_4, reverseResource, "reverse deal", _buildTransitionParametersItem_10);
    _transitions_14.add(_createTransition_5);
    EList<TransitionRef> _transitions_15 = item.getTransitions();
    TransitionRef _createContextEnquiryTransition = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
    _transitions_15.add(_createContextEnquiryTransition);
    EList<TransitionRef> _transitions_16 = newResource.getTransitions();
    Event _createPOST_8 = this.eventFactory.createPOST();
    TransitionRef _createTransitionEmbedded_2 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_8, T24VersionMapper.metadataResource, "metadata", null);
    _transitions_16.add(_createTransitionEmbedded_2);
    EList<TransitionRef> _transitions_17 = newResource.getTransitions();
    Event _createPOST_9 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_11 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_6 = ResourceMapperCommon.createTransition(_createPOST_9, validateResource, "validate deal", _buildTransitionParametersItem_11);
    _transitions_17.add(_createTransition_6);
    EList<TransitionRef> _transitions_18 = newResource.getTransitions();
    Event _createPUT_5 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_12 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_7 = ResourceMapperCommon.createTransition(_createPUT_5, inputResource, "input deal", _buildTransitionParametersItem_12);
    _transitions_18.add(_createTransition_7);
    EList<TransitionRef> _transitions_19 = newResource.getTransitions();
    Event _createPOST_10 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_13 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_8 = ResourceMapperCommon.createTransition(_createPOST_10, verifyResource, "verify deal", _buildTransitionParametersItem_13);
    _transitions_19.add(_createTransition_8);
    EList<TransitionRef> _transitions_20 = inputResource.getTransitions();
    Event _createPOST_11 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_14 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_9 = ResourceMapperCommon.createTransition(_createPOST_11, verifyResource, "verify deal", _buildTransitionParametersItem_14);
    _transitions_20.add(_createTransition_9);
    EList<TransitionRef> _transitions_21 = inputResource.getTransitions();
    Event _createPUT_6 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_15 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_10 = ResourceMapperCommon.createTransition(_createPUT_6, reverseResource, "reverse deal", _buildTransitionParametersItem_15);
    _transitions_21.add(_createTransition_10);
    EList<TransitionRef> _transitions_22 = inputResource.getTransitions();
    TransitionRef _createContextEnquiryTransition_1 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
    _transitions_22.add(_createContextEnquiryTransition_1);
    inputResource.setErrorState(T24VersionMapper.customErrorHandler);
    Event _createGET_2 = this.eventFactory.createGET();
    T24VersionMapper.embedConditionalErrors(validateResource, _createGET_2, "errors", null);
    EList<TransitionRef> _transitions_23 = validateResource.getTransitions();
    Event _createPUT_7 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_16 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_11 = ResourceMapperCommon.createTransition(_createPUT_7, inputResource, "input deal", _buildTransitionParametersItem_16);
    _transitions_23.add(_createTransition_11);
    EList<TransitionRef> _transitions_24 = validateResource.getTransitions();
    Event _createPOST_12 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_17 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_12 = ResourceMapperCommon.createTransition(_createPOST_12, verifyResource, "verify deal", _buildTransitionParametersItem_17);
    _transitions_24.add(_createTransition_12);
    reverseResource.setErrorState(T24VersionMapper.customErrorHandler);
    Event _createPOST_13 = this.eventFactory.createPOST();
    final TransitionRef entryToNewItemTransition = ResourceMapperCommon.createTransition(_createPOST_13, newResource, "create new deal", null);
    EList<TransitionRef> _transitions_25 = entryResource.getTransitions();
    TransitionRef _addOkCondition_1 = T24VersionMapper.addOkCondition(entryToNewItemTransition, autoIdResource);
    _transitions_25.add(_addOkCondition_1);
    Event _createPOST_14 = this.eventFactory.createPOST();
    final TransitionRef entryToPopulateItemTransition = ResourceMapperCommon.createTransition(_createPOST_14, populateResource, "populate existing deal", null);
    EList<TransitionRef> _transitions_26 = entryResource.getTransitions();
    _transitions_26.add(entryToPopulateItemTransition);
    final HashMap<String,String> params = new HashMap<String, String>();
    params.put("id", "@");
    Event _createGET_3 = this.eventFactory.createGET();
    final TransitionRef transitionToSee = ResourceMapperCommon.createTransitionEmbedded(_createGET_3, (this.entityName + "_see"), "See transaction", params);
    EList<TransitionRef> _transitions_27 = entryResource.getTransitions();
    _transitions_27.add(transitionToSee);
    Event _createGET_4 = this.eventFactory.createGET();
    final TransitionRef transitionToContextEnquiries = ResourceMapperCommon.createTransitionEmbedded(_createGET_4, (this.entityName + "_ContextEnquiries"), "All context Enquiries", null);
    if ((!(skipDomainGenerationForIRIS).booleanValue())) {
      EList<TransitionRef> _transitions_28 = entryResource.getTransitions();
      _transitions_28.add(transitionToContextEnquiries);
    }
    EList<TransitionRef> _transitions_29 = entryResource.getTransitions();
    Event _createPOST_15 = this.eventFactory.createPOST();
    TransitionRef _createTransition_13 = ResourceMapperCommon.createTransition(_createPOST_15, copyResource, "Copy deal", null);
    _transitions_29.add(_createTransition_13);
    EList<TransitionRef> _transitions_30 = entryResource.getTransitions();
    Event _createPOST_16 = this.eventFactory.createPOST();
    TransitionRef _createTransition_14 = ResourceMapperCommon.createTransition(_createPOST_16, pasteResource, "Paste deal", null);
    _transitions_30.add(_createTransition_14);
    Event _createPOST_17 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_18 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    final TransitionRef populateToValidateTransition = ResourceMapperCommon.createTransition(_createPOST_17, validateResource, "validate deal", _buildTransitionParametersItem_18);
    EList<TransitionRef> _transitions_31 = populateResource.getTransitions();
    _transitions_31.add(populateToValidateTransition);
    Event _createPOST_18 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_19 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    final TransitionRef populateToInputTransition = ResourceMapperCommon.createTransition(_createPOST_18, inputResource, "input deal", _buildTransitionParametersItem_19);
    EList<TransitionRef> _transitions_32 = populateResource.getTransitions();
    _transitions_32.add(populateToInputTransition);
    Event _createPOST_19 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_20 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    final TransitionRef populateToVerifyTransition = ResourceMapperCommon.createTransition(_createPOST_19, verifyResource, "verify deal", _buildTransitionParametersItem_20);
    EList<TransitionRef> _transitions_33 = populateResource.getTransitions();
    _transitions_33.add(populateToVerifyTransition);
    Event _createPOST_20 = this.eventFactory.createPOST();
    final TransitionRef populateToMetadataTransition = ResourceMapperCommon.createTransition(_createPOST_20, T24VersionMapper.metadataResource, "metadata", null);
    EList<TransitionRef> _transitions_34 = populateResource.getTransitions();
    _transitions_34.add(populateToMetadataTransition);
    EList<TransitionRef> _transitions_35 = populateResource.getTransitions();
    Event _createPUT_8 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_21 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_15 = ResourceMapperCommon.createTransition(_createPUT_8, reverseResource, "reverse deal", _buildTransitionParametersItem_21);
    _transitions_35.add(_createTransition_15);
    EList<TransitionRef> _transitions_36 = populateResource.getTransitions();
    TransitionRef _createContextEnquiryTransition_2 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
    _transitions_36.add(_createContextEnquiryTransition_2);
    EList<TransitionRef> _transitions_37 = pasteResource.getTransitions();
    Event _createPOST_21 = this.eventFactory.createPOST();
    TransitionRef _createTransitionEmbedded_3 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_21, T24VersionMapper.metadataResource, "metadata", null);
    _transitions_37.add(_createTransitionEmbedded_3);
    EList<TransitionRef> _transitions_38 = pasteResource.getTransitions();
    Event _createPOST_22 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_22 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_16 = ResourceMapperCommon.createTransition(_createPOST_22, validateResource, "validate deal", _buildTransitionParametersItem_22);
    _transitions_38.add(_createTransition_16);
    EList<TransitionRef> _transitions_39 = pasteResource.getTransitions();
    Event _createPUT_9 = this.eventFactory.createPUT();
    Map<String,String> _buildTransitionParametersItem_23 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_17 = ResourceMapperCommon.createTransition(_createPUT_9, inputResource, "input deal", _buildTransitionParametersItem_23);
    _transitions_39.add(_createTransition_17);
    EList<TransitionRef> _transitions_40 = pasteResource.getTransitions();
    Event _createPOST_23 = this.eventFactory.createPOST();
    Map<String,String> _buildTransitionParametersItem_24 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
    TransitionRef _createTransition_18 = ResourceMapperCommon.createTransition(_createPOST_23, verifyResource, "verify deal", _buildTransitionParametersItem_24);
    _transitions_40.add(_createTransition_18);
  }
}