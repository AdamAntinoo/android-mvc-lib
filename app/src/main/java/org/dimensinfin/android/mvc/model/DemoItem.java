package org.dimensinfin.android.mvc.model;

import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IIconReference;

/**
 * @author Adam Antinoo
 */
public class DemoItem extends DemoLabel {
	// - F I E L D - S E C T I O N
	private IIconReference icon = new AndroidIconResource(R.drawable.defaulticonplaceholder);

	// - C O N S T R U C T O R - S E C T I O N
	public DemoItem() {
		super();
	}

	// - M E T H O D - S E C T I O N
	public int getIconIdentifier() {
		return icon.getIdentifier();
	}

	public DemoItem setIcon(final int resourceIdentifier) {
		icon = new AndroidIconResource(resourceIdentifier);
		return this;
	}

	// - G E T T E R S   &   S E T T E R S
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoItem [ ");
		buffer.append("title: ").append(getTitle());
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
