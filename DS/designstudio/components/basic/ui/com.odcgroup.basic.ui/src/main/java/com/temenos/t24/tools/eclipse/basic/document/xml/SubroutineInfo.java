package com.temenos.t24.tools.eclipse.basic.document.xml;

public class SubroutineInfo {

    private String subroutineName;
    private String product;
    private String component;

    public SubroutineInfo(String subroutineName, String product, String component) {
        this.subroutineName = subroutineName;
        this.product = product;
        this.component = component;
    }

    public String getSubroutineName() {
        return subroutineName;
    }

    public String getProduct() {
        return product;
    }

    public String getComponent() {
        return component;
    }

    public String getComponentDir() {
        return component.toLowerCase();
    }

    public String getProductDir() {
        return product.toLowerCase();
    }
}
