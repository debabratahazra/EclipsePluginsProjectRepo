package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyDefinitionDialog;
import com.odcgroup.workbench.editors.properties.widgets.ComboPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;

/**
 *
 * @author pkk
 *
 */
public class TransitionMappingDefDialog extends AbstractPropertyDefinitionDialog {
	
	/**
	 * @param parentShell
	 * @param element
	 * @param parent
	 * @param update
	 */
	public TransitionMappingDefDialog(Shell parentShell, EObject element,
			EObject parent, boolean update) {
		super(parentShell, element, parent, update);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.workbench.editors.properties.item.
	 * AbstractPropertyDefinitionDialog#configureProperties()
	 */
	@Override
	protected void configureProperties() {
		final PageflowPackage ePackage = PageflowPackage.eINSTANCE;

		SimpleGroupWidget group = new SimpleGroupWidget(null);
		group.setFillBoth(false);

		// combo widget for sub-pageflow endstates
		ComboPropertyWidget endState = new ComboPropertyWidget(ePackage
				.getTransitionMapping_EndState(),
				Messages.TransitionMappingEndStateLabel) {

			public Object[] getComboItems(Object element) {
				return getEndStates((SubPageflowState) element);
			}

			public String getItemDisplayText(Object element) {
				return ((EndState) element).getDisplayName();
			}
		};
		group.addPropertyFeature(endState);

		// combo widget for out going transitions
		ComboPropertyWidget transition = new ComboPropertyWidget(ePackage
				.getTransitionMapping_Transition(),
				Messages.TransitionMappingOutTransitionLabel) {

			public Object[] getComboItems(Object element) {
				return getOutTransitions((SubPageflowState) element);
			}

			public String getItemDisplayText(Object element) {
				return ((Transition)element).getDisplayName();
			}
		};
		group.addPropertyFeature(transition);

		addPropertyFeature(group);

	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog#validate(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean validate(EObject element) {
		final PageflowPackage ePackage = PageflowPackage.eINSTANCE;
		if (element.eGet(ePackage.getTransitionMapping_EndState()) != null
			&& element.eGet(ePackage.getTransitionMapping_Transition()) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param element
	 * @return
	 */
	private Object[] getOutTransitions(SubPageflowState subpageflow) {
		Pageflow pageflow = (Pageflow) subpageflow.eContainer();
		Transition transition = null;
		List<Transition> outTrans = new ArrayList<Transition>();
		for(Object obj :pageflow.getTransitions()) {
			transition = (Transition) obj;
			if (subpageflow.equals(transition.getFromState())) {
				outTrans.add(transition);
			}
		}
		return outTrans.toArray();
		
	}

	/**
	 * @param element
	 * @return
	 */
	private Object[] getEndStates(SubPageflowState subpageflow) {
		if (subpageflow.getSubPageflow() == null) {
			return Collections.EMPTY_LIST.toArray();
		}
		EClass endState = PageflowPackage.eINSTANCE.getEndState();
		EList<?> states = subpageflow.getSubPageflow().getStates();
		Collection<?> endStates = EcoreUtil.getObjectsByType(states, endState);
		return endStates.toArray();

	}

}
