package com.temenos.t24.tools.eclipse.basic.views.checkDependency;

import java.util.ArrayList;
import java.util.List;

/** This object holds the input for the CheckDependencyView.*/            

public class T24DependencyDetail {

    private List<T24ItemDetail> itemDetails;
    private List<T24UpdateDetails> updateDetails;

    public T24DependencyDetail() {
        this.itemDetails = new ArrayList<T24ItemDetail>();
        this.updateDetails = new ArrayList<T24UpdateDetails>();
    }

    public void setItemDetails(List<T24ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public void setUpdateDetails(List<T24UpdateDetails> updateDetails) {
        this.updateDetails = updateDetails;
    }

    public List<T24ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public List<T24UpdateDetails> getUpdateDetails() {
        return updateDetails;
    }
}
