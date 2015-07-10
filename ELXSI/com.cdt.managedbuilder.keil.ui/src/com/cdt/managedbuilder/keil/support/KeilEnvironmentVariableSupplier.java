package com.cdt.managedbuilder.keil.support;

import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.envvar.IBuildEnvironmentVariable;
import org.eclipse.cdt.managedbuilder.envvar.IConfigurationEnvironmentVariableSupplier;
import org.eclipse.cdt.managedbuilder.envvar.IEnvironmentVariableProvider;
import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

public class KeilEnvironmentVariableSupplier implements
		IConfigurationEnvironmentVariableSupplier {

	
	private static class KeilBuildEnvironmentVariable implements IBuildEnvironmentVariable {
		
		private final String name;
		private final String value;
		private final int operation;
		
		public KeilBuildEnvironmentVariable(String name, String value, int operation) {
			this.name = name;
			this.value = value;
			this.operation = operation;
		}
		
		public String getName() {
			return name;
		}
		
		public String getValue() {
			return value;
		}
		
		public int getOperation() {
			return operation;
		}
		
		public String getDelimiter() {
			return ";";
		}
	}
	
	private IBuildEnvironmentVariable path;
	
	public static IPath getBinDir() {
		
		IPath subPath = new Path("Keil\\C51\\BIN");
		// 1. Try the Keil directory in the platform install directory.
		IPath installPath = new Path(Platform.getInstallLocation().getURL().getFile());
		IPath binPath = installPath.append(subPath);
		if (binPath.toFile().isDirectory())
			return binPath;
		
		// 2. Try the directory above the install directory.
		binPath = installPath.removeLastSegments(1).append(subPath);
		if (binPath.toFile().isDirectory())
			return binPath;
		
		// 3. Try looking if the Keil installer in RegEdit or not......
		String keilPath = WindowsRegistry.getRegistry().getLocalMachineValue(
					"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Keil uVision3",
					"InstallLocation");
		if (keilPath != null) {
			binPath = new Path(keilPath).append("C51\\BIN");
			if (binPath.toFile().isDirectory())
				return binPath;
		}
		
		// 4. Try the default Keil install directory.
		binPath = new Path("C:\\Keil\\C51\\BIN");
		if (binPath.toFile().isDirectory())
			return binPath;
		
		// If Not Found, return null
		return null;
	}	
	
	public static IPath getMsysBinDir() {
		// Just look in the install location MSys parent directory
		IPath installPath = new Path(Platform.getInstallLocation().getURL().getFile()).removeLastSegments(1);
		IPath msysBinPath = installPath.append("msys\\bin");
		return msysBinPath.toFile().isDirectory() ? msysBinPath : null;
	}	
	
	public KeilEnvironmentVariableSupplier() {
		IPath binPath = getBinDir();
		if (binPath != null) {
			String pathStr = binPath.toOSString();
			IPath msysBinPath = getMsysBinDir();
			if (msysBinPath != null)
				pathStr += ';' + msysBinPath.toOSString();
			
			path = new KeilBuildEnvironmentVariable("PATH", pathStr, IBuildEnvironmentVariable.ENVVAR_PREPEND);
		}
	}
	
	



	public IBuildEnvironmentVariable getVariable(String variableName,
			IConfiguration configuration, IEnvironmentVariableProvider provider) {
		if (path != null && variableName.equals(path.getName()))
			return path;
		else
			return null;
	}

	public IBuildEnvironmentVariable[] getVariables(
			IConfiguration configuration, IEnvironmentVariableProvider provider) {
		return path != null
		? new IBuildEnvironmentVariable[] { path }
		: new IBuildEnvironmentVariable[0];
	}

}
