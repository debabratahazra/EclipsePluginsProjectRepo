package samplecode.source;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import samplecode.Activator;
import samplecode.source.view.SampleContentProvider;
import samplecode.source.view.SampleFilter;
import samplecode.source.view.SampleLabelProvider;
import samplecode.source.view.SampleViewerSorter;

public class SampleView extends ViewPart implements IResourceChangeListener {
	
	private TreeViewer tViewer;
	private Action filterAction;
	private SampleFilter vFilter;
	private Action sortAction;

	public SampleView() {
		Activator
				.getDefault()
				.getLog()
				.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, SWT.OK,
						"Excaption occured.", null));
		
		vFilter = new SampleFilter();
	}

	@Override
	public void createPartControl(Composite parent) {

		tViewer = new TreeViewer(parent);

		/**
		 *  Provide the ContentProvider
		 *  The ContentProvider class needs to implement ITreeContentProvider
		 */
		tViewer.setContentProvider(new SampleContentProvider());

		/** 
		 * Provider the LabelProvider
		 * The labelProvider class extends LabelProvider
		 */
		tViewer.setLabelProvider(new SampleLabelProvider());

		/** 
		 * Method that fills the Viewer with the Content
		 * This invokes the getElements method in the ContentProvider
		 */
		tViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		// tViewer.setUseHashlookup(true);
		tViewer.setColumnProperties(new String[] { "" });

		/*CellEditor[] cellEditor = new CellEditor[1];
		cellEditor[0] = new TextCellEditor(tViewer.getTree());

		tViewer.setCellEditors(cellEditor);
		tViewer.setCellModifier(new SampleViewCellModifier());*/

		/** 
		 * Registering the selection on tViewer to be broadcasted to its listener
		 */
		this.getSite().setSelectionProvider(tViewer);

		/** 
		 * Double Click on the Files in our Navigator and open the Default Editor.
		 */
		tViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				if (((IStructuredSelection) tViewer.getSelection())
						.getFirstElement() instanceof IFile) {
					try {
						IDE.openEditor(Activator.getDefault().getWorkbench()
								.getActiveWorkbenchWindow().getActivePage(),
								(IFile) ((IStructuredSelection) tViewer
										.getSelection()).getFirstElement());
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}

		});

		/** Registering as a Listener to changes in Workspace. */
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		
		/** Creating Actions */
		makeAction();

		/** Adding Action on the Menu Bar */
		fillMenuBar();

		/** Adding Action on the ToolBar */
		fillToolBar();

		/** Activating Right Click Pop-up/context menu  */
		hookContextMenu();
	}

	private void hookContextMenu() {
		// Create menu manager.
		MenuManager menuMgr = new MenuManager();
		
		//Clear every time when right click is clicked.
		menuMgr.setRemoveAllWhenShown(true);
		
		//Fill the contextMenu with the menu for the corresponding selection.
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				mgr.add(sortAction);
				mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
				mgr.add(filterAction);
			}
		});

		// Create menu.
		Menu menu = menuMgr.createContextMenu(tViewer.getControl());
		tViewer.getControl().setMenu(menu);

		// Register menu for extension.
		getSite().registerContextMenu(menuMgr, tViewer);
	}

	private void fillToolBar() {
		this.getViewSite().getActionBars().getToolBarManager().add(filterAction);
		this.getViewSite().getActionBars().getToolBarManager().add(sortAction);
	}

	private void fillMenuBar() {
		this.getViewSite().getActionBars().getMenuManager().add(filterAction);
	}

	private void makeAction() {

		filterAction = new Action("Filter", SWT.TOGGLE) {
			public void run() {
				if (filterAction.isChecked()) {
					tViewer.addFilter(vFilter);
				} else {
					tViewer.removeFilter(vFilter);
				}
			}
		};
		
		filterAction.setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/fields_co.gif"));
		
		sortAction = new Action("Sort", SWT.TOGGLE) {
			public void run() {
				
				if (sortAction.isChecked()) {
					tViewer.setSorter(new SampleViewerSorter());
				} else {
					tViewer.setSorter(null);
				}
			}
		};
		
		sortAction.setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/alphab_sort_co.gif"));
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		Runnable run = new Runnable() {
			public void run() {
				tViewer.refresh();
			}
		};

		Display.getDefault().asyncExec(run);
	}

	public TreeViewer gettViewer() {
		return tViewer;
	}

}
