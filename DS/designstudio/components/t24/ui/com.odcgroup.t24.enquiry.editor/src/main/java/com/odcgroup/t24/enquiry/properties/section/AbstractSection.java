package com.odcgroup.t24.enquiry.properties.section;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor.PropertyValueWrapper;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.properties.UndoablePropertySheetEntry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.t24.enquiry.enquiry.provider.EnquiryItemProviderAdapterFactory;
import com.odcgroup.t24.enquiry.properties.EnquiryTabbedPropertySheetPage;
import com.odcgroup.t24.enquiry.properties.filter.IPropertyFilter;

/**
 *
 */
public abstract class AbstractSection extends AbstractPropertySection implements IPropertySourceProvider {

	/**
	 * The Property Sheet Page.
	 */
	protected PropertySheetPage page;
	protected Composite parent;
	protected IPropertySourceProvider modelPropertySourceProvider = new AdapterFactoryContentProvider(new EnquiryItemProviderAdapterFactory());

	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		final Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		//create a section property sheet page.
		page = new SectionPropertySheetPage();
		EnquiryTabbedPropertySheetPage etabpage = (EnquiryTabbedPropertySheetPage) aTabbedPropertySheetPage;
		UndoablePropertySheetEntry root = new UndoablePropertySheetEntry(etabpage.getCommandStack());
        root.setPropertySourceProvider(this);
		page.setRootEntry(root);
        page.createControl(composite);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		page.getControl().setLayoutData(data);
		
		((SectionPropertySheetPage)page).setSorter(new PropertyPageSorter());
		final Tree tree = (Tree) page.getControl();
		page.getControl().addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				aTabbedPropertySheetPage.resizeScrolledComposite();
				Rectangle area = composite.getClientArea();
				Point size = tree.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				ScrollBar vBar = tree.getVerticalBar();
				int width = area.width - tree.computeTrim(0,0,0,0).width - vBar.getSize().x;
				if (size.y > area.height + tree.getHeaderHeight()) {
					Point vBarSize = vBar.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = tree.getSize();
				if (oldSize.x > area.width) {
					TreeColumn[] columns = tree.getColumns();
					for (TreeColumn column : columns) {
						column.setWidth(width/columns.length);
					}
				 	tree.setSize(area.width, area.height);
				 } else {
				 	tree.setSize(area.width, area.height);
				 	TreeColumn[] columns = tree.getColumns();
					for (TreeColumn column : columns) {
						column.setWidth(width/columns.length);
					}
				}
			}
		});
		this.parent = parent;
		
	}

	/**
	 * @generated
	 */
	protected IPropertySourceProvider getPropertySourceProvider() {
		return this;
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		IPropertySource source = null;
		if (object instanceof EditPart) {
			Object model = ((EditPart) object).getModel();
			source = modelPropertySourceProvider.getPropertySource(model);
			return new UnwrappingPropertySource(source, (EObject) model);
		} else {
			source = modelPropertySourceProvider.getPropertySource(object);
		}
		return source;
	}
	
	public class UnwrappingPropertySource implements IPropertySource {
	    private final IPropertySource source;
	    private EObject model;

	    public UnwrappingPropertySource(final IPropertySource source, EObject model) {
	      this.source = source;
	      this.model = model;
	    }

	    @Override
	    public Object getEditableValue() {
	      Object value = source.getEditableValue();
	      if(value instanceof PropertyValueWrapper) {
	        PropertyValueWrapper wrapper = (PropertyValueWrapper) value;
	        return wrapper.getEditableValue(null);
	      } else {
	        return source.getEditableValue();
	      }
	    }

	    @Override
	    public IPropertyDescriptor[] getPropertyDescriptors() {
			IPropertyDescriptor[] descs = source.getPropertyDescriptors();
			IPropertyFilter filter = getPropertyFilter();
			if (filter != null) {
				return filter.getFilteredPropertyDescriptors(model, source);
			} else {
				return descs;
			}
	    }

	    @Override
	    public Object getPropertyValue(Object id) {
	      Object value = source.getPropertyValue(id);
	      if(value instanceof PropertyValueWrapper) {
	        PropertyValueWrapper wrapper = (PropertyValueWrapper) value;
	        return wrapper.getEditableValue(null);
	      } else {
	        return source.getPropertyValue(id);
	      }
	    }

	    @Override
	    public boolean isPropertySet(Object id) {
	           return source.isPropertySet(id);
	    }

	    @Override
	    public void resetPropertyValue(Object id) {
	      source.resetPropertyValue(id);
	    }

	    @Override
	    public void setPropertyValue(Object id, Object value) {
	    	source.setPropertyValue(id, value);
	    	
	    }
	  }

	/**
	 * Notifies the section that the workbench selection has changed. 
	 * 
	 * @param part The active workench part.
	 * @param selection The active selection in the workbench part.
	 * 
	 * Implementation note: transmit the category to the selection only
	 * if it's an instance of a WidgetPropertySource.
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {			
		if (part instanceof IEditorPart) {
			IEditorInput editInput = ((IEditorPart) part).getEditorInput();
			if (editInput instanceof IFileEditorInput) {
				parent.setEnabled(true);
			} else {
				parent.setEnabled(false);
			}
		}
		
		super.setInput(part, selection);
		page.selectionChanged(part, selection);
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	public void dispose() {
		super.dispose();

		if (page != null) {
			page.dispose();
			page = null;
		}

	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	public void refresh() {
		page.refresh();
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#shouldUseExtraSpace()
	 */
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
	/**
	 * returns the propertyfilter for the section
	 * 
	 * @return
	 */
	public abstract IPropertyFilter getPropertyFilter();
	
	/**
	 * a custom property sheet page to set the sorter.
	 * @author snn
	 *
	 */
	class SectionPropertySheetPage extends PropertySheetPage{
		/**
		 * setSorter to set the sorter to the propertysheet page.
		 */
		public void setSorter(PropertySheetSorter sorter) {
			super.setSorter(sorter);
		}
	}
	
	/**
	 * set the sorter for this propertysheet page
	 * @param sorter.
	 */
	protected void setSorter(PropertySheetSorter sorter){
		((SectionPropertySheetPage)page).setSorter(sorter);
	}
	
}
