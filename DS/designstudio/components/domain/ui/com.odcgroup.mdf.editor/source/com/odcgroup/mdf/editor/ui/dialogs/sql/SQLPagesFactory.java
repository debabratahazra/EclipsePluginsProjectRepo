package com.odcgroup.mdf.editor.ui.dialogs.sql;

import java.util.List;

import com.odcgroup.mdf.editor.ui.dialogs.DialogPagesFactory;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class SQLPagesFactory implements DialogPagesFactory {

    public void addPages(MdfModelElement model, List pages) {
        if ((model instanceof MdfClass) 
        		|| (model instanceof MdfProperty) 
        		|| (model instanceof MdfBusinessType)
        		|| (model instanceof MdfDomain)) {
            pages.add(new SQLAnnotationExtensionsPage(model));
        }
    }
}
