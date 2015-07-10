package com.zealcore.se.core.format;

import java.util.ArrayList;
import java.util.List;

class EventIndex {
    private long numBlocks;

    private long numEvents;

    private long totalEventSize;

    private long firstEventTimestamp;

    private long lastEventTimestamp;

    private List<Block> blocks = new ArrayList<Block>();

    public long getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(final long numBlocks) {
        this.numBlocks = numBlocks;
    }

    public long getNumEvents() {
        return numEvents;
    }

    public void setNumEvents(final long numEvents) {
        this.numEvents = numEvents;
    }

    public long getTotalEventSize() {
        return totalEventSize;
    }

    public void setTotalEventSize(final long totalEventSize) {
        this.totalEventSize = totalEventSize;
    }

    public long getFirstEventTimestamp() {
        return firstEventTimestamp;
    }

    public void setFirstEventTimestamp(final long firstEventTimestamp) {
        this.firstEventTimestamp = firstEventTimestamp;
    }

    public long getLastEventTimestamp() {
        return lastEventTimestamp;
    }

    public void setLastEventTimestamp(final long lastEventTimestamp) {
        this.lastEventTimestamp = lastEventTimestamp;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void addBlock(final Block block) {
        blocks.add(block);
    }

    public void clear() {
        numBlocks = 0;
        numEvents = 0;
        totalEventSize = 0;
        firstEventTimestamp = 0;
        lastEventTimestamp = 0;
        blocks.clear();
    }

    static class Block {
        private long numEvents;

        private long totalEventSize;

        private long firstEventTimestamp;

        private long lastEventTimestamp;

        private long firstEventIndex;

        private long firstEventFptr;

        public long getNumEvents() {
            return numEvents;
        }

        public void setNumEvents(final long numEvents) {
            this.numEvents = numEvents;
        }

        public long getTotalEventSize() {
            return totalEventSize;
        }

        public void setTotalEventSize(final long totalEventSize) {
            this.totalEventSize = totalEventSize;
        }

        public long getFirstEventTimestamp() {
            return firstEventTimestamp;
        }

        public void setFirstEventTimestamp(final long firstEventTimestamp) {
            this.firstEventTimestamp = firstEventTimestamp;
        }

        public long getLastEventTimestamp() {
            return lastEventTimestamp;
        }

        public void setLastEventTimestamp(final long lastEventTimestamp) {
            this.lastEventTimestamp = lastEventTimestamp;
        }

        public long getFirstEventIndex() {
            return firstEventIndex;
        }

        public void setFirstEventIndex(final long firstEventIndex) {
            this.firstEventIndex = firstEventIndex;
        }

        public long getFirstEventFptr() {
            return firstEventFptr;
        }

        public void setFirstEventFptr(final long firstEventFptr) {
            this.firstEventFptr = firstEventFptr;
        }
    }
}
