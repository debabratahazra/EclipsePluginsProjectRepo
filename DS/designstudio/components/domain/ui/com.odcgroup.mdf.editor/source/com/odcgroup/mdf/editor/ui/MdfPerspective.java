package com.odcgroup.mdf.editor.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.odcgroup.mdf.editor.MdfCore;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini </a>
 */
public class MdfPerspective implements IPerspectiveFactory {

    /**
     * Constructor for MdfPerspective
     */
    public MdfPerspective() {
        super();
    }

    /**
     * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
     */
    public void createInitialLayout(IPageLayout layout) {
        //-- Get the editor area.
        String editorArea = layout.getEditorArea();

        //-- Top left: Resource Navigator view and Bookmarks view placeholder
        IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT,
                0.25f, editorArea);
        left.addView(MdfCore.PROJECTS_VIEW_ID);
        left.addView(IPageLayout.ID_RES_NAV);

        IFolderLayout bottomLeft = layout.createFolder("bottomLeft",
                IPageLayout.BOTTOM, 0.66f, "left");
        bottomLeft.addView(IPageLayout.ID_PROP_SHEET);

        //-- Bottom right: Task List & Problem view
        IFolderLayout bottomRight = layout.createFolder("bottomRight",
                IPageLayout.BOTTOM, 0.66f, editorArea);
        bottomRight.addView(IPageLayout.ID_PROBLEM_VIEW);
        bottomRight.addView(IPageLayout.ID_TASK_LIST);

        IFolderLayout topRight = layout.createFolder("topRight",
                IPageLayout.RIGHT, 0.75f, editorArea);
        topRight.addView(IPageLayout.ID_OUTLINE);

        layout.addPerspectiveShortcut(MdfCore.PERSPECTIVE_ID);
        layout.addShowViewShortcut(MdfCore.PROJECTS_VIEW_ID);
    }

}