package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.util.Statistics;

public class SearchQuery extends AbstractQuery {

    // Initial Search MINIMUM LIMIT
    public static final int MIN_SEARCH_LIMIT = 1000;

    public static final int SEARCH_INFO_START_INDEX = 0;

    private final IFilter<IObject> filter;

    private LinkedList<IObject> results;

    private final SearchInfo info;

    private int hitCount;

    private int singleHitCount;

    private int skipCount;

    private int skipEvents;

    private boolean firstSearch;

    private int totalEvents;

    private int totalHitCount;

    private boolean skipEventsRequired;

    private int logEventIndex;

    // Search limit set by user in preferences. Default is MIN_SEARCH_LIMIT.
    private int currentSearchLimit;

    private int logEventCount;
    
    private ILogEvent lastLogEvent;
    
    public SearchQuery(final IFilter<IObject> filter, final int setLimit,
            final SearchInfo searchInfo) {
        this.filter = filter;
        if (searchInfo == null) {
            currentSearchLimit = (setLimit < MIN_SEARCH_LIMIT) ? MIN_SEARCH_LIMIT
                    : setLimit;
            info = new SearchInfo();
            info.setMaxNumberOfEvents(currentSearchLimit);
            firstSearch = true;
            info.setTotalStatistics(new Statistics());
        } else {
            info = searchInfo;
            currentSearchLimit = info.getMaxNumberOfEvents();
        }
    }

    public SearchInfo getSearchInfo() {
        return info;
    }

    public List<IObject> getResults() {
        return results;
    }

    /**
     * IQuery implementation
     */
    public void initialize(final IDataSource data) {}

    public boolean visitBegin(final Reason reason) {
        if (results != null) {
            throw new IllegalStateException("SearchQuery used twice");
        }
        results = new LinkedList<IObject>();
        hitCount = 0;
        singleHitCount = 0;
        skipCount = 0;
        totalEvents = 0;
        totalHitCount = 0;
        logEventIndex = 0;
        logEventCount = 0;
        lastLogEvent = null;
        info.setCurrentStatistics(new Statistics());
        if (skipEventsRequired) {
            skipEvents = 0;
        } else {
            skipEvents = info.getCurrentHit().getSkipCount();
        }
        return true;
    }

    public boolean visit(final IObject item) {
        totalEvents++;
        if (skipEvents > 0) {
            skipEvents--;
            return true;
        }
        
        boolean isILogEvent = item instanceof ILogEvent;
        ILogEvent logEvent = null;
        
        if (isILogEvent) {
            logEventIndex++;
            logEvent = (ILogEvent)item;
        }
        if (filter.filter(item)) {
            totalHitCount++;
            
            if(firstSearch && isILogEvent && lastLogEvent != null) {
            	info.getTotalStatistics().update(logEvent.getTs() - lastLogEvent.getTs());
            }
            
            if (singleHitCount < currentSearchLimit) {
                singleHitCount++;
                results.addFirst(item);                
                if (isILogEvent && lastLogEvent != null) {
                	info.getCurrentStatistics().update(logEvent.getTs() - lastLogEvent.getTs());
                }
            }
            if (isILogEvent) {
            	lastLogEvent = logEvent;
            }
            if ((singleHitCount == currentSearchLimit) && (!firstSearch)) {
                return false;
            } else {
                hitCount++;
                if (hitCount == currentSearchLimit) {
                    info.addHit(new Hit(totalHitCount, hitCount, skipCount,
                            logEventCount));
                    info.setTotalEvents(totalHitCount);
                    skipCount = totalEvents;
                    logEventCount = logEventIndex;
                    hitCount = 0;
                }
            }
        }
        return true;
    }

    public void visitEnd(final boolean atEnd) {

        if (!results.isEmpty()) {
            if (firstSearch && (totalHitCount != logEventCount)) {
                info.addHit(new Hit(totalHitCount, hitCount, skipCount,
                        logEventCount));
                info.setTotalEvents(totalHitCount);
            }
            firstSearch = false;
        }
    }

    public static class Hit {

        private final int totalEventCount;

        private final int hitsCount;

        private final int skipCount;

        private final int iLogEventSkipCount;

        Hit(final int totalEventCount, final int hitsCount,
                final int skipCount, final int iLogEventSkipCount) {
            this.totalEventCount = totalEventCount;
            this.hitsCount = hitsCount;
            this.skipCount = skipCount;
            this.iLogEventSkipCount = iLogEventSkipCount;
        }

        public int getTotalEventCount() {
            return totalEventCount;
        }

        public int getHitsCount() {
            return hitsCount;
        }

        public int getSkipCount() {
            return skipCount;
        }

        public int getILogEventSkipCount() {
            return iLogEventSkipCount;
        }
    }

    public static class SearchInfo {

        private static final long serialVersionUID = 1L;

        private final List<Hit> hitsInfo;

        private int currentHitIndex;

        private int totalEvents;

        private int maxNumberOfEvents;
        
        private Statistics currentStatistics;
        
        private Statistics totalStatistics;
        
        public SearchInfo() {
            hitsInfo = new ArrayList<Hit>();
            currentHitIndex = SEARCH_INFO_START_INDEX;
        }

        public void addHit(final Hit hit) {
            hitsInfo.add(hit);
        }

        public int getHitsSize() {
            return hitsInfo.size();
        }

        public Hit getHitInfoAtIndex(final int index) {
            if (index > hitsInfo.size()) {
                return null;
            }
            return hitsInfo.get(index);
        }

        public Hit getCurrentHit() {
            if ((hitsInfo.size() == 0)
                    || ((currentHitIndex + 1) > hitsInfo.size())) {
                return new Hit(0, 0, 0, 0);
            }
            return hitsInfo.get(currentHitIndex);
        }

        public int getCurrentHitIndex() {
            return currentHitIndex;
        }

        public void setCurrentHitIndex(int currentHitIndex) {
            this.currentHitIndex = currentHitIndex;
        }

        public int getTotalEvents() {
            return totalEvents;
        }

        public void setTotalEvents(final int totalEvents) {
            this.totalEvents = totalEvents;
        }

        public int getMaxNumberOfEvents() {
            return maxNumberOfEvents;
        }

        public void setMaxNumberOfEvents(final int maxNumberOfEvents) {
            this.maxNumberOfEvents = maxNumberOfEvents;
        }

		public Statistics getCurrentStatistics() {
			return currentStatistics;
		}

		public void setCurrentStatistics(Statistics currentStatistics) {
			this.currentStatistics = currentStatistics;
		}

		public Statistics getTotalStatistics() {
			return totalStatistics;
		}

		public void setTotalStatistics(Statistics totalStatistics) {
			this.totalStatistics = totalStatistics;
		}		
    }

    public void setSkipEventsRequired(boolean skipEventsRequired) {
        this.skipEventsRequired = skipEventsRequired;
    }
}
