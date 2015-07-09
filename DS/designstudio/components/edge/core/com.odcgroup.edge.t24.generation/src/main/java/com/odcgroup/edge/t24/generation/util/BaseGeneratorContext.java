package com.odcgroup.edge.t24.generation.util;

import gen.com.acquire.intelligentforms.entities.DataStructureWrapper;
import gen.com.acquire.intelligentforms.entities.DictionaryRootWrapper;
import gen.com.acquire.intelligentforms.entities.FormListWrapper;
import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.ProjectWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTypeWrapper;

import javax.naming.NameNotFoundException;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.data.DataDictionary;
import com.acquire.intelligentforms.data.SessionDictionary;
import com.acquire.intelligentforms.entities.DataStructure;
import com.acquire.intelligentforms.entities.FormList;
import com.acquire.intelligentforms.entities.GenericLeafNode;
import com.acquire.intelligentforms.entities.Property;
import com.acquire.intelligentforms.entities.RulesRoot;
import com.acquire.intelligentforms.entities.root.ListsRoot;
import com.acquire.intelligentforms.entities.root.StructuresRoot;
import com.acquire.intelligentforms.entities.root.TypesRoot;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.BasicEdgeMapperProject;
import com.odcgroup.edge.t24.generation.EdgeMapper;

/**
 * <code>BaseGeneratorContext</code> provides an abstract base for context object classes representing particular "template" IFPs that are read by generators to act as
 * the starting point for the dynamically generated IFPs they produce. 
 *
 * @author Simon Hayes
 */
public abstract class BaseGeneratorContext {
	final BasicEdgeMapperProject m_basicProject;
	final TemplateProject m_templateProject;
	final FormContext m_formContext;
	final ProjectWrapper m_project;
	final RichHTMLPresentationTypeWrapper m_richHTMLPresentationType;
	final String m_projectName;
	final TypesRoot m_typesRoot;
	final RulesRoot m_rulesRoot;
	final SessionDictionary m_sessionDictionary;
	final DataDictionary m_dataDictionary;
	final DictionaryRootWrapper m_dictionaryRoot;
	
	public BaseGeneratorContext(BasicEdgeMapperProject p_project, TemplateProject p_templateProject)
	{
		m_basicProject = AssertionUtils.requireNonNull(p_project, "p_project");
		m_templateProject = AssertionUtils.requireNonNull(p_templateProject, "p_templateProject");
		m_formContext = m_templateProject.getFormContext();
		m_project = m_basicProject.getProject();
		m_projectName = m_project.getName();
		m_richHTMLPresentationType = m_templateProject.getRichPresentationType();
		m_typesRoot = m_project.unwrap().getTypesRoot();
		m_rulesRoot = m_project.getRulesRoot().unwrap();
		m_sessionDictionary = m_basicProject.getSessionDictionary();
		m_dataDictionary = m_sessionDictionary.getDataDictionary();
		m_dictionaryRoot = DictionaryRootWrapper.wrap(m_dataDictionary.getDictionaryRoot());
	}
	
	protected BaseGeneratorContext(BaseGeneratorContext p_other)
	{
		AssertionUtils.requireNonNull(p_other, "p_other");
		m_basicProject = p_other.m_basicProject;
		m_templateProject = p_other.m_templateProject;
		m_formContext = p_other.m_formContext;
		m_project = p_other.m_project;
		m_projectName = p_other.m_projectName;
		m_richHTMLPresentationType = p_other.m_richHTMLPresentationType;
		m_typesRoot = p_other.m_typesRoot;
		m_rulesRoot = p_other.m_rulesRoot;
		m_sessionDictionary= p_other.m_sessionDictionary;
		m_dataDictionary = p_other.m_dataDictionary;
		m_dictionaryRoot = p_other.m_dictionaryRoot;
	}
	
	public EdgeMapper<?> getEdgeMapper()
    {
        return m_basicProject.getEdgeMapper();
    }

	public TemplateProject getTemplateProject()
	{
		return m_templateProject;
	}
	
	public FormContext getFormContext()
	{
		return m_formContext;
	}

	public ListsRoot getListsRoot()
	{
		return m_typesRoot.getLists();
	}
	
	public StructuresRoot getStructuresRoot()
	{
		return m_typesRoot.getStructures();
	}
	
	public DictionaryRootWrapper getDictionaryRoot()
	{
		return m_dictionaryRoot;
	}
	
	public RulesRoot getRulesRoot()
	{
		return m_rulesRoot;
	}
	
	public <RuleType extends GenericLeafNode> RuleType getMandatoryTemplateDefinedTopLevelRule(String p_ruleName, Class<RuleType> p_ruleClass) throws Exception
	{
		final RuleType result = m_rulesRoot.getChildWithName(p_ruleName, p_ruleClass);
		
		if (result == null)
			throw new Exception("Template project: " + m_projectName + ".ifp doesn't define top-level " + p_ruleClass.getSimpleName() + ": " + p_ruleName + " !");
		
		return result;
	}
	
	public PhaseWrapper getMandatoryTemplateDefinedPhase(String p_fullPhaseName) throws Exception
	{
		AssertionUtils.requireNonNullAndNonEmpty(p_fullPhaseName, "p_fullPhaseName");
		
		final PhaseWrapper phase = m_templateProject.getPhase(p_fullPhaseName);
		
		if (phase == null)
			throw new Exception("Template project: " + m_projectName + ".ifp doesn't define phase: " + p_fullPhaseName);
		
		return phase;
	}
	
	public PresentationPhaseWrapper getMandatoryTemplateDefinedPresentationPhase(PhaseWrapper p_phase) throws Exception
	{
		AssertionUtils.requireNonNull(p_phase, "p_phase");
		return m_templateProject.getPresentationPhase(m_richHTMLPresentationType, p_phase);
	}
	
	public PropertyWrapper getMandatoryTemplateDefinedProperty(String p_itemPath) throws Exception
	{
		AssertionUtils.requireNonNullAndNonEmpty(p_itemPath, "p_itemPath");
		
		final Property property = m_sessionDictionary.getDataDictionary().getProperty(p_itemPath, false);
		
		if (property == null)
			throw new Exception("Template project: " + m_projectName + ".ifp doesn't define data store item: " + p_itemPath + " !");
		
		return PropertyWrapper.wrap(property);
	}	

	public PropertyGroupWrapper getMandatoryTemplateDefinedPropertyGroup(String p_groupPath) throws Exception
	{
		AssertionUtils.requireNonNullAndNonEmpty(p_groupPath, "p_groupPath");
		
		try
		{
			return PropertyGroupWrapper.wrap(m_sessionDictionary.getDataDictionary().getPropertyGroup(p_groupPath));
		}
		
		catch (NameNotFoundException nnfe)
		{
			throw new Exception("Template project: " + m_projectName + ".ifp doesn't define data store group: " + p_groupPath + " !");
		}
	}
	
	public FormListWrapper getMandatoryTemplateDefinedListType(String p_listPath) throws Exception {
		AssertionUtils.requireNonNullAndNonEmpty(p_listPath, "p_listPath");
		
		final FormList formList = m_formContext.getEntityofType(p_listPath, FormList.class);
		
		if (formList == null)
			throw new Exception("Template project: " + m_projectName + ".ifp doesn't define list type: " + p_listPath + " !");
		
		return FormListWrapper.wrap(formList);
	}
	
	public DataStructureWrapper getMandatoryTemplateDefinedStructure(String p_structureName) throws Exception
	{
		AssertionUtils.requireNonNullAndNonEmpty(p_structureName, "p_structureName");
		
		final DataStructure dataStructure = m_project.unwrap().getTypesRoot().getStructures().getChildWithName(p_structureName, DataStructure.class);
		
		if (dataStructure == null)
			throw new Exception("Template project: " + m_projectName + ".ifp doesn't define data structure: " + p_structureName + " !");
		
		return DataStructureWrapper.wrap(dataStructure);
	}
}
