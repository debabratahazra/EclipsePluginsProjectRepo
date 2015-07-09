package com.odcgroup.workbench.el.generator;

import java.util.Set;

import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.compiler.ImportManager;
import org.eclipse.xtext.xbase.compiler.output.FakeTreeAppendable;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;

import ch.vorburger.el.generator.ELCompiler;

/**
 * Code generation for DS EL expressions.
 * 
 * @author Kai Kreuzer
 */
@SuppressWarnings("restriction")
public class DSELCompiler extends ELCompiler {

	// TODO MUST FIND OUT AND DOCUMENT WHY THIS IS DONE! 
	
	@Override
	public ITreeAppendable compile(XExpression obj, ITreeAppendable appendable, JvmTypeReference expectedReturnType, Set<JvmTypeReference> declaredExceptions) {
		internalToJavaStatement(obj, appendable, false);
		return appendable;
	}
	
	public String compile(XExpression expression, Class<?> expectedType) {
		FakeTreeAppendable appendable = new FakeTreeAppendable(new ImportManager(false)) {
			public String getName(Object key) {
				if(key instanceof JvmOperation) {
					JvmOperation op = (JvmOperation) key;
					return op.getSimpleName();
				}
				return super.getName(key);
			}
		};
		
		ITreeAppendable ap = null;
		if(appendable instanceof ITreeAppendable) {
			ap = (ITreeAppendable)appendable;
		}
		return compileAsJavaExpression(expression, ap, expectedType).toString();
	}
}