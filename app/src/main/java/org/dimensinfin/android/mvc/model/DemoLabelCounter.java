package org.dimensinfin.android.mvc.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DemoLabelCounter extends DemoLabel {
	// - F I E L D - S E C T I O N
	private int counter = 0;

	// - G E T T E R S   &   S E T T E R S
	public int getCounter() {
		return counter;
	}

	public DemoLabelCounter setCounter(final int counter) {
		this.counter = counter;
		return this;
	}

	// -  C O R E
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final DemoLabelCounter that = (DemoLabelCounter) o;
		return new EqualsBuilder()
				.appendSuper(super.equals(o))
				.append(counter, that.counter)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.appendSuper(super.hashCode())
				.append(counter)
				.toHashCode();
	}
}
