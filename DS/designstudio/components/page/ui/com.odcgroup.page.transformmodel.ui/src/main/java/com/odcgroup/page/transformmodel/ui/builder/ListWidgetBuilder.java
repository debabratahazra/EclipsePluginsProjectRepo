package com.odcgroup.page.transformmodel.ui.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.model.Widget;

/**
 * A special WidgetBuilder used to build Lists of MdfModelElements.
 * 
 * @author atr
 */
public class ListWidgetBuilder extends AbstractWidgetBuilder {
	
	/** The MdfPaths. */
	private List<MdfModelElement> domainElements;

	/**
	 * Creates a new AbstractWidgetBuilder.
	 * 
	 * @param paths The MdfPaths
	 * @param parentWidget The Widget to which child Widgets will be added
	 * @param builderContext The context
	 */
	public ListWidgetBuilder(List<MdfModelElement> domainElements, Widget parentWidget, WidgetBuilderContext builderContext) {
		super(null, parentWidget, builderContext);
		
		Assert.isNotNull(domainElements);
		this.domainElements = domainElements;
	}
	
	/**
	 * Creates the Widgets. These Widgets may contain child Widgets.
	 * 
	 * @return List The newly created Widgets. These Widgets may contain child Widgets
	 */
	public List<Widget> buildWidgets() {
		List<Widget> widgets = new ArrayList<Widget>();
		
		for (MdfModelElement element : domainElements) {
			WidgetBuilder builder = WidgetBuilderFactory.createRootBuilder(getOfsProject(), getParentWidget(), element);
			List<Widget> cws = builder.buildWidgets();
			widgets.addAll(cws);
		}
		
		return widgets;
	}	
	
}
