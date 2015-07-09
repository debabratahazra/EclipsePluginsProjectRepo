package com.odcgroup.t24.application.localrefapplication;

import com.odcgroup.t24.common.importer.IT24ImportModel;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public interface ILocalRefApplicationImporter extends IT24ImportModel {

	ILocalRefApplicationSelector getApplicationSelector();
}
