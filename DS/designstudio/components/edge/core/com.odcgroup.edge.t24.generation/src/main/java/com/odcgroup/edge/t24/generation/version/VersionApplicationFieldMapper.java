package com.odcgroup.edge.t24.generation.version;

import java.util.List;

import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * Represents the combined application fields (e.g. from MV/SV groups) and a Version field that has been specified for them.
 *
 * @author saleem.akbar
 *
 */
public class VersionApplicationFieldMapper extends VersionFieldMapper
{
    private final ApplicationFieldMapper m_applicationField;

    public VersionApplicationFieldMapper(Version p_version, Field p_field, ApplicationFieldMapper p_applicationField, int p_order)
    {
        super(p_version, p_field, p_applicationField.getMdfProperty(), p_order);
        m_applicationField = p_applicationField;
    }

    @Override
    public List<IVersionFieldMapper> getGroupInputFields()
    {
        return m_applicationField.getGroupInputFields();
    }
}
