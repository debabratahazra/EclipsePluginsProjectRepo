package com.odcgroup.page.ui.action;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.dialog.CreateImageDialog;
import com.odcgroup.page.ui.util.SaveImageUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.ui.ModelEditorInput;

/**
 * This action enables the user to generate an image file containing the page.
 * 
 * @author Gary Hayes
 * @author Alexandre Jaquet
 */
public class CreateImageAction extends Action {

	/** The Id of the CreateImageAction. */
	public static final String ID = "com.odcgroup.page.ui.action.CreateImageAction";

	/** The IWorkbenchPart. */
	private IWorkbenchPart workbenchPart;
	
	/** The icon image of the action. */
	
	private static ImageDescriptor image = PageUIPlugin.getImageDescriptor("icons/obj16/saveAsImage.png");

	/**
	 * Constructs a new CreateImageAction.
	 * 
	 * @param workbenchPart
	 *            The IWorkbenchPart
	 */
	public CreateImageAction(IWorkbenchPart workbenchPart) {
		super("Save as image File ...", image);
		Assert.isNotNull(workbenchPart);		
		this.workbenchPart = workbenchPart;
		setId(ID);
		setToolTipText("Save as Image File");
	}

	/**
	 * Runs the action. This creates the image file.
	 */
	public void run() {
		// Retrieve the active page for getting the full path
		IWorkbenchPage page = workbenchPart.getSite().getPage();
		IEditorPart editor = page.getActiveEditor();
		IEditorInput input = editor.getEditorInput();
		
		if (input instanceof ModelEditorInput) {
			ModelEditorInput modelInput = (ModelEditorInput) input;
			try {
				IStorage storage = modelInput.getStorage();
				if (storage instanceof IOfsModelResource) {
					IOfsModelResource ofsResource = (IOfsModelResource) storage;
					IPath prjLocation = ofsResource.getOfsProject().getProject().getLocation();
					StringBuilder builder = new StringBuilder(prjLocation.toPortableString());
					builder.append(File.separator);
					builder.append(ofsResource.getURI().fileExtension());
					builder.append(ofsResource.getFullPath().toPortableString());
					IPath fullPath = new Path(builder.toString());
				
					String folder = fullPath.removeLastSegments(1).toPortableString();
					String lastSegment = fullPath.lastSegment();
					String filename = lastSegment.substring(0, lastSegment.lastIndexOf('.')+1)+"gif";
					
					CreateImageDialog dialog = new CreateImageDialog(Display.getCurrent().getActiveShell(), "Save As Image File");		
					dialog.setInputFolderDestination(folder);
					dialog.setImageFormat("GIF");
					dialog.setFileName(filename);		
					if (Dialog.OK == dialog.open()) {		
					
						// Remove the file extension
						String finalFilename = dialog.getSettedFilename();
						if (finalFilename.lastIndexOf('.') > 0) {
							finalFilename = finalFilename.substring(0, finalFilename.lastIndexOf('.'));
						}
						
						IPath path = new Path(dialog.getInputFolderDestination());
						int format = getImageFormatCode(dialog.getImageFormat());
						
						if (fileExist(path + File.separator + finalFilename) && !dialog.getOverwriting()) {
							boolean confirm = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), 
									"Confirm","The image file already exist, overwrite ?");
							if (confirm) {
								SaveImageUtils.createImagesAndConditions(ofsResource, finalFilename, path, format);
							}
						}else {
							SaveImageUtils.createImagesAndConditions(ofsResource, finalFilename, path, format);
						}
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		if (input instanceof FileEditorInput) {
			FileEditorInput fileInput = (FileEditorInput) input;
			IPath folder = fileInput.getPath().removeLastSegments(1);
			// Retrieve the file name without file extension
			String filename = fileInput.getName().substring(0, fileInput.getName().lastIndexOf('.')+1)+"gif";
			CreateImageDialog dialog = new CreateImageDialog(Display.getCurrent().getActiveShell(), "Save As Image File");		
			dialog.setInputFolderDestination(folder.toString());
			dialog.setImageFormat("GIF");
			dialog.setFileName(filename);		
			if (Dialog.OK == dialog.open()) {		
			
				// Remove the file extension
				String finalFilename = dialog.getSettedFilename();
				if (finalFilename.lastIndexOf('.') > 0) {
					finalFilename = finalFilename.substring(0, finalFilename.lastIndexOf('.'));
				}
				
				IPath path = new Path(dialog.getInputFolderDestination());
				int format = getImageFormatCode(dialog.getImageFormat());
				
				if (fileExist(path + File.separator + finalFilename) && !dialog.getOverwriting()) {
					boolean confirm = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), 
							"Confirm","The image file already exist, overwrite ?");
					if (confirm) {
						saveImage(fileInput.getFile(), finalFilename, path, format);
					}
				}else {
					saveImage(fileInput.getFile(), finalFilename, path, format);
				}
			}
		}
	}

	/**
	 * Gets the image file format code.
	 * 
	 * @param imageFormat
	 * 			The given image format
	 * @return int returns the int value of the format
	 */
	private int getImageFormatCode(String imageFormat) {
		int format = 0;
		if (imageFormat.equalsIgnoreCase("jpeg")) {
			format = SWT.IMAGE_JPEG;
		}else if (imageFormat.equalsIgnoreCase("gif")) {
			format = SWT.IMAGE_GIF;
		}else if (imageFormat.equalsIgnoreCase("png")) {
			format = SWT.IMAGE_PNG;
		}else if (imageFormat.equalsIgnoreCase("bmp")) {
			format = SWT.IMAGE_BMP;
		}else if (imageFormat.equalsIgnoreCase("jpg")) {
			format = SWT.IMAGE_JPEG;
		}
		return format;
	}
	
	/**
	 * Does our image file already exists ?
	 * 
	 * @param path
	 * 			The path to the image
	 * @return boolean returns true if the image file already exist
	 */
	private boolean fileExist(String path) {		
		return new File(path).exists();
	}
	
    /**
     * Create an image for the given file.
     * 
     * @param file The input file
     * @param filename The filename (prefix) used for the generated images
     * @param path The path
     * @param imageFormat The format
     */
	private void saveImage(IFile file, String filename, IPath path, int imageFormat) {
	    SaveImageUtils.createImages(file, filename, path, imageFormat);
	}
}