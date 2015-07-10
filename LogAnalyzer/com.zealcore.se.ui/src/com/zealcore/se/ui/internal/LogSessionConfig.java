/**
 * 
 */
package com.zealcore.se.ui.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ImportExceptionList;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.ILogAdapter;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.ILogMark;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.util.ArtifactColorMap;

public class LogSessionConfig {

	/** The Constant CONFIG_NODE. */
	static final String CONFIG_NODE = "config";

	static final String TAG_LOGFILE = "log-file";

	static final String TAG_IMPORTED_FILES = "log-files";

	static final QualifiedName QN_IS_INITIALIZED = new QualifiedName(
			SeUiPlugin.PLUGIN_ID, "initialized");

	static final QualifiedName QN_COLOR_MAP = new QualifiedName(
			SeUiPlugin.PLUGIN_ID, DirectoryLogSession.COLOR_MAP_NODE);

	private static final String TAG_LOGADAPTER_ID = "importer";

	private static final String TAG_CURRENT_TIME = "logset.current.time";

	private static final String TAG_LOGMARK = "logset.logmark";

	private static final String TAG_UUID = "logset.uuid";

	private final DirectoryLogSession parent;

	private UUID id;

	private static final String START = "start";

	private static final String STATUS = "status";

	private static final String END = "end";

	static final String TAG_IMPORTER_VERSION = "importer-version";

	public static final int IIMPORTER_VERSION = 1;

	public static final int IEXTENDEDIMPORTER_VERSION = 2;

	LogSessionConfig(final DirectoryLogSession parent, final IFile configFile) {
		this.parent = parent;

	}

	void create() {
		final IFile file = getConfigFile();
		try {
			file.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (final CoreException e1) {
			throw new RuntimeException(e1);
		}
		if (!file.exists()) {

			final XMLMemento configMemento = XMLMemento
					.createWriteRoot(LogSessionConfig.CONFIG_NODE);
			configMemento.createChild(DirectoryLogSession.TIME_CLUSTERS_NODE);
			configMemento.createChild(DirectoryLogSession.COLOR_MAP_NODE);
			// FIXME Timecluster chain

			final StringWriter writer = new StringWriter();

			try {
				configMemento.save(writer);
				final ByteArrayInputStream input = new ByteArrayInputStream(
						writer.getBuffer().toString().getBytes());
				file.create(input, true, null);
			} catch (final CoreException e) {
				throw new RuntimeException(e);
			} catch (final IOException e) {
				// Wraps the exception and propagates it
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Gets the configuration.
	 * 
	 * @return the configuration
	 */
	IMemento getConfiguration() {
		// Create the config if it doesn't already exists
		create();
		final IFile file = getConfigFile();
		try {
			file.refreshLocal(IResource.DEPTH_ZERO, null);
			final InputStream contents = file.getContents();

			final XMLMemento memento = XMLMemento
					.createReadRoot(new InputStreamReader(contents));
			return memento;
		} catch (final WorkbenchException e) {
			throw new RuntimeException(e);
		} catch (final CoreException e) {
			throw new RuntimeException(e);
		}
	}

	UUID getId() {
		if (id == null) {
			id = UUID.randomUUID();
			write();
		}
		return id;
	}

	void read() {
		try {
			// May already been initialized (all info is stored as session
			// properties of the folder
			if (parent.getFolder().getSessionProperty(
					LogSessionConfig.QN_IS_INITIALIZED) != null) {
				return;
			}

			final IMemento configuration = getConfiguration();
			final String mementoId = configuration
					.getString(LogSessionConfig.TAG_UUID);
			if (mementoId != null) {
				id = UUID.fromString(mementoId);
			}
			configureArtifactColorMap(configuration);
			configureViewSets(configuration);
			parent.getFolder().setSessionProperty(
					LogSessionConfig.QN_IS_INITIALIZED, Boolean.TRUE);
			configureLogFiles(configuration);

		} catch (final CoreException e) {
			throw new RuntimeException(e);
		}
	}

	void write() {
		create();
		final XMLMemento configMemento = XMLMemento
				.createWriteRoot(LogSessionConfig.CONFIG_NODE);

		configMemento.putString(LogSessionConfig.TAG_CURRENT_TIME, Long
				.toString(parent.getDefaultViewSet().getCurrentTime()));
		for (final ILogMark logMark : parent.getDefaultViewSet().getLogmarks()) {
			final IMemento logmarkMemento = configMemento
					.createChild(LogSessionConfig.TAG_LOGMARK);
			((LogMark) logMark).saveState(logmarkMemento);
		}

		final IMemento colorMapNode = configMemento
				.createChild(DirectoryLogSession.COLOR_MAP_NODE);
		parent.getColorMap().saveState(colorMapNode);

		writeLogfiles(configMemento);
		configMemento.putString(LogSessionConfig.TAG_UUID, getId().toString());

		final WorkspaceJob job = new WorkspaceJob("Writing " + parent
				+ " configuration") {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				LogSessionConfig.this.writeToFile(configMemento);
				return Status.OK_STATUS;
			}
		};
		job.setRule(getConfigFile());
		job.setSystem(true);
		job.schedule();
	}

	private void configureArtifactColorMap(final IMemento configuration)
			throws CoreException {
		final IMemento colorMapNode = configuration
				.getChild(DirectoryLogSession.COLOR_MAP_NODE);

		final ArtifactColorMap map = new ArtifactColorMap();
		map.init(colorMapNode);

		parent.setColorMap(map);

		// Set a session property with the colormap for this DLS
		parent.getFolder().setSessionProperty(LogSessionConfig.QN_COLOR_MAP,
				map);
	}

	private void configureLogFiles(final IMemento configuration) {
		final IMemento child = configuration
				.getChild(LogSessionConfig.TAG_IMPORTED_FILES);

		if (child != null) {
			Integer importerVersionType = child
					.getInteger(TAG_IMPORTER_VERSION);
			if (importerVersionType == null) {
				importerVersionType = IEXTENDEDIMPORTER_VERSION;
			}
			parent.setImporterVersionType(importerVersionType);

			final List<ILogAdapter> logAdapters = new ArrayList<ILogAdapter>();
			final Logset logset = Logset.valueOf(getId());

			final IFolder folder = parent.getFolder();
			for (final IMemento iMemento : child
					.getChildren(LogSessionConfig.TAG_LOGFILE)) {
				final String fileName = iMemento
						.getString(LogSessionConfig.TAG_LOGFILE);
				final String adapterId = iMemento
						.getString(LogSessionConfig.TAG_LOGADAPTER_ID);

				final IResource resource = folder.findMember(fileName);
				if (resource instanceof IFile && adapterId != null) {
					final IFile file = (IFile) resource;
					final ILogAdapter adapter = IFWFacade.getLogAdapter(
							adapterId, importerVersionType);

					if (file.exists() && adapter != null) {
						adapter.setContext(ImportContext.valueOf(logset, file));
						logAdapters.add(adapter);
					}
				}
			}
			try {
				logset.addLogs(logAdapters);

				if ((child.getString(STATUS) != null)
						&& child.getString(STATUS).equalsIgnoreCase(START)) {
					logset.setImportSuccess(false);
				} else {
					logset.setImportSuccess(true);
				}
			} catch (final RuntimeException e) {
				if (e instanceof ImportException) {
					reportException((ImportException) e);
				} else if (e instanceof ImportExceptionList) {
					for (ImportException ie : ((ImportExceptionList) e)
							.getExceptions()) {
						reportException(ie);
					}
				} else {
					SeUiPlugin.reportUnhandledRuntimeException(this.getClass(),
							e, true);
				}
			}
		}
	}

	private void reportException(final ImportException e) {
		SeUiPlugin.reportError("Import failed", e);
		if (e.equals(e.getCause())) {
			SeUiPlugin.logError(e.getCause());
		} else {
			SeUiPlugin.logError(e);
		}
	}

	private void configureViewSets(final IMemento configuration) {
		final String string = configuration
				.getString(LogSessionConfig.TAG_CURRENT_TIME);
		Long current = 0L;
		if (string != null) {
			try {
				current = Long.valueOf(string);
			} catch (final NumberFormatException ex) {
				SeUiPlugin.logError(ex);

			}
		}
		parent.getDefaultViewSet().setCurrentTime(current);

		for (final IMemento memLogmark : configuration
				.getChildren(LogSessionConfig.TAG_LOGMARK)) {
			final LogMark mark = LogMark.valueOf(memLogmark);
			parent.getDefaultViewSet().addLogMark(mark);

		}
	}

	private IFile getConfigFile() {
		return parent.getFolder().getFile(DirectoryLogSession.CONFIG_FILE);
	}

	private void writeLogfiles(final XMLMemento configMemento) {
		/*
		 * Write the configuration of f1ilename - importer to the cfg
		 */
		final IMemento logfiles = configMemento
				.createChild(LogSessionConfig.TAG_IMPORTED_FILES);
		final Logset logset = Logset.valueOf(getId());
		if (logset.isImportSuccess()) {
			logfiles.putString(STATUS, END);
		} else {
			logfiles.putString(STATUS, START);
		}

		// Tag version-2 by default
		logfiles.putInteger(LogSessionConfig.TAG_IMPORTER_VERSION, parent
				.getImporterVersionType());
		for (final ILogAdapter adapter : logset.getLogs()) {
			final IFile file = adapter.getFile();
			if (file == null) {
				throw new IllegalStateException(
						"Log adapter resource is null! Adapter is " + adapter);
			} else {
				final String adapterId = adapter.getId();
				final IMemento logMem = logfiles
						.createChild(LogSessionConfig.TAG_LOGFILE);
				logMem.putString(LogSessionConfig.TAG_LOGFILE, file.getName());
				logMem.putString(LogSessionConfig.TAG_LOGADAPTER_ID, adapterId);
			}
		}
	}

	private void writeToFile(final XMLMemento configMemento) {

		final StringWriter writer = new StringWriter();

		try {
			configMemento.save(writer);

			final IFile file = getConfigFile();
			final ByteArrayInputStream input = new ByteArrayInputStream(writer
					.getBuffer().toString().getBytes());
			file.setContents(input, true, false, null);
		} catch (final CoreException e) {
			throw new RuntimeException(e);
		} catch (final IOException e) {
			// Wraps the exception and propagates it
			throw new RuntimeException(e);
		}
	}
}
