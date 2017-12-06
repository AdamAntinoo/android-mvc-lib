//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. This is the Android application but shares
//                  libraries and code with other application designed for alternate platforms.
//                  The model management is shown using a generic Model View Controller that allows make the
//                  rendering of the model data similar on all the platforms used.
//                  Latest version adds support for ESI authentication since CREST and other sources will be
//                  removed on 2018.
package org.dimensinfin.android.mvc.demo.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.AbstractPagerActivity;
import org.dimensinfin.android.mvc.demo.AndroidMVCApp;
import org.dimensinfin.android.mvc.demo.R;


// - CLASS IMPLEMENTATION ...................................................................................
public class AndroidMVCDemoActivity extends AbstractPagerActivity {
	public enum EDemoVariants{
		DEMO_SEPARATOR_CATALOG,DEMO_SEPARATOR_DETAIL
	}
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * Return the name of this activity.
	 *
	 * @return
	 */
	public String getName () {
		return getApplicationContext().getResources().getString(R.string.activity_title_AndroidMVCDemoActivity);
	}

	/**
	 * On the Activity create phase we will set the layout, then create the action bar and all other UI elements
	 * and finally creates and sets the fragments. This is to avoid the multiple creation and addition of more
	 * fragments when the activity is put again on the foreground.
	 */
	@Override
	protected void onCreate (final Bundle savedInstanceState) {
		logger.info(">> [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		try {
			// Process the parameters into the context. This initial Activity is the only one with no parameters.
			// Create the pages that form this Activity. Each page implemented by a Fragment.
			int page = 0;
			// Register this Activity as the current active Activity.
			AndroidMVCApp.getSingleton().activateActivity(this);
			this.addPage(new AndroidMVCDemoFragment().setVariant(EDemoVariants.DEMO_SEPARATOR_CATALOG.name()), page++);
		} catch (final Exception rtex) {
			AbstractPagerActivity.logger.severe("RTEX [AndroidMVCDemoActivity.onCreate]> Runtime Exception." + rtex.getMessage());
			rtex.printStackTrace();
			this.stopActivity(rtex);
		}
		// Reinitialize the tile and subtitle from the first page.
		this.updateInitialTitle();
		logger.info("<< [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
	}
}
// - UNUSED CODE ............................................................................................
