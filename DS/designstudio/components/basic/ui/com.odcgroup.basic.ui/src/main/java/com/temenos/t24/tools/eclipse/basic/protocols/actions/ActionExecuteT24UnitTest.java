package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/**
 * Action class responsible for constructing the Browser request to execute a
 * particular test in T24 Server.
 * 
 * @author ssethupathi
 * 
 */
public class ActionExecuteT24UnitTest extends AbstractAction {

    /**
     * Builds and sends http request for the test execution from the parameters
     * passed-in.
     */
    public Response processAction(String[] args) {
        String sessionToken = args[0];
        String fileName = args[1];
        String fileDirectory = args[2];
        String fileContent = args[3];
        String userName = args[4];
        Command command = buildCommand(sessionToken, fileName, fileContent, fileDirectory, userName);
        Response response = httpMgr.sendCommand(command);
        return response;
    }

    @Override
    public Response processResponse(Command cmd, Response res, String[] args) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Builds command object to be used during http request creation
     * 
     * @param sessionToken
     * @param fileName
     * @param fileContent
     * @param fileDirectory
     * @param username
     * @return Command
     */
    private Command buildCommand(String sessionToken, String fileName, String fileContent, String fileDirectory, String username) {
        Command cmd = new Command(ProtocolConstants.COMMAND_EXECUTE_TEST);
        cmd.setBody(buildCommandXml(sessionToken, fileName, fileContent, fileDirectory, username));
        return cmd;
    }

    /**
     * Builds the command in XML format based on the protocol laid out.
     * 
     * @param sessionToken
     * @param fileName
     * @param fileContent
     * @param fileDirectory
     * @param username
     * @return command in Xml format
     */
    private String buildCommandXml(String sessionToken, String fileName, String fileContent, String fileDirectory, String username) {
        String header = ActionCommon.getCommandHeader();
        Document dom = DocumentHelper.createDocument();
        Element dev = buildCommonCommandBody(dom, sessionToken);
        dev.addElement("useBase64").addText("true");
        dev.addElement("action").addText("EXECUTE.TEST");
        dev.addElement("prog").addText(fileName);
        dev.addElement("bp").addText(fileDirectory);
        ProtocolUtil pu = new ProtocolUtil();
        String fileEncoded = pu.encodeComplete(fileContent);
        dev.addElement("code").addText(fileEncoded);
        String compileWithDebugProperty = EclipseUtil.getPreferenceStore().getString(PluginConstants.T24_COMPILE_WITH_DEBUG);
        if (Boolean.parseBoolean(compileWithDebugProperty)) {
            dev.addElement("options").addText("D");
        }
        dev.addElement("lusername").addText(username);
        return header + dom.asXML();
    }

    /**
     * Builds the common command elements.
     * 
     * @param dom
     * @param tokenString
     * @return body element of the request
     */
    private Element buildCommonCommandBody(Document dom, String tokenString) {
        Element root = dom.addElement("ofsSessionRequest").addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.addElement("requestType").addText("UTILITY.ROUTINE");
        root.addElement("token").addText(tokenString);
        root.addElement("detailtag");
        Element reqArgs = root.addElement("requestArguments");
        reqArgs.addElement("routineName").addText("EB.DEV.STUDIO");
        Element routineArgs = reqArgs.addElement("routineArgs");
        Element dev = routineArgs.addElement("dev");
        reqArgs.addElement("companyId");
        reqArgs.addElement("windowName");
        reqArgs.addElement("compScreen");
        reqArgs.addElement("unlock");
        reqArgs.addElement("closing");
        return dev;
    }
}
