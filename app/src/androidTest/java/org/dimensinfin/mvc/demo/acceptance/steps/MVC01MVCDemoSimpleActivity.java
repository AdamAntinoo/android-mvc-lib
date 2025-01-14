package org.dimensinfin.mvc.demo.acceptance.steps;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import org.junit.Assert;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.activity.MVCDemoSimpleActivity;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MVC01MVCDemoSimpleActivity {
	private MVCWorld world;

	static {
		System.setProperty( "org.mockito.android.target", ApplicationProvider.getApplicationContext().getCacheDir().getPath() );
	}

	public MVC01MVCDemoSimpleActivity( final MVCWorld world ) {
		this.world = world;
		Ristretto.setWorld( this.world );
	}

	@Given("the MVCDemoSimpleActivity")
	public void theMVCDemoSimpleActivity() {
		LogWrapper.info( "[GIVEN] the MVCDemoSimpleActivity" );
	}

	@When("the MVCDemoSimpleActivity activity lifecycle completes for page {string}")
	public void the_MVCDemoSimpleActivity_activity_lifecycle_completes_for_page( final String pageNumber ) {
		LogWrapper.info( "[WHEN] the MVCDemoSimpleActivity activity lifecycle completes for page {string}" );
		LogWrapper.info( "Going to launch the scenario with the MVCDemoSimpleActivity..." );
		final ActivityScenario<MVCDemoSimpleActivity> scenario = ActivityScenario.launch(
				this.world.generateIntent( MVCDemoSimpleActivity.class )
		);
		Assert.assertNotNull( scenario );
		LogWrapper.info( "Store the scenario..." );
		this.world.setMvcDemoSimpleActivityScenario( scenario );
		scenario.onActivity( ( activity ) -> {
			LogWrapper.info( "Verify the activity starts without exceptions..." );
			Assert.assertNotNull( activity );
			LogWrapper.info( "Store the activity..." + activity.getClass().getSimpleName() );
			this.world.setActiveActivity( activity );
			LogWrapper.info( "Set the configured page..." + pageNumber );
			this.world.setSelectedPage( Integer.parseInt( pageNumber ) );
			LogWrapper.info( "Verify the current activity state..." );
			final ActivityLifecycleMonitor registry = ActivityLifecycleMonitorRegistry.getInstance();
			final Stage stage = registry.getLifecycleStageOf( activity );
			Assert.assertEquals( Stage.RESUMED, stage );
		} );
		Ristretto.waitForBackground( () -> {
			Ristretto.waitForCompletion( () -> {
				LogWrapper.info( "Invalidate all the display and wait termination..." );
				Ristretto.updateDisplay();
			} );
		} );
	}
		@Then("the MVCDemoSimpleActivity has {string} pages")
	public void the_MVCDemoSimpleActivity_has_pages( final String expectedPageCount ) {
		LogWrapper.info( "[THEN] the MVCDemoSimpleActivity has {string} pages" );
		Assert.assertEquals( Integer.parseInt( expectedPageCount ), Ristretto.activityPageCount() );
	}
}