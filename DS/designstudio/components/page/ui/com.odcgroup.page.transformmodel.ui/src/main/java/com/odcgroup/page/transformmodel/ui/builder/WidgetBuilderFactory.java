package com.odcgroup.page.transformmodel.ui.builder;

import java.util.List;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * The WidgetBuilderFactory is a helper class for creating Widget Builder's. The
 * main entry point is createRootWidgetBuilder().
 * 
 * @author atr
 */
public final class WidgetBuilderFactory {

	/** The singleton instance */
	private static final WidgetBuilderFactory INSTANCE = new WidgetBuilderFactory();

	/**
	 * @return the singleton
	 */
	public static final WidgetBuilderFactory getInstance() {
		return WidgetBuilderFactory.INSTANCE;
	}

	/**
	 * Creates the root WidgetBuilder. This method is needed so that we can
	 * provide a different Factory depending upon the business Domain in which
	 * we are working.
	 * 
	 * @param ofsProject
	 *            The OFS project for which you wish to create the WidgetBuilder
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param domainElements
	 *            the elements of a domain
	 * @return WidgetBuilder The WidgetBuilder
	 */
	public static WidgetBuilder createRootBuilder(IOfsProject ofsProject, Widget parentWidget,
			List<MdfModelElement> domainElements) {
		WidgetBuilder builder = null;
		if (domainElements.size() == 1) {
			builder = createRootBuilder(ofsProject, parentWidget, domainElements.get(0));
		} else {
			WidgetBuilderContext builderContext = new WidgetBuilderContext(ofsProject, WidgetBuilderFactory.INSTANCE);
			builder = new ListWidgetBuilder(domainElements, parentWidget, builderContext);
		}
		return builder;
	}

	/**
	 * Creates the root WidgetBuilder. This method is needed so that we can
	 * provide a different Factory depending upon the business Domain in which
	 * we are working.
	 * 
	 * @param ofsProject
	 *            The OFS project for which you wish to create the WidgetBuilder
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param element
	 *            A element of the domain
	 * @return WidgetBuilder The WidgetBuilder
	 */
	public static WidgetBuilder createRootBuilder(IOfsProject ofsProject, Widget parentWidget, MdfModelElement element) {
		WidgetBuilderContext builderContext = new WidgetBuilderContext(ofsProject, WidgetBuilderFactory.INSTANCE);
		return INSTANCE.createBuilder(element, parentWidget, builderContext);
	}

	/**
	 * Creates a WidgetBuilder given the domain element.
	 * 
	 * @param modelElement
	 *            The MdfModelElement to build the Widget for
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param builderContext
	 *            The WidgetBuilderContext
	 * @return WidgetBuilder The WidgetBuilder
	 */
	public WidgetBuilder createBuilder(MdfModelElement modelElement, Widget parentWidget,
			WidgetBuilderContext builderContext) {
		if (modelElement instanceof MdfDataset) {
			return createBuilder((MdfDataset) modelElement, parentWidget, builderContext);
		} else if (modelElement instanceof MdfDatasetMappedProperty) {
			return createBuilder((MdfDatasetMappedProperty) modelElement, parentWidget, builderContext);
		} else if (modelElement instanceof MdfDatasetDerivedProperty) {
			return createBuilder((MdfDatasetDerivedProperty) modelElement, parentWidget, builderContext);
		} else if (modelElement instanceof MdfAssociation) {
			return createBuilder((MdfAssociation) modelElement, parentWidget, builderContext);
		} else {
			throw new PageException("Unknown Type: " + modelElement.getClass().getName());
		}
	}

	/**
	 * Creates a WidgetBuilder given the domain element.
	 * 
	 * @param modelElement
	 *            The MdfDataset to build the Widget for
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param builderContext
	 *            The WidgetBuilderContext
	 * @return WidgetBuilder The WidgetBuilder
	 */
	public WidgetBuilder createBuilder(MdfDataset modelElement, Widget parentWidget, WidgetBuilderContext builderContext) {
		return new MdfDatasetWidgetBuilder(modelElement, parentWidget, builderContext);
	}

	/**
	 * Creates a WidgetBuilder given the domain element.
	 * 
	 * @param modelElement
	 *            The MdfDatasetMappedProperty to build the Widget for
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param builderContext
	 *            The WidgetBuilderContext
	 * @return WidgetBuilder The WidgetBuilder
	 */
	public WidgetBuilder createBuilder(MdfDatasetMappedProperty modelElement, Widget parentWidget,
			WidgetBuilderContext builderContext) {
		return new MdfDatasetMappedPropertyWidgetBuilder((MdfDatasetMappedProperty) modelElement, parentWidget,
				builderContext);
	}

	/**
	 * Creates a WidgetBuilder given the domain element.
	 * 
	 * @param modelElement
	 *            The MdfDatasetDerivedProperty to build the Widget for
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param builderContext
	 *            The WidgetBuilderContext
	 * @return WidgetBuilder The WidgetBuilder
	 */
	public WidgetBuilder createBuilder(MdfDatasetDerivedProperty modelElement, Widget parentWidget,
			WidgetBuilderContext builderContext) {
		return new MdfDatasetDerivedPropertyWidgetBuilder((MdfDatasetDerivedProperty) modelElement, parentWidget,
				builderContext);
	}

	/**
	 * Creates a WidgetBuilder given the domain element.
	 * 
	 * @param modelElement
	 *            The MdfAssociation to build the Widget for
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param builderContext
	 *            The WidgetBuilderContext
	 * @return WidgetBuilder The WidgetBuilder
	 */
	public WidgetBuilder createBuilder(MdfAssociation modelElement, Widget parentWidget,
			WidgetBuilderContext builderContext) {
		Multiplicity multiplicity = new Multiplicity(modelElement);
		if (multiplicity.isMany()) {
			if (multiplicity.isByReference()) {
				// [0..n] by reference
				return new MdfAssociationManyByRefWidgetBuilder(modelElement, parentWidget, builderContext);
			} else {
				// [0..n] by value
				return new MdfAssociationManyByValWidgetBuilder(modelElement, parentWidget, builderContext);
			}
		} else {
			if (multiplicity.isByReference()) {
				// [0..1] by reference
				return new MdfAssociationOneByRefWidgetBuilder(modelElement, parentWidget, builderContext);
			} else {
				// [0..1] by value
				return new MdfAssociationOneByValWidgetBuilder(modelElement, parentWidget, builderContext);
			}
		}
	}

}