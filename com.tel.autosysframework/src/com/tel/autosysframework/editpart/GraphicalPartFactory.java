package com.tel.autosysframework.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.tel.autosysframework.model.AutoModel;
import com.tel.autosysframework.model.AutosysDiagram;
import com.tel.autosysframework.model.SimpleOutput;
import com.tel.autosysframework.model.Wire;



public class GraphicalPartFactory
	implements EditPartFactory
{

public EditPart createEditPart(EditPart context, Object model) {
	EditPart child = null;
	
	/*if (model instanceof AutosysFlowContainer)
		child = new AutosysFlowContainerEditPart();
	else */if (model instanceof Wire)
		child = new WireEditPart();
//	else if (model instanceof LED)
//		child = new LEDEditPart();
	/*else if (model instanceof AutosysLabel)
		child = new AutosysLabelEditPart();*/
	/*else if (model instanceof Circuit)
		child = new CircuitEditPart();
	else if (model instanceof Gate)
		child = new GateEditPart();$*/
	else if (model instanceof AutoModel)
		child = new AutoModelEditPart();
	else if (model instanceof SimpleOutput)
		child = new OutputEditPart();
	/*else if (model instanceof AutoModel)
		child = new OutputEditPart();*/
	//Note that subclasses of AutosysDiagram have already been matched above, like Circuit
	else if (model instanceof AutosysDiagram)
		child = new AutosysDiagramEditPart();
	child.setModel(model);
	return child;
}

}
