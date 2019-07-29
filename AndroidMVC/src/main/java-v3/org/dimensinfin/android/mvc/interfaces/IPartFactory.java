//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.interfaces;

import org.dimensinfin.core.interfaces.ICollaboration;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IPartFactory {
	// - M E T H O D - S E C T I O N ..........................................................................
	public IPart createPart(ICollaboration model);

	public String getVariant();
}

// - UNUSED CODE ............................................................................................
