package org.dimensinfin.android.mvc.model;

import android.support.annotation.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;

import java.util.ArrayList;
import java.util.List;

public class DemoHeaderTitle implements ICollaboration {
	private static final long serialVersionUID = 7084637836405461264L;

	// - F I E L D - S E C T I O N
	private String name = "-APPLICATION NAME-";
	private String version = "-VERSION-";

	// - C O N S T R U C T O R - S E C T I O N
//	public DemoHeaderTitle() {
//	}

	public DemoHeaderTitle(@NonNull final String name, @NonNull final String version) {
		this.name = name;
		this.version = version;
	}

	//- M E T H O D - S E C T I O N
	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	// - I C O L L A B O R A T I O N   I N T E R F A C E
	@Override
	public List<ICollaboration> collaborate2Model(final String variant) {
		return new ArrayList<ICollaboration>();
	}

	// - C O M P A R A B L E   I N T E R F A C E
	@Override
	public int compareTo(@NonNull final Object o) {
		if (o instanceof DemoHeaderTitle) {
			final DemoHeaderTitle target = (DemoHeaderTitle) o;
			return this.name.compareTo(target.name);
		} else return -1;
	}

	// - C O R E
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final DemoHeaderTitle that = (DemoHeaderTitle) o;
		return new EqualsBuilder()
				.append(name, that.name)
				.append(version, that.version)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(name)
				.append(version)
				.toHashCode();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoHeaderTitle [");
		buffer.append(getName()).append(" - ").append(getVersion());
		buffer.append(" ]");
		return buffer.toString();
	}
}
