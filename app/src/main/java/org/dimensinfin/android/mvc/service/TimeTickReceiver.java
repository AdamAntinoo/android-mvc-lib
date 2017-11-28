//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API11.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.dimensinfin.android.mvc.demo.AndroidMVCApp;

import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This class is a one minute timer activated at startup. It will search for data obsolete and fire the
 * services required to update that data by downloading new CCP api calls.<br>
 * There are two sets of services, one for character data and the other for market data that have quite
 * different mechanics. All data updates the background counters that inform the user that some background
 * services are active and the number of elements on their queues.
 *
 * @author Adam Antinoo
 */
public class TimeTickReceiver extends BroadcastReceiver {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger					= Logger.getLogger("TimeTickReceiver");

	// - F I E L D - S E C T I O N ............................................................................
	private Context _context				= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public TimeTickReceiver(final Context context) {
		_context = context;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Receive an intent on every minute tick.<br>
	 * We should scan the list of pending data for queued requests and after termination we have to check for
	 * character structures that need update depending on their different valid periods.<br>
	 * Pending requests are processed by priority and the number of requests queued to the service is limited so
	 * more requests will be queued on the next tick if they are more than the queue limit.<br>
	 * The user data is refreshed on step two and the time limit is one hour.<br>
	 * The asset information is also updated on step 2 but with a duration of 8 hours. <br>
	 * Every minute the process checks for pending market data downloads.
	 */
	@Override
	public void onReceive(final Context context, final Intent intent) {
		TimeTickReceiver.logger.info(">> [TimeTickReceiver.onReceive]");
		// This is a demo of the progress indidcator. Just increment the counters.
		AndroidMVCApp.getSingleton().incrementMarketCounter();
		final int mCounter = AndroidMVCApp.getSingleton().getMarketCounter();
		// Every 50 market add onw to top counter.
		if(mCounter>50){
			AndroidMVCApp.getSingleton().incrementTopCounter();
			AndroidMVCApp.getSingleton().setMarketCounter(0);
		}
		TimeTickReceiver.logger.info("<< [TimeTickReceiver.onReceive]> " + AndroidMVCApp.getSingleton().getTopCounter()
				+ "/" + AndroidMVCApp.getSingleton().getMarketCounter() + "]");
	}
}

// - UNUSED CODE ............................................................................................
