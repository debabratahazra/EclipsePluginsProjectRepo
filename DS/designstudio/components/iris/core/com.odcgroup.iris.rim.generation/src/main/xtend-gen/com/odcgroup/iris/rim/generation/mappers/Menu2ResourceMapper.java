package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.rim.generation.CachedStates;
import com.odcgroup.iris.rim.generation.MappingStatus;
import com.odcgroup.iris.rim.generation.ParameterParser;
import com.odcgroup.iris.rim.generation.ParameterParserResult;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.EventFactory;
import com.odcgroup.iris.rim.generation.mappers.ImportedNamespaceFactory;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.Translation;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.BasePath;
import com.temenos.interaction.rimdsl.rim.Command;
import com.temenos.interaction.rimdsl.rim.DomainDeclaration;
import com.temenos.interaction.rimdsl.rim.DomainModel;
import com.temenos.interaction.rimdsl.rim.Entity;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.ImplRef;
import com.temenos.interaction.rimdsl.rim.Ref;
import com.temenos.interaction.rimdsl.rim.RelationConstant;
import com.temenos.interaction.rimdsl.rim.RelationRef;
import com.temenos.interaction.rimdsl.rim.ResourceCommand;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.ResourceType;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.RimPackage;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.Title;
import com.temenos.interaction.rimdsl.rim.Transition;
import com.temenos.interaction.rimdsl.rim.TransitionEmbedded;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import com.temenos.interaction.rimdsl.rim.TransitionSpec;
import java.util.HashMap;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class Menu2ResourceMapper {
  private final DomainModel domainModel = RimFactory.eINSTANCE.createDomainModel();
  
  private ImportedNamespaceFactory useFactory = null;
  
  private CommandFactory commandFactory = null;
  
  private MenuRoot rootObject = null;
  
  private ModelLoader modelLoader = null;
  
  private Event getEvent = null;
  
  private Event postEvent = null;
  
  private MappingStatus status = null;
  
  private CachedStates cachedStates = null;
  
  public DomainModel mapMenu(final ModelLoader loader, final MenuRoot menu, final String rimName, final MappingStatus s, final CachedStates cache) throws Exception {
    this.rootObject = menu;
    this.modelLoader = loader;
    this.status = s;
    this.cachedStates = cache;
    final DomainDeclaration domain = RimFactory.eINSTANCE.createDomainDeclaration();
    domain.setName("menu");
    EList<Ref> _rims = this.domainModel.getRims();
    _rims.add(domain);
    final EList<Ref> modelReferences = domain.getRims();
    ImportedNamespaceFactory _importedNamespaceFactory = new ImportedNamespaceFactory(modelReferences);
    this.useFactory = _importedNamespaceFactory;
    this.useFactory.createUse("common.CoreCommands.*");
    this.useFactory.createUse("common.HTTPEvents.*");
    this.useFactory.createUse("common.ODataCommands.*");
    this.useFactory.createUse("common.T24Commands.*");
    this.useFactory.createUse("common.T24Events.*");
    final ResourceInteractionModel rim = RimFactory.eINSTANCE.createResourceInteractionModel();
    rim.setName(rimName);
    modelReferences.add(rim);
    final BasePath basePath = RimFactory.eINSTANCE.createBasePath();
    basePath.setName("/{companyid}");
    rim.setBasepath(basePath);
    final EventFactory eventFactory = new EventFactory(loader, this.rootObject);
    EList<Command> _commands = rim.getCommands();
    CommandFactory _commandFactory = new CommandFactory(_commands);
    this.commandFactory = _commandFactory;
    Object _namedEObjectOrProxy = this.modelLoader.<Object>getNamedEObjectOrProxy(this.rootObject, "common.HTTPEvents.GET", RimPackage.Literals.EVENT, false, false);
    this.getEvent = ((Event) _namedEObjectOrProxy);
    boolean _equals = Objects.equal(this.getEvent, null);
    if (_equals) {
      Event _createGET = eventFactory.createGET();
      this.getEvent = _createGET;
    }
    Object _namedEObjectOrProxy_1 = this.modelLoader.<Object>getNamedEObjectOrProxy(this.rootObject, "common.HTTPEvents.POST", RimPackage.Literals.EVENT, false, false);
    this.postEvent = ((Event) _namedEObjectOrProxy_1);
    boolean _equals_1 = Objects.equal(this.postEvent, null);
    if (_equals_1) {
      Event _createPOST = eventFactory.createPOST();
      this.postEvent = _createPOST;
    }
    final EList<State> resources = rim.getStates();
    final State noopLeaf = RimFactory.eINSTANCE.createState();
    noopLeaf.setName("MenuErrors");
    ResourceType _createCollectionResourceType = this.createCollectionResourceType();
    noopLeaf.setType(_createCollectionResourceType);
    Entity _createEntity = this.createEntity("Errors");
    noopLeaf.setEntity(_createEntity);
    final ImplRef collectionImpl = RimFactory.eINSTANCE.createImplRef();
    final HashMap<String,String> properties = new HashMap<String, String>();
    ResourceCommand _createResourceCommand = this.commandFactory.createResourceCommand("NoopGET", properties);
    collectionImpl.setView(_createResourceCommand);
    noopLeaf.setImpl(collectionImpl);
    resources.add(noopLeaf);
    MenuItem _rootItem = menu.getRootItem();
    final State top = this.createBranchOrLeaf(noopLeaf, resources, _rootItem);
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(top, null));
    if (!_notEquals) {
      _and = false;
    } else {
      int _status = this.status.getStatus();
      boolean _equals_2 = (_status == 0);
      _and = _equals_2;
    }
    if (_and) {
      MenuItem _rootItem_1 = menu.getRootItem();
      String _name = _rootItem_1.getName();
      this.cachedStates.addKnownState(_name, top);
    }
    return this.domainModel;
  }
  
  public State createBranchOrLeaf(final State dummyLeaf, final EList<State> resource, final MenuItem item) {
    EList<MenuItem> _submenus = item.getSubmenus();
    int _size = _submenus.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      return this.createBranch(dummyLeaf, resource, item);
    } else {
      final State leaf = this.createLeaf(dummyLeaf, resource, item);
      final State oneBranch = RimFactory.eINSTANCE.createState();
      final String parameters = item.getParameters();
      Resource.RESOURCE_TYPE _TYPE_MENU = T24ResourceModelsGenerator.TYPE_MENU();
      String _name = item.getName();
      ParameterParserResult _resourceName = ParameterParser.getResourceName(_TYPE_MENU, _name, parameters);
      final String oneBranchRimName = _resourceName.getResourceName();
      oneBranch.setName(oneBranchRimName);
      ResourceType _createItemResourceType = this.createItemResourceType();
      oneBranch.setType(_createItemResourceType);
      Entity _createEntity = this.createEntity("Menu");
      oneBranch.setEntity(_createEntity);
      final ImplRef branchImpl = RimFactory.eINSTANCE.createImplRef();
      HashMap<String,String> _hashMap = new HashMap<String, String>();
      ResourceCommand _createResourceCommand = this.commandFactory.createResourceCommand("GETMenu", _hashMap);
      branchImpl.setView(_createResourceCommand);
      oneBranch.setImpl(branchImpl);
      final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
      relation.setName("http://www.temenos.com/rels/menu");
      EList<RelationRef> _relations = oneBranch.getRelations();
      _relations.add(relation);
      resource.add(oneBranch);
      final EList<TransitionRef> pointer = oneBranch.getTransitions();
      TransitionRef _createGetPointer = this.createGetPointer(leaf, item);
      pointer.add(_createGetPointer);
      return oneBranch;
    }
  }
  
  public State createBranch(final State noopLeaf, final EList<State> resource, final MenuItem item) {
    final State oneBranch = RimFactory.eINSTANCE.createState();
    Resource.RESOURCE_TYPE _TYPE_MENU = T24ResourceModelsGenerator.TYPE_MENU();
    String _name = item.getName();
    ParameterParserResult _resourceName = ParameterParser.getResourceName(_TYPE_MENU, _name, null);
    final String oneBranchRimName = _resourceName.getResourceName();
    oneBranch.setName(oneBranchRimName);
    ResourceType _createItemResourceType = this.createItemResourceType();
    oneBranch.setType(_createItemResourceType);
    Entity _createEntity = this.createEntity("Menu");
    oneBranch.setEntity(_createEntity);
    final ImplRef branchImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    ResourceCommand _createResourceCommand = this.commandFactory.createResourceCommand("GETMenu", _hashMap);
    branchImpl.setView(_createResourceCommand);
    oneBranch.setImpl(branchImpl);
    final RelationConstant relation = RimFactory.eINSTANCE.createRelationConstant();
    relation.setName("http://www.temenos.com/rels/menu");
    EList<RelationRef> _relations = oneBranch.getRelations();
    _relations.add(relation);
    resource.add(oneBranch);
    EList<MenuItem> _submenus = item.getSubmenus();
    for (final MenuItem child : _submenus) {
      {
        final EList<TransitionRef> pointer = oneBranch.getTransitions();
        final HashMap<String,String> parameters = new HashMap<String, String>();
        String title = this.getFirstLabel(child);
        parameters.put("title", title);
        EList<MenuItem> _submenus_1 = child.getSubmenus();
        int _size = _submenus_1.size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          State _createBranch = this.createBranch(noopLeaf, resource, child);
          TransitionRef _createGetPointerEmbedded = this.createGetPointerEmbedded(_createBranch, child);
          pointer.add(_createGetPointerEmbedded);
        } else {
          State _createLeaf = this.createLeaf(noopLeaf, resource, child);
          TransitionRef _createGetPointer = this.createGetPointer(_createLeaf, child);
          pointer.add(_createGetPointer);
        }
      }
    }
    return oneBranch;
  }
  
  public State createLeaf(final State noopLeaf, final EList<State> resource, final MenuItem item) {
    String t24Name = "";
    String packageName = T24ResourceModelsGenerator.getDomain(null);
    Resource.RESOURCE_TYPE resourceType = T24ResourceModelsGenerator.TYPE_UNKNOWN();
    final Version versionItem = item.getVersion();
    boolean _notEquals = (!Objects.equal(versionItem, null));
    if (_notEquals) {
      String _t24Name = versionItem.getT24Name();
      t24Name = _t24Name;
      Resource.RESOURCE_TYPE _TYPE_VERSION = T24ResourceModelsGenerator.TYPE_VERSION();
      resourceType = _TYPE_VERSION;
      String _string = resourceType.toString();
      String _plus = ((packageName + ".") + _string);
      String _camelCaseName = EMUtils.camelCaseName(t24Name);
      String _plus_1 = (_plus + _camelCaseName);
      packageName = _plus_1;
    } else {
      final CompositeScreen compositeItem = item.getCompositeScreen();
      boolean _notEquals_1 = (!Objects.equal(compositeItem, null));
      if (_notEquals_1) {
        String _name = compositeItem.getName();
        t24Name = _name;
        Resource.RESOURCE_TYPE _TYPE_COMPOSITE = T24ResourceModelsGenerator.TYPE_COMPOSITE();
        resourceType = _TYPE_COMPOSITE;
      } else {
        final Enquiry enquiryItem = item.getEnquiry();
        boolean _notEquals_2 = (!Objects.equal(enquiryItem, null));
        if (_notEquals_2) {
          String _name_1 = enquiryItem.getName();
          t24Name = _name_1;
          Resource.RESOURCE_TYPE _TYPE_ENQUIRY = T24ResourceModelsGenerator.TYPE_ENQUIRY();
          resourceType = _TYPE_ENQUIRY;
        } else {
          final MenuRoot menuItem = item.getIncludedMenu();
          boolean _notEquals_3 = (!Objects.equal(menuItem, null));
          if (_notEquals_3) {
            MenuItem _rootItem = menuItem.getRootItem();
            boolean _notEquals_4 = (!Objects.equal(_rootItem, null));
            if (_notEquals_4) {
              MenuItem _rootItem_1 = menuItem.getRootItem();
              String _name_2 = _rootItem_1.getName();
              t24Name = _name_2;
              Resource.RESOURCE_TYPE _TYPE_MENU = T24ResourceModelsGenerator.TYPE_MENU();
              resourceType = _TYPE_MENU;
              String _string_1 = resourceType.toString();
              String _plus_2 = ((packageName + ".") + _string_1);
              String _camelCaseName_1 = EMUtils.camelCaseName(t24Name);
              String _plus_3 = (_plus_2 + _camelCaseName_1);
              packageName = _plus_3;
            } else {
              t24Name = "unknown";
            }
          }
        }
      }
    }
    boolean _or = false;
    boolean _equals = Objects.equal(t24Name, null);
    if (_equals) {
      _or = true;
    } else {
      int _length = t24Name.length();
      boolean _equals_1 = (_length == 0);
      _or = _equals_1;
    }
    if (_or) {
      Resource.RESOURCE_TYPE _TYPE_UNKNOWN = T24ResourceModelsGenerator.TYPE_UNKNOWN();
      resourceType = _TYPE_UNKNOWN;
      t24Name = "InteractionException";
      packageName = "hothouse.Hothouse";
    }
    final String parameters = item.getParameters();
    ParameterParserResult _resourceName = ParameterParser.getResourceName(resourceType, t24Name, parameters);
    String resourceName = _resourceName.getResourceName();
    int _length_1 = packageName.length();
    boolean _greaterThan = (_length_1 > 0);
    if (_greaterThan) {
      resourceName = ((packageName + ".") + resourceName);
    }
    final State menuLeaf = RimFactory.eINSTANCE.createState();
    menuLeaf.setName(resourceName);
    return menuLeaf;
  }
  
  public String getFirstLabel(final MenuItem item) {
    boolean _and = false;
    boolean _and_1 = false;
    EList<Translation> _labels = item.getLabels();
    boolean _notEquals = (!Objects.equal(_labels, null));
    if (!_notEquals) {
      _and_1 = false;
    } else {
      EList<Translation> _labels_1 = item.getLabels();
      int _size = _labels_1.size();
      boolean _greaterThan = (_size > 0);
      _and_1 = _greaterThan;
    }
    if (!_and_1) {
      _and = false;
    } else {
      EList<Translation> _labels_2 = item.getLabels();
      Translation _get = _labels_2.get(0);
      String _message = _get.getMessage();
      boolean _notEquals_1 = (!Objects.equal(_message, null));
      _and = _notEquals_1;
    }
    if (_and) {
      EList<Translation> _labels_3 = item.getLabels();
      Translation _get_1 = _labels_3.get(0);
      String _message_1 = _get_1.getMessage();
      final String title = _message_1.replace("\"", "\'");
      return title;
    } else {
      return item.getName();
    }
  }
  
  public ResourceType createItemResourceType() {
    final ResourceType resourceType = RimFactory.eINSTANCE.createResourceType();
    resourceType.setIsItem(true);
    return resourceType;
  }
  
  public Entity createEntity(final String entityName) {
    final Entity entity = RimFactory.eINSTANCE.createEntity();
    entity.setName(entityName);
    return entity;
  }
  
  /**
   * For menu only.
   */
  public TransitionRef createGetPointerEmbedded(final State resources, final MenuItem item) {
    final String sTitle = this.getFirstLabel(item);
    final TransitionEmbedded transition = RimFactory.eINSTANCE.createTransitionEmbedded();
    transition.setEvent(this.getEvent);
    transition.setState(resources);
    final TransitionSpec spec = RimFactory.eINSTANCE.createTransitionSpec();
    final Title title = RimFactory.eINSTANCE.createTitle();
    title.setName(sTitle);
    spec.setTitle(title);
    transition.setSpec(spec);
    return transition;
  }
  
  public TransitionRef createGetPointer(final State resources, final MenuItem item) {
    final String rName = resources.getName();
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(rName, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _startsWith = rName.startsWith("mnu");
      _and = _startsWith;
    }
    if (_and) {
      return this.createGetPointerEmbedded(resources, item);
    }
    final String sTitle = this.getFirstLabel(item);
    final Transition transition = RimFactory.eINSTANCE.createTransition();
    boolean _endsWith = rName.endsWith("_new");
    if (_endsWith) {
      transition.setEvent(this.postEvent);
    } else {
      transition.setEvent(this.getEvent);
    }
    transition.setState(resources);
    final TransitionSpec spec = RimFactory.eINSTANCE.createTransitionSpec();
    final Title title = RimFactory.eINSTANCE.createTitle();
    title.setName(sTitle);
    spec.setTitle(title);
    return transition;
  }
  
  public ResourceType createCollectionResourceType() {
    final ResourceType resourceType = RimFactory.eINSTANCE.createResourceType();
    resourceType.setIsCollection(true);
    return resourceType;
  }
}
