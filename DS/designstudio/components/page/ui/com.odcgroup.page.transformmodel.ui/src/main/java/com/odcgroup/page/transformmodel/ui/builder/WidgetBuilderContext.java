package com.odcgroup.page.transformmodel.ui.builder;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * The WidgetBuilderContext is used to provide information to all the different
 * WidgetBuilder's used when building a Widget.
 * 
 * @author Gary Hayes
 */
public class WidgetBuilderContext {
	
	/** The OFS project for which we are building Widgets. */
	private IOfsProject ofsProject;
	
	/** This is the corporate design to use when building template. */
	private CorporateDesign corporateDesign;
	
	/** The WidgetBuilderFactory used to build Widgets. */
	private WidgetBuilderFactory widgetBuilderFactory;
	
	/**
	 * Creates a new WidgetBuilderContext.
	 * 
	 * @param ofsProject The OFS project for which we are building Widgets
	 * @param widgetBuilderFactory The WidgetBuilderFactory used to build Widgets
	 */
	public WidgetBuilderContext(IOfsProject ofsProject, WidgetBuilderFactory widgetBuilderFactory) {
		Assert.isNotNull(ofsProject);
		Assert.isNotNull(widgetBuilderFactory);
		
		this.ofsProject = ofsProject;
		this.widgetBuilderFactory = widgetBuilderFactory;
		
		corporateDesign = CorporateDesignUtils.getCorporateDesign(ofsProject);
		
	}
	
	/**
	 * Gets the path containing the model definitions.
	 * 
	 * @return path
	 */
	public final DomainRepository getDomainRepository() {
		return DomainRepository.getInstance(ofsProject);
	}
	
	/**
	 * Gets the Corporate Design.
	 * 
	 * @return CorporateDesign The corporate design attached to this builder
	 */
	public final CorporateDesign getCorporateDesign() {
		return corporateDesign;
	}

	/**
	 * Gets the metamodel.
	 * 
	 * @return MetaModel The metamodel.
	 */
	public final MetaModel getMetaModel() {
		return MetaModelRegistry.getMetaModel();
	}
	
	/**
	 * Gets the project for which we are building Widgets.
	 * 
	 * @return IProject
	 */
	public final IOfsProject getOfsProject() {
		return ofsProject;
	}
	
	/**
	 * Gets the Factory used to build Widgets.
	 * 
	 * @return WidgetBuilderFactory
	 */
	public final WidgetBuilderFactory getBuilderFactory() {
		return widgetBuilderFactory;
	}
	
	/**
	 * Gets the WidgetFactory.
	 * 
	 * @return WidgetFactory The WidgetFactory
	 */
	public WidgetFactory getWidgetFactory() {
		return new WidgetFactory();
	}
}