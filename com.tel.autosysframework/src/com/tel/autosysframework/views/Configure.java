package com.tel.autosysframework.views;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.EditPart;

import com.tel.autosysframework.internal.VLCD_SaveConfig;
import com.tel.autosysframework.intro.AutosysApplication;
import com.tel.autosysframework.configureclasses.LTEParameters;
import com.tel.autosysframework.editpart.AutoModelEditPart;
import com.tel.autosysframework.editpart.AutosysDiagramEditPart;
import com.tel.autosysframework.model.CRC;
import com.tel.autosysframework.model.CSB;
import com.tel.autosysframework.model.ChannelCoding;
import com.tel.autosysframework.model.CyclicPrefix;
import com.tel.autosysframework.model.InputPort;
import com.tel.autosysframework.model.LayerMapper;
import com.tel.autosysframework.model.Modulator;
import com.tel.autosysframework.model.MonitorArea;
import com.tel.autosysframework.model.OFDM;
import com.tel.autosysframework.model.OutputPort;
import com.tel.autosysframework.model.Precoding;
import com.tel.autosysframework.model.REM;
import com.tel.autosysframework.model.RateMatching;
import com.tel.autosysframework.model.Scrambler;
import com.tel.autosysframework.model.VideoAnalyser;
import com.tel.autosysframework.model.VideoDisplay;
import com.tel.autosysframework.run.RunAutosysProject;
import com.tel.autosysframework.wizard.AutosysFrameworkWizardPage;
import com.tel.autosysframework.workspace.ProjectInformation;

/**
 * Configures all the module that is selected in Container
 * EditPart.
 * @version 1.0
 */


public class Configure extends ViewPart implements Serializable{


	private static final long serialVersionUID = 1L;
	private static File file;
	private FileOutputStream fout = null;
	private FileInputStream fin = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private LTEParameters par_obj = null;


	private GridLayout gridLayout = null;
	private static final int ZERO = 0;

	public static boolean flag = true;
	public static Composite comp = null;

	private Label setTipMsg = null;

	//************widgets for Modulator*************/
	private Label setModulationTypeLabel = null;
	private Combo Mod_typeCombo = null;
	private Button setSaveButton = null;
	//**********************************************/

	//************widgets for LayerMapper***********/
	private Label AntennaPortslabel = null;
	private Combo Portscombo = null;
	private Label NumberofLayers = null;
	private Spinner NumberOfLayers = null;
	private Label NumberofCodewords = null;
	private Scale CodewordsScale = null;
	private Label Configuration = null;
	private Button LMSaveButton = null;
	private Label scaleArea = null;
	//**********************************************/

	//************widgets for CRC*******************/
	private Label CRCHeadingLabel = null;
	//**********************************************/

	//************widgets for CSB*******************/
	private Label CSBHeadingLabel = null;
	//**********************************************/

	//*********widgets for ChannelCoding************/
	private Label CCHeadingLabel = null;
	//**********************************************/

	//*********widgets for RateMatching*************/
	private Label Nirlabel = null;
	private Text Nirtext = null;
	private Label Qmlabel = null;
	private Composite Qmgroup = null;
	private Combo Qmcombo = null;
	private Label Qmhelplabel1 = null;
	private Label Qmhelplabel2 = null;
	//	private Label Qmhelplabel3 = null;
	private Label Nllabel = null;
	private Composite Nlgroup = null;
	private Combo Nlcombo = null;
	private Label Nlhelplabel1 = null;
	private Label Rv_idxlabel = null;
	private Composite Rv_idxgroup = null;
	private Combo Rv_idxcombo = null;
	private Label Rv_idxhelplabel = null;
	private Label RMheadinglabel = null;
	private Button RMSavebutton = null;
	private Label RMerrorlabel = null;
	private static boolean RM_Properties = false;
	//**********************************************/

	//*********widgets for Scrambler****************/
	private Label nRNTIlabel = null;
	private Composite nRNTIgroup = null;
	private Text nRNTItext = null;
	private Label nRNTIhelplabel = null;
	private Label nslabel = null;
	private Composite nsgroup = null;
	private Text nstext = null;
	private Label nshelplabel = null;
	private Label N_cell_id_label = null;
	private Composite N_Cell_id_group = null;
	private Text N_cell_id_text = null;
	private Label N_Cell_id_help_label = null;
	private Label ScramHeadinglabel = null;
	private Button ScramSavebutton = null;
	private Label Scramerrorlabel = null;
	private VerifyListener nRNTIlistener ;
	private VerifyListener nslistener ;
	private VerifyListener N_cell_id_listener ;
	//**********************************************/

	//*********widgets for Precoding****************/
	private Label prAntennaPortslabel = null;
	private Combo prPortscombo = null;
	private Label prNumberofLayers = null;
	private Spinner prNumberOfLayers = null;
	private Label prCodeBookIndex = null;
	private Spinner prCodeBookScale = null;
	private Button prSaveButton = null;
	//**********************************************/

	//***************widgets for REM****************/
	private Label Configurationlabel = null;
	private Combo REM_CP_Typecombo = null;
	private Label Bnadwidthlabel = null;
	private Composite Buttoncomposite = null;
	private Button normalButton = null;
	private Button ExtendedButton = null;
	private Label REMHeadinglabel = null;
	private Button REMSavebutton = null;
	private Label REMerrorlabel = null;
	private static boolean REM_Properties = false;
	//**********************************************/

	//**************widgets for OFDM****************/
	private Label Titlelabel = null;
	private Label IFFTlabel = null;
	private Label LayerNolabel = null;
	private Combo LayerNocombo = null;
	private Group IFFTgroup = null;
	private Text IFFTtext = null;
	private Label IFFTHelplabel = null;
	private Button OFDMSavebutton = null;
	private Label fillerlabel = null;
	//**********************************************/

	//*********widgets for CyclicPrefix*************/
	private Label CPIHeadingLabel = null;
	private Label CPTypelabel = null;
	private Combo CPTypecombo = null;
	private Label CPIIFFTlabel = null;
	private Group CPIIFFTgroup = null;
	private Text CPIIFFTtext = null;
	private Label CPIIFFThelplabel = null;
	private Label CPILayerNolabel = null;
	private Combo CPILayerNocombo = null;
	private Label CPILlabel = null;
	private Spinner CPILspinner = null;
	private Button CPISavebutton = null;
	private Label CPIerrorlabel = null;
	//**********************************************/


	/**
	 * Monitor Area Data Member
	 */
	private Label NUM_YETA_Label = null;
	private Text NUM_YETA_textbox = null;
	private Label Threshold_Label = null;
	private Spinner Threshold_spinner = null;
	private Label FRAME_WIDTHxHEIGHT_Label = null;
	private Text FRAME_WIDTHxHEIGHT_textbox = null;
	private Label START_ROW_Label = null;
	private Spinner START_ROW_Spinner = null;
	private Label START_COL_Label = null;
	private Spinner START_COL_Spinner = null;
	private Label END_ROW_Label = null;
	private Spinner END_ROW_Spinner = null;
	private Label END_COL_Label = null;
	private Spinner END_COL_Spinner = null;
	private Label RECT_HEIGHTxWIDTH_Label = null;
	private Text RECT_HEIGHTxWIDTH_textbox = null;
	private Label TOTAL_FRAMES_Label = null;
	private Spinner TOTAL_FRAMES_spiner = null;
	private Button MonitorAreaSaveButton = null;
	private Label MonitorAreaConfiguration=null;

	//***********************************************


	/**
	 * Video Analysis Data Member
	 */
	private Label MAX_OBJ_Label = null;
	private Spinner MAX_OBJ_Spinner = null;
	private Label NO_OBJ_LABEL_Label = null;
	private Spinner NO_OBJ_LABEL_Spinner = null;
	private Label MAX_FOREGR_PIX_MB_Label = null;
	private Spinner MAX_FOREGR_PIX_MB_Spinner = null;
	private Label MAX_MB_OBJ_Label = null;
	private Spinner MAX_MB_OBJ_Spinner = null;
	private Label RECT_HEIGHTxWIDTH_VA_Label = null;
	private Text RECT_HEIGHTxWIDTH_VA_textbox = null;
	private Label VideoAnalysisConfiguration=null;
	private Button saveVAButton = null;

	//***********************************************************

	/**
	 * Display Video Data Member
	 */
	private Label MAX_OBJ_DV_Label = null;
	private Text MAX_OBJ_DV_textbox = null;
	private Label FRAME_WIDTHxHEIGHT_DV_Label = null;
	private Text FRAME_WIDTHxHEIGHT_DV_textbox = null;
	private Label DisplayVideoConfiguration = null;

	//*********************************************************************

	private Button EditSourceFile_1 = null;

	private Button ViewOutputFile = null;

	private Label ModConfiguration;

	private Button EditSourceFile_2;

	private Label HeaderLabel;
	private VerifyListener listener ;
	private Label errorlabel;
	private static String input2 = "";
	private Composite reference;

	private static boolean CPI_Properites = false;	
	private static boolean OFDM_Properites = false;
	private static boolean Precoding_Properites = false;
	private static boolean Scrambler_Properites = false;
	private static boolean LM_Properites = false;
	private static boolean MM_Properites = false;
	private static boolean objectwritten = false;

	public static boolean count = true;

	private String OS_Separator = "";

	private AutoModelEditPart amep = null;










	public void createPartControl(Composite parent) {
		if(parent==null) return;

		if(parent!=null) {
			while(ZERO != parent.getChildren().length)
			{
				parent.getChildren()[0].dispose();		
			}
		}

		comp = parent;

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.FILL;
		HeaderLabel = new Label(parent, SWT.NONE);
		HeaderLabel.setText("========== Select a Module to Configure ==========");
		HeaderLabel.setLayoutData(gridData);	

		//parent.setbackground(new Color(parent.getDisplay(), 255, 250, 250));
		parent.setLayout(new GridLayout());		

		parent.layout(true);		
	}


	/**
	 * Custom Create part control for each module
	 * @param parent
	 * @param part
	 * @param add
	 */
	public void createPartControl(Composite parent, EditPart part, boolean add) {

		if(Platform.getOS().equalsIgnoreCase("win32"))
			setOS_Separator("\\");
		else if(Platform.getOS().equalsIgnoreCase("linux"))
			setOS_Separator("/");

		if(parent== null || part == null) return;
		if(parent!=null) {
			while(ZERO != parent.getChildren().length){
				parent.getChildren()[0].dispose();		
			}
		}	

		comp = parent; 

		if(part instanceof AutoModelEditPart) {

			RunAutosysProject.childs = part.getParent().getChildren();
			amep = (AutoModelEditPart) part;
			file = new File(new ProjectInformation().getProjectName(4)+getOS_Separator()+".settings");

			try {
				if(!file.exists()) {
					fout = new FileOutputStream(file);
					oos = new ObjectOutputStream(fout);
					par_obj = new LTEParameters();
					par_obj.setDefault_param("DEFAULT");
					par_obj.setMod_written(String.valueOf(false));
					par_obj.setLm_written(String.valueOf(false));
					par_obj.setPrecoding_written(String.valueOf(false));
					par_obj.setScarmblerwritten(String.valueOf(false));
					par_obj.setRM_written(String.valueOf(false));
					par_obj.setOFDM_written(String.valueOf(false));
					par_obj.setCPI_written(String.valueOf(false));
					par_obj.setREM_written(String.valueOf(false));
					par_obj.setGeneralparam_written(String.valueOf(false));
					par_obj.setObject_written(String.valueOf(true));
					oos.writeObject(par_obj);
					setObjectwritten(true);
					oos.close();
					fout.close();
					par_obj = null;
				} else {
					fin = new FileInputStream(file);
					ois = new ObjectInputStream(fin);
					par_obj = (LTEParameters) ois.readObject();
					setObjectwritten(Boolean.parseBoolean(par_obj.getObject_written()));
					setMM_Properites(Boolean.parseBoolean(par_obj.getMod_written()));
					setCPI_Properites(Boolean.parseBoolean(par_obj.getCPI_written()));
					setREM_Properties(Boolean.parseBoolean(par_obj.getREM_written()));
					setOFDM_Properites(Boolean.parseBoolean(par_obj.getOFDM_written()));
					setPrecoding_Properites(Boolean.parseBoolean(par_obj.getPrecoding_written()));
					setScrambler_Properites(Boolean.parseBoolean(par_obj.getScarmblerwritten()));
					setRM_Properties(Boolean.parseBoolean(par_obj.getRM_written()));
					setLM_Properites(Boolean.parseBoolean(par_obj.getLm_written()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if((part.getModel() instanceof Modulator) && add){
				createModulatorConfig(parent, part, add);
			}

			else if(part.getModel() instanceof LayerMapper 	&& add) {
				createLayerMapperConfig(parent, part, add);
			}

			else if(part.getModel() instanceof InputPort && add) {
				createInputPortConfig(parent, part, add);
			}

			else if(part.getModel() instanceof OutputPort && add) {
				createOutputPortConfig(parent, part, add);
			}

			else if(part.getModel() instanceof CRC && add) {
				createCRCConfig(parent, part, add);
			}

			else if(part.getModel() instanceof CSB && add) {
				createCSBConfig(parent, part, add);
			}

			else if(part.getModel() instanceof ChannelCoding && add) {
				createChannelCodingConfig(parent, part, add);
			}

			else if(part.getModel() instanceof RateMatching && add) {
				createRateMatchingConfig(parent, part, add);
			}

			else if(part.getModel() instanceof Scrambler && add) {
				createScramblerConfig(parent, part, add);
			}

			else if(part.getModel() instanceof Precoding && add) {
				createPrecodingConfig(parent, part, add);
			}

			else if(part.getModel() instanceof REM && add) {
				createREMConfig(parent, part, add);
			}

			else if(part.getModel() instanceof OFDM && add) {
				createOFDMConfig(parent, part, add);
			}

			else if(part.getModel() instanceof CyclicPrefix && add) {
				createCyclicPrefixConfig(parent, part, add);
			}

			else if(part.getModel() instanceof MonitorArea && add){
				createMonitorAreaConfig(parent, part, add);
			}
			else if(part.getModel() instanceof VideoAnalyser && add){
				createVideoAnalyser(parent, part, add);
			}
			else if(part.getModel() instanceof VideoDisplay && add){
				createVideoDisplay(parent, part, add);
			}

			else{
				createDefault(parent, part, add);
			}
		}
		else if(part instanceof AutosysDiagramEditPart){

			//AutoModelEditPart amep = null;
			createDefault(parent, part, add);
			//update childs in container editpart
			RunAutosysProject.childs = part.getChildren();	
		}
		/*//parent.setbackground(new Color(parent.getDisplay(), 255, 250, 250));
		for(int i = 0; i < parent.getChildren().length; i++){
			parent.getChildren()[i].setBackground(new Color(parent.getChildren()[i].getDisplay(), 255, 250, 250));
		}*/

		parent.layout(true);

	}

	private void createVideoDisplay(Composite parent, EditPart part, boolean add) {

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalIndent = 0;
		gridData1.horizontalSpan = 3;

		DisplayVideoConfiguration  = new Label(parent, SWT.NONE);
		DisplayVideoConfiguration.setText("========== Video Display Configuration ===========");
		DisplayVideoConfiguration.setLayoutData(gridData1);

		MAX_OBJ_DV_Label = new Label(parent, SWT.NONE);
		MAX_OBJ_DV_Label.setText("MAX_OBJ : ");
		MAX_OBJ_DV_textbox = new Text(parent, SWT.BORDER);
		MAX_OBJ_DV_textbox.setEditable(false);
		MAX_OBJ_DV_textbox.setBackground(new Color(parent.getDisplay(), 255, 255, 220));
		if(null != VLCD_SaveConfig.getMap(4)){
			MAX_OBJ_DV_textbox.setText(VLCD_SaveConfig.getMap(4));
		}else{
			MAX_OBJ_DV_textbox.setText("6");
		}

		FRAME_WIDTHxHEIGHT_DV_Label = new Label(parent, SWT.NONE);
		FRAME_WIDTHxHEIGHT_DV_Label.setText("FRAME WIDTH x HEIGHT : ");
		FRAME_WIDTHxHEIGHT_DV_textbox = new Text(parent, SWT.BORDER);
		FRAME_WIDTHxHEIGHT_DV_textbox.setText("101376");
		FRAME_WIDTHxHEIGHT_DV_textbox.setEditable(false);
		FRAME_WIDTHxHEIGHT_DV_textbox.setBackground(new Color(parent.getDisplay(), 255, 255, 220));

		parent.setLayout(gridLayout);
		parent.layout(true);
	}


	private void createVideoAnalyser(Composite parent, EditPart part,
			boolean add) {

		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalIndent = 0;
		gridData4.horizontalSpan = 3;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;

		VideoAnalysisConfiguration = new Label(parent, SWT.NONE);
		VideoAnalysisConfiguration.setText("========== Video Analyser Configuration ===========");
		VideoAnalysisConfiguration.setLayoutData(gridData4);

		MAX_OBJ_Label = new Label(parent, SWT.NONE);
		MAX_OBJ_Label.setText("MAX_OBJ : ");
		MAX_OBJ_Spinner = new Spinner(parent, SWT.NONE);
		MAX_OBJ_Spinner.setMinimum(6);
		MAX_OBJ_Spinner.setMaximum(20);
		MAX_OBJ_Spinner.setLayoutData(gridData);
		MAX_OBJ_Spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				saveVAButton.setEnabled(true);
			}
		});
		MAX_OBJ_Spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				saveVAButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label MAX_OBJ_tips = new Label(parent, SWT.NONE);
		MAX_OBJ_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		MAX_OBJ_tips.setToolTipText("Range : 6 to 20");

		NO_OBJ_LABEL_Label = new Label(parent, SWT.NONE);
		NO_OBJ_LABEL_Label.setText("NO_OBJ_LABEL : ");
		NO_OBJ_LABEL_Spinner = new Spinner(parent, SWT.NONE);
		NO_OBJ_LABEL_Spinner.setMinimum(7);
		NO_OBJ_LABEL_Spinner.setMaximum(100);
		NO_OBJ_LABEL_Spinner.setLayoutData(gridData1);
		NO_OBJ_LABEL_Spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				saveVAButton.setEnabled(true);
			}
		});
		NO_OBJ_LABEL_Spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				saveVAButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label NO_OBJ_LABEL_tips = new Label(parent, SWT.NONE);
		NO_OBJ_LABEL_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		NO_OBJ_LABEL_tips.setToolTipText("Range : 7 to 100");

		MAX_FOREGR_PIX_MB_Label = new Label(parent, SWT.NONE);
		MAX_FOREGR_PIX_MB_Label.setText("MAX_FOREGR_PIX_MB : ");
		MAX_FOREGR_PIX_MB_Spinner = new Spinner(parent, SWT.NONE);
		MAX_FOREGR_PIX_MB_Spinner.setMinimum(6);
		MAX_FOREGR_PIX_MB_Spinner.setMaximum(10);
		MAX_FOREGR_PIX_MB_Spinner.setLayoutData(gridData2);
		MAX_FOREGR_PIX_MB_Spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				saveVAButton.setEnabled(true);
			}
		});
		MAX_FOREGR_PIX_MB_Spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				saveVAButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label MAX_FOREGR_PIX_MB_tips = new Label(parent, SWT.NONE);
		MAX_FOREGR_PIX_MB_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		MAX_FOREGR_PIX_MB_tips.setToolTipText("Range : 6 to 10");

		MAX_MB_OBJ_Label = new Label(parent, SWT.NONE);
		MAX_MB_OBJ_Label.setText("MAX_MB_OBJ : ");
		MAX_MB_OBJ_Spinner = new Spinner(parent, SWT.NONE);
		MAX_MB_OBJ_Spinner.setMinimum(18);
		MAX_MB_OBJ_Spinner.setMaximum(200);
		MAX_MB_OBJ_Spinner.setLayoutData(gridData3);
		MAX_MB_OBJ_Spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				saveVAButton.setEnabled(true);		
			}
		});
		MAX_MB_OBJ_Spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				saveVAButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label MAX_MB_OBJ_tips = new Label(parent, SWT.NONE);
		MAX_MB_OBJ_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		MAX_MB_OBJ_tips.setToolTipText("Range : 18 to 200");

		RECT_HEIGHTxWIDTH_VA_Label = new Label(parent, SWT.NONE);
		RECT_HEIGHTxWIDTH_VA_Label.setText("RECT HEIGHT x WIDTH : ");
		RECT_HEIGHTxWIDTH_VA_textbox = new Text(parent, SWT.BORDER);
		if(null != VLCD_SaveConfig.getMap(12)){
			RECT_HEIGHTxWIDTH_VA_textbox.setText(VLCD_SaveConfig.getMap(12));
		}else
			RECT_HEIGHTxWIDTH_VA_textbox.setText("0");
		RECT_HEIGHTxWIDTH_VA_textbox.setLayoutData(gridData5);
		RECT_HEIGHTxWIDTH_VA_textbox.setEditable(false);
		RECT_HEIGHTxWIDTH_VA_textbox.setBackground(new Color(parent.getDisplay(), 255, 255, 220));

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		saveVAButton  = new Button(parent, SWT.NONE);
		saveVAButton.setText("Save");
		saveVAButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				VLCD_SaveConfig.putMap(4,MAX_OBJ_Spinner.getText());
				VLCD_SaveConfig.putMap(3,NO_OBJ_LABEL_Spinner.getText());
				VLCD_SaveConfig.putMap(6,MAX_FOREGR_PIX_MB_Spinner.getText());
				VLCD_SaveConfig.putMap(5,MAX_MB_OBJ_Spinner.getText());

				VLCD_SaveConfig.VideoAnalysisSaveState = true;
				saveVAButton.setEnabled(false);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		if(VLCD_SaveConfig.VideoAnalysisSaveState){
			retrivedData();
		}
		parent.setLayout(gridLayout);	
		parent.layout(true);
	}


	private void retrivedData() {

		MAX_OBJ_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(4)));
		NO_OBJ_LABEL_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(3)));
		MAX_FOREGR_PIX_MB_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(6)));
		MAX_MB_OBJ_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(5)));
	}


	private void createMonitorAreaConfig(Composite parent, EditPart part,boolean add) {

		GridData gridData8 = new GridData();
		gridData8.horizontalAlignment = GridData.FILL;
		gridData8.verticalAlignment = GridData.CENTER;
		GridData gridData7 = new GridData();
		gridData7.horizontalAlignment = GridData.FILL;
		gridData7.verticalAlignment = GridData.CENTER;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;


		GridData gridData9 = new GridData();
		gridData9.horizontalIndent = 0;
		gridData9.horizontalSpan = 3;


		MonitorAreaConfiguration = new Label(parent, SWT.NONE);
		MonitorAreaConfiguration.setText("========== Monitor Area Configuration ===========");
		MonitorAreaConfiguration.setLayoutData(gridData9);

		TOTAL_FRAMES_Label = new Label(parent, SWT.NONE);
		TOTAL_FRAMES_Label.setText("TOTAL FRAMES : ");
		TOTAL_FRAMES_spiner  = new Spinner(parent, SWT.BORDER);
		TOTAL_FRAMES_spiner.setMinimum(1);
		TOTAL_FRAMES_spiner.setMaximum(1000);
		TOTAL_FRAMES_spiner.setLayoutData(gridData7);
		TOTAL_FRAMES_spiner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				MonitorAreaSaveButton.setEnabled(true);				
			}
		});
		TOTAL_FRAMES_spiner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				MonitorAreaSaveButton.setEnabled(true);				
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label TOTAL_FRAMES_tips = new Label(parent, SWT.NONE);
		TOTAL_FRAMES_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		TOTAL_FRAMES_tips.setToolTipText("Range : 1 to 1000");


		NUM_YETA_Label = new Label(parent, SWT.NONE);
		NUM_YETA_Label.setText("Learning Frames : ");
		NUM_YETA_textbox = new Text(parent, SWT.BORDER);
		NUM_YETA_textbox.setText("1");
		NUM_YETA_textbox.setTextLimit(4);
		NUM_YETA_textbox.setLayoutData(gridData8);
		VerifyListener verify = null;
		NUM_YETA_textbox.addVerifyListener(verify  = new VerifyListener() {			
			public void verifyText(VerifyEvent e) {
				if(e.character == '!' || e.character == '@' || e.character == '#' || e.character == '$' ||
						e.character == '%' || e.character == '^' || e.character == '&' || e.character == '*' ||
						e.character == '(' || e.character == ')'){
					e.doit = false;
					return;
				}
				if(e.keyCode == 8 || e.keyCode == 127){
					e.doit = true;
					MonitorAreaSaveButton.setEnabled(true);
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 16777264 && e.keyCode <= 16777273)){
					if(Integer.parseInt("0" + NUM_YETA_textbox.getText() + e.character) <= 
						Integer.parseInt("0" + TOTAL_FRAMES_spiner.getText())){
						e.doit = true;
						MonitorAreaSaveButton.setEnabled(true);
					}else
						e.doit = false;
				}else
					e.doit = false;
			}
		});
		final Label NUM_YETA_tips = new Label(parent, SWT.NONE);
		NUM_YETA_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));		
		NUM_YETA_tips.addMouseMoveListener(new MouseMoveListener() {			
			public void mouseMove(MouseEvent e) {
				NUM_YETA_tips.setToolTipText("Range : 1 to " + TOTAL_FRAMES_spiner.getText());				
			}
		});

		Threshold_Label = new Label(parent, SWT.NONE);
		Threshold_Label.setText("Threshold : ");
		Threshold_spinner = new Spinner(parent, SWT.NONE);
		Threshold_spinner.setMinimum(15);
		Threshold_spinner.setMaximum(30);
		Threshold_spinner.setLayoutData(gridData1);
		Threshold_spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				MonitorAreaSaveButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Threshold_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				MonitorAreaSaveButton.setEnabled(true);				
			}
		});
		Label Threshold_tips = new Label(parent, SWT.NONE);
		Threshold_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		Threshold_tips.setToolTipText("Range : 15 to 30");

		FRAME_WIDTHxHEIGHT_Label = new Label(parent, SWT.NONE);
		FRAME_WIDTHxHEIGHT_Label.setText("Frame Width x Height : ");
		FRAME_WIDTHxHEIGHT_textbox = new Text(parent, SWT.BORDER);
		FRAME_WIDTHxHEIGHT_textbox.setText("101376");
		FRAME_WIDTHxHEIGHT_textbox.setEditable(false);
		FRAME_WIDTHxHEIGHT_textbox.setLayoutData(gridData);
		FRAME_WIDTHxHEIGHT_textbox.setBackground(new Color(parent.getDisplay(), 255, 255, 220));

		new Label(parent, SWT.NONE);

		START_ROW_Label = new Label(parent, SWT.NONE);
		START_ROW_Label.setText("START_ROW : ");
		START_ROW_Spinner = new Spinner(parent, SWT.NONE);
		START_ROW_Spinner.setMinimum(0);
		START_ROW_Spinner.setMaximum(287);
		START_ROW_Spinner.setLayoutData(gridData2);
		START_ROW_Spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());
				MonitorAreaSaveButton.setEnabled(true);
			}
		});
		START_ROW_Spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());	
				MonitorAreaSaveButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label START_ROW_tips = new Label(parent, SWT.NONE);
		START_ROW_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		START_ROW_tips.setToolTipText("Range : 0 to 287");

		START_COL_Label = new Label(parent, SWT.NONE);
		START_COL_Label.setText("START_COL : ");
		START_COL_Spinner = new Spinner(parent, SWT.NONE);
		START_COL_Spinner.setMinimum(0);
		START_COL_Spinner.setMaximum(351);
		START_COL_Spinner.setLayoutData(gridData3);
		START_COL_Spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());	
				MonitorAreaSaveButton.setEnabled(true);
			}
		});
		START_COL_Spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());
				MonitorAreaSaveButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label START_COL_tips = new Label(parent, SWT.NONE);
		START_COL_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		START_COL_tips.setToolTipText("Range : 0 to 351");

		END_ROW_Label = new Label(parent, SWT.NONE);
		END_ROW_Label.setText("END_ROW : ");
		END_ROW_Spinner = new Spinner(parent, SWT.NONE);
		END_ROW_Spinner.setMinimum(0);
		END_ROW_Spinner.setMaximum(287);
		END_ROW_Spinner.setLayoutData(gridData4);
		END_ROW_Spinner.addModifyListener(new ModifyListener() {			
			public void modifyText(ModifyEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());
				MonitorAreaSaveButton.setEnabled(true);
			}
		});
		END_ROW_Spinner.addMouseListener(new MouseListener() {			
			public void mouseUp(MouseEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());
				MonitorAreaSaveButton.setEnabled(true);
			}

			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label END_ROW_tips = new Label(parent, SWT.NONE);
		END_ROW_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		END_ROW_tips.setToolTipText("Range : 0 to 287");

		END_COL_Label = new Label(parent, SWT.NONE);
		END_COL_Label.setText("END_COL : ");
		END_COL_Spinner = new Spinner(parent, SWT.NONE);
		END_COL_Spinner.setMinimum(0);
		END_COL_Spinner.setMaximum(351);
		END_COL_Spinner.setLayoutData(gridData5);
		END_COL_Spinner.addModifyListener(new ModifyListener() {			
			public void modifyText(ModifyEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());
				MonitorAreaSaveButton.setEnabled(true);
			}
		});
		END_COL_Spinner.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				Long value = (Long.parseLong(END_ROW_Spinner.getText()) - Long.parseLong(START_ROW_Spinner.getText())) *
				(Long.parseLong(END_COL_Spinner.getText()) - Long.parseLong(START_COL_Spinner.getText()));
				RECT_HEIGHTxWIDTH_textbox.setText(value.toString());
				MonitorAreaSaveButton.setEnabled(true);
			}
			public void mouseDown(MouseEvent e) {
			}
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Label END_COL_tips = new Label(parent, SWT.NONE);
		END_COL_tips.setImage(new Image(parent.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		END_COL_tips.setToolTipText("Range : 0 to 351");

		RECT_HEIGHTxWIDTH_Label = new Label(parent, SWT.NONE);
		RECT_HEIGHTxWIDTH_Label.setText("RECT HEIGHT x WIDTH : ");
		RECT_HEIGHTxWIDTH_textbox = new Text(parent, SWT.BORDER);
		RECT_HEIGHTxWIDTH_textbox.setText("0");
		RECT_HEIGHTxWIDTH_textbox.setEditable(false);
		RECT_HEIGHTxWIDTH_textbox.setBackground(new Color(parent.getDisplay(), 255, 255, 220));
		RECT_HEIGHTxWIDTH_textbox.setLayoutData(gridData6);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		MonitorAreaSaveButton = new Button(parent, SWT.NONE);
		MonitorAreaSaveButton.setText("Save");
		MonitorAreaSaveButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				VLCD_SaveConfig.putMap(7,TOTAL_FRAMES_spiner.getText());
				VLCD_SaveConfig.putMap(1, NUM_YETA_textbox.getText());
				VLCD_SaveConfig.putMap(2, Threshold_spinner.getText());
				VLCD_SaveConfig.putMap(8, START_ROW_Spinner.getText());
				VLCD_SaveConfig.putMap(9, START_COL_Spinner.getText());
				VLCD_SaveConfig.putMap(10, END_ROW_Spinner.getText());
				VLCD_SaveConfig.putMap(11, END_COL_Spinner.getText());
				VLCD_SaveConfig.putMap(12, RECT_HEIGHTxWIDTH_textbox.getText());	

				VLCD_SaveConfig.MonitorAreaSaveState = true;
				MonitorAreaSaveButton.setEnabled(false);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		if(VLCD_SaveConfig.MonitorAreaSaveState){
			restoreSaveData(verify);
		}


		parent.setLayout(gridLayout);
		parent.layout(true);
	}


	private void restoreSaveData(VerifyListener verify) {

		TOTAL_FRAMES_spiner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(7)));
		NUM_YETA_textbox.removeVerifyListener(verify);
		NUM_YETA_textbox.setText(VLCD_SaveConfig.getMap(1));
		NUM_YETA_textbox.addVerifyListener(verify);
		Threshold_spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(2)));
		START_ROW_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(8)));
		START_COL_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(9)));
		END_ROW_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(10)));
		END_COL_Spinner.setSelection(Integer.parseInt(VLCD_SaveConfig.getMap(11)));
		RECT_HEIGHTxWIDTH_textbox.setText(VLCD_SaveConfig.getMap(12));
		MonitorAreaSaveButton.setEnabled(false);
	}


	/**
	 * Create Configuration for CyclicPrefix
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createCyclicPrefixConfig(Composite parent, EditPart part,
			boolean add) {

		GridData gridData7 = new GridData();		
		gridData7.horizontalSpan = 2;
		GridData gridData6 = new GridData();		
		gridData6.horizontalSpan = 2;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.BEGINNING;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.horizontalSpan = 2;
		gridData5.horizontalIndent = 100;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.BEGINNING;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;		

		CPIHeadingLabel = new Label(parent, SWT.NONE);
		CPIHeadingLabel.setText("====== CPI Configutaion Parameters ======");
		CPIHeadingLabel.setLayoutData(gridData6);

		CPTypelabel = new Label(parent, SWT.NONE);
		CPTypelabel.setText("CP Type :");
		CPTypelabel.setLayoutData(gridData);
		CPTypecombo = new Combo(parent, SWT.READ_ONLY);
		CPIIFFTlabel = new Label(parent, SWT.NONE);
		CPIIFFTlabel.setText("IFFT Points (x) :");
		CPIIFFTlabel.setLayoutData(gridData1);
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		CPIIFFTgroup = new Group(parent, SWT.NONE);
		CPIIFFTgroup.setLayout(gridLayout1);
		CPIIFFTtext = new Text(CPIIFFTgroup, SWT.BORDER);
		CPIIFFThelplabel = new Label(CPIIFFTgroup, SWT.NONE);
		CPIIFFThelplabel.setText("(2^x will be Taken)");
		CPILayerNolabel = new Label(parent, SWT.NONE);
		CPILayerNolabel.setText("Layer No 's :");
		CPILayerNolabel.setLayoutData(gridData2);
		CPILayerNocombo = new Combo(parent, SWT.READ_ONLY);
		CPILlabel = new Label(parent, SWT.NONE);
		CPILlabel.setText("l :");
		CPILlabel.setLayoutData(gridData3);
		CPILspinner = new Spinner(parent, SWT.READ_ONLY);
		CPILspinner.setLayoutData(gridData4);
		CPISavebutton = new Button(parent, SWT.NONE);
		CPISavebutton.setText("Save Configuration");
		CPISavebutton.setLayoutData(gridData5);
		CPIerrorlabel = new Label(parent, SWT.NONE);
		CPISavebutton.setEnabled(false);
		CPIerrorlabel.setText("");
		CPIerrorlabel.setForeground(new Color(parent.getDisplay(), 255, 0, 0));
		CPIerrorlabel.setLayoutData(gridData7);
		parent.setLayout(gridLayout);


		CPTypecombo.add("NORMAL");
		CPTypecombo.add("EXTENDED");
		CPTypecombo.select(0);

		CPILayerNocombo.add("1");
		CPILayerNocombo.add("2");
		CPILayerNocombo.add("3");
		CPILayerNocombo.add("4");
		CPILayerNocombo.select(0);

		CPILspinner.setMinimum(1);
		CPILspinner.setMaximum(6);
		CPILspinner.setBackground(ColorConstants.white);

		parent.layout(true);
		reference = parent;	

		CPTypecombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				CPISavebutton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		CPIIFFTtext.addVerifyListener(listener = new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					CPISavebutton.setEnabled(false);
					if(CPIIFFTtext.getText().equalsIgnoreCase("")) {
						CPIerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;
					CPISavebutton.setEnabled(true);
					CPIerrorlabel.setText("");
				}else{
					if(CPIIFFTtext.getText().equalsIgnoreCase("")) {
						CPIerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					e.doit = false;
				}
			}
		});

		CPILayerNocombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				CPISavebutton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		CPILspinner.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				CPISavebutton.setEnabled(true);				
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		CPISavebutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(CPIIFFTtext.getText().equalsIgnoreCase("")) {
					CPIerrorlabel.setText("#Error : IFFT Points cannot be blank");
					reference.layout(true);
					return;
				}
				if(Integer.parseInt(CPIIFFTtext.getText()) > 12) {
					CPIerrorlabel.setText("#Error : IFFT Points  > 12 Not Supported");
					CPIIFFTtext.removeVerifyListener(listener);
					CPIIFFTtext.setText("");
					CPIIFFTtext.addVerifyListener(listener);
					reference.layout(true);
					return;
				}
				if(Integer.parseInt(CPIIFFTtext.getText()) < 1) {
					CPIerrorlabel.setText("#Error : IFFT Points must be > 1");
					CPIIFFTtext.removeVerifyListener(listener);
					CPIIFFTtext.setText("");
					CPIIFFTtext.addVerifyListener(listener);
					reference.layout(true);
					return;
				}
				CPIerrorlabel.setText("");
				reference.layout(true);				

				saveConfiguration(amep);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		if(isCPI_Properites())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}


	/**
	 * Create Configuration for OFDM
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createOFDMConfig(Composite parent, EditPart part, boolean add) {

		GridData gridData5 = new GridData();		
		gridData5.horizontalSpan = 2;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.BEGINNING;
		gridData3.grabExcessVerticalSpace = false;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData21 = new GridData();
		gridData21.grabExcessVerticalSpace = false;
		gridData21.verticalAlignment = GridData.FILL;
		gridData21.horizontalAlignment = GridData.BEGINNING;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalIndent = 131;
		gridData1.grabExcessHorizontalSpace = true;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		Titlelabel = new Label(parent, SWT.NONE);
		Titlelabel.setText("=========== OFDM Parameters ============");
		Titlelabel.setLayoutData(gridData);
		IFFTlabel = new Label(parent, SWT.NONE);
		IFFTlabel.setText("IFFTPoints (x) :");
		IFFTlabel.setLayoutData(gridData3);
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.BEGINNING;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.grabExcessVerticalSpace = false;
		gridData4.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		IFFTgroup = new Group(parent, SWT.NONE);
		IFFTgroup.setLayout(gridLayout1);
		IFFTgroup.setLayoutData(gridData4);
		//		IFFTgroup.setBackground(new Color(IFFTgroup.getDisplay(), 255, 250, 250));
		IFFTtext = new Text(IFFTgroup, SWT.BORDER);
		IFFTHelplabel = new Label(IFFTgroup, SWT.NONE);
		IFFTHelplabel.setText("(2^x will be taken)");
		LayerNolabel = new Label(parent, SWT.NONE);
		LayerNolabel.setText("Layer_No' s :");
		LayerNolabel.setLayoutData(gridData21);
		/*for(int i = 0; i < IFFTgroup.getChildren().length; i++) {
			IFFTgroup.getChildren()[i].setBackground(new Color(IFFTgroup.getChildren()[i].getDisplay(), 255,250,250));
		}*/
		parent.setLayout(gridLayout);

		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalAlignment = GridData.BEGINNING;
		LayerNocombo = new Combo(parent, SWT.READ_ONLY);
		LayerNocombo.setLayoutData(gridData2);

		new Label(parent, SWT.NONE);
		fillerlabel = new Label(parent, SWT.NONE);
		fillerlabel.setText("");
		OFDMSavebutton = new Button(parent, SWT.NONE);
		OFDMSavebutton.setText("Save Config");
		OFDMSavebutton.setLayoutData(gridData1); 
		errorlabel = new Label(parent, SWT.ERROR);
		OFDMSavebutton.setEnabled(false);

		errorlabel.setForeground(new Color(parent.getDisplay(), 255, 0, 0));
		errorlabel.setLayoutData(gridData5);
		errorlabel.setText("");

		parent.layout(true);

		LayerNocombo.add("1");
		LayerNocombo.add("2");
		LayerNocombo.add("3");
		LayerNocombo.add("4");
		LayerNocombo.select(0);

		reference = parent; 

		IFFTtext.addVerifyListener(listener = new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					OFDMSavebutton.setEnabled(false);
					if(IFFTtext.getText().equalsIgnoreCase("")) {
						errorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;
					OFDMSavebutton.setEnabled(true);
					errorlabel.setText("");
				}else{
					if(IFFTtext.getText().equalsIgnoreCase("")) {
						errorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					e.doit = false;
				}
			}
		});

		LayerNocombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				OFDMSavebutton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		OFDMSavebutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(IFFTtext.getText().equalsIgnoreCase("")) {
					errorlabel.setText("#Error : IFFT Points cannot be blank");
					reference.layout(true);
					return;
				}
				if(Integer.parseInt(IFFTtext.getText()) > 12) {
					errorlabel.setText("#Error : IFFT Points  > 12 Not Supported");
					IFFTtext.removeVerifyListener(listener);
					IFFTtext.setText("");
					IFFTtext.addVerifyListener(listener);
					reference.layout(true);
					return;
				}
				if(Integer.parseInt(IFFTtext.getText().trim()) < 1) {
					errorlabel.setText("#Error : IFFT Points must be > 1");
					IFFTtext.removeVerifyListener(listener);
					IFFTtext.setText("");
					IFFTtext.addVerifyListener(listener);
					reference.layout(true);
					return;
				}
				errorlabel.setText("");
				reference.layout(true);

				saveConfiguration(amep);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		if(isOFDM_Properites())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}

	/**
	 * Create Configuration for REM
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createREMConfig(Composite parent, EditPart part, boolean add) {
		GridData gridData11 = new GridData();
		gridData11.horizontalSpan = 2;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalSpan = 2;
		gridData2.horizontalAlignment = GridData.BEGINNING;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalIndent = 100;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		REMHeadinglabel = new Label(parent, SWT.NONE);
		REMHeadinglabel.setText("======= REM Configuration =======");
		REMHeadinglabel.setLayoutData(gridData1);
		Configurationlabel = new Label(parent, SWT.NONE);
		Configurationlabel.setText("CP_Type :");
		Configurationlabel.setLayoutData(gridData);
		parent.setLayout(gridLayout);
		REM_CP_Typecombo = new Combo(parent, SWT.READ_ONLY);
		REM_CP_Typecombo.add("NORMAL");
		REM_CP_Typecombo.add("EXTENDED");
		REM_CP_Typecombo.select(0);
		Bnadwidthlabel = new Label(parent, SWT.NONE);
		Bnadwidthlabel.setText("Bandwidth :");
		Bnadwidthlabel.setLayoutData(gridData3);
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		Buttoncomposite = new Composite(parent, SWT.NONE);
		Buttoncomposite.setLayout(gridLayout1);
		normalButton = new Button(Buttoncomposite, SWT.RADIO);
		normalButton.setText("15 kHz");
		normalButton.setSelection(true);
		ExtendedButton = new Button(Buttoncomposite, SWT.RADIO);
		ExtendedButton.setText("7.5 kHz");
		ExtendedButton.setEnabled(false);
		REMSavebutton = new Button(parent, SWT.NONE);
		REMSavebutton.setText("Save Config");
		REMSavebutton.setLayoutData(gridData2);
		REMSavebutton.setEnabled(false);
		REMerrorlabel = new Label(parent, SWT.NONE);
		REMerrorlabel.setText("");
		REMerrorlabel.setLayoutData(gridData11);
		REMerrorlabel.setForeground(new Color(parent.getDisplay(), 255, 0, 0));
		reference = parent;

		REM_CP_Typecombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if(REM_CP_Typecombo.getItem(REM_CP_Typecombo.getSelectionIndex()).equalsIgnoreCase("EXTENDED")){
					ExtendedButton.setEnabled(true);
					REMSavebutton.setEnabled(false);
					REMerrorlabel.setText("EXTENDED CP_Type Not Supported");
					reference.layout(true);
					return;
				} else {
					normalButton.setSelection(true);
					ExtendedButton.setSelection(false);
				}
				ExtendedButton.setEnabled(false);
				REMerrorlabel.setText("");
				reference.layout(true);
				REMSavebutton.setEnabled(true);
			}
		});

		normalButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(REM_CP_Typecombo.getItem(REM_CP_Typecombo.getSelectionIndex()).equalsIgnoreCase("EXTENDED")){
					return;
				}
				REMSavebutton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		REMSavebutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				saveConfiguration(amep);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		if(isREM_Properties())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}

	/**
	 * Create Configuration for Precoding
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createPrecodingConfig(Composite parent, EditPart part,
			boolean add) {

		GridData gridData1 = new GridData();
		gridData1.horizontalIndent = 0;
		gridData1.horizontalSpan = 3;

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;

		Configuration = new Label(parent, SWT.NONE);
		Configuration.setText("========== Precoding Configuration ===========");
		Configuration.setLayoutData(gridData1);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		prAntennaPortslabel = new Label(parent, SWT.NONE);
		prAntennaPortslabel.setText("Number Of Antenna Ports :");

		prPortscombo = new Combo(parent, SWT.READ_ONLY);	
		prPortscombo.add("1");
		prPortscombo.add("2");
		prPortscombo.add("3");
		prPortscombo.add("4");
		prPortscombo.select(0);		

		prPortscombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				prSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		new Label(parent, SWT.NONE);

		prNumberofLayers = new Label(parent, SWT.NONE);
		prNumberofLayers.setText("Number Of Layers :");

		prNumberOfLayers = new Spinner(parent, SWT.NONE);		
		prNumberOfLayers.setMaximum(4);
		prNumberOfLayers.setMinimum(0);
		prNumberOfLayers.setSelection(1);
		prNumberOfLayers.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				prSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		new Label(parent, SWT.NONE);

		prCodeBookIndex = new Label(parent, SWT.NONE);
		prCodeBookIndex.setText("Code Book Index :");

		prCodeBookScale = new Spinner(parent, SWT.NONE);
		prCodeBookScale.setMaximum(15);
		prCodeBookScale.setMinimum(0);
		prCodeBookScale.setSelection(8);


		prCodeBookScale.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				prSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});	

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		prSaveButton = new Button(parent, SWT.NONE);
		prSaveButton.setText("Save Configuration");
		prSaveButton.redraw();
		prSaveButton.setEnabled(false);

		prSaveButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				saveConfiguration(amep);
			}
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		parent.setLayout(gridLayout);

		if(isPrecoding_Properites())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}

	/**
	 * Create Configuration for Scrambler
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createScramblerConfig(Composite parent, EditPart part,
			boolean add) {
		GridData gridData11 = new GridData();
		gridData11.horizontalSpan = 2;
		gridData11.horizontalIndent = 130;
		GridData gridData7 = new GridData();
		gridData7.horizontalSpan = 2;
		GridData gridData6 = new GridData();
		gridData6.horizontalSpan = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		ScramHeadinglabel = new Label(parent, SWT.NONE);
		ScramHeadinglabel.setText("======== Scrambler Configuration =======");
		ScramHeadinglabel.setLayoutData(gridData6);
		nRNTIlabel = new Label(parent, SWT.NONE);
		nRNTIlabel.setText("nRNTI :");
		nRNTIlabel.setLayoutData(gridData);
		nRNTIlabel.setToolTipText("Radio Network Temporary Identifier");
		parent.setLayout(gridLayout);

		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = false;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		nRNTIgroup = new Composite(parent, SWT.NONE);
		nRNTIgroup.setLayout(gridLayout1);
		nRNTIgroup.setLayoutData(gridData2);
		nRNTItext = new Text(nRNTIgroup, SWT.BORDER);
		nRNTIhelplabel = new Label(nRNTIgroup, SWT.NONE);
		nRNTIhelplabel.setText("(0 - 65535)");

		//		setSize(new Point(335, 244));
		nslabel = new Label(parent, SWT.NONE);
		nslabel.setText("ns :");
		nslabel.setLayoutData(gridData1);
		nslabel.setToolTipText("Slot Number 's within radio frame");

		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		nsgroup = new Composite(parent, SWT.NONE);
		nsgroup.setLayout(gridLayout2);
		nstext = new Text(nsgroup, SWT.BORDER);
		nshelplabel = new Label(nsgroup, SWT.NONE);
		nshelplabel.setText("(0 -19)");

		N_cell_id_label = new Label(parent, SWT.NONE);
		N_cell_id_label.setText("N_Cell_id :");
		N_cell_id_label.setToolTipText("Physical Layer Cell Identity");

		GridLayout gridLayout3 = new GridLayout();
		gridLayout3.numColumns = 2;
		N_Cell_id_group = new Composite(parent, SWT.NONE);
		N_Cell_id_group.setLayout(gridLayout3);
		N_cell_id_text = new Text(N_Cell_id_group, SWT.BORDER);
		N_Cell_id_help_label = new Label(N_Cell_id_group, SWT.NONE);
		N_Cell_id_help_label.setText("(0 - 503)");

		ScramSavebutton = new Button(parent, SWT.NONE);
		ScramSavebutton.setText("Save Config");
		ScramSavebutton.setLayoutData(gridData11);
		ScramSavebutton.setEnabled(false);
		Scramerrorlabel = new Label(parent, SWT.NONE);
		Scramerrorlabel.setText("");
		Scramerrorlabel.setLayoutData(gridData7);
		Scramerrorlabel.setForeground(new Color(parent.getDisplay(), 255, 0, 0));

		reference = parent;

		nRNTItext.addVerifyListener(nRNTIlistener = new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					ScramSavebutton.setEnabled(false);
					if(nRNTItext.getText().equalsIgnoreCase("")) {
						Scramerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;
					ScramSavebutton.setEnabled(true);
					Scramerrorlabel.setText("");
				}else{
					if(nRNTItext.getText().equalsIgnoreCase("")) {
						Scramerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					e.doit = false;
				}
			}
		});

		nstext.addVerifyListener(nslistener = new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					ScramSavebutton.setEnabled(false);
					if(nstext.getText().equalsIgnoreCase("")) {
						Scramerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;
					ScramSavebutton.setEnabled(true);
					Scramerrorlabel.setText("");
				}else{
					if(nstext.getText().equalsIgnoreCase("")) {
						Scramerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					e.doit = false;
				}
			}
		});

		N_cell_id_text.addVerifyListener(N_cell_id_listener = new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				if(e.character == '!' || e.character == '@' || e.character == '#' ||
						e.character == '$' || e.character == '%' || e.character == '^' ||
						e.character == '&' || e.character == '*' || 
						e.character == '(' || e.character == ')'){
					e.doit = false;
					ScramSavebutton.setEnabled(false);
					if(N_cell_id_text.getText().equalsIgnoreCase("")) {
						Scramerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					return;
				}
				if((e.keyCode >= 48 && e.keyCode <=57) 
						|| e.keyCode==127 || e.keyCode==8 
						||(e.keyCode >= 16777264 && e.keyCode <=16777273)){
					e.doit = true;
					ScramSavebutton.setEnabled(true);
					Scramerrorlabel.setText("");
				}else{
					if(N_cell_id_text.getText().equalsIgnoreCase("")) {
						Scramerrorlabel.setText("#Error : Invalid Characters. Please Use Numeric Keys");
						reference.layout(true);
					}
					e.doit = false;
				}
			}
		});

		ScramSavebutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(nRNTItext.getText().equalsIgnoreCase("")) {
					Scramerrorlabel.setText("#Error : nRNTI cannot be blank");
					reference.layout(true);
					return;
				} else if(Integer.parseInt(nRNTItext.getText().trim()) > 65535) {
					Scramerrorlabel.setText("#Error : nRNTI cannot exceed 65535");
					reference.layout(true);
					return;
				}
				if(nstext.getText().equalsIgnoreCase("")) {
					Scramerrorlabel.setText("#Error : ns cannot be blank");
					reference.layout(true);
					return;
				} else if(Integer.parseInt(nstext.getText().trim()) > 19) {
					Scramerrorlabel.setText("#Error : ns cannot exceed 19");
					reference.layout(true);
					return;
				}
				if(N_cell_id_text.getText().equalsIgnoreCase("")) {
					Scramerrorlabel.setText("#Error : N_cell_id cannot be blank");
					reference.layout(true);
					return;
				} else if(Integer.parseInt(N_cell_id_text.getText().trim()) > 503) {
					Scramerrorlabel.setText("#Error : N_cell_id cannot exceed 503");
					reference.layout(true);
					return;
				}
				Scramerrorlabel.setText("");
				reference.layout(true);

				saveConfiguration(amep);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		if(isScrambler_Properites())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}

	/**
	 * Create Configuration for RateMatching
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createRateMatchingConfig(Composite parent, EditPart part,
			boolean add) {
		GridData gridData9 = new GridData();
		gridData9.horizontalSpan = 2;
		GridData gridData8 = new GridData();
		gridData8.horizontalSpan = 2;
		gridData8.horizontalIndent = 130;
		GridData gridData7 = new GridData();
		gridData7.horizontalSpan = 2;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		RMheadinglabel = new Label(parent, SWT.NONE);
		RMheadinglabel.setText("===============Rate Matching Configuration=============");
		RMheadinglabel.setLayoutData(gridData7);
		Nirlabel = new Label(parent, SWT.NONE);
		Nirlabel.setText("Nir :");
		Nirlabel.setLayoutData(gridData4);
		Nirtext = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		Nirtext.setText("300000");
		Nirtext.setBackground(new Color(parent.getDisplay(), 255, 255, 230));
		Qmlabel = new Label(parent, SWT.NONE);
		Qmlabel.setText("Qm :");
		Qmlabel.setLayoutData(gridData3);
		parent.setLayout(gridLayout);
		//		parent.setSize(new Point(377, 259));

		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 3;
		Qmgroup = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.verticalSpan = 1;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.BEGINNING;
		GridData gridData20 = new GridData();
		gridData20.horizontalIndent = 10;
		gridData20.verticalAlignment = GridData.CENTER;
		gridData20.horizontalAlignment = GridData.BEGINNING;
		Qmcombo = new Combo(Qmgroup, SWT.READ_ONLY);
		Qmcombo.setLayoutData(gridData);
		Qmcombo.add("2");
		Qmcombo.add("4");
		Qmcombo.add("6");
		Qmcombo.select(0);

		Qmgroup.setLayout(gridLayout1);
		Qmhelplabel1 = new Label(Qmgroup, SWT.NONE);
		Qmhelplabel1.setLayoutData(gridData20);
		Qmhelplabel1.setText("Help");
		Qmhelplabel2 = new Label(Qmgroup, SWT.NONE);
		Qmhelplabel2.setImage(new Image(Qmgroup.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));
		/*Qmhelplabel3 = new Label(Qmgroup, SWT.NONE);
		Qmhelplabel3.setText("6 - 64QAM");*/
		Qmhelplabel1.setToolTipText("2 - QPSK \n4 - 16QAM\n6 - 64QAM");
		Qmhelplabel2.setToolTipText("2 - QPSK \n4 - 16QAM\n6 - 64QAM");
		Nllabel = new Label(parent, SWT.NONE);
		Nllabel.setText("Nl :");
		Nllabel.setLayoutData(gridData2);

		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.END;
		gridData5.horizontalIndent = 10;
		gridData5.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 3;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		Nlgroup = new Composite(parent, SWT.NONE);
		Nlcombo = new Combo(Nlgroup, SWT.READ_ONLY);
		Nlcombo.add("1");
		Nlcombo.add("2");
		Nlcombo.select(0);

		Nlgroup.setLayout(gridLayout2);
		Nlgroup.setLayoutData(gridData1);
		Nlhelplabel1 = new Label(Nlgroup, SWT.NONE);
		Nlhelplabel1.setText("Help");
		Nlhelplabel1.setLayoutData(gridData5);
		Label imagelabel = new Label(Nlgroup,SWT.NONE);
		imagelabel.setImage(new Image(Nlgroup.getDisplay(), this.getClass().getResourceAsStream("icons/helpimage.jpg")));

		Nlhelplabel1.setToolTipText(
				"NL is equal to 1 for transport" +
				" blocks mapped onto one \n" +
				"transmission layer, i.e.," +
				" single-antenna, 1-layer spatial\n" +
				"multiplexing, both transport" +
				" blocks for 2-layer spatial \n" +
				"multiplexing, or the first " +
				"transport block for 3-layer \n" +
				"spatial multiplexing.\n\n" +
				"NL is equal to 2 for transport" +
				" blocks mapped onto two or four\n" +
				"transmission layers, i.e., 2-layer " +
				"transmit diversity, the second\n" +
				"transport block for 3-layer spatial " +
				"multiplexing, both transport\n" +
				"blocks for 4-layer spatial " +
				"multiplexing, or 4-layer transmit\n" +
		"diversity");
		imagelabel.setToolTipText("NL is equal to 1 for transport" +
				" blocks mapped onto one \n" +
				"transmission layer, i.e.," +
				" single-antenna, 1-layer spatial\n" +
				"multiplexing, both transport" +
				" blocks for 2-layer spatial \n" +
				"multiplexing, or the first " +
				"transport block for 3-layer \n" +
				"spatial multiplexing.\n\n" +
				"NL is equal to 2 for transport" +
				" blocks mapped onto two or four\n" +
				"transmission layers, i.e., 2-layer " +
				"transmit diversity, the second\n" +
				"transport block for 3-layer spatial " +
				"multiplexing, both transport\n" +
				"blocks for 4-layer spatial " +
				"multiplexing, or 4-layer transmit\n" +
		"diversity");
		Rv_idxlabel = new Label(parent, SWT.NONE);
		Rv_idxlabel.setText("Rv_idx :");
		Rv_idxlabel.setLayoutData(gridData6);

		GridLayout gridLayout3 = new GridLayout();
		gridLayout3.numColumns = 2;
		Rv_idxgroup = new Composite(parent, SWT.NONE);
		Rv_idxcombo = new Combo(Rv_idxgroup, SWT.READ_ONLY);
		Rv_idxcombo.add("0");
		Rv_idxcombo.add("1");
		Rv_idxcombo.add("2");
		Rv_idxcombo.add("3");
		Rv_idxcombo.select(0);

		Rv_idxgroup.setLayout(gridLayout3);
		Rv_idxhelplabel = new Label(Rv_idxgroup, SWT.NONE);
		Rv_idxhelplabel.setText("(Redundancy Version Number)");

		RMSavebutton = new Button(parent, SWT.NONE);
		RMSavebutton.setText("Save Config");
		RMSavebutton.setLayoutData(gridData8);
		RMSavebutton.setEnabled(false);

		RMerrorlabel = new Label(parent, SWT.NONE);
		RMerrorlabel.setText("");
		RMerrorlabel.setLayoutData(gridData9);

		Qmcombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				RMSavebutton.setEnabled(true);
			}
		});

		Nlcombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				RMSavebutton.setEnabled(true);
			}
		});

		Rv_idxcombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				RMSavebutton.setEnabled(true);
			}
		});

		RMSavebutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				saveConfiguration(amep);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		if(isRM_Properties())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}

	/**
	 * Create Configuration for ChannelCoding
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createChannelCodingConfig(Composite parent, EditPart part,
			boolean add) {
		GridLayout cc_layout = new GridLayout();
		cc_layout.numColumns = 1;
		CCHeadingLabel = new Label(parent, SWT.NONE);
		CCHeadingLabel.setText("=========== ChannelCoder Configuration Parameters =========");
		new Label(parent, SWT.NONE).setText("No Parameters");
		parent.setLayout(cc_layout);
		parent.layout(true);
	}

	/**
	 * Create Configuration for CSB
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createCSBConfig(Composite parent, EditPart part, boolean add) {
		GridLayout csb_layout = new GridLayout();
		csb_layout.numColumns = 1;
		CSBHeadingLabel = new Label(parent, SWT.NONE);
		CSBHeadingLabel.setText("=========== CSB Configuration Parameters ==========");
		new Label(parent, SWT.NONE).setText("No Parameters");
		parent.setLayout(csb_layout);
		parent.layout(true);
	}

	/**
	 * Create Configuration for CRC
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createCRCConfig(Composite parent, EditPart part, boolean add) {

		GridLayout crc_layout = new GridLayout();
		crc_layout.numColumns = 1;
		CRCHeadingLabel = new Label(parent, SWT.NONE);
		CRCHeadingLabel.setText("=========== CRC Configuration Parameters ==========");
		new Label(parent, SWT.NONE).setText("No Parameters");
		parent.setLayout(crc_layout);
		parent.layout(true);
	}

	/**
	 * Delete the Composite object.
	 */
	public void dispose() {
		comp = null;
		super.dispose();
	}


	/**
	 * Create Default Configuration
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createDefault(Composite parent, EditPart part, boolean add) {

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.FILL;
		HeaderLabel = new Label(parent, SWT.NONE);
		HeaderLabel.setText("========== Select a Module to Configure ==========");
		HeaderLabel.setLayoutData(gridData);
		parent.setLayout(new GridLayout());
		parent.layout(true);

	}

	/**
	 * Create Configuration for Output Port
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createOutputPortConfig(Composite parent, EditPart part,
			boolean add) {

		GridData gridData1 = new GridData();
		gridData1.verticalSpan = 1;
		gridData1.horizontalSpan = 1;
		gridData1.horizontalAlignment = GridData.END;
		gridData1.verticalAlignment = GridData.END;
		gridData1.grabExcessHorizontalSpace = false;

		GridData gridData = new GridData();
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;				

		setTipMsg = new Label(parent, SWT.NONE);
		setTipMsg.setLayoutData(gridData);				
		setTipMsg.setText("========== Output Port Configuration ===========");

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		ViewOutputFile  = new Button(parent, SWT.NONE);
		if(AutosysFrameworkWizardPage.designselection == 0)
			ViewOutputFile.setText("Show Output File");	
		else if(AutosysFrameworkWizardPage.designselection == 1)
			ViewOutputFile.setText("Show Output Video");	
		ViewOutputFile.setLayoutData(gridData1);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;

		ViewOutputFile.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				
				if(AutosysFrameworkWizardPage.designselection == 0){
					String names = GeneralConfigure.getOutputpath();
					if(names.equalsIgnoreCase("")){
						AutosysApplication.console.println("#Error : E_GUI_031: Simulated Output file not found.", 2);
						return;
					}
					IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(names));							
					if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
						IWorkbenchPage page=  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						try {
							IDE.openEditorOnFileStore(page, fileStore);
						} catch (PartInitException e1) {							     
							e1.printStackTrace();
						}
					}	
					ViewOutputFile.setEnabled(false);
				} else if(AutosysFrameworkWizardPage.designselection == 1){
					try {
						if(!(new File(GeneralConfigure.outputVLCDFilepath).exists())){
							AutosysApplication.console.println("#Error : E_GUI_031: Simulated Output file not found.", 2);
							return;
						}
						Process proc = Runtime.getRuntime().exec("YUV.exe -a play -s yuv420 -p planar" +
								" -f progressive -o yuv -w 352 -h 288 -v iyuv -e exit -n "+GeneralConfigure.outputVLCDFilepath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				//				ViewOutputFile.setEnabled(false);

			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		//Check Simulation Completed or not.
		if(((AutosysFrameworkWizardPage.designselection == 0)
				&& (new File(GeneralConfigure.getOutputpath()).exists()))
				||(AutosysFrameworkWizardPage.designselection == 1)){
			ViewOutputFile.setEnabled(true);
		}else{
			ViewOutputFile.setEnabled(false);
		}

		parent.setLayout(gridLayout);
		parent.layout(true);
	}


	/**
	 * Create Configuration for Input Port.
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createInputPortConfig(Composite parent, EditPart part,	boolean add) {

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.makeColumnsEqualWidth = false;
		parent.setLayout(gridLayout);

		GridData gridData1 = new GridData();
		gridData1.verticalSpan = 1;
		gridData1.horizontalSpan = 1;
		gridData1.horizontalAlignment = GridData.END;
		gridData1.verticalAlignment = GridData.END;
		gridData1.grabExcessHorizontalSpace = false;

		GridData gridData = new GridData();
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;				

		setTipMsg = new Label(parent, SWT.NONE);
		setTipMsg.setLayoutData(gridData);				
		setTipMsg.setText("============== Input Port Configuration ===============");

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		EditSourceFile_1 = new Button(parent, SWT.NONE);
		if(AutosysFrameworkWizardPage.designselection == 0)
			EditSourceFile_1.setText("Show First Source Config File");
		else if(AutosysFrameworkWizardPage.designselection == 1)
			EditSourceFile_1.setText("Show Input Video");
		EditSourceFile_1.setLayoutData(gridData1);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		//For 2 channels
		if(GeneralConfigure.isTwoChannel()){
			EditSourceFile_2 = new Button(parent, SWT.NONE);
			EditSourceFile_2.setText("Show Second Source Config File");				
			EditSourceFile_2.setLayoutData(gridData1);

			EditSourceFile_2.addMouseListener(new MouseListener() {

				public void mouseUp(MouseEvent e) {


					if(GeneralConfigure.getInputpath2() != null) {
						File file = new File(GeneralConfigure.getInputpath2());
						if(!file.canExecute()){
							AutosysApplication.console.println("#Error : E_GUI_032: Input source file not found.", 2);
							return;
						}					
						EditSourceFile_2.setEnabled(false);

						//Open first input .ini file into eclipse editor.
						String names = GeneralConfigure.getInputpath2();
						if(names.equalsIgnoreCase("")){
							AutosysApplication.console.println("#Error : E_GUI_032: Input source file not found.", 2);
							return;
						}
						IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(names));							
						if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
							IWorkbenchPage page=  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							try {
								IDE.openEditorOnFileStore(page, fileStore);
							} catch (PartInitException e1) {							     
								e1.printStackTrace();
							}
						}				
						ViewOutputFile.setEnabled(false);
					} else {
						AutosysApplication.console.println("#Error : E_GUI_003: Autosys I/O Settings Error.", 2);
						return;
					}				
				}

				public void mouseDown(MouseEvent e) {
				}

				public void mouseDoubleClick(MouseEvent e) {
				}
			});

		}else{
			//For 1 channel
			if(AutosysFrameworkWizardPage.designselection == 0)
				EditSourceFile_1.setText("Show Source Config File");
		}

		//Open Input- 1 .ini Source file.
		EditSourceFile_1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if(AutosysFrameworkWizardPage.designselection == 0) {
					if(GeneralConfigure.getInputpath1() != null) {
						File file = new File(GeneralConfigure.getInputpath1());
						if(!file.canExecute()){
							AutosysApplication.console.println("#Error : E_GUI_032: Input source file not found.", 2);
							return;
						}					
						EditSourceFile_1.setEnabled(false);

						//Open first input .ini file into eclipse editor.
						String names = GeneralConfigure.getInputpath1();
						if(names.equalsIgnoreCase("")){
							AutosysApplication.console.println("#Error : E_GUI_032: Input source file not found.", 2);
							return;
						}
						IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(names));							
						if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
							IWorkbenchPage page=  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							try {
								IDE.openEditorOnFileStore(page, fileStore);
							} catch (PartInitException e1) {							     
								e1.printStackTrace();
							}
						}				
						ViewOutputFile.setEnabled(false);

					} else {
						AutosysApplication.console.println("#Error : E_GUI_003: Autosys I/O Settings Error.", 2);
						return;
					}		
				} else if(AutosysFrameworkWizardPage.designselection == 1) {
					try {
						Process proc = Runtime.getRuntime().exec("YUV.exe -a play -s yuv420 -p planar" +
								" -f progressive -o yuv -w 352 -h 288 -v iyuv -e exit -n "+GeneralConfigure.inputVLCDFilepath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});				
	}

	/**
	 * Create Configuration for Layer Mapper.
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createLayerMapperConfig(Composite parent, EditPart part,
			boolean add) {

		GridData gridData1 = new GridData();
		gridData1.horizontalIndent = 0;
		gridData1.horizontalSpan = 3;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		gridData.grabExcessVerticalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;

		Configuration = new Label(parent, SWT.NONE);
		Configuration.setText("========== Layer Mapper Configuration ===========");
		Configuration.setLayoutData(gridData1);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		AntennaPortslabel = new Label(parent, SWT.NONE);
		AntennaPortslabel.setText("Number Of Antenna Ports :");

		Portscombo = new Combo(parent, SWT.READ_ONLY);	
		Portscombo.add("1");
		Portscombo.add("2");
		Portscombo.add("3");
		Portscombo.add("4");
		Portscombo.select(0);		

		Portscombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {

				LMSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		new Label(parent, SWT.NONE);

		LMSaveButton = new Button(parent, SWT.NONE);
		LMSaveButton.setText("Save Configuration");
		LMSaveButton.redraw();
		LMSaveButton.setEnabled(false);

		LMSaveButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				saveConfiguration(amep);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		NumberofLayers = new Label(parent, SWT.NONE);
		NumberofLayers.setText("Number Of Layers :");

		NumberOfLayers = new Spinner(parent, SWT.READ_ONLY);
		NumberOfLayers.setBackground(ColorConstants.white);
		NumberOfLayers.setMaximum(4);
		NumberOfLayers.setMinimum(0);
		NumberOfLayers.setSelection(1);
		NumberOfLayers.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				LMSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		NumberofCodewords = new Label(parent, SWT.NONE);
		NumberofCodewords.setText("Size Of Codewords :");

		CodewordsScale = new Scale(parent, SWT.NONE);
		CodewordsScale.setMaximum(256);
		CodewordsScale.setMinimum(0);
		CodewordsScale.setSelection(8);
		CodewordsScale.setIncrement(8);

		scaleArea = new Label(parent, SWT.NONE);
		scaleArea.setText("Value");
		scaleArea.setLayoutData(gridData);
		scaleArea.setSize(150, 50);
		scaleArea.setText(String.valueOf(CodewordsScale.getSelection()));

		CodewordsScale.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				LMSaveButton.setEnabled(true);
				scaleArea.setText(String.valueOf(CodewordsScale.getSelection()));
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});	

		parent.setLayout(gridLayout);

		if(isLM_Properites())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}


	/**
	 * Create Configuration for Modulator
	 * @param parent
	 * @param part
	 * @param add
	 */
	private void createModulatorConfig(Composite parent, EditPart part,
			boolean add) {

		GridData gridData3 = new GridData();
		gridData3.verticalSpan = 1;
		gridData3.horizontalSpan = 1;
		gridData3.horizontalAlignment = GridData.BEGINNING;
		gridData3.grabExcessHorizontalSpace = false;				
		gridData3.verticalAlignment = GridData.BEGINNING;		

		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.heightHint = -1;
		gridData.widthHint = -1;
		gridData.grabExcessHorizontalSpace = false;

		GridData gridData6 = new GridData();
		gridData6.horizontalSpan = 3;
		ModConfiguration = new Label(parent, SWT.NONE);
		ModConfiguration.setText("========== Modulator Configuration ===========");
		ModConfiguration.setLayoutData(gridData6);

		setModulationTypeLabel = new Label(parent, SWT.NONE);
		setModulationTypeLabel.setText("Select Modulation Type");
		Mod_typeCombo = new Combo(parent, SWT.READ_ONLY);
		Mod_typeCombo.setLayoutData(gridData);
		Mod_typeCombo.add("QPSK");
		Mod_typeCombo.add("BPSK");
		Mod_typeCombo.add("16QAM");
		Mod_typeCombo.add("64QAM");
		Mod_typeCombo.select(0);

		setSaveButton = new Button(parent, SWT.NONE);
		setSaveButton.setText("Save Configuration");
		setSaveButton.setLayoutData(gridData3);
		setSaveButton.setEnabled(false);

		gridLayout = new GridLayout(2, false);
		parent.setLayout(gridLayout);
		parent.layout(true);

		Mod_typeCombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				setSaveButton.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		setSaveButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				saveConfiguration(amep);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		if(isMM_Properites())
			setVluesToParameters(part);
		else
			initialiseParameters(part);
	}

	public void setFocus() {

	}

	public static String getInput2() {
		return input2;
	}

	public static void setInput2(String input2) {
		Configure.input2 = input2;
	}

	public void setOFDM_Properites(boolean oFDM_Properites) {
		OFDM_Properites = oFDM_Properites;
	}

	public boolean isOFDM_Properites() {
		return OFDM_Properites;
	}

	public void setPrecoding_Properites(boolean precoding_Properites) {
		Precoding_Properites = precoding_Properites;
	}

	public boolean isPrecoding_Properites() {
		return Precoding_Properites;
	}

	public void setLM_Properites(boolean lM_Properites) {
		LM_Properites = lM_Properites;
	}

	public boolean isLM_Properites() {
		return LM_Properites;
	}

	public void setMM_Properites(boolean mM_Properites) {
		MM_Properites = mM_Properites;
	}

	public boolean isMM_Properites() {
		return MM_Properites;
	}	

	public boolean isCPI_Properites() {
		return CPI_Properites;
	}

	public void setCPI_Properites(boolean cPIProperites) {
		CPI_Properites = cPIProperites;
	}

	public static void setObjectwritten(boolean objectwritten) {
		Configure.objectwritten = objectwritten;
	}

	public static boolean isObjectwritten() {
		return objectwritten;
	}

	public static void setScrambler_Properites(boolean scrambler_Properites) {
		Scrambler_Properites = scrambler_Properites;
	}

	public static boolean isScrambler_Properites() {
		return Scrambler_Properites;
	}

	public static void setRM_Properties(boolean rM_written) {
		RM_Properties = rM_written;
	}

	public static boolean isRM_Properties() {
		return RM_Properties;
	}

	public static void setREM_Properties(boolean rEM_Properties) {
		REM_Properties = rEM_Properties;
	}

	public static boolean isREM_Properties() {
		return REM_Properties;
	}

	private void saveConfiguration(AutoModelEditPart amep) {
		try {
			fin = new FileInputStream(file);
			ois = new ObjectInputStream(fin);
			par_obj = (LTEParameters) ois.readObject();					
		}catch (Exception e1) {
			e1.printStackTrace();
		}

		if(amep.getModel() instanceof CyclicPrefix) {
			par_obj.setCP_Type("CP_Type: "+CPTypecombo.getItem(CPTypecombo.getSelectionIndex()));
			par_obj.setCPI_IFFT_Points("CPI_IFFT_Points: "+CPIIFFTtext.getText());
			par_obj.setCPI_Layer_nos("CPI_Layer_nos: "+CPILayerNocombo.getItem(CPILayerNocombo.getSelectionIndex()));
			par_obj.setL("l: "+String.valueOf(CPILspinner.getSelection()));
			par_obj.setCPI_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setCPI_Properites(true);
			CPISavebutton.setEnabled(false);
		} else if(amep.getModel() instanceof OFDM) {
			par_obj.setOFDM_IFFT_Points("OFDM_IFFT_Points: " +IFFTtext.getText());
			par_obj.setOFDM_Layer_nos("OFDM_IFFT_Layer_Nos: " + LayerNocombo.getItem(LayerNocombo.getSelectionIndex()));
			par_obj.setOFDM_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setOFDM_Properites(true);
			OFDMSavebutton.setEnabled(false);	
		} else if(amep.getModel() instanceof REM) {
			par_obj.setConfigurationType("Configuration_Type: "+REM_CP_Typecombo.getItem(REM_CP_Typecombo.getSelectionIndex()));
			if(normalButton.getSelection())
				par_obj.setBandwidth("Bandwidth: "+"15");
			else if(ExtendedButton.getSelection())
				par_obj.setBandwidth("Bandwidth: "+"7.5");
			par_obj.setREM_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setREM_Properties(true);
			REMSavebutton.setEnabled(false);	
		} else if(amep.getModel() instanceof Precoding) {
			par_obj.setPr_AntennaPorts("PreCoding_AntennaPorts: "+prPortscombo.getItem(prPortscombo.getSelectionIndex()));
			par_obj.setPr_NumberofLayers("PreCoding_LayerNos: "+String.valueOf(prNumberOfLayers.getSelection()));
			par_obj.setPr_CodeBookIndex("PreCoding_CodeBookIndex: "+String.valueOf(prCodeBookScale.getSelection()));
			par_obj.setPrecoding_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setPrecoding_Properites(true);
			prSaveButton.setEnabled(false);	
		} else if(amep.getModel() instanceof Scrambler) {
			par_obj.setnRNTI("nRNTI: "+nRNTItext.getText());
			par_obj.setNs("ns: "+nstext.getText());
			par_obj.setN_cell_id("N_cell_id_text: "+N_cell_id_text.getText());
			par_obj.setScarmblerwritten(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setScrambler_Properites(true);
			ScramSavebutton.setEnabled(false);
		} else if(amep.getModel() instanceof RateMatching) {
			par_obj.setNir("Nir: "+Nirtext.getText());
			par_obj.setQm("Qm: "+Qmcombo.getItem(Qmcombo.getSelectionIndex()));
			par_obj.setNl("Nl: "+Nlcombo.getItem(Nlcombo.getSelectionIndex()));
			par_obj.setRv_idx("Rv_idx: "+Rv_idxcombo.getItem(Rv_idxcombo.getSelectionIndex()));
			par_obj.setRM_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setRM_Properties(true);
			RMSavebutton.setEnabled(false); 
		} else if(amep.getModel() instanceof LayerMapper) {
			par_obj.setLm_AntennaPorts("Antenna_Port_Nos: "+Portscombo.getItem(Portscombo.getSelectionIndex()));
			par_obj.setLm_NumberofLayers("Layer_Nos: "+String.valueOf(NumberOfLayers.getSelection()));
			par_obj.setLm_CodeBookIndex("Codewords: "+String.valueOf(CodewordsScale.getSelection()));
			par_obj.setLm_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setLM_Properites(true);
			LMSaveButton.setEnabled(false); 
		} else if(amep.getModel() instanceof Modulator) {
			par_obj.setMod_type("Modulation_Type: "+Mod_typeCombo.getItem(Mod_typeCombo.getSelectionIndex()));
			par_obj.setMod_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			setSaveButton.setEnabled(false);
			setMM_Properites(true);
		} 

		try {
			setObjectwritten(true);
			fout = new FileOutputStream(file);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(par_obj);
			fin.close();
			ois.close();
			fout.close();
			oos.close();
			par_obj = null;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void setVluesToParameters(EditPart part) {

		try {
			fin = new FileInputStream(file);
			ois = new ObjectInputStream(fin);
			par_obj = (LTEParameters) ois.readObject();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(part.getModel() instanceof CyclicPrefix) {
			CPIIFFTtext.removeVerifyListener(listener);
			CPIIFFTtext.setText(par_obj.getCPI_IFFT_Points().substring(par_obj.getCPI_IFFT_Points().indexOf(":")+2).trim());
			CPIIFFTtext.addVerifyListener(listener);

			CPILayerNocombo.select(CPILayerNocombo.indexOf(par_obj.getCPI_Layer_nos().substring(par_obj.getCPI_Layer_nos().indexOf(":")+2)));
			CPTypecombo.select(CPTypecombo.indexOf(par_obj.getCP_Type().substring(par_obj.getCP_Type().indexOf(":")+2)));
			CPILspinner.setSelection(Integer.parseInt(par_obj.getL().substring(par_obj.getL().indexOf(":")+2)));
		} else if(part.getModel() instanceof OFDM) {
			IFFTtext.removeVerifyListener(listener);
			IFFTtext.setText(par_obj.getOFDM_IFFT_Points().substring(par_obj.getOFDM_IFFT_Points().lastIndexOf(":")+2));
			IFFTtext.addVerifyListener(listener);

			LayerNocombo.select(LayerNocombo.indexOf(par_obj.getOFDM_Layer_nos().substring(par_obj.getOFDM_Layer_nos().lastIndexOf(":")+2)));
		} else if(part.getModel() instanceof REM) {
			REM_CP_Typecombo.select(REM_CP_Typecombo.indexOf(par_obj.getConfigurationType().substring(par_obj.getConfigurationType().lastIndexOf(":")+2)));
			String bandwidth = par_obj.getBandwidth().substring(par_obj.getBandwidth().indexOf(":"));
			if(bandwidth.equalsIgnoreCase("15"))
				normalButton.setSelection(true);
			else if(bandwidth.equalsIgnoreCase("7.5"))
				ExtendedButton.setSelection(true);
		} else if(part.getModel() instanceof Precoding) {
			prPortscombo.select(prPortscombo.indexOf(par_obj.getPr_AntennaPorts().substring(par_obj.getPr_AntennaPorts().lastIndexOf(":")+2)));
			prNumberOfLayers.setSelection(Integer.parseInt(par_obj.getPr_NumberofLayers().substring(par_obj.getPr_NumberofLayers().lastIndexOf(":")+2)));
			prCodeBookScale.setSelection(Integer.parseInt(par_obj.getPr_CodeBookIndex().substring(par_obj.getPr_CodeBookIndex().lastIndexOf(":")+2)));
		} else if(part.getModel() instanceof Scrambler) {
			nRNTItext.removeVerifyListener(nRNTIlistener);
			nstext.removeVerifyListener(nslistener);
			N_cell_id_text.removeVerifyListener(N_cell_id_listener);

			nRNTItext.setText(par_obj.getnRNTI().substring(par_obj.getnRNTI().indexOf(":")+2));
			nstext.setText(par_obj.getNs().substring(par_obj.getNs().indexOf(":")+2));
			N_cell_id_text.setText(par_obj.getN_cell_id().substring(par_obj.getN_cell_id().indexOf(":")+2));

			nRNTItext.addVerifyListener(nRNTIlistener);
			nstext.addVerifyListener(nslistener);
			N_cell_id_text.addVerifyListener(N_cell_id_listener);
		} else if(part.getModel() instanceof RateMatching) {
			Qmcombo.select(Qmcombo.indexOf(par_obj.getQm().substring(par_obj.getQm().indexOf(":")+2)));
			Nlcombo.select(Nlcombo.indexOf(par_obj.getNl().substring(par_obj.getNl().indexOf(":")+2)));
			Rv_idxcombo.select(Rv_idxcombo.indexOf(par_obj.getRv_idx().substring(par_obj.getRv_idx().indexOf(":")+2)));
			Nirtext.setText(par_obj.getNir().substring(par_obj.getNir().indexOf(":")+2));
		} else if(part.getModel() instanceof LayerMapper) {
			Portscombo.select(Portscombo.indexOf(par_obj.getLm_AntennaPorts().substring(par_obj.getLm_AntennaPorts().lastIndexOf(":")+2)));
			NumberOfLayers.setSelection(Integer.parseInt(par_obj.getLm_NumberofLayers().substring(par_obj.getLm_NumberofLayers().lastIndexOf(":")+2)));
			CodewordsScale.setSelection(Integer.parseInt(par_obj.getLm_CodeBookIndex().toString().substring(par_obj.getLm_CodeBookIndex().toString().lastIndexOf(":")+2)));
			scaleArea.setText(String.valueOf(CodewordsScale.getSelection()));
		} else if(part.getModel() instanceof Modulator) {
			Mod_typeCombo.select(Mod_typeCombo.indexOf(par_obj.getMod_type().substring(par_obj.getMod_type().indexOf(":")+1).trim()));
		} 

		try {
			fin.close();
			ois.close();
			par_obj = null;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void initialiseParameters(EditPart part) {
		try {
			if(isObjectwritten()) {
				fin = new FileInputStream(file);
				ois = new ObjectInputStream(fin);
				par_obj = (LTEParameters) ois.readObject();
				fin.close();
				ois.close();
			}
			else
				par_obj = new LTEParameters();				
		}catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		if(part.getModel() instanceof CyclicPrefix) {
			par_obj.setCP_Type("CP_Type: NORMAL");
			par_obj.setCPI_IFFT_Points("CPI_IFFT_Points: 2");
			par_obj.setCPI_Layer_nos("CPI_Layer_nos: 1");
			par_obj.setL("l: 1");
			par_obj.setCPI_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
			CPIIFFTtext.removeVerifyListener(listener);
			CPIIFFTtext.setText("2");
			CPIIFFTtext.addVerifyListener(listener);
		} else if(part.getModel() instanceof OFDM) {
			par_obj.setOFDM_IFFT_Points("OFDM_IFFT_Points: " + "2");
			par_obj.setOFDM_Layer_nos("OFDM_IFFT_Layer_Nos: " +  "1");
			par_obj.setOFDM_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));

			IFFTtext.removeVerifyListener(listener);
			IFFTtext.setText("2");
			IFFTtext.addVerifyListener(listener);
		} else if(part.getModel() instanceof REM) {
			par_obj.setConfigurationType("Configuration_Type: "+"NORMAL");
			par_obj.setBandwidth("Bandwidth: 15");
			par_obj.setREM_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
		} else if(part.getModel() instanceof Precoding) {
			par_obj.setPr_AntennaPorts("PreCoding_AntennaPorts: "+"1");
			par_obj.setPr_NumberofLayers("PreCoding_LayerNos: "+"1");
			par_obj.setPr_CodeBookIndex("PreCoding_CodeBookIndex: "+"8");			
			par_obj.setPrecoding_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
		} else if(part.getModel() instanceof Scrambler) {
			par_obj.setnRNTI("nRNTI: "+"0");
			par_obj.setNs("ns: "+"0");
			par_obj.setN_cell_id("N_cell_id_text: "+"0");
			par_obj.setScarmblerwritten(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
		} else if(part.getModel() instanceof RateMatching) {
			par_obj.setNir("Nir: "+"300000");
			par_obj.setQm("Qm: "+"2");
			par_obj.setNl("Nl: "+"1");
			par_obj.setRv_idx("Rv_idx: "+"0");
			par_obj.setRM_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
		} else if(part.getModel() instanceof LayerMapper) {
			par_obj.setLm_AntennaPorts("Antenna_Port_Nos: "+"1");
			par_obj.setLm_NumberofLayers("Layer_Nos: "+"1");
			par_obj.setLm_CodeBookIndex("Codewords: "+"8");	
			par_obj.setLm_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
		} else if(part.getModel() instanceof Modulator) {
			par_obj.setMod_type("Modulation_Type: "+Mod_typeCombo.getItem(0));
			par_obj.setMod_written(String.valueOf(true));
			par_obj.setObject_written(String.valueOf(true));
		}
		try {
			fout = new FileOutputStream(file);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(par_obj);				
			setMM_Properites(true);
			setObjectwritten(true);
			fout.close();
			oos.close();
			par_obj = null;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public void setOS_Separator(String oS_Separator) {
		OS_Separator = oS_Separator;
	}


	public String getOS_Separator() {
		return OS_Separator;
	}


	public void createOutputFileView(Composite parent) {
		if(parent!=null) {
			while(ZERO != parent.getChildren().length){
				parent.getChildren()[0].dispose();		
			}
		}
		GridData gridData1 = new GridData();
		gridData1.verticalSpan = 1;
		gridData1.horizontalSpan = 1;
		gridData1.horizontalAlignment = GridData.END;
		gridData1.verticalAlignment = GridData.END;
		gridData1.grabExcessHorizontalSpace = false;

		GridData gridData = new GridData();
		gridData.verticalSpan = 1;
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;				

		setTipMsg = new Label(parent, SWT.NONE);
		setTipMsg.setLayoutData(gridData);				
		setTipMsg.setText("========== Output View ===========");

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		ViewOutputFile  = new Button(parent, SWT.NONE);
		ViewOutputFile.setText("Show Output File");				
		ViewOutputFile.setLayoutData(gridData1);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;

		ViewOutputFile.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				String names = GeneralConfigure.getOutputpath();
				if(names.equalsIgnoreCase("")){
					AutosysApplication.console.println("#Error : E_GUI_031: Simulated Output file not found.", 2);
					return;
				}
				IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(names));							
				if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
					IWorkbenchPage page=  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						IDE.openEditorOnFileStore(page, fileStore);
					} catch (PartInitException e1) {							     
						e1.printStackTrace();
					}
				}				
				ViewOutputFile.setEnabled(false);

			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		//Check Simulation Completed or not.
		if(new File(GeneralConfigure.getOutputpath()).exists()){
			ViewOutputFile.setEnabled(true);
		}else{
			ViewOutputFile.setEnabled(false);
		}

		parent.setLayout(gridLayout);
		parent.layout(true);
	}

}
