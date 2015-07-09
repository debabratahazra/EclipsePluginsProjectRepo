package com.odcgroup.iris.rim.generation.mappers

import com.odcgroup.iris.rim.generation.CachedStates
import com.odcgroup.iris.rim.generation.MappingStatus
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator
import com.odcgroup.iris.rim.generation.ParameterParser
import com.odcgroup.t24.menu.menu.MenuItem
import com.odcgroup.t24.menu.menu.MenuRoot
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader
import com.temenos.interaction.rimdsl.rim.DomainModel
import com.temenos.interaction.rimdsl.rim.Entity
import com.temenos.interaction.rimdsl.rim.Event
import com.temenos.interaction.rimdsl.rim.ResourceType
import com.temenos.interaction.rimdsl.rim.RimFactory
import com.temenos.interaction.rimdsl.rim.RimPackage$Literals
import com.temenos.interaction.rimdsl.rim.State
import com.temenos.interaction.rimdsl.rim.TransitionRef
import java.util.HashMap
import org.eclipse.emf.common.util.EList
import com.odcgroup.t24.enquiry.util.EMUtils

class Menu2ResourceMapper {
	
	val domainModel = RimFactory::eINSTANCE.createDomainModel;
	
	ImportedNamespaceFactory useFactory = null;
	CommandFactory commandFactory = null;
	MenuRoot rootObject = null;
	ModelLoader modelLoader = null;
	Event getEvent = null;
	Event postEvent = null;
	MappingStatus status = null;
	CachedStates cachedStates = null;
	
	def DomainModel mapMenu(ModelLoader loader, MenuRoot menu, String rimName, MappingStatus s, CachedStates cache) throws Exception {
		rootObject = menu;
		modelLoader = loader;
		status = s;
		cachedStates = cache; 
		
		/*
		 * First, create a domain. "hothouse" seems to be a good name at that stage (Mar 2014)
		 */
		val domain = RimFactory::eINSTANCE.createDomainDeclaration;
		domain.setName("menu");
		
		domainModel.rims.add(domain);

		val modelReferences = domain.rims;
		/*
		 * This list could well be dynamic as soon as we have the capability to introspect the resources.
		 */
		useFactory = new ImportedNamespaceFactory(modelReferences);
		useFactory.createUse("common.CoreCommands.*");
		useFactory.createUse("common.HTTPEvents.*");
		useFactory.createUse("common.ODataCommands.*");
		useFactory.createUse("common.T24Commands.*");
		useFactory.createUse("common.T24Events.*");
		
		/*
		 * The rimName is coming from the original Filename
		 * eg ModelBank.menu -> modelbank
		 */
		val rim = RimFactory::eINSTANCE.createResourceInteractionModel
		rim.setName(rimName);
		modelReferences.add(rim);
		
		val basePath = RimFactory::eINSTANCE.createBasePath();
		basePath.setName("/{companyid}");
		rim.setBasepath(basePath);
		/* 
		 * A couple of usefull factories.
		 */
		// create the event factory, this adds only the events used
		val eventFactory = new EventFactory(loader, rootObject);
		// create the command factory, this adds only the command used
		commandFactory = new CommandFactory(rim.commands);

/*
 * Since we will use it plenty of time, just generate it once.
 */
        getEvent = modelLoader.getNamedEObjectOrProxy(rootObject, "common.HTTPEvents.GET", RimPackage$Literals::EVENT, false, false) as Event; 
        if (getEvent == null){
        	getEvent = eventFactory.createGET();
        }

        postEvent = modelLoader.getNamedEObjectOrProxy(rootObject, "common.HTTPEvents.POST", RimPackage$Literals::EVENT, false, false) as Event; 
        if (postEvent == null){
        	postEvent = eventFactory.createPOST();
        }
		/*
		 * The top collection (List) of resources.
		 */ 
		val resources = rim.states;

		/*
		 * First a Dummy Leaf. This leaf will be referenced everytimes a menu point to a 
		 * non-existing resource.
		 */
		val noopLeaf = RimFactory::eINSTANCE.createState();
		noopLeaf.setName("MenuErrors");
		noopLeaf.setType(createCollectionResourceType());
		noopLeaf.setEntity(createEntity("Errors")); 
		val collectionImpl = RimFactory::eINSTANCE.createImplRef();
		val properties = new HashMap<String,String>();
		collectionImpl.setView(commandFactory.createResourceCommand("NoopGET", properties));
		noopLeaf.setImpl(collectionImpl);

		resources.add(noopLeaf);
		
		/*
		 * And there we go ! Ignore the Root Menu and create all the branches (! recursive call) 
		 */
//		for(oneMenuItem : menu.getRootItem().getSubmenus()){
		/*
		 * Start from the TOP menu as the root menu is not in the generated menus anymore.
		 */
		val top = createBranchOrLeaf(noopLeaf, resources, menu.getRootItem());
		if (top != null && status.getStatus() == 0) {
			// Only if all fine !
			cachedStates.addKnownState(menu.getRootItem().getName(), top);
		}

//		}
		
		/*
		 * An return a nice rim structure.
		 */
		return domainModel
	}
	
	def State createBranchOrLeaf(State dummyLeaf, EList<State> resource, MenuItem item){
		if (item.getSubmenus().size > 0){
			return createBranch(dummyLeaf, resource, item);
		}else{
			
			val leaf = createLeaf(dummyLeaf, resource,  item);
			
			val oneBranch = RimFactory::eINSTANCE.createState();
			val parameters = item.getParameters();
			val oneBranchRimName = ParameterParser::getResourceName(T24ResourceModelsGenerator::TYPE_MENU, item.getName(), parameters).getResourceName();
			oneBranch.setName(oneBranchRimName); 
			oneBranch.setType(createItemResourceType());
			oneBranch.setEntity(createEntity("Menu"))
			val branchImpl = RimFactory::eINSTANCE.createImplRef();
			branchImpl.setView(commandFactory.createResourceCommand("GETMenu", new HashMap<String,String>()));
			oneBranch.setImpl(branchImpl);
			val relation = RimFactory::eINSTANCE.createRelationConstant();
			relation.setName("http://www.temenos.com/rels/menu");
			oneBranch.relations.add(relation);
			resource.add(oneBranch);
			val pointer = oneBranch.getTransitions();
			pointer.add(createGetPointer(leaf, item));
			return oneBranch;
		}
	}
	
	def State createBranch(State noopLeaf, EList<State> resource, MenuItem item){
		val oneBranch = RimFactory::eINSTANCE.createState();
		/*
		 * No possible parameter on a branch
		 */
		val oneBranchRimName = ParameterParser::getResourceName(T24ResourceModelsGenerator::TYPE_MENU, item.getName(), null).getResourceName();
		oneBranch.setName(oneBranchRimName); 
		oneBranch.setType(createItemResourceType());
		oneBranch.setEntity(createEntity("Menu"))
		val branchImpl = RimFactory::eINSTANCE.createImplRef();
		branchImpl.setView(commandFactory.createResourceCommand("GETMenu", new HashMap<String,String>()));
		oneBranch.setImpl(branchImpl);
		val relation = RimFactory::eINSTANCE.createRelationConstant();
		relation.setName("http://www.temenos.com/rels/menu");
		oneBranch.relations.add(relation);
		resource.add(oneBranch);
		for (child : item.getSubmenus()){
			val pointer = oneBranch.getTransitions();
			val parameters = new HashMap<String,String>();
			var title = getFirstLabel(child);
			parameters.put("title", title);
			if (child.getSubmenus().size > 0){
				pointer.add(createGetPointerEmbedded(createBranch(noopLeaf, resource, child), child));
			} else {
				pointer.add(createGetPointer(createLeaf(noopLeaf, resource, child), child));
			}
		}
		return oneBranch;
	}
	
	def State createLeaf(State noopLeaf, EList<State> resource,  MenuItem item){
		var t24Name = "";
		var packageName = T24ResourceModelsGenerator::getDomain(null); // default
		var resourceType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
		
		val versionItem = item.getVersion();   
		if (versionItem != null){
			t24Name = versionItem.getT24Name();
			resourceType = T24ResourceModelsGenerator::TYPE_VERSION;
			packageName = packageName + "." + resourceType.toString() + EMUtils::camelCaseName(t24Name);
		}else{
			val compositeItem = item.getCompositeScreen();
			if (compositeItem != null){
				t24Name = compositeItem.getName();
				resourceType = T24ResourceModelsGenerator::TYPE_COMPOSITE;
			}else{
				val enquiryItem = item.getEnquiry();
				if (enquiryItem != null){
					t24Name = enquiryItem.getName();
					resourceType = T24ResourceModelsGenerator::TYPE_ENQUIRY;
				}else{
					val menuItem = item.getIncludedMenu();
					if (menuItem != null){
						if (menuItem.getRootItem() != null){
							t24Name = menuItem.getRootItem().getName();
							resourceType = T24ResourceModelsGenerator::TYPE_MENU;
							packageName = packageName + "." + resourceType.toString() + EMUtils::camelCaseName(t24Name);
						}else{
							t24Name = "unknown"; // How can it be ?
						}
					}
				}
			}
		}
		
		if (t24Name == null || t24Name.length == 0){
			resourceType = T24ResourceModelsGenerator::TYPE_UNKNOWN;
			t24Name = "InteractionException";
			packageName = "hothouse.Hothouse";
		}
		
		val parameters = item.getParameters();
		var resourceName = ParameterParser::getResourceName(resourceType, t24Name, parameters).getResourceName();

		if (packageName.length() > 0) {
			resourceName = packageName + "." + resourceName;
		}
		
		
//		val targetState = RimFactory::eINSTANCE.createState;
		val menuLeaf = RimFactory::eINSTANCE.createState;
//		menuLeaf.setName(resourceName);

 
 		// Note that resourceName should never be empty. Only if T24 name is empty
//		if (menuLeaf == null || resourceName.length == 0){
//			if (resourceType == T24ResourceModelsGenerator::TYPE_MENU){
//				// So we need to rerun it as all the menus have not been generated
//
//// TODO : Unrem-out to have recursive processing
////				status.setStatus(1); // need to rerun
// 				status.addDependency(item.includedMenu); 
//			}
//			return noopLeaf;
//		} else { 
			menuLeaf.setName(resourceName);
//			/*
//			 * Add the necessary in useFactory
//			 */
			 return menuLeaf;
//		}
	}

	def String getFirstLabel(MenuItem item){
		if (item.getLabels() != null && item.getLabels().size > 0 && item.getLabels().get(0).getMessage() != null){
			val title = item.getLabels().get(0).getMessage().replace("\"", "'");
			return title;
		}else{
			/*
			 * Should always exists.
			 */
			return item.getName();
		}
	}
	
	def ResourceType createItemResourceType() {
		val resourceType = RimFactory::eINSTANCE.createResourceType
		resourceType.setIsItem(true)
		return resourceType;
	}
	
	def Entity createEntity(String entityName) {
		val entity = RimFactory::eINSTANCE.createEntity();
		entity.setName(entityName);
		return entity;
	}
	
	/**
	 * For menu only.
	 */
	def TransitionRef createGetPointerEmbedded(State resources, MenuItem item) {
		val sTitle = getFirstLabel(item);
		val	transition = RimFactory::eINSTANCE.createTransitionEmbedded;
		/*
		 * Always get for embedded item.
		 */
		transition.setEvent(getEvent);
		transition.setState(resources);
		val spec = RimFactory::eINSTANCE.createTransitionSpec
		val title = RimFactory::eINSTANCE.createTitle();
		title.setName(sTitle);
		spec.setTitle(title);
		transition.setSpec(spec)
		return transition;
	}
	
	def TransitionRef createGetPointer(State resources, MenuItem item) {
		val rName = resources.getName();
		if (rName != null && rName.startsWith("mnu")){
			/*
			 * This is a menu, so do an embedded link @see createLeaf()
			 */
			return createGetPointerEmbedded(resources, item);
		}
		val sTitle = getFirstLabel(item);
		val	transition = RimFactory::eINSTANCE.createTransition;
		/*
		 * Post or Get ?
		 */
		 if (rName.endsWith("_new")){
		 	transition.setEvent(postEvent);
		 }else{
		 	transition.setEvent(getEvent);
		 }
		
		transition.setState(resources);
		val spec = RimFactory::eINSTANCE.createTransitionSpec
		val title = RimFactory::eINSTANCE.createTitle();
		title.setName(sTitle);
		spec.setTitle(title);
		
		/*
		 * Obsolete class. Just keep it for now as "example"
		 */
		
//		val parameters = T24ResourceModelsGenerator::getParameterMap(item.getParameters());
//		for (String paramkey : parameters.keySet) {
//			val uriParameter = RimFactory::eINSTANCE.createUriLink
//			uriParameter.setTemplateProperty(paramkey)
//			val entityProperty = RimFactory::eINSTANCE.createUriLinkage
//			entityProperty.setName(parameters.get(paramkey))
//			uriParameter.setEntityProperty(entityProperty)
//			spec.uriLinks.add(uriParameter)
//		}
//		transition.setSpec(spec)
		return transition;
	}


	
	def ResourceType createCollectionResourceType() {
		val resourceType = RimFactory::eINSTANCE.createResourceType
		resourceType.setIsCollection(true)
		return resourceType;
	}

}
