package com.odcgroup.translation.ui.editor.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.internal.ui.util.PatternConstructor;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.translation.ui.editor.model.ITranslationTableItem;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class TranslationItemFilter implements IFilter {
	
	protected boolean filterDependents = true;
	protected boolean filterEmpty = true;
	protected String search = "*";
	protected boolean patternChanged = false;
	protected Pattern pattern;
	protected List<Locale> locales = new ArrayList<Locale>();
	protected IProject project = null;
	
	/**
	 * @param ofsProject
	 * @param locales
	 */
	public TranslationItemFilter(IProject project, List<Locale> locales) {
		this.project = project;
		this.locales.addAll(locales);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (!(toTest instanceof ITranslationTableItem)) {
			return false;
		}
		ITranslationTableItem item = (ITranslationTableItem) toTest;
		if (filterDependents(item)) {
			if (filterEmpty(item)) {
				return matchesSearch(item);
			}
		}
		return false;	
	}
	
	/**
	 * @param item
	 * @return
	 */
	protected boolean matchesSearch(ITranslationTableItem item) {
		if (StringUtils.isEmpty(search)) {
			return true;
		} else {
			for (Locale locale : locales) {
				String message = item.getText(locale);
				if (StringUtils.isEmpty(message)) {
					continue;
				}
				if (pattern.matcher(message).matches()){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param filter
	 */
	public void toggleFilterDependents(boolean filter) {
		this.filterDependents = filter;
	}
	
	/**
	 * @param filter
	 */
	public void toggleFilterEmpty(boolean filter) {
		this.filterEmpty = filter;
	}
	
	/**
	 * @param search
	 */
	public void setSearch(String search) {
		patternChanged = ! this.search.equals(search);
		if (patternChanged) {
			this.search = search;
			if (!search.endsWith("*"))
				search = search + "*";
			pattern = PatternConstructor.createPattern(search, false, false);
		}
	}
	
	/**
	 * @param item
	 * @return
	 */
	private boolean filterDependents(ITranslationTableItem item) {
		if (filterDependents && isDependentTranslation(item)) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param item
	 * @return
	 */
	protected boolean filterEmpty(ITranslationTableItem item) {
		if (filterEmpty && isEmptyTranslation(item)) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param item
	 * @return
	 */
	protected boolean isDependentTranslation(ITranslationTableItem item)  {
		EObject eObj = (EObject) item.getTranslation().getOwner();
		if (eObj.eResource() != null) {
			 IProject rProject = OfsResourceHelper.getProject(eObj.eResource());
			 if (!project.getName().equals(rProject.getName())) {
				 return true;
			 }
		}
		return false;
	}
	
	/**
	 * @param item
	 * @return
	 */
	private boolean isEmptyTranslation(ITranslationTableItem item) {
		for (Locale locale : locales) {
			try {
				String message = item.getText(locale);
				if (!StringUtils.isEmpty(message)) {
					return false;
				}
			} catch (Exception e) {
				//
			}
		} 
		return true;
	}
		

}
