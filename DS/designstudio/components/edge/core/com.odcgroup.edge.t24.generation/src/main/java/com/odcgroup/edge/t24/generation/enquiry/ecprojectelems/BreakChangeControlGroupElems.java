package com.odcgroup.edge.t24.generation.enquiry.ecprojectelems;

import gen.com.acquire.intelligentforms.entities.FormListWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.types.NumberType;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.pattern.container.BasicProject;

/**
 * <code>BreakChangeControlGroupElems</code> is a self-populating creator / immutable aggregate of edgeConnect project elements for the <code>WorkingElements[1].BreakChangeControl</code> data group.<p>
 * 
 * All data members are <code>public</code>, <code>final</code> so no "getter" methods are required.
 *
 * All data members are <code>public</code>, <code>final</code> (being initialized on {@link #BreakChangeControlGroupElems(BasicProject, PropertyGroupWrapper) construction}, hence the absence of
 * (superfluous) "getter" methods.<p>
 *
 * @author Simon Hayes
 */
public class BreakChangeControlGroupElems
{
	public final PropertyGroupWrapper breakChangeControlGroup;
	
	public final PropertyWrapper breakChangeDropdownItem;
	
	public final PropertyWrapper firstDynamicBreakChangeListKey;
	
	public final PropertyWrapper numBreakChangeValuesItem;
	
	public final PropertyWrapper irisResultGroupStartInstanceItem;
	
	public final PropertyWrapper irisResultGroupEndInstanceItem;
	
	public BreakChangeControlGroupElems(BasicProject p_project, PropertyGroupWrapper p_workingElementsGroup)
	{
		AssertionUtils.requireNonNull(p_project, "p_project");
		AssertionUtils.requireNonNull(p_workingElementsGroup, "p_workingElementsGroup");
		
		final FormContext formContext = p_project.getFormContext();
		
		final FormListWrapper breakChangeFormList = FormListWrapper.create(formContext, "Break-change");
		breakChangeFormList.setDynamicList(Boolean.TRUE);
		p_project.getListsRoot().addChild(breakChangeFormList);

		breakChangeControlGroup = PropertyGroupWrapper.create(formContext, "BreakChangeControl");
		p_workingElementsGroup.addChild(breakChangeControlGroup);
		
		breakChangeDropdownItem = PropertyWrapper.create(formContext, "BreakChangeDropdownItem");
		breakChangeDropdownItem.setType(breakChangeFormList.getName() + " List");
		breakChangeControlGroup.addChild(breakChangeDropdownItem);
		
		firstDynamicBreakChangeListKey = PropertyWrapper.create(formContext, "FirstBreakChangeListKey");
		breakChangeControlGroup.addChild(firstDynamicBreakChangeListKey);
		
		numBreakChangeValuesItem = PropertyWrapper.create(formContext, "NumBreakChangeValues");
		numBreakChangeValuesItem.setType(NumberType.TYPE);
		breakChangeControlGroup.addChild(numBreakChangeValuesItem);
		
		irisResultGroupStartInstanceItem = PropertyWrapper.create(formContext, "IrisResultGroupStartInstance");
		irisResultGroupStartInstanceItem.setType(NumberType.TYPE);
		breakChangeControlGroup.addChild(irisResultGroupStartInstanceItem);
		
		irisResultGroupEndInstanceItem = PropertyWrapper.create(formContext, "IrisResultGroupEndInstance");
		irisResultGroupEndInstanceItem.setType(NumberType.TYPE);
		breakChangeControlGroup.addChild(irisResultGroupEndInstanceItem);
	}
}
