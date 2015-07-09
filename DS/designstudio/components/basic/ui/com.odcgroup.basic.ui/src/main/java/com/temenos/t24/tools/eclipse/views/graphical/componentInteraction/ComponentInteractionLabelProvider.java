package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.zest.core.viewers.EntityConnectionData;

import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractiontNode;

public class ComponentInteractionLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if (element instanceof ComponentInteractiontNode) {
			ComponentInteractiontNode myNode = (ComponentInteractiontNode) element;
			return myNode.getNodeName();
		}
		if (element instanceof EntityConnectionData) {
			// EntityConnectionData test = (EntityConnectionData) element;
			return "";
		}
		throw new RuntimeException("Wrong type: "
				+ element.getClass().toString());
	}

}
