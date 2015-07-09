package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;

import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;


/**
 * The <code>ColumnDesignOption</code> interface formalizes the idea that anything claiming to be a "column design option" ought to know how to {@link Appliable#applyTo(Object) apply} itself to a
 * target <code>RichHTMLPresentationColumnBreakWrapper</code>.<p>
 * 
 * The purpose in defining this as an interface (rather than leaving implementing class / enum to declare <code>implements Appliable&lt;RichHTMLPresentationColumnBreakWrapper&gt;</code> for itself)
 * is simply that it gives us an easy way of finding classes / enums representing "column design options" within eclipse (by right clicking this interface and choosing the "Implementors" option).<p> 
 *
 * @author  Simon Hayes
 */
public interface ColumnDesignOption extends Appliable<RichHTMLPresentationColumnBreakWrapper> { }
