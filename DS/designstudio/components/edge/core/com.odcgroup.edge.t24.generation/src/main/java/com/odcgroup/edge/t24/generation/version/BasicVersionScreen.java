package com.odcgroup.edge.t24.generation.version;


import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTabPaneWrapper;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.VersionMapper;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.RichHelper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24.generation.version.util.RichLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * Used to build a basic version screen, which is created as a rich presentation type.
 *
 * <b>NB:</b> This is now abstract as the 3D versions should be used instead.
 * 
 * @author saleem.akbar
 *
 */
public abstract class BasicVersionScreen extends AbstractBasicVersionScreen<VersionMapper>
{
    /** The Constant LOGGER with value of {@value}. */
    private static final Logger LOGGER = GenLogger.getLogger(BasicVersionScreen.class);
    
    /** The version name. */
    private final String m_versionName;
    
    /** The version. */
    private final Version m_version;
    
	/**
     * Instantiates a new basic version screen.
     *
     * @param p_versionName the version name
     * @param p_version the version
     * @param p_mapper the mapper
     * @param p_templateProject the template project
     * @param p_languageMapHelper the language map helper
     * @throws Exception the exception
     */
    public BasicVersionScreen(String p_versionName, Version p_version, VersionMapper p_mapper, TemplateProject p_templateProject, LanguageMapHelper p_languageMapHelper) throws Exception
    {
        super( p_mapper, p_templateProject, p_versionName, p_versionName, p_languageMapHelper );
        
        m_version = p_version;
        
        m_versionName = p_versionName;
    }
    
    /**
     * Builds the header.
     *
     * @throws Exception the exception
     */
    @Override
    protected void buildHeader(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer) throws Exception
    {
        addMessageHeading(p_processContainer, p_presentationContainer);
        addReadOnlyHeading(p_processContainer, p_presentationContainer);
    }

    /**
     * Builds the body.
     *
     * @throws Exception the exception
     */
    @Override
    protected void buildBody(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer) throws Exception
    {
        EList<Version> associatedVersions = getVersion().getAssociatedVersions();
        
        boolean hasAssociatedVersions = associatedVersions != null && associatedVersions.size() > 0;

        TextTranslations mainHeader = VersionUtility.getMainHeading( getMapper(), getVersion(), true /* needed for tabs */ );
        
        boolean                     conjoinedTabs  = false;
        RichHelper.ETabDisplay      tabDisplay     = null;
        RichHelper.ESectionDisplay  sectionDisplay = null;

        if  ( hasAssociatedVersions )
        {
            AssociatedVersionsPresentationPattern pattern = getVersion().getAssociatedVersionsPresentationPattern();
            
            if ( pattern == null )
                pattern = AssociatedVersionsPresentationPattern.NONE;
            
            switch (pattern)
            {
                case NONE:      tabDisplay      = RichHelper.ETabDisplay.JQUERY_TAB; 
                                sectionDisplay  = RichHelper.ESectionDisplay.JQUERY_TABSECTION;
                                conjoinedTabs   = false;
                                
                                break;
                                
                case TABS:      tabDisplay      = RichHelper.ETabDisplay.JQUERY_TAB; 
                                sectionDisplay  = RichHelper.ESectionDisplay.JQUERY_TABSECTION;
                                conjoinedTabs   = hasAssociatedVersions;
                                
                                break;
                                
                case ACCORDIONS:tabDisplay      = RichHelper.ETabDisplay.ACCORDIAN_TAB; 
                                sectionDisplay  = RichHelper.ESectionDisplay.ACCORDIAN_SECTION;
                                conjoinedTabs   = hasAssociatedVersions;
                                
                                break;
                                
                case VERTICAL:  tabDisplay      = null;
                                sectionDisplay  = null;
                                conjoinedTabs   = false;
                                
                                break;
                                
                default:        throw new UnsupportedOperationException("Unknown presentation pattern of " + pattern);
            }
        }
        
        // Now set up the presentation
        //
        PresentationObjectWrapper<?> mainPresContainer = p_presentationContainer;

        RichHTMLPresentationFormatBreakWrapper tabSection = null;

        if  ( conjoinedTabs )
        {
            tabSection = RichHelper.createSection( getFormContext(), sectionDisplay );
            
            mainPresContainer.addChild( tabSection );
            
            RichHTMLPresentationTabPaneWrapper mainTab = RichHelper.createTab( getFormContext(), tabDisplay, mainHeader, m_languageMapHelper );
            
            tabSection.addChild( mainTab );
            
            mainPresContainer = mainTab;
        }
        else
        {
            addMainHeading(mainHeader, p_processContainer, mainPresContainer);
        }
        
        RichLayoutManager layMan = new RichLayoutManager(getFormContext(), p_processContainer, mainPresContainer, m_languageMapHelper);
        
        @SuppressWarnings("unchecked")
        BasicVersionSection<BasicVersionScreen> vsection = newVersionSection( getVersion(), layMan );
        
        vsection.build();

        if  ( hasAssociatedVersions )
        {
            // Set things up for the next set of fields
            //
            layMan.revertToInitialState();

            if  ( conjoinedTabs )
            {
                layMan.resetPresentationContainers( tabSection );
            }
            else if ( sectionDisplay != null )
            {
                tabSection = RichHelper.createSection( getFormContext(), sectionDisplay );

                layMan.addPresentationChild( tabSection );
                
                layMan.resetPresentationContainers( tabSection );
            }
            
            addAssociatedVersions(vsection, associatedVersions, tabDisplay, null, 0, true);
        }
        
        vsection.addOverrideGroup();
    }

    @SuppressWarnings({
            "rawtypes",
            "unchecked"
    })
    protected BasicVersionSection newVersionSection(Version p_version, RichLayoutManager p_containerLayoutManager)
    {
        return new VersionSectionWithPrimaryKey( this, p_version, p_containerLayoutManager );
    }

    /**
     * Builds the footer.
     */
    @Override
    protected void buildFooter(GenericNodeWrapper<?> p_processContainer, PresentationObjectWrapper<?> p_presentationContainer) throws Exception
    {
        
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public Version getVersion()
    {
        return m_version;
    }

    /**
     * Gets the version name.
     *
     * @return the version name
     */
    public String getVersionName()
    {
        return m_versionName;
    }
}
