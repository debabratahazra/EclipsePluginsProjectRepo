package com.odcgroup.integrationfwk.ui.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.Flow;

public class CacheManager {

	public static String flowToXML(Flow flow) {
		StringWriter sw = new StringWriter();
		Marshaller marshaller = null;
		try {
			marshaller = getMarshaller(flow);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

		} catch (JAXBException e) {
			TWSConsumerLog.logError(e);
		}

		try {
			marshaller.marshal(flow, sw);
		} catch (JAXBException e) {
			TWSConsumerLog.logError(e);
		}
		return sw.toString();

	}

	public static Flow getFlow(String filePath) {
		new StringWriter();
		Flow flow = null;
		try {
			Unmarshaller m = getUnmarshaller(new Flow());
			flow = (Flow) m.unmarshal(new FileInputStream(filePath));
		} catch (JAXBException exception) {
			// TODO handle the exception
		} catch (FileNotFoundException exception) {
			// TODO handle the exception
		}
		return flow;
	}

	public static Flow getFlowFromString(String xmlContent) {
		Flow flow = null;
		new StringWriter();
		try {
			Unmarshaller m = getUnmarshaller(new Flow());
			StringReader reader = new StringReader(xmlContent);
			flow = (Flow) m.unmarshal(reader);
		} catch (JAXBException exception) {
			exception.printStackTrace();
			// TODO handle the exception
		}

		return flow;
	}

	private static Marshaller getMarshaller(Object clazz) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(clazz.getClass());
		Marshaller m = jc.createMarshaller();
		return m;
	}

	private static Unmarshaller getUnmarshaller(Object className)
			throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(className.getClass());
		Unmarshaller um = jc.createUnmarshaller();
		return um;
	}

	public String eventToXML(Event event) {
		StringWriter sw = new StringWriter();
		Marshaller marshaller = null;
		try {
			marshaller = getMarshaller(event);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

		} catch (JAXBException e) {
			TWSConsumerLog.logError(e);
		}
		try {
			marshaller.marshal(event, sw);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			TWSConsumerLog.logError(e);
		}
		return sw.toString();

	}

	public Event getEvent(String filePath) {
		new StringWriter();
		Event event = null;
		try {
			Unmarshaller m = getUnmarshaller(new Event());
			event = (Event) m.unmarshal(new FileInputStream(filePath));
		} catch (JAXBException exception) {
			exception.printStackTrace();
			// TODO handle the exception
		} catch (FileNotFoundException exception) {
			// TODO handle the exception
			exception.printStackTrace();
		}

		return event;
	}

	public Event getEventFromString(String xmlContent) {
		Event event = null;
		new StringWriter();
		try {
			Unmarshaller m = getUnmarshaller(new Event());
			StringReader reader = new StringReader(xmlContent);
			event = (Event) m.unmarshal(reader);
		} catch (JAXBException exception) {
			exception.printStackTrace();
			// TODO handle the exception
		}

		return event;
	}
}
