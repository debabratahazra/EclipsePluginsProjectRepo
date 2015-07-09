package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
/**
 * Origin of Data Source Adapter.
 * @author snn
 *
 */
public class OriginOfDataSourceAdapter extends AbstractPropertySourceAdapter {


    public OriginOfDataSourceAdapter(Property property,
	    CommandStack commandStack) {
	super(property, commandStack);
	Widget w = getProperty().getWidget();
	Property p = w.findProperty("hierarchy");
	if (p != null && !p.getBooleanValue()) {
	    getProperty().setReadonly(true);  
	 } else{ 
	     getProperty().setReadonly(false);
	 }
    }
    /**
     * get the property value
     */
    public Object getPropertyValue() {
	String str = getProperty().getValue();
	DataType dtype = getProperty().getType().getDataType();
	DataValue dvalue = dtype.findDataValue(str);
	return dvalue;
    }

    @Override
    public void setPropertyValue(Object newValue) {
	DataValue dataValue = (DataValue) newValue;
	if (dataValue != null) {
	    String str = dataValue.getValue();
	    UpdatePropertyCommand command = new UpdatePropertyCommand(getProperty(), str);
	    getCommandStack().execute(command);
	    Widget w = getProperty().getWidget();
	    Property p = w.findProperty("eventLevel");
	    if(str.equals("false")){
	      if (p != null) {
		    p.setReadonly(false);  
	       }
	    } else {
		p.setReadonly(true);
		UpdatePropertyCommand commandPro = new UpdatePropertyCommand(p, "");
		getCommandStack().execute(commandPro);
		
	    }
	}
    }

}


