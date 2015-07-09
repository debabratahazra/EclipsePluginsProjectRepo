package com.odcgroup.visualrules.integration.ui.filter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IActionFilter;

import com.odcgroup.mdf.generation.rules.RuleCategory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class is returned by the <code>ActionFilterAdapterFactory</code>, which provides
 * a filtering mechanism for MdfEntity instances.
 * This filtering is used to enable the correct context menus depending on what kind
 * of rule exists for a MdfEntity.
 *
 * @author Kai Kreuzer
 *
 */
public class RuleActionFilter implements IActionFilter {

	/**
	 * Tests if a rule of the given category is present. The value of the category
	 * must be identical to the enum definition in RulesCategory, e.g. "VALIDATION".
	 */
	private static final String RULE_EDIT_CATEGORY_PROPERTY = "ds.edit.rule.category.enabled";

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionFilter#testAttribute(java.lang.Object, java.lang.String, java.lang.String)
	 */
	public boolean testAttribute(Object target, String name, String value) {
		if((target instanceof MdfClass || target instanceof MdfDataset) 
				&& target instanceof EObject && RULE_EDIT_CATEGORY_PROPERTY.equals(name)) {
			RuleCategory category = RuleCategory.valueOf(value);
			if(category==null) return false;
			MdfEntity entity = (MdfEntity) target;
			if(RulesIntegrationPlugin.ruleExists(entity, category)) return true;
		}

		// DS-2874
		if((target instanceof MdfClass || target instanceof MdfDataset) 
				&& target instanceof EObject && "ds.filtered".equals(name) && "edit.rules.menu".equals(value)) {
			EObject eObj = (EObject) target;
			URI uri = eObj.eResource().getURI();
			try {
				IOfsModelResource modelResource = OfsResourceHelper.getOfsProject(eObj.eResource()).getOfsModelResource(uri);
				if(modelResource.hasScope(IOfsProject.SCOPE_DEPENDENCY)) return true;				
			} catch (ModelNotFoundException e) {
				RulesIntegrationPlugin.getDefault().logError("We cannot retrieve the ofsResource for " + uri.toString(), e);
			}
			
		}
		
		return false;
	}
}
