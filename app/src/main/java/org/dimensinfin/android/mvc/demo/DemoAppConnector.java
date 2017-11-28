//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		This sample application tests and shown the correct way to use the Android Model-View-Controller
//                  library. It will create a test Activity, fill it with all the Separator varians and show the
//                  correct coding for collapse/expand and click listening with also the added code to show the item
//                  contextual menu activation.
package org.dimensinfin.android.mvc.demo;

import org.dimensinfin.android.mvc.connector.MVCAppConnector;

import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * Decorator implementation to code the library requirements from the application. The dependencies are so reduced
 * that only a single method is required from the library.
 *
 * @author Adam Antinoo
 */

public class DemoAppConnector extends MVCAppConnector implements IDemoAppConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = Logger.getLogger("DemoAppConnector");
	private static DemoAppConnector _singleton = null;
	public static DemoAppConnector getSingleton () {
		if ( null == DemoAppConnector._singleton ) throw new RuntimeException(
				"RTEX [DemoAppConnector.getSingleton]> Application chain not initialized. All class functionalities disabled.");
		return DemoAppConnector._singleton;
	}


	// - F I E L D - S E C T I O N ............................................................................
	private IDemoAppConnector _appConnector = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoAppConnector (IDemoAppConnector application) {
				super(application);
		logger.info(">> [DemoAppConnector.<contructor>]");
		// Setup the referencing structures that will serve as proxy and global references.
		_appConnector = application;
		logger.info("<< [DemoAppConnector.<consructor>]");
	}

	// - M E T H O D - S E C T I O N ..........................................................................
}
