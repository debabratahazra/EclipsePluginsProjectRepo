package com.odcgroup.t24.enquiry.properties;

import org.eclipse.ui.views.properties.tabbed.AbstractTypeMapper;

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
public class EnquiryTypeMapper extends AbstractTypeMapper {

	@Override
	public Class mapType(Object object) {
		if (object instanceof EnquiryDiagramEditPart) {
			return ((EnquiryDiagramEditPart) object).getModel().getClass();
		} else if (object instanceof FixedSelectionEditPart) {
			return ((FixedSelectionEditPart) object).getModel().getClass();
		} else if (object instanceof SelectionEditPart) {
			return ((SelectionEditPart) object).getModel().getClass();
		} else if (object instanceof HeaderEditPart) {
			return ((HeaderEditPart) object).getModel().getClass();
		} else if (object instanceof FieldEditPart) {
			return ((FieldEditPart) object).getModel().getClass();
		} else if (object instanceof ToolEditPart) {
			return ((ToolEditPart) object).getModel().getClass();
		} else if (object instanceof RoutineEditPart) {
			return ((RoutineEditPart) object).getModel().getClass();
		} else if (object instanceof TargetEditPart) {
			return ((TargetEditPart) object).getModel().getClass();			
		} else if (object instanceof TargetMappingEditPart) {
			return ((TargetMappingEditPart) object).getModel().getClass();						
		} else if (object instanceof WebServiceEditPart) {
			return ((WebServiceEditPart) object).getModel().getClass();						
		} else if (object instanceof DrillDownEditPart) {
			return ((DrillDownEditPart) object).getModel().getClass();						
		} else if (object instanceof SelectionCriteriaEditPart) {
			return ((SelectionCriteriaEditPart) object).getModel().getClass();
		} else if (object instanceof OutputFieldEditPart) {
			return ((OutputFieldEditPart) object).getModel().getClass();
		} else if (object instanceof TitleEditPart) {
			return ((TitleEditPart) object).getModel().getClass();
		}
		return super.mapType(object);
	}

}
