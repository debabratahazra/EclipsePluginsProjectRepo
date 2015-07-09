package com.odcgroup.page.domain.ui.outline;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.domain.ui.dnd.DomainElementTransfer;
import com.odcgroup.page.domain.ui.view.DomainContentProvider;
import com.odcgroup.page.domain.ui.view.DomainLabelProvider;
import com.odcgroup.page.domain.ui.view.DomainSorter;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * A DomainOutlinePage presents a TreeView of the domain element bound to a
 * module or a page.
 * 
 * @author atr
 */
public class DomainOutlineView extends ContentOutlinePage {

	/** The Domain Repository */
	private DomainRepository repository;
	
	/** The root widget selected in the editor */
	private Widget widget;
	
	/** Listen to a selection change in the editor */
	private DomainSelectionListener selectionListener;
	
	/** Listen to a double-click in the tree view */
	private DomainDoubleClickListener domainDblClickListener = new DomainDoubleClickListener();
	
	/** Listen to domain resources changes */
	private DomainModelResourceListener domainResourceListener;
	
	/** Listen to widget's domain property changes */
	private DomainPropertyChangeListener domainPropertyChangeListener;

	/**
	 * @return Property
	 */
	protected Property getDomainProperty() {
		return widget.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
	}

	/**
	 * @return String
	 */
	protected String getDomainPropertyValue() {
		String value = null;
		Property p = getDomainProperty();
		if (p != null) {
			value = p.getValue();
			if (value != null) {
				value = value.trim();
			}
		}
		return value;
	}

	/**
	 * 
	 */
	protected void domainPropertyUpdated() {
		TreeViewer tv = getTreeViewer();
		if (tv != null && ! tv.getControl().isDisposed()) {
			String value = getDomainPropertyValue();
			if (StringUtils.isEmpty(value)) {
				tv.setInput(null);
				tv.refresh();
			} else {
				tv.setInput(MdfNameFactory.createMdfName(value));
				tv.expandToLevel(2);
			}
		}
	}

	/**
	 * Override the parent to use the WidgetTreeContentProvider and
	 * WidgetLabelProvider.
	 * 
	 * @param parent
	 *            The parent
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);

		final TreeViewer tv = getTreeViewer();

		tv.setContentProvider(new DomainContentProvider(repository));
		tv.setLabelProvider(new DomainLabelProvider());
		tv.setSorter(new DomainSorter());
		tv.addDoubleClickListener(domainDblClickListener);

		// drag source
		DragSource ds = new DragSource(tv.getTree(), DND.DROP_COPY);
		ds.setTransfer(new Transfer[] { DomainElementTransfer.getInstance() });
		ds.addDragListener(new DragSourceAdapter() {
			public void dragSetData(DragSourceEvent event) {
				event.data = Arrays.asList(((IStructuredSelection) tv.getSelection()).toArray());
			}
		});

		// tv.setUseHashlookup(true);
		domainPropertyUpdated();
	}

	/**
	 * @see org.eclipse.ui.part.Page#dispose()
	 */
	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(selectionListener);
		getTreeViewer().removeDoubleClickListener(domainDblClickListener);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(domainResourceListener);
		domainPropertyChangeListener.dispose();
		super.dispose();
	}

	/**
	 * @see org.eclipse.ui.views.contentoutline.ContentOutlinePage#init(org.eclipse.ui.part.IPageSite)
	 */
	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);

		domainPropertyChangeListener = new DomainPropertyChangeListener(getDomainProperty()) {
			protected void notifyChange() {
				Runnable r = new Runnable() {
					public void run() {
						domainPropertyUpdated();
					}
				};
				if (Display.getCurrent() != null) {
					r.run();
				} else {
					Display.getDefault().asyncExec(r);
				}
			}
		};

		domainResourceListener = new DomainModelResourceListener() {
			protected void notifyChange(String path) {
				Runnable r = new Runnable() {
					public void run() {
						TreeViewer tv = getTreeViewer();
						if (tv.getControl().isDisposed()) return;
						if (tv.getContentProvider() != null) {
							tv.setInput(tv.getInput());
							tv.expandToLevel(2);
						}
					}
				};
				if (Display.getCurrent() != null) {
					r.run();
				} else {
					Display.getDefault().asyncExec(r);
				}
			}
		};
		
		selectionListener = new DomainSelectionListener() {
			protected TreeViewer getViewer() {
				return getTreeViewer();
			}
		};
		pageSite.getWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);

		ResourcesPlugin.getWorkspace().addResourceChangeListener(domainResourceListener,
				IResourceChangeEvent.POST_CHANGE);

	}

	/**
	 * @param repository
	 *            the domain repository
	 * @param widget
	 *            the widget
	 */
	public DomainOutlineView(DomainRepository repository, Widget widget) {
		this.repository = repository;
		this.widget = widget;
	}
}