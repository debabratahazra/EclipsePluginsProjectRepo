package com.temenos.t24.tools.eclipse.basic.views.inlineDocs;

import java.io.File;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateDocConstants;

/**
 * initialize the In Line Doc viewer. Actual need for this class is get hold of
 * the subroutine name and pass to {@link InLineDocsViewController} whenever it
 * needed.
 * 
 * @author sbharathraja
 */
public class InLineDocsViewInitializer {

    /** instance of {@link InLineDocsViewInitializer} */
    private static InLineDocsViewInitializer viewInitializer = new InLineDocsViewInitializer();
    /**
     * name of the subroutine which retrieved from double clicked node of
     * component landscape view
     */
    private String subRoutineName = "";

    /**
     * get the instance of {@link InLineDocsViewInitializer} (singleton pattern)
     * 
     * @return instance of {@link InLineDocsViewInitializer}
     */
    public static InLineDocsViewInitializer getInstance() {
        return viewInitializer;
    }

    /**
     * set the subroutine name
     * 
     * @param subRoutineName - name of the subroutine
     */
    public void setSubRoutineName(String subRoutineName) {
        this.subRoutineName = subRoutineName;
    }

    /**
     * get the subroutine name
     * 
     * @return name of the basicFile without the .b extn
     */
    public String getSubRoutineName() {
        return subRoutineName;
    }

    /**
     * checking whether the InLineDoc folder available in T24CompDoc path or
     * not. The InLineDoc view has to be loaded only if that directory available
     * in that path.
     * 
     * @return true if avail, false otherwise
     */
    public boolean isEligibleToShowInLineDoc() {
        String t24CompDocPath = DocumentFileUtil.getRoot();
        if (t24CompDocPath == null || t24CompDocPath.equalsIgnoreCase(""))
            return false;
        String inLineDocPath = t24CompDocPath + GenerateDocConstants.PATH_SEPARATOR + GenerateDocConstants.NAME_OF_STRIPPED_FOLDER;
        File inLineDocDir = new File(inLineDocPath);
        if (inLineDocDir.exists())
            return true;
        return false;
    }
}
