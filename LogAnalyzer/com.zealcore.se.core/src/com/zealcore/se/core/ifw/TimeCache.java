package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.zealcore.se.core.model.ITimed;

final class TimeCache<T extends ITimed> implements Iterable<T>, ITimeProvider {

    private final ITimeProvider time;

    private int size = 5000;

    private int halfsize = size / 2;

    private boolean atStart;

    private boolean atEnd;

    private final List<T> cache = new ArrayList<T>(size);

    private Object[] arr = new Object[size];

    private int after;

    private int before;

    private int pointer;

    private T last;

    TimeCache(final ITimeProvider time) {
        this(time, 5000);

    }

    TimeCache(final ITimeProvider time, final int size) {
        this.time = time;
        setSize(size);
    }

    boolean putInCache(final T element) {
        put(element);
        return (before + after) < arr.length;
    }

    @SuppressWarnings("unchecked")
    T put(final T element) {
        final int where = pointer % arr.length;
        T old = (T) arr[where];
        arr[where] = element;
        ++pointer;

        if (element.getTimeReference() < time.getCurrentTime()) {
            ++before;
            if (pointer > halfsize) {
                atStart = false;
                before = halfsize;
            }
        } else {
            ++after;
        }
        last = element;
        return old;
    }

    boolean isFull() {
        return before + after >= arr.length;
    }

    public ArrayList<T> get(final int backward, final int forward) {

        ensureCache();
        if (cache.size() == 0) {
            return null;
        }
        final long ts = time.getCurrentTime();
        int from = 0;
        int to = 0;
        ArrayList<T> elements = new ArrayList<T>(cache);
        int index = Collections.binarySearch(elements, new Seed(ts), null);

        int actualBackward = backward;
        int actualForward = forward;

        if (backward + 1 > halfsize) {
            actualBackward = halfsize;
        }
        if (forward + 1 > halfsize) {
            actualForward = halfsize;
        }

        /*
         * If failed to found object with exactly the same time
         */
        if (index < 0) {
            index = -index - 1;
        }
        from = index - actualBackward;
        to = index + actualForward;
        if (from < 0 && atStart) {
            from = 0;
        }
        if (to > cache.size() && atEnd) {
            to = cache.size();
        }

        // cachemisses
        if (from < 0) {
            return null;
        } else if (to > cache.size()) {
            return null;
        }
        return new ArrayList<T>(elements.subList(from, to));
    }

    @SuppressWarnings("unchecked")
    private void ensureCache() {
        if (cache.size() == 0 && pointer > 0) {
            for (Object e : arr) {
                if (e != null) {
                    cache.add((T) e);
                }
            }
            Collections.sort(cache);
        }
    }


    public void setAtStart(final boolean atStart) {
        this.atStart = atStart;
    }

    public void setAtEnd(final boolean atEnd) {
        this.atEnd = atEnd;
    }

    public ITimeProvider getTime() {
        return time;
    }

    void clear() {
        pointer = 0;
        before = 0;
        after = 0;
        atStart = true;
        atEnd = false;
        last = null;
        arr = new Object[size];

        cache.clear();
    }

    void setSize(final int cachesize) {
        size = cachesize;
        halfsize = size / 2;
    }

    int getSize() {
        return size;
    }

    static class Seed implements ITimed {

        private final long ts;

        public Seed(final long ts) {
            this.ts = ts;
        }

        public int compareTo(final Object arg0) {
            if (arg0 instanceof ITimed) {
                final ITimed other = (ITimed) arg0;
                return getTimeReference().compareTo(other.getTimeReference());
            }
            return -1;
        }

        public Long getTimeReference() {
            return ts;
        }

    }

    public boolean isEmpty() {
        return before + after == 0;
    }

    @SuppressWarnings("unchecked")
    public T getFirst() {
        if (pointer > arr.length) {
            return (T) arr[(pointer + 1) % arr.length];
        }
        return (T) arr[0];
    }

    public T getLast() {
        return last;
    }

    public Iterator<T> iterator() {
        ensureCache();
        return Collections.unmodifiableCollection(cache).iterator();
    }

    public long getCurrentTime() {
        return getTime().getCurrentTime();
    }
}
