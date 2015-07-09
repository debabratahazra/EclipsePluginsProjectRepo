package com.odcgroup.mdf.editor.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.WidgetFactory;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class TitleAreaPageContainer
	implements DialogPageContainer, PaintListener {

	private int TITLE_HMARGIN = 10;
	private int TITLE_VMARGIN = 5;
	private int widthHint = SWT.DEFAULT;
	private int heightHint = SWT.DEFAULT;

	private final DialogPageContainer container;
	private final DialogPage page;

	private Control control;
	private Composite messageArea;
	private Image headingImage;
	private String headingText;
	private Font titleFont;

	private Label messageImage;
	private Label messageText;

	/**
	 * Constructor for MdfModeEditorPageContainer
	 */
	public TitleAreaPageContainer(DialogPageContainer container, DialogPage page) {
		super();
		this.container = container;
		this.page = page;
		page.setContainer(this);

		headingImage =
			MdfPlugin.getDefault().getImage(MdfCore.FORM_BANNER);
		titleFont = JFaceResources.getHeaderFont();
		headingText = page.getTitle();
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#getCurrentPage()
	 */
	public DialogPage getCurrentPage() {
		return container.getCurrentPage();
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#updateTitleBar()
	 */
	public void updateTitleBar() {
		updateMessage();
		headingText = page.getTitle();
		if ((control != null) && !control.isDisposed()) {
			control.redraw();
		}
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#getShell()
	 */
	public Shell getShell() {
		return container.getShell();
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#updateButtons()
	 */
	public void updateButtons() {
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#updateMessage()
	 */
	public void updateMessage() {
		Image image = null;
		String message = page.getErrorMessage();
		int type = IMessageProvider.ERROR;

		if (message == null) {
			message = page.getMessage();
			type = page.getMessageType();
		}

		if (message == null) {
			message = page.getDescription();
			type = IMessageProvider.NONE;
		}

		if (message != null) {
			switch (type) {
				case IMessageProvider.NONE :
				case IMessageProvider.INFORMATION :
					break;
				case IMessageProvider.WARNING :
					image =
						JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
					break;
				case IMessageProvider.ERROR :
					image =
						JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR);
					break;
			}
		}

		showMessage(message, image);
		container.updateMessage();
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#setDirty(boolean)
	 */
	public void setDirty(boolean dirty) {
		container.setDirty(dirty);
	}

	public final void paintControl(PaintEvent event) {
		GC gc = event.gc;
		Control form = (Control) event.widget;
		paint(form, gc);
	}

	public Control createControl(Composite parent) {
        WidgetFactory widgetFactory = getWidgetFactory(); 
        
        Composite canvas = widgetFactory.createComposite(parent);
		canvas.addPaintListener(this);
		canvas.setLayout(new FormLayout());
		canvas.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				page.dispose();
			}

		});

		Composite formParent = widgetFactory.createComposite(canvas);
		formParent.setLayout(new GridLayout());
		createMessageArea(formParent);
		page.createControl(formParent);

		updateMessage();

		this.control = canvas;
		return canvas;
	}

	private void createMessageArea(Composite parent) {
        WidgetFactory widgetFactory = getWidgetFactory(); 
        
		messageArea = widgetFactory.createComposite(parent);
		messageArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		messageArea.setLayout(new GridLayout(2, false));

		messageImage = widgetFactory.createLabel(messageArea, null);
		messageImage.setLayoutData(
			new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		messageText = widgetFactory.createLabel(messageArea, null);
		messageText.setLayoutData(
			new GridData(
				GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));

		Composite sep = widgetFactory.createCompositeSeparator(messageArea);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		layoutData.heightHint = 1;
		sep.setLayoutData(layoutData);
	}

	private void showMessage(String message, Image image) {
		if (message == null) {
			message = ""; //$NON-NLS-1$
		}

		//Be sure there are always 2 lines for layout purposes
		if (message.indexOf('\n') == -1) {
			message = message + "\n "; //$NON-NLS-1$
		}

		messageImage.setImage(image);
		messageImage.setVisible(image != null);
		messageText.setText(message);
		messageText.setToolTipText(message);

		messageArea.layout();
	}

	private int getTitleHeight() {
		int imageHeight = 0;
		if (headingImage != null
			&& SWT.getPlatform().equals("motif") == false) {
			imageHeight = headingImage.getBounds().height;
		}
		GC gc = new GC(control);
		gc.setFont(titleFont);
		FontMetrics fm = gc.getFontMetrics();
		int fontHeight = fm.getHeight();
		gc.dispose();

		int height = fontHeight + TITLE_VMARGIN + TITLE_VMARGIN;
		return Math.max(height, imageHeight);
	}

	private void paint(Control form, GC gc) {
		int x = 0;
		int y = 0;

		if (SWT.getPlatform().equals("motif") == false) {
			gc.drawImage(headingImage, x, y);
		}

		gc.setForeground(getWidgetFactory().getForegroundColor());
		gc.setFont(titleFont);
		gc.drawText(headingText, TITLE_HMARGIN, TITLE_VMARGIN, true);
	}

	class FormLayout extends Layout {
		protected Point computeSize(
			Composite composite,
			int wHint,
			int hHint,
			boolean flushCache) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
				return new Point(wHint, hHint);
			}
			Control client = composite.getChildren()[0];
			Point csize = client.computeSize(widthHint, heightHint, flushCache);
			csize.y += getTitleHeight();
			return csize;
		}
		protected void layout(Composite composite, boolean flushCache) {
			Rectangle clientArea = composite.getClientArea();
			Control client = composite.getChildren()[0];
			if (client != null && !client.isDisposed()) {
				int theight = getTitleHeight();
				client.setBounds(
					clientArea.x,
					clientArea.y + theight,
					clientArea.width,
					clientArea.height - theight);
			}
		}
	}

    public WidgetFactory getWidgetFactory() {
        return container.getWidgetFactory();
    }
    
    /** 
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPageContainer#dispose()
     * //OCS-26284
     */
    public void dispose() {
    	if (control != null) {
    		control.dispose();
    	}
    }

}
