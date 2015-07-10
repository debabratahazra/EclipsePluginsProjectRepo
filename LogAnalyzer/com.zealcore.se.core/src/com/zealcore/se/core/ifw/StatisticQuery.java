package com.zealcore.se.core.ifw;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.ITimed;

public final class StatisticQuery extends AbstractQuery {

    private static final double PERCENT = 100.0;

    private static final int NR_OF_DECIMALS = 2;

    private long minTs = -1;

    private long maxTs = -1;

    private long countEvent = -1;

    private boolean processing;

    private StatisticQuery() {}

    private static Map<Logset, StatisticQuery> statistics = new HashMap<Logset, StatisticQuery>();

    private Reason reason;

    public static StatisticQuery valueOf(final Logset logset) {
        StatisticQuery query = statistics.get(logset);
        if (query == null) {
            query = new StatisticQuery();
            statistics.put(logset, query);
        }
        return query;

    }

    public boolean visit(final IObject item) {
        if (reason == Reason.QUERY_ADDED || reason == Reason.REFRESH) {
            return false;
        }
        minMaxTs(item);

        if (item instanceof ILogEvent) {
            countEvent++;
        }
        if (item instanceof ITaskDuration) {
            utilization((ITaskDuration) item);
        }
        return true;
    }

    void minMaxTs(final IObject item) {
        // FIXME Needed to TaskInstance
        // for(SEProperty property : item.getZPropertyAnnotations()) {
        // if(property.isTs()) {
        // final long ts = ((Number)property.getData()).longValue();
        // maxTs = Math.max(maxTs, ts);
        //                
        // }
        // }
        if (item instanceof ITimed) {
            ITimed timed = (ITimed) item;
            minTs = Math.min(minTs, timed.getTimeReference());
            maxTs = Math.max(maxTs, timed.getTimeReference());

        }
    }

    private long firstExe;

    private long lastExe;

    private Map<ITask, Long> sumExec = new HashMap<ITask, Long>();

    private void utilization(final ITaskDuration exec) {
        firstExe = Math.min(firstExe, exec.getStartTime());
        lastExe = Math.max(lastExe, exec.getStartTime()
                + exec.getDurationTime());

        ITask task = exec.getOwner();

        Long exe = sumExec.get(task);
        if (exe == null) {
            exe = 0L;
        }
        exe += exec.getDurationTime();
        sumExec.put(task, exe);
    }

    public boolean visitBegin(final Reason reason) {
        this.reason = reason;
        if (reason == Reason.REFRESH || reason == Reason.QUERY_ADDED) {
            return false;
        }

        minTs = Long.MAX_VALUE;
        maxTs = Long.MIN_VALUE;
        processing = true;
        firstExe = Long.MAX_VALUE;
        lastExe = Long.MIN_VALUE;
        sumExec.clear();
        countEvent = 0;
        return true;
    }

    public void visitEnd(final boolean atEnd) {
        processing = false;
        double period = lastExe - firstExe;
        for (Entry<ITask, Long> entry : sumExec.entrySet()) {
            Long sum = entry.getValue();
            double util = ((double) sum / period);
            ITask process = entry.getKey();
            process.setUtilization(round(util * PERCENT, NR_OF_DECIMALS));
        }
    }

    public static Double round(final double value, final int decimalPlace) {
        Double powerOfTen = 1.0;
        int dp = decimalPlace;
        while (dp-- > 0) {
            powerOfTen *= 10.0;
        }
        return Math.round(value * powerOfTen) / powerOfTen;
    }

    public long getMaxTs() {
        checkQuery();
        return maxTs;
    }

    private void checkQuery() {
        if (processing) {
            throw new IllegalStateException(
                    "No Calls to the StatisticsQuery allowed during processing");
        }
    }

    public long getMinTs() {
        checkQuery();
        return minTs;
    }

    public long getNumberOfEvents() {
        checkQuery();
        return countEvent;
    }

    public void initialize(final IDataSource data) {}

    public void setMinTs(final long minTs) {
        this.minTs = minTs;
    }

    public void setMaxTs(final long maxTs) {
        this.maxTs = maxTs;
    }

    public void setCountEvent(final long countEvent) {
        this.countEvent = countEvent;
    }
}
