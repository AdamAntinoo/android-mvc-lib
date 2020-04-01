package org.dimensinfin.mvc.demo.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;
import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;

public class MVCDemoSimpleActivity extends MVCMultiPageActivity {
	/**
	 * This demo will only generate a list of values on a simple list with no hierarchies that when rendered will generate also a list of small
	 * panels that will reflect the model data.
	 * This simple activity will use the header to render the application name and the section title that should not scroll with the content list.
	 * The list is populated with enough elements to be able to show scrolling but only when the page layout is horizontal.
	 * The activity has a single page so the multipage indicator is not visible.
	 */
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		LoggerWrapper.info( ">> [AndroidMVCDemoActivity.onCreate]" ); //$NON-NLS-1$
		super.onCreate( savedInstanceState );
		// Process the parameters into the context. This initial Activity is the only one with no parameters.
		this.addPage( new MVCDemoSimpleFragment().setVariant( PageDefinitions.MVCDEMOLIST_ITEMS.name() ) );
		LoggerWrapper.info( "<< [AndroidMVCDemoActivity.onCreate]" ); //$NON-NLS-1$
	}
}
