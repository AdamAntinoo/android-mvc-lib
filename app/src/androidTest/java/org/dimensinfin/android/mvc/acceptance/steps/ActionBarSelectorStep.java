package org.dimensinfin.android.mvc.acceptance.steps;

import android.app.ActionBar;
import android.view.View;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

import org.dimensinfin.android.mvc.acceptance.ristretto.Ristretto;
import org.dimensinfin.android.mvc.acceptance.support.ActionBarTypes;
import org.dimensinfin.android.mvc.acceptance.support.MVCValidators;
import org.dimensinfin.android.mvc.acceptance.support.core.AcceptanceNeoComLogger;
import org.dimensinfin.android.mvc.acceptance.support.core.MVCWorld;

import cucumber.api.java.en.Then;

public class ActionBarSelectorStep {
	private MVCWorld world;

	public ActionBarSelectorStep( final MVCWorld world ) {
		this.world = world;
		Ristretto.setWorld( this.world );
	}

	@Then("there is a action bar of type {string} with the next fields")
	public void there_is_a_action_bar_of_type_with_the_next_fields( final String actionBarName,
	                                                                final List<Map<String, String>> dataTable ) {
		AcceptanceNeoComLogger.info( "[THEN] there is a action bar of type {string} with the next fields" );
//		final ActionBar actionBar = Objects.requireNonNull( this.world.getActiveActivity().getActionBar() );
//		final View view = actionBar.getCustomView();
//		Assert.assertNotNull( view );
//		final ActionBarTypes actionBarType = ActionBarTypes.valueOf( actionBarName );
//		switch (actionBarType) {
//			case APP_VERSION:
//				Assert.assertTrue( MVCValidators.validateAppVersionActionBar( dataTable.get( 0 ), view ) );
//				break;
//			default:
//				throw new RuntimeException( "The Action Bar type " + actionBarName + " is not registered." );
//		}
	}
}
