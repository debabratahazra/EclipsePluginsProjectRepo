package com.odcgroup.t24.enquiry.translation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.Graph;
import com.odcgroup.t24.enquiry.enquiry.Label;
import com.odcgroup.t24.enquiry.enquiry.LabelOperation;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.translation.translationDsl.Translations;

public final class EnquiryModelTranlsationUtill {

	private static void add(List<Translations> list, Translations translations) {
		if (translations != null) {
			list.add(translations);
		}
	}
	
	/**
	 * @param enquiry
	 * @return the list contains no null.
	 */
	public static List<Translations> getAllEnqiryTranslations(Enquiry enquiry) {
		List<Translations> result = new ArrayList<Translations>();
		add(result, enquiry.getDescription());
		EList<EnquiryHeader> headerList = enquiry.getHeader();
		for (EnquiryHeader header : headerList) {
			add(result, header.getLabel());
		}
		if (enquiry.getCustomSelection() != null) {
			EList<Selection> selectionList = enquiry.getCustomSelection().getSelection();
			for (Selection selection : selectionList) {
				add(result, selection.getLabel());
			}
		}
		EList<Field> fieldsList = enquiry.getFields();
		for (Field field : fieldsList) {
			add(result, field.getLabel());
			if (field.getOperation() != null && field.getOperation() instanceof LabelOperation) {
				add(result, ((LabelOperation) field.getOperation()).getLabel());
			}
		}
		EList<Tool> toolsList = enquiry.getTools();
		for (Tool tool : toolsList) {
			add(result, tool.getLabel());
		}
		Graph graph = enquiry.getGraph();
		if (graph != null) {
			EList<Label> lablesList = graph.getLabels();
			for (Label label : lablesList) {
				add(result, label.getDescription());

			}
		}
		return result;
	}

}
