package com.temenos.t24.tools.eclipse.basic.views.checkDependency;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.jremote.IJremoteClient;
import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnectionType;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.Protocol;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteConnectionFactory;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitePlatform;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItem;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItemCollection;
import com.temenos.t24.tools.eclipse.basic.remote.data.RemoteInstallerHelper;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;

/** Helper class for CheckDependencyView */

public class CheckDependencyViewHelper {

    private static CheckDependencyViewHelper HELPER = new CheckDependencyViewHelper();
    private int sourceItemsCount = 0;
    private int dataItemsCount = 0;
    private int componentsCount = 0;
    private int productsCount = 0;
    private int totalUpdatesCount = 0;
    private List<T24ItemDetail> sourceItemDetails = null;
    private List<T24ItemDetail> dataItemdetails = null;

    private CheckDependencyViewHelper() {
    }

    /**
     * Returns this instance.
     * 
     * @return instance.
     */
    public static CheckDependencyViewHelper getInstance() {
        return HELPER;
    }

    public T24DependencyDetail getItemDetails(InstallableItemCollection collection, String relNo) {
        if (collection == null) {
            return null;
        }
        List<InstallableItem> sourceItems = collection.getSourceItems();
        List<InstallableItem> dataItems = collection.getDataItems();
        if (sourceItems.size() == 0 && dataItems.size() == 0) {
            return new T24DependencyDetail();
        }
        String[] args = new String[5];
        args[0] = relNo;
        args[1] = buildItemList(collection);
        args[2] = "";
        args[3] = "";
        args[4] = "";
        JSubroutineParameters params = RemoteInstallerHelper.getParameters(args);
        JSubroutineParameters params1 = params;
        String remotePgm = PluginConstants.CHECK_DEPENDENCY;
        RemoteSite site = getRemoteSite(relNo);
        if (site == null) {
            resetCount();
            return getEmptyList();
        }
        params = executeRemoteSubroutine(params, remotePgm, site);
        if (params == null) {
            boolean started = false;
            started = startAgent(site);
            if (started) {
                params = executeRemoteSubroutine(params1, remotePgm, site);
                if (params == null) {
                    showErrorMessage(site, relNo);
                    resetCount();
                    return getEmptyList();
                } else {
                    return buildList(params);
                }
            } else {
                showErrorMessage(site, relNo);
                resetCount();
                return getEmptyList();
            }
        } else {
            return buildList(params);
        }
    }

    private T24DependencyDetail buildList(JSubroutineParameters params) {
        String[] resultArray = RemoteInstallerHelper.getStringParams(params);
        String result = resultArray[2];
        String totalUpdates = resultArray[3];
        return buildItemsList(result, totalUpdates);
    }

    private boolean startAgent(RemoteSite site) {
        boolean started = false;
        IT24FTPClient client = null;
        client = RemoteConnectionFactory.getInstance().createRemoteConnection(site).getFtpClient();
        if (connect(client)) {
            started = client.startAgent();
        }
        return started;
    }

    private boolean connect(IT24FTPClient client) {
        boolean isConnected = false;
        if (client == null) {
            return false;
        }
        if(client.isConnected()){
            return true;
        }
        isConnected = client.connect();
        return isConnected;
    }

    private void showErrorMessage(RemoteSite site, String relNo) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window == null) {
            final T24MessageDialogThread dialogThread = new T24MessageDialogThread(relNo);
            Display.getDefault().syncExec(new Runnable() {

                public void run() {
                    Thread thread = new Thread(dialogThread);
                    thread.start();
                    while (dialogThread.okPressed == false) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            return;
        }
    }

    private static class T24MessageDialogThread implements Runnable {

        private boolean okPressed = false;
        private String releaseNo = "";

        public T24MessageDialogThread(String relNo) {
            this.releaseNo = relNo;
        }

        public void run() {
            Display display = PlatformUI.createDisplay();
            final Shell shell = new Shell(display);
            T24MessageDialog dialog = new T24MessageDialog(shell, "T24 Error Message Dialog",
                    "Unable to connect to jagent for release " + releaseNo + " . Please mail to updates@temenos.com .",
                    MessageDialog.ERROR);
            dialog.open();
            okPressed = true;
            return;
        }
    }

    private T24DependencyDetail getEmptyList() {
        T24DependencyDetail dependencyDetail = new T24DependencyDetail();
        List<T24ItemDetail> itemDetails = new ArrayList<T24ItemDetail>();
        List<T24UpdateDetails> updateDetails = new ArrayList<T24UpdateDetails>();
        dependencyDetail.setItemDetails(itemDetails);
        dependencyDetail.setUpdateDetails(updateDetails);
        return dependencyDetail;
    }

    public void resetCount() {
        sourceItemsCount = 0;
        dataItemsCount = 0;
        componentsCount = 0;
        productsCount = 0;
        totalUpdatesCount = 0;
    }

    public int getSourceItemCount() {
        return sourceItemsCount;
    }

    public int getDataItemCount() {
        return dataItemsCount;
    }

    public int getComponentsCount() {
        return componentsCount;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public int getTotalUpdates() {
        return totalUpdatesCount;
    }

    private T24DependencyDetail buildItemsList(String result, String totalUpdates) {
        List<T24ItemDetail> itemDetails = new ArrayList<T24ItemDetail>();
        sourceItemDetails = getSourceItems(result);
        dataItemdetails = getDataItems(result);
        sourceItemsCount = sourceItemDetails.size();
        dataItemsCount = dataItemdetails.size();
        itemDetails.addAll(sourceItemDetails);
        itemDetails.addAll(dataItemdetails);
        componentsCount = getTotalComponents(sourceItemDetails, dataItemdetails);
        productsCount = getTotalProducts(sourceItemDetails, dataItemdetails);
        totalUpdatesCount = Integer.parseInt(totalUpdates);
        List<T24UpdateDetails> updateDetails = extractComponentDetails(result);
        T24DependencyDetail dependencyDetail = new T24DependencyDetail();
        dependencyDetail.setItemDetails(itemDetails);
        dependencyDetail.setUpdateDetails(updateDetails);
        return dependencyDetail;
    }

    private int getTotalComponents(List<T24ItemDetail> sourceDetail, List<T24ItemDetail> dataDetail) {
        int components = 0;
        List<String> compList = new ArrayList<String>();
        for (T24ItemDetail detail : sourceDetail) {
            String compName = detail.getComponentName();
            if (compName.equals("Unknown")) {
                continue;
            }
            if (!compList.contains(compName)) {
                compList.add(compName);
                components++;
            }
        }
        for (T24ItemDetail detail : dataDetail) {
            String compName = detail.getComponentName();
            if (compName.equals("Unknown")) {
                continue;
            }
            if (!compList.contains(compName)) {
                compList.add(compName);
                components++;
            }
        }
        return components;
    }

    private int getTotalProducts(List<T24ItemDetail> sourceDetail, List<T24ItemDetail> dataDetail) {
        int products = 0;
        List<String> prodList = new ArrayList<String>();
        for (T24ItemDetail detail : sourceDetail) {
            String productName = detail.getProductName();
            if (productName.equals("Unknown")) {
                continue;
            }
            if (!prodList.contains(productName)) {
                prodList.add(productName);
                products++;
            }
        }
        for (T24ItemDetail detail : dataDetail) {
            String productName = detail.getProductName();
            if (productName.equals("Unknown")) {
                continue;
            }
            if (!prodList.contains(productName)) {
                prodList.add(productName);
                products++;
            }
        }
        return products;
    }

    public List<T24UpdateDetails> getUpdateInput(List<T24ItemDetail> details, String relNo) {
        String componentList = "";
        List<String> compList = new ArrayList<String>();
        for (T24ItemDetail item : sourceItemDetails) {
            String compName = item.getComponentName();
            if (compName.equals("Unknown")) {
                continue;
            }
            if (item.isSelected() && !compList.contains(compName)) {
                compList.add(compName);
            }
        }
        for (T24ItemDetail item : dataItemdetails) {
            String compName = item.getComponentName();
            if (compName.equals("Unknown")) {
                continue;
            }
            if (item.isSelected() && !compList.contains(compName)) {
                compList.add(compName);
            }
        }
        for (String comp : compList) {
            componentList = componentList + comp + " ";
        }
        String[] args = new String[4];
        args[0] = relNo;
        args[1] = componentList;
        args[2] = "";
        args[3] = "";
        JSubroutineParameters params = RemoteInstallerHelper.getParameters(args);
        JSubroutineParameters params1 = params;
        String remotePgm = PluginConstants.EB_GET_RELATED_UPDATES;
        RemoteSite site = getRemoteSite(relNo);
        if (site == null) {
            totalUpdatesCount = 0;
            return new ArrayList<T24UpdateDetails>();
        }
        params = executeRemoteSubroutine(params, remotePgm, site);
        if (params == null) {
            boolean started = false;
            started = startAgent(site);
            if (started) {
                params = executeRemoteSubroutine(params1, remotePgm, site);
                if (params == null) {
                    showErrorMessage(site, relNo);
                    totalUpdatesCount = 0;
                    return new ArrayList<T24UpdateDetails>();
                } else {
                    return buildDependencyDetails(params);
                }
            } else {
                showErrorMessage(site, relNo);
                totalUpdatesCount = 0;
                return new ArrayList<T24UpdateDetails>();
            }
        } else {
            return buildDependencyDetails(params);
        }
    }

    private List<T24UpdateDetails> buildDependencyDetails(JSubroutineParameters params) {
        String[] resultArray = RemoteInstallerHelper.getStringParams(params);
        String result = resultArray[1];
        String totUpdates = resultArray[3];
        totalUpdatesCount = Integer.parseInt(totUpdates);
        return getDependencyDetails(result);
    }

    private RemoteSite getRemoteSite(String relNo) {
        RemoteSite newSite = null;
        RemoteSitePlatform platform = RemoteSitePlatform.UNIX;
        RemoteConnectionType connectionType = RemoteConnectionType.JCA;
        Protocol protocolType = Protocol.SFTP;
        String siteName = "";
        String hostName = "";
        String userName = "";
        String password = "";
        boolean isDefault = true;
        String portNo = "";
        if (relNo.equals("R09")) {
            siteName = PluginConstants.R09_SITE_NAME;
            hostName = PluginConstants.R09_HOST_NAME;
            userName = PluginConstants.R09_USER_NAME;
            password = getPassword("R09");
            portNo = PluginConstants.R09_PORT_NUMBER;
            if (!password.equals("")) {
                newSite = new RemoteSite(siteName, hostName, userName, password, platform, isDefault, portNo, connectionType,
                        protocolType, null);
            }
        } else {
            siteName = PluginConstants.R10_SITE_NAME;
            hostName = PluginConstants.R10_HOST_NAME;
            userName = PluginConstants.R10_USER_NAME;
            password = getPassword("R10");
            portNo = PluginConstants.R10_PORT_NUMBER;
            if (!password.equals("")) {
                newSite = new RemoteSite(siteName, hostName, userName, password, platform, isDefault, portNo, connectionType,
                        protocolType, null);
            }
        }
        return newSite;
    }

    private String getPassword(String relNo) {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        String pwd = "";
        if (relNo.equals("R09")) {
            pwd = mu.getProperty("r09.key");
        } else {
            pwd = mu.getProperty("r10.key");
        }
        if (pwd == null) {
            pwd = "";
        }
        return pwd;
    }

    private List<T24UpdateDetails> getDependencyDetails(String result) {
        List<T24UpdateDetails> updateDetails = new ArrayList<T24UpdateDetails>();
        if (result.length() == 0) {
            return updateDetails;
        }
        String updateList[] = result.split("#");
        if (updateList.length > 0) {
            for (String updateDetail : updateList) {
                String update[] = updateDetail.split("!");
                String updateName = "";
                String dependencyList = "";
                int nod = 0;
                if (update.length == 2) {
                    updateName = update[0];
                    dependencyList = update[1];
                    nod = dependencyList.split(",").length;
                }
                T24UpdateDetails details = new T24UpdateDetails(updateName, nod, dependencyList);
                updateDetails.add(details);
            }
        }
        return updateDetails;
    }

    private List<T24ItemDetail> getSourceItems(String result) {
        String sourceItems = "";
        int startIndex = result.indexOf("<sourceitems>");
        int endIndex = result.indexOf("</sourceitems>");
        int startIndexLength = "<sourceitems>".length();
        sourceItems = result.substring(startIndex + startIndexLength, endIndex);
        List<T24ItemDetail> sourceDetails = new ArrayList<T24ItemDetail>();
        if (sourceItems.length() == 0) {
            return sourceDetails;
        }
        sourceItems = sourceItems.replaceAll("<", "");
        sourceItems = sourceItems.replaceAll(">", "");
        String[] sourceItemList = sourceItems.split(";");
        if (sourceItemList.length != 0) {
            for (String sourceItem : sourceItemList) {
                sourceDetails.add(constructDetail(sourceItem, "Source"));
            }
        }
        return sourceDetails;
    }

    private List<T24ItemDetail> getDataItems(String result) {
        String dataItems = "";
        int startIndex = result.indexOf("<dataitems>");
        int endIndex = result.indexOf("</dataitems>");
        int startIndexLength = "<dataitems>".length();
        dataItems = result.substring(startIndex + startIndexLength, endIndex);
        List<T24ItemDetail> dataDetails = new ArrayList<T24ItemDetail>();
        if (dataItems.length() == 0) {
            return dataDetails;
        }
        String[] dataItemList = dataItems.split(";");
        for (String dataItem : dataItemList) {
            dataItem = dataItem.replaceFirst(">", "!");
            dataItem = dataItem.replaceAll("<", "");
            dataItem = dataItem.replaceAll(">", "");
            dataDetails.add(constructDetail(dataItem, "Data"));
        }
        return dataDetails;
    }

    private List<T24UpdateDetails> extractComponentDetails(String result) {
        String componentList = "";
        int startIndex = result.indexOf("<componentlist>");
        int endIndex = result.indexOf("</componentlist>");
        int startIndexLength = "<componentlist>".length();
        componentList = result.substring(startIndex + startIndexLength, endIndex);
        return getDependencyDetails(componentList);
    }

    private T24ItemDetail constructDetail(String item, String itemType) {
        String[] itemArray = item.split(" ");
        T24ItemDetail detail = new T24ItemDetail(itemArray[0], itemType, itemArray[1], itemArray[2]);
        detail.setSelected(true);
        return detail;
    }

    private String buildItemList(InstallableItemCollection collection) {
        String itemList = "";
        List<InstallableItem> sourceItems = collection.getSourceItems();
        List<InstallableItem> dataItems = collection.getDataItems();
        for (InstallableItem item : sourceItems) {
            itemList = itemList + item.getName().replaceAll(".b", "") + " ";
        }
        itemList = itemList + ";";
        for (InstallableItem item : dataItems) {
            String itemName = item.getName().replaceAll(".d", "");
            itemName = itemName.replaceAll("!", ">");
            itemList = itemList + itemName + " ";
        }
        return itemList;
    }

    private JSubroutineParameters executeRemoteSubroutine(JSubroutineParameters params, String remotePgm, RemoteSite remoteSite) {
        IJremoteClient client = RemoteConnectionFactory.getInstance().createJremoteClient(remoteSite);
        try {
            params = client.run(remotePgm, params);
            return params;
        } catch (JSubroutineNotFoundException e) {
            RemoteOperationsLog.error("Subroutine not found. " + e.getMessage());
        } catch (JRemoteException e) {
            RemoteOperationsLog.error("Remote Error. " + e.getMessage());
        }
        return null;
    }
}
