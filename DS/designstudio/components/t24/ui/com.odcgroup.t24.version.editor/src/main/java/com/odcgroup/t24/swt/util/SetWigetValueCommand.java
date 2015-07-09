package com.odcgroup.t24.swt.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.odcgroup.t24.version.versionDSL.Version;

public class SetWigetValueCommand extends SetCommand{

	public SetWigetValueCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value) {
		super(domain, owner, feature, value);
		this.vResource = (Version) owner;
		newValue = value;
	}

	private EditingDomain editingdomain;
	private Version vResource;
	private Object newValue;

	
	
	@Override
	public void doExecute() {
		
	}
	
	@Override
	public boolean doCanExecute() {
		return true;
	}

}
