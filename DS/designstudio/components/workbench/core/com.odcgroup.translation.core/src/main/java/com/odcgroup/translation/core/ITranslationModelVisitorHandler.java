package com.odcgroup.translation.core;

import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
public interface ITranslationModelVisitorHandler {

	void notify(ITranslation translation);
	
}
