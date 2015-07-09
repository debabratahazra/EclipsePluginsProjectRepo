package com.odcgroup.workbench.el.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.util.TypeReferences;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.xbase.lib.Pair;

import ch.vorburger.el.engine.ExpressionContext;

/**
 * The "Context" for DS EL expressions.
 * 
 * This class is intentionally kept free of "implementation details" like
 * JvmType etc. both so that it's easy to use for consuming code as well as
 * (more importantly) so that the implementation, not the caller, can decide in
 * which ResourceSet to look stuff up in.
 * 
 * @author Michael Vorburger
 */
@SuppressWarnings("restriction")
public class DSELContext {

	private static final URI dsexURI = URI.createURI("DSELContext.dsex");
	private static final IResourceServiceProvider rsp = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(dsexURI);
	private static final TypeReferences typeReferences = rsp.get(TypeReferences.class); 

	private List<Pair<String, String>> vars = new ArrayList<Pair<String, String>>(3);
	private Class<?> type = Boolean.TYPE;
	
	public DSELContext add(String variableName, String mdfTypeName) {
		vars.add(Pair.of(variableName, mdfTypeName));
		return this;
	}

	public List<Pair<String, String>> getVariables() {
		return Collections.unmodifiableList(vars);
	}
	
	public void setType(Class<?> type) {
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}

	public ExpressionContext getExpressionContext(Notifier context) {
		ExpressionContext expressionContext = new ExpressionContext(context);
		expressionContext.setType(typeReferences.getTypeForName(type, context));

		for (Pair<String, String> var : this.getVariables()) {
			String variableName = var.getKey();
			String mdfTypeName = var.getValue().replace(':', '.');
			JvmTypeReference jvmType = typeReferences.getTypeForName(mdfTypeName, context);
			expressionContext.addVariable(variableName, jvmType);
		}

		return expressionContext;
	}
	
}
