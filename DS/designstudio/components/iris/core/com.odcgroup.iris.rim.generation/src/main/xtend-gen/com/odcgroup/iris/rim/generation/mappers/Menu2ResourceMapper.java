package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.rim.generation.CachedStates;
import com.odcgroup.iris.rim.generation.MappingStatus;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.ImportedNamespaceFactory;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.Translation;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class Menu2ResourceMapper {
  private final Object domainModel /* Skipped initializer because of errors */;
  
  private ImportedNamespaceFactory useFactory = null;
  
  private CommandFactory commandFactory = null;
  
  private MenuRoot rootObject = null;
  
  private ModelLoader modelLoader = null;
  
  private /* Event */Object getEvent = null;
  
  private /* Event */Object postEvent = null;
  
  private MappingStatus status = null;
  
  private CachedStates cachedStates = null;
  
  public /* DomainModel */Object mapMenu(final ModelLoader loader, final MenuRoot menu, final String rimName, final MappingStatus s, final CachedStates cache) throws Exception {
    throw new Error("Unresolved compilation problems:"
      + "\nEvent cannot be resolved to a type."
      + "\nEvent cannot be resolved to a type."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nImportedNamespaceFactory cannot be resolved."
      + "\nThe method createUse is undefined for the type Menu2ResourceMapper"
      + "\nThe method createUse is undefined for the type Menu2ResourceMapper"
      + "\nThe method createUse is undefined for the type Menu2ResourceMapper"
      + "\nThe method createUse is undefined for the type Menu2ResourceMapper"
      + "\nThe method createUse is undefined for the type Menu2ResourceMapper"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nCommandFactory cannot be resolved."
      + "\nRimPackage$Literals cannot be resolved to a type."
      + "\nThe method createGET is undefined for the type Menu2ResourceMapper"
      + "\nRimPackage$Literals cannot be resolved to a type."
      + "\nThe method createPOST is undefined for the type Menu2ResourceMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type Menu2ResourceMapper"
      + "\nThe method addKnownState is undefined for the type Menu2ResourceMapper"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateDomainDeclaration cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrims cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nrims cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateResourceInteractionModel cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateBasePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetBasepath cannot be resolved"
      + "\ncommands cannot be resolved"
      + "\nEVENT cannot be resolved"
      + "\n== cannot be resolved"
      + "\nEVENT cannot be resolved"
      + "\n== cannot be resolved"
      + "\nstates cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nsetView cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\nadd cannot be resolved"
      + "\n!= cannot be resolved"
      + "\n&& cannot be resolved");
  }
  
  public /* State */Object createBranchOrLeaf(final /* State */Object dummyLeaf, final /* EList<State> */Object resource, final MenuItem item) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type Menu2ResourceMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nsetView cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetTransitions cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public /* State */Object createBranch(final /* State */Object noopLeaf, final /* EList<State> */Object resource, final MenuItem item) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type Menu2ResourceMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nsetView cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetTransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public /* State */Object createLeaf(final /* State */Object noopLeaf, final /* EList<State> */Object resource, final MenuItem item) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved");
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
  
  public /* ResourceType */Object createItemResourceType() {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateResourceType cannot be resolved"
      + "\nsetIsItem cannot be resolved");
  }
  
  public /* Entity */Object createEntity(final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateEntity cannot be resolved"
      + "\nsetName cannot be resolved");
  }
  
  /**
   * For menu only.
   */
  public /* TransitionRef */Object createGetPointerEmbedded(final /* State */Object resources, final MenuItem item) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateTransitionEmbedded cannot be resolved"
      + "\nsetEvent cannot be resolved"
      + "\nsetState cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateTransitionSpec cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateTitle cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetTitle cannot be resolved"
      + "\nsetSpec cannot be resolved");
  }
  
  public /* TransitionRef */Object createGetPointer(final /* State */Object resources, final MenuItem item) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\ngetName cannot be resolved"
      + "\n!= cannot be resolved"
      + "\n&& cannot be resolved"
      + "\nstartsWith cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateTransition cannot be resolved"
      + "\nendsWith cannot be resolved"
      + "\nsetEvent cannot be resolved"
      + "\nsetEvent cannot be resolved"
      + "\nsetState cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateTransitionSpec cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateTitle cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetTitle cannot be resolved");
  }
  
  public /* ResourceType */Object createCollectionResourceType() {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateResourceType cannot be resolved"
      + "\nsetIsCollection cannot be resolved");
  }
}
