package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class which collecting the details about parent node
 * 
 * @author sbharathraja
 * 
 */
public class ComponentInteractiontNode {
	/** name of the node **/
	private String nodeName = "";
	/** list of child nodes **/
	private List<ComponentInteractiontNode> childNodes;
	private volatile int hashCode = 0;

	/**
	 * set the node name
	 * 
	 * @param nodeName
	 *            - name of the node
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * get the node name
	 * 
	 * @return name of the node
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * setting the children's for current node
	 * 
	 * @param childrens
	 *            - list of children's
	 */
	public void setChilderns(ArrayList<ComponentInteractiontNode> childrens) {
		this.childNodes = childrens;
	}

	/**
	 * get the children's for current node
	 * 
	 * @return - list of children's
	 */

	public List<ComponentInteractiontNode> getChildrens() {
		if (childNodes == null)
			return childNodes = new ArrayList<ComponentInteractiontNode>();
		else
			return childNodes;
	}

	/** overrided equals method */
	public boolean equals(Object incomingModel) {
		if (this == incomingModel)
			return true;
		if (!(incomingModel instanceof ComponentInteractiontNode))
			return false;
		return nodeName.equals(((ComponentInteractiontNode) incomingModel).nodeName);
	}

	/** overrided hash code method */
	public int hashCode() {
		final int multiplier = 1;
		if (hashCode == 0) {
			int code = 2;
			code = multiplier * code + nodeName.hashCode();
			hashCode = code;
		}
		return hashCode;
	}
}
