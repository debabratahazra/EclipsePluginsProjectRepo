package com.temenos.ds.t24.data.rps;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * ResourceProviderService for use by DS.
 *
 * <p>
 * With documentation (because CF WS gen. impl. do not publish any JavaDoc / src
 * artifacts, doc in *.components not used; must consult jBC routine
 * documentation using e.g. http://10.44.5.14:9090/source/, like
 * http://10.44.5.14
 * :9090/source/search?q=ResourceProviderService&defs=&refs=&path
 * =&hist=&project=T24).
 *
 * <p>
 * All methods throw the checked RemoteServiceException (instead of, possibly
 * different by implementation, runtime exceptions).
 *
 * <p>
 * We do NOT extend ResourceProviderService, because there everything
 * returns void like in jBC and there are funky outParams - we want a real Java
 * API. We also do NOT extends ResourceProviderServiceWSPortType because that
 * takes funky JAXB objects - we want a real Java API
 *
 * @author Michael Vorburger
 */
public interface ResourceProviderService {

	public static class GetResourceResponse {
		public static class Field {
			public final String name;
			public final String value;
			public final Integer mv;
			public final String mvGroupName;
			public final Integer sv;
			public final String svGroupName;
			
			public Field(String name, String value) {
				this(name, value, 1, "", 1, "");
			}

			public Field(String name, String value, int mv, String mvGroupName) {
				this(name, value, mv, mvGroupName, 1, "");
			}
			
			public Field(String name, String value, int mv, String mvGroupName, int sv, String svGroupName) {
				this.name = name;
				this.value = value;
				this.mv = mv;
				this.mvGroupName = mvGroupName;
				this.sv = sv;
				this.svGroupName = svGroupName;
			}
			
			public boolean isValid() {
				if (name == null) return false;
				if (mv == null || mv < 1) return false;
				if (sv == null || sv < 1) return false;
				if (mv > 1 && StringUtils.isEmpty(mvGroupName)) return false;
				if (sv > 1 && (StringUtils.isEmpty(mvGroupName) || StringUtils.isEmpty(svGroupName))) return false;
				if (StringUtils.isEmpty(mvGroupName) && StringUtils.isNotEmpty(svGroupName)) return false;
				if (value == null) return false;
				
				return true;
			}
			
			public String toString() {
				return "name: \"" + name + "\", " +
						"value: \"" + value + "\", " + 
						"mv: " + mv + ", " +
						"mvGroupName: \"" + mvGroupName + "\", " +
						"sv: " + sv + ", " +
						"svGroupName: \"" + svGroupName + "\"";
			}
			
			public static boolean equals(Field field1, Field field2) {
				return ObjectUtils.compare(field1.name, field2.name) == 0 &&
						ObjectUtils.compare(field1.value, field2.value) == 0 &&
						ObjectUtils.compare(field1.mv, field2.mv) == 0 &&
						ObjectUtils.compare(field1.mvGroupName, field2.mvGroupName) == 0 &&
						ObjectUtils.compare(field1.sv, field2.sv) == 0 &&
						ObjectUtils.compare(field1.svGroupName, field2.svGroupName) == 0;
			}
		}

		public final String resourceName;
		public final List<Field> fields = new ArrayList<Field>();

		public GetResourceResponse(String resourceName) {
			this.resourceName = resourceName;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("GetResourceResponse\n");
			sb.append("===================\n");
			sb.append("resourceName: " + resourceName + "\n");
			sb.append(
					StringUtils.rightPad("Name", 20) + 
					"|" +
					StringUtils.rightPad("Value", 30) + 
					"|" +
					StringUtils.rightPad("mv", 3) +
					"|" +
					StringUtils.rightPad("sv", 3) +
					"\n");
			sb.append(StringUtils.rightPad("", 59, "-") + "\n");
			for (GetResourceResponse.Field field: fields) {
				sb.append(
						StringUtils.rightPad(field.name, 20) + 
						"|" +
						StringUtils.rightPad(field.value, 30) + 
						"|" +
						StringUtils.rightPad(field.mv.toString(), 3) +
						"|" +
						StringUtils.rightPad(field.sv.toString(), 3) +
						"\n");
			}
			sb.append(StringUtils.rightPad("", 59, "-") + "\n");
			return sb.toString();
		}
	}

	/**
	 * GET Resource.
	 *
	 * @param asUser the T24 User to run as
	 * @param interactionCtx the T24InteractionContext
	 * @param resourceName T24 Application name (or Version? or Enquiry?)
	 * @param id T24 Application Record ID
	 * @param resourceState TODO DOC
	 */
	GetResourceResponse getResource(T24UserDetails asUser, T24InteractionContext interactionCtx, String resourceName, String id, String resourceState) throws RemoteServiceException;

	/**
	 * INPUT Resource
	 * @param userDetails
	 * @param inT24InteractionContext
	 * @param resourceName
	 * @param id
	 * @param resourceState
	 * @param autoOverride
	 * @throws RemoteServiceException
	 */
	void inputResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext, String resourceName, String id, String resourceState, GetResourceResponse resourceResponse, boolean autoOverride) throws RemoteServiceException;

	/**
	 * AUTHORISE Resource
	 * @param userDetails
	 * @param inT24InteractionContext 
	 * @param resourceName
	 * @param id
	 */
	void authorizeResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext, String resourceName, String id) throws RemoteServiceException;
}
