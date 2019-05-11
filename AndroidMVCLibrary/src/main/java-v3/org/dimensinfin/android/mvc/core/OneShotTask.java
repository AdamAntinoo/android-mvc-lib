//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.core;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class OneShotTask<T> implements Runnable {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 7601587036153405892L;

	// - F I E L D - S E C T I O N ............................................................................
	private T target = null;

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public OneShotTask ( T source ) {
		target = source;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public abstract void run ();

	public T getTarget () {
		return target;
	}
}

// - UNUSED CODE ............................................................................................
