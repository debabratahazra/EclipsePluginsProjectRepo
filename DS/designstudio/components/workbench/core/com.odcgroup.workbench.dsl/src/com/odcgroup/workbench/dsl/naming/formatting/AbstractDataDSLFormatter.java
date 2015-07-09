package com.odcgroup.workbench.dsl.naming.formatting;

import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.util.Pair;

/**
 * Xtext Formatter for our "Data-like" DSLs.
 * 
 * This is the basis for the Formatter of the Enquiry & Version etc. DSL.
 * The Domain & Page DSL which use a slightly different syntax don't use this.  
 * 
 * @author Alain Tripod - Initial version of configureGeneralFormatting()
 * @author Michael Vorburger - Factored this out into new AbstractDataDSLFormatter from initial EnquiryFormatter
 */
public abstract class AbstractDataDSLFormatter<T extends IGrammarAccess> extends AbstractDeclarativeFormatter {

	@Override
	protected final void configureFormatting(FormattingConfig c) {
		@SuppressWarnings("unchecked")
		T g = (T) getGrammarAccess();
		
		configureGeneralFormatting(c, g);
		configureSpecificFormatting(c, g);
	}

	abstract protected void configureSpecificFormatting(FormattingConfig c, T g);

	private void configureGeneralFormatting(FormattingConfig c, IGrammarAccess g) {
		c.setAutoLinewrap(120);

		for (Pair<Keyword, Keyword> pair : g.findKeywordPairs("{", "}")) {
			c.setIndentation(pair.getFirst(), pair.getSecond());
			c.setLinewrap().after(pair.getFirst());
			c.setLinewrap().before(pair.getSecond());
			c.setLinewrap(2).after(pair.getSecond());
		}

		for (Keyword comma : g.findKeywords(",")) {
			c.setNoLinewrap().before(comma);
			c.setNoLinewrap().after(comma);
			c.setNoSpace().before(comma);
		}

		for (Keyword colon : g.findKeywords(":")) {
			c.setNoSpace().before(colon);
			c.setNoSpace().after(colon);
		}
	}

}
