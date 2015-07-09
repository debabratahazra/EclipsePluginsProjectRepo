package com.odcgroup.page.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;



/**
 * Defines a dialog window for entering informations about
 * the image to generate from the CreateImageAction.
 *  
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class CreateImageDialog extends Dialog {

	/** The text of the GIF image in the Combo Box. */
	public static final String GIF_IMAGE_TEXT = "GIF";
	
	/** The text of the JPEG image in the Combo Box. */
	public static final String JPEG_IMAGE_TEXT = "JPEG";

	/** The text of the JPG image in the Combo Box. */
	public static final String JPG_IMAGE_TEXT = "JPG";

	/** The text of the PNG image in the Combo Box. */
	public static final String PNG_IMAGE_TEXT = "PNG";
	
	/** The text of the BMP image in the Combo Box. */
	public static final String BMP_IMAGE_TEXT = "BMP";
	
	/** The types of images which we can create. */
	private static final List<String> IMAGE_TYPES;
	static {
		IMAGE_TYPES = new ArrayList<String>();
		IMAGE_TYPES.add(GIF_IMAGE_TEXT);
		IMAGE_TYPES.add(BMP_IMAGE_TEXT);
		IMAGE_TYPES.add(JPEG_IMAGE_TEXT);
		IMAGE_TYPES.add(JPG_IMAGE_TEXT);
		IMAGE_TYPES.add(PNG_IMAGE_TEXT);		
	}
	
	/** The title of the dialog.*/
	private String title;
	
	/** The folder text. */
	private static final String FOLDER_TITLE = "Folder : ";
	
	/** The file name text. */
	private static final String FILENAME_TITLE = "File name : ";
	
	/** The image format text. */
	private static final String IMAGE_FORMAT_TITLE = "Image format : ";
	
	/** The browse text. */
	private static final String BROWSE = "Browse ... ";
	
	/** The overwrite text. */
	private static final String OVERWRITE = "Overwrite existing file without warning";
	
	/** The input folder destination. */
	private String inputFolderDestination;
	
	/** The file name of the image to save. */
	private String fileName;
	
	/** The image format of the image to save. */
	private String imageFormat;
	
	/** The input folder text fields */
	private Text inputFolder; 
	
	/** The input file name text field. */
	private Text inputFilename;
	
	/** The overwriting checkbox. */
	private Button checkBoxOverwrite; 
	
	/** The overwriting value. */
	private boolean overwriting;
	/**
     * Constructor
     * 
     * @param parent
     * 			The parent window
     * @param title
     * 			The title of the dialog
     */
	public CreateImageDialog(Shell parent, String title){
    	super(parent);
    	this.title = title;    	
    }

	/**
	 * Configures the shell.
	 * 
	 * @param shell
	 * 		The shell to configure
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
		shell.setMinimumSize(360, 120);
	}
	
	/**
	 * Creates and returns the contents of the upper part of this dialog.
	 * This implementation creates a labeled text field for the URI(s) and buttons for browsing the
	 * file system and workspace. These buttons are configured (selection listeners are added) by calling
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
	    Composite composite = (Composite)super.createDialogArea(parent);
	    composite.setLayout(null);
	    
	    Label labelFolder = new Label(composite,SWT.NONE);	    
	    labelFolder.setText(FOLDER_TITLE);
	    labelFolder.setBounds(5,10,45,20);
	    
	    inputFolder = new Text(composite, SWT.BORDER);
	    inputFolder.setBounds(65,10,200,20);
		this.inputFolder.setText(this.getInputFolderDestination());
		final Button button = new Button(composite, SWT.NONE);
		button.setText(BROWSE);
		button.setBounds(280,10,70,25);
		Listener listener = new Listener() {
		  public void handleEvent(Event event) {
			  	Shell shell = new Shell();
	        	DirectoryDialog  dialog = new DirectoryDialog (shell, SWT.SAVE);
	            String path = dialog.open();
	            shell.dispose();
		        inputFolder.setText(path);
		  }
		};
		button.addListener(SWT.Selection, listener);

	    Label labelFilename = new Label(composite,SWT.NONE);	    
	    labelFilename.setText(FILENAME_TITLE);
	    labelFilename.setBounds(5,40,45,20);
	    
	    inputFilename = new Text(composite, SWT.BORDER);
	    inputFilename.setBounds(65,40,280,20);
	    inputFilename.setText(this.getFileName());
	    Label labelImageFormat = new Label(composite,SWT.NONE);	    
	    labelImageFormat.setText(IMAGE_FORMAT_TITLE);
	    labelImageFormat.setBounds(5,70,80,20);

	    final Combo combo = new Combo(composite, SWT.NONE);
	    for (String s : IMAGE_TYPES) {
	    	combo.add(s);
	    }
	    
	    combo.setBounds(85,70,260,20);
	    int selection = IMAGE_TYPES.indexOf(getImageFormat());
	    combo.select(selection);

		Listener comboListener = new Listener() {
			  public void handleEvent(Event event) {
				  	
		        	String fileNameWithoutExtension = getFilenameWithoutExtension();			        	
		    		setImageFormat(combo.getText());
		    		setFileName(fileNameWithoutExtension + "." + combo.getText().toLowerCase());			        	
			  }			  
			};
		combo.addListener(SWT.Selection,comboListener);

	    checkBoxOverwrite = new Button(composite, SWT.CHECK);
	    checkBoxOverwrite.setBounds(5, 100, 20, 20);
	    
	    Label labelOverwrite = new Label(composite, SWT.NONE);
	    labelOverwrite.setText(OVERWRITE);
	    labelOverwrite.setBounds(25, 100, 190, 20);
	    return composite;
	 }


	  /**
	   * Gets the input folder destination.
	   * 
	   * @return String returns the folder destination
	   */
	public String getInputFolderDestination() {
	    return inputFolderDestination;
	}
	  /**
	   * Sets the input folder destination
	   * 
	   * @param inputFolderDestination
	   * 			The input folder destination
	   */
	public void setInputFolderDestination(String inputFolderDestination) {					
		this.inputFolderDestination = inputFolderDestination.replaceAll("/","\\\\");
	}

	/**
	 * Gets the file name of the image to save.
	 * 
	 * @return String returns the file name of the image to save
	 */
	public String getFileName() {
		if (!StringUtils.isEmpty(inputFilename.getText())) {
			return inputFilename.getText();
		}else {
			return this.fileName;
		}
	}
	/**
	 * Sets the file name of the image to save.
	 * @param fileName
	 * 			The file name of the image to save
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
		if (inputFilename != null) {
			this.inputFilename.setText(fileName);
		}
	}

	/**
	 * Gets the image format of the image to save.
	 * @return String returns the image format of the image to save
	 */
	public String getImageFormat() {
		return imageFormat;
	}
	
	/**
	 * Sets the image format of the image to save
	 * @param imageFormat
	 * 			The image format of the image to save
	 */
	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}
	
	/**
	 * Gets the overwriting value of the image to save
	 * 
	 * @return boolean returns true if we overwrite an existing image without message
	 */
	public boolean getOverwriting() {
		return this.overwriting;
	}
	
	/**
	 * Sets the overwriting value
	 * 
	 * @param overwriting
	 * 				The overwriting value
	 */
	public void setOverwriting(boolean overwriting) {
		this.overwriting = overwriting;
	}
	
	/**
	 * Notifies that the ok button of this dialog has been pressed.
	 * <p>
	 * The <code>Dialog</code> implementation of this framework method sets
	 * this dialog's return code to <code>Window.OK</code> and closes the
	 * dialog. Subclasses may override.
	 * </p>
	 */
	protected void okPressed() {
		setReturnCode(OK);
		setInputFolderDestination(this.inputFolder.getText());
		setFileName(this.inputFilename.getText());
		
		setOverwriting(this.checkBoxOverwrite.getSelection());
		close();

	}
	

	/**
	 * Notifies that the cancel button of this dialog has been pressed.
	 * <p>
	 * The <code>Dialog</code> implementation of this framework method sets
	 * this dialog's return code to <code>Window.CANCEL</code> and closes the
	 * dialog. Subclasses may override if desired.
	 * </p>
	 */
	protected void cancelPressed() {
		setReturnCode(CANCEL);
		close();
	}
	/**
	 * Gets the filename without the file extension.
	 * 
	 * @return String returns the file name without the file extension
	 */
	private String getFilenameWithoutExtension ()  {
		return getFileName().substring(0, getFileName().lastIndexOf('.'));
	}
	
	/**
	 * Gets the setted filename.
	 * 
	 * @return String returns the setted filename
	 */
	public String getSettedFilename() {
		return this.fileName;
	}
}
