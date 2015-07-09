package com.odcgroup.workbench.ui.repository;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

import com.odcgroup.workbench.ui.OfsUICore;


/**
 * A dialog to select a concept.
 */
public class ModelSelectionDialog extends TwoPaneElementSelector {

    private static final String TITLE_KEY = OfsUICore.getDefault().getString("ModelSelectionDialog.title"); //$NON-NLS-1$
    private static final String MESSAGE_KEY = OfsUICore.getDefault().getString("ModelSelectionDialog.message"); //$NON-NLS-1$
    private static final String UPPER_LABEL_KEY = OfsUICore.getDefault().getString("ModelSelectionDialog.upperLabel"); //$NON-NLS-1$
    private static final String LOWER_LABEL_KEY = OfsUICore.getDefault().getString("ModelSelectionDialog.lowerLabel"); //$NON-NLS-1$
    private static final String DEFAULT_FILTER = OfsUICore.getDefault().getString("ModelSelectionDialog.defaultFilter"); //$NON-NLS-1$

    private final ILabelProvider elementRenderer;

    /**
     * Constructs a type selection dialog.
     * 
     * @param parent the parent shell.
     * @param context the runnable context.
     */
    public ModelSelectionDialog(Shell parent, ILabelProvider elementRenderer,
            ILabelProvider qualifierRenderer) {
        super(parent, elementRenderer, qualifierRenderer);
        this.elementRenderer = elementRenderer;
        setMatchEmptyString(false);
        setTitle(TITLE_KEY);
        setMessage(MESSAGE_KEY);
        setUpperListLabel(UPPER_LABEL_KEY);
        setLowerListLabel(LOWER_LABEL_KEY);
    }

    public static ModelSelectionDialog createDialog(Shell parent,
            Object[] elements, final AdapterFactory adapterFactory) {

        final ILabelProvider elementRenderer = new AdapterFactoryLabelProvider(
                adapterFactory);

        ILabelProvider qualifierRenderer = new LabelProvider() {

            public Image getImage(Object element) {
                EObject eObject = (EObject) element;
                return elementRenderer.getImage(eObject.eResource());
            }

            public String getText(Object element) {
                EObject eObject = (EObject) element;
                return elementRenderer.getText(eObject.eResource());
            }
        };

        ModelSelectionDialog dialog = new ModelSelectionDialog(parent,
                elementRenderer, qualifierRenderer);

        dialog.setElements(elements);
        return dialog;
    }

    public void setSelection(Object selection) {
        if (selection != null) {
            setSelection(elementRenderer.getText(selection));
        } else {
            setFilter(DEFAULT_FILTER);
        }
    }

    /**
     * @see org.eclipse.ui.dialogs.AbstractElementListSelectionDialog#createFilteredList(org.eclipse.swt.widgets.Composite)
     */
    protected FilteredList createFilteredList(Composite parent) {
        FilteredList list = super.createFilteredList(parent);
        list.setFilterMatcher(new NameFilterMatcher());
        return list;
    }

    private class NameFilterMatcher implements FilteredList.FilterMatcher {

        private static final char END_SYMBOL = '<';
        private static final char ANY_STRING = '*';
        private StringMatcher _matcher;

        public NameFilterMatcher() {
            super();
        }

        /**
         * @see org.eclipse.ui.dialogs.FilteredList.FilterMatcher#setFilter(java.lang.String,
         *      boolean, boolean)
         */
        public void setFilter(String pattern, boolean ignoreCase,
                boolean ignoreWildCards) {
            _matcher = new StringMatcher(adjustPattern(pattern), ignoreCase,
                    ignoreWildCards);
        }

        /**
         * @see org.eclipse.ui.dialogs.FilteredList.FilterMatcher#match(java.lang.Object)
         */
        public boolean match(Object element) {
            String actual = elementRenderer.getText(element);
            return _matcher.match(actual);
        }

        private String adjustPattern(String pattern) {
            int length = pattern.length();

            if (length > 0) {
                switch (pattern.charAt(length - 1)) {
                    case END_SYMBOL:
                        pattern = pattern.substring(0, length - 1);
                        break;

                    case ANY_STRING:
                        break;

                    default:
                        pattern = pattern + ANY_STRING;
                }
            }

            return pattern;
        }
    }

}
