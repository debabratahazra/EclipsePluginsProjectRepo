package com.odcgroup.t24.enquiry.editor.part;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.CreateRequest;

import com.google.common.collect.Lists;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryDiagramXYLayoutPolicy;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownStringType;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.FunctionKind;
import com.odcgroup.t24.enquiry.enquiry.Parameters;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.figure.DrillDownFigure;
import com.odcgroup.t24.enquiry.figure.LabelValueFigure;
import com.odcgroup.translation.translationDsl.LocalTranslations;

/**
 *
 * @author phanikumark
 *
 */
public class DrillDownEditPart extends AbstractEnquiryEditPart {


	@Override
	protected IFigure createFigure() {
		DrillDownFigure figure = new DrillDownFigure();
		CreateRequest request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				SelectionCriteria selection = EnquiryFactory.eINSTANCE.createSelectionCriteria();
				selection.setField("Field");
				return selection;
			}
		};
		addMouseListener(figure.getSelectionCriteriaAddIcon(), request);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EnquiryDiagramXYLayoutPolicy());
	}
	
	@Override
	protected List<EObject> getModelChildren() {
		final List<EObject> modelChildren = Lists.newArrayList();
		final DrillDown model = (DrillDown) getModel();
		if (model.getCriteria() != null && !model.getCriteria().isEmpty())
			modelChildren.addAll(model.getCriteria());
		if (model.getParameters() != null)
			modelChildren.add(model.getParameters());
		return modelChildren;		
	}

	@Override
	protected void refreshVisuals() {
		DrillDownFigure figure = (DrillDownFigure) getFigure();
		DrillDown model = (DrillDown) getModel();
		LocalTranslations translation = (LocalTranslations) model.getDescription();
		if (translation != null && !translation.getTranslations().isEmpty()) {
			final String labelText = translation.getTranslations().get(0).getText();
			figure.getLabel().getTypeLabel().setText("Translation: ");
			figure.getLabel().getValueLabel().setText(labelText);
		}
		DrillDownType type = model.getType();
		figure.getTypeLabel().getTypeLabel().setText("Type:");
		setDrillDownTypeValue(type, figure.getTypeLabel());
		
		String lblfld = model.getLabelField();
		if (!StringUtils.isEmpty(lblfld)) {
			figure.getFieldLabel().getTypeLabel().setText("Label Field: ");
			figure.getFieldLabel().getValueLabel().setText(lblfld);
		}
		
		Parameters params = model.getParameters();
		if (params != null) {			
			StringBuffer paramVal = new StringBuffer();
			if (params.getFunction() != null && 
					!params.getFunction().equals(FunctionKind.UNSPECIFIED)) {
				paramVal.append(params.getFunction()+" ");				
			}
			if (params.isAuto()) {
				paramVal.append("F3 ");
			}
			
			if (params.getPwActivity() != null) {
				paramVal.append(params.getPwActivity()+" ");
			}
			
			if (!params.getFieldName().isEmpty()) {
				for(String str : params.getFieldName()) {
					paramVal.append(str+" ");
				}
			}
			if (!params.getVariable().isEmpty()) {
				for(String str : params.getVariable()) {
					paramVal.append(str+" ");					
				}
			}
			figure.getParamLabel().getTypeLabel().setText("Parameters:");
			figure.getParamLabel().getValueLabel().setText(paramVal.toString());
		}
	}
	
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		final Object model = childEditPart.getModel();
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (model instanceof SelectionCriteria) {
			((DrillDownFigure) getFigure()).getSelectionContainerFigure().getContentPane().add(child);
		}
	}
	
	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		DrillDownFigure figure = (DrillDownFigure) getFigure();
		if (childEditPart instanceof SelectionCriteriaEditPart) {
			figure.getSelectionContainerFigure().getContentPane().remove(child);
		}
	}

	/**
	 * @param type
	 * @return
	 */
	private void setDrillDownTypeValue(DrillDownType type, LabelValueFigure figure) {
		if (type instanceof DrillDownStringType) {
			DrillDownStringType stype = (DrillDownStringType) type;
			figure.getValueLabel().setText(stype.getProperty()+stype.getValue());
		} else if (type instanceof BlankType) {
			BlankType stype = (BlankType) type;
			figure.getValueLabel().setText(stype.getProperty()+stype.getValue());
		}
	}
	
	private void addMouseListener(ImageFigure figure,final CreateRequest request ){
		figure.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				Command command = getCommand(request);
				CommandStack stack = getViewer().getEditDomain().getCommandStack();
				stack.execute(command);
			}
			
			@Override
			public void mouseDoubleClicked(MouseEvent arg0) {				
			}
		});
	}

}
