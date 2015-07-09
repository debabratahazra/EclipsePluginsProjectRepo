package com.odcgroup.workbench.generation.cartridge.ng;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.AbstractFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.util.StringInputStream;

/**
 * Originally a copy of
 * org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2 from Xtext v2.3.1,
 * minus some Trace & SMAP stuff we don't need (or don't have in v2.5+ anymore),
 * and with slf4j instead log4j. We have a copy here, because we do not want
 * workbench-generation to depend on xtext-builder, because that depends on UI.
 * 
 * @author Michael Vorburger
 */
public class SimplerEclipseResourceFileSystemAccess2 extends AbstractFileSystemAccess {
//	private final static Logger log = LoggerFactory.getLogger(SimplerEclipseResourceFileSystemAccess2.class);
	
	/**
	 * @noimplement This interface is not intended to be implemented by clients.
	 */
	public interface IFileCallback {
		public void afterFileUpdate(IFile file);
		public void afterFileCreation(IFile file);
		/**
		 * @return whether a deletion is vetoed.
		 */
		public boolean beforeFileDeletion(IFile file);
	}
	
	private IProject project;
	
	private IProgressMonitor monitor;
	
	private IFileCallback callBack;

	private boolean forceGeneration;
	/**
	 * @since 2.3
	 */
	protected IFileCallback getCallBack() {
		return callBack;
	}
	
	public void setProject(IProject project) {
		this.project = project;
	}
	
	public IProject getProject() {
		return this.project;
	}
	
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void setPostProcessor(IFileCallback callBack) {
		this.callBack = callBack;
	}
	
	protected IProgressMonitor getMonitor() {
		return monitor;
	}

	public void generateFile(String fileName, String outputName, CharSequence contents) {
		if (monitor.isCanceled())
			throw new OperationCanceledException();
		OutputConfiguration outputConfig = getOutputConfig(outputName);
		
		// check output folder exists
		IFolder folder = getFolder(outputConfig);
		if (!folder.exists()) {
			if (outputConfig.isCreateOutputDirectory()) {
				try {
					createFolder(folder);
				} catch (CoreException e) {
					throw new RuntimeException(e);
				}
			} else {
				return;
			}
		}
		
		IFile file = getFile(fileName, outputName);
//		IFile traceFile = getTraceFile(file);
//		IFile smapFile = getSmapFile(file);
		CharSequence postProcessedContent = postProcess(fileName, outputName, contents);
		String contentsAsString = postProcessedContent.toString(); 
		if (file.exists()) {
			if (outputConfig.isOverrideExistingResources()) {
				try {
					StringInputStream newContent = getInputStream(contentsAsString, getEncoding(file));
					if (hasContentsChanged(file, newContent) || isForceGeneration()) {
						// reset to offset zero allows to reuse internal byte[]
						// no need to convert the string twice
						newContent.reset();
						file.setContents(newContent, true, true, monitor);
						if (file.isDerived() != outputConfig.isSetDerivedProperty()) {
							setDerived(file, outputConfig.isSetDerivedProperty());
						}
//					} else {
//						if (smapFile != null)
//							file.touch(monitor);
					}
//					if (smapFile != null)
//						updateSmapInformation(smapFile, postProcessedContent, file);
//					updateTraceInformation(traceFile, postProcessedContent, outputConfig.isSetDerivedProperty());
				} catch (CoreException e) {
					throw new RuntimeException(e);
//				} catch (IOException e) {
//					throw new RuntimeException(e);
				}
				if (callBack != null)
					callBack.afterFileUpdate(file);
			}
		} else {
			try {
				ensureParentExists(file);
				file.create(getInputStream(contentsAsString, getEncoding(file)), true, monitor);
				if (outputConfig.isSetDerivedProperty()) {
					setDerived(file, true);
				}
//				updateTraceInformation(traceFile, postProcessedContent, outputConfig.isSetDerivedProperty());
			} catch (CoreException e) {
				throw new RuntimeException(e);
//			} catch (IOException e) {
//				throw new RuntimeException(e);
			}
			if (callBack != null)
				callBack.afterFileCreation(file);
		}
	}

	/**
	 * @since 2.3
	 */
	protected String getEncoding(IFile file) throws CoreException {
		return file.getCharset(true);
	}
	
	/**
	 * @since 2.3
	 */
	@SuppressWarnings("deprecation")
	protected void setDerived(IFile file, boolean derived) throws CoreException {
		file.setDerived(derived);
	}

	protected void createFolder(IFolder folder) throws CoreException {
		ensureExists(folder);
	}

	protected void ensureParentExists(IFile file) throws CoreException {
		if (!file.exists()) {
			ensureExists(file.getParent());
		}
	}
	
	protected void ensureExists(IContainer container) throws CoreException {
		if (container.exists())
			return;
		if (container instanceof IFolder) {
			ensureExists(container.getParent());
			((IFolder)container).create(true, true, monitor);
		}
	}

	protected StringInputStream getInputStream(String contentsAsString, String encoding) {
		try {
			return new StringInputStream(contentsAsString, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	protected IFolder getFolder(OutputConfiguration outputConfig) {
		return project.getFolder(new Path(outputConfig.getOutputDirectory()));
	}

	protected boolean hasContentsChanged(IFile file, StringInputStream newContent) {
		boolean contentChanged = false;
		BufferedInputStream oldContent = null;
		try {
			oldContent = new BufferedInputStream(file.getContents());
			int newByte = newContent.read();
			int oldByte = oldContent.read();
			while(newByte != -1 && oldByte != -1 && newByte == oldByte) {
				newByte = newContent.read();
				oldByte = oldContent.read();
			}
			contentChanged = newByte != oldByte;
		} catch (CoreException e) {
			contentChanged = true;
		} catch (IOException e) {
			contentChanged = true;
		} finally {
			if (oldContent != null) {
				try {
					oldContent.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return contentChanged;
	}
	
	private boolean isGenerationMandatory(){

		return false;
	}

	@Override
	public void deleteFile(String fileName, String outputName) {
		try {
			IFile file = getFile(fileName, outputName);
			deleteFile(file, monitor);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @since 2.3
	 */
	public void deleteFile(IFile file, IProgressMonitor monitor) throws CoreException {
		IFileCallback callBack = getCallBack();
		if ((callBack == null || callBack.beforeFileDeletion(file)) && file.exists()) {
//			IFile traceFile = getTraceFile(file);
			file.delete(IResource.KEEP_HISTORY, monitor);
//			if (traceFile.exists()) {
//				traceFile.delete(IResource.KEEP_HISTORY, monitor);
//			}
		}
	}
	
	protected IFile getFile(String fileName, String outputName) {
		OutputConfiguration configuration = getOutputConfig(outputName);
		IFolder folder = getFolder(configuration);
		return folder.getFile(new Path(fileName));
	}
	
	/**
	 * We cannot use the storage to URI mapper here, as it only works for Xtext based languages 
	 * @since 2.3
	 */
	public URI getURI(String fileName, String outputConfiguration) {
		IFile file = getFile(fileName, outputConfiguration);
		return URI.createPlatformResourceURI(file.getFullPath().toString(), true);
	}

	/**
	 * @return the forceGeneration
	 */
	public boolean isForceGeneration() {
		return forceGeneration;
	}

	/**
	 * @param forceGeneration the forceGeneration to set
	 */
	public void setForceGeneration(boolean forceGeneration) {
		this.forceGeneration = forceGeneration;
	}

}
