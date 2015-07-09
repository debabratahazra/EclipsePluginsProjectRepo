package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import com.acquire.intelligentforms.FormContext;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
interface ResultsTableQuestionGenerator
{
    boolean isForSingleImageTypedField();
    
	boolean isForCollapsibleResultsTableColumnQuestion();

	QuestionWrapperPair generateResultsTableQuestion(FormContext p_formContext, EnquiryAttrsDigest p_enquiryAttrsDigest);
	
}
