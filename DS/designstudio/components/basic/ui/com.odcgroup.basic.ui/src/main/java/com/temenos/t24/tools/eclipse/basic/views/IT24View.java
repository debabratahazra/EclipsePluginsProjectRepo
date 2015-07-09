package com.temenos.t24.tools.eclipse.basic.views;

import org.eclipse.jface.viewers.TableViewer;

public interface IT24View {
    public String getViewID();
    public TableViewer getT24Viewer();    
    public void refresh();
}
