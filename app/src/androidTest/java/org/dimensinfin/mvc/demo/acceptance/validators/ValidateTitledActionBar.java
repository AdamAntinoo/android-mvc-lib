package org.dimensinfin.mvc.demo.acceptance.validators;

import android.view.View;

import java.util.Map;

import org.junit.Assert;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.core.ValidationSupport;

public class ValidateTitledActionBar<C extends IAndroidController> extends ValidationSupport {
	public ValidateTitledActionBar( final MVCWorld world ) {
		super( world );
	}

	public boolean validateTitledActionBar( final Map<String, String> expectedData, final View targetView ) {
		final String TITLE = "title";

		Assert.assertTrue( this.validatePanelFieldContentsNoCaps( expectedData, targetView, R.id.title, TITLE ) );
		return true;
	}
}
