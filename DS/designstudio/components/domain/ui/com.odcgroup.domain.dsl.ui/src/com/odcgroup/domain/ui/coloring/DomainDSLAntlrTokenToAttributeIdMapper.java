package com.odcgroup.domain.ui.coloring;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

import com.odcgroup.domain.ui.coloring.DomainDSLHighlightingConfiguration;

public class DomainDSLAntlrTokenToAttributeIdMapper extends
		DefaultAntlrTokenToAttributeIdMapper {
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		if("RULE_ML_DOC".equals(tokenName)) {
			return DomainDSLHighlightingConfiguration.MDF_MODEL_DOCUMENTATION;
		}
		if("RULE_ID".equals(tokenName)) {
			return DomainDSLHighlightingConfiguration.MDF_MODEL_ELEMENT;
		}
		return super.calculateId(tokenName, tokenType);
	}
}
