package org.dimensinfin.android.mvc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.core.domain.Node;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IExpandable;

import java.util.ArrayList;
import java.util.List;

public class Container<M> extends Node implements IExpandable {
	private static final long serialVersionUID = -957283664928489030L;

	// - F I E L D - S E C T I O N
	private final List<M> contents = new ArrayList<>();
	private boolean expanded = false;

	// - C O N S T R U C T O R S
	public Container() {
		super();
	}

	// - M E T H O D - S E C T I O N
	public int addContent( final M node ) {
		this.contents.add(node);
		return contents.size();
	}

	public void clean() {
		this.contents.clear();
	}

	public List<M> getContents() {
		return contents;
	}

	public int getContentSize() {
		return this.contents.size();
	}

	// - I E X P A N D A B L E
	public boolean collapse() {
		this.expanded = false;
		return this.expanded;
	}

	public boolean expand() {
		this.expanded = true;
		return this.expanded;
	}

	public boolean isEmpty() {
		if (getContentSize() > 0)
			return true;
		else
			return false;
	}

	public boolean isExpanded() {
		return this.expanded;
	}

	public boolean toggleExpand() {
		this.expanded = !this.expanded;
		return this.expanded;
	}

	// - I C O L L A B O R A T I O N

	/**
	 * Check if the <code>Container</code> has contents and then add all them to the model.
	 */
	@Override
	public List<ICollaboration> collaborate2Model( final String variant ) {
		return (List<ICollaboration>) this.getContents();
	}

	// - C O R E
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				       .append("contents", this.contents)
				       .append("expanded", this.expanded)
				       .toString();
	}
}
