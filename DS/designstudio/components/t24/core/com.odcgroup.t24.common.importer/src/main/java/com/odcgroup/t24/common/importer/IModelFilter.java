package com.odcgroup.t24.common.importer;

public interface IModelFilter<T> {

	boolean accept(T model);

}
