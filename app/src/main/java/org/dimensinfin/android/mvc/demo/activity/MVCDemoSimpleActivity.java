package org.dimensinfin.android.mvc.demo.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;

public class AndroidMVCDemoActivity extends MVCMultiPageActivity {
	/**
	 * On this demo we will use all the default code but still we should create two pages and the fragments inside them. I
	 * am going to use variants to change the result render but reusing the code to the limit. So even there are two pages
	 * we only have a Fragment class because the <code>variant</code> will define the <code>DataSource</code> to use.
	 */
//	@LogEnterExit
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		LoggerWra.info(">> [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		// Process the parameters into the context. This initial Activity is the only one with no parameters.
		this.addPage(new AndroidMVCDemoFragment().setVariant(PageDefinitions.MVCDEMOLIST_ITEMS.name()));
//		this.addPage(new AndroidMVCDemoFragment().setVariant(EDemoVariants.EXPANDABLE_SECTION.name()));
//		this.addPage(new DatabaseDemoFragment().setVariant(EDemoVariants.DATABASE_SECTION.name()));
		logger.info("<< [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
	}
}
