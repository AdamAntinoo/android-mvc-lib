package org.dimensinfin.mvc.demo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.android.mvc.domain.AndroidIconResource;
import org.dimensinfin.android.mvc.interfaces.IIconReference;

/**
 * @author Adam Antinoo
 */
public class DemoItem extends DemoLabel {
	// - F I E L D - S E C T I O N
	private IIconReference icon = new AndroidIconResource();

	// - C O N S T R U C T O R - S E C T I O N
	public DemoItem() {
		super();
	}

	// - M E T H O D - S E C T I O N
	public int getIconIdentifier() {
		return icon.getIdentifier();
	}

	public DemoItem setIcon( final int resourceIdentifier ) {
		icon = new AndroidIconResource( resourceIdentifier );
		return this;
	}

	// - C O R E
	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				       .append( "title", title )
				       .toString();
	}
}
