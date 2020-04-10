package org.dimensinfin.core.interfaces;

/**
 * This interface defines the nodes that can contain or reference other nodes. Node structure is not expected
 * to be hierarchical, this is a classic parent-children structure, but allows to use a graph. Nodes can
 * collaborate to the global model with other elements that interconnect the pieces in ways different from
 * parent-child relationships. Anyway of the structure it should have signals to tell model view constructors
 * about the expandability and availability of contents.
 */
public interface IExpandable extends ICollaboration {
    boolean collapse();

    boolean expand();

    boolean isEmpty();

    boolean isExpanded();

    boolean toggleExpand();
}
