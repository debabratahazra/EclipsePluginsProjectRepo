package com.tel.autosysframework.run;



import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tel.autosysframework.DateUtils;
import com.tel.autosysframework.configureclasses.LTEParameters;
import com.tel.autosysframework.editpart.AutoModelEditPart;
import com.tel.autosysframework.editpart.WireEditPart;
import com.tel.autosysframework.internal.Refresh;
import com.tel.autosysframework.internal.VLCD_SaveConfig;
import com.tel.autosysframework.intro.AutosysApplication;
import com.tel.autosysframework.model.CRC;
import com.tel.autosysframework.model.CSB;
import com.tel.autosysframework.model.ChannelCoding;
import com.tel.autosysframework.model.CyclicPrefix;
import com.tel.autosysframework.model.InputPort;
import com.tel.autosysframework.model.LayerMapper;
import com.tel.autosysframework.model.Modulator;
import com.tel.autosysframework.model.OFDM;
import com.tel.autosysframework.model.OutputPort;
import com.tel.autosysframework.model.Precoding;
import com.tel.autosysframework.model.REM;
import com.tel.autosysframework.model.RateMatching;
import com.tel.autosysframework.model.Scrambler;
import com.tel.autosysframework.views.Configure;
import com.tel.autosysframework.views.GeneralConfigure;
import com.tel.autosysframework.wizard.AutosysFrameworkWizardPage;
import com.tel.autosysframework.workspace.ProjectInformation;

public class RunAutosysProject extends JavaNativeClass implements IWorkbenchWindowActionDelegate {

	public static List childs = null;	
	public static File configfile;
	public static File modulesfile;

	private FileOutputStream configFile = null;
	private DataOutputStream writeConfigFile = null;
	private File file;	
	private boolean inputPortRegistered ;
	private boolean outputPortRegistered ;
	private int hasOtherModules = 0;
	private static int count = 0;
	private int moduleCountDown;
	private boolean FirstRegistered = false;
	private String modulestack;

	//private Runnable AutosysRunner = null;
	private static boolean simulationSuccessful = false;

	private FileInputStream in = null;
	private ObjectInputStream ois = null;
	private LTEParameters par_obj = null;

	private String OS_Separator = "";

	public void init(IWorkbenchWindow window) {
		
	}

	/**
	 * Run the Autosys Simulation
	 */
	public void run(IAction action) {
		
		AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"Simulation Started...",4);
		
		if(AutosysFrameworkWizardPage.designselection == 0){

			BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {

				public void run() {
					FirstRegistered = false;

					if(Platform.getOS().equalsIgnoreCase("win32"))
						setOS_Separator("\\");
					else if(Platform.getOS().equalsIgnoreCase("linux"))
						setOS_Separator("/");

					PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getActivePage().saveAllEditors(true);

					if(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
							getActivePage().getActiveEditor().isDirty()){
						AutosysApplication.console.println("Simulation Cancelled by user", 1);
						return;
					}

					try {
						//Activate and focus the Console View.
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView
						("org.eclipse.ui.console.ConsoleView", null, 1);
					} catch (PartInitException e2) {}

					//Clear the console for every new run.
					//AutosysApplication.console.clear();

					//Check the Project state.
					if(new ProjectInformation().getProjectName(4)==null){
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_033: Project is Closed.", 2);
						return;
					}

					//Autosys File not created/open Error
					if(childs==null){
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_001: Autosys File not Opened.", 2);
						return;
					}
					//No Module is added in editor error.
					if(childs.isEmpty()){
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_002: File is empty.", 2);
						return;
					}
					//Error in Autosys I/O Setting
					if((GeneralConfigure.getInputpath1().equalsIgnoreCase("")
							|| GeneralConfigure.getOutputpath().equalsIgnoreCase(""))
							|| (GeneralConfigure.isTwoChannel() 
									&& GeneralConfigure.getInputpath2().equalsIgnoreCase(""))){			
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_003: Autosys I/O Files Setting Error.", 2);
						return;	
					}

					//Modules trace variables
					hasOtherModules = 0;
					inputPortRegistered  = false;
					outputPortRegistered = false;

					//Check all the models in editor
					Object obj = setModuleChecks();	
					if(obj == null) return;

					//Get the absolute config file location path
					String projectPath = new ProjectInformation().getProjectName(0);
					if(projectPath==null) return;					//Project is closed.

					configfile = new File(projectPath);		

					configfile.setWritable(true);
					file = new File(new ProjectInformation().getProjectName(4)+getOS_Separator()+".settings");
					if(!file.exists())
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_00XI: Configuration Parameters not set", 2);

					try {
						configFile = new FileOutputStream(configfile);
						writeConfigFile = new DataOutputStream(configFile);
						in = new FileInputStream(file);
						ois = new ObjectInputStream(in);
						par_obj = (LTEParameters) ois.readObject();
					} catch (FileNotFoundException e2) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_00XI: Configuration Parameters not set", 2);
						e2.printStackTrace();
						return;
					} catch (IOException e) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_00XI: Configuration Parameters not set", 2);
						e.printStackTrace();
						return;
					} catch (ClassNotFoundException e) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_00XI: Configuration Parameters not set", 2);
						e.printStackTrace();
						return;
					}

					/*
					 * Check the connection between Input port and other module
					 * Check Design Flow validation and
					 * Check the LTE logic Design validation. 
					 */		
					obj = getLTE_DesignFlow(childs);
					if(obj==null) return;

					//Re-initialize
					hasOtherModules=0;

					if(!childs.isEmpty()) {	

						try {
							writeConfigFile.writeBytes(par_obj.getChannelType()+"\n");
							writeConfigFile.writeBytes(par_obj.getTransportBlock()+"\n");
							writeConfigFile.writeBytes(par_obj.getBlocksize1()+"\n");
							if(Boolean.parseBoolean(par_obj.getTwoblocks()))
								writeConfigFile.writeBytes(par_obj.getBlocksize2()+"\n");
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						Iterator itr = childs.iterator();
						while(itr.hasNext()){

							AutoModelEditPart amep = (AutoModelEditPart)itr.next();

							if(!(amep.getModel() instanceof InputPort)
									&& !(amep.getModel() instanceof OutputPort)){	
								hasOtherModules++;
								if(amep.getModel() instanceof RateMatching) {
									try {
										writeConfigFile.writeBytes(par_obj.getNir()+"\n");
										writeConfigFile.writeBytes(par_obj.getQm()+"\n");
										writeConfigFile.writeBytes(par_obj.getNl()+"\n");
										writeConfigFile.writeBytes(par_obj.getRv_idx()+"\n");
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if(amep.getModel() instanceof Scrambler){
									try {
										writeConfigFile.writeBytes(par_obj.getnRNTI()+"\n");
										writeConfigFile.writeBytes(par_obj.getNs()+"\n");
										writeConfigFile.writeBytes(par_obj.getN_cell_id()+"\n");
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if(amep.getModel() instanceof Modulator) {
									try {
										writeConfigFile.writeBytes(par_obj.getMod_type()+"\n");
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if(amep.getModel() instanceof LayerMapper) {
									try {
										writeConfigFile.writeBytes(par_obj.getLm_AntennaPorts()+"\n");
										writeConfigFile.writeBytes(par_obj.getLm_NumberofLayers()+"\n");
										writeConfigFile.writeBytes(par_obj.getLm_CodeBookIndex()+"\n");
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if(amep.getModel() instanceof REM){
									try {
										writeConfigFile.writeBytes(par_obj.getConfigurationType()+"\n");
										writeConfigFile.writeBytes(par_obj.getBandwidth()+"\n");
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if(amep.getModel() instanceof CyclicPrefix) {

									int i = (int)Integer.parseInt(par_obj.getCPI_IFFT_Points().substring(par_obj.getCPI_IFFT_Points().indexOf(":")+2));
									String IFFTPoints = par_obj.getCPI_IFFT_Points().substring(0,par_obj.getCPI_IFFT_Points().indexOf(":")+1)+" "+String.valueOf(Math.pow(2, i));
									try {
										writeConfigFile.writeBytes(par_obj.getCP_Type()+"\n");
										writeConfigFile.writeBytes(IFFTPoints+"\n");
										writeConfigFile.writeBytes(par_obj.getCPI_Layer_nos()+"\n");
										writeConfigFile.writeBytes(par_obj.getL());
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if(amep.getModel() instanceof OFDM) {
									int i = (int)Integer.parseInt(par_obj.getOFDM_IFFT_Points().substring(par_obj.getOFDM_IFFT_Points().indexOf(":")+2));
									String IFFTPoints = par_obj.getOFDM_IFFT_Points().substring(0,par_obj.getOFDM_IFFT_Points().indexOf(":")+1)+" "+String.valueOf(Math.pow(2, i));
									try {
										writeConfigFile.writeBytes(IFFTPoints+"\n");
										writeConfigFile.writeBytes(par_obj.getOFDM_Layer_nos()+"\n");
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if(amep.getModel() instanceof Precoding) {
									try {
										writeConfigFile.writeBytes(par_obj.getPr_AntennaPorts()+"\n");
										writeConfigFile.writeBytes(par_obj.getPr_CodeBookIndex()+"\n");
										writeConfigFile.writeBytes(par_obj.getPr_NumberofLayers()+"\n");
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} else if(amep.getModel() instanceof InputPort) {
								inputPortRegistered = true;
							} else if(amep.getModel() instanceof OutputPort) {
								outputPortRegistered = true;
							}

						}

						try {				
							in.close();
							ois.close();
							par_obj = null;
							writeConfigFile.close();
							configFile.close();
							//						configfile.setWritable(false);
							//						configfile.setReadOnly();
							//new Refresh().start();
						} catch (Exception e) {
							e.printStackTrace();
						}
						/*if(new JavaNativeClass().AutosysStart());
						//AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"Simulation Started...",4);				
					else {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_XXX: Simulation taking Indefinite time,\n" +
								" Simulation aborting abruptly", 2);
						return;
					}*/
						//			BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new JavaNativeClass());

						{
							if(configfile.exists()) {
								//checking if test vector is generated
								if(GeneralConfigure.isGenerateTestVector() 
										&& ((!new File(GeneralConfigure.getInputpath1()).exists())
												/*|| (GeneralConfigure.isTwoChannel()	
													&& (!new File(GeneralConfigure.getInputpath2()).exists()))*/)) {
									AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_00XX: TestVector Not generated" , 2);
									return;
								}					
								jniFilePaths(GeneralConfigure.getInputpath1()
										, GeneralConfigure.getInputpath2()
										, GeneralConfigure.getOutputpath()
										, configfile.getAbsolutePath());
							}
							else {					
								AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_003: Configuration File Not Found.", 2);
								return;
							}
							try {
								jniInstantiateModules();
							} catch (Exception e) {
								AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_0XX: " +
										"Simulation taking indefinite time\n" +
										"simulation abortion failed", 2);
								return;
							}
							simulationdone();
						}
					}
					else {
						//Invalid design. [already checked it previously]
						return;
					}
				}
			});
		} else if(AutosysFrameworkWizardPage.designselection == 1){
			createVLCD_ConfigFile();
		}

		count++;	
		dispose();
		setSimulationSuccessful(true);		
		return;
	}


	private void createVLCD_ConfigFile() {

		BufferedWriter writer = null;
		String projectPath = new ProjectInformation().getProjectName(0);
		if(projectPath==null) return;				//Project is closed.
		configfile = new File(projectPath);		
	    try {
			writer = new BufferedWriter(new FileWriter(configfile));
			writer.write("InputFilePath " + GeneralConfigure.inputVLCDFilepath);
		    writer.newLine();
		    writer.write("OutputFilePath " + GeneralConfigure.outputVLCDFilepath);
		    writer.newLine();
		    writer.write("NUM_YETA " + VLCD_SaveConfig.getMap(1));
		    writer.newLine();
		    writer.write("Threshold " + VLCD_SaveConfig.getMap(2));
		    writer.newLine();
		    writer.write("NO_OBJ_LABEL " + VLCD_SaveConfig.getMap(3));
		    writer.newLine();
		    writer.write("MAX_OBJ " + VLCD_SaveConfig.getMap(4));
		    writer.newLine();
		    writer.write("MAX_MB_OBJ " + VLCD_SaveConfig.getMap(5));
		    writer.newLine();
		    writer.write("MAX_FOREGR_PIX_MB " + VLCD_SaveConfig.getMap(6));
		    writer.newLine();
		    writer.write("TOTAL_FRAMES " + VLCD_SaveConfig.getMap(7));
		    writer.newLine();
		    writer.write("FRAME_WIDTH " + "352");
		    writer.newLine();
		    writer.write("FRAME_HEIGHT " + "288");
		    writer.newLine();
		    writer.write("START_ROW " + VLCD_SaveConfig.getMap(8));
		    writer.newLine();
		    writer.write("START_COL " + VLCD_SaveConfig.getMap(9));
		    writer.newLine();
		    writer.write("END_ROW " + VLCD_SaveConfig.getMap(10));
		    writer.newLine();
		    writer.write("END_COL " + VLCD_SaveConfig.getMap(11));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.flush();
				writer.close();
				new Refresh().start();
			} catch (IOException e) {
			}
		}
		
		jniVLCDSystem(configfile.getAbsolutePath());

		AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"*** Simulation Completed Successfully ***", 3);
		//TODO
	}


	
	
	/**
	 * Opens the Output file in Eclipse Text Editor
	 */
	public static boolean showOutputFile() {

		String names = GeneralConfigure.getOutputpath();
		if(!new File(names).exists()){
			AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_031: Simulated Output file not found.", 2);
			AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"Note: Simulation must have taken indefinite time",1);
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


	/**
	 * Displays Simulation Done.
	 */
	private void simulationdone() {

		configfile.delete();

		/*if(!showOutputFile())
			return;*/

		AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"*** Simulation Completed Successfully ***", 3);

		//Open Configfile.ini file in editor.
		new Refresh().start();

		try {
			//Activate and focus the Console View.
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView
			("com.tel.autosysframework.configure", null, 1);
		} catch (PartInitException e2) {}
		new Configure().createOutputFileView(Configure.comp);
		//		AutosysRunner.stop();
	}


	/**
	 * Checks the I/O ports and Module(s) connections
	 */
	private Object setModuleChecks() {
		Iterator itr = childs.iterator();		
		while(itr.hasNext()){
			AutoModelEditPart amep = (AutoModelEditPart)itr.next();

			if(!(amep.getModel() instanceof InputPort) && !(amep.getModel() instanceof OutputPort)){
				hasOtherModules++;
			} else if(amep.getModel() instanceof InputPort) {
				inputPortRegistered = true;
			} else if(amep.getModel() instanceof OutputPort) {
				outputPortRegistered = true;
			}

		}		

		//Error: Input port is not present in model.
		if(!inputPortRegistered){
			AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_005: Input Port not found.", 2);
			return null;
		}

		//Error: Output port is not present in model.
		if(!outputPortRegistered){
			AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_006: Output Port not found.", 2);
			return null;
		}

		//Model(s) not found i.e. only Input & Output ports are added.
		if(hasOtherModules==0){
			AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_007: Model(s) not found.", 2);
			return null;
		}		

		return ((Object)new String("Valid"));
	}


	/**
	 * Check the LTE Design Validation	
	 * @param LIST childs
	 * @return Object
	 */
	private Object getLTE_DesignFlow(List childs2) {
		Iterator itr = childs.iterator();
		while(itr.hasNext()) {
			AutoModelEditPart amep = (AutoModelEditPart)itr.next();
			List con;
			if(amep.getModel() instanceof InputPort) {

				// Get Source connections and check for null
				con = amep.getSourceConnections();
				if(con.isEmpty()){						
					//Input Port is not connected to the module Error.
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_004: Input Port not connected.", 2);
					return null;
				}					
				if(!designChainValidation(amep)) {
					return null;
				}
				if(!designValidation(amep)) {
					try {
						writeConfigFile.close();
						configFile.close();
						configfile.delete();
						new Refresh().start();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}					
			}		
		}		
		return ((Object)new String("Valid"));
	}


	public static void setSimulationSuccessful(boolean simulationSuccessful) {
		RunAutosysProject.simulationSuccessful = simulationSuccessful;
	}


	public static boolean isSimulationSuccessful() {
		return simulationSuccessful;
	}


	/**
	 * Open Configfile.ini file from wrokspace.
	 */
	/*private void openConfigFile() {

		String configFile = new ProjectInformation().getProjectName(1);
		if(configFile==null) return;			//Project is closed
		new Refresh().start();	
		IPath iPath = new Path(configFile);
		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(iPath);	
		new Refresh().start();	
		try {			
			new Refresh().start();	
			IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getActivePage(), 
					iFile, 	"com.tel.autosysframework.editor.ini");							
		} catch (PartInitException e) {}
		catch (Exception e) {}
	}*/





	/**
	 * Validates the Design of LTE
	 * @param AutosysModelEditpart
	 * @return
	 */
	private boolean designValidation(AutoModelEditPart amep) {

		WireEditPart wep;
		List con = amep.getSourceConnections();
		Iterator itr = con.iterator();
		wep = (WireEditPart) itr.next();

		int modulecount = hasOtherModules;
		while(!(wep.getTarget().getModel() instanceof OutputPort)/* && (hasOtherModules > 1)*/) {
			modulesregistered();
			amep = (AutoModelEditPart) wep.getTarget();

			if(wep.getTarget().getModel() instanceof CRC) {				
				if(modulecount > 0) {
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("CRC "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("CRC "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}					

					if(!((par_obj.getChannelType().substring(par_obj.getChannelType()
							.indexOf(":")+2).trim()).equalsIgnoreCase("Broadcast_Channel"))
							&& !(wep.getTarget().getModel() instanceof CSB)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					} else if(((par_obj.getChannelType().substring(par_obj.getChannelType()
							.indexOf(":")+2).trim()).equalsIgnoreCase("Broadcast_Channel"))
							&& !(wep.getTarget().getModel() instanceof ChannelCoding)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						AutosysApplication.console.println("\t>> 'Channel Coder must follow CRC for Broadcast Channel'", 1);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof CSB) {				
				if(modulecount > 0) {	
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("CSB "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("CSB "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}					
					if(!(wep.getTarget().getModel() instanceof ChannelCoding)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof ChannelCoding) {				
				if(modulecount > 0) {	
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("ChannelCoding "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("ChannelCoding "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof RateMatching)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof RateMatching) {				
				if(modulecount > 0) {
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("RateMatching "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("RateMatching "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof Scrambler)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof Scrambler) {				
				if(modulecount > 0) {
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("Scrambler "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("Scrambler "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof Modulator)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof Modulator) {	
				if(modulecount > 0) {
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("Modulator "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("Modulator "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();						
					}
					if(!(wep.getTarget().getModel() instanceof LayerMapper)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof LayerMapper) {				
				if(modulecount > 0) {
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("LayerMapper "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("LayerMapper "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof Precoding)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof Precoding) {				
				if(modulecount > 0) {	
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("Precoding "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("Precoding "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof REM)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof REM) {				
				if(modulecount > 0) {
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("REM "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("REM "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof OFDM)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
					modulecount--;
				}
			}
			else if(wep.getTarget().getModel() instanceof OFDM) {				
				if(modulecount > 0) {	
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("OFDM "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("OFDM "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof CyclicPrefix)
							&& !((modulecount == 1) && (wep.getTarget().getModel() instanceof OutputPort))) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
				}
				modulecount--;
			}
			else if(wep.getTarget().getModel() instanceof CyclicPrefix) {				
				if(modulecount > 0) {	
					con = amep.getSourceConnections();
					itr = con.iterator();
					wep = (WireEditPart) itr.next();
					try {
						if(wep.getTarget().getModel() instanceof OutputPort 
								&& (hasOtherModules != 1))
							writeConfigFile.writeBytes("CyclicPrefix "+"Last"+"\n\n");
						else
							writeConfigFile.writeBytes("CyclicPrefix "+modulestack);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(!(wep.getTarget().getModel() instanceof OutputPort)) {
						AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_027: Invalid LTE Design: #07", 2);
						return false;
					}
				}
				modulecount--;
			}

		}		
		return true;		
	}


	/**
	 * Design chain connection validation.
	 * @param amep
	 * @return
	 */
	private boolean designChainValidation(AutoModelEditPart amep) {		
		WireEditPart wep;
		Iterator itr;
		moduleCountDown = hasOtherModules + 1;
		while(!(amep.getModel() instanceof OutputPort)){
			moduleCountDown--;
			List con = amep.getSourceConnections();
			if(con.isEmpty()){						
				if(amep.getModel().toString().contains("CRC")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of CRC is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("CSB")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of CSB is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("ChannelCoding")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of Channel Coding is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("RateMatching")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of Rate Matching is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("Scrambler")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of Scrambler is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("Modulator")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of Modulator is opened.", 2);
					return false;					
				}else if(amep.getModel().toString().contains("LayerMapper")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of Layer Mapper is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("Precoding")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of Precoding is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("REM")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of REM is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("OFDM")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of OFDM is opened.", 2);
					return false;
				}else if(amep.getModel().toString().contains("CyclicPrefix")){
					AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_013: Output port of Cyclic Prefix is opened.", 2);
					return false;
				}
			}
			itr = con.iterator();
			wep = (WireEditPart) itr.next();
			amep = (AutoModelEditPart) wep.getTarget();
		}
		if((amep.getModel() instanceof OutputPort) && moduleCountDown != 0 && moduleCountDown == 1) {
			AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_019: Module not connected.", 2);
			return false;
		}else if((amep.getModel() instanceof OutputPort) && moduleCountDown != 0 && moduleCountDown > 1) {
			AutosysApplication.console.println(">>("+DateUtils.now()+")>>"+"#Error : E_GUI_020: Modules not connected.", 2);
			return false;
		}		
		return true;			
	}	


	public void selectionChanged(IAction action, ISelection selection) {

		/*IStructuredSelection sSelection = (IStructuredSelection) selection;
		Object[] selectedObjects = sSelection.toArray();
		for(int i =0; i<selectedObjects.length; i++) {
			if(selectedObjects[i] instanceof IAdaptable) {
				Object adaptableObject = ((IAdaptable)selectedObjects[i]).getAdapter(IResource.class);
				if(adaptableObject instanceof IProject) {				
					action.setEnabled(true);
					break;
				}
				else { 
					action.setEnabled(false);
				}
			}
		}*/
	}

	public void dispose() {
		configfile = null;
		file = null;
	}

	private void modulesregistered() {
		if(hasOtherModules == 1) {
			modulestack = "Alone\n\n";
			return;
		}
		if(isFirstRegistered()) {			
			modulestack = "Intermediate\n";
		} else {
			FirstRegistered = true;
			modulestack = "First\n";
		}
	}

	private boolean isFirstRegistered() {
		return FirstRegistered;
	}


	public void setOS_Separator(String oS_Separator) {
		OS_Separator = oS_Separator;
	}


	public String getOS_Separator() {
		return OS_Separator;
	}

}
