package com.odcgroup.t24.server.external.util;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public interface IT24Connection {
	
	public void sendOfsMessage(String message) throws T24ServerException;
	
	public void close() throws T24ServerException;

}
