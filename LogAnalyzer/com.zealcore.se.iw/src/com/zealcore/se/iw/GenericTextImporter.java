package com.zealcore.se.iw;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.generic.GenericLogFile;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.iw.wizard.internal.MessageTree;
import com.zealcore.se.iw.wizard.internal.TextParser;

@SuppressWarnings("unchecked")
public class GenericTextImporter implements IImporter {

	private static final String FILENAME_IS = "Filename is ";

	private static final String NO_CONFIG_FOUND = "No Import Wizard configuration found for file: ";

	private File file;

	public GenericTextImporter() {
	}

	private static IServiceProvider serviceProvider;

	private final TextParser parser = new TextParser();

	public Iterable<ILogEvent> getLogEvents() {
		final GenericTextImportData config = getImporterConfiguration(this.file);
		return getLogEvents(config, this.file);
	}

	public Iterable<ILogEvent> getLogEvents(final GenericTextImportData config,
			final File thefile) {
		MessageTree tree = null;
		try {
			tree = this.parser.parse(thefile, config);
		} catch (IOException e1) {
			throw new ImportException("File " + thefile.getName()
                    + " could not be imported.", e1);
		}
		final GenericLogFile genericLogFile = GenericLogFile.valueOf(thefile);
		genericLogFile.setImporterId(GenericTextImporter.class.getName());

		final EventCompiler compiler = new EventCompiler(config
				.getDefaultEventName(), genericLogFile);
		tree.accept(compiler);
		final List<ILogEvent> events = compiler.getEvents();
		Collections.sort(events);
		return events;
	}

	public static void setServiceProvider(final IServiceProvider serviceProvider) {
		GenericTextImporter.serviceProvider = serviceProvider;
	}

	private static GenericImportRegistry getRegistry() {
		return GenericTextImporter.serviceProvider
				.getService(GenericImportRegistry.class);
	}

	public boolean canRead(final File file) {
		return GenericTextImporter.getRegistry().canRead(file);
	}

	public Iterable<IArtifact> getArtifacts() {
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zealcore.se.core.IImporter#setLogFile(java.io.File)
	 */
	public void setContext(final ImportContext context) {
		File log = context.getFile();
		getImporterConfiguration(log);
		if (this.file != null) {
			throw new IllegalStateException(
					"SetLogFile should only be called once");
		}
		this.file = log;
	}

	private GenericTextImportData getImporterConfiguration(final File file) {
		try {
			final GenericImportRegistry service = GenericTextImporter
					.getRegistry();
			final GenericTextImportData config = service.getImportData(file);
			if (config == null) {
				throw new RuntimeException(GenericTextImporter.NO_CONFIG_FOUND
						+ file.getName());
			}
			return config;
		} catch (final IllegalArgumentException e) {
			throw new RuntimeException(FILENAME_IS + file.getName(), e);
		} catch (final IllegalStateException e) {
			throw new RuntimeException(FILENAME_IS + file.getName(), e);
		}
	}

	public int size() {
		return 0;
	}

	@Override
	public String toString() {
		return "Generic Text Importer (Requires pre-configurated data)";
	}
}
