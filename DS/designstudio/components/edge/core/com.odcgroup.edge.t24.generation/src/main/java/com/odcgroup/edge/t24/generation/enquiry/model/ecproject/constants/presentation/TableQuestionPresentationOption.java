package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;

/**
 * The <code>TableQuestionPresentationOption</code> interface formalizes the idea that anything claiming to be a "table question presentation option" ought to know how to
 * {@link Appliable#applyTo(Object) apply} itself to a target <code>RichHTMLPresentationQuestionWrapper</code>.
 *
 * @author	Simon Hayes
 */
public interface TableQuestionPresentationOption extends QuestionPresentationOption { }
