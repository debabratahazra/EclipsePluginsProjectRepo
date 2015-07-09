package com.odcgroup.page.ui.edit.handler;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.util.DomainObjectUtils;
import com.odcgroup.workbench.core.IOfsProject;


/**
 * This RequestHandler tries to open the Domain Editor and show the selected Domain Attribute.
 * 
 * @author Gary Hayes
 */
public class DomainObjectRequestHandler implements WidgetRequestHandler {

	/**
	 * @see com.odcgroup.page.ui.edit.handler.WidgetRequestHandler#handle(com.odcgroup.workbench.core.IOfsProject, org.eclipse.gef.commands.CommandStack, com.odcgroup.page.model.Widget)
	 */
	@Override
    public void handle(IOfsProject ofsProject, CommandStack commandStack, Widget widget) {

        String propertyName = WidgetUtils.getDomainAttribute(widget);
        if (StringUtils.isNotEmpty(propertyName)) {
    	
        	MdfDataset dataset = DomainObjectUtils.getDataset(ofsProject, widget);
        	if (dataset != null) {
        		
            	MdfDatasetProperty dsProperty = dataset.getProperty(propertyName);
            	if (dsProperty != null) {
            		DomainObjectUtils.openDomainEditor(dsProperty);
            	}
        	}
        }
    	
    }
}