package com.odcgroup.workbench.editors.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * 
 * @author pkk 
 */
public class TabbedPropertyMessage extends Composite implements IResourceChangeListener {
	/** */
	private CLabel errorLabel;
	/** */
	private CLabel warningLabel;
	/** */
	private String errorMsg = null;
	/** */
	private String warnMsg = null;
	/** */
	private String eTooltip = null;
	/** */
	private String wTooltip = null;
	/** */
	private static final String BLANK = ""; //$NON-NLS-1$	
	/** */
	private TabbedPropertySheetWidgetFactory factory;
	
	private static int DRAW_FLAGS = SWT.DRAW_MNEMONIC | SWT.DRAW_TAB | SWT.DRAW_TRANSPARENT | SWT.DRAW_DELIMITER;

	/**
	 * Constructor for TabbedPropertyMessage.
	 * 
	 * @param parent
	 * @param factory
	 */
	public TabbedPropertyMessage(Composite parent, TabbedPropertySheetWidgetFactory factory) {
		super(parent, SWT.NO_FOCUS);
		this.factory = factory;

		this.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				boolean redraw = false;
				if (errorMsg == null || errorMsg.equals(BLANK)) {
					errorLabel.setVisible(false);
				} else {
					errorLabel.setVisible(true);
					redraw = true;
				}

				if (warnMsg == null || warnMsg.equals(BLANK)) {
					warningLabel.setVisible(true);
				} else {
					warningLabel.setVisible(true);
					redraw = true;
				}
				if (redraw) {
					drawTitleBackground(e);
				}
			}
		});

		factory.getColors().initializeSectionToolBarColors();
		setBackground(factory.getColors().getBackground());
		setForeground(factory.getColors().getForeground());

		FormLayout layout = new FormLayout();
		layout.marginWidth = 1;
		layout.marginHeight = 2;
		setLayout(layout);

		errorLabel = factory.createCLabel(this, BLANK, SWT.WRAP);
		errorLabel.setBackground(new Color[] { factory.getColors().getColor(IFormColors.H_GRADIENT_END),
				factory.getColors().getColor(IFormColors.H_GRADIENT_END) }, new int[] { 100 }, false);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(50, 0);
		data.height = 17;
		errorLabel.setLayoutData(data);
		errorLabel.setAlignment(SWT.LEFT);
		errorLabel.setVisible(false);

		warningLabel = factory.createCLabel(this, BLANK, SWT.WRAP);
		warningLabel.setBackground(new Color[] { factory.getColors().getColor(IFormColors.H_GRADIENT_END),
				factory.getColors().getColor(IFormColors.H_GRADIENT_START) }, new int[] { 100 }, true);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(errorLabel, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(100, 0);
		data.height = 17;
		warningLabel.setLayoutData(data);
		warningLabel.setAlignment(SWT.LEFT);
	}

	/**
	 * @param e
	 */
	protected void drawTitleBackground(PaintEvent e) {
		Rectangle bounds = getClientArea();

		errorLabel.setBackground(new Color[] { factory.getColors().getColor(IFormColors.H_GRADIENT_END),
				factory.getColors().getColor(IFormColors.H_GRADIENT_END) }, new int[] { 100 }, false);
		warningLabel.setBackground(new Color[] { factory.getColors().getColor(IFormColors.H_GRADIENT_END),
				factory.getColors().getColor(IFormColors.H_GRADIENT_START) }, new int[] { 100 }, true);
		Color bg = factory.getColors().getColor(IFormColors.H_GRADIENT_END);
		Color gbg = factory.getColors().getColor(IFormColors.H_GRADIENT_START);

		GC gc = e.gc;
		gc.setForeground(bg);
		gc.setBackground(gbg);
		gc.fillGradientRectangle(bounds.x, bounds.y, bounds.width, bounds.height, true);
		// background bottom separator
		gc.setForeground(factory.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE1));
		gc.drawLine(bounds.x, bounds.height - 2, bounds.x + bounds.width - 1, bounds.height - 2);
		gc.setForeground(factory.getColors().getColor(IFormColors.H_BOTTOM_KEYLINE2));
		gc.drawLine(bounds.x, bounds.height - 1, bounds.x + bounds.width - 1, bounds.height - 1);
	}
	
	/**
	 * @param error
	 * @param warn
	 */
	private void setErrorMessages(String error, String warn) {
		this.errorMsg = error;
		this.warnMsg = warn;
		int width = errorLabel.getClientArea().width;
		if (error != null && !error.trim().equals(BLANK)) {
			error = getTrimmedText(error, getImage(IMarker.SEVERITY_ERROR), width);
			errorLabel.setText(error);
			errorLabel.setImage(getImage(IMarker.SEVERITY_ERROR));
			if (eTooltip != null && !eTooltip.trim().equals(BLANK)){
				errorLabel.setToolTipText(eTooltip);
			}
		} else {
			errorLabel.setText(BLANK);
			errorLabel.setImage(null);
			errorLabel.setToolTipText(null);
		}
		if (warn != null && !warn.trim().equals(BLANK)) {
			warn = getTrimmedText(warn, getImage(IMarker.SEVERITY_WARNING), width);
			warningLabel.setText(warn);
			warningLabel.setImage(getImage(IMarker.SEVERITY_WARNING));
			if (wTooltip != null && !wTooltip.trim().equals(BLANK)){
				warningLabel.setToolTipText(wTooltip);
			}
		} else {
			warningLabel.setText(BLANK);
			warningLabel.setImage(null);
			warningLabel.setToolTipText(null);
		}
		redraw();
	}
	
	
	/**
	 * @param text
	 * @param image
	 * @param width
	 * @return
	 */
	private String getTrimmedText(String text, Image image, int width) {
		if (text != null && text.length()>0) {
			int extent = getTotalSize(image, text).x;
			if (extent > width) {
				GC gc = new GC(this);
				Point e = gc.textExtent(text, DRAW_FLAGS);
				int iWidth = image.getBounds().width;
				int factor = e.x / (text.length() - iWidth);
				int abs = Math.abs(width / factor);
				if ((abs-iWidth)> 0 && text.length() > (abs-iWidth)) {
					text = text.substring(0, abs - iWidth);
					text = text+" .....";
				}
			}
		}
		return text;
	}
	
	/**
	 * @param image
	 * @param text
	 * @return
	 */
	private Point getTotalSize(Image image, String text) {
		Point size = new Point(0, 0);
		if (image != null) {
			Rectangle r = image.getBounds();
			size.x += r.width;
			size.y += r.height;
		}			
		GC gc = new GC(this);
		if (text != null && text.length() > 0) {
			Point e = gc.textExtent(text, DRAW_FLAGS);
			size.x += e.x;
			size.y = Math.max(size.y, e.y);
			if (image != null) size.x += 5;
		} else {
			size.y = Math.max(size.y, gc.getFontMetrics().getHeight());
		}
		gc.dispose();		
		return size;
	}
	
	/**
	 * @param markers
	 */
	public void updateMessage(IMarker[] markers) {
		if (markers != null && markers.length > 0) {
			List<String> errorMsgs = new ArrayList<String>();
			List<String> warnMsgs = new ArrayList<String>();
			String message = null;
			for (IMarker marker : markers) {
				int severity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
				message = marker.getAttribute(IMarker.MESSAGE, "");
				if (severity == IMarker.SEVERITY_ERROR) {
					if (!errorMsgs.contains(message)) {
						errorMsgs.add(message);
					}
				} else if (severity == IMarker.SEVERITY_WARNING) {
					if (!warnMsgs.contains(message)) {
						warnMsgs.add(message);
					}
				}
			}				

			StringBuffer errorMsg = new StringBuffer();
			StringBuffer warnMsg = new StringBuffer();
			
			if (!errorMsgs.isEmpty()) {
				errorMsg.append("| ");
			}
			for (String string : errorMsgs) {
				errorMsg.append(string+" | ");
			}
			if (!warnMsgs.isEmpty()) {
				warnMsg.append("| ");
			}
			for (String string : warnMsgs) {
				warnMsg.append(string+" | ");
			}
			setTooltip(errorMsgs, warnMsgs);
			setErrorMessages(errorMsg.toString(), warnMsg.toString());
			return;		
		}
		setErrorMessages(null, null);
	}
	
	/**
	 * @param errorMsgs
	 * @param warnMsgs
	 */
	private void setTooltip(List<String> errorMsgs, List<String> warnMsgs) {
		StringBuffer error = new StringBuffer();
		for (String string : errorMsgs) {
			error.append("# "+string);
			if (errorMsgs.indexOf(string) != errorMsgs.size()-1) {
				error.append("\n");
			} 
		}
		
		StringBuffer warn = new StringBuffer();
		for (String string : warnMsgs) {
			warn.append("# "+string);
			if (warnMsgs.indexOf(string) != warnMsgs.size()-1) {
				warn.append("\n");
			} 
		}
		
		this.eTooltip = error.toString();
		this.wTooltip = warn.toString();
		
	}
	
	/**
	 * @param severity 
	 * @return Image
	 */
	private Image getImage(int severity) {
		String imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
		switch (severity) {
		case IMarker.SEVERITY_ERROR:
			imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
			break;
		case IMarker.SEVERITY_WARNING:
			imageName = ISharedImages.IMG_OBJS_WARN_TSK;
			break;
		default:
			imageName = ISharedImages.IMG_OBJS_INFO_TSK;
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageName);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
	}
}
