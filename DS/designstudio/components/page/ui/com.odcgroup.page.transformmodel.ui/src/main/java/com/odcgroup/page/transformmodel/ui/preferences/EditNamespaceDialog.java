package com.odcgroup.page.transformmodel.ui.preferences;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.transformmodel.Namespace;

/**
 * TODO: Document me!
 * 
 * @author pkk
 * 
 */
public class EditNamespaceDialog extends TitleAreaDialog {
	
	/** The namespace */
	private Namespace namespace;
	
	/** the prefix of the namespace */
	private Text prefix;

	/** the uri of the namespace */
	private Text uri;
	
	/**
	 * @return the namespace prefix
	 */
	protected String getPrefix() {
		String value = namespace.getPrefix();
		return value == null ? "" : value;
	}
	
	/**
	 * @return the namespace uri
	 */
	protected String getUri() {
		String value = namespace.getUri();
		return value == null ? "" : value;
	}

	/**
	 * initializes the controls
	 */
	protected void initialize() {
		prefix.setText(getPrefix());
		uri.setText(getUri());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		setTitle("User Defined Namespace");
		setMessage("Add a new namespace");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("User Defined Namespace");
	}
	

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
//		Composite root = (Composite) super.createDialogArea(parent);
//		GridLayout gridLayout = new GridLayout(2, false);
//		gridLayout.marginHeight = 5;
//		gridLayout.marginWidth = 5;
//		root.setLayout(gridLayout);

		Composite body = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout gridLayout = new GridLayout(2, false);
		body.setLayout(gridLayout);
		body.setLayoutData(new GridData(GridData.FILL_BOTH));

		new Label(body, SWT.NONE).setText("Prefix:");		
		prefix = new Text(body, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		prefix.setLayoutData(gd);

		new Label(body, SWT.NONE).setText("Uri:");		
		uri = new Text(body, SWT.BORDER);	
		gd = new GridData(GridData.FILL_HORIZONTAL);
		uri.setLayoutData(gd);
		
		initialize();

		return body;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		this.namespace.setPrefix(prefix.getText());
		this.namespace.setUri(uri.getText());
		super.okPressed();
	}	
	
	/**
	 * @param parentShell
	 * @param namespace
	 */
	public EditNamespaceDialog(Shell parentShell, Namespace namespace) {
		super(parentShell);
		this.namespace = namespace;
	}

}
