//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import org.dimensinfin.core.model.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoContainer extends Container {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("DemoContainer");

	// - F I E L D - S E C T I O N ............................................................................
private String name = "-CONTAINER-NAME-";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoContainer() {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//--- G E T T E R S   &   S E T T E R S
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
