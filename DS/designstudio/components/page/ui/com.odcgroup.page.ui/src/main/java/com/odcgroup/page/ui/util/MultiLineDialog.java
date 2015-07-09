package com.odcgroup.page.ui.util;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * A multi line dialog box who contain a textarea.
 * 
 * @author atr
 */
public class MultiLineDialog extends TitleAreaDialog {

	/** The title of this dialog */
	private String title = "Undefined Title";

	/** The message of this dialog */
	private String message = "Undefined message";

	/** The text area */
	private StyledText textArea = null;
	
	/** The font used to display the code */
	private Font textFont = null;

	/** Color for the syntax highlight. */
	private Color greenColor;

	/** The entered text */
	private String text = "";
        private final String MULTILINE_DIALOG_GREEN_COLOR = "MULTILINE_DIALOG_GREEN_COLOR" ;
	/**
	 * @return green color
	 */
	protected final Color getGreenColor() {
		if (greenColor == null) {
			greenColor = PageUIPlugin.getColor(MULTILINE_DIALOG_GREEN_COLOR);
		}
		return greenColor;
	}

	/**
	 * @see org.eclipse.jface.dialogs.TrayDialog#close()
	 */
	@Override
	public boolean close() {
		if (textFont != null) {
			textFont.dispose();
			textFont = null;
		}
		return super.close();
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
		shell.setSize(550, 600);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		setTitle(title);
		setMessage(message);
	}

	/**
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		Composite top = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		top.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		top.setLayoutData(gridData);

		textArea =  new StyledText(top, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		textArea.setLayoutData(new GridData(GridData.FILL_BOTH));
		textArea.addExtendedModifyListener(new ExtendedModifyListener() {
			public void modifyText(ExtendedModifyEvent event) {
				// Determine the ending offset
				String text = textArea.getText();

				// Create a collection to hold the StyleRanges
				java.util.List<StyleRange> ranges = new java.util.ArrayList<StyleRange>();
				int startIndex = 0;
				int indexOfEndingChar = 0;
				int endIndex = text.lastIndexOf('>');
				int tmpIndex = 0;
				boolean stop = false;
				while (!stop) {
					tmpIndex = +indexOfEndingChar;
					startIndex = +text.indexOf("<", tmpIndex);
					int length = 0;
					if (startIndex > -1) {

						indexOfEndingChar = text.indexOf(">", startIndex);
						if (indexOfEndingChar > -1) {
							length = indexOfEndingChar - startIndex;
							ranges.add(new StyleRange(startIndex, length + 1, getGreenColor(), null, SWT.BOLD));
						}

					}
					if (startIndex >= endIndex || startIndex <= -1) {
						stop = true;
					}
				}

				// If we have any ranges to set, set them
				if (!ranges.isEmpty()) {
					StyleRange[] styleRanges = new StyleRange[ranges.size()];
					for (int i = 0; i < ranges.size(); i++) {
						Object o = ranges.get(i);
						StyleRange styleRange = (StyleRange) o;
						styleRanges[i] = styleRange;

					}
					textArea.setStyleRanges(styleRanges);
				}
			}
		});
		textArea.setText(this.text);
	    
		textFont = new Font(getShell().getDisplay(), "Courier New", 10, SWT.NORMAL);
		textArea.setFont(textFont);
		
		return top;

	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		text = textArea.getText();
		super.okPressed();
	}

	/**
	 * @return the entered text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            The parent shell
	 * @param title
	 *            The title of this dialog
	 * @param message
	 *            The message to display in the dialog header
	 * @param property
	 *            The selected property
	 */
	public MultiLineDialog(Shell shell, String title, String message, Property property) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.FILL);
		setHelpAvailable(false);
		this.title = title;
		this.message = message;

		// super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		String value = property.getValue();
		this.text = value != null ? value : "";
		//init method
		initialize();
		

	}
	/**
	 * initialize the color.
	 */
	private void initialize() {
	    PageUIPlugin.setColorInRegistry(MULTILINE_DIALOG_GREEN_COLOR,new RGB(63,127,127));
	    
	}

	/**
	 * @param shell
	 * @param property
	 */
	public MultiLineDialog(Shell shell, Property property) {
		this(shell,
				"XSP Code Editor", //$NON-NLS-1$ 
				"Please enter your specific code below.", //$NON-NLS-1$
				property);
	}
	
}
