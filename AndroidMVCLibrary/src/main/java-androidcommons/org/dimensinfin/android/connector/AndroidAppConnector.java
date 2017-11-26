//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.connector;

import java.io.File;

import org.joda.time.Instant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;

// - CLASS IMPLEMENTATION ...................................................................................
public class AndroidAppConnector implements IAndroidAppConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	//	private static Logger								logger	= Logger.getLogger("AndroidAppDecorator");

	// - F I E L D - S E C T I O N ............................................................................
	private final IAndroidAppConnector _connector;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AndroidAppConnector(final IAndroidAppConnector application) {
		this._connector = application;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Context getApplicationContext() {
		if (null == this._connector)
			throw new RuntimeException(
					"RTEX [AndroidAppDecorator.getApplicationContext]> Application connection not defined. Functionality 'getApplicationContext' disabled.");
		else
			return this._connector.getApplicationContext();
	}

	public Resources getResources() {
		if (null == this._connector)
			throw new RuntimeException(
					"RTEX [AndroidAppDecorator.getResources]> Application connection not defined. Functionality 'getResources' disabled.");
		else
			return this._connector.getResources();
	}
	/**
	 * Checks that the current parameter timestamp is still on the frame of the window.
	 * 
	 * @param timestamp
	 *          the current and last timestamp of the object.
	 * @param window
	 *          time span window in milliseconds.
	 */
	public boolean checkExpiration(final long timestamp, final long window) {
		// logger.info("-- Checking expiration for " + timestamp + ". Window " + window);
		if (0 == timestamp) return true;
		final long now = Instant.now().getMillis();
		final long endWindow = timestamp + window;
		if (now < endWindow)
			return false;
		else
			return true;
	}
	public String getResourceString(final int reference) {
		//		logger.info(">< [GenericAppConnector.getResourceString]> Accessing resource: " + reference);
		return getResources().getString(reference);
	}
	/**
	 * Return the file that points to the application folder on the external (SDCARD) storage.
	 */
	public File getAppDirectory(final int appname) {
		return new File(Environment.getExternalStorageDirectory(), getResourceString(appname));
	}
	/**
	 * This method checks if the application has access to the external disk (SDCARD) and if that access
	 * included the writing operations.<br>
	 * This method should be called before any expected access to the filesystem by the minor number of classes
	 * because it is a method strictly related to the Android OS. The execution may change the state of some
	 * external variables but taking on account that this state may change dynamically I would prefer to call
	 * repeatedly the method than storing the initial call results.
	 * 
	 * @return if the FS is writable. This also implies that the SDCARD is available.
	 */
	public boolean sdcardAvailable() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		final String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state))
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else
			// Something else is wrong. It may be one of many other states, but all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		return mExternalStorageWriteable;
	}

	public boolean getBooleanPreference(final String preferenceName, final boolean defaultValue) {
		// Read the flag values from the preferences.
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		boolean pref = sharedPrefs.getBoolean(preferenceName, defaultValue);
		return pref;
	}

	public boolean checkNetworkAccess() {
		final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if ((netInfo != null) && netInfo.isConnectedOrConnecting()) return true;
		return false;
	}

	public Object getSystemService(final String name) {
		if (null == this._connector)
			throw new RuntimeException(
					"RTEX [AndroidAppDecorator.getSystemService]> Application connection not defined. Functionality 'getSystemService' disabled.");
		else
			return this._connector.getSystemService(name);
	}
}

// - UNUSED CODE ............................................................................................
