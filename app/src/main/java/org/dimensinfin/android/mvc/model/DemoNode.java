package org.dimensinfin.android.mvc.model;

import android.support.annotation.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */
public class DemoNode /*extends EmptyNode*/ implements ICollaboration, Comparable {
	// - F I E L D - S E C T I O N
	public String jsonClass = "DemoNode";

	// - C O N S T R U C T O R - S E C T I O N
	public DemoNode() {
		super();
	}

	// - M E T H O D - S E C T I O N
	// - I C O L L A B O R A T I O N   I N T E R F A C E
	@Override
	public List<ICollaboration> collaborate2Model(final String variant) {
		return new ArrayList<>();
	}

	// - G E T T E R S   &   S E T T E R S
	public String getJsonClass() {
		return jsonClass;
	}

	// - C O R E
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final DemoNode demoNode = (DemoNode) o;
		return new EqualsBuilder()
				.append(jsonClass, demoNode.jsonClass)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(jsonClass)
				.toHashCode();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoLabel [ ");
		buffer.append("jsonClass: ").append(jsonClass).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public int compareTo(@NonNull final Object o) {
		if (o instanceof DemoNode) {
			final DemoNode target = (DemoNode) o;
			return this.jsonClass.compareTo(target.jsonClass);
		} else return -1;
	}
}
