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
package org.dimensinfin.android.mvc.constants;

// - IMPORT SECTION .........................................................................................

// - CLASS IMPLEMENTATION ...................................................................................
public class SystemWideConstants {
	// - F I R E D   E V E N T S
	public enum events {
		EVENTSTRUCTURE_NEWDATA, EVENTSTRUCTURE_DOWNLOADDATA, EVENTSTRUCTURE_DELETEDATA,
		EVENTCONTENTS_ACTIONEXPANDCOLLAPSE,
		EVENTADAPTER_REQUESTNOTIFYCHANGES
	}

	// - S T A T I C - S E C T I O N ..........................................................................
	/** This flag controls of the view adapter caches the generated views into the Part to optimize the code execution. */
	public static final boolean ENABLECACHE = true;
}

// - UNUSED CODE ............................................................................................
