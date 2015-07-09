package com.temenos.t24.tools.eclipse.basic.views.inlineDocs.listener;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentTextConverter;
import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateDocConstants;

/**
 * class which extends the jface's popup dialog which helps to show the
 * information in that
 * 
 * @author sbharathraja
 * 
 */
public final class InfoPopup extends PopupDialog {

    /** The label control that displays the text. */
    private Text textAsAreaofPopUp;
    /** The String shown in the pop-up. */
    private String contents = "";
    /** Object of type {@link Rectangle} */
    private Rectangle rectangle = null;
    /** Action for get more information */
    private Action actionMoreInfo;
    /** Action for closing the pop-up window */
    private Action actionClose;
    /** point where the user hover the mouse on tree viewer */
    private Point position;
    /** flag for checking whether the more info action needed or not */
    private boolean isMoreInfoActionNeed = true;
    /** name of the subroutine currently accessed by user from in-line doc view */
    private String subRoutineName;
    /** instance of {@link InLineDocsHelpCache} */
    private InLineDocsHelpCache helpCache;

    /**
     * Default constructor
     * 
     * @param parent of type {@link Shell}
     * @param rectangle of type {@link Rectangle}
     * @param headerString of type String indicating the header
     * @param footerString of type String indicating the footer
     */
    @SuppressWarnings("deprecation")
    public InfoPopup(Shell parent, Rectangle rectangle, Point point, String headerString, String footerString) {
        super(parent, PopupDialog.HOVER_SHELLSTYLE, false, false, true, false, headerString, footerString);
        setShellStyle(getShellStyle() | SWT.RESIZE);
        this.rectangle = rectangle;
        this.position = point;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
     */
    protected void handleShellCloseEvent() {
        // Comment out the following if do not want any kind of animated effect.
        // doAnimation(getShell());
        super.handleShellCloseEvent();
    }

    /**
     * 
     * @see org.eclipse.jface.dialogs.PopupDialog#createTitleMenuArea(org.eclipse
     *      .swt.widgets.Composite)
     */
    protected Control createTitleMenuArea(Composite parentComposite) {
        Control ctrl = super.createTitleMenuArea(parentComposite);
        createActions();
        return ctrl;
    }

    /**
     * create actions for pop-up window
     */
    private void createActions() {
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO));
        actionMoreInfo = new Action("More Info", imageDescriptor) {

            public void run() {
                close();
                helpCache = new InLineDocsHelpCache();
                Rectangle rect = new Rectangle(410, 420, 400, 410);
                InfoPopup moreInfoPop = new InfoPopup(new Shell(), rect, position, "More Information About  : " + subRoutineName,
                        "Select and press ESC to close");
                moreInfoPop.setText(DocumentTextConverter.convertCarriageReturnToNewLineFeed(helpCache.getRoutineHelpText(
                        subRoutineName, GenerateDocConstants.NAME_OF_LARGE_COMMENT)));
                moreInfoPop.setIsMoreInfoActionNeed(false);
                moreInfoPop.open();
            }
        };
        imageDescriptor = ImageDescriptor.createFromImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR));
        actionClose = new Action("Close", imageDescriptor) {

            public void run() {
                handleShellCloseEvent();
            }
        };
    }

    @Override
    protected Point getInitialLocation(Point initialSize) {
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.PopupDialog#fillDialogMenu(org.eclipse.jface
     * .action.IMenuManager)
     */
    protected void fillDialogMenu(IMenuManager dialogMenu) {
        super.fillDialogMenu(dialogMenu);
        // action more info is added only when it needed
        if (isMoreInfoActionNeed)
            dialogMenu.add(actionMoreInfo);
        dialogMenu.add(actionClose);
    }

    /*
     * Create a text control for showing the info about a proposal.
     */
    protected Control createDialogArea(Composite parent) {
        textAsAreaofPopUp = new Text(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
        textAsAreaofPopUp.setText(contents);
        textAsAreaofPopUp.addFocusListener(new FocusAdapter() {

            public void focusLost(FocusEvent event) {
                close();
            }
        });
        // Use the compact margins employed by
        // PopupDialog.
        GridData gd = new GridData(GridData.BEGINNING | GridData.FILL_BOTH);
        gd.horizontalIndent = PopupDialog.POPUP_HORIZONTALSPACING;
        gd.verticalIndent = PopupDialog.POPUP_VERTICALSPACING;
        textAsAreaofPopUp.setLayoutData(gd);
        return textAsAreaofPopUp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.PopupDialog#adjustBounds()
     */
    protected void adjustBounds() {
        Point pt = getShell().getDisplay().getCursorLocation();
        getShell().setBounds(pt.x, pt.y, rectangle.width, rectangle.height);
    }

    /**
     * Method to set the text contents of the InfoPop dialog
     * 
     * @param textContents of type String indicating the message
     */
    public void setText(String textContents) {
        this.contents = textContents;
    }

    /**
     * set whether the more info action need or not
     * 
     * @param isNeed - true if needed, false otherwise.
     */
    public void setIsMoreInfoActionNeed(boolean isNeed) {
        this.isMoreInfoActionNeed = isNeed;
    }

    /**
     * set the subroutine name which going to help us while getting more info
     * about this subroutine
     * 
     * @param routineName - name of the subroutine without extension
     */
    public void setSubRoutineName(String routineName) {
        this.subRoutineName = routineName;
    }
}
