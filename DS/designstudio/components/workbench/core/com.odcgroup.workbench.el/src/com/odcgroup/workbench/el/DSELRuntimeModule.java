/*
 * generated by Xtext
 */
package com.odcgroup.workbench.el;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedFeatures;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputer;

import ch.vorburger.el.engine.Expression;
import ch.vorburger.el.engine.ExpressionImpl;
import ch.vorburger.el.naming.ELQualifiedNameProvider;
import ch.vorburger.el.typesystem.computation.ELTypeComputer;

import com.odcgroup.workbench.el.generator.DSELCompiler;
import com.odcgroup.workbench.el.scoping.DSELImplicitlyImportedTypes;
import com.odcgroup.workbench.el.valueconverter.DSELValueConverterService;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
@SuppressWarnings("restriction")
public class DSELRuntimeModule extends com.odcgroup.workbench.el.AbstractDSELRuntimeModule {
	
	// @see (and keep-in-sync-with...) ch.vorburger.el.ELRuntimeModule
	
	public Class<? extends XbaseCompiler> bindXbaseCompiler() {
		return DSELCompiler.class;
	}

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return DSELValueConverterService.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return ELQualifiedNameProvider.class;
	}

	public Class<? extends ImplicitlyImportedFeatures> bindImplicitlyImportedTypes() {
		return DSELImplicitlyImportedTypes.class;
	}

	@Override
	public Class<? extends ITypeComputer> bindITypeComputer() {
		return ELTypeComputer.class;
	}

	public Class<? extends Expression> bindExpression() {
		return ExpressionImpl.class;
	}

}