package com.odcgroup.t24.enquiry.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.antlr.SyntaxErrorMessageProvider;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.Graph;
import com.odcgroup.t24.enquiry.enquiry.Security;


/**
 * customized error messages for enquiry model
 * 
 * @author phanikumark
 *
 */
public class EnquirySyntaxErrorMessageProvider extends SyntaxErrorMessageProvider {

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(IParserErrorContext context) {
		EObject currContext = context.getCurrentContext();
		String msg = null;
		if (currContext instanceof Enquiry) {
			String defaultMsg = context.getDefaultMessage();
			String prop = null;
			if (defaultMsg.contains("expecting")) {
				int index = defaultMsg.indexOf("expecting")+9;
				prop = defaultMsg.substring(index).trim();
			}
			if(prop != null)  {
				msg = "Property "+prop+" is required";
			}
		} else if (currContext instanceof EnquiryHeader) {
			msg = "Header requires both column and line properties";
		} else if (currContext instanceof Security) {
			msg	= "Security requires property sets: application, field, abort";	
		} else if (currContext instanceof Graph) {
			msg = "Graph requires property sets: type, lable, dimension, margins, scale, legend, x-axis, y-axis";
		}
		
		if (msg != null) {
			return new SyntaxErrorMessage(msg, Diagnostic.SYNTAX_DIAGNOSTIC);
		}
		return super.getSyntaxErrorMessage(context);
	}

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(
			IValueConverterErrorContext context) {
		return super.getSyntaxErrorMessage(context);
	}


}
 