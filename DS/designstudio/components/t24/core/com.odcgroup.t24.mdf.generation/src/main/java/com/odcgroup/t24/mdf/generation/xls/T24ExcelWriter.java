package com.odcgroup.t24.mdf.generation.xls;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;

public class T24ExcelWriter implements ModelWriter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(T24ExcelWriter.class);	
	
	private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);
	
	private ExcelContext ctx;
	
	private OutputStreamFactory streamFactory;
	
	@Override
	public void setMessageHandler(MessageHandler handler) {
		if (handler == null) {
			throw new NullPointerException("handler");
		}

		this.messageHandler = handler;
	}

	@Override
	public String getName() {
		return "Excel file generator";
	}

	@Override
	public void write(MdfDomain domain, Collection<MdfDomain> importedDomains, OutputStreamFactory factory) throws IOException {
		
		streamFactory = factory;
		
		try {
			new T24Domain(domain).writeTo(ctx, factory);
		} catch (Exception ex) {
			ctx.error(ex.getLocalizedMessage(), ex);
			throw new IOException(ex.getMessage());
		}

	}

	public void open() {
		ctx = new ExcelContext();
		ctx.setMessageHandler(messageHandler);
	}

	public void close() throws IOException {
		// write 
		if (ctx != null && streamFactory != null) {
			try {
				new T24DataModel().write(ctx, streamFactory);
			} catch (Exception ex) {
				ctx.error(ex.getLocalizedMessage(), ex);
				throw new IOException(ex.getMessage());
			}
		}
	}

}
