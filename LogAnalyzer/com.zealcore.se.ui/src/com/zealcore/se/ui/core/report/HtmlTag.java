package com.zealcore.se.ui.core.report;

public enum HtmlTag {
    TR("tr"), TD("td"), H1("h1"), H2("h2"), H3("h3"), BR("br");

    private static final String OPEN = "<%1$s>";

    private static final String CLOSE = "</%1$s>";

    private static final String WITH_CLASS = "<%1$s class=\"%2$s\">";

    private String name;

    private HtmlTag(final String name) {
        this.name = name;
    }

    public String open() {
        return String.format(HtmlTag.OPEN, this.name);
    }

    public String close() {
        return String.format(HtmlTag.CLOSE, this.name);
    }

    public String withClass(final String clazz) {
        return String.format(HtmlTag.WITH_CLASS, this.name, clazz);
    }

    @Override
    public String toString() {
        return open();
    }
}
