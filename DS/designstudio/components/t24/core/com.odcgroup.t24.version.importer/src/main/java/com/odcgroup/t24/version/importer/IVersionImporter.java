package com.odcgroup.t24.version.importer;

import com.odcgroup.t24.common.importer.IT24ImportModel;

/**
 */
public interface IVersionImporter extends IT24ImportModel {

	IVersionSelector getVersionSelector();

}
