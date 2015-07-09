package com.odcgroup.t24.application.importer;

import com.odcgroup.t24.common.importer.IT24ImportModel;

/**
 */
public interface IApplicationImporter extends IT24ImportModel {

	IApplicationSelector getApplicationSelector();

}
