//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		This sample application tests and shown the correct way to use the Android Model-View-Controller
//                  library. It will create a test Activity, fill it with all the Separator varians and show the
//                  correct coding for collapse/expand and click listening with also the added code to show the item
//                  contextual menu activation.
package org.dimensinfin.android.mvc.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.dimensinfin.android.mvc.connector.MVCAppConnector;
import org.dimensinfin.android.mvc.demo.activity.AndroidMVCDemoActivity;
import org.dimensinfin.android.mvc.service.TimeTickReceiver;

import java.text.DecimalFormat;
import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * Decorator implementation to code the library requirements from the application. The dependencies are so reduced
 * that only a single method is required from the library.
 *
 * @author Adam Antinoo
 */

public class AndroidMVCApp extends Application implements IDemoAppConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = Logger.getLogger("AndroidMVCApp");
	private static final AndroidMVCApp singleton = new AndroidMVCApp();

	public static AndroidMVCApp getSingleton () {
		return singleton;
	}

	private static DecimalFormat pendingCounter = new DecimalFormat("0.0##");


	// - F I E L D - S E C T I O N ............................................................................
	private DemoAppConnector _appConnector = null;
	private TimeTickReceiver timeTickReceiver = null;
	private int topCounter = 0;
	private int marketCounter = 0;
	private Activity _activity = null;
	private Menu _appMenu = null;
	private Class<?> _firstActivity = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AndroidMVCApp () {
		logger.info(">> [AndroidMVCApp.<contructor>]");
		// Setup the referencing structures that will serve as proxy and global references.
		_appConnector = new DemoAppConnector(this);
		_firstActivity = AndroidMVCDemoActivity.class;
		logger.info("<< [AndroidMVCApp.<consructor>]");
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Menu getAppMenu () {
		if ( null != _appMenu ) return _appMenu;
		else throw new RuntimeException("RTEX [AndroidMVCAppSingleton.getAppMenu]> No menu defines and stored.");
	}

	public Class<?> getFirstActivity () {
		if ( null != _firstActivity ) return _firstActivity;
		else throw new RuntimeException(
				"RTEX [AndroidMVCAppSingleton.getFirstActivity]> No target First Activity defined.");
	}

	/**
	 * Start the background broadcast receiver to intercept the minute tick and process the data structures
	 * checking elements that should be updated because they are obsolete. New updates will be launched as
	 * separate async tasks.
	 */
	public void startTimer () {
		if ( null == timeTickReceiver ) {
			timeTickReceiver = new TimeTickReceiver(MVCAppConnector.getSingleton().getApplicationContext());
			MVCAppConnector.getSingleton().getApplicationContext().registerReceiver(timeTickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
		}
	}


	public void updateProgressSpinner () {
		if ( (marketCounter > 0) || (topCounter > 0) ) {
			double divider = 10.0;
			if ( topCounter > 10 ) {
				divider = 100.0;
			}
			startProgress(new Double(marketCounter + new Double(topCounter / divider)));
		} else {
			stopProgress();
		}
	}

	/**
	 * Gets the menu context and updates the background progress indicator with the counters.
	 *
	 * @param countIndicator
	 */
	private void startProgress (final double countIndicator) {
		// Activate menu icon of progress.
		final Menu menu = DemoAppConnector.getSingleton().getAppMenu();
		if ( null != menu ) {
			final MenuItem updatingItem = menu.findItem(R.id.action_launchUpdate);
			final LayoutInflater mInflater = (LayoutInflater) MVCAppConnector.getSingleton().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			final View actionView = mInflater.inflate(R.layout.actionbar_indeterminateprogress, null);
			if ( null != updatingItem ) {
				updatingItem.setVisible(true);
				updatingItem.setActionView(actionView);
			}
			final TextView counter = (TextView) actionView.findViewById(R.id.progressCounter);
			if ( null != counter ) {
				counter.setVisibility(View.VISIBLE);
				// REFACTOR This should be configured on the XML and not on the
				// code. If the progress is set on a library
				// then it should be styled.
				final Typeface daysFace = Typeface.createFromAsset(MVCAppConnector.getSingleton().getApplicationContext().getAssets(), "fonts/Days.otf");
				counter.setTypeface(daysFace);
				counter.setText(pendingCounter.format(countIndicator));
			}
			actionView.invalidate();
		}
	}

	/**
	 * Hides the background progress indicator.
	 */
	private void stopProgress () {
		// Clear the update progress.
		final Menu menu = DemoAppConnector.getSingleton().getAppMenu();
		if ( null != menu ) {
			final MenuItem updatingItem = menu.findItem(R.id.action_launchUpdate);
			final LayoutInflater mInflater = (LayoutInflater) MVCAppConnector.getSingleton().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			final View actionView = mInflater.inflate(R.layout.actionbar_indeterminateprogress, null);
			if ( null != updatingItem ) {
				updatingItem.setActionView(null);
				updatingItem.setVisible(false);
			}
			final TextView counter = (TextView) actionView.findViewById(R.id.progressCounter);
			if ( null != counter ) {
				counter.setVisibility(View.GONE);
			}
			actionView.invalidate();
		}
	}

	public void incrementTopCounter () {
		topCounter++;
	}

	public void incrementMarketCounter () {
		marketCounter++;
	}

	public int getTopCounter () {
		return topCounter;
	}

	public void setTopCounter (final int topCounter) {
		this.topCounter = topCounter;
	}

	public int getMarketCounter () {
		return marketCounter;
	}

	public void setMarketCounter (final int marketCounter) {
		this.marketCounter = marketCounter;
	}

	public void activateActivity (final Activity currentActivity) {
		_activity = currentActivity;
	}

	@Override
	public String getResourceString (final int referenceId) {
		try {
			return getResources().getString(referenceId);
		} catch (RuntimeException rtex) {
			rtex.printStackTrace();
			return "-STRING-";
		}
	}
}
