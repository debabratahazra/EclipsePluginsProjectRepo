package com.odcgroup.visualrules.integration.ui.custom;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.rules.vrules.actions.MessageAction;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.ModelEditorInput;

import de.visualrules.custom.core.IExternalParameterAccessor;
import de.visualrules.custom.core.IParameterTransactionManager;
import de.visualrules.custom.ui.ICustomUI;
import de.visualrules.custom.ui.IWidgetFactory;

/** 
 * The custom user-interface for integrating Visual Rules
 * with the translation framework.
 * 
 * @author Gary Hayes, Kai Kreuzer
 */

public class CustomMessageActionUI implements ICustomUI {

	private static final Logger logger = LoggerFactory.getLogger(CustomMessageActionUI.class);

	// As per MDF-Core.jar's com.odcgroup.mdf.core.services.Message (no need to depend on it just for 3 constants!)
	private static final int MDF_MESSAGE_INFO = 0;
	private static final int MDF_MESSAGE_WARNING = 1;
	private static final int MDF_MESSAGE_ERROR = 2;

	/** 
	 * The severity codes. Since most messages are expected to be errors we put this first.
	 */
	private static String[] SEVERITY_CODES = new String[]{"Info", "Warning", "Error"};
	
	/** The severity combo-box. */
	private Combo severity;
	
	/** The default message. */
	private Text defaultMessage;
	
	private IOfsProject ofsProject;
	
	/** The parameter accessor. */
	private IExternalParameterAccessor accessor;
	
	/** Enable updates to be done in such a way that a single undo can be performed. */
	//private IParameterTransactionManager manager;
	
	/** Focus listener for the severity. */
    private FocusListener severityFocusListener = new FocusAdapter() {

        public void focusLost(FocusEvent e) {
            String s = severity.getText();
            int i = convertSeverity(s);
            accessor.setIntegerConstant(MessageAction.MESSAGE_SEVERITY, i);       
        }
    };
    
    /** Focus listener for the default message. */
    private FocusListener defaultMessageFocusListener = new FocusAdapter() {

        public void focusLost(FocusEvent e) {
            accessor.setStringConstant(MessageAction.MESSAGE_DEFAULT_MESSAGE, defaultMessage.getText());        
        }
    };

	/** Translation viewer */
	private ITranslationViewer viewer;

	public CustomMessageActionUI() {
	}

	@Override
	public Composite createUI(Composite parent, FormToolkit toolKit, IWidgetFactory factory) {
	    Composite composite = toolKit.createComposite(parent);
	    composite.addListener(SWT.Dispose, new Listener(){
			public void handleEvent(Event event) {
				if (viewer != null) {
					viewer.dispose();
				}
			}
	    });
	    composite.setLayout(new GridLayout(1, false));
	    
	    Composite sComp = toolKit.createComposite(composite);
	    sComp.setLayout(new GridLayout(4, false));
	    
	    toolKit.createLabel(sComp, "Severity: ");
	    
	    severity = new Combo(sComp, SWT.NONE);
	    severity.setItems(SEVERITY_CODES);
	    severity.setText(SEVERITY_CODES[2]);
        
        toolKit.createLabel(sComp, "   Default Message: ");
        defaultMessage = toolKit.createText(sComp, "");
        
        GridData gd = new GridData();
        gd.widthHint = 500;
        defaultMessage.setLayoutData(gd);
        
        Composite lComp = toolKit.createComposite(composite);
        lComp.setLayout(new GridLayout(2, false));
        int style = GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_VERTICAL | GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING;
		lComp.setLayoutData(new GridData(style));
		Label msgLabel = toolKit.createLabel(lComp, "Message: ");

		ofsProject = findCurrentOfsProject();
		
		if(ofsProject!=null) {
			viewer = TranslationUICore.getTranslationViewer(ofsProject.getProject(), lComp);		
		} else {
    		// DS-2151
    		msgLabel.setText("");
    	}
    	
	    /* DS-401 
	    createParametersList(composite, toolKit, factory);
	    */
	    
	    return composite;
	}

	/**
	 * Creates the Parameters list.
	 * 
     * @param parent The parent SWT widget
     * @param toolKit Visual Rules supplied toolkit
     * @param factory Visual Rules supplied factory
	 */
	@SuppressWarnings("unused")
	private void createParametersList(Composite parent, FormToolkit toolKit,
	        IWidgetFactory factory) {
	    
        Composite comp = toolKit.createComposite(parent);
        comp.setLayout(new GridLayout(2, false));	   
        toolKit.createLabel(comp, "Parameters: ");

        Composite pComp = toolKit.createComposite(comp, SWT.BORDER);
        pComp.setLayout(new GridLayout(2, false));
	    for (int i = 0; i < 5; ++i) {
	        toolKit.createLabel(pComp, "" + (i + 1) + ". ");
	        Control c = factory.createExpressionViewer(MessageAction.MESSAGE_PARAMETERS + i, pComp, SWT.NONE);
	        GridData gd = new GridData();
	        gd.widthHint = 400;
	        c.setLayoutData(gd);
	    }
	}
	
	/**
	 * Refreshes the editor.
	 */
	public void refresh() {
		// We remove and then add the ModifyListener since otherwise Visual Rules raises a Transaction Exception
		severity.removeFocusListener(severityFocusListener);
		defaultMessage.removeFocusListener(defaultMessageFocusListener);
		
		int i = accessor.getIntegerConstant(MessageAction.MESSAGE_SEVERITY);
		String s = convertSeverity(i);
		severity.setText(s);
		
		String dm = accessor.getStringConstant(MessageAction.MESSAGE_DEFAULT_MESSAGE);
		defaultMessage.setText(dm);
		
		if(viewer!=null) {
			String code = accessor.getStringConstant(MessageAction.MESSAGE_CODE);
			if(code.isEmpty()) {
				code = generateNewMessageCode();
				accessor.setStringConstant(MessageAction.MESSAGE_CODE, code);
			}
			RuleTranslationRepo repo = RulesIntegrationUtils.getRulesTranslationModel(ofsProject.getProject());
			if(repo!=null) {
				RuleMessageProxy owner = repo.getCodeMap().get(code);
				if(owner==null) {
					owner = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
					owner.setCode(code);
					repo.getCodeMap().put(code, owner);
					try {
						repo.eResource().save(null);
						owner = repo.getCodeMap().get(code);
					} catch (IOException e) {
						logger.warn("Rule translation model cannot be modified.");
					}
				}
				ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
				ITranslation translation = manager.getTranslation(owner);
				TranslationModel model = new TranslationModel(manager.getPreferences(), translation);
				viewer.setTranslationModel(model);
			} else {
				logger.warn("No rule translation model could be loaded.");
			}
		}

	    severity.addFocusListener(severityFocusListener);
	    defaultMessage.addFocusListener(defaultMessageFocusListener);	    
	}

	/**
	 * @return a new message code
	 */
	private String generateNewMessageCode() {
		return String.valueOf(System.nanoTime()) + ".rulemsg";
	}

	/**
	 * Sets the focus.
	 */
	public void setFocus() {
	      String dm = accessor.getStringConstant(MessageAction.MESSAGE_DEFAULT_MESSAGE);
        defaultMessage.setText(dm);
	}

	/**
	 * Provides access to message parameters.
	 * 
	 * @param accessor The accessor
	 */
	public void setParameterAccessor(IExternalParameterAccessor accessor) {
		this.accessor = accessor;
	}

	/**
	 * Sets the transaction manager enabling several properties to be modified at one time.
	 * 
	 * @param manager The transaction manager
	 */
	public void setTransactionManager(IParameterTransactionManager manager) {
	    // No-op
	}	
	
	/**
	 * Converts the severity to an integer.
	 * 
	 * @param severity The severity as a String
	 * @return int
	 */
	private static int convertSeverity(String s) {
		if (StringUtils.isEmpty(s)) {
			return MDF_MESSAGE_ERROR;
		} else if (s.equals(SEVERITY_CODES[0])) {
			return MDF_MESSAGE_INFO;
		} else if (s.equals(SEVERITY_CODES[1])) {
			return MDF_MESSAGE_WARNING;
		} else if (s.equals(SEVERITY_CODES[2])) {
			return MDF_MESSAGE_ERROR;
		} else {
			return MDF_MESSAGE_ERROR;
		}
	}
	
	/**
	 * Converts the severity to an string.
	 * 
	 * @param severity The severity as an integer
	 * @return String
	 */
	private static String convertSeverity(int i) {
		if (i >= 0 && i < 3) {
		    return SEVERITY_CODES[i];
		}
		return SEVERITY_CODES[2];
	}	
	
	/**
	 * Finds the current project.
	 * 
	 * @return IProject The current project
	 */
	public static IOfsProject findCurrentOfsProject() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return null;
		}
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		if (workbenchWindow == null) {
			return null;
		}
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		if (workbenchPage == null) {
			return null;
		}
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		if (editorPart == null) {
			return null;
		}

		IEditorInput input = editorPart.getEditorInput();
		
		if (input instanceof IFileEditorInput) {
			IProject project = ((IFileEditorInput) input).getFile().getProject();
			return OfsCore.getOfsProjectManager().getOfsProject(project);

		} else if (input instanceof ModelEditorInput) {
			try {
				IOfsModelResource ofsResource = (IOfsModelResource) ((ModelEditorInput) input).getStorage();
				return ofsResource.getOfsProject();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} 
		
		// DS-2151 - begin  
		// Unable to retrieve the project when the input is an
		// instance of de.visualrules.model.ui.util.ResourceEditorInput
		// and the URI points to a jar file.
		// so a null is returned.
		// DS-2151 - end  
		
		return null;
	}

}
