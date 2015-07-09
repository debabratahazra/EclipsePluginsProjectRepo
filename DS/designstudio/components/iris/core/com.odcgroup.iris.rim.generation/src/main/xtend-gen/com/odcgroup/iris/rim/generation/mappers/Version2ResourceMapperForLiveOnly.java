package com.odcgroup.iris.rim.generation.mappers;

import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.iris.rim.generation.mappers.T24VersionMapper;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import java.util.Map;
import org.eclipse.emf.common.util.EList;

/**
 * This class can be used to generate RIM for LIVE version only
 * 
 * @author sjunejo
 */
@SuppressWarnings("all")
public class Version2ResourceMapperForLiveOnly extends T24VersionMapper {
  public Version2ResourceMapperForLiveOnly(final ModelLoader _loader, final MdfClass _mdfClass, final Application _application, final Version _version, final ResourceInteractionModel _rim) {
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
    EList<State> _states_3 = this.rim.getStates();
    _states_3.add(contextEnquiriesResource);
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
  }
}
