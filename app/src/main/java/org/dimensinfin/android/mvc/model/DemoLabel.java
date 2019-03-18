package org.dimensinfin.android.mvc.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */
public class DemoLabel extends DemoNode {
	// - F I E L D - S E C T I O N
	private String title = "-TITLE-";

	// - C O N S T R U C T O R - S E C T I O N
	public DemoLabel() {
		super();
	}

	public DemoLabel(final String newTitle) {
		this();
		title = newTitle;
	}

	// - M E T H O D - S E C T I O N
	// - G E T T E R S   &   S E T T E R S
	public String getTitle() {
		return title;
	}

	public DemoLabel setTitle(final String title) {
		this.title = title;
		return this;
	}

	// -  C O R E
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final DemoLabel demoLabel = (DemoLabel) o;
		return new EqualsBuilder()
				.append(title, demoLabel.title)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(title)
				.toHashCode();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoLabel [ ");
		buffer.append("title: ").append(title).append(" ");
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
