package com.odcgroup.t24.version.formatting;

import java.util.List;
import java.util.Set;

import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

import com.odcgroup.t24.version.services.VersionDSLGrammarAccess;
import com.odcgroup.workbench.dsl.naming.formatting.AbstractDataDSLFormatter;

/**
 * Xtext Formatter for Version.
 *
 * See http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it
 *
 * @see AbstractDataDSLFormatter
 */
public class VersionDSLFormatter extends
		AbstractDataDSLFormatter<VersionDSLGrammarAccess> {

	@Override
	protected void configureSpecificFormatting(FormattingConfig c,
			VersionDSLGrammarAccess g) {
		c.setLinewrap().before(g.getML_COMMENTRule());
		c.setLinewrap().after(g.getML_COMMENTRule());

		final Set<String> allKeywords = GrammarUtil.getAllKeywords(grammar
				.getGrammar());
		List<Keyword> keywords = grammar.findKeywords(allKeywords
				.toArray(new String[allKeywords.size()]));
		for (final Keyword keyword : keywords) {
			String name = keyword.getValue();
			if (name.endsWith(":")) { //
				c.setLinewrap().before(keyword);
			}
		}

		// usage of parenthesis in custom selections
		String[] parenthesis = { "{", "}" };
		for (Keyword keyword : g.findKeywords(parenthesis)) {
			if(keyword.getValue().equals("{")) {
				c.setLinewrap().after(keyword);
			}
			else {
				c.setLinewrap().before(keyword);
				c.setLinewrap(2).after(keyword);
			}
		}

		String[] specialKeywords = { "Presentation", "IB", "TransactionFlow", "Fields", "API", "WebServices", "Connect"};
		keywords = g.findKeywords(specialKeywords);
		for (final Keyword keyword : keywords) {
			c.setLinewrap(2).before(keyword);
		}

		c.setIndentationIncrement().before(g.getVersionAccess().getT24NameKeyword_4_0());
		c.setIndentationDecrement().after(g.getVersionAccess().getT24NameKeyword_4_0());

		c.setNoLinewrap().after(g.getVersionAccess().getMetamodelVersionKeyword_5_0());// done by Satish
		c.setIndentationIncrement().before(g.getVersionAccess().getMetamodelVersionKeyword_5_0());
		c.setIndentationDecrement().after(g.getVersionAccess().getMetamodelVersionKeyword_5_0());

		c.setIndentationIncrement().before(g.getVersionAccess().getNumberOfAuthorisersKeyword_7_0());
		c.setIndentationDecrement().after(g.getVersionAccess().getNumberOfAuthorisersKeyword_7_0());

		c.setIndentationIncrement().before(g.getVersionAccess().getDescriptionKeyword_8_0());
		c.setIndentationDecrement().after(g.getVersionAccess().getDescriptionKeyword_8_0());

		c.setLinewrap().around(g.getVersionAccess().getHeaderKeyword_10_9_0());
		c.setLinewrap().around(g.getVersionAccess().getHeaderAssignment_10_9_1());

		c.setIndentationIncrement().before(g.getFieldAccess().getLabelKeyword_4_1_0());
		c.setIndentationDecrement().after(g.getFieldAccess().getLabelKeyword_4_1_0());

		c.setIndentationIncrement().before(g.getFieldAccess().getToolTipKeyword_4_2_0());
		c.setIndentationDecrement().after(g.getFieldAccess().getToolTipKeyword_4_2_0());

		c.setIndentationIncrement().before(g.getJavaRoutineAccess().getJavaKeyword_0());
		c.setIndentationDecrement().after(g.getJavaRoutineAccess().getJavaKeyword_0());

		c.setIndentationIncrement().before(g.getJBCRoutineAccess().getJBCKeyword_0());
		c.setIndentationDecrement().after(g.getJBCRoutineAccess().getJBCKeyword_0());
	}

}