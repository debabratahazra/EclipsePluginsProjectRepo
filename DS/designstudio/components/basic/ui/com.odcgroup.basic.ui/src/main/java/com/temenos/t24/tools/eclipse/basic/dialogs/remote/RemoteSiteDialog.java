package com.temenos.t24.tools.eclipse.basic.dialogs.remote;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnectionType;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.Protocol;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitePlatform;

/**
 * Abstract class for RemoteSite Dialog
 * 
 * @author vsanjai
 * 
 */
public abstract class RemoteSiteDialog extends Dialog {

    protected String siteName = "";
    protected String hostName = "";
    protected String userName = "";
    protected String password = "";
    protected RemoteSitePlatform platform = RemoteSitePlatform.UNIX;
    protected boolean isDefault = false;
    protected String portNumber = "";
    protected Text siteNameText;
    protected Text hostNameText;
    protected Text userNameText;
    protected Text passwordText;
    protected Text portNumberText;
    protected Button osWinButton;
    protected Button osUnixButton;
    protected Button osLinuxButton;
    protected Label dialogLabel = null;
    protected Button okButton; 
    protected Button ftpButton;
    protected Button sftpButton;
    protected RemoteConnectionType connectionType = RemoteConnectionType.JCA;
    protected Protocol protocolType = Protocol.SFTP;

    public RemoteSiteDialog(Shell shell) {
        super(shell);
    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        okButton = super.getButton(IDialogConstants.OK_ID);
        if (okButton != null) {
            okButton.setEnabled(initOkButton());
        }
    }

    protected abstract void initializeDialog();

    protected abstract boolean initOkButton();

    public abstract RemoteSite finish();

    protected abstract void validateInput();

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);
        dialogLabel = new Label(container, SWT.NONE);
        GridData daialogData = new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 4, 1);
        dialogLabel.setLayoutData(daialogData);
        final Label siteNameLabel = new Label(container, SWT.NONE);
        siteNameLabel.setText("Site Name");
        siteNameLabel.setToolTipText("Name of the remote site");
        siteNameText = new Text(container, SWT.BORDER);
        GridData data = new GridData(GridData.BEGINNING, GridData.BEGINNING, true, false, 1, 1);
        data.minimumWidth = 100;
        data.grabExcessVerticalSpace = true;
        data.minimumHeight = 20;
        siteNameText.setLayoutData(data);
        final Label hostNameLabel = new Label(container, SWT.NONE);
        hostNameLabel.setText("Host IP");
        hostNameLabel.setToolTipText("IP address of the remote site Eg: 10.92.5.15");
        hostNameText = new Text(container, SWT.BORDER);
        hostNameText.setLayoutData(data);
        final Label userNameLabel = new Label(container, SWT.NONE);
        userNameLabel.setText("User Name");
        userNameText = new Text(container, SWT.BORDER);
        userNameText.setLayoutData(data);
        userNameLabel.setToolTipText("Name of the user");
        final Label passwordLabel = new Label(container, SWT.NONE);
        passwordLabel.setText("Password");
        passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
        passwordText.setLayoutData(data);
        passwordText.setToolTipText("Password for your area");
        final Label portNumberLabel = new Label(container, SWT.NONE);
        portNumberLabel.setText("JAgent Port");
        portNumberText = new Text(container, SWT.BORDER);
        portNumberText.setLayoutData(data);
        portNumberText.setToolTipText("Port Number of the JAgent");
        final Label osLable = new Label(container, SWT.NONE);
        osLable.setText("OS Type");
        osLable.setToolTipText("Operating system of the remote site");
        Group OSButtonGroup = new Group(container, SWT.HORIZONTAL);
        GridLayout osButtonsLayout = new GridLayout();
        osButtonsLayout.numColumns = 3;
        osWinButton = new Button(OSButtonGroup, SWT.RADIO | SWT.LEFT);
        osWinButton.setText("Win");
        osUnixButton = new Button(OSButtonGroup, SWT.RADIO | SWT.LEFT);
        osUnixButton.setText("Unix");
        osLinuxButton = new Button(OSButtonGroup, SWT.RADIO | SWT.LEFT);
        osLinuxButton.setText("Linux");
        OSButtonGroup.setLayout(osButtonsLayout);
        final Label protocolLable = new Label(container, SWT.NONE);
        protocolLable.setText("Protocol");
        protocolLable.setToolTipText("Type of protocol");
        Group protocolButtonGroup = new Group(container, SWT.HORIZONTAL);
        GridLayout protocolButtonsLayout = new GridLayout();
        protocolButtonsLayout.numColumns = 2;
        ftpButton = new Button(protocolButtonGroup, SWT.RADIO);
        ftpButton.setText("FTP");
        ftpButton.setToolTipText("Sets ftp as protocol type");
        sftpButton = new Button(protocolButtonGroup, SWT.RADIO);
        sftpButton.setText("SFTP");
        sftpButton.setToolTipText("Sets sftp as protocol type");
        protocolButtonGroup.setLayout(protocolButtonsLayout);
        setupListeners();
        initializeDialog();
        return container;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getPassword() {
        return password;
    }

    public RemoteSitePlatform getPlatform() {
        return platform;
    }

    public String getUserName() {
        return userName;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public RemoteConnectionType getConnectionType() {
        return connectionType;
    }
    
    public Protocol getProtocolType(){
        return protocolType;
    }

    private void setupListeners() {
        siteNameText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                siteName = siteNameText.getText().trim();
                validateInput();
            }
        });
        hostNameText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                hostName = hostNameText.getText().trim();
                validateInput();
            }
        });
        userNameText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                userName = userNameText.getText().trim();
                validateInput();
            }
        });
        passwordText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                password = passwordText.getText();
                validateInput();
            }
        });
        portNumberText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                portNumber = portNumberText.getText().trim();
                validateInput();
            }
        });
       
        osWinButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                if (osWinButton.getSelection()) {
                    platform = RemoteSitePlatform.NT;
                }
            }
        });
        osLinuxButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub
            }

            public void widgetSelected(SelectionEvent e) {
                if (osLinuxButton.getSelection()) {
                    platform = RemoteSitePlatform.LINUX;
                }
            }
        });
        osUnixButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub
            }

            public void widgetSelected(SelectionEvent e) {
                if (osUnixButton.getSelection()) {
                    platform = RemoteSitePlatform.UNIX;
                }
            }
        });
        ftpButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub
            }

            public void widgetSelected(SelectionEvent e) {
                if (ftpButton.getSelection()) {
                    protocolType = Protocol.FTP;
                    validateInput();
                }
            }
        });
        
        
        sftpButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub
            }

            public void widgetSelected(SelectionEvent e) {
                if (sftpButton.getSelection()) {
                    protocolType = Protocol.SFTP;
                    validateInput();
                }
            }
        });


    }
}
