package com.odcgroup.service.gen.t24.internal.data;

import com.odcgroup.service.gen.t24.internal.exceptions.InvalidModelException;

/**
 * 
 * Defines T24 Markers for code generation
 *
 * @author sjunejo
 *
 */
public enum Markers {
	FM ("@FM"),
	VM ("@VM"),
	SVM("@SVM");
	
	private String marker;
	
	private Markers (String marker) {
		this.marker = marker;
	}
	
	public String getMarker() {
		return this.marker;
	}
	
	public static String getMarker(int level) throws InvalidModelException {
		if (level == 1) 
			return FM.getMarker();
		else if (level == 2)
			return VM.getMarker();
		else if (level == 3)
			return SVM.getMarker();
		else
			throw new InvalidModelException("Markers for level '" + level + "' not supported!");
	}
};
