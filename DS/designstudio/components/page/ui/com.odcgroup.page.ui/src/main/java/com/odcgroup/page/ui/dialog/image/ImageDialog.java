package com.odcgroup.page.ui.dialog.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.model.corporate.ImageFileDescriptor;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * A Dialog to select images one or more images
 * based on a list of image descriptors.
 * 
 * @author atr
 */
public class ImageDialog extends TitleAreaDialog {

	/** The image descriptor to display */
	private List<ImageDescriptor> imageDescriptors;

	/** The image seleted by the user */
	private List<ImageDescriptor> selectedImages;

	/** The image shown the upper left corner of the dialog */
	private Image titleImage;

	/** true if this support for multi-selection is set, default is false */
	private boolean multi = false;
	
	/** true if the sub part to add images shall be displayed */
	private boolean mayAddImages = false;
	
	/** contains the name of a regular image filename */
	private Text filenameTxt = null;
	
	/** */
	private List<Thumbnail> thumbnails = new ArrayList<Thumbnail>();
	
	/** */
	private Composite chooser;

	/**
	 * 
	 */
	private Map<String, Image> imageCache = new LinkedHashMap<String, Image>();

	/**
	 * @return Image
	 */
	private Image getTitleImage() {
		Image tmpImage = PageUIPlugin.getImageDescriptor("icons/images.png").createImage();
		int width = tmpImage.getBounds().width;
		final int height = tmpImage.getBounds().height;
		titleImage = new Image(getShell().getDisplay(), 66, 75);
		GC gc = new GC(titleImage);
		gc.drawImage(tmpImage, 0, 0, width, height, 5, 5, 56, 56);
		gc.dispose();
		tmpImage.dispose();
		return titleImage;
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Image Chooser");
		newShell.setSize(615, 570);
	}

	/**
	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 */
	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle /*| SWT.RESIZE | SWT.MAX*/);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();

		setTitleImage(getTitleImage());
	}

	/**
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Select an Image");
		setMessage("Click on a thumbnail to select an image", IMessageProvider.INFORMATION);
		return contents;
	}
	
	/**
	 * 
	 */
	private void createThumbnails() {
		thumbnails.clear();
		final Thumbnail[] lastSelected = {null};
		for (ImageDescriptor descriptor : this.imageDescriptors) {
			final ImageDescriptor id = descriptor;
			final Image image = ImageUtils.getImageFromDescriptor(getShell().getDisplay(), imageCache, descriptor);
			Thumbnail thumbnail = new Thumbnail(chooser, SWT.FOCUSED, image, descriptor.getName()) {
				protected void thumbnailSelected(boolean selection) {
					if (!multi) {
						// single selection
						Thumbnail prev = lastSelected[0];
						if (prev != null) {
							prev.setSelection(false);
						}
						selectedImages.clear();
						lastSelected[0] = this;
						selectedImages.add(id); 
					} else {
						// multi selection
					}
				}
			};			
			thumbnails.add(thumbnail);
		}
	}
	
	/**
	 * Adds a new regular image descriptor given a filename and returns it
	 * @param filename
	 * @return ImageDescriptor
	 */
	private ImageDescriptor addRegularImage(String filename) {
		ImageFileDescriptor fd = new ImageFileDescriptor("",filename);
		int pos = filename.lastIndexOf('.');
		String imageName = filename;
		if (pos != -1) {
			imageName = filename.substring(0,pos);
		}
		ImageDescriptor id = new ImageDescriptor(imageName, fd, false);
		imageDescriptors.add(id);
		Collections.sort(imageDescriptors, new Comparator<ImageDescriptor>(){
			public int compare(ImageDescriptor left, ImageDescriptor right) {
				return left.getName().compareToIgnoreCase(right.getName());
			}			
		});		
//		for (Control ctrl : chooser.getChildren()) {
//			ctrl.dispose();
//		}
//		createThumbnails();
//		chooser.layout(true);
		return id;
	}
	
	/**
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(final Composite parent) {
		final Composite root = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		root.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		
		final ScrolledComposite sc = new ScrolledComposite(root, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 510;
		sc.setLayoutData(gridData);
		
		chooser = new Composite(sc, SWT.NONE);
		GridLayout chooserGridLayout = new GridLayout(8, true);
		chooserGridLayout.horizontalSpacing = 8;
		chooserGridLayout.verticalSpacing = 8;
		chooser.setLayout(chooserGridLayout);
		chooser.setBackground(ColorConstants.white);

		sc.setLayoutData(gridData);
		sc.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		sc.setContent(chooser);
		// sc.setExpandVertical(true);
		// sc.setExpandHorizontal(true);
		sc.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle r = sc.getClientArea();
				sc.setMinSize(chooser.computeSize(r.width, SWT.DEFAULT));
				// chooserGridLayout.numColumns = r.width / 70;
				// chooser.layout();
				// System.out.println(r);
			}
		});
		
		if (mayAddImages) {
		
			Group grp = new Group(root, SWT.NONE);
			grp.setText("Additional Regular Image");
			grp.setLayout(new GridLayout(3, false));
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			//gridData.heightHint = 250;
			grp.setLayoutData(gridData);
			
			Label lbl = new Label(grp, SWT.NONE);
			lbl.setText("Filename:");
			filenameTxt = new Text(grp, SWT.BORDER);
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			filenameTxt.setLayoutData(gridData);
//			Button addBtn = new Button(grp,SWT.PUSH);
//			addBtn.setText("Add");
//			addBtn.addSelectionListener(new SelectionAdapter(){
//				public void widgetSelected(SelectionEvent e) {
//					addImage(txt.getText().trim());
//				}
//			});
		}

		selectedImages = new ArrayList<ImageDescriptor>();
		
		createThumbnails();
		chooser.pack();
		sc.layout();
		
		return root;
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String, boolean)
	 */
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		String newLabel = label;
		if (mayAddImages && id == Dialog.OK) {
			newLabel = "Add";
		}
		return super.createButton(parent, id, newLabel, defaultButton);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if (filenameTxt != null) {
			String value = filenameTxt.getText().trim();
			if (StringUtils.isNotEmpty(value)) {
				ImageDescriptor id = addRegularImage(value);
				selectedImages.add(id);
			}
		}
		super.okPressed();
	}

	/**
	 * @see org.eclipse.jface.dialogs.TrayDialog#close()
	 */
	public boolean close() {
		if (titleImage != null) {
			titleImage.dispose();
		}
		if (imageCache != null) {
			for (Image img : imageCache.values()) {
				img.dispose();
			}
			imageCache.clear();
		}
		return super.close();
	}

	/**
	 * @param descriptors
	 */
	public void setImageDescriptors(List<ImageDescriptor> descriptors) {
		this.imageDescriptors = descriptors;

	}

	/**
	 * @return the list of selected image descriptor
	 */
	public List<ImageDescriptor> getSelectedDescriptors() {
		return this.selectedImages;
	}

	/**
	 * @param parentShell
	 * @param mayAddImages
	 */
	public ImageDialog(Shell parentShell, boolean mayAddImages) {
		super(parentShell);
		this.mayAddImages = mayAddImages;
	}
	
	
	/**
	 * A simple Thumbnail class to display image 
	 */
	private class Thumbnail {
		
		/** The image to display */
		private Image image;

		/** The name of the image */
		private String imgName;
		
		/** The wrapped composite widget */
		private Composite composite;
		
		/** indicates if the thumbnail is selected of not */
		private boolean selection = false;
		
		/**
		 * Draw the thumbnail image
		 * @param event
		 */
 		private void doPaint(Event event) {
			Rectangle rect = composite.getClientArea();
			Rectangle b = composite.getBounds();
			rect.height -= 20;
			rect.width -= 20;
			
			if (image != null) {
				// draw the image above its name
				Rectangle bounds = image.getBounds();
				int x = 0, y = 0, width = 0, height = 0;
//				if (bounds.width > bounds.height) {
//					width = rect.width;
//					height = bounds.height * rect.height / bounds.width;
//					y = (rect.height - height) / 2;
//				} else {
//					height = rect.height;
//					width = bounds.width * rect.width / bounds.height;
//					x = (rect.width - width) / 2;
//				}
				/*
				 *  Center the image in the client area
				 *  try to keep the original dimension of the image.
				 *  if, not possible, the image is stretch client area
				 */
				if (bounds.width < b.width) {
					width = bounds.width;
					x = (b.width - width) / 2;
				} else {
					width = b.width;
				}
				if (bounds.height < rect.height) {
					height = bounds.height;
					y = (rect.height - height) / 2;
				} else {
					height = rect.height;
				}

				Color backgroundColor = null;
//				if (selection) {
//					x = rect.x; y = rect.y;
//					width = b.width-1;
//					height = rect.height;
//					event.gc.setLineWidth(2);
//					event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_BLUE));
//					event.gc.drawLine(x,y,x+width,y);
//					event.gc.drawLine(x,y+height, x+width, y+height);
//					event.gc.drawLine(x,y,x,y+height);
//					event.gc.drawLine(x+width,y, x+width, y+height);
//					
//				} else {
					backgroundColor = event.display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
//				}
				event.gc.setBackground(backgroundColor);
				event.gc.fillRectangle(0, 0, b.width, b.height);
				event.gc.drawImage(image, 0, 0, bounds.width, bounds.height, x, y, width, height);

				// center the image name
				Point textSize = event.gc.textExtent(imgName);
		        int tx = 1;
		        if (textSize.x < composite.getSize().x) {
		        	tx = (composite.getSize().x - textSize.x)/2;
		        }
		        event.gc.drawText(imgName, tx, rect.height + 4);

			} else {
				// draw a cross above the image name when the image is not available
				event.gc.setBackground(event.display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				event.gc.fillRectangle(0, 0, b.width, b.height);

				// draw a red cross
				Color fgColor = event.gc.getForeground();
				event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_RED));
				event.gc.setLineWidth(1);
//				int x = (b.width - 16) / 2;
//				int y = (rect.height - 16) / 2;
//				event.gc.drawLine(x,y,x+16,y+16);
//				event.gc.drawLine(x,y+16, x+16, y);
				int x = rect.x+1; int y = rect.y+1;
				int width = b.width-2;
				int height = rect.height-2;
				event.gc.drawLine(x,y,x+width,y+height);
				event.gc.drawLine(x,y+height, x+width, y);
				
				event.gc.setForeground(fgColor);

				Point textSize = event.gc.textExtent(imgName);
		        int tx = 1;
		        if (textSize.x < composite.getSize().x) {
		        	tx = (composite.getSize().x - textSize.x)/2;
		        }
		        event.gc.drawText(imgName, tx, rect.height + 4);
			}
			
			if (selection) {
				event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_BLUE));
				event.gc.setLineWidth(2);
			} else {
				event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_GRAY));
				event.gc.setLineWidth(1);
			}
			
			int x = rect.x+1; int y = rect.y+1;
			int width = b.width-2;
			int height = rect.height-2;
			event.gc.drawLine(x,y,x+width,y);
			event.gc.drawLine(x,y+height, x+width, y+height);
			event.gc.drawLine(x,y,x,y+height);
			event.gc.drawLine(x+width,y, x+width, y+height);

			
		}
		
		
		/**
		 * Draw the thumbnail image
		 * @param event
		 */
		private void doMouseUp(Event event) {
			int onlyButton1 = SWT.BUTTON1 | ~SWT.BUTTON2 | ~SWT.BUTTON3;
			int modifier = SWT.CTRL | SWT.ALT | SWT.SHIFT | SWT.COMMAND;
			if (((event.stateMask & onlyButton1) != 0) && ((event.stateMask & modifier) == 0)) {
				selection = !selection;
				thumbnailSelected(selection);
				composite.redraw();
			}
		}
		
		
		/**
		 * @param parent
		 * @param style
		 */
		private void createContents(Composite parent, int style) {
			
			composite = new Composite(parent, style);
			composite.setToolTipText(imgName);
			
			// install a paint listener
			composite.addListener(SWT.Paint, new Listener(){
				public void handleEvent(Event event) {
					doPaint(event);
				}
			});

			// install a mouse-up listener
			composite.addListener(SWT.MouseUp, new Listener(){
				public void handleEvent(Event event) {
					doMouseUp(event);
				}
			});
			
		}
		
		/**
		 * receives notification when the thumbnail is (de)selected
		 * @param selection current value
		 */
		protected void thumbnailSelected(boolean selection) {
		}
			
		/**
		 * 
		 */
		public void dispose() {
		}
		
		/**
		 * 
		 */
		public void redraw() {
			if (!composite.isDisposed()) {
				composite.redraw();
			}
		}
		
		/**
		 * Sets the selection of this thumbnail
		 * @param value
		 */
		public void setSelection(boolean value) {
			this.selection = value;
			composite.redraw();
		}
		
		/**
		 * @return {@code true} if the thumbnail is selected, otherwise 
		 *         it returns {@code false}
		 */
		public boolean getSelection() {
			return this.selection;
		}

		/**
		 * @param parent the parent composite
		 * @param style the style
		 * @param image the image to display
		 * @param imageName the name of the image
		 */
		public Thumbnail(Composite parent, int style, Image image, String imageName) {
			this.imgName = imageName;
			this.image = image;
			createContents(parent, style);
		}
	}

}
