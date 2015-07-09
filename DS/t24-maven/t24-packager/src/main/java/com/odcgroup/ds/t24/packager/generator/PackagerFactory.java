package com.odcgroup.ds.t24.packager.generator;

import java.io.File;

import com.odcgroup.ds.t24.packager.basic.TAFCBasicCompiler;
import com.odcgroup.ds.t24.packager.basic.external.tool.ExternalTool;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidator;
import com.odcgroup.ds.t24.packager.data.DataGenerator;
import com.odcgroup.ds.t24.packager.data.validator.DataHeaderValidator;
import com.odcgroup.ds.t24.packager.t24gen.T24GenProjectPackager;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectPackager;
import com.odcgroup.ds.t24.packager.t24tafj.TafjProjectPackager;

public class PackagerFactory {
	
	public T24Packager createPackager(PackageTypeEnum type, String release, String componentName, String rev, String os, String version, File outputFolder) {
		T24Packager packager = new T24Packager(type, release, componentName, rev, os, version, outputFolder);
	
		// Data generator
		DataGenerator dataGenerator = new DataGenerator();
		packager.setDataGenerator(dataGenerator);
		dataGenerator.setPackager(packager);
		dataGenerator.setValidator(new DataHeaderValidator());
		
		// Basic compiler
		TAFCBasicCompiler tafcCompiler = new TAFCBasicCompiler();
		tafcCompiler.setPackager(packager);
		packager.setTafcCompiler(tafcCompiler);
		BasicValidator basicValidator = new BasicValidator();
		tafcCompiler.setValidator(basicValidator);
		ExternalTool externalTool = new ExternalTool();
		externalTool.setPackager(packager);
		tafcCompiler.setExternalTool(externalTool);
		
		// DS generated T24 project
		T24GenProjectPackager t24genProject = new T24GenProjectPackager();
		packager.setT24genProjectPackager(t24genProject);
		t24genProject.setPackager(packager);

		// T24 project packager
		T24ProjectPackager t24ProjectPackager = new T24ProjectPackager();
		packager.setT24ProjectPackager(t24ProjectPackager);
		t24ProjectPackager.setPackager(packager);
		
		// TAFJ project packager
		TafjProjectPackager t24TafjProjectPackager = new TafjProjectPackager();
		packager.setT24TafjProjectPackager(t24TafjProjectPackager);
		t24TafjProjectPackager.setPackager(packager);
		
		return packager;
	}
	
}
