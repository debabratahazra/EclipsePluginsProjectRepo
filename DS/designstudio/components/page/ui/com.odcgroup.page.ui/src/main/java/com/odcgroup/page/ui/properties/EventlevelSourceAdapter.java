package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * EventLevel Property source Adapter
 * @author snn
 * 
 *
 */
public class EventlevelSourceAdapter extends AbstractPropertySourceAdapter {

    public EventlevelSourceAdapter(Property property, CommandStack commandStack) {
	super(property, commandStack);
	Widget w = getProperty().getWidget();
	Property aggProperty = w.findProperty("aggregateData");
	Property hierPro = w.findProperty("hierarchy");
	if (aggProperty != null && hierPro!=null && (aggProperty.getValue().equals("false")&& hierPro.getBooleanValue())) {
	    getProperty().setReadonly(false);  
	 } else{ 
	     getProperty().setReadonly(true);
	 }
	
    }

    @Override
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
	    
	}

    }

}
