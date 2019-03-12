package org.dimensinfin.android.mvc.demo.activity;

import android.os.Bundle;
import org.dimensinfin.android.mvc.activity.AbstractPagerActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidMVCDemoActivity extends AbstractPagerActivity {

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
		this.addPage(new AndroidMVCDemoFragment().setVariant(EDemoVariants.NON_EXPANDABLE_SECTION.name()));
//		this.addPage(new AndroidMVCDemoFragment().setVariant(EDemoVariants.EXPANDABLE_SECTION.name()));
		logger.info("<< [AndroidMVCDemoActivity.onCreate]"); //$NON-NLS-1$
	}
}
