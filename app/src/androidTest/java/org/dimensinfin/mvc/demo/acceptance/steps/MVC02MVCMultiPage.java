package org.dimensinfin.mvc.demo.acceptance.steps;

import androidx.test.core.app.ActivityScenario;

import org.junit.Assert;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.activity.DemoMultiPageActivity;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class MVC02MVCMultiPage extends SupportStepParent {
//	static {
//		System.setProperty( "org.mockito.android.target", ApplicationProvider.getApplicationContext().getCacheDir().getPath() );
//	}

	public MVC02MVCMultiPage( final MVCWorld world ) {
		super( world );
	}

	@Given("the DemoMultiPageActivity")
	public void the_DemoMultiPageActivity() {
		LogWrapper.info( "[GIVEN] the DemoMultiPageActivity" );
	}

	@When("the DemoMultiPageActivity activity lifecycle completes")
	public void the_DemoMultiPageActivity_activity_lifecycle_completes() {
		LogWrapper.info( "[WHEN] the DemoMultiPageActivity activity lifecycle completes" );
		LogWrapper.info( "Going to launch the scenario with the DemoMultiPageActivity..." );
		final ActivityScenario<DemoMultiPageActivity> scenario = ActivityScenario.launch(
				this.world.generateIntent( DemoMultiPageActivity.class )
		);
		Assert.assertNotNull( scenario );
		LogWrapper.info( "Store the scenario..." );
		this.world.setDemoMultiPageActivityScenario( scenario );
		this.verifyActivityLifecycleCompletion( scenario, 0 );
	}

	@When("the DemoMultiPageActivity has {string} pages")
	public void the_DemoMultiPageActivity_has_pages( final String pageCount ) {
		LogWrapper.info( "[WHEN] the DemoMultiPageActivity has {string} pages" );
		Assert.assertEquals( Integer.parseInt( pageCount ), Ristretto.activityPageCount() );
	}
}
