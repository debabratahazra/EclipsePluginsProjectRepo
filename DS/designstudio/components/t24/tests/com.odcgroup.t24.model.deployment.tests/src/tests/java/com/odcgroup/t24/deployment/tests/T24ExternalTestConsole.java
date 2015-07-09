package com.odcgroup.t24.deployment.tests;

import java.util.ArrayList;

import com.odcgroup.t24.server.external.ui.builder.T24DeployConsole;

public class T24ExternalTestConsole extends T24DeployConsole {

	private ArrayList<String> infoList = new ArrayList<String>();

	private ArrayList<String> errorList = new ArrayList<String>();

	@Override
	public void printInfo(String message) {
		super.printInfo(message);
		infoList.add(message + "\n");
	}

	@Override
	public void printDebug(String message) {
		// TODO Auto-generated method stub
		super.printDebug(message);
	}

	@Override
	public void printError(String message, Throwable t) {
		// TODO Auto-generated method stub
		super.printError(message, t);
		errorList.add(message + " (" + t.getMessage() + " )");
	}

	public ArrayList<String> getInfoList() {
		return infoList;
	}

	public ArrayList<String> getErrorList() {
		return errorList;
	}

	public void cleanConsole() {
		infoList.clear();
		errorList.clear();
	}
}
