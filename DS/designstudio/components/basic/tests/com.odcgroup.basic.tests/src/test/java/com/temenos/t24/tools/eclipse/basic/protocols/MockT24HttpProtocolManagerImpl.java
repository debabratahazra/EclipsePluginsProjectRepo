package com.temenos.t24.tools.eclipse.basic.protocols;

/**
 * Mock class used to simulate the Http layer for test messages sent and received.
 * 
 * In a crude way it traps commands sent down, and sends back "hardcoded" responses
 * simulating real ones. In this way different scenarios can be tested.
 * 
 * Be aware that
 * 
 * TODO: think of a more efficient way of doing this.
 *
 */
public class MockT24HttpProtocolManagerImpl implements T24HttpProtocolManager {

    public static TEST_ID testId;
    
    /** Enum with tests Ids */
    public enum TEST_ID {
        SUCCESS1, SUCCESS2,
        FAIL1, FAIL2,
        GET_CHANNELS_SUCCESS, GET_CHANNELS_FAIL,
        SIGNON_SUCCESS, SIGNON_FAIL,
        SAVE_SUCCESS, SAVE_FAIL1,
        CHECK_LOCK_SUCCESS, CHECK_LOCK_FAIL1,
        LOCK_SUCCESS, LOCK_FAIL,
        UNLOCK_SUCCESS, UNLOCK_FAIL,
        GET_LOCKS_SUCCESS, GET_LOCKS_FAIL,
        RETRIEVE_FILE_SUCCESS, RETRIEVE_FILE_FAIL,
        RETRIEVE_FILE_READONLY_SUCCESS,
        COMPILE_SUCCESS, COMPILE_FAIL,
        NEW_USER1, NEW_USER2, NEW_USER3,
        PSWD_EXPIRED1, PSWD_EXPIRED2, PSWD_EXPIRED3,
        GET_SERVER_FILES_SUCCESS
    };
    
    public void httpFinished(Response res) {
    }

    public Response sendCommand(Command cmd) {
        Response res = new Response();
        
        String cmdName = cmd.getCommandName();
        String resXml = "";
        if(ProtocolConstants.COMMAND_SIGN_ON.equals(cmdName)) {
            
            /** Here we simulate the HTTP comms. layer, we assume this always succeeds, therefore passed = true */
            res.setPassed(true);
            
            if(TEST_ID.SIGNON_SUCCESS.equals(testId)) {
                resXml = signOn_Sucess;
                
            } else if (TEST_ID.SIGNON_FAIL.equals(testId)) {
                resXml = signOn_Fail;
                
            } else if (TEST_ID.NEW_USER1.equals(testId)) {
                resXml = signOnNewUser_First_Response_Ok;
                
            } else if (TEST_ID.NEW_USER2.equals(testId)) {
                resXml = signOnNewUser_Second_Response_Ok;              
                    
            } else if (TEST_ID.NEW_USER3.equals(testId)) {
                resXml = signOnNewUser_Second_Response_Fail;
                
            } else if (TEST_ID.PSWD_EXPIRED1.equals(testId)) {
                resXml = signOnPswdExpired_First_Response_Ok;

            } else if (TEST_ID.PSWD_EXPIRED2.equals(testId)) {
                resXml = signOnPswdExpired_Second_Response_Ok;                
                
            } else if (TEST_ID.PSWD_EXPIRED3.equals(testId)) {
                /** It is the xml response from the server the one containing the failure within the errrorMsg */
                resXml = signOnPswdExpired_Second_Response_Fail;
                
            } else {
                /** All other cases will assume a succesful sign on by default. */
                resXml = signOn_Sucess;
            }
            
        } else if(ProtocolConstants.COMMAND_SIGN_OFF.equals(cmdName)) { 
            res.setPassed(true);
            resXml = signOff_Success;        
            
        } else if (ProtocolConstants.COMMAND_SAVE_FILE.equals(cmdName)){
            res.setPassed(true);
            if(TEST_ID.SAVE_SUCCESS.equals(testId)){
                resXml = saveFile_Success;
                
            } else if (TEST_ID.SAVE_FAIL1.equals(testId)) {
                resXml = saveFile_Fail;
                
            } else if(TEST_ID.COMPILE_SUCCESS.equals(testId)){
                resXml = saveFile_Success;
            }
            
        } else if (ProtocolConstants.COMMAND_CHECK_LOCK.equals(cmdName)){
            res.setPassed(true);
            if (TEST_ID.RETRIEVE_FILE_FAIL.equals(testId)) {
                /** Retrieving file, first checks whether file is locked. If it is
                 * retrieval fails. */
                resXml = checkLock_Success_Locked;
                
            } else if (TEST_ID.RETRIEVE_FILE_READONLY_SUCCESS.equals(testId)) {
                /** Readonly doesn't check for lockings, this tests for that. */
                resXml = checkLock_Success_Locked;
                
            } else {
                resXml = checkLock_Success_NotLocked;
            }
            
        } else if (ProtocolConstants.COMMAND_LOCK_FILE.equals(cmdName)) {
            res.setPassed(true);
            if(TEST_ID.LOCK_SUCCESS.equals(testId)) {
                resXml = lockFile_Success;
                
            } else if (TEST_ID.LOCK_FAIL.equals(testId)) {
                resXml = lockFile_Fail;
                
            } else {
                /** Locking succeeds by default, i.e. locked = true */
                resXml = lockFile_Success;
            }
            
        }else if (ProtocolConstants.COMMAND_UNLOCK_FILE.equals(cmdName)) {
            res.setPassed(true);
            resXml = unlockFile_Success;
            
        } else if (ProtocolConstants.COMMAND_GET_LOCKS.equals(cmdName)) {
            res.setPassed(true);
            if (TEST_ID.GET_LOCKS_SUCCESS.equals(testId)) {
                resXml = getLocks_Success;
                
            } else {
                /** By default it's a success */
                resXml = getLocks_Success;
            }
            
        } else if (ProtocolConstants.COMMAND_RETRIEVE_FILE.equals(cmdName)){
            res.setPassed(true);
            if (TEST_ID.RETRIEVE_FILE_SUCCESS.equals(testId)) {
                resXml = retrieveFile_Success;
                
            } else if (TEST_ID.RETRIEVE_FILE_FAIL.equals(testId)) {
                resXml = retrieveFile_Fail;
            }
            
        } else if (ProtocolConstants.COMMAND_GET_SERVER_FILES.equals(cmdName)){
                res.setPassed(true);
                resXml = getServerFilesOKXml;

        } else if (ProtocolConstants.COMMAND_RETRIEVE_READ_ONLY_FILE.equals(cmdName)){
            res.setPassed(true);
            resXml = retrieveFile_Success;
            
        } else if (ProtocolConstants.COMMAND_COMPILE_FILE.equals(cmdName)){
            res.setPassed(true);
            resXml = compileFileOKXml;
            
        } else if (ProtocolConstants.COMMAND_GET_CHANNELS.equals(cmdName)){
            res.setPassed(true);
            
            if(TEST_ID.GET_CHANNELS_SUCCESS.equals(testId)){
                resXml = getChannels_Success;
                
            } else if(TEST_ID.GET_CHANNELS_FAIL.equals(testId)){
                resXml = getChannels_Fail;
                
            } else {
                resXml = getChannels_Success;
            }
            
        } else if (ProtocolConstants.COMMAND_EMPTY_ACTION.equals(cmdName)){
            res.setPassed(true);
            resXml = emptyActionXml;
        }        
        
        res.setObject(resXml);
        return res;
    }

    public void stopHttp() {
    }

    private String getLocks_Success = "<ofsSessionResponse><token>0000641843.00</token>" +
        "<responseType>XML.</responseType><responseData>" +
        "<responseDetails><tdevstudio><useBase64>true</useBase64>" +
        "<noSrcCompile>true</noSrcCompile>" +
        "<locklist>TEST1:TEST2:TEST3</locklist>" +
        "</tdevstudio></responseDetails></responseData></ofsSessionResponse>";
    
    private String signOn_Sucess = "<ofsSessionResponse><token>0000156407.03</token><responseType>XML.FRAMES</responseType></ofsSessionResponse>";

    private String signOn_Fail = "<ofsSessionResponse><token></token><styleSheet></styleSheet><responseType>ERROR.LOGIN</responseType><responseData><responseDetails><userDetails><company></company><companyId></companyId><multiCo>Y</multiCo><user></user><skin>default</skin><pwprocessid></pwprocessid><popupDropDown>true</popupDropDown><clientExpansion>true</clientExpansion><cferiAllowed>N</cferiAllowed><today>10-JAN-2001</today><release>200712.003</release><lng>GB</lng><lngs>GB,FR,DE,ES</lngs><SaveChanges>YES</SaveChanges><useTabbedMenu>false</useTabbedMenu><stripFrameToolbars>false</stripFrameToolbars><cleanPrint>false</cleanPrint><clientLogging>false</clientLogging><useKeepaliveHandling>false</useKeepaliveHandling><useAutoHoldDeals>false</useAutoHoldDeals></userDetails><windowCoordinates><windowTop>0</windowTop><windowLeft>0</windowLeft><windowWidth>800</windowWidth><windowHeight>500</windowHeight></windowCoordinates><error>SECURITY VIOLATION</error></responseDetails></responseData></ofsSessionResponse>";
    
    private String signOff_Success = "<ofsSessionResponse><token/><styleSheet>logoff.xsl</styleSheet><responseType>XML.LOGOFF</responseType>"
        + "<responseData><user>TONYC1</user></responseData></ofsSessionResponse>";    

        private String saveFile_Success = "<ofsSessionResponse>"
        + "<token>0000142138.05</token>"
        + "<responseType>XML.</responseType>"
        + "<responseData><responseDetails><tdevstudio>"
        + "<useBase64>true</useBase64><saved>TRUE</saved>"
        + "</tdevstudio></responseDetails></responseData>"
        + "</ofsSessionResponse>";
    
        private String saveFile_Fail = "<ofsSessionResponse><token></token><styleSheet>/transforms/errorMessage.xsl</styleSheet><responseType>XML.MSG</responseType><responseData>" +
                            "<responseDetails><userDetails><company>BNK Master</company><companyId>US0010001</companyId>" +
                            "<user></user><skin>default</skin><pwprocessid></pwprocessid><popupDropDown>true</popupDropDown>" +
                            "<clientExpansion>true</clientExpansion><cferiAllowed>N</cferiAllowed><today>10-JAN-2001</today>" +
                            "<release>200712.003</release><lng>GB</lng><lngs>GB,FR,DE,ES</lngs><SaveChanges>YES</SaveChanges>" +
                            "<useTabbedMenu>false</useTabbedMenu><stripFrameToolbars>false</stripFrameToolbars><cleanPrint>false</cleanPrint>" +
                            "<clientLogging>false</clientLogging><useKeepaliveHandling>false</useKeepaliveHandling><useAutoHoldDeals>false</useAutoHoldDeals>" +
                            "</userDetails><windowCoordinates><windowTop>0</windowTop><windowLeft>0</windowLeft><windowWidth>800</windowWidth>" +
                            "<windowHeight>500</windowHeight></windowCoordinates><messages><msg>SECURITY.VIOLATION</msg><title>Error message</title>" +
                            "</messages></responseDetails></responseData></ofsSessionResponse>";            
        
        
    private String checkLock_Success_NotLocked = "<ofsSessionResponse>"
                + "<token>0000153365.00</token>"
                + "<responseType>XML.</responseType>"
                + "<responseData><responseDetails>"
                + "<tdevstudio>"
                + "<useBase64>true</useBase64>"
                + "<progname>ECLIPSE.TEST1</progname>"
                + "<locked>FALSE</locked>"
                + "</tdevstudio>"
                + "</responseDetails></responseData></ofsSessionResponse>";
    
    private String checkLock_Success_Locked = "<ofsSessionResponse>"
        + "<token>0000153365.00</token>"
        + "<responseType>XML.</responseType>"
        + "<responseData><responseDetails>"
        + "<tdevstudio>"
        + "<useBase64>true</useBase64>"
        + "<progname>ECLIPSE.TEST1</progname>"
        + "<locked>TRUE</locked>"
        + "</tdevstudio>"
        + "</responseDetails></responseData></ofsSessionResponse>";    

    private String retrieveFile_Success = "<ofsSessionResponse>"
        + "<token>0000152781.02</token>"
        + "<responseData><tdevstudio>"
        + "<useBase64>true</useBase64>"
        + "<code>TElOIDENTElOIDINTElOIDMNTElOIDQ=</code>"
        + "</tdevstudio>"
        + "</responseData></ofsSessionResponse>";
    
    private String retrieveFile_Fail = ""; // TODO fill in
    
    private String lockFile_Success = "<ofsSessionResponse>"
        + "<token>0006455582.01</token>"
        + "<responseData><responseDetails><tdevstudio>"
        + "<useBase64>true</useBase64><noSrcCompile>true</noSrcCompile>"
        + "<progname>TEST</progname><locked>TRUE</locked>"
        + "</tdevstudio></responseDetails></responseData></ofsSessionResponse>";
    
    private String lockFile_Fail = "<ofsSessionResponse>"
        + "<token>0006455582.01</token>"
        + "<responseData><responseDetails><tdevstudio>"
        + "<useBase64>true</useBase64><noSrcCompile>true</noSrcCompile>"
        + "<progname>TEST</progname><locked>FALSE</locked>"
        + "</tdevstudio></responseDetails></responseData></ofsSessionResponse>";
    
    private String compileFileOKXml = "<ofsSessionResponse>"
        + "<token>0000461598.00</token>"
        + "<responseType>XML.</responseType>"
        + "<responseData><responseDetails><userDetails>"
        + "<user>TOOLBOXA</user></userDetails>"
        + "<tdevstudio>"
        + "<useBase64>true</useBase64>"
        + "<saved>TRUE</saved>"
        + "<compileout><![CDATA[Source directory : BP Single item : TESTI!FM!Score Checks Compile Catalog !FM!Score Checks Compile Catalog !FM!Dev bin & lib currently set to: !FM!        /storage1/globus.accounts/globus.acc.3/venkat.sanjai/vs06a/vs06a.run/bin & lib!FM!Now changed to :!FM!        /storage1/globus.accounts/globus.acc.3/venkat.sanjai/vs06a/vs06a.run/globusbin & lib!FM!Dev bin & lib now reset to: !FM!        /storage1/globus.accounts/globus.acc.3/venkat.sanjai/vs06a/vs06a.run/bin & lib!FM!TESTI!FM!Source file TESTI compiled successfully!FM!"
        + "<compileoutput><errors></errors>"
        + "<warnings>"
        + "<warning><msg>Java Standards was not checked! Code 3 was returned.</msg><line></line><type>Ensure your T24 java environment is configured correctly.</type></warning>"
        + "<warning><msg>Warning - found 'CRT'</msg><line>5</line><type>Incompatible with Browser, Desktop and OFS. Do not use</type></warning>"
        + "</warnings>"
        + "<codereviews></codereviews><standards></standards><RatingDetail><CodeLines>0</CodeLines>"
        + "<ParaLines>0</ParaLines><Nests>0</Nests><Conditions>0</Conditions>"
        + "<Gotos>0</Gotos><SameLine>0</SameLine><Labels>0</Labels><Comments>0</Comments>"
        + "<Rating>0</Rating><CurrentRating>0</CurrentRating></RatingDetail>"
        + "</compileoutput>!FM! ]]></compileout><cmd>EB.COMPILE BP TESTI</cmd>"
        + "</tdevstudio></responseDetails></responseData></ofsSessionResponse>";
    
    private String unlockFile_Success = "<ofsSessionResponse><token>0000462878.02</token>"
        + "<responseType>XML.</responseType>"
        + "<responseData>"
        + "<tdevstudio><useBase64>true</useBase64>"
        + "<progname>TESTI</progname><locked>FALSE</locked>"
        + "</tdevstudio></responseData></ofsSessionResponse>";
    
    private String getChannels_Success = "<channels>"
        + "<channel>browser.1</channel>"
        + "<channel>browser.2</channel>"
        + "<channel>mq.sample</channel>"
        + "</channels>";
    
    // Real response from Browser - when the GET.CHANNELS command is sent to a version of Browser that
    // doesn't implement this command.
    private String getChannels_Fail = "<ofsSessionResponse><token></token><styleSheet>errorMessage.xsl</styleSheet>"+
                    "<responseType>XML.MSG</responseType><responseData><responseDetails>"+
                    "<userDetails><company>BNK TBASE</company><companyId>US0010001</companyId><user></user><skin>arc-ib</skin>"+
                    "<pwprocessid></pwprocessid><hotsAllowed>N</hotsAllowed><autosAllowed>N</autosAllowed>"+
                    "<cferiAllowed>N</cferiAllowed><today>29-DEC-2000</today>"+
                    "<release>200610</release><lng>GB</lng><stripFrameToolbars>TRUE</stripFrameToolbars>"+
                    "<printFeature>true</printFeature><hotsAllowed>N</hotsAllowed><autosAllowed>N</autosAllowed></userDetails>"+
                    "<screenMode>I</screenMode><windowCoordinates><windowTop>0</windowTop>"+
                    "<windowLeft>0</windowLeft><windowWidth>800</windowWidth>"+
                    "<windowHeight>500</windowHeight></windowCoordinates>"+
                    "<messages><msg>SECURITY.VIOLATION</msg>"+
                    "<title>Error message</title></messages></responseDetails></responseData></ofsSessionResponse>";
    
    private String getServerFilesOKXml = "<ofsSessionResponse>"
        + "<token>0001038135.04</token>"
        + "<responseType>XML.</responseType>"
        + "<responseData><responseDetails>"
        + "<tdevstudio><useBase64>true</useBase64>"
        + "<item>ACCOUNT.1</item>"
        + "<item>ACCOUNT.2</item>"
        + "<item>ACCOUNT.3</item>"
        + "</tdevstudio></responseDetails></responseData></ofsSessionResponse>";
 
    private String emptyActionXml = "<ofsSessionResponse><token>0001160562.00</token>"
            + "<responseData><responseDetails>"
            + "<tdevstudio><useBase64>true</useBase64><noSrcCompile>true</noSrcCompile>"
            + "</tdevstudio></responseDetails></responseData></ofsSessionResponse>";
    
    private String signOnNewUser_First_Response_Ok = "<ofsSessionResponse><token></token><styleSheet>/transforms/changePassword.xsl</styleSheet>"+
                         "<responseType>XML.REPEAT.PASSWORD</responseType><responseData><responseDetails><userDetails>" +
                         "<company></company><companyId></companyId><multiCo>Y</multiCo><user></user><skin>default</skin>"+
                         "<pwprocessid></pwprocessid><popupDropDown>true</popupDropDown><clientExpansion>true</clientExpansion>"+
                         "<cferiAllowed>N</cferiAllowed><today>12-JAN-2004</today><release>200712.003</release><lng>GB</lng>"+
                         "<lngs>GB,FR,DE,ES,AB</lngs><SaveChanges>YES</SaveChanges><brMgr>Y</brMgr><menuStyle>DEFAULT</menuStyle>"+
                         "<stripFrameToolbars>false</stripFrameToolbars><cleanPrint>false</cleanPrint><clientLogging>false</clientLogging>"+
                         "<useKeepaliveHandling>false</useKeepaliveHandling><useAutoHoldDeals>false</useAutoHoldDeals><menuStyle>DEFAULT</menuStyle>"+
                         "</userDetails><windowCoordinates><windowTop>0</windowTop><windowLeft>0</windowLeft><windowWidth>800</windowWidth>"+
                         "<windowHeight>500</windowHeight></windowCoordinates><routineArgs>PROCESS.REPEAT</routineArgs>"+
                         "<title>Please repeat new password</title><signOnName>WESAM10</signOnName>"+
                         "<r><ce><c>Password</c></ce></r><r><ce><n>oldPassword</n><ty>input</ty><v>123456</v></ce></r><r><ce><c>Repeat Password</c>"+
                         "</ce></r><r><ce><n>password</n><ty>input</ty><v></v></ce></r></responseDetails></responseData></ofsSessionResponse>";
    
    private String signOnNewUser_Second_Response_Ok = "<ofsSessionResponse><token>0001241089.04</token><styleSheet>/transforms/window.xsl</styleSheet>" +
                                   "<responseType>XML.FRAMES</responseType><responseData><responseDetails><userDetails><company>239-srdevc</company>" + 
                                   "<companyId>US0010001</companyId><multiCo>Y</multiCo><user>WESAM.10</user><skin>default</skin><pwprocessid></pwprocessid>" + 
                                   "<popupDropDown>true</popupDropDown><clientExpansion>true</clientExpansion><cferiAllowed>N</cferiAllowed>" + 
                                   "<today>12-JAN-2004</today><release>200712.003</release><compScreen>COMPOSITE.SCREEN_WESAM.10_000124108903</compScreen>" +
                                   "<compTargets>menu_menu000124108902|</compTargets><lng>GB</lng><lngs>GB,FR,DE,ES,AB</lngs><SaveChanges>YES</SaveChanges>" +
                                   "<brMgr>Y</brMgr><menuStyle>DEFAULT</menuStyle><stripFrameToolbars>false</stripFrameToolbars><cleanPrint>false</cleanPrint>" + 
                                   "<clientLogging>false</clientLogging><useKeepaliveHandling>false</useKeepaliveHandling><useAutoHoldDeals>false</useAutoHoldDeals>" +
                                   "<menuStyle>DEFAULT</menuStyle></userDetails><windowCoordinates><windowTop>0</windowTop><windowLeft>0</windowLeft>" +
                                   "<windowWidth>800</windowWidth><windowHeight>500</windowHeight></windowCoordinates><window><title>T24</title><stylesheets>" +
                                   "<stylesheet>deal.css</stylesheet></stylesheets><scripts><script>enquiry.js</script><script>menu.js</script>" + 
                                   "<script>request.js</script><script>Deal.js</script><script>commandline.js</script></scripts><frames><rows>100,*</rows>" +
                                   "<border>0</border><frame><n>banner000124108901</n><tar></tar><scroll>no</scroll><routine>OS.NEW.USER</routine>" + 
                                   "<args>BANNER</args></frame><frame><n>menu000124108902</n><tar></tar><routine>OS.NEW.USER</routine><args>MENU</args>" +
                                   "</frame></frames></window></responseDetails></responseData></ofsSessionResponse>";
    
    private String signOnNewUser_Second_Response_Fail = "<ofsSessionResponse><token></token>" +
                              "<responseType>XML.REPEAT.PASSWORD</responseType><responseData><responseDetails>" +
                              "<routineArgs>PROCESS.REPEAT</routineArgs><title>Please repeat new password</title>" +
                              "<signOnName>TEST11</signOnName><r><ce><c>Password</c></ce></r><r><ce><n>oldPassword</n>" +
                              "<ty>input</ty><v>1</v></ce></r><r><ce><c>Repeat Password</c></ce></r><r><ce><n>password</n><ty>input</ty><v></v>" +
                              "</ce></r><errorMessage>SECURITY VIOLATION- SECURITY.VIOLATION</errorMessage></responseDetails>" +
                              "</responseData></ofsSessionResponse>";
    
    private String signOnPswdExpired_First_Response_Ok = "<ofsSessionResponse><token></token><styleSheet>/transforms/changePassword.xsl</styleSheet>"+
                                       "<responseType>XML.EXPIRED.PASSWORD</responseType><responseData><responseDetails><userDetails>" + 
                                       "<company></company><companyId></companyId><multiCo>Y</multiCo><user></user><skin>default</skin>" +
                                       "<pwprocessid></pwprocessid><popupDropDown>true</popupDropDown><clientExpansion>true</clientExpansion>" +
                                       "<cferiAllowed>N</cferiAllowed><today>12-JAN-2004</today><release>200712.003</release><lng>GB</lng><lngs>0</lngs>" +
                                       "<SaveChanges>YES</SaveChanges><brMgr>Y</brMgr><menuStyle>DEFAULT</menuStyle><stripFrameToolbars>false</stripFrameToolbars>" +
                                       "<cleanPrint>false</cleanPrint><clientLogging>false</clientLogging><useKeepaliveHandling>false</useKeepaliveHandling>" +
                                       "<useAutoHoldDeals>false</useAutoHoldDeals><menuStyle>DEFAULT</menuStyle></userDetails><windowCoordinates>" +
                                       "<windowTop>0</windowTop><windowLeft>0</windowLeft><windowWidth>800</windowWidth><windowHeight>500</windowHeight>" +
                                       "</windowCoordinates><routineArgs>PROCESS.EXPIRED</routineArgs><title>Password terminated - Please enter a new one</title>" +
                                       "<signOnName>WESAM10</signOnName><r><ce><c>Password</c></ce></r><r><ce><n>oldPassword</n><ty>input</ty><v></v></ce></r><r><ce>" +
                                       "<c>Repeat Password</c></ce></r><r><ce><n>password</n><ty>input</ty><v></v></ce></r></responseDetails></responseData></ofsSessionResponse>";

    private String signOnPswdExpired_Second_Response_Ok = "<ofsSessionResponse><token>0001641552.06</token><styleSheet>/transforms/window.xsl</styleSheet>" + 
                                                      "<responseType>XML.FRAMES</responseType><responseData><responseDetails><userDetails>" + 
                                                      "<company>239-srdevc</company><companyId>US0010001</companyId><multiCo>Y</multiCo><user>WESAM.10</user>" +
                                                      "<skin>default</skin><pwprocessid></pwprocessid><popupDropDown>true</popupDropDown><clientExpansion>true</clientExpansion>" + 
                                                      "<cferiAllowed>N</cferiAllowed><today>12-JAN-2004</today><release>200712.003</release><compScreen>COMPOSITE.SCREEN_WESAM.10_000164155205</compScreen>" + 
                                                      "<compTargets>menu_menu000164155204|</compTargets><lng>GB</lng><lngs>GB,FR,DE,ES,AB</lngs><SaveChanges>YES</SaveChanges><brMgr>Y</brMgr>" + 
                                                      "<menuStyle>DEFAULT</menuStyle><stripFrameToolbars>false</stripFrameToolbars><cleanPrint>false</cleanPrint><clientLogging>false</clientLogging>" + 
                                                      "<useKeepaliveHandling>false</useKeepaliveHandling><useAutoHoldDeals>false</useAutoHoldDeals><menuStyle>DEFAULT</menuStyle></userDetails>" + 
                                                      "<windowCoordinates><windowTop>0</windowTop><windowLeft>0</windowLeft><windowWidth>800</windowWidth><windowHeight>500</windowHeight>" +
                                                      "</windowCoordinates><window><title>T24</title><stylesheets><stylesheet>deal.css</stylesheet></stylesheets><scripts><script>enquiry.js</script>" + 
                                                      "<script>menu.js</script><script>request.js</script><script>Deal.js</script><script>commandline.js</script></scripts><frames><rows>100,*</rows>" +
                                                      "<border>0</border><frame><n>banner000164155203</n><tar></tar><scroll>no</scroll><routine>OS.NEW.USER</routine><args>BANNER</args></frame><frame>" +
                                                      "<n>menu000164155204</n><tar></tar><routine>OS.NEW.USER</routine><args>MENU</args></frame></frames></window></responseDetails></responseData>" +
                                                      "</ofsSessionResponse>";

    private String signOnPswdExpired_Second_Response_Fail =  "<ofsSessionResponse><token>0001841836.00</token><styleSheet>/transforms/changePassword.xsl</styleSheet>" +
                                                 "<responseType>XML.EXPIRED.PASSWORD</responseType><responseData><responseDetails><userDetails><company>" + 
                                                 "</company><companyId></companyId><multiCo>Y</multiCo><user>WESAM.10</user><skin>default</skin><pwprocessid>" +
                                                 "</pwprocessid><popupDropDown>true</popupDropDown><clientExpansion>true</clientExpansion><cferiAllowed>N</cferiAllowed>" +
                                                 "<today>12-JAN-2004</today><release>200712.003</release><lng>GB</lng><lngs>GB,FR,DE,ES,AB</lngs><SaveChanges>YES</SaveChanges>" +
                                                 "<brMgr>Y</brMgr><menuStyle>DEFAULT</menuStyle><stripFrameToolbars>false</stripFrameToolbars><cleanPrint>false</cleanPrint>" + 
                                                 "<clientLogging>false</clientLogging><useKeepaliveHandling>false</useKeepaliveHandling><useAutoHoldDeals>false</useAutoHoldDeals>" + 
                                                 "<menuStyle>DEFAULT</menuStyle></userDetails><windowCoordinates><windowTop>0</windowTop><windowLeft>0</windowLeft><windowWidth>800</windowWidth>" +
                                                 "<windowHeight>500</windowHeight></windowCoordinates><routineArgs>PROCESS.EXPIRED</routineArgs><title>Password terminated - Please enter a new one</title>" +
                                                 "<signOnName>WESAM10</signOnName><r><ce><c>Password</c></ce></r><r><ce><n>oldPassword</n><ty>input</ty><v></v></ce></r><r><ce><c>Repeat Password</c>" + 
                                                 "</ce></r><r><ce><n>password</n><ty>input</ty><v></v></ce></r><errorMessage>INVALID PASSWORD REPETITION</errorMessage></responseDetails></responseData>" +
                                                 "</ofsSessionResponse>";


}
