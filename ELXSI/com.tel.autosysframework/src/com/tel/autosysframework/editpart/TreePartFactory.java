package com.tel.autosysframework.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

//import com.tel.autosysframwork.model.LED;
import com.tel.autosysframework.model.AutosysDiagram;

public class TreePartFactory
	implements EditPartFactory
{

public EditPart createEditPart(EditPart context, Object model) {
	/*if (model instanceof LED)
		return new AutosysTreeEditPart(model);*/
	if (model instanceof AutosysDiagram)
		return new AutosysContainerTreeEditPart(model);
	return new AutosysTreeEditPart(model);
}

}
