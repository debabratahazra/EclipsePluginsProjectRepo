package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.palette.PaletteEditPartFactory;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.Parameters;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.enquiry.WebService;

/**
 *
 * @author phanikumark
 *
 */
public class EnquiryEditPartFactory extends PaletteEditPartFactory {

	@Override
	public EditPart createEditPart(EditPart parentEditPart, Object model) {
		AbstractGraphicalEditPart part = null;
		if (model instanceof Enquiry) {
			part = new EnquiryDiagramEditPart();
		} else if (model instanceof FixedSelection) {
			part = new FixedSelectionEditPart();
		} else if (model instanceof SelectionExpression) {
			part = new CustomSelectionEditPart();
		} else if (model instanceof Selection) {
			part = new SelectionEditPart();
		} else if (model instanceof EnquiryHeader) {
			part = new HeaderEditPart();
		} else if (model instanceof Field) {
			part = new FieldEditPart();
		} else if (model instanceof Tool) {
			part = new ToolEditPart();
		} else if (model instanceof Routine) {
			part = new RoutineEditPart();
		} else if (model instanceof Target) {
			part = new TargetEditPart();
		} else if (model instanceof TargetMapping) {
			part = new TargetMappingEditPart();
		} else if (model instanceof WebService) {
			part = new WebServiceEditPart();
		} else if (model instanceof DrillDown) {
			part = new DrillDownEditPart();
		} else if (model instanceof SelectionCriteria) {
			part = new SelectionCriteriaEditPart();
		} else if (model instanceof Parameters) {
			part = new ParametersEditPart();
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}

}
