package com.temenos.t24.tools.eclipse.basic.rtc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;

import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.scm.client.IWorkspaceManager;
import com.ibm.team.scm.client.SCMPlatform;
import com.ibm.team.scm.client.content.IVersionedContentManager;
import com.odcgroup.t24.enquiry.xml.generator.EnquiryXMLGenerator;
import com.odcgroup.t24.version.xml.generator.VersionXMLGenerator;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.resources.OfsProject;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItem;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItemCollection;
import com.temenos.t24.tools.eclipse.basic.remote.data.ItemType;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.wizards.rtc.install.T24ChangeSet;

public class RepositoryFileContentHandler {

	private ITeamRepository repository;
	private IVersionedContentManager versionedContentManager;
	private IProgressMonitor monitor;
	private boolean isXmlReq = false;
	private String tempPath;

	public RepositoryFileContentHandler(ITeamRepository repository) {
		this.repository = repository;
		initContentManager();
		this.monitor = new SysoutProgressMonitor();
	}

	private void initContentManager() {
		IWorkspaceManager workSpaceManager = SCMPlatform.getWorkspaceManager(repository);
		versionedContentManager = workSpaceManager.getSCMContentManager();
	}

	public InstallableItemCollection retrieveContent(List<T24ChangeSet> t24ChangeSets) throws DownloadSourceException {
		InstallableItemCollection installableItemCollection = new InstallableItemCollection();
		InstallableItem item = null;
		for (T24ChangeSet changeSet : t24ChangeSets) {
			String localPath = getLocalPath(changeSet.getReference(), changeSet.getWorkItemReference());
			File directory = new File(localPath);
			try {
				directory.mkdirs();
			} catch (SecurityException e) {
				throw new DownloadSourceException("Unable to create directory " + localPath + ". " + e.getMessage());
			}
			List<T24SourceItem> sourceItems = changeSet.getSourceItems();
			for (T24SourceItem t24SourceItem : sourceItems) {
				String fileName = t24SourceItem.getName();
				if (fileName.endsWith(".version") || fileName.endsWith(".enquiry")) {
					IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
					for (IProject iProject : projects) {
						if (iProject.getName().endsWith("-models")) {
							IFolder folder = null;
							if(fileName.endsWith(".version")){
								folder = iProject.getFolder("version");
							}else if(fileName.endsWith(".enquiry")){
								folder = iProject.getFolder("enquiry");
							}
							try {
								OfsCore.createFolder(folder.getFolder("temp"));
								if (!folder.exists()) {
									// some error msg
									//folder is missing msg
								}
							} catch (CoreException e) {
								e.printStackTrace();
							}
							isXmlReq = true;
							IFolder tempFolder = iProject.getFolder("\\"+ folder.getName() + "\\temp");
							try {
								ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
							} catch (CoreException e) {
								e.printStackTrace();
							}
							if (tempFolder.exists()) {
								tempPath = tempFolder.getFullPath().append("\\" + fileName).toString();
							}
							break;
						}

					}
				}
				if (!isXmlReq && (fileName.endsWith(".version") || fileName.endsWith(".enquiry"))) {
					MessageDialog mDialog = new MessageDialog(null, "File Location Error", null, "The file " + fileName
							+ " has to be under a *-models project for Install Change Set to be done!",
							MessageDialog.ERROR, new String[] { "OK" }, 0);
					mDialog.open();
					return installableItemCollection;
				}
				File file = null;
				if (!isXmlReq)
					file = new File(localPath + "/" + fileName);
				else {
					file = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation() + tempPath);
				}
				
				boolean isExists = false;
				try {
					isExists = file.exists();
					if (!isExists) {
						isExists = file.createNewFile();
						try {
							ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
						} catch (CoreException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					throw new DownloadSourceException("Unable to create file " + file.getAbsolutePath() + ". "
							+ e.getMessage());
				}
				if (!isExists) {
					throw new DownloadSourceException("File not found. " + file.getAbsolutePath());
				}
				try {
					FileOutputStream fos = new FileOutputStream(file);
					versionedContentManager.retrieveContent(t24SourceItem.getFileItem(),
							t24SourceItem.getFileContent(), fos, monitor);
				} catch (FileNotFoundException e) {
					throw new DownloadSourceException("File not created. " + e.getMessage());
				} catch (TeamRepositoryException e) {
					throw new DownloadSourceException("Failed to retieve source. " + e.getMessage());
				}
				item = new InstallableItem(fileName, file.getAbsolutePath());
				
				if (ItemType.SOURCE.equals(item.getType())) {
					installableItemCollection.addSourceItem(item);
				} else if (ItemType.DATA.equals(item.getType())) {
					installableItemCollection.addDataItem(item);
				} else if (ItemType.XML.equals(item.getType()) && tempPath != null) {
					try {
						ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
					OfsProject ofsProject = null;
					IFile actualFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(tempPath));
					ofsProject = (OfsProject) OfsCore.getOfsProjectManager().getOfsProject(actualFile.getProject());
					URI uri = ModelURIConverter.createModelURI((IResource) actualFile);
					Resource eResource = null;
					try {
						eResource = ofsProject.getOfsModelResource(uri).getEMFModel().get(0).eResource();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidMetamodelVersionException e) {
						e.printStackTrace();
					} catch (ModelNotFoundException e) {
						e.printStackTrace();
					}
					if(fileName.endsWith(".version")){
						if (eResource != null) {
							VersionXMLGenerator xmlGenerator = new VersionXMLGenerator();
							try {
								xmlGenerator.generateXML(eResource, null);
							} catch (Exception e) {
								e.printStackTrace(); // TODO Fix shitty exception handling.. I've copy/pasted from above - this class is a mess! :(
							}
						}
					} else if (fileName.endsWith(".enquiry")) {
						if (eResource != null) {
							EnquiryXMLGenerator xmlGenerator = new EnquiryXMLGenerator();
							try {
								xmlGenerator.generateXML(eResource, null);
							} catch (Exception e) {
								e.printStackTrace(); // TODO Fix shitty exception handling.. I've copy/pasted from above - this class is a mess! :(
							}
						}
					} else {
						MessageDialog mDialog = new MessageDialog(
								null,
								"File Location Error",
								null,
								"The file "
										+ fileName
										+ " has to be under a *-models project for Install Change Set to be done!",
								MessageDialog.ERROR, new String[] { "OK" }, 0);
						mDialog.open();
					}

					File xmlFile = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation() + "/"
							+ actualFile.getProject().getName().replace("-models", "-models-gen") + "/src/xml-t24i/"
							+ file.getName() + ".xml");
					if(xmlFile.exists()){
						item = new InstallableItem(xmlFile.getName(), xmlFile.getAbsolutePath().replace("temp\\", ""));
						installableItemCollection.addXmlItem(item);
					}else{
						MessageDialog mDialog = new MessageDialog(null, "File does not exists", null, "The file " + file.getName() + ".xml"
								+ " does not exists in the proper location for Install Change Set to be done!",
								MessageDialog.ERROR, new String[] { "OK" }, 0);
						mDialog.open();
					}
					
				} else {
					installableItemCollection.addUndefinedItem(item);
				}
			}
		}
		return installableItemCollection;
	}

	private String getLocalPath(String changeSetRef, int workItemRef) {
		String localPath = null;
		localPath = EclipseUtil.getTemporaryProject().toOSString() + "/T24ChangeSets/" + "WI-" + workItemRef + "-"
				+ changeSetRef;
		return localPath;
	}
}
