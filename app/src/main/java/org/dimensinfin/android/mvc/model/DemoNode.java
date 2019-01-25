//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import org.dimensinfin.core.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoNode implements ICollaboration {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("DemoNode");

	// - F I E L D - S E C T I O N ............................................................................
	public String jsonClass = "DemoNode";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoNode() {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//--- I C O L L A B O R A T I O N   I N T E R F A C E
	@Override
	public List<ICollaboration> collaborate2Model(final String variant) {
		return new ArrayList<>();
	}

	//--- G E T T E R S   &   S E T T E R S
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoLabel [ ");
		buffer.append("jsonClass: ").append(jsonClass).append(" ");
		buffer.append("]");
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
