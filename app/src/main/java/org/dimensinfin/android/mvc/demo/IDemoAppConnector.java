//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		This sample application tests and shown the correct way to use the Android Model-View-Controller
//                  library. It will create a test Activity, fill it with all the Separator varians and show the
//                  correct coding for collapse/expand and click listening with also the added code to show the item
//                  contextual menu activation.
package org.dimensinfin.android.mvc.demo;


import android.view.Menu;

import org.dimensinfin.android.mvc.connector.IMVCAppConnector;

public interface IDemoAppConnector extends IMVCAppConnector {
	public String getResourceString (int referenceId);

	public Menu getAppMenu ();
}
