package com.odcgroup.process.compare.internal.viewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.emf.compare.diff.metamodel.ConflictingDiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.emf.compare.ui.util.EMFCompareEObjectUtils;
import org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab;
import org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabItem;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.EditPartService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;

import com.odcgroup.process.diagram.edit.parts.ProcessEditPartFactory;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.process.model.Process;

public class ProcessContentMergeModelPart implements IModelContentMergeViewerTab {

	protected DiagramGraphicalViewer viewer;
	private final int partSide;
	
	public ProcessContentMergeModelPart(Composite composite, int partSide) {
		this.partSide = partSide;
		viewer = new DiagramGraphicalViewer();
		viewer.createControl(composite);
		configureGraphicalViewer();
	}

	
	/**
	 * 
	 */
	private void configureGraphicalViewer() {
		viewer.getControl().setBackground(ColorConstants.listBackground);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        ((FigureCanvas) viewer.getControl())
            .setScrollBarVisibility(FigureCanvas.AUTOMATIC);
        DiagramEditDomain editDomain = new DiagramEditDomain(null);
        editDomain.setCommandStack(new DiagramCommandStack(editDomain));
        viewer.setEditDomain(editDomain);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.parts.IContentMergeModelPart#setInput(java.lang.Object)
	 */
	public void setInput(Object input) {		
		Diagram diagram = null;
        if (input instanceof Process){
            Process process = (Process) input;
			diagram = fetchDiagram(process.eResource());
        } else if (input instanceof Diagram) {
        	diagram = (Diagram) input;
		} else if (input instanceof Resource) {
			Resource res = (Resource) input;
			diagram = fetchDiagram(res);
		} else if (input instanceof ResourceSet) {
			Resource res = ((ResourceSet) input).getResources().get(0);
			diagram = fetchDiagram(res);
		}
		TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(diagram.eResource().getResourceSet());
        RootEditPart rootEP = EditPartService.getInstance().createRootEditPart(diagram);
        if (rootEP instanceof DiagramRootEditPart){
        	((DiagramRootEditPart)rootEP).setPreferencesHint(ProcessDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        } 
        viewer.setRootEditPart(rootEP);
        viewer.setEditPartFactory(new ProcessEditPartFactory()); 
        viewer.setContents(diagram);
        viewer.flush();
        if (viewer.getContents() instanceof DiagramEditPart){
            DiagramEditPart dp = (DiagramEditPart)viewer.getContents();
            dp.disableEditMode();
        }
        return;
	}
	
	/**
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	private Diagram fetchDiagram(Resource resource){
		if(!resource.isLoaded()) {
			try {
				resource.load(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (Iterator<EObject> it = resource.getContents().iterator(); it.hasNext();) {
			Object rootElement = it.next();
			if (rootElement instanceof Diagram) {
				Diagram diagram = (Diagram) rootElement;
				return diagram;
			}
		}
		return null;
	}

	/**
	 * @param items
	 */
	public void showItem(List<EObject> items) {
		for(EObject item : items) {
			List<EditPart> editParts = getEditPartsForElement(item);
			if(editParts.size()>0) {
				viewer.select(editParts.get(0));
				viewer.reveal(editParts.get(0));
			} else {
				//DS-1534
				viewer.deselectAll();
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	//DS-1534
	private List<EditPart> getEditPartsForElement(EObject item) {
		if (item == null) {
			return Collections.emptyList();
		}
		String id = ((XMLResource) item.eResource()).getID(item);
		List<EditPart> editParts = viewer.findEditPartsForElement(id, EditPart.class);
		if (editParts.isEmpty() && item.eContainer() != null) {
			return getEditPartsForElement(item.eContainer());
		}
		return editParts;
	}

	public void addSelectionChangedListener(
			ISelectionChangedListener selectionChangedListener) {
		viewer.addSelectionChangedListener(selectionChangedListener);
	}

	public Control getControl() {
		return viewer.getControl();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#dispose()
	 */
	public void dispose() {
		viewer.getControl().dispose();	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getUIItem(org.eclipse.emf.ecore.EObject)
	 */
	public ModelContentMergeTabItem getUIItem(EObject data) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#redraw()
	 */
	@Override
	public void redraw() {
		viewer.getControl().redraw();		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#setReflectiveInput(java.lang.Object)
	 */
	public void setReflectiveInput(Object input) {
		setInput(input);		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#showItems(java.util.List)
	 */
	public void showItems(List<DiffElement> items) {
		final List<EObject> datas = new ArrayList<EObject>();
		for (int i = 0; i < items.size(); i++) {
			if (partSide == EMFCompareConstants.ANCESTOR && items.get(i) instanceof ConflictingDiffElement) {
				datas.add(((ConflictingDiffElement)items.get(i)).getOriginElement());
			} else if (partSide == EMFCompareConstants.LEFT) {
				datas.add(EMFCompareEObjectUtils.getLeftElement(items.get(i)));
			} else {
				datas.add(EMFCompareEObjectUtils.getRightElement(items.get(i)));
			}
		}
		showItem(datas);		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getSelectedElements()
	 */
	public List<? extends Item> getSelectedElements() {
		return viewer.getSelectedEditParts();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getVisibleElements()
	 */
	public List<ModelContentMergeTabItem> getVisibleElements() {
		return new ArrayList<ModelContentMergeTabItem>();
	}

}
