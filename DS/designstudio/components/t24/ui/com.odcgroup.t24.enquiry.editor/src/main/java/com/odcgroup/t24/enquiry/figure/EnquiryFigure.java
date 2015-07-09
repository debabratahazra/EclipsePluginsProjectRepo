package com.odcgroup.t24.enquiry.figure;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.SimpleLoweredBorder;
import org.eclipse.draw2d.SimpleRaisedBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.osgi.framework.Bundle;

import com.odcgroup.t24.enquiry.figure.tab.TabFolderFigure;
import com.odcgroup.t24.enquiry.figure.tab.TabItemFigure;

/**
 * 
 * @author phanikumark
 * 
 */
public class EnquiryFigure extends Figure {

	public static final String CONFIGURATION = "Configuration";
	public static final String DATA_OUTPUT = "Data Output";
	public static final String DATA_SELECTION = "Data Selection";
	public static final String DRILL_DOWN = "Drill Down";
	public static final String PRESENTATION = "Presentation";

	private Panel dstabPanel;
	private Panel dotabPanel;
	private Panel configPanel;
	private Panel drilldownPanel;
	private Panel prsntPanel;

	private final TabFolderFigure tabFolder;

	private SelectionContainerFigure psSelectionFigure;
	private SelectionContainerFigure csSelectionFigure;
	
	private HeadersContainerFigure headersFigure;
	private OutputColumnsContainerFigure columnsFigure;
	
	private ConfigurationContainerFigure toolsFigure;
	private ConfigurationContainerFigure routinesFigure;
	
	private DrilldownContainerFigure drilldownFigure;
	private TitlesContainerFigure titlesFigure;
	private FieldsContainerFigure fieldsFigure;
	
	private TargetFigure targetFigure;
	private WebServiceFigure webServiceFigure;
	private Point location = new Point();
	private ImageFigure javaRoutineAdd;
	private ImageFigure jbcRoutineAdd;
	private MouseListener listener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
		
		@Override
		public void mousePressed(MouseEvent event) {
			location.x = event.x;
			location.y =event.y;
		}
		
		@Override
		public void mouseDoubleClicked(MouseEvent arg0) {
			
		}
	};

	public EnquiryFigure() {
		setLayoutManager(new GridLayout());

		tabFolder = new TabFolderFigure();
		
		final TabItemFigure dstab = new TabItemFigure(tabFolder, DATA_SELECTION);
		final TabItemFigure dotab = new TabItemFigure(tabFolder, DATA_OUTPUT);
		final TabItemFigure drilldowntab = new TabItemFigure(tabFolder, DRILL_DOWN);
		final TabItemFigure configtab = new TabItemFigure(tabFolder, CONFIGURATION);
		final TabItemFigure prsnttab = new TabItemFigure(tabFolder, PRESENTATION);

		// Data Selection tab
		fillDataSelectionTab(dstab);		
		// Data output tab
		fillDataOutputTab(dotab);
		// fill drilldown tab
		fillDrillDownTab(drilldowntab);
		// Configuration tab
		fillConfigurationTab(configtab);
		// presentation tab
		fillPresentationTab(prsnttab);

		tabFolder.addItem(dotab);
		tabFolder.addItem(prsnttab);
		tabFolder.addItem(dstab);
		tabFolder.addItem(drilldowntab);
		tabFolder.addItem(configtab);
		
		tabFolder.select(dotab);
		add(tabFolder);	
		
		setConstraint(tabFolder, new GridData(GridData.FILL_BOTH));
		addMouseListener(listener);
	}
	
	/**
	 * @return the tabFolder
	 */
	public TabFolderFigure getTabFolder() {
		return tabFolder;
	}
	
	/**
	 * @param drilldownTab
	 */
	private void fillDrillDownTab(TabItemFigure drilldownTab) {
		drilldownPanel = new Panel();
		drilldownPanel.setBorder(getCustomBorder());

		final GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.horizontalSpacing = 5;
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;
		drilldownPanel.setLayoutManager(gridLayout);

		// drilldown container		
		GridData data = new GridData(GridData.FILL_BOTH);
		drilldownFigure = new DrilldownContainerFigure("Drill Downs:");
		drilldownFigure.setPreferredSize(new Dimension(100,100));
		gridLayout.setConstraint(drilldownFigure, data);
		drilldownPanel.add(drilldownFigure);
		
		drilldownTab.setContent(drilldownPanel);
	}

	/**
	 * @param configtab
	 */
	private void fillConfigurationTab(TabItemFigure configtab) {
		configPanel = new Panel();
		configPanel.setBorder(getCustomBorder());
		
		final GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.horizontalSpacing = 5;
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;

		configPanel.setLayoutManager(gridLayout);
		
		GridData data = new GridData(GridData.FILL_BOTH);
		// Tools section
		toolsFigure = new ConfigurationContainerFigure("Tools");
		gridLayout.setConstraint(toolsFigure, data);
		configPanel.add(toolsFigure);
		
		// routines section
		routinesFigure = new ConfigurationContainerFigure("Routines"){
			@Override
			protected void addImage(Bundle bundle) {
				final GridLayout gridLayout = new GridLayout(5, false);
				gridLayout.horizontalSpacing = 10;
				gridLayout.marginHeight = 2;
				gridLayout.marginWidth = 10;

				panel.setLayoutManager(gridLayout);
				Label javaLabel = new Label("Java");
				javaLabel.setLabelAlignment(PositionConstants.LEFT);
				javaLabel.setOpaque(true);
				javaLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
				panel.add(javaLabel);
				URL url = FileLocator.find(bundle, new Path("icons/add-icon.png"), null);
				javaRoutineAdd = new ImageFigure(ImageDescriptor.createFromURL(url).createImage());
				javaRoutineAdd.setAlignment(PositionConstants.RIGHT);
				panel.add(javaRoutineAdd);
				
				Label jBCLabel = new Label("JBC");
				jBCLabel.setLabelAlignment(PositionConstants.LEFT);
				jBCLabel.setOpaque(true);
				jBCLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
				panel.add(jBCLabel);
				jbcRoutineAdd = new ImageFigure(ImageDescriptor.createFromURL(url).createImage());
				jbcRoutineAdd.setAlignment(PositionConstants.RIGHT);
				panel.add(jbcRoutineAdd);
			}
		};
		gridLayout.setConstraint(routinesFigure, data);
		configPanel.add(routinesFigure);
		
		// target section
		targetFigure = new TargetFigure("Target");
		gridLayout.setConstraint(targetFigure, data);
		configPanel.add(targetFigure);
		
		// webservices section
		webServiceFigure = new WebServiceFigure();
		gridLayout.setConstraint(webServiceFigure, data);
		configPanel.add(webServiceFigure);
		
		configtab.setContent(configPanel);
	}
	
	private void fillPresentationTab(TabItemFigure prsntTab) {
		prsntPanel = new Panel();
		prsntPanel.setBorder(getCustomBorder());

		final GridLayout gridLayout = new GridLayout();
		gridLayout.horizontalSpacing = 2;
		
		prsntPanel.setLayoutManager(gridLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		// Enquiry Headers
		headersFigure = new HeadersContainerFigure("Titles");
		headersFigure.setPreferredSize(new Dimension(100,100));
		gridLayout.setConstraint(headersFigure, gd);
		prsntPanel.add(headersFigure);
		
		// Columns
		columnsFigure = new OutputColumnsContainerFigure("Columns");
		columnsFigure.setPreferredSize(new Dimension(100,100));
		gd = new GridData(GridData.FILL_BOTH);
		gridLayout.setConstraint(columnsFigure, gd);
		prsntPanel.add(columnsFigure);
		
		// footer
/*		footerFigure = new OutputContainerFigure("Footer", true,GridData.FILL_HORIZONTAL);
		gridLayout.setConstraint(footerFigure, new GridData(GridData.FILL_HORIZONTAL));
		dotabPanel.add(footerFigure);
*/		
		prsntTab.setContent(prsntPanel);
	}

	/**
	 * @param dotab
	 */
	private void fillDataOutputTab(TabItemFigure dataOutputTab) {
		dotabPanel = new Panel();
		dotabPanel.setBorder(getCustomBorder());

		final GridLayout gridLayout = new GridLayout();
		gridLayout.horizontalSpacing = 2;
		
		dotabPanel.setLayoutManager(gridLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		
		// Enquiry Headers
		titlesFigure = new TitlesContainerFigure("Titles");
		titlesFigure.setPreferredSize(new Dimension(100,100));
		gridLayout.setConstraint(titlesFigure, data);
		dotabPanel.add(titlesFigure);	
		
		fieldsFigure = new FieldsContainerFigure("Fields");
		fieldsFigure.setPreferredSize(new Dimension(100,100));
		data = new GridData(GridData.FILL_BOTH);
		gridLayout.setConstraint(fieldsFigure, data);
		dotabPanel.add(fieldsFigure);	
		
		
		dataOutputTab.setContent(dotabPanel);
	}

	/**
	 * 
	 * @param panel
	 */
	private void fillDataSelectionTab(final TabItemFigure dataSelectionTab) {
		dstabPanel = new Panel();
		dstabPanel.setBorder(getCustomBorder());

		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 2;
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;

		dstabPanel.setLayoutManager(gridLayout);
		GridData data = new GridData(GridData.FILL_BOTH);

		// Predefined Selection
		psSelectionFigure = new SelectionContainerFigure("Predefined Selection", true);	
		psSelectionFigure.setPreferredSize(new Dimension(100,100));	
		gridLayout.setConstraint(psSelectionFigure, data);
		dstabPanel.add(psSelectionFigure);

		// Custom Selection
		csSelectionFigure = new SelectionContainerFigure("Custom Selection", true);	
		csSelectionFigure.setPreferredSize(new Dimension(100,100));
		gridLayout.setConstraint(csSelectionFigure, data);
		dstabPanel.add(csSelectionFigure);
		
		dataSelectionTab.setContent(dstabPanel);
	}
	
	public IFigure getDataOutputTitlesContainer() {
		return titlesFigure.getContentPane();
	}
	
	public IFigure getDataOutputFieldsContainer() {
		return fieldsFigure.getContentPane();
	}
	
	public IFigure getFixedSelectionContainer() {
		return psSelectionFigure.getContentPane();
	}
	
	public ImageFigure getFixedSelectionAddIcon(){
		psSelectionFigure.getImageFigure().setToolTip(new Label("Add Fixed Selection"));
		return psSelectionFigure.getImageFigure();
	}
	
	public IFigure getCustomSelectionContainer() {
		return csSelectionFigure.getContentPane();
	}

	public ImageFigure getCustomSelectionAddIcon(){
		csSelectionFigure.getImageFigure().setToolTip(new Label("Add Custom Selection"));
		return csSelectionFigure.getImageFigure();
	}
	
	public IFigure getHeadersContainer() {
		return headersFigure.getContentPane();
	}
	
	public IFigure getTitlesContainer() {
		return titlesFigure.getContentPane();
	}
	
	public ImageFigure getHeadersAddIcon(){
		titlesFigure.getImageFigure().setToolTip(new Label("Add Header"));
		return titlesFigure.getImageFigure();
	}
	
	public IFigure getColumnsContainer() {
		return columnsFigure.getContentPane();
	}
	public ImageFigure getColumnsAddIcon(){
		fieldsFigure.getImageFigure().setToolTip(new Label("Add Field"));
		return fieldsFigure.getImageFigure();
	}

	public IFigure getToolsContainer() {
		return toolsFigure.getContentPane();
	}

	public ImageFigure getToolsAddIcon(){
		toolsFigure.getImageFigure().setToolTip(new Label("Add Tool"));
		return toolsFigure.getImageFigure();
	}

	public IFigure getRoutineContainer() {
		return routinesFigure.getContentPane();		
	}

	public ImageFigure getJavaRoutineAddIcon(){
		javaRoutineAdd.setToolTip(new Label("Add Java Routine"));
		return javaRoutineAdd;
	}

	public ImageFigure getJBCRoutineAddIcon(){
		jbcRoutineAdd.setToolTip(new Label("Add JBC Routine"));
		return jbcRoutineAdd;
	}

	public IFigure getTargetContainer() {
		return targetFigure;		
	}
	
	public ImageFigure getTargetAddIcon(){
		targetFigure.getImgFigure().setToolTip(new Label("Add Target Mapping"));
		return targetFigure.getImgFigure();
	}

	public IFigure getDrilldownContainer() {
		return drilldownFigure.getContentPane();
	}

	public ImageFigure getDrilldownAddIcon(){
		drilldownFigure.getImageFigure().setToolTip(new Label("Add DrillDown"));
		return drilldownFigure.getImageFigure();
	}

	public IFigure getWebServicesFigure() {
		return webServiceFigure;
	}
	
	public ImageFigure getWebServicesAddIcon() {
		webServiceFigure.getImgFigure().setToolTip(new Label("Add Web Service Name"));
		return webServiceFigure.getImgFigure();
	}
	
	private CompoundBorder getCustomBorder() {
		return new CompoundBorder(new SimpleLoweredBorder(4), new SimpleRaisedBorder(4));
	}
	
	@Override
	public boolean isCoordinateSystem() {
		return true;
	}
	
	public boolean isFocusInPredefinedContainer(){
		return getFixedSelectionContainer().getBounds().contains(location);
	}
	
	public boolean isFocusInCustomSelectionContainer(){
		return getCustomSelectionContainer().getBounds().contains(location);
	}
	
	public boolean isFocusInColumnContainer(){
		return getColumnsContainer().getBounds().contains(location);
	}
	
	public boolean isFocusInToolContainer(){
		return getToolsContainer().getBounds().contains(location);
	}
	
	public boolean isFocusInRoutineContainer(){
		return getRoutineContainer().getBounds().contains(location);
	}

	public boolean isFocusInTargetContainer(){
		return getTargetContainer().getBounds().contains(location);
	}
	
	public boolean isFocusInWebServiceContainer(){
		return getWebServicesFigure().getBounds().contains(location);
	}
	
	public boolean isFocusInHeadersContainer(){
		return getHeadersContainer().getBounds().contains(location);
	}

	public boolean isFocusInDrillDownContainer(){
		return getDrilldownContainer().getBounds().contains(location);
	}
}