package com.temenos.t24.tools.eclipse.basic.views.document;

import java.util.ArrayList;
import java.util.List;

public class PresentationHelper {

    private static List<String> supportedTags;

    public static String prepareOverview(String text) {
        if (text == null) {
            return null;
        }
        text = clean(text);
        // text = beautify(text);
        return text;
    }

    private static String clean(String text) {
        // text = text.replaceAll("\\*", "");
        text = text.replaceAll("<doc>", "");
        text = text.replaceAll("</doc>", "");
        return text;
    }

    private PresentationHelper() {
        supportedTags = new ArrayList<String>();
        supportedTags.add("<b>");
        supportedTags.add("<p>");
        supportedTags.add("<li>");
        supportedTags.add("<br>");
        supportedTags.add("<img>");
        supportedTags.add("<a>");
    }
}
