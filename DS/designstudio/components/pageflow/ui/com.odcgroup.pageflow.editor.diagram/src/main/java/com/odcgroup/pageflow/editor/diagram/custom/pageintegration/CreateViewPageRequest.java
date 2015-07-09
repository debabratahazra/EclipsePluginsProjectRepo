package com.odcgroup.pageflow.editor.diagram.custom.pageintegration;
import org.eclipse.gef.requests.CreateRequest;

/**
 * @author pkk
 *
 */
public class CreateViewPageRequest extends CreateRequest {
	
	/**
	 * 
	 */
	private String pageUri;

	/**
	 * @return
	 */
	public String getPageUri() {
		return pageUri;
	}

	/**
	 * @param pageUri
	 */
	public void setPageUri(String pageUri) {
		this.pageUri = pageUri;
	}	

}
