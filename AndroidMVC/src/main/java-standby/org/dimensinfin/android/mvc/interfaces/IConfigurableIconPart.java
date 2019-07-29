//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//									a generic data graph into a AndroidController hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.interfaces;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IConfigurableIconPart {
	public int getIconReference ();

	public IConfigurableIconPart setIconReference (int resourceIdentifier);
}

// - UNUSED CODE ............................................................................................
