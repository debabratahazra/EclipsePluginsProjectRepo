package com.temenos.t24.tools.eclipse.basic.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import com.temenos.t24.tools.eclipse.basic.IDocViewProvider;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.views.calls.CallsView;
import com.temenos.t24.tools.eclipse.basic.views.callsHierarchy.CallsHierarchyView;
import com.temenos.t24.tools.eclipse.basic.views.inserts.InsertsView;
import com.temenos.t24.tools.eclipse.basic.views.labels.T24LabelsRegionsView;
import com.temenos.t24.tools.eclipse.basic.views.macrotemplate.MacrosView;
import com.temenos.t24.tools.eclipse.basic.views.macrotemplate.TemplatesView;
import com.temenos.t24.tools.eclipse.basic.views.t24unit.T24UnitTestView;
import com.temenos.t24.tools.eclipse.basic.views.todo.TodoView;
import com.temenos.t24.tools.eclipse.basic.views.variables.VariablesView;

/**
 * Initialises T24 BASIC perspective.
 * 
 * @author lfernandez
 * 
 */
public class T24BasicPerspectiveFactory implements IPerspectiveFactory {

    public static final String T24_BASIC_PERSPECTIVE_ID = "com.temenos.t24.tools.eclipse.basic.t24BasicPerspective";
    // ID of the actionSet - this is the T24Basic menu in the tool bar
    private static final String T24_ACTION_SET_ID = "com.temenos.t24.tools.eclipse.basic.actionSet";

    public void createInitialLayout(IPageLayout layout) {
        defineActions(layout);
        defineLayout(layout);
    }

    public void defineActions(IPageLayout layout) {
        /**
         * Add wizards shortcuts to the perspective. These shortcuts will appear
         * whenever the user switches to this perspective, right-click on an
         * item and selects New.
         */
        layout.addNewWizardShortcut("com.temenos.t24.newFileWizard");
        layout.addNewWizardShortcut("com.temenos.t24.TUnitTestGenerationWizard");
        layout.addNewWizardShortcut("com.temenos.t24.T24ObjectWizard");
        layout.addNewWizardShortcut("com.temenos.t24.selectTemplateWizard");
        layout.addNewWizardShortcut("com.temenos.t24.T24DataFileCreationWizard");
        layout.addNewWizardShortcut("com.temenos.t24.basic.wizards.T24BasicCategory");
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
        /** Associate the T24Basic menu to this perspective. */
        layout.addActionSet(T24_ACTION_SET_ID);
    }

    @SuppressWarnings("deprecation")
    public void defineLayout(IPageLayout layout) {
        // Editors are placed for free.
        String editorArea = layout.getEditorArea();
        // Create several folders (each can potentially contain a serval Views)
        // LEFT FOLDER
        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.22f, editorArea);
        topLeft.addView(IPageLayout.ID_RES_NAV);
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.50f, "topLeft");
        bottomLeft.addView("com.odcgroup.server.ui.views.ServerView");
        // TODO: The outline view will be added at a later phase
        // Note that it needs the editor implementing getAdapter()
        // left.addView(IPageLayout.ID_OUTLINE);
        // RIGHT FOLDER
        IFolderLayout right1 = layout.createFolder("right", IPageLayout.RIGHT, (float) .75, editorArea);
        right1.addPlaceholder(FTPClientImplConstants.REMOTE_VIEW_ID + "*");
        IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, (float) .80, editorArea);
        right.addPlaceholder(CallsView.VIEW_ID);
        right.addPlaceholder(InsertsView.VIEW_ID);
        right.addPlaceholder(T24LabelsRegionsView.VIEW_ID);
        right.addPlaceholder(MacrosView.VIEW_ID);
        right.addPlaceholder(TemplatesView.VIEW_ID);
        right.addPlaceholder(VariablesView.VIEW_ID);
        // added for phase 2
        
        
        // BOTTOM FOLDER
        IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.75, editorArea);
        bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
        bottom.addPlaceholder((new TodoView()).getViewID());
        bottom.addPlaceholder(T24UnitTestView.VIEW_ID);
        bottom.addPlaceholder(CallsHierarchyView.VIEW_ID);
        // bottom.addPlaceholder(T24DocumentView.VIEW_ID);
        
        IDocViewProvider provider = T24BasicPlugin.getDefault().getProvider();
        if (provider != null) {
            provider.enableView(right, bottom);
        }
    }
}
