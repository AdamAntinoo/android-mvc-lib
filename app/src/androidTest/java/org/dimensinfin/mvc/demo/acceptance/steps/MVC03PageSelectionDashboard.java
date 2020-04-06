package org.dimensinfin.mvc.demo.acceptance.steps;

import android.app.Activity;
import androidx.test.core.app.ActivityScenario;

import java.util.List;

import org.junit.Assert;

import org.dimensinfin.android.mvc.core.MVCScheduler;
import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.TypeMatcher;
import org.dimensinfin.mvc.demo.activity.PageSelectionDashboardActivity;
import org.dimensinfin.mvc.demo.controller.PageButtonController;

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

	@When("there is a click on PageButton panel {string} we move to activity {string}")
	public void there_is_a_click_on_PageButton_panel_we_move_to_activity( final String targetPanelNumber, final String destinationActivityName ) {
		final List<PageButtonController> targetControllers = (List<PageButtonController>) Ristretto.withTypex(
				Ristretto.accessHeaderPanels( this.world.getSelectedPage() ),
				new TypeMatcher( PageButtonController.class )
		);
		Assert.assertNotNull( targetControllers );
		Assert.assertTrue( targetControllers.size() > 0 );
		final PageButtonController target = targetControllers.get( 0 );
		Assert.assertNotNull( target );
		target.onClick( target.getViewCache() );
		Ristretto.waitForCompletion( () -> {
			MVCScheduler.backgroundExecutor.submit( () -> {
				LoggerWrapper.info( "Detect destination activity..." );
				final Activity currentActivity = this.getActivityInstance();
				Assert.assertNotNull( currentActivity );
				Assert.assertEquals( destinationActivityName, currentActivity.getClass().getSimpleName() );
			} );
		} );
	}
}
