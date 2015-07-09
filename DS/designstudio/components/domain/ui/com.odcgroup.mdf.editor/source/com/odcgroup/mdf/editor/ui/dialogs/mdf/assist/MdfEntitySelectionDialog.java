package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTableLabelProvider;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * A dialog to select a concept.
 */
public class MdfEntitySelectionDialog extends TwoPaneElementSelector {

    private static final String TITLE_KEY = "entitySelectionDialog.title"; //$NON-NLS-1$
    private static final String MESSAGE_KEY = "entitySelectionDialog.message"; //$NON-NLS-1$
    private static final String UPPER_LABEL_KEY = "entitySelectionDialog.upperLabel"; //$NON-NLS-1$
    private static final String LOWER_LABEL_KEY = "entitySelectionDialog.lowerLabel"; //$NON-NLS-1$
    private static final String APP_TITLE_KEY = "appSelectionDialog.title"; //$NON-NLS-1$
    private static final String APP_MESSAGE_KEY = "appSelectionDialog.message"; //$NON-NLS-1$
    private static final String APP_UPPER_LABEL_KEY = "appSelectionDialog.upperLabel"; //$NON-NLS-1$
    private static final String DEFAULT_FILTER = "";
    private static final String DISPLAY_ALL = "*";
	private StyledText documentText;
	private static boolean isLowerListDisabled = false;
	private static String modelName;

    /**
     * Constructs a type selection dialog.
     * 
     * @param parent the parent shell.
     * @param context the runnable context.
     */
    private MdfEntitySelectionDialog(Shell parent,
            ILabelProvider entityRenderer, ILabelProvider domainRenderer) {
        super(parent, entityRenderer, domainRenderer);
        setMatchEmptyString(true);
    }
    
    @Override
    public Control createDialogArea(Composite parent) {
    	
    	Composite mainComposite = (Composite) super.createDialogArea(parent);
    	setFilter(DISPLAY_ALL);
    	documentationTextGroup(mainComposite);
    	
		return mainComposite;
    }
    
	/**
	 * Overriding the method for DS-7941.
	 */
	@Override
	protected Table createLowerList(Composite parent) {
		Table lowerList = super.createLowerList(parent);
		((GridData) lowerList.getLayoutData()).heightHint = 4;
		if(isLowerListDisabled) {
			lowerList.setVisible(false);
			isLowerListDisabled = false;
		}
		return lowerList;
	}
    
    /**
	 * create the document text in the lowerpane.
	 * 
	 * @param parent
	 */
	protected void documentationTextGroup(Composite parent) {
		Group textGourp = new Group(parent, SWT.NONE);
		textGourp.setText("Documentation");
		textGourp.setLayout(new GridLayout());
		GridData documentGroupData = new GridData();
		documentGroupData.grabExcessVerticalSpace = true;
		documentGroupData.grabExcessHorizontalSpace = true;
		documentGroupData.horizontalAlignment = GridData.FILL;
		documentGroupData.verticalAlignment = GridData.FILL;
		documentGroupData.minimumHeight = 95;
		textGourp.setLayoutData(documentGroupData);

		documentText = new StyledText(textGourp, SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		documentText.setLayoutData(documentGroupData);
		documentText.setEditable(false);
		documentText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
	}

    public static MdfEntitySelectionDialog createDialog(Shell parent,
            IContentAssistProvider provider) {
        final ILabelProvider baseProvider = new MdfTableLabelProvider();
        ILabelDecorator decorator = MdfPlugin.getDefault().getWorkbench().getDecoratorManager().getLabelDecorator();

        MdfEntitySelectionDialog dialog = new MdfEntitySelectionDialog(parent,
                new DecoratingLabelProvider(baseProvider, decorator){
        			@Override
        			public String getText(Object element) {
        				MdfEntityImpl entity =  (MdfEntityImpl) element;
        				if(entity.eIsProxy()) {
	    					String text = baseProvider.getText(entity);
	    					String[] entityName = text.split("\\.");
	    					if(entityName != null && entityName.length > 1) {
	    						return entityName[1].replace("_", ".");
	    					}
	    					return text.replace('_', '.');
        				}
						return baseProvider.getText(entity).replace('_', '.');
        			}								
        		},
                new DecoratingLabelProvider(new LabelProvider() {

                    public Image getImage(Object entity) {
                    	MdfEntityImpl entityImpl =  (MdfEntityImpl) entity;
                    	if(entityImpl.eIsProxy()) {
                    		return MdfPlugin.getImage(MdfCore.ICON_DOMAIN);
                    	}
                    	return baseProvider.getImage(entityImpl.getParentDomain());
                    }

                    public String getText(Object entity) {
                    	MdfEntityImpl entityImpl =  (MdfEntityImpl) entity;
                    	if(entityImpl.eIsProxy()) {
                    		String text = baseProvider.getText(entityImpl);
        					String[] domainName = text.split("\\.");
        					return domainName[0];
                    	}
                    	return baseProvider.getText(entityImpl.getParentDomain());
                    }
                }, decorator));

        // Modified for DS-7944 
		if (StringUtils.isNotBlank(modelName)
				&& (modelName.equalsIgnoreCase("enquiry") || modelName.equalsIgnoreCase("version"))) {
			dialog.setTitle(getResourceString(APP_TITLE_KEY));
			dialog.setMessage(getResourceString(APP_MESSAGE_KEY));
			dialog.setUpperListLabel(getResourceString(APP_UPPER_LABEL_KEY));
		} else {
			dialog.setTitle(getResourceString(TITLE_KEY));
			dialog.setMessage(getResourceString(MESSAGE_KEY));
			dialog.setUpperListLabel(getResourceString(UPPER_LABEL_KEY));
		}
		if (!isLowerListDisabled) {
			dialog.setLowerListLabel(getResourceString(LOWER_LABEL_KEY));
		}
		dialog.setElements(provider.getCandidates());

        return dialog;
    }
    
    public static MdfEntitySelectionDialog createDialog(Shell parent,
            IContentAssistProvider provider, boolean isDomainDisabled, String entityName) {
    	isLowerListDisabled = isDomainDisabled;
    	modelName = entityName;
        return createDialog(parent, provider);
    }

    @Override
    protected Object[] getSelectedElements() {
    	Object[] selectedElements = super.getSelectedElements();
		return selectedElements;
    }
    
    @Override
    protected void handleSelectionChanged() {
    	super.handleSelectionChanged();
    	
    	Object[] selectedElements = getSelectedElements();
    	documentText.setText("");
    	if(selectedElements.length > 0){
    		if(selectedElements[0] instanceof MdfClass){
    		MdfClassImpl mdfClass = (MdfClassImpl) selectedElements[0];
    		if(mdfClass.getDocumentation() != null){
        		String documentation = mdfClass.getDocumentation();
        		documentText.setText(documentation.trim());
        		}
    		}
    		else if(selectedElements[0] instanceof MdfProperty){
    			MdfProperty mdfProp = (MdfProperty) selectedElements[0];
        		if(mdfProp.getDocumentation() != null){
            		String documentation = mdfProp.getDocumentation();
            		documentText.setText(documentation.trim());
            		}
        		}
    	}
    }

    public void setSelection(MdfEntity entity) {
        if (entity != null) {
            setSelection(entity.getQualifiedName());
        } else {
            setFilter(DEFAULT_FILTER);
        }
    }

    public void setSelection(MdfName qname) {
        if (qname != null) {
            setFilter(qname.getLocalName());
        } else {
            setFilter(DEFAULT_FILTER);
        }
    }

    protected static String getResourceString(String key) {
        return MdfPlugin.getResourceString(key);
    }

    /**
     * @see org.eclipse.ui.dialogs.AbstractElementListSelectionDialog#createFilteredList(org.eclipse.swt.widgets.Composite)
     */
    protected FilteredList createFilteredList(Composite parent) {
        FilteredList list = super.createFilteredList(parent);
        return list;
    }

}
