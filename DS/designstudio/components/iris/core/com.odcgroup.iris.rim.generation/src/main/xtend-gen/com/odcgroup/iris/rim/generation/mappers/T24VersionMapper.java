package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.IRISDomainMapper;
import com.odcgroup.iris.generator.IRISVersionMapper;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.EventFactory;
import com.odcgroup.iris.rim.generation.mappers.MappersConstants;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.schema.ApplicationType;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.BasePath;
import com.temenos.interaction.rimdsl.rim.Command;
import com.temenos.interaction.rimdsl.rim.Entity;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.Expression;
import com.temenos.interaction.rimdsl.rim.Function;
import com.temenos.interaction.rimdsl.rim.ImplRef;
import com.temenos.interaction.rimdsl.rim.MethodRef;
import com.temenos.interaction.rimdsl.rim.OKFunction;
import com.temenos.interaction.rimdsl.rim.Path;
import com.temenos.interaction.rimdsl.rim.RelationConstant;
import com.temenos.interaction.rimdsl.rim.RelationRef;
import com.temenos.interaction.rimdsl.rim.ResourceCommand;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.ResourceType;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.RimPackage;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import com.temenos.interaction.rimdsl.rim.TransitionSpec;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.EList;

/**
 * Super class which will provide common functionality required for different types of Version2Resource Mappers
 * 
 * @author sjunejo
 */
@SuppressWarnings("all")
public abstract class T24VersionMapper {
  protected ModelLoader loader;
  
  protected MdfClass mdfClass;
  
  protected Application application;
  
  protected Version version;
  
  protected EMEntity emEntity;
  
  protected String entityName;
  
  protected String thisVersionCollectionResource;
  
  protected ResourceInteractionModel rim;
  
  protected EventFactory eventFactory;
  
  protected CommandFactory commandFactory;
  
  protected String applicationType;
  
  protected String additionalInfo;
  
  protected boolean isAAVersion;
  
  protected boolean isAAArr;
  
  protected FieldTypeChecker fieldTypeChecker;
  
  /**
   * A Flag to know if we already tried to get the errorResource or not
   */
  protected static boolean errorsResourceSearched = false;
  
  protected static State checkIfErrorResource = null;
  
  protected static State processErrorResource = null;
  
  protected static State errorsResource = null;
  
  protected static State customErrorHandler = null;
  
  protected static State metadataResource = null;
  
  protected static State regularMetadataResourceForAADeals = null;
  
  public T24VersionMapper(final ModelLoader _loader, final MdfClass _mdfClass, final Application _application, final Version _version, final ResourceInteractionModel _rim) {
    this.loader = _loader;
    this.mdfClass = _mdfClass;
    this.application = _application;
    this.version = _version;
    this.rim = _rim;
    FieldTypeChecker _fieldTypeChecker = new FieldTypeChecker(_version);
    this.fieldTypeChecker = _fieldTypeChecker;
    this.initialiseRIM();
  }
  
  private void initialiseRIM() {
    final IRISVersionMapper versionMapper = new IRISVersionMapper();
    EMEntity _entity = versionMapper.getEntity(this.version, this.application, this.fieldTypeChecker);
    this.emEntity = _entity;
    Resource.RESOURCE_TYPE _TYPE_VERSION = T24ResourceModelsGenerator.TYPE_VERSION();
    String _t24Name = this.version.getT24Name();
    String _camelCaseName = EMUtils.camelCaseName(_t24Name);
    String _plus = (_TYPE_VERSION + _camelCaseName);
    this.entityName = _plus;
    this.thisVersionCollectionResource = (this.entityName + "s");
    EventFactory _eventFactory = new EventFactory(this.loader, this.version);
    this.eventFactory = _eventFactory;
    EList<Command> _commands = this.rim.getCommands();
    CommandFactory _commandFactory = new CommandFactory(_commands);
    this.commandFactory = _commandFactory;
    final BasePath basepath = RimFactory.eINSTANCE.createBasePath();
    basepath.setName(MappersConstants.RIM_BASE_PATH);
    this.rim.setBasepath(basepath);
    ApplicationType _type = T24Aspect.getType(this.mdfClass);
    String _name = _type.name();
    this.applicationType = _name;
    String _additionalInfo = T24Aspect.getAdditionalInfo(this.mdfClass);
    this.additionalInfo = _additionalInfo;
    String _t24Name_1 = this.version.getT24Name();
    boolean _startsWith = _t24Name_1.startsWith(MappersConstants.AAA_VERSION_PREFIX);
    this.isAAVersion = _startsWith;
    boolean _isAAResource = IRISDomainMapper.isAAResource(this.mdfClass);
    this.isAAArr = _isAAResource;
    State _createMetadataResource = T24VersionMapper.createMetadataResource(this.commandFactory, this.entityName, this.isAAVersion);
    T24VersionMapper.metadataResource = _createMetadataResource;
    if (this.isAAVersion) {
      State _createRegularMetadataResourceForAADeals = T24VersionMapper.createRegularMetadataResourceForAADeals(this.commandFactory, this.entityName);
      T24VersionMapper.regularMetadataResourceForAADeals = _createRegularMetadataResourceForAADeals;
      EList<State> _states = this.rim.getStates();
      _states.add(T24VersionMapper.regularMetadataResourceForAADeals);
    }
    this.initialiseErrorHandler();
  }
  
  private boolean initialiseErrorHandler() {
    boolean _xblockexpression = false;
    {
      if ((!T24VersionMapper.errorsResourceSearched)) {
        Object _namedEObjectOrProxy = this.loader.<Object>getNamedEObjectOrProxy(this.version, "Errors.Errors", RimPackage.Literals.STATE, true, false);
        T24VersionMapper.errorsResource = ((State) _namedEObjectOrProxy);
        Object _namedEObjectOrProxy_1 = this.loader.<Object>getNamedEObjectOrProxy(this.version, "Errors.CheckIfError", RimPackage.Literals.STATE, true, false);
        T24VersionMapper.checkIfErrorResource = ((State) _namedEObjectOrProxy_1);
        Object _namedEObjectOrProxy_2 = this.loader.<Object>getNamedEObjectOrProxy(this.version, "Errors.ProcessErrors", RimPackage.Literals.STATE, true, false);
        T24VersionMapper.processErrorResource = ((State) _namedEObjectOrProxy_2);
        T24VersionMapper.errorsResourceSearched = true;
      }
      State _createCustomeErrorHandlerResource = T24VersionMapper.createCustomeErrorHandlerResource(this.commandFactory, this.entityName);
      T24VersionMapper.customErrorHandler = _createCustomeErrorHandlerResource;
      Event _createGET = this.eventFactory.createGET();
      T24VersionMapper.embedErrorResource(T24VersionMapper.customErrorHandler, _createGET, "errors", null);
      EList<TransitionRef> _transitions = T24VersionMapper.customErrorHandler.getTransitions();
      Event _createPOST = this.eventFactory.createPOST();
      TransitionRef _createTransitionEmbedded = ResourceMapperCommon.createTransitionEmbedded(_createPOST, T24VersionMapper.metadataResource, "metadata", null);
      _transitions.add(_createTransitionEmbedded);
      EList<State> _states = this.rim.getStates();
      _xblockexpression = _states.add(T24VersionMapper.customErrorHandler);
    }
    return _xblockexpression;
  }
  
  public abstract void generate();
  
  public static TransitionRef createContextEnquiryTransition(final ModelLoader loader, final Version version, final String entityName, final EventFactory eventFactory) {
    final HashMap<String,String> parameters = new HashMap<String, String>();
    parameters.put("entity", entityName);
    Event _createGET = eventFactory.createGET();
    return ResourceMapperCommon.createTransition(_createGET, "\"T24.ContextEnquiry.ContextEnquiryList\"", "Context Enquiries", parameters);
  }
  
  public static State createNewResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_new"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant odataRelation = RimFactory.eINSTANCE.createRelationConstant();
    odataRelation.setName(((MappersConstants.MS_ODATA_RELATED + "/") + entityName));
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(odataRelation);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/new");
    EList<RelationRef> _relations_1 = item.getRelations();
    _relations_1.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "CreateEntity";
    if (isAAVersion) {
      commandName = "CreateAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand newCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(newCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", newCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s()/new"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createCopyResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_copy"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/copy");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "CopyEntity";
    if (isAAVersion) {
      commandName = "CopyAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand command = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(command);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", command);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s()/copy"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createDeliveryPreviewResource(final CommandFactory commandFactory, final String entityName, final EventFactory eventFactory) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_deliveryPreview"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/deliveryPreview");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "CreateDEPreview";
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand command = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(command);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", command);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/deliveryPreview"));
    item.setPath(itemPath);
    final HashMap<String,String> params = new HashMap<String, String>();
    params.put("filter", "ContractId eq \'{id}\'");
    EList<TransitionRef> _transitions = item.getTransitions();
    Event _createGET = eventFactory.createGET();
    TransitionRef _createTransitionEmbedded = ResourceMapperCommon.createTransitionEmbedded(_createGET, "T24.enqDePreview.enqDePreviews", "delivery preview enquiry", params);
    _transitions.add(_createTransitionEmbedded);
    return item;
  }
  
  public static State getDealSlipsResource(final CommandFactory commandFactory, final String entityName, final EventFactory eventFactory) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_dealSlips"));
    ResourceType _createCollectionResourceType = ResourceMapperCommon.createCollectionResourceType();
    item.setType(_createCollectionResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity("DealSlip");
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/dealSlip");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "GetDealSlipEntities";
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand command = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(command);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", command);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    return item;
  }
  
  public static State createPasteResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_paste"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/paste");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "PasteEntity";
    if (isAAVersion) {
      commandName = "PasteAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand command = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(command);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", command);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s()/paste"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createInputResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_input"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/input");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "InputEntity";
    if (isAAVersion) {
      commandName = "InputAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand inputCommandForPut = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(inputCommandForPut);
    HashMap<String,String> _hashMap_1 = new HashMap<String, String>();
    final ResourceCommand inputCommandForPost = commandFactory.createResourceCommand(commandName, _hashMap_1);
    EList<ResourceCommand> _actions_1 = itemImpl.getActions();
    _actions_1.add(inputCommandForPost);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("PUT", inputCommandForPut);
    _methods.add(_createMethod);
    EList<MethodRef> _methods_1 = itemImpl.getMethods();
    MethodRef _createMethod_1 = commandFactory.createMethod("POST", inputCommandForPost);
    _methods_1.add(_createMethod_1);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createCustomeErrorHandlerResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_errorHandler"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/error");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand dummyCommand = commandFactory.createResourceCommand("NoopGET", _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(dummyCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", dummyCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/error"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createVerifyResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_verify"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/verify");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "VerifyEntity";
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand verifyCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(verifyCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", verifyCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/verify"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createHoldResource(final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_hold"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/hold");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "HoldEntity";
    if (isAAResource) {
      commandName = "HoldAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand inputCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(inputCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", inputCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/hold"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createAuthoriseResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_authorise"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/authorise");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand authoriseCommand = commandFactory.createResourceCommand("AuthoriseEntity", _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(authoriseCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("PUT", authoriseCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/authorise"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createChangedValuesResource(final CommandFactory commandFactory, final String entityName, final String command) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_changedValues"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/changedValues");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand changeValuesCommand = commandFactory.createResourceCommand(command, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(changeValuesCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", changeValuesCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/changedValues"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createRestoreResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_restore"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/restore");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand authoriseCommand = commandFactory.createResourceCommand("RestoreEntity", _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(authoriseCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", authoriseCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/restore"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createAuditResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_audit"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/review");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand authoriseCommand = commandFactory.createResourceCommand("ReviewEntity", _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(authoriseCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", authoriseCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/review"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createReverseResource(final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_reverse"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/reverse");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "ReverseEntity";
    if (isAAResource) {
      commandName = "ReverseAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand reverseCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(reverseCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("PUT", reverseCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/reverse"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createDeleteResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_delete"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/delete");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand deleteCommand = commandFactory.createResourceCommand("DeleteEntity", _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(deleteCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("DELETE", deleteCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/delete"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createValidateResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_validate"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/validate");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "ValidateEntity";
    if (isAAVersion) {
      commandName = "ValidateAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand validateCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(validateCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", validateCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/validate"));
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
  public static State createSeeResource(final String t24Type, final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    String type = "";
    boolean _notEquals = (!Objects.equal(t24Type, null));
    if (_notEquals) {
      String _lowerCase = t24Type.toLowerCase();
      type = _lowerCase;
    }
    String commandSuffix = "Live";
    String pathAndNameSuffix = "";
    boolean _equals = "inau".equals(type);
    if (_equals) {
      commandSuffix = "Iauth";
      pathAndNameSuffix = "_IAuth";
    } else {
      boolean _equals_1 = "hist".equals(type);
      if (_equals_1) {
        commandSuffix = "Hauth";
        pathAndNameSuffix = "_HAuth";
      }
    }
    final State item = RimFactory.eINSTANCE.createState();
    item.setName(((entityName + pathAndNameSuffix) + "_see"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant relation1 = RimFactory.eINSTANCE.createRelationConstant();
    relation1.setName((("http://schemas.microsoft.com/ado/2007/08/dataservices/related/" + entityName) + pathAndNameSuffix));
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation1);
    final RelationConstant relation2 = RimFactory.eINSTANCE.createRelationConstant();
    relation2.setName("http://temenostech.temenos.com/rels/see");
    EList<RelationRef> _relations_1 = item.getRelations();
    _relations_1.add(relation2);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = (("Get" + commandSuffix) + "Entity");
    if (isAAResource) {
      commandName = (("Get" + commandSuffix) + "AAEntity");
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand seeCommands = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(seeCommands);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", seeCommands);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((((("/" + entityName) + "s") + pathAndNameSuffix) + "(\'{id}\')/see"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createRegularMetadataResourceForAADeals(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_metadata_regular"));
    ResourceType _createCollectionResourceType = ResourceMapperCommon.createCollectionResourceType();
    item.setType(_createCollectionResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity("T24FieldMetadata");
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/metadata");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "T24FieldMetadata";
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand metadataCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(metadataCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", metadataCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s()/regularmetadata"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createMetadataResource(final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_metadata"));
    ResourceType _createCollectionResourceType = ResourceMapperCommon.createCollectionResourceType();
    item.setType(_createCollectionResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity("T24FieldMetadata");
    item.setEntity(_createEntity);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/metadata");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "T24FieldMetadata";
    if (isAAResource) {
      commandName = "AAFieldMetadata";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand metadataCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(metadataCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", metadataCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s()/metadata"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createEntryResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "Entry"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant odataRelation = RimFactory.eINSTANCE.createRelationConstant();
    odataRelation.setName(((MappersConstants.MS_ODATA_RELATED + "/") + entityName));
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(odataRelation);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/contract");
    EList<RelationRef> _relations_1 = item.getRelations();
    _relations_1.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand entryCommand = commandFactory.createResourceCommand("NoopGET", _hashMap);
    itemImpl.setView(entryCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", entryCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "Entry"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createPopulateResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_populate"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant odataRelation = RimFactory.eINSTANCE.createRelationConstant();
    odataRelation.setName(((MappersConstants.MS_ODATA_RELATED + "/") + entityName));
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(odataRelation);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/populate");
    EList<RelationRef> _relations_1 = item.getRelations();
    _relations_1.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "CreateEntity";
    if (isAAVersion) {
      commandName = "CreateAAEntity";
    }
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand populateCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(populateCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", populateCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s()/populate"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createAAPopulateResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_aapopulate"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final RelationConstant odataRelation = RimFactory.eINSTANCE.createRelationConstant();
    odataRelation.setName(((MappersConstants.MS_ODATA_RELATED + "/") + entityName));
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(odataRelation);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/aapopulate");
    EList<RelationRef> _relations_1 = item.getRelations();
    _relations_1.add(relation);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    String commandName = "PopulateAAEntity";
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand newCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(newCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("POST", newCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')/aapopulate"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createAutoResource(final CommandFactory commandFactory, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_autoId"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand checkAutoIdSupportCommand = commandFactory.createResourceCommand("CheckAutoIdSupport", _hashMap);
    itemImpl.setView(checkAutoIdSupportCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", checkAutoIdSupportCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    return item;
  }
  
  public static String buildDefaultListEnquiryStateName(final Application application) {
    String _t24Name = application.getT24Name();
    final String applicationName = EMUtils.camelCaseName(_t24Name);
    Resource.RESOURCE_TYPE _TYPE_ENQLIST = T24ResourceModelsGenerator.TYPE_ENQLIST();
    final String resourceName = (_TYPE_ENQLIST + applicationName);
    String enqListName = (((("T24." + resourceName) + ".") + resourceName) + "s");
    return enqListName;
  }
  
  public static TransitionRef addOkCondition(final TransitionRef transition, final State targetResource) {
    final Expression inputOkUnAuthExpression = RimFactory.eINSTANCE.createExpression();
    final OKFunction inputOkUnAuthFunction = RimFactory.eINSTANCE.createOKFunction();
    inputOkUnAuthFunction.setState(targetResource);
    EList<Function> _expressions = inputOkUnAuthExpression.getExpressions();
    _expressions.add(inputOkUnAuthFunction);
    TransitionSpec _spec = transition.getSpec();
    _spec.setEval(inputOkUnAuthExpression);
    return transition;
  }
  
  /**
   * Create the resource returning all the contextEnquiries'
   * 
   * all the List & Search (Live, Unauth, Hist) + a link to the ContextEnquiries resource
   */
  public static State createContextEnquiriesResource(final Application application, final CommandFactory commandFactory, final EventFactory eventFactory, final String entityName, final String applicationType, final boolean isAAArr) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + "_ContextEnquiries"));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand noopGet = commandFactory.createResourceCommand("NoopGET", _hashMap);
    EList<ResourceCommand> _actions = itemImpl.getActions();
    _actions.add(noopGet);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", noopGet);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "/ContextEnquiries"));
    item.setPath(itemPath);
    String _t24Name = application.getT24Name();
    final String applicationName = EMUtils.camelCaseName(_t24Name);
    Resource.RESOURCE_TYPE _TYPE_ENQLIST = T24ResourceModelsGenerator.TYPE_ENQLIST();
    final String resourceName = (_TYPE_ENQLIST + applicationName);
    String enqListName = ((("T24." + resourceName) + ".") + resourceName);
    final HashMap<String,String> params = new HashMap<String, String>();
    TransitionRef entry = ((TransitionRef) null);
    if ((!isAAArr)) {
      params.put("param", "list");
      Event _createGET = eventFactory.createGET();
      TransitionRef _createTransition = ResourceMapperCommon.createTransition(_createGET, (enqListName + "s"), "List live deals", params);
      entry = _createTransition;
      EList<TransitionRef> _transitions = item.getTransitions();
      _transitions.add(entry);
      boolean _isUnauthFile = ResourceMapperCommon.isUnauthFile(applicationType);
      if (_isUnauthFile) {
        Event _createGET_1 = eventFactory.createGET();
        TransitionRef _createTransition_1 = ResourceMapperCommon.createTransition(_createGET_1, (enqListName + "sUnauth"), "List unauthorised deals", params);
        entry = _createTransition_1;
        EList<TransitionRef> _transitions_1 = item.getTransitions();
        _transitions_1.add(entry);
        boolean _isHistFile = ResourceMapperCommon.isHistFile(applicationType);
        if (_isHistFile) {
          Event _createGET_2 = eventFactory.createGET();
          TransitionRef _createTransition_2 = ResourceMapperCommon.createTransition(_createGET_2, (enqListName + "sHist"), "List history deals", params);
          entry = _createTransition_2;
          EList<TransitionRef> _transitions_2 = item.getTransitions();
          _transitions_2.add(entry);
        }
      }
      Event _createGET_3 = eventFactory.createGET();
      TransitionRef _createTransition_3 = ResourceMapperCommon.createTransition(_createGET_3, (enqListName + "s"), "Search live deals", null);
      entry = _createTransition_3;
      EList<TransitionRef> _transitions_3 = item.getTransitions();
      _transitions_3.add(entry);
      boolean _isUnauthFile_1 = ResourceMapperCommon.isUnauthFile(applicationType);
      if (_isUnauthFile_1) {
        Event _createGET_4 = eventFactory.createGET();
        TransitionRef _createTransition_4 = ResourceMapperCommon.createTransition(_createGET_4, (enqListName + "sUnauth"), "Search unauthorised deals", null);
        entry = _createTransition_4;
        EList<TransitionRef> _transitions_4 = item.getTransitions();
        _transitions_4.add(entry);
        boolean _isHistFile_1 = ResourceMapperCommon.isHistFile(applicationType);
        if (_isHistFile_1) {
          Event _createGET_5 = eventFactory.createGET();
          TransitionRef _createTransition_5 = ResourceMapperCommon.createTransition(_createGET_5, (enqListName + "sHist"), "Search history deals", null);
          entry = _createTransition_5;
          EList<TransitionRef> _transitions_5 = item.getTransitions();
          _transitions_5.add(entry);
        }
      }
    }
    params.clear();
    params.put("entity", entityName);
    Event _createGET_6 = eventFactory.createGET();
    TransitionRef _createTransitionEmbedded = ResourceMapperCommon.createTransitionEmbedded(_createGET_6, "ContextEnquiryList", "Other context Enquiries", params);
    entry = _createTransitionEmbedded;
    EList<TransitionRef> _transitions_6 = item.getTransitions();
    _transitions_6.add(entry);
    return item;
  }
  
  public static void embedErrorResource(final State resource, final Event event, final String title, final Map<String,String> params) {
    boolean _notEquals = (!Objects.equal(T24VersionMapper.processErrorResource, null));
    if (_notEquals) {
      final TransitionRef embeddedErrorTransition = ResourceMapperCommon.createTransitionEmbedded(event, T24VersionMapper.processErrorResource, title, params);
      EList<TransitionRef> _transitions = resource.getTransitions();
      _transitions.add(embeddedErrorTransition);
    }
  }
  
  public static void embedConditionalErrors(final State resource, final Event event, final String title, final Map<String,String> params) {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(T24VersionMapper.checkIfErrorResource, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _notEquals_1 = (!Objects.equal(T24VersionMapper.processErrorResource, null));
      _and = _notEquals_1;
    }
    if (_and) {
      final TransitionRef embeddedErrorTransition = ResourceMapperCommon.createTransitionEmbedded(event, T24VersionMapper.processErrorResource, title, params);
      EList<TransitionRef> _transitions = resource.getTransitions();
      TransitionRef _addOkCondition = T24VersionMapper.addOkCondition(embeddedErrorTransition, T24VersionMapper.checkIfErrorResource);
      _transitions.add(_addOkCondition);
    }
  }
}
