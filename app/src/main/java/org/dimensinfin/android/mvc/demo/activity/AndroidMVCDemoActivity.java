//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.demo.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.AbstractPagerActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// - CLASS IMPLEMENTATION ...................................................................................
public class AndroidMVCDemoActivity extends AbstractPagerActivity {
	public enum EDemoVariants {
		DEMO_PLANTS, DEMO_ANIMALS
	}

	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("AndroidMVCDemoActivity");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * On this demo we will use all the default code but still we should create two pages and the fragments insise them. I am
	 * going to use variants to change the result render but reusing the code to the limit. So even there are two pages we only
	 * have a Fragment class because the <code>variant</code> will define the <code>DataSource</code> to use.
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		logger.info(">> [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		try {
			this.addPage(new AndroidMVCDemoFragment().setVariant(EDemoVariants.DEMO_PLANTS.name()));
			this.addPage(new AndroidMVCDemoFragment().setVariant(EDemoVariants.DEMO_ANIMALS.name()));
		} catch (final Exception rtex) {
			AbstractPagerActivity.logger.severe("RTEX [AndroidMVCDemoActivity.onCreate]> Runtime Exception." + rtex.getMessage());
			rtex.printStackTrace();
		}
		// Reinitialize the tile and subtitle from the first page.
//		this.updateInitialTitle();
		logger.info("<< [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
	}
}
// - UNUSED CODE ............................................................................................
//
//	/**
//	 * Return the name of this activity.
//	 *
//	 * @return
//	 */
//	public String getName () {
//		return getApplicationContext().getResources().getString(R.string.activity_title_AndroidMVCDemoActivity);
//	}
//
