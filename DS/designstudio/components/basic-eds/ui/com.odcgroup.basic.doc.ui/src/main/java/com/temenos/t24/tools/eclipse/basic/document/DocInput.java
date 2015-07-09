package com.temenos.t24.tools.eclipse.basic.document;

import com.temenos.t24.tools.eclipse.basic.document.parser.BatchesWrapper;
import com.temenos.t24.tools.eclipse.basic.document.parser.TablesWrapper;

public class DocInput implements IDocInput {

    private String itemName;
    private String overViewInfo;
    private String product;
    private String component;
    private ViewSection activeSection;
    private BatchesWrapper batchesWrapper;
    private TablesWrapper tablesWrapper;

    public DocInput(String itemName, String overviewInfo, String product, String component, ViewSection activeSection) {
        this.itemName = itemName;
        this.overViewInfo = overviewInfo;
        this.product = product;
        this.component = component;
        this.activeSection = activeSection;
    }

    public String getItemName() {
        return itemName;
    }

    public ViewSection activeSection() {
        return activeSection;
    }

    public BatchesWrapper getCobInput() {
        if (batchesWrapper == null) {
            batchesWrapper = BatchDocumentSupplier.getBatches(product, component);
        }
        return batchesWrapper;
    }

    public String getOverviewInput() {
        return overViewInfo;
    }

    public TablesWrapper getTableInput() {
        if (tablesWrapper == null) {
            tablesWrapper = DataDocumentSupplier.getTables(product, component);
        }
        return tablesWrapper;
    }
}
