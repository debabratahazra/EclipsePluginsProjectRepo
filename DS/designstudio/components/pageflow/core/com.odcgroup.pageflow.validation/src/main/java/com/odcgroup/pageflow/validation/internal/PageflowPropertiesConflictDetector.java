package com.odcgroup.pageflow.validation.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;

/**
 * This class detects if some pageflow's property conflicts exists. 
 */
public class PageflowPropertiesConflictDetector {
	
	private boolean hasConflicts = false;
	private StringBuilder errMessage = null;
	
	// contains a partition of the de declared properties in direct subpageflow.
	// property-name => property-value => set-of-sub-pageflow
	// this structure is incrementally updated, and used to detect conflicts.
	private Map<String, Map<String, Set<Node>>> partitions = new HashMap<String, Map<String,Set<Node>>>();
	
	private class Node {
		
		// the parent node.
		private Node parent = null;

		// the (sub)pageflow associated with this node
		private Pageflow pageflow;

		// the set of sub-pageflow. 
		private Set<Node> children = new HashSet<Node>();

		// contains the values of each propagated properties
		// key:property-name value:property-value
		private Map<String, String> properties = new HashMap<String, String>();
		
		// associate each propagated properties with a number. when the number is zero, it mean
		// that the properties has not been overrident by the propagation. When there exist at least
		// one property with a counter of 0 and at least 2 sibling nodes (subpageflows) declare this
		// property with different value a conflict will exist at runtime. 
		private Map<String, Integer> counter = new HashMap<String, Integer>();
		
		public Node(Pageflow pageflow) {
			this(null, pageflow);
		}
		
		@SuppressWarnings("unchecked")
		public Node(Node parent, Pageflow pageflow) {
			this.parent = parent;
			this.pageflow = pageflow;
			
			// initialise counter on my properties
			for (Property prop : (List<Property>)pageflow.getProperty()) {
				properties.put(prop.getName(), prop.getValue());
				counter.put(prop.getName(), 0);
			}
			
			// propagate parent properties and update counter
			if (parent != null) {
				
				for (Map.Entry<String, String> entry : parent.properties.entrySet()) {
					properties.put(entry.getKey(), entry.getValue());
				}
				for (Map.Entry<String, Integer> entry : parent.counter.entrySet()) {
					counter.put(entry.getKey(), entry.getValue()+1);
				}
				
				// update partitions 
				for (Map.Entry<String, Integer> entry : counter.entrySet()) {
					if (entry.getValue() == 0) {
						Map<String, Set<Node>> valueSet = partitions.get(entry.getKey());
						if (valueSet == null) {
							valueSet = new HashMap<String, Set<Node>>();
							partitions.put(entry.getKey(), valueSet);
						}
						Set<Node> nodeSet = valueSet.get(properties.get(entry.getKey()));
						if (nodeSet == null) {
							nodeSet = new HashSet<Node>();
							valueSet.put(properties.get(entry.getKey()), nodeSet);
						}
						nodeSet.add(this);
					}
				}
			}
			
		}
		
		public final Pageflow getPageflow() {
			return this.pageflow;
		}
		
		public final Node addChild(Pageflow pageflow) {
			Node child = new Node(this, pageflow);
			children.add(child);
			return child;
		}
		
		public final Collection<Node> children() {
			return children;
		}
	}
	
	
	/**
	 * This method build a tree of nesting/nested pageflows. 
	 */
	@SuppressWarnings("unchecked")
	private void visitPageflow(Node parent) {
		for (State state : (List<State>)parent.getPageflow().getStates()) {
			if (state instanceof SubPageflowState) {
				Pageflow spf = ((SubPageflowState)state).getSubPageflow();
				if (spf != null) {
					// create a child node and propagate properties
					// complete the partition in the parent node
					visitPageflow(parent.addChild(spf));
				}
			}
		}
	}
	
	private String getFullName(Node node) {
		StringBuffer name = new StringBuffer();
		Node tmp = node;
		while (tmp != null) {
			name.insert(0,tmp.getPageflow().getName());
			tmp = tmp.parent;
			if (tmp != null) {
				name.insert(0,"/");
			}
		}
		return name.toString();
	}
	
	/**
	 * Find partitions with size greater than 1. These partitions indicate a ambiguous
	 * in the declaration of properties that implies a non deterministic behavior at runtime.
	 * @param node the node to be checked
	 */
	private void findConflicts(Node node) {
		// find all partition with size > 1 => indicates an ambiguous declaration in subpageflows.
		for (Map.Entry<String, Map<String, Set<Node>>> entry : partitions.entrySet()) {
			int size = entry.getValue().size();
			if (size > 1) {
				hasConflicts = true;
				errMessage.append("The property ["+entry.getKey()+"] is declared with different values in nested pageflows:");
				for (Map.Entry<String, Set<Node>> valueSet : entry.getValue().entrySet()) {
					errMessage.append("\n\tValue=["+valueSet.getKey()+"]");
					errMessage.append(" in subpageflow");
					int nb = valueSet.getValue().size();
					if (nb > 1) {
						errMessage.append("s");
					}
					errMessage.append(" [");
					for (Node n : valueSet.getValue()) {
						errMessage.append(getFullName(n));
						if (--nb > 0) {
							errMessage.append(",");
						}
					}
					errMessage.append("]");
				}
				errMessage.append("\n");
			}
		}
	}
	
	public final boolean hasConflicts() {
		return hasConflicts;
	}
	
	public final String getErrorMessage() {
		return errMessage.toString();
	}

	public PageflowPropertiesConflictDetector(Pageflow pageflow) {
		errMessage = new StringBuilder();
		Node root = new Node(pageflow);
		visitPageflow(root);
		findConflicts(root);
	}
	

}
