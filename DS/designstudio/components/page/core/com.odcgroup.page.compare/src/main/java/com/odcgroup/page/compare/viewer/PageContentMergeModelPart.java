package com.odcgroup.page.compare.viewer;

import java.util.ArrayList;
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
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.DesignEditPartFactory;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class is responsible for finding and highlighting EditPart's which have been modified.
 * 
 * @author Gary Hayes
 */
public class PageContentMergeModelPart implements IModelContentMergeViewerTab {
	
	private final int partSide;

	/** The GraphicalViewer used to display the page. */
	protected ScrollingGraphicalViewer viewer;

	/**
	 * This is not really used however we need to create one to avoid NullPointerException's and Assertion problems.
	 */
	private CommandStack commandStack;

	/**
	 * Creates a new PageContentMergeModelPart.
	 * 
	 * @param composite
	 *            The parent Widget
	 */
	public PageContentMergeModelPart(Composite composite, int partSide) {
		this.partSide = partSide;
		viewer = new ScrollingGraphicalViewer();
		viewer.createControl(composite);
		
		configureGraphicalViewer();
	}

	/**
	 * Gets the viewer control used to present the page.
	 * 
	 * @return Control.
	 */
	public Control getControl() {
		return viewer.getControl();
	}

	/**
	 * Sets the model input.
	 * 
	 * @param input
	 *            In this case it MUST be an EMF Model object
	 */
	public void setInput(Object input) {
		if (input instanceof Model) {
			Model m = (Model) input;
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(m.eResource());
	
			viewer.setEditPartFactory(new DesignEditPartFactory(ofsProject.getProject(), false, commandStack));
			viewer.setContents(m.getWidget());
			viewer.flush();
		}
		return;
	}

	/**
	 * DS-1534
	 * Ensures the first element of the given list of items is visible in the modelPart, and sets the modelPart's
	 * selection to this list.
	 * 
	 * @param items
	 *            Items to make visible.
	 */
	public void showItem(List<EObject> items) {
		// We need to find the Widgets associated with each item since these are the only
		// things which can be highlighted in the Page Designer (the Properties etc. are not visible)
		List<Widget> widgets = findWidgets(items);
		EditPart rep = viewer.getRootEditPart();

		for (Widget w : widgets) {
			EditPart ep = (EditPart) viewer.getEditPartRegistry().get(w);
			if (ep!=null) {
				viewer.select(ep);
				viewer.reveal(ep);
			} else {
				viewer.deselectAll();
			}
		}
	}
	

	/**
	 * Finds the Widget associated with each Item.
	 * 
	 * @param items
	 *            The List of items
	 * @return List of Widgets
	 */
	private List<Widget> findWidgets(List items) {
			List<Widget> widgets = new ArrayList<Widget>();
		for (Object item : items) {
			Widget w = null;
			if (item instanceof Property) {
				Property p = (Property) item;
				w = p.getWidget();
			} else if (item instanceof Widget) {
				w = (Widget) item;
			} else if (item instanceof Event) {
				Event e = (Event) item;
				w = e.getWidget();
			} else if (item instanceof Parameter) {
				Parameter p = (Parameter) item;
				w = p.getEvent().getWidget();
			} else if (item instanceof Model) {
				Model m = (Model) item;
				w = m.getWidget();
			}
			
			if (w != null) {
				widgets.add(w);
			}
		}
		
		return widgets;
	}

	/**
	 * Configures the GraphicalViewer.
	 */
	private void configureGraphicalViewer() {
		viewer.getControl().setBackground(ColorConstants.listBackground);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		((FigureCanvas) viewer.getControl()).setScrollBarVisibility(FigureCanvas.AUTOMATIC);
		DefaultEditDomain editDomain = new DefaultEditDomain(null);
		commandStack = new CommandStack();
		editDomain.setCommandStack(commandStack);
		viewer.setEditDomain(editDomain);
	}

	/**
	 * Add a selection changes listener to the viewer so that the view can be updated when new items are selected.
	 * 
	 * @param selectionChangedListener
	 *            The selection changed listener to add
	 */
	public void addSelectionChangedListener(ISelectionChangedListener selectionChangedListener) {
		viewer.addSelectionChangedListener(selectionChangedListener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#dispose()
	 */
	public void dispose() {
		
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
	public void redraw() {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#setReflectiveInput(java.lang.Object)
	 */
	public void setReflectiveInput(Object input) {
		Model m = null;
		IOfsProject ofsProject = null;
		if (input instanceof Model) {
			m  = (Model) input;
			ofsProject = OfsResourceHelper.getOfsProject(m.eResource());
		} else if (input instanceof Resource) {
			Resource res = (Resource) input;
			ofsProject = OfsResourceHelper.getOfsProject(res);
			m = (Model) res.getContents().get(0);
		} else if (input instanceof ResourceSet) {
			Resource res = ((ResourceSet) input).getResources().get(0);
			ofsProject = OfsResourceHelper.getOfsProject(res);
			m = (Model) res.getContents().get(0);
		}
		
		viewer.setEditPartFactory(new DesignEditPartFactory(ofsProject.getProject(), false, commandStack));
		viewer.setContents(m.getWidget());
		viewer.flush();
		
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
		redraw();		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getVisibleElements()
	 */
	public List<ModelContentMergeTabItem> getVisibleElements() {
		return new ArrayList<ModelContentMergeTabItem>();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab#getSelectedElements()
	 */
	public List<? extends Item> getSelectedElements() {
		return viewer.getSelectedEditParts();
	}
}