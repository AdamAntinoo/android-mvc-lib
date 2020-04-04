package org.dimensinfin.mvc.demo.acceptance.steps;

import android.app.ActionBar;
import android.view.View;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;

import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.ActionBarTypes;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.validators.ValidateTitledActionBar;

import cucumber.api.java.en.Then;

public class MVC00ActionBarSelectorStep extends SupportStepParent {
	public MVC00ActionBarSelectorStep( final MVCWorld world ) {
		super( world );
	}

	@Then("there is an action bar of type {string} with the next fields")
	public void there_is_an_action_bar_of_type_with_the_next_fields( final String actionBarName,
	                                                                 final List<Map<String, String>> dataTable ) {
		LoggerWrapper.info( "[THEN] there is a action bar of type {string} with the next fields" );
		final ActionBarTypes actionBarType = ActionBarTypes.from( actionBarName );
		final ActionBar actionBar = Objects.requireNonNull(
				Objects.requireNonNull( this.world.getActiveActivity() )
						.getActionBar()
		);
		final View view = actionBar.getCustomView();
		Assert.assertNotNull( view );
		switch (actionBarType) {
			case TITLED_ACTIONBAR:
				Assert.assertTrue( new ValidateTitledActionBar( this.world ).validateTitledActionBar( dataTable.get( 0 ), view ) );
				break;
//			case MULTI_PAGE_ACTIONBAR_TYPE:
//				Assert.assertTrue( MVCValidators.validateMultiPageActionBar( dataTable.get( 0 ), view ) );
//				break;
			default:
				throw new NotImplementedException( "The Action Bar type " + actionBarName + " is not registered." );
		}
	}
}