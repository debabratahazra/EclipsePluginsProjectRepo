package com.odcgroup.edge.t24.generation.enquiry.ecprojectelems;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.pattern.container.BasicProject;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.QuestionWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableQuestionStyleOption;

/**
 * <code>TreeEnquirySupportElems</code> is a self-populating creator / immutable aggregate of (i) additional data store elements and (ii) additional table questions (for addition to the
 * {@link ResultsTableType#MAIN_ENQUIRY_RESULTS main} Enquiry results table) required to support the use of the jQuery "dataTable" widget to present "tree" Enquiry(ies).<p> 
 *
 * @author Simon Hayes
 */
public class TreeEnquirySupportElems
{
	private static final String JQUERY_DATATABLE_CONTROL_GROUP_NAME = "JQueryDataTableControl";
	
	/**
	 * A sub-group named {@value TreeEnquirySupportElems#JQUERY_DATATABLE_CONTROL_GROUP_NAME} of the "display result group" for the {@link ResultsTableType#MAIN_ENQUIRY_RESULTS main}
	 * Enquiry results table containing additional data items needed to support the jQuery dataTable widget.
	 */
	public final PropertyGroupWrapper jQueryDataTableControlGroup;
	
	/**
	 * <code>breakChangeValueItemForJQueryDataTable</code> is a child item of {@link #jQueryDataTableControlGroup} group, which is itself part of the "display result group" for the
	 * {@link ResultsTableType#MAIN_ENQUIRY_RESULTS main} Enquiry results table.<p>
	 * 
	 *  This item provides the column values for the 1st column of that table (which is the one that acts as the "input" for the jQuery dataTable's "row grouping" functionality). Note that
	 *  the jQuery data widget will automatically <u>hide</u> this column once it's done its row-grouping stuff.<p>
	 *  
	 *  For every instance of the containing display result group, this item needs to be populated with a non-empty value, this being that for the top-level break field (i.e. the
	 *  one whose values represent the top-level group-by). Note that currently (due to limitations of jQuery dataTable widget - or possibly our understanding of it ?) we currently
	 *  only support 1 level of expand/collapsibility.
	 *  
	 *  (The reason we have to go through this malarky is because the IRIS response doesn't currently include values for those break fields).
	 */
	public final PropertyWrapper breakChangeValueItemForJQueryDataTable;
	
	/**
	 * <code>dummyColumnValueItemForJQueryDataTable</code> is a sibling data item of {@link #breakChangeValueItemForJQueryDataTable}, and backs the dummy table column question
	 * ({@link #dummyColumnValueItemForJQueryDataTable}) that appears immediately to the right of {@link #breakChangeValueQuestionForJQueryDataTable}.<p>
	 * 
	 * For at least one instance of the display result group, this item needs to be populated with something harmless (e.g. "&nbsp;") to prevent edgeConnect from failing to display
	 * the table column at all.<p>
	 * 
	 * NB: AFIK, the only reason we need {@link #dummyColumnValueItemForJQueryDataTable} (and hence this data item) all is for its column title, which gets "adopted" as the title
	 * for the leftmost table column once the jQuery dataTable widget has done its stuff and hidden the original leftmost column ({@link #breakChangeValueQuestionForJQueryDataTable}). 
	 */
	public final PropertyWrapper dummyColumnValueItemForJQueryDataTable;
	
	/**
	 * A table question backed by {@link #breakChangeValueItemForJQueryDataTable} (see above).<p>
	 * 
	 * Note that the <code>FormTableWrapper</code> within this wrapper-pair is configured as an <u>editable</u> (i.e. non read-only) question. (This turns out to be crucial to the
	 * correct functioning of the jQuery dataTable widget, which is looking for <input> elems in the table column specified by this table question).<p>
	 *  
	 * <b>IMPORTANT:</b> This class does <u>NOT<u> take responsibility for actually attaching this to the main enuiry results table (where it needs to be inserted as the 1st column question).  
	 */
	public final QuestionWrapperPair breakChangeValueQuestionForJQueryDataTable;
	
	/**
	 * A table question backed by {@link #dummyColumnValueItemForJQueryDataTable} (see above).<p>
	 */
	public final QuestionWrapperPair dummyColumnQuestionForJQueryDataTable;
	
	public TreeEnquirySupportElems(BasicProject p_project, PropertyGroupWrapper p_mainDisplayResultGroup)
	{
		AssertionUtils.requireNonNull(p_mainDisplayResultGroup, "p_mainDisplayResultGroup");
		
		final FormContext  formContext = p_project.getFormContext();

		jQueryDataTableControlGroup = PropertyGroupWrapper.create(formContext, JQUERY_DATATABLE_CONTROL_GROUP_NAME);
		p_mainDisplayResultGroup.unwrap().addChild(jQueryDataTableControlGroup.unwrap(), 0);
		
		breakChangeValueItemForJQueryDataTable = PropertyWrapper.create(formContext, "BreakChangeValue");
		jQueryDataTableControlGroup.addChild(breakChangeValueItemForJQueryDataTable);
		
		dummyColumnValueItemForJQueryDataTable = PropertyWrapper.create(formContext, "DummyColumnValue");
		jQueryDataTableControlGroup.addChild(dummyColumnValueItemForJQueryDataTable);
		
		final QuestionWrapper breakChangeValueQuestion = QuestionWrapper.create(formContext);
		breakChangeValueQuestion.setQuestionText("$%SLANG Enquiry.BreakChangeValueLabel [Break-change value for jQuery dataTable]$");
		breakChangeValueQuestion.setPropertyKey(breakChangeValueItemForJQueryDataTable);
		breakChangeValueQuestion.setMandatory(Boolean.FALSE);
		breakChangeValueQuestion.setReadOnly(Boolean.FALSE); /* because jQuery dataTable stuff will be looking for <input> elems */
		
		final RichHTMLPresentationQuestionWrapper breakChangeValuePresQuestion = RichHTMLPresentationQuestionWrapper.create(formContext, breakChangeValueQuestion.unwrap()); 
		ResultsTableQuestionStyleOption.STANDARD.applyTo(breakChangeValuePresQuestion);
		
		breakChangeValueQuestionForJQueryDataTable = new QuestionWrapperPair(breakChangeValueQuestion, breakChangeValuePresQuestion);
		
		final QuestionWrapper dummyColumnValueQuestion = QuestionWrapper.create(formContext);
		dummyColumnValueQuestion.setQuestionText("[Dummy column for jQuery dataTable]");
		dummyColumnValueQuestion.setPropertyKey(dummyColumnValueItemForJQueryDataTable);
		dummyColumnValueQuestion.setMandatory(Boolean.FALSE);
		dummyColumnValueQuestion.setReadOnly(Boolean.TRUE);
		
		final RichHTMLPresentationQuestionWrapper dummyColumnValuePresQuestion = RichHTMLPresentationQuestionWrapper.create(formContext, dummyColumnValueQuestion.unwrap());
		ResultsTableQuestionStyleOption.STANDARD.applyTo(dummyColumnValuePresQuestion);
		
		dummyColumnQuestionForJQueryDataTable = new QuestionWrapperPair(dummyColumnValueQuestion, dummyColumnValuePresQuestion);
	}
}
