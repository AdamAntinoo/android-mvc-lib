//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.model;

import org.dimensinfin.core.interfaces.ICollaboration;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoHeaderTitle implements ICollaboration {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 7084637836405461264L;

	// - F I E L D - S E C T I O N ............................................................................
	private String name = "-APPLICATION NAME-";
	private String version = "-VERSION-";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoHeaderTitle () {
	}

	public DemoHeaderTitle (final String name, String version) {
		this.name = name;
		this.version = version;
	}

	//- M E T H O D - S E C T I O N ..........................................................................
	public String getName () {
		return name;
	}

	public void setName (final String name) {
		this.name = name;
	}

	public String getVersion () {
		return version;
	}

	public void setVersion (final String version) {
		this.version = version;
	}

	@Override
	public List<ICollaboration> collaborate2Model (final String variant) {
		return new Vector<ICollaboration>();
	}
	public String toString () {
		StringBuffer buffer = new StringBuffer("DemoHeaderTitle [");
		buffer.append(getName()).append(" - ").append(getVersion());
		buffer.append(" ]");
		return buffer.toString();
	}
}

// - UNUSED CODE ............................................................................................
