package com.odcgroup.edge.t24.generation.languagemaps;

import com.acquire.intelligentforms.entities.GenericAttributeNode;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public interface T24TranslationLookup {
	public String getT24Translation(GenericAttributeNode p_node, String p_attrName, String p_englishAttrValue, String p_languageCode);
}
