package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

public class HierarchySourceAdapter extends AbstractPropertySourceAdapter {

    public HierarchySourceAdapter(Property property, CommandStack commandStack) {
	super(property, commandStack);
	
	
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
	    Widget w = getProperty().getWidget();
	    Property p = w.findProperty("aggregateData");
	    Property level=w.findProperty("eventLevel");
	    if(str.equals("false")){
	      if (p != null) {
		    p.setReadonly(true);
		    level.setReadonly(true);
		    UpdatePropertyCommand commandp = new UpdatePropertyCommand(p, "true");
		    getCommandStack().execute(commandp);
		    UpdatePropertyCommand commandlevel = new UpdatePropertyCommand(level, "");
		    getCommandStack().execute(commandlevel);
	       }
	    } else {
		p.setReadonly(false);
	    }
	}

    }

}
