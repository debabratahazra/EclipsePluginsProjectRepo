package com.temenos.t24.tools.eclipse.basic.document;

import com.temenos.t24.tools.eclipse.basic.document.parser.BatchesWrapper;
import com.temenos.t24.tools.eclipse.basic.document.parser.TablesWrapper;

/**
 * Contract responsible for containing the info related to the overview,tables
 * and cob related to each component
 * 
 * @author ragajanani
 * 
 */
public interface IDocInput {

    public String getItemName();

    public ViewSection activeSection();

    /**
     * Contains the overview info
     * 
     * @return String
     */
    public String getOverviewInput();

    /**
     * Contains the Table info
     * 
     * @return String
     */
    public TablesWrapper getTableInput();

    /**
     * Contains the COB info
     * 
     * @return String
     */
    public BatchesWrapper getCobInput();
}
