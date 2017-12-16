//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.core;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class OneShotTask<T> implements Runnable {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 7601587036153405892L;

	// - F I E L D - S E C T I O N ............................................................................
	private T part = null;

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public OneShotTask (T sourcePart) {
		part = sourcePart;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public abstract void run ();

	public T getTargetPart () {
		return part;
	}
}

// - UNUSED CODE ............................................................................................
