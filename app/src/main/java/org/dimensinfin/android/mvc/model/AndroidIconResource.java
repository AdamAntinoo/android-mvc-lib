//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.interfaces.IIconReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class AndroidIconResource implements IIconReference {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("AndroidIconResource");

	// - F I E L D - S E C T I O N ............................................................................
	public int resourceId = R.drawable.defaulticonplaceholder;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AndroidIconResource() {
		super();
	}

	public AndroidIconResource(final int resourceIdentifier) {
		this();
		resourceId = resourceIdentifier;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public int getIdentifier(){
		return resourceId;
	}
	//--- G E T T E R S   &   S E T T E R S
	//	@Override
	//	public String toString() {
	//		StringBuffer buffer = new StringBuffer("AndroidIconResource [ ");
	//		buffer.append("name: ").append(0);
	//		buffer.append("]");
	//		buffer.append("->").append(super.toString());
	//		return buffer.toString();
	//	}
}
// - UNUSED CODE ............................................................................................
//[01]
