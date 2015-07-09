package com.odcgroup.workbench.editors.properties.choice;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.workbench.editors.properties.util.SectionPropertyHelper;

public class ReferenceAttributesItem implements ChoiceItem {
	
	private String choiceLabel;
	private EReference choiceReference;
	private List<SectionPropertyHelper> structuralfeatures = new LinkedList<SectionPropertyHelper>();
	
	/**
	 * @param choiceLabel
	 * @param choiceReference
	 */
	public ReferenceAttributesItem(String choiceLabel, EReference choiceReference){
		this.choiceLabel = choiceLabel;
		this.choiceReference = choiceReference;
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
	public List<SectionPropertyHelper> getStructuralfeatures() {
		return structuralfeatures;
	}
	
	/**
	 * @param label
	 * @param structuralfeature
	 */
	public void addStructuralfeature(SectionPropertyHelper structuralfeature){
		structuralfeatures.add(structuralfeature);
	}

}
