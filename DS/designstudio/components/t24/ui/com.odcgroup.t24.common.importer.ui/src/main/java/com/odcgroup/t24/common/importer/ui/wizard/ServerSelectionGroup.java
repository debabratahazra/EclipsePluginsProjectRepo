package com.odcgroup.t24.common.importer.ui.wizard;

import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.t24.common.importer.IServerSelector;
import com.odcgroup.t24.common.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.IExternalServer;

public class ServerSelectionGroup {
	
	// Specific T24.
	private static class ContentProvider implements IStructuredContentProvider {
		public void dispose() {
		}
		public Object[] getElements(Object inputElement) {
			@SuppressWarnings("unchecked")
			List<IExternalServer> servers = (List<IExternalServer>) inputElement;
			return servers != null ? servers.toArray() : null;
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	} 
	
	
	// Specific T24.
	private static class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		public String getColumnText(Object element, int columnIndex) {
			IExternalServer server = (IExternalServer)element;
			String result = ""; //$NON-NLS-1$
			switch(columnIndex){
			case 0:
				result = server.getName();
				break;
			default:
				//should not reach here
				result = ""; //$NON-NLS-1$
			}
			return result;
		}
	}
	
	
	private IServerSelector serverSelector;

	private Listener listener;

	private TableViewer tableViewer;
	
	private void handleSelectionChange(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			IExternalServer server = (IExternalServer)((IStructuredSelection) selection).getFirstElement();
			serverSelector.setServer(server);
		} 
		// fire an event so the parent can update its controls
		if (listener != null) {
			Event changeEvent = new Event();
			changeEvent.type = SWT.Selection;
			changeEvent.widget = tableViewer.getTable();
			listener.handleEvent(changeEvent);
		}
	}

	protected final IServerSelector getServerSelector() {
		return this.serverSelector;
	}
	
	protected IContentProvider createContentProvider() {
		return new ContentProvider();
	}
	
	protected IBaseLabelProvider createLabelProvider() {
		return new TableLabelProvider();
	}
	
	public void createContents(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		int styleTableViewer = SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		tableViewer = new TableViewer(composite, styleTableViewer);		
		tableViewer.setContentProvider(createContentProvider());
		tableViewer.setLabelProvider(createLabelProvider());
		
		TableColumn tblColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		tblColumn.setText(Messages.ServerSelectionGroup_columnServer);

		TableColumnLayout layout = new TableColumnLayout();
		layout.setColumnData(tblColumn, new ColumnWeightData(100));
		composite.setLayout(layout);

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChange(event);
			}
		});
		
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);	
	}
	
	public void setInput(List<IExternalServer> servers) {
		if (tableViewer != null) {
			tableViewer.setInput(servers);
		}
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	
	 * @param parent
	 *            The parent widget of the group.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is
	 *            required.
	 */
	public ServerSelectionGroup(Composite parent, Listener listener, IServerSelector serverSelector, String message) {
		this.serverSelector = serverSelector;
		this.listener = listener;
		createContents(parent);
	}

}
