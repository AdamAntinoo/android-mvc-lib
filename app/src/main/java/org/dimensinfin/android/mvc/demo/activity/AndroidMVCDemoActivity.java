//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.demo.activity;

import android.os.Bundle;
import org.dimensinfin.android.mvc.activity.AbstractPagerActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidMVCDemoActivity extends AbstractPagerActivity {
//	private static Logger logger = LoggerFactory.getLogger(AndroidMVCDemoActivity.class);

	public enum EDemoVariants {
		NON_EXPANDABLE_SECTION, EXPANDABLE_SECTION
	}

	// - M E T H O D - S E C T I O N

	/**
	 * On this demo we will use all the default code but still we should create two pages and the fragments insise them. I
	 * am going to use variants to change the result render but reusing the code to the limit. So even there are two pages
	 * we only have a Fragment class because the <code>variant</code> will define the <code>DataSource</code> to use.
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		logger.info(">> [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		// Process the parameters into the context. This initial Activity is the only one with no parameters.
		this.addPage(new AndroidMVCDemoFragment(this.getApplicationContext()).setVariant(EDemoVariants.NON_EXPANDABLE_SECTION.name()));
//		this.addPage(new AndroidMVCDemoFragment().setVariant(EDemoVariants.EXPANDABLE_SECTION.name()));
		logger.info("<< [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
	}
}
