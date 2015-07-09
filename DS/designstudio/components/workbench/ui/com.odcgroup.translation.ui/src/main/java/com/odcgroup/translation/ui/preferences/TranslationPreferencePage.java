package com.odcgroup.translation.ui.preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationPreferenceConstants;
import com.odcgroup.translation.core.util.LanguageUtils;

/**
 * The Preferences page for the languages.
 */
public class TranslationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPropertyPage {

	/** Stores owning element of properties */
	private IAdaptable element;
	
	/** Locales cache  */
	private Set<Locale> locales = new HashSet<Locale>();

	/** Current default locale, updated via combo */
	private Locale defaultLocale;

	/** The additional locales shown in the list */
	private Set<Locale> additionalLocales = new HashSet<Locale>();
	
	private MyComboFieldEditor defLanguage; 

	/**
	 * LocalesContentProvider
	 */
	private class LocalesContentProvider implements IStructuredContentProvider {
		private Locale[] locales = {};

		public Object[] getElements(Object element) {
			return locales;
		}

		public void dispose() {
			locales = null;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.locales = (Locale[]) newInput;
		}
	}

	/**
	 * localeLabelProvider
	 */
	private class LocaleLabelProvider extends LabelProvider {
		public String getText(Object element) {
			Locale locale = (Locale) element;
			return LanguageUtils.getFullDisplayStringFromLocale(locale, TranslationPreferenceConstants.DEFAULT_LOCALE);
		}
	}

	/**
	 * LocaleViewerFilter
	 */
	private class LocaleViewerFilter extends ViewerFilter {
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			Locale locale = (Locale) element;
			String text = LanguageUtils.getFullDisplayStringFromLocale(locale, TranslationPreferenceConstants.DEFAULT_LOCALE);
			return !additionalLocales.contains(locale) && !defLanguage.getText().equals(text);
		}
	}

	/**
	 * LocaleViewerSorter
	 */
	private class LocaleViewerSorter extends ViewerSorter {
		public int compare(Viewer viewer, Object left, Object right) {
			String leftStr = LanguageUtils.getFullDisplayStringFromLocale((Locale) left, TranslationPreferenceConstants.DEFAULT_LOCALE);
			String rightStr = LanguageUtils.getFullDisplayStringFromLocale((Locale) right, TranslationPreferenceConstants.DEFAULT_LOCALE);
			return leftStr.compareTo(rightStr);
		}
	}

	/**
	 * Helper class to choose additional interest languages
	 */
	private class LocalesDialog extends Dialog {

		private ListViewer localesViewer;
		private List<Locale> locales;

		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(" Additional Languages");
			newShell.setSize(250, 300);
			Point pt = newShell.getDisplay().getCursorLocation();
			newShell.setLocation(pt.x, pt.y);
		}

		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			composite.setLayout(new FillLayout());

			ScrolledComposite sc = new ScrolledComposite(composite, SWT.BORDER);

			localesViewer = new ListViewer(sc, SWT.MULTI | SWT.V_SCROLL);
			localesViewer.getControl().setSize(250, 300);
			localesViewer.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			localesViewer.setContentProvider(new LocalesContentProvider());
			localesViewer.setLabelProvider(new LocaleLabelProvider());
			localesViewer.addFilter(new LocaleViewerFilter());
			localesViewer.setSorter(new LocaleViewerSorter());
			localesViewer.setInput(LanguageUtils.getISOLocales());

			sc.setContent(localesViewer.getControl());
			sc.setExpandHorizontal(true);
			sc.setExpandVertical(true);
			parent.pack();
			return composite;
		}

		@SuppressWarnings({ "rawtypes" })
		protected void okPressed() {
			IStructuredSelection selection = (IStructuredSelection) localesViewer.getSelection();
			locales = new ArrayList<Locale>();
			Iterator it = selection.iterator();
			while (it.hasNext()) {
				locales.add((Locale) it.next());
			}
			super.okPressed();
		}

		/**
		 * @return selected Locales
		 */
		public List<Locale> getLocales() {
			return locales;
		}

		public LocalesDialog(Shell shell) {
			super(shell);
		}
	}

	/**
	 * Helper class to display choosen interest languages
	 */
	private class MyLocaleListEditor extends LocaleListEditor {

		/**
		 * @param name
		 * @param labelText
		 * @param parent
		 */
		public MyLocaleListEditor(String name, String labelText, Composite parent) {
			super(name, labelText, parent);
		}

		/**
		 * @see org.eclipse.jface.preference.ListEditor#createList(java.lang.String[])
		 */
		protected String createList(String[] items) {
			StringBuilder builder = new StringBuilder();
			for (int kx = 0; kx < items.length; kx++) {
				if (kx > 0) {
					builder.append(",");
				}
				builder.append(LanguageUtils.getLanguage(items[kx], TranslationPreferenceConstants.DEFAULT_LOCALE));
			}
			return builder.toString();
		}

		/**
		 * @see org.eclipse.jface.preference.ListEditor#getNewInputObject()
		 */
		protected String[] getNewInputObject() {

			String[] result = {};

			Shell shell = new Shell(getShell(), SWT.TITLE);
			LocalesDialog dialog = new LocalesDialog(shell);

			if (Window.OK == dialog.open()) {
				List<Locale> locales = dialog.getLocales();
				result = new String[locales.size()];
				int kx = 0;
				for (Locale locale : locales) {
					result[kx++] = LanguageUtils.getFullDisplayStringFromLocale(locale, TranslationPreferenceConstants.DEFAULT_LOCALE);
				}
				additionalLocales.addAll(locales);
			}

			return result;
		}

		/**
		 * @see org.eclipse.jface.preference.ListEditor#parseString(java.lang.String)
		 */
		protected String[] parseString(String stringList) {
			ArrayList<String> strList = new ArrayList<String>();
			StringTokenizer tokenizer = new StringTokenizer(stringList, ",");
			while (tokenizer.hasMoreTokens()) {
				Locale loc = LanguageUtils.getLocaleFromString(tokenizer.nextToken());
				strList.add(LanguageUtils.getFullDisplayStringFromLocale(loc, TranslationPreferenceConstants.DEFAULT_LOCALE));
			}
			return strList.toArray(new String[strList.size()]);
		}

		/** 
		 * @see com.odcgroup.translation.ui.preferences.LocaleListEditor#itemsRemoved(java.lang.String[])
		 */
		protected void itemsRemoved(String[] items) {
			for (int kx = 0; kx < items.length; kx++) {
				Locale loc = LanguageUtils.getLanguage(items[kx], TranslationPreferenceConstants.DEFAULT_LOCALE);
				additionalLocales.remove(loc);
			}
		}

	}
	
	private class LocaleComparator implements Comparator<Locale> {
		@Override
		public int compare(Locale o1, Locale o2) {
			String n1 = LanguageUtils.getFullDisplayStringFromLocale(o1, TranslationPreferenceConstants.DEFAULT_LOCALE);
			String n2 = LanguageUtils.getFullDisplayStringFromLocale(o2, TranslationPreferenceConstants.DEFAULT_LOCALE);
			return n1.compareTo(n2);
		}
		
	}
	
	private String[][] getEntryNamesAndValues() {

		// builds the locales list for the combobox
		List<Locale> localeCbx = new ArrayList<Locale>(locales);
		for (Locale locale : additionalLocales) {
			localeCbx.remove(locale);
		}
		Collections.sort(localeCbx, new LocaleComparator());
		
		int nbLocales = localeCbx.size();
		String[][] entryNamesAndValues = new String[nbLocales][2];
		for (int kx = 0; kx < nbLocales; kx++) {
			Locale locale = localeCbx.get(kx);
			String name = LanguageUtils.getFullDisplayStringFromLocale(locale, TranslationPreferenceConstants.DEFAULT_LOCALE);
			String value = locale.toString();
			entryNamesAndValues[kx] = new String[] { name, value };
		}
		
		return entryNamesAndValues;
	}
	
	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	protected void createFieldEditors() {
		
		for (Locale locale : Arrays.asList(LanguageUtils.getISOLocales())) {
			locales.add(locale);
		}
		
		IProject project = (IProject) getElement().getAdapter(IProject.class);
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		ITranslationPreferences prefs = manager.getPreferences();
		defaultLocale = prefs.getDefaultLocale();
		for (Locale locale : prefs.getAdditionalLocales()) {
			additionalLocales.add(locale);
		}

		defLanguage = new MyComboFieldEditor(   
				TranslationPreferenceConstants.PROPERTY_DEFAULT_LANGUAGE,
				"Default Language", 
				getEntryNamesAndValues(), 
				getFieldEditorParent());
		addField(defLanguage);
		
		defLanguage.setPropertyChangeListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				defaultLocale = LanguageUtils.getLocaleFromString((String)event.getOldValue());
			}
		});

		MyLocaleListEditor localeListEditor = new MyLocaleListEditor(
				TranslationPreferenceConstants.PROPERTY_ADDITIONAL_LANGUAGES, 
				"Interest Languages",
				getFieldEditorParent()) {
					@Override
					protected void itemsChanged() {
						defLanguage.setEntryNamesAndValues(getEntryNamesAndValues());
					}
		};
		addField(localeListEditor);
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	public IAdaptable getElement() {
		return element;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		this.element = element;
	}

	/**
	 */
	public TranslationPreferencePage() {
		super("Translation Settings", FieldEditorPreferencePage.GRID);
	}

    protected IPreferenceStore doGetPreferenceStore() {
		IProject project = (IProject) getElement().getAdapter(IProject.class);
        return new ScopedPreferenceStore(new ProjectScope(project), TranslationCore.PLUGIN_ID);
    }

}