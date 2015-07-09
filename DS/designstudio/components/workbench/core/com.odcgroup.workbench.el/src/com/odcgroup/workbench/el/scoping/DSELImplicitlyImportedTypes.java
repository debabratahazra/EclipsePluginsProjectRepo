package com.odcgroup.workbench.el.scoping;

import java.util.Collections;
import java.util.List;

import org.eclipse.xtext.xbase.lib.CollectionLiterals;

import ch.vorburger.el.scoping.batch.ELImplicitlyImportedTypes;

import com.odcgroup.workbench.el.runtime.BooleanExtensions;
import com.odcgroup.workbench.el.runtime.ComparableExtensions;
import com.odcgroup.workbench.el.runtime.DecimalExtensions;
import com.odcgroup.workbench.el.runtime.ObjectExtensions;
import com.odcgroup.workbench.el.runtime.StringExtensions;

public class DSELImplicitlyImportedTypes extends ELImplicitlyImportedTypes {

	@Override
	protected List<Class<?>> getStaticImportClasses() {
		return Collections.emptyList();
	}

	@Override
	protected List<Class<?>> getExtensionClasses() {
		return CollectionLiterals.<Class<?>> newArrayList(
				ObjectExtensions.class, 
				DecimalExtensions.class,
				ComparableExtensions.class, 
				BooleanExtensions.class, 
				StringExtensions.class
			);
	}

}
