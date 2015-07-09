package com.odcgroup.edge.t24.generation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * TODO: Document me!
 *
 * @author sfile
 *
 */
public class XmlCompare {

	long m_metaSize = 0;
	long m_dataSize = 0;
	
	public XmlCompare()
	{
		
	}
	
	static void parseXml(Element p_element, MessageDigest p_md, ArrayList<String> p_ignoreList) 
	{
		p_md.update(p_element.getName().getBytes());
		
		String content = p_element.getText();
		
		if(content != null)
		{
			p_md.update(content.getBytes());
		}
		
		for(Attribute attribute : p_element.getAttributes())
		{
			String name = attribute.getName();
			
			if(p_ignoreList.contains(name))
			{
				continue;
			}
			
			String value = attribute.getValue();
			
			if(value != null)
			{
				p_md.update(value.getBytes());
			}
		}
		
		for(Element child : p_element.getChildren())
		{
			parseXml(child, p_md, p_ignoreList);
		}
	}
	
	void metaVersusData(File p_file) throws Exception
	{
		SAXBuilder leftBuilder= new SAXBuilder();
		
		Document doc = leftBuilder.build(p_file);
		
		metaVersusData(doc.getRootElement());
		
	}
	
	void metaVersusData(Element p_element) 
	{
		m_metaSize += p_element.getName().length();
		
		String content = p_element.getText();
		
		if(content != null)
			m_dataSize += content.length();
	
		
		for(Attribute attribute : p_element.getAttributes())
		{
			String name = attribute.getName();
		
			m_metaSize += name.length();
			
			String value = attribute.getValue();
			
			if(value != null)
			{
				m_dataSize += value.length();
			}
		}
		
		for(Element child : p_element.getChildren())
		{
			metaVersusData(child);
		}
	}
	
	public void printMetaVersusData()
	{
		System.out.println("Meta size = " + m_metaSize);
		System.out.println("Data size = " + m_dataSize);
	}
	
	public static boolean isTheSame(InputStream p_left, InputStream p_right, ArrayList<String> p_ignoreList) throws Exception
	{
		SAXBuilder leftBuilder= new SAXBuilder();
		
		Document doc = leftBuilder.build(p_left);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		md.reset();
		
		parseXml(doc.getRootElement(), md, p_ignoreList);
		
		byte[] leftDigest = md.digest();
		
		md.reset();
		
		doc = leftBuilder.build(p_right);
		
		parseXml(doc.getRootElement(), md, p_ignoreList);
		
		byte[] rightDigest = md.digest();
		
		return (Arrays.equals(leftDigest, rightDigest));
		
	}
	
	public static boolean isTheSame(File p_left, File p_right, ArrayList<String> p_ignoreList) throws Exception
	{
		FileInputStream left = new FileInputStream(p_left);
		FileInputStream right = new FileInputStream(p_right);
		
		return isTheSame(left, right, p_ignoreList);
	}
}
