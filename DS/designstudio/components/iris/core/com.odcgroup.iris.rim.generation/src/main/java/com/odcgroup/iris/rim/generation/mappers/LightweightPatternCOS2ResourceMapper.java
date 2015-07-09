package com.odcgroup.iris.rim.generation.mappers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.common.Translation;
import com.odcgroup.edge.t24ui.cos.pattern.COSPanel;
import com.odcgroup.edge.t24ui.cos.pattern.COSPatternContainer;
import com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec;
import com.odcgroup.edge.t24ui.cos.pattern.InitialCOS;
import com.odcgroup.edge.t24ui.cos.pattern.InitialEnquiry;
import com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec;
import com.odcgroup.edge.t24ui.cos.pattern.InitialScreen;
import com.odcgroup.edge.t24ui.cos.pattern.InitialURL;
import com.odcgroup.edge.t24ui.cos.pattern.InlineInitialCOS;
import com.odcgroup.edge.t24ui.cos.pattern.MultiComponentPanel;
import com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.rim.generation.MapperHelper;
import com.odcgroup.iris.rim.generation.ParameterParser;
import com.odcgroup.iris.rim.generation.ParameterParserResult;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.di.ILangSpecificProvider;
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;
import com.odcgroup.workbench.generation.cartridge.ng.SimplerEclipseResourceFileSystemAccess2;

public class LightweightPatternCOS2ResourceMapper {
	private static final String RELATION_PREFIX = "http://www.temenos.com/rels/";
	private static final String EMPTY_COS_PANEL_RESOURCE_NAME = "common.COS.emptyCOSPanelContentPlaceholder";
	private static final String URL_COS_PANEL_RESOURCE_NAME = "common.COS.urlCOSPanelContentPlaceholder";

	private static final String[] USE_EXPRESSIONS = new String[] {
		"common.CoreCommands.*",
		"common.HTTPEvents.*"
	};

	private static final String BASEPATH = "/{companyid}";

	private final SimplerEclipseResourceFileSystemAccess2 m_hhouseModelsFSA;
	private final MapperHelper m_mapperHelper;
    private final ResourceSet m_resourceSet;
    private final PatternCOSPropertiesWriter m_cosPropsWriter;
	private final HashMap<String,PendingExternalPatternCOS> m_pendingExternalPatternCOSByFullyQualifiedResourceName;
	private final Logger m_logger = LoggerFactory.getLogger(LightweightPatternCOS2ResourceMapper.class);
	private final ILangSpecificProvider<XtextProxyUtil> m_proxyUtilProvider ;

	private PrintWriter m_rimWriter;
    private String indentStr = "";

	public LightweightPatternCOS2ResourceMapper(
	    SimplerEclipseResourceFileSystemAccess2 p_hhouseModelsFSA,
	    MapperHelper p_mapperHelper,
	    ILangSpecificProvider<XtextProxyUtil> proxyUtilProvider
	) {
		m_proxyUtilProvider = proxyUtilProvider;
		m_hhouseModelsFSA = p_hhouseModelsFSA;
		m_mapperHelper = p_mapperHelper;
		m_resourceSet = m_mapperHelper.getResourceSet();
		m_cosPropsWriter = new PatternCOSPropertiesWriter(p_hhouseModelsFSA);
		m_pendingExternalPatternCOSByFullyQualifiedResourceName = new HashMap<String,PendingExternalPatternCOS>();
	}

    public void writePatternCOSRIM(CompositeScreen p_patternCOS, boolean p_doRecurseRefdPatternCOSes) throws Exception {
       final IrisResourceUrlSet refdIrisUrlSet = (p_doRecurseRefdPatternCOSes && p_patternCOS.isTopLevelCOS()) ?
           // [See 15/01/2015 comment re propsFilenameNoExtn change in addPatternCOSResourceDefRecursive() for explanation :-)
           // new IrisResourceUrlSet(ParameterParser.getResourceName(RESOURCE_TYPE.composite, p_patternCOS.getDomain() + '.' + p_patternCOS.getName(), null).getResourceName() + ".dat") :
           new IrisResourceUrlSet(ParameterParser.getResourceName(RESOURCE_TYPE.composite, p_patternCOS.getName(), null).getResourceName() + ".dat") :
           null;

       writePatternCOSRIM(p_patternCOS, p_doRecurseRefdPatternCOSes, refdIrisUrlSet);

       if (refdIrisUrlSet != null)
           new IrisResourceUrlSetWriter(m_cosPropsWriter).writeToFile(refdIrisUrlSet);
    }

    private LightweightPatternCOS2ResourceMapper(LightweightPatternCOS2ResourceMapper p_other) {
        m_proxyUtilProvider = p_other.m_proxyUtilProvider;
        m_hhouseModelsFSA = p_other.m_hhouseModelsFSA;
        m_mapperHelper = p_other.m_mapperHelper;
        m_resourceSet = p_other.m_resourceSet;
        m_cosPropsWriter = p_other.m_cosPropsWriter;
        m_pendingExternalPatternCOSByFullyQualifiedResourceName = new HashMap<String,PendingExternalPatternCOS>();
    }

	private void writePatternCOSRIM(CompositeScreen p_patternCOS, boolean p_doRecursePatternCOSRefs, IrisResourceUrlSet p_refdIrisUrlSet) throws Exception {
		final String
		    domainPath = T24ResourceModelsGenerator.getDomain(null),
		    rimName = RESOURCE_TYPE.composite.toString() + EMUtils.camelCaseName(p_patternCOS.getName()),
		    rimFileName = rimName + ".rim";

		final URI rimURI = m_hhouseModelsFSA.getURI(domainPath.replace('.', '/') + '/' + rimFileName);

		m_rimWriter = new PrintWriter(m_resourceSet.getURIConverter().createOutputStream(rimURI));
		m_logger.info("Generating: " + rimURI + " from CompositeScreen: " + p_patternCOS.getName());

		writeOpeningRimElements(domainPath, rimName);
		writeRIMBody(p_patternCOS, rimName, p_refdIrisUrlSet);
		writeClosingRimElements();

		m_rimWriter.flush();
		m_rimWriter.close();

		m_logger.info("RIM for CompositeScreen" + p_patternCOS.getName() + " generated to: " + rimURI);

		final Resource resource = m_resourceSet.getResource(rimURI, true /* loadOnDemand */);
		m_mapperHelper.generateJava(resource);

		if (p_doRecursePatternCOSRefs && ! m_pendingExternalPatternCOSByFullyQualifiedResourceName.isEmpty()) {
		    for (PendingExternalPatternCOS pendingExternalPatternCOS: m_pendingExternalPatternCOSByFullyQualifiedResourceName.values()) {
                final LightweightPatternCOS2ResourceMapper mapper = new LightweightPatternCOS2ResourceMapper(this);

                try {
                    mapper.writePatternCOSRIM(pendingExternalPatternCOS.m_externalPatternCOS, p_doRecursePatternCOSRefs, pendingExternalPatternCOS.m_refdIrisUrlSet);
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
		    }
		}
	}

	private void writeOpeningRimElements(String p_domainPath, String p_rimName) {
		printLine("domain " + T24ResourceModelsGenerator.getDomain(null) + " {");

		incrementIndentLevel();

		for (String useExpn: USE_EXPRESSIONS) {
			printLineAtCurrentIndent("use " + useExpn);
		}

		printBlankLine();
		printLineAtCurrentIndent("rim " + p_rimName + " {");
		incrementIndentLevel();
	}

	private void writeRIMBody(CompositeScreen p_patternCOS, String p_rimName, IrisResourceUrlSet p_refdIrisUrlSet) throws Exception {
		printLineAtCurrentIndent("basepath: \"" + BASEPATH + '"');
		printLineAtCurrentIndent("command NoopGET");
		addPatternCOSResourceDefRecursive(p_patternCOS.getDomain(), p_patternCOS.getName(), p_patternCOS, p_patternCOS.getPanels(), true, p_rimName, p_refdIrisUrlSet);
	}

	private void addPatternCOSResourceDefRecursive(
	    String p_domain,
	    String p_esonModelElemName,
	    COSPatternContainer p_cosPatternContainer,
	    EList<COSPanel> p_panelList,
	    boolean p_isForTopLevelCOS,
	    String p_rimName,
	    IrisResourceUrlSet p_refdIrisUrlSet
	) throws Exception {
		final String
		    cosResourceName = ParameterParser.getResourceName(RESOURCE_TYPE.composite, p_esonModelElemName, null).getResourceName(),
		    /*
		     * The link relation for a COS resource gives the (pathless/extension-less) filename of the properties file we generate for that COS (all
		     * such properties files being sorted in a single directory known to the Edge runtime).
		     *
		     * Originally we generated names featuring the value of the CompositeScreen.domain attribute in order that generated files with the same
		     * domain would naturally "clump together" in that single directory within which they are stored.
		     *
		     * Following external changes to optimise retrieval times for large menus (wherein the Edge front-end now sources menu responses from a model
		     * derived from a properties file produced as a byproduct of generating any menu rather than going to IRIS), this causes problems however.
		     * The problem is that the only way for the code that generates / updates the menu properties file to discover the domain (in order
		     * to derive the properties file-name we specify via the link relation) would be to *load* each referencing a COS would be to *load* that COS
		     * (which would be prohibitively slow).
		     *
		     * For this reason, we have to abandon the domain-name scoping of generated COS property filenames, using the unadorned COS resource-name
		     * instead (which can be deduced by the menu properties generation code WITHOUT having to load referenced COS(es).
		     *
		     * 15/01/2015 Simon Hayes
		     */
		    // propsFilenameNoExtn = ParameterParser.getResourceName(RESOURCE_TYPE.composite, p_domain + '.' + p_esonModelElemName, null).getResourceName();

            propsFilenameNoExtn = cosResourceName;

		final PatternCOSProperties cosProperties = new PatternCOSProperties(propsFilenameNoExtn + ".properties", p_cosPatternContainer);
		final List<PendingInlineChild> pendingChildRecords = new ArrayList<PendingInlineChild>(p_panelList.size());

        boolean
            isCOSResourceDefOpened = false,
            collectRefdIrisUrlForFirstPanelOnly = "Tabs".equals(p_cosPatternContainer.getPattern().getName());

        IrisResourceUrlSet refdIrisUrlSet = p_refdIrisUrlSet;

		for (COSPanel panel: p_panelList) {
			cosProperties.addPropertiesForPanel(panel);

			if (isCOSResourceDefOpened) {
			    if (collectRefdIrisUrlForFirstPanelOnly)
			        refdIrisUrlSet = null;
			}

			else {
				printBlankLine();
				printLineAtCurrentIndent("resource " + cosResourceName + " {");
				incrementIndentLevel();
				printLineAtCurrentIndent("type: item");
				printLineAtCurrentIndent("entity: Menu");
				printLineAtCurrentIndent("view: NoopGET");
				printLineAtCurrentIndent("relations [ \"" + RELATION_PREFIX + propsFilenameNoExtn + "\" ]");

				if (p_isForTopLevelCOS && (p_cosPatternContainer instanceof CompositeScreen)) {
					final String menuDslName = ((CompositeScreen) p_cosPatternContainer).getMenuDslName();

					if (menuDslName != null)
						writeGETForMenuDslName(menuDslName, refdIrisUrlSet);
				}

				isCOSResourceDefOpened = true;
			}

			if (panel instanceof SingleComponentPanel) {
			    final SingleComponentPanel singleComponentPanel = (SingleComponentPanel) panel;

				writeGETForChildPanel(singleComponentPanel, refdIrisUrlSet);
				appendPendingInlineChild(singleComponentPanel, refdIrisUrlSet, pendingChildRecords);
			}

            else if (panel instanceof MultiComponentPanel) {
			    final MultiComponentPanel multiComponentPanel = (MultiComponentPanel) panel;

				writeGETForChildPanel(multiComponentPanel, refdIrisUrlSet);
				appendPendingInlineChild(multiComponentPanel, refdIrisUrlSet, pendingChildRecords);
			}
		}

		if (isCOSResourceDefOpened) {
			decrementIndentLevel();
			printLineAtCurrentIndent("}");
		}

		m_cosPropsWriter.writeToFile(cosProperties);

		for (PendingInlineChild pendingInline: pendingChildRecords)
		    pendingInline.addPatternCOSResourceDefRecursive(p_domain, p_cosPatternContainer, p_rimName);
	}

	private void appendPendingInlineChild(SingleComponentPanel p_childPanel, IrisResourceUrlSet p_refdIrisUrlSet, List<PendingInlineChild> p_pendingChildRecords) {
        final InitialPanelContentSpec initialContentSpec = p_childPanel.getInitialContent();

        if (initialContentSpec instanceof InlineInitialCOS) {
            final InlineInitialCOS inlineInitialCOS = (InlineInitialCOS) initialContentSpec;
            p_pendingChildRecords.add(new PendingInlineChild(inlineInitialCOS.getName(), inlineInitialCOS, inlineInitialCOS.getPanels(), p_refdIrisUrlSet));
        }
	}

    private void appendPendingInlineChild(MultiComponentPanel p_childPanel, IrisResourceUrlSet p_refdIrisUrlSet, List<PendingInlineChild> p_pendingChildRecords) {
        p_pendingChildRecords.add(new PendingInlineChild(p_childPanel.getName(), p_childPanel, p_childPanel.getChildPanels(), p_refdIrisUrlSet));
    }

	private void writeGETForChildPanel(SingleComponentPanel p_panel, IrisResourceUrlSet p_refdIrisUrlSet) {
		final InitialPanelContentSpec initialContent = p_panel.getInitialContent();

		if (initialContent == null)
			writeGETTransition(EMPTY_COS_PANEL_RESOURCE_NAME, getEnglishPanelTitleOrNull(p_panel), null, p_refdIrisUrlSet);

		else if (initialContent instanceof InitialURL)
		    writeGETTransition(URL_COS_PANEL_RESOURCE_NAME, getEnglishPanelTitleOrNull(p_panel), null, p_refdIrisUrlSet);

		else if (initialContent instanceof ChildResourceSpec) {
			final String parameters = ((ChildResourceSpec) initialContent).getParameters();

			String targetResourceName = null;
			String refdInitialContentNullMsg = null;
			ParameterParserResult pResult = null;

			if (initialContent instanceof InitialCOS) {
				final InitialCOS initialPatternCOS = (InitialCOS) initialContent;
				final CompositeScreen refdPatternCOS = initialPatternCOS.getCompositeScreen();

				String t24Name = null;

				if (refdPatternCOS == null)
					refdInitialContentNullMsg = "CompositeScreen referenced as initialContent for panel: " + p_panel.getName() + " not found";

				else if (refdPatternCOS.eIsProxy())
					t24Name = m_proxyUtilProvider.get(initialPatternCOS.eResource().getURI()).getProxyCrossRefAsString(initialPatternCOS, refdPatternCOS);

				else
					t24Name = refdPatternCOS.getName();

				if (t24Name != null){
					pResult = ParameterParser.getResourceName(RESOURCE_TYPE.composite, t24Name, parameters);
					targetResourceName = T24ResourceModelsGenerator.getDomain(null) + '.' + RESOURCE_TYPE.composite + EMUtils.camelCaseName(t24Name) + '.' + pResult.getResourceName();
					m_pendingExternalPatternCOSByFullyQualifiedResourceName.put(targetResourceName, new PendingExternalPatternCOS(refdPatternCOS, p_refdIrisUrlSet));
				}
			}

			else if (initialContent instanceof InlineInitialCOS) {
				final InlineInitialCOS inlineInitialCOS = (InlineInitialCOS) initialContent;
				pResult = ParameterParser.getResourceName(RESOURCE_TYPE.composite, inlineInitialCOS.getName(), null);
				final String refdPatternCOSName = pResult.getResourceName();
				targetResourceName = T24ResourceModelsGenerator.getDomain(null) + '.' + refdPatternCOSName + '.' + refdPatternCOSName;
			}

			else if (initialContent instanceof InitialEnquiry) {
				final InitialEnquiry initialEnquiry = (InitialEnquiry) initialContent;
				final Enquiry refdEnquiry = initialEnquiry.getEnquiry();

				String t24Name = null;

				if (refdEnquiry == null)
					refdInitialContentNullMsg = "Enquiry referenced as initialContent within panel: " + p_panel.getName() + " not found";

				else if (refdEnquiry.eIsProxy())
					t24Name = m_proxyUtilProvider.get(initialEnquiry.eResource().getURI()).getProxyCrossRefAsString(initialEnquiry, refdEnquiry);

				else
					t24Name = refdEnquiry.getName();

				if (t24Name != null){
					pResult = ParameterParser.getResourceName(RESOURCE_TYPE.enquiry, t24Name, parameters);
					targetResourceName = T24ResourceModelsGenerator.getDomain(null) + '.' + RESOURCE_TYPE.enquiry + EMUtils.camelCaseName(t24Name) + '.' + pResult.getResourceName();
				}
			}

			else if (initialContent instanceof InitialScreen) {
				final InitialScreen initialScreen = (InitialScreen) initialContent;
				final Version refdVersion = initialScreen.getScreen();

				String t24Name = null;

				if (refdVersion == null)
					refdInitialContentNullMsg = "Version referenced as initialContent with panel: " + p_panel.getName() + " not found";

			    else if(refdVersion.eIsProxy())
					t24Name = m_proxyUtilProvider.get(initialScreen.eResource().getURI()).getProxyCrossRefAsString(initialScreen, refdVersion);

			    else
					t24Name = refdVersion.getT24Name();

				if (t24Name != null) {
					pResult = ParameterParser.getResourceName(RESOURCE_TYPE.version, t24Name, parameters);
					targetResourceName = T24ResourceModelsGenerator.getDomain(null) + '.' + RESOURCE_TYPE.version + EMUtils.camelCaseName(t24Name) + '.' + pResult.getResourceName();
				}
			}

			if (targetResourceName != null) {
				/*
				 * If targetResourceName is not null, this means that pResult is not null.
				 * However, better to be safe and double-check.
				 */
				writeGETTransition( targetResourceName, getEnglishPanelTitleOrNull(p_panel), (pResult == null) ? null : pResult.getParameters(), p_refdIrisUrlSet);

			}

			else if (refdInitialContentNullMsg != null) {
				m_logger.error(refdInitialContentNullMsg + " (TIP: sometimes this can be solved by retrying after doing a \"clean\" on the hothouse-models-gen project)");
			}
		}
	}

	private void writeGETForChildPanel(MultiComponentPanel p_panel, IrisResourceUrlSet p_refdIrisUrlSet) {
		final ParameterParserResult pResult = ParameterParser.getResourceName(RESOURCE_TYPE.composite, p_panel.getName(), p_panel.getParameters());
		writeGETTransition(pResult.getResourceName(), getEnglishPanelTitleOrNull(p_panel), pResult.getParameters(), p_refdIrisUrlSet);
	}

	private void writeGETForMenuDslName(String p_menuDslName, IrisResourceUrlSet p_refdIrisUrlSet) {
		final ParameterParserResult pResult = ParameterParser.getResourceName(RESOURCE_TYPE.menu, p_menuDslName, null);
		String menuResourceName = pResult.getResourceName();
		final String rimName = menuResourceName;
		final String fullyQualifiedMenuResourceName = T24ResourceModelsGenerator.getDomain(null) + "." + rimName + "." + menuResourceName;
		writeGETTransition(fullyQualifiedMenuResourceName, null /* p_transitionTitle */, null /* p_irisTransitionParams */, p_refdIrisUrlSet);
	}

	private void writeGETTransition(String p_targetResourceName, String p_transitionTitle, String p_irisTransitionParams, IrisResourceUrlSet p_refdIrisUrlSet) {
		final boolean isTargetResourceDefLocal = p_targetResourceName.indexOf('.') < 0;
		final String targetResourceNameQuotedIfNecessary = isTargetResourceDefLocal ? p_targetResourceName : '"' + p_targetResourceName + '"';

		final boolean haveTransitionTitle = (p_transitionTitle != null) && (p_transitionTitle.length() > 0);
		final boolean haveTransitionParams = p_irisTransitionParams != null;

		if (haveTransitionTitle || haveTransitionParams) {
			printLineAtCurrentIndent("GET -> " + targetResourceNameQuotedIfNecessary + " {" );
			incrementIndentLevel();

			if (haveTransitionTitle)
				printLineAtCurrentIndent("title: \"" + p_transitionTitle + '"');

			if (haveTransitionParams)
				printLineAtCurrentIndent(p_irisTransitionParams);

			decrementIndentLevel();
			printLineAtCurrentIndent("}");
		}

		else {
			printLineAtCurrentIndent("GET -> " + targetResourceNameQuotedIfNecessary + " { }");
		}

		if (! ((p_refdIrisUrlSet == null) || (EMPTY_COS_PANEL_RESOURCE_NAME == p_targetResourceName) || (URL_COS_PANEL_RESOURCE_NAME == p_targetResourceName))) {
		    p_refdIrisUrlSet.addEntryFor(p_targetResourceName, p_irisTransitionParams, p_transitionTitle);
		}
	}

	private void writeClosingRimElements() {
		decrementIndentLevel();
		printLineAtCurrentIndent("}");
		decrementIndentLevel();
		printLineAtCurrentIndent("}");
	}

	private void incrementIndentLevel() {
		indentStr += '\t';
	}

	private void decrementIndentLevel() {
		int currLen = (indentStr == null) ? 0 : indentStr.length();
		indentStr = (currLen > 0) ? indentStr.substring(1) : "";
	}

	private void printLine(String s) {
		m_rimWriter.println(s);

	}
	private void printLineAtCurrentIndent(String s) {
		if (s != null)
			printLine(indentStr + s);
	}

	private void printBlankLine() {
		m_rimWriter.println();
	}

	private static String getEnglishPanelTitleOrNull(COSPanel p_panel) {
		final EList<Translation> panelTitleTranslations = p_panel.getTitle();
		final int numTranslations = (panelTitleTranslations == null) ? 0 : panelTitleTranslations.size();

		String result = null;

		for (int i = 0; i < numTranslations; ++i) {
			Translation titleTranslation = panelTitleTranslations.get(i);

			if ((titleTranslation != null) && "en".equals(titleTranslation.getLanguage().getName())) {
				result = titleTranslation.getValue();
				break;
			}
		}

		return result;
	}

    private class PendingInlineChild {
        private final String m_pendingChildName;
        private final COSPatternContainer m_pendingChild;
        private final EList<COSPanel> m_grandChildPanels;
        private final IrisResourceUrlSet m_refdIrisUrlSet;

        PendingInlineChild(String p_pendingChildName, COSPatternContainer p_pendingChild, EList<COSPanel> p_pendingGrandChildPanels, IrisResourceUrlSet p_refdIrisUrlSet) {
            m_pendingChildName = p_pendingChildName;
            m_pendingChild = p_pendingChild;
            m_grandChildPanels = p_pendingGrandChildPanels;
            m_refdIrisUrlSet = p_refdIrisUrlSet;
        }

        void addPatternCOSResourceDefRecursive(String p_domain, COSPatternContainer p_cosPatternContainer, String p_rimName) throws Exception {
            LightweightPatternCOS2ResourceMapper.this.addPatternCOSResourceDefRecursive(
                p_domain,
                m_pendingChildName,
                m_pendingChild,
                m_grandChildPanels,
                false, // p_isForTopLevelCOS
                p_rimName,
                m_refdIrisUrlSet
            );
        }
    }

    private class PendingExternalPatternCOS {
        final CompositeScreen m_externalPatternCOS;
        final IrisResourceUrlSet m_refdIrisUrlSet;

        PendingExternalPatternCOS(CompositeScreen p_externalPatternCOS, IrisResourceUrlSet p_refdIrisUrlSet) {
            m_externalPatternCOS = p_externalPatternCOS;
            m_refdIrisUrlSet = p_refdIrisUrlSet;
        }
    }
}
