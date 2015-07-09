package com.temenos.t24.tools.eclipse.basic.actions.remote;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.jremote.MonitoredCompileAction;
import com.temenos.t24.tools.eclipse.basic.jremote.MonitoredExecuteAction;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestConstants;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestResponse;
import com.temenos.t24.tools.eclipse.basic.t24unit.T24UnitTestResponseBuilder;
import com.temenos.t24.tools.eclipse.basic.t24unit.TestFile;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.t24unit.T24UnitTestView;
import com.temenos.t24.tools.eclipse.basic.views.t24unit.T24UnitTestViewController;

/**
 * Class which delegates the T24 Unit test execution to it's lower layers and
 * processes the response returned. It is responsible for building up the view
 * with the response it received by passing it to the view controller object.
 * 
 * @author ssethupathi
 * 
 */
public class T24UnitTestActionDelegate {

    private T24UnitTestViewController viewController = T24UnitTestViewController.getInstance();
    private T24UnitTestResponseBuilder responseBuilder = T24UnitTestResponseBuilder.getInstance();
    private T24UnitTestView testView = null;
    private boolean isCompileTest;

    /**
     * Runs the tests which are supplied from the Eclipse launcher
     * 
     * @param files supplied files.
     */
    public void run(ArrayList<TestFile> files) {
        showView();
        initCompileTest();
        T24UnitTestResponse testResponse = new T24UnitTestResponse();
        testResponse.setTestsToRun(files.size());
        for (TestFile testFile : files) {
            Response response = performTest(testFile);
            testResponse.addTest(responseBuilder.buildTestFromResponse(testFile, response));
            testResponse.addExecutionTime(new Double(response.getActionTimeMillis()));
            viewController.setTestResponse(testResponse);
            rebuildView();
        }
    }

    /**
     * Performs one test at a time by calling the action delegate
     * 
     * @param file
     * @return Response
     */
    private Response performTest(TestFile file) {
        Response response = null;
        response = executeThroughJCA(file);
        return response;
    }

    private Response executeThroughJCA(TestFile file) {
        String basicFilenameNoPrefix = file.getFileName();
        if (isCompileTest && file.getFile() != null) {
            boolean saved = false;
            saved = RemoteOperationsManager.getInstance().saveFile(file.getRemoteSite(), file.getRemotePath(),
                    basicFilenameNoPrefix, file.getFile());
            if (!saved) {
                return notSavedResponse();
            }
            Response response = compileThroughJCA(file.getRemoteSite(), file.getRemotePath(), basicFilenameNoPrefix);
            if (!response.getPassed()) {
                return response;
            }
        }
        HashMap<Integer, String> fileToTest = new HashMap<Integer, String>();
        fileToTest.put(new Integer(1), basicFilenameNoPrefix);
        return executeUnitTest(file.getRemoteSite(), fileToTest);
    }

    /**
     * Opens the T24 Unit test view to display the results
     */
    private void showView() {
        IWorkbenchPage page = getActivePage();
        if (page == null) {
            return;
        }
        try {
            page.showView(T24UnitTestView.VIEW_ID);
        } catch (PartInitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        IViewReference[] viewReferences = page.getViewReferences();
        IViewPart viewPart;
        for (IViewReference viewReference : viewReferences) {
            viewPart = viewReference.getView(true);
            if (viewPart instanceof T24UnitTestView) {
                testView = (T24UnitTestView) viewPart;
                viewController.clearView();
                testView.refresh();
            }
        }
    }

    /**
     * Rebuilds T24 Unit test view with the current results
     */
    private void rebuildView() {
        if (testView != null) {
            testView.refresh();
        }
        return;
    }

    /**
     * Returns the active work page
     * 
     * @return
     */
    private IWorkbenchPage getActivePage() {
        IWorkbenchWindow awbw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IWorkbenchPage page = awbw.getActivePage();
        if (page != null) {
            return page;
        }
        return null;
    }

    private void initCompileTest() {
        String s = EclipseUtil.getPreferenceStore().getString(PluginConstants.T24UNIT_TEST_COMPILE);
        isCompileTest = Boolean.parseBoolean(s);
    }

    private Response executeUnitTest(RemoteSite remoteSite, HashMap<Integer, String> fileToTest) {
        MonitoredExecuteAction executeAction = new MonitoredExecuteAction();
        Response response = executeAction.execute(remoteSite, T24UnitTestConstants.T24UNIT_TEST_WRAPPER, fileToTest);
        return response;
    }

    private Response notSavedResponse() {
        String errMsg = "Unable to save file to server";
        Response errorResp = new Response();
        errorResp.setPassed(false);
        errorResp.setObject(errMsg);
        return errorResp;
    }

    private Response compileThroughJCA(RemoteSite remoteSite, String remotePath, String fileName) {
        MonitoredCompileAction compileAction = new MonitoredCompileAction();
        Response response = compileAction.execute(remoteSite, StringUtil.getFolderFromServerDirectory(remotePath), fileName);
        return response;
    }
}
