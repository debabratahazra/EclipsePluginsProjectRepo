package com.odcgroup.edge.t24.generation.enquiry;

import gen.com.acquire.intelligentforms.entities.DataStructureWrapper;
import gen.com.acquire.intelligentforms.entities.FormButtonWrapper;
import gen.com.acquire.intelligentforms.entities.FormListWrapper;
import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.ListItemWrapper;
import gen.com.acquire.intelligentforms.entities.PhaseWrapper;
import gen.com.acquire.intelligentforms.entities.ProductWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.HTMLPresentationQuestionWrapper.EReadOnlyFormat;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationButtonWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationChartWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationPhaseWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTextWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTypeWrapper;
import gen.com.acquire.intelligentforms.entities.xml.XMLDataSourceWrapper;
import gen.com.acquire.intelligentforms.entities.xml.XMLMappingSetWrapper;
import gen.com.acquire.intelligentforms.entities.xml.XMLNodeMappingWrapper;
import gen.com.acquire.intelligentforms.entities.xml.XMLNodeRelationshipWrapper;
import gen.com.acquire.intelligentforms.rules.AddToListRuleWrapper;
import gen.com.acquire.intelligentforms.rules.BroadcastRuleWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;
import gen.com.acquire.intelligentforms.rules.EvaluateRuleWrapper;
import gen.com.acquire.intelligentforms.rules.ExpressionRuleWrapper;
import gen.com.acquire.intelligentforms.rules.GotoRuleWrapper;
import gen.com.acquire.intelligentforms.rules.GotoRuleWrapper.ERemainingRules;
import gen.com.acquire.intelligentforms.rules.IncrementorRuleWrapper;
import gen.com.acquire.intelligentforms.rules.QuestionInErrorRuleWrapper;
import gen.com.acquire.intelligentforms.rules.RepeatRuleWrapper;
import gen.com.acquire.intelligentforms.rules.ResetDataRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetQuestionStatusWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;
import gen.com.acquire.intelligentforms.rules.XMLMapperRuleWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NameNotFoundException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;

import com.acquire.intelligentforms.entities.DataStructure;
import com.acquire.intelligentforms.entities.FormList;
import com.acquire.intelligentforms.entities.GenericLeafNode;
import com.acquire.intelligentforms.entities.Property;
import com.acquire.intelligentforms.entities.PropertyGroup;
import com.acquire.intelligentforms.entities.RulesRoot;
import com.acquire.intelligentforms.entities.meta.ErrorHandlingItem;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestion;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacing;
import com.acquire.intelligentforms.entities.root.StructuresRoot;
import com.acquire.intelligentforms.entities.xml.XMLDataSource;
import com.acquire.intelligentforms.entities.xml.XMLMappingSet;
import com.acquire.intelligentforms.rules.ContainerRule;
import com.acquire.intelligentforms.rules.EvaluateRule;
import com.acquire.intelligentforms.rules.Rule;
import com.acquire.util.AssertionUtils;
import com.acquire.util.IntegerFactory;
import com.acquire.util.StringUtils;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.BasicEdgeMapperProject;
import com.odcgroup.edge.t24.generation.EnquiryMapper;
import com.odcgroup.edge.t24.generation.enquiry.core.EnquirySelectionFieldInfo;
import com.odcgroup.edge.t24.generation.enquiry.core.SearchParamComparisonOp;
import com.odcgroup.edge.t24.generation.enquiry.core.SearchParamDropdownInfo;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.BreakChangeControlGroupElems;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.BreakChangeSnapshotGroupElems;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.EnquiryResultsToolbarElems;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.StaticResultsScreenHeaderControlElems;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.TreeEnquirySupportElems;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.DSLGraphType;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryFieldsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.constants.FieldDisplayType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.ButtonWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.DisplayResultGroupSpec;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.DisplayResultGroupSpecCollection;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.DisplayResultsTableWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.IrisResultBreakChangeItem;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.IrisResultGroupSpec;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.IrisResultGroupWrapper;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.QuestionWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.StaticResultsScreenHeadersModel;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.TableWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.VisibleIrisResultItemSpec;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.VisualItemContainerWrapperPair;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.EnquiryStyleStrings;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.EnquiryWidgets;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableColumnHeaderDisplayOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTablePaginationOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableQuestionStyleOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget.GenericActionWidget;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget.SearchResultsTableWidget;
import com.odcgroup.edge.t24.generation.enquiry.model.iris.IrisEnquiryMetaData;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.CachedTemplateProject;
import com.odcgroup.edge.t24.generation.util.FileSystemUtils;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.RichHelper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.Axis;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.BreakKind;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.Dimension;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.Graph;
import com.odcgroup.t24.enquiry.enquiry.Label;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;
import com.odcgroup.t24.enquiry.enquiry.impl.SelectionExpressionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.connect.enquiry.EdgeListEntryDef;
import com.temenos.connect.enquiry.EdgeODataComparisonOpDef;
import com.temenos.connect.enquiry.EnquiryDisplayResultsPopulatorRule;
import com.temenos.connect.enquiry.EnquiryLastSavedSelectionRule;
import com.temenos.connect.enquiry.EnquiryResultConstants;
import com.temenos.connect.enquiry.EnquiryResultsCSVGeneratorRule;
import com.temenos.connect.enquiry.EnquirySearchUrlBuilderRule;
import com.temenos.connect.enquiry.EnquiryUrlAnalyserRule;
import com.temenos.connect.odata.IRISRequest;

/**
 * A <code>BasicEnquiryProject</code> instance is a throw-away, single-use strategy object for {@link #build() constructing} an in-memory object model of an edgeConnect IFP based on
 * a specified {@link Enquiry} and taking <code>templates/BasicEnquiryTemplate.ifp</code> as a starting point.
 *
 * @author Simon Hayes
 */
public class BasicEnquiryProject extends BasicEdgeMapperProject implements BasicEnquiryTemplateIFPConstants
{
    private static interface RuleNames
    {
    	String FIND_BUTTON_RULES = "Find button rules";
    	String EXEC_IRIS_SEARCH_URL = "Execute IrisSearchUrl";
    	String SEARCH_REQUEST_DISPATCH = "Dispatch the search request to IRIS and process the response";
    	String IRIS_REQUEST = "Call IRIS";
    	String TEST_WHETHER_ANY_RESULTS_RETURNED = "Test whether any results were returned";
    	String SET_SEARCH_OUTCOME_TO_NO_RESULTS_FOUND = "Set SearchOutcome to " + Lists.SearchOutcome.Keys.NO_RESULTS_FOUND;
    }

    private static final int DEFAULT_NO_OF_ROWS_PER_PAGE = 10;
    
    private static final int IRIS_RESULT_GROUP_INSTANCE_CAP_WHERE_BREAK_CHANGE_VALUES_UNKNOWN = 10;

    private static final Pattern REGEXP_LIVE_PATTERN_FILENAME = Pattern.compile("^([^$]+)(?:[$][A-Z]+)?(#.*)$");

    private static final String RUNTIME_ENQUIRY_DATA_DIR_EXPN = "$$PROJECTHOME$/WEB-INF/data/enquiry";
    
    // TEMPORARY {

	private static boolean DEBUG_ENABLED = "true".equals(System.getProperty("egen.options.enquiry.debugEnabled"));

	// } TEMPORARY

    private static final Logger                     LOGGER                                           = GenLogger.getLogger( BasicEnquiryProject.class );

    private static final Integer                    DEFAULT_SPACING_FOR_GROUP_WITH_PREVIOUS          = new Integer( 5 );
	private static final Integer                    FAVOURITES_SPACING_FOR_GROUP_WITH_PREVIOUS       = new Integer( 18 ); //GitHub #679

    private static final String SEARCH_PRIMARY_KEY = "Id";

    private static final String                     SERVER_FILE_RETRIEVAL_SERVLET_INVOKER_JAVASCRIPT = (
    	"$%if WorkingElements[1].FileDownloadParamsSessionAttrName != ''$\n" +
    		"<script>\n" +
    			"\tvar downloadForm = document.forms['$$NAMESPACE$fileDownloadForm'];\n" +
    			"\tdownloadForm.paramsSessionAttrName.value='$$" + DataStore.WorkingElementsGroup.ItemPaths.FILE_DOWNLOAD_PARAMS_SESSION_ATTR_NAME + "$';\n" +
    			"\tdownloadForm.submit();\n" +
	         "</script>\n" +
	     "$%endif$"
	 );

    private static final String                     RESULT_HTML_TEMPLATE_PREAMBLE = (
		"<html>\n" +
		"\t<head>\n" +
			"\t\t<style>\n" +
				"\t\t\tth {color: #FF8400; text-align: left}\n" +
				"\t\t\ttr:nth-child(even) {background: #EEEEEE}\n" +
				"\t\t\ttr:nth-child(odd) {background: #FFFFFF}\n" +
			"\t\t</style>\n" +
         "\t</head>\n" +
         "\t<body>"
    );

    private static final String                     RESULT_HTML_TEMPLATE_POSTAMBLE = (
    		"\t</body>\n" +
    	"</html>"
    );

    private final TemplateProject                   m_templateProject;
    private final RulesRoot                         m_rulesRoot;

    private final String                            m_ifpFriendlyEnquiryName;

    private final Enquiry                           m_enquiry;
    private final MdfClass                          m_enquiryApplicationMdfClass;

    private final IrisEnquiryMetaData				m_irisEnquiryMetaData;
    private final EnquiryFieldsDigest				m_enquiryFieldsDigest;
    private final EnquiryAttrsDigest                m_enquiryAttrsDigest;

    /**
     * The subset of Search Results screen elements that need to be switched into read-only mode on entry into "Auto Refresh" mode, and back to editable mode on exit.
     */
    private final Set<GenericNodeWrapper<?>>    m_autoRefreshStateSensitiveResultsScreenElems = new HashSet<GenericNodeWrapper<?>>();
    
    // NEW_RESULTS_TABLE_FLATTENING_STUFF {

    private StaticResultsScreenHeadersModel m_staticResultsScreenHeadersModel;

    private StaticResultsScreenHeaderControlElems m_staticResultsScreenHeaderControlElems;

    /**
     * This is the NEW "IrisResult" group as created by {@link #addSearchResultsTableQuestionsAndDataItems}. This is the "target" group for the version of the IRISRequest rule that is invoked
     * in the case where the runtime value of data item: WorkingElements[1].ShowFlattenedTables (m_showFlattenedTablesItem) is "Y"
     */
    private IrisResultGroupWrapper					m_irisResultGroupWrapper;

    /**
     * The following two members are initialised / used only for non "tree" (see {@link #m_treeEnquirySupportElems} below) enquiries having break-change fields
     */
    private BreakChangeControlGroupElems			m_breakChangeControlGroupElems;
    private QuestionWrapperPair                     m_newBreakChangeQWP;

    /**
     * m_treeEnquirySupportElems is initialised / used used only for "tree" enquiries - i.e. Enquiry(ies) having at least one field with with a {@link Field#getDisplaySection()} of
     * "STARTTREE" or "ENDTREE".
     */
    private TreeEnquirySupportElems					m_treeEnquirySupportElems;

    private EnquiryResultsToolbarElems              m_enquiryResultsToolbarElems;

    /**
     * The following are references to the NEW (flattened) "display" result groups backing the new "flattened" enquiry results tables created by {@link #addSearchResultsTableQuestionsAndDataItems}.
     */
    private PropertyGroupWrapper[]					m_headerDisplayResultGroups;
    private PropertyGroupWrapper					m_displayOnceDisplayResultGroup;
    private PropertyGroupWrapper					m_mainDisplayResultGroup;
    private PropertyGroupWrapper					m_footerDisplayResultGroup;
    private PropertyGroupWrapper					m_pageThrowDisplayResultGroup;
    private PropertyGroupWrapper					m_displayEndDisplayResultGroup;

    /**
     * The following is the item to be passed to {@link EnquiryDisplayResultsPopulatorRule} as the 'output status' item to capture whether or not we need to show the drilldown column
     * for given range of displayable search results.
     *
     * It is created by {@link #addResultsTablesAndSupportingDataStoreElems} if {@link #m_enquiryAttrsDigest.isEnquiryWithDrilldowns()} is true, or left null otherwise.
     */
    private PropertyWrapper                         m_anyDynamicDrilldownsToDisplayItem;

    // } NEW_RESULTS_TABLE_FLATTENING_STUFF

    private FormListWrapper                         m_allSearchParamComparisonOperatorsList;

    private LanguageMapHelper                       m_languageMapHelper;

    /*
     * Compared few enquiries and inferred the following about headers.
     * Headers seem to start with line 0. Headers can also have attributes set.
     * Headers can be of the following form
     *
     * 1. @(col,row) or @(col) -- These headers are displayed using edge heading as they can only contain static text.
     *
     * The following may have line no's Eg(1,1) or (1 ) . Fortunately, there are no related entries in header(1,+1).However footer have these
     * The headers can be application specific fields(could be @ID as well) or field extracts(Eg:AM.RM.PERF.OVERVIEW)  or selection(AM.RM.PERF.OVERVIEW) or constants(static text). Additionally in enquiry AC.INTERFACE.REPORT, operation is defined as DATE.TIME and with a header defined.
     * Operation can be calculation as well. Eg: CUST.POSITION.TAB.HP -> TOT.CUST.ASST.G as well it can be TOTAL -->TOT.CUST.LIAB.G.
     * It can be decision field as well. TOT.DISP.LIAB.G in the above enquiry.
     * 2. display section eq header Eg: LD.DEP.TODAY
     * 3. display section eq header and page
     * 4. display section eq header and new page -- CUSTOMER.POSITION.SCV--> field DISP.CUST
     * 5. Headers can also be defined with display break as once in which case it should  appear in the first page of the entire results. Eg: POS.MVMT.HISTORY
     * 6. There can also be headers with display break as none.Eg:OUTSTAT.CHQ.OUTSTG
     * Eg of a date field appears as header RG.LCS.BY.RISKPT, field D.BATCH and for D2 type example AM.RM.PERF.OVERVIEW, field DISP.OPENED.DATE but this is non displayable field.No column specified.
     * 7. Language specific(Eg: CUSTOMER.POSITION.SCV --> CUSTOMER.NAME)/currency header fields Eg: AM.PERF.PRT.D.1M field DISP.PORTFOLIO ,BEG.VAL .(The enquiry has attribute no selection but uses the selection field for display. How to handle this?)
     * 8. There is !TODAY as part of the operation in the header fields.(Eg: AM.PERF.PRT.D.1M -- field -DATE.TODAY and in AM.GRID2, field HEAD9 has attributes as PAGE.Hopefully not there in browser.)
     * 9. Ignore any field that has nodisplay in section.(Not related to headers)
     *
     * Headers are not defined in proper order in the enquiry (Eg: AM.PERF.PRT.D.1M  --> field IHDR)
     * (Probably No for the below questions for headers unless explicitly marked as Yes.)
     * Qn:
     *    2. Can there be headers where break condition is specified as a break change field? Yes.. Eg: CUST.POSITION.TAB.HP --> field ASST.LIAB.TEXT.
     *    3. Can they be idesc,calc or total or decision fields? Yes.. CUST.POSITION.TAB.HP,WITHDRAWAL.AVAILABLE.CHECK
     *    4. What does the length mask /RV in the enquiry ACCT.AVAILABLE.LADDER indicate-?
     *    5. Can there be a header with attribs as hidden?  Yes..but no special meaning but a header
     *    6. Can there be headers with display break as end?Yes.. GB.VALUATION.MATRIX, field TOT.EST.AMT section as header and display break as end.
     *    7. Enquiry AM.RM.PERF.OVERVIEW has field name ending with a "-". Have to test this.
     * 		p in the column field simply denotes that the records statisfying the break change condition fits in a single page till the condition is changed.
     * 	  8. Enquiry DPC.TXNS has the second header defined with a column number. What does this mean(Field CONV.CCY)?
     *
     * Footers can have break condition on a field.Eg. CUSTOMER.POSITION.TAB.HP---> field TOT.CUST.ASSET.
     */

    // A list of Enquiry Field(s) having a BreakOnChangeOperationImpl as their field operation. Order is that of declaration in the Enquiry DSL
    private final ArrayList<Field> m_breakChangeFields = new ArrayList<Field>();

    /*
     * {field.getName() |-> field} for those Enquiry Field(s) that have non-null names.
     *
     * NB: In the case where the Enquiry defines > 1 Field with a given name, the one that ends up in this map is that encountered 1st when traversing fields in order of
     * declaration in the Enquiry DSL
     */
    private final HashMap<String,Field> m_enquiryExtractFieldByT24FieldName = new HashMap<String,Field>();

    private boolean m_allDrilldownsAreFieldSpecific = true;

    private int m_nameCounter = 0;

    public static BasicEnquiryProject create(
    	EnquiryMapper p_enquiryMapper, String p_ifpFriendlyEnquiryName,
    	String p_irisEnquiryResultEntryResourceName, //!! PENDING REMOVAL (no longer required now that we're using IRIS meta data)
    	Enquiry p_enquiry,
    	ModelLoader p_modelLoader
    ) {
        AssertionUtils.requireNonNull( p_enquiry, "p_enquiry" );

        p_enquiry.getStartLine();
        p_enquiry.getEndLine();

        final TemplateProject templateProject = CachedTemplateProject.getTemplateProject("/templates/BasicEnquiryTemplate.ifp", "/templates/Brand.ect");

        return new BasicEnquiryProject( p_enquiryMapper, templateProject, p_ifpFriendlyEnquiryName, p_enquiry, p_modelLoader );
    }

    public void go(File p_templatesDir) throws Exception
    {
        AssertionUtils.requireNonNull( p_templatesDir, "p_templatesDir" );

        final File templatesDir = FileSystemUtils.findOrCreateMandatoryDirectory( p_templatesDir );
        m_languageMapHelper = new LanguageMapHelper( m_templateProject, p_templatesDir.getParentFile() );

        /*
         * Begin by obtaining Wrapper objects for all pertinent elements that we rely on being defined by our template IFP, throwing an exception in the
         * case where we fail to find any such element.
         *
         * Note that all lookups performed at this stage are by the names/paths as defined in the template IFP.
         *
         * IMPORTANT: Many of those names/paths will cease to be valid (in the sense of continuing to match the structure of the in-memory model of the
         * IFP we're building) when we subsequently rename both the main data group and the main process after the specific enquiry we're generating for.
         */

        final FormListWrapper allFilterFieldsList = getMandatoryTemplateDefinedList( Lists.AllFilterFields.FULLNAME );

        @SuppressWarnings("unused")
    	final DataStructureWrapper
    		linkTypeStruct				= getMandatoryTemplateDefinedStructure(Structures.LinkType.NAME),
			searchRequestTypeStruct		= getMandatoryTemplateDefinedStructure(Structures.SearchRequestType.NAME);

        @SuppressWarnings("unused")
		final PropertyGroupWrapper
			searchFiltersStructGroup	= getMandatoryTemplateDefinedStructureSubgroupByPath(Structures.SearchRequestType.FiltersGroup.PATH),
    		topLevelEnquiryGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.EnquiryGroup.PATH),
    		enqSearchRequestGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.EnquiryGroup.SearchRequestGroup.PATH),
    		enqSearchFiltersGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.EnquiryGroup.SearchRequestGroup.FiltersGroup.PATH),
    		enqSearchOrderByGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.EnquiryGroup.SearchRequestGroup.OrderByGroup.PATH),
    		enqColumnSortSpecGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.EnquiryGroup.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.PATH),
    		enqSearchResponseGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.EnquiryGroup.SearchResponseGroup.PATH),
    		enqSearchResultGroup 		= getMandatoryTemplateDefinedPropertyGroup(DataStore.EnquiryGroup.SearchResponseGroup.SearchResultGroup.PATH),
    		savedSearchesGroup			= getMandatoryTemplateDefinedPropertyGroup(DataStore.SavedSearchesGroup.PATH),
    		savedSearchGroup			= getMandatoryTemplateDefinedPropertyGroup(DataStore.SavedSearchesGroup.SavedSearchGroup.PATH),
    		savedSearchRequestGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.PATH),
    		workingElementsGroup		= getMandatoryTemplateDefinedPropertyGroup(DataStore.WorkingElementsGroup.PATH);

        final PropertyWrapper
            baseIrisSearchUrlItem                              = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.BASE_IRIS_SEARCH_URL),
            fullIrisSearchUrlItem                              = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.FULL_IRIS_SEARCH_URL),
        	invokedInDrilldownContextItem                      = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.INVOKED_IN_DRILLDOWN_CONTEXT),
        	irisInputContextItem                               = getMandatoryTemplateDefinedProperty(DataStore.LogicalScreenModel.ItemPaths.IRIS_INPUT_CONTEXT),
        	scratchItem                                        = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SCRATCH),
        	searchOutcomeItem                                  = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SEARCH_OUTCOME),
        	autoRefreshButtonImageFilenameItem                 = getMandatoryTemplateDefinedProperty(DataStore.AutoRefreshGroup.ItemPaths.BUTTON_IMAGE_FILENAME),
        	autoRefreshIntervalInputAndCountdownDisplayItem    = getMandatoryTemplateDefinedProperty(DataStore.AutoRefreshGroup.ItemPaths.INTERVAL_INPUT_AND_COUNTDOWN_DISPLAY_ITEM),
        	autoRefreshIntervalSearchParamItem                 = getMandatoryTemplateDefinedProperty(DataStore.EnquiryGroup.SearchRequestGroup.AutoRefreshIntervalSecondsItem.PATH),
        	selectionElemsHideShowStateItem                    = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SELECTION_ELEMS_HIDE_SHOW_STATE_ITEM),
			sortOptionsHideShowStateItem                       = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SORT_OPTIONS_HIDE_SHOW_STATE),
        	enqResultsTableSelectorItem                        = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.RESULTS_TABLE_SELECTOR),
        	topLevelLinksActionItem                            = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.TOP_LEVEL_LINKS_ACTION),
        	genericResultsScreenActionItem                     = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.GENERIC_RESULTS_SCREEN_ACTION),
        	resultsScreenSearchElemsHideShowStateItem          = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.RESULTS_SCREEN_SEARCH_ELEMS_HIDE_SHOW_STATE),
        	saveSearchModeItem                                 = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SAVE_SEARCH_MODE),
        	newSearchNameItem                                  = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.NEW_SEARCH_NAME),
        	savedSearchNameToUpdateItem                        = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SAVED_SEARCH_NAME_TO_UPDATE),
        	downloadFilePathItem                               = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.DOWNLOAD_FILE_PATH),
        	saveSearchDialogHideShowStateItem                  = getMandatoryTemplateDefinedProperty(DataStore.WorkingElementsGroup.ItemPaths.SAVE_SEARCH_DIALOG_HIDE_SHOW_STATE);

        final RichHTMLPresentationTypeWrapper richHTMLPresentationType = m_templateProject.getRichPresentationType();

        final ProductWrapper enqProduct = m_templateProject.getProcess(Processes.EnquiryProcess.NAME);

        final PhaseWrapper
        	startPhase					= getMandatoryTemplateDefinedPhase(Processes.EnquiryProcess.StartPhase.FULLNAME),
        	searchPhase 				= getMandatoryTemplateDefinedPhase(Processes.EnquiryProcess.SearchPhase.FULLNAME),
        	searchResultsPhase 			= getMandatoryTemplateDefinedPhase(Processes.EnquiryProcess.SearchResultsPhase.FULLNAME);

        final PresentationPhaseWrapper
        	searchPresPhase				= m_templateProject.getPresentationPhase(richHTMLPresentationType, searchPhase),
        	searchResultsPresPhase		= m_templateProject.getPresentationPhase(richHTMLPresentationType, searchResultsPhase);

        final ContainerRuleWrapper
        	moreOptionsButtonContainerRule                         = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.MORE_OPTIONS_BUTTON_CONTAINER_RULE, ContainerRule.class)),
        	addFavouriteButtonContainerRule                        = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.ADD_FAVOURITE_BUTTON_CONTAINER_RULE, ContainerRule.class)),
        	clearButtonContainerRule				               = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.CLEAR_BUTTON_CONTAINER_RULE, ContainerRule.class)),
        	findButtonContainerRule                                = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.FIND_BUTTON_CONTAINER_RULE, ContainerRule.class)),
        	saveSearchAsFavouriteContainerRule                     = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.SAVE_SEARCH_AS_FAVOURITE_CONTAINER_RULE, ContainerRule.class)),
        	reportDuplicateSearchNameContainerRule                 = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.REPORT_DUPLICATE_SEARCH_NAME_ERROR_RULE, ContainerRule.class)),
        	copySearchToCurrentSavedSearchRule                     = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.COPY_SEARCH_PARAMS_TO_CURRENT_SAVED_SEARCH_INSTANCE_CONTAINER_RULE, ContainerRule.class)),
        	loadAndExecSavedSearchContainerRule                    = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.LOAD_AND_EXEC_SAVED_SEARCH_CONTAINER_RULE, ContainerRule.class)),
        	deleteSavedSearchContainerRule                         = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.DELETE_SAVED_SEARCH_CONTAINER_RULE, ContainerRule.class)),
        	resultActionQLRContainerRule                           = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.RESULTS_SCREEN_ACTION_QLR_CONTAINER_RULE, ContainerRule.class)),
        	csvFileGenerationContainerRule                         = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.CSV_FILE_GENERATION_CONTAINER_RULE, ContainerRule.class)),
        	preprocessFilterOperandsContainerRule                  = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.PREPROCESS_FILTER_OPERANDS_CONTAINER_RULE, ContainerRule.class)),
         	startupRulesContainerRule                              = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.STARTUP_RULES_CONTAINER_RULE, ContainerRule.class)),
  			refreshButtonContainerRule                             = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.REFRESH_PAGE_CONTAINER_RULE, ContainerRule.class)),
  			autoRefreshStateEntryContainerRule                     = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.AUTO_REFRESH_STATE_ENTRY_CONTAINER_RULE, ContainerRule.class)),
  		    autoRefreshStateExitContainerRule                      = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.AUTO_REFRESH_STATE_EXIT_CONTAINER_RULE, ContainerRule.class)),
  			autoRefreshToggleButtonContainerRule                   = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.AUTO_REFRESH_TOGGLE_BUTTON_CONTAINER_RULE, ContainerRule.class)),
  			adjustInputStatesForAutoRefreshActivatedRule           = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.ADJUST_INPUT_STATES_FOR_AUTO_REFRESH_ACTIVATED, ContainerRule.class)),
  		    adjustInputStatesForAutoRefreshDeactivatedRule         = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.ADJUST_INPUT_STATES_FOR_AUTO_REFRESH_DEACTIVATED, ContainerRule.class)),
  		    loadResultsScreenAutoRefreshValueFromSearchParamRule   = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.LOAD_RESULTS_SCREEN_AUTO_REFRESH_VALUE_FROM_SEARCH_PARAM, ContainerRule.class)),
  		    saveResultsScreenAutoRefreshValueToSearchParamRule     = ContainerRuleWrapper.wrap(getMandatoryTemplateDefinedTopLevelRule(TopLevelRuleNames.SAVE_RESULTS_SCREEN_AUTO_REFRESH_VALUE_TO_SEARCH_PARAM, ContainerRule.class));

        @SuppressWarnings("unused")
		final XMLDataSourceWrapper
        	savedSearchesXmlSource = getMandatoryTemplateDefinedXMLDataSource(Integration.XML.SavedSearches.SOURCE_NAME),
        	searchResultsXmlSource = getMandatoryTemplateDefinedXMLDataSource(Integration.XML.SearchResults.SOURCE_NAME);

		final XMLMappingSetWrapper
        	savedSearchesXmlInterface = getMandatoryXMLMappingSet(Integration.XML.SavedSearches.INTERFACE_NAME),
        	searchResultsXmlInterface = getMandatoryXMLMappingSet(Integration.XML.SearchResults.INTERFACE_NAME);

        richHTMLPresentationType.setThemesFromEntityKey("Brand");
    	topLevelEnquiryGroup.setName(m_ifpFriendlyEnquiryName);
        enqProduct.setName( m_ifpFriendlyEnquiryName );
        enqProduct.setInitialPhase( startPhase );
        ((RichHTMLPresentationPhaseWrapper) searchResultsPresPhase).setSetQuestionGrid(true);
		((RichHTMLPresentationPhaseWrapper) searchPresPhase).setSetQuestionGrid(true);//GitHub #679

        addRulesToStartPhase(
			startPhase,
			searchPhase,
			searchResultsPhase,
			irisInputContextItem,
			invokedInDrilldownContextItem,
			baseIrisSearchUrlItem,
			selectionElemsHideShowStateItem,
			searchOutcomeItem,
			enqSearchResultGroup
        );

        final XMLNodeRelationshipWrapper searchRequestFiltersXmlNodeRelationship = m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired() ? addInitialSavedSearchMappingsAndRelationships( savedSearchesXmlInterface ) : null;
        final Map<String, EnquirySelectionFieldInfo> enquirySelectionFieldInfoByAppFieldName = new HashMap<String, EnquirySelectionFieldInfo>();
        final SortedMap<Integer, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByT24ColumnNumber = new TreeMap<Integer, EnquirySelectionFieldInfo>();

        populateSearchResultsPhase(
			searchResultsPhase,
			searchResultsPresPhase,
			enqSearchResultGroup,
			linkTypeStruct,
			searchResultsXmlInterface,
			scratchItem,
			invokedInDrilldownContextItem,
			selectionElemsHideShowStateItem,
			searchOutcomeItem,
	        autoRefreshButtonImageFilenameItem,
	        autoRefreshIntervalInputAndCountdownDisplayItem,
	        topLevelLinksActionItem,
			genericResultsScreenActionItem,
			enqResultsTableSelectorItem,
			makeGoBackToNamedPhaseRule( searchPhase ),
			makeGoForwardToPhaseRule( searchPhase ),
			resultActionQLRContainerRule,
			moreOptionsButtonContainerRule,
			clearButtonContainerRule,
			findButtonContainerRule,
            loadResultsScreenAutoRefreshValueFromSearchParamRule,
            saveResultsScreenAutoRefreshValueToSearchParamRule,
	        autoRefreshIntervalSearchParamItem,
			enqColumnSortSpecGroup,
			searchFiltersStructGroup,
			enqSearchFiltersGroup,
			searchRequestFiltersXmlNodeRelationship,
			sortOptionsHideShowStateItem,
			saveSearchDialogHideShowStateItem,
			saveSearchModeItem,
			newSearchNameItem,
			savedSearchNameToUpdateItem,
			savedSearchGroup,
			addFavouriteButtonContainerRule,
			saveSearchAsFavouriteContainerRule,
			reportDuplicateSearchNameContainerRule,
			loadAndExecSavedSearchContainerRule,
			deleteSavedSearchContainerRule,
			preprocessFilterOperandsContainerRule,
			templatesDir,
			enquirySelectionFieldInfoByAppFieldName,
			p_enquirySelectionFieldInfoByT24ColumnNumber,
			refreshButtonContainerRule,
			autoRefreshToggleButtonContainerRule,
			autoRefreshStateExitContainerRule
        );

        populateSearchPhase(
			searchPhase,
			searchPresPhase,
	        autoRefreshIntervalSearchParamItem,
			enqColumnSortSpecGroup,
			searchFiltersStructGroup,
			enqSearchFiltersGroup,
			searchRequestFiltersXmlNodeRelationship,
			scratchItem,
			searchOutcomeItem,
			sortOptionsHideShowStateItem,
			saveSearchDialogHideShowStateItem,
			saveSearchModeItem,
			newSearchNameItem,
			savedSearchNameToUpdateItem,
			savedSearchGroup,
			moreOptionsButtonContainerRule,
			clearButtonContainerRule,
			findButtonContainerRule,
	        loadResultsScreenAutoRefreshValueFromSearchParamRule,
	        saveResultsScreenAutoRefreshValueToSearchParamRule,
			addFavouriteButtonContainerRule,
			saveSearchAsFavouriteContainerRule,
			reportDuplicateSearchNameContainerRule,
			loadAndExecSavedSearchContainerRule,
			deleteSavedSearchContainerRule,
			preprocessFilterOperandsContainerRule,
			enquirySelectionFieldInfoByAppFieldName,
			p_enquirySelectionFieldInfoByT24ColumnNumber,
			allFilterFieldsList
        );

        if (m_enquiryAttrsDigest.isResultsScreenToolbarRequired())
        	addCsvFileGeneratorRule( csvFileGenerationContainerRule, enqSearchResultGroup, downloadFilePathItem );

        else
        {
        	m_rulesRoot.removeChild(resultActionQLRContainerRule.unwrap());
        	m_rulesRoot.removeChild(csvFileGenerationContainerRule.unwrap());
        }

        if (m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired())
        {
        	addCopySearchToCurrentSavedSearchRules( copySearchToCurrentSavedSearchRule, enqSearchRequestGroup );
        	addExecSavedSearchRules( loadAndExecSavedSearchContainerRule, loadResultsScreenAutoRefreshValueFromSearchParamRule, findButtonContainerRule, enqSearchRequestGroup );
        }

        else
        {
        	m_rulesRoot.removeChild(addFavouriteButtonContainerRule.unwrap());
        	m_rulesRoot.removeChild(copySearchToCurrentSavedSearchRule.unwrap());
        	m_rulesRoot.removeChild(loadAndExecSavedSearchContainerRule.unwrap());
        	m_rulesRoot.removeChild(reportDuplicateSearchNameContainerRule.unwrap());
        	m_rulesRoot.removeChild(saveSearchAsFavouriteContainerRule.unwrap());
        }

        // Complete the implementation of the search screen functionality by adding the necessary enquiry-specific child rules to relevant 'placeholder' container rules defined by our template IFP

        addFindButtonRules(
			findButtonContainerRule,
			preprocessFilterOperandsContainerRule,
			autoRefreshStateEntryContainerRule,
			autoRefreshIntervalSearchParamItem,
			enqColumnSortSpecGroup,
			enqSearchFiltersGroup,
			invokedInDrilldownContextItem,
			irisInputContextItem,
			baseIrisSearchUrlItem,
			fullIrisSearchUrlItem,
			searchResultsPhase,
			selectionElemsHideShowStateItem,
			searchOutcomeItem,
			resultsScreenSearchElemsHideShowStateItem,
			scratchItem,
			enqSearchResponseGroup,
			enqSearchResultGroup,
			searchResultsPresPhase,
			refreshButtonContainerRule
        );

        addInitStaticHeaderRulesToStartPhase(startPhase);

        addExecuteRulesToStartPhase(
			startPhase,
			searchPhase,
			searchResultsPhase,
			irisInputContextItem,
			invokedInDrilldownContextItem,
			selectionElemsHideShowStateItem,
			searchOutcomeItem,
			enqColumnSortSpecGroup,
			enqSearchFiltersGroup,
			enqSearchResultGroup
        );

        /*
         * Unless display of the results screen toolbar has been disabled for our Enquiry, we need to flesh out various Container rules defined (and linked into other pre-defined rules)
         * within BasicEnquiryTemplate.ifp) relating to the "auto-refresh" functionality.
         */
        if (m_enquiryAttrsDigest.isResultsScreenToolbarRequired())
        {
            // 1. Convert m_autoRefreshStateSensitiveResultsScreenElems into the comma-separated string of input element names that needs to be supplied to the SetQuestionStatus rules for
            //    disabling/reenabling those elements on entry / exit from auto-refresh mode.
            
            final Iterator<GenericNodeWrapper<?>> autoRefreshStateSensitiveResultScreenElemsIter = m_autoRefreshStateSensitiveResultsScreenElems.iterator();
            final StringBuilder commaSeparatedElemNamesBuffer = new StringBuilder();
            
            while(autoRefreshStateSensitiveResultScreenElemsIter.hasNext())
            {
                if (commaSeparatedElemNamesBuffer.length() > 0)
                    commaSeparatedElemNamesBuffer.append(',');
                
                commaSeparatedElemNamesBuffer.append(autoRefreshStateSensitiveResultScreenElemsIter.next().getName());
            }
            
            final String commaSeparatedElemNames = commaSeparatedElemNamesBuffer.toString();
            
            // 2. Create the required SetQuestionStatus rules as child rules of the relevant container rules pre-defined within BasicEnquiryTemplate.ifp
            
            final SetQuestionStatusWrapper disableAutoRefreshStateSensitiveResultScreenElems = SetQuestionStatusWrapper.create(getFormContext());
            disableAutoRefreshStateSensitiveResultScreenElems.setName("Disable relevant result screen elements");
            disableAutoRefreshStateSensitiveResultScreenElems.setItemType(SetQuestionStatusWrapper.EItemType.QUESTION);
            disableAutoRefreshStateSensitiveResultScreenElems.setChangeReadOnlyStatus(Boolean.TRUE);
            disableAutoRefreshStateSensitiveResultScreenElems.setReadOnly(Boolean.TRUE);
            disableAutoRefreshStateSensitiveResultScreenElems.setQuestion(commaSeparatedElemNames);
            adjustInputStatesForAutoRefreshActivatedRule.addTrueRule(disableAutoRefreshStateSensitiveResultScreenElems);

            final SetQuestionStatusWrapper reenableAutoRefreshStateSensitiveResultScreenElems = SetQuestionStatusWrapper.create(getFormContext());
            reenableAutoRefreshStateSensitiveResultScreenElems.setName("Reenable relevant result screen elements");
            reenableAutoRefreshStateSensitiveResultScreenElems.setItemType(SetQuestionStatusWrapper.EItemType.QUESTION);
            reenableAutoRefreshStateSensitiveResultScreenElems.setChangeReadOnlyStatus(Boolean.TRUE);
            reenableAutoRefreshStateSensitiveResultScreenElems.setReadOnly(Boolean.FALSE);
            reenableAutoRefreshStateSensitiveResultScreenElems.setIgnoreQuestions(Boolean.TRUE); // misnomer: means "Ignore any questions that are *statically* flagged as read-only"
            reenableAutoRefreshStateSensitiveResultScreenElems.setQuestion(commaSeparatedElemNames);
            adjustInputStatesForAutoRefreshDeactivatedRule.addTrueRule(reenableAutoRefreshStateSensitiveResultScreenElems);
            
            // 3. Create the SetValueRule(s) responsible for copying the value of the AutoRefreshIntervalSeconds item within SearchRequest to the dual-purpose IntervalInputAndCountdownDisplayItem
            //    backing the auto-refresh interval/countdown question within the results screen toolbar, adding each to the relevant ContainerRule pre-defined within BasicEnquiryTemplate.ifp.
            
            final SetValueRuleWrapper setResultsScreenAutoRefreshValueFromSearchParamRule = SetValueRuleWrapper.create(getFormContext());
            setResultsScreenAutoRefreshValueFromSearchParamRule.setName("Set " + autoRefreshIntervalInputAndCountdownDisplayItem.getName() + " from " + autoRefreshIntervalSearchParamItem.getName());
            setResultsScreenAutoRefreshValueFromSearchParamRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            setResultsScreenAutoRefreshValueFromSearchParamRule.setPropertyName(autoRefreshIntervalInputAndCountdownDisplayItem);
            setResultsScreenAutoRefreshValueFromSearchParamRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
            setResultsScreenAutoRefreshValueFromSearchParamRule.setFromPropertyName(autoRefreshIntervalSearchParamItem);
            loadResultsScreenAutoRefreshValueFromSearchParamRule.addTrueRule(setResultsScreenAutoRefreshValueFromSearchParamRule);
            
            final SetValueRuleWrapper setAutoRefreshIntervalSearchParamItemFromResultsScreenAutoRefreshValueRule = SetValueRuleWrapper.create(getFormContext());
            setAutoRefreshIntervalSearchParamItemFromResultsScreenAutoRefreshValueRule.setName("Set " + autoRefreshIntervalSearchParamItem.getName() + " from " + autoRefreshIntervalInputAndCountdownDisplayItem.getName());
            setAutoRefreshIntervalSearchParamItemFromResultsScreenAutoRefreshValueRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            setAutoRefreshIntervalSearchParamItemFromResultsScreenAutoRefreshValueRule.setPropertyName(autoRefreshIntervalSearchParamItem);
            setAutoRefreshIntervalSearchParamItemFromResultsScreenAutoRefreshValueRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
            setAutoRefreshIntervalSearchParamItemFromResultsScreenAutoRefreshValueRule.setFromPropertyName(autoRefreshIntervalInputAndCountdownDisplayItem);
            saveResultsScreenAutoRefreshValueToSearchParamRule.addTrueRule(setAutoRefreshIntervalSearchParamItemFromResultsScreenAutoRefreshValueRule);
        }
        
        m_languageMapHelper.generateLanguageMapsAndRules( startupRulesContainerRule, startPhase );
    }

    /**
     * @return the languageMapHelper
     */
    public LanguageMapHelper getLanguageMapHelper()
    {
        return m_languageMapHelper;
    }

    private void addRulesToStartPhase(
        PhaseWrapper p_startPhase,
        PhaseWrapper p_searchPhase,
        PhaseWrapper p_searchResultsPhase,
        PropertyWrapper p_irisInputContextItem,
        PropertyWrapper p_invokedInDrilldownContextItem,
        PropertyWrapper p_baseSearchUrlItem,
        PropertyWrapper p_selectionElemsHideShowStateItem,
        PropertyWrapper p_searchOutcomeItem,
        PropertyGroupWrapper p_searchResultGroup
	) {
        if (m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired())
        {
        	// Create XML integration to load user's favourite searches for this enquiry...

        	final XMLMapperRuleWrapper loadFavouritesFromXmlFile = XMLMapperRuleWrapper.create( getFormContext() );
        	loadFavouritesFromXmlFile.setName( "Load favourites from XML file" );
        	loadFavouritesFromXmlFile.setDataSourceNameFromEntityKey( Integration.XML.SavedSearches.SOURCE_NAME );
        	loadFavouritesFromXmlFile.setMappingSetNameFromEntityKey( Integration.XML.SavedSearches.INTERFACE_NAME );
        	loadFavouritesFromXmlFile.setOperation( XMLMapperRuleWrapper.EOperation.READ );
        	p_startPhase.addPostPhaseRule( loadFavouritesFromXmlFile.unwrap() );
        }
        
        final EnquiryUrlAnalyserRule enquiryUrlAnalyzerRule = new EnquiryUrlAnalyserRule(getFormContext());
        enquiryUrlAnalyzerRule.setName("Enquiry URL Analyzer");
        enquiryUrlAnalyzerRule.setErrorHandlingType("Pass Up");
        enquiryUrlAnalyzerRule.setAttribute(EnquiryUrlAnalyserRule.RuleAttrNames.IRIS_URL_INPUT_ITEM_PATH, p_irisInputContextItem.getEntityKeyName());
        enquiryUrlAnalyzerRule.setAttribute(EnquiryUrlAnalyserRule.RuleAttrNames.IS_DRILLDOWN_CONTEXT_OUTPUT_ITEM_PATH, p_invokedInDrilldownContextItem.getEntityKeyName());
        enquiryUrlAnalyzerRule.setAttribute(EnquiryUrlAnalyserRule.RuleAttrNames.BASE_SEARCH_URL_OUTPUT_ITEM_PATH, p_baseSearchUrlItem.getEntityKeyName());
        enquiryUrlAnalyzerRule.setAttribute(EnquiryUrlAnalyserRule.RuleAttrNames.MAX_RESULTS_CAP, "$$!MAX_ENQUIRY_RESULTS$");
        p_startPhase.addPostPhaseRule(enquiryUrlAnalyzerRule);

        /*
         * Handle the Drilldown clause in the DSL.  We need to find drill downs that use images
         * and store their drill down 'name' (its id) and image in the Generated IFP's data store.  At runtime
         * IRIS will return links representing the drill downs.  IRIS will provide a name attribute which
         * will match the DSL name.
         *
         * The Generic Action Widget will then match this with the information stored below
         * thereby gaining access the to the image
         */

		final EList<DrillDown> drilldowns = m_enquiry.getDrillDowns();
		final int numDrilldowns = (drilldowns == null) ? 0 : drilldowns.size();

		ContainerRuleWrapper loadDrilldownInfoContainer = null;

		for (int i = 0; i < numDrilldowns; i++)
		{
			final DrillDown drillDown = drilldowns.get(i);

			final String drillDownID = drillDown.getDrill_name();
			final String imageName = drillDown.getImage();
			final String labelField = drillDown.getLabelField();

			if (StringUtils.isEmpty(drillDownID) )
				continue;

			// Set the drilldown name / id

			SetValueRuleWrapper setValueOfName = SetValueRuleWrapper.create(getFormContext());

			setValueOfName.setName("Set the Drilldown id for the drill count " + i);
			setValueOfName.setType(SetValueRuleWrapper.EType.DATA_ITEM);
			setValueOfName.setPropertyNameFromEntityKey(DataStore.DrilldownMapGroup.ItemPaths.CURRENT_INSTANCE_ID.replace("[C]", "[" + (i + 1) + "]"));
			setValueOfName.setFromType(SetValueRuleWrapper.EFromType.VALUE);
			setValueOfName.setFromValue(drillDownID);

			if (loadDrilldownInfoContainer == null)
			{
				loadDrilldownInfoContainer = ContainerRuleWrapper.create(getFormContext(), "Load Drilldown Info");
				p_startPhase.addPostPhaseRule(loadDrilldownInfoContainer);
			}

			loadDrilldownInfoContainer.addTrueRule(setValueOfName);

			// Set the drilldown image

			if ( !StringUtils.isEmpty(imageName) ) {
				SetValueRuleWrapper setValueOfImage = SetValueRuleWrapper.create(getFormContext());
				setValueOfImage.setName("Set the Drilldown image for the drill count " + i);
				setValueOfImage.setType(SetValueRuleWrapper.EType.DATA_ITEM);
				setValueOfImage.setPropertyNameFromEntityKey(DataStore.DrilldownMapGroup.ItemPaths.CURRENT_INSTANCE_IMAGE_PATH.replace("[C]", "[" + (i + 1) + "]"));
				setValueOfImage.setFromType(SetValueRuleWrapper.EFromType.VALUE);
				setValueOfImage.setFromValue(imageName);
				loadDrilldownInfoContainer.addTrueRule(setValueOfImage);
			}
			
			if ( StringUtils.isEmpty(labelField) )
			    m_allDrilldownsAreFieldSpecific = false;
			
			else {
				SetValueRuleWrapper setColumnRef = SetValueRuleWrapper.create(getFormContext());
				setColumnRef.setName("Set the Drilldown column ref for the drill count " + i);
				setColumnRef.setType(SetValueRuleWrapper.EType.DATA_ITEM);
				setColumnRef.setPropertyNameFromEntityKey(DataStore.DrilldownMapGroup.ItemPaths.CURRENT_INSTANCE_REFCOL_PATH.replace("[C]", "[" + (i + 1) + "]"));
				setColumnRef.setFromType(SetValueRuleWrapper.EFromType.VALUE);
				setColumnRef.setFromValue(labelField);
				loadDrilldownInfoContainer.addTrueRule(setColumnRef);
			}
		}
    }

    private void populateSearchResultsPhase(
        PhaseWrapper p_searchResultsPhase,
        PresentationPhaseWrapper p_searchResultsPresPhase,
        PropertyGroupWrapper p_searchResultGroup,
        DataStructureWrapper p_linkTypeStruct,
        XMLMappingSetWrapper p_searchResultsXmlInterface,
        PropertyWrapper p_scratchItem,
        PropertyWrapper p_invokedInDrilldownContextItem,
        PropertyWrapper p_selectionElemsHideShowStateItem,
        PropertyWrapper p_searchOutcomeItem,
        PropertyWrapper p_autoRefreshButtonImageFilenameItem,
        PropertyWrapper p_autoRefreshIntervalInputAndCountdownDisplayItem,
        PropertyWrapper p_topLevelLinksActionItem,
        PropertyWrapper p_genericResultsScreenActionItem,
        PropertyWrapper p_resultsTableSelectorItem,
        GotoRuleWrapper p_goBackToSearchPhaseRule,
        GotoRuleWrapper p_goForwardToSearchPhaseRule,
        ContainerRuleWrapper p_resultActionQLRContainerRule,
        ContainerRuleWrapper p_moreOptionsButtonContainerRule,
        ContainerRuleWrapper p_clearButtonContainerRule,
        ContainerRuleWrapper p_findButtonContainerRule,
        ContainerRuleWrapper p_loadResultsScreenAutoRefreshValueFromSearchParam,
        ContainerRuleWrapper p_saveResultsScreenAutoRefreshValueToSearchParam,
        PropertyWrapper p_autoRefreshIntervalItem,
        PropertyGroupWrapper p_columnSortSpecGroup,
        PropertyGroupWrapper p_searchFiltersStructGroup,
        PropertyGroupWrapper p_searchFiltersGroup,
        XMLNodeRelationshipWrapper p_searchRequestFiltersXmlNodeRelationship,
        PropertyWrapper p_sortOptionsHideShowStateItem,
        PropertyWrapper p_saveSearchDialogHideShowStateItem,
        PropertyWrapper p_saveSearchModeItem,
        PropertyWrapper p_newSearchNameItem,
        PropertyWrapper p_savedSearchNameToUpdateItem,
        PropertyGroupWrapper p_savedSearchGroup,
        ContainerRuleWrapper p_addFavouriteButtonContainerRule,
        ContainerRuleWrapper p_saveSearchAsFavouriteContainerRule,
        ContainerRuleWrapper p_reportDuplicateSearchNameContainerRule,
        ContainerRuleWrapper p_loadAndExecSavedSearchContainerRule,
        ContainerRuleWrapper p_deleteSavedSearchContainerRule,
        ContainerRuleWrapper p_preprocessFilterOperandsContainerRule,
        File p_templatesDir,
        Map<String, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByAppFieldName,
        SortedMap<Integer, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByT24ColumnNumber,
        ContainerRuleWrapper p_refreshButtonContainerRule,
        ContainerRuleWrapper p_autoRefreshToggleButtonContainerRule,
        ContainerRuleWrapper p_autoRefreshStateExitContainerRule
	) throws Exception {
        // Add the "Selection Screen" button, "[Action]" dropdown and supporting elements (process & presentation)

    	if (m_enquiryAttrsDigest.isResultsScreenToolbarRequired()) {
    	    addSearchResultsToolbarElems(
                 p_searchResultsPhase,
                 p_searchResultsPresPhase,
                 p_goBackToSearchPhaseRule,
                 p_goForwardToSearchPhaseRule,
                 p_invokedInDrilldownContextItem,
                 p_selectionElemsHideShowStateItem,
                 p_searchOutcomeItem,
                 p_autoRefreshButtonImageFilenameItem,
                 p_autoRefreshIntervalInputAndCountdownDisplayItem,
                 p_genericResultsScreenActionItem,
                 p_resultActionQLRContainerRule,
                 p_refreshButtonContainerRule,
                 p_autoRefreshToggleButtonContainerRule,
                 p_autoRefreshStateExitContainerRule
	        );
    	}

        // Add the search results table & supporting elements (process & presentation)

        addSearchResultsTableQuestionsAndDataItems(
            p_searchResultsPhase,
            p_searchResultsPresPhase,
            p_searchResultGroup,
            p_linkTypeStruct,
            p_searchResultsXmlInterface,
            p_selectionElemsHideShowStateItem,
            p_searchOutcomeItem,
            p_topLevelLinksActionItem,
            p_resultsTableSelectorItem,
            p_templatesDir,
            p_enquirySelectionFieldInfoByAppFieldName,
            p_enquirySelectionFieldInfoByT24ColumnNumber
		);

        if ((! m_enquiryAttrsDigest.isResultsScreenToolbarRequired()) && (m_enquiryResultsToolbarElems != null))
        {
            addApplicabilityConditionToToolbarItemGroupIfNeeded(
                m_enquiryResultsToolbarElems,
                m_breakChangeControlGroupElems,
                p_searchOutcomeItem,
                p_topLevelLinksActionItem
            );
        }

        if (m_enquiryAttrsDigest.isReiterationOfPopulatedSearchParamsBeneathResultsTableEnabled())
        {
            addFavouritesAndSearchElems(
    		    p_searchResultsPhase,
    		    p_searchResultsPresPhase,
    		    p_autoRefreshIntervalItem,
    		    p_columnSortSpecGroup,
    		    p_searchFiltersStructGroup,
    		    p_searchFiltersGroup,
    		    p_searchRequestFiltersXmlNodeRelationship,
    		    p_scratchItem,
    		    p_searchOutcomeItem,
    		    p_sortOptionsHideShowStateItem,
    		    p_saveSearchDialogHideShowStateItem,
    		    p_saveSearchModeItem,
    		    p_newSearchNameItem,
    		    p_savedSearchNameToUpdateItem,
    		    p_savedSearchGroup,
    		    p_moreOptionsButtonContainerRule,
    		    p_clearButtonContainerRule,
    		    p_findButtonContainerRule,
                p_loadResultsScreenAutoRefreshValueFromSearchParam,
                p_saveResultsScreenAutoRefreshValueToSearchParam,
    		    p_addFavouriteButtonContainerRule,
    		    p_saveSearchAsFavouriteContainerRule,
    		    p_reportDuplicateSearchNameContainerRule,
    		    p_loadAndExecSavedSearchContainerRule,
    		    p_deleteSavedSearchContainerRule,
    		    p_preprocessFilterOperandsContainerRule,
    		    p_enquirySelectionFieldInfoByAppFieldName,
    		    p_enquirySelectionFieldInfoByT24ColumnNumber,
    		    null /* p_allFilterFieldsList */
            );
        }
    }

	private void addFeedLevelLinksQuestionToEnquiryResultsToolbar(PropertyWrapper p_topLevelLinksActionItem) throws Exception
	{
	    AssertionUtils.requireNonNull(p_topLevelLinksActionItem, "p_topLevelLinksActionItem");

	    /*
         *  Create and add the "[Enquiry Result Set Level Actions]" question
         */
        final QuestionWrapper enqResultsTopLevelLinksQuestion = QuestionWrapper.create( getFormContext() );
        enqResultsTopLevelLinksQuestion.setQuestionText( "$%SLANG Enquiry.SetLevelActionsLabel [Enquiry Result Set Level Actions]$" );
        enqResultsTopLevelLinksQuestion.setMandatory(Boolean.FALSE);
        enqResultsTopLevelLinksQuestion.setReadOnly(Boolean.FALSE);
        enqResultsTopLevelLinksQuestion.setPostQuestionRules(Boolean.TRUE);
        enqResultsTopLevelLinksQuestion.setPropertyKey(p_topLevelLinksActionItem);
        enqResultsTopLevelLinksQuestion.setDisableInput(Boolean.TRUE);
        m_autoRefreshStateSensitiveResultsScreenElems.add(enqResultsTopLevelLinksQuestion);

        /*
         * Add question-level rules to set LogicalScreenModel[1].IRISOuputContext and LogicalScreenModel[1].IRISRelation, then return control to DashboardDynamic.ifp
         */
        final SetValueRuleWrapper setIRISOutputContextRule = SetValueRuleWrapper.create( getFormContext() );
        setIRISOutputContextRule.setName( "Set IRISOutputContext from the key of the selected top-level-links list entry" );
        setIRISOutputContextRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setIRISOutputContextRule.setPropertyNameFromEntityKey( DataStore.LogicalScreenModel.ItemPaths.IRIS_OUTPUT_CONTEXT );
        setIRISOutputContextRule.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setIRISOutputContextRule.setFromPropertyNameFromEntityKey( DataStore.WorkingElementsGroup.ItemPaths.TOP_LEVEL_LINKS_ACTION );
        enqResultsTopLevelLinksQuestion.addChild( setIRISOutputContextRule );

        final SetValueRuleWrapper setIRISRelationRule = SetValueRuleWrapper.create( getFormContext() );
        setIRISRelationRule.setName( "Set IRISRelation from the group value of the selected top-level-links list entry" );
        setIRISRelationRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setIRISRelationRule.setPropertyNameFromEntityKey( DataStore.LogicalScreenModel.ItemPaths.IRIS_RELATION );
        setIRISRelationRule.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setIRISRelationRule.setFromPropertyNameFromEntityKey( DataStore.WorkingElementsGroup.ItemPaths.TOP_LEVEL_LINKS_ACTION + ".groupValue()" );
        enqResultsTopLevelLinksQuestion.addChild( setIRISRelationRule );

        final BroadcastRuleWrapper broadCastRule = BroadcastRuleWrapper.create( getFormContext() );
        broadCastRule.setName( "Broadcast to the Dashboard to execute any nested component rules" );
        broadCastRule.setBroadcastMessage( "BRP Forever" );
        enqResultsTopLevelLinksQuestion.addChild( broadCastRule );

        // Add the Action presentation question (answer presented by GenericAction widget) with answer right-aligned so that r.h. edge is same as that of results table.
        final RichHTMLPresentationQuestionWrapper enqResultsTopLevelLinksPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), enqResultsTopLevelLinksQuestion.unwrap());
        enqResultsTopLevelLinksPresQuestion.setHideQuestion( Boolean.TRUE );
        EnquiryWidgets.GENERIC_ACTION.applyTo(enqResultsTopLevelLinksPresQuestion, new GenericActionWidget.Params("- Related Actions -"));
        enqResultsTopLevelLinksPresQuestion.setDesignToUseFromEntityKey( "Enquiry Top-links Question" );
        enqResultsTopLevelLinksPresQuestion.setSpecificAnswerControlStyle( "EnquiryTopLinksControl" );
        enqResultsTopLevelLinksPresQuestion.setReadOnlyFormat(EReadOnlyFormat.TEXT);

        m_enquiryResultsToolbarElems.addEnquiryResultsTopLinksQuestion(new QuestionWrapperPair(enqResultsTopLevelLinksQuestion, enqResultsTopLevelLinksPresQuestion));
    } // addFeedLevelLinksQuestionToEnquiryResultsToolbar()

    private void addSearchResultsToolbarElems(
        PhaseWrapper p_searchResultsPhase,
        PresentationPhaseWrapper p_searchResultsPresPhase,
        GotoRuleWrapper p_goBackToSearchPhaseRule,
        GotoRuleWrapper p_goForwardToSearchPhaseRule,
        PropertyWrapper p_invokedInDrilldownContextItem,
        PropertyWrapper p_selectionElemsHideShowStateItem,
        PropertyWrapper p_searchOutcomeItem,
        PropertyWrapper p_autoRefreshButtonImageFilenameItem,
        PropertyWrapper p_autoRefreshIntervalInputAndCountdownDisplayItem,
        PropertyWrapper p_genericResultsScreenActionItem,
        ContainerRuleWrapper p_resultActionQLRContainerRule,
        ContainerRuleWrapper p_refreshButtonContainerRule,
        ContainerRuleWrapper p_autoRefreshToggleButtonContainerRule,
        ContainerRuleWrapper p_autoRefreshStateExitContainerRule
	) {
        m_enquiryResultsToolbarElems = new EnquiryResultsToolbarElems(this, "Toolbar Elements", new VisualItemContainerWrapperPair(p_searchResultsPhase, p_searchResultsPresPhase), p_searchOutcomeItem, false);

        // 0) Create the rules for the "Selection Screen" button

        final ContainerRuleWrapper selectionScreenButtonRulesContainer = ContainerRuleWrapper.create(getFormContext(), "Button Rules");

        final ResetDataRuleWrapper resetSearchOutcomeItemRule = ResetDataRuleWrapper.create( getFormContext() );
        resetSearchOutcomeItemRule.setName( "Reset SearchOutcome item" );
        resetSearchOutcomeItemRule.setResetProperty( p_searchOutcomeItem );
        selectionScreenButtonRulesContainer.addTrueRule( resetSearchOutcomeItemRule );

        final EvaluateRuleWrapper checkInvokedInDrilldownContextEvalRule = EvaluateRuleWrapper.create( getFormContext() );
        checkInvokedInDrilldownContextEvalRule.setName( "Check whether our InvokedInDrilldownContext flag is set - if so we have not yet shown the Search screen" );
        checkInvokedInDrilldownContextEvalRule.setExpression("$$" + p_invokedInDrilldownContextItem.getPropertyKey(null) + "$ == 'true'");
        selectionScreenButtonRulesContainer.addTrueRule( checkInvokedInDrilldownContextEvalRule );

        checkInvokedInDrilldownContextEvalRule.addTrueRule( p_goForwardToSearchPhaseRule.unwrap() );
        
        checkInvokedInDrilldownContextEvalRule.unwrap().addFalseRule(p_autoRefreshStateExitContainerRule.unwrap(), true /* p_linkedObject */);
        checkInvokedInDrilldownContextEvalRule.addFalseRule( p_goBackToSearchPhaseRule.unwrap() );

        // 1) Add the "Selection Screen" button

        final FormButtonWrapper backToSearchPhaseButton = FormButtonWrapper.create( getFormContext() );
        backToSearchPhaseButton.setActionCommand( "Selection Screen" );
        backToSearchPhaseButton.setDependencyType( FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE );
		backToSearchPhaseButton.addChild(selectionScreenButtonRulesContainer);

        final RichHTMLPresentationButtonWrapper backToSearchPhasePresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), backToSearchPhaseButton.unwrap());
        backToSearchPhasePresButton.setDesignToUseFromEntityKey( "Enquiry Results Selection Screen Button" );
        backToSearchPhasePresButton.setDisplayInAnswerColumn( Boolean.FALSE );
        backToSearchPhasePresButton.setHintText( "$%SLANG Enquiry.SelectionScreenHint Selection Screen$" );
        
        m_enquiryResultsToolbarElems.addSelectionScreenButton(new ButtonWrapperPair(backToSearchPhaseButton, backToSearchPhasePresButton));

        // 2) Add the "Refresh Enquiry" button.

        final FormButtonWrapper refreshEnquiryResultsButton = FormButtonWrapper.create( getFormContext() );
        refreshEnquiryResultsButton.setActionCommand( "Refresh Enquiry" );
        refreshEnquiryResultsButton.setDependencyType( FormButtonWrapper.EDependencyType.NONE );
        refreshEnquiryResultsButton.unwrap().addChild( p_refreshButtonContainerRule.unwrap(), true /* p_linkedObject */);

        final RichHTMLPresentationButtonWrapper refreshEnquiryResultsPresButton = RichHTMLPresentationButtonWrapper.create( getFormContext(), refreshEnquiryResultsButton.unwrap() );
        refreshEnquiryResultsPresButton.setDisplayInAnswerColumn( false );
        refreshEnquiryResultsPresButton.setButtonNewLine( false );
        refreshEnquiryResultsPresButton.setDesignToUseFromEntityKey( "Enquiry Results Refresh Enquiry button" );
        refreshEnquiryResultsPresButton.setEditDesignToUse( "N" );
        refreshEnquiryResultsPresButton.setHintText( "$%SLANG Enquiry.RefreshEnquiryHint Refresh Enquiry$");
        
        final ButtonWrapperPair refreshEnquiryButtonWrapperPair = new ButtonWrapperPair(refreshEnquiryResultsButton, refreshEnquiryResultsPresButton);
        m_autoRefreshStateSensitiveResultsScreenElems.add(refreshEnquiryResultsButton);
        m_enquiryResultsToolbarElems.addRefreshEnquiryResultsButton(refreshEnquiryButtonWrapperPair);

        // 3) Add the "Enquiry Info" ("(i)") image widget

        //TODO: Create and add an Appliable widget class for the "Image" widget, plumb into EnquiryWidgets I/F and update code below to use that
        final RichHTMLPresentationTextWrapper enquiryInfoImage = RichHTMLPresentationTextWrapper.create( getFormContext() );
        enquiryInfoImage.setDesignToUseFromEntityKey( "Custom" );
        enquiryInfoImage.setEditDesignToUse( "Y" );
        enquiryInfoImage.setAttribute( "DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Image" );
        enquiryInfoImage.setAttribute( "DisplayTypeIMG_SRC", "images/enquiry/info.png" );
        enquiryInfoImage.setAttribute( "DisplayTypeIMG_ALT_TEXT", "Info" );
        enquiryInfoImage.setTextStyle( "enquiryInfo EnquiryToolbarButton" );
        enquiryInfoImage.setAttribute( "DisplayTypeIMG_TITLE", m_enquiry.getName());

        m_enquiryResultsToolbarElems.addEnquiryInfoImageWidget(enquiryInfoImage);
        
        // 4) Add the Auto-refresh controls (toggle button + refresh interval text input)

        final FormButtonWrapper autoRefreshToggleButton = FormButtonWrapper.create( getFormContext() );
        autoRefreshToggleButton.setActionCommand( "[Auto Refresh Toggle Button]" );
        autoRefreshToggleButton.setDependencyType( FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE );
        autoRefreshToggleButton.addLink(p_autoRefreshToggleButtonContainerRule);
        
        final RichHTMLPresentationButtonWrapper presAutoRefreshToggleButton = RichHTMLPresentationButtonWrapper.create( getFormContext(), autoRefreshToggleButton.unwrap() );
        presAutoRefreshToggleButton.setDisplayInAnswerColumn( false );
        presAutoRefreshToggleButton.setButtonNewLine( false );
        presAutoRefreshToggleButton.setDesignToUseFromEntityKey( "Custom" );
        presAutoRefreshToggleButton.setMandatoryStyle( EnquiryStyleStrings.NOT_DISPLAYED );
        presAutoRefreshToggleButton.setPrefixStyle( EnquiryStyleStrings.NOT_DISPLAYED );
        presAutoRefreshToggleButton.setDisplayType( "Use Image" );
        presAutoRefreshToggleButton.setHintText("$%SLANG Enquiry.AutoRefreshToggleHint Toggle Auto Refresh$");
        presAutoRefreshToggleButton.setImageFileName( "enquiry/$$" + p_autoRefreshButtonImageFilenameItem.getEntityKeyName() + "$" );
        presAutoRefreshToggleButton.setButtonStyle( "EnquiryAutoRefreshButton" );
        
        final QuestionWrapper autoRefreshIntervalCountdownQuestion = QuestionWrapper.create(getFormContext());
        autoRefreshIntervalCountdownQuestion.setQuestionText("$%SLANG Enquiry.AutoRefreshLabel [Auto Refresh Interval / Countdown Value]$");
        autoRefreshIntervalCountdownQuestion.setMandatory(false);
        autoRefreshIntervalCountdownQuestion.setPropertyKey(p_autoRefreshIntervalInputAndCountdownDisplayItem);
        
        final RichHTMLPresentationQuestionWrapper autoRefreshIntervalCountdownPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), autoRefreshIntervalCountdownQuestion.unwrap());
        autoRefreshIntervalCountdownPresQuestion.setHideQuestion(true);
        autoRefreshIntervalCountdownPresQuestion.setDesignToUseFromEntityKey("Custom");
        autoRefreshIntervalCountdownPresQuestion.setEditDesignToUse("N");
        autoRefreshIntervalCountdownPresQuestion.setRowStyle("QRow");
        autoRefreshIntervalCountdownPresQuestion.setQuestionStyle("NotDisplayed");
        autoRefreshIntervalCountdownPresQuestion.setMandatoryStyle("NotDisplayed");
        autoRefreshIntervalCountdownPresQuestion.setPrefixStyle("NotDisplayed");
        autoRefreshIntervalCountdownPresQuestion.setAnswerStyle("QAns EnquiryAutoRefreshInput");
        autoRefreshIntervalCountdownPresQuestion.setPostfixStyle("NotDisplayed");
        autoRefreshIntervalCountdownPresQuestion.setHelpStyle("QHelp");
        autoRefreshIntervalCountdownPresQuestion.setErrorStyle("ErrorColor SmallFont");
        autoRefreshIntervalCountdownPresQuestion.setInfoStyle("VSmallFont");
        autoRefreshIntervalCountdownPresQuestion.setJustification(RichHTMLPresentationQuestionWrapper.EJustification.NONE);
        autoRefreshIntervalCountdownPresQuestion.setDisplayType("Text Input Field");
        autoRefreshIntervalCountdownPresQuestion.setReadOnlyFormat(RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER);
        autoRefreshIntervalCountdownPresQuestion.setDefaultButton(autoRefreshToggleButton.unwrap().getActionCommand());
        autoRefreshIntervalCountdownPresQuestion.setDefaultButtonName(autoRefreshToggleButton.getName());
        
        m_autoRefreshStateSensitiveResultsScreenElems.add(autoRefreshIntervalCountdownQuestion);

        m_enquiryResultsToolbarElems.addAutoRefreshControls(
            refreshEnquiryButtonWrapperPair,
            new ButtonWrapperPair(autoRefreshToggleButton, presAutoRefreshToggleButton),
            new QuestionWrapperPair(autoRefreshIntervalCountdownQuestion, autoRefreshIntervalCountdownPresQuestion)
        );
        
        // 5) Add the "[Generic Result Screen Actions]" question & child QLR to the phase.

        final QuestionWrapper genericResultScreenActionsQuestion = QuestionWrapper.create( getFormContext() );
        genericResultScreenActionsQuestion.setQuestionText( "$%SLANG Enquiry.GenericSearchResultActionsLabel [Generic Search Result Actions]$" );
        genericResultScreenActionsQuestion.setPostQuestionRules( Boolean.TRUE );
        genericResultScreenActionsQuestion.setDisableInput( Boolean.TRUE );
        genericResultScreenActionsQuestion.setMandatory( Boolean.FALSE );
        genericResultScreenActionsQuestion.setPropertyKey( p_genericResultsScreenActionItem );
        genericResultScreenActionsQuestion.unwrap().addChild( p_resultActionQLRContainerRule.unwrap(), true /* p_linkedObject */);

        final RichHTMLPresentationQuestionWrapper genericResultScreenActionsPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), genericResultScreenActionsQuestion.unwrap());
        genericResultScreenActionsPresQuestion.setQuestionNewLine( Boolean.FALSE );
        genericResultScreenActionsPresQuestion.setDisplayType( "Drop down list" );
        genericResultScreenActionsPresQuestion.setListPrompt( RichHTMLPresentationQuestionWrapper.EListPrompt.SPECIFY );
        genericResultScreenActionsPresQuestion.setQuestionListDefault( "$%SLANG Enquiry.GenericSearchResultActionsListDefault - Choose Action -$" );
        genericResultScreenActionsPresQuestion.setDefaultButtonName( "Use Default" );
        genericResultScreenActionsPresQuestion.setReadOnlyFormat( RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER );
        genericResultScreenActionsPresQuestion.setHideQuestion( true );

        m_autoRefreshStateSensitiveResultsScreenElems.add(genericResultScreenActionsQuestion);
        m_enquiryResultsToolbarElems.addGenericResultScreenActionsQuestion(new QuestionWrapperPair(genericResultScreenActionsQuestion, genericResultScreenActionsPresQuestion));

    } // addSearchResultsToolbarElems()

    private void populateSearchPhase(
        PhaseWrapper p_searchPhase,
        PresentationPhaseWrapper p_searchPresPhase,
        PropertyWrapper p_autoRefreshIntervalItem,
        PropertyGroupWrapper p_columnSortSpecGroup,
        PropertyGroupWrapper p_searchFiltersStructGroup,
        PropertyGroupWrapper p_searchFiltersGroup,
        XMLNodeRelationshipWrapper p_searchRequestFiltersXmlNodeRelationship,
        PropertyWrapper p_scratchItem,
        PropertyWrapper p_searchOutcomeItem,
        PropertyWrapper p_sortOptionsHideShowStateItem,
        PropertyWrapper p_saveSearchDialogHideShowStateItem,
        PropertyWrapper p_saveSearchModeItem,
        PropertyWrapper p_newSearchNameItem,
        PropertyWrapper p_savedSearchNameToUpdateItem,
        PropertyGroupWrapper p_savedSearchGroup,
        ContainerRuleWrapper p_moreOptionsButtonContainerRule,
        ContainerRuleWrapper p_clearButtonContainerRule,
        ContainerRuleWrapper p_findButtonContainerRule,
        ContainerRuleWrapper p_loadResultsScreenAutoRefreshValueFromSearchParam,
        ContainerRuleWrapper p_saveResultsScreenAutoRefreshValueToSearchParam,
        ContainerRuleWrapper p_addFavouriteButtonContainerRule,
        ContainerRuleWrapper p_saveSearchAsFavouriteContainerRule,
        ContainerRuleWrapper p_reportDuplicateSearchNameContainerRule,
        ContainerRuleWrapper p_loadAndExecSavedSearchContainerRule,
        ContainerRuleWrapper p_deleteSavedSearchContainerRule,
        ContainerRuleWrapper p_preprocessFilterOperandsContainerRule,
        Map<String, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByAppFieldName,
        SortedMap<Integer, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByT24ColumnNumber,
        FormListWrapper p_allFilterFieldsList
	) throws Exception {
        addFavouritesAndSearchElems(
			p_searchPhase,
			p_searchPresPhase,
	        p_autoRefreshIntervalItem,
			p_columnSortSpecGroup,
			p_searchFiltersStructGroup,
			p_searchFiltersGroup,
			p_searchRequestFiltersXmlNodeRelationship,
			p_scratchItem,
			p_searchOutcomeItem,
			p_sortOptionsHideShowStateItem,
			p_saveSearchDialogHideShowStateItem,
			p_saveSearchModeItem,
			p_newSearchNameItem,
			p_savedSearchNameToUpdateItem,
			p_savedSearchGroup,
			p_moreOptionsButtonContainerRule,
			p_clearButtonContainerRule,
			p_findButtonContainerRule,
			p_loadResultsScreenAutoRefreshValueFromSearchParam,
			p_saveResultsScreenAutoRefreshValueToSearchParam,
			p_addFavouriteButtonContainerRule,
			p_saveSearchAsFavouriteContainerRule,
			p_reportDuplicateSearchNameContainerRule,
			p_loadAndExecSavedSearchContainerRule,
			p_deleteSavedSearchContainerRule,
			p_preprocessFilterOperandsContainerRule,
			p_enquirySelectionFieldInfoByAppFieldName,
			p_enquirySelectionFieldInfoByT24ColumnNumber,
			p_allFilterFieldsList
		);
    }

    private XMLNodeRelationshipWrapper addInitialSavedSearchMappingsAndRelationships(XMLMappingSetWrapper p_xmlInterface)
    {
        // Create relationship node for the singleton "SavedSearches[1]" group
        final XMLNodeRelationshipWrapper xmlNodeSavedSearches = makeXMLNodeRelationshipForSingleInstanceGroup( DataStore.SavedSearchesGroup.NAME );
        p_xmlInterface.addChild( xmlNodeSavedSearches );

        // Attach child relationship for the multi-instance "SavedSearches[1].SavedSearch[C]" group
        final XMLNodeRelationshipWrapper xmlNodeSavedSearch = makeXMLNodeRelationshipForMultiInstanceGroup( DataStore.SavedSearchesGroup.SavedSearchGroup.CURRENT_INSTANCE_PATH );
        xmlNodeSavedSearches.addChild( xmlNodeSavedSearch );

        // Attach child mapping for the "SavedSearches[1].SavedSearch[C].SearchName" item
        final XMLNodeMappingWrapper xmlAttributeSearchName = makeReadWriteXMLNodeMapping( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchNameItem.CURRENT_INSTANCE_PATH );
        xmlNodeSavedSearch.addChild( xmlAttributeSearchName );

        // Attach sibling relationship for the singleton "SavedSearch[1].SavedSearch[C].SearchRequest[1]" group
        final XMLNodeRelationshipWrapper xmlNodeSearchRequest = makeXMLNodeRelationshipForSingleInstanceGroup( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.NAME );
        xmlNodeSavedSearch.addChild( xmlNodeSearchRequest );

        // Attach child mapping for the "SavedSearch[1].SavedSearch[C].SearchRequest[1].AutoRefreshIntervalSeconds" item to xmlNodeSearchRequest
        final XMLNodeMappingWrapper xmlAttributeAutoRefreshIntervalSeconds = makeReadWriteXMLNodeMapping( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.AutoRefreshIntervalSecondsItem.CURRENT_INSTANCE_PATH );
        xmlNodeSearchRequest.addChild(xmlAttributeAutoRefreshIntervalSeconds);
        
        // Attach child relationship for the singleton "SavedSearch[1].SavedSearch[C].SearchRequest[1].Filters[1]" child group
        // NB: This relationship requires further population to reflect the enquiry-specific per-search-param filter sub-groups, hence it's this relationship that we return to the caller for that purpose
        final XMLNodeRelationshipWrapper xmlNodeFilters = makeXMLNodeRelationshipForSingleInstanceGroup( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.FiltersGroup.NAME );
        xmlNodeSearchRequest.addChild( xmlNodeFilters );

        // Attach sibling relationship for the singleton "SavedSearch[1].SavedSearch[C].SearchRequest[1].OrderBy[1]" child group
        final XMLNodeRelationshipWrapper xmlNodeOrderBy = makeXMLNodeRelationshipForSingleInstanceGroup( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.OrderByGroup.NAME );
        xmlNodeOrderBy.setName( "XML Node " + DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.OrderByGroup.NAME );
        xmlNodeOrderBy.setNodeName( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.OrderByGroup.NAME );
        xmlNodeSearchRequest.addChild( xmlNodeOrderBy );

        // Attach child relationship for the multi-instance "SavedSearch[1].SavedSearch[C].SearchRequest[1].OrderBy[1].ColumnSortSpec[C]" child group
        final XMLNodeRelationshipWrapper xmlNodeColumnSortSpec = makeXMLNodeRelationshipForMultiInstanceGroup( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.CURRENT_INSTANCE_PATH );
        xmlNodeOrderBy.addChild( xmlNodeColumnSortSpec );

        // Attach child mapping for the "SavedSearch[1].SavedSearch[C].SearchRequest[1].OrderBy[1].ColumnSortSpec[C].FieldName" item
        final XMLNodeMappingWrapper xmlAttributeFieldName = makeReadWriteXMLNodeMapping( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.FieldNameItem.CURRENT_INSTANCE_PATH );
		xmlAttributeFieldName.setName("XML Attribute " + DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.FieldNameItem.NAME);
        xmlNodeColumnSortSpec.addChild( xmlAttributeFieldName );

        // Attach sibling mapping for the "SavedSearch[1].SavedSearch[C].SearchRequest[1].OrderBy[1].ColumnSortSpec[C].SortOrder" item
        final XMLNodeMappingWrapper xmlAttributeSortOrder = makeReadWriteXMLNodeMapping( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.SortOrderItem.CURRENT_INSTANCE_PATH );
        xmlNodeColumnSortSpec.addChild( xmlAttributeSortOrder );

        return xmlNodeFilters;
    }

    private void addFindButtonRules(
        final ContainerRuleWrapper p_findButtonContainerRule,
        final ContainerRuleWrapper p_preprocessFilterOperandsContainerRule,
        final ContainerRuleWrapper p_autoRefreshStateEntryContainerRule,
        final PropertyWrapper p_autoRefreshIntervalSearchParamItem,
        final PropertyGroupWrapper p_enqColumnSortSpecGroup,
        final PropertyGroupWrapper p_enqSearchFiltersGroup,
        final PropertyWrapper p_invokedInDrilldownContextItem,
        final PropertyWrapper p_irisInputContextItem,
        final PropertyWrapper p_baseIrisSearchUrlItem,
        final PropertyWrapper p_fullIrisSearchUrlItem,
        final PhaseWrapper p_searchResultsPhase,
        final PropertyWrapper p_selectionElemsHideShowStateItem,
        final PropertyWrapper p_searchOutcomeItem,
        final PropertyWrapper p_resultsScreenSearchElemsHideShowStateItem,
        final PropertyWrapper p_scratchItem,
        final PropertyGroupWrapper p_searchResponseGroup,
        final PropertyGroupWrapper p_searchResultGroup,
        final PresentationPhaseWrapper p_searchResultsPresPhase,
        final ContainerRuleWrapper p_refreshButtonContainerRule
	) {
    	AssertionUtils.requireNonNull(p_findButtonContainerRule, "p_findButtonContainerRule");
    	AssertionUtils.requireNonNull(p_preprocessFilterOperandsContainerRule, "p_preprocessFilterOperandsContainerRule");
        AssertionUtils.requireNonNull(p_autoRefreshIntervalSearchParamItem, "p_autoRefreshIntervalSearchParamItem");
    	AssertionUtils.requireNonNull(p_enqColumnSortSpecGroup, "p_enqColumnSortSpecGroup");
        AssertionUtils.requireNonNull(p_enqSearchFiltersGroup, "p_enqSearchFiltersGroup");
        AssertionUtils.requireNonNull(p_invokedInDrilldownContextItem, "p_invokedInDrilldownContextItem");
        AssertionUtils.requireNonNull(p_irisInputContextItem, "p_invokedInDrilldownContextItem");
    	AssertionUtils.requireNonNull(p_fullIrisSearchUrlItem, "p_fullIrisSearchUrlItem");
    	AssertionUtils.requireNonNull(p_searchResultsPhase, "p_searchResultsPhase");
    	AssertionUtils.requireNonNull(p_selectionElemsHideShowStateItem, "p_selectionElemsHideShowStateItem");
    	AssertionUtils.requireNonNull(p_searchOutcomeItem, "p_searchOutcomeItem");
    	AssertionUtils.requireNonNull(p_resultsScreenSearchElemsHideShowStateItem, "p_resultsScreenSearchElemsHideShowStateItem");
    	AssertionUtils.requireNonNull(p_searchResponseGroup, "p_searchResponseGroup");
    	AssertionUtils.requireNonNull(p_searchResultGroup, "p_searchResultGroup");
    	AssertionUtils.requireNonNull(p_searchResultsPresPhase, "p_searchResultsPresPhase");
    	AssertionUtils.requireNonNull(p_refreshButtonContainerRule, "p_refreshButtonContainerRule");

    	// "Create IrisSearchUrl" container rule & children...
    	
        final ContainerRuleWrapper createIrisSearchUrl = ContainerRuleWrapper.create( getFormContext() );
        createIrisSearchUrl.setName( "Create IrisSearchUrl" + (++m_nameCounter) );

        final ResetDataRuleWrapper resetSearchResponseGroupRule = ResetDataRuleWrapper.create( getFormContext() );
        resetSearchResponseGroupRule.setName( "Clear the " + p_searchResponseGroup.getName() + " group" );
        resetSearchResponseGroupRule.setResetPropertyGroup( p_searchResponseGroup );
        createIrisSearchUrl.addTrueRule( resetSearchResponseGroupRule.unwrap() );

        createIrisSearchUrl.unwrap().addTrueRule(p_preprocessFilterOperandsContainerRule.unwrap(), true /* p_linkedObject */);

        final EnquirySearchUrlBuilderRule enquirySearchUrlBuilderRule = new EnquirySearchUrlBuilderRule( getFormContext() );
        enquirySearchUrlBuilderRule.setName( "Build full IRIS Search URL" );
        enquirySearchUrlBuilderRule.setBaseIrisUrl( "$$" + p_baseIrisSearchUrlItem.getEntityKeyName() + "$" );
        enquirySearchUrlBuilderRule.setFilterParamsGroupPath( p_enqSearchFiltersGroup.getPropertyGroupKey() );
        enquirySearchUrlBuilderRule.setRepeatableOrderByParamGroupPath( p_enqColumnSortSpecGroup.getPropertyGroupKey() );
        enquirySearchUrlBuilderRule.setFullIrisSearchUrlOutputItemPath( p_fullIrisSearchUrlItem.getEntityKeyName() );
        enquirySearchUrlBuilderRule.setRuntimeDataDirPath( RUNTIME_ENQUIRY_DATA_DIR_EXPN );
        enquirySearchUrlBuilderRule.setErrorHandlingType( ErrorHandlingItem.ON_ERROR_PASS_UP );
        createIrisSearchUrl.addTrueRule( enquirySearchUrlBuilderRule );

        p_findButtonContainerRule.addTrueRule(createIrisSearchUrl);

        // "Manage conditional transition into Auto Refresh state" container rule & children...
        
        final ContainerRuleWrapper manageConditionalAutoRefreshStateEntryRule = ContainerRuleWrapper.create(getFormContext());
        manageConditionalAutoRefreshStateEntryRule.setName("Manage conditional transition into Auto Refresh state");
        p_findButtonContainerRule.addTrueRule(manageConditionalAutoRefreshStateEntryRule);
        
        final EvaluateRuleWrapper testForPositiveAutoRefreshIntervalValue = EvaluateRuleWrapper.create(getFormContext());
        testForPositiveAutoRefreshIntervalValue.setName("If AutoRefreshIntervalSeconds holds a positive value");
        testForPositiveAutoRefreshIntervalValue.setExpression("$$" + p_autoRefreshIntervalSearchParamItem.getEntityKeyName() + "$ > '0'");
        manageConditionalAutoRefreshStateEntryRule.addTrueRule(testForPositiveAutoRefreshIntervalValue);
        testForPositiveAutoRefreshIntervalValue.addTrueRuleLink(p_autoRefreshStateEntryContainerRule);

        // "Execute IrisSearchUrl" container rule & children...
        
        final ContainerRuleWrapper executeIrisSearchUrl = ContainerRuleWrapper.create( getFormContext() );
        executeIrisSearchUrl.setName(RuleNames.EXEC_IRIS_SEARCH_URL);
        p_findButtonContainerRule.addTrueRule(executeIrisSearchUrl);

        final EvaluateRuleWrapper evalOrderByOrFilterParamsSupplied = EvaluateRuleWrapper.create( getFormContext() );
        evalOrderByOrFilterParamsSupplied.setName( "If are are NOT hiding search elements due to being a no-selection enquiry invoked in COS context AND order-by or filter parameters were supplied" );
        evalOrderByOrFilterParamsSupplied.setExpression(
			"$$" + p_selectionElemsHideShowStateItem.getEntityKeyName() + "$ != '" + Lists.ShowOrHide.Keys.HIDE + "' AND " +
			"$$" + DataStore.WorkingElementsGroup.ItemPaths.FULL_IRIS_SEARCH_URL + "$ != '$$" + DataStore.LogicalScreenModel.ItemPaths.IRIS_INPUT_CONTEXT + "$'"
        );
        executeIrisSearchUrl.addTrueRule( evalOrderByOrFilterParamsSupplied.unwrap() );

        final SetValueRuleWrapper showResultScreenSearchElemsRule = SetValueRuleWrapper.create( getFormContext() );
        showResultScreenSearchElemsRule.setName( "Show the result screen search elements" );
        showResultScreenSearchElemsRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        showResultScreenSearchElemsRule.setPropertyName( p_resultsScreenSearchElemsHideShowStateItem );
        showResultScreenSearchElemsRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
        showResultScreenSearchElemsRule.setFromValue( "SHOW" );
        showResultScreenSearchElemsRule.setFromValueList( "SHOW" );
        evalOrderByOrFilterParamsSupplied.addTrueRule( showResultScreenSearchElemsRule.unwrap() );

        final SetValueRuleWrapper hideResultScreenSearchElemsRule = SetValueRuleWrapper.create( getFormContext() );
        hideResultScreenSearchElemsRule.setName( "Hide the result screen search elements" );
        hideResultScreenSearchElemsRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        hideResultScreenSearchElemsRule.setPropertyName( p_resultsScreenSearchElemsHideShowStateItem );
        hideResultScreenSearchElemsRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
        hideResultScreenSearchElemsRule.setFromValue( "HIDE" );
        hideResultScreenSearchElemsRule.setFromValueList( "HIDE" );
        evalOrderByOrFilterParamsSupplied.addFalseRule( hideResultScreenSearchElemsRule.unwrap() );

        final SetValueRuleWrapper setSearchOutcomeToNoResultsFoundRule = SetValueRuleWrapper.create( getFormContext() );
        setSearchOutcomeToNoResultsFoundRule.setName(RuleNames.SET_SEARCH_OUTCOME_TO_NO_RESULTS_FOUND);
        setSearchOutcomeToNoResultsFoundRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setSearchOutcomeToNoResultsFoundRule.setPropertyName( p_searchOutcomeItem );
        setSearchOutcomeToNoResultsFoundRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
        setSearchOutcomeToNoResultsFoundRule.setFromValue( Lists.SearchOutcome.Keys.NO_RESULTS_FOUND );
        setSearchOutcomeToNoResultsFoundRule.setFromValueList( Lists.SearchOutcome.Keys.NO_RESULTS_FOUND );
        executeIrisSearchUrl.addTrueRule(setSearchOutcomeToNoResultsFoundRule);

        final ContainerRuleWrapper searchRequestDispatchRulesContainer = ContainerRuleWrapper.create(getFormContext(), RuleNames.SEARCH_REQUEST_DISPATCH);
        
        executeIrisSearchUrl.addTrueRule(searchRequestDispatchRulesContainer);
        
        final EvaluateRuleWrapper evalInvokedInDrilldownContextRule = EvaluateRuleWrapper.create(getFormContext());
        evalInvokedInDrilldownContextRule.setName("If we got here by being invoked in drilldown context - as opposed to user executing search manually");
        evalInvokedInDrilldownContextRule.setExpression("$$" + p_invokedInDrilldownContextItem.getEntityKeyName() + "$ == 'true'");

        final SetValueRuleWrapper setFullIrisSearchUrlFromIrisinputContext = SetValueRuleWrapper.create(getFormContext());
        setFullIrisSearchUrlFromIrisinputContext.setName("Set " + p_fullIrisSearchUrlItem.getName() + " from IRISInputContext");
        setFullIrisSearchUrlFromIrisinputContext.setType(SetValueRuleWrapper.EType.DATA_ITEM);
        setFullIrisSearchUrlFromIrisinputContext.setPropertyName(p_fullIrisSearchUrlItem);
        setFullIrisSearchUrlFromIrisinputContext.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
        setFullIrisSearchUrlFromIrisinputContext.setFromPropertyName(p_irisInputContextItem);
        evalInvokedInDrilldownContextRule.addTrueRule(setFullIrisSearchUrlFromIrisinputContext);

        p_refreshButtonContainerRule.addTrueRule(evalInvokedInDrilldownContextRule);
        p_refreshButtonContainerRule.addTrueRuleLink(searchRequestDispatchRulesContainer);

        final PropertyGroupWrapper searchResponseGroup = PropertyGroupWrapper.wrap((PropertyGroup) m_irisResultGroupWrapper.unwrap().getParent());

        final ResetDataRuleWrapper clearIrisResultGroupRule = ResetDataRuleWrapper.create(getFormContext());
        clearIrisResultGroupRule.setName("Reset the " + searchResponseGroup.getName() + " group");
        clearIrisResultGroupRule.setResetPropertyGroup(searchResponseGroup);
        searchRequestDispatchRulesContainer.addTrueRule(clearIrisResultGroupRule);

        final IRISRequest irisRequestRule = new IRISRequest(getFormContext());
        irisRequestRule.setName(RuleNames.IRIS_REQUEST);
        irisRequestRule.setErrorHandlingType("Goto Error Phase");
        irisRequestRule.setAttribute("ErrorHandlingType", "Pass Up");
        irisRequestRule.setAttribute(IRISRequest.TARGET_URL, "$$" + p_fullIrisSearchUrlItem.getEntityKeyName() + "$");
        irisRequestRule.setAttribute(IRISRequest.TARGET_GROUP, m_irisResultGroupWrapper.getEntityKeyName());
        irisRequestRule.setAttribute(IRISRequest.TOP_LINKS, DataStore.WorkingElementsGroup.TopLevelLinksGroup.LinksGroup.CURRENT_INSTANCE_PATH);
        irisRequestRule.setAttribute(IRISRequest.APPENDS_RESULT, "N");
        searchRequestDispatchRulesContainer.addTrueRule( irisRequestRule);

        final EvaluateRuleWrapper evalResultsReturnedRule = EvaluateRuleWrapper.create(getFormContext());
        evalResultsReturnedRule.setName(RuleNames.TEST_WHETHER_ANY_RESULTS_RETURNED);
        evalResultsReturnedRule.setExpression("$$" + m_irisResultGroupWrapper.getEntityKeyName() + ".firstFreeInstance()$ > 1");
        irisRequestRule.addTrueRule(evalResultsReturnedRule.unwrap(), false /* p_linkedObject */);

        final ContainerRuleWrapper resultsFoundRulesContainer = ContainerRuleWrapper.create(getFormContext(), "Handle results found");
        evalResultsReturnedRule.addTrueRule(resultsFoundRulesContainer);

        final boolean haveBreakChangeItems = m_irisResultGroupWrapper.includesIrisBreakChangeItems();
        final boolean haveBreakChangeControlElems = (m_breakChangeControlGroupElems != null);

        if (haveBreakChangeItems && haveBreakChangeControlElems)
        {
            final PropertyGroupWrapper workingElementsGroup = PropertyGroupWrapper.wrap((PropertyGroup) p_scratchItem.unwrap().getParent());
            final BreakChangeSnapshotGroupElems breakChangeSnapshotGroupElems = new BreakChangeSnapshotGroupElems(this, workingElementsGroup);

            final PropertyWrapper
                prevBreakChangeListEntryDisplayValueItem = breakChangeSnapshotGroupElems.prevBreakChangeListEntryDisplayValueItem,
                currBreakChangeListEntryDisplayValueItem = breakChangeSnapshotGroupElems.currBreakChangeListEntryDisplayValueItem,
                irisResultStartInstanceForNextBreakChangeListEntry = breakChangeSnapshotGroupElems.irisResultStartInstanceForNextBreakChangeListEntry,
                irisResultEndInstanceForNextBreakChangeListEntry = breakChangeSnapshotGroupElems.irisResultEndInstanceForNextBreakChangeListEntry;

            final ContainerRuleWrapper
                populateDynBreakChangeListContainer = ContainerRuleWrapper.create(getFormContext()),
                snapshotCurrentBreakChangeValuesContainer = ContainerRuleWrapper.create(getFormContext(), "Update " + breakChangeSnapshotGroupElems.breakChangeValuesSnapshotGroup.getName() + " for the current " + m_irisResultGroupWrapper.getName() + " instance");

            resultsFoundRulesContainer.addTrueRule(populateDynBreakChangeListContainer);

            final ResetDataRuleWrapper resetBreakChangeControlGroupAndDynListValues = ResetDataRuleWrapper.create(getFormContext());
            resetBreakChangeControlGroupAndDynListValues.setName("Reset our " + m_breakChangeControlGroupElems.breakChangeControlGroup.getName() + " group - including dynamic list values");
            resetBreakChangeControlGroupAndDynListValues.setResetList(m_breakChangeControlGroupElems.breakChangeDropdownItem);
            resetBreakChangeControlGroupAndDynListValues.setResetPropertyGroup(m_breakChangeControlGroupElems.breakChangeControlGroup);
            populateDynBreakChangeListContainer.addTrueRule(resetBreakChangeControlGroupAndDynListValues);

            final SetValueRuleWrapper initIrisResultStartAndEndInstanceForNextBreakChangeListEntry = SetValueRuleWrapper.create(getFormContext());
            initIrisResultStartAndEndInstanceForNextBreakChangeListEntry.setName("Initialise " + breakChangeSnapshotGroupElems.irisResultStartInstanceForNextBreakChangeListEntry.getName() + " and " + breakChangeSnapshotGroupElems.irisResultEndInstanceForNextBreakChangeListEntry.getName() + " to 1");
            initIrisResultStartAndEndInstanceForNextBreakChangeListEntry.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            initIrisResultStartAndEndInstanceForNextBreakChangeListEntry.setPropertyNameFromEntityKey(breakChangeSnapshotGroupElems.irisResultStartInstanceForNextBreakChangeListEntry.getEntityKeyName() + ',' + breakChangeSnapshotGroupElems.irisResultEndInstanceForNextBreakChangeListEntry.getEntityKeyName());
            initIrisResultStartAndEndInstanceForNextBreakChangeListEntry.setFromType(SetValueRuleWrapper.EFromType.VALUE);
            initIrisResultStartAndEndInstanceForNextBreakChangeListEntry.setFromValue("1");
            populateDynBreakChangeListContainer.addTrueRule(initIrisResultStartAndEndInstanceForNextBreakChangeListEntry);

            final SetValueRuleWrapper zeroiseNumBreakChangeValuesRule = SetValueRuleWrapper.create(getFormContext());
            zeroiseNumBreakChangeValuesRule.setName("Zeroise " + m_breakChangeControlGroupElems.numBreakChangeValuesItem.getName());
            zeroiseNumBreakChangeValuesRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            zeroiseNumBreakChangeValuesRule.setPropertyName(m_breakChangeControlGroupElems.numBreakChangeValuesItem);
            zeroiseNumBreakChangeValuesRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
            zeroiseNumBreakChangeValuesRule.setFromValue("0");
            populateDynBreakChangeListContainer.addTrueRule(zeroiseNumBreakChangeValuesRule);

            final RepeatRuleWrapper repeatOverAllIrisResultInstances = RepeatRuleWrapper.create( getFormContext() );
            repeatOverAllIrisResultInstances.setName("For each populated " + m_irisResultGroupWrapper.getName() + " instance");
            repeatOverAllIrisResultInstances.setRepeatType(RepeatRuleWrapper.ERepeatType.DATA_GROUP);
            repeatOverAllIrisResultInstances.setDataGroupName(m_irisResultGroupWrapper);
            repeatOverAllIrisResultInstances.setRichEndInstance("$$" + m_irisResultGroupWrapper.getEntityKeyName() + ".lastInstance()$" );
            repeatOverAllIrisResultInstances.setIgnoreChildRuleResults(Boolean.TRUE);
            populateDynBreakChangeListContainer.addTrueRule(repeatOverAllIrisResultInstances);
            repeatOverAllIrisResultInstances.addTrueRule(snapshotCurrentBreakChangeValuesContainer);

            final StringBuilder
                currBreakChangeListEntryValueBuilder = new StringBuilder(),
                breakChangeT24FieldnameListBuilder = new StringBuilder();

            final Iterator<IrisResultBreakChangeItem> irisResultBreakChangeItemIter = m_irisResultGroupWrapper.breakChangeItemList.iterator();

            while(irisResultBreakChangeItemIter.hasNext())
            {
                final IrisResultBreakChangeItem irisResultBreakChangeItem = irisResultBreakChangeItemIter.next();

                if (breakChangeT24FieldnameListBuilder.length() > 0)
                    breakChangeT24FieldnameListBuilder.append(irisResultBreakChangeItemIter.hasNext() ? " " : " and ");

                breakChangeT24FieldnameListBuilder.append(irisResultBreakChangeItem.getIrisResultItemSpec().getT24FieldName());

                final PropertyWrapper
                    irisResultBreakChangeDataItem = irisResultBreakChangeItem.getBreakChangeDataItem(),
                    breakChangeValueSnapshotItem = irisResultBreakChangeDataItem.clone();

                breakChangeSnapshotGroupElems.breakChangeValuesSnapshotGroup.addChild(breakChangeValueSnapshotItem);

                final EvaluateRuleWrapper evalIsCurrentIrisBreakChangeItemBlank = EvaluateRuleWrapper.create(getFormContext());
                evalIsCurrentIrisBreakChangeItemBlank.setName("Is the current IRIS value for break-item " + irisResultBreakChangeDataItem.getName() + " blank");
                evalIsCurrentIrisBreakChangeItemBlank.setExpression("$$" + irisResultBreakChangeDataItem.getEntityKeyName() + "$ == '&nbsp;'");
                snapshotCurrentBreakChangeValuesContainer.addTrueRule(evalIsCurrentIrisBreakChangeItemBlank);

                final SetValueRuleWrapper copyPrevBreakChangeItemValueToIrisResultItem = SetValueRuleWrapper.create(getFormContext());
                copyPrevBreakChangeItemValueToIrisResultItem.setName("Copy previous " + breakChangeValueSnapshotItem.getName() + " to " + m_irisResultGroupWrapper.getName());
                copyPrevBreakChangeItemValueToIrisResultItem.setType(SetValueRuleWrapper.EType.DATA_ITEM);
                copyPrevBreakChangeItemValueToIrisResultItem.setPropertyName(irisResultBreakChangeDataItem);
                copyPrevBreakChangeItemValueToIrisResultItem.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
                copyPrevBreakChangeItemValueToIrisResultItem.setFromPropertyName(breakChangeValueSnapshotItem);
                copyPrevBreakChangeItemValueToIrisResultItem.setCopyIfBlank(Boolean.TRUE);
                evalIsCurrentIrisBreakChangeItemBlank.addTrueRule(copyPrevBreakChangeItemValueToIrisResultItem);

                final EvaluateRuleWrapper evalDoUpdateBreakChangeItemValueSnapshot = EvaluateRuleWrapper.create(getFormContext());
                evalDoUpdateBreakChangeItemValueSnapshot.setName("Do we need to update our snapshot for break-item  " + breakChangeValueSnapshotItem.getName());
                evalDoUpdateBreakChangeItemValueSnapshot.setExpression(
                    "$$" + breakChangeValueSnapshotItem.getEntityKeyName() + "$ == '' " +
                    "OR (" +
                        "$$" + irisResultBreakChangeDataItem.getEntityKeyName() + "$ != '' " +
                        "AND $$" + irisResultBreakChangeDataItem.getEntityKeyName() + "$ != $$" + breakChangeValueSnapshotItem.getEntityKeyName() + "$" +
                    ")"
                );
                snapshotCurrentBreakChangeValuesContainer.addTrueRule(evalDoUpdateBreakChangeItemValueSnapshot);

                if (currBreakChangeListEntryValueBuilder.length() > 0) currBreakChangeListEntryValueBuilder.append(' ');
                currBreakChangeListEntryValueBuilder.append("$$" + breakChangeValueSnapshotItem.getEntityKeyName() + "$");

                final SetValueRuleWrapper setBreakChangeValueSnapshotItemRule = SetValueRuleWrapper.create(getFormContext());
                setBreakChangeValueSnapshotItemRule.setName("Set snapshot item - " + breakChangeValueSnapshotItem.getName());
                setBreakChangeValueSnapshotItemRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
                setBreakChangeValueSnapshotItemRule.setPropertyName(breakChangeValueSnapshotItem);
                setBreakChangeValueSnapshotItemRule.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
                setBreakChangeValueSnapshotItemRule.setFromPropertyName(irisResultBreakChangeDataItem);
                evalDoUpdateBreakChangeItemValueSnapshot.addTrueRule(setBreakChangeValueSnapshotItemRule);
            }

            populateDynBreakChangeListContainer.setName("Populate our dynamic break-change list from the unique " + breakChangeT24FieldnameListBuilder + " values in the IRIS response");

            final ResetDataRuleWrapper resetBreakChangeValuesSnapshotGroupRule = ResetDataRuleWrapper.create(getFormContext());
            resetBreakChangeValuesSnapshotGroupRule.setName("Reset our scratch " + breakChangeSnapshotGroupElems.breakChangeValuesSnapshotGroup.getName() + " group");
            resetBreakChangeValuesSnapshotGroupRule.setResetPropertyGroup(breakChangeSnapshotGroupElems.breakChangeValuesSnapshotGroup);
            populateDynBreakChangeListContainer.addTrueRule(resetBreakChangeValuesSnapshotGroupRule);

            final SetValueRuleWrapper setCurrBreakChangeListEntryDisplayValueRule = SetValueRuleWrapper.create(getFormContext());
            setCurrBreakChangeListEntryDisplayValueRule.setName("Set " + breakChangeSnapshotGroupElems.currBreakChangeListEntryDisplayValueItem.getName() + " value");
            setCurrBreakChangeListEntryDisplayValueRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            setCurrBreakChangeListEntryDisplayValueRule.setPropertyName(currBreakChangeListEntryDisplayValueItem);
            setCurrBreakChangeListEntryDisplayValueRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
            setCurrBreakChangeListEntryDisplayValueRule.setFromValue(currBreakChangeListEntryValueBuilder.toString());
            snapshotCurrentBreakChangeValuesContainer.addTrueRule(setCurrBreakChangeListEntryDisplayValueRule);

            final ContainerRuleWrapper addAnotherBreakChangeListItemIfNecessaryContainer = ContainerRuleWrapper.create(getFormContext(), "Add a list entry to our break change list if necessary");
            repeatOverAllIrisResultInstances.addTrueRule(addAnotherBreakChangeListItemIfNecessaryContainer);

            final EvaluateRuleWrapper evalIsPrevBreakChangeListEntryDisplayValueItemBlank = EvaluateRuleWrapper.create(getFormContext());
            evalIsPrevBreakChangeListEntryDisplayValueItemBlank.setName("Is " + prevBreakChangeListEntryDisplayValueItem.getName() + " blank");
            evalIsPrevBreakChangeListEntryDisplayValueItemBlank.setExpression("$$" + prevBreakChangeListEntryDisplayValueItem.getEntityKeyName() + "$ == ''");
            addAnotherBreakChangeListItemIfNecessaryContainer.addTrueRule(evalIsPrevBreakChangeListEntryDisplayValueItemBlank);

            final SetValueRuleWrapper setPrevBreakChangeListEntryDisplayValueItemFromCurrent = SetValueRuleWrapper.create(getFormContext());
            setPrevBreakChangeListEntryDisplayValueItemFromCurrent.setName("Set " + prevBreakChangeListEntryDisplayValueItem.getName() + " from " + currBreakChangeListEntryDisplayValueItem.getName());
            setPrevBreakChangeListEntryDisplayValueItemFromCurrent.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            setPrevBreakChangeListEntryDisplayValueItemFromCurrent.setPropertyName(prevBreakChangeListEntryDisplayValueItem);
            setPrevBreakChangeListEntryDisplayValueItemFromCurrent.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
            setPrevBreakChangeListEntryDisplayValueItemFromCurrent.setFromPropertyName(currBreakChangeListEntryDisplayValueItem);
            evalIsPrevBreakChangeListEntryDisplayValueItemBlank.addTrueRule(setPrevBreakChangeListEntryDisplayValueItemFromCurrent);

            final EvaluateRuleWrapper evalDoAddAnotherBreakChangeListItem = EvaluateRuleWrapper.create(getFormContext());
            evalDoAddAnotherBreakChangeListItem.setName("Do we need to add a list entry to our dynamic break change list item");
            evalDoAddAnotherBreakChangeListItem.setExpression("$$" + currBreakChangeListEntryDisplayValueItem.getEntityKeyName() + "$ != $$" + prevBreakChangeListEntryDisplayValueItem.getEntityKeyName() + "$");
            evalIsPrevBreakChangeListEntryDisplayValueItemBlank.addFalseRule(evalDoAddAnotherBreakChangeListItem);

            final ContainerRuleWrapper doAddNextBreakChangeListItemContainer = ContainerRuleWrapper.create(getFormContext());
            doAddNextBreakChangeListItemContainer.setName("Add a new list entry and set " + m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey.getName() + " item with the key if this is the 1st entry to be added");
            evalDoAddAnotherBreakChangeListItem.addTrueRule(doAddNextBreakChangeListItemContainer);

            final AddToListRuleWrapper addListEntryToDynBreakChangeListRule = AddToListRuleWrapper.create(getFormContext());
            addListEntryToDynBreakChangeListRule.setName("Add a list entry to the dynamic list refd by our " + m_breakChangeControlGroupElems.breakChangeDropdownItem.getName() + " item");
            addListEntryToDynBreakChangeListRule.setErrorHandlingType( AddToListRuleWrapper.EErrorHandlingType.PASS_UP );
            addListEntryToDynBreakChangeListRule.setListType(AddToListRuleWrapper.EListType.DATA_ITEM);
            addListEntryToDynBreakChangeListRule.setListToAddTo(m_breakChangeControlGroupElems.breakChangeDropdownItem);
            addListEntryToDynBreakChangeListRule.setKeyType(AddToListRuleWrapper.EKeyType.DATA_ITEM);
            addListEntryToDynBreakChangeListRule.setKeyPropertyName(irisResultStartInstanceForNextBreakChangeListEntry);
            addListEntryToDynBreakChangeListRule.setValueType(AddToListRuleWrapper.EValueType.DATA_ITEM);
            addListEntryToDynBreakChangeListRule.setValuePropertyName(prevBreakChangeListEntryDisplayValueItem);
            addListEntryToDynBreakChangeListRule.setGroupValueType(AddToListRuleWrapper.EGroupValueType.DATA_ITEM);
            addListEntryToDynBreakChangeListRule.setGroupValuePropertyName(irisResultEndInstanceForNextBreakChangeListEntry);
            doAddNextBreakChangeListItemContainer.addTrueRule(addListEntryToDynBreakChangeListRule);

            final EvaluateRuleWrapper evalFirstBreakChangeListKeyAsYetUnset = EvaluateRuleWrapper.create(getFormContext());
            evalFirstBreakChangeListKeyAsYetUnset.setName("Is the " + m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey.getName() + " item within our " + m_breakChangeControlGroupElems.breakChangeControlGroup.getName() + " as yet unset");
            evalFirstBreakChangeListKeyAsYetUnset.setExpression("$$" + m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey.getEntityKeyName() + "$ == ''");
            doAddNextBreakChangeListItemContainer.addTrueRule(evalFirstBreakChangeListKeyAsYetUnset);

            final SetValueRuleWrapper setBreakControlFirstBreakChangeListKey = SetValueRuleWrapper.create(getFormContext());
            setBreakControlFirstBreakChangeListKey.setName("Set " + m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey.getEntityKeyName() + " to the list key just added");
            setBreakControlFirstBreakChangeListKey.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            setBreakControlFirstBreakChangeListKey.setPropertyName(m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey);
            setBreakControlFirstBreakChangeListKey.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
            setBreakControlFirstBreakChangeListKey.setFromPropertyName(irisResultStartInstanceForNextBreakChangeListEntry);
            evalFirstBreakChangeListKeyAsYetUnset.addTrueRule(setBreakControlFirstBreakChangeListKey);
            doAddNextBreakChangeListItemContainer.addTrueRuleLink(setPrevBreakChangeListEntryDisplayValueItemFromCurrent);

            final IncrementorRuleWrapper incrementNumBreakChangeValuesRule = IncrementorRuleWrapper.create(getFormContext());
            incrementNumBreakChangeValuesRule.setName("Increment NumBreakChangeValues");
            incrementNumBreakChangeValuesRule.setOperator(IncrementorRuleWrapper.EOperator.INCREMENT);
            incrementNumBreakChangeValuesRule.setType(IncrementorRuleWrapper.EType.DATA_ITEM);
            incrementNumBreakChangeValuesRule.setPropertyNameFromEntityKey("WorkingElements[1].BreakChangeControl[1].NumBreakChangeValues");
            incrementNumBreakChangeValuesRule.setIncrementBy("1");
            doAddNextBreakChangeListItemContainer.addTrueRule(incrementNumBreakChangeValuesRule);

            final SetValueRuleWrapper setIrisResultStartInstanceForNextBreakChangeListEntry = SetValueRuleWrapper.create(getFormContext());
            setIrisResultStartInstanceForNextBreakChangeListEntry.setName("Set " + irisResultStartInstanceForNextBreakChangeListEntry.getName() + " and " + irisResultEndInstanceForNextBreakChangeListEntry.getName() + " from current " + m_irisResultGroupWrapper.getName() + " instance");
            setIrisResultStartInstanceForNextBreakChangeListEntry.setType(SetValueRuleWrapper.EType.DATA_ITEM);
            setIrisResultStartInstanceForNextBreakChangeListEntry.setPropertyNameFromEntityKey(irisResultStartInstanceForNextBreakChangeListEntry.getEntityKeyName() + "," + irisResultEndInstanceForNextBreakChangeListEntry.getEntityKeyName());
            setIrisResultStartInstanceForNextBreakChangeListEntry.setFromType(SetValueRuleWrapper.EFromType.VALUE);
            setIrisResultStartInstanceForNextBreakChangeListEntry.setFromValue("$$" + m_irisResultGroupWrapper.getEntityKeyName() + ".instance()$");
            doAddNextBreakChangeListItemContainer.addTrueRule(setIrisResultStartInstanceForNextBreakChangeListEntry);

            final IncrementorRuleWrapper incrementIrisResultEndInstanceForNextBreakChangeListEntry = IncrementorRuleWrapper.create(getFormContext());
            incrementIrisResultEndInstanceForNextBreakChangeListEntry.setName("Increment " + irisResultEndInstanceForNextBreakChangeListEntry.getName() + " by 1");
            incrementIrisResultEndInstanceForNextBreakChangeListEntry.setOperator( IncrementorRuleWrapper.EOperator.INCREMENT );
            incrementIrisResultEndInstanceForNextBreakChangeListEntry.setType(IncrementorRuleWrapper.EType.DATA_ITEM);
            incrementIrisResultEndInstanceForNextBreakChangeListEntry.setPropertyName(irisResultEndInstanceForNextBreakChangeListEntry);
            incrementIrisResultEndInstanceForNextBreakChangeListEntry.setIncrementBy("1");
            evalDoAddAnotherBreakChangeListItem.addFalseRule(incrementIrisResultEndInstanceForNextBreakChangeListEntry);

            final ContainerRuleWrapper ensureThatFinalListEntryIsAdded = ContainerRuleWrapper.create(getFormContext(), "Ensure that the final list entry is added");
            repeatOverAllIrisResultInstances.addFalseRule(ensureThatFinalListEntryIsAdded);

            final EvaluateRuleWrapper evalIsFinalEntryWaitingToBeAdded = EvaluateRuleWrapper.create(getFormContext());
            evalIsFinalEntryWaitingToBeAdded.setName("Is there a final entry waiting to be added");
            evalIsFinalEntryWaitingToBeAdded.setExpression("$$" + prevBreakChangeListEntryDisplayValueItem.getEntityKeyName() + "$ != ''");
            ensureThatFinalListEntryIsAdded.addTrueRule(evalIsFinalEntryWaitingToBeAdded);

            evalIsFinalEntryWaitingToBeAdded.addTrueRuleLink(addListEntryToDynBreakChangeListRule);
            evalIsFinalEntryWaitingToBeAdded.addTrueRuleLink(evalFirstBreakChangeListKeyAsYetUnset);
            evalIsFinalEntryWaitingToBeAdded.addTrueRuleLink(incrementNumBreakChangeValuesRule);
        }

        final ContainerRuleWrapper containerRuleToPopulateNewDisplayResultGroupsFromIrisResponse = createRuleToPopulateNewDisplayResultGroupsFromIrisResponse(p_scratchItem);
        getRulesRoot().addChild(containerRuleToPopulateNewDisplayResultGroupsFromIrisResponse);
        resultsFoundRulesContainer.addTrueRuleLink(containerRuleToPopulateNewDisplayResultGroupsFromIrisResponse);

        if (m_newBreakChangeQWP != null)
            m_newBreakChangeQWP.wrapperObject.addLink(containerRuleToPopulateNewDisplayResultGroupsFromIrisResponse);

        final SetValueRuleWrapper setSearchOutcomeToResultsFoundRule = SetValueRuleWrapper.create( getFormContext() );
        setSearchOutcomeToResultsFoundRule.setName( "Set SearchOutcome to " + Lists.SearchOutcome.Keys.RESULTS_FOUND );
        setSearchOutcomeToResultsFoundRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setSearchOutcomeToResultsFoundRule.setPropertyName( p_searchOutcomeItem );
        setSearchOutcomeToResultsFoundRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
        setSearchOutcomeToResultsFoundRule.setFromValue( Lists.SearchOutcome.Keys.RESULTS_FOUND );
        setSearchOutcomeToResultsFoundRule.setFromValueList( Lists.SearchOutcome.Keys.RESULTS_FOUND );
        resultsFoundRulesContainer.addTrueRule(setSearchOutcomeToResultsFoundRule);

        final GotoRuleWrapper goForwardToSearchResultsPhaseRule = makeGoForwardToPhaseRule( p_searchResultsPhase );
        resultsFoundRulesContainer.addTrueRule(goForwardToSearchResultsPhaseRule);
    } // addFindButtonRules()

    private void addCsvFileGeneratorRule(ContainerRuleWrapper p_csvFileGenerationContainerRule, PropertyGroupWrapper p_searchResultGroup, PropertyWrapper p_downloadFilePathItem)
    {
        final EnquiryResultsCSVGeneratorRule csvFileGenereratorRule = new EnquiryResultsCSVGeneratorRule( getFormContext() );
        csvFileGenereratorRule.setName( "CSV File Genererator" );
        csvFileGenereratorRule.setAttribute( "ErrorHandlingType", "Pass Up" );
		csvFileGenereratorRule.setAttribute(EnquiryResultsCSVGeneratorRule.RuleAttrNames.REPEATABLE_CSV_DATA_ROW_GROUP_PATH, p_searchResultGroup.getPropertyGroupKey());
		csvFileGenereratorRule.setAttribute(EnquiryResultsCSVGeneratorRule.RuleAttrNames.CSV_OUTPUT_FILE_PATH, "$$" + p_downloadFilePathItem.getPropertyKey(null) + "$");
        p_csvFileGenerationContainerRule.addTrueRule( csvFileGenereratorRule );
    }

	private void addExecSavedSearchRules(
	    ContainerRuleWrapper p_loadAndExecSavedSearchContainerRule,
	    ContainerRuleWrapper p_loadResultsScreenAutoRefreshValueFromSearchParamRule,
	    ContainerRuleWrapper p_findButtonContainerRule,
	    PropertyGroupWrapper enqSearchRequestGroup
	) {
        final SetValueRuleWrapper populateSearchRequestRule = SetValueRuleWrapper.create( getFormContext() );
        populateSearchRequestRule.setName( "Populate SearchRequest from the selected saved search instance" );
        populateSearchRequestRule.setType( SetValueRuleWrapper.EType.DATA_GROUP );
        populateSearchRequestRule.setPropertyGroupName( enqSearchRequestGroup );
        populateSearchRequestRule.setRecursiveCopy( Boolean.TRUE );
        populateSearchRequestRule.setCopyIfBlank( Boolean.TRUE );
        populateSearchRequestRule.setFromType( SetValueRuleWrapper.EFromType.DATA_GROUP );
        populateSearchRequestRule.setFromPropertyGroupNameFromEntityKey( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.CURRENT_INSTANCE_PATH );
        
        p_loadAndExecSavedSearchContainerRule.addTrueRule( populateSearchRequestRule.unwrap() );
        p_loadAndExecSavedSearchContainerRule.addTrueRuleLink(p_loadResultsScreenAutoRefreshValueFromSearchParamRule);
        p_loadAndExecSavedSearchContainerRule.addTrueRuleLink(p_findButtonContainerRule);
    }

	private void addCopySearchToCurrentSavedSearchRules(ContainerRuleWrapper p_copySearchToCurrentSavedSearchRule, PropertyGroupWrapper p_searchRequestGroup)
    {
        final SetValueRuleWrapper populateCurrentSavedSearchInstanceFromSearchRequestGroupValues = SetValueRuleWrapper.create( getFormContext() );
        populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.setName( "Populate current SavedSearch instance from SearchRequest group values" );
        populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.setType( SetValueRuleWrapper.EType.DATA_GROUP );
        populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.setPropertyGroupNameFromEntityKey( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.CURRENT_INSTANCE_PATH );
        populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.setRecursiveCopy( Boolean.TRUE );
        populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.setCopyIfBlank( Boolean.FALSE );
        populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.setFromType( SetValueRuleWrapper.EFromType.DATA_GROUP );
        populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.setFromPropertyGroupNameFromEntityKey( p_searchRequestGroup.getPropertyGroupKey() );
        p_copySearchToCurrentSavedSearchRule.addTrueRule( populateCurrentSavedSearchInstanceFromSearchRequestGroupValues.unwrap() );
    }

    private BasicEnquiryProject(
    	EnquiryMapper p_enquiryMapper, TemplateProject p_templateProject,
    	String p_ifpFriendlylEnquiryName,
    	Enquiry p_enquiry,
    	ModelLoader p_modelLoader
    ) {
        super( AssertionUtils.requireNonNull( p_templateProject, "p_templateProject" ).getProject(), p_enquiryMapper );

        m_templateProject = p_templateProject;
        m_ifpFriendlyEnquiryName = AssertionUtils.requireNonNullAndNonEmpty( p_ifpFriendlylEnquiryName, "p_ifpFriendlylEnquiryName" );
        m_enquiry = AssertionUtils.requireNonNull( p_enquiry, "p_enquiry" );

        m_enquiryApplicationMdfClass = EMUtils.getAppByEnquiry(p_modelLoader, p_enquiry);
        m_irisEnquiryMetaData = new IrisEnquiryMetaData(m_enquiry, m_enquiryApplicationMdfClass);
        m_enquiryFieldsDigest = new EnquiryFieldsDigest(this, m_enquiry);
        m_enquiryAttrsDigest = new EnquiryAttrsDigest(m_enquiryFieldsDigest);

        m_rulesRoot = getProject().getRulesRoot().unwrap();
    }

    private GotoRuleWrapper makeGoBackToNamedPhaseRule(PhaseWrapper p_targetPhase)
    {
        final GotoRuleWrapper result = GotoRuleWrapper.create( getFormContext() );
        result.setName( "Go back to Search phase" );
        result.setOperationType( GotoRuleWrapper.EOperationType.GO_BACK_TO_A_NAMED_PHASE );
        result.setPhase( p_targetPhase );
        result.setRemainingRules( GotoRuleWrapper.ERemainingRules.NEVER );
        return result;
    }

    private GotoRuleWrapper makeGoForwardToPhaseRule(PhaseWrapper p_phaseToGoTo)
    {
        final GotoRuleWrapper result = GotoRuleWrapper.create( getFormContext() );

        result.setName( "Go forward to " + p_phaseToGoTo.getName() );
        result.setOperationType( GotoRuleWrapper.EOperationType.GO_FORWARD_TO_A_NAMED_PHASE );
        result.setPhase( p_phaseToGoTo );
        result.setRemainingRules( ERemainingRules.NEVER );

        return result;
    }

    private XMLNodeRelationshipWrapper makeXMLNodeRelationshipForSingleInstanceGroup(String p_dataGroupName)
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_dataGroupName, "p_dataGroupName" );

        XMLNodeRelationshipWrapper result = XMLNodeRelationshipWrapper.create( getFormContext() );
        result.setName( "XML Node " + p_dataGroupName );
        result.setNodeName( p_dataGroupName );

        return result;
    }

    private XMLNodeRelationshipWrapper makeXMLNodeRelationshipForMultiInstanceGroup(String p_dataGroupCurrentInstancePath)
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_dataGroupCurrentInstancePath, "p_dataGroupCurrentInstancePath" );

        final int indexOfFinalDot = p_dataGroupCurrentInstancePath.lastIndexOf( '.' );
		AssertionUtils.requireConditionTrue((indexOfFinalDot > 0) && (indexOfFinalDot < (p_dataGroupCurrentInstancePath.length() - 1)), "(indexOfFinalDot > 0) && (indexOfFinalDot < (p_dataGroupCurrentInstancePath.length() - 1))");

        String leafGroupName = stripTrailingInstanceSuffix( p_dataGroupCurrentInstancePath.substring( indexOfFinalDot + 1 ) );

        final XMLNodeRelationshipWrapper result = makeXMLNodeRelationshipForSingleInstanceGroup( leafGroupName );
        result.setPropertyGroupsToIncrementFromEntityKey( p_dataGroupCurrentInstancePath );

        return result;
    }

    private XMLNodeMappingWrapper makeReadWriteXMLNodeMapping(String p_dataItemPath)
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_dataItemPath, "p_dataItemPath" );

        final int indexOfFinalDot = p_dataItemPath.lastIndexOf( '.' );
		AssertionUtils.requireConditionTrue((indexOfFinalDot > 0) && (indexOfFinalDot < (p_dataItemPath.length() - 1)), "(indexOfFinalDot > 0) && (indexOfFinalDot < (p_dataItemPath.length() - 1))");

        final String dataItemName = p_dataItemPath.substring( indexOfFinalDot + 1 );

        final XMLNodeMappingWrapper result = XMLNodeMappingWrapper.create( getFormContext() );
        result.setName( "XML Attribute " + dataItemName );
        result.setAttributeName( dataItemName );
        result.setRead( Boolean.TRUE );
        result.setWrite( Boolean.TRUE );
        result.setDataItemNameFromEntityKey( p_dataItemPath );

        return result;
    }

    private void addFavouritesAndSearchElems(
        PhaseWrapper p_phase,
        PresentationPhaseWrapper p_presPhase,
        PropertyWrapper p_autoRefreshIntervalItem,
        PropertyGroupWrapper p_columnSortSpecGroup,
        PropertyGroupWrapper p_searchFiltersStructGroup,
        PropertyGroupWrapper p_searchFiltersGroup,
        XMLNodeRelationshipWrapper p_searchRequestFiltersXmlNodeRelationship,
        PropertyWrapper p_scratchItem,
        PropertyWrapper p_searchOutcomeItem,
        PropertyWrapper p_sortOptionsHideShowStateItem,
        PropertyWrapper p_saveSearchDialogHideShowStateItem,
        PropertyWrapper p_saveSearchModeItem,
        PropertyWrapper p_newSearchNameItem,
        PropertyWrapper p_savedSearchNameToUpdateItem,
        PropertyGroupWrapper p_savedSearchGroup,
        ContainerRuleWrapper p_moreOptionsButtonContainerRule,
        ContainerRuleWrapper p_clearButtonContainerRule,
        ContainerRuleWrapper p_findButtonContainerRule,
        ContainerRuleWrapper p_loadResultsScreenAutoRefreshValueFromSearchParam,
        ContainerRuleWrapper p_saveResultsScreenAutoRefreshValueToSearchParam,
        ContainerRuleWrapper p_addFavouriteButtonContainerRule,
        ContainerRuleWrapper p_saveSearchAsFavouriteContainerRule,
        ContainerRuleWrapper p_reportDuplicateSearchNameContainerRule,
        ContainerRuleWrapper p_loadAndExecSavedSearchContainerRule,
        ContainerRuleWrapper p_deleteSavedSearchContainerRule,
        ContainerRuleWrapper p_preprocessFilterOperandsContainerRule,
        Map<String, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByAppFieldName,
        SortedMap<Integer, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByT24ColumnNumber,
        FormListWrapper p_allFilterFieldsList
	) throws Exception {
        final boolean isCalledForSearchResultsPhase = Processes.EnquiryProcess.SearchResultsPhase.NAME.equals( p_phase.getName() );

        final GenericNodeWrapper<?> parentNodeForSearchElems;
        final PresentationObjectWrapper<?> parentPresNodeForSearchElems;

        // 1 Figure out containership for the elements we're about to add =============================================================================================

        if ( isCalledForSearchResultsPhase )
        {
            // Create and add a "Search Elements" item group (process and presentation) to contain the search-related elements and set parentNodeForSearchElems &
            // parentPresNodeForSearchItems to point to those rather than p_phase & p_presPhase.

            final ItemGroupWrapper searchElemsItemGroup = ItemGroupWrapper.create( getFormContext(), "Search Elements" );
            searchElemsItemGroup.setNotApplicable( Boolean.TRUE );
			searchElemsItemGroup.setConditionExpression("$$" + DataStore.WorkingElementsGroup.ItemPaths.RESULTS_SCREEN_SEARCH_ELEMS_HIDE_SHOW_STATE + "$ == 'SHOW'");
            p_phase.addChild( parentNodeForSearchElems = searchElemsItemGroup );

			final RichHTMLPresentationItemGroupWrapper searchElemsPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), searchElemsItemGroup.unwrap());
			searchElemsPresItemGroup.addChild( RichHTMLPresentationSpacingWrapper.create( getFormContext() ) );
            p_presPhase.addChild( parentPresNodeForSearchElems = searchElemsPresItemGroup );
        }

        else
        {
            addNoResultsFoundHeadingIfApplicable( p_phase, p_presPhase, p_searchOutcomeItem);

            parentNodeForSearchElems = p_phase;
            parentPresNodeForSearchElems = p_presPhase;
        }

        // 2 Favourites (process & presentation) ======================================================================================================================

        if (m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired())
        {
	        addSearchFavouritesElems(
                  parentNodeForSearchElems,
                  parentPresNodeForSearchElems,
                  p_saveSearchDialogHideShowStateItem,
                  p_addFavouriteButtonContainerRule,
                  p_loadAndExecSavedSearchContainerRule,
                  p_deleteSavedSearchContainerRule
			);
        }

        // 3A Search Actions (process) ================================================================================================================================

        // 3A.1 Search Actions item group

        final ItemGroupWrapper searchActionsItemGroup = ItemGroupWrapper.create( getFormContext(), "Search Actions" );
        parentNodeForSearchElems.addChild( searchActionsItemGroup );

        // 3A.1.1 Enquiry description heading

        final HeadingWrapper enquiryDescriptionHeading = HeadingWrapper.create( getFormContext() );
        TextTranslations translations = TextTranslations.getLocalTranslations( getEdgeMapper(), m_enquiry.getDescription(), m_enquiry.getName() );
        m_languageMapHelper.registerT24TextTranslations( enquiryDescriptionHeading, translations );

        enquiryDescriptionHeading.setHeaderText( translations.getText() );
        enquiryDescriptionHeading.setHeaderLevel( HeadingWrapper.EHeaderLevel.HEADER_LEVEL3 );
        searchActionsItemGroup.addChild( enquiryDescriptionHeading );

        final FormButtonWrapper showHideFavouritesButton;

        if (m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired())
        {
	        // 3A.1.2 "[Show / Hide Favourites Toggle]" button & rules

	        showHideFavouritesButton = FormButtonWrapper.create( getFormContext() );
	        showHideFavouritesButton.setActionCommand( "[Show / Hide Favourites Toggle]" );
	        showHideFavouritesButton.setDependencyType( FormButtonWrapper.EDependencyType.NONE );
	        searchActionsItemGroup.addChild( showHideFavouritesButton );
	        
            if (isCalledForSearchResultsPhase)
                m_autoRefreshStateSensitiveResultsScreenElems.add(showHideFavouritesButton);
        }

        else
        {
        	showHideFavouritesButton = null;
        }

        // 3A.1.3 "More Options" button

        final FormButtonWrapper moreOptionsButton = FormButtonWrapper.create( getFormContext() );
        moreOptionsButton.setActionCommand( "More Options" );
        moreOptionsButton.setDependencyType( FormButtonWrapper.EDependencyType.NONE );
        moreOptionsButton.addLink( p_moreOptionsButtonContainerRule );
        searchActionsItemGroup.addChild( moreOptionsButton );
        
        if (isCalledForSearchResultsPhase)
            m_autoRefreshStateSensitiveResultsScreenElems.add(moreOptionsButton);

        // 3A.1.4 "Clear Selection" button

        final FormButtonWrapper clearSearchParamsButton = FormButtonWrapper.create( getFormContext() );
        clearSearchParamsButton.setActionCommand( "Clear Selection" );
        clearSearchParamsButton.setDependencyType( FormButtonWrapper.EDependencyType.NONE );
        clearSearchParamsButton.addLink( p_clearButtonContainerRule.unwrap() );
        searchActionsItemGroup.addChild( clearSearchParamsButton );
        
        if (isCalledForSearchResultsPhase)
            m_autoRefreshStateSensitiveResultsScreenElems.add(clearSearchParamsButton);

        // 3A.1.5 "Find button"

        final FormButtonWrapper invokeSearchButton = FormButtonWrapper.create( getFormContext() );
        invokeSearchButton.setActionCommand( "Find" );
        invokeSearchButton.setDependencyType( FormButtonWrapper.EDependencyType.PREVIOUS_QUESTIONS_IN_GROUP );
        invokeSearchButton.setDisableInput( Boolean.TRUE );
        
        if (m_enquiryAttrsDigest.isResultsScreenToolbarRequired())
        {
            if (isCalledForSearchResultsPhase)
                invokeSearchButton.addLink(p_saveResultsScreenAutoRefreshValueToSearchParam);
            
            else
                invokeSearchButton.addLink(p_loadResultsScreenAutoRefreshValueFromSearchParam);
        }
        
        invokeSearchButton.addLink(p_findButtonContainerRule.unwrap());
        searchActionsItemGroup.addChild(invokeSearchButton);
        
        if (isCalledForSearchResultsPhase)
            m_autoRefreshStateSensitiveResultsScreenElems.add(invokeSearchButton);

        // 3B Search Actions (presentation) ========================================================================================================

        final RichHTMLPresentationColumnBreakWrapper searchActionsPresColumn = RichHTMLPresentationColumnBreakWrapper.create( getFormContext() );
        searchActionsPresColumn.setDesignToUseFromEntityKey( "Enquiry Search Actions and Parameters Column" );
        parentPresNodeForSearchElems.addChild( searchActionsPresColumn );

        final RichHTMLPresentationFormatBreakWrapper searchActionsPresSection = RichHTMLPresentationFormatBreakWrapper.create( getFormContext() );
        searchActionsPresSection.setDesignToUseFromEntityKey( "Enquiry Search Actions Section" );
        searchActionsPresColumn.addChild( searchActionsPresSection );

        // 3B.1 Search Actions presentation item group

		final RichHTMLPresentationItemGroupWrapper searchActionsPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), searchActionsItemGroup.unwrap());
        searchActionsPresSection.addChild( searchActionsPresItemGroup );

        final RichHTMLPresentationFormatBreakWrapper enquiryToolBar = RichHTMLPresentationFormatBreakWrapper.create( getFormContext() );
        searchActionsPresSection.setDesignToUseFromEntityKey( "EnquiryToolbar" );
        searchActionsPresItemGroup.addChild( enquiryToolBar );

        
        //final RichHTMLPresentationSpacingWrapper horizontalLinePresSpacing1 = RichHTMLPresentationSpacingWrapper.create( getFormContext() );
        //horizontalLinePresSpacing1.setSpacingType( RichHTMLPresentationSpacingWrapper.ESpacingType.LINE );
        //searchActionsPresItemGroup.addChild( horizontalLinePresSpacing1 );

        final RichHTMLPresentationColumnBreakWrapper enquiryDescriptionPresColumn = RichHTMLPresentationColumnBreakWrapper.create( getFormContext() );
        enquiryDescriptionPresColumn.setDesignToUseFromEntityKey( "Enquiry Description Column" );
        enquiryToolBar.addChild( enquiryDescriptionPresColumn );

        // 3B.1.1 Enquiry description presentation heading

		final RichHTMLPresentationQuestionWrapper enquiryDescriptionPresHeading = RichHTMLPresentationQuestionWrapper.create(getFormContext(), enquiryDescriptionHeading.unwrap());
		setQuestionDesign(enquiryDescriptionPresHeading);
		enquiryDescriptionPresHeading.setDesignToUseFromEntityKey( "Enquiry Description Heading" );
        enquiryDescriptionPresColumn.addChild( enquiryDescriptionPresHeading );

        
        final RichHTMLPresentationColumnBreakWrapper enquiryOptionsPresColumn = RichHTMLPresentationColumnBreakWrapper.create( getFormContext() );
        enquiryOptionsPresColumn.setDesignToUseFromEntityKey( "Enquiry Options And Clear Buttons Column" );
        enquiryToolBar.addChild( enquiryOptionsPresColumn );

        // 3B.1.3 "More Options" presentation button

		final RichHTMLPresentationButtonWrapper moreOptionsPresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), moreOptionsButton.unwrap());
		moreOptionsPresButton.setDisplayInAnswerColumn( Boolean.FALSE );
        moreOptionsPresButton.setDesignToUseFromEntityKey( "Enquiry Options Column Button" );
        moreOptionsPresButton.setHintText( "$%SLANG Enquiry.MoreOptionsHint More Options$" );
        
        String showSlang = "$%SLANG Enquiry.ShowText Show$";
        String hideSlang = "$%SLANG Enquiry.HideText Hide$";
        
        moreOptionsPresButton.setLinkText("$%if " + p_sortOptionsHideShowStateItem.getPropertyKey(getSessionDictionary()) + " != '" + Lists.ShowOrHide.Keys.SHOW + "'$" + showSlang + "$%else$" + hideSlang + "$%endif$ $%SLANG Enquiry.OptionsText Options$");
        enquiryOptionsPresColumn.addChild( moreOptionsPresButton );

        if (m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired())
        {
	        // 3B.1.2 "[Show / Hide Favourites Toggle]" presentation button

			final RichHTMLPresentationButtonWrapper showHideFavouritesPresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), showHideFavouritesButton.unwrap());
			showHideFavouritesPresButton.setGroupWithPrevious( Boolean.TRUE );
	        showHideFavouritesPresButton.setDesignToUseFromEntityKey( "Enquiry Toggle Favourites Button" );
	        showHideFavouritesPresButton.setHintText( "$%SLANG Enquiry.ShowHideFavouritesHint [Show / Hide Favourites Toggle]$");
			enquiryOptionsPresColumn.addChild( showHideFavouritesPresButton );
        }

        // 3B.1.4 "Clear Selection" presentation button

		final RichHTMLPresentationButtonWrapper clearSearchParamsPresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), clearSearchParamsButton.unwrap());
		clearSearchParamsPresButton.setGroupWithPrevious( Boolean.TRUE );
        clearSearchParamsPresButton.setDesignToUseFromEntityKey( "Enquiry Options Clear Button" );
        clearSearchParamsPresButton.setHintText( "$%SLANG Enquiry.ClearSelectionHint Clear Selection$");
        enquiryOptionsPresColumn.addChild( clearSearchParamsPresButton );

        // 3B.1.5 "Find button"

        final RichHTMLPresentationButtonWrapper invokeSearchPresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), invokeSearchButton.unwrap());
        invokeSearchPresButton.setGroupWithPrevious( Boolean.TRUE );
        invokeSearchPresButton.setDesignToUseFromEntityKey( "Enquiry Find Button" );
        invokeSearchPresButton.setButtonText( "$%SLANG Enquiry.FindButtonLabel Find$" );
        invokeSearchPresButton.setHintText( "$%SLANG Enquiry.FindButtonHint Executes the query$" );
        enquiryOptionsPresColumn.addChild( invokeSearchPresButton );

        //final RichHTMLPresentationColumnBreakWrapper findButtonPresColumn = RichHTMLPresentationColumnBreakWrapper.create( getFormContext() );
        //findButtonPresColumn.setDesignToUseFromEntityKey( "Enquiry Find Button Column" );
        //enquiryToolBar.addChild( findButtonPresColumn );

        //final RichHTMLPresentationSpacingWrapper horizontalLinePresSpacing2 = RichHTMLPresentationSpacingWrapper.create( getFormContext() );
        //horizontalLinePresSpacing2.setSpacingType( RichHTMLPresentationSpacingWrapper.ESpacingType.LINE );
        //searchActionsPresItemGroup.addChild( horizontalLinePresSpacing2 );

        // 4A Sort Options item group

        final ItemGroupWrapper sortOptionsItemGroup = ItemGroupWrapper.create( getFormContext(), "Sort Options" );
        parentNodeForSearchElems.addChild( sortOptionsItemGroup );

        // 4B Sort Options presentation item group

        final RichHTMLPresentationFormatBreakWrapper sortOptionsPresSection = RichHTMLPresentationFormatBreakWrapper.create( getFormContext() );
        //sortOptionsPresSection.setHideField( Boolean.TRUE );
		//sortOptionsPresSection.setConditionExpression("$$" + p_sortOptionsHideShowStateItem.getPropertyKey(getSessionDictionary()) + "$ == 'SHOW'");
        sortOptionsPresSection.setDesignToUseFromEntityKey( "Enquiry Sort Options Section" );
        searchActionsPresSection.addChild( sortOptionsPresSection );
        
        final RichHTMLPresentationItemGroupWrapper sortOptionsPresItemGroup =  RichHTMLPresentationItemGroupWrapper.create(getFormContext(), sortOptionsItemGroup.unwrap());

        if (isCalledForSearchResultsPhase)
            sortOptionsPresSection.addChild(sortOptionsPresItemGroup);
        
        else
        {
            final RichHTMLPresentationColumnBreakWrapper sortOptionsPresColumn = RichHTMLPresentationColumnBreakWrapper.create( getFormContext() );
            sortOptionsPresSection.addChild(sortOptionsPresColumn);
            sortOptionsPresColumn.addChild(sortOptionsPresItemGroup);
        }

        // 5 Sort Options item group contents (process and presentation)

        String baseColumnSortByGroupPath = p_columnSortSpecGroup.getPropertyGroupKey();
        baseColumnSortByGroupPath = baseColumnSortByGroupPath.substring( 0, baseColumnSortByGroupPath.length() - 2 );

        for (int i = 1; i <= 3; ++i)
        {
			final String
				columnSortByGroupPath = baseColumnSortByGroupPath + i + "]",
				fieldNameItemPath = columnSortByGroupPath + '.' + DataStore.EnquiryGroup.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.FIELD_NAME_ITEM_NAME,
				sortOrderItemPath = columnSortByGroupPath + '.' + DataStore.EnquiryGroup.SearchRequestGroup.OrderByGroup.ColumnSortSpecGroup.SORT_ORDER_ITEM_NAME;

            // 5A Sort option process item group & questions

            final ItemGroupWrapper columnSortSpecItemGroup = ItemGroupWrapper.create( getFormContext(), "Column Sort " + i );
            sortOptionsItemGroup.addChild( columnSortSpecItemGroup );

            final QuestionWrapper sortFieldNameQuestion = QuestionWrapper.create( getFormContext() );
            sortFieldNameQuestion.setQuestionText( ( i == 1 ) ? "$%SLANG Enquiry.SortByLabel Sort By:$" : "$%SLANG Enquiry.ThenByLabel Then By:$" );
            sortFieldNameQuestion.setMandatory( Boolean.FALSE );
            sortFieldNameQuestion.setPropertyKeyFromEntityKey( fieldNameItemPath );
            columnSortSpecItemGroup.addChild( sortFieldNameQuestion );
            
            if (isCalledForSearchResultsPhase)
                m_autoRefreshStateSensitiveResultsScreenElems.add(sortFieldNameQuestion);

            final QuestionWrapper sortOrderQuestion = QuestionWrapper.create( getFormContext() );
            sortOrderQuestion.setQuestionText( "$%SLANG Enquiry.SortOrderLabel [Sort Order]$" );
            sortOrderQuestion.setMandatory( Boolean.FALSE );
            sortOrderQuestion.setPropertyKeyFromEntityKey( sortOrderItemPath );
            columnSortSpecItemGroup.addChild( sortOrderQuestion );

            // 5B Sort option presentation item group & questions

			final RichHTMLPresentationItemGroupWrapper columnSortSpecPresItemGroup =  RichHTMLPresentationItemGroupWrapper.create(getFormContext(), columnSortSpecItemGroup.unwrap());
            sortOptionsPresItemGroup.addChild( columnSortSpecPresItemGroup );

			final RichHTMLPresentationQuestionWrapper sortFieldNamePresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), sortFieldNameQuestion.unwrap());
			sortFieldNamePresQuestion.setDisplayType( "Drop down list" );
            sortFieldNamePresQuestion.setDesignToUseForDisplayTypeFromEntityKey( "Enquiry Sort By Field Answer" );
            columnSortSpecPresItemGroup.addChild( sortFieldNamePresQuestion );


			final RichHTMLPresentationQuestionWrapper sortOrderPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), sortOrderQuestion.unwrap());
            sortOrderPresQuestion.setHideQuestion( Boolean.TRUE );
            sortOrderPresQuestion.setQuestionNewLine( Boolean.FALSE );
            sortOrderPresQuestion.setDisplayType( "Radio Button" );
            sortOrderPresQuestion.setDefaultListValue( "Ascending" );
            sortOrderPresQuestion.setReadOnlyFormat( RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER );
            columnSortSpecPresItemGroup.addChild( sortOrderPresQuestion );
        }

        if (! isCalledForSearchResultsPhase)
        {
            // 6A Auto-refresh interval item group and question
            
            final ItemGroupWrapper autoRefreshItemGroup = ItemGroupWrapper.create( getFormContext(), "Auto-refresh Option" );
            parentNodeForSearchElems.addChild( autoRefreshItemGroup );
            
            final QuestionWrapper autoRefreshIntervalQuestion = QuestionWrapper.create(getFormContext());
            autoRefreshIntervalQuestion.setQuestionText("$%SLANG Enquiry.AutoRefreshIntervalSecondsLabel Auto refresh interval (seconds)$");
            autoRefreshIntervalQuestion.setMandatory(false);
            autoRefreshIntervalQuestion.setPropertyKey(p_autoRefreshIntervalItem);
            autoRefreshItemGroup.addChild(autoRefreshIntervalQuestion);
    
            // 6B Auto-refresh interval presentation column, item group and question
            
            final RichHTMLPresentationColumnBreakWrapper autoRefreshIntervalColumn = RichHTMLPresentationColumnBreakWrapper.create( getFormContext() );
            sortOptionsPresSection.addChild(autoRefreshIntervalColumn);
            
            final RichHTMLPresentationItemGroupWrapper autoRefreshPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), autoRefreshItemGroup.unwrap());
            autoRefreshIntervalColumn.addChild(autoRefreshPresItemGroup);
            
            final RichHTMLPresentationQuestionWrapper autoRefreshIntervalPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), autoRefreshIntervalQuestion.unwrap());
            autoRefreshPresItemGroup.addChild(autoRefreshIntervalPresQuestion);
        }

        final RichHTMLPresentationSpacingWrapper horizontalLinePresSpacing3 = RichHTMLPresentationSpacingWrapper.create( getFormContext() );
        horizontalLinePresSpacing3.setSpacingType( RichHTMLPresentationSpacingWrapper.ESpacingType.BLANK_LINE );
        horizontalLinePresSpacing3.setSpacingStyle( "EnquirySearchScreenWidth" );

        sortOptionsPresSection.addChild( horizontalLinePresSpacing3 );

        // 7A Search Parameters item group

        final ItemGroupWrapper searchParamsItemGroup = ItemGroupWrapper.create( getFormContext(), "Search Parameters" );
        parentNodeForSearchElems.addChild( searchParamsItemGroup );

        // 7B Search Parameters presentation item group

        final RichHTMLPresentationFormatBreakWrapper searchParamsPresSection = RichHTMLPresentationFormatBreakWrapper.create( getFormContext() );
        searchParamsPresSection.setDesignToUseFromEntityKey( "Enquiry Search Params Section" );
        searchParamsPresSection.setDisplayType( "Standard section" );
        searchParamsPresSection.setDefaultButton( invokeSearchButton.unwrap().getActionCommand() );
        searchParamsPresSection.setDefaultButtonName( invokeSearchButton.getEntityKeyName() );
        searchActionsPresColumn.addChild( searchParamsPresSection );

		final RichHTMLPresentationItemGroupWrapper searchParamsPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), searchParamsItemGroup.unwrap());
        searchParamsPresSection.addChild( searchParamsPresItemGroup );

        // 8 Per filter param item groups (process and presentation)

        final SelectionExpression selectionExpn = (m_enquiry.getCustomSelection() == null) ? buildSelectionExpressionForAllResultFields(p_enquirySelectionFieldInfoByT24ColumnNumber) : m_enquiry.getCustomSelection();
        final EList<EObject> searchParamDefs = ( selectionExpn == null ) ? null : selectionExpn.eContents();
        final int numSearchParamDefs = ( searchParamDefs == null ) ? 0 : searchParamDefs.size();
		final StringBuilder resultScreenHideShowStateItemPathList = isCalledForSearchResultsPhase ? new StringBuilder(numSearchParamDefs * 85) : null;

		final HashSet<String> processedOdataFieldName = new HashSet<String>();

        for (int i = 0; i < numSearchParamDefs; ++i)
        {
            final EObject searchParamDef = searchParamDefs.get( i );

			if (! (searchParamDef instanceof SelectionImpl)) {
                LOGGER.debug( "- searchParamDefs[" + i + "]: " + searchParamDef + " (ignoring)" );
                continue;
            }

            final SelectionImpl selectionImpl = (SelectionImpl) searchParamDef;
            final Boolean isParamValueMandatory = Boolean.TRUE.equals( selectionImpl.getMandatory() );
            final String appFieldName = selectionImpl.getField();
            final EnquirySelectionFieldInfo enqSelectionFieldInfo = p_enquirySelectionFieldInfoByAppFieldName.get( appFieldName );

			final String enqResultFieldName = ( enqSelectionFieldInfo == null ) ? null : enqSelectionFieldInfo.appFieldName;
			//final String enqResultFieldName = ( enqSelectionFieldInfo == null ) ? null : enqSelectionFieldInfo.enquiryFieldName;
            //changed to fix customer info-- enquiryFieldName changed to appFieldName
			final String odataFieldName =  MapperUtility.processT24NameToIRISName((enqResultFieldName == null) ? appFieldName : enqResultFieldName);

			if (processedOdataFieldName.contains(odataFieldName))
				continue;

			processedOdataFieldName.add(odataFieldName);

			final EList<SelectionOperator> paramSpecificComparisonOperatorList = selectionImpl.getOperands();

            // Add the item group (process & presentation) that will contain the set of questions for the current search parameter

            final ItemGroupWrapper questionItemGroup = ItemGroupWrapper.create( getFormContext() );
            questionItemGroup.setGroupName( odataFieldName );
            searchParamsItemGroup.addChild( questionItemGroup );

            final String searchParamGroupName = odataFieldName;
            final String searchParamGroupPath = p_searchFiltersGroup.getPropertyGroupKey() + '.' + searchParamGroupName + "[1]";


            if ( isCalledForSearchResultsPhase )
            {
                // 3. At the "Search Results" phase, we only want to show filter item groups relating to fields for which the user has either:
                // a) specified a filter value, OR...
                // b) specified as an "order by" column (TODO)
                questionItemGroup.setNotApplicable( Boolean.TRUE );
				questionItemGroup.setConditionExpression("$$" + searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.RESULTS_SCREEN_HIDE_SHOW_STATE + "$ == 'SHOW'" );
            }

            else // by implication, we've been invoked for the "Search" phase
            {
                /*
                 * Below we create all the bits that we MUST have to support the "Search" screen and which the "Search Results" screen will ALSO require unless it's marked
                 * as a "no selection" enquiry (in which case we wouldn't get called for the "Search Results" phase - which is why, in that scenario, we need to grab the
                 * opportunity to do this stuff NOW :-)
                 *
                 * 26/10/2014 STH
                 */

                // 1. Add child group & items for the current search parameter to the search filters data group

                final PropertyGroupWrapper searchParamGroup = PropertyGroupWrapper.create( getFormContext(), searchParamGroupName );
                p_searchFiltersStructGroup.addChild( searchParamGroup );
                searchParamGroup.addChild( makeSearchParamCompareOpItem( odataFieldName, paramSpecificComparisonOperatorList ) );

                // Please note that we ignore Date types as we handle dates with Widgets.  Text is needed to store "!TODAY" etc.

                final PropertyWrapper searchParamOperand1Item = PropertyWrapper.create(getFormContext(), DataStore.SearchParamFilterGroupTemplate.OPERAND1_ITEM_NAME);
                searchParamOperand1Item.setType( EdgeConnectDataType.TEXT.getDataTypeName());
                searchParamGroup.addChild( searchParamOperand1Item );

                final PropertyWrapper searchParamOperand2Item = PropertyWrapper.create(getFormContext(), DataStore.SearchParamFilterGroupTemplate.OPERAND2_ITEM_NAME);
                searchParamOperand2Item.setType( EdgeConnectDataType.TEXT.getDataTypeName() );
                searchParamGroup.addChild( searchParamOperand2Item );

                final PropertyWrapper searchParamResultScreenHideShowStateItem = PropertyWrapper.create(getFormContext(), DataStore.SearchParamFilterGroupTemplate.RESULTS_SCREEN_HIDE_SHOW_STATE);
                searchParamResultScreenHideShowStateItem.setType( Lists.ShowOrHide.FULLNAME + " List" );
                searchParamGroup.addChild( searchParamResultScreenHideShowStateItem );

                // 2. Add rule block for this parameter to p_preprocessFilterOperandsContainerRule

                addPerSearchParamFilterOperandsPreprocessingRules(p_preprocessFilterOperandsContainerRule, p_searchFiltersGroup, searchParamGroupName, p_scratchItem, resultScreenHideShowStateItemPathList);

                // 3. If search favourites support is required, then add child mappings and relationships for this parameter to p_searchRequestFiltersXmlNodeRelationship

                if ( m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired() && (p_searchRequestFiltersXmlNodeRelationship != null) )
                    addSearchRequestFilterToFavouritesXMLInterface( p_searchRequestFiltersXmlNodeRelationship, searchParamGroup );
            }

            if ( p_allFilterFieldsList != null )
            {
                ListItemWrapper filterFieldListItem = ListItemWrapper.create( getFormContext(), odataFieldName, appFieldName, null );
                p_allFilterFieldsList.addChild( filterFieldListItem );
            }

			final RichHTMLPresentationItemGroupWrapper questionPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), questionItemGroup.unwrap());
            searchParamsPresItemGroup.addChild( questionPresItemGroup );

			final String comparisonOperatorItemPath = searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME;

            final String[] operandItemPaths = new String[] {
                searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.OPERAND1_ITEM_NAME,
                searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.OPERAND2_ITEM_NAME
            };

            // Add comparison operator question (process & presentation)

            final QuestionWrapper paramCompareOpQuestion = QuestionWrapper.create( getFormContext() );
            paramCompareOpQuestion.setPropertyKeyFromEntityKey( comparisonOperatorItemPath );
            paramCompareOpQuestion.setMandatory( isParamValueMandatory );
            
            TextTranslations questionTranslations = TextTranslations.getLabelTranslations( getEdgeMapper(), (enqSelectionFieldInfo != null) ? enqSelectionFieldInfo.appFieldDef : null, selectionImpl.getLabel(), appFieldName );
            m_languageMapHelper.registerT24TextTranslations( paramCompareOpQuestion, questionTranslations );

            paramCompareOpQuestion.setQuestionText( questionTranslations.getText() );
            questionItemGroup.addChild( paramCompareOpQuestion );
            
            if (isCalledForSearchResultsPhase)
                m_autoRefreshStateSensitiveResultsScreenElems.add(paramCompareOpQuestion);

	        final RichHTMLPresentationQuestionWrapper paramCompareOpPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), paramCompareOpQuestion.unwrap());
            paramCompareOpPresQuestion.setDesignToUseFromEntityKey( "Enquiry Search Comparison  Operator Question" );
            paramCompareOpPresQuestion.setDisplayType( "Drop down list" );
            paramCompareOpPresQuestion.setListPrompt( RichHTMLPresentationQuestionWrapper.EListPrompt.NO_PROMPT );
            paramCompareOpPresQuestion.setDefaultListValue( "equals" );
            paramCompareOpPresQuestion.setReadOnlyFormat( RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER );
            questionPresItemGroup.addChild( paramCompareOpPresQuestion );

            // Add the [Single Operand] question (process & presentation)

            final QuestionWrapper singleOperandQuestion = QuestionWrapper.create( getFormContext() );
            singleOperandQuestion.setQuestionText( "$%SLANG Enquiry.SingleOperandLabel [Single Operand]$" );
            singleOperandQuestion.setNotApplicable( Boolean.TRUE );
            singleOperandQuestion.setConditionExpression( "$$" + comparisonOperatorItemPath + ".groupValue()$ != 'RANGE'" );
            singleOperandQuestion.setMandatory( Boolean.FALSE );
            singleOperandQuestion.setPropertyKeyFromEntityKey( operandItemPaths[0] );
            questionItemGroup.addChild( singleOperandQuestion );
            
            if (isCalledForSearchResultsPhase)
                m_autoRefreshStateSensitiveResultsScreenElems.add(singleOperandQuestion);

            addSearchFilterValuePresQuestion( singleOperandQuestion, enqSelectionFieldInfo, questionPresItemGroup );

            // Add the [Range Bound #] questions (process and presentation)

            for (int j = 0; j < operandItemPaths.length; ++j)
            {
                final QuestionWrapper rangeBoundQuestion = QuestionWrapper.create( getFormContext() );
                rangeBoundQuestion.setQuestionText( "[$%SLANG Enquiry.RangeBoundLabel Range Bound$ #" + ( j + 1 ) + "]" );
                rangeBoundQuestion.setNotApplicable( Boolean.TRUE );
                rangeBoundQuestion.setConditionExpression( "$$" + comparisonOperatorItemPath + ".groupValue()$ == 'RANGE'" );
                rangeBoundQuestion.setMandatory( Boolean.FALSE );
                rangeBoundQuestion.setPropertyKeyFromEntityKey( operandItemPaths[j] );
                questionItemGroup.addChild( rangeBoundQuestion );
                
                if (isCalledForSearchResultsPhase)
                    m_autoRefreshStateSensitiveResultsScreenElems.add(rangeBoundQuestion);
                
                addSearchFilterValuePresQuestion( rangeBoundQuestion, enqSelectionFieldInfo, questionPresItemGroup );
            }
        }

        if (m_enquiryAttrsDigest.isSearchFavouritesFunctionalityRequired())
        {
	        addItemGroupForSaveSearchDialog(
                p_phase.getName(),
                parentNodeForSearchElems,
                parentPresNodeForSearchElems,
                p_saveSearchDialogHideShowStateItem,
                p_saveSearchModeItem,
                p_newSearchNameItem,
                p_savedSearchNameToUpdateItem,
                p_savedSearchGroup,
                p_saveSearchAsFavouriteContainerRule,
                p_reportDuplicateSearchNameContainerRule
			);
        }

        if ( isCalledForSearchResultsPhase )
        {
			final PropertyGroupWrapper searchRequestGroup = PropertyGroupWrapper.wrap((PropertyGroup) p_searchFiltersGroup.unwrap().getParent());
            final ResetDataRuleWrapper resetSearchRequestGroupRule = ResetDataRuleWrapper.create( getFormContext() );
            resetSearchRequestGroupRule.setName( "Reset " + searchRequestGroup.getName() + " group" );
            resetSearchRequestGroupRule.setResetPropertyGroup( searchRequestGroup );
            resetSearchRequestGroupRule.setExcludePropertyListFromEntityKey( resultScreenHideShowStateItemPathList.toString() );
            p_clearButtonContainerRule.addTrueRule( resetSearchRequestGroupRule.unwrap() );
        }
    }

	private PropertyWrapper makeSearchParamCompareOpItem(String p_odataFieldName, EList<SelectionOperator> p_paramSpecificSelectionOperatorList)
    {
		final PropertyWrapper searchParamCompareOpItem = PropertyWrapper.create(getFormContext(), DataStore.SearchParamFilterGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME);

        final FormListWrapper listDef = ( ( p_paramSpecificSelectionOperatorList == null ) || p_paramSpecificSelectionOperatorList.isEmpty() ) ?
			findOrCreateAllSearchParamComparisonOperatorsList() : addSearchParamSpecificCompareOpList(p_odataFieldName, p_paramSpecificSelectionOperatorList);

        searchParamCompareOpItem.setType( listDef.getFullName() );

        return searchParamCompareOpItem;
    }

	private FormListWrapper addSearchParamSpecificCompareOpList(String p_odataFieldName, EList<SelectionOperator> p_paramSpecificComparisonOperatorList)
    {
        final FormListWrapper result = FormListWrapper.create( getFormContext(), p_odataFieldName + "ComparisonOperators" );
		final SearchParamComparisonOp[] searchParamComparisonOps = SearchParamComparisonOp.translateToUniqueInstanceList(p_paramSpecificComparisonOperatorList, LOGGER);
        final int numSearchParamComparisonOps = ( searchParamComparisonOps == null ) ? 0 : searchParamComparisonOps.length;

        for (int i = 0; i < numSearchParamComparisonOps; ++i)
        {
            final SearchParamComparisonOp searchParamComparisonOp = searchParamComparisonOps[i];
            final EdgeODataComparisonOpDef edgeODataComparisonOpDef = searchParamComparisonOp.getEdgeODataComparisonOpDef();

            if ( edgeODataComparisonOpDef == null )
                continue;

            final EdgeListEntryDef edgeListEntryDef = edgeODataComparisonOpDef.getEdgeListEntryDef();

            result.addChild( ListItemWrapper.create( getFormContext(), edgeListEntryDef.getKey(), edgeListEntryDef.getValue(), null ) );
        }

        getProject().getListsRoot().addChild( result.unwrap() );

        return result;
    }

	private FormListWrapper findOrCreateAllSearchParamComparisonOperatorsList() {
        if ( m_allSearchParamComparisonOperatorsList == null )
        {
			m_allSearchParamComparisonOperatorsList = FormListWrapper.create(getFormContext(), Lists.AllSearchComparisonOperators.FULLNAME);
            final int numEntries = EdgeODataComparisonOpDef.NUM_VALUES;

            for (int i = 0; i < numEntries; ++i)
            {
                final EdgeListEntryDef edgeListEntryDef = EdgeODataComparisonOpDef.VALUES[i].getEdgeListEntryDef();
				m_allSearchParamComparisonOperatorsList.addChild(ListItemWrapper.create(getFormContext(), edgeListEntryDef.getKey(), edgeListEntryDef.getValue(), edgeListEntryDef.getGroupValue()));
            }

            getProject().getListsRoot().addChild( m_allSearchParamComparisonOperatorsList.unwrap() );
        }

        return m_allSearchParamComparisonOperatorsList;
    }

	private void addSearchFilterValuePresQuestion(QuestionWrapper p_filterValueQuestion, EnquirySelectionFieldInfo p_fieldInfo, RichHTMLPresentationItemGroupWrapper p_questionPresItemGroup)
    {
        final RichHTMLPresentationQuestionWrapper filterValuePresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), p_filterValueQuestion.unwrap());
        filterValuePresQuestion.setHideQuestion( Boolean.TRUE );
        filterValuePresQuestion.setQuestionNewLine( Boolean.FALSE );
        filterValuePresQuestion.setFieldSize( IntegerFactory.getInteger( 20 ) );
        filterValuePresQuestion.setDesignToUseFromEntityKey( "Standard Question" );
        filterValuePresQuestion.setReadOnlyFormat( RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.DISABLED_ANSWER );

		final SearchParamDropdownInfo p_searchParamDropdownInfo = (p_fieldInfo == null) ? null : p_fieldInfo.searchParamDropdownInfo;

		/*
		 * TODO: Need some mechanism for toggling the widgets in and out of read-only mode for Auto Refresh ENABLED scenario at Search Results screen.
		 */
        if ( p_searchParamDropdownInfo == null )
        {
            if  ( ( p_fieldInfo != null ) && ( p_fieldInfo.appFieldDef != null) )
            {
                final EdgeConnectDataType edgeConnectDataType = p_fieldInfo.edgeConnectDataType;
                
                final String
                    typeModifier = RichHelper.getTypeModifiers(p_fieldInfo.appFieldDef),
                    businessType = RichHelper.getBusinessType(p_fieldInfo.appFieldDef),
                    enquiryFieldName = p_fieldInfo.enquiryFieldName;
                
                final String irisNameForField = MapperUtility.processT24NameToIRISName((enquiryFieldName == null) ? p_fieldInfo.appFieldName : enquiryFieldName);
                
               // final String irisNameForField = p_fieldInfo.enquiryFieldName;
                
                // The following two properties are not applicable for enquiries.
                final boolean isEnrichmentOnly = false;
                
                final boolean hasRecurrencePopupBehavior = false;
                
                //Widgets
                if ( edgeConnectDataType == EdgeConnectDataType.DATE )
                {
                    /*
                    //Setting the date format as yyyy/mm/dd and removing the default icon
                    filterValuePresQuestion.setDateFormatType( RichHTMLPresentationQuestionWrapper.EDateFormatType.SPECIFY_DATE_FORMAT );
                    filterValuePresQuestion.setDateFormat( " True, False, /, Day, Text, , , /, Month, Text, , , /, Year, Text, , , False, False, :, Hours, Text, , , :, Minutes, Text, , , :, Seconds, Text, , , Specify, dd, Specify, mm, Specify, yyyy, Specify, hh, Specify, mm, Specify, ss, True, T, False" );
                    */                
                    
                    RichHelper.setCalendarWidgetV2( filterValuePresQuestion,irisNameForField, false,filterValuePresQuestion.unwrap().getName());
                }
                
                else if (edgeConnectDataType == EdgeConnectDataType.RELATIVEDATE)
                {
                    
                    filterValuePresQuestion.setAttribute( "IsEnquiry","YES" );                
                    RichHelper.setRelativeCalendarWidget( filterValuePresQuestion,typeModifier, irisNameForField, isEnrichmentOnly, filterValuePresQuestion.unwrap().getName());
                                    
                }
                else if (edgeConnectDataType == EdgeConnectDataType.FREQUENCY)
                {
                    filterValuePresQuestion.setAttribute( "IsEnquiry","YES" );
                    RichHelper.setFrequencyControlWidget( filterValuePresQuestion, businessType, typeModifier, irisNameForField , isEnrichmentOnly, hasRecurrencePopupBehavior, filterValuePresQuestion.unwrap().getName());
                     
                }
                else
                {
                    filterValuePresQuestion.setDisplayType( "Text Input Field" );
                }
            }
            else
            {
                filterValuePresQuestion.setDisplayType( "Text Input Field" );
            }
        }

        else
        {
			filterValuePresQuestion.setAttribute("DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Dropdown v2");
			filterValuePresQuestion.setAttribute( "DisplayTypeResourceURL", "enqlist"+p_searchParamDropdownInfo.irisResourceName +"()");
           // filterValuePresQuestion.setAttribute( "DisplayTypePrimaryKey", p_searchParamDropdownInfo.irisPrimaryKeyName );
			filterValuePresQuestion.setAttribute( "DisplayTypePrimaryKey", SEARCH_PRIMARY_KEY );
        }

        p_questionPresItemGroup.addChild( filterValuePresQuestion );
    }

    private void addNoResultsFoundHeadingIfApplicable(PhaseWrapper p_parentPhase,  PresentationObjectWrapper<?> p_parentPresNode, PropertyWrapper p_searchOutcomeItem) {
        final String noResultsFoundMsgText = m_enquiryFieldsDigest.getApplicableNoResultsFoundMessageOrNull();

        if (noResultsFoundMsgText == null)
            return;

        final StringBuilder applicabilityConditionBuff = new StringBuilder();

        applicabilityConditionBuff
            .append( "$$" )
            .append( p_searchOutcomeItem.getEntityKeyName() )
            .append( "$ == '" )
            .append( Lists.SearchOutcome.Keys.NO_RESULTS_FOUND )
            .append( "'" );

        final ItemGroupWrapper noResultsMsgItemGroup = ItemGroupWrapper.create(getFormContext(), "No Results Found Elements");
        noResultsMsgItemGroup.setNotApplicable(Boolean.TRUE);
        noResultsMsgItemGroup.setConditionExpression(applicabilityConditionBuff.toString());
        p_parentPhase.addChild(noResultsMsgItemGroup);

        final HeadingWrapper noResultsMsgHeading = HeadingWrapper.create(getFormContext());
        noResultsMsgHeading.setHeaderText(noResultsFoundMsgText);
        noResultsMsgHeading.setHeaderLevel( HeadingWrapper.EHeaderLevel.HEADER_LEVEL2 );
        noResultsMsgItemGroup.addChild(noResultsMsgHeading);


        final RichHTMLPresentationItemGroupWrapper noResultsMsgPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), noResultsMsgItemGroup.unwrap());
        p_parentPresNode.addChild(noResultsMsgPresItemGroup);

        final RichHTMLPresentationFormatBreakWrapper searchResultsSection = RichHTMLPresentationFormatBreakWrapper.create( getFormContext() );
        searchResultsSection.setDesignToUseFromEntityKey( "Enquiry Search Results Section" );
        noResultsMsgPresItemGroup.addChild(searchResultsSection);
        
        final RichHTMLPresentationQuestionWrapper noResultsMsgPresHeading = RichHTMLPresentationQuestionWrapper.create(getFormContext(), noResultsMsgHeading.unwrap());
        noResultsMsgPresHeading.setDesignToUseFromEntityKey( "Custom" );
        FieldDisplayType.CUSTOM_NO_RESULTS_MESSAGE.applyTo(noResultsMsgPresHeading);
        searchResultsSection.addChild(RichHTMLPresentationSpacingWrapper.create(getFormContext()));
        searchResultsSection.addChild(noResultsMsgPresHeading);

        if (Processes.EnquiryProcess.SearchPhase.NAME.equals(p_parentPhase.getName()))
        	searchResultsSection.addChild(RichHTMLPresentationSpacingWrapper.create(getFormContext()));
        
        return;
    }

    private void addSearchFavouritesElems(
        GenericNodeWrapper<?> p_parentNodeForSearchElems,
        PresentationObjectWrapper<?> p_parentPresNodeForSearchElems,
        PropertyWrapper p_saveSearchDialogHideShowStateItem,
        ContainerRuleWrapper p_addFavouriteButtonContainerRule,
        ContainerRuleWrapper p_loadAndExecSavedSearchContainerRule,
        ContainerRuleWrapper p_deleteSavedSearchContainerRule
	) {
        // 1 Process elements

        // 1.1 "Favourites" item group
        final ItemGroupWrapper favouritesItemGroup = ItemGroupWrapper.create( getFormContext(), "Favourites" );
        p_parentNodeForSearchElems.addChild( favouritesItemGroup );

        // 1.2 "Favourites" heading
        final HeadingWrapper favouritesHeading = HeadingWrapper.create( getFormContext() );
        favouritesHeading.setHeaderText( "$%SLANG Enquiry.FavouritesHeading Favourites$" );
        favouritesHeading.setHeaderLevel( HeadingWrapper.EHeaderLevel.HEADER_LEVEL2 );
        favouritesItemGroup.addChild( favouritesHeading );

        // 1.3 Add Favourite (+) button & child rules
        final FormButtonWrapper addCurrentSelectionAsFavouriteButton = FormButtonWrapper.create( getFormContext() );
        addCurrentSelectionAsFavouriteButton.setActionCommand( "Add" );
        addCurrentSelectionAsFavouriteButton.setDependencyType( FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE );
        favouritesItemGroup.addChild( addCurrentSelectionAsFavouriteButton );

		addCurrentSelectionAsFavouriteButton.unwrap().addChild(p_addFavouriteButtonContainerRule.unwrap(), true /* p_linkedObject */);

        // 1.4 "Saved Searches" table
        final FormTableWrapper savedSearchesTable = FormTableWrapper.create( getFormContext() );
        savedSearchesTable.setTableTitle( "Saved Searches" );
        savedSearchesTable.setSelectorFromEntityKey( DataStore.WorkingElementsGroup.ItemPaths.SELECTED_SAVED_SEARCH_INSTANCE );
        savedSearchesTable.setSelectorMand( Boolean.FALSE );
        favouritesItemGroup.addChild( savedSearchesTable );

        // 1.4.1 Hidden "Search Name" table question
        final QuestionWrapper searchNameTableQuestion = QuestionWrapper.create( getFormContext() );
        searchNameTableQuestion.setQuestionText( "$%SLANG Enquiry.SearchNameLabel [Search Name]$" );
        searchNameTableQuestion.setNotApplicable( Boolean.TRUE );
        searchNameTableQuestion.setMandatory( Boolean.FALSE );
        searchNameTableQuestion.setReadOnly( Boolean.TRUE );
        searchNameTableQuestion.setPropertyKeyFromEntityKey( DataStore.SavedSearchesGroup.SavedSearchGroup.SearchNameItem.CURRENT_INSTANCE_PATH );
        savedSearchesTable.addChild( searchNameTableQuestion );

        // 1.4.2 Load-and-exec-saved-search table button
        final FormButtonWrapper loadAndExecSavedSearchTableButton = FormButtonWrapper.create( getFormContext() );
        loadAndExecSavedSearchTableButton.setActionCommand( "[Exec]" );
        loadAndExecSavedSearchTableButton.setDependencyType( FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE );
		loadAndExecSavedSearchTableButton.unwrap().addChild(p_loadAndExecSavedSearchContainerRule.unwrap(), true /* p_linkedObject */);
        savedSearchesTable.addChild( loadAndExecSavedSearchTableButton );

        // 1.4.3 Delete saved search table button
        final FormButtonWrapper deleteSavedSearchTableButton = FormButtonWrapper.create( getFormContext() );
        deleteSavedSearchTableButton.setActionCommand( "Delete" );
        deleteSavedSearchTableButton.setDependencyType( FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE );
        deleteSavedSearchTableButton.unwrap().addChild( p_deleteSavedSearchContainerRule.unwrap(), true /* p_linkedObject */);
        savedSearchesTable.addChild( deleteSavedSearchTableButton );

        // 2 Presentation elements

        final RichHTMLPresentationColumnBreakWrapper favouritesPresColumn = RichHTMLPresentationColumnBreakWrapper.create( getFormContext() );
        favouritesPresColumn.setDesignToUseFromEntityKey( "Enquiry Favourites Column" );
        p_parentPresNodeForSearchElems.addChild( favouritesPresColumn );

        // 2.1 "Favourites" presentation item group
		final RichHTMLPresentationItemGroupWrapper favouritesPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), favouritesItemGroup.unwrap());
        favouritesPresColumn.addChild( favouritesPresItemGroup );

        // 2.2 "Favourites" presentation heading
		final RichHTMLPresentationQuestionWrapper favouritesPresHeading = RichHTMLPresentationQuestionWrapper.create(getFormContext(), favouritesHeading.unwrap());
        favouritesPresItemGroup.addChild( favouritesPresHeading );

        // 2.3 "Add" presentation button
		final RichHTMLPresentationButtonWrapper addCurrentSelectionAsFavouritePresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), addCurrentSelectionAsFavouriteButton.unwrap());
        addCurrentSelectionAsFavouritePresButton.setGroupWithPrevious( Boolean.TRUE );
        addCurrentSelectionAsFavouritePresButton.setSpacingToPrevious( FAVOURITES_SPACING_FOR_GROUP_WITH_PREVIOUS ); //GitHub #679
        addCurrentSelectionAsFavouritePresButton.setButtonNewLine( Boolean.FALSE );
        addCurrentSelectionAsFavouritePresButton.setDesignToUseFromEntityKey( "Enquiry Add Favourite Buttton" );
        addCurrentSelectionAsFavouritePresButton.setHintText( "$%SLANG Enquiry.AddCurrentSelectionAsFavouriteHint Add$" );
        favouritesPresItemGroup.addChild( addCurrentSelectionAsFavouritePresButton );

        // 2.4 "Saved Searches" presentation table

		final RichHTMLPresentationTableWrapper savedSearchesPresTable = RichHTMLPresentationTableWrapper.create(getFormContext(), savedSearchesTable.unwrap());
		savedSearchesPresTable.setTableSummary("$%SLANG Enquiry.SavedSearchesHint Saved Searches$");
        savedSearchesPresTable.setDesignToUseFromEntityKey( "Enquiry Saved Searches Table" );
        favouritesPresItemGroup.addChild( savedSearchesPresTable );

        // 2.4.1 Hidden "Search Name" presentation table question
		final RichHTMLPresentationQuestionWrapper searchNamePresTableQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), searchNameTableQuestion.unwrap());
        savedSearchesPresTable.addChild( searchNamePresTableQuestion );

        // 2.4.2 Load-and-exec-saved-search presentation table button
		final RichHTMLPresentationButtonWrapper loadAndExecSavedSearchPresTableButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), loadAndExecSavedSearchTableButton.unwrap());
        loadAndExecSavedSearchPresTableButton.setDesignToUseFromEntityKey( "Enquiry Favourite Link" );
		loadAndExecSavedSearchPresTableButton.setButtonText("$$" + DataStore.SavedSearchesGroup.SavedSearchGroup.SearchNameItem.CURRENT_INSTANCE_PATH + "$");
        savedSearchesPresTable.addChild( loadAndExecSavedSearchPresTableButton );

        // 2.4.3 Delete saved search table button
		final RichHTMLPresentationButtonWrapper deleteSavedSearchPresTableButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), deleteSavedSearchTableButton.unwrap());
		deleteSavedSearchPresTableButton.setHintText( "$%SLANG Enquiry.DeleteSavedSearchHint Delete$" );
        deleteSavedSearchPresTableButton.setDesignToUseFromEntityKey( "Enquiry Delete Favourite Button" );
        savedSearchesPresTable.addChild( deleteSavedSearchPresTableButton );
    }

	private void addSearchRequestFilterToFavouritesXMLInterface(XMLNodeRelationshipWrapper p_searchRequestFiltersXmlNodeRelationship, PropertyGroupWrapper searchParamGroup) {
        final String searchParamGroupName = searchParamGroup.getName();

        final XMLNodeRelationshipWrapper xmlNodeRelationship = XMLNodeRelationshipWrapper.create( getFormContext() );
        xmlNodeRelationship.setName( "XML Node " + searchParamGroupName );
        xmlNodeRelationship.setNodeName( searchParamGroupName );
        p_searchRequestFiltersXmlNodeRelationship.addChild( xmlNodeRelationship );

		final String searchParamGroupInstancePath = DataStore.SavedSearchesGroup.SavedSearchGroup.SearchRequestGroup.FiltersGroup.CURRENT_INSTANCE_PATH + '.' + searchParamGroupName + "[1]";

        final XMLNodeMappingWrapper xmlAttrComparisonOperator = XMLNodeMappingWrapper.create( getFormContext() );
		xmlAttrComparisonOperator.setName("XML Attribute " + DataStore.SearchParamFilterGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME);
        xmlAttrComparisonOperator.setRead( Boolean.TRUE );
        xmlAttrComparisonOperator.setWrite( Boolean.TRUE );
		xmlAttrComparisonOperator.setDataItemNameFromEntityKey(searchParamGroupInstancePath + '.' + DataStore.SearchParamFilterGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME);
        xmlAttrComparisonOperator.setAttributeName( DataStore.SearchParamFilterGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME );
        xmlNodeRelationship.addChild( xmlAttrComparisonOperator );

        final XMLNodeMappingWrapper xmlAttributeComparisonOperand1 = XMLNodeMappingWrapper.create( getFormContext() );
        xmlAttributeComparisonOperand1.setName( "XML Attribute " + DataStore.SearchParamFilterGroupTemplate.OPERAND1_ITEM_NAME );
        xmlAttributeComparisonOperand1.setRead( Boolean.TRUE );
        xmlAttributeComparisonOperand1.setWrite( Boolean.TRUE );
		xmlAttributeComparisonOperand1.setDataItemNameFromEntityKey(searchParamGroupInstancePath + '.'  + DataStore.SearchParamFilterGroupTemplate.OPERAND1_ITEM_NAME);
        xmlAttributeComparisonOperand1.setAttributeName( DataStore.SearchParamFilterGroupTemplate.OPERAND1_ITEM_NAME );
        xmlNodeRelationship.addChild( xmlAttributeComparisonOperand1 );

        final XMLNodeMappingWrapper xmlAttributeComparisonOperand2 = XMLNodeMappingWrapper.create( getFormContext() );
        xmlAttributeComparisonOperand2.setName( "XML Attribute " + DataStore.SearchParamFilterGroupTemplate.OPERAND2_ITEM_NAME );
        xmlAttributeComparisonOperand2.setRead( Boolean.TRUE );
        xmlAttributeComparisonOperand2.setWrite( Boolean.TRUE );
		xmlAttributeComparisonOperand2.setDataItemNameFromEntityKey(searchParamGroupInstancePath + '.'  + DataStore.SearchParamFilterGroupTemplate.OPERAND2_ITEM_NAME);
        xmlAttributeComparisonOperand2.setAttributeName( DataStore.SearchParamFilterGroupTemplate.OPERAND2_ITEM_NAME );
        xmlNodeRelationship.addChild( xmlAttributeComparisonOperand2 );
    }

    private void addItemGroupForSaveSearchDialog(
        final String p_phaseName,
        GenericNodeWrapper<?> p_parentNodeForSearchElems,
        PresentationObjectWrapper<?> p_parentPresNodeForSearchElems,
        PropertyWrapper p_saveSearchDialogHideShowStateItem,
        PropertyWrapper p_saveSearchModeItem,
        PropertyWrapper p_newSearchNameItem,
        PropertyWrapper p_savedSearchNameToUpdateItem,
        PropertyGroupWrapper p_savedSearchGroup,
        ContainerRuleWrapper p_saveSearchAsFavouriteContainerRule,
        ContainerRuleWrapper p_reportDuplicateSearchNameContainerRule
	) {
        // 8A Save Favourite Dialog process item group

        final ItemGroupWrapper saveFavouriteDialogItemGroup = ItemGroupWrapper.create( getFormContext() );
        saveFavouriteDialogItemGroup.setGroupName( "Save Favourite Dialog" );
        saveFavouriteDialogItemGroup.setNotApplicable( Boolean.TRUE );
		saveFavouriteDialogItemGroup.setConditionExpression("$$" + p_saveSearchDialogHideShowStateItem.getPropertyKey(null) + "$ == 'SHOW'");
        p_parentNodeForSearchElems.addChild( saveFavouriteDialogItemGroup );

        final QuestionWrapper saveSearchModeQuestion = QuestionWrapper.create( getFormContext() );
        saveSearchModeQuestion.setQuestionText( "$%SLANG Enquiry.SaveSearchModeLabel Would you like to:$" );
        saveSearchModeQuestion.setNotApplicable( Boolean.TRUE );
        saveSearchModeQuestion.setConditionExpression( "$$" + p_savedSearchGroup.getPropertyGroupKey() + ".lastInstance()$ > '0'" );
        saveSearchModeQuestion.setPropertyKey( p_saveSearchModeItem );
        saveFavouriteDialogItemGroup.addChild( saveSearchModeQuestion );

        final QuestionWrapper enterNewSearchNameQuestion = QuestionWrapper.create( getFormContext() );
        enterNewSearchNameQuestion.setQuestionText( "$%SLANG Enquiry.EnterNewSearchNameLabel Please enter a name for the new favourite:$" );
        enterNewSearchNameQuestion.setPropertyKey( p_newSearchNameItem );
        enterNewSearchNameQuestion.setNotApplicable( Boolean.TRUE );
		enterNewSearchNameQuestion.setConditionExpression("$$" + p_saveSearchModeItem.getPropertyKey(null) + "$ == 'SAVE_AS_NEW'");
        enterNewSearchNameQuestion.setPropertyKey( p_newSearchNameItem );
        saveFavouriteDialogItemGroup.addChild( enterNewSearchNameQuestion );

        final QuestionInErrorRuleWrapper reportDuplicateSearchNameRule = QuestionInErrorRuleWrapper.create( getFormContext() );
		reportDuplicateSearchNameRule.setName("Report duplicate search name for SAVE_AS_NEW within Save Favourite Dialog at " + p_phaseName + " phase");
        reportDuplicateSearchNameRule.setSpecifyQuestions( QuestionInErrorRuleWrapper.ESpecifyQuestions.SPECIFY );
        reportDuplicateSearchNameRule.setQuestionsFromEntityKey( enterNewSearchNameQuestion.getEntityKeyName() );
        reportDuplicateSearchNameRule.setErrorMsg( "$%SLANG Enquiry.ReportDuplicateSearchErrorText Name already in use$" );
        p_reportDuplicateSearchNameContainerRule.addTrueRule( reportDuplicateSearchNameRule.unwrap() );

        final QuestionWrapper selectExistingSearchNameForUpdateQuestion = QuestionWrapper.create( getFormContext() );
        selectExistingSearchNameForUpdateQuestion.setQuestionText( "$%SLANG Enquiry.SelectExistingSearchNameForUpdateLabel Please choose an existing favourite to update$" );
        selectExistingSearchNameForUpdateQuestion.setNotApplicable( Boolean.TRUE );
		selectExistingSearchNameForUpdateQuestion.setConditionExpression("$$" + p_saveSearchModeItem.getPropertyKey(null) + "$ == 'UPDATE_EXISTING'");
        selectExistingSearchNameForUpdateQuestion.setPropertyKey( p_savedSearchNameToUpdateItem );
        saveFavouriteDialogItemGroup.addChild( selectExistingSearchNameForUpdateQuestion );

        final FormButtonWrapper okayButton = FormButtonWrapper.create( getFormContext() );
        okayButton.setActionCommand( "Okay" );
        okayButton.setCheckMandatoryFields( Boolean.TRUE );
        okayButton.setDependencyType( FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE );
        okayButton.unwrap().addChild( p_saveSearchAsFavouriteContainerRule.unwrap(), true /* p_linkedObject */);
        saveFavouriteDialogItemGroup.addChild( okayButton );

        final FormButtonWrapper cancelButton = FormButtonWrapper.create( getFormContext() );
        cancelButton.setActionCommand( "Cancel" );
        cancelButton.setDependencyType( FormButtonWrapper.EDependencyType.ALL_QUESTIONS_IN_PHASE );
        saveFavouriteDialogItemGroup.addChild( cancelButton );

        final SetValueRuleWrapper hideSaveSearchDialogRule = SetValueRuleWrapper.create( getFormContext() );
        hideSaveSearchDialogRule.setName( "Set Value" );
        hideSaveSearchDialogRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        hideSaveSearchDialogRule.setPropertyName( p_saveSearchDialogHideShowStateItem );
        hideSaveSearchDialogRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
        hideSaveSearchDialogRule.setFromValue( Lists.ShowOrHide.Keys.HIDE );
        hideSaveSearchDialogRule.setFromValueList( Lists.ShowOrHide.Keys.HIDE );
        cancelButton.addChild( hideSaveSearchDialogRule );

        // 8B Save Favourite Dialog (presentation)

        final RichHTMLPresentationFormatBreakWrapper saveFavouriteDialogFloatingWindowWidgetSection = RichHTMLPresentationFormatBreakWrapper.create( getFormContext() );
        saveFavouriteDialogFloatingWindowWidgetSection.setHideField( Boolean.TRUE );
		saveFavouriteDialogFloatingWindowWidgetSection.setConditionExpression("$$" + p_saveSearchDialogHideShowStateItem.getPropertyKey(null) + "$ == 'SHOW'");
		saveFavouriteDialogFloatingWindowWidgetSection.setAttribute("DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|Enquiry Saved Search Name Entry Dialog");
        saveFavouriteDialogFloatingWindowWidgetSection.setAttribute( "DisplayTypeHEIGHT", "210" );
        saveFavouriteDialogFloatingWindowWidgetSection.setAttribute( "DisplayTypeTITLE", "Save Favourite" );
        saveFavouriteDialogFloatingWindowWidgetSection.setAttribute( "DisplayTypeWIDTH", "350" );
        saveFavouriteDialogFloatingWindowWidgetSection.setDefaultButton( okayButton.unwrap().getActionCommand() );
        saveFavouriteDialogFloatingWindowWidgetSection.setDefaultButtonName( okayButton.getEntityKeyName() );
        p_parentPresNodeForSearchElems.addChild( saveFavouriteDialogFloatingWindowWidgetSection );

		final RichHTMLPresentationItemGroupWrapper saveFavouriteDialogPresItemGroup =  RichHTMLPresentationItemGroupWrapper.create(getFormContext(), saveFavouriteDialogItemGroup.unwrap());
        saveFavouriteDialogFloatingWindowWidgetSection.addChild( saveFavouriteDialogPresItemGroup );

		final RichHTMLPresentationQuestionWrapper saveSearchModePresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), saveSearchModeQuestion.unwrap());
        saveSearchModePresQuestion.setAnswerNewLine( Boolean.TRUE );
        saveSearchModePresQuestion.setEditDesignToUse( "N" );
        saveSearchModePresQuestion.setDisplayType( "Radio Button" );
        saveSearchModePresQuestion.setLayoutDirection( "Vertical" );
        saveFavouriteDialogPresItemGroup.addChild( saveSearchModePresQuestion );

        saveFavouriteDialogPresItemGroup.addChild( RichHTMLPresentationSpacingWrapper.create( getFormContext() ) );

		final RichHTMLPresentationQuestionWrapper searchNamePresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), enterNewSearchNameQuestion.unwrap());
        searchNamePresQuestion.setDesignToUseForDisplayTypeFromEntityKey( "Enquiry Search Name Field" );
        searchNamePresQuestion.setDisplayType( "Text Input Field" );
        searchNamePresQuestion.setDesignToUseFromEntityKey( "Enquiry Save Search Dialog Question" );
        saveFavouriteDialogPresItemGroup.addChild( searchNamePresQuestion );

		final RichHTMLPresentationQuestionWrapper selectExistingSearchNameForUpdatePresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), selectExistingSearchNameForUpdateQuestion.unwrap());
        selectExistingSearchNameForUpdatePresQuestion.setDisplayType( "Drop down list" );
        selectExistingSearchNameForUpdatePresQuestion.setDesignToUseFromEntityKey( "Enquiry Save Search Dialog Question" );
        saveFavouriteDialogPresItemGroup.addChild( selectExistingSearchNameForUpdatePresQuestion );

        final RichHTMLPresentationSpacingWrapper presSpacingWithAline = RichHTMLPresentationSpacingWrapper.create( getFormContext() );
        presSpacingWithAline.setSpacingType( RichHTMLPresentationSpacingWrapper.ESpacingType.LINE );
        saveFavouriteDialogPresItemGroup.addChild( presSpacingWithAline );

		final RichHTMLPresentationButtonWrapper okayPresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), okayButton.unwrap());
        okayPresButton.setDisplayInAnswerColumn( Boolean.FALSE );
        okayPresButton.setDesignToUseFromEntityKey( "Custom" );
        okayPresButton.setEditDesignToUse( "N" );
        okayPresButton.setDisplayType( "Use Link" );
        okayPresButton.setButtonStyle( "EnquiryLeftMostLinkButton" );
        saveFavouriteDialogPresItemGroup.addChild( okayPresButton );

		final RichHTMLPresentationButtonWrapper cancelPresButton = RichHTMLPresentationButtonWrapper.create(getFormContext(), cancelButton.unwrap());
        cancelPresButton.setGroupWithPrevious( Boolean.TRUE );
        cancelPresButton.setSpacingToPrevious( DEFAULT_SPACING_FOR_GROUP_WITH_PREVIOUS );
        cancelPresButton.setDesignToUseFromEntityKey( "Link Button" );
        saveFavouriteDialogPresItemGroup.addChild( cancelPresButton );
    }

    private EdgeConnectDataType mapToEdgeConnectDataType(MdfProperty p_mdfProperty)
    {
        AssertionUtils.requireNonNull( p_mdfProperty, "p_mdfProperty" );

		final MdfAssociationImpl mdfAssociation = (p_mdfProperty instanceof MdfAssociationImpl) ? (MdfAssociationImpl) p_mdfProperty : null;

        /*
         * Kludge City (pop: 1004)
         *
         * The MdfEntity (i.e. type def) returned for an MdfProperty representing a primary or foreign key is always the MdfClass describing the entity type referred to by
         * that key - meaning that (apparently) there is no way to discover the primitive type of such a key.
         *
         * The following cop-out clause is therefore necessary to prevent infinite recursion should p_mdfProperty turn out to represent a primary or foreign key.
         *
         * Fortunately we only make use of this type information for the Search screen's filter value parameters, and there we present the filter value input for foreign-key type
         * parameters using the "Dropdown" widget, which assumes a source Question with a "Text" typed answer item.
         */
        if ( ( mdfAssociation != null ) && ( mdfAssociation.isPrimaryKey() || isForeignKeyAssociation( mdfAssociation ) ) )
            return EdgeConnectDataType.TEXT;

        final MdfEntity mdfEntity = p_mdfProperty.getType();

        if ( mdfEntity != null )
        {
            if ( mdfEntity.isPrimitiveType() )
            {
                return EdgeConnectDataType.findForPrimitive((MdfPrimitive) mdfEntity, EdgeConnectDataType.TEXT);
            }

            else if ( mdfEntity instanceof MdfClass )
            {
                // NB: Given the cop-out clause at the beginning of this method, we know this must be a reference to a contained type (vs. the referent of a primary or foreign key)

                MdfClass mdfClass = (MdfClass) mdfEntity;

                final List<?> mdfClassProperties = mdfClass.getProperties();
                final int numMdfClassProperties = ( mdfClassProperties == null ) ? 0 : mdfClassProperties.size();

                for (int i = 0; i < numMdfClassProperties; ++i)
                {
                    final Object o = mdfClassProperties.get( i );

                    if ( o instanceof MdfProperty )
                    {
                        MdfProperty mdfProperty = (MdfProperty) o;

                        if ( mdfProperty.getName().equals( p_mdfProperty.getName() ) )
                            return mapToEdgeConnectDataType( mdfProperty );
                    }
                }
            }

			else {
				LOGGER.warn("Unhandled MdfEntity subtype: " + mdfEntity.getClass().getName() + " for property: " + p_mdfProperty.getQualifiedName());
            }
        }

        return EdgeConnectDataType.TEXT;
    }

    private void addSearchResultsTableQuestionsAndDataItems(
        PhaseWrapper p_searchResultsPhase,
        PresentationPhaseWrapper p_searchResultsPresPhase,
        PropertyGroupWrapper p_searchResultGroup,
        DataStructureWrapper p_linkTypeStruct,
        XMLMappingSetWrapper p_searchResultsXmlInterface,
        PropertyWrapper p_selectionElemsHideShowStateItem,
        PropertyWrapper p_searchOutcomeItem,
        PropertyWrapper p_topLevelLinksActionItem,
        PropertyWrapper p_resultsTableSelectorItem,
        File p_templatesDir,
        Map<String, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByAppFieldName,
        SortedMap<Integer, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByT24ColumnNumber
	) throws Exception {
        AssertionUtils.requireNonNull(p_searchResultsPhase, "p_searchResultsPhase");
        AssertionUtils.requireNonNull(p_searchResultsPresPhase, "p_searchResultsPresPhase");
        AssertionUtils.requireNonNull(p_searchResultGroup, "p_searchResultGroup");
        AssertionUtils.requireNonNull(p_linkTypeStruct, "p_linkTypeStruct");
        AssertionUtils.requireNonNull(p_searchResultsXmlInterface, "p_searchResultsXmlInterface");
        AssertionUtils.requireNonNull(p_selectionElemsHideShowStateItem, "p_selectionElemsHideShowStateItem");
        AssertionUtils.requireNonNull(p_searchOutcomeItem, "p_searchOutcomeItem");
        AssertionUtils.requireNonNull(p_topLevelLinksActionItem, "p_topLevelLinksActionItem");
        AssertionUtils.requireNonNull(p_resultsTableSelectorItem, "p_resultsTableSelectorItem");
        AssertionUtils.requireNonNull(p_templatesDir, "p_templatesDir");
        AssertionUtils.requireNonNull(p_enquirySelectionFieldInfoByAppFieldName, "p_enquirySelectionFieldInfoByAppFieldName");
        AssertionUtils.requireNonNull(p_enquirySelectionFieldInfoByT24ColumnNumber, "p_enquirySelectionFieldInfoByT24ColumnNumber");

        final EList<Field> fields = m_enquiry.getFields();
        final int numFields = ( fields == null ) ? 0 : fields.size();

        if ( numFields == 0 )
            return;

        final String resultGroupPath = p_searchResultGroup.getPropertyGroupKey();
        final String currentResultGroupInstancePath = resultGroupPath.substring( 0, resultGroupPath.length() - 2 ) + "C]";

        final PrintWriter searchResultsHTMLTemplateWriter;

        final XMLNodeRelationshipWrapper xmlNodeDataRow;
        final XMLNodeRelationshipWrapper xmlNodeColumnheaders;

        if (m_enquiryAttrsDigest.isResultsScreenToolbarRequired())
        {
            // Figure out where we're going to be writing our Search Results HTML template & set up the PrintWriter we'll use to write it

        	final File searchResultsHTMLTemplateFile = new File( p_templatesDir, m_ifpFriendlyEnquiryName + "ResultsHTML.tpl" );

	        try
	        {
	            searchResultsHTMLTemplateWriter = new PrintWriter( new BufferedWriter( new FileWriter( searchResultsHTMLTemplateFile ) ) );
	            searchResultsHTMLTemplateWriter.println( RESULT_HTML_TEMPLATE_PREAMBLE );
	            searchResultsHTMLTemplateWriter.println( "\t\t<table>" );
	        }

	        catch (IOException ioe)
	        {
	            throw new Exception(
	            	searchResultsHTMLTemplateFile.exists() ?
	                    "Error opening " + searchResultsHTMLTemplateFile.getPath() + " for writing" :
	    				"Error creating " + searchResultsHTMLTemplateFile.getPath()
	    		);
	        }

	        // Create the opening elements of the Search Results XML interface definition needed to support the "export results as XML" option ...

	        final XMLNodeRelationshipWrapper xmlNodeEnquiry = XMLNodeRelationshipWrapper.create( getFormContext() );
	        xmlNodeEnquiry.setName( "XML Node enquiry" );
	        xmlNodeEnquiry.setNodeName( "enquiry" );
	        p_searchResultsXmlInterface.addChild( xmlNodeEnquiry );

	        final XMLNodeMappingWrapper xmlAttributeTitle = XMLNodeMappingWrapper.create( getFormContext() );
	        xmlAttributeTitle.setName( "XML Attribute title" );
	        xmlAttributeTitle.setWrite( Boolean.TRUE );
	        xmlAttributeTitle.setDataType( XMLNodeMappingWrapper.EDataType.VALUE );
	        xmlAttributeTitle.setDataValue( m_enquiry.getName() );
	        xmlAttributeTitle.setAttributeName( "title" );
	        xmlNodeEnquiry.addChild( xmlAttributeTitle );

	        final XMLNodeRelationshipWrapper xmlNodeHeader = XMLNodeRelationshipWrapper.create( getFormContext() );
	        xmlNodeHeader.setName( "XML Node header" );
	        xmlNodeHeader.setNodeName( "header" );
	        xmlNodeEnquiry.addChild( xmlNodeHeader );

	        final XMLNodeRelationshipWrapper xmlNodeData = XMLNodeRelationshipWrapper.create( getFormContext() );
	        xmlNodeData.setName( "XML Node data" );
	        xmlNodeData.setNodeName( "data" );
	        xmlNodeEnquiry.addChild( xmlNodeData );

		    xmlNodeDataRow = XMLNodeRelationshipWrapper.create( getFormContext() );
	        xmlNodeDataRow.setName( "XML Node row" );
	        xmlNodeDataRow.setPropertyGroupsToIncrementFromEntityKey( currentResultGroupInstancePath );
	        xmlNodeDataRow.setNodeName( "row" );
	        xmlNodeData.addChild( xmlNodeDataRow );

	        xmlNodeColumnheaders = XMLNodeRelationshipWrapper.create( getFormContext() );
	        xmlNodeColumnheaders.setName( "XML Node columnheaders" );
	        xmlNodeColumnheaders.setNodeName( "columnheaders" );
	        xmlNodeHeader.addChild( xmlNodeColumnheaders );
        }

        else
        {
        	searchResultsHTMLTemplateWriter = null;

        	xmlNodeDataRow = null;
        	xmlNodeColumnheaders = null;
        }

        populateBreakAndConversionFields(fields);

        // NEW {

        final PropertyGroupWrapper workingElementsGroup = PropertyGroupWrapper.wrap((PropertyGroup) p_selectionElemsHideShowStateItem.unwrap().getParent());

        final IrisResultGroupSpec irisResultGroupSpec = new IrisResultGroupSpec("IrisResult", m_irisEnquiryMetaData);

    	final boolean isPseudoResultsToolbar = (m_enquiryResultsToolbarElems == null);

    	if (isPseudoResultsToolbar)
    	    m_enquiryResultsToolbarElems = new EnquiryResultsToolbarElems(this, "Enquiry Result-set Actions", new VisualItemContainerWrapperPair(p_searchResultsPhase, p_searchResultsPresPhase), p_searchOutcomeItem, true);

        if (m_enquiryAttrsDigest.isEnquiryWithDrilldowns())
            workingElementsGroup.addChild(m_anyDynamicDrilldownsToDisplayItem = PropertyWrapper.create(getFormContext(), "AnyDynamicDrilldownsToDisplay"));

        m_staticResultsScreenHeadersModel = StaticResultsScreenHeadersModel.createIfNeededFor(this, m_enquiryAttrsDigest, workingElementsGroup);

        // } NEW

        addBreakQuestionAndDataStoreElems(workingElementsGroup);

        try
        {
            for (int i = 0; i < numFields; ++i)
            {
                final Field field = fields.get( i );

                if ( field == null )
                {
                    LOGGER.error( "(Enquiry).getFields().get(" + i + ") -> null !" );
                    continue;
                }

                final String t24EnqFieldName = field.getName();

                if ( ( t24EnqFieldName == null ) || ( t24EnqFieldName.length() == 0 ) || t24EnqFieldName.equalsIgnoreCase( "NULL" ) )
                {
					LOGGER.error("(Enqiry).getFields().get(" + i + ").getName() -> " + StringUtils.quoteIfString(t24EnqFieldName) + " !");
                    continue;
                }

                if (m_enquiryFieldsDigest.isCustomNoResultsFoundMessageField(field))
                {
                    /*
                     * The actual message text (if we're supposed to use it) will have been captured by EnquiryFieldsDigest, and is therefore *already* available
                     * via: m_enquiryFieldsDigest.getCustomNoResultsFoundMessage().
                     *
                     * Our only concern here is to avoid mistaking such a field for one that needs to be added to our IrisResultGroupSpec (failure to avoid this
                     * may result in the displayable results table column for the t24 column ref'd by such a field getting styled as a "no results" message !).
                     */
                    continue;
                }

                final Operation fieldOperation = field.getOperation();

                if ( fieldOperation == null )
                {
                    LOGGER.error( "[\"" + t24EnqFieldName + "\"] field.getOperation() -> null !" );
                    continue;
                }
                
                if (fieldOperation instanceof BreakOnChangeOperation)
                {
                    final String refdT24FieldName = ((BreakOnChangeOperation) fieldOperation).getField();
                    
                    if (refdT24FieldName == null)
                    {
                        LOGGER.error( "[\"" + t24EnqFieldName + "\"] ((BreakOnChangeOperation) fieldOperation).getField() -> null !");
                        continue;
                    }
                    
                    irisResultGroupSpec.notifyBreakChangeValueBearingT24FieldName(refdT24FieldName);
                }

                final FieldPosition fieldPosition = field.getPosition();

                if ( fieldPosition == null )
                {
                    LOGGER.info( "[\"" + t24EnqFieldName + "\"] field.getPosition() -> null (field ignored)" );
                    continue;
                }

                if ( Boolean.TRUE.equals( fieldPosition.getPageThrow() ) )
                {
                    continue;
                }

                if (handleConstantOperation(field, irisResultGroupSpec))
                    continue;

	    		// (field is displayable either as a dynamic header or as a value in one of the results tables)

                final Integer t24LineNumber = fieldPosition.getLine();
                final int t24ColumnNumber = fieldPosition.getColumn();

                final String relativePosition = fieldPosition.getRelative();
                final DisplaySectionKind displaySection = field.getDisplaySection();
                final BreakCondition breakCondition = field.getBreakCondition();
                final String breakField = (breakCondition == null) ? null : breakCondition.getField();
                final BreakKind breakKind = (breakCondition == null) ? null : breakCondition.getBreak();

                final boolean fieldHasLineNumberAndRelativePosition = (
            		(t24LineNumber != null) &&
                	(relativePosition != null) &&
                	("+".equals(relativePosition) || "-".equals(relativePosition))
                );

	    		//first and last lines of enquiry results table. How to determine the line number?
	    		//+0 actually this is displaybreak with once
	    		//+1 constant with a display break with break change on a field and is a constant EG:STMT.ENT.BOOK
	    		//+1 could be display break with an END
	    		//+1 could be display break with a page throw

	    		boolean isFirstLine = false;
	    		boolean isResultFirstLine = m_enquiryFieldsDigest.allVisibleResultsFieldsHaveRelativePositions();
	    		boolean isLastLine = false;
	    		boolean isPageThrowLine = false;
	    		boolean isFooterLine = (displaySection == DisplaySectionKind.FOOTER);

	    		ResultsTableType targetResultsTableType = ResultsTableType.MAIN_ENQUIRY_RESULTS; // tentative

	    		if (breakCondition != null)
	    		{
	    			//may need to handle page and new page based on discussion.(Eg:Balance at period start in nostro)
	    			if ((breakKind == BreakKind.ONCE) && (displaySection != DisplaySectionKind.HEADER))
	    			{
	    				if (! ((displaySection == null) || (displaySection == DisplaySectionKind.FOOTER)))
	    					isFirstLine = true;
	    			}

	    			if (breakKind == BreakKind.PAGE && ! isResultFirstLine)
	    			{
	    				//fixed for MF.FEE.DETAILS
	    				isResultFirstLine = (
	    					(displaySection == null) ||
	    					(
	    							(displaySection != DisplaySectionKind.HEADER) &&
	    							(displaySection != DisplaySectionKind.FOOTER) &&
	    							(t24LineNumber != null) &&
	    							(t24LineNumber.intValue() != 0)
	    					)
	    				);
	    			}

	    			if (breakKind == BreakKind.END)
	    			{
	    				// e.g. "Balance at period end" in STMT.ENT.BOOK
	    				isLastLine=true;
	    			}

	    			if ((breakCondition != null) && (breakCondition.getField() != null))
	    			{
	    				final String breakConditionFieldName = breakCondition.getField();

	    				if (breakConditionFieldName != null) {
		    				final Iterator<Field> fieldsListIterator = m_enquiry.getFields().iterator();

		    				while (fieldsListIterator.hasNext())
		    				{
		    					final Field enquiryField = fieldsListIterator.next();

		    					if (
		    						breakConditionFieldName.equals(enquiryField.getName()) &&
		    						(enquiryField.getPosition() != null) &&
		    						(enquiryField.getPosition().getPageThrow() != null)
		    					) {
	    		    				isPageThrowLine = true;
	    		    				break;
		    					}
		    				}

	    				}
	    			}
	    		}

                if ( (t24LineNumber != null) && (isFirstLine || isResultFirstLine || isLastLine || isPageThrowLine || isFooterLine) )
                {
                    int logicalLineNumber = t24LineNumber.intValue();

                    //display once,end,footer section and pagethrow
                    if(isFirstLine)
                    	logicalLineNumber = 0;
                    if(isResultFirstLine)
                    	logicalLineNumber = 2;
                    if(isFooterLine)
                    	logicalLineNumber = 3;
                    if(isPageThrowLine)
                    	logicalLineNumber = 4;
                    if(isLastLine)
                    	logicalLineNumber = 1;

                    switch (logicalLineNumber)
                    {
                        case 0:
    						// NEW {
    						targetResultsTableType = ResultsTableType.DISPLAY_ONCE_RESULTS;
    						// } NEW
                            break;

                        case 1:
    						// NEW {
    						targetResultsTableType = ResultsTableType.DISPLAY_END_RESULTS;
    						// } NEW
                            break;

                        case 2:
                        	if (! m_enquiryFieldsDigest.allVisibleResultsFieldsHaveRelativePositions())
                        	{
        						// NEW {
                        		targetResultsTableType = (displaySection == DisplaySectionKind.HEADER) ? ResultsTableType.ENQUIRY_HEADER_RESULTS : ResultsTableType.MAIN_ENQUIRY_RESULTS;
        						// } NEW
                        	}

                            break;

                        case 3:
    						// NEW {
    						targetResultsTableType = ResultsTableType.FOOTER_RESULTS;
    						// } NEW
                            break;

                        case 4:
    						// NEW {
    						targetResultsTableType = ResultsTableType.PAGE_THROW_RESULTS;
    						// } NEW
                            break;

                        default:
    						LOGGER.warn("[\"" + t24EnqFieldName + "\"] field.getPosition().getRelative() -> \"+\", but field.getLine() -> " + t24LineNumber + " (field ignored)");
                            continue;
                    }

                    //!! STH: How come we leave it until now to do this (i.e. how come this isn't this done before we set (& then switch on) logicalLineNumber above) ?

                    if (isFooterLine && isPageThrowLine)
                    {
                    	isFooterLine = false;
                    	targetResultsTableType = ResultsTableType.PAGE_THROW_RESULTS;
                    }

                    // NEW {

                    if (fieldHasLineNumberAndRelativePosition)
                    {
                    	if ((breakKind == BreakKind.PAGE) && (t24LineNumber.intValue() >= 0) && displaySection != DisplaySectionKind.HEADER)
                    		targetResultsTableType = ResultsTableType.DISPLAY_ONCE_RESULTS;

                    	/*
                    	 * Actually we don't get here in the 1st place to make the following test for STMT.ENT.BOOK's D.BAL field because:
                    	 * - although that field refs a break field ("B.ACC")
                    	 * - we're currently requiring that B.ACC is a displayable field with a page-throw
                    	 * - whereas B.ACC is not displayable (has no position) and has operation: break-change "ACC" (which is also not displayable)
                    	 */
                    	/*
                    	else if ((breakCondition.getField() != null) && t24LineNumber.intValue() > 0)
                    		targetResultsTableType = ResultsTableType.PAGE_THROW_RESULTS;
                    	*/
                    }

                    irisResultGroupSpec.add(new VisibleIrisResultItemSpec(field, targetResultsTableType, m_enquiryFieldsDigest));

                    // } NEW

                    continue;
                }

                // NEW {

                if (field.getDisplaySection() == DisplaySectionKind.FOOTER)
                {
                    irisResultGroupSpec.add(new VisibleIrisResultItemSpec(field, ResultsTableType.DISPLAY_END_RESULTS, m_enquiryFieldsDigest));
                    continue;
                }

            	if (fieldHasLineNumberAndRelativePosition && (breakField != null) && t24LineNumber.intValue() > 0)
            	{
                	irisResultGroupSpec.add(new VisibleIrisResultItemSpec(field, ResultsTableType.PAGE_THROW_RESULTS, m_enquiryFieldsDigest));
                	continue;
            	}

                // } NEW

 	    		final AppFieldNameOrNumberResult appFieldNameOrNumberResult = getAppFieldNameOrNumber(fieldOperation, t24LineNumber, relativePosition);

            	final String appFieldName = appFieldNameOrNumberResult.appFieldName;
 	    		final int appFieldPos = appFieldNameOrNumberResult.appFieldPos;

 	    		// NB: We're only interested in building EnquirySelectionFieldInfo for enquiry fields that are *direct* references to fields on the underlying application

				if ((appFieldName != null) && (m_enquiryApplicationMdfClass != null))
				{
					final MdfProperty ultimateAppFieldDefForSearchParamTypeInfo = getAppFieldDef(t24EnqFieldName, appFieldName, appFieldPos);
					final MdfProperty directAppFieldDefForSearchParamDropdownInfo = "@ID".equals(appFieldName) ? ultimateAppFieldDefForSearchParamTypeInfo : m_enquiryApplicationMdfClass.getProperty(appFieldName.replace('.', '_'));

					final EdgeConnectDataType edgeConnectDataType = (ultimateAppFieldDefForSearchParamTypeInfo == null) ? EdgeConnectDataType.TEXT : mapToEdgeConnectDataType(ultimateAppFieldDefForSearchParamTypeInfo);

					final SearchParamDropdownInfo searchParamDropdownInfo = (directAppFieldDefForSearchParamDropdownInfo instanceof MdfAssociation) ?
						extractSearchParamDropdownInfo((MdfAssociation) directAppFieldDefForSearchParamDropdownInfo ) : null;

                    final EnquirySelectionFieldInfo enqSelectionFieldInfo = new EnquirySelectionFieldInfo(appFieldName, t24EnqFieldName, edgeConnectDataType, searchParamDropdownInfo, directAppFieldDefForSearchParamDropdownInfo);
	    			p_enquirySelectionFieldInfoByAppFieldName.put(appFieldName, enqSelectionFieldInfo);
					p_enquirySelectionFieldInfoByT24ColumnNumber.put(t24ColumnNumber, enqSelectionFieldInfo);
				}

                // New condition for MF.FEE.DETAILS
                if (
                	(t24LineNumber != null) &&
                	(
                		(
        					(displaySection == DisplaySectionKind.HEADER) ||
        					((breakCondition != null) && (breakCondition.getBreak() == BreakKind.NEW_PAGE)) ||
        					(t24LineNumber.intValue() == 0)
                		)
                	)
                ) {
                    // NEW {
                    irisResultGroupSpec.add(new VisibleIrisResultItemSpec(field, ResultsTableType.ENQUIRY_HEADER_RESULTS, m_enquiryFieldsDigest));
                    // } NEW

                    continue;
                }

                // NEW {

                /*
                 * Turns out that (right at the BOTTOM of this field-processing loop), there's a last-ditch bailout condition (as per the 'if' below) to AVOID
                 * actually adding the table question (that's already been lovingly created) into m_resultsTableQuestionByT24ColumnNumber.
                 *
                 * Turns out this is absolutely CRUCIAL. Having missed this first time around, ran into probs with AA.PRODUCT.CATALOG which defines a couple of
                 * fields like this. The symptom with our new table-flattening stuff is that we end up with 2 pairs of fields (each containing both the field we
                 * want and a break field that we DON'T want) where both items in each pair get mapped to the same displayable result item with the SAME
                 * sortOrderForValuesBlockWithinColumn value of 0, which leads to EnquiryDisplayResultsPopulatorRule populating the right item first with the
                 * right value, and then overwriting it with a 2nd value that comes through as blank in the IRIS reponse.
                 *
           		 * TODO: Add some safeguards into our model building code (and EnquiryDisplayResultsPopulatorRule too) so that at least some kind of warning when
           		 * this type of thing happens !
           		 *
           		 * 21/10/2014 STH
                 */

                if ((breakCondition == null) || (breakCondition.getField() == null))
                	irisResultGroupSpec.add(new VisibleIrisResultItemSpec(field, targetResultsTableType, m_enquiryFieldsDigest));

                // } NEW
            } // for each enquiry Field

            addNoResultsFoundHeadingIfApplicable(p_searchResultsPhase, p_searchResultsPresPhase, p_searchOutcomeItem);

            final ItemGroupWrapper searchResultsItemGroup = ItemGroupWrapper.create( getFormContext(), "Search Results" );
            searchResultsItemGroup.setNotApplicable( Boolean.TRUE );
            searchResultsItemGroup.setConditionExpression("$$" + p_searchOutcomeItem.getPropertyKey(null) + "$ != '" + Lists.SearchOutcome.Keys.NO_RESULTS_FOUND + "'" );
            p_searchResultsPhase.addChild( searchResultsItemGroup );

            final RichHTMLPresentationItemGroupWrapper searchResultsPresItemGroup = RichHTMLPresentationItemGroupWrapper.create(getFormContext(), searchResultsItemGroup.unwrap());
            p_searchResultsPresPhase.addChild( searchResultsPresItemGroup );

            final RichHTMLPresentationFormatBreakWrapper searchResultsSection = RichHTMLPresentationFormatBreakWrapper.create( getFormContext() );
            searchResultsSection.setDesignToUseFromEntityKey( "Enquiry Search Results Section" );
            searchResultsPresItemGroup.addChild(searchResultsSection);

	        addFeedLevelLinksQuestionToEnquiryResultsToolbar(p_topLevelLinksActionItem);

            final StringBuilder headerCellsHTMLTemplateBuffer;
            final StringBuilder dataCellsHTMLTemplateBuffer;
            final StringBuilder forCommandTemplateBuffer;

            if (searchResultsHTMLTemplateWriter != null)
            {
            	headerCellsHTMLTemplateBuffer = new StringBuilder();
            	dataCellsHTMLTemplateBuffer = new StringBuilder();
            	forCommandTemplateBuffer = new StringBuilder();

                headerCellsHTMLTemplateBuffer.append( "\t\t\t<tr>\n" );
                dataCellsHTMLTemplateBuffer.append( "\t\t\t<tr>\n" );

                forCommandTemplateBuffer
                                        .append( "\t\t$%for 1 .. " )
                                        .append( currentResultGroupInstancePath )
                                        .append( ".lastInstance() " )
                                        .append( currentResultGroupInstancePath )
                                        .append( '$' );
            }

            else {
            	headerCellsHTMLTemplateBuffer = null;
            	dataCellsHTMLTemplateBuffer = null;
            	forCommandTemplateBuffer = null;
            }

            if (searchResultsHTMLTemplateWriter != null)
            {
                // Write the closing part of our Search Results HTML template
				searchResultsHTMLTemplateWriter.println( headerCellsHTMLTemplateBuffer.append( "\t\t\t</tr>" ) );
                searchResultsHTMLTemplateWriter.println( forCommandTemplateBuffer );
                searchResultsHTMLTemplateWriter.println( dataCellsHTMLTemplateBuffer.append( "\t\t\t</tr>" ) );
                searchResultsHTMLTemplateWriter.println( "\t\t$%endfor$" );
            }

            final PropertyGroupWrapper linksGroup = PropertyGroupWrapper.create( getFormContext(), "Links" );
            linksGroup.setFixedSize( Boolean.FALSE );
            linksGroup.setLinkToStructure( Boolean.TRUE );
            linksGroup.setLinkedStructure( p_linkTypeStruct );

            // NEW_RESULTS_TABLE_AND_DATASTORE_GENERATION {

            if (m_staticResultsScreenHeadersModel != null)
                m_staticResultsScreenHeaderControlElems = m_staticResultsScreenHeadersModel.createAndAddStaticHeaderTablesTo(new VisualItemContainerWrapperPair(searchResultsItemGroup, searchResultsSection));

            final PropertyGroup searchResponseGroup = (PropertyGroup) p_searchResultGroup.unwrap().getParent();

            addResultsTablesAndSupportingDataStoreElems(p_searchResultsPhase, irisResultGroupSpec, searchResultsItemGroup, searchResultsSection, linksGroup, searchResponseGroup, p_searchOutcomeItem, p_topLevelLinksActionItem);

            // } NEW_RESULTS_TABLE_AND_DATASTORE_GENERATION

            m_enquiryResultsToolbarElems.flushAllDeferredAddsAndRemoveAnyEmptyColumns();
            addScriptsAsPresentationOnlyText(searchResultsPresItemGroup);
        }

        finally
        {
        	if (searchResultsHTMLTemplateWriter != null)
        	{
	            searchResultsHTMLTemplateWriter.println( "\t\t</table>" );
	            searchResultsHTMLTemplateWriter.println( RESULT_HTML_TEMPLATE_POSTAMBLE );
	            searchResultsHTMLTemplateWriter.flush();
	            searchResultsHTMLTemplateWriter.close();
        	}
        }
    } // addSearchResultsTableQuestionsAndDataItems()

    private PropertyWrapper addActionListTypedActionDataItemTo(PropertyGroupWrapper p_resultGroup)
    {
    	AssertionUtils.requireNonNull(p_resultGroup, "p_resultGroup");

        final PropertyWrapper actionItem = PropertyWrapper.create(getFormContext(), "Action");
        actionItem.setType(getFormContext().getEntityofType("Actions", FormList.class).getFullName().toString());
        actionItem.setAttribute(EnquiryResultConstants.CustomAttrNames.IS_DATA_FOR_EXPORT, "false");
        p_resultGroup.addChild(actionItem);

        return actionItem;
    }

    private int getDisplayResultsGroupCount()
    {
    	int result = 0;

    	if (m_headerDisplayResultGroups != null)
    		result += m_headerDisplayResultGroups.length;

    	final PropertyGroupWrapper[] nonHeaderDisplayGroups = {
    		m_displayOnceDisplayResultGroup,
    		m_mainDisplayResultGroup,
    		m_footerDisplayResultGroup,
    		m_pageThrowDisplayResultGroup,
    		m_displayEndDisplayResultGroup
    	};

    	for (int i = 0; i < nonHeaderDisplayGroups.length; ++i)
    	{
    		if (nonHeaderDisplayGroups[i] != null)
    			++result;
    	}

    	return result;
    }

    private MdfProperty getAppFieldDef(String p_enqFieldName, String p_refdAppFieldName, int p_appFieldPos)
    {
    	AssertionUtils.requireNonNullAndNonEmpty(p_enqFieldName, "p_enqFieldName");

    	MdfProperty result = null;

    	if (m_enquiryApplicationMdfClass != null)
    	{
    		if ("@ID".equals(p_refdAppFieldName))
    		{
    			final List<?> primaryKeysList = m_enquiryApplicationMdfClass.getPrimaryKeys();
    			final int numPropertyKeyProps = (primaryKeysList == null) ? 0 : primaryKeysList.size();

    			switch(numPropertyKeyProps) {
    				case 0:
    					LOGGER.warn("Unable to resolve reference to application field: " + p_refdAppFieldName + " from enquiry field: " + p_enqFieldName + "to primary key on underlying application: " + m_enquiryApplicationMdfClass.getName() + " (application has no primary key properties !)");
    					break;

    				case 1:
    					result = (MdfProperty) primaryKeysList.get(0);
    					break;

    				default:
    					LOGGER.warn("Problem resolving reference to application field: " + p_refdAppFieldName + " from enquiry field: " + p_enqFieldName + " to primary key on underlying application: " + m_enquiryApplicationMdfClass.getName() + " (application has no primary key properties !)");
    					result = (MdfProperty) primaryKeysList.get(0);
    					LOGGER.warn("Arbitrarily taking the FIRST primary key property: " + result.getName());
    			}

    			return result;
    		}

    		if ((p_refdAppFieldName != null) || (p_appFieldPos > 0))
    		{
    	    	final List<?> mdfClassProperties = m_enquiryApplicationMdfClass.getProperties();
    			final int numMdfClassProperties = (mdfClassProperties == null) ? 0 : mdfClassProperties.size();

    			for (int i = 0; i < numMdfClassProperties; ++i)
    			{
    				final Object o = mdfClassProperties.get(i);

    				if (o instanceof MdfAssociation)
    				{
    					final MdfAssociation mdfAssociation = (MdfAssociation) o;

    		    		if (DEBUG_ENABLED) System.out.println("getAppFieldDef(): calling findApplicationField for enquiry field: " + StringUtils.quoteIfString(p_enqFieldName));

	    				if (null != (result = findApplicationField(mdfAssociation, p_refdAppFieldName, p_appFieldPos)))
	    					break;
    				}
    			}
    		}
    	}

    	return result;
    } // getAppFieldDef()

    private MdfProperty findApplicationField(final MdfAssociation p_mdfAssociation, final String p_appFieldName, final int p_appFieldPos)
    {
    	AssertionUtils.requireNonNull(p_mdfAssociation, "p_mdfAssociation");

    	if (! (p_mdfAssociation.getType() instanceof MdfClass))
    		return null;

    	final MdfClass assocMdfClass = (MdfClass) p_mdfAssociation.getType();
    	final List<?> assocMdfClassProps = assocMdfClass.getProperties();

    	if ((assocMdfClassProps == null) || assocMdfClassProps.isEmpty())
    		return null;

    	final boolean isAppFieldNameNullOrEmpty = ((p_appFieldName == null) || (p_appFieldName.length() == 0));

    	MdfProperty result = null;

    	final Iterator<?> assocMdfClassPropIter = assocMdfClassProps.iterator();

    	while(assocMdfClassPropIter.hasNext() && (result == null))
    	{
    		final Object _o = assocMdfClassPropIter.next();

    		if (! (_o instanceof MdfProperty))
    			continue;

    		final MdfProperty assocMdfClassProp = (MdfProperty) _o;

    		int fieldSysNumber =-1; //field numbers can be zero as well. Hence set to a negative value

    		if (assocMdfClassProp.getAnnotation("http://www.temenos.com/t24", "i") != null)
    		{
    			MdfAnnotationPropertyImpl fieldAttributeProperty = (MdfAnnotationPropertyImpl) assocMdfClassProp.getAnnotation("http://www.temenos.com/t24", "i").getProperty("sysNumber");

    			if (fieldAttributeProperty!=null && fieldAttributeProperty.getValue()!=null && !fieldAttributeProperty.getValue().equals("0.0"))
    				fieldSysNumber = (int) Float.parseFloat(fieldAttributeProperty.getValue());
    		}

    		if (assocMdfClassProp instanceof MdfAttribute) {
    			final MdfAttribute childAttribute = (MdfAttribute) assocMdfClassProp;

        		if (
    				(fieldSysNumber == p_appFieldPos) ||
    				(
    					! isAppFieldNameNullOrEmpty &&
    					p_appFieldName.replace('.', '_').equals(childAttribute.getName())
    				)
        		)
        		{
        			if (isAppFieldNameNullOrEmpty && (p_appFieldPos != 0) && ! assocMdfClass.getQualifiedName().getDomain().equals(p_mdfAssociation.getQualifiedName().getDomain()))
        			{
        				return null; // // field position given but different domain
        			}

        			/*
        			 * Simon: This looks wrong - shouldn't we actually be returning childAttribute here ?!
        			 * > 04/09/2014: Sudar: yes, it was intended to return the parent association in this case (but can't remember quite why)
        			 */
        			// result = p_mdfAssociation;

        			if (DEBUG_ENABLED && (p_mdfAssociation != childAttribute))
        			{
        				System.out.println(
        					"STH: findApplicationField():\n" +
        				    "- inputs:\n" +
        				    "  - p_mdfAssociation=" + p_mdfAssociation + '\n' +
        				    "  - p_appFieldName=" + StringUtils.quoteIfString(p_appFieldName) + '\n' +
        				    "  - p_appFieldPos=" + p_appFieldPos + "\n" +
        				    "- output:\n" +
        					"  - sudar: " + p_mdfAssociation + '\n' +
        					"  * simon: " + childAttribute + '\n'
        				);
        			}

        			result = childAttribute;
        		}
    		} // (assocMdfClassProp instanceof MdfAttribute)

    		else if (assocMdfClassProp instanceof MdfAssociation)
    		{
    			final MdfAssociation childAssociation = (MdfAssociation) assocMdfClassProp;

        		if (isAppFieldNameNullOrEmpty && (p_appFieldPos > 0))
    			{
    				//this is field position handling
    				MdfAnnotation annotationList = childAssociation.getAnnotation("http://www.temenos.com/t24", "i");
    				int fieldAssociationPropertyValue = 0;

    				if(annotationList !=null)
    				{
    					MdfAnnotationPropertyImpl fieldAssociationProperty = (MdfAnnotationPropertyImpl) annotationList.getProperty("sysNumber");

    					if(fieldAssociationProperty!= null && fieldAssociationProperty.getValue()!=null && !fieldAssociationProperty.getValue().equals("0.0"))
        					fieldAssociationPropertyValue = (int)Float.parseFloat(fieldAssociationProperty.getValue());
        			}

    				if (fieldAssociationPropertyValue == p_appFieldPos)
    				{
    					if (childAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY)
        				{
        					MdfProperty multiLanguageProperty = findApplicationField(childAssociation, p_appFieldName, p_appFieldPos);
        					if(multiLanguageProperty != null )
        					{
        						result = multiLanguageProperty;
        					}
        				}
        				else
        				{
	        			  //may need to work out sub values
	         				result = (MdfAssociation) _o;
	         				//for non aa applications
	         				if(result.getParentClass().equals(childAssociation.getParentClass()))
	         				{
	         					result = childAssociation;
	         				}
	        				//may be a multivalued field
         				}
    				}
    			}

    			else if ((! isAppFieldNameNullOrEmpty) && p_appFieldName.replace('.', '_').equals(childAssociation.getName() ))
    			{
    				//handle appfieldname case
    				if(childAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY)
    				{
    					MdfProperty multiLanguageProperty = findApplicationField(childAssociation, p_appFieldName, p_appFieldPos);
    					if(multiLanguageProperty !=null )
    					{
    						result = multiLanguageProperty;
    					}
    				}
    				else
    				{

        				//			p_level = p_level+1;
        			  //may need to work out sub values
         				result = (MdfAssociation) _o;
         				//for non aa applications
         				if (result.getParentClass().equals(childAssociation.getParentClass()))
         				{
         					result = childAssociation;
         				}
        				//may be a multivalued field
					}
        		}

    			else if (assocMdfClass.getQualifiedName().getDomain().equals(p_mdfAssociation.getQualifiedName().getDomain()))
				{
					//if still in the same domain and it cud be a subvalued field in a non aa application. Here appfieldname is not null (EB.CONTRACT.BALANCES.BALANCE and AA.FIND.ARRANGEMENT.AD)
					if(childAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY)
    				{
    					MdfProperty subValuedProperty = findApplicationField(childAssociation, p_appFieldName, p_appFieldPos);
    					if(subValuedProperty != null )
    					{
    						result = subValuedProperty;
    					}
    				}
				}
    		} // (assocMdfClassProp instanceof MdfAssociation)
    	} // while(assocMdfClassPropIter.hasNext() && (result == null))

    	return result;
    } // findApplicationField()

    private QuestionWrapperPair addGenericActionWidgetQuestionToResultsTable(TableWrapperPair p_resultsTableWrapperPair, PropertyGroupWrapper p_displayResultGroup, PropertyWrapper p_drilldownActionItem) {
        AssertionUtils.requireNonNull(p_resultsTableWrapperPair, "p_resultsTableWrapperPair");
        AssertionUtils.requireNonNull(p_displayResultGroup, "p_displayResultGroup");
        AssertionUtils.requireNonNull(p_drilldownActionItem, "p_drilldownActionItem");
        
        final QuestionWrapper actionQuestion = QuestionWrapper.create( getFormContext() );
        actionQuestion.setQuestionText( "$%SLANG Enquiry.DrilldownLabel [Drilldown]$" );
        actionQuestion.setMandatory( Boolean.FALSE );
        actionQuestion.setReadOnly( Boolean.FALSE );
        actionQuestion.setPostQuestionRules( Boolean.TRUE );
        actionQuestion.setPropertyKey( p_drilldownActionItem );
        actionQuestion.setDisableInput( Boolean.TRUE );
        actionQuestion.setNotApplicable( Boolean.TRUE );
        actionQuestion.setConditionExpression("$$" + m_anyDynamicDrilldownsToDisplayItem.getEntityKeyName() + "$ == 'Y'");
        p_resultsTableWrapperPair.wrapperObject.addChild( actionQuestion );
        m_autoRefreshStateSensitiveResultsScreenElems.add( actionQuestion );

        final SetValueRuleWrapper setCurrentResultGroupInstance = SetValueRuleWrapper.create( getFormContext() );
        setCurrentResultGroupInstance.setName( "Set the current SearchResult instance to be the one for the row containing this question" );
        setCurrentResultGroupInstance.setType( SetValueRuleWrapper.EType.DATA_GROUP_INSTANCE );
        setCurrentResultGroupInstance.setPropertyGroupInstanceName( p_displayResultGroup );
        setCurrentResultGroupInstance.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setCurrentResultGroupInstance.setFromPropertyNameFromEntityKey( DataStore.WorkingElementsGroup.ItemPaths.RESULTS_TABLE_SELECTOR );
        actionQuestion.addChild( setCurrentResultGroupInstance );

        final SetValueRuleWrapper setIRISOutputContextRule = SetValueRuleWrapper.create( getFormContext() );
        setIRISOutputContextRule.setName( "Set IRISOutputContext from the key of the selected Action list entry" );
        setIRISOutputContextRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setIRISOutputContextRule.setPropertyNameFromEntityKey( DataStore.LogicalScreenModel.ItemPaths.IRIS_OUTPUT_CONTEXT );
        setIRISOutputContextRule.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setIRISOutputContextRule.setFromPropertyName( p_drilldownActionItem );
        actionQuestion.addChild( setIRISOutputContextRule );

        final SetValueRuleWrapper setIRISRelationRule = SetValueRuleWrapper.create( getFormContext() );
        setIRISRelationRule.setName( "Set IRISRelation from the group value of the selected Action list entry" );
        setIRISRelationRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setIRISRelationRule.setPropertyNameFromEntityKey( DataStore.LogicalScreenModel.ItemPaths.IRIS_RELATION );
        setIRISRelationRule.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setIRISRelationRule.setFromPropertyNameFromEntityKey( p_drilldownActionItem.getEntityKeyName() + ".groupValue()" );
        actionQuestion.addChild( setIRISRelationRule );

        final ExpressionRuleWrapper exprRemoveImageId = ExpressionRuleWrapper.create( getFormContext() );
        exprRemoveImageId.setName( "Expr to remove Image Id" );
        exprRemoveImageId.setExpression(
            "function removeImageId(p_inputStr) {\n" +
            "  var tmp = p_inputStr;\n" +
            "  if (tmp.indexOf(\"^\") > 0) {\n" +
            "     tmp = tmp.substring(0, tmp.indexOf(\"^\"));\n" +
            "  }\n" +
            "\n" +
            " return tmp;\n" +
            "}\n" +
            "\n" +
            "removeImageId(\"$$" + DataStore.LogicalScreenModel.ItemPaths.IRIS_RELATION + "$\");\n"
        );
        exprRemoveImageId.setOutputPropertyFromEntityKey( DataStore.LogicalScreenModel.ItemPaths.IRIS_RELATION );
        actionQuestion.addChild(exprRemoveImageId);

        // TEMP_KLUDGE {
        
        /*
         * The label needs to be translated - meaning that we need to:
         * - create a dynamic EnquiryDesriptions list (with language codes as keys and translations as values)
         * - create a WorkingElements data item EnquiryDescription referencing that list
         * - in startup rules, set the value of that data item according to the active language.
         * 
         * Given that we're really short of time now (and in any case translations stuff needs to be revisited following
         * switch to the new enquiry results model), for now I'm going to kludge the value to be either (i) the English
         * Enquiry description (if there is one), else (ii) the T24 Enquiry name.
         */
        
        // FIXME: As above, need runtime translations
        TextTranslations breadcrumbLabel = TextTranslations.getLocalTranslations( getEdgeMapper(), m_enquiry.getDescription(), m_enquiry.getName() );
        
        // } TEMP_KLUDGE

        final BroadcastRuleWrapper broadCastRule = BroadcastRuleWrapper.create( getFormContext() );
        broadCastRule.setName( "Broadcast to the Dashboard to execute any nested component rules" );
        broadCastRule.setBroadcastMessage(
            "DRILLDOWN_FROM:$%IF " + DataStore.WorkingElementsGroup.ItemPaths.INVOKED_IN_DRILLDOWN_CONTEXT + " == 'true'$$$" +
            DataStore.LogicalScreenModel.ItemPaths.IRIS_INPUT_CONTEXT +
            "$$%ELSE$$$" +
            DataStore.WorkingElementsGroup.ItemPaths.FULL_IRIS_SEARCH_URL +
            "$$%ENDIF$|$$" +
            DataStore.WorkingElementsGroup.ItemPaths.IRIS_RELATION_ON_ENTRY +
            "$|" + breadcrumbLabel.getText()
        );
        actionQuestion.addChild( broadCastRule );

    	final RichHTMLPresentationQuestionWrapper actionPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), actionQuestion.unwrap());
    	actionPresQuestion.setDisplayText("&nbsp;");
		ResultsTableQuestionStyleOption.STANDARD.applyTo(actionPresQuestion);

		EnquiryWidgets.GENERIC_ACTION.applyTo(actionPresQuestion, new GenericActionWidget.Params("- related action -"));
        actionPresQuestion.setHideQuestion(Boolean.TRUE);
        
        if (m_allDrilldownsAreFieldSpecific)
        {
            actionPresQuestion.setQuestionStyle(EnquiryStyleStrings.NOT_DISPLAYED);
            actionPresQuestion.setAnswerStyle(EnquiryStyleStrings.NOT_DISPLAYED);
        }
        
        p_resultsTableWrapperPair.presWrapperObject.addChild( actionPresQuestion );

        return new QuestionWrapperPair(actionQuestion, actionPresQuestion);
    }

    private void addScriptsAsPresentationOnlyText(RichHTMLPresentationItemGroupWrapper p_searchResultsPresItemGroup)
    {
        AssertionUtils.requireNonNull(p_searchResultsPresItemGroup, "p_searchResultsPresItemGroup");

        final RichHTMLPresentationTextWrapper serverFileRetrievalServletInvokerScript = RichHTMLPresentationTextWrapper.create( getFormContext() );
        serverFileRetrievalServletInvokerScript.setTextType( RichHTMLPresentationTextWrapper.ETextType.SPECIFY );
        serverFileRetrievalServletInvokerScript.setText( SERVER_FILE_RETRIEVAL_SERVLET_INVOKER_JAVASCRIPT );
        p_searchResultsPresItemGroup.addChild( serverFileRetrievalServletInvokerScript );

        final RichHTMLPresentationTextWrapper enquiryPKLinksInjectorScript = RichHTMLPresentationTextWrapper.create( getFormContext() );
        enquiryPKLinksInjectorScript.setHtmltextFile( "$$PROJECTHOME$/templates/enquiryResultPKLinksInjector.tpl" );
        p_searchResultsPresItemGroup.addChild( enquiryPKLinksInjectorScript );
    }

    private void addPerSearchParamFilterOperandsPreprocessingRules(
        ContainerRuleWrapper p_preprocessFilterOperandsContainerRule,
        PropertyGroupWrapper p_searchFiltersGroup,
        String p_searchParamGroupName,
        PropertyWrapper p_scratchItem,
        StringBuilder p_resultScreenHideShowStateItemPathList
	) {
        AssertionUtils.requireNonNull( p_preprocessFilterOperandsContainerRule, "p_preprocessFilterOperandsContainerRule" );
        AssertionUtils.requireNonNull( p_searchFiltersGroup, "p_searchFiltersGroup" );
        AssertionUtils.requireNonNullAndNonEmpty( p_searchParamGroupName, "p_searchParamGroupName" );
        AssertionUtils.requireNonNull( p_scratchItem, "p_scratchItem" );

        final String searchParamGroupPath = p_searchFiltersGroup.getPropertyGroupKey() + '.' + p_searchParamGroupName + "[1]";
		final String comparisonOperatorItemPath = searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.COMPARISON_OPERATOR_ITEM_NAME;
        final String operand1ItemPath = searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.OPERAND1_ITEM_NAME;
        final String operand2ItemPath = searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.OPERAND2_ITEM_NAME;
		final String resultsScreenHideShowStateItemPath = searchParamGroupPath + '.' + DataStore.SearchParamFilterGroupTemplate.RESULTS_SCREEN_HIDE_SHOW_STATE;

        if ( p_resultScreenHideShowStateItemPathList != null )
        {
            if ( p_resultScreenHideShowStateItemPathList.length() > 0 )
                p_resultScreenHideShowStateItemPathList.append( ',' );
            p_resultScreenHideShowStateItemPathList.append( resultsScreenHideShowStateItemPath );
        }

        final ContainerRuleWrapper perParamRulesContainer = ContainerRuleWrapper.create( getFormContext(), p_searchParamGroupName );
        p_preprocessFilterOperandsContainerRule.addTrueRule( perParamRulesContainer.unwrap() );

        final SetValueRuleWrapper setResultsScreenHidShowStateToHideRule = SetValueRuleWrapper.create( getFormContext() );
		setResultsScreenHidShowStateToHideRule.setName("Set " + DataStore.SearchParamFilterGroupTemplate.RESULTS_SCREEN_HIDE_SHOW_STATE + " to " + Lists.ShowOrHide.Keys.HIDE);
        setResultsScreenHidShowStateToHideRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setResultsScreenHidShowStateToHideRule.setPropertyNameFromEntityKey( resultsScreenHideShowStateItemPath );
        setResultsScreenHidShowStateToHideRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
        setResultsScreenHidShowStateToHideRule.setFromValue( Lists.ShowOrHide.Keys.HIDE );
        setResultsScreenHidShowStateToHideRule.setFromValueList( Lists.ShowOrHide.Keys.HIDE );
        perParamRulesContainer.addTrueRule( setResultsScreenHidShowStateToHideRule.unwrap() );

        final EvaluateRuleWrapper isRangeComparisonOperatorEvalRule = EvaluateRuleWrapper.create( getFormContext() );
        isRangeComparisonOperatorEvalRule.setName( "If a range-based operator was chosen for " + p_searchParamGroupName );
        isRangeComparisonOperatorEvalRule.setExpression( "$$" + comparisonOperatorItemPath + ".groupValue()$ == 'RANGE'" );
        perParamRulesContainer.addTrueRule( isRangeComparisonOperatorEvalRule.unwrap() );

        final EvaluateRuleWrapper isEitherOperandNonEmptyEvalRule = EvaluateRuleWrapper.create( getFormContext() );
        isEitherOperandNonEmptyEvalRule.setName( "If either operand is non-empty" );
        isEitherOperandNonEmptyEvalRule.setExpression( "$$" + operand1ItemPath + "$ != '' OR $$" + operand2ItemPath + "$ != ''" );
        isRangeComparisonOperatorEvalRule.addTrueRule( isEitherOperandNonEmptyEvalRule.unwrap() );

        final SetValueRuleWrapper setResultsScreenHideShowStateToShowRule = SetValueRuleWrapper.create( getFormContext() );
		setResultsScreenHideShowStateToShowRule.setName("Set " + DataStore.SearchParamFilterGroupTemplate.RESULTS_SCREEN_HIDE_SHOW_STATE + " to " + Lists.ShowOrHide.Keys.SHOW);
        setResultsScreenHideShowStateToShowRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setResultsScreenHideShowStateToShowRule.setPropertyNameFromEntityKey( resultsScreenHideShowStateItemPath );
        setResultsScreenHideShowStateToShowRule.setFromType( SetValueRuleWrapper.EFromType.VALUE );
        setResultsScreenHideShowStateToShowRule.setFromValue( Lists.ShowOrHide.Keys.SHOW );
        setResultsScreenHideShowStateToShowRule.setFromValueList( Lists.ShowOrHide.Keys.SHOW );
        isEitherOperandNonEmptyEvalRule.addTrueRule( setResultsScreenHideShowStateToShowRule.unwrap() );

        final EvaluateRuleWrapper areRangeOperatorsMisorderedEvalRule = EvaluateRuleWrapper.create( getFormContext() );
        areRangeOperatorsMisorderedEvalRule.setName( "Are the range operands for " + p_searchParamGroupName + " mis-ordered" );
        areRangeOperatorsMisorderedEvalRule.setExpression( "$$" + operand2ItemPath + "$ < $$" + operand1ItemPath + "$" );
        isEitherOperandNonEmptyEvalRule.addTrueRule( areRangeOperatorsMisorderedEvalRule.unwrap() );

        final EvaluateRuleWrapper isOperand1NonEmptyEvalRule = EvaluateRuleWrapper.create( getFormContext() );
        isOperand1NonEmptyEvalRule.setName( "Is " + DataStore.SearchParamFilterGroupTemplate.OPERAND1_ITEM_NAME + " non-empty" );
        isOperand1NonEmptyEvalRule.setExpression( "$$" + operand1ItemPath + "$ != ''" );
		isOperand1NonEmptyEvalRule.unwrap().addTrueRule(setResultsScreenHideShowStateToShowRule.unwrap(), true /* p_linkedObject */);
        isRangeComparisonOperatorEvalRule.addFalseRule( isOperand1NonEmptyEvalRule.unwrap() );

		final ContainerRuleWrapper swapOperandsContainerRule = ContainerRuleWrapper.create(getFormContext(), "Swap the range operands for " + p_searchParamGroupName);
        areRangeOperatorsMisorderedEvalRule.addTrueRule( swapOperandsContainerRule.unwrap() );

        final SetValueRuleWrapper setScratchItemFromOperand1ItemRule = SetValueRuleWrapper.create( getFormContext() );
        setScratchItemFromOperand1ItemRule.setName( "Set Scratch from Operand1" );
        setScratchItemFromOperand1ItemRule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setScratchItemFromOperand1ItemRule.setPropertyName( p_scratchItem );
        setScratchItemFromOperand1ItemRule.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setScratchItemFromOperand1ItemRule.setFromPropertyNameFromEntityKey( operand1ItemPath );
        swapOperandsContainerRule.addTrueRule( setScratchItemFromOperand1ItemRule.unwrap() );

        final SetValueRuleWrapper setOperand1ItemFromOperand2Rule = SetValueRuleWrapper.create( getFormContext() );
        setOperand1ItemFromOperand2Rule.setName( "Set Operand1 from Operand2" );
        setOperand1ItemFromOperand2Rule.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setOperand1ItemFromOperand2Rule.setPropertyNameFromEntityKey( operand1ItemPath );
        setOperand1ItemFromOperand2Rule.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setOperand1ItemFromOperand2Rule.setFromPropertyNameFromEntityKey( operand2ItemPath );
        swapOperandsContainerRule.addTrueRule( setOperand1ItemFromOperand2Rule.unwrap() );

        final SetValueRuleWrapper setOperand2ItemFromScratchItem = SetValueRuleWrapper.create( getFormContext() );
        setOperand2ItemFromScratchItem.setName( "Set Operand2 from Scratch" );
        setOperand2ItemFromScratchItem.setType( SetValueRuleWrapper.EType.DATA_ITEM );
        setOperand2ItemFromScratchItem.setPropertyNameFromEntityKey( operand2ItemPath );
        setOperand2ItemFromScratchItem.setFromType( SetValueRuleWrapper.EFromType.DATA_ITEM );
        setOperand2ItemFromScratchItem.setFromPropertyName( p_scratchItem );
        swapOperandsContainerRule.addTrueRule( setOperand2ItemFromScratchItem.unwrap() );
    }

    // NEW_METHODS_FOR_FLATTENED_RESULTS_TABLE_PATTERN {

    private void addResultsTablesAndSupportingDataStoreElems(
        final PhaseWrapper p_searchResultsPhase,
        final IrisResultGroupSpec p_irisResultGroupSpec,
        final ItemGroupWrapper p_searchResultsItemGroup,
        final RichHTMLPresentationFormatBreakWrapper p_searchResultsSection,
        final PropertyGroupWrapper p_linksGroup,
        final PropertyGroup p_searchResponseGroup,
        final PropertyWrapper p_searchOutcomeItem,
        final PropertyWrapper p_topLevelLinksActionItem
    ) {
        AssertionUtils.requireNonNull(p_searchResultsPhase, "p_searchResultsPhase");
        AssertionUtils.requireNonNull(p_irisResultGroupSpec, "p_irisResultGroupSpec");
        AssertionUtils.requireNonNull(p_searchResultsItemGroup, "p_searchResultsItemGroup");
        AssertionUtils.requireNonNull(p_searchResultsSection, "p_searchResultsPresItemGroup");
        AssertionUtils.requireNonNull(p_linksGroup, "p_linksGroup");
        AssertionUtils.requireNonNull(p_searchResponseGroup, "p_searchResponseGroup");
        AssertionUtils.requireNonNull(p_topLevelLinksActionItem, "p_topLevelLinksActionItem");

        final DisplayResultGroupSpecCollection displayResultGroupCollection = p_irisResultGroupSpec.createDisplayResultGroupSpecs();

        m_irisResultGroupWrapper = p_irisResultGroupSpec.generateDataGroup(getFormContext(), m_enquiryFieldsDigest);

        if (m_enquiryAttrsDigest.isEnquiryWithDrilldowns())
            addActionListTypedActionDataItemTo(m_irisResultGroupWrapper);

        final PropertyGroupWrapper irisResultLinksGroup = p_linksGroup.clone();
        irisResultLinksGroup.unwrap().setName(p_linksGroup.getName(), true); // Necessary to get rid of "- Copy" suffix !
        m_irisResultGroupWrapper.addChild(irisResultLinksGroup);

        p_searchResponseGroup.addChild(m_irisResultGroupWrapper.unwrap());

        final ContainerRuleWrapper resetResultGroupInstancesContainerRule = ContainerRuleWrapper.create(getFormContext(), "Reset current instance pointers for all result groups");
        p_searchResultsPhase.addPrePhaseRule(resetResultGroupInstancesContainerRule);
        
        final ResetDataRuleWrapper resetIrisResultGroupInstanceRule = ResetDataRuleWrapper.create(getFormContext());
        resetIrisResultGroupInstanceRule.setName("Reset the current instance pointer for the " + m_irisResultGroupWrapper.getName() + " group to point at the 1st instance");
        resetIrisResultGroupInstanceRule.setResetPropertyGroupInstance(m_irisResultGroupWrapper);
        resetResultGroupInstancesContainerRule.addTrueRule(resetIrisResultGroupInstanceRule);

        final Iterator<DisplayResultGroupSpec> displayResultGroupSpecIter = displayResultGroupCollection.getDisplayOrderedResultGroupSpecIterator();
        final ArrayList<PropertyGroupWrapper> headerDisplayResultGroupList = new ArrayList<PropertyGroupWrapper>();

        DisplayResultsTableWrapperPair
            displayOnceTableWrapperPair = null,
            mainEnqResultsTableWrapperPair = null,
            displayEndResultsTableWrapperPair = null;

        final boolean
            areResultsToolbarElemsPresent = (m_enquiryResultsToolbarElems != null),
            areHeaderTablesPresent = (m_staticResultsScreenHeadersModel != null) || displayResultGroupCollection.includesHeaderResultsTables(),
            isPreFirstHeaderTableLineSpacingRequiredRequired = (areResultsToolbarElemsPresent && areHeaderTablesPresent),
            isPreFirstNonHeaderTableLineSpacingRequired = (areResultsToolbarElemsPresent || areHeaderTablesPresent);

        final String toolbarApplicabilityCondition = areResultsToolbarElemsPresent ?
            genToolbarApplicabilityCondition(m_enquiryResultsToolbarElems, m_breakChangeControlGroupElems, p_searchOutcomeItem, p_topLevelLinksActionItem) : null;
            
        if (m_staticResultsScreenHeaderControlElems != null)
        {
            final RichHTMLPresentationSpacingWrapper lineSpacingBeforeFirstStaticResultsTable = m_staticResultsScreenHeaderControlElems.lineSpacingBeforeFirstStaticResultsTable;

            if (areResultsToolbarElemsPresent)
            {
                if (toolbarApplicabilityCondition == null)
                    lineSpacingBeforeFirstStaticResultsTable.setHideField(Boolean.FALSE);
                
                else
                {
                    lineSpacingBeforeFirstStaticResultsTable.setHideField(Boolean.TRUE);
                    lineSpacingBeforeFirstStaticResultsTable.setConditionExpression(toolbarApplicabilityCondition);
                }
            }

            else
            {
                final RichHTMLPresentationSpacing lineSpacingUnwrapped = lineSpacingBeforeFirstStaticResultsTable.unwrap();
                lineSpacingUnwrapped.getParent().removeChild(lineSpacingUnwrapped);
            }
        }

        RichHTMLPresentationSpacingWrapper
            preFirstHeaderTableLineSpacing = null,
            preNonHeaderResultTableLineSpacing = null;

        boolean anyResultsTableIncludesCollapsibleColumns = false;

        while(displayResultGroupSpecIter.hasNext())
        {
            final DisplayResultGroupSpec displayResultGroupSpec = displayResultGroupSpecIter.next();

            final PropertyGroupWrapper displayResultGroup = displayResultGroupSpec.generateDataGroup(getFormContext());
            p_searchResponseGroup.addChild(displayResultGroup.unwrap());

            final ResetDataRuleWrapper resetDisplayResultGroupInstanceRule = ResetDataRuleWrapper.create(getFormContext());
            resetDisplayResultGroupInstanceRule.setName("Reset the current instance pointer for the " + displayResultGroup.getName() + " group to point at the 1st instance");
            resetDisplayResultGroupInstanceRule.setResetPropertyGroupInstance(displayResultGroup);
            resetResultGroupInstancesContainerRule.addTrueRule(resetDisplayResultGroupInstanceRule);

            final DisplayResultsTableWrapperPair displayResultsTableWrapperPair = displayResultGroupSpec.generateResultsTable(getFormContext(), m_enquiryAttrsDigest);

            anyResultsTableIncludesCollapsibleColumns |= displayResultsTableWrapperPair.includesColumnQuestionsMarkedAsCollapsible;

            final ResultsTableType targetResultsTableType = displayResultGroupSpec.getTargetResultsTableType();

            if (m_enquiryAttrsDigest.isEnquiryWithDrilldowns())
            {
                final String drilldownColumnVisibilityCondition = "$$" + m_anyDynamicDrilldownsToDisplayItem.getEntityKeyName() + "$ != ''";

                if (targetResultsTableType.offersEnquiryDrilldowns() || (targetResultsTableType.isDisplayEndResults() && (mainEnqResultsTableWrapperPair == null)))
                {
                    final PropertyWrapper drilldownActionItem = addActionListTypedActionDataItemTo(displayResultGroup);
                    addGenericActionWidgetQuestionToResultsTable(displayResultsTableWrapperPair, displayResultGroup, drilldownActionItem);
                }

                else if (targetResultsTableType.isClubbableColumnSetMember())
                {
                    final QuestionWrapper dummyDrilldownActionQuestion = QuestionWrapper.create(getFormContext());
                    dummyDrilldownActionQuestion.setQuestionText("[Drilldown DUMMY]");
                    dummyDrilldownActionQuestion.setMandatory(Boolean.FALSE);
                    dummyDrilldownActionQuestion.setReadOnly(Boolean.TRUE);
                    dummyDrilldownActionQuestion.setPropertyKey(displayResultGroupSpec.getEmptyColumnFillerItem());
                    dummyDrilldownActionQuestion.setNotApplicable(Boolean.TRUE);
                    dummyDrilldownActionQuestion.setConditionExpression(drilldownColumnVisibilityCondition);

                    final RichHTMLPresentationQuestionWrapper dummyDrilldownActionPresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), dummyDrilldownActionQuestion.unwrap());
                    ResultsTableQuestionStyleOption.STANDARD.applyTo(dummyDrilldownActionPresQuestion);
                    
                    if (m_allDrilldownsAreFieldSpecific)
                    {
                        dummyDrilldownActionPresQuestion.setQuestionStyle(EnquiryStyleStrings.NOT_DISPLAYED);
                        dummyDrilldownActionPresQuestion.setAnswerStyle(EnquiryStyleStrings.NOT_DISPLAYED);
                    }

                    displayResultsTableWrapperPair.wrapperObject.addChild(dummyDrilldownActionQuestion);
                    displayResultsTableWrapperPair.presWrapperObject.addChild(dummyDrilldownActionPresQuestion);
                }
            }

            switch(targetResultsTableType)
            {
                case ENQUIRY_HEADER_RESULTS:
                    headerDisplayResultGroupList.add(displayResultGroup);
                    break;

                case DISPLAY_ONCE_RESULTS:
                    m_displayOnceDisplayResultGroup = displayResultGroup;
                    displayOnceTableWrapperPair = displayResultsTableWrapperPair;
                    break;

                case MAIN_ENQUIRY_RESULTS:
                    m_mainDisplayResultGroup = displayResultGroup;
                    mainEnqResultsTableWrapperPair = displayResultsTableWrapperPair;
                    break;

                case FOOTER_RESULTS:
                    m_footerDisplayResultGroup = displayResultGroup;
                    break;

                case PAGE_THROW_RESULTS:
                    m_pageThrowDisplayResultGroup = displayResultGroup;
                    break;

                case DISPLAY_END_RESULTS:
                    m_displayEndDisplayResultGroup = displayResultGroup;
                    displayEndResultsTableWrapperPair = displayResultsTableWrapperPair;
                    break;
            } // switch(targetResultsTableType)

            RichHTMLPresentationSpacingWrapper preTableLineSpacing = null;
            boolean doApplyToolbarApplicabilityConditionToPreTableLineSpacing = false;

            if (targetResultsTableType.isEnquiryHeaderResults())
            {
                if (isPreFirstHeaderTableLineSpacingRequiredRequired && (preFirstHeaderTableLineSpacing == null))
                {
                    preTableLineSpacing = preFirstHeaderTableLineSpacing = RichHTMLPresentationSpacingWrapper.create(getFormContext());
                    doApplyToolbarApplicabilityConditionToPreTableLineSpacing = (toolbarApplicabilityCondition != null);
                }
            }

            else if (isPreFirstNonHeaderTableLineSpacingRequired && (preNonHeaderResultTableLineSpacing == null))
            {
                preTableLineSpacing = preNonHeaderResultTableLineSpacing = RichHTMLPresentationSpacingWrapper.create(getFormContext());
                doApplyToolbarApplicabilityConditionToPreTableLineSpacing = (toolbarApplicabilityCondition != null) && ! areHeaderTablesPresent;
            }

            if (preTableLineSpacing != null)
            {
                p_searchResultsSection.addChild(preTableLineSpacing);

                if (doApplyToolbarApplicabilityConditionToPreTableLineSpacing)
                {
                    preTableLineSpacing.setHideField(Boolean.TRUE);
                    preTableLineSpacing.setConditionExpression(toolbarApplicabilityCondition);
                }
            }

            if (targetResultsTableType == ResultsTableType.MAIN_ENQUIRY_RESULTS)
            {
                addGraphElemsWhereDefined(p_searchResultsSection);
            }
            
            p_searchResultsItemGroup.addChild(displayResultsTableWrapperPair.wrapperObject);
            p_searchResultsSection.addChild(displayResultsTableWrapperPair.presWrapperObject.unwrap());
        } // while(displayResultGroupSpecIter.hasNext())

        m_headerDisplayResultGroups = new PropertyGroupWrapper[headerDisplayResultGroupList.size()];
        headerDisplayResultGroupList.toArray(m_headerDisplayResultGroups);

        if (mainEnqResultsTableWrapperPair != null)
        {
            // Create the "parameters" object for the SearchResultsTableWidget (EnquiryWidgets.SEARCH_RESULT_TABLE) that we'll eventually be "applying" to mainEnqResultsTableWrapperPair.presWrapperObject...
            final SearchResultsTableWidget.Params mainResultsTableWidgetParams = new SearchResultsTableWidget.Params();

            // Establish our default position regarding whether or not table column headers need to be hidden on mainEnqResultsTableWrapperPair.presWrapperObject...
            boolean doHideColumnHeadersOnMainEnqResultsTable = m_enquiryAttrsDigest.areResultsTableColumnHeadersSuppressed();

            if (m_enquiryAttrsDigest.isTreeEnquiry())
            {
                m_treeEnquirySupportElems = new TreeEnquirySupportElems(this, m_mainDisplayResultGroup);

                mainResultsTableWidgetParams.setIsTreeEnquiry(true);
                mainResultsTableWidgetParams.setGroupingKeyItemForJQueryDataTable(m_treeEnquirySupportElems.breakChangeValueItemForJQueryDataTable);

                final QuestionWrapperPair breakChangeValueQuestionWrapperPair = m_treeEnquirySupportElems.breakChangeValueQuestionForJQueryDataTable;

                mainEnqResultsTableWrapperPair.wrapperObject.unwrap().addChild(breakChangeValueQuestionWrapperPair.wrapperObject.unwrap(), 0);
                mainEnqResultsTableWrapperPair.presWrapperObject.unwrap().addChild(breakChangeValueQuestionWrapperPair.presWrapperObject.unwrap(), 0);

                final QuestionWrapperPair dummyColumnQuestion = m_treeEnquirySupportElems.dummyColumnQuestionForJQueryDataTable;

                mainEnqResultsTableWrapperPair.wrapperObject.unwrap().addChild(dummyColumnQuestion.wrapperObject.unwrap(), 1);
                mainEnqResultsTableWrapperPair.presWrapperObject.unwrap().addChild(dummyColumnQuestion.presWrapperObject.unwrap(), 1);
            }

            else if (m_enquiryAttrsDigest.areResultsTableColumnHeadersSuppressed())
            {
                ResultsTableColumnHeaderDisplayOption.HIDE_TABLE_COLUMN_HEADERS.applyTo(mainEnqResultsTableWrapperPair.presWrapperObject);
            }

            else if (displayOnceTableWrapperPair != null)
            {
                // displayOncePresTable needs to 'commandeer' column titles from mainEnqResultsPresTable

                final List<RichHTMLPresentationQuestion>
                    displayOncePresTableQuestions = new ArrayList<RichHTMLPresentationQuestion>(),
                    mainEnqResultsPresTableQuestions = new ArrayList<RichHTMLPresentationQuestion>();

                displayOnceTableWrapperPair.presWrapperObject.unwrap().getChildrenOfType(RichHTMLPresentationQuestion.class, displayOncePresTableQuestions, false /* p_recursive */);
                mainEnqResultsTableWrapperPair.presWrapperObject.unwrap().getChildrenOfType(RichHTMLPresentationQuestion.class, mainEnqResultsPresTableQuestions, false /* p_recursive */);

                final int iStop = Math.min(displayOncePresTableQuestions.size(), mainEnqResultsPresTableQuestions.size());

                for (int i = 0; i < iStop; ++i)
                {
                    final RichHTMLPresentationQuestion
                        displayOncePresTableQuestion = displayOncePresTableQuestions.get(i),
                        mainEnqResultsPresTableQuestion = mainEnqResultsPresTableQuestions.get(i);

                    final String displayTextAttrName = displayOncePresTableQuestion.getDisplayTextAttribute();

                    displayOncePresTableQuestion.setAttribute(displayTextAttrName, mainEnqResultsPresTableQuestion.getAttribute(displayTextAttrName));
                    mainEnqResultsPresTableQuestion.setAttribute(displayTextAttrName, null);
                }

                ResultsTableColumnHeaderDisplayOption.SHOW_TABLE_COLUMN_HEADERS.applyTo(displayOnceTableWrapperPair.presWrapperObject);
                doHideColumnHeadersOnMainEnqResultsTable = true;
            }

            // Complete the configuration of mainResultsTableWidgetParams and "apply" the widget to mainEnqResultsTableWrapperPair.presWrapperObject...

            if (anyResultsTableIncludesCollapsibleColumns)
                mainResultsTableWidgetParams.setHasCollapsibleColumns(true);

            if (displayEndResultsTableWrapperPair != null)
                mainResultsTableWidgetParams.setDisplayEndResultsTable(displayEndResultsTableWrapperPair);

            if (doHideColumnHeadersOnMainEnqResultsTable)
                ResultsTableColumnHeaderDisplayOption.HIDE_TABLE_COLUMN_HEADERS.applyTo(mainEnqResultsTableWrapperPair.presWrapperObject);

            EnquiryWidgets.SEARCH_RESULT_TABLE.applyTo(mainEnqResultsTableWrapperPair.presWrapperObject, mainResultsTableWidgetParams);

            // Finally, determine the appropriate ResultsTablePaginationOption and "apply" to mainEnqResultsTableWrapperPair.presWrapperObject...

            if (! m_enquiryAttrsDigest.isResultsTablePaginationRequiredFor(mainEnqResultsTableWrapperPair, m_irisResultGroupWrapper))
                ResultsTablePaginationOption.ALL_ROWS_ON_ONE_PAGE.applyTo(mainEnqResultsTableWrapperPair.presWrapperObject);

            else
            {
                final Integer numMainResultsTableRowsPerPage = m_enquiry.getEndLine(); // Yup - the attribute is misnamed in Enquiry DSL (and so the Enquiry model object it defines)

                final int numRowsPerPage = ((numMainResultsTableRowsPerPage == null) || numMainResultsTableRowsPerPage.intValue() <= 0) ?
                    DEFAULT_NO_OF_ROWS_PER_PAGE : numMainResultsTableRowsPerPage.intValue();

                ResultsTablePaginationOption.AUTOMATIC_PAGING.applyTo(mainEnqResultsTableWrapperPair.presWrapperObject, numRowsPerPage);
            }
        } // if (mainEnqResultsTableWrapperPair != null)
    } // addNewResultsTablesAndSupportingDataStoreElems()

    private void addGraphElemsWhereDefined(final RichHTMLPresentationFormatBreakWrapper p_searchResultsSection)
    {
        final Graph graph = m_enquiry.getGraph();
        
        if (graph == null)
            return;
        
        final DSLGraphType graphType = DSLGraphType.getDSLGraphType(graph);
        
        if (graphType == null)
        {
            LOGGER.warn("Unrecognized graph type: {}", graphType);
            return;
        }
        
        final RichHTMLPresentationChartWrapper presChartWrapper = RichHTMLPresentationChartWrapper.create(getFormContext());
                
        applyDefaultSettingsTo(presChartWrapper);
        graphType.applyTypeSpecificSettingsTo(presChartWrapper);

        final EList<Label> labels = graph.getLabels();
        final Label titleLabel = (labels == null) || labels.isEmpty() ? null : labels.get(0);
        
        // FIXME: how to handle at runtime?
        TextTranslations titleLabelTranslations = TextTranslations.getLocalTranslations( getEdgeMapper(), (titleLabel != null) ? titleLabel.getDescription() : null, null );
                
        if (titleLabelTranslations.getText() != null)
            presChartWrapper.setTitle(titleLabelTranslations.getText());
        
        final Dimension graphDims = graph.getDimension();
        
        if (graphDims != null)
        {
            presChartWrapper.setWidth(graphDims.getWidth());
            presChartWrapper.setHeight(graphDims.getHeight());
        }

        presChartWrapper.setDisplayKey(false);
        presChartWrapper.setTitleSize(24);
        presChartWrapper.setShowHint(true);

        final Axis
            xAxis = graph.getXAxis(),
            yAxis = graph.getYAxis();
        
        if (xAxis != null)
        {
            //? What about label for x axis
            presChartWrapper.setXaxis(findDisplayResultGroupItemForAxisField(xAxis, m_mainDisplayResultGroup));
        }
        
        if (yAxis != null)
        {
            // FIXME: Runtime translation?
            TextTranslations labelTranslationsForAxis = getLabelTranslationsForAxis(yAxis);
            String ySeriesName = labelTranslationsForAxis.getText();
            
            if (ySeriesName != null)
                presChartWrapper.setYseriesName1(ySeriesName);
            
            presChartWrapper.setYaxis1(findDisplayResultGroupItemForAxisField(yAxis, m_mainDisplayResultGroup));
        }

        p_searchResultsSection.addChild(presChartWrapper);
    } // addGraphElemsWhereDefined()
    
    private static void applyDefaultSettingsTo(RichHTMLPresentationChartWrapper p_presChartWrapper)
    {
        p_presChartWrapper.setKeyPosition(RichHTMLPresentationChartWrapper.EKeyPosition.LEFT);
        p_presChartWrapper.setTitleSize(24);
        p_presChartWrapper.setShowHint(true);
        p_presChartWrapper.setColor1("#236293");
        p_presChartWrapper.setColor2("#338CD3");
        p_presChartWrapper.setColor3("#FF9322");
        p_presChartWrapper.setColor4("#FFDFBD");
        p_presChartWrapper.setColor5("#7ED0E0");
        p_presChartWrapper.setColor6("#344D60");
        p_presChartWrapper.setColor7("#E9F6F8");
        p_presChartWrapper.setColor8("#6B6B6B");
        p_presChartWrapper.setColor9("#A8E97C");
        p_presChartWrapper.setColor10("#95A5A6");
        p_presChartWrapper.setColor11("#FAE25A");
        p_presChartWrapper.setColor12("#A69AB2");
        p_presChartWrapper.setColor13("#FFFFCC");
        p_presChartWrapper.setColor14("#EDF1F2");
        p_presChartWrapper.setColor15("#D7D7D7");
        p_presChartWrapper.setColor16("#27AE60");
        p_presChartWrapper.setColor17("#86100A");
        p_presChartWrapper.setColor18("#006633");
        p_presChartWrapper.setColor19("#16A085");
        p_presChartWrapper.setRuntimeId("CHT_2E33E9E9FF3402A115290");
        p_presChartWrapper.setAttribute("DisplayType", "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|HighCharts");
        p_presChartWrapper.setAttribute("DisplayTypeALLOW_POINT_SELECT", "N");
        p_presChartWrapper.setAttribute("DisplayTypeCHART_BG_COLOR_1", "transparent");
        p_presChartWrapper.setAttribute("DisplayTypeCHART_BG_COLOR_2", "transparent");
        p_presChartWrapper.setAttribute("DisplayTypeCHART_FONT_COLOR", "teal");
        p_presChartWrapper.setAttribute("DisplayTypeCHART_TT_BG_COLOR", "#F2F2F2");
        p_presChartWrapper.setAttribute("DisplayTypeCHART_TT_BG_COLOR_2", "#FFFFFF");
        p_presChartWrapper.setAttribute("DisplayTypeCHART_TT_FONT_COLOR", "#a9a9a9");
        p_presChartWrapper.setAttribute("DisplayTypeCONNECTOR_COLOR", "#000000");
        p_presChartWrapper.setAttribute("DisplayTypeDECIMALPOINT", ".");
        p_presChartWrapper.setAttribute("DisplayTypeDL_BG_COLOR_1", "#ffffff");
        p_presChartWrapper.setAttribute("DisplayTypeDL_FORMAT", "abc = {y}");
        p_presChartWrapper.setAttribute("DisplayTypeENABLE_DATALABELS", "N");
        p_presChartWrapper.setAttribute("DisplayTypeEXPORTING", "N");
        p_presChartWrapper.setAttribute("DisplayTypeFORMATTER", "N");
        p_presChartWrapper.setAttribute("DisplayTypeKEYVERTICALALIGN", "Top");
        p_presChartWrapper.setAttribute("DisplayTypeL_BG_COLOR", "#ffffff");
        p_presChartWrapper.setAttribute("DisplayTypeNODATA_FONT_COLOR", "#303030");
        p_presChartWrapper.setAttribute("DisplayTypeNODATA_MESSAGE", "No data to display");
        p_presChartWrapper.setAttribute("DisplayTypeNUMERICSYMBOLS", "\"k\", \"M\"");
        p_presChartWrapper.setAttribute("DisplayTypePOLAR", "N");
        p_presChartWrapper.setAttribute("DisplayTypeSHOW_X_AXIS_GRID_LINES", "Y");
        p_presChartWrapper.setAttribute("DisplayTypeSHOW_X_LABELS", "Y");
        p_presChartWrapper.setAttribute("DisplayTypeSHOW_Y_AXIS_GRID_LINES", "Y");
        p_presChartWrapper.setAttribute("DisplayTypeSHOW_Y_LABELS", "Y");
        p_presChartWrapper.setAttribute("DisplayTypeSTACKING", "N");
        p_presChartWrapper.setAttribute("DisplayTypeS_CURSOR", "N");
        p_presChartWrapper.setAttribute("DisplayTypeTHOUSANDSSEP", ",");
        p_presChartWrapper.setAttribute("DisplayTypeTOGGLE_BTN", "Y");
        p_presChartWrapper.setAttribute("DisplayTypeTT_CROSSHAIRS", "N");
        p_presChartWrapper.setAttribute("DisplayTypeTT_FORMATTER", "Y");
        p_presChartWrapper.setAttribute("DisplayTypeTT_USEHTML", "N");
        p_presChartWrapper.setAttribute("DisplayTypeUSE_GRADIENT", "N");
        p_presChartWrapper.setAttribute("DisplayTypeUSE_GRADIENT2", "N");
    } // applyDefaultSettingsTo(RichHTMLPresentationChartWrapper)

    private TextTranslations getLabelTranslationsForAxis(Axis p_axis)
    {
        AssertionUtils.requireNonNull(p_axis, "p_axis");
        
        final String enqFieldName = p_axis.getField();
        TextTranslations result = null;
        
        if (enqFieldName != null)
        {
            final Field enqField = m_enquiryFieldsDigest.getEnquiryFieldByT24Name(enqFieldName);
            
            if (enqField != null && enqField.getLabel() != null )
                result = TextTranslations.getLocalTranslations( getEdgeMapper(), enqField.getLabel(), null );
        }
        
        if  ( result == null )
        {
            result = TextTranslations.getDefaultText( p_axis.getField() );
        }

        return result;
    } // getLabelTranslationsForAxis()
    
    private PropertyWrapper findDisplayResultGroupItemForAxisField(Axis p_axis, PropertyGroupWrapper m_displayResultGroup)
    {
        AssertionUtils.requireNonNull(p_axis, "p_axis");
        AssertionUtils.requireNonNull(m_displayResultGroup, "m_displayResultGroup");
        
        final String enqFieldName = p_axis.getField();
        PropertyWrapper result = null;
        
        if (enqFieldName != null)
        {
            final String dataItemName = MapperUtility.processT24NameToIRISName(enqFieldName);
            final Property dataItem = m_displayResultGroup.unwrap().getChildWithName(dataItemName, Property.class);
            
            if (dataItem != null)
                result = PropertyWrapper.wrap(dataItem);
        }
        
        return result;
    } // findDisplayResultGroupItemForAxisField()
    
    private ContainerRuleWrapper createRuleToPopulateNewDisplayResultGroupsFromIrisResponse(PropertyWrapper p_scratchItem)
    {
    	AssertionUtils.requireNonNull(p_scratchItem, "p_scratchItem");

        AssertionUtils.requireConditionTrue(
            ! ((m_anyDynamicDrilldownsToDisplayItem == null) && m_enquiryAttrsDigest.isEnquiryWithDrilldowns()),
            "! ((m_anyDynamicDrilldownsToDisplayItem == null) && m_enquiryAttrsDigest.isEnquiryWithDrilldowns())"
        );

        final String populateDisplayResultGroupsContainerName = (getDisplayResultsGroupCount() == 1) ?
        	"Re-populate the display result group that backs our result table from the " + m_irisResultGroupWrapper.getName() + " group" :
        	"Re-populate the display result groups that back our various result tables from the appropriate " + m_irisResultGroupWrapper.getName() + " instances";

        final ContainerRuleWrapper populateDisplayResultGroupsContainer = ContainerRuleWrapper.create(getFormContext());
   		populateDisplayResultGroupsContainer.setName(populateDisplayResultGroupsContainerName);

   		if (m_enquiryAttrsDigest.isEnquiryWithDrilldowns())
   		{
   		    final ResetDataRuleWrapper resetAnyDrilldownsFoundItemRule = ResetDataRuleWrapper.create(getFormContext());
   		    resetAnyDrilldownsFoundItemRule.setName("Reset our " + m_anyDynamicDrilldownsToDisplayItem.getName() + " item");
   		    resetAnyDrilldownsFoundItemRule.setResetProperty(m_anyDynamicDrilldownsToDisplayItem);
   		    populateDisplayResultGroupsContainer.addTrueRule(resetAnyDrilldownsFoundItemRule);
   		}

        final String irisResultStartInstanceExpr;
	    final String irisResultEndInstanceExpr;

	    if (m_breakChangeControlGroupElems != null)
        {
        	populateDisplayResultGroupsContainer.addTrueRule(createRuleToSetupBreakChangeControlItemsFromSelectedBreakChangeListValue());
			irisResultStartInstanceExpr				= "$$" + m_breakChangeControlGroupElems.irisResultGroupStartInstanceItem.getEntityKeyName() + "$";
			irisResultEndInstanceExpr				= "$$" + m_breakChangeControlGroupElems.irisResultGroupEndInstanceItem.getEntityKeyName() + "$";
        }

        else
        {
        	irisResultStartInstanceExpr				= "1";
        	irisResultEndInstanceExpr				= null;
        }

        final int numHeaderDisplayResultGroups = (m_headerDisplayResultGroups == null) ? 0 : m_headerDisplayResultGroups.length;

        for (int i = 0; i < numHeaderDisplayResultGroups; ++i)
            populateDisplayResultGroupsContainer.addTrueRule(createEnquiryDisplayResultsPopulatorRule(irisResultStartInstanceExpr, irisResultStartInstanceExpr, m_headerDisplayResultGroups[i]));

        if (m_displayOnceDisplayResultGroup != null)
            populateDisplayResultGroupsContainer.addTrueRule(createEnquiryDisplayResultsPopulatorRule("1", "1", m_displayOnceDisplayResultGroup));

        if (m_mainDisplayResultGroup != null)
            populateDisplayResultGroupsContainer.addTrueRule(createEnquiryDisplayResultsPopulatorRule(irisResultStartInstanceExpr, irisResultEndInstanceExpr, m_mainDisplayResultGroup));

        if (m_pageThrowDisplayResultGroup != null)
            populateDisplayResultGroupsContainer.addTrueRule(createEnquiryDisplayResultsPopulatorRule(irisResultEndInstanceExpr, irisResultEndInstanceExpr, m_pageThrowDisplayResultGroup));

        if (m_displayEndDisplayResultGroup != null)
        {
            final String finalIrisResultGroupInstanceExpn = "$$" + m_irisResultGroupWrapper.getEntityKeyName() + ".lastInstance()$";
            populateDisplayResultGroupsContainer.addTrueRule(createEnquiryDisplayResultsPopulatorRule(finalIrisResultGroupInstanceExpn, finalIrisResultGroupInstanceExpn, m_displayEndDisplayResultGroup));
        }

        if (m_enquiryAttrsDigest.isTreeEnquiry() && (m_treeEnquirySupportElems != null) && m_irisResultGroupWrapper.includesIrisBreakChangeItems())
	    {
	    	final IrisResultBreakChangeItem firstIrisResultBreakChangeItem = m_irisResultGroupWrapper.breakChangeItemList.get(0);

	    	final String
	    		mainDisplayResultGroupName = m_mainDisplayResultGroup.getName(),
	    		jQueryDataTableControlSubGroupName = m_treeEnquirySupportElems.jQueryDataTableControlGroup.getName(),
	    		firstBreakChangeItemName = firstIrisResultBreakChangeItem.getBreakChangeDataItem().getName(),
	    		scratchItemName = p_scratchItem.getName();

	    	// TEMPORARY_KLUDGE {

	    	/*
	    	 * Really it would be better if LanguageMapHelper's registerT24TextTranslations() method:
	    	 * - took in as an extra argument a "default" value to use in the case where there are no translations
	    	 * - took responsibility for setting the right attribute on whatever kind of wrapper object is passed in
	    	 *   - from the ENGLISH translation (if there are Translations AND the Translations contain an ENGLISH value)
	    	 *   - else if a default has been provided, from the default value
	    	 *
	    	 * BUT: LanguageMapHelper also used in Version stuff though, so anything we change for Enquiry has to be backward-compatible
	    	 * unless we (as a team) can afford to take on updating Version to use a new scheme.
	    	 *
	    	 * see Saleem's com.odcgroup.edge.t24.generation.util.TextTranslations
	    	 *
	    	 * 23/10/2014 STH
	    	 */
	    	final PropertyWrapper displayCorrespondentForFirstIrisBreakChangeItem = PropertyWrapper.wrap(m_mainDisplayResultGroup.unwrap().getChildWithName(firstBreakChangeItemName, Property.class));
	    	final Field enquiryBreakChangeField = firstIrisResultBreakChangeItem.getIrisResultItemSpec().getEnquiryField();
            
	    	final TextTranslations firstBreakChangeFieldLabelTranslations = TextTranslations.getLabelTranslations( getEdgeMapper(),
                                                                                                                   null, // FIXME: Can we get the mdf property?
                                                                                                                   enquiryBreakChangeField.getLabel(),
                                                                                                                   enquiryBreakChangeField.getName() );
            
	    	m_languageMapHelper.registerT24TextTranslations(m_treeEnquirySupportElems.dummyColumnQuestionForJQueryDataTable.wrapperObject, firstBreakChangeFieldLabelTranslations);

	    	m_treeEnquirySupportElems.dummyColumnQuestionForJQueryDataTable.presWrapperObject.setDisplayText(firstBreakChangeFieldLabelTranslations.getText());

	    	// } TEMPORARY_KLUDGE

	    	final ContainerRuleWrapper treeEnquiryRulesContainer = ContainerRuleWrapper.create(getFormContext());
	    	treeEnquiryRulesContainer.setName("Populate the additional " + jQueryDataTableControlSubGroupName + " items for the jQuery dataTable widget used to display the results for this Tree enquiry");
	    	populateDisplayResultGroupsContainer.addTrueRule(treeEnquiryRulesContainer);

	    	final ResetDataRuleWrapper resetScratchItem = ResetDataRuleWrapper.create(getFormContext());
	    	resetScratchItem.setName("Reset " + scratchItemName + " item");
	    	resetScratchItem.setResetProperty(p_scratchItem);
	    	treeEnquiryRulesContainer.addTrueRule(resetScratchItem);

	    	final RepeatRuleWrapper iterateAllDisplayMainResultInstances = RepeatRuleWrapper.create(getFormContext());
	    	iterateAllDisplayMainResultInstances.setName("Iterate all DisplayMainResult instances");
	    	iterateAllDisplayMainResultInstances.setRepeatType(RepeatRuleWrapper.ERepeatType.DATA_GROUP);
	    	iterateAllDisplayMainResultInstances.setDataGroupName(m_mainDisplayResultGroup);
	    	iterateAllDisplayMainResultInstances.setRichEndInstance("$$" + m_mainDisplayResultGroup.getEntityKeyName() + ".lastInstance()$");
	    	iterateAllDisplayMainResultInstances.setIgnoreChildRuleResults(true);
	    	treeEnquiryRulesContainer.addTrueRule(iterateAllDisplayMainResultInstances);

	    	final EvaluateRuleWrapper evalIsIrisBreakChangeValueNonBlank = EvaluateRuleWrapper.create(getFormContext());
	    	evalIsIrisBreakChangeValueNonBlank.setName("Is the value for break-change item " + firstBreakChangeItemName + " within the current " + mainDisplayResultGroupName + " instance non-blank");
	    	evalIsIrisBreakChangeValueNonBlank.setExpression("$$" + displayCorrespondentForFirstIrisBreakChangeItem.getEntityKeyName() + "$ != '&nbsp;'");
	    	iterateAllDisplayMainResultInstances.addTrueRule(evalIsIrisBreakChangeValueNonBlank);

	    	final SetValueRuleWrapper updateScratchItemIrisBreakChangeValue = SetValueRuleWrapper.create(getFormContext());
	    	updateScratchItemIrisBreakChangeValue.setName("Update our Scratch item from the value of break-change item " + firstBreakChangeItemName  + " within the current " + mainDisplayResultGroupName + " instance");
	    	updateScratchItemIrisBreakChangeValue.setType(SetValueRuleWrapper.EType.DATA_ITEM);
	    	updateScratchItemIrisBreakChangeValue.setPropertyName(p_scratchItem);
	    	updateScratchItemIrisBreakChangeValue.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
	    	updateScratchItemIrisBreakChangeValue.setFromPropertyName(displayCorrespondentForFirstIrisBreakChangeItem);
	    	evalIsIrisBreakChangeValueNonBlank.addTrueRule(updateScratchItemIrisBreakChangeValue);

	    	final SetValueRuleWrapper setBothIrisBreakChangeItemAndJQDataTableBreakChangeItemFromScratchItem = SetValueRuleWrapper.create(getFormContext());
	    	setBothIrisBreakChangeItemAndJQDataTableBreakChangeItemFromScratchItem.setName("Set both " + firstBreakChangeItemName + " and " + m_treeEnquirySupportElems.breakChangeValueItemForJQueryDataTable.getName() + " within our " + jQueryDataTableControlSubGroupName + " group from our " + scratchItemName + " item");
	    	setBothIrisBreakChangeItemAndJQDataTableBreakChangeItemFromScratchItem.setType(SetValueRuleWrapper.EType.DATA_ITEM);
	    	setBothIrisBreakChangeItemAndJQDataTableBreakChangeItemFromScratchItem.setPropertyNameFromEntityKey(displayCorrespondentForFirstIrisBreakChangeItem.getEntityKeyName() + "," + m_treeEnquirySupportElems.breakChangeValueItemForJQueryDataTable.getEntityKeyName());
	    	setBothIrisBreakChangeItemAndJQDataTableBreakChangeItemFromScratchItem.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
	    	setBothIrisBreakChangeItemAndJQDataTableBreakChangeItemFromScratchItem.setFromPropertyNameFromEntityKey("WorkingElements[1].Scratch");
	    	iterateAllDisplayMainResultInstances.addTrueRule(setBothIrisBreakChangeItemAndJQDataTableBreakChangeItemFromScratchItem);

	    	final SetValueRuleWrapper setDummyForJQDataTable = SetValueRuleWrapper.create(getFormContext());
	    	setDummyForJQDataTable.setName("Set " + m_treeEnquirySupportElems.dummyColumnValueItemForJQueryDataTable.getName() + " within our " + jQueryDataTableControlSubGroupName + " group to an HTML non-breaking space");
	    	setDummyForJQDataTable.setType(SetValueRuleWrapper.EType.DATA_ITEM);
	    	setDummyForJQDataTable.setPropertyName(m_treeEnquirySupportElems.dummyColumnValueItemForJQueryDataTable);
	    	setDummyForJQDataTable.setFromType(SetValueRuleWrapper.EFromType.VALUE);
	    	setDummyForJQDataTable.setFromValue("&nbsp;");
	    	iterateAllDisplayMainResultInstances.addTrueRule(setDummyForJQDataTable);
	    }

        return populateDisplayResultGroupsContainer;
    } // createRuleToPopulateNewDisplayResultGroupsFromIrisResponse()

    private EnquiryDisplayResultsPopulatorRule createEnquiryDisplayResultsPopulatorRule(
        String p_irisResultStartInstanceExpr,
        String p_irisResultEndInstanceExpr,
        PropertyGroupWrapper p_targetDisplayResultsGroup
    ) {
        final EnquiryDisplayResultsPopulatorRule displayResultsPopulatorRule = new EnquiryDisplayResultsPopulatorRule(getFormContext());

        displayResultsPopulatorRule.setAttribute(EnquiryDisplayResultsPopulatorRule.RuleAttrNames.IRIS_RESULT_GROUP_PATH, m_irisResultGroupWrapper.getEntityKeyName());
        displayResultsPopulatorRule.setAttribute(EnquiryDisplayResultsPopulatorRule.RuleAttrNames.IRIS_RESULT_GROUP_START_INSTANCE, p_irisResultStartInstanceExpr);
        displayResultsPopulatorRule.setAttribute(EnquiryDisplayResultsPopulatorRule.RuleAttrNames.IRIS_RESULT_GROUP_END_INSTANCE, p_irisResultEndInstanceExpr);
        displayResultsPopulatorRule.setAttribute(EnquiryDisplayResultsPopulatorRule.RuleAttrNames.DISPLAY_RESULT_GROUP_PATH, p_targetDisplayResultsGroup.getEntityKeyName());

        if (m_enquiryAttrsDigest.isEnquiryWithDrilldowns() && (m_anyDynamicDrilldownsToDisplayItem != null))
            displayResultsPopulatorRule.setAttribute(EnquiryDisplayResultsPopulatorRule.RuleAttrNames.DRILLDOWN_ACTIONS_FOUND_ITEM_PATH, m_anyDynamicDrilldownsToDisplayItem.getEntityKeyName());

        displayResultsPopulatorRule.applySelfGeneratedRuleName();

        return displayResultsPopulatorRule;
    }

    private ContainerRuleWrapper createRuleToSetupBreakChangeControlItemsFromSelectedBreakChangeListValue()
    {
    	AssertionUtils.requireConditionTrue(m_breakChangeControlGroupElems != null, "m_breakChangeControlGroupElems != null");

		final ContainerRuleWrapper setupBreakChangeControlItemsFromSelectedBreakChangeListValue = ContainerRuleWrapper.create(getFormContext());
    	setupBreakChangeControlItemsFromSelectedBreakChangeListValue.setName("Determine the overall " + m_irisResultGroupWrapper.getName() + " instance-range for the selected break-change value - if there is one - or the 1st if we know the list-key for that");

    	final ResetDataRuleWrapper resetIrisResultStartAndEndInstanceItems = ResetDataRuleWrapper.create(getFormContext());
    	resetIrisResultStartAndEndInstanceItems.setName("Reset the " + m_breakChangeControlGroupElems.irisResultGroupStartInstanceItem.getName() + " and " + m_breakChangeControlGroupElems.irisResultGroupStartInstanceItem.getName() + " page-control items");
    	resetIrisResultStartAndEndInstanceItems.setResetPropertyFromEntityKey( "WorkingElements[1].BreakChangeControl[1].IrisResultGroupStartInstance,WorkingElements[1].BreakChangeControl[1].IrisResultGroupEndInstance" );
    	setupBreakChangeControlItemsFromSelectedBreakChangeListValue.addTrueRule(resetIrisResultStartAndEndInstanceItems);

    	final EvaluateRuleWrapper evalDidWeManageToFindAnyBreakChangeValues = EvaluateRuleWrapper.create(getFormContext());
    	evalDidWeManageToFindAnyBreakChangeValues.setName("Did we find ANY break-change values in the IRIS response");
    	evalDidWeManageToFindAnyBreakChangeValues.setExpression("$$" + m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey.getEntityKeyName() + "$ != ''");
    	setupBreakChangeControlItemsFromSelectedBreakChangeListValue.addTrueRule(evalDidWeManageToFindAnyBreakChangeValues);

    	final ContainerRuleWrapper situationNormalRulesContainer = ContainerRuleWrapper.create(getFormContext(), "Sitation NORMAL");
    	evalDidWeManageToFindAnyBreakChangeValues.addTrueRule(situationNormalRulesContainer);

    	final EvaluateRuleWrapper evalDoPreselectFirstBreakChangeListValue = EvaluateRuleWrapper.create(getFormContext());
    	evalDoPreselectFirstBreakChangeListValue.setName("Do we need to default " + m_breakChangeControlGroupElems.breakChangeDropdownItem.getName() + " to the first list value");
    	evalDoPreselectFirstBreakChangeListValue.setExpression("$$" + m_breakChangeControlGroupElems.breakChangeDropdownItem.getEntityKeyName() + "$ == ''");
    	situationNormalRulesContainer.addTrueRule(evalDoPreselectFirstBreakChangeListValue);

    	final SetValueRuleWrapper doPreselectFirstBreakChangeListValue = SetValueRuleWrapper.create(getFormContext());
    	doPreselectFirstBreakChangeListValue.setName("Set " + m_breakChangeControlGroupElems.breakChangeDropdownItem.getName() + " from " + m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey.getName());
    	doPreselectFirstBreakChangeListValue.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	doPreselectFirstBreakChangeListValue.setPropertyName(m_breakChangeControlGroupElems.breakChangeDropdownItem);
    	doPreselectFirstBreakChangeListValue.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
    	doPreselectFirstBreakChangeListValue.setFromPropertyName(m_breakChangeControlGroupElems.firstDynamicBreakChangeListKey);
    	evalDoPreselectFirstBreakChangeListValue.addTrueRule(doPreselectFirstBreakChangeListValue);

    	final SetValueRuleWrapper setIrisResultGroupStartInstanceItemFromSelectedBreakChangeKey = SetValueRuleWrapper.create(getFormContext());
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeKey.setName("Set " + m_breakChangeControlGroupElems.irisResultGroupStartInstanceItem.getName() + " from the KEY of the selected break-change list item");
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeKey.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeKey.setPropertyName(m_breakChangeControlGroupElems.irisResultGroupStartInstanceItem);
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeKey.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeKey.setFromPropertyNameFromEntityKey(m_breakChangeControlGroupElems.breakChangeDropdownItem.getEntityKeyName());
    	situationNormalRulesContainer.addTrueRule(setIrisResultGroupStartInstanceItemFromSelectedBreakChangeKey);

    	final SetValueRuleWrapper setIrisResultGroupStartInstanceItemFromSelectedBreakChangeGroupValue = SetValueRuleWrapper.create(getFormContext());
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeGroupValue.setName("Set " + m_breakChangeControlGroupElems.irisResultGroupEndInstanceItem.getName() + " from the GROUP VALUE of the selected break-change list item");
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeGroupValue.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeGroupValue.setPropertyName(m_breakChangeControlGroupElems.irisResultGroupEndInstanceItem);
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeGroupValue.setFromType(SetValueRuleWrapper.EFromType.DATA_ITEM);
    	setIrisResultGroupStartInstanceItemFromSelectedBreakChangeGroupValue.setFromPropertyNameFromEntityKey(m_breakChangeControlGroupElems.breakChangeDropdownItem.getEntityKeyName() + ".groupValue()");
    	situationNormalRulesContainer.addTrueRule(setIrisResultGroupStartInstanceItemFromSelectedBreakChangeGroupValue);

    	final ContainerRuleWrapper situationFUBARRulesContainer = ContainerRuleWrapper.create(getFormContext(), "Situation FUBAR");
    	evalDidWeManageToFindAnyBreakChangeValues.addFalseRule(situationFUBARRulesContainer);

    	final SetValueRuleWrapper defaultResultGroupStartInstanceItemToFirst = SetValueRuleWrapper.create(getFormContext());
    	defaultResultGroupStartInstanceItemToFirst.setName("Default " + m_breakChangeControlGroupElems.irisResultGroupStartInstanceItem.getName() + " to 1");
    	defaultResultGroupStartInstanceItemToFirst.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	defaultResultGroupStartInstanceItemToFirst.setPropertyName(m_breakChangeControlGroupElems.irisResultGroupStartInstanceItem);
    	defaultResultGroupStartInstanceItemToFirst.setFromType(SetValueRuleWrapper.EFromType.VALUE);
    	defaultResultGroupStartInstanceItemToFirst.setFromValue("1");
    	situationFUBARRulesContainer.addTrueRule(defaultResultGroupStartInstanceItemToFirst);

    	final EvaluateRuleWrapper evalDoApplyMaxIrisResultInstancesCap = EvaluateRuleWrapper.create(getFormContext());
    	evalDoApplyMaxIrisResultInstancesCap.setName("Do we have more than " + IRIS_RESULT_GROUP_INSTANCE_CAP_WHERE_BREAK_CHANGE_VALUES_UNKNOWN + " " + m_irisResultGroupWrapper.getName() + " instances");
    	evalDoApplyMaxIrisResultInstancesCap.setExpression("$$" + m_irisResultGroupWrapper.getEntityKeyName() + ".lastInstance()$ > '" + IRIS_RESULT_GROUP_INSTANCE_CAP_WHERE_BREAK_CHANGE_VALUES_UNKNOWN + "'");
    	situationFUBARRulesContainer.addTrueRule(evalDoApplyMaxIrisResultInstancesCap);

    	final SetValueRuleWrapper applyMaxIrisResultInstanceCap = SetValueRuleWrapper.create(getFormContext());
    	applyMaxIrisResultInstanceCap.setName("Set " + m_breakChangeControlGroupElems.irisResultGroupEndInstanceItem.getName() + " to " + IRIS_RESULT_GROUP_INSTANCE_CAP_WHERE_BREAK_CHANGE_VALUES_UNKNOWN);
    	applyMaxIrisResultInstanceCap.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	applyMaxIrisResultInstanceCap.setPropertyName(m_breakChangeControlGroupElems.irisResultGroupEndInstanceItem);
    	applyMaxIrisResultInstanceCap.setFromType(SetValueRuleWrapper.EFromType.VALUE);
    	applyMaxIrisResultInstanceCap.setFromValue(String.valueOf(IRIS_RESULT_GROUP_INSTANCE_CAP_WHERE_BREAK_CHANGE_VALUES_UNKNOWN));
    	evalDoApplyMaxIrisResultInstancesCap.addTrueRule(applyMaxIrisResultInstanceCap);

    	final SetValueRuleWrapper useAllIrisResultInstances = SetValueRuleWrapper.create(getFormContext());
    	useAllIrisResultInstances.setName("Set " + m_breakChangeControlGroupElems.irisResultGroupEndInstanceItem.getName() + " to point at the last populated " + m_irisResultGroupWrapper.getName() + " instance");
    	useAllIrisResultInstances.setType(SetValueRuleWrapper.EType.DATA_ITEM);
    	useAllIrisResultInstances.setPropertyName(m_breakChangeControlGroupElems.irisResultGroupEndInstanceItem);
    	useAllIrisResultInstances.setFromType(SetValueRuleWrapper.EFromType.VALUE);
    	useAllIrisResultInstances.setFromValue("$$" + m_irisResultGroupWrapper.getEntityKeyName() + ".lastInstance()$");

    	evalDoApplyMaxIrisResultInstancesCap.addFalseRule(useAllIrisResultInstances);

    	return setupBreakChangeControlItemsFromSelectedBreakChangeListValue;
    } // createRuleToSetupBreakChangeControlItemsFromSelectedBreakChangeListValue()

    // } NEW_METHODS_FOR_FLATTENED_RESULTS_TABLE_PATTERN

    private boolean isForeignKeyAssociation(MdfAssociation p_appFieldAssociationDef)
    {
        AssertionUtils.requireNonNull( p_appFieldAssociationDef, "p_appFieldAssociationDef" );
		return (p_appFieldAssociationDef.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) && ! p_appFieldAssociationDef.isPrimaryKey();
    }

    private SearchParamDropdownInfo extractSearchParamDropdownInfo(MdfAssociation p_appFieldAssociationDef) throws Exception
    {
        SearchParamDropdownInfo result = null;

        if ( isForeignKeyAssociation( p_appFieldAssociationDef ) )
        {
            final MdfClass refdAppMdfClass = (MdfClass) p_appFieldAssociationDef.getType();
            if ( refdAppMdfClass != null )
            {
                final List<?> refdAppPrimaryKeys = refdAppMdfClass.getPrimaryKeys();

                if ( refdAppPrimaryKeys.size() == 1 )
                {
                    final Object primaryKeyObj = refdAppPrimaryKeys.get( 0 );

                    if ( primaryKeyObj instanceof MdfAssociation )
                    {
                        final String refdIrisResourceName = MapperUtility.processT24NameToIRISName( refdAppMdfClass.getName() ) + "s";
                        final String refdIrisPrimaryKeyName = MapperUtility.processT24NameToIRISName( ( (MdfAssociation) primaryKeyObj ).getName() );

                        result = new SearchParamDropdownInfo( refdIrisResourceName, refdIrisPrimaryKeyName );
                    }
                }
            }
        }

        return result;
    }

	private <RuleType extends GenericLeafNode> RuleType getMandatoryTemplateDefinedTopLevelRule(String p_ruleName, Class<RuleType> p_ruleClass) throws Exception
    {
        final RuleType result = m_rulesRoot.getChildWithName( p_ruleName, p_ruleClass );

        if ( result == null )
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define top-level " + p_ruleClass.getSimpleName() + ": " + p_ruleName + " !");

        return result;
    }

    private PhaseWrapper getMandatoryTemplateDefinedPhase(String p_fullPhaseName) throws Exception
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_fullPhaseName, "p_fullPhaseName" );

        final PhaseWrapper phase = m_templateProject.getPhase( p_fullPhaseName );

        if ( phase == null )
            throw new Exception( "Template project: " + getProject().getName() + ".ifp doesn't define phase: " + p_fullPhaseName );

        return phase;
    }

    private PropertyWrapper getMandatoryTemplateDefinedProperty(String p_itemPath) throws Exception
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_itemPath, "p_itemPath" );

        final Property property = getSessionDictionary().getDataDictionary().getProperty( p_itemPath, false );

        if ( property == null )
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define data store item: " + p_itemPath + " !");

        return PropertyWrapper.wrap( property );
    }

    private PropertyGroupWrapper getMandatoryTemplateDefinedPropertyGroup(String p_groupPath) throws Exception
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_groupPath, "p_groupPath" );

        try
        {
            return PropertyGroupWrapper.wrap( getSessionDictionary().getDataDictionary().getPropertyGroup( p_groupPath ) );
        }

        catch (NameNotFoundException nnfe)
        {
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define data store group: " + p_groupPath + " !");
        }
    }

	private FormListWrapper getMandatoryTemplateDefinedList(String p_listPath) throws Exception {
        AssertionUtils.requireNonNullAndNonEmpty( p_listPath, "p_listPath" );

        final FormList formList = getFormContext().getEntityofType( p_listPath, FormList.class );

        if ( formList == null )
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define list type: " + p_listPath + " !");

        return FormListWrapper.wrap( formList );
    }

    private DataStructureWrapper getMandatoryTemplateDefinedStructure(String p_structureName) throws Exception
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_structureName, "p_structureName" );

		final DataStructure dataStructure = getProject().unwrap().getTypesRoot().getStructures().getChildWithName(p_structureName, DataStructure.class);

        if ( dataStructure == null )
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define data structure: " + p_structureName + " !");

        return DataStructureWrapper.wrap( dataStructure );
    }

    private PropertyGroupWrapper getMandatoryTemplateDefinedStructureSubgroupByPath(String p_fullPath) throws Exception
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_fullPath, "p_fullPath" );

        final String fullPath = p_fullPath.endsWith( "[1]" ) ? p_fullPath.substring( 0, p_fullPath.length() - 3 ) : p_fullPath;
		final PropertyGroup propertyGroup = getFormContext().getEntityofType(StructuresRoot.DATA_STRUCTURES_PREFIX + fullPath, PropertyGroup.class);

        if ( propertyGroup == null )
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define structure sub-group: " + p_fullPath + " !");

        return PropertyGroupWrapper.wrap( propertyGroup );
    }

    private XMLMappingSetWrapper getMandatoryXMLMappingSet(String p_xmlInterfaceName) throws Exception
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_xmlInterfaceName, "p_xmlInterfaceName" );

        final XMLMappingSet xmlMappingSet = (XMLMappingSet) getFormContext().getEntity( p_xmlInterfaceName, XMLMappingSet.class );

        if ( xmlMappingSet == null )
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define XML interface: " + p_xmlInterfaceName + " !");

        return XMLMappingSetWrapper.wrap( xmlMappingSet );
    }

    private XMLDataSourceWrapper getMandatoryTemplateDefinedXMLDataSource(String p_xmlDataSourceName) throws Exception
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_xmlDataSourceName, "p_xmlDataSourceName" );

        final XMLDataSource xmlDataSource = (XMLDataSource) getFormContext().getEntity( p_xmlDataSourceName, XMLDataSource.class );

        if ( xmlDataSource == null )
			throw new Exception("Template project: " + getProject().getName() + ".ifp doesn't define XML data source: " + p_xmlDataSourceName + " !");

        return XMLDataSourceWrapper.wrap( xmlDataSource );
    }

    private AppFieldNameOrNumberResult getAppFieldNameOrNumber(Operation p_fieldOperation, Integer p_t24LineNumber, String p_relativePosition)
    {
    	String appFieldName = null;
    	int appFieldPos = 0;

    	if (p_fieldOperation instanceof ApplicationFieldNameOperation)
		{
			appFieldName = ((ApplicationFieldNameOperation) p_fieldOperation).getField();
		}

    	else if ((p_fieldOperation instanceof FieldExtractOperation) && (p_t24LineNumber == null || p_relativePosition == null))
		{
			// fields that are not part of a related set.(+ is not defined in line number for the field)
			Field extractField = m_enquiryExtractFieldByT24FieldName.get(((FieldExtractOperation) p_fieldOperation).getField());

			if (extractField != null)
			{
				if (extractField.getOperation() instanceof ApplicationFieldNameOperation)
				{
					appFieldName = ((ApplicationFieldNameOperation) extractField.getOperation()).getField();
				}

				else
				{
					// if instance of nested FieldExtractOperation or instance of ConstantOperation or eq field position
					if (extractField.getOperation() instanceof FieldNumberOperation)
					{
						appFieldPos = ((FieldNumberOperation) extractField.getOperation()).getNumber();
					}

					if (extractField.getOperation() instanceof FieldExtractOperation)
					{
						Field currentField = extractField;
						extractField = m_enquiryExtractFieldByT24FieldName.get(((FieldExtractOperation) extractField.getOperation()).getField());

						if (extractField.getPosition() != null)
						{
							p_t24LineNumber = extractField.getPosition().getLine();
							p_relativePosition = extractField.getPosition().getRelative();
						}

						else
						{
							p_t24LineNumber = null;
							p_relativePosition= null;
						}

						if (currentField != extractField)
							return getAppFieldNameOrNumber(extractField.getOperation(), p_t24LineNumber, p_relativePosition);

						//should not occur
						LOGGER.error("[\"" + currentField.getName() + "\"] " + "(Duplicate field ignored. Check enquiry definition)");
					}
				}
			}
		}

		else if(p_fieldOperation instanceof FieldNumberOperation)
		{
			appFieldPos = ((FieldNumberOperation) p_fieldOperation).getNumber();
		}

		else if (p_fieldOperation instanceof FieldExtractOperation)
		{
			Field extractField = m_enquiryExtractFieldByT24FieldName.get(((FieldExtractOperation) p_fieldOperation).getField());

			if ((extractField != null) && (extractField.getOperation() instanceof FieldNumberOperation))
			{
				appFieldPos = ((FieldNumberOperation) extractField.getOperation()).getNumber();
			}
		}

    	return new AppFieldNameOrNumberResult(appFieldName, appFieldPos);
    }

	private RichHTMLPresentationQuestionWrapper setQuestionDesign(RichHTMLPresentationQuestionWrapper presentationQuestion)
	{
		presentationQuestion.setDesignToUseFromEntityKey("Custom");
		presentationQuestion.setRowStyle("QRow");
		presentationQuestion.setQuestionStyle("FontGrey QQues");
		presentationQuestion.setMandatoryStyle("ErrorColor QMand");
		presentationQuestion.setPrefixStyle("QPref");
		presentationQuestion.setAnswerStyle("QAns");
		presentationQuestion.setAnswerControlStyle("FontDarkGrey");
		presentationQuestion.setPostfixStyle("QPost");
		presentationQuestion.setHelpStyle("QHelp");
		presentationQuestion.setErrorStyle("ErrorColor SmallFont");
		presentationQuestion.setInfoStyle("VSmallFont Italic");
		presentationQuestion.setJustification(RichHTMLPresentationQuestionWrapper.EJustification.NONE);
		presentationQuestion.setAnswerJustification(RichHTMLPresentationQuestionWrapper.EAnswerJustification.NONE);
		presentationQuestion.setDisplayType("Text Input Field");
		presentationQuestion.setReadOnlyFormat(RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.TEXT);
		presentationQuestion.setDefaultButtonName("Use Default");
		return presentationQuestion;
	}

	private void populateBreakAndConversionFields(EList<Field> fields) {
		final int numFields = (fields == null) ? 0 : fields.size();

		for (int i = 0; i < numFields; ++i) {
			final Field field = fields.get(i);

			final DisplaySectionKind fieldDisplaySection = field.getDisplaySection();

			if (fieldDisplaySection == DisplaySectionKind.NO_DISPLAY)
				continue;

			final String fieldName = field.getName();
			final Operation fieldOperation = field.getOperation();

			if (fieldOperation instanceof BreakOnChangeOperation)
				m_breakChangeFields.add(field);

			if (fieldName != null)
			{
				if (m_enquiryExtractFieldByT24FieldName.get(fieldName) == null)
					m_enquiryExtractFieldByT24FieldName.put(fieldName, field);

				else
					LOGGER.error("Duplicate field definition for field name: \"" + field.getName() + "\" (ignoring - please check enquiry definition)");
			}
		}
	}

    private void addBreakQuestionAndDataStoreElems(PropertyGroupWrapper p_workingElementsGroup) {
        AssertionUtils.requireNonNull(p_workingElementsGroup, "p_workingElementsGroup");

    	final int numBreakChangeFields = m_breakChangeFields.size();

    	if ((numBreakChangeFields > 0) && ! m_enquiryAttrsDigest.isTreeEnquiry())
    	{
            m_breakChangeControlGroupElems = new BreakChangeControlGroupElems(this, p_workingElementsGroup);

            final QuestionWrapper newBreakChangeQuestion = QuestionWrapper.create(getFormContext());
            newBreakChangeQuestion.setQuestionText("$%SLANG Enquiry.NewBreakChangeLabel [Break-change]$");
            newBreakChangeQuestion.setPropertyKey(m_breakChangeControlGroupElems.breakChangeDropdownItem);
            newBreakChangeQuestion.setPostQuestionRules(Boolean.TRUE);
            newBreakChangeQuestion.setMandatory(Boolean.FALSE);
            newBreakChangeQuestion.setPostQuestionRules(Boolean.TRUE);

            final RichHTMLPresentationQuestionWrapper newBreakChangePresQuestion = RichHTMLPresentationQuestionWrapper.create(getFormContext(), newBreakChangeQuestion.unwrap());
            setQuestionDesign(newBreakChangePresQuestion);
            newBreakChangePresQuestion.setHideQuestion(Boolean.TRUE);           // Sathish wants some question text, but dunno what we can use (given that it would need translations and there may be > 1 Brk/Chg field)
            newBreakChangePresQuestion.setDisplayType("Drop down list");
            newBreakChangePresQuestion.setMandatoryStyle("NotDisplayed");       // Necessary to prevent indentation of the break-change question
            newBreakChangePresQuestion.setPrefixStyle("NotDisplayed");          // ditto

            m_enquiryResultsToolbarElems.addEnquiryBreakChangeQuestion(m_newBreakChangeQWP = new QuestionWrapperPair(newBreakChangeQuestion, newBreakChangePresQuestion));
    	}
    }

    /** @return <code>true</code> if this field is either (ii) ignorable or (ii) has been fully dealt with here, or <code>false</code> otherwise */
    private boolean handleConstantOperation(
    	final Field p_field,
    	final IrisResultGroupSpec p_irisResultGroupSpec
    ) {
    	AssertionUtils.requireNonNull(p_field, "p_field");
    	AssertionUtils.requireNonNull(p_irisResultGroupSpec, "p_irisResultGroupSpec");

    	final FieldPosition fieldPosition = p_field.getPosition();
    	final DisplaySectionKind fieldDisplaySection = p_field.getDisplaySection();

        boolean
            isFieldIgnorable = (fieldDisplaySection == DisplaySectionKind.NO_DISPLAY) || (fieldPosition.getColumn() == 0),
            isFieldProcessed = false;

        if (! isFieldIgnorable)
        {
        	final Operation fieldOperation = p_field.getOperation();
        	final BreakCondition breakCondition = p_field.getBreakCondition();
    
        	if (
    			(fieldOperation instanceof ConstantOperation) &&
    			(fieldPosition.getLine() != null ) &&
    			(
    				(p_field.getDisplaySection() == DisplaySectionKind.HEADER) ||
    				((breakCondition != null) &&breakCondition.getBreak() == BreakKind.NEW_PAGE)
    			)
    		) {
        		p_irisResultGroupSpec.add(new VisibleIrisResultItemSpec(p_field, ResultsTableType.ENQUIRY_HEADER_RESULTS, m_enquiryFieldsDigest));
        		isFieldProcessed = true;
            }
    	}

    	return (isFieldIgnorable || isFieldProcessed);
    }

    private void addInitStaticHeaderRulesToStartPhase(PhaseWrapper p_startPhase)
    {
        AssertionUtils.requireNonNull(p_startPhase, "p_startPhase");

        if (m_staticResultsScreenHeaderControlElems != null)
            p_startPhase.addPostPhaseRule(m_staticResultsScreenHeaderControlElems.initStaticHeaderValuesRule);
    }

    private void addExecuteRulesToStartPhase(
        PhaseWrapper p_startPhase,
        PhaseWrapper p_searchPhase,
        PhaseWrapper p_searchResultsPhase,
        PropertyWrapper p_irisInputContextItem,
        PropertyWrapper p_invokedInDrilldownContextItem,
        PropertyWrapper p_selectionElemsHideShowStateItem,
        PropertyWrapper p_searchOutcomeItem,
        PropertyGroupWrapper p_columnSortSpecGroup,
        PropertyGroupWrapper p_searchFiltersGroup,
        PropertyGroupWrapper p_searchResultGroup
	) {
        AssertionUtils.requireNonNull(p_startPhase, "p_startPhase");
        AssertionUtils.requireNonNull(p_searchPhase, "p_searchPhase");
        AssertionUtils.requireNonNull(p_searchResultsPhase, "p_searchResultsPhase");
        AssertionUtils.requireNonNull(p_irisInputContextItem, "p_irisInputContextItem");
        AssertionUtils.requireNonNull(p_invokedInDrilldownContextItem, "p_invokedInDrilldownContextItem");
        AssertionUtils.requireNonNull(p_selectionElemsHideShowStateItem, "p_selectionElemsHideShowStateItem");
        AssertionUtils.requireNonNull(p_searchOutcomeItem, "p_searchOutcomeItem");
        AssertionUtils.requireNonNull(p_searchResultGroup, "p_searchResultGroup");

        final Rule findButtonContainerRule = (Rule) m_rulesRoot.getChildWithName(RuleNames.FIND_BUTTON_RULES);
		final Rule execIrisSearchUrlContainerRule = (Rule)  findButtonContainerRule.getChildWithName(RuleNames.EXEC_IRIS_SEARCH_URL);
		final Rule searchRequestDispatchContainerRule = (Rule) execIrisSearchUrlContainerRule.getChildWithName(RuleNames.SEARCH_REQUEST_DISPATCH);

		/*
		 *  We need to modify key sub-rules of searchRequestDispatchContainerRule to do the right thing in the "Start" phase context, but WITHOUT affecting the originals, so
		 *  begin by taking a "deep copy" of searchRequestDispatchContainerRule and "unhooking" our copy from the original's parent.
		 */

        final Rule clonedSearchRequestDispatchContainerRule = (Rule) searchRequestDispatchContainerRule.clone();
        clonedSearchRequestDispatchContainerRule.setParent(null);

        /*
         * Now we need to navigate downwards from clonedSearchRequestDispatchContainerRule to find the descendant rules we need to modify to fit the needs of the Start phase.
         * 
         * These are:
         * 
         * - The IRISRequest rule: this needs to be modified so that URL invoked is the one supplied to us as our IRISInputContext (vs. the one constructed from user-entered
         *   search parameters by EnquirySearchUrlBuilderRule, which is what happens in the "Find button rules" context).
         *
         * - The EvaluateRule that tests whether the IRISRequest returned any results: to this we need to add newGoForwardsToSearchResultsPhaseRule as a 'false' rule
         *   since, in the case where we execute the search immediately (as part of the Start phase rules), we ALWAYS want to take the user to the "Search Results" screen -
         *   i.e. *regardless* of whether any results were returned. (This differs from the behaviour required for the "Find button rules", where in the case where no results
         *   are returned, we actually want to leave them on the screen containing the "Find" button that they clicked (whether that be the "Search" screen or the
         *   "Search Results" screen).
         */

        final Rule clonedIrisRequestRule = (Rule) clonedSearchRequestDispatchContainerRule.getChildWithName(RuleNames.IRIS_REQUEST);

        clonedIrisRequestRule.setAttribute(IRISRequest.TARGET_URL, "$$" + DataStore.LogicalScreenModel.ItemPaths.IRIS_INPUT_CONTEXT + "$");

        final EvaluateRule clonedEvalAnyResultsReturnedRule = (EvaluateRule) clonedIrisRequestRule.getChildWithName(RuleNames.TEST_WHETHER_ANY_RESULTS_RETURNED);

        final Rule newGoForwardsToSearchResultsPhaseRule = makeGoForwardToPhaseRule(p_searchResultsPhase).unwrap();
        clonedEvalAnyResultsReturnedRule.addFalseRule(newGoForwardsToSearchResultsPhaseRule, false /* p_linkedObject */);

        /*
         * Now we need to decide how we need to wire clonedEvalRunNewSearchRules into p_startPhase.
         *
         * This depends on whether or not the execute-on-load behaviour of our particular Enquiry is conditional upon the its having been loaded in a "drilldown" context (i.e.
         * with parameter values included in the IRIS url).
         */
        
    	if (m_enquiryAttrsDigest.isAutoRunEnquiryOnLoad())
			p_startPhase.addPostPhaseRule(clonedSearchRequestDispatchContainerRule);

    	else
    	{
            /*
             * Execution *is* conditional upon our having been invoked in a "drilldown" context.
             * Create / attach a post-phase EvaluateRule to p_startPhase to test the value of data item: p_invokedInDrilldownContextItem.
             */

            final EvaluateRuleWrapper testAutoRunEnquiryEvalRule = EvaluateRuleWrapper.create( getFormContext() );
            testAutoRunEnquiryEvalRule.setName( "If we have been invoked as a drilldown from another entity" );
            testAutoRunEnquiryEvalRule.setExpression( "$$" + p_invokedInDrilldownContextItem.getPropertyKey( null ) + "$ == 'true'" );
            p_startPhase.addPostPhaseRule(testAutoRunEnquiryEvalRule);

            /*
             * Create / attach a container for the rules to be run when testAutoRunEnquiryEvalRule evaluates as true
             */
            final ContainerRuleWrapper execSearchAndTakeUserToResultsScreenRegardlessOfOutcomeRules = ContainerRuleWrapper.create(
                getFormContext(),
                "Execute search and take user to the " + p_searchResultsPhase.getName() + " phase - REGARDLESS of whether any results are returned"
            );
            
            testAutoRunEnquiryEvalRule.addTrueRule(execSearchAndTakeUserToResultsScreenRegardlessOfOutcomeRules);

            /*
             * Add child rules to execSearchAndTakeUserToResultsScreenRegardlessOfOutcomeRule
             */
        	final Rule setSearchOutcomeToNoResultsFoundRule = (Rule) execIrisSearchUrlContainerRule.getChildWithName(RuleNames.SET_SEARCH_OUTCOME_TO_NO_RESULTS_FOUND);
        	execSearchAndTakeUserToResultsScreenRegardlessOfOutcomeRules.addTrueRuleLink(setSearchOutcomeToNoResultsFoundRule);
        	execSearchAndTakeUserToResultsScreenRegardlessOfOutcomeRules.addTrueRule( clonedSearchRequestDispatchContainerRule );
        	
        	/*
        	 * Create / attach a container for the rules to be run when testAutoRunEnquiryEvalRule evaluates as false
        	 */
        	final ContainerRuleWrapper loadLastUsedFavouritesAndTakeUserToSearchScreenRules = ContainerRuleWrapper.create(
        	    getFormContext(),
        	    "Load user's last used search parameters - if available - and take the user to the " + p_searchPhase.getName() + " phase"
        	);
        	
        	testAutoRunEnquiryEvalRule.addFalseRule(loadLastUsedFavouritesAndTakeUserToSearchScreenRules);
        	
            EnquiryLastSavedSelectionRule loadLastUsedSelectionRule = new EnquiryLastSavedSelectionRule(getFormContext());
            loadLastUsedSelectionRule.setName( "LastUsedSelectionRule" );
            loadLastUsedSelectionRule.setErrorHandlingType( "Goto Error Phase" );
            loadLastUsedSelectionRule.setRuntimeDataDirPath( RUNTIME_ENQUIRY_DATA_DIR_EXPN );
            loadLastUsedSelectionRule.setFilterParamsGroupPath(p_searchFiltersGroup.getPropertyGroupKey());
            loadLastUsedSelectionRule.setRepeatableOrderByParamGroupPath(p_columnSortSpecGroup.getPropertyGroupKey());

        	loadLastUsedFavouritesAndTakeUserToSearchScreenRules.addTrueRule(loadLastUsedSelectionRule);
        	
        	loadLastUsedFavouritesAndTakeUserToSearchScreenRules.addTrueRule( makeGoForwardToPhaseRule(p_searchPhase));
    	}
    }

    // NEW {
    
    private static void addApplicabilityConditionToToolbarItemGroupIfNeeded(
        EnquiryResultsToolbarElems p_pseudoEnquiryResultsToolbarElems,
        BreakChangeControlGroupElems p_breakChangeControlGroupElems,
        PropertyWrapper p_searchOutcomeItem,
        PropertyWrapper p_topLevelLinksActionItem
    ) {
        AssertionUtils.requireNonNull(p_pseudoEnquiryResultsToolbarElems, "p_pseudoEnquiryResultsToolbarElems");
        AssertionUtils.requireNonNull(p_searchOutcomeItem, "p_searchOutcomeItem");
        AssertionUtils.requireNonNull(p_topLevelLinksActionItem, "p_topLevelLinksActionItem");

        final String applicabilityCondition = genToolbarApplicabilityCondition(p_pseudoEnquiryResultsToolbarElems, p_breakChangeControlGroupElems, p_searchOutcomeItem, p_topLevelLinksActionItem);
        
        if (applicabilityCondition != null)
        {
            final ItemGroupWrapper pseudoResultsToolbarItemGroup = p_pseudoEnquiryResultsToolbarElems.getToolbarItemGroupWrapperPair().wrapperObject;
            pseudoResultsToolbarItemGroup.setNotApplicable(Boolean.TRUE);
            pseudoResultsToolbarItemGroup.setConditionExpression(applicabilityCondition);
        }
    }

    private static String genToolbarApplicabilityCondition(
        EnquiryResultsToolbarElems p_enquiryResultsToolbarElems,
        BreakChangeControlGroupElems p_breakChangeControlGroupElems,
        PropertyWrapper p_searchOutcomeItem,
        PropertyWrapper p_topLevelLinksActionItem
    ) {
        AssertionUtils.requireNonNull(p_enquiryResultsToolbarElems, "p_enquiryResultsToolbarElems");
        AssertionUtils.requireNonNull(p_searchOutcomeItem, "p_searchOutcomeItem");
        AssertionUtils.requireNonNull(p_topLevelLinksActionItem, "p_topLevelLinksActionItem");
        
        String result = null;
        
        if (p_enquiryResultsToolbarElems.isPseudoEnquiryResultsToolbar())
        {
            final boolean isBreakChangeControlPresent = (p_breakChangeControlGroupElems != null);
            final StringBuilder applicabilityConditionBuffer = new StringBuilder();
            
            applicabilityConditionBuffer
                .append("$$")
                .append(p_searchOutcomeItem.getEntityKeyName())
                .append("$ == '")
                .append(Lists.SearchOutcome.Keys.RESULTS_FOUND)
                .append("' AND ");
            
            if (isBreakChangeControlPresent)
            {
                applicabilityConditionBuffer
                    .append("($$")
                    .append(p_breakChangeControlGroupElems.numBreakChangeValuesItem.getEntityKeyName())
                    .append("$ > '1' OR ");
            }
                
            applicabilityConditionBuffer
                .append("$$")
                .append(p_topLevelLinksActionItem.getEntityKeyName())
                .append(".listKeys()$ != ''");
            
            if (isBreakChangeControlPresent)
                applicabilityConditionBuffer.append(')');
    
            result = applicabilityConditionBuffer.toString();
        }
        
        return result;
    } // genToolbarApplicabilityCondition()

    // } NEW

    /*
     * I'm concerned to see that this method is no longer used anywhere in this class, but don't have time right now to find out:
     * - the situation it was introduced to deal with
     * - when/why the original handling of that situation was removed
     * - what (if anything) is being done about this situation now
     *
     * Pending investigation, I've suppressed the "unused" warning as I don't want this deleted until we know for sure we don't need it !
     * 
     * 31/08/2014 Simon Hayes
     */
    @SuppressWarnings("unused")
	private static String convertToLiveApplicationFilenameValue(String p_applicationFilenameValue)
    {
    	String result = p_applicationFilenameValue;

    	if (p_applicationFilenameValue != null) {
    		final Matcher matcher = REGEXP_LIVE_PATTERN_FILENAME.matcher(p_applicationFilenameValue);

    		if (matcher.matches()) {
    			result = matcher.group(1) + matcher.group(2);
    		}
    	}

    	return result;
    }

    private static SelectionExpression buildSelectionExpressionForAllResultFields(SortedMap<Integer, EnquirySelectionFieldInfo> p_enquirySelectionFieldInfoByT24ColumnNumber)
    {
        final SelectionExpressionImplExtn result = new SelectionExpressionImplExtn();

        if (! ((p_enquirySelectionFieldInfoByT24ColumnNumber == null) || p_enquirySelectionFieldInfoByT24ColumnNumber.isEmpty()))
        {
            final Iterator<EnquirySelectionFieldInfo> enqSelFieldInfoIter = p_enquirySelectionFieldInfoByT24ColumnNumber.values().iterator();

            while(enqSelFieldInfoIter.hasNext())
            {
                final EnquirySelectionFieldInfo enqSelFieldInfo = enqSelFieldInfoIter.next();
                result.getSelection().add(new SelectionImplExtn(enqSelFieldInfo.appFieldName));
            }
        }

        return result;
    }

	private static String stripTrailingInstanceSuffix(String p_groupPath)
	{
        AssertionUtils.requireNonNullAndNonEmpty( p_groupPath, "p_groupPath" );

        final int indexOfInstanceSuffix = p_groupPath.lastIndexOf( '[' );
		return (indexOfInstanceSuffix == (p_groupPath.length() - 3)) ? p_groupPath.substring(0, indexOfInstanceSuffix) : p_groupPath;
    }

    private static class AppFieldNameOrNumberResult
    {
    	final String appFieldName;
    	final int appFieldPos;

    	AppFieldNameOrNumberResult(String p_appFieldName, int p_appFieldPos)
    	{
    		appFieldName = p_appFieldName;
    		appFieldPos = p_appFieldPos;
    	}
    }

    /** The purpose of this extension is simply to give buildSelectionExpressionForAllResultFields() accesss to the default constructor (which is protected in SelectionExpressionImpl) */
    private static class SelectionExpressionImplExtn extends SelectionExpressionImpl
    {
    	SelectionExpressionImplExtn() { }
    }

    /** The purpose of this extension is simply to give buildSelectionExpressionForAllResultFields() accesss to the default constructor (which is protected in SelectionImpl) */
    private static class SelectionImplExtn extends SelectionImpl
    {
    	SelectionImplExtn(String p_appFieldName) {
    		setField(p_appFieldName);
    	}
    }
}
