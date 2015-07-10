package com.zealcore.se.core.ifw;


public abstract class AbstractQuery implements IQuery {

    private Logset logset;

    public AbstractQuery() {
        super();
    }

    public Logset getLogset() {
        if(logset==null) {
            throw new IllegalStateException("logset is null");
        }
        return this.logset;
    }

    public void setLogset(final Logset logset) {
        this.logset = logset;
    }
    
    public void setStart(final boolean start) {
    	//Not required to be implemented for all types of queries
    }

    public void setEnd(final boolean end) {
        //Not required to be implemented for all types of queries
    }
}
