package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.iris.rim.generation.mappers.T24VersionMapper;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.version.versionDSL.DealSlip;
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
 * TODO: Document me!
 * 
 * @author sjunejo
 */
@SuppressWarnings("all")
public class Version2ResourceMapperForLiveNauHist extends T24VersionMapper {
  public Version2ResourceMapperForLiveNauHist(final ModelLoader _loader, final MdfClass _mdfClass, final Application _application, final Version _version, final ResourceInteractionModel _rim) {
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
    boolean _isUnauthFile = ResourceMapperCommon.isUnauthFile(this.applicationType);
    if (_isUnauthFile) {
      final State seeIAuthResource = T24VersionMapper.createSeeResource("inau", this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_3 = this.rim.getStates();
      _states_3.add(seeIAuthResource);
      boolean _isHistFile = ResourceMapperCommon.isHistFile(this.applicationType);
      if (_isHistFile) {
        final State seeHistResource = T24VersionMapper.createSeeResource("hist", this.commandFactory, this.entityName, this.isAAVersion);
        EList<State> _states_4 = this.rim.getStates();
        _states_4.add(seeHistResource);
      }
    }
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
      EList<State> _states_5 = this.rim.getStates();
      _states_5.add(contextEnquiriesResource);
    }
    EList<State> _states_6 = this.rim.getStates();
    _states_6.add(T24VersionMapper.metadataResource);
    EList<TransitionRef> _transitions_2 = seeResource.getTransitions();
    Event _createPOST = this.eventFactory.createPOST();
    TransitionRef _createTransitionEmbedded = ResourceMapperCommon.createTransitionEmbedded(_createPOST, T24VersionMapper.metadataResource, "metadata", null);
    _transitions_2.add(_createTransitionEmbedded);
    EList<TransitionRef> _transitions_3 = item.getTransitions();
    Event _createPOST_1 = this.eventFactory.createPOST();
    TransitionRef _createTransitionEmbedded_1 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_1, T24VersionMapper.metadataResource, "metadata", null);
    _transitions_3.add(_createTransitionEmbedded_1);
    boolean _isUnauthFile_1 = ResourceMapperCommon.isUnauthFile(this.applicationType);
    if (_isUnauthFile_1) {
      final State unauthCollectionResource = ResourceMapperCommon.createUnauthCollectionResource(this.commandFactory, this.entityName, "_IAuth", "GetIauthEntities");
      EList<State> _states_7 = this.rim.getStates();
      _states_7.add(unauthCollectionResource);
      final State newResource = T24VersionMapper.createNewResource(this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_8 = this.rim.getStates();
      _states_8.add(newResource);
      final State inputResource = T24VersionMapper.createInputResource(this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_9 = this.rim.getStates();
      _states_9.add(inputResource);
      final State holdResource = T24VersionMapper.createHoldResource(this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_10 = this.rim.getStates();
      _states_10.add(holdResource);
      final State authoriseResource = T24VersionMapper.createAuthoriseResource(this.commandFactory, this.entityName);
      EList<State> _states_11 = this.rim.getStates();
      _states_11.add(authoriseResource);
      String changedValuesCommandName = "GetChangedValues";
      if (this.isAAVersion) {
        changedValuesCommandName = "GetAAChangedValues";
      }
      final State changedValuesResource = T24VersionMapper.createChangedValuesResource(this.commandFactory, this.entityName, changedValuesCommandName);
      EList<State> _states_12 = this.rim.getStates();
      _states_12.add(changedValuesResource);
      final State auditResource = T24VersionMapper.createAuditResource(this.commandFactory, this.entityName);
      EList<State> _states_13 = this.rim.getStates();
      _states_13.add(auditResource);
      State reverseResource = null;
      State _createReverseResource = T24VersionMapper.createReverseResource(this.commandFactory, this.entityName, this.isAAVersion);
      reverseResource = _createReverseResource;
      EList<State> _states_14 = this.rim.getStates();
      _states_14.add(reverseResource);
      final State validateResource = T24VersionMapper.createValidateResource(this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_15 = this.rim.getStates();
      _states_15.add(validateResource);
      String unauthCommandName = "GetIauthEntity";
      if (this.isAAVersion) {
        unauthCommandName = "GetIauthAAEntity";
      }
      final State unAuthItem = ResourceMapperCommon.createUnAuthItemResource(this.commandFactory, this.entityName, "_IAuth", unauthCommandName);
      EList<State> _states_16 = this.rim.getStates();
      _states_16.add(unAuthItem);
      final State restoreResource = T24VersionMapper.createRestoreResource(this.commandFactory, this.entityName);
      final State deleteResource = T24VersionMapper.createDeleteResource(this.commandFactory, this.entityName);
      EList<State> _states_17 = this.rim.getStates();
      _states_17.add(deleteResource);
      final State entryResource = T24VersionMapper.createEntryResource(this.commandFactory, this.entityName);
      EList<State> _states_18 = this.rim.getStates();
      _states_18.add(entryResource);
      final State populateResource = T24VersionMapper.createPopulateResource(this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_19 = this.rim.getStates();
      _states_19.add(populateResource);
      final State copyResource = T24VersionMapper.createCopyResource(this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_20 = this.rim.getStates();
      _states_20.add(copyResource);
      final State pasteResource = T24VersionMapper.createPasteResource(this.commandFactory, this.entityName, this.isAAVersion);
      EList<State> _states_21 = this.rim.getStates();
      _states_21.add(pasteResource);
      final State createDePreviewResource = T24VersionMapper.createDeliveryPreviewResource(this.commandFactory, this.entityName, this.eventFactory);
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(this.additionalInfo, null));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _contains = this.additionalInfo.contains("PREVIEW");
        _and = _contains;
      }
      if (_and) {
        EList<State> _states_22 = this.rim.getStates();
        _states_22.add(createDePreviewResource);
      }
      final State dealSlipsResource = T24VersionMapper.getDealSlipsResource(this.commandFactory, this.entityName, this.eventFactory);
      final EList<DealSlip> dealSlipFormats = this.version.getDealSlipFormats();
      boolean includeDealSlip = false;
      boolean _and_1 = false;
      boolean _notEquals_1 = (!Objects.equal(dealSlipFormats, null));
      if (!_notEquals_1) {
        _and_1 = false;
      } else {
        boolean _isEmpty = dealSlipFormats.isEmpty();
        boolean _not = (!_isEmpty);
        _and_1 = _not;
      }
      if (_and_1) {
        EList<State> _states_23 = this.rim.getStates();
        _states_23.add(dealSlipsResource);
        includeDealSlip = true;
      }
      final State enrichmentItemResource = ResourceMapperCommon.createEnrichmentIemResource(this.commandFactory, this.entityName, this.thisVersionCollectionResource, "_enrichment");
      EList<State> _states_24 = this.rim.getStates();
      _states_24.add(enrichmentItemResource);
      final State autoIdResource = T24VersionMapper.createAutoResource(this.commandFactory, this.entityName);
      EList<State> _states_25 = this.rim.getStates();
      _states_25.add(autoIdResource);
      State histItem = null;
      boolean _isHistFile_1 = ResourceMapperCommon.isHistFile(this.applicationType);
      if (_isHistFile_1) {
        EList<State> _states_26 = this.rim.getStates();
        _states_26.add(restoreResource);
        final State histCollectionResource = ResourceMapperCommon.createUnauthCollectionResource(this.commandFactory, this.entityName, "_HAuth", "GetHauthEntities");
        EList<State> _states_27 = this.rim.getStates();
        _states_27.add(histCollectionResource);
        String commandName = "GetHauthEntity";
        if (this.isAAVersion) {
          commandName = "GetHauthAAEntity";
        }
        State _createUnAuthItemResource = ResourceMapperCommon.createUnAuthItemResource(this.commandFactory, this.entityName, "_HAuth", commandName);
        histItem = _createUnAuthItemResource;
        EList<State> _states_28 = this.rim.getStates();
        _states_28.add(histItem);
        EList<TransitionRef> _transitions_4 = histCollectionResource.getTransitions();
        Event _createGET_2 = this.eventFactory.createGET();
        Map<String,String> _buildTransitionParametersItem_2 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransitionForEach_2 = ResourceMapperCommon.createTransitionForEach(_createGET_2, histItem, null, _buildTransitionParametersItem_2);
        _transitions_4.add(_createTransitionForEach_2);
        Event _createPOST_2 = this.eventFactory.createPOST();
        final TransitionRef histCollectionToNewTransition = ResourceMapperCommon.createTransition(_createPOST_2, newResource, "create new deal", null);
        EList<TransitionRef> _transitions_5 = histCollectionResource.getTransitions();
        TransitionRef _addOkCondition = T24VersionMapper.addOkCondition(histCollectionToNewTransition, autoIdResource);
        _transitions_5.add(_addOkCondition);
        EList<TransitionRef> _transitions_6 = histCollectionResource.getTransitions();
        Event _createPOST_3 = this.eventFactory.createPOST();
        TransitionRef _createTransition = ResourceMapperCommon.createTransition(_createPOST_3, populateResource, "populate existing deal", null);
        _transitions_6.add(_createTransition);
        EList<TransitionRef> _transitions_7 = histCollectionResource.getTransitions();
        Event _createPOST_4 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_3 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransitionForEach_3 = ResourceMapperCommon.createTransitionForEach(_createPOST_4, restoreResource, "restore deal", _buildTransitionParametersItem_3);
        _transitions_7.add(_createTransitionForEach_3);
        EList<TransitionRef> _transitions_8 = histItem.getTransitions();
        Event _createPOST_5 = this.eventFactory.createPOST();
        TransitionRef _createTransitionEmbedded_2 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_5, T24VersionMapper.metadataResource, "metadata", null);
        _transitions_8.add(_createTransitionEmbedded_2);
        EList<TransitionRef> _transitions_9 = histItem.getTransitions();
        Event _createPOST_6 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_4 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_1 = ResourceMapperCommon.createTransition(_createPOST_6, validateResource, "validate deal", _buildTransitionParametersItem_4);
        _transitions_9.add(_createTransition_1);
        EList<TransitionRef> _transitions_10 = histItem.getTransitions();
        Event _createPUT = this.eventFactory.createPUT();
        Map<String,String> _buildTransitionParametersItem_5 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_2 = ResourceMapperCommon.createTransition(_createPUT, inputResource, "input deal", _buildTransitionParametersItem_5);
        _transitions_10.add(_createTransition_2);
        EList<TransitionRef> _transitions_11 = histItem.getTransitions();
        Event _createPOST_7 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_6 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_3 = ResourceMapperCommon.createTransition(_createPOST_7, holdResource, "hold deal", _buildTransitionParametersItem_6);
        _transitions_11.add(_createTransition_3);
        EList<TransitionRef> _transitions_12 = histItem.getTransitions();
        Event _createPOST_8 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_7 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_4 = ResourceMapperCommon.createTransition(_createPOST_8, authoriseResource, "authorise deal", _buildTransitionParametersItem_7);
        _transitions_12.add(_createTransition_4);
        EList<TransitionRef> _transitions_13 = histItem.getTransitions();
        Event _createDELETE = this.eventFactory.createDELETE();
        Map<String,String> _buildTransitionParametersItem_8 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_5 = ResourceMapperCommon.createTransition(_createDELETE, deleteResource, "delete", _buildTransitionParametersItem_8);
        _transitions_13.add(_createTransition_5);
        EList<TransitionRef> _transitions_14 = histItem.getTransitions();
        TransitionRef _createContextEnquiryTransition = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
        _transitions_14.add(_createContextEnquiryTransition);
      }
      Event _createPOST_9 = this.eventFactory.createPOST();
      final TransitionRef collectionToNewTransition = ResourceMapperCommon.createTransition(_createPOST_9, newResource, "create new deal", null);
      EList<TransitionRef> _transitions_15 = collection.getTransitions();
      TransitionRef _addOkCondition_1 = T24VersionMapper.addOkCondition(collectionToNewTransition, autoIdResource);
      _transitions_15.add(_addOkCondition_1);
      EList<TransitionRef> _transitions_16 = collection.getTransitions();
      Event _createPOST_10 = this.eventFactory.createPOST();
      TransitionRef _createTransition_6 = ResourceMapperCommon.createTransition(_createPOST_10, populateResource, "populate existing deal", null);
      _transitions_16.add(_createTransition_6);
      EList<TransitionRef> _transitions_17 = collection.getTransitions();
      Event _createPUT_1 = this.eventFactory.createPUT();
      Map<String,String> _buildTransitionParametersItem_9 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransitionForEach_4 = ResourceMapperCommon.createTransitionForEach(_createPUT_1, inputResource, "input deal", _buildTransitionParametersItem_9);
      _transitions_17.add(_createTransitionForEach_4);
      EList<TransitionRef> _transitions_18 = collection.getTransitions();
      Event _createPOST_11 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_10 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransitionForEach_5 = ResourceMapperCommon.createTransitionForEach(_createPOST_11, auditResource, "audit deal", _buildTransitionParametersItem_10);
      _transitions_18.add(_createTransitionForEach_5);
      boolean _isHistFile_2 = ResourceMapperCommon.isHistFile(this.applicationType);
      if (_isHistFile_2) {
        EList<TransitionRef> _transitions_19 = collection.getTransitions();
        Event _createPOST_12 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_11 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransitionForEach_6 = ResourceMapperCommon.createTransitionForEach(_createPOST_12, reverseResource, "reverse deal", _buildTransitionParametersItem_11);
        _transitions_19.add(_createTransitionForEach_6);
      }
      EList<TransitionRef> _transitions_20 = unauthCollectionResource.getTransitions();
      Event _createGET_3 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_12 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransitionForEach_7 = ResourceMapperCommon.createTransitionForEach(_createGET_3, unAuthItem, null, _buildTransitionParametersItem_12);
      _transitions_20.add(_createTransitionForEach_7);
      Event _createPOST_13 = this.eventFactory.createPOST();
      final TransitionRef unauthCollectionToNewTransition = ResourceMapperCommon.createTransition(_createPOST_13, newResource, "create new deal", null);
      EList<TransitionRef> _transitions_21 = unauthCollectionResource.getTransitions();
      TransitionRef _addOkCondition_2 = T24VersionMapper.addOkCondition(unauthCollectionToNewTransition, autoIdResource);
      _transitions_21.add(_addOkCondition_2);
      EList<TransitionRef> _transitions_22 = unauthCollectionResource.getTransitions();
      Event _createPOST_14 = this.eventFactory.createPOST();
      TransitionRef _createTransition_7 = ResourceMapperCommon.createTransition(_createPOST_14, populateResource, "populate existing deal", null);
      _transitions_22.add(_createTransition_7);
      EList<TransitionRef> _transitions_23 = unauthCollectionResource.getTransitions();
      Event _createPUT_2 = this.eventFactory.createPUT();
      Map<String,String> _buildTransitionParametersItem_13 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransitionForEach_8 = ResourceMapperCommon.createTransitionForEach(_createPUT_2, inputResource, "input deal", _buildTransitionParametersItem_13);
      _transitions_23.add(_createTransitionForEach_8);
      EList<TransitionRef> _transitions_24 = unauthCollectionResource.getTransitions();
      Event _createDELETE_1 = this.eventFactory.createDELETE();
      Map<String,String> _buildTransitionParametersItem_14 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransitionForEach_9 = ResourceMapperCommon.createTransitionForEach(_createDELETE_1, deleteResource, "delete", _buildTransitionParametersItem_14);
      _transitions_24.add(_createTransitionForEach_9);
      EList<TransitionRef> _transitions_25 = unauthCollectionResource.getTransitions();
      Event _createPUT_3 = this.eventFactory.createPUT();
      Map<String,String> _buildTransitionParametersItem_15 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransitionForEach_10 = ResourceMapperCommon.createTransitionForEach(_createPUT_3, authoriseResource, "authorise deal", _buildTransitionParametersItem_15);
      _transitions_25.add(_createTransitionForEach_10);
      Event _createGET_4 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_16 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef unAuthItemTransition = ResourceMapperCommon.createTransitionAuto(_createGET_4, unAuthItem, null, _buildTransitionParametersItem_16);
      EList<TransitionRef> _transitions_26 = item.getTransitions();
      TransitionRef _addOkCondition_3 = T24VersionMapper.addOkCondition(unAuthItemTransition, unAuthItem);
      _transitions_26.add(_addOkCondition_3);
      EList<TransitionRef> _transitions_27 = item.getTransitions();
      Event _createPOST_15 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_17 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_8 = ResourceMapperCommon.createTransition(_createPOST_15, validateResource, "validate deal", _buildTransitionParametersItem_17);
      _transitions_27.add(_createTransition_8);
      EList<TransitionRef> _transitions_28 = item.getTransitions();
      Event _createPUT_4 = this.eventFactory.createPUT();
      Map<String,String> _buildTransitionParametersItem_18 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_9 = ResourceMapperCommon.createTransition(_createPUT_4, inputResource, "input deal", _buildTransitionParametersItem_18);
      _transitions_28.add(_createTransition_9);
      EList<TransitionRef> _transitions_29 = item.getTransitions();
      Event _createPOST_16 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_19 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_10 = ResourceMapperCommon.createTransition(_createPOST_16, auditResource, "audit deal", _buildTransitionParametersItem_19);
      _transitions_29.add(_createTransition_10);
      EList<TransitionRef> _transitions_30 = item.getTransitions();
      Event _createPOST_17 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_20 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_11 = ResourceMapperCommon.createTransition(_createPOST_17, reverseResource, "reverse deal", _buildTransitionParametersItem_20);
      _transitions_30.add(_createTransition_11);
      boolean _and_2 = false;
      boolean _notEquals_2 = (!Objects.equal(this.additionalInfo, null));
      if (!_notEquals_2) {
        _and_2 = false;
      } else {
        boolean _contains_1 = this.additionalInfo.contains("PREVIEW");
        _and_2 = _contains_1;
      }
      if (_and_2) {
        EList<TransitionRef> _transitions_31 = item.getTransitions();
        Event _createPOST_18 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_21 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_12 = ResourceMapperCommon.createTransition(_createPOST_18, createDePreviewResource, "delivery preview", _buildTransitionParametersItem_21);
        _transitions_31.add(_createTransition_12);
      }
      if (includeDealSlip) {
        EList<TransitionRef> _transitions_32 = item.getTransitions();
        Event _createGET_5 = this.eventFactory.createGET();
        Map<String,String> _buildTransitionParametersItem_22 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransitionEmbedded_3 = ResourceMapperCommon.createTransitionEmbedded(_createGET_5, dealSlipsResource, "deal slips", _buildTransitionParametersItem_22);
        _transitions_32.add(_createTransitionEmbedded_3);
      }
      EList<TransitionRef> _transitions_33 = item.getTransitions();
      TransitionRef _createContextEnquiryTransition_1 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
      _transitions_33.add(_createContextEnquiryTransition_1);
      EList<TransitionRef> _transitions_34 = unAuthItem.getTransitions();
      Event _createPOST_19 = this.eventFactory.createPOST();
      TransitionRef _createTransitionEmbedded_4 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_19, T24VersionMapper.metadataResource, "metadata", null);
      _transitions_34.add(_createTransitionEmbedded_4);
      EList<TransitionRef> _transitions_35 = unAuthItem.getTransitions();
      Event _createGET_6 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_23 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransitionEmbedded_5 = ResourceMapperCommon.createTransitionEmbedded(_createGET_6, changedValuesResource, "changed values", _buildTransitionParametersItem_23);
      _transitions_35.add(_createTransitionEmbedded_5);
      EList<TransitionRef> _transitions_36 = unAuthItem.getTransitions();
      Event _createPOST_20 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_24 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_13 = ResourceMapperCommon.createTransition(_createPOST_20, validateResource, "validate deal", _buildTransitionParametersItem_24);
      _transitions_36.add(_createTransition_13);
      EList<TransitionRef> _transitions_37 = unAuthItem.getTransitions();
      Event _createPUT_5 = this.eventFactory.createPUT();
      Map<String,String> _buildTransitionParametersItem_25 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_14 = ResourceMapperCommon.createTransition(_createPUT_5, inputResource, "input deal", _buildTransitionParametersItem_25);
      _transitions_37.add(_createTransition_14);
      EList<TransitionRef> _transitions_38 = unAuthItem.getTransitions();
      Event _createPOST_21 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_26 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_15 = ResourceMapperCommon.createTransition(_createPOST_21, holdResource, "hold deal", _buildTransitionParametersItem_26);
      _transitions_38.add(_createTransition_15);
      EList<TransitionRef> _transitions_39 = unAuthItem.getTransitions();
      Event _createPOST_22 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_27 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_16 = ResourceMapperCommon.createTransition(_createPOST_22, authoriseResource, "authorise deal", _buildTransitionParametersItem_27);
      _transitions_39.add(_createTransition_16);
      EList<TransitionRef> _transitions_40 = unAuthItem.getTransitions();
      Event _createDELETE_2 = this.eventFactory.createDELETE();
      Map<String,String> _buildTransitionParametersItem_28 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_17 = ResourceMapperCommon.createTransition(_createDELETE_2, deleteResource, "delete", _buildTransitionParametersItem_28);
      _transitions_40.add(_createTransition_17);
      EList<TransitionRef> _transitions_41 = unAuthItem.getTransitions();
      TransitionRef _createContextEnquiryTransition_2 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
      _transitions_41.add(_createContextEnquiryTransition_2);
      State aapopulateResource = null;
      if (this.isAAVersion) {
        EList<TransitionRef> _transitions_42 = newResource.getTransitions();
        Event _createPOST_23 = this.eventFactory.createPOST();
        TransitionRef _createTransitionEmbedded_6 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_23, T24VersionMapper.regularMetadataResourceForAADeals, "metadata", null);
        _transitions_42.add(_createTransitionEmbedded_6);
        State _createAAPopulateResource = T24VersionMapper.createAAPopulateResource(this.commandFactory, this.entityName);
        aapopulateResource = _createAAPopulateResource;
        EList<State> _states_29 = this.rim.getStates();
        _states_29.add(aapopulateResource);
        EList<TransitionRef> _transitions_43 = newResource.getTransitions();
        Event _createPOST_24 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_29 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_18 = ResourceMapperCommon.createTransition(_createPOST_24, aapopulateResource, "populate deal", _buildTransitionParametersItem_29);
        _transitions_43.add(_createTransition_18);
      } else {
        EList<TransitionRef> _transitions_44 = newResource.getTransitions();
        Event _createPOST_25 = this.eventFactory.createPOST();
        TransitionRef _createTransitionEmbedded_7 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_25, T24VersionMapper.metadataResource, "metadata", null);
        _transitions_44.add(_createTransitionEmbedded_7);
        EList<TransitionRef> _transitions_45 = newResource.getTransitions();
        Event _createPOST_26 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_30 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_19 = ResourceMapperCommon.createTransition(_createPOST_26, validateResource, "validate deal", _buildTransitionParametersItem_30);
        _transitions_45.add(_createTransition_19);
        EList<TransitionRef> _transitions_46 = newResource.getTransitions();
        Event _createPUT_6 = this.eventFactory.createPUT();
        Map<String,String> _buildTransitionParametersItem_31 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_20 = ResourceMapperCommon.createTransition(_createPUT_6, inputResource, "input deal", _buildTransitionParametersItem_31);
        _transitions_46.add(_createTransition_20);
        EList<TransitionRef> _transitions_47 = newResource.getTransitions();
        Event _createPOST_27 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_32 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_21 = ResourceMapperCommon.createTransition(_createPOST_27, holdResource, "hold deal", _buildTransitionParametersItem_32);
        _transitions_47.add(_createTransition_21);
      }
      boolean _and_3 = false;
      boolean _notEquals_3 = (!Objects.equal(this.additionalInfo, null));
      if (!_notEquals_3) {
        _and_3 = false;
      } else {
        boolean _contains_2 = this.additionalInfo.contains("PREVIEW");
        _and_3 = _contains_2;
      }
      if (_and_3) {
        EList<TransitionRef> _transitions_48 = newResource.getTransitions();
        Event _createPOST_28 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_33 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_22 = ResourceMapperCommon.createTransition(_createPOST_28, createDePreviewResource, "delivery preview", _buildTransitionParametersItem_33);
        _transitions_48.add(_createTransition_22);
      }
      EList<TransitionRef> _transitions_49 = newResource.getTransitions();
      TransitionRef _createContextEnquiryTransition_3 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
      _transitions_49.add(_createContextEnquiryTransition_3);
      boolean _and_4 = false;
      if (!this.isAAVersion) {
        _and_4 = false;
      } else {
        boolean _notEquals_4 = (!Objects.equal(aapopulateResource, null));
        _and_4 = _notEquals_4;
      }
      if (_and_4) {
        EList<TransitionRef> _transitions_50 = aapopulateResource.getTransitions();
        Event _createPOST_29 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_34 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransitionEmbedded_8 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_29, T24VersionMapper.metadataResource, "metadata", _buildTransitionParametersItem_34);
        _transitions_50.add(_createTransitionEmbedded_8);
        EList<TransitionRef> _transitions_51 = aapopulateResource.getTransitions();
        Event _createPOST_30 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_35 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_23 = ResourceMapperCommon.createTransition(_createPOST_30, validateResource, "validate deal", _buildTransitionParametersItem_35);
        _transitions_51.add(_createTransition_23);
        EList<TransitionRef> _transitions_52 = aapopulateResource.getTransitions();
        Event _createPUT_7 = this.eventFactory.createPUT();
        Map<String,String> _buildTransitionParametersItem_36 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_24 = ResourceMapperCommon.createTransition(_createPUT_7, inputResource, "input deal", _buildTransitionParametersItem_36);
        _transitions_52.add(_createTransition_24);
        EList<TransitionRef> _transitions_53 = aapopulateResource.getTransitions();
        Event _createPOST_31 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_37 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_25 = ResourceMapperCommon.createTransition(_createPOST_31, holdResource, "hold deal", _buildTransitionParametersItem_37);
        _transitions_53.add(_createTransition_25);
        EList<TransitionRef> _transitions_54 = aapopulateResource.getTransitions();
        TransitionRef _createContextEnquiryTransition_4 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
        _transitions_54.add(_createContextEnquiryTransition_4);
      }
      final State nextStateItem = ResourceMapperCommon.createNextStateItemResource(this.commandFactory);
      Event _createGET_7 = this.eventFactory.createGET();
      final TransitionRef inputToNextStateAutoTransition = ResourceMapperCommon.createTransitionAuto(_createGET_7, nextStateItem, null, null);
      EList<TransitionRef> _transitions_55 = inputResource.getTransitions();
      TransitionRef _addOkCondition_4 = T24VersionMapper.addOkCondition(inputToNextStateAutoTransition, nextStateItem);
      _transitions_55.add(_addOkCondition_4);
      Event _createGET_8 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_38 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef inputToUnAuthAutoTransition = ResourceMapperCommon.createTransitionAuto(_createGET_8, unAuthItem, null, _buildTransitionParametersItem_38);
      EList<TransitionRef> _transitions_56 = inputResource.getTransitions();
      TransitionRef _addOkCondition_5 = T24VersionMapper.addOkCondition(inputToUnAuthAutoTransition, unAuthItem);
      _transitions_56.add(_addOkCondition_5);
      Event _createGET_9 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_39 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef inputToLiveTransition = ResourceMapperCommon.createTransitionAuto(_createGET_9, item, null, _buildTransitionParametersItem_39);
      EList<TransitionRef> _transitions_57 = inputResource.getTransitions();
      TransitionRef _addOkCondition_6 = T24VersionMapper.addOkCondition(inputToLiveTransition, item);
      _transitions_57.add(_addOkCondition_6);
      EList<TransitionRef> _transitions_58 = inputResource.getTransitions();
      TransitionRef _createContextEnquiryTransition_5 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
      _transitions_58.add(_createContextEnquiryTransition_5);
      inputResource.setErrorState(T24VersionMapper.customErrorHandler);
      EList<TransitionRef> _transitions_59 = holdResource.getTransitions();
      Event _createPUT_8 = this.eventFactory.createPUT();
      Map<String,String> _buildTransitionParametersItem_40 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_26 = ResourceMapperCommon.createTransition(_createPUT_8, inputResource, "input deal", _buildTransitionParametersItem_40);
      _transitions_59.add(_createTransition_26);
      EList<TransitionRef> _transitions_60 = holdResource.getTransitions();
      Event _createDELETE_3 = this.eventFactory.createDELETE();
      Map<String,String> _buildTransitionParametersItem_41 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_27 = ResourceMapperCommon.createTransition(_createDELETE_3, deleteResource, "delete", _buildTransitionParametersItem_41);
      _transitions_60.add(_createTransition_27);
      Event _createGET_10 = this.eventFactory.createGET();
      T24VersionMapper.embedConditionalErrors(validateResource, _createGET_10, "errors", null);
      EList<TransitionRef> _transitions_61 = validateResource.getTransitions();
      Event _createPOST_32 = this.eventFactory.createPOST();
      TransitionRef _createTransitionEmbedded_9 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_32, T24VersionMapper.metadataResource, "metadata", null);
      _transitions_61.add(_createTransitionEmbedded_9);
      EList<TransitionRef> _transitions_62 = validateResource.getTransitions();
      Event _createPUT_9 = this.eventFactory.createPUT();
      Map<String,String> _buildTransitionParametersItem_42 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_28 = ResourceMapperCommon.createTransition(_createPUT_9, inputResource, "input deal", _buildTransitionParametersItem_42);
      _transitions_62.add(_createTransition_28);
      EList<TransitionRef> _transitions_63 = validateResource.getTransitions();
      Event _createPOST_33 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_43 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_29 = ResourceMapperCommon.createTransition(_createPOST_33, holdResource, "hold deal", _buildTransitionParametersItem_43);
      _transitions_63.add(_createTransition_29);
      Event _createGET_11 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_44 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef authToUnAuthAutoTransition = ResourceMapperCommon.createTransitionAuto(_createGET_11, unAuthItem, null, _buildTransitionParametersItem_44);
      EList<TransitionRef> _transitions_64 = authoriseResource.getTransitions();
      TransitionRef _addOkCondition_7 = T24VersionMapper.addOkCondition(authToUnAuthAutoTransition, unAuthItem);
      _transitions_64.add(_addOkCondition_7);
      Event _createGET_12 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_45 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef authToLiveAuthAutoTransition = ResourceMapperCommon.createTransitionAuto(_createGET_12, item, null, _buildTransitionParametersItem_45);
      EList<TransitionRef> _transitions_65 = authoriseResource.getTransitions();
      TransitionRef _addOkCondition_8 = T24VersionMapper.addOkCondition(authToLiveAuthAutoTransition, item);
      _transitions_65.add(_addOkCondition_8);
      EList<TransitionRef> _transitions_66 = auditResource.getTransitions();
      Event _createGET_13 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_46 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      TransitionRef _createTransition_30 = ResourceMapperCommon.createTransition(_createGET_13, item, null, _buildTransitionParametersItem_46);
      _transitions_66.add(_createTransition_30);
      Event _createGET_14 = this.eventFactory.createGET();
      Map<String,String> _buildTransitionParametersItem_47 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef reverseToUnauthTransition = ResourceMapperCommon.createTransitionAuto(_createGET_14, unAuthItem, null, _buildTransitionParametersItem_47);
      EList<TransitionRef> _transitions_67 = reverseResource.getTransitions();
      TransitionRef _addOkCondition_9 = T24VersionMapper.addOkCondition(reverseToUnauthTransition, unAuthItem);
      _transitions_67.add(_addOkCondition_9);
      boolean _isHistFile_3 = ResourceMapperCommon.isHistFile(this.applicationType);
      if (_isHistFile_3) {
        Event _createGET_15 = this.eventFactory.createGET();
        Map<String,String> _buildTransitionParametersItem_48 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        final TransitionRef reverseToHistTransition = ResourceMapperCommon.createTransitionAuto(_createGET_15, histItem, null, _buildTransitionParametersItem_48);
        EList<TransitionRef> _transitions_68 = reverseResource.getTransitions();
        TransitionRef _addOkCondition_10 = T24VersionMapper.addOkCondition(reverseToHistTransition, histItem);
        _transitions_68.add(_addOkCondition_10);
        Event _createGET_16 = this.eventFactory.createGET();
        Map<String,String> _buildTransitionParametersItem_49 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        final TransitionRef restoreToUnauthTransition = ResourceMapperCommon.createTransitionAuto(_createGET_16, unAuthItem, null, _buildTransitionParametersItem_49);
        EList<TransitionRef> _transitions_69 = restoreResource.getTransitions();
        TransitionRef _addOkCondition_11 = T24VersionMapper.addOkCondition(restoreToUnauthTransition, unAuthItem);
        _transitions_69.add(_addOkCondition_11);
        Event _createGET_17 = this.eventFactory.createGET();
        Map<String,String> _buildTransitionParametersItem_50 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        final TransitionRef restoreToLiveTransition = ResourceMapperCommon.createTransitionAuto(_createGET_17, item, null, _buildTransitionParametersItem_50);
        EList<TransitionRef> _transitions_70 = restoreResource.getTransitions();
        TransitionRef _addOkCondition_12 = T24VersionMapper.addOkCondition(restoreToLiveTransition, item);
        _transitions_70.add(_addOkCondition_12);
        EList<TransitionRef> _transitions_71 = restoreResource.getTransitions();
        Event _createPOST_34 = this.eventFactory.createPOST();
        TransitionRef _createTransition_31 = ResourceMapperCommon.createTransition(_createPOST_34, T24VersionMapper.metadataResource, "metadata", null);
        _transitions_71.add(_createTransition_31);
        EList<TransitionRef> _transitions_72 = restoreResource.getTransitions();
        TransitionRef _createContextEnquiryTransition_6 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
        _transitions_72.add(_createContextEnquiryTransition_6);
      }
      reverseResource.setErrorState(T24VersionMapper.customErrorHandler);
      EList<TransitionRef> _transitions_73 = authoriseResource.getTransitions();
      TransitionRef _createContextEnquiryTransition_7 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
      _transitions_73.add(_createContextEnquiryTransition_7);
      authoriseResource.setErrorState(T24VersionMapper.customErrorHandler);
      Event _createPOST_35 = this.eventFactory.createPOST();
      final TransitionRef entryToNewItemTransition = ResourceMapperCommon.createTransition(_createPOST_35, newResource, "create new deal", null);
      EList<TransitionRef> _transitions_74 = entryResource.getTransitions();
      TransitionRef _addOkCondition_13 = T24VersionMapper.addOkCondition(entryToNewItemTransition, autoIdResource);
      _transitions_74.add(_addOkCondition_13);
      Event _createPOST_36 = this.eventFactory.createPOST();
      final TransitionRef entryToPopulateItemTransition = ResourceMapperCommon.createTransition(_createPOST_36, populateResource, "populate existing deal", null);
      EList<TransitionRef> _transitions_75 = entryResource.getTransitions();
      _transitions_75.add(entryToPopulateItemTransition);
      final HashMap<String,String> params = new HashMap<String, String>();
      params.put("id", "@");
      Event _createGET_18 = this.eventFactory.createGET();
      final TransitionRef transitionToSee = ResourceMapperCommon.createTransitionEmbedded(_createGET_18, (this.entityName + "_see"), "See transaction", params);
      EList<TransitionRef> _transitions_76 = entryResource.getTransitions();
      _transitions_76.add(transitionToSee);
      boolean _isUnauthFile_2 = ResourceMapperCommon.isUnauthFile(this.applicationType);
      if (_isUnauthFile_2) {
        Event _createGET_19 = this.eventFactory.createGET();
        final TransitionRef transitionToIAuth = ResourceMapperCommon.createTransitionEmbedded(_createGET_19, (this.entityName + "_IAuth_see"), "GetIauthEntity", params);
        EList<TransitionRef> _transitions_77 = entryResource.getTransitions();
        _transitions_77.add(transitionToIAuth);
        boolean _isHistFile_4 = ResourceMapperCommon.isHistFile(this.applicationType);
        if (_isHistFile_4) {
          Event _createGET_20 = this.eventFactory.createGET();
          final TransitionRef transitionToHAuth = ResourceMapperCommon.createTransitionEmbedded(_createGET_20, (this.entityName + "_HAuth_see"), "GetHauthEntity", params);
          EList<TransitionRef> _transitions_78 = entryResource.getTransitions();
          _transitions_78.add(transitionToHAuth);
        }
      }
      boolean _isUnauthFile_3 = ResourceMapperCommon.isUnauthFile(this.applicationType);
      if (_isUnauthFile_3) {
        Event _createGET_21 = this.eventFactory.createGET();
        final TransitionRef transitionToIAuth_1 = ResourceMapperCommon.createTransitionEmbedded(_createGET_21, (this.entityName + "_IAuth"), "GetIauthEntity", params);
        EList<TransitionRef> _transitions_79 = entryResource.getTransitions();
        _transitions_79.add(transitionToIAuth_1);
        boolean _isHistFile_5 = ResourceMapperCommon.isHistFile(this.applicationType);
        if (_isHistFile_5) {
          Event _createGET_22 = this.eventFactory.createGET();
          final TransitionRef transitionToHAuth_1 = ResourceMapperCommon.createTransitionEmbedded(_createGET_22, (this.entityName + "_HAuth"), "GetHauthEntity", params);
          EList<TransitionRef> _transitions_80 = entryResource.getTransitions();
          _transitions_80.add(transitionToHAuth_1);
        }
      }
      Event _createGET_23 = this.eventFactory.createGET();
      final TransitionRef transitionToContextEnquiries = ResourceMapperCommon.createTransitionEmbedded(_createGET_23, (this.entityName + "_ContextEnquiries"), "All context Enquiries", null);
      if ((!(skipDomainGenerationForIRIS).booleanValue())) {
        EList<TransitionRef> _transitions_81 = entryResource.getTransitions();
        _transitions_81.add(transitionToContextEnquiries);
      }
      EList<TransitionRef> _transitions_82 = entryResource.getTransitions();
      Event _createPOST_37 = this.eventFactory.createPOST();
      TransitionRef _createTransition_32 = ResourceMapperCommon.createTransition(_createPOST_37, copyResource, "Copy deal", null);
      _transitions_82.add(_createTransition_32);
      EList<TransitionRef> _transitions_83 = entryResource.getTransitions();
      Event _createPOST_38 = this.eventFactory.createPOST();
      TransitionRef _createTransition_33 = ResourceMapperCommon.createTransition(_createPOST_38, pasteResource, "Paste deal", null);
      _transitions_83.add(_createTransition_33);
      Event _createPOST_39 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_51 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef populateToValidateTransition = ResourceMapperCommon.createTransition(_createPOST_39, validateResource, "validate deal", _buildTransitionParametersItem_51);
      EList<TransitionRef> _transitions_84 = populateResource.getTransitions();
      _transitions_84.add(populateToValidateTransition);
      Event _createPOST_40 = this.eventFactory.createPOST();
      Map<String,String> _buildTransitionParametersItem_52 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef populateToInputTransition = ResourceMapperCommon.createTransition(_createPOST_40, inputResource, "input deal", _buildTransitionParametersItem_52);
      EList<TransitionRef> _transitions_85 = populateResource.getTransitions();
      _transitions_85.add(populateToInputTransition);
      Event _createDELETE_4 = this.eventFactory.createDELETE();
      Map<String,String> _buildTransitionParametersItem_53 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
      final TransitionRef populateToDeleteTransition = ResourceMapperCommon.createTransition(_createDELETE_4, deleteResource, "delete", _buildTransitionParametersItem_53);
      EList<TransitionRef> _transitions_86 = populateResource.getTransitions();
      TransitionRef _addOkCondition_14 = T24VersionMapper.addOkCondition(populateToDeleteTransition, unAuthItem);
      _transitions_86.add(_addOkCondition_14);
      Event _createPOST_41 = this.eventFactory.createPOST();
      final TransitionRef populateToMetadataTransition = ResourceMapperCommon.createTransition(_createPOST_41, T24VersionMapper.metadataResource, "metadata", null);
      EList<TransitionRef> _transitions_87 = populateResource.getTransitions();
      _transitions_87.add(populateToMetadataTransition);
      EList<TransitionRef> _transitions_88 = populateResource.getTransitions();
      TransitionRef _createContextEnquiryTransition_8 = T24VersionMapper.createContextEnquiryTransition(this.loader, this.version, this.entityName, this.eventFactory);
      _transitions_88.add(_createContextEnquiryTransition_8);
      boolean _notEquals_5 = (!Objects.equal(T24VersionMapper.errorsResource, null));
      if (_notEquals_5) {
        holdResource.setErrorState(T24VersionMapper.errorsResource);
        auditResource.setErrorState(T24VersionMapper.errorsResource);
        restoreResource.setErrorState(T24VersionMapper.errorsResource);
      }
      if (this.isAAVersion) {
        EList<TransitionRef> _transitions_89 = pasteResource.getTransitions();
        Event _createPOST_42 = this.eventFactory.createPOST();
        TransitionRef _createTransitionEmbedded_10 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_42, T24VersionMapper.regularMetadataResourceForAADeals, "metadata", null);
        _transitions_89.add(_createTransitionEmbedded_10);
        EList<TransitionRef> _transitions_90 = pasteResource.getTransitions();
        Event _createPOST_43 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_54 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_34 = ResourceMapperCommon.createTransition(_createPOST_43, aapopulateResource, "populate deal", _buildTransitionParametersItem_54);
        _transitions_90.add(_createTransition_34);
      } else {
        EList<TransitionRef> _transitions_91 = pasteResource.getTransitions();
        Event _createPOST_44 = this.eventFactory.createPOST();
        TransitionRef _createTransitionEmbedded_11 = ResourceMapperCommon.createTransitionEmbedded(_createPOST_44, T24VersionMapper.metadataResource, "metadata", null);
        _transitions_91.add(_createTransitionEmbedded_11);
        EList<TransitionRef> _transitions_92 = pasteResource.getTransitions();
        Event _createPOST_45 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_55 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_35 = ResourceMapperCommon.createTransition(_createPOST_45, validateResource, "validate deal", _buildTransitionParametersItem_55);
        _transitions_92.add(_createTransition_35);
        EList<TransitionRef> _transitions_93 = pasteResource.getTransitions();
        Event _createPUT_10 = this.eventFactory.createPUT();
        Map<String,String> _buildTransitionParametersItem_56 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_36 = ResourceMapperCommon.createTransition(_createPUT_10, inputResource, "input deal", _buildTransitionParametersItem_56);
        _transitions_93.add(_createTransition_36);
        EList<TransitionRef> _transitions_94 = pasteResource.getTransitions();
        Event _createPOST_46 = this.eventFactory.createPOST();
        Map<String,String> _buildTransitionParametersItem_57 = ResourceMapperCommon.buildTransitionParametersItem(this.emEntity);
        TransitionRef _createTransition_37 = ResourceMapperCommon.createTransition(_createPOST_46, holdResource, "hold deal", _buildTransitionParametersItem_57);
        _transitions_94.add(_createTransition_37);
      }
    }
  }
}
