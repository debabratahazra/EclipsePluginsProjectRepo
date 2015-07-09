package com.odcgroup.t24.server.properties.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
import org.eclipse.swt.widgets.DirectoryDialog;
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

public class ServerFormPageAgentConnPage extends FormPage {

	private ServerFormEditor editor;
	private Text usernameText;
	private Text passwordText;
	private Text branchText;
	private Text hostText;
	private Text envUsernameText;
	private Text envPwdtext;
	private Text portText;
	private Text ofsIDText;
	private List list;
	private ISWTObservableList itemsListObserveWidget;

	private Server server;
	private Button ftp;
	private Button sftp;
	private Button local;
	private Button unix;
	private Button linux;
	private Button windows;
	private DataBindingContext bindingContext;
	private UpdateValueStrategy stratProtocolsftp;
	private UpdateValueStrategy stratProtocolftp;
	private UpdateValueStrategy stratProtocolslocal;
	private UpdateValueStrategy stratOstypeWin;
	private UpdateValueStrategy stratOstypeLin;
	private UpdateValueStrategy stratOstypeUnx;
	private UpdateValueStrategy stratTafcHome;
	private Text displayLocation;
	private Button browseButton;

	private ServerProperties serverProperties;

	/**
	 * Create the form page.
	 * @param editor
	 * @param id
	 * @param title
	 * @param server
	 * @param server
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter id "id"
	 * @wbp.eval.method.parameter title "title"
	 */
	public ServerFormPageAgentConnPage(ServerFormEditor editor, String id, String title, Server server) {
		super(editor, id, title);
		this.editor = editor;
		this.server = server;
	}

	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
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

		Composite composite_6 = new Composite(mainComposite, SWT.NONE);
		composite_6.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		managedForm.getToolkit().adapt(composite_6);
		managedForm.getToolkit().paintBordersFor(composite_6);
		composite_6.setLayout(new GridLayout(1, false));

		Composite composite_8 = new Composite(composite_6, SWT.NONE);
		composite_8.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().adapt(composite_8);
		managedForm.getToolkit().paintBordersFor(composite_8);
		composite_8.setLayout(new GridLayout(1, false));

		Group grpT = new Group(composite_8, SWT.NONE);
		grpT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		grpT.setLayout(new GridLayout(2, false));
		grpT.setText("T24 User (deployment only)");
		managedForm.getToolkit().adapt(grpT);
		managedForm.getToolkit().paintBordersFor(grpT);

		Label lblUsername = new Label(grpT, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.LEFT, false, false, 1, 1));
		managedForm.getToolkit().adapt(lblUsername, true, true);
		lblUsername.setText("Username:");

		usernameText = new Text(grpT, SWT.BORDER);
		usernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().adapt(usernameText, true, true);
		usernameText.addListener(SWT.KeyUp, copyListener);
		usernameText.addListener(SWT.KeyUp, pasteListener);

		Label lblPassword = new Label(grpT, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
		managedForm.getToolkit().adapt(lblPassword, true, true);
		lblPassword.setText("Password:");

		passwordText = new Text(grpT, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().adapt(passwordText, true, true);
		passwordText.addListener(SWT.KeyUp, copyListener);
		passwordText.addListener(SWT.KeyUp, pasteListener);

		Label lblBranch = new Label(grpT, SWT.NONE);
		lblBranch.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
		managedForm.getToolkit().adapt(lblBranch, true, true);
		lblBranch.setText("Branch:");

		branchText = new Text(grpT, SWT.BORDER);
		branchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().adapt(branchText, true, true);
		branchText.addListener(SWT.KeyUp, copyListener);
		branchText.addListener(SWT.KeyUp, pasteListener);

		Composite composite_4 = new Composite(mainComposite, SWT.NONE);
		composite_4.setLayout(new GridLayout(2, false));
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().adapt(composite_4);
		managedForm.getToolkit().paintBordersFor(composite_4);

		Composite composite_3 = new Composite(composite_4, SWT.NONE);
		composite_3.setLayout(new GridLayout(1, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().adapt(composite_3);
		managedForm.getToolkit().paintBordersFor(composite_3);

		Group grpAgentConnection = new Group(composite_3, SWT.NONE);
		grpAgentConnection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAgentConnection.setLayout(new GridLayout(1, false));
		grpAgentConnection.setText("Agent Connection");
		managedForm.getToolkit().adapt(grpAgentConnection);
		managedForm.getToolkit().paintBordersFor(grpAgentConnection);

		Composite composite = new Composite(grpAgentConnection, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setLayout(new GridLayout(2, false));
		managedForm.getToolkit().adapt(composite);
		managedForm.getToolkit().paintBordersFor(composite);

		Label lblEnvironmentHost = new Label(composite, SWT.NONE);
		managedForm.getToolkit().adapt(lblEnvironmentHost, true, true);
		lblEnvironmentHost.setText("Hostname:");

		hostText = new Text(composite, SWT.BORDER);
		hostText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().adapt(hostText, true, true);
		hostText.addListener(SWT.KeyUp, copyListener);
		hostText.addListener(SWT.KeyUp, pasteListener);

		Label lblEnvironmentUsername = new Label(composite, SWT.NONE);
		managedForm.getToolkit().adapt(lblEnvironmentUsername, true, true);
		lblEnvironmentUsername.setText("Environment Username:");

		envUsernameText = new Text(composite, SWT.BORDER);
		envUsernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		managedForm.getToolkit().adapt(envUsernameText, true, true);
		envUsernameText.addListener(SWT.KeyUp, copyListener);
		envUsernameText.addListener(SWT.KeyUp, pasteListener);

		Label lblEnvironmentPassword = new Label(composite, SWT.NONE);
		managedForm.getToolkit().adapt(lblEnvironmentPassword, true, true);
		lblEnvironmentPassword.setText("Environment Password:");

		envPwdtext = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		envPwdtext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		managedForm.getToolkit().adapt(envPwdtext, true, true);
		envPwdtext.addListener(SWT.KeyUp, copyListener);
		envPwdtext.addListener(SWT.KeyUp, pasteListener);

		Label lblEnvironmentPort = new Label(composite, SWT.NONE);
		managedForm.getToolkit().adapt(lblEnvironmentPort, true, true);
		lblEnvironmentPort.setText("Environment Port:");

		portText = new Text(composite, SWT.BORDER);
		portText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		managedForm.getToolkit().adapt(portText, true, true);
		portText.addListener(SWT.KeyUp, copyListener);
		portText.addListener(SWT.KeyUp, pasteListener);

		Label lblOfsId = new Label(composite, SWT.NONE);
		managedForm.getToolkit().adapt(lblOfsId, true, true);
		lblOfsId.setText("OFS ID:");

		ofsIDText = new Text(composite, SWT.BORDER);
		ofsIDText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		managedForm.getToolkit().adapt(ofsIDText, true, true);
		ofsIDText.addListener(SWT.KeyUp, copyListener);
		ofsIDText.addListener(SWT.KeyUp, pasteListener);

		Composite composite_7 = new Composite(composite_4, SWT.NONE);
		composite_7.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().adapt(composite_7);
		managedForm.getToolkit().paintBordersFor(composite_7);
		composite_7.setLayout(new GridLayout(1, false));

		Group group = new Group(composite_7, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		group.setText("Deployed Projects");
		managedForm.getToolkit().adapt(group);
		managedForm.getToolkit().paintBordersFor(group);
		group.setLayout(new GridLayout(2, false));

		ListViewer listViewer = new ListViewer(group, SWT.BORDER | SWT.V_SCROLL);
		list = listViewer.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite composite_5 = new Composite(group, SWT.NONE);
		composite_5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		managedForm.getToolkit().adapt(composite_5);
		managedForm.getToolkit().paintBordersFor(composite_5);
		composite_5.setLayout(new GridLayout(1, false));

		Button btnAdd = new Button(composite_5, SWT.NONE);
		GridData gd_btnAdd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAdd.widthHint = 30;
		btnAdd.setLayoutData(gd_btnAdd);
		managedForm.getToolkit().adapt(btnAdd, true, true);
		btnAdd.setToolTipText("Add/Remove Project(s)");

		Bundle bundle = T24ServerPropertiesActivator.getDefault().getBundle();
		Path path = new Path("icons/addproj.gif");
		URL url = FileLocator.find(bundle, path, Collections.EMPTY_MAP);
		URL fileUrl = null;
		try {
			fileUrl = FileLocator.toFileURL(url);
		}
		catch (IOException e) {
			Logger.getLogger(ServerFormPageAgentConnPage.class.getName()).log(Level.SEVERE, "IO Exception ", e);
		}
		try {
			btnAdd.setImage(new Image(Display.getCurrent(), new ImageData(new FileInputStream(fileUrl.getPath()))));
		} catch (FileNotFoundException e2) {
			Logger.getLogger(ServerFormPageAgentConnPage.class.getName()).log(Level.SEVERE, "File Not Found Exception ", e2);
		}

		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((FileEditorInput) editor.getEditorInput()).getFile() instanceof IFile) {
					IFile file = ((FileEditorInput) editor.getEditorInput()).getFile();
					ExternalT24Server extServer = getServer();
					ManageProjectsDialog dialog = new ManageProjectsDialog(new Shell(), extServer);
					if (dialog.open() == Dialog.OK) {
						itemsListObserveWidget.clear();
						try {
							if(!file.isSynchronized(IWorkspaceRoot.DEPTH_INFINITE)){
								file.refreshLocal(IWorkspaceRoot.DEPTH_INFINITE, null);
							}
						} catch (CoreException e1) {
							Logger.getLogger(ServerFormPageAgentConnPage.class.getName()).log(Level.SEVERE, "Refresh issue.", e);
						}
						String projects = null;
						for (IDSProject proj : extServer.getDeployedProjects()) {
							if (!itemsListObserveWidget.contains(proj.getName())) {
								itemsListObserveWidget.add(proj.getName());
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

		Group group_1 = new Group(composite_3, SWT.NONE);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		group_1.setText("File Transfer (for TAFC jBC Compile)");
		managedForm.getToolkit().adapt(group_1);
		managedForm.getToolkit().paintBordersFor(group_1);
		group_1.setLayout(new GridLayout(1, false));

		Composite composite_1 = new Composite(group_1, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		managedForm.getToolkit().adapt(composite_1);
		managedForm.getToolkit().paintBordersFor(composite_1);
		composite_1.setLayout(new GridLayout(6, false));

		Label label = new Label(composite_1, SWT.NONE);
		label.setText("OS Type:  ");
		managedForm.getToolkit().adapt(label, true, true);

		windows = new Button(composite_1, SWT.RADIO);
		windows.setText("Windows");
		managedForm.getToolkit().adapt(windows, true, true);
		windows.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (windows != null && local != null) {
					local.setEnabled(windows.getSelection());
					if (!windows.getSelection() && local.getSelection()) {
						sftp.setSelection(true);
						local.setSelection(false);
						setEnableLocation(false);
					}
				}
			}
		});
		new Label(composite_1, SWT.NONE);

		unix = new Button(composite_1, SWT.RADIO);
		unix.setText("Unix");
		unix.setSelection(true);
		managedForm.getToolkit().adapt(unix, true, true);
		new Label(composite_1, SWT.NONE);

		linux = new Button(composite_1, SWT.RADIO);
		linux.setText("Linux");
		managedForm.getToolkit().adapt(linux, true, true);

		Composite composite_2 = new Composite(group_1, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		managedForm.getToolkit().adapt(composite_2);
		managedForm.getToolkit().paintBordersFor(composite_2);
		composite_2.setLayout(new GridLayout(7, false));

		Label label_1 = new Label(composite_2, SWT.NONE);
		label_1.setText("Protocol:");
		managedForm.getToolkit().adapt(label_1, true, true);

		ftp = new Button(composite_2, SWT.RADIO);
		ftp.setText("FTP         ");
		managedForm.getToolkit().adapt(ftp, true, true);

		sftp = new Button(composite_2, SWT.RADIO);
		sftp.setText("SFTP");
		sftp.setSelection(true);
		managedForm.getToolkit().adapt(sftp, true, true);
		new Label(composite_2, SWT.NONE);

		local = new Button(composite_2, SWT.RADIO);
		local.setText("Local");
		managedForm.getToolkit().adapt(local, true, true);
		local.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				setEnableLocation(local.getSelection());
			}
		});

		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		Label label_location = new Label(composite_2, SWT.NONE);
		label_location.setText("T24 (bnk.run) directory:");
		label_location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		managedForm.getToolkit().adapt(label_location, true, true);

		displayLocation = new Text(composite_2, SWT.LEFT | SWT.BORDER | SWT.READ_ONLY);
		GridData locationCombodata = new GridData(GridData.HORIZONTAL_ALIGN_END, SWT.CENTER, true, true, 4, 1);
		locationCombodata.minimumWidth = 350;
		displayLocation.setLayoutData(locationCombodata);

		browseButton = new Button(composite_2, SWT.NONE);
		browseButton.setText("Browse");
		managedForm.getToolkit().adapt(browseButton, true, true);
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DirectoryDialog dialog = new DirectoryDialog(new Shell(browseButton.getDisplay()));
				dialog.setText("T24 (bnk.run) directory");
				dialog.setMessage("Select T24 (bnk.run) directory");
				if (!displayLocation.getText().isEmpty()) {
					dialog.setFilterPath(displayLocation.getText());
				}
				String dir = dialog.open();
				if (dir != null) {
					updateLocation(dir);
				}
			}
		});

		td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabVertical = true;
		td.grabHorizontal = true;
		mainComposite.setLayoutData(td);
	}

	/**
	 * Get all the list of files and folder in 
	 * selected directory.
	 * @param dir
	 */
	private void updateLocation(String dir) {
		displayLocation.setText(dir);
		GridData locationCombodata = new GridData(GridData.HORIZONTAL_ALIGN_END);
		locationCombodata.minimumWidth = 350;
		displayLocation.setLayoutData(locationCombodata);
	}

	/**
	 * Enable for Location - Combo-box and Browse button
	 * @param enabled
	 */
	private void setEnableLocation(boolean enabled) {
		displayLocation.setEnabled(enabled);
		browseButton.setEnabled(enabled);
		if (enabled) {
			displayLocation.setForeground(displayLocation.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		} else {
			displayLocation.setForeground(displayLocation.getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}
	}

	public DataBindingContext initDataBindings() {
		bindingContext = new DataBindingContext();

		UpdateValueStrategy stratUserName = updateStrategy(server, "t24username");
		UpdateValueStrategy stratPwd = updateStrategy(server, "t24password");
		UpdateValueStrategy stratBranch = updateStrategy(server, "branch");
		UpdateValueStrategy strathost = updateStrategy(server, "host");
		UpdateValueStrategy startEnvUsername = updateStrategy(server, "username");
		UpdateValueStrategy stratEnvPwd = updateStrategy(server, "password");
		UpdateValueStrategy stratPort = updateStrategy(server, "agent.port");
		UpdateValueStrategy stratTafcHome = updateStrategy(server, "tafchome");

		UpdateListStrategy listStrat = new UpdateListStrategy() {
			private String projectNames = null;

			@Override
			protected IStatus doAdd(IObservableList observableList, Object element, int index) {
				IStatus status = Status.OK_STATUS;
				if (!(element.equals("") || element.equals(null))) {
					ExternalT24Server extServer = null;
					if (((FileEditorInput) editor.getEditorInput()).getFile() instanceof IFile) {
						extServer = getServer();
					}
					if (extServer != null) {
						extServer.addDsProject(new DSProject(element.toString(), ResourcesPlugin.getWorkspace()
								.getRoot().toString()));
					}
				}
				status = super.doAdd(observableList, element, index);
				return status;
			}

			@Override
			protected IStatus doRemove(IObservableList observableList, int index) {
				IStatus status = super.doRemove(observableList, index);
				ExternalT24Server extServer = getServer();
				java.util.List<IDSProject> projectsList = new ArrayList<IDSProject>();
				if (extServer != null) {
					projectsList = extServer.getDsProjects();
				}
				try {
					// Remove all from ObservableList
					for (int i = 0; i < observableList.size(); i++) {
						observableList.remove(i);
					}
					// Add selected projects to ObservableList
					for (Iterator<IDSProject> iterator = projectsList.iterator(); iterator.hasNext();) {
						IDSProject idsProject = (IDSProject) iterator.next();
						observableList.add(idsProject);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				projectNames = null;
				for (IDSProject project : projectsList) {
					if (projectNames != null)
						projectNames = projectNames + "," + project.getName();
					else
						projectNames = project.getName();
				}
				if (projectNames == null)
					setProperty("deployed.projects", "");
				else
					setProperty("deployed.projects", projectNames);
				return status;
			}
		};

		UpdateValueStrategy stratOfsID = updateStrategy(server, "ofsid");
		stratProtocolsftp = updateStrategyForProtocol(server, "protocol", "sftp");
		stratProtocolftp = updateStrategyForProtocol(server, "protocol", "ftp");
		stratProtocolslocal = updateStrategyForProtocol(server, "protocol", "local");
		stratOstypeWin = updateStrategyForOS(server, "ostype", "windows");
		stratOstypeLin = updateStrategyForOS(server, "ostype", "linux");
		stratOstypeUnx = updateStrategyForOS(server, "ostype", "unix");

		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(usernameText);
		IObservableValue serverUsernameObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__ENV_USERNAME);
		bindingContext.bindValue(observeTextTextObserveWidget, serverUsernameObserveValue, stratUserName, stratUserName);
		//
		IObservableValue observeTextText_1ObserveWidget = WidgetProperties.text(SWT.Modify).observe(passwordText);
		IObservableValue serverPasswordObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__ENV_PASSWORD);
		bindingContext.bindValue(observeTextText_1ObserveWidget, serverPasswordObserveValue, stratPwd, stratPwd);
		//
		IObservableValue observeTextText_2ObserveWidget = WidgetProperties.text(SWT.Modify).observe(branchText);
		IObservableValue serverBranchObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__BRANCH);
		bindingContext.bindValue(observeTextText_2ObserveWidget, serverBranchObserveValue, stratBranch, stratBranch);
		//
		IObservableValue observeTextText_3ObserveWidget = WidgetProperties.text(SWT.Modify).observe(hostText);
		IObservableValue serverHostObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__HOST);
		bindingContext.bindValue(observeTextText_3ObserveWidget, serverHostObserveValue, strathost, strathost);
		//
		IObservableValue observeTextText_4ObserveWidget = WidgetProperties.text(SWT.Modify).observe(envUsernameText);
		IObservableValue serverEnvUsernameObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__USERNAME);
		bindingContext.bindValue(observeTextText_4ObserveWidget, serverEnvUsernameObserveValue, startEnvUsername, startEnvUsername);
		//
		IObservableValue observeTextText_5ObserveWidget = WidgetProperties.text(SWT.Modify).observe(envPwdtext);
		IObservableValue serverEnvPasswordObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__PASSWORD);
		bindingContext.bindValue(observeTextText_5ObserveWidget, serverEnvPasswordObserveValue, stratEnvPwd, stratEnvPwd);
		//
		IObservableValue observeTextText_6ObserveWidget = WidgetProperties.text(SWT.Modify).observe(portText);
		IObservableValue serverPortObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__PORT);
		bindingContext.bindValue(observeTextText_6ObserveWidget, serverPortObserveValue, stratPort, stratPort);
		//
		itemsListObserveWidget = WidgetProperties.items().observe(list);
		IObservableList serverProjectsObserveList = EMFEditObservables.observeList(editor.getEditingDomain(),server, Literals.SERVER__PROJECTS);
		bindingContext.bindList(itemsListObserveWidget, serverProjectsObserveList, listStrat, listStrat);
		//
		IObservableValue observeTextText_7ObserveWidget = WidgetProperties.text(SWT.Modify).observe(ofsIDText);
		IObservableValue serverOfsIDObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__OFS_ID);
		bindingContext.bindValue(observeTextText_7ObserveWidget, serverOfsIDObserveValue, stratOfsID, stratOfsID);

		//
		IObservableValue observeTextTafcHomeObserveWidget = WidgetProperties.text(SWT.Modify).observe(displayLocation);
		IObservableValue serverTafcHomeObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__TAFCHOME);
		bindingContext.bindValue(observeTextTafcHomeObserveWidget, serverTafcHomeObserveValue, stratTafcHome, stratTafcHome);

		databindingForRadioButtons();

		return bindingContext;
	}

	/**
	 * @param server
	 * @return
	 */
	private UpdateValueStrategy updateStrategy(final Server server, final String propName) {
		UpdateValueStrategy strat = new UpdateValueStrategy() {
			@Override
			protected IStatus doSet(IObservableValue observableValue,
					Object value) {
				IStatus doSet = Status.OK_STATUS;
				if (value != null) {
					doSet = super.doSet(observableValue, value);
					setProperty(propName, value.toString());
				}
				return doSet;
			}
		};
		return strat;
	}

	/**
	 * @param server
	 * @return
	 */
	private UpdateValueStrategy updateStrategyForProtocol(final Server server, final String propName, final String protocol) {
		UpdateValueStrategy strat = new UpdateValueStrategy() {
			@Override
			protected IStatus doSet(IObservableValue observableValue,
					Object value) {
				IStatus doSet = null;
				if (value != null) {
					value = protocol;
					doSet = super.doSet(observableValue, value);
					setProperty(propName, value.toString());
				}
				return doSet;
			}
		};
		return strat;
	}

	/**
	 * @param server
	 * @return
	 */
	private UpdateValueStrategy updateStrategyForOS(final Server server, final String propName, final String os) {
		UpdateValueStrategy strat = new UpdateValueStrategy() {
			@Override
			protected IStatus doSet(IObservableValue observableValue,
					Object value) {
				IStatus doSet = null;
				if (value != null) {
					value = os;
					doSet = super.doSet(observableValue, value);
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

	/**
	 * @param propName
	 * @param propValue
	 * @param fileContent
	 */
	private String passwordScrambler(String value) {
		ServerPropertiesPasswordScrambler scrambler = new ServerPropertiesPasswordScrambler();
		String scramble = new String();
		if (!value.isEmpty()) {
			scramble = scrambler.encode(value);
		}
		if (scramble != null) {
			return scramble.trim();
		}
		return new String();
	}

	public void refreshBindings(Server server) {
		initDataBindings();
	}

	Listener copyListener = new Listener() {
		public void handleEvent(Event event) {
			if ((event.keyCode == 'c' || event.keyCode == 'C') && (event.stateMask & SWT.CTRL) != 0) {
				((Text) event.widget).copy();
			}
		}
	};

	Listener pasteListener = new Listener() {
		public void handleEvent(Event event) {
			if ((event.keyCode == 'v' || event.keyCode == 'V') && (event.stateMask & SWT.CTRL) != 0) {
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
			if (idsServer.getServerProject().getName().equalsIgnoreCase(file.getProject().getName()))
				extServer = (ExternalT24Server) idsServer;
		}
		return extServer;
	}

	/**
	 * 
	 */
	public void databindingForRadioButtons() {
		IObservableValue ftpbserveWidget = WidgetProperties.selection().observe(ftp);
		IObservableValue sftpObserveValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__PROTOCOL);
		bindingContext.bindValue(ftpbserveWidget, sftpObserveValue, stratProtocolftp, null);
		//
		IObservableValue sftpWidget = WidgetProperties.selection().observe(sftp);
		bindingContext.bindValue(sftpWidget, sftpObserveValue, stratProtocolsftp, null);

		IObservableValue localWidget = WidgetProperties.selection().observe(local);
		bindingContext.bindValue(localWidget, sftpObserveValue, stratProtocolslocal, null);
		//
		IObservableValue ostypeUnix = WidgetProperties.selection().observe(unix);
		IObservableValue ostyeValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__OSTYPE);
		bindingContext.bindValue(ostypeUnix, ostyeValue, stratOstypeUnx, null);
		//
		IObservableValue ostypeLinux = WidgetProperties.selection().observe(linux);
		bindingContext.bindValue(ostypeLinux, ostyeValue, stratOstypeLin, null);
		//
		IObservableValue ostypeWin = WidgetProperties.selection().observe(windows);
		bindingContext.bindValue(ostypeWin, ostyeValue, stratOstypeWin, null);

		IObservableValue tafcHomeValue = EMFEditObservables.observeValue(editor.getEditingDomain(), server, Literals.SERVER__TAFCHOME);
		IObservableValue tafcHomeLocation = WidgetProperties.text(SWT.Modify).observe(displayLocation);
		bindingContext.bindValue(tafcHomeLocation, tafcHomeValue, stratTafcHome, null);

		//
		if (server.getProtocol() != null) {
			if (server.getProtocol().equals("ftp")) {
				ftp.setSelection(true);
				sftp.setSelection(false);
				local.setSelection(false);
			} else if (server.getProtocol().equals("sftp")) {
				sftp.setSelection(true);
				ftp.setSelection(false);
				local.setSelection(false);
			} else if (server.getProtocol().equals("local")) {
				sftp.setSelection(false);
				ftp.setSelection(false);
				local.setSelection(true);
			}
		}
		//
		if (server.getOstype() != null) {
			if (server.getOstype().equals("windows")) {
				windows.setSelection(true);
				unix.setSelection(false);
				linux.setSelection(false);
				if (local != null) {
					local.setEnabled(true);
				}
			} else if (server.getOstype().equals("linux")) {
				windows.setSelection(false);
				unix.setSelection(false);
				linux.setSelection(true);
				if (local != null) {
					local.setEnabled(false);
				}
			} else if (server.getOstype().equals("unix")) {
				windows.setSelection(false);
				unix.setSelection(true);
				linux.setSelection(false);
				if (local != null) {
					local.setEnabled(false);
				}
			}
		}
		setEnableLocation(local.getSelection());
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
				Logger.getLogger(ServerFormPageAgentConnPage.class.getName()).log(Level.SEVERE, "Read server.properties file is failed.", e);
			}
		}
	}

	private void saveServerProperty(String key, String value) {
		if (serverProperties == null) {
			serverProperties = new ServerProperties();
		}
		if (key.contains("password")) {
			value = passwordScrambler(value);
		}
		String oldValue = serverProperties.getProperty(key);
		if (oldValue != null && oldValue.equals(value) && key != null && !key.equals("deployed.projects")) {
			// if old value and new value are same, no need to write to file.
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
			if(key.equals("tafchome")){
				value = value.replace("\\", "\\\\");
			}
			stringBuilder.append(value);
			stringBuilder.append(ls);
			stringBuilder.append(ls);
		}
		try {
			fileWriter = new FileWriter(serverFile);
			fileWriter.write(stringBuilder.toString());
			fileWriter.close();
		} catch (IOException e) {
			Logger.getLogger(ServerFormPageAgentConnPage.class.getName()).log(Level.SEVERE, "Write server.properties file is failed.", e);
			fileWriter = null;
		}
	}
}
