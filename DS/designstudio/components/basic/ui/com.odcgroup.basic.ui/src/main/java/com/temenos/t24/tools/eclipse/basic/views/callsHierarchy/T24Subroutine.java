package com.temenos.t24.tools.eclipse.basic.views.callsHierarchy;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;

/**
 * A POJO represents a T24 Subroutine.
 * 
 * @author ssethupathi
 * 
 */
public class T24Subroutine {

    private String name;
    private String siteName;
    private CallLineDetail lineDetail;

    public T24Subroutine(String name, String siteName, CallLineDetail lineDetail) {
        this.name = name;
        this.siteName = siteName;
        this.lineDetail = lineDetail;
    }

    /**
     * Returns the name of the T24 Subroutine Eg,. EB.ACCOUNTING
     * 
     * @return T24 Subroutine name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the {@link RemoteSite} of this T24 Subroutine.
     * 
     * @return {@link RemoteSite}
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * Returns the call line detail.
     * 
     * @return call line detail
     */
    public CallLineDetail getLineDetail() {
        return lineDetail;
    }
}
