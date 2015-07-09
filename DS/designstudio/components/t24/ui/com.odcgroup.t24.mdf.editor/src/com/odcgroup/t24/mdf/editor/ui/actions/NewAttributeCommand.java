package com.odcgroup.t24.mdf.editor.ui.actions;

import java.util.ArrayList;

import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.workbench.core.IOfsProject;

public class NewAttributeCommand extends ChangeCommand {

	private MdfClass owner = null;
	private IOfsProject ofsProject = null;
	private String value = null;
	private DomainModelEditor editor = null;
	private static DomainRepository repo = null;

	/**
	 * @param owner 
	 * @param project 
	 * @param editor 
	 * @param notifiers
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NewAttributeCommand(MdfClass owner, IOfsProject project, DomainModelEditor editor) {
		super(new ArrayList(1));
		this.setLabel("Create New Attribute from Local Field");
		this.owner = owner;
		this.ofsProject = project;
		this.editor  = editor;
	}
	
	public DomainModelEditor getDomainModelEditor() {
		return this.editor;				
	}
	
	@SuppressWarnings("unchecked")
	protected void doExecute() {
		if(repo == null) {
			repo = DomainRepository.getInstance(ofsProject);
		}
		MdfDomainImpl domain = (MdfDomainImpl)repo.getDomain("LocalFieldsDefinition");
		MdfClass klass = domain.getClass("LocalFields");
		if(this.value != null && klass != null) {
			MdfProperty classProperty =  klass.getProperty(this.value);
			classProperty = setT24NameForProperty((MdfPropertyImpl) classProperty);
			if(classProperty instanceof MdfAttribute) {
				MdfAttributeImpl attribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute(); 
				attribute = (MdfAttributeImpl) classProperty;
				this.owner.getProperties().add(attribute);
			} else if (classProperty instanceof MdfAssociation) {
				MdfAssociationImpl association = (MdfAssociationImpl)MdfFactory.eINSTANCE.createMdfAssociation();
				association = (MdfAssociationImpl) classProperty;
				this.owner.getProperties().add(association);
			}
		}
	}

	private MdfProperty setT24NameForProperty(MdfPropertyImpl classProperty) {
		T24Aspect.setT24Name(classProperty, classProperty.getName());
		String attrName = classProperty.getName().replace(".", "_");
		if(attrName.contains("-1")) {
			attrName = attrName.replace("-1", "_1");
		}
		classProperty.setName(attrName);
		return classProperty;
	}

	public void setSelection(ISelection iSelection) {
		StructuredSelection selection = null;
		if(iSelection instanceof StructuredSelection) {
			selection = (StructuredSelection)iSelection;
			Object val = selection.getFirstElement();
			if(val != null && val instanceof MdfProperty) {
				this.value = ((MdfProperty)val).getName();
			}
		}
	}
		
}
