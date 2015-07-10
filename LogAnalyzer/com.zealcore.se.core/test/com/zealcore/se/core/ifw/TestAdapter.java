/**
 * 
 */
package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.resources.IFile;

import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;

public class TestAdapter implements ILogAdapter {

    private final Collection<ILogEvent> events;
    private Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
    private ImportContext context;

    public TestAdapter(final Collection<ILogEvent> events) {
        super();
        this.events = events;
    }

    public TestAdapter(final ILogEvent... events) {
        this.events = Arrays.asList(events);
    }

    public boolean canRead(final IFile file) {
        return true;
    }

    public Iterable<GenericArtifactInfo> getLAArtifacts() {
        //return artifacts;
    	return null;
    }

    public IFile getFile() {
    	IFile ifile = new ImplementIFile();
        return ifile;
    }

    public String getId() {
        return null;
    }

    public Iterable<GenericEventInfo> getLAEvents() {
        //return events;
    	return null;
    }

    public String getName() {
        return null;
    }

    public ILogAdapter setFile(final IFile file) {
        return null;
    }

    public int size() {
        return events.size();
    }

    public void close() {}

    public ILogAdapter setContext(final ImportContext context) {
        this.context = context;
        return this;
    }

    public void addArtifact(final IArtifact newSquence) {
        artifacts.add(context.resolveArtifact(newSquence));
        

    }

    public Iterable<IArtifact> getArtifacts() {
        // TODO Implementation required with latest changes supporting both importer interfaces
        return artifacts;
    }

    public Iterable<ILogEvent> getLogEvents() {
        // TODO Implementation required with latest changes supporting both importer interfaces
        return events;
    }
}
