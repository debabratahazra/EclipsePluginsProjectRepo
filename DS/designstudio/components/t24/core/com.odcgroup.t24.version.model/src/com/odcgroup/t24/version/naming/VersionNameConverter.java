package com.odcgroup.t24.version.naming;

import com.odcgroup.workbench.dsl.naming.MultiDelimiterQualifiedNameConverter;



/**
 * IQualifiedNameConverter for Version.
 * 
 * @author Michael Vorburger
 */
public class VersionNameConverter extends MultiDelimiterQualifiedNameConverter {

    @Override
    public String[] getDelimiters() {
        return new String[] { ":", "," };
    }
    
}
