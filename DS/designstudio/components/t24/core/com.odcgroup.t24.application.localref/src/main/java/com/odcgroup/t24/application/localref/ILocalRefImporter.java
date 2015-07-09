package com.odcgroup.t24.application.localref;

import com.odcgroup.t24.common.importer.IT24ImportModel;

/**
 * @author ssreekanth
 *
 */
public interface ILocalRefImporter extends IT24ImportModel {

	ILocalRefSelector getLocalRefSelector();

}
