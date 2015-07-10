package com.zealcore.srl.offline;

public class UmlState extends UmlEntity {
    private final String name;

    private final UmlEntity parent;

    private final int depth;

    public int getDepth() {
        return depth;
    }

    public UmlState(final String name, final UmlEntity parent, final int depth) {
        super();
        this.name = name;
        this.parent = parent;
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public UmlEntity getParent() {
        return parent;
    }
}
