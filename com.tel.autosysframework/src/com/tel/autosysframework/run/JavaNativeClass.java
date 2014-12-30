package com.tel.autosysframework.run;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tel.autosysframework.internal.Refresh;
import com.tel.autosysframework.views.Configure;
import com.tel.autosysframework.views.GeneralConfigure;
import com.tel.autosysframework.workspace.ProjectInformation;



public class JavaNativeClass /*implements Runnable*/{
	static
	{
		try{
			System.loadLibrary("Autosys");
			System.loadLibrary("VLCD_DLL");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

//	public static File configfile;
	/*public JavaNativeClass(){
		String projectPath = new ProjectInformation().getProjectName(0);
		if(projectPath==null) return;
		configfile = new File(projectPath);
	}*/
	
	
//	Thread AutosysRunner;
	public native void jniFilePaths(String input1,String input2 ,String output,String config);
	public native void jniInstantiateModules();
	public native void jniVLCDSystem(String Configfilepath);
//	public native void jniPlotGrpah(String Outputsource, String PlotPath);

	/*public void run() {
		if(configfile.exists()) {
			//checking if test vector is generated
			if(GeneralConfigure.isGenerateTestVector() 
					&& ((!new File(GeneralConfigure.getInputpath1()).exists())
							|| (GeneralConfigure.isTwoChannel()	
									&& (!new File(GeneralConfigure.getInputpath2()).exists())))) {
				Activator.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_00XX: TestVector Not generated" , 2);
				return;
			}					
			jniFilePaths(GeneralConfigure.getInputpath1()
					, GeneralConfigure.getInputpath2()
					, GeneralConfigure.getOutputpath()
					, configfile.getAbsolutePath());
		}
		else {					
			Activator.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_003: Configuration File Not Found.", 2);
			return;
		}
		try {
			jniInstantiateModules();
		} catch (Exception e) {
			Activator.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_0XX: " +
						"Simulation taking indefinite time\n" +
						"simulation abortion failed", 2);
			return;
		}
		AutosysStop();
		
	}*/
	
	/*private void AutosysStop() {
		simulationdone();
	}
	*/
/*	*//**
	 * Opens the Output file in Eclipse Text Editor
	 *//*
	public static boolean showOutputFile() {

		String names = GeneralConfigure.getOutputpath();
		if(!new File(names).exists()){
			Activator.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_031: Simulated Output file not found.", 2);
			Activator.console.println(">>("+DateUtils.now()+")>>"+"Note: Simulation must have taken indefinite time",1);
			return false;
		}
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(names));							
		if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
			IWorkbenchPage page = null;
			try {
				page =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IDE.openEditorOnFileStore(page, fileStore);
			} catch (PartInitException e1) {							     
				e1.printStackTrace();
			} catch (NullPointerException e2) {
				e2.printStackTrace();
			}
		}
		return true;
	}
*/
	
	/**
	 * Displays Simulation Done.
	 *//*
	private void simulationdone() {

		configfile.delete();
		
		try {
			//Activate and focus the Console View.
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView
			("com.tel.autosysframework.configure", null, 1);
		} catch (PartInitException e2) {}
		new Configure().createOutputFileView(Configure.comp);
		
		if(!showOutputFile())
			return;

		Activator.console.println(">>("+DateUtils.now()+")>>"+"*** Simulation Completed Successfully ***", 3);

		//Open Configfile.ini file in editor.
		new Refresh().start();
//		AutosysRunner.stop();
	}
*/
/*	public boolean AutosysStart(){
		AutosysRunner = new Thread(this);
		try {
			AutosysRunner.start();
		} catch (Exception e) {
			return false;
		}
		return true;
	}*/
}
