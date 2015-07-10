package com.zealcore.se.core.ifw;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.TestTypeRegistry.TestTypePackage;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ReflectiveType;

public class LogsetTest {

    private static final int NUM_EVENTS = 100000;

    @Before
    public void setUp() {
        // register ITypeRegistry service
        if (SeCorePlugin.getDefault() == null) {
            new SeCorePlugin();
        }
        TestTypeRegistry registry = new TestTypeRegistry();
        TestTypePackage tp = new TestTypePackage();
        tp.addType(ReflectiveType.valueOf(Event.class));
        registry.addPackage(tp);
        SeCorePlugin.getDefault()
                .registerService(ITypeRegistry.class, registry);

    }

    @Test
    public void testPerformance() throws Exception {

        Set<ILogAdapter> data = new LinkedHashSet<ILogAdapter>();
        data.add(new SimpleAdapter());

        long start = System.currentTimeMillis();
        Logset subject = Logset.valueOf(UUID.randomUUID());

        subject.addQuery(new EventQuery(new ITimeProvider() {
            public long getCurrentTime() {
                return NUM_EVENTS;
            }
        }));

        subject.addLogs(data);
        long end = System.currentTimeMillis();
        System.out.println("Took:  " + (end - start)
                + " ms on " + NUM_EVENTS + " events with EventQuery");
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testRemoveLogEmptyArtifacts() throws Exception {
        TestAdapter adapter = new TestAdapter(Collections.EMPTY_LIST);
        Logset subject = Logset.valueOf(UUID.randomUUID());
        subject.setImporterVersionType(IFWFacade.IIMPORTER_VERSION);
        adapter.setContext(ImportContext.valueOf(subject, adapter.getFile()));
        adapter.addArtifact(ModelFactory.newSquence("Foobar"));
        subject.addLog(adapter);
        assertEquals(1,subject.getArtifacts().size());
        subject.removeLog(adapter);
        assertEquals(0,subject.getArtifacts().size());
    
    }

    private static class Event extends AbstractLogEvent {

    }

    static class SimpleAdapter implements ILogAdapter, Iterable<ILogEvent> {

        public SimpleAdapter() {
            super();
        }

        public boolean canRead(final IFile file) {
            return true;
        }

        @SuppressWarnings("unchecked")
        public Iterable<IArtifact> getArtifacts() {
            return Collections.EMPTY_LIST;
        }

        public IFile getFile() {
            return null;
        }

        public String getId() {
            return null;
        }

        public Iterable<ILogEvent> getLogEvents() {
            return this;
        }

        public String getName() {
            return null;
        }

        public ILogAdapter setFile(final IFile file) {
            return null;
        }

        public int size() {
            return -1;
        }

        public void close() {}

        public ILogAdapter setContext(final ImportContext context) {
            return this;
        }

        public Iterator<ILogEvent> iterator() {
            return new SpawnIterator(0, NUM_EVENTS);
        }

    }

    private static class SpawnIterator implements Iterator<ILogEvent> {

        private int numLeft;

        private long ts;

        private int delta = 1;

        public SpawnIterator(final long ts, final int numEvents) {
            numLeft = numEvents;
            this.ts = ts;
        }

        public boolean hasNext() {
            return numLeft > 0;

        }

        public ILogEvent next() {
            Event e = new Event();
            e.setTs(ts);
            ts += delta;
            numLeft--;
            return e;
        }

        public void remove() {
            throw new UnsupportedOperationException("NotImplementedYet");
        }

    }
}
