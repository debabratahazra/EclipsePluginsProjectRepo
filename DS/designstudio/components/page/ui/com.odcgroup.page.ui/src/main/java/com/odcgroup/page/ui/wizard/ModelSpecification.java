package com.odcgroup.page.ui.wizard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetTemplateConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Defines specification for creating a new page model
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class ModelSpecification {

	/** The default regular expression used to check the name of the model */
	private static final String DEFAULT_NAME_PATTERN = "[A-Za-z]([_]?[A-Za-z0-9])*";

	/** Indicates the cardinality of domain association */
	private boolean cardinalityMany = false;

	/** The path of the container that will contains the new model */
	private IPath containerPath;

	/** Indicates if a default fragment shall be created */
	private boolean defaultFragment = false;

	/** A description of the page model */
	private String description = "";

	/** The name of a domain object bound to the module */
	private MdfName domainName;

	/** The type of the fragment to create */
	private FragmentModelType fragmentType = FragmentModelType.VERTICAL;

	/** the name of the model */
	private String modelName = "";

	/** A regular expression used to validate a new model name */
	private String modelNamePattern = ModelSpecification.DEFAULT_NAME_PATTERN;

	/** The type of the model to create */
	private ModelType modelType;
	
	private String searchType = "none";

	/**
	 * Creates the root Widget and the Model from this specification
	 * 
	 * @return Model The new created model
	 */
	public Model createModel() {

		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetFactory factory = new WidgetFactory();
		
		Widget rootWidget = null;
		
		// 1. create the root widget
		WidgetTemplate template = library.findWidgetTemplate(getWidgetTemplateName());
		rootWidget = factory.create(template);
		rootWidget.setPropertyValue(PropertyTypeConstants.DOCUMENTATION, getDescription());
		if (ModelType.FRAGMENT.equals(getModelType())) {
            String isDefaultValue = String.valueOf(isDefaultFragment());
            rootWidget.setPropertyValue(PropertyTypeConstants.DEFAULT_FRAGMENT, isDefaultValue);
            String cardinalityValue = isCardinalityMany() ? "many" : "one";
            rootWidget.setPropertyValue(PropertyTypeConstants.CARDINALITY, cardinalityValue);		
		}

		// 2. bind the root widget to the domain 
		String selectedPath = getDomainName() != null ? getDomainName().getQualifiedName() : "";
		if (StringUtils.isNotEmpty(selectedPath)) {
			rootWidget.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, selectedPath);
			
			// DS-2571 define default value for bean-name & bean-define
			String BEAN_KEY = "DSBean";
			String domainName = getDomainName().getDomain();
			String datasetName = getDomainName().getLocalName();
			if (StringUtils.isNotEmpty(datasetName)) {
				if ("CDMAPP".equals(domainName)) {
					// DS-2955 CDM exception
					rootWidget.setPropertyValue(PropertyTypeConstants.BEAN_NAME, datasetName.toLowerCase());
					rootWidget.setPropertyValue(PropertyTypeConstants.BEAN_DEFINE, datasetName.toLowerCase());
				} else {
					DomainRepository repository = DomainRepository.getInstance(EclipseUtils.findProject(getContainerPath()));
					MdfDataset  dataset = repository.getDataset(getDomainName());
					if (dataset != null) {
						MdfAnnotation serviceAnnotation = dataset.getAnnotation(AAAAspect.SERVICES_NAMESPACE_URI, AAAAspect.SERVICES_ANNOTATION);
						if (serviceAnnotation != null) {
							MdfAnnotationProperty prop = serviceAnnotation.getProperty("ScopeActivity");
							if (prop != null && prop.getValue().equals("true")) {
								BEAN_KEY = "DSActivityBean";
							}
						}
					}
					rootWidget.setPropertyValue(PropertyTypeConstants.BEAN_NAME, datasetName);
					rootWidget.setPropertyValue(PropertyTypeConstants.BEAN_DEFINE, BEAN_KEY + "." + domainName + "." + datasetName);
				}
			}
		}

		// 3. Create the container just below the root widget (default: vbox)
		String widgetTemplateName = WidgetTemplateConstants.VERTICAL_BOX_TEMPLATE;
		if (ModelType.FRAGMENT.equals(getModelType()) || ModelType.LAYER.equals(getModelType())) {
			switch (getFragmentType()) {
				case HORIZONTAL:
					widgetTemplateName = WidgetTemplateConstants.HORIZONTAL_BOX_TEMPLATE;
					break;
				case VERTICAL:
					widgetTemplateName = WidgetTemplateConstants.VERTICAL_BOX_TEMPLATE;
					break;
			}
		} 
		//if the Fragment is Xtooltip Type create a Xtooltip Widget and Grid inside it .
		if(ModelType.FRAGMENT.equals(getModelType())&&getFragmentType().equals(FragmentModelType.XTOOLTIP)){
			widgetTemplateName=WidgetTemplateConstants.XTOOLTIP_TEMPLATE;
			template = library.findWidgetTemplate(widgetTemplateName);
			Widget xtooltipWidget=factory.create(template);
			template=library.findWidgetTemplate(WidgetTemplateConstants.GRID_TEMPLATE);
			xtooltipWidget.getContents().add(factory.create(template));
			rootWidget.getContents().add(xtooltipWidget);
			rootWidget.setPropertyValue(PropertyTypeConstants.FRAGMENT_TYPE,getFragmentType().toString().toLowerCase());
		}else{
		template = library.findWidgetTemplate(widgetTemplateName);
		rootWidget.getContents().add(factory.create(template));
		 }
		// set an id incase of a module
		if (ModelType.MODULE.equals(getModelType()) || ModelType.LAYER.equals(getModelType())) {
			rootWidget.setID(UniqueIdGenerator.generateModuleId(getModelName()));
		}
		
		// set the search module type
		if (ModelType.MODULE.equals(getModelType())) {
			rootWidget.setPropertyValue(PropertyTypeConstants.SEARCH, getSearchType());
			rootWidget.findProperty(PropertyTypeConstants.SEARCH).setReadonly(true);
		}
		// 4. Create a new model and attach to it the root model
		Model model = ModelFactory.eINSTANCE.createModel();
		model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion(rootWidget.getTypeName()));
		
		model.setWidget(rootWidget);

		// 5. returns the new model to the caller
		return model;
	}

	/**
	 * @return the containerPath
	 */
	public final IPath getContainerPath() {
		return containerPath;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @return the domain name
	 */
	public final MdfName getDomainName() {
		return domainName;
	}

	/**
	 * @return the domain object provider
	 */
	public IContentAssistProvider getDomainObjectProvider() {
		final DomainRepository repository = DomainRepository.getInstance(EclipseUtils.findProject(getContainerPath()));
		final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {
			public MdfModelElement[] getCandidates() {
				final List<MdfDataset> allDatasets = new ArrayList<MdfDataset>();
				Iterator<MdfDomain> it = repository.getDomains().iterator();
				while (it.hasNext()) {
					new ModelWalker(new ModelVisitor() {
						public boolean accept(MdfModelElement model) {
							if (model instanceof MdfDataset) {
								allDatasets.add((MdfDataset) model);
							}
							return true;
						}
					}).visit(it.next());
				}
				return allDatasets.toArray(new MdfModelElement[] {});
			}

			public String getDefaultDomainName() {
				return "";
			}
		};
		return contentAssistProvider;
	}

	/**
	 * @return the fileExtension
	 */
	public final String getFileExtension() {
		return this.modelType.getFileExtension();
	}

	/**
	 * @return the fragment type
	 */
	public final FragmentModelType getFragmentType() {
		return fragmentType;
	}

	/**
	 * @return the model name
	 */
	public final String getModelName() {
		return modelName;
	}

	/**
	 * @return the modelNamePattern
	 */
	public final String getModelNamePattern() {
		return modelNamePattern;
	}

	/**
	 * @return the file name
	 */
	public IPath getModelPath() {
		return getContainerPath().append(getModelName() + getFileExtension());
	}

	/**
	 * @return the modelType
	 */
	public final ModelType getModelType() {
		return modelType;
	}

	/**
	 * @return the model type name
	 */
	public final String getModelTypeName() {
		return getModelType().getTypeName();
	}

	/**
	 * @return the widget template name
	 */
	public final String getWidgetTemplateName() {
		return getModelType().getWidgetTemplateName();
	}

	/**
	 * @return the cardinalityMany
	 */
	public final boolean isCardinalityMany() {
		return cardinalityMany;
	}

	/**
	 * @return the defaultFragment
	 */
	public final boolean isDefaultFragment() {
		return defaultFragment;
	}

	/**
	 * @param modelName
	 *            the name of the model to be checked
	 * 
	 * @return {@code true} if the name of model matches the pattern for new
	 *         model name, otherwise it returns {@code false}
	 */
	public boolean isValidModelName(String modelName) {
		return modelName.matches(getModelNamePattern());
	}

	/**
	 * Returns {@code true} if a model name already exists, otherwise it returns
	 * {@code false}.
	 * 
	 * @param modelName
	 *            the name of the model to be checked
	 * @return {@code true} if this model name already exists, otherwise it
	 *         returns {@code false}
	 */
	public boolean modelExists(String modelName) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IPath filePath = getContainerPath().append(modelName + getFileExtension());
		return workspaceRoot.exists(filePath);
	}

	/**
	 * @param cardinalityMany
	 *            the cardinalityMany to set
	 */
	public final void setCardinalityMany(boolean cardinalityMany) {
		this.cardinalityMany = cardinalityMany;
	}

	/**
	 * @param defaultFragment
	 *            the defaultFragment to set
	 */
	public final void setDefaultFragment(boolean defaultFragment) {
		this.defaultFragment = defaultFragment;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param name
	 *            the domain name to set
	 */
	public final void setDomainName(MdfName name) {
		this.domainName = name;
	}

	/**
	 * @param type
	 *            the fragment type to set
	 */
	public final void setFragmentType(FragmentModelType type) {
		this.fragmentType = type;
	}

	/**
	 * @param name
	 *            the model name to set
	 */
	public final void setModelName(String name) {
		this.modelName = name;
		// ensure the name is unique in the container
		int index = 1;
		while (modelExists(modelName)) {
			this.modelName = name + index++;
		}
	}

	/**
	 * @param modelNamePattern
	 *            the modelNamePattern to set
	 */
	public final void setModelNamePattern(String modelNamePattern) {
		this.modelNamePattern = modelNamePattern;
	}

	/**
	 * @param modelType
	 *            the modelType to set
	 */
	public final void setModelType(ModelType modelType) {
		this.modelType = modelType;
	}

	/**
	 * @return
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * Create a new model specification
	 * 
	 * @param modelType
	 *            the type of the model to be created
	 * @param  containerPath
	 *            the container that owns the new widget 
	 */
	public ModelSpecification(ModelType modelType, IPath containerPath) {
		super();
		this.modelType = modelType;
		this.containerPath = containerPath;
		setModelName(modelType.getDefaultModelName());
	}

}
