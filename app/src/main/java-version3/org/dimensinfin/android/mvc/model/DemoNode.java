package org.dimensinfin.android.mvc.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.dimensinfin.core.interfaces.ICollaboration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * This is the most simplest class for any model node on the application demo for the MVC library. Just defines the json
 * class that is something unused on the demo but that allows to use inheritance over the model instances to check this
 * point.
 * @author Adam Antinoo
 */
public class DemoNode implements ICollaboration {
	// - F I E L D - S E C T I O N
	public String jsonClass = "DemoNode";

	// - C O N S T R U C T O R - S E C T I O N
	public DemoNode() {
		super();
	}

	// - I C O L L A B O R A T I O N   I N T E R F A C E
	@Override
	public List<ICollaboration> collaborate2Model(final String variant) {
		return new ArrayList<>();
	}

	// - G E T T E R S   &   S E T T E R S
	public String getJsonClass() {
		return jsonClass;
	}

	// - C O M P A R A B L E   I N T E R F A C E
	@Override
	public int compareTo(@NonNull final Object o) {
		if (o instanceof DemoNode) {
			final DemoNode target = (DemoNode) o;
			return this.jsonClass.compareTo(target.jsonClass);
		} else return -1;
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
}
