/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.ITimestampUpdateListener;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ImportExceptionList;
import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.ILogAdapter;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.UseAsLogFile;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.util.ArtifactColorMap;

/**
 * The Class DirectoryLogSession.
 */
public class DirectoryLogSession extends AbstractLogSet {

    private final class LostFileListener implements IResourceChangeListener,IResourceDeltaVisitor {

    	private final Map<Logset, List<IFile>> removeFiles = new HashMap<Logset, List<IFile>>();

        private final List<IFile> newFiles = new ArrayList<IFile>();

    	private final List<IFile> changedFiles = new ArrayList<IFile>();

    	private static final String IMPORT_FAILED = "Import failed";

    	private boolean affectsMe;

    	private boolean rename;

    	public void resourceChanged(final IResourceChangeEvent event) {

    		try {
    			if (event.getDelta() == null) {
    				return;
    			}
    			start(event.getDelta());
    			event.getDelta().accept(this);
    			finish();
    		} catch (final CoreException e) {
    			SeUiPlugin.logError(e);
    		}

    	}

    	private void start(IResourceDelta delta) {
    		if ((folder == null) || (!folder.exists())) {
    			folder.getWorkspace().removeResourceChangeListener(lostFileListener);
    		}
    		changedFiles.clear();
    		rename = isRename(delta);
    	}

    	private void finish(){
            if (affectsMe) {
                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        importNewFiles();
                    }
                });
            }
            importChangedLogsets();
    		changedFiles.clear();
            affectsMe = false;
        }

    	public boolean visit(IResourceDelta delta) throws CoreException {

    		switch (delta.getKind()) {
    			case IResourceDelta.ADDED:
    			case IResourceDelta.REPLACED:
    				if (delta.getResource() instanceof IFolder) {
    					folderAdded(delta.getResource());
    				} else if (delta.getResource() instanceof IFile) {
    					fileAdded(delta);
    				}
    			break;
    			case IResourceDelta.REMOVED:
    				if (delta.getResource() instanceof IFolder) {
    					folderRemoved(delta.getResource());
    				} else if (delta.getResource() instanceof IFile) {
    					fileRemoved(delta.getResource());
    				}
    			break;
    			case IResourceDelta.CHANGED:
    				if (delta.getResource() instanceof IFolder) {
    					folderChanged(delta.getResource());
    				} else if (delta.getResource() instanceof IFile) {
    					fileChanged(delta.getResource());
    				}
    			break;
    		}
    		return true;
    	}
		
		
        private void folderRemoved(final IResource folderReource) {
            //close all the affected views/editor
    		closeAffectedViews(folderReource);
    		
    		//remove change listeners
            if (folder.equals(folderReource)) {
            	folderReource.getWorkspace()
                        .removeResourceChangeListener(this);
                removeAllListeners();
            }
        }

    	private void fileAdded(final IResourceDelta delta) {
    		boolean canAdd = false;
    		if (delta.getMovedFromPath() != null ) {
                final Logset logset = Logset.valueOf(getId());
    				for (final ILogAdapter adapter : logset.getLogs()) {
    					if (adapter.getFile().getFullPath().toPortableString()
    							.equals(delta.getMovedFromPath().toPortableString())) {
    						canAdd = true;
    						break;
    					} else {
    						canAdd = false;
    					}
    				}
    			if(!this.rename){
    				canAdd = true;
    			}
    		} else {
    			canAdd = true;
    		}
    		if (canAdd) {
    			affectsMe = true;
    			newFiles.add((IFile) delta.getResource());
    		}
    	}

    	private void fileRemoved(final IResource fileResource) {

    		IFile file = (IFile) fileResource;
    		addToRemovedFileList(file);
    	}

    	private void fileChanged(final IResource fileResource) {
    		changedFiles.add((IFile)fileResource);
    	}

    	private void folderAdded(final IResource folderReource) {}

    	
    	private void folderChanged(final IResource folderReource) {
    		if(rename){
    			closeAffectedViews(folderReource);		
    		}
    	}

    	private void addToRemovedFileList(IFile file) {

    		Logset logset = null;
    		IContainer container = file.getParent();
    		if (container instanceof IFolder) {
    			IFolder folder = (IFolder) container;
    			try {
    				if (folder.exists()) {
    					IResource[] resources = folder.members();
    					if(resources.length>0){
    						logset = CaseFileManager.getLogset((IFile) resources[0]);
    					}
    				}
    			} catch (CoreException e) {};
    		}
    		if (removeFiles.get(logset) == null) {
    			removeFiles.put(logset, new ArrayList<IFile>());
    		}
    		removeFiles.get(logset).add(file);
    		if (uses(file)) {
                affectsMe = true;
            }
        }

        private void importNewFiles() {
            List<IFile> importFiles = new ArrayList<IFile>();
            synchronized (importFiles) {
                for (Iterator<IFile> iterator = newFiles.iterator(); iterator.hasNext();) {
                        
                    IFile file =(IFile) iterator.next();
                    if (hasOnlyOneLogAdapter(file) && !isBinFile(file)) {
                        importFiles.add(file);
                    }
                }
            }

            newFiles.clear();
            if (importFiles.size() > 0) {
                new UiAutomaticImport("New logfiles detected", importFiles, rename)
                        .schedule();
            } 
            deConfigureRemovedFiles();
            removeFiles.clear();
            rename = false;
    	}
    	
    	private void deConfigureRemovedFiles(){
    		if (removeFiles.size() > 0) {
    			for (Iterator<Logset> iterator = removeFiles.keySet()
    					.iterator(); iterator.hasNext();) {
    				Logset logset = iterator.next();
    				try {
    					if(logset != null) {
    						long lock = logset.getLock();
    						if (lock != -1)
    						{
	    						logset.removeLogs(removeFiles.get(logset));
	    						logset.closeIMBinaryStream();
	    						logset.releaseLock(lock);
    						}
    					}
    				} catch (ImportOperationCancelledException e) {
    					e.printStackTrace();
    				}
    			}
    			UseAsLogFile useAsLogFile = new UseAsLogFile();
    			useAsLogFile.handleLostFiles(getLogset());
    		}
    	}

    	private void importChangedLogsets() {
    		final Set<Logset> sessions = new HashSet<Logset>();

    		for (final IFile file : changedFiles) {
    			final Logset logset = CaseFileManager.getLogset(file);
    			if (logset != null && logset.contains(file)) {
    				sessions.add(logset);
    			}
    		}

    		for (final Logset logset : sessions) {
    			try {
    				logset.refresh();
    			} catch (final RuntimeException e) {
    				if (e instanceof ImportException) {
    					reportException((ImportException) e);
    					logset.refresh();
    				} else if (e instanceof ImportExceptionList) {
    					for (ImportException ie : ((ImportExceptionList) e)
    							.getExceptions()) {
    						reportException(ie);
    					}
    					logset.refresh();
    				} else {
    					SeUiPlugin.reportUnhandledRuntimeException(this.getClass(),
    							e, true);
    				}
    			}
    		}
    	}

    	private void closeAffectedViews(final IResource folder){

    		new AbstractSafeUIJob("Closing Affected Windows") {
    			@Override
    			public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
    				for (final IWorkbenchWindow window : PlatformUI.getWorkbench()
    						.getWorkbenchWindows()) {
    					for (final IWorkbenchPage page : window.getPages()) {
    						for (final IEditorReference reference : page
    								.getEditorReferences()) {
    							if (reference.getId()
    									.equals(LogsetEditor.EDITOR_ID)) {
    								IEditorInput input;
    								try {
    									input = reference.getEditorInput();
    									if (input instanceof ILogViewInput) {
    										final ILogViewInput oldInput = (ILogViewInput) input;
    										if (oldInput.getLog().equals(
    												folder)) {
    											page
    											.closeEditors(
    													new IEditorReference[] { reference },
    													false);
    										}
    									}
    								} catch (final PartInitException e) {
    									SeUiPlugin.logError(e);
    								}
    							}
    						}
    					}
    				}
    				return Status.OK_STATUS;
    			}
    		}.schedule();

    	}

    	private boolean isRename(final IResourceDelta delta){

    		IResourceDelta[] deltas = delta.getAffectedChildren();
    		while ((deltas.length != 0)
    				&& (deltas.length != 2 )) {
    			deltas = deltas[0].getAffectedChildren(); 
    		}
    		boolean rename = false;
    		// Rename check
    		if ((deltas.length == 2)
    				&& ((deltas[0].getMovedFromPath() != null)
    						|| (deltas[0].getMovedToPath() != null))
    						&& ((deltas[1].getMovedFromPath() != null)
    								|| (deltas[1].getMovedToPath() != null))) {
    			String path1 = deltas[0].getMovedFromPath() != null ? deltas[0].getMovedFromPath().toPortableString() : deltas[0].getMovedToPath().toPortableString();
    			String path2 = deltas[1].getMovedToPath() != null ? deltas[1].getMovedToPath().toPortableString() : deltas[1].getMovedFromPath().toPortableString();
    			String path11 = deltas[0].getResource().getFullPath().toPortableString();
    			String path22 = deltas[1].getResource().getFullPath().toPortableString();
    			if (path1.equals(path22) && path2.equals(path11)) {
    				rename = true;
    			}
    		}
    		return rename;
    	}

        /**
         * Determines if a file has distinct importer (one and only one known
         * importer).
         * 
         * @param file
         *            the file
         * 
         * @return true, if file has distinct importer
         */
        private boolean hasOnlyOneLogAdapter(final IFile file) {
            if (!file.exists()) {
                return false;
            }
            if ((file.getName()
                    .equalsIgnoreCase(DirectoryLogSession.CONFIG_FILE))
                    || (file.getName()
                            .equalsIgnoreCase(DirectoryLogSession.BIN_FILE))) {
                return false;
            }

            Logset logset = CaseFileManager.getLogset(file);
            if (logset != null) {
                Collection<ILogAdapter> logAdapters = IFWFacade.findLogAdapter(
                        file, IFWFacade.IEXTENDEDIMPORTER_VERSION);
                if (logAdapters.size() == 0) {
                    logAdapters = IFWFacade.findLogAdapter(file,
                            IFWFacade.IIMPORTER_VERSION);
                }
                return logAdapters.size() == 1;
            } else {
                return false;
            }

    	}
    	    	
    	private boolean isBinFile(final IFile file) {
    		return file.getFileExtension().equals("bin");
    	}
    	
    	private void reportException(final ImportException e) {
    		SeUiPlugin.reportError(IMPORT_FAILED, e);
    		if (e.equals(e.getCause())) {
    			SeUiPlugin.logError(e.getCause());
    		} else {
    			SeUiPlugin.logError(e);
    		}
    	}
    }

    static final String COLOR_MAP_NODE = "color-map";

    /** The Constant TIME_CLUSTERS_NODE. */
    static final String TIME_CLUSTERS_NODE = "view-sets";

    /** The Constant CONFIG_FILE. */
    public static final String CONFIG_FILE = ".logset";

    /** Index based intermediate file name */
    public static final String BIN_FILE = "logset.bin";

    /** The Constant IS_LOGSESSION. */
    static final QualifiedName IS_LOGSESSION = new QualifiedName(
            SeUiPlugin.PLUGIN_ID, ".logsession");

    /**
     * The folder. The actual DLS instance is stored as a session property in
     * the folder, meaning that there may be no constructors and the valueOf
     * methods must check the session property first.
     * 
     */

    private final IFolder folder;

    private final LostFileListener lostFileListener;

    private ArtifactColorMap map;

    private LogSessionConfig config;

    private final ITimeCluster defaultViewSet = TimeCluster2.valueOf(this);

    private IChangeListener changeListener;

    private ITimestampUpdateListener timesatampUpdateListener;

    private int importerVersionType = LogSessionConfig.IEXTENDEDIMPORTER_VERSION;

    /**
     * The Constructor.
     * 
     * @param folder
     *            the folder
     */
    DirectoryLogSession(final IFolder folder, final Display display) {
        this.folder = folder;
        try {
            folder.setSessionProperty(DirectoryLogSession.IS_LOGSESSION, this);
            config = new LogSessionConfig(this,
                    folder.getFile(DirectoryLogSession.CONFIG_FILE));
            config.create();
            config.read();

            lostFileListener = new LostFileListener();

            folder.getWorkspace().addResourceChangeListener(lostFileListener);

            changeListener = new IChangeListener() {
                public void update(final boolean changed) {
                    notifyListeners();
                    if (changed && folder.exists()) {
                        DirectoryLogSession.this.writeConfig();
                    }
                }
            };
            getLogset().addChangeListener(changeListener);
            timesatampUpdateListener = new ITimestampUpdateListener() {

                public void timestampUpdated(final long timestamp) {
                    defaultViewSet.setCurrentTime(timestamp);
                }
            };
            getLogset().addTimestampUpdateListener(timesatampUpdateListener);

        } catch (final CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the resource is a log session.
     * 
     * @param resource
     *            the resource to test
     * 
     * @return true, if resource is log session
     */
    public static boolean isLogSession(final IResource resource) {
        if (!resource.exists()) {
            return false;
        }

        if (resource.getParent() == null
                || !DirectoryCaseFile.isCaseFile(resource.getParent())) {
            return false;
        }
        if (resource instanceof IFolder) {
            final IFolder folder = (IFolder) resource;
            final IFile identifier = folder
                    .getFile(DirectoryLogSession.CONFIG_FILE);
            return identifier != null && identifier.exists();
        }
        return false;
    }

    /**
     * Creates a new ILogSessionWrapper representing the specified IResource
     * value.
     * 
     * @param resource
     *            the resource
     * 
     * @return the I log session wrapper
     */
    public static ILogSessionWrapper valueOf(final IResource resource) {
        if (!DirectoryLogSession.isLogSession(resource)) {
            throw new IllegalArgumentException("This is not a valid LogSet");
        }

        final IFolder sessionFolder = (IFolder) resource;
        try {
            if (!sessionFolder.getWorkspace().isTreeLocked()) {
                sessionFolder.refreshLocal(IResource.DEPTH_ZERO, null);
            }
            final Object sessionProperty = sessionFolder
                    .getSessionProperty(DirectoryLogSession.IS_LOGSESSION);
            if (sessionProperty != null) {
                final DirectoryLogSession dls = (DirectoryLogSession) sessionProperty;
                if (sessionFolder.equals(dls.folder)) {
                    return dls;
                }
                sessionFolder.setSessionProperty(
                        LogSessionConfig.QN_IS_INITIALIZED, null);
            }
        } catch (final CoreException e) {
            throw new RuntimeException(e);
        }
        return new DirectoryLogSession(sessionFolder, Display.getCurrent());
    }

    /**
     * {@inheritDoc}
     */
    public ICaseFile2 getCaseFile() {
        return DirectoryCaseFile.valueOf(folder.getParent());
    }

    /**
     * {@inheritDoc}
     */
    public ArtifactColorMap getColorMap() {

        if (map == null) {
            map = new ArtifactColorMap();
            map.init(config.getConfiguration().getChild(
                    DirectoryLogSession.COLOR_MAP_NODE));
            map.addChangeListener(new IChangeListener() {
                public void update(final boolean changed) {
                    DirectoryLogSession.this.writeConfig();
                }
            });
        }
        return map;
    }

    public TimeCluster2[] getTimeClusters() {
        return new TimeCluster2[] { (TimeCluster2) getDefaultViewSet() };
    }

    /**
     * {@inheritDoc}
     */
    public Object[] getChildren(final Object o) {
        if (!folder.exists()) {
            return new Object[0];
        }
        try {
            final IResource[] members = folder.members();

            final List<Object> items = new ArrayList<Object>();
            items.addAll(Arrays.asList(members));
            return items.toArray();
        } catch (final CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel(final Object o) {
        return folder.getName();
    }

    /**
     * Creates a new ILogSessionWrapper on the specified IFolder parent and
     * representing LogSession value.
     * 
     * @param file
     *            the file
     * @param container
     *            the parent
     * 
     * @return the I log session wrapper
     * 
     * @throws CoreException
     *             the core exception
     */
    static ILogSessionWrapper create(final IContainer container,
            final String file) throws CoreException {
        final IFolder instance = container.getFolder(new Path(file));
        instance.create(false, true, null);
        return new DirectoryLogSession(instance, Display.getCurrent());

    }

    /**
     * Writes the config to the .config file, creating one if it doesn't already
     * exist.
     */
    public void writeConfig() {
        if (folder.exists()) {
            config.write();
        }

    }

    /**
     * Gets the represented folder.
     * 
     * @return the folder
     */
    public IFolder getFolder() {
        return folder;
    }

    @Override
    public String toString() {
        return getLabel(this);
    }

    public ITimeCluster getDefaultViewSet() {
        return defaultViewSet;
    }

    public void setColorMap(final ArtifactColorMap map) {
        // FIXME This check is actually valid but disabled 4 release
        // if (this.map != null) {
        // throw new AssertionError("Cannot re-target the color map");
        // }
        this.map = map;
        this.map.addChangeListener(new IChangeListener() {
            public void update(final boolean changed) {
                DirectoryLogSession.this.writeConfig();
            }
        });
    }

    @Override
    public int hashCode() {
        return folder.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof IResource) {
            final IResource resource = (IResource) o;
            return folder.equals(resource);

        }
        return super.equals(o);
    }

    boolean uses(final IFile file) {
        if ((file.getName().equals(DirectoryLogSession.CONFIG_FILE))
                || (file.getName()
                        .equalsIgnoreCase(DirectoryLogSession.BIN_FILE))) {
            return false;
        }

        if (!file.getParent().equals(folder)) {
            return false;
        }

        for (final ILogAdapter adapter : getLogset().getLogs()) {
            IFile file2 = adapter.getFile();
            if (file2 != null) {
                if (file2.equals(file)) {
                    return true;
                }
            } else {
                throw new IllegalStateException(
                        "Log adapter resource is null! Adapter is " + adapter);
            }
        }
        return false;
    }

    public UUID getId() {
        return config.getId();
    }

    Logset getLogset() {
        return Logset.valueOf(getId());
    }

    public void setCurrentTime(final long time) {
        getLogset().setCurrentTime(time);
    }

    public int getImporterVersionType() {
        return importerVersionType;
    }

    public void setImporterVersionType(final int versionType) {

        // Update the state variable in DirectoryLogSession(UI plug-in) and
        // also in IFWFacde (Core plug-in)

        importerVersionType = versionType;
        if (versionType <= 0) {
            importerVersionType = LogSessionConfig.IIMPORTER_VERSION;
        } else if (versionType > 2) {
            importerVersionType = LogSessionConfig.IEXTENDEDIMPORTER_VERSION;
        }
        // IFWFacade.setLAImporterVersionType(importerVersionType);
        getLogset().setImporterVersionType(importerVersionType);
    }

    private static class UiAutomaticImport extends AbstractSafeUIJob {
        private final Collection<IFile> files;

        private final boolean rename;

        private static final String IMPORT_FAILED = "Import failed";

        public UiAutomaticImport(final String name,
                final Collection<IFile> files, final boolean rename) {
            super(name);

            this.files = new ArrayList<IFile>();
            this.files.addAll(files);
            this.rename = rename;
        }

        @Override
        public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
            if (!monitor.isCanceled()) {
                try {
                    UseAsLogFile useAsLogFile = new UseAsLogFile();
                    useAsLogFile.handleImport(files, rename);
                } catch (RuntimeException e) {
                    return new Status(Status.ERROR, "com.zealcore.se.ui",
                            IMPORT_FAILED);
                }
            }
            return Status.OK_STATUS;
        }
    }

    public void removeAllListeners() {
        if (changeListener != null) {
            getLogset().removeChangeListener(changeListener);
        }
        if (timesatampUpdateListener != null) {
            getLogset().removeTimestampUpdateListener(timesatampUpdateListener);
        }
    }
}
