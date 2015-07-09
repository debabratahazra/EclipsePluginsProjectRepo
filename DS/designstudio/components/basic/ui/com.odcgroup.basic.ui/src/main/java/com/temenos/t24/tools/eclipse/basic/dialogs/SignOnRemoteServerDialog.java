package com.temenos.t24.tools.eclipse.basic.dialogs;

import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;

public class SignOnRemoteServerDialog extends Dialog {

    private String username = "";
    private String password = "";
    private String newChannelSelected  = "";
    private boolean rememberLogin = false; // true ==> login info will be persisted, false ==> will not be persisted.
    
    private Text usernameText   = null;
    private Text passwordText   = null;
    private Combo channelCombo  = null;
    private Text channelText    = null;
    private Button rememberLoginBox  = null;
    private Button getChannelsButton = null;
    
    private static Composite container;     

    private final int DIALOG_WIDTH = 500;
    private final int DIALOG_HEIGHT = 300;
    
    /**
     * Creates a dialog instance. Note that the window will have no visual
     * representation (no widgets) until it is told to open. By default,
     * <code>open</code> blocks for dialogs.
     * 
     * @param parentShell the parent shell, or <code>null</code> to create a
     *            top-level shell
     */
    public SignOnRemoteServerDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
    }

    /**
     * Creates and returns the contents of the upper part of this dialog (above
     * the button bar).
     * 
     * @param parent the parent composite to contain the dialog area
     * @return the dialog area control
     */
    protected Control createDialogArea(Composite parent) {
        container = (Composite) super.createDialogArea(parent);
        container.setBounds(100,100,DIALOG_WIDTH,DIALOG_HEIGHT);

        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        container.setLayout(gridLayout);
        
        // *******************************************************
        // Username
        final Label usernameLabel = new Label(container, SWT.NONE);
        usernameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        usernameLabel.setText("Username:");
        usernameText= new Text(container, SWT.BORDER | SWT.PASSWORD);
        usernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        // *******************************************************
        // Password
        final Label passwordLabel = new Label(container, SWT.NONE);
        passwordLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        passwordLabel.setText("Password:");
        passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
        passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // *******************************************************
        // Remember data checkbox
        final Label rememberLabel = new Label(container, SWT.NONE);
        rememberLabel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
        rememberLabel.setText("Remember me:");
        rememberLoginBox = new Button(container, SWT.CHECK);
       
        // *******************************************************
        // Get channels button
        getChannelsButton = new Button(container, SWT.PUSH);
        getChannelsButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        getChannelsButton.setText("Get Channels");        
        
        // *******************************************************
        // Channel Text       
        channelText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
        channelText.setLayoutData(new GridData());
        
        // INITIALISE VALUES
        initialiseValues();

        // SETUP LISTENERS FOR THE FIELDS
        setupListeners();
        return container;
    }

    /**
     * Sets up listeners for the text fields, so whenever they are modified the
     * relevant variables are updated.
     */
    private void setupListeners() {
     
       usernameText.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
            username= usernameText.getText();
         }
      });
   
       passwordText.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
            password = passwordText.getText();
         }
      });       
       
       getChannelsButton.addSelectionListener(new SelectionAdapter(){
           public void widgetSelected(SelectionEvent event){
               if(channelText!=null){
                   channelText.dispose();
                   channelText = null;
               }
               if(channelCombo!=null){
                   channelCombo.dispose();
                   channelCombo = null;
               }
               drawChannelCombo();
               container.layout();
               container.pack();
           }
       });       
       
       rememberLoginBox.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event){
                rememberLogin = rememberLoginBox.getSelection();
            }
       });
   }
    
    
    /**
     * Retrieves the available channels list (if any) from Browser
     * and populates a Combo box (drop down menu) with them.
     */
    private void drawChannelCombo(){
        // Get the channels available from Browser.
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        Response res = sesMgr.getChannelList();
        if(res.getPassed()){
            // SUCCEED READING THE CHANNELS FROM BROWSER
            String[] channels = (String[])res.getObject();
            if(channels.length>0){
                // There are channels in the list
                buildCombo(channels);
            } else {
                // There are no channels in the list
                buildChannelText();
            }

        } else {
            // FAIL READING THE CHANNELS FROM BROWSER
            buildChannelText();
        }
                
    }

    /**
     * Creates a Text field (writable) for the user to write down the channels 
     */
    private void buildChannelText(){
        channelText = new Text(container, SWT.BORDER);
        channelText.setLayoutData(new GridData());
        // pre-populate with default channel
        channelText.setText(ProtocolConstants.DEFAULT_CHANNEL_STRING);
        channelText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                newChannelSelected = channelText.getText();
            }
         });
    }
    
    /**
     * Creates a combo box with the passed channels
     * @param channels
     */
    private void buildCombo(String[] channels){

        channelCombo = new Combo(container, SWT.DROP_DOWN| SWT.READ_ONLY);
        channelCombo.setLayoutData(new GridData());
        channelCombo.setItems(channels);
        
        // Set as default channel in the combo field the last one selected (if any)
        // check if it exists in retrieved channel list.
        ProtocolUtil pu = new ProtocolUtil();   
        String lastChannelSelected = pu.getCurrentChannel();
        int index = Arrays.binarySearch(channels, lastChannelSelected);
        // if it does, then set is as default.
        if(index >= 0){
            channelCombo.setText(lastChannelSelected);
            // pre-select the new one with the last one.
            newChannelSelected = lastChannelSelected;
        }
        // Assign a listener to the combo
        channelCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                newChannelSelected = channelCombo.getText();
            }
        });
    }

    /**
     * Initialise the fields (login, pswrd, remember) values
     */
    public void initialiseValues(){
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        // Get remember property (and unbox Boolean down to boolean)
        rememberLogin = new Boolean(mu.getProperty("local.user.rememberlogin.flag"));
        if(rememberLogin){
            this.username = mu.getProperty("local.user.login");
            this.password = mu.getProperty("local.user.password");
            usernameText.setText(username);
            passwordText.setText(password);
            rememberLoginBox.setSelection(true);
        } else {
            // Leave the login and password fields empty, and set the check box to false
            rememberLoginBox.setSelection(false);
        }
        
        // Channel Text populate with current channel (if any)
        ProtocolUtil pu = new ProtocolUtil();
        String currentChannel = pu.getCurrentChannel();
        currentChannel = (("".equals(currentChannel) || (currentChannel==null))?ProtocolConstants.DEFAULT_CHANNEL_STRING:currentChannel);
        newChannelSelected = currentChannel;
        channelText.setText(currentChannel);
    }
    
    /**
     * Configures the given shell in preparation for opening this window in it.
     * In this case, we set the dialog title.
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("SignOn Dialog");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getChannel() {
        return newChannelSelected;
    }    
    public boolean getRememberLogin() {
        return rememberLogin;
    }
    protected Point getInitialSize() {
        return new Point(229, 174);
    }
}
