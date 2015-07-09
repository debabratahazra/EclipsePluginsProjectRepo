package com.odcgroup.workbench.tap.validation.ui.internal;

import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.ui.IStartup;

import com.odcgroup.workbench.tap.validation.internal.provider.CheckConstraintProvider;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		for(IConstraintDescriptor descriptor : CheckConstraintProvider.getCheckConstraintDescriptors()) {
			try {
				ConstraintRegistry.getInstance().register(descriptor);
			} catch (ConstraintExistsException e) {
				// ignore
			}
		}
	}

}
