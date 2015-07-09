package com.odcgroup.t24.applicationimport.reader;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class IgnoringValidationEventHandler implements ValidationEventHandler {

	@Override
	public boolean handleEvent(ValidationEvent event) {
		return true;
	}

}
