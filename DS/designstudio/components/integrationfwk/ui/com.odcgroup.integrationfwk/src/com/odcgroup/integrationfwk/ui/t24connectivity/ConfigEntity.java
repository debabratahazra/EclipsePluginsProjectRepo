package com.odcgroup.integrationfwk.ui.t24connectivity;

/**
 * Model class containing all connectivity details.
 * @author eswaranmuthu
 *
 */
public class ConfigEntity {
	 
	        private Boolean agentConnectivity;
	        private Boolean serviceConnectivity;
	        private String hostName;
	        private int portNumber;
	        private String ofsSource;
	        private String charSet;
	        private Boolean enableAgentCredentials;
	        private String agentUserName;
	        private String agentPassword;
	        private Boolean enableSSL;
	        private String dn;
	        private String serviceConnectionString;
	        private Boolean rememberCredentials;
			public Boolean getAgentConnectivity() {
				return agentConnectivity;
			}
			public void setAgentConnectivity(Boolean agentConnectivity) {
				this.agentConnectivity = agentConnectivity;
			}
			public Boolean getServiceConnectivity() {
				return serviceConnectivity;
			}
			public void setServiceConnectivity(Boolean serviceConnectivity) {
				this.serviceConnectivity = serviceConnectivity;
			}
			public String getHostName() {
				return hostName;
			}
			public void setHostName(String hostName) {
				this.hostName = hostName;
			}
			public int getPortNumber() {
				return portNumber;
			}
			public void setPortNumber(int portNumber) {
				this.portNumber = portNumber;
			}
			public String getOfsSource() {
				return ofsSource;
			}
			public void setOfsSource(String ofsSource) {
				this.ofsSource = ofsSource;
			}
			public String getCharSet() {
				return charSet;
			}
			public void setCharSet(String charSet) {
				this.charSet = charSet;
			}
			public Boolean getEnableAgentCredentials() {
				return enableAgentCredentials;
			}
			public void setEnableAgentCredentials(Boolean enableAgentCredentials) {
				this.enableAgentCredentials = enableAgentCredentials;
			}
			public String getAgentUserName() {
				return agentUserName;
			}
			public void setAgentUserName(String agentUserName) {
				this.agentUserName = agentUserName;
			}
			public String getAgentPassword() {
				return agentPassword;
			}
			public void setAgentPassword(String agentPassword) {
				this.agentPassword = agentPassword;
			}
			public Boolean getEnableSSL() {
				return enableSSL;
			}
			public void setEnableSSL(Boolean enableSSL) {
				this.enableSSL = enableSSL;
			}
			public String getDn() {
				return dn;
			}
			public void setDn(String dn) {
				this.dn = dn;
			}
			public String getServiceConnectionString() {
				return serviceConnectionString;
			}
			public void setServiceConnectionString(String serviceConnectionString) {
				this.serviceConnectionString = serviceConnectionString;
			}
			public Boolean getRememberCredentials() {
				return rememberCredentials;
			}
			public void setRememberCredentials(Boolean rememberCredentials) {
				this.rememberCredentials = rememberCredentials;
			}
}
