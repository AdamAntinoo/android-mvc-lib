//	PROJECT:        EVEIndustrialist (EVEI)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API11.
//	DESCRIPTION:		Application helper for Eve Online Industrialists. Will help on Industry and Manufacture.

package org.dimensinfin.evedroid.activity.core;

//- IMPORT SECTION .........................................................................................
import org.dimensinfin.evedroid.EVEDroidApp;
import org.dimensinfin.evedroid.constant.AppWideConstants;
import org.dimensinfin.evedroid.storage.AppModelStore;

import android.os.Bundle;
import android.util.Log;

//- CLASS IMPLEMENTATION ...................................................................................
/**
 * This abstract Activity will collect all the common code that is being used on the new Activity pattern.
 * Most of the new activities change minor actions on some methods while sharing all the rest of the code.<br>
 * This class implements a generic Activity with a swipe gesture multi page layout and Titled pages that will
 * show names only if the number of pages is more than 1. Current implementation ises a cicle indicator but
 * will be transistioned to a Titled indicator. The base code will take care of the menu and the Action tool
 * bar.
 * 
 * @author Adam Antinoo
 */
public abstract class PilotPagerActivity extends AbstractPagerActivity {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	protected AppModelStore	_store	= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		Log.i("EVEI", ">> PilotPagerActivity.onCreate"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_pager);
		try {
			// Process the parameters into the context.
			final Bundle extras = getIntent().getExtras();
			if (null == extras)
				throw new RuntimeException(
						"RT IndustryDirectorActivity.onCreate - Unable to continue. Required parameters not defined on Extras.");
			//Instantiate the pilot from the characterID.
			final long characterid = extras.getLong(AppWideConstants.extras.EXTRA_EVECHARACTERID);
			if (characterid > 0) {
				// Initialize the access to the global structures.
				this._store = EVEDroidApp.getAppStore();
				this._store.activatePilot(characterid);
				this._store.activateActivity(this);
			}
		} catch (final Exception rtex) {
			Log.e("EVEI", "RTEX> PilotPagerActivity.onCreate - " + rtex.getMessage());
			rtex.printStackTrace();
			stopActivity(new RuntimeException("RTEX> PilotPagerActivity.onCreate - " + rtex.getMessage()));
		}
		Log.i("EVEI", "<< PilotPagerActivity.onCreate"); //$NON-NLS-1$
	}

	/**
	 * Save the store to their persistent file before releasing the control to another activity that will then
	 * be able to make use of that data structures.
	 */
	@Override
	protected void onPause() {
		Log.i("NEOCOM", ">> PilotPagerActivity.onPause");
		// Check store state and update cache on disk if it has changed.
		if (this._store.isDirty()) this._store.save();
		super.onPause();
		Log.i("NEOCOM", "<< PilotPagerActivity.onPause");
	}

	@Override
	protected void onSaveInstanceState(final Bundle savedInstanceState) {
		Log.i("NEOCOM", ">> PilotPagerActivity.onSaveInstanceState"); //$NON-NLS-1$
		super.onSaveInstanceState(savedInstanceState);
		// Add current model data dependencies. EVECHARACTERID
		savedInstanceState.putLong(AppWideConstants.extras.EXTRA_EVECHARACTERID, this._store.getPilot().getCharacterID());
		this._store.save();
		Log.i("NEOCOM", "<< PilotPagerActivity.onSaveInstanceState"); //$NON-NLS-1$
	}
}
//- UNUSED CODE ............................................................................................
