//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoLabel extends DemoNode {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("DemoLabel");

	// - F I E L D - S E C T I O N ............................................................................
	private String title = "-TITLE-";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoLabel() {
		super();
	}

	public DemoLabel(final String newTitle) {
		this();
		title = newTitle;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//--- G E T T E R S   &   S E T T E R S
	public String getTitle() {
		return title;
	}

	public DemoLabel setTitle(final String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoLabel [ ");
		buffer.append("title: ").append(title).append(" ");
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
