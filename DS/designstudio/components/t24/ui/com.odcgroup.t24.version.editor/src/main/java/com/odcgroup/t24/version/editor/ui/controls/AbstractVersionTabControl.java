package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.naming.VersionNameProvider;
import com.odcgroup.t24.version.utils.VersionNameUtils;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * @author phanikumark
 *
 */
public abstract class AbstractVersionTabControl extends ScrolledComposite implements IVersionDataBindingControl {
	
	private Resource resource;
	protected DataBindingContext context;
	protected VersionDesignerEditor editor = null;

	/**
	 * 
	 */
	protected final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Diagnostic diagnostic;

	/**
	 * @param parent
	 * @param style
	 */
	public AbstractVersionTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context) {
		super(parent, SWT.NONE);
		this.resource = editor.getEditedResource();
		this.editor = editor;
		this.context = context;
		Composite body = createBody();
		createTabControls(body);
		setContent(body);
		initDataBindings(context);

	}
	
	protected AbstractVersionTabControl(Composite parent){
	    super(parent, SWT.BORDER | SWT.V_SCROLL);
	}
	/** create the body for the Tab
	 * @return body Composite
	 */
	protected Composite createBody() {
	    setLayout(new GridLayout());
	    setExpandHorizontal(true);
	    setExpandVertical(true);
	    toolkit.adapt(this);
	    toolkit.paintBordersFor(this);		

	    final Composite body = new Composite(this, SWT.NONE);
	    body.setLayout(new GridLayout(1, false));
	    body.setLayoutData(new GridData(GridData.FILL_BOTH));
	    toolkit.adapt(body);

//	    addListener(SWT.Resize, new Listener() {
//			int width = -1;
//			public void handleEvent(Event e) {
//			    int newWidth = body.getSize().x;
//			    if (newWidth != width) {
//			    	setMinHeight(body.computeSize(newWidth, SWT.DEFAULT).y);
//			    	width = newWidth;
//			    }
//			}
//	    });
	    addDisposeListener(new DisposeListener() {
		public void widgetDisposed(DisposeEvent e) {
		    toolkit.dispose();
		}
	    });
	    return body;
	}
	
	/**
	 * @return
	 */
	public Version getEditedVersion() {
		return (Version) resource.getContents().get(0);
	}	
	
	/**
	 * @return
	 */
	public DataBindingContext getBindingContext() {
		return context;
	}

	/**
	 * @return
	 */
	public VersionDesignerEditor getEditor() {
		return editor;
	}

	/**
	 * @param body
	 */
	protected abstract void createTabControls(Composite body);

	/* (non-Javadoc)
	 * @see com.odcgroup.t24.version.editor.ui.controls.IVersionDataBindingControl#initDataBindings(org.eclipse.core.databinding.DataBindingContext)
	 */
	public final void initDataBindings(DataBindingContext context) {
		bindData();
	}	
	

	
	/**
	 * @param version
	 * @return
	 */
	public static String getDisplayName2(Version version) {
		VersionNameProvider vnp = new VersionNameProvider();
		QualifiedName fq = vnp.getFullyQualifiedName(version);
		String versionref = "";
		if(fq != null){
			versionref = fq.getSegment(1)+",";
			if(fq.getSegmentCount() > 2){
				versionref += fq.getLastSegment();
			}
		}
		return versionref;
	}
	
	/**
	 * @param version
	 * @return
	 */
	protected String getDisplayName(Version version) {
		String displayName = VersionUtils.getDisplayName(version);
		if(displayName.isEmpty() || displayName == null){
			displayName = "Resource does not exists or is missing but is referred here. DSL shows this resource's name.";
		}
		return displayName;
	}
	
	/**
	 * @param version
	 * @return
	 */
	protected String getDisplayNameForAssocVersion(Version version, int i) {
		String displayName = VersionNameUtils.getVersionForAssociation(version, i).toString();
		if(displayName.isEmpty() || displayName == null){
			Diagnostic analyzeResourceProblems = getEditor().analyzeResourceProblems(getEditedVersion().eResource(), null);
			if(version.eIsProxy()){
				diagnostic = analyzeResourceProblems.getChildren().get(i);
				String[] splitStr = diagnostic.getMessage().split("'");
				displayName = splitStr[2];
				}
			else
				displayName = "Resource does not exists or is missing but is referred here. DSL shows this resource's name.";
		}
		return displayName;
	}
	
	/**
	 * @return
	 */
	protected EditingDomain getEditingDomain() {
		return getEditor().getEditingDomain();
	}
	
	/**
	 * @param feature
	 * @param ref
	 * @return
	 */
	protected Command getAddCommand(EStructuralFeature feature, Object ref) {
		return AddCommand.create(getEditor().getEditingDomain(), getEditedVersion(), feature, ref);
	}
	
	/**
	 * @param feature
	 * @param ref
	 * @return
	 */
	protected Command getSetCommand(EStructuralFeature feature, Object ref) {
		return SetCommand.create(getEditor().getEditingDomain(), getEditedVersion(), feature, ref);
	}
	
	
	/**
	 * @param cmd
	 */
	protected void executeCommand(Command cmd) {
		if (cmd.canExecute()) {
			getEditingDomain().getCommandStack().execute(cmd);
		}
	}
	
	/**
	 * 
	 */
	protected abstract void bindData();
	
	public Composite getControl() {
		return this;
	}
	
	/**
	 * 
	 */
	public void refreshControls() {
	}

}
