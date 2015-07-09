package com.odcgroup.t24.server.properties.ui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.t24.server.external.ui.AddT24ExternalServerAction;
import com.odcgroup.t24.server.properties.Messages;
import com.odcgroup.t24.server.properties.server.Server;
import com.odcgroup.t24.server.properties.server.impl.ServerFactoryImpl;
import com.odcgroup.t24.server.properties.util.ServerProperties;
import com.odcgroup.t24.server.properties.wizards.ServerPropertiesConnectionTypePage;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

/**
 * @author rak
 *
 */
public class ServerMultiPageEditor extends MultiPageEditorPart implements IEditingDomainProvider {
	
	private TextEditor textEditor;
	private ServerFormEditor formEditor;
	private Server server;
	private ServerProperties serverProperties;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void createPages() {
		server = ServerFactoryImpl.getPackage().getServerFactory().createServer();
		createServerFormEditor();
		createServerTextEditor();	
		refreshServer();
	}
	
	

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		serverProperties = new ServerProperties();
	}
	
	private void refreshServer() {
		if(formEditor.getSelectedPage() instanceof ServerFormPageAgentConnPage)
			((ServerFormPageAgentConnPage)formEditor.getSelectedPage()).initDataBindings();
		else
			((ServerFormPageWebServicePage)formEditor.getSelectedPage()).initDataBindings();
		ServerPropertiesConnectionTypePage.currentSelected = null;
		
		updateServer();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (formEditor.isDirty()) {
			formEditor.doSave(monitor);
		} else if (textEditor.isDirty()) {
			formEditor.changeFromOtherTab = true;
			textEditor.doSave(monitor);
			setProperties();
			if(formEditor.getSelectedPage() instanceof ServerFormPageAgentConnPage)
				((ServerFormPageAgentConnPage)formEditor.getSelectedPage()).databindingForRadioButtons();
		}
		//site manager server
		updateServer();
	}

	/**
	 * 
	 */
	private void updateServer() {
		if (!formEditor.isConnectionTypeSelected()) {
			if (formEditor.getSelectedPage() instanceof ServerFormPageAgentConnPage) {
				String name = ((FileEditorInput) getEditorInput()).getFile().getProject().getName();
				try {
					T24BasicPlugin.getDefault().updateServer(name);
				} catch (T24ServerException e) {
					Logger.getLogger(ServerMultiPageEditor.class.getName()).log(Level.SEVERE, "Update Server failed.", e);
				}
			}
		}
		formEditor.setConnectionTypeSelected(false);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#pageChange(int)
	 */
	protected void pageChange(int newPageIndex) {
		if ( (newPageIndex == 1 && formEditor.isDirty()) 
				|| (newPageIndex == 0 && textEditor.isDirty())) {
			if (querySwitchTab()) {
				super.pageChange(newPageIndex);
				IEditorPart active = getActiveEditor();
				if (isDirty()) {
					doSave(new NullProgressMonitor());
					if (active == textEditor) {
						textEditor.setInput(getEditorInput());
					}
				}
			} else {
				if (newPageIndex == 0) {
					newPageIndex = 1;
				} else {
					newPageIndex = 0;
				}
				setActivePage(newPageIndex);
				super.pageChange(newPageIndex);
			}
		} else {
			super.pageChange(newPageIndex);
		}
	}
	
	
	private void createServerTextEditor() {
		try {
			textEditor = new TextEditor();
			addPage(1, textEditor, getEditorInput());
			String currentSelected = ServerPropertiesConnectionTypePage.currentSelected;
			if (currentSelected == null) {
				String textEditorStrings = getTextEditorStrings();
				InputStream stream = new ByteArrayInputStream(textEditorStrings.getBytes("UTF-8"));
				serverProperties.load(stream);
				if(serverProperties.containsKey("agent.port")){
					setProperties();
				} else {
					setWebServicesProperties();
				}
			} else if (currentSelected == "WebServices") {
				setWebServicesProperties();
			} else {
				setProperties();
			}

			setPageText(1, "Source");
			if(formEditor.isConnectionTypeSelected()) {
				setActivePage(1);
			}
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating text editor", null, e.getStatus());
		} catch (IOException e) {
			Logger.getLogger(ServerMultiPageEditor.class.getName()).log(Level.SEVERE, "IO Exception.", e);
		}

	}

	private void createDefaultsPropertiesForWS(String textEditorStrings) {
		
		serverProperties = new ServerProperties();
		
		serverProperties.setProperty("host", "");
		serverProperties.setProperty("password", "");
		serverProperties.setProperty("branch", "");
		serverProperties.setProperty("ws.port", "");
		serverProperties.setProperty("protocol", ServerPropertiesHelper.DEFAULT_WS_PROTOCOL);
		serverProperties.setProperty("username", "");
		String deployedProjectsName = "";
		try {
			deployedProjectsName = AddT24ExternalServerAction.addDeplyedProjectsName();
		} catch (CoreException e) {
			Logger.getLogger(ServerMultiPageEditor.class.getName()).log(Level.SEVERE, "Core Exception.", e);
		}
		serverProperties.setProperty("deployed.projects", deployedProjectsName);
		
		IEditorInput editorInput = this.getEditorInput();
		if (((FileEditorInput) editorInput).getFile() instanceof IFile) {
			IFile file = ((FileEditorInput) editorInput).getFile();
			IPath fullPath = file.getLocation();
			File serverFile = fullPath.toFile();
			writeToFile(serverFile);
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
			if(key.equals("protocol") && ServerPropertiesConnectionTypePage.currentSelected.equals("WebServices")){
				stringBuilder.append(Messages.getString("ws.protocol"));
			}else{
				stringBuilder.append(Messages.getString(key));
			}
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
	

	/**
	 * 
	 */
	private void setProperties() {
		String textEditorStrings = getTextEditorStrings();
		try {
			InputStream stream = new ByteArrayInputStream(textEditorStrings.getBytes("UTF-8"));
			serverProperties.load(stream);
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
			Logger.getLogger(ServerMultiPageEditor.class.getName()).log(Level.SEVERE, "IO Exception.", e);
		}
		if(serverProperties.containsKey("ws.port")){
			ServerPropertiesConnectionTypePage.currentSelected = "WebServices";
		}else{
			ServerPropertiesConnectionTypePage.currentSelected = "Agent_Connection";
		}
		String currentSelected = ServerPropertiesConnectionTypePage.currentSelected;
		if(currentSelected.equals("Agent_Connection")){
			setAgentProperties();
			formEditor.setPage(1);
		}
		else{
			setWebServicesProperties();
			formEditor.setPage(2);
		}
	}

	private void setWebServicesProperties() {
		boolean overwrite = true;
		String textEditorStrings = getTextEditorStrings();
		try {
			InputStream stream = new ByteArrayInputStream(textEditorStrings.getBytes("UTF-8"));
			serverProperties.load(stream);
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
			Logger.getLogger(ServerMultiPageEditor.class.getName()).log(Level.SEVERE, "IO Exception.", e);
		}
		if(serverProperties.containsKey("ws.port")){
			overwrite = false;
		}
		
		if(!serverProperties.containsKey("ws.port") && ServerPropertiesConnectionTypePage.currentSelected == "WebServices"){
			if(overwrite == true)
				createDefaultsPropertiesForWS(textEditorStrings);
		}
		setCommonProperties();
		String property = serverProperties.getProperty("ws.port");
		server.setPort(property);
		property = serverProperties.getProperty("protocol");
		server.setProtocol(property);
		property = serverProperties.getProperty("username");
		server.setUsername(property);
		property = serverProperties.getProperty("password");
		server.setPassword(property);
	}

	/**
	 * 
	 */
	private void setAgentProperties() {
		
		String textEditorStrings = getTextEditorStrings();
		try {
			InputStream stream = new ByteArrayInputStream(textEditorStrings.getBytes("UTF-8"));
			serverProperties.load(stream);
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
			Logger.getLogger(ServerMultiPageEditor.class.getName()).log(Level.SEVERE, "IO Exception.", e);
		}
		
		setCommonProperties();
		String property = serverProperties.getProperty("agent.port");
		server.setPort(property);
		property = serverProperties.getProperty("username");
		server.setUsername(property);
		property = serverProperties.getProperty("password");
		server.setPassword(property);
		property = serverProperties.getProperty("t24username");
		server.setEnvUsername(property);
		property = serverProperties.getProperty("t24password");
		server.setEnvPassword(property);
		property = serverProperties.getProperty("protocol");
		server.setProtocol(property);
		property = serverProperties.getProperty("ostype");
		server.setOstype(property);
		property = serverProperties.getProperty("ofsid");
		server.setOfsID(property);
		property = serverProperties.getProperty("tafchome");
		server.setTafchome(property);
	}

	private String getTextEditorStrings() {
		return textEditor.getDocumentProvider()
				.getDocument(textEditor.getEditorInput()).get();
	}
	
	private void setCommonProperties() {
		String property = serverProperties.getProperty("deployed.projects");
		if(null == property){
			server.getProjects().clear();
		}
		else if(property.contains(",")){
			String[] projects = property.split(",");
			server.getProjects().addAll(Arrays.asList(projects));
		}else{
			server.getProjects().add(property.trim());
		}
		
		property = serverProperties.getProperty("host");
		server.setHost(property);
		property = serverProperties.getProperty("branch");
		server.setBranch(property);
	}

	/**
	 * 
	 */
	private void createServerFormEditor() {
		try {
			formEditor = new ServerFormEditor(server);
			IEditorInput editorInput = getEditorInput();
			addPage(0, formEditor, editorInput);
			setPageText(0, "Form");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating form editor", null, e.getStatus());
		}
	}
	
	/**
	 * @return
	 */
	public ServerFormEditor getVersionFormEditor() {
		return formEditor;
	}

	/**
	 * @return
	 */
	private boolean querySwitchTab() {
		DialogPrompter dialogPrompter = new DialogPrompter();
		Display.getDefault().syncExec(dialogPrompter);
		boolean yesResult = dialogPrompter.isYesResult();
		return yesResult;
	}

	/**
	 *
	 */
	private class DialogPrompter implements Runnable {

		private boolean isYesResult;

		public boolean isYesResult() {
			return isYesResult;
		}

		public void run() {
			Shell shell = getActiveEditor().getSite().getShell();
			boolean formeditor = false;
			if (formEditor.isDirty()) {
				formeditor = true;
			}
			String editor = formeditor ? "Form Editor" : "Source Editor";
			String seditor = formeditor ? "Source Editor" : "Form Editor";
			String msg = editor + " has a modified screen model. \n"
					+ "Your changes are saved automatically when switching editors.\n" + "Would you like to switch to "
					+ seditor + "?";
			isYesResult = MessageDialog.open(MessageDialog.CONFIRM, shell, "Screen Model Changes", msg, SWT.NONE);
		}
	}

	@Override
	public EditingDomain getEditingDomain() {
		return formEditor.getEditingDomain();
	}

}
