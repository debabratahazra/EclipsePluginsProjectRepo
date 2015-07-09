package com.temenos.t24.tools.eclipse.basic.views.document;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.temenos.t24.tools.eclipse.basic.document.parser.JobInfo;

public class JobTableContentProvider implements IStructuredContentProvider {

    /*
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public Object[] getElements(Object arg0) {
        List<JobInfo> jobInfo = (List<JobInfo>) arg0;
        return jobInfo.toArray();
    }

    /**
     * {@inheritDoc}
     */
    public void dispose() {
    }

    /**
     *{@inheritDoc}
     */
    public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
    }
}
