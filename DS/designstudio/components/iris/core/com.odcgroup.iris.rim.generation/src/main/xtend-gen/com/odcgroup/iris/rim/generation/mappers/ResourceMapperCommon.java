package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.ImportedNamespaceFactory;
import com.odcgroup.iris.rim.generation.mappers.MappersConstants;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTermType;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.temenos.interaction.rimdsl.rim.Entity;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.ImplRef;
import com.temenos.interaction.rimdsl.rim.LinkId;
import com.temenos.interaction.rimdsl.rim.MethodRef;
import com.temenos.interaction.rimdsl.rim.Path;
import com.temenos.interaction.rimdsl.rim.Ref;
import com.temenos.interaction.rimdsl.rim.RelationConstant;
import com.temenos.interaction.rimdsl.rim.RelationRef;
import com.temenos.interaction.rimdsl.rim.ResourceCommand;
import com.temenos.interaction.rimdsl.rim.ResourceType;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.Title;
import com.temenos.interaction.rimdsl.rim.Transition;
import com.temenos.interaction.rimdsl.rim.TransitionAuto;
import com.temenos.interaction.rimdsl.rim.TransitionEmbedded;
import com.temenos.interaction.rimdsl.rim.TransitionForEach;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import com.temenos.interaction.rimdsl.rim.TransitionSpec;
import com.temenos.interaction.rimdsl.rim.UriLink;
import com.temenos.interaction.rimdsl.rim.UriLinkage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;

/**
 * This class contains the methods that are common to various rim generation mappers.
 */
@SuppressWarnings("all")
public class ResourceMapperCommon {
  public static void addUsedDomains(final EList<Ref> modelReferences) {
    final ImportedNamespaceFactory useFactory = new ImportedNamespaceFactory(modelReferences);
    useFactory.createUse("common.CoreCommands.*");
    useFactory.createUse("common.HTTPEvents.*");
    useFactory.createUse("common.ODataCommands.*");
    useFactory.createUse("common.T24Commands.*");
    useFactory.createUse("common.T24Events.*");
    useFactory.createUse("common.NextState.*");
  }
  
  public static void addUsedVersionDomains(final EList<Ref> modelReferences) {
    final ImportedNamespaceFactory useFactory = new ImportedNamespaceFactory(modelReferences);
    useFactory.createUse("common.CoreCommands.*");
    useFactory.createUse("common.HTTPEvents.*");
    useFactory.createUse("common.ODataCommands.*");
    useFactory.createUse("common.T24Commands.*");
    useFactory.createUse("common.T24Events.*");
    useFactory.createUse("common.NextState.*");
    useFactory.createUse("common.Errors.*");
  }
  
  public static ResourceType createCollectionResourceType() {
    final ResourceType resourceType = RimFactory.eINSTANCE.createResourceType();
    resourceType.setIsCollection(true);
    return resourceType;
  }
  
  public static Entity createEntity(final String entityName) {
    final Entity entity = RimFactory.eINSTANCE.createEntity();
    entity.setName(entityName);
    return entity;
  }
  
  public static TransitionRef createTransitionForEach(final Event event, final State target, final String titleStr, final Map<String,String> parameters) {
    final TransitionForEach transition = RimFactory.eINSTANCE.createTransitionForEach();
    return ResourceMapperCommon.buildTransition(transition, event, target, titleStr, parameters);
  }
  
  public static TransitionRef buildTransition(final TransitionRef transition, final Event event, final State target, final String titleStr, final Map<String,String> parameters) {
    transition.setEvent(event);
    transition.setState(target);
    return ResourceMapperCommon.buildTransitionSpec(transition, titleStr, parameters);
  }
  
  public static TransitionRef buildTransitionSpec(final TransitionRef transition, final String titleStr, final Map<String,String> parameters) {
    return ResourceMapperCommon.buildTransitionSpec(transition, titleStr, parameters, null);
  }
  
  public static TransitionRef buildTransitionSpec(final TransitionRef transition, final String titleStr, final Map<String,String> parameters, final String linkIdStr) {
    final TransitionSpec spec = RimFactory.eINSTANCE.createTransitionSpec();
    boolean _notEquals = (!Objects.equal(titleStr, null));
    if (_notEquals) {
      final Title title = RimFactory.eINSTANCE.createTitle();
      final String titleStrCorrected = titleStr.replace("\\", "\\\\");
      title.setName(titleStrCorrected);
      spec.setTitle(title);
    }
    boolean _and = false;
    boolean _notEquals_1 = (!Objects.equal(linkIdStr, null));
    if (!_notEquals_1) {
      _and = false;
    } else {
      int _length = linkIdStr.length();
      boolean _greaterThan = (_length > 0);
      _and = _greaterThan;
    }
    if (_and) {
      final LinkId linkId = RimFactory.eINSTANCE.createLinkId();
      linkId.setName(linkIdStr);
      spec.setId(linkId);
    }
    boolean _notEquals_2 = (!Objects.equal(parameters, null));
    if (_notEquals_2) {
      Set<String> _keySet = parameters.keySet();
      for (final String paramkey : _keySet) {
        {
          final UriLink uriParameter = RimFactory.eINSTANCE.createUriLink();
          uriParameter.setTemplateProperty(paramkey);
          final UriLinkage entityProperty = RimFactory.eINSTANCE.createUriLinkage();
          String _get = parameters.get(paramkey);
          entityProperty.setName(_get);
          uriParameter.setEntityProperty(entityProperty);
          EList<UriLink> _uriLinks = spec.getUriLinks();
          _uriLinks.add(uriParameter);
        }
      }
    }
    transition.setSpec(spec);
    return transition;
  }
  
  public static State createEnrichmentIemResource(final CommandFactory commandFactory, final String entityName, final String resourceName, final String resourceSuffix) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((resourceName + resourceSuffix));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity("Enrichment");
    item.setEntity(_createEntity);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    final HashMap<String,String> properties = new HashMap<String, String>();
    properties.put("entity", entityName);
    final ResourceCommand itemCommand = commandFactory.createResourceCommand("T24Enrichment", properties);
    itemImpl.setView(itemCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", itemCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://temenostech.temenos.com/rels/enrichment");
    EList<RelationRef> _relations = item.getRelations();
    _relations.add(relation);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + resourceName) + "()/enrichment"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createCollectionResource(final CommandFactory commandFactory, final String entityName, final String resourceName, final String commandName) {
    final State collection = RimFactory.eINSTANCE.createState();
    collection.setName(resourceName);
    ResourceType _createCollectionResourceType = ResourceMapperCommon.createCollectionResourceType();
    collection.setType(_createCollectionResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    collection.setEntity(_createEntity);
    final ImplRef collectionImpl = RimFactory.eINSTANCE.createImplRef();
    final HashMap<String,String> properties = new HashMap<String, String>();
    properties.put("filter", "{filter}");
    final ResourceCommand collectionCommand = commandFactory.createResourceCommand(commandName, properties);
    collectionImpl.setView(collectionCommand);
    EList<MethodRef> _methods = collectionImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", collectionCommand);
    _methods.add(_createMethod);
    collection.setImpl(collectionImpl);
    final Path collectionPath = RimFactory.eINSTANCE.createPath();
    collectionPath.setName((("/" + resourceName) + "()"));
    collection.setPath(collectionPath);
    return collection;
  }
  
  public static State createEnquiryItemResource(final CommandFactory commandFactory, final String entityName) {
    return ResourceMapperCommon.createItemResource(commandFactory, "GETEntity", entityName);
  }
  
  public static State createDomainItemResource(final CommandFactory commandFactory, final String entityName) {
    return ResourceMapperCommon.createItemResource(commandFactory, "GETEntity", entityName);
  }
  
  public static State createVersionItemResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    String commandName = "GetLiveEntity";
    if (isAAVersion) {
      commandName = "GetLiveAAEntity";
    }
    return ResourceMapperCommon.createItemResource(commandFactory, commandName, entityName);
  }
  
  public static State createCollectionResource(final CommandFactory commandFactory, final String entityName, final String resourceName) {
    return ResourceMapperCommon.createCollectionResource(commandFactory, entityName, resourceName, "GETEntities");
  }
  
  public static State createUnauthCollectionResource(final CommandFactory commandFactory, final String entityName, final String resourceIdentifier, final String command) {
    final State unauthCollection = RimFactory.eINSTANCE.createState();
    final String resourceName = ((entityName + "s") + resourceIdentifier);
    unauthCollection.setName(resourceName);
    ResourceType _createCollectionResourceType = ResourceMapperCommon.createCollectionResourceType();
    unauthCollection.setType(_createCollectionResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    unauthCollection.setEntity(_createEntity);
    final ImplRef collectionImpl = RimFactory.eINSTANCE.createImplRef();
    final HashMap<String,String> properties = new HashMap<String, String>();
    properties.put("filter", "{filter}");
    final ResourceCommand unauthCollectionCommand = commandFactory.createResourceCommand(command, properties);
    collectionImpl.setView(unauthCollectionCommand);
    EList<MethodRef> _methods = collectionImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", unauthCollectionCommand);
    _methods.add(_createMethod);
    unauthCollection.setImpl(collectionImpl);
    final Path collectionPath = RimFactory.eINSTANCE.createPath();
    collectionPath.setName((("/" + resourceName) + "()"));
    unauthCollection.setPath(collectionPath);
    return unauthCollection;
  }
  
  public static State createItemResource(final CommandFactory commandFactory, final String commandName, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName(entityName);
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand itemCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    itemImpl.setView(itemCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", itemCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createItemResource(final CommandFactory commandFactory, final String entityName) {
    return ResourceMapperCommon.createItemResource(commandFactory, "GETEntity", entityName);
  }
  
  public static State createDefaultListCollectionResource(final CommandFactory commandFactory, final String entityName, final String resourceIdentifier, final String command) {
    final State collection = RimFactory.eINSTANCE.createState();
    final String resourceName = ((entityName + "s") + resourceIdentifier);
    collection.setName(resourceName);
    ResourceType _createCollectionResourceType = ResourceMapperCommon.createCollectionResourceType();
    collection.setType(_createCollectionResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity((entityName + resourceIdentifier));
    collection.setEntity(_createEntity);
    final ImplRef collectionImpl = RimFactory.eINSTANCE.createImplRef();
    final HashMap<String,String> properties = new HashMap<String, String>();
    properties.put("filter", "{filter}");
    final ResourceCommand collectionCommand = commandFactory.createResourceCommand(command, properties);
    collectionImpl.setView(collectionCommand);
    EList<MethodRef> _methods = collectionImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", collectionCommand);
    _methods.add(_createMethod);
    collection.setImpl(collectionImpl);
    final Path collectionPath = RimFactory.eINSTANCE.createPath();
    collectionPath.setName((("/" + resourceName) + "()"));
    collection.setPath(collectionPath);
    return collection;
  }
  
  public static State createDomainEnqListItemResource(final CommandFactory commandFactory, final String entityName) {
    return ResourceMapperCommon.createItemResource(commandFactory, "GETRODBEntity", entityName);
  }
  
  public static State createUnAuthItemResource(final CommandFactory commandFactory, final String entityName, final String resourceIdentifier, final String command) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + resourceIdentifier));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand unauthItemCommand = commandFactory.createResourceCommand(command, _hashMap);
    itemImpl.setView(unauthItemCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", unauthItemCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((((("/" + entityName) + "s") + resourceIdentifier) + "(\'{id}\')"));
    item.setPath(itemPath);
    return item;
  }
  
  public static State createNextStateItemResource(final CommandFactory commandFactory) {
    final State nextStateItem = RimFactory.eINSTANCE.createState();
    nextStateItem.setName("nextState");
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    nextStateItem.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity("NextState");
    nextStateItem.setEntity(_createEntity);
    final ImplRef nextStateItemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand nextStateCommand = commandFactory.createResourceCommand("T24NextState", _hashMap);
    nextStateItemImpl.setView(nextStateCommand);
    EList<MethodRef> _methods = nextStateItemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", nextStateCommand);
    _methods.add(_createMethod);
    nextStateItem.setImpl(nextStateItemImpl);
    final Path nextStateItemPath = RimFactory.eINSTANCE.createPath();
    nextStateItemPath.setName("/verCustomer_Inputs/next");
    nextStateItem.setPath(nextStateItemPath);
    return nextStateItem;
  }
  
  public static State createDefaultListItemResource(final CommandFactory commandFactory, final String entityName, final String resourceIdentifier, final String command) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName((entityName + resourceIdentifier));
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity((entityName + resourceIdentifier));
    item.setEntity(_createEntity);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand itemCommand = commandFactory.createResourceCommand(command, _hashMap);
    itemImpl.setView(itemCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", itemCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((((("/" + entityName) + "s") + resourceIdentifier) + "(\'{id}\')"));
    item.setPath(itemPath);
    return item;
  }
  
  public static ResourceType createItemResourceType() {
    final ResourceType resourceType = RimFactory.eINSTANCE.createResourceType();
    resourceType.setIsItem(true);
    return resourceType;
  }
  
  public static Map<String,String> buildTransitionParametersItem(final EMEntity emEntity) {
    final HashMap<String,String> parameters = new HashMap<String, String>();
    List<EMProperty> _properties = emEntity.getProperties();
    for (final EMProperty property : _properties) {
      boolean _hasVocabularyTerm = property.hasVocabularyTerm(EMTermType.ID_TERM);
      if (_hasVocabularyTerm) {
        String _name = property.getName();
        String _camelCaseName = EMUtils.camelCaseName(_name);
        String _plus = ("{" + _camelCaseName);
        String _plus_1 = (_plus + "}");
        parameters.put("id", _plus_1);
      }
    }
    return parameters;
  }
  
  public static TransitionRef createTransitionForEach(final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters) {
    return ResourceMapperCommon.createTransitionForEach(event, targetStr, titleStr, parameters, null);
  }
  
  public static TransitionRef createTransitionForEach(final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters, final String linkIdStr) {
    final TransitionForEach transition = RimFactory.eINSTANCE.createTransitionForEach();
    return ResourceMapperCommon.buildTransition(transition, event, targetStr, titleStr, parameters, linkIdStr);
  }
  
  public static TransitionRef createTransitionEmbedded(final Event event, final State target, final String titleStr, final Map<String,String> parameters) {
    final TransitionEmbedded transition = RimFactory.eINSTANCE.createTransitionEmbedded();
    return ResourceMapperCommon.buildTransition(transition, event, target, titleStr, parameters);
  }
  
  public static TransitionRef createTransitionEmbedded(final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters) {
    final TransitionEmbedded transition = RimFactory.eINSTANCE.createTransitionEmbedded();
    return ResourceMapperCommon.buildTransition(transition, event, targetStr, titleStr, parameters);
  }
  
  public static TransitionRef createTransitionAuto(final Event event, final State target, final String titleStr, final Map<String,String> parameters) {
    final TransitionAuto transition = RimFactory.eINSTANCE.createTransitionAuto();
    return ResourceMapperCommon.buildTransition(transition, event, target, titleStr, parameters);
  }
  
  public static TransitionRef createTransitionAuto(final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters) {
    final TransitionAuto transition = RimFactory.eINSTANCE.createTransitionAuto();
    return ResourceMapperCommon.buildTransition(transition, event, targetStr, titleStr, parameters);
  }
  
  public static TransitionRef createTransition(final Event event, final State target, final String titleStr, final Map<String,String> parameters) {
    final Transition transition = RimFactory.eINSTANCE.createTransition();
    return ResourceMapperCommon.buildTransition(transition, event, target, titleStr, parameters);
  }
  
  public static TransitionRef createTransition(final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters) {
    final Transition transition = RimFactory.eINSTANCE.createTransition();
    return ResourceMapperCommon.buildTransition(transition, event, targetStr, titleStr, parameters);
  }
  
  public static TransitionRef buildTransition(final TransitionRef transition, final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters) {
    return ResourceMapperCommon.buildTransition(transition, event, targetStr, titleStr, parameters, null);
  }
  
  public static TransitionRef buildTransition(final TransitionRef transition, final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters, final String linkIdStr) {
    transition.setEvent(event);
    transition.setName(targetStr);
    return ResourceMapperCommon.buildTransitionSpec(transition, titleStr, parameters, linkIdStr);
  }
  
  public static boolean isHistFile(final String applicationType) {
    return MappersConstants.APPLICATION_TYPE_HIST.equals(applicationType);
  }
  
  public static boolean isUnauthFile(final String applicationType) {
    boolean _or = false;
    boolean _or_1 = false;
    boolean _equals = MappersConstants.APPLICATION_TYPE_UNAUTH.equals(applicationType);
    if (_equals) {
      _or_1 = true;
    } else {
      boolean _equals_1 = MappersConstants.APPLICATION_TYPE_HIST.equals(applicationType);
      _or_1 = _equals_1;
    }
    if (_or_1) {
      _or = true;
    } else {
      boolean _equals_2 = MappersConstants.APPLICATION_TYPE_DYNAMIC.equals(applicationType);
      _or = _equals_2;
    }
    if (_or) {
      return true;
    }
    return false;
  }
  
  public static boolean isVerifiable(final String applicationType) {
    boolean _equals = MappersConstants.APPLICATION_TYPE_VERIFY.equals(applicationType);
    if (_equals) {
      return true;
    }
    return false;
  }
  
  public static boolean isOnlyLiveFile(final String applicationType) {
    boolean _equals = MappersConstants.APPLICATION_TYPE_LIVE.equals(applicationType);
    if (_equals) {
      return true;
    }
    return false;
  }
}
