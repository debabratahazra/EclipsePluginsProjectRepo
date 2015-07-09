package com.temenos.t24.tools.eclipse.basic.document.parser;

import java.util.ArrayList;
import java.util.List;

public class BatchesWrapper {

    private String description;
    private List<BatchInfo> batches;
    public static BatchesWrapper EMPTY = new BatchesWrapper("NO BATCHES AVAILABLE");

    public BatchesWrapper(String description) {
        this.description = description;
        batches = new ArrayList<BatchInfo>();
    }

    public String getDescription() {
        return description;
    }

    public List<BatchInfo> getBatches() {
        return batches;
    }

    public void addBatch(BatchInfo batch) {
        batches.add(batch);
    }
}
