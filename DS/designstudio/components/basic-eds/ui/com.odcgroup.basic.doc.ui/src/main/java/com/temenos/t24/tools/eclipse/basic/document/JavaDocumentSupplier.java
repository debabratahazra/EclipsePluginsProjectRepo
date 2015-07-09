package com.temenos.t24.tools.eclipse.basic.document;

public class JavaDocumentSupplier {

    private static JavaDocumentSupplier INSTANCE = new JavaDocumentSupplier();

    private JavaDocumentSupplier() {
    }

    public static JavaDocumentSupplier getInstance() {
        return INSTANCE;
    }

    public String[] getJavaClasses(String product, String component) {
        return new String[] { "rules-engine", "antlr" };
    }

    public String getJavaClassDocument(String product, String component, String javaClass) {
        return "This java package is used in rules-engine T24 component";
    }
}
