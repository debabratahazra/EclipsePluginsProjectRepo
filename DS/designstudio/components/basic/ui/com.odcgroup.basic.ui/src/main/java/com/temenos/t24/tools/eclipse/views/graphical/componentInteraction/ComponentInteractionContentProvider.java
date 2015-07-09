package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractiontNode;

/**
 * content provider for zest's {@link GraphViewer}
 * 
 * @author sbharathraja
 * 
 */
public class ComponentInteractionContentProvider extends ArrayContentProvider implements
		IGraphEntityContentProvider {

	public Object[] getConnectedTo(Object entity) {
		if (entity instanceof ComponentInteractiontNode) {
        	ComponentInteractiontNode nodeModel = (ComponentInteractiontNode) entity;
            return nodeModel.getChildrens().toArray();
        }
        throw new RuntimeException("Type not supported");
	}

}
