package com.odcgroup.edge.t24.generation.languagemaps;

import com.acquire.intelligentforms.entities.FormList;
import com.acquire.intelligentforms.entities.GenericAttributeNode;
import com.acquire.intelligentforms.entities.ListItem;
import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class LanguageMapGenerationModel extends com.acquire.intelligentforms.languagemaps.LanguageMapGenerationModel {
	private final T24TranslationLookup m_t24TranslationLookup;
	
	LanguageMapGenerationModel(T24TranslationLookup p_t24TranslationLookup)
	{
		m_t24TranslationLookup = AssertionUtils.requireNonNull(p_t24TranslationLookup, "p_t24TranslationLookup");
	}
	
  
	@Override
	public void addListItemRecord(
	    FormList p_parentList, 
        ListItem p_listItem, 
        boolean p_remove, 
        String p_key, 
        String p_projectValue, 
        String p_mapValue,
        EMappingState p_valueMappingState, 
        String p_projectGroupValue, 
        String p_mapGroupValue, 
        EMappingState p_groupMappingState, 
        Object p_sourceData
        )
	{
	    final ListRecord listRecord = super.getListRecords().get(p_parentList.getEID());
        
        if (listRecord == null)
            return;
        
        final Language targetLanguage = TargetTranslationLanguageSet.getTargetTranslationLanguageByISOCode(getTargetLanguage());
        
        if (targetLanguage == null)
            return;
        
        final String englishValue = p_listItem.getValue();
        final String translatedValue = SupplementaryTranslationsManager.getTranslation(englishValue, targetLanguage.isoCode);
        
        listRecord.addListItemRecord(p_listItem, p_remove, p_key, englishValue, translatedValue, p_valueMappingState, p_projectGroupValue, p_mapGroupValue, p_groupMappingState, p_sourceData );
	}

	@Override
	public void addAttributeRecord(
		GenericAttributeNode p_entity,
		String p_attributeName,
		GenericAttributeNode p_entityUsingAttribute,
		String p_projectValue,
		String p_mapValue,
		EMappingState p_state,
		EMappingType p_mappingType,
		Notes p_notes,
		Object p_sourceData
	) {
		String translatedValue = m_t24TranslationLookup.getT24Translation(p_entity, p_attributeName, p_projectValue, getTargetLanguage());
		super.addAttributeRecord(p_entity, p_attributeName, p_entityUsingAttribute, p_projectValue, translatedValue, p_state, p_mappingType, p_notes, p_sourceData);
	}
}
