package com.odcgroup.page.ui.properties.sections;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.page.metamodel.PropertyCategory;
/**
 * XtooltipSection Class section class for xtooltip tab.
 * @author snn
 *
 */
public class XtooltipSection extends AbstractSection {
 
	   /**
	    * Constructor.
	    */
		public XtooltipSection() {
			super(PropertyCategory.XTOOLTIP_LITERAL);
		}

	
	
	/**
	 * creat the propertysheet page and set the sorter  
	 * @see org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	
	public void createControls(Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		//set the sorter for this property sheet page.
	    setSorter(new XtooltipPropertySheetSorter()); 
		
	}

	
	/**
	 * custom sorter class for the xtooltip tab category .
	 * @author snn
	 *
	 */
	class XtooltipPropertySheetSorter extends PropertySheetSorter{
        ArrayList<String> properties=new ArrayList<String>();
        /*
         * get the list of properties.
         */
		public XtooltipPropertySheetSorter() {
			properties.add("Xtooltip Type");
			properties.add("Include Xtooltip Fragment");
			properties.add("Hold tooltip window");
			properties.add("Delay");
			
		}
		/* 
		 * 
		 * @see org.eclipse.ui.views.properties.PropertySheetSorter#compare(org.eclipse.ui.views.properties.IPropertySheetEntry, org.eclipse.ui.views.properties.IPropertySheetEntry)
		 */
		public int compare(IPropertySheetEntry entryA,
				IPropertySheetEntry entryB) {
		  int aIndex= properties.indexOf(entryA.getDisplayName().trim());    
		  int bIndex=properties.indexOf(entryB.getDisplayName().trim());
			return aIndex-bIndex;
		}
		
	}
	

  

   
	
	

}
