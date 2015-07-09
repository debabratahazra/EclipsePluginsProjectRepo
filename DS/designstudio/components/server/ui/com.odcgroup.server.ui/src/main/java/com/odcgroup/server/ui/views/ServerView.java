package com.odcgroup.server.ui.views;


import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.server.IDSServerStateChangeListener;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.server.ui.actions.AbstractServerAction;
import com.odcgroup.server.ui.actions.AddRemoveProjectsAction;
import com.odcgroup.server.ui.actions.DebugServerAction;
import com.odcgroup.server.ui.actions.StartServerAction;
import com.odcgroup.server.ui.actions.StopServerAction;

/**
 * @author pkk
 *
 */
public class ServerView extends ViewPart {

	public static final String SERVER_VIEW_ID = "com.odcgroup.server.ui.views.ServerView";

	public static final String SERVER_COL = "Server";
	public static final String STATE_COL = "State";
	
	public final String EXT_POINT_ID = "com.odcgroup.server.ui.serverproperties";
	public final String TEXT_EDITOR = "org.eclipse.ui.DefaultTextEditor";
	public final String PROPERTIES_EDITOR = "org.jboss.tools.common.propertieseditor.PropertiesCompoundEditor";
	public final String ID = "id";

	protected TreeViewer viewer;
	protected String[] columns = {SERVER_COL, STATE_COL };

	private Action startServer = null;
	private Action debugServer = null;
	private Action stopServer = null;
	private Action addRemoveProject = null;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		Tree tree = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE
				| SWT.FULL_SELECTION);
		tree.setLinesVisible(true);
		viewer = new TreeViewer(tree);
		viewer.setContentProvider(new ServerTreeContentProvider());
		viewer.setLabelProvider(new ServerTreeLabelProvider());
		createColumns(tree);

		initActions(viewer);
		hookContextMenu();
		contributeToActionBars();

		initializeContents();
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) viewer.getSelection();
				Object obj = selection.getFirstElement();
				IDSServer server = null;
				if (obj instanceof IDSServer) {
					server = (IDSServer) obj;
				} else if (obj instanceof IDSProject) {
					server = ((IDSProject) obj).getServer();
				}
				if(server == null) {
					return;
				}
				IFile file = server.getServerProject().getFile(
						"/config/server.properties");
				// Iris Embedded Server
				IFile irisServerFile = server.getServerProject().getFile("/src/main/resources/tafc-connection.properties");
				
				if (file != null && file.exists()) {
					IWorkbenchPage activePage = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					
					IConfigurationElement[] configs = Platform.getExtensionRegistry()
							.getConfigurationElementsFor(EXT_POINT_ID);

					if (configs.length == 0) {
						try {
							activePage
									.openEditor(new FileEditorInput(file),
											TEXT_EDITOR);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}else{
						for (IConfigurationElement config : configs) {
							try {
								Object editor = config.getAttribute(ID);
								activePage
								.openEditor(new FileEditorInput(file),
										editor.toString());
							} catch (CoreException e) {
								e.printStackTrace();
							}

						}
					}
				}
				// Iris Embedded Server
				else if (irisServerFile != null && irisServerFile.exists()) {
					IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						activePage.openEditor(new FileEditorInput(irisServerFile), PROPERTIES_EDITOR);
						IFile irisServerFile2 = server.getServerProject().getFile("/src/main/resources/web-server.properties");
						activePage.openEditor(new FileEditorInput(irisServerFile2), PROPERTIES_EDITOR);
					} catch (PartInitException e) {
						// Failed to open in Jboss properties editor.
						try {
							activePage.openEditor(new FileEditorInput(irisServerFile), TEXT_EDITOR);
							IFile irisServerFile2 = server.getServerProject().getFile("/src/main/resources/web-server.properties");
							activePage.openEditor(new FileEditorInput(irisServerFile2), TEXT_EDITOR);
						} catch (CoreException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
	 */
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		final IDSServerStateChangeListener serverStateChangeListener = new IDSServerStateChangeListener() {
			public void serverStateChanged(IDSServer server) {
				forceViewerRefresh();
			}
		};
		for (IDSServer server : ServerUICore.getDefault().getContributions().getServers()) {
			server.addServerStateChangeListener(serverStateChangeListener);
		}

		ServerUICore.getDefault().getContributions().addListenerServerAddedRemovedExternally(new IServerExternalChangeListener() {
			@SuppressWarnings("unchecked")
			public void serverRemovedExternally(IDSServer server) {
				List<IDSServer> servers = (List<IDSServer>)viewer.getInput();
				if(servers == null || server == null) return;
				if (servers.remove(server)) {
					server.removeServerStateChangeListener((AbstractServerAction)startServer);
					server.removeServerStateChangeListener((StopServerAction)stopServer);
					server.removeServerStateChangeListener((DebugServerAction)debugServer);
					server.removeServerStateChangeListener(serverStateChangeListener);
					forceViewerRefresh();
				}
			}
			@SuppressWarnings("unchecked")
			public void serverAddedExternally(IDSServer server) {
				List<IDSServer> servers = (List<IDSServer>)viewer.getInput();
				if(servers == null || server == null) return;
				if (!servers.contains(server)) {
					servers.add(server);
					server.addServerStateChangeListener((AbstractServerAction)startServer);
					server.addServerStateChangeListener((StopServerAction)stopServer);
					server.addServerStateChangeListener((DebugServerAction)debugServer);
					server.addServerStateChangeListener(serverStateChangeListener);
					forceViewerRefresh();
					expand(server);
				}
			}
		});
	}

	/**
	 * @param tree
	 */
	protected void createColumns(final Tree tree) {
		TableLayout layout = new TableLayout();
		tree.setLayout(layout);
		tree.setHeaderVisible(true);

		for (int i = 0; i < columns.length; i++) {
			layout.addColumnData(new ColumnWeightData(1));
			TreeColumn tc = new TreeColumn(tree, SWT.NONE, i);
			tc.setResizable(true);
			tc.setMoveable(true);
			tc.setText(columns[i]);
			TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, tc);
			viewerColumn.setLabelProvider(new ServerTreeColumnLabelProvider(columns[i]));
		}

		getSite().setSelectionProvider(viewer);

	}

	/**
	 *
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ServerView.this.fillContextualMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 *
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * @param manager
	 */
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(startServer);
		manager.add(debugServer);
		manager.add(stopServer);

		CreateServerAction createServer = new CreateServerAction();
		createServer.setImageDescriptor(ServerUICore.getImageDescriptor("icons/addserver.png"));
		ServerUICore.getDefault().getContributions().fillAddServerToolbarMenu(createServer);
		manager.add(createServer);
		
		Action refreshServerView = new Action() {
			public void runWithEvent(Event event) {
				ServerUICore.getDefault().getContributions().refreshServers();
		    	forceViewerRefresh(true);
			}
		};
		refreshServerView.setImageDescriptor(ServerUICore.getImageDescriptor("icons/refresh.png"));
		refreshServerView.setToolTipText("Refresh Server View");
		manager.add(refreshServerView);
	}

	/**
	 * @param provider
	 */
	private void initActions(ISelectionProvider provider) {
		final Shell shell = getSite().getShell();

		provider.getSelection();

		startServer = new StartServerAction(shell, provider);

		stopServer = new StopServerAction(shell, provider);

		debugServer = new DebugServerAction(shell, provider);

		addRemoveProject = new AddRemoveProjectsAction(shell, provider, this);

		for (IDSServer server : ServerUICore.getDefault().getContributions().getServers()) {
			server.addServerStateChangeListener((AbstractServerAction)startServer);
			server.addServerStateChangeListener((StopServerAction)stopServer);
			server.addServerStateChangeListener((DebugServerAction)debugServer);
		}

	}

	/**
	 * @param shell
	 * @param menu
	 */
	protected void fillContextualMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

		if (!selection.isEmpty()) {
			addServersAvailableMenu(menu, selection);
		} else {
			addNoServersAvailableMenu(menu);
		}
	}

	/**
	 * @param menu
	 * @param selection
	 */
	private void addServersAvailableMenu(IMenuManager menu,	IStructuredSelection selection) {
		Object obj = selection.getFirstElement();

		IDSServer server = null;
		if (obj instanceof IDSServer) {
			server = (IDSServer) obj;
		} else if (obj instanceof IDSProject) {
			server = ((IDSProject) obj).getServer();
		}

		if (server != null) {
			menu.add(startServer);
			menu.add(debugServer);
			menu.add(stopServer);
			menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(addRemoveProject);
			addConfigureExternalServerAction(menu, server);
		}
		addServerAction(menu, server);
	}

	/**
	 * @param menu
	 */
	private void addNoServersAvailableMenu(IMenuManager menu) {
		addConfigureExternalServerAction(menu, null);
		addServerAction(menu, null);
	}

	/**
	 * @param menu
	 * @param server
	 */
	private void addServerAction(IMenuManager menu, IDSServer server) {
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		IAddAction addAction = new MenuManagerAddServerAction(menu);
		ServerUICore.getDefault().getContributions().fillAddServerContextMenu(server, addAction);
	}

	/**
	 * @param menu
	 * @param server
	 */
	private void addConfigureExternalServerAction(IMenuManager menu, IDSServer server) {
		IAddAction addContextMenuAction = new MenuManagerAddConfigureMenuAction(menu);
		ServerUICore.getDefault().getContributions().fillConfigureServerContextMenu(server, addContextMenuAction);
	}

	private void initializeContents() {
		viewer.setInput(ServerUICore.getDefault().getContributions().getServers());
		viewer.expandAll();
	}

	@Override
	public void setFocus() {
		if (viewer != null) {
			viewer.getTree().setFocus();
			viewer.refresh();
		}
	}

	private void forceViewerRefresh() {
		forceViewerRefresh(false);
	}
	
	public void forceViewerRefresh(final boolean reinitContents) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (viewer != null && ! viewer.getTree().isDisposed()) {
					if (reinitContents) {
						initializeContents();
					}
					viewer.refresh();
				}
			}
		});
	}
	
	public void expand(final IDSServer server) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (viewer != null && ! viewer.getTree().isDisposed()) {
					viewer.expandToLevel(server, TreeViewer.ALL_LEVELS);
				}
			}
		});
		
	}

}