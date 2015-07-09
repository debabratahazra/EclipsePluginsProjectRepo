package com.odcgroup.workbench.editors.properties.choice;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EReference;

public class ReferenceListItem implements ChoiceItem {
	
	private String choiceLabel;
	private EReference choiceReference;
	private EClass referenceClass;
	private EFactory factory;
	
	/**
	 * @param choiceLabel
	 * @param choiceReference
	 * @param referenceClass
	 */
	public ReferenceListItem(String choiceLabel, EReference choiceReference, EClass referenceClass, EFactory factory){
		this.choiceLabel = choiceLabel;
		this.choiceReference = choiceReference;
		this.referenceClass = referenceClass;
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.choice.ChoiceItem#getChoiceLabel()
	 */
	public String getChoiceLabel() {
		return choiceLabel;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.choice.ChoiceItem#getChoiceReference()
	 */
	public EReference getChoiceReference() {
		return choiceReference;
	}

	/**
	 * @return
	 */
	public EClass getReferenceClass() {
		return referenceClass;
	}

	public EFactory getFactory() {
		return factory;
	}

}
