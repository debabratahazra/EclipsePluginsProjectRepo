package com.tel.autosysframework.palette;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tel.autosysframework.actions.DesignSelection;
import com.tel.autosysframework.model.AutoModel;
import com.tel.autosysframework.model.AutosysElement;
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
import com.tel.autosysframework.model.MonitorArea;
import com.tel.autosysframework.model.VideoAnalyser;
import com.tel.autosysframework.model.VideoDisplay;
import com.tel.autosysframework.wizard.AutosysFrameworkWizardPage;

public class PaletteCreator {

	private static List createCategories(PaletteRoot root){
		List categories = new ArrayList();

		categories.add(createControlGroup(root));
		
		//new DesignSelection().design();
		if(AutosysFrameworkWizardPage.designselection == 0){
			//LTE
			categories.add(createLTEComponentsDrawer());
		}else if(AutosysFrameworkWizardPage.designselection == 1){
			//VLCD
			categories.add(createVLCDComponentsDrawer());
		}/*else{
			
		}
		*/
		
		categories.add(createPortsComponentsDrawer());

		return categories;
	}

	/*	static private PaletteContainer createComplexPartsDrawer(){
	PaletteDrawer drawer = new PaletteDrawer(AutosysMessages.AutosysPlugin_Category_ComplexParts_Label, ImageDescriptor.createFromFile(Circuit.class, "icons/can.gif")); //$NON-NLS-1$

	List entries = new ArrayList();

	CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
			AutosysMessages.AutosysPlugin_Tool_CreationTool_HalfAdder_Label,
			AutosysMessages.AutosysPlugin_Tool_CreationTool_HalfAdder_Description,
			AutosysDiagramFactory.getHalfAdderFactory(),
			ImageDescriptor.createFromFile(Circuit.class, "icons/halfadder16.gif"), //$NON-NLS-1$
			ImageDescriptor.createFromFile(Circuit.class, "icons/halfadder24.gif") //$NON-NLS-1$
	);
	entries.add(combined);

	CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
			AutosysMessages.AutosysPlugin_Tool_CreationTool_FullAdder_Label,
			AutosysMessages.AutosysPlugin_Tool_CreationTool_FullAdder_Description,
			AutosysDiagramFactory.getFullAdderFactory(),
			ImageDescriptor.createFromFile(Circuit.class, "icons/fulladder16.gif"), //$NON-NLS-1$
			ImageDescriptor.createFromFile(Circuit.class, "icons/fulladder24.gif") //$NON-NLS-1$
	);
	entries.add(combined);

	drawer.addAll(entries);
	return drawer;
}*/


	private static Object createVLCDComponentsDrawer() {
		PaletteDrawer drawer = new PaletteDrawer(
				"VLCD Components",
				ImageDescriptor.createFromFile(AutoModel.class, "icons/vlcd.jpg"));//$NON-NLS-1$

		List vlcdentries = new ArrayList();	
		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				"Monitor Area",
				"Creates a Monitor Area Module",
				new SimpleFactory(MonitorArea.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/monitor.jpg"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/monitor.jpg")//$NON-NLS-1$
		);
		vlcdentries.add(combined);
		
		combined = new CombinedTemplateCreationEntry(
				"Video Analyzer",
				"Creates a Video Analyzer Module",
				new SimpleFactory(VideoAnalyser.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/monitor.jpg"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/monitor.jpg")//$NON-NLS-1$
		);
		vlcdentries.add(combined);
		
		combined = new CombinedTemplateCreationEntry(
				"Video Display",
				"Creates a Video Display Module",
				new SimpleFactory(VideoDisplay.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/monitor.jpg"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/monitor.jpg")//$NON-NLS-1$
		);
		vlcdentries.add(combined);
		
		drawer.addAll(vlcdentries);
		return drawer;
	}

	/**
	 * Create Port Drawer
	 * @return drawer 
	 */
	private static PaletteContainer createPortsComponentsDrawer() {

		PaletteDrawer drawer = new PaletteDrawer(
				"Ports",
				ImageDescriptor.createFromFile(AutoModel.class, "icons/io_port.gif"));//$NON-NLS-1$
		List entries = new ArrayList();	

		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				"InputPort",
				"Creates a Input Port",
				new SimpleFactory(InputPort.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/inputport.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/inputport.gif")//$NON-NLS-1$
		);
		entries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"OutputPort",
				"Creates an OutputPort",
				new SimpleFactory(OutputPort.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/outputport.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/outputport.gif")//$NON-NLS-1$
		);
		entries.add(combined);		

		drawer.addAll(entries);

		return drawer;
	}


	/**
	 * Create Component Drawer
	 * @return drawer
	 */
	private static PaletteContainer createLTEComponentsDrawer(){

		PaletteDrawer drawer = new PaletteDrawer(
				"LTE Components",
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"));//$NON-NLS-1$

		List lteentries = new ArrayList();	

		/*CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
			"Tata",
			"Creates Tata Logo",
			new SimpleFactory(TATAlogo.class),
			ImageDescriptor.createFromFile(AutoModel.class, "icons/inputport.gif"),//$NON-NLS-1$
			ImageDescriptor.createFromFile(AutoModel.class, "icons/inputport.gif")//$NON-NLS-1$
	);
	entries.add(combined);*/

		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				"Cyclic Redundacy Check",
				"Creates a CRC Module",
				new SimpleFactory(CRC.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"Code Block Segmentation",
				"Creates a CSB Module",
				new SimpleFactory(CSB.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"Channel Coding",
				"Creates a Channel Coding Module",
				new SimpleFactory(ChannelCoding.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"Rate Matching",
				"Creates a Rate Matching Module",
				new SimpleFactory(RateMatching.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"Scrambler",
				"Creates a Scrambler Module",
				new SimpleFactory(Scrambler.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);


		combined = new CombinedTemplateCreationEntry(
				"Modulator",
				"Creates a Modulator Module",
				new SimpleFactory(Modulator.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"LayerMapper",
				"Creates a Layer Mapper Module",
				new SimpleFactory(LayerMapper.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"Precoding",
				"Creates a Precoding Module",
				new SimpleFactory(Precoding.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"Resource Element Mapper",
				"Creates a REM Module",
				new SimpleFactory(REM.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"OFDM",
				"Creates an OFDM Module",
				new SimpleFactory(OFDM.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				"Cyclic Prefix",
				"Creates a Cyclic Prefix Module",
				new SimpleFactory(CyclicPrefix.class),
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutoModel.class, "icons/comp.JPG")//$NON-NLS-1$
		);
		lteentries.add(combined);		

		drawer.addAll(lteentries);
		return drawer;
	}



	static private PaletteContainer createControlGroup(PaletteRoot root){
		PaletteGroup controlGroup = new PaletteGroup(
		"Control Group");

		List entries = new ArrayList();

		ToolEntry tool = new PanningSelectionToolEntry();
		entries.add(tool);
		root.setDefaultEntry(tool);

		PaletteStack marqueeStack = new PaletteStack("Marquee Tools", "", null); //$NON-NLS-1$
		marqueeStack.add(new MarqueeToolEntry());
		MarqueeToolEntry marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR, 
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED));
		marqueeStack.add(marquee);
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR, 
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED 
						| MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED));
		marqueeStack.add(marquee);
		marqueeStack.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
		entries.add(marqueeStack);

		tool = new ConnectionCreationToolEntry(
				"Connection",
				"Connection tool can be used to connect the various module parts",
				null,
				ImageDescriptor.createFromFile(AutosysElement.class, "icons/connection16.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(AutosysElement.class, "icons/connection24.gif")//$NON-NLS-1$
		);
		entries.add(tool);
		controlGroup.addAll(entries);
		return controlGroup;
	}

	public static PaletteRoot createPalette() {
		PaletteRoot AutosysPalette = new PaletteRoot();
		AutosysPalette.addAll(createCategories(AutosysPalette));
		return AutosysPalette;
	}
}
