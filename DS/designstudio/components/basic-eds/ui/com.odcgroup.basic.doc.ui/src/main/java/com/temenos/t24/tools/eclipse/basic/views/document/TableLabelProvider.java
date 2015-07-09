package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.parser.TableInfo;

public class TableLabelProvider extends LabelProvider implements ITableLabelProvider {

    public Image getColumnImage(Object arg0, int arg1) {
        return null;
    }

    public String getColumnText(Object arg0, int arg1) {
        TableInfo inXML = (TableInfo) arg0;
        switch (arg1) {
            case 0:
                return inXML.getTableName();
            case 1:
                return DocInputDecorator.wrapText(inXML.getDescription());
            case 2:
                return inXML.getType();
            case 3:
                return inXML.getClassification();
        }
        return "";
    }
}
