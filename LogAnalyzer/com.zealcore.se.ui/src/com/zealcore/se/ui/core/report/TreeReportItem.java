package com.zealcore.se.ui.core.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeReportItem extends AbstractReportItem {

    public static final class TreeNode {
        private final String value;

        private final List<TreeNode> children = new ArrayList<TreeNode>();

        public TreeNode(final String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public List<TreeNode> getChildren() {
            return Collections.unmodifiableList(this.children);
        }

        public void addNode(final TreeNode node) {
            this.children.add(node);
        }
    }

    private final HashSet<TreeNode> nodes = new HashSet<TreeNode>();

    public void addNode(final TreeNode node) {
        this.nodes.add(node);
    }

    public Set<TreeNode> getNodes() {
        return Collections.unmodifiableSet(this.nodes);
    }
}
