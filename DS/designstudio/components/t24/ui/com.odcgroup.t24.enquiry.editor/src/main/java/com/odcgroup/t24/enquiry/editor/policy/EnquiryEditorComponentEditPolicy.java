package com.odcgroup.t24.enquiry.editor.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.odcgroup.t24.enquiry.editor.part.commands.CriteriaDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.DrillDownDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FieldDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.FixedSelectionDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.HeaderDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.RoutineDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.SelectionExpressionDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.TargetDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.ToolDeleteCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.WebServiceDeleteCommand;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
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
 * @author mumesh
 *
 */
public class EnquiryEditorComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if(parent instanceof Enquiry && child instanceof FixedSelection){
			return new FixedSelectionDeleteCommand((Enquiry)parent,(FixedSelection) child);
		}
		else if(parent instanceof SelectionExpression && child instanceof Selection){
			return new SelectionExpressionDeleteCommand((SelectionExpression)parent,(Selection)child);
		}
		else if(parent instanceof Enquiry && child instanceof Field){
			return new FieldDeleteCommand((Field) child,(Enquiry)parent);
		}
		else if(parent instanceof Enquiry && child instanceof Tool){
			return new ToolDeleteCommand((Tool)child, (Enquiry)parent);
		}
		else if(parent instanceof Enquiry && child instanceof Routine){
			return new RoutineDeleteCommand((Routine)child, (Enquiry)parent);
		}
		else if(parent instanceof Target && child instanceof TargetMapping){
			return new TargetDeleteCommand((Target)parent, (TargetMapping)child);
		}
		else if(parent instanceof Enquiry && child instanceof WebService){
			return new WebServiceDeleteCommand((WebService)child, (Enquiry)parent);
		}
		else if(parent instanceof Enquiry && child instanceof EnquiryHeader){
			return new HeaderDeleteCommand((EnquiryHeader)child,(Enquiry) parent);
		}
		else if(parent instanceof Enquiry && child instanceof DrillDown){
			return new DrillDownDeleteCommand((DrillDown)child,(Enquiry) parent);
		} else if (parent instanceof DrillDown && child instanceof SelectionCriteria) {
			return new CriteriaDeleteCommand((SelectionCriteria)child, (DrillDown) parent);
		}
		return super.createDeleteCommand(deleteRequest);
	}
}
