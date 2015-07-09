package com.temenos.t24.tools.eclipse.basic.views.checkDependency;

/** This object represents each row in Item Details section in CheckDependencyView.*/

public class T24ItemDetail {

    private String itemName;
    private String type;
    private String componentName;
    private String productName;
    private boolean selected = false;

    T24ItemDetail(String item, String type, String product, String component) {
        this.itemName = item;
        this.type = type;
        this.componentName = component;
        this.productName = product;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
