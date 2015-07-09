package com.odcgroup.t24.common.importer;


public interface IT24ImportModel extends IImportModel {

	IServerSelector getServerSelector();

	IContainerSelector getContainerSelector();

}
