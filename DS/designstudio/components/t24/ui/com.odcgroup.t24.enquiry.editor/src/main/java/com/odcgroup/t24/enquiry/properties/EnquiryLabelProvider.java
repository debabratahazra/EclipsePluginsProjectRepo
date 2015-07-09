package com.odcgroup.t24.enquiry.properties;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;

import com.odcgroup.t24.enquiry.editor.part.DrillDownEditPart;
import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.FixedSelectionEditPart;
import com.odcgroup.t24.enquiry.editor.part.HeaderEditPart;
import com.odcgroup.t24.enquiry.editor.part.OutputFieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.RoutineEditPart;
import com.odcgroup.t24.enquiry.editor.part.SelectionCriteriaEditPart;
import com.odcgroup.t24.enquiry.editor.part.SelectionEditPart;
import com.odcgroup.t24.enquiry.editor.part.TargetEditPart;
import com.odcgroup.t24.enquiry.editor.part.TargetMappingEditPart;
import com.odcgroup.t24.enquiry.editor.part.TitleEditPart;
import com.odcgroup.t24.enquiry.editor.part.ToolEditPart;
import com.odcgroup.t24.enquiry.editor.part.WebServiceEditPart;

/**
 * 
 * @author phanikumark
 * 
 */
public class EnquiryLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element == null) {
			return "";
		}

		if (!(element instanceof IStructuredSelection)) {
			return "";
		}

		IStructuredSelection ss = (IStructuredSelection) element;
		if (ss.isEmpty() || ss.size() > 1) {
			return "";
		}

		Object obj = ss.getFirstElement();
		if (obj instanceof EnquiryDiagramEditPart) {
			return "Enquiry";
		} else if (obj instanceof FixedSelectionEditPart) {
			return "Fixed Selection";
		} else if (obj instanceof SelectionEditPart) {
			return "Selection";
		} else if (obj instanceof HeaderEditPart) {
			return "Eqnuiry Header";
		} else if (obj instanceof FieldEditPart) {
			return "Column";
		} else if (obj instanceof ToolEditPart) {
			return "Tool";
		} else if (obj instanceof RoutineEditPart) {
			return "Routine";
		} else if (obj instanceof TargetEditPart) {
			return "Application Target";
		} else if (obj instanceof TargetMappingEditPart) {
			return "Field Mapping";
		} else if (obj instanceof WebServiceEditPart) {
			return "Web Service";
		} else if (obj instanceof DrillDownEditPart) {
			return "Drilldown";
		} else if (obj instanceof SelectionCriteriaEditPart) {
			return "Criteria";
		} else if (obj instanceof OutputFieldEditPart) {
			return "Field";
		} else if (obj instanceof TitleEditPart) {
			return "Title";
		}
		return super.getText(element);
	}

}
