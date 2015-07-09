package com.odcgroup.t24.server.external.model.internal;

import java.io.InputStream;
import java.util.Map;

import com.odcgroup.t24.server.external.model.IExternalLoader;

public interface IInteractionLoader extends IExternalLoader {
	
	void setModelName(String name);
	
	InputStream getRIM() throws T24ServerException;
	
	Map<String,String> getRIMsMap() throws T24ServerException;

}
