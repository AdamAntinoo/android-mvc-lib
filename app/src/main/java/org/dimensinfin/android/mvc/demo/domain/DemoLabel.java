package org.dimensinfin.android.mvc.demo.domain;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.android.mvc.core.domain.MVCNode;

/**
 * @author Adam Antinoo
 */
public class DemoLabel extends MVCNode {
	// - F I E L D - S E C T I O N
	public String title = "-TITLE-";

	// - C O N S T R U C T O R - S E C T I O N
	public DemoLabel() {
		super();
	}

	public DemoLabel( final String newTitle ) {
		this();
		title = newTitle;
	}

	// - M E T H O D - S E C T I O N
	// - G E T T E R S   &   S E T T E R S
	public String getTitle() {
		return title;
	}

	public DemoLabel setTitle( final String title ) {
		this.title = title;
		return this;
	}

	// -  C O R E
	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final DemoLabel demoLabel = (DemoLabel) o;
		return new EqualsBuilder()
				       .append( this.title, demoLabel.title )
				       .isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				       .append( this.title )
				       .toHashCode();
	}

	@Override
	public int compareTo( @NonNull final Object o ) {
		if (o instanceof DemoLabel) {
			final DemoLabel target = (DemoLabel) o;
			return this.title.compareTo( target.title );
		} else return -1;
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				       .append( "title", this.title )
				       .toString();
	}
}
