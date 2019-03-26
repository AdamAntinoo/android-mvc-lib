package org.dimensinfin.android.mvc.model;

/**
 * @author Adam Antinoo
 */

public interface IExpandable {
	boolean toggleExpand();

	boolean collapse();

	boolean expand();

	boolean isExpanded();

	boolean isRenderWhenEmpty();

	void setRenderWhenEmpty(final boolean renderWhenEmpty);
}
