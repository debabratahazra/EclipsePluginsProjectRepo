package com.odcgroup.edge.t24.generation.languagemaps;

import gen.com.acquire.intelligentforms.entities.FormButtonWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationTabPaneWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationTypeWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.HTMLPresentationTableWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetLanguageMapRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetLanguageMapRuleWrapper.ENotfoundaction;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.FormButton;
import com.acquire.intelligentforms.entities.GenericAttributeNode;
import com.acquire.intelligentforms.entities.Heading;
import com.acquire.intelligentforms.entities.Question;
import com.acquire.intelligentforms.entities.presentation.PresentationTabPane;
import com.acquire.intelligentforms.entities.presentation.html.HTMLPresentationQuestion;
import com.acquire.intelligentforms.entities.presentation.html.HTMLPresentationTable;
import com.acquire.intelligentforms.languagemaps.filehandlers.ILanguageMapFileProcessor;
import com.acquire.intelligentforms.languagemaps.filehandlers.XMLLanguageMapFileV1;
import com.acquire.intelligentforms.rules.LoadLanguageMapRule;
import com.acquire.intelligentforms.rules.SetLanguageMapRule;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.GenericAttributeNodeWrapper;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.util.FileSystemUtils;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.translation.translationDsl.Translations;
import com.temenos.connect.T24Browser.LoadISOLanguageMapsRule;

/**
 * A <code>LanguageMapsHelper</code> is a single-use, throwaway strategy object for (a) keeping track of the T24-defined <code>Translations</code> objects from which a given IFP generator instance extracts
 * the English display/help/hint texts for the {@link Question}(s) and {@link Heading}(s) it creates, and then - once all such entities have been created - (b) auto-generating edgeConnect language map files
 * for each language defined by {@link TargetTranslationLanguageSet}.<p>
 * 
 * In outline, the usage pattern (fron the perspective of an IFP-generator invoked to generate a given IFP) is as follows:<p>
 * 
 * <ol>
 *     <li>
 *         Prior to creating any content for the target IFP, {@link #LanguageMapHelper(TemplateProject, File) create} a new <code>LanguageMapHelper</code> instance.
 *     </li>
 *     <li>
 *         As part of the creation of any <code>*Wrapper</code> object having translatable values sourced from a T24 {@link Translations} object, call the appropriate
 *         <code>registerT24*TextTranslations</code> method on the <code>LanguageMapHelper</code> instance created above.
 *     </li>
 *     <li>
 *         Finally (once the in-memory model of the target IFP is complete), call {@link #generateLanguageMapsAndRules(ContainerRuleWrapper, PhaseWrapper)} on the
 *         <code>LanguageMapHelper</code> instance to provoke:<p>
 *         <ul>
 *             <li>
 *                 the generation of the physical language map files (which are stored to sub-directory: <b>languages</b> which is automatically created under the
 *                 top-level project output directory)
 *             </li>
 *             <li>
 *                 creation / addition of relevant {@link LoadLanguageMapRule}(s) to the supplied start-up rules container <i>(note that it's the caller's responsibility
 *                 for set this as the top-level start-up rule on the generated edgeConnect project)</i>
 *             </li>
 *             <li>
 *                 creation / addition of a rule (to specified initial phase) to test whether there's a language map for the logged-in user's language, and if so, to
 *                 invoke an appropriate {@link SetLanguageMapRule} to set that as the active language map 
 *             </li>
 *     </li>
 * </ol>
 *
 * @author Simon Hayes
 */
public class LanguageMapHelper implements T24TranslationLookup
{
	private static String LANGUAGE_MAPS_SUBDIR_NAME = "languages";
	private static boolean TARGET_TRANSLATION_LANGUAGES_FOUND = ! TargetTranslationLanguageSet.isEmpty();
    private static final Logger LOGGER = GenLogger.getLogger(LanguageMapHelper.class);
	private static final ILanguageMapFileProcessor LANGUAGE_MAP_FILE_PROCESSOR = new XMLLanguageMapFileV1();
	
	private final Map<GenericAttributeNode,AttributeNameToTranslationsMap> m_attributeToTranslationsMapByGenericAttributeNode;
	private final FormContext m_formContext;
	private final File m_languageMapsOutputDir;
	private final PresentationTypeWrapper m_targetPresentationType;

	public LanguageMapHelper(TemplateProject p_templateProject, File p_projectDirectory) throws Exception
	{
		AssertionUtils.requireNonNull(p_templateProject, "p_templateProject");
		AssertionUtils.requireNonNull(p_projectDirectory, "p_projectDirectory");
		
		m_attributeToTranslationsMapByGenericAttributeNode = new HashMap<GenericAttributeNode,AttributeNameToTranslationsMap>();
		m_formContext = AssertionUtils.requireNonNull(p_templateProject.getFormContext(), "p_templateProject.getFormContext()");
		m_targetPresentationType = p_templateProject.getRichPresentationType();
		m_languageMapsOutputDir = new File(p_projectDirectory, LANGUAGE_MAPS_SUBDIR_NAME);

		FileSystemUtils.findOrCreateMandatoryDirectory(m_languageMapsOutputDir);
	}
	
	public void registerT24TextTranslations(QuestionWrapper p_question, TextTranslations p_translations)
	{
		if (TARGET_TRANSLATION_LANGUAGES_FOUND)
		{
			AssertionUtils.requireNonNull(p_question, "p_question");
			putNodeTranslations(p_question, HTMLPresentationQuestion.ATTR_DISPLAY_TEXT, p_translations);
		}
	}
	
	// TODO: Check this is valid!
	public void registerT24TextTranslations(PresentationQuestionWrapper p_presQuestion, TextTranslations p_translations)
    {
        if (TARGET_TRANSLATION_LANGUAGES_FOUND)
        {
            AssertionUtils.requireNonNull(p_presQuestion, "p_question");
            putNodeTranslations(p_presQuestion, HTMLPresentationQuestion.ATTR_DISPLAY_TEXT, p_translations);
        }
    }
	
	
	public void registerT24TextTranslations(HeadingWrapper p_heading, TextTranslations p_translations)
	{
		if (TARGET_TRANSLATION_LANGUAGES_FOUND)
		{		
			AssertionUtils.requireNonNull(p_heading, "p_heading");
			putNodeTranslations(p_heading, HTMLPresentationQuestion.ATTR_DISPLAY_TEXT, p_translations);
		}
	}

    public void registerT24TextTranslations(HTMLPresentationTableWrapper p_table, TextTranslations p_translations)
    {
        if (TARGET_TRANSLATION_LANGUAGES_FOUND)
        {       
            AssertionUtils.requireNonNull(p_table, "p_table");
            putNodeTranslations(p_table, HTMLPresentationTable.ATT_TABLE_SUMMARY, p_translations);
        }
    }

	public void registerT24TextTranslations(PresentationTabPaneWrapper p_tabPane, TextTranslations p_translations)
	{
		if (TARGET_TRANSLATION_LANGUAGES_FOUND)
		{
			AssertionUtils.requireNonNull(p_tabPane, "p_tabPane");
			putNodeTranslations(p_tabPane, PresentationTabPane.ATT_TAB_NAME, p_translations);
		}
	}
	
	public void registerT24HintTextTranslations(QuestionWrapper p_question, TextTranslations p_translations)
	{
		if (TARGET_TRANSLATION_LANGUAGES_FOUND)
		{		
			AssertionUtils.requireNonNull(p_question, "p_question");
			putNodeTranslations(p_question, Question.ATTR_HINT_TEXT, p_translations);
		}
	}
	
	public void registerT24HintTextTranslations(HeadingWrapper p_heading, TextTranslations p_translations)
	{
		if (TARGET_TRANSLATION_LANGUAGES_FOUND)
		{
			AssertionUtils.requireNonNull(p_heading, "p_heading");
			putNodeTranslations(p_heading, Heading.ATTR_HINT_TEXT, p_translations);
		}
	}
	
	public void registerT24HelpTextTranslations(QuestionWrapper p_question, TextTranslations p_translations)
	{
		if (TARGET_TRANSLATION_LANGUAGES_FOUND)
		{		
			AssertionUtils.requireNonNull(p_question, "p_question");
			putNodeTranslations(p_question, Question.ATTR_HELP_TEXT, p_translations);
		}
	}
	
	public void registerT24HelpTextTranslations(HeadingWrapper p_heading, TextTranslations p_translations)
	{
		if (TARGET_TRANSLATION_LANGUAGES_FOUND)
		{		
			AssertionUtils.requireNonNull(p_heading, "p_heading");
			putNodeTranslations(p_heading, Heading.ATTR_HELP_TEXT, p_translations);
		}
	}

    public void registerT24HintTextTranslations(FormButtonWrapper p_button, TextTranslations p_translations)
    {
        if ( TARGET_TRANSLATION_LANGUAGES_FOUND )
        {
            AssertionUtils.requireNonNull( p_button, "p_button" );
            putNodeTranslations( p_button, FormButton.ATTR_HINT_TEXT, p_translations );
        }
    }

	public void generateLanguageMapsAndRules(ContainerRuleWrapper p_startupRulesContainer, PhaseWrapper p_initialPhase) throws Exception
	{
		if (! TARGET_TRANSLATION_LANGUAGES_FOUND)
			return;
		
		AssertionUtils.requireNonNull(p_startupRulesContainer, "p_startupRulesContainer");
		AssertionUtils.requireNonNull(p_initialPhase, "p_initialPhase");
		
		final ContainerRuleWrapper loadLanguageMapsContainerRule = ContainerRuleWrapper.create(m_formContext, "Load language maps");
		p_startupRulesContainer.addTrueRule(loadLanguageMapsContainerRule.unwrap());
		
        
        // Create the "Load ISO Language Maps" Load ISO Language Maps Rule.
        //
        final LoadISOLanguageMapsRule loadIsoLanguageMaps = new LoadISOLanguageMapsRule( m_formContext );

        loadIsoLanguageMaps.setAttribute( "Name", "Load ISO Language Maps" );
        loadIsoLanguageMaps.setAttribute( "ErrorHandlingType", "Pass Up");
        loadIsoLanguageMaps.setAttribute( "LanguageMapFolder", "$$LIBRARY_PATH$/" + LANGUAGE_MAPS_SUBDIR_NAME );
        loadIsoLanguageMaps.setAttribute( "LanguageMapExtension", "xml" );

        loadLanguageMapsContainerRule.addTrueRule(loadIsoLanguageMaps);

		final String targetPresentationTypeName = (m_targetPresentationType == null) ? null : m_targetPresentationType.getName();
		final Iterator<Language> targetLanguageIter = TargetTranslationLanguageSet.iterator();
		final Set<Language> successfullyMappedLanguages = new TreeSet<Language>();
		
		while(targetLanguageIter.hasNext())
		{
			final Language targetLanguage = targetLanguageIter.next();
			final String languageMapFilename = targetLanguage.isoCode + ".xml";
			final File languageMapFile = new File(m_languageMapsOutputDir, languageMapFilename);
			final LanguageMapGenerationModel model = new LanguageMapGenerationModel(this);
			
			model.generateFromContext(
				m_formContext,
				null, /* p_fileName */
				"" /* p_comment */,
				targetPresentationTypeName,
				"en-US" /* p_sourceLanguage */,
				targetLanguage.isoCode.value,
				null /* p_includedEVANoteTypeEIDs */,
				false
			);
			
			try {
			    LOGGER.info("BEFORE Creating language map: " + languageMapFile.getPath());
				LANGUAGE_MAP_FILE_PROCESSOR.writeLanguageMapGenerationModel(m_formContext, languageMapFile, null, model);
				LOGGER.info("AFTER Created language map: " + languageMapFile.getPath());
				successfullyMappedLanguages.add(targetLanguage);
			}
			
			catch (Exception e)
			{
				LOGGER.error("Problem writing language map file: " + languageMapFile.getPath(), e);
			}
			
		}

		final SetLanguageMapRuleWrapper setLanguageMapRule = SetLanguageMapRuleWrapper.create(m_formContext);
		setLanguageMapRule.setName("Set Language Map");
		setLanguageMapRule.setErrorHandlingType( SetLanguageMapRuleWrapper.EErrorHandlingType.PASS_UP );
		setLanguageMapRule.setLanguagemapalias("$$!LANGUAGE$");
		
		setLanguageMapRule.setPropagateToMain( false );
		setLanguageMapRule.setNotfoundaction( ENotfoundaction.DO_NOTHING );

//		evalIsLanguageMapAvailableForUserLanguage.addTrueRule(setLanguageMapRule.unwrap());
		
		p_initialPhase.addPrePhaseRule(setLanguageMapRule.unwrap());
		
	}
		
	@Override
	public String getT24Translation(GenericAttributeNode p_node, String p_attrName, String p_englishAttrValue, String p_languageCode) {
		//!! AssertionUtils.requireNonNull(p_node, "p_node");
		AssertionUtils.requireNonNullAndNonEmpty(p_languageCode, "p_languageCode");
		
		String result = null;
		
		if ((p_node != null) && (p_englishAttrValue != null) && (p_englishAttrValue.length() > 0))
		{
			final AttributeNameToTranslationsMap attrNameToTranslationsMap = m_attributeToTranslationsMapByGenericAttributeNode.get(p_node);
			
			if (attrNameToTranslationsMap != null)
			{
			    TextTranslations t24Translations = attrNameToTranslationsMap.get(p_attrName);
				
				if (t24Translations != null)
				{
			    	result = t24Translations.getText( p_languageCode );
			    }
			}
		}
		
		return result;
	}

	/**
	 * <b>IMPORTANT:</b> This constructor is <u>solely</u> for the use of delegating-wrapper / nullvar type subclasses - i.e. ones that  override <u>all</u> <code>public</code> methods of this class such that
	 * <u>none</u> of the methods on this class (as parent) will <u>ever</u> be invoked.
	 */
	protected LanguageMapHelper()
	{
		m_attributeToTranslationsMapByGenericAttributeNode = null;
		m_formContext = null;
		m_targetPresentationType = null;
		m_languageMapsOutputDir = null;
	}

	private void putNodeTranslations(GenericAttributeNodeWrapper<?> p_nodeWrapper, String p_attrName, TextTranslations p_translations)
	{
		if (p_translations != null && p_translations.hasTranslations() )
		{
			findOrCreateAttributeNameToTranslationsMap(p_nodeWrapper).put(p_attrName, p_translations);
		}
	}
	
	private AttributeNameToTranslationsMap findOrCreateAttributeNameToTranslationsMap(GenericAttributeNodeWrapper<?> p_nodeWrapper)
	{
		AttributeNameToTranslationsMap result = m_attributeToTranslationsMapByGenericAttributeNode.get(p_nodeWrapper.unwrap());
		
		if (result == null)
			m_attributeToTranslationsMapByGenericAttributeNode.put(p_nodeWrapper.unwrap(), result = new AttributeNameToTranslationsMap());
		
		return result;
	}

	@SuppressWarnings("serial")
	private static class AttributeNameToTranslationsMap extends HashMap<String,TextTranslations> { }
}
