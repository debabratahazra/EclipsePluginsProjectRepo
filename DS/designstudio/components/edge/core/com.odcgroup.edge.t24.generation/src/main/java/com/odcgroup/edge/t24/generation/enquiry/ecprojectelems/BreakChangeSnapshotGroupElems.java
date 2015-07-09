package com.odcgroup.edge.t24.generation.enquiry.ecprojectelems;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.types.NumberType;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.pattern.container.BasicProject;

/**
 * <code>BreakChangeSnapshotGroupElems</code> is a self-populating creator / immutable aggregate of edgeConnect project elements for the <code>WorkingElements[1].BreakChangeValuesSnapshot</code> data group
 * (as used by the rules generated to populate the dynamic list of the break-change dropdown {@link BreakChangeControlGroupElems#breakChangeDropdownItem item}).<p>
 * 
 * All data members are <code>public</code>, <code>final</code> (being initialized on {@link #BreakChangeSnapshotGroupElems(BasicProject, PropertyGroupWrapper) construction}, hence the absence of
 * (superfluous) "getter" methods.<p>
 *
 * @author Simon Hayes
 */
public class BreakChangeSnapshotGroupElems
{
	public final PropertyGroupWrapper breakChangeValuesSnapshotGroup;
	
	public final PropertyWrapper prevBreakChangeListEntryDisplayValueItem;
	public final PropertyWrapper currBreakChangeListEntryDisplayValueItem;
	
	public final PropertyWrapper irisResultStartInstanceForNextBreakChangeListEntry;
	public final PropertyWrapper irisResultEndInstanceForNextBreakChangeListEntry;
	
	public BreakChangeSnapshotGroupElems(BasicProject p_project, PropertyGroupWrapper p_workingElementsGroup)
	{
		AssertionUtils.requireNonNull(p_project, "p_project");
		AssertionUtils.requireNonNull(p_workingElementsGroup, "p_workingElementsGroup");

		final FormContext formContext = p_project.getFormContext();

		breakChangeValuesSnapshotGroup = PropertyGroupWrapper.create(formContext, "BreakChangeValuesSnapshot");
		p_workingElementsGroup.addChild(breakChangeValuesSnapshotGroup);
    	
    	prevBreakChangeListEntryDisplayValueItem = PropertyWrapper.create(formContext, "PreviousBreakChangeListValue");
    	breakChangeValuesSnapshotGroup.addChild(prevBreakChangeListEntryDisplayValueItem);
    	
    	currBreakChangeListEntryDisplayValueItem = PropertyWrapper.create(formContext, "CurrentBreakChangeListValue");
    	breakChangeValuesSnapshotGroup.addChild(currBreakChangeListEntryDisplayValueItem);
    	
    	irisResultStartInstanceForNextBreakChangeListEntry = PropertyWrapper.create(formContext, "IrisResultStartInstanceForNextBreakChangeListEntry");
    	irisResultStartInstanceForNextBreakChangeListEntry.setType(NumberType.TYPE);
    	breakChangeValuesSnapshotGroup.addChild(irisResultStartInstanceForNextBreakChangeListEntry);
    	
    	irisResultEndInstanceForNextBreakChangeListEntry = PropertyWrapper.create(formContext, "IrisResultEndInstanceForNextBreakChangeListEntry");
    	irisResultEndInstanceForNextBreakChangeListEntry.setType(NumberType.TYPE);
    	breakChangeValuesSnapshotGroup.addChild(irisResultEndInstanceForNextBreakChangeListEntry);

	}
}
