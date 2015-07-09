package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;

/**
 * The <code>QuestionPresentationOption</code> interface formalizes the idea that anything claiming to be a "question presentation option" ought to know how to
 * {@link Appliable#applyTo(Object) apply} itself to a target <code>RichHTMLPresentationQuestionWrapper</code>.
 *
 * @author  Simon Hayes
 */
public interface QuestionPresentationOption extends Appliable<RichHTMLPresentationQuestionWrapper> { }
