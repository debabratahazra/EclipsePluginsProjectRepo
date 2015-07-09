package com.odcgroup.iris.rim.generation.streams.mappers;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.IOUtils;

/**
 * TODO: Document me!
 *
 * @author taubert (mvorburger)
 */
public class RimWriter {

	private String sIdent = "";
	private boolean ident = true;
	private final Writer writer;

	public RimWriter(Writer writer) {
		this.writer = writer;
	}

	public void println(String sLine){
		if (sLine.trim().endsWith("}") || sLine.trim().endsWith("]")){
			desIdent();
		}
		try{
			writer.write((ident?sIdent:"") + sLine);
			writer.write(IOUtils.LINE_SEPARATOR);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		if (sLine.trim().endsWith("{")  || sLine.trim().endsWith("[")){
			ident();
		}
		ident = true;
	}
	
	public void print(String sLine){
		if (sLine.trim().endsWith("}") || sLine.trim().endsWith("]")){
			desIdent();
		}
		try{
			writer.write((ident?sIdent:"") + sLine);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		if (sLine.trim().endsWith("{")  || sLine.trim().endsWith("[")){
			ident();
		}
		ident = false;
	}
		
	public void ident(){
		sIdent += "   ";
	}

	public void desIdent(){
		int len = sIdent.length()-3;
		if (len >= 0){
			sIdent = sIdent.substring(0, len);
		}
	}

	
}
