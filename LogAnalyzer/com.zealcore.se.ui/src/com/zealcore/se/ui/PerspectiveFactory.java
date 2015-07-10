/*
 * 
 */
package com.zealcore.se.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.zealcore.se.ui.views.LogmarkView;
import com.zealcore.se.ui.views.OverviewTimeclusters;
import com.zealcore.se.ui.views.SystemNavigator;

/**
 * Factory class for creating the ZealCore SystemExplorer Perspective
 */
public class PerspectiveFactory implements IPerspectiveFactory {

    public static final String PERSPECTIVE_ID = "com.zealcore.se.ui.PerspectiveFactory";

    private static final String LEFT_BOTTOM_FOLDER = "left-bottom";

    private static final String LEFT_MID_FOLDER = "left-mid";

    private static final String LEFT_FOLDER = "left";

    /**
     * {@inheritDoc}
     */
    public void createInitialLayout(final IPageLayout layout) {
        createLayout(layout);
    }

    private void createLayout(final IPageLayout layout) {
        final String editorArea = layout.getEditorArea();

        final IFolderLayout left = layout.createFolder(
                PerspectiveFactory.LEFT_FOLDER, IPageLayout.LEFT, 0.25f,
                editorArea);

        final IFolderLayout leftMid = layout.createFolder(
                PerspectiveFactory.LEFT_MID_FOLDER, IPageLayout.BOTTOM, 0.25f,
                PerspectiveFactory.LEFT_FOLDER);

        final IFolderLayout leftBottom = layout.createFolder(
                PerspectiveFactory.LEFT_BOTTOM_FOLDER, IPageLayout.BOTTOM,
                0.25f, PerspectiveFactory.LEFT_MID_FOLDER);

        left.addView(SystemNavigator.VIEW_ID);
        leftMid.addView(OverviewTimeclusters.VIEW_ID);
        leftMid.addView(LogmarkView.VIEW_ID);
        leftBottom.addView("org.eclipse.ui.views.PropertySheet");
        // layout.setFixed(true);
    }
}
