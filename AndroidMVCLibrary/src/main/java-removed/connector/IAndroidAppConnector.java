//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.connector;

import android.content.Context;
import android.content.res.Resources;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IAndroidAppConnector {
	public Context getApplicationContext();

	public Resources getResources();

	public Object getSystemService(String name);
}

// - UNUSED CODE ............................................................................................
