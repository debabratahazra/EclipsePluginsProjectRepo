package com.odcgroup.mdf.editor.ui.dialogs.annotations;

import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class AnnotationsPagesFactory implements DialogPagesFactory {
    public void addPages(MdfModelElement model, List pages) {
        //pages.add(new AnnotationsExtensionsPage(model));
        pages.add(new CustomAnnotationsExtensionsPage(model));
    }
}
