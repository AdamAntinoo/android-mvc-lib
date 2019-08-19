package org.dimensinfin.android.mvc.support;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.dimensinfin.core.interfaces.ICollaboration;

import java.util.ArrayList;
import java.util.List;

public class TestNode implements ICollaboration {
	private final String name;

	public TestNode( final String name ) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				       .append(this.name)
				       .toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final TestNode emptyNode = (TestNode) o;
		return new EqualsBuilder()
				       .append(this.name, emptyNode.name)
				       .isEquals();
	}

	// - C O R E
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				       .append("name", this.name)
				       .toString();
	}

	// - C O M P A R A B L E   I N T E R F A C E
	@Override
	public int compareTo( @NonNull final Object o ) {
		if (o instanceof TestNode) {
			final TestNode target = (TestNode) o;
			return this.name.compareTo(target.name);
		} else return -1;
	}

	// - I C O L L A B O R A T I O N
	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}
}
