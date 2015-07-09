package com.odcgroup.service.model.ui.coloring;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

public class ServiceDSLAntlrTokenToAttributeIdMapper extends
		DefaultAntlrTokenToAttributeIdMapper {
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		if("RULE_ML_DOC".equals(tokenName)) {
			return ServiceDSLHighlightingConfiguration.SERVICE_MODEL_DOCUMENTATION;
		}
		if("RULE_ID".equals(tokenName)) {
			return ServiceDSLHighlightingConfiguration.SERVICE_MODEL_ELEMENT;
		}
		return super.calculateId(tokenName, tokenType);
	}
}
