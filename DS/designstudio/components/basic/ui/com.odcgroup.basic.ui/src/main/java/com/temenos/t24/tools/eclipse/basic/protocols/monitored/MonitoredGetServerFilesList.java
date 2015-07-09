package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

public class MonitoredGetServerFilesList extends AbstractMonitoredAction {

    public Response execute(T24_VIEW_ITEM_CATEGORY viewItem) {
        String[] params = new String[]{viewItem.getCategory()};
        return super.execute(params);
    }
    
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        String category = params[0];
        return new ThreadGetServerFilesList(MonitoredGetServerFilesList.this, T24_VIEW_ITEM_CATEGORY.find(category));
    }
}
