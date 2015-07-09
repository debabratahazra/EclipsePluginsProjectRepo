package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnectionType;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Class acts as a model controller for the {@link RemoteSite}.<br>
 * This controller creates RemoteSite object from the xml persisted in memento
 * and persisting the RemoteSite object into memento when created/modified.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteSiteModelController {

    private static ProtocolUtil pu = new ProtocolUtil();

    /**
     * Returns the xml string representation of the {@link RemoteSite} which can
     * be persisted in memento.<br>
     * <br>
     * The example structure of the xml for a remote site as follows. <br>
     * <br>
     * t24.remotesite.dev15=&lt;remote&gt;
     * &lt;site_name&gt;dev15&lt;/site_name&gt;
     * &lt;default&gt;true&lt;/default&gt;
     * &lt;host_ip&gt;10.92.5.15&lt;/host_ip&gt;
     * &lt;user_name&gt;devac1&lt;/user_name&gt;
     * &lt;password&gt;****&lt;/password&gt;
     * &lt;port_number&gt;20002&lt;/port_number&gt;
     * &lt;platform&gt;UNIX&lt;/platform&gt;
     * &lt;connection_type&gt;jca&lt;/connection_type&gt;
     * &lt;protocol&gt;sftp&lt;/protocol&gt;
     * &lt;/remote&gt;
     * 
     * @param remoteSite {@link RemoteSite}
     * @return remote site in xml form
     */
    public static String marshal(RemoteSite remoteSite) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<remote>");
        buffer.append("<site_name>" + remoteSite.getSiteName() + "</site_name>");
        buffer.append("<default>" + remoteSite.isDefault() + "</default>");
        buffer.append("<host_ip>" + remoteSite.getHostIP() + "</host_ip>");
        buffer.append("<user_name>" + remoteSite.getUserName() + "</user_name>");
        buffer.append("<password>" + encodePassword(remoteSite.getPassword()) + "</password>");
        buffer.append("<port_number>" + remoteSite.getPortNumber() + "</port_number>");
        buffer.append("<platform>" + remoteSite.getPlatform().toString() + "</platform>");
        buffer.append("<connection_type>" + remoteSite.getConnectionType().toString() + "</connection_type>");
        buffer.append("<protocol>" + remoteSite.getProtocol().toString() + "</protocol>");
        buffer.append("</remote>");
        return buffer.toString();
    }

    /**
     * Constructs a {@link RemoteSite} object using the persisted xml available
     * from the memento.
     * 
     * @param siteInfo
     * @return remote site
     */
    public static RemoteSite unmarshal(String siteInfo) {
        RemoteSite remoteSite = null;
        String siteName = XmlUtil.getSafeText(siteInfo, "//site_name");
        String defaultStatus = XmlUtil.getSafeText(siteInfo, "//default");
        String hostIp = XmlUtil.getSafeText(siteInfo, "//host_ip");
        String userName = XmlUtil.getSafeText(siteInfo, "//user_name");
        String password = XmlUtil.getSafeText(siteInfo, "//password");
        String portNumber = XmlUtil.getSafeText(siteInfo, "//port_number");
        if (portNumber == null || portNumber.length() <= 0) {
            portNumber = "20002"; // default port used by jbase agent
        }
        String platformStr = XmlUtil.getSafeText(siteInfo, "//platform");
        RemoteSitePlatform platform = RemoteSitePlatform.getByValue(platformStr);
        if (platform == null) {
            platform = RemoteSitePlatform.UNIX;
        }
        String connectionTypeStr = XmlUtil.getSafeText(siteInfo, "//connection_type");
        RemoteConnectionType connectionType = RemoteConnectionType.getByValue(connectionTypeStr);
        String protocolStr = XmlUtil.getSafeText(siteInfo, "//protocol");
        Protocol protocol = Protocol.getByValue(protocolStr);
        if (protocol == null) {
            protocol = Protocol.SFTP;
        }
        remoteSite = new RemoteSite(siteName, hostIp, userName, decodePassword(password), platform, isDefault(defaultStatus),
                portNumber, connectionType , protocol, null);
        return remoteSite;
    }

    private static boolean isDefault(String defaultStatus) {
        if (defaultStatus == null || defaultStatus.length() <= 0) {
            return false;
        }
        return Boolean.parseBoolean(defaultStatus);
    }

    private static String encodePassword(String password) {
        return pu.encodeComplete(password);
    }

    private static String decodePassword(String password) {
        return pu.decodeComplete(password);
    }
}
