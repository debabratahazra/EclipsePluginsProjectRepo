package com.odcgroup.page.transformmodel.ui.builder;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.Widget;

/**
 * Builds the Widgets for an MdfDataset.
 * 
 * @author atr
 */
public class MdfDatasetWidgetBuilder extends AbstractWidgetBuilder {

	/**
	 * Creates a new MdfDatasetWidgetBuilder.
	 * 
	 * @param modelElement
	 *            The MdfModelElement to create
	 * @param parentWidget
	 *            The Widget to which child Widgets will be added
	 * @param builderContext
	 *            The context
	 */
	public MdfDatasetWidgetBuilder(MdfDataset modelElement, Widget parentWidget, WidgetBuilderContext builderContext) {
		super(modelElement, parentWidget, builderContext);
	}

	/**
	 * Creates the Widgets. These Widgets may contain child Widgets.
	 * 
	 * @return List The newly created Widgets. These Widgets may contain child
	 *         Widgets
	 */
	@SuppressWarnings("unchecked")
	public List<Widget> buildWidgets() {
		List<Widget> widgets = new ArrayList<Widget>();
		List<MdfDatasetProperty> properties = getMdfDataset().getProperties();
		for (MdfDatasetProperty property : properties) {
			WidgetBuilder wb = getBuilderFactory()
					.createBuilder(property, getParentWidget(), getBuilderContext());
			widgets.addAll(wb.buildWidgets());
		}

		return widgets;

	}

	/**
	 * Gets the MdfModelElement as an MdfDataset.
	 * 
	 * @return MdfDataset
	 */
	private MdfDataset getMdfDataset() {
		return (MdfDataset) getModelElement();
	}
}
