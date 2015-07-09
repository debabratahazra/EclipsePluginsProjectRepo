package com.odcgroup.mdf.editor.ui.providers.label;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.mdf.editor.ui.providers.MdfBaseProvider;

/**
 * @version 1.0
 * @author Glaude
 */
public class MdfTableLabelProvider extends MdfBaseProvider implements ITableLabelProvider {

    /**
     * Constructor for MdfTableLabelProvider
     */
    public MdfTableLabelProvider() {
        super();
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object obj, int columnIndex) {
        if (columnIndex == 0) {
        	return getImage(obj);
        }
        
        return null;
    }

}
