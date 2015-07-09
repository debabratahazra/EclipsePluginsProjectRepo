package com.odcgroup.edge.t24.generation.version;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.version.util.RichLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * Section that ensures a primary key has been added.
 *
 * @author sakbar
 *
 */
public class VersionSectionWithPrimaryKey extends BasicVersionSection
{
    private static final Logger LOGGER = GenLogger.getLogger(VersionSectionWithPrimaryKey.class);

    public VersionSectionWithPrimaryKey(AbstractBasicVersionScreen<?> p_process, Version p_version, RichLayoutManager p_containerLayoutManager)
    {
        super( p_process, p_version, p_containerLayoutManager );
    }

    @Override
    protected List<IVersionFieldMapper> buildVersionFields()
    {
        List<IVersionFieldMapper> fields = new ArrayList<IVersionFieldMapper>(); 

        buildFieldsWithPrimaryKey( fields );
        
        return fields;
    }
    
    /**
     * Builds the fields with primary key.
     *
     * @param p_fields the fields
     */
    protected void buildFieldsWithPrimaryKey(List<IVersionFieldMapper> p_fields)
    {
        IVersionFieldMapper primaryKey = VersionUtility.getPrimaryKeyAndSortedFields( ((AbstractBasicVersionScreen<?>) getBasicProcess()).getMapper(), getVersion(), p_fields );
        
        if  ( primaryKey == null )
        {
            // Look for the key in the domain
            //
            List<?> primaryKeys = getVersion().getForApplication().getPrimaryKeys();
            
            if  ( primaryKeys == null || primaryKeys.size() == 0 )
            {
                LOGGER.error( "Unable to locate primary key for application: \"{}\"", getVersion().getForApplication());
            }
            else if ( primaryKeys.size() > 0 )
            {
                MdfProperty primaryKeyProp = (MdfProperty) primaryKeys.get( 0 );
                
                if  ( primaryKeys.size() > 1 )
                {
                    LOGGER.error( "Located multiple primary keys for application, will default to first one: {} {}", getVersion().getForApplication(), primaryKeys);
                }
                else
                {
                    LOGGER.info( "Primary key not specified in version, will default to applications primary key {} {}", getVersion().getForApplication(),primaryKeyProp );
                }
                
                primaryKey = new ApplicationPrimaryKeyFieldMapper( primaryKeyProp, getVersion().getForApplication(), -1 );
                
                p_fields.add( 0, primaryKey );
            }
        }
        else
        {
        	p_fields.remove(primaryKey);
        	/*
        	 * We need to return an instance of ApplicationPrimaryKeyFieldMapper otherwise we'll miss it in the ifp generation
        	 */
        	primaryKey = new ApplicationPrimaryKeyFieldMapper( primaryKey.getMdfProperty(), primaryKey.getForApplication(), -1 );
        	p_fields.add( 0, primaryKey );
        }
    }
}
