package com.temenos.ds.t24.data.eson.tests;

import java.io.IOException;

import com.temenos.ds.t24.data.eson.T24ResourceProviderFacade;
import com.temenos.ds.t24.data.rps.RemoteServiceException;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse.Field;

/**
 * Rudimentary utility to search for specific record. Adapt the test() method to your need
 * @author yandenmatten
 */
public class SearchT24Tool {
	
	private static final String TABLE = "CUSTOMER";
	private static final int START_INDEX = 101000;
	private static final int END_INDEX = 102000;

	public static void main(String[] args) throws RemoteServiceException, IOException {
		T24ResourceProviderFacade t24ComponentFwkFacade = new T24ResourceProviderFacade();
		try {
			t24ComponentFwkFacade.connectTAFC("/tafc-integrationtest-server.properties");
			
			for (int i=START_INDEX; i<END_INDEX; i++) {
				try {
					GetResourceResponse resource = t24ComponentFwkFacade.readRecord(TABLE, ""+i);
					for (Field field : resource.fields) {
						if (fieldCritegraMatch(field)) {
							System.out.println(
									"Found ! ID= " + i + " " +
									"field.name: " + field.name + 
									" .value: " + field.value + 
									" .mv: " + field.mv + 
									" .sv: " + field.sv);
						}
					}
				} catch (Exception e) {
					// Ignore
				}
			}
			System.out.println("Done!");
		} finally {
			t24ComponentFwkFacade.close();
		}
	}

	private static boolean fieldCritegraMatch(Field field) {
		boolean test;
		// test = ((field.mv != null && field.mv > 1) || (field.sv != null && field.sv > 1));
		// test = (field.name.equals("OVERRIDE") && !field.value.equals(""));
		test = field.name.equals("COUNTRY") && !field.value.equals("GREAT BRITAIN");
		return test;
	}

}
