package com.odcgroup.t24.server.properties.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.server.ui.dialogs.ManageProjectsDialog;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.util.ServerPropertiesPasswordScrambler;
import com.odcgroup.t24.server.properties.Messages;
import com.odcgroup.t24.server.properties.T24ServerPropertiesActivator;
import com.odcgroup.t24.server.properties.server.Server;
import com.odcgroup.t24.server.properties.server.ServerPackage.Literals;
import com.odcgroup.t24.server.properties.util.ServerProperties;

public class ServerFormPageWebServicePage extends FormPage {
	private Text usernameText;
	private Text protocolText;
	private Text hostText;
	private Text portText;
	private Text pwdText;

	private List list;
	private ServerFormEditor editor;
	private ISWTObservableList itemsListObserveWidget;
	private Server server;
	private Text branchText;
	
	private ServerProperties serverProperties;
	
	public ServerFormPageWebServicePage(ServerFormEditor editor, String id,
			String title, Server server) {
		super(editor, id, title);
		this.editor = editor;
		this.server = server;
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		form.setText("Server Properties");
		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		form.getBody().setLayout(layout);
		TableWrapData td;

		Composite mainComposite = new Composite(form.getBody(), SWT.NONE);
		managedForm.getToolkit().adapt(mainComposite);
		managedForm.getToolkit().paintBordersFor(mainComposite);
		mainComposite.setLayout(new GridLayout(1, false));

		Group grpT = new Group(mainComposite, SWT.NONE);
		grpT.setLayout(new GridLayout(2, false));
		GridData gd_grpT = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpT.heightHint = 128;
		grpT.setLayoutData(gd_grpT);
		grpT.setText("T24 User (deployment only)");
		managedForm.getToolkit().adapt(grpT);
		managedForm.getToolkit().paintBordersFor(grpT);

		Label lblUsername = new Label(grpT, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1));
		managedForm.getToolkit().adapt(lblUsername, true, true);
		lblUsername.setText("Username:");

		usernameText = new Text(grpT, SWT.BORDER);
		usernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		managedForm.getToolkit().adapt(usernameText, true, true);
		usernameText.addListener(SWT.KeyUp, copyListener);
		usernameText.addListener(SWT.KeyUp, pasteListener);

		Label lblPassword = new Label(grpT, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1));
		managedForm.getToolkit().adapt(lblPassword, true, true);
		lblPassword.setText("Password:");

		pwdText = new Text(grpT, SWT.BORDER | SWT.PASSWORD);
		pwdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		pwdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		managedForm.getToolkit().adapt(pwdText, true, true);
		pwdText.addListener(SWT.KeyUp, copyListener);
		pwdText.addListener(SWT.KeyUp, pasteListener);

		Label lblBranch = new Label(grpT, SWT.NONE);
		lblBranch.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false,
				1, 1));
		managedForm.getToolkit().adapt(lblBranch, true, true);
		lblBranch.setText("Branch:");

		branchText = new Text(grpT, SWT.BORDER);
		branchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		managedForm.getToolkit().adapt(branchText, true, true);
		branchText.addListener(SWT.KeyUp, copyListener);
		branchText.addListener(SWT.KeyUp, pasteListener);

		Composite composite = new Composite(mainComposite, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		managedForm.getToolkit().adapt(composite);
		managedForm.getToolkit().paintBordersFor(composite);

		Group grpAgentConnection = new Group(composite, SWT.NONE);
		grpAgentConnection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		grpAgentConnection.setText("WebService (TAFj) Connection");
		managedForm.getToolkit().adapt(grpAgentConnection);
		managedForm.getToolkit().paintBordersFor(grpAgentConnection);
		grpAgentConnection.setLayout(new GridLayout(2, false));

		Label lblProtocol = new Label(grpAgentConnection, SWT.NONE);
		managedForm.getToolkit().adapt(lblProtocol, true, true);
		lblProtocol.setText("Protocol:");

		protocolText = new Text(grpAgentConnection, SWT.BORDER);
		protocolText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		managedForm.getToolkit().adapt(protocolText, true, true);
		protocolText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false,
				1, 1));
		managedForm.getToolkit().adapt(protocolText, true, true);
		protocolText.addListener(SWT.KeyUp, copyListener);
		protocolText.addListener(SWT.KeyUp, pasteListener);

		Label lblHost = new Label(grpAgentConnection, SWT.NONE);
		managedForm.getToolkit().adapt(lblHost, true, true);
		lblHost.setText("Hostname:");

		hostText = new Text(grpAgentConnection, SWT.BORDER);
		hostText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		managedForm.getToolkit().adapt(hostText, true, true);
		hostText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		managedForm.getToolkit().adapt(hostText, true, true);
		hostText.addListener(SWT.KeyUp, copyListener);
		hostText.addListener(SWT.KeyUp, pasteListener);

		Label lblPort = new Label(grpAgentConnection, SWT.NONE);
		managedForm.getToolkit().adapt(lblPort, true, true);
		lblPort.setText("Port:");

		portText = new Text(grpAgentConnection, SWT.BORDER);
		portText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		managedForm.getToolkit().adapt(portText, true, true);
		portText.addListener(SWT.KeyUp, copyListener);
		portText.addListener(SWT.KeyUp, pasteListener);

		Group group = new Group(composite, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		group.setText("Deployed Projects");
		managedForm.getToolkit().adapt(group);
		managedForm.getToolkit().paintBordersFor(group);
		group.setLayout(new GridLayout(2, false));

		ListViewer listViewer = new ListViewer(group, SWT.BORDER | SWT.V_SCROLL);
		list = listViewer.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite composite_1 = new Composite(group, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true,
				1, 1));
		managedForm.getToolkit().adapt(composite_1);
		managedForm.getToolkit().paintBordersFor(composite_1);
		composite_1.setLayout(new GridLayout(1, false));

		Button button = new Button(composite_1, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		button.setLayoutData(gd_button);

		button.setToolTipText("Add/Remove Project(s)");
		managedForm.getToolkit().adapt(button, true, true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((FileEditorInput) editor.getEditorInput()).getFile() instanceof IFile) {
					IFile file = ((FileEditorInput) editor.getEditorInput()).getFile();
					ExternalT24Server extServer = getServer();
					ManageProjectsDialog dialog = new ManageProjectsDialog(new Shell(), extServer);
					if (dialog.open() == Dialog.OK) {
						itemsListObserveWidget.clear();
						try {
							file.refreshLocal(IWorkspaceRoot.DEPTH_INFINITE, null);
						} catch (CoreException e1) {
							e1.printStackTrace();
						}
						String projects = null;
						for (IDSProject proj : extServer.getDsProjects()) {
							if (!itemsListObserveWidget.contains(proj.getName())) {
								itemsListObserveWidget.add(proj.getName());
								if (proj != null)
									projects = projects + "," + proj.getName();
							}
						}
						if (projects == null)
							setProperty("deployed.projects", "");
						else
							setProperty("deployed.projects", projects.replace("null,", ""));
					}
				}
			}
		});

		Bundle bundle = T24ServerPropertiesActivator.getDefault().getBundle();
		Path path = new Path("icons/addproj.gif");
		URL url = FileLocator.find(bundle, path, Collections.EMPTY_MAP);
		URL fileUrl = null;
		try {
			fileUrl = FileLocator.toFileURL(url);
		} catch (IOException e) {
			Logger.getLogger(ServerFormPageWebServicePage.class.getName()).log(Level.SEVERE, "IO Exception ", e);
		}
		try {
			button.setImage(new Image(Display.getCurrent(), new ImageData(
					new FileInputStream(fileUrl.getPath()))));
		} catch (FileNotFoundException e2) {
			Logger.getLogger(ServerFormPageWebServicePage.class.getName()).log(Level.SEVERE, "File not found exception ", e2);
		}
		td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabVertical = true;
		td.grabHorizontal = true;
		mainComposite.setLayoutData(td);

	}

	/**
	 * @param server
	 * @return
	 */
	private UpdateValueStrategy updateStrategy(final Server server,
			final String propName) {
		UpdateValueStrategy strat = new UpdateValueStrategy() {
			@Override
			protected IStatus doSet(IObservableValue observableValue,
					Object value) {
				IStatus doSet = Status.CANCEL_STATUS;;
				if (value != null) {
					doSet = super.doSet(observableValue, value.toString());
					setProperty(propName, value.toString());
				}
				return doSet;
			}
		};
		return strat;
	}

	private void setProperty(String propName, String propValue) {
		
		loadServerProperties();
		saveServerProperty(propName, propValue);
	}

	public DataBindingContext initDataBindings() {

		DataBindingContext bindingContext = new DataBindingContext();

		UpdateValueStrategy stratUserName = updateStrategy(server, "username");
		UpdateValueStrategy stratPwd = updateStrategy(server, "password");
		UpdateValueStrategy strathost = updateStrategy(server, "host");
		UpdateValueStrategy startprotocol = updateStrategy(server, "protocol");
		UpdateValueStrategy stratport = updateStrategy(server, "ws.port");
		UpdateValueStrategy stratBranch = updateStrategy(server, "branch");

		UpdateListStrategy listStrat = new UpdateListStrategy() {

			@Override
			protected IStatus doAdd(IObservableList observableList, Object element, int index) {
				IStatus status = Status.OK_STATUS;
				if (!(element.equals("") || element.equals(null))) {
					if (((FileEditorInput) editor.getEditorInput()).getFile() instanceof IFile) {
						ExternalT24Server extServer = getServer();
						if (extServer != null) {
							extServer.addDsProject(new DSProject(element.toString(), ResourcesPlugin.getWorkspace()
									.getRoot().toString()));
						}
					}
					status = super.doAdd(observableList, element, index);
				}
				return status;
			}

			@Override
			protected IStatus doRemove(IObservableList observableList, int index) {
				IStatus status = super.doRemove(observableList, index);
				EList<String> projects = server.getProjects();
				String projs = null;
				for (String string : projects) {
					if (projs != null)
						projs = projs + "," + string;
					else
						projs = string;
				}
				if (projs == null)
					setProperty("deployed.projects", "");
				else
					setProperty("deployed.projects", projs);
				return status;
			}
		};

		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(
				SWT.Modify).observe(usernameText);
		IObservableValue serverUsernameObserveValue = EMFEditObservables
				.observeValue(editor.getEditingDomain(), server,
						Literals.SERVER__USERNAME);
		bindingContext.bindValue(observeTextTextObserveWidget,
				serverUsernameObserveValue, stratUserName, stratUserName);
		//
		IObservableValue observeTextText_1ObserveWidget = WidgetProperties
				.text(SWT.Modify).observe(pwdText);
		IObservableValue serverPasswordObserveValue = EMFEditObservables
				.observeValue(editor.getEditingDomain(), server,
						Literals.SERVER__PASSWORD);
		bindingContext.bindValue(observeTextText_1ObserveWidget,
				serverPasswordObserveValue, stratPwd, stratPwd);
		//
		IObservableValue observeTextText_3ObserveWidget = WidgetProperties
				.text(SWT.Modify).observe(hostText);
		IObservableValue serverHostObserveValue = EMFEditObservables
				.observeValue(editor.getEditingDomain(), server,
						Literals.SERVER__HOST);
		bindingContext.bindValue(observeTextText_3ObserveWidget,
				serverHostObserveValue, strathost, strathost);
		//
		IObservableValue observeTextText_2ObserveWidget = WidgetProperties
				.text(SWT.Modify).observe(branchText);
		IObservableValue serverBranchObserveValue = EMFEditObservables
				.observeValue(editor.getEditingDomain(), server,
						Literals.SERVER__BRANCH);
		bindingContext.bindValue(observeTextText_2ObserveWidget,
				serverBranchObserveValue, stratBranch, stratBranch);
		//
		IObservableValue observeTextText_4ObserveWidget = WidgetProperties
				.text(SWT.Modify).observe(protocolText);
		IObservableValue serverEnvUsernameObserveValue = EMFEditObservables
				.observeValue(editor.getEditingDomain(), server,
						Literals.SERVER__PROTOCOL);
		bindingContext.bindValue(observeTextText_4ObserveWidget,
				serverEnvUsernameObserveValue, startprotocol, startprotocol);
		//
		IObservableValue observeTextText_6ObserveWidget = WidgetProperties
				.text(SWT.Modify).observe(portText);
		IObservableValue serverPortObserveValue = EMFEditObservables
				.observeValue(editor.getEditingDomain(), server,
						Literals.SERVER__PORT);
		bindingContext.bindValue(observeTextText_6ObserveWidget,
				serverPortObserveValue, stratport, stratport);
		//
		//
		itemsListObserveWidget = WidgetProperties.items().observe(list);
		IObservableList serverProjectsObserveList = EMFEditObservables
				.observeList(editor.getEditingDomain(), server,
						Literals.SERVER__PROJECTS);
		bindingContext.bindList(itemsListObserveWidget,
				serverProjectsObserveList, listStrat, listStrat);
		//
		return bindingContext;

	}

	public void refreshBindings(Server server2) {
		initDataBindings();
	}

	Listener copyListener = new Listener() {
		public void handleEvent(Event event) {
			if ((event.keyCode == 'c' || event.keyCode == 'C')
					&& (event.stateMask & SWT.CTRL) != 0) {
				((Text) event.widget).copy();
			}
		}
	};

	Listener pasteListener = new Listener() {
		public void handleEvent(Event event) {
			if ((event.keyCode == 'v' || event.keyCode == 'V')
					&& (event.stateMask & SWT.CTRL) != 0) {
				((Text) event.widget).paste();
			}
		}
	};
	
	/**
	 * @param extServer
	 * @return
	 */
	public ExternalT24Server getServer() {
		IFile file = ((FileEditorInput) editor.getEditorInput()).getFile();
		java.util.List<IDSServer> servers = ServerUICore.getDefault().getContributions().getServers();
		ExternalT24Server extServer = null;
		for (IDSServer idsServer : servers) {
		    if(idsServer.getServerProject().getName().equalsIgnoreCase(file.getProject().getName()))
		    	extServer  =  (ExternalT24Server)idsServer;
		}
		return extServer;
	}
	
	private void loadServerProperties() {
		serverProperties = new ServerProperties();
		InputStream is = null;
		IEditorInput editorInput = editor.getEditorInput();
		if (((FileEditorInput) editorInput).getFile() instanceof IFile) {
			IFile file = ((FileEditorInput) editorInput).getFile();
			IPath fullPath = file.getLocation();
			File tempFile = fullPath.toFile();
			try {
				is = new FileInputStream(tempFile);
				serverProperties.load(is);
				is.close();
			} catch (IOException e) {
				is = null;
				Logger.getLogger(ServerFormPageWebServicePage.class.getName()).log(Level.SEVERE, "Read server.properties file is failed.", e);
			}
		}
	}
	
	private void saveServerProperty(String key, String value){
		if (serverProperties == null) {
			serverProperties = new ServerProperties();
		}
		if (key.contains("password")) {
			value = passwordScrambler(value);
		}
		String oldValue = serverProperties.getProperty(key);
		if (oldValue != null && oldValue.equals(value) && key != null && !key.equals("deployed.projects")) {
			return;
		}
		serverProperties.setProperty(key, value);
		IEditorInput editorInput = editor.getEditorInput();
		if (((FileEditorInput) editorInput).getFile() instanceof IFile) {
			IFile file = ((FileEditorInput) editorInput).getFile();
			IPath fullPath = file.getLocation();
			File serverFile = fullPath.toFile();
			writeToFile(serverFile);
			try {
				if (!file.isSynchronized(IResource.DEPTH_ONE)) {
					file.refreshLocal(IResource.DEPTH_ONE, null);
				}
			} catch (CoreException e) {
				Logger.getLogger(ServerFormPageWebServicePage.class.getName()).log(Level.SEVERE, "Refresh failed.", e);
			}
		}
	}
	
	/**
	 * @param propName
	 * @param propValue
	 * @param fileContent
	 */
	private String passwordScrambler(String value) {
		ServerPropertiesPasswordScrambler scrambler = new ServerPropertiesPasswordScrambler();
		String scramble = new String();
		if(!value.isEmpty()) {
			scramble = scrambler.encode(value);
		}
		if (scramble != null) {
			return scramble.trim();
		}
		return new String();
	}
	
	/**
	 * @param serverFile
	 */
	private void writeToFile(File serverFile) {
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		String equals = "=";
		FileWriter fileWriter = null;
		Set<Entry<Object, Object>> entry = serverProperties.entrySet();
		for (Iterator<Entry<Object, Object>> iterator = entry.iterator(); iterator.hasNext();) {
			Entry<Object, Object> entry2 = (Entry<Object, Object>) iterator.next();
			String value = entry2.getValue().toString();
			String key = entry2.getKey().toString();
			stringBuilder.append(Messages.getString(key));
			stringBuilder.append(ls);
			stringBuilder.append(key);
			stringBuilder.append(equals);
			stringBuilder.append(value);
			stringBuilder.append(ls);
			stringBuilder.append(ls);
		}
		try {
			fileWriter = new FileWriter(serverFile);
			fileWriter.write(stringBuilder.toString());
			fileWriter.close();
		} catch (IOException e) {
			Logger.getLogger(ServerFormPageWebServicePage.class.getName()).log(Level.SEVERE, "Write server.properties file is failed.", e);
			fileWriter = null;
		}
	}
}
