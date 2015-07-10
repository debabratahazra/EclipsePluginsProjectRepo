package com.zealcore.se.iw;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;

/**
 * The GenericImportRegistry is not intended to be sub classed.
 */
 public class GenericImportRegistry {

    private static final String TAG_FILE_ID = "fileId";

    public static final String TAG_CONFIGURATION = "configuration";

    private final Map<String, GenericTextImportData> configurations = new HashMap<String, GenericTextImportData>();

    public GenericImportRegistry() {}

    protected IPath getRegistryPath() {
        return SeCorePlugin.getDefault().getStateLocation().append(
                "genImport.xml");
    }

    protected IMementoService getMementoService() {
        return SeCorePlugin.getDefault().getService(IMementoService.class);
    }

    public void save() {
       save(getRegistryPath());
    }
    
    public void save(IPath filePath) {
        final IMemento2 root = getMementoService().createWriteRoot(
                "generic-import-config", filePath);
        for (final Entry<String, GenericTextImportData> entry : this.configurations
                .entrySet()) {
            final IMemento instance = root
                    .createChild(GenericImportRegistry.TAG_CONFIGURATION);
            entry.getValue().save(instance);
            instance.putString(GenericImportRegistry.TAG_FILE_ID, entry
                    .getKey());
        }

        try {
            root.save();
        } catch (final IOException e) {
            ImportWizardPlugin.logError(e);
        }
    }
    
    public void restore() {
        
        try {
            if (getRegistryPath().toFile().exists()) {
                final IMemento root = getMementoService().createReadRoot(
                        getRegistryPath());

                for (final IMemento instance : root
                        .getChildren(GenericImportRegistry.TAG_CONFIGURATION)) {
                    final String fileId = instance
                            .getString(GenericImportRegistry.TAG_FILE_ID);
                    this.configurations.put(fileId, GenericTextImportData
                            .valueOf(instance));
                }
            }
        } catch (final IOException e) {
            ImportWizardPlugin.logError(e);
        } catch (final WorkbenchException e) {
            ImportWizardPlugin.logError(e);
        }

    }

    public boolean canRead(final File file) {
        return this.configurations.containsKey(getFileId(file));
    }

    public void addImportData(final GenericTextImportData data) {
        this.configurations.put(data.getFileId(), data);
        save();
    }

    public GenericTextImportData getImportData(final File file) {
        final GenericTextImportData data = this.configurations
                .get(getFileId(file));
        if (data == null) {
            return null;
        }
        return data.copy();
    }

    private String getFileId(final File file) {
        final String fileName = file.getName();
        String fileId;
        final int indexOfDot = fileName.lastIndexOf(".");
        if (indexOfDot < 0) {
            fileId = fileName;
        } else {
            fileId = fileName.substring(indexOfDot);
        }
        return fileId;
    }
    
    public void updateImportData(GenericTextImportData data){
        this.configurations.put(data.getFileId(), data);
    }
}
