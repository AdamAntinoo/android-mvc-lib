//	PROJECT:        NeoCom.model (NEOC.M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Isolated model structures to access and manage Eve Online character data and their
//									available databases.
//									This version includes the access to the latest 6.x version of eveapi libraries to
//									download ad parse the CCP XML API data.
//									Code integration that is not dependent on any specific platform.
package org.dimensinfin.android.mvc.model;

//- IMPORT SECTION .........................................................................................

import org.dimensinfin.android.model.AbstractViewableNode;
import org.dimensinfin.android.model.Separator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.AbstractComplexNode;
import org.dimensinfin.core.model.IGEFNode;

import java.util.ArrayList;
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
	public ArrayList<AbstractComplexNode> collaborate2Model (final String variant) {
		return new ArrayList<AbstractComplexNode>();
	}
	public String toString () {
		StringBuffer buffer = new StringBuffer("DemoHeaderTitle [");
		buffer.append(getName()).append(" - ").append(getVersion());
		buffer.append(" ]");
		return buffer.toString();
	}
}

// - UNUSED CODE ............................................................................................
