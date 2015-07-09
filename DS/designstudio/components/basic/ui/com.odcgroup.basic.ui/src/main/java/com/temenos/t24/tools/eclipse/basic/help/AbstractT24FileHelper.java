package com.temenos.t24.tools.eclipse.basic.help;

import java.io.File;

import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineDocumentLookup;
import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;

/**
 * helper class which helps to get the basic file from current user workspace
 * using subroutine name (with out basic extension)
 * 
 * @author sbharathraja
 * 
 */
public abstract class AbstractT24FileHelper {

    /**
     * get the sub routine information from subroutine_lookup.xml file
     * 
     * @param subRoutineName - name of the basic file, without extension
     * @return instance of class {@link SubroutineInfo}
     */
    public SubroutineInfo getSubRoutineInformation(String subRoutineName) {
        return SubroutineDocumentLookup.getInstance().getSubroutineInfo(subRoutineName);
    }

    /**
     * get the basic file corresponding to given subroutine name from user
     * current workspace.
     * 
     * @param subRoutineName - name of the subroutine
     * @return basic file
     */
    public abstract File getBasicFile(String subRoutineName);

    /**
     * get the stripped out comments xml file path with the help of given
     * subroutine name and the path given in preferences page by user
     * 
     * @param subRoutineName - name of the subroutine accessed from in-line doc
     *            view
     * @return full path of respective xml file
     */
    public abstract String getCommentsXMLPath(String subRoutineName);
}
