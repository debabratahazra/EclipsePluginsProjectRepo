package com.odcgroup.t24.enquiry.editor.part;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.Lists;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.editor.part.commands.CreateCustomSelectionCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.CreateFixedSelectionCommand;
import com.odcgroup.t24.enquiry.editor.part.commands.ManageFieldsCommand;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryDiagramXYLayoutPolicy;
import com.odcgroup.t24.enquiry.editor.request.CreateCustomSelectionRequest;
import com.odcgroup.t24.enquiry.editor.request.CreateFixedSelectionRequest;
import com.odcgroup.t24.enquiry.editor.request.ManageFieldsRequest;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.enquiry.WebService;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;
import com.odcgroup.t24.enquiry.figure.TargetFigure;
import com.odcgroup.t24.enquiry.figure.WebServiceFigure;
import com.odcgroup.t24.enquiry.properties.dialogs.EnquiryFieldSelectionDialog;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 
 * @author phanikumark
 * 
 */
public class EnquiryDiagramEditPart extends AbstractEnquiryEditPart {
	
	private EnquiryFigure diagram;

	@Override
	protected IFigure createFigure() {		
		diagram = new EnquiryFigure();
		updateListeners();
		return diagram;
	}

	/**
	 * 
	 */
	private void updateListeners() {
		CreateFixedSelectionRequest cfReq = new CreateFixedSelectionRequest((Enquiry)getModel());
		addMouseListener(diagram.getFixedSelectionAddIcon(), cfReq);	
		
		CreateCustomSelectionRequest csReq = new CreateCustomSelectionRequest((Enquiry)getModel());
		addMouseListener(diagram.getCustomSelectionAddIcon(),csReq);	
		
		CreateRequest request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				EnquiryHeader header = EnquiryFactory.eINSTANCE.createEnquiryHeader();
				header.setColumn(1);
				header.setLine(1);
				return header;
			}
		};
		addMouseListener(diagram.getHeadersAddIcon(),request);
		
		// manage fields request
		ManageFieldsRequest mr = new ManageFieldsRequest((Enquiry)getModel());
		addMouseListener(diagram.getColumnsAddIcon(), mr);

		request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				Tool tool = EnquiryFactory.eINSTANCE.createTool();
				tool.setName("ToolName");
				return tool;
			}
		};
		addMouseListener(diagram.getToolsAddIcon(),request);

		request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				Routine routine = EnquiryFactory.eINSTANCE.createJavaRoutine();
				routine.setName("RoutineName");
				return routine;
			}
		};
		addMouseListener(diagram.getJavaRoutineAddIcon(),request);

		request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				Routine routine = EnquiryFactory.eINSTANCE.createJBCRoutine();
				routine.setName("RoutineName");
				return routine;
			}
		};
		addMouseListener(diagram.getJBCRoutineAddIcon(),request);

		request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				WebService webService = EnquiryFactory.eINSTANCE.createWebService();
				webService.setWebServiceActivity("ServiceActivity");
				return webService;
			}
		};
		addMouseListener(diagram.getWebServicesAddIcon(),request);

		request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				TargetMapping mapping = EnquiryFactory.eINSTANCE.createTargetMapping();
				mapping.setFromField("FromField");
				mapping.setToField("ToField");
				return mapping;
			}
		};
		addMouseListener(diagram.getTargetAddIcon(),request);

		request = new CreateRequest(){
			@Override
			public Object getNewObject() {
				DrillDown drillDown = EnquiryFactory.eINSTANCE.createDrillDown();
				EnquiryUtil.setDrillDownName(drillDown, (Enquiry) getModel());
				return drillDown;
			}
		};
		addMouseListener(diagram.getDrilldownAddIcon(),request);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EnquiryDiagramXYLayoutPolicy());
	}

	@Override
	public IFigure getContentPane() {
		return diagram;
	}

	@Override
	protected List<EObject> getModelChildren() {
		final List<EObject> modelChildren = Lists.newArrayList();
		
		final Enquiry enquiry = (Enquiry) getModel();

		if (null != enquiry.getFields() && !enquiry.getFields().isEmpty()) {
			modelChildren.addAll(enquiry.getFields());
		}
		
		if (null != enquiry.getFixedSelections() && !enquiry.getFixedSelections().isEmpty()) {
			modelChildren.addAll(enquiry.getFixedSelections());
		}
		
		if(null != enquiry.getCustomSelection()){
			modelChildren.add(enquiry.getCustomSelection());
		}
		
		if(enquiry.getHeader() != null && !enquiry.getHeader().isEmpty()) {
			modelChildren.addAll(enquiry.getHeader());
		}
		
		if (enquiry.getDrillDowns() != null && !enquiry.getDrillDowns().isEmpty()) {
			modelChildren.addAll(enquiry.getDrillDowns());
		}
		
		if (null != enquiry.getTools() && !enquiry.getTools().isEmpty()) {
			modelChildren.addAll(enquiry.getTools());
		}
		
		if (null != enquiry.getBuildRoutines() && !enquiry.getBuildRoutines().isEmpty()) {
			modelChildren.addAll(enquiry.getBuildRoutines());
		}
		
		if (null != enquiry.getTarget()) {
			modelChildren.add(enquiry.getTarget());
		}
		
		if (null != enquiry.getWebService()) {
			modelChildren.add(enquiry.getWebService());
		}
		return modelChildren;
	}
	
	@Override
	protected void refreshChildren() {
		int i;
		EditPart editPart;
		EObject model;
		
		List<EObject> modelObjects = getModelChildren();
		for (i = 0; i < modelObjects.size(); i++) {
			model = modelObjects.get(i);
			if (isChildAvailable(model))
				continue;
			if (model instanceof Field) {
				editPart = new OutputFieldEditPart();
				editPart.setModel(model);
				addChild(editPart, i);
			} else if (model instanceof EnquiryHeader) {
				editPart = new TitleEditPart();
				editPart.setModel(model);
				addChild(editPart, i);
			}
			
			if (model instanceof Field) {
				// add only display fields to the columns container
				Field field = (Field) model;
				if (EnquiryUtil.isDisplayField(field)) {
					editPart = createChild(model);
					addChild(editPart, i);					
				}
			} else {
				editPart = createChild(model);
				addChild(editPart, i);				
			}
		}

		// remove the remaining EditParts
		List<EditPart> trash = getTrashEditParts();
		for (EditPart ep : trash) {
			removeChild(ep);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<EditPart> getTrashEditParts() {
		List<EObject> modelObjects = getModelChildren();
		List<EditPart> eparts = getChildren();
		List<EditPart> valid = new ArrayList<EditPart>();
		for (EditPart editPart : eparts) {
			for (EObject eobj : modelObjects) {
				if (editPart.getModel() == eobj) {
					valid.add(editPart);
				}
			}
		}
		List<EditPart> trash = new ArrayList<EditPart>();
		for (EditPart editPart : eparts) {
			if (!valid.contains(editPart)) {
				trash.add(editPart);
			}
		}
		return trash;
	}
	 
	@SuppressWarnings("unchecked")
	private boolean isChildAvailable(EObject model) {
		List<EditPart> children = getChildren();
		for (EditPart editPart : children) {
			if (model instanceof Field) {
				if (editPart instanceof FieldEditPart || editPart instanceof OutputFieldEditPart) {
					if (editPart.getModel() == model) {
						return true;
					}
				}
			} else if (model instanceof EnquiryHeader) {
				if (editPart instanceof HeaderEditPart || editPart instanceof TitleEditPart) {
					if (editPart.getModel() == model) {
						return true;
					}
				}
			} else {
				if (editPart.getModel() == model) {
					return true;
				}
			}
		}
		return false;
	}
 
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		final Object model = childEditPart.getModel();
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();		
		if (model instanceof FixedSelection) {
			diagram.getFixedSelectionContainer().add( child);
		} else if (model instanceof Field) {
			if (childEditPart instanceof FieldEditPart) {
				if (diagram.getColumnsContainer().getChildren().size() > index) {
					diagram.getColumnsContainer().add(child, index);
				} else {
					diagram.getColumnsContainer().add(child);
				}
			} else if (childEditPart instanceof OutputFieldEditPart) {
				if (diagram.getDataOutputFieldsContainer().getChildren().size() > index) {
					diagram.getDataOutputFieldsContainer().add(child, index);
				} else {
					diagram.getDataOutputFieldsContainer().add(child);
				}
			}
		} else if (model instanceof Routine) {
			int ind = ((Enquiry)this.getModel()).getBuildRoutines().indexOf((Routine)model);
			diagram.getRoutineContainer().add(child,ind);
		} else if (model instanceof Tool) {
			int ind = ((Enquiry)this.getModel()).getTools().indexOf((Tool)model);
			diagram.getToolsContainer().add(child,ind);
		} else if (model instanceof EnquiryHeader) {
			if (childEditPart instanceof HeaderEditPart) {
				if (diagram.getHeadersContainer().getChildren().size() > index) {
					diagram.getHeadersContainer().add(child, index);
				} else {
					diagram.getHeadersContainer().add(child);
				}
			} else if (childEditPart instanceof TitleEditPart) {
				if (diagram.getTitlesContainer().getChildren().size() > index) {
					diagram.getTitlesContainer().add(child, index);
				} else {
					diagram.getTitlesContainer().add(child);
				}				
			}
		} else if (model instanceof DrillDown) {
			if (diagram.getDrilldownContainer().getChildren().size() > index) {
				diagram.getDrilldownContainer().add(child, index);
			} else {
				diagram.getDrilldownContainer().add(child);
			}
		}
	}
	

	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof FixedSelectionEditPart) {
			diagram.getFixedSelectionContainer().remove(child);
		} else if (childEditPart instanceof FieldEditPart) {
			diagram.getColumnsContainer().remove(child);
		} else if(childEditPart instanceof ToolEditPart){
			diagram.getToolsContainer().remove(child);
		} else if(childEditPart instanceof RoutineEditPart){
			diagram.getRoutineContainer().remove(child);
		}  else if(childEditPart instanceof HeaderEditPart){
			diagram.getHeadersContainer().remove(child);
		} else if (childEditPart instanceof DrillDownEditPart) {
			diagram.getDrilldownContainer().remove(child);
		} else if (childEditPart instanceof OutputFieldEditPart) {
			diagram.getDataOutputFieldsContainer().remove(child);
		} else if (childEditPart instanceof TitleEditPart) {
			diagram.getTitlesContainer().remove(child);
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		Enquiry enquiry = (Enquiry) getModel();
		if (enquiry.getWebService() == null) {
			WebServiceFigure figure = (WebServiceFigure) diagram.getWebServicesFigure();
			figure.getActivityLabel().setText("");
		}
		else{
			WebServiceFigure figure = (WebServiceFigure) diagram.getWebServicesFigure();
			figure.getImgFigure().setEnabled(false);
		}
		if (enquiry.getTarget() == null) {
			TargetFigure figure = (TargetFigure) diagram.getTargetContainer();
			figure.getApplicationLabel().setText("");
			figure.getScreenLabel().setText("");
		}
	}
	
	private void addMouseListener(ImageFigure figure,final Request request ){
		figure.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				Command command = getCommand(request);
				if (command instanceof ManageFieldsCommand) {
					ManageFieldsCommand mfc = (ManageFieldsCommand) command;
					Object retVal = EnquiryFieldSelectionDialog.openDialog(new Shell(), mfc.getEnquiry(), null, true);
					if (retVal != null) {
						if (retVal instanceof String) {
							mfc.setCalculatedFieldName((String)retVal);
						} else {
							Object[] properties = (Object[]) retVal;
							List<MdfProperty> list = new ArrayList<MdfProperty>();
							Collections.addAll(list, properties);
							mfc.setProperties(list);
						}
						CommandStack stack = getViewer().getEditDomain().getCommandStack();
						stack.execute(mfc);
					}					
				} else if (command instanceof CreateFixedSelectionCommand) {
					CreateFixedSelectionCommand mfc = (CreateFixedSelectionCommand) command;
					Object retVal = EnquiryFieldSelectionDialog.openDialog(new Shell(), mfc.getEnquiry(), null, false);
					if (retVal != null) {
						String name = (String) retVal;
						if (!StringUtils.isEmpty(name)) {
							FixedSelection sel = EnquiryFactory.eINSTANCE.createFixedSelection();
							sel.setField(name);
							mfc.setSelection(sel);
							CommandStack stack = getViewer().getEditDomain().getCommandStack();
							stack.execute(mfc);
						}
					}
				}  else if (command instanceof CreateCustomSelectionCommand) {
					CreateCustomSelectionCommand mfc = (CreateCustomSelectionCommand) command;
					Object retVal = EnquiryFieldSelectionDialog.openDialog(new Shell(), mfc.getEnquiry(), null, false);
					if (retVal != null) {
						String name = (String) retVal;
						if (!StringUtils.isEmpty(name)) {
							Selection sel = EnquiryFactory.eINSTANCE.createSelection();
							sel.setField(name);
							mfc.setSelection(sel);
							CommandStack stack = getViewer().getEditDomain().getCommandStack();
							stack.execute(mfc);
						}
					}
				} else {
					CommandStack stack = getViewer().getEditDomain().getCommandStack();
					stack.execute(command);
				}
			}
			
			@Override
			public void mouseDoubleClicked(MouseEvent arg0) {				
			}
		});
	}
	
	/**
	 * Gets the offset to apply to the Location caused by the fact that the scrollbar is active.
	 * 
	 * @return Point
	 */
	public Point getScrollbarOffset() {
		ScrollingGraphicalViewer sgv = (ScrollingGraphicalViewer) getViewer();
		FigureCanvas fc = (FigureCanvas) sgv.getControl();
		int hValue = fc.getHorizontalBar().getSelection();
		int vValue = fc.getVerticalBar().getSelection();
		return new Point(hValue, vValue);
	}

}
