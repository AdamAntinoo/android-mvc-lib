//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.connector;

import org.dimensinfin.android.connector.AndroidAppConnector;

// - CLASS IMPLEMENTATION ...................................................................................
public class MVCAppConnector extends AndroidAppConnector implements IMVCAppConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static MVCAppConnector _singleton = null;

	public static MVCAppConnector getSingleton() {
		if (null == MVCAppConnector._singleton) throw new RuntimeException(
				"RTEX [MVCAppConnector.getSingleton]> Application chain not initialized. All class functionalities disabled.");
		return MVCAppConnector._singleton;
	}

	// - F I E L D - S E C T I O N ............................................................................
	private final IMVCAppConnector _connector;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MVCAppConnector(final IMVCAppConnector application) {
		super(application);
		_connector = application;
		MVCAppConnector._singleton = this;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//	@Override
	//	public Context getApplicationContext() {
	//		if (null == _connector)
	//			throw new RuntimeException(
	//					"RTEX [MVCAppConnector.getApplicationContext]> Application connection not defined. Functionality 'getApplicationContext' disabled.");
	//		else
	//			return _connector.getApplicationContext();
	//	}

	@Override
	public Class<?> getFirstActivity() {
		if (null == _connector)
			throw new RuntimeException(
					"RTEX [MVCAppConnector.getFirstActivity]> Application connection not defined. Functionality 'getFirstActivity' disabled.");
		else
			return _connector.getFirstActivity();
	}
	//
	//	@Override
	//	public Resources getResources() {
	//		if (null == _connector)
	//			throw new RuntimeException(
	//					"RTEX [MVCAppConnector.getResources]> Application connection not defined. Functionality 'getResources' disabled.");
	//		else
	//			return _connector.getResources();
	//	}
	//
	//	@Override
	//	public Object getSystemService(final String name) {
	//		if (null == _connector)
	//			throw new RuntimeException(
	//					"RTEX [MVCAppConnector.getSystemService]> Application connection not defined. Functionality 'getSystemService' disabled.");
	//		else
	//			return _connector.getSystemService(name);
	//	}
}

// - UNUSED CODE ............................................................................................
