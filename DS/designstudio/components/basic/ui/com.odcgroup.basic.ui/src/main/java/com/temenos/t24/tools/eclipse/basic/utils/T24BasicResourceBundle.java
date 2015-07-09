package com.temenos.t24.tools.eclipse.basic.utils;

/**
 * Resource bundle is here only for content assist and it does not have any
 * locale specific resource management
 */
import java.util.ListResourceBundle;

public class T24BasicResourceBundle extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        final Object[][] contents = {};
        return contents;
    }
}
