package com.odcgroup.visualrules.integration.ui.dialog;

import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.ui.RulesIntegrationUICore;
import com.odcgroup.workbench.ui.OfsUICore;


/**
 * A dialog to select a concept.
 */
public class RuleModelSelectionDialog extends FilteredItemsSelectionDialog {
	
	private static final String DIALOG_SETTINGS= "com.odcgroup.visualrules.integration.repository.RuleModelSelectionDialog"; //$NON-NLS-1$

	private Object[] rules;

	public RuleModelSelectionDialog(Shell shell, Object[] rules, boolean multi) {
		super(shell, multi);
		this.rules = rules;
		
		setTitle("Rule Selection Dialog");
 	    setSelectionHistory(new RuleSelectionHistory());
 	    setHelpAvailable(false);
 	    
        ILabelProvider elementRenderer = new LabelProvider() {

            public Image getImage(Object element) {
                return OfsUICore.imageDescriptorFromPlugin(
        				RulesIntegrationPlugin.PLUGIN_ID, "icons/rule.gif").createImage();
            }
            public String getText(Object element) {
            	if(element instanceof String) {
            		String uri = (String) element;
            		if(uri.startsWith("resource:/")) {
            			uri = uri.substring(10);
            			if(uri.indexOf("#")>=0) {
            				uri = uri.substring(uri.indexOf("#")+1);
            			}
            		}
                    return uri;
            	}
            	return "";
            }
        };
        setListLabelProvider(elementRenderer);
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new RuleItemsFilter();
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		progressMonitor.beginTask("Searching", rules.length); //$NON-NLS-1$
		for(Object rule : rules) {
			contentProvider.add(rule, itemsFilter);
			progressMonitor.worked(1);
		}
		progressMonitor.done();
	}

	@Override
	protected IDialogSettings getDialogSettings() {
      	IDialogSettings settings = RulesIntegrationUICore.getDefault().getDialogSettings()
      		.getSection(DIALOG_SETTINGS);
		if (settings == null) {
			settings = RulesIntegrationUICore.getDefault().getDialogSettings()
					.addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	@Override
	public String getElementName(Object item) {
		return item.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Comparator getItemsComparator() {
		return new Comparator() {
	         public int compare(Object arg0, Object arg1) {
	            return arg0.toString().compareTo(arg1.toString());
	         }
	      };
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	/**
	 * Filters types using pattern, scope, element kind and filter extension.
	 */
	protected class RuleItemsFilter extends ItemsFilter {

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}

		@Override
		public boolean matchItem(Object item) {
			String rule = item.toString();
			return 
				matches(rule) ||
				matches(rule.substring(rule.lastIndexOf("/")+1)) ||
				matches(rule.substring(rule.indexOf("#")+1));
		}
		
		@Override
		public boolean equalsFilter(ItemsFilter filter) {
            return false;
        }
	}
	
	private class RuleSelectionHistory extends SelectionHistory {
		/*
	       * @see org.eclipse.ui.dialogs.FilteredItemsSelectionDialog.SelectionHistory#restoreItemFromMemento(org.eclipse.ui.IMemento)
	       */
	      protected Object restoreItemFromMemento(IMemento element) {
	         return element.getString("rule"); //$NON-NLS-1$
	      }
	      /*
	       * @see org.eclipse.ui.dialogs.FilteredItemsSelectionDialog.SelectionHistory#storeItemToMemento(java.lang.Object,
	       *      org.eclipse.ui.IMemento)
	       */
	      protected void storeItemToMemento(Object item, IMemento element) {
	         element.putString("rule", item.toString()); //$NON-NLS-1$
	      }
	}
	
	public String getResultRuleId() {
		String result = (String) getFirstResult();
		
		if(result==null) return null;
		
		if(result.indexOf("#")>=0) {
			return result.substring(result.indexOf("#")+1);
		}
		
		return null;
	}
}
