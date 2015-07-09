package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.document.parser.JobInfo;

public class JobTableLabelProvider extends LabelProvider implements ITableLabelProvider {

    public Image getColumnImage(Object arg0, int arg1) {
        return null;
    }

    public String getColumnText(Object arg0, int arg1) {
        JobInfo jobInfo = (JobInfo) arg0;
        switch (arg1) {
            case 0:
                return jobInfo.getJobName();
            case 1:
                return DocInputDecorator.wrapText(jobInfo.getDescription());
        }
        return "";
    }
}
