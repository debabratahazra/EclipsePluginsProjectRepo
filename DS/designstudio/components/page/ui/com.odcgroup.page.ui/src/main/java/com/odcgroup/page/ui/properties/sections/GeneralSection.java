package com.odcgroup.page.ui.properties.sections;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.ui.properties.sections.table.TableGroupPropertySorter;

/**
 * An instance of this class will contains the general widget properties
 * 
 * @author atr
 */
public class GeneralSection extends AbstractSection {


	/**	*/
	public GeneralSection() {
		super(PropertyCategory.GENERAL_LITERAL);
	}
	
	@Override
	public void createControls(Composite parent,
		TabbedPropertySheetPage aTabbedPropertySheetPage) {
	    super.createControls(parent, aTabbedPropertySheetPage);
	    setSorter(new TableGroupPropertySorter());
	}
}
