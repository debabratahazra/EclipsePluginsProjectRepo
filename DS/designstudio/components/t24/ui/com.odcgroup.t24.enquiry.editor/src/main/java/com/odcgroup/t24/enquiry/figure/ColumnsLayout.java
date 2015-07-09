package com.odcgroup.t24.enquiry.figure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;

import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 *
 * @author phanikumark
 *
 */
public class ColumnsLayout extends CustomLayout {
	
	@SuppressWarnings("rawtypes")
	@Override
	public void layout(IFigure parent) {
		List children = getSortedChildren(parent.getChildren());
		doLayout(parent, children);
	}
	
	@SuppressWarnings("rawtypes")
	private List<FieldFigure> getSortedChildren(List children) {
		List<FieldFigure> sorted = new ArrayList<FieldFigure>();
		Map<Field, FieldFigure> map = new LinkedHashMap<Field, FieldFigure>();
		List<Field> fieldList = new ArrayList<Field>();
		for(int i=0;i<children.size();i++) {
			FieldFigure ff = (FieldFigure) children.get(i);
			fieldList.add(ff.getField());
			map.put(ff.getField(), ff);
		}
		EnquiryUtil.sortEnquiryFields(fieldList);
		for (Field field : fieldList) {
			sorted.add(map.get(field));
		}
		return sorted;
	}

}
