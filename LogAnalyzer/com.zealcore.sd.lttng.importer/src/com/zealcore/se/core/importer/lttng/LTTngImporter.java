package com.zealcore.se.core.importer.lttng;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import com.zealcore.plugin.control.LicenseException;
import com.zealcore.plugin.control.LicenseManager;
import com.zealcore.se.core.IExtendedImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.generic.GenericLogFile;

public class LTTngImporter implements IExtendedImporter, Closeable {
	private static final String PLUGIN_ID = "com.zealcore.sd.lttng.importer";

	private static final String PLUGIN_NAME = "LTTng Trace Importer";

	private ImportContext importContext;

	private File file;

	private GenericLogFile genericLogFile;

	private final LTTngEventFactory eventFactory = new LTTngEventFactory();

	public void close() throws IOException {
		// XXX: Due to a bug in the FLEXlm Java client library on Solaris,
		// we cannot check in the FLEXlm license without hanging the whole
		// JVM when checking out the FLEXlm license again.
		//LicenseManager.getInstance().unregisterPlugin(PLUGIN_ID);
	}

	public boolean canRead(File file) {
		try {
			String fileName = file.toString();
			int mid = fileName.lastIndexOf(".");
			if (!("lttng".equalsIgnoreCase(fileName.substring(mid + 1, fileName
					.length())))) {
				return false;
			}
			/*
			 * SAXXmlParser sx = new SAXXmlParser(); sx.parseDocument(new
			 * FileInputStream(file));
			 * JniTraceFactory.getJniTrace(sx.getFolder(), false); } catch
			 * (JniException e) {
			 * System.out.println("Unsupported trace file version"); return
			 * false;
			 */
		} catch (Exception e) {
			System.out.println("Not a LTTng trace file");
			return false;
		}
		return true;
	}

	public void setContext(ImportContext context) {
		// Check out FLEXlm license.
		try {
			LicenseManager lm = LicenseManager.getInstance();
			long cookie = (long) (Math.random() * Long.MAX_VALUE);
			long r = lm.registerPlugin(PLUGIN_ID, "1.000", cookie);
			long t = System.currentTimeMillis() / 10000;
			if ((r != (cookie ^ t)) && (r != (cookie ^ (t - 1)))) {
				throw new LicenseException("Incorrect response value");
			}
		} catch (LicenseException e) {
			throw new ImportException("Could not check out a FLEXlm license " +
				"for the " + PLUGIN_NAME + " plugin (" + PLUGIN_ID + ").", e);
		}

		if (importContext != null) {
			throw new IllegalStateException("Can't import log twice!");
		}
		importContext = context;
		if (file != null) {
			throw new IllegalStateException(
					"setContext should only be called once");
		}

		file = importContext.getFile();
		file = context.getFile();
		genericLogFile = GenericLogFile.valueOf(file);
		genericLogFile.setImportedAt(System.currentTimeMillis());

		if (!canRead(file)) {
			throw new ImportException("Invalid file format. " + toString()
					+ " can't read the file: " + file.getName() + " on path: "
					+ file.getAbsolutePath());
		}
	}

	public Iterable<TypeDescription> getTypeDescriptions() {
		return LTTngEventFactory.getTypeDescriptions();
	}

	public Iterable<GenericEventInfo> getEvents() {
		try {
			initialize();
		} catch (IOException e) {
			throw new ImportException("Failed to read events from file: "
					+ this.importContext.getFile().getAbsolutePath(), e);
		}

		return new Iterable<GenericEventInfo>() {
			public Iterator<GenericEventInfo> iterator() {
				try {
					return new LTTngElementIterator();
				} catch (Exception e) {
					throw new ImportException(e.getMessage(), e);
				}
			}
		};
	}

	public Iterable<GenericArtifactInfo> getArtifacts() {
		return eventFactory.getArtifacts();
	}

	/**
	 * Sets up the importer before it can start reading log events.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		genericLogFile = new GenericLogFile();
		genericLogFile.setFileName(importContext.getFile().getName());
		genericLogFile.setImporterId(this.getClass().getName());
		genericLogFile.setImportedAt(System.currentTimeMillis());
		genericLogFile.setVersion(1);
	}

	class LTTngElementIterator implements Iterator<GenericEventInfo> {
		LttTrace trace;
		private LttEvent event = null;
		GenericEventInfo readEvent = null;

		public LTTngElementIterator() throws Exception {
			SAXXmlParser sx = new SAXXmlParser();
			try {
				sx.parseDocument(new FileInputStream(file));
				trace = new LttTrace(sx.getFolder());
			} catch (FileNotFoundException e) {
				throw new Exception("Invalid Log File  : File does not exists");
			} catch (Exception e) {
				throw e;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			try {
				event = trace.hasNextEvent();
				readEvent = createLAEventFromLTTEvent(event);

				while ((readEvent == null) && (event != null)) {
					event = trace.hasNextEvent();
					readEvent = createLAEventFromLTTEvent(event);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ImportException(e.getMessage(), e);
			}
			if (readEvent != null) {
				return true;
			} else {
				return false;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		public GenericEventInfo next() {
			if (readEvent == null) {
				System.out.println("Event is null");
			}
			return readEvent;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Creates a log event from the given string. A log event always have a time
	 * stamp, a reference to its log file and a log event type. Any number of
	 * properties can be set to a generic log event using the addPropery method.
	 * 
	 * @param readLine
	 *            string with event data
	 * @return the created log event.
	 */
	public GenericEventInfo createLAEventFromLTTEvent(final LttEvent lttEvent) {
		return eventFactory.getEvent(lttEvent);
	}

	@Override
	public String toString() {
		return PLUGIN_NAME;
	}
}
