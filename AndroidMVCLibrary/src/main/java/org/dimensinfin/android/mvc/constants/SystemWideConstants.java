//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.constants;

// - IMPORT SECTION .........................................................................................

// - CLASS IMPLEMENTATION ...................................................................................
public class SystemWideConstants {
	// - F I R E D   E V E N T S
	public enum events {
		ADD_CHILD, REMOVE_CHILD, EVENTSTRUCTURE_ACTIONEXPANDCOLLAPSE, EVENTSTRUCTURE_NEWDATA, EVENTSTRUCTURE_DOWNLOADDATA, EVENTADAPTER_REQUESTNOTIFYCHANGES
	}

	// - S T A T I C - S E C T I O N ..........................................................................
/** This flag controls of the view adapter caches the generated views into the Part to optimize the code execution. */
	public static final boolean ENABLECACHE = false;
}

// - UNUSED CODE ............................................................................................
