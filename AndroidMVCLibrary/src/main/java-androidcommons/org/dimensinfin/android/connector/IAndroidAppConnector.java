//	PROJECT:        Core.Android (C.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library to define core interfaces and code for standard development of native Android
//									applications. Will provide the contants and Decorators to isolate implementations
//									and generate the request to implement the correct methods.
package org.dimensinfin.android.connector;

import android.content.Context;
import android.content.res.Resources;

// - CLASS IMPLEMENTATION ...................................................................................
public interface IAndroidAppConnector {
	public Context getApplicationContext();

	public Resources getResources();

	public Object getSystemService(String name);
}

// - UNUSED CODE ............................................................................................
