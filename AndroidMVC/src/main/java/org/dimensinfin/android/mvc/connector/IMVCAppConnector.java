//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.connector;

import org.dimensinfin.android.connector.IAndroidAppConnector;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IMVCAppConnector extends IAndroidAppConnector {
	public Class<?> getFirstActivity();
}

// - UNUSED CODE ............................................................................................
