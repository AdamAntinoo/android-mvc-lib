package org.dimensinfin.mvc.demo.acceptance.steps;

import androidx.test.core.app.ActivityScenario;

import org.junit.Assert;

import org.dimensinfin.mvc.demo.acceptance.support.core.LoggerWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.activity.PageSelectionDashboardActivity;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class MVC03PageSelectionDashboard extends SupportStepParent {
	public MVC03PageSelectionDashboard( final MVCWorld world ) {
		super( world );
	}

	@Given("the PageSelectionDashboardActivity")
	public void the_PageSelectionDashboardActivity() {
		LoggerWrapper.info( "[GIVEN] the PageSelectionDashboardActivity" );
	}

	@When("the PageSelectionDashboardActivity activity lifecycle completes")
	public void the_PageSelectionDashboardActivity_activity_lifecycle_completes() {
		LoggerWrapper.info( "[WHEN] the PageSelectionDashboardActivity activity lifecycle completes" );
		LoggerWrapper.info( "Going to launch the scenario with the PageSelectionDashboardActivity..." );
		final ActivityScenario<PageSelectionDashboardActivity> scenario = ActivityScenario.launch(
				this.world.generateIntent( PageSelectionDashboardActivity.class )
		);
		Assert.assertNotNull( scenario );
		LoggerWrapper.info( "Store the scenario..." );
		this.world.setPageSelectionDashboardActivityScenario( scenario );
		this.verifyActivityLifecycleCompletion( scenario, 0 );
	}
	@And("the PageSelectionDashboardActivity has {string} pages")
	public void the_PageSelectionDashboardActivity_has_pages( final String pageCount ) {
		Assert.assertEquals( Integer.parseInt( pageCount ), Ristretto.activityPageCount() );
	}

	//	@When("there is a click on {string} panel {string} we move to activity {string}")
	public void there_is_a_click_on_panel_we_move_to_activity( String string, String string2, String string3 ) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

}
