package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper.EAnswerJustification;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper.EJustification;

import com.odcgroup.t24.enquiry.enquiry.AlignmentKind;

/**
 * <code>AlignmentKindMapper</code> provides static utility methods for mapping Enquiry DSL field alignment values (<code>AlignmentKind</code>) to the enumerated constants
 * expected by <code>RichHTMLPresentationQuestionWrapper</code>'s <code>setJustification(EJustification)</code> and <code>setAnswerJustification(EAnswerJustification)</code>
 * methods.
 *
 * @author	Simon Hayes
 */
public abstract class AlignmentKindMapper
{
	public static EJustification toEJustification(AlignmentKind p_alignmentKind, EJustification p_default)
	{
		final EJustification result = toEJustification(p_alignmentKind);
		return (result == null) ? p_default : result;
	}
	
	public static EJustification toEJustification(AlignmentKind p_alignmentKind)
	{
		if (! ((p_alignmentKind == null)|| (p_alignmentKind == AlignmentKind.UNSPECIFIED)))
		{
			switch (p_alignmentKind)
			{
				case NONE: return EJustification.NONE;
				case LEFT: return EJustification.LEFT;
				case RIGHT: return EJustification.RIGHT;
				
				default: throw new RuntimeException("Unhandled AlignmentKind enum constant: " + p_alignmentKind);
			}
		}
		
		return null;
	}
	
	public static EAnswerJustification toEAnswerJustification(AlignmentKind p_alignmentKind, EAnswerJustification p_default)
	{
		final EAnswerJustification result = toEAnswerJustification(p_alignmentKind);
		return (result == null) ? p_default : result;
	}
	
	public static EAnswerJustification toEAnswerJustification(AlignmentKind p_alignmentKind)
	{
		if (! ((p_alignmentKind == null) || (p_alignmentKind == AlignmentKind.UNSPECIFIED)))
		{
			switch(p_alignmentKind)
			{
				case NONE: return EAnswerJustification.NONE;
				case LEFT: return EAnswerJustification.LEFT;
				case RIGHT: return EAnswerJustification.RIGHT;
				
				default: throw new RuntimeException("Unhandled AlignmentKind enum constant: " + p_alignmentKind);
			}
		}
		
		return null;
	}
}
