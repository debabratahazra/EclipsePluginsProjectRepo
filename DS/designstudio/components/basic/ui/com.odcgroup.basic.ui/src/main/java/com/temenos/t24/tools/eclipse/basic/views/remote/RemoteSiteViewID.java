package com.temenos.t24.tools.eclipse.basic.views.remote;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;

public final class RemoteSiteViewID {

    private String primaryId = FTPClientImplConstants.REMOTE_VIEW_ID;
    private String secondaryId;

    public RemoteSiteViewID(String secondaryId) {
        this.secondaryId = secondaryId;
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public String getSecondaryId() {
        return secondaryId;
    }

    public String toString() {
        return secondaryId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof RemoteSiteViewID) {
            if (((RemoteSiteViewID) obj).getPrimaryId().equals(this.primaryId)
                    && ((RemoteSiteViewID) obj).getSecondaryId().equals(this.secondaryId)) {
                return true;
            }
        }
        return false;
    }
}
