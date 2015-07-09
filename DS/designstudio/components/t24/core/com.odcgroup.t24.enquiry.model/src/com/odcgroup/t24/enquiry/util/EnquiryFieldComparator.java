package com.odcgroup.t24.enquiry.util;

import java.util.Comparator;

import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 * Comparator class for Enquiry field.
 *
 * @author mumesh
 *
 */
public class EnquiryFieldComparator implements Comparator<Field> {
	

	@Override
	public int compare(Field firstField, Field secondField) {
		int fCol = firstField.getPosition().getColumn();
		int sCol = secondField.getPosition().getColumn();
		if(fCol > sCol){
			return 1;
		} else if(fCol < sCol){
			return -1;
		} else if (fCol == sCol) {
			String fRel = firstField.getPosition().getRelative();
			String sRel = firstField.getPosition().getRelative();
			if (fRel != null && sRel != null && fRel == sRel) {
				Integer fLine = firstField.getPosition().getLine();
				Integer sLine = secondField.getPosition().getLine();
				if (fLine != null && sLine != null) {
					if (fLine > sLine) {
						if (fRel.equals("+")) {
							return 1;
						} else if(fRel.equals("-")) {
							return -1;
						}
					} else if (fLine < sLine){
						if (fRel.equals("+")) {
							return -1;
						} else if(fRel.equals("-")) {
							return 1;
						}
					}
				}				
			}
		}
		return 0;
	}

}
