package com.odcgroup.t24.server.external.model;

import java.util.List;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * @author atripod
 *
 * @param <T>
 */
public interface IExternalLoader {
	
	/**
	 * @throws T24ServerException
	 */
	void open() throws T24ServerException;

	/**
	 */
	void close();

	/**
	 * @return
	 * @throws T24ServerException
	 */
	<T extends IExternalObject>
	List<T> getObjectDetails() throws T24ServerException;

	/**
	 * @param detail
	 * @return
	 * @throws T24ServerException
	 */
	<T extends IExternalObject>
	String getObjectAsXML(T detail) throws T24ServerException;
}