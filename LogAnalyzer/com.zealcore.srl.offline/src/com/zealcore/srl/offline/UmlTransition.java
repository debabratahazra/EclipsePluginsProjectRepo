package com.zealcore.srl.offline;

public class UmlTransition extends UmlEntity {
    private int source;

    private int target;

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public UmlTransition(final int source, final int target) {
        super();
        this.source = source;
        this.target = target;
    }

    public void setSource(final int source) {
        this.source = source;
    }

    public void setTarget(final int target) {
        this.target = target;
    }

}
