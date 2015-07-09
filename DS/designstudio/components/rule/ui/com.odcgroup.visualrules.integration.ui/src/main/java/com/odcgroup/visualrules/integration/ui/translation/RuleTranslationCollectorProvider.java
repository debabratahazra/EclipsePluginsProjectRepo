package com.odcgroup.visualrules.integration.ui.translation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.model.ITranslationCollector;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 */
public class RuleTranslationCollectorProvider extends BaseTranslationCollectorProvider {
	
	private LabelProvider labelProvider = null;

	@Override
	public ITranslationCollector getTranslationCollector(IOfsProject ofsProject) {
		return new RuleTranslationCollector(ofsProject, getModelExtensions());
	}

	@Override
	public ITranslationOwnerSelector getTranslationOwnerSelector() {
		return null; /*new RuleTranslationOwnerSelector()*/
	}
	
	@Override
	public LabelProvider getModelLabelProvider() {
		if (null == labelProvider) {
			labelProvider = new LabelProvider() {
				@Override
				public String getText(Object element) {
					String text = null;
					if (element != null && element instanceof StructuredSelection) {
						Object item = ((StructuredSelection)element).getFirstElement();
						if (item instanceof RuleMessageProxy) {
							RuleMessageProxy proxy = (RuleMessageProxy)item;
							text = "Rule " + proxy.getCode();
							int pos = text.indexOf('.');
							if (pos > 0) {
								text = text.substring(0,pos-1);
							}
						} 
					}
					return text;
				}
			};
		}
		return labelProvider;
	}

}
