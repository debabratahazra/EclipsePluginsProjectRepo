package com.odcgroup.pageflow.compare.provider;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import com.odcgroup.pageflow.model.provider.PageflowItemProviderAdapterFactory;

/**
 *
 * @author pkk
 *
 */
public class PageflowCompareItemProviderAdapterFactory extends PageflowItemProviderAdapterFactory {
	
	@SuppressWarnings("unchecked")
	public PageflowCompareItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}
	
	public Adapter createEndStateAdapter() {
		if (endStateItemProvider == null) {
			endStateItemProvider = new EndStateCompareItemProvider(this);
		}

		return endStateItemProvider;
	}
	
	public Adapter createInitStateAdapter() {
		if (initStateItemProvider == null) {
			initStateItemProvider = new InitStateCompareItemProvider(this);
		}

		return initStateItemProvider;
	}
	
	public Adapter createViewStateAdapter() {
		if (viewStateItemProvider == null) {
			viewStateItemProvider = new ViewStateCompareItemProvider(this);
		}

		return viewStateItemProvider;
	}
	
	public Adapter createDecisionStateAdapter() {
		if (decisionStateItemProvider == null) {
			decisionStateItemProvider = new DecisionStateCompareItemProvider(this);			
		}
		
		return decisionStateItemProvider;
	}
	
	public Adapter createTransitionAdapter() {
		if (transitionItemProvider == null) {
			transitionItemProvider = new TransitionCompareItemProvider(this);
		}

		return transitionItemProvider;
	}
	
	public Adapter createSubPageflowStateAdapter() {
		if (subPageflowStateItemProvider == null) {
			subPageflowStateItemProvider = new SubpageflowStateCompareItemProvider(this);
		}

		return subPageflowStateItemProvider;
	}

}
