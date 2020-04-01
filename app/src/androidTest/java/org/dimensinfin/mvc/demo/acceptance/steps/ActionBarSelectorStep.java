package org.dimensinfin.mvc.demo.acceptance.steps;

import java.util.List;
import java.util.Map;

import org.dimensinfin.mvc.demo.acceptance.support.MVCValidators;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.acceptance.support.core.AcceptanceNeoComLogger;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;

import cucumber.api.java.en.Then;

public class ActionBarSelectorStep {
	private MVCWorld world;

	public ActionBarSelectorStep( final MVCWorld world ) {
		this.world = world;
		Ristretto.setWorld( this.world );
		MVCValidators.setWorld(this.world);
	}

	@Then("there is a action bar of type {string} with the next fields")
	public void there_is_a_action_bar_of_type_with_the_next_fields( final String actionBarName,
	                                                                final List<Map<String, String>> dataTable ) {
		AcceptanceNeoComLogger.info( "[THEN] there is a action bar of type {string} with the next fields" );
//		final ActionBar actionBar = Objects.requireNonNull( this.world.getActiveActivity().getActionBar() );
//		final View view = actionBar.getCustomView();
//		Assert.assertNotNull( view );
//		final ActionBarTypes actionBarType = ActionBarTypes.from( actionBarName );
//		switch (actionBarType) {
//			case APP_VERSION:
//				Assert.assertTrue( MVCValidators.validateAppVersionActionBar( dataTable.get( 0 ), view ) );
//				break;
//			default:
//				throw new NullPointerException( "The Action Bar type " + actionBarName + " is not registered." );
//		}
	}
}
