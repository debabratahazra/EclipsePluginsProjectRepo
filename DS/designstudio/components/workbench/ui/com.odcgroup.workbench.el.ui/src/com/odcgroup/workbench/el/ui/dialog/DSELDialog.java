package com.odcgroup.workbench.el.ui.dialog;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.codetemplates.ui.preferences.TemplateResourceProvider;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Injector;
import com.odcgroup.workbench.el.engine.DSELContext;
import com.odcgroup.workbench.el.ui.embeddededitor.EmbeddedEditorFactory;
import com.odcgroup.workbench.el.ui.internal.DSELActivator;

/**
 * A multi line dialog box which contains a textarea for DSEL expressions.
 * 
 * @author Kai Kreuzer
 * @author Michael Vorburger (really rather Itemis consultant Anton) - now based on Xtext core EmbeddedEditorModelAccess (which didn't exist when we originally wrote this), instead of loads of copy/pasted code
 */
@SuppressWarnings("restriction")
public class DSELDialog extends TitleAreaDialog {

	/** The title of this dialog */
	private String title = "Undefined Title";

	/** The message of this dialog */
	private String message = "Undefined message";

	/** The entered text */
	private String text = "";

	private final DSELContext context;

	private final IProject iProject;
	
	private EmbeddedEditorModelAccess partialEditor;

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
		shell.setSize(640, 450);
	}

	@Override
	public void create() {
		super.create();
		setTitle(title);
		setMessage(message);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite top = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		top.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		top.setLayoutData(gridData);
				
		Text label = new Text(top, SWT.WRAP | SWT.READ_ONLY);
		String str =  "An expression has no argument and returns "
				+ "a Boolean value: \"true\" or \"false\".";
		label.setText(str); // two lines//$NON-NLS-1$
		label.setFont(JFaceResources.getDialogFont());
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		
		Text label2 = new Text(top, SWT.WRAP | SWT.READ_ONLY);
		label2.setText("Expression example: \nDatasetDomainName.Attribute == EnumerationDomainName::EnumerationName::EnumeratedValue"); // two lines//$NON-NLS-1$
		label2.setFont(JFaceResources.getDialogFont());
		label2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label2.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		
		Text label3 = new Text(top, SWT.WRAP | SWT.READ_ONLY);
		label3.setText("Use CTRL+SPACE for content assist. For more details, please refer to documentation."); // two lines//$NON-NLS-1$
		label3.setFont(JFaceResources.getDialogFont());
		label3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label3.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		
		final Injector injector = DSELActivator.getInstance().getInjector(DSELActivator.COM_ODCGROUP_WORKBENCH_EL_DSEL);
		EmbeddedEditorFactory editorFactory = injector.getInstance(EmbeddedEditorFactory.class);
		final IResourceSetProvider resourceSetProvider = injector.getInstance(IResourceSetProvider.class);
		final EmbeddedEditor embeddedEditor = editorFactory.newEditor(new IEditedResourceProvider() {
			
			@Override
			public XtextResource createResource() {
				ResourceSet resourceSet = resourceSetProvider.get(iProject);
				XtextResource result = (XtextResource) resourceSet.createResource(
						URI.createURI(TemplateResourceProvider.SYNTHETIC_SCHEME + ":/embedded.dsex")
				);
				Adapter adapterContext = context.getExpressionContext(resourceSet); 
				result.eAdapters().add(adapterContext);
				return result;
			};
			
		}).showErrorAndWarningAnnotations().withParent(top);
		
		partialEditor = embeddedEditor.createPartialEditor();
		
		partialEditor.updateModel("", this.text, "");
		return top;
	}
	
	@Override
	protected void okPressed() {
		text = partialEditor.getSerializedModel();
		super.okPressed();
	}

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
	 */
	public DSELDialog(Shell shell, String title, String message, String expression, DSELContext context, IProject iProject) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.FILL);
		setHelpAvailable(false);
		this.title = title;
		this.message = message;
		this.context = context;
		this.iProject = iProject;
		String value = expression;
		this.text = value != null ? value : "";
	}
	
	public DSELDialog(Shell shell, String expression, DSELContext context, IProject iProject) {
		this(shell,
				"Script Editor", //$NON-NLS-1$ 
				"Please enter your expression below. ", //$NON-NLS-1$
				expression, context, iProject);
	}

}
