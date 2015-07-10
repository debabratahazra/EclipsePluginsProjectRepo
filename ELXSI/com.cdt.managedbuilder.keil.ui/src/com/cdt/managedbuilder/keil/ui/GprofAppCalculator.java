
package com.cdt.managedbuilder.keil.ui;

import org.eclipse.cdt.managedbuilder.core.IOptionApplicability;

public class GprofAppCalculator extends ProfAppCalculator implements IOptionApplicability {
	protected String getOptionIdPattern() {
		return ".compiler.option.debugging.gprof";
	}
}
