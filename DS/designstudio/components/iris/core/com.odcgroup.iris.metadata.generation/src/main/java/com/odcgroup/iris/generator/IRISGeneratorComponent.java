package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;

import com.google.inject.Injector;
import com.odcgroup.t24.enquiry.util.EMEntityModel;

/**
 * @author phanikumark
 *
 */
public class IRISGeneratorComponent extends AbstractWorkflowComponent {
	
	private List<GlobalVar> globalVars = new ArrayList<GlobalVar>();
	private Injector injector;
	private String outlet = null;
	
	/**
	 * @param globalVar
	 */
	public void addGlobalVar(GlobalVar globalVar) {
		globalVars.add(globalVar);
	}
	
	/**
	 * @param setup
	 */
	public void setRegister(ISetup setup) {
		injector = setup.createInjectorAndDoEMFRegistration();
	}
	
	/**
	 * @param outlet
	 */
	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}

	@Override
	public void checkConfiguration(Issues issues) {
		if (globalVars.isEmpty())
			throw new IllegalStateException("no 'globalVar' has been configured.");
		if (injector == null)
			throw new IllegalStateException("no Injector has been configured. Use 'provider' with an ISetup.");
		if (outlet == null)
			throw new IllegalStateException("no 'outlet' has been configured.");		
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {	
		IIRISGenerator generator = getCompiler();
		if (generator == null) {
			return;
		}
		IFileSystemAccess fsa = getConfiguredFileSystemAccess();
		String[] slots = ctx.getSlotNames();
		for (String slot : slots) {
			Object object = ctx.get(slot);
			if (object == null) {
				throw new IllegalStateException("Slot '"+slot+"' is empty!");
			}
			if (object instanceof EMEntityModel) {
				generator.doGenerate((EMEntityModel)object, fsa, getGlobalVars(ctx));
			}
		}
	}
	
	protected HashMap<String, Variable> getGlobalVars(WorkflowContext ctx) {
		final HashMap<String, Variable> result = new HashMap<String, Variable>();
		for (GlobalVar gv : globalVars) {
			result.put(gv.getName(), new Variable(gv.getName(), gv.getValue()));
		}
		return result;
	}
	
	/**
	 * @return
	 */
	protected IIRISGenerator getCompiler() {
		return injector.getInstance(IIRISGenerator.class);
	}

	/**
	 * @return
	 */
	protected IFileSystemAccess getConfiguredFileSystemAccess() {
		final JavaIoFileSystemAccess configuredFileSystemAccess = injector.getInstance(JavaIoFileSystemAccess.class);
		configuredFileSystemAccess.setOutputPath(IFileSystemAccess.DEFAULT_OUTPUT, outlet);
		return configuredFileSystemAccess;
	}
	
	public static class GlobalVar {
		
		private String name;
		private Object value;

		public void setName(String name) {
			this.name = name;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}
	}
	
	
	

}