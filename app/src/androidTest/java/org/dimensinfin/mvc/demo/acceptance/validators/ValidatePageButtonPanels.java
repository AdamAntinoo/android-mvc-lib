package org.dimensinfin.mvc.demo.acceptance.validators;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.core.ValidationSupport;

public class ValidatePageButtonPanels<C extends IAndroidController> extends ValidationSupport {

	public ValidatePageButtonPanels( final MVCWorld world ) {
		super( world );
	}

	public boolean validatePageButtonPanels( final List<Map<String, String>> dataTable, final List<C> panelsMatching ) {
		final String TITLE = "title";
		int index = 0;
		for (Map<String, String> row : dataTable) {
			final C panel = panelsMatching.get( index );
			Assert.assertTrue( this.validatePanelFieldContents( panel.getViewCache(), row, R.id.title, TITLE ) );
		}
		return true;
	}
}
