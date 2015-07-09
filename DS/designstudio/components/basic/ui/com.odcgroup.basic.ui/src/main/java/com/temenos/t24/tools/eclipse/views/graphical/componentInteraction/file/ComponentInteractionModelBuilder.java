package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.file;

import java.util.ArrayList;
import java.util.HashMap;

import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractiontNode;

/**
 * Helper class for building the model objects
 * 
 * @author sbharathraja
 * 
 */
public class ComponentInteractionModelBuilder {
	/** instance of model builder **/
	private static ComponentInteractionModelBuilder builder = new ComponentInteractionModelBuilder();
	/** collection of child after grouping as product */
	private HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>> groupChildModelMap;
	/** collection of child models **/
	private ArrayList<ComponentInteractiontNode> childModelList;

	/**
	 * get the instance of the Model Builder class
	 * 
	 * @return instance
	 */
	public static ComponentInteractionModelBuilder getInstance() {
		return builder;
	}

	private ComponentInteractionModelBuilder() {
		// block the instance creation
	}

	/**
	 * construct the parent model
	 * 
	 * @param nodeName
	 *            - name of the node
	 * @return instance of model class
	 */
	protected ComponentInteractiontNode constructParentModel(String nodeName) {
		return constructSingleModel(nodeName);
	}

	/**
	 * construct the child models one by one and collect it in array list
	 * 
	 * @param childAsSingleLine
	 *            - names of the child node split by ','
	 * @return - array list contains child models
	 */
	protected HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>> constructChildModels(
			String childAsSingleLine) {
		ComponentInteractiontNode groupModel = null;
		if (!childAsSingleLine.equalsIgnoreCase("")) {
			for (String childName : childAsSingleLine.split(",")) {
				groupModel = constructSingleGroupModel(childName);
				putInGroupMap(groupModel, constructSingleModel(childName));
			}
		}
		return groupChildModelMap;
	}

	/**
	 * make the new map for collecting the new line
	 * 
	 * @return true - if map is empty and ready to collect new data's, false
	 *         otherwise.
	 */
	protected boolean isEligibleToCollectNewLine() {
		// make ready the new map for collecting new line
		groupChildModelMap = new HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>>();
		if (groupChildModelMap.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * construct the model
	 * 
	 * @param nodeName
	 *            - name of the node
	 * @return - instance of model class
	 */
	private ComponentInteractiontNode constructSingleModel(String nodeName) {
		ComponentInteractiontNode nodeModel = new ComponentInteractiontNode();
		nodeModel.setNodeName(nodeName);
		return nodeModel;
	}

	/**
	 * construct the group model after splitting the component name as product
	 * 
	 * @param singleChild
	 *            - component name
	 * @return group model as instance of class {@link ComponentInteractiontNode}
	 */
	private ComponentInteractiontNode constructSingleGroupModel(String singleChild) {
		String[] splittedChild = singleChild.split("_");
		// since the product name is in first position index as 0
		ComponentInteractiontNode groupModel = constructSingleModel(splittedChild[0]);
		return groupModel;
	}

	/**
	 * collect the group model and its children in map
	 * 
	 * @param groupModel
	 *            - parent model (group model)
	 * @param childModelForThisGroup
	 *            - child model (for above group model)
	 */
	private void putInGroupMap(ComponentInteractiontNode groupModel,
			ComponentInteractiontNode childModelForThisGroup) {
		validateChildList(groupModel, childModelForThisGroup);
		groupChildModelMap.put(groupModel, childModelList);
	}

	/**
	 * validate the child list. If the group model has some children's already
	 * get that list and make it ready for adding new child model, else make the
	 * new list for adding child.
	 * 
	 * @param groupModel
	 *            - parent model (group model)
	 * @param childModelForThisGroup
	 *            - child model (for above group model)
	 */
	private void validateChildList(ComponentInteractiontNode groupModel,
			ComponentInteractiontNode childModelForThisGroup) {
		childModelList = new ArrayList<ComponentInteractiontNode>();
		if (groupChildModelMap.containsKey(groupModel))
			childModelList = groupChildModelMap.get(groupModel);
		childModelList.add(childModelForThisGroup);

	}
}
