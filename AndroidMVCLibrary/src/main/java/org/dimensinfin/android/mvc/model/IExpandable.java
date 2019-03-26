package org.dimensinfin.android.mvc.model;

import org.dimensinfin.android.mvc.interfaces.ICollaboration;

/**
 * @author Adam Antinoo
 */

public interface IExpandable extends ICollaboration {
	boolean toggleExpand();

	boolean collapse();

	boolean expand();

	boolean isExpanded();

	boolean isRenderWhenEmpty();

	void setRenderWhenEmpty(final boolean renderWhenEmpty);
}
