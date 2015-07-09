package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.generator.IRISDefaultListEnquiryMapper;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.schema.ApplicationType;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.BasePath;
import com.temenos.interaction.rimdsl.rim.Command;
import com.temenos.interaction.rimdsl.rim.DomainDeclaration;
import com.temenos.interaction.rimdsl.rim.DomainModel;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.Ref;
import com.temenos.interaction.rimdsl.rim.RelationConstant;
import com.temenos.interaction.rimdsl.rim.RelationRef;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.RimPackage;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for building the DomainModel from the MdfClass to build rim for the default list enquiry
 * of the application (represented by the MdfClass)
 */
@SuppressWarnings("all")
public class Domain2ResourceMapper {
  /**
   * Since looking for the fact that the resource already exist slow down a lot the generation,
   * allow to skip it. In the case of 500 domain in a 1/2 fully loaded environment, having this flag turned on
   * the rim generation takes 30 seconds. If the flag is turned off, this takes 166 seconds !
   */
  private final static boolean fastMode = "true".equals(System.getProperty("rim.fast.mode"));
  
  public DomainModel mapDomain(final MdfClass mdfClass, final ModelLoader modelLoader, final IFileSystemAccess fsa) throws Exception {
    Logger logger = LoggerFactory.getLogger(Domain2ResourceMapper.class);
    Resource.RESOURCE_TYPE _TYPE_ENQLIST = T24ResourceModelsGenerator.TYPE_ENQLIST();
    String _name = mdfClass.getName();
    String _camelCaseName = EMUtils.camelCaseName(_name);
    final String enqName = (_TYPE_ENQLIST + _camelCaseName);
    final String enqCollectionName = (enqName + "s");
    final String rimName = enqName;
    if ((!Domain2ResourceMapper.fastMode)) {
      final EObject domainEobject = ((EObject) mdfClass);
      Object _namedEObjectOrProxy = modelLoader.<Object>getNamedEObjectOrProxy(domainEobject, enqCollectionName, RimPackage.Literals.STATE, true, false);
      final State existingResource = ((State) _namedEObjectOrProxy);
      boolean _notEquals = (!Objects.equal(existingResource, null));
      if (_notEquals) {
        final InternalEObject internal = ((InternalEObject) existingResource);
        final URI path = internal.eProxyURI();
        String[] _segments = path.segments();
        for (final String oneSegment : _segments) {
          boolean _equals = oneSegment.equals("adopted");
          if (_equals) {
            logger.info((("Skipping generation of [" + rimName) + "], already in our models project"));
            return null;
          }
        }
      }
    }
    logger.info((("Generating RIM for [" + rimName) + "]"));
    final DomainModel domainModel = RimFactory.eINSTANCE.createDomainModel();
    final DomainDeclaration domain = RimFactory.eINSTANCE.createDomainDeclaration();
    String _domain = T24ResourceModelsGenerator.getDomain(null);
    domain.setName(_domain);
    EList<Ref> _rims = domainModel.getRims();
    _rims.add(domain);
    final EList<Ref> modelReferences = domain.getRims();
    ResourceMapperCommon.addUsedDomains(modelReferences);
    final ResourceInteractionModel rim = RimFactory.eINSTANCE.createResourceInteractionModel();
    rim.setName(rimName);
    modelReferences.add(rim);
    String applicationType = "";
    final ApplicationType tmpAppType = T24Aspect.getType(mdfClass);
    boolean _notEquals_1 = (!Objects.equal(tmpAppType, null));
    if (_notEquals_1) {
      String _name_1 = tmpAppType.name();
      applicationType = _name_1;
    }
    IRISDefaultListEnquiryMapper defaultListEnquiryMapper = new IRISDefaultListEnquiryMapper();
    Enquiry defaultEnquiryEntity = defaultListEnquiryMapper.getExistingEnquiry(mdfClass, modelLoader, "");
    String targetReference = "";
    boolean _notEquals_2 = (!Objects.equal(defaultEnquiryEntity, null));
    if (_notEquals_2) {
      Resource.RESOURCE_TYPE _TYPE_ENQUIRY = T24ResourceModelsGenerator.TYPE_ENQUIRY();
      String _name_2 = defaultEnquiryEntity.getName();
      String _camelCaseName_1 = EMUtils.camelCaseName(_name_2);
      String _plus = (_TYPE_ENQUIRY + _camelCaseName_1);
      String _plus_1 = (_plus + "s");
      targetReference = _plus_1;
    } else {
      Resource.RESOURCE_TYPE _TYPE_ENQUIRY_1 = T24ResourceModelsGenerator.TYPE_ENQUIRY();
      String _name_3 = mdfClass.getName();
      String _plus_2 = ("%" + _name_3);
      String _camelCaseName_2 = EMUtils.camelCaseName(_plus_2);
      String _plus_3 = (_TYPE_ENQUIRY_1 + _camelCaseName_2);
      String _plus_4 = (_plus_3 + "s");
      targetReference = _plus_4;
    }
    EList<Command> _commands = rim.getCommands();
    final CommandFactory commandFactory = new CommandFactory(_commands);
    final BasePath basepath = RimFactory.eINSTANCE.createBasePath();
    basepath.setName("/{companyid}");
    rim.setBasepath(basepath);
    final Event getEvent = RimFactory.eINSTANCE.createEvent();
    getEvent.setHttpMethod("GET");
    getEvent.setName("GET");
    final State collection = ResourceMapperCommon.createCollectionResource(commandFactory, enqName, enqCollectionName);
    RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName(("http://schemas.microsoft.com/ado/2007/08/dataservices/related/live/" + targetReference));
    EList<RelationRef> _relations = collection.getRelations();
    _relations.add(relation);
    EList<State> _states = rim.getStates();
    _states.add(collection);
    final State liveItem = ResourceMapperCommon.createDomainItemResource(commandFactory, enqName);
    EList<TransitionRef> transitions = collection.getTransitions();
    Map<String,String> _buildTransitionParametersItem = Domain2ResourceMapper.buildTransitionParametersItem();
    TransitionRef _createTransitionForEach = ResourceMapperCommon.createTransitionForEach(getEvent, liveItem, null, _buildTransitionParametersItem);
    transitions.add(_createTransitionForEach);
    EList<State> _states_1 = rim.getStates();
    _states_1.add(liveItem);
    boolean _isUnauthFile = ResourceMapperCommon.isUnauthFile(applicationType);
    if (_isUnauthFile) {
      final State unauthCollectionResource = ResourceMapperCommon.createDefaultListCollectionResource(commandFactory, enqName, "Unauth", "GETEntities");
      RelationConstant _createRelationConstant = RimFactory.eINSTANCE.createRelationConstant();
      relation = _createRelationConstant;
      relation.setName(("http://schemas.microsoft.com/ado/2007/08/dataservices/related/unauth/" + targetReference));
      EList<RelationRef> _relations_1 = unauthCollectionResource.getRelations();
      _relations_1.add(relation);
      EList<State> _states_2 = rim.getStates();
      _states_2.add(unauthCollectionResource);
      final State unauthItem = ResourceMapperCommon.createDefaultListItemResource(commandFactory, enqName, "Unauth", "GETEntity");
      EList<TransitionRef> _transitions = unauthCollectionResource.getTransitions();
      transitions = _transitions;
      Map<String,String> _buildTransitionParametersItem_1 = Domain2ResourceMapper.buildTransitionParametersItem();
      TransitionRef _createTransitionForEach_1 = ResourceMapperCommon.createTransitionForEach(getEvent, unauthItem, null, _buildTransitionParametersItem_1);
      transitions.add(_createTransitionForEach_1);
      EList<State> _states_3 = rim.getStates();
      _states_3.add(unauthItem);
      boolean _isHistFile = ResourceMapperCommon.isHistFile(applicationType);
      if (_isHistFile) {
        final State histCollectionResource = ResourceMapperCommon.createDefaultListCollectionResource(commandFactory, enqName, "Hist", "GETEntities");
        RelationConstant _createRelationConstant_1 = RimFactory.eINSTANCE.createRelationConstant();
        relation = _createRelationConstant_1;
        relation.setName(("http://schemas.microsoft.com/ado/2007/08/dataservices/related/history/" + targetReference));
        EList<RelationRef> _relations_2 = histCollectionResource.getRelations();
        _relations_2.add(relation);
        EList<State> _states_4 = rim.getStates();
        _states_4.add(histCollectionResource);
        final State histItem = ResourceMapperCommon.createDefaultListItemResource(commandFactory, enqName, "Hist", "GETEntity");
        EList<TransitionRef> _transitions_1 = histCollectionResource.getTransitions();
        transitions = _transitions_1;
        Map<String,String> _buildTransitionParametersItem_2 = Domain2ResourceMapper.buildTransitionParametersItem();
        TransitionRef _createTransitionForEach_2 = ResourceMapperCommon.createTransitionForEach(getEvent, histItem, null, _buildTransitionParametersItem_2);
        transitions.add(_createTransitionForEach_2);
        EList<State> _states_5 = rim.getStates();
        _states_5.add(histItem);
      }
    }
    return domainModel;
  }
  
  public static Map<String,String> buildTransitionParametersItem() {
    final HashMap<String,String> parameters = new HashMap<String, String>();
    parameters.put("id", "{Id}");
    return parameters;
  }
}
