package com.odcgroup.page.model.util;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.generator.trace.AbstractTraceRegion;
import org.eclipse.xtext.generator.trace.ILocationData;
import org.eclipse.xtext.generator.trace.TraceNotFoundException;
import org.eclipse.xtext.xbase.compiler.IAppendable;
import org.eclipse.xtext.xbase.compiler.output.ErrorTreeAppendable;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;

/**
 * Delegating ITreeAppendable
 *
 * @author Michael Vorburger
 */
@SuppressWarnings("restriction")
public class DelegatingTreeAppendable implements ITreeAppendable {

	protected final ITreeAppendable delegate;

	public DelegatingTreeAppendable(ITreeAppendable delegate) {
		super();
		this.delegate = delegate;
	}

	public AbstractTraceRegion getTraceRegion() throws TraceNotFoundException {
		return delegate.getTraceRegion();
	}

	public IAppendable append(LightweightTypeReference typeRef) {
		return delegate.append(typeRef);
	}

	public boolean isJava() {
		return delegate.isJava();
	}

	@SuppressWarnings("deprecation")
	public List<String> getImports() {
		return delegate.getImports();
	}

	public ITreeAppendable trace(EObject object, boolean useForDebugging) {
		return delegate.trace(object, useForDebugging);
	}

	public void openScope() {
		delegate.openScope();
	}

	public void openPseudoScope() {
		delegate.openPseudoScope();
	}

	public ITreeAppendable trace(EObject object) {
		return delegate.trace(object);
	}

	public String declareVariable(Object key, String proposedName) {
		return delegate.declareVariable(key, proposedName);
	}

	public ITreeAppendable trace(Iterable<? extends EObject> objects) {
		return delegate.trace(objects);
	}

	public String declareSyntheticVariable(Object key, String proposedName) {
		return delegate.declareSyntheticVariable(key, proposedName);
	}

	public ITreeAppendable trace(EObject object, EStructuralFeature feature, int indexInList) {
		return delegate.trace(object, feature, indexInList);
	}

	public String getName(Object key) {
		return delegate.getName(key);
	}

	public boolean hasName(Object key) {
		return delegate.hasName(key);
	}

	public Object getObject(String name) {
		return delegate.getObject(name);
	}

	public ITreeAppendable trace(ILocationData location) {
		return delegate.trace(location);
	}

	public boolean hasObject(String name) {
		return delegate.hasObject(name);
	}

	public ITreeAppendable trace(ILocationData location, boolean useForDebugging) {
		return delegate.trace(location, useForDebugging);
	}

	public void closeScope() {
		delegate.closeScope();
	}

	public int length() {
		return delegate.length();
	}

	public String toString() {
		return delegate.toString();
	}

	public String getContent() {
		return delegate.getContent();
	}

	public ErrorTreeAppendable errorChild(EObject context) {
		return delegate.errorChild(context);
	}

	public ITreeAppendable append(JvmType type) {
		return delegate.append(type);
	}

	public ITreeAppendable append(CharSequence content) {
		return delegate.append(content);
	}

	public ITreeAppendable decreaseIndentation() {
		return delegate.decreaseIndentation();
	}

	public ITreeAppendable increaseIndentation() {
		return delegate.increaseIndentation();
	}

	public ITreeAppendable newLine() {
		return delegate.newLine();
	}

	public String removeName(Object key) throws IllegalStateException {
		return delegate.removeName(key);
	}

	public ITreeAppendable append(Class<?> type) {
		return delegate.append(type);
	}
	
}
