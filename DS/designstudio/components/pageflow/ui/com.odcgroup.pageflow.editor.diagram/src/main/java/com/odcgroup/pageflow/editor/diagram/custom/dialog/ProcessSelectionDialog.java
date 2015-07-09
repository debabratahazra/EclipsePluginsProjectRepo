package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.internal.core.search.matching.SuperTypeReferencePattern;
import org.eclipse.jdt.internal.ui.viewsupport.JavaUILabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;

/**
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class ProcessSelectionDialog extends TwoPaneElementSelector implements IPropertySelectionDialog {

	private static final String TITLE_KEY = "Pageflow Action Selection"; //$NON-NLS-1$
	private static final String MESSAGE_KEY = "Choose an Action"; //$NON-NLS-1$
	private static final String UPPER_LABEL_KEY = "Matching Actions"; //$NON-NLS-1$
	private static final String LOWER_LABEL_KEY = "Resource"; //$NON-NLS-1$

	private static final String PAGEFLOW_PACKAGE = "com.odcgroup.wui.pageflow";
	private static final String PAGEFLOW_CLASS = "DSProcess";
	private static final String VALIDATION_PROCESS_INTERFACE_STR_PATTERN = "com.odcgroup.uif.validation.ValidationProcess";

	/**
	 * @param parent
	 * @param elementRenderer
	 * @param qualifierRenderer
	 */
	public ProcessSelectionDialog(Shell parent, ILabelProvider elementRenderer,
			ILabelProvider qualifierRenderer) {
		super(parent, elementRenderer, qualifierRenderer);
		setMatchEmptyString(false);
		setTitle(TITLE_KEY);
		setMessage(MESSAGE_KEY);
		setUpperListLabel(UPPER_LABEL_KEY);
		setLowerListLabel(LOWER_LABEL_KEY);
	}
	
	/**
	 * @param parent
	 * @param filterByValidation
	 * @return
	 */
	public static ProcessSelectionDialog createDialog(Shell parent,
			boolean filterByValidation) {
		final ILabelProvider elementRenderer = new JavaUILabelProvider();

		ILabelProvider qualifierRenderer = new LabelProvider() {

			public Image getImage(Object element) {
				IType type = (IType) element;
				return elementRenderer.getImage(type.getJavaProject());
			}

			public String getText(Object element) {
				IType type = (IType) element;
				return type.getPath().toOSString();
			}
		};

		ProcessSelectionDialog dialog = new ProcessSelectionDialog(parent,	elementRenderer, qualifierRenderer);

		ProcessSearchRequestor requestor = new ProcessSearchRequestor();
		searchFilteredProcesses(createSearchPattern(filterByValidation), requestor,filterByValidation);
		dialog.setElements(requestor.getSearchMatches());
		return dialog;
	}

	/**
	 * @param filterByValidation
	 * @return
	 */
	protected static SearchPattern createSearchPattern(
			boolean filterByValidation) {
		SearchPattern pattern = null;
		if (filterByValidation) {
			pattern = SearchPattern.createPattern(
					VALIDATION_PROCESS_INTERFACE_STR_PATTERN,
					IJavaSearchConstants.CLASS,
					IJavaSearchConstants.IMPLEMENTORS,
					SearchPattern.R_EXACT_MATCH);
		} else {
			pattern = new SuperTypeReferencePattern(PAGEFLOW_PACKAGE
					.toCharArray(), PAGEFLOW_CLASS.toCharArray(),
					SuperTypeReferencePattern.ALL_SUPER_TYPES,
					SearchPattern.R_EXACT_MATCH
							| SearchPattern.R_CASE_SENSITIVE);
		}

		return pattern;
	}
	
	/**
	 * @param classPattern
	 * @return
	 */
	protected static SearchPattern createSearchPattern(String classPattern) {
		String packageName = classPattern.substring(0, classPattern.lastIndexOf('.'));
		String className = classPattern.substring(classPattern.lastIndexOf('.')+1);
		return  new SuperTypeReferencePattern(packageName
				.toCharArray(), className.toCharArray(),
				SuperTypeReferencePattern.ALL_SUPER_TYPES,
				SearchPattern.R_EXACT_MATCH
						| SearchPattern.R_CASE_SENSITIVE);
	}
	
	/**
	 * @param pattern
	 * @param requestor
	 */
	private static void searchFilteredProcesses(SearchPattern pattern, ProcessSearchRequestor requestor, boolean filterByValidation) {
		IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
		SearchEngine searchEngine = new SearchEngine();
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine
					.getDefaultSearchParticipant() }, scope, requestor, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		List<SearchMatch> abstractTypes = new ArrayList<SearchMatch>();
		abstractTypes.addAll(requestor.getAbstractTypes());
		abstractTypes.addAll(requestor.getConcreteTypes());
		requestor.getAbstractTypes().clear();
		requestor.getConcreteTypes().clear();
		for(Iterator<SearchMatch> iter = abstractTypes.iterator();iter.hasNext();){
			IType type = (IType)iter.next().getElement();
			String typeName = type.getFullyQualifiedName();		
			searchFilteredProcesses(createSearchPattern(typeName), requestor, filterByValidation);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog#getSelection()
	 */
	@Override
	public Object getSelection() {
		IType result = (IType) getFirstResult();
		if (result != null)
			return "class:"+result.getFullyQualifiedName();
		else 
			return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.widgets.model.IPropertySelectionDialog#getResultByProperty(com.odcgroup.workbench.editors.properties.model.IProperty)
	 */
	@Override
	public Object getResultByProperty(IPropertyFeature property) {
		IType result = (IType) getFirstResult();
		EStructuralFeature feature = property.getStructuralFeature();
		if (feature == PageflowPackage.eINSTANCE.getAction_Uri()) {
			return "class:"+result.getFullyQualifiedName();
		} else if (feature == PageflowPackage.eINSTANCE.getAction_Name()) {
			return result.getElementName();
		}
		return null;
	}

}
