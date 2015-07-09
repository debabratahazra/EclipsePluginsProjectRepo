package com.odcgroup.edge.t24.generation.version;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;

import com.odcgroup.edge.t24.generation.version.util.RichLayoutManager;
import com.odcgroup.t24.version.versionDSL.Version;
import com.temenos.connect.T24Browser.utils.IRISMvGroup.EGroupType;

/**
 * Associated version section, which is a section container within the main section
 *
 * @author sakbar
 */
public class AssociatedVersionSection<BasicProcessType extends AbstractBasicVersionScreen<?>> extends BasicVersionSection<BasicProcessType>
{
    /** The main version section. */
    private final BasicVersionSection<BasicProcessType> m_mainVersionSection;

    /**
     * Instantiates a new associated version section.
     *
     * @param p_mainVersionSection the main version section
     * @param p_associatedVersion the version
     * @param p_containerLayoutManager the container layout manager
     */
    public AssociatedVersionSection(BasicVersionSection<BasicProcessType> p_mainVersionSection, Version p_associatedVersion, RichLayoutManager p_containerLayoutManager)
    {
        super( p_mainVersionSection.getBasicProcess(), p_associatedVersion, p_containerLayoutManager );
        
        m_mainVersionSection = p_mainVersionSection;
    }
    
    @Override
    public void build() throws Exception
    {
        getContainerLayoutManager().addBlankLine();
        
        super.build();
    }
    /**
     * Adds the version data group.
     *
     * @return the property group wrapper
     */
    @Override
    protected PropertyGroupWrapper addVersionDataGroup()
    {
        // Associated versions use the same data groups as the main version
        //
        return m_mainVersionSection.getVersionDataGroup();
    }

    @Override
    public void addOverrideGroup()
    {
        // Skip adding of override data groups as we don't have separate ones for associated versions
        //
        //super.addOverrideGroup();
    }
    
    @Override
    protected String getIRISGroupName(IVersionFieldMapper p_field, EGroupType p_groupType)
    {
        // Associated versions use the same data group names as the main version
        //
        return m_mainVersionSection.getVersionName() + "_" + p_field.getProcessedName();
    }
}
