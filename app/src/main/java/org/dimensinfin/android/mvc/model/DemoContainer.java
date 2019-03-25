package org.dimensinfin.android.mvc.model;

/**
 * @author Adam Antinoo
 */

public class DemoContainer extends Container {
	// - F I E L D - S E C T I O N
	private String name = "-CONTAINER-NAME-";

	// - C O N S T R U C T O R - S E C T I O N
	public DemoContainer() {
		super();
	}

	public String getName() {
		return name;
	}

	public DemoContainer setName(final String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoContainer [");
		buffer.append("name: ").append(name).append(" ");
		buffer.append("[  ").append(getContentSize()).append(" ");
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
